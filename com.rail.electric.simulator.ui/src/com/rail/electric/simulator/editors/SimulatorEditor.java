package com.rail.electric.simulator.editors;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.draw2d.ConnectionLayer;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.ViewportAwareConnectionLayerClippingStrategy;
import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.KeyStroke;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.MouseWheelHandler;
import org.eclipse.gef.MouseWheelZoomHandler;
import org.eclipse.gef.SnapToGeometry;
import org.eclipse.gef.SnapToGrid;
import org.eclipse.gef.dnd.TemplateTransferDragSourceListener;
import org.eclipse.gef.dnd.TemplateTransferDropTargetListener;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.rulers.RulerProvider;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.AlignmentAction;
import org.eclipse.gef.ui.actions.CopyTemplateAction;
import org.eclipse.gef.ui.actions.DirectEditAction;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.gef.ui.actions.MatchHeightAction;
import org.eclipse.gef.ui.actions.MatchSizeAction;
import org.eclipse.gef.ui.actions.MatchWidthAction;
import org.eclipse.gef.ui.actions.PasteTemplateAction;
import org.eclipse.gef.ui.actions.ToggleGridAction;
import org.eclipse.gef.ui.actions.ToggleRulerVisibilityAction;
import org.eclipse.gef.ui.actions.ToggleSnapToGeometryAction;
import org.eclipse.gef.ui.actions.ZoomInAction;
import org.eclipse.gef.ui.actions.ZoomOutAction;
import org.eclipse.gef.ui.palette.PaletteViewer;
import org.eclipse.gef.ui.palette.PaletteViewerProvider;
import org.eclipse.gef.ui.parts.GraphicalEditorWithFlyoutPalette;
import org.eclipse.gef.ui.parts.GraphicalViewerKeyHandler;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.gef.ui.rulers.RulerComposite;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.util.TransferDropTargetListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.SaveAsDialog;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.IPageSite;
import org.eclipse.ui.views.properties.IPropertySheetPage;

import com.rail.electric.simulator.SimulatorContextMenuProvider;
import com.rail.electric.simulator.SimulatorMessages;
import com.rail.electric.simulator.SimulatorPlugin;
import com.rail.electric.simulator.dnd.TextTransferDropTargetListener;
import com.rail.electric.simulator.model.SimulatorDiagram;
import com.rail.electric.simulator.model.SimulatorRuler;
import com.rail.electric.simulator.palette.SimulatorPaletteCustomizer;
import com.rail.electric.simulator.parts.GraphicalPartFactory;
import com.rail.electric.simulator.properties.SimulatorPropertySheetPage;
import com.rail.electric.simulator.rulers.SimulatorRulerProvider;

public class SimulatorEditor extends GraphicalEditorWithFlyoutPalette {

	private KeyHandler sharedKeyHandler;
	private PaletteRoot root;
	private boolean editorSaving = false;

	// This class listens to changes to the file system in the workspace, and
	// makes changes accordingly.
	// 1) An open, saved file gets deleted -> close the editor
	// 2) An open file gets renamed or moved -> change the editor's input
	// accordingly
	class ResourceTracker implements IResourceChangeListener,
			IResourceDeltaVisitor {
		public void resourceChanged(IResourceChangeEvent event) {
			IResourceDelta delta = event.getDelta();
			try {
				if (delta != null)
					delta.accept(this);
			} catch (CoreException exception) {
				// What should be done here?
			}
		}

		public boolean visit(IResourceDelta delta) {
			if (delta == null
					|| !delta.getResource().equals(
							((IFileEditorInput) getEditorInput()).getFile()))
				return true;

			if (delta.getKind() == IResourceDelta.REMOVED) {
				Display display = getSite().getShell().getDisplay();
				if ((IResourceDelta.MOVED_TO & delta.getFlags()) == 0) { // if
																			// the
																			// file
																			// was
																			// deleted
					// NOTE: The case where an open, unsaved file is deleted is
					// being handled by the
					// PartListener added to the Workbench in the initialize()
					// method.
					display.asyncExec(new Runnable() {
						public void run() {
							if (!isDirty())
								closeEditor(false);
						}
					});
				} else { // else if it was moved or renamed
					final IFile newFile = ResourcesPlugin.getWorkspace()
							.getRoot().getFile(delta.getMovedToPath());
					display.asyncExec(new Runnable() {
						public void run() {
							superSetInput(new FileEditorInput(newFile));
						}
					});
				}
			} else if (delta.getKind() == IResourceDelta.CHANGED) {
				if (!editorSaving) {
					// the file was overwritten somehow (could have been
					// replaced by another
					// version in the respository)
					final IFile newFile = ResourcesPlugin.getWorkspace()
							.getRoot().getFile(delta.getFullPath());
					Display display = getSite().getShell().getDisplay();
					display.asyncExec(new Runnable() {
						public void run() {
							setInput(new FileEditorInput(newFile));
							getCommandStack().flush();
						}
					});
				}
			}
			return false;
		}
	}

	private IPartListener partListener = new IPartListener() {
		// If an open, unsaved file was deleted, query the user to either do a
		// "Save As"
		// or close the editor.
		public void partActivated(IWorkbenchPart part) {
			if (part != SimulatorEditor.this)
				return;
			if (!((IFileEditorInput) getEditorInput()).getFile().exists()) {
				Shell shell = getSite().getShell();
				String title = SimulatorMessages.GraphicalEditor_FILE_DELETED_TITLE_UI;
				String message = SimulatorMessages.GraphicalEditor_FILE_DELETED_WITHOUT_SAVE_INFO;
				String[] buttons = {
						SimulatorMessages.GraphicalEditor_SAVE_BUTTON_UI,
						SimulatorMessages.GraphicalEditor_CLOSE_BUTTON_UI };
				MessageDialog dialog = new MessageDialog(shell, title, null,
						message, MessageDialog.QUESTION, buttons, 0);
				if (dialog.open() == 0) {
					if (!performSaveAs())
						partActivated(part);
				} else {
					closeEditor(false);
				}
			}
		}

		public void partBroughtToTop(IWorkbenchPart part) {
		}

		public void partClosed(IWorkbenchPart part) {
		}

		public void partDeactivated(IWorkbenchPart part) {
		}

		public void partOpened(IWorkbenchPart part) {
		}
	};

	private SimulatorDiagram simulatorDiagram = new SimulatorDiagram();
	private ResourceTracker resourceListener = new ResourceTracker();
	private RulerComposite rulerComp;

	protected static final String PALETTE_DOCK_LOCATION = "Dock location"; //$NON-NLS-1$
	protected static final String PALETTE_SIZE = "Palette Size"; //$NON-NLS-1$
	protected static final String PALETTE_STATE = "Palette state"; //$NON-NLS-1$
	protected static final int DEFAULT_PALETTE_SIZE = 130;

	static {
		SimulatorPlugin.getDefault().getPreferenceStore()
				.setDefault(PALETTE_SIZE, DEFAULT_PALETTE_SIZE);
	}

	public SimulatorEditor() {
		setEditDomain(new DefaultEditDomain(this));
	}

	protected void closeEditor(boolean save) {
		getSite().getPage().closeEditor(SimulatorEditor.this, save);
	}

	public void commandStackChanged(EventObject event) {
		firePropertyChange(IEditorPart.PROP_DIRTY);
		super.commandStackChanged(event);
	}

	@Override
	protected void configureGraphicalViewer() {
		super.configureGraphicalViewer();
		ScrollingGraphicalViewer viewer = (ScrollingGraphicalViewer) getGraphicalViewer();

		ScalableFreeformRootEditPart root = new ScalableFreeformRootEditPart();

		// set clipping strategy for connection layer
		ConnectionLayer connectionLayer = (ConnectionLayer) root
				.getLayer(LayerConstants.CONNECTION_LAYER);
		connectionLayer
				.setClippingStrategy(new ViewportAwareConnectionLayerClippingStrategy(
						connectionLayer));

		List zoomLevels = new ArrayList(3);
		zoomLevels.add(ZoomManager.FIT_ALL);
		zoomLevels.add(ZoomManager.FIT_WIDTH);
		zoomLevels.add(ZoomManager.FIT_HEIGHT);
		root.getZoomManager().setZoomLevelContributions(zoomLevels);

		IAction zoomIn = new ZoomInAction(root.getZoomManager());
		IAction zoomOut = new ZoomOutAction(root.getZoomManager());
		getActionRegistry().registerAction(zoomIn);
		getActionRegistry().registerAction(zoomOut);
		getSite().getKeyBindingService().registerAction(zoomIn);
		getSite().getKeyBindingService().registerAction(zoomOut);

		viewer.setRootEditPart(root);

		viewer.setEditPartFactory(new GraphicalPartFactory());
		ContextMenuProvider provider = new SimulatorContextMenuProvider(viewer,
				getActionRegistry());
		viewer.setContextMenu(provider);
		getSite().registerContextMenu(
				"org.eclipse.gef.examples.logic.editor.contextmenu", //$NON-NLS-1$
				provider, viewer);
		viewer.setKeyHandler(new GraphicalViewerKeyHandler(viewer)
				.setParent(getCommonKeyHandler()));

		loadProperties();

		// Actions
		IAction showRulers = new ToggleRulerVisibilityAction(
				getGraphicalViewer());
		getActionRegistry().registerAction(showRulers);

		IAction snapAction = new ToggleSnapToGeometryAction(
				getGraphicalViewer());
		getActionRegistry().registerAction(snapAction);

		IAction showGrid = new ToggleGridAction(getGraphicalViewer());
		getActionRegistry().registerAction(showGrid);

		Listener listener = new Listener() {
			public void handleEvent(Event event) {
				handleActivationChanged(event);
			}
		};
		getGraphicalControl().addListener(SWT.Activate, listener);
		getGraphicalControl().addListener(SWT.Deactivate, listener);
	}

	protected void writeToOutputStream(OutputStream os) throws IOException {
		ObjectOutputStream out = new ObjectOutputStream(os);
		out.writeObject(getSimulatorDiagram());
		out.close();
	}

	protected CustomPalettePage createPalettePage() {
		return new CustomPalettePage(getPaletteViewerProvider()) {
			public void init(IPageSite pageSite) {
				super.init(pageSite);
				IAction copy = getActionRegistry().getAction(
						ActionFactory.COPY.getId());
				pageSite.getActionBars().setGlobalActionHandler(
						ActionFactory.COPY.getId(), copy);
			}
		};
	}

	protected PaletteViewerProvider createPaletteViewerProvider() {
		return new PaletteViewerProvider(getEditDomain()) {
			private IMenuListener menuListener;

			protected void configurePaletteViewer(PaletteViewer viewer) {
				super.configurePaletteViewer(viewer);
				viewer.setCustomizer(new SimulatorPaletteCustomizer());
				viewer.addDragSourceListener(new TemplateTransferDragSourceListener(
						viewer));
			}

			protected void hookPaletteViewer(PaletteViewer viewer) {
				super.hookPaletteViewer(viewer);
				final CopyTemplateAction copy = (CopyTemplateAction) getActionRegistry()
						.getAction(ActionFactory.COPY.getId());
				viewer.addSelectionChangedListener(copy);
				if (menuListener == null)
					menuListener = new IMenuListener() {
						public void menuAboutToShow(IMenuManager manager) {
							manager.appendToGroup(
									GEFActionConstants.GROUP_COPY, copy);
						}
					};
				viewer.getContextMenu().addMenuListener(menuListener);
			}
		};
	}

	public void dispose() {
		getSite().getWorkbenchWindow().getPartService()
				.removePartListener(partListener);
		partListener = null;
		((IFileEditorInput) getEditorInput()).getFile().getWorkspace()
				.removeResourceChangeListener(resourceListener);
		super.dispose();
	}

	public void doSave(final IProgressMonitor progressMonitor) {
		editorSaving = true;
		Platform.run(new SafeRunnable() {
			public void run() throws Exception {
				saveProperties();
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				writeToOutputStream(out);
				IFile file = ((IFileEditorInput) getEditorInput()).getFile();
				file.setContents(new ByteArrayInputStream(out.toByteArray()),
						true, false, progressMonitor);
				getCommandStack().markSaveLocation();
			}
		});
		editorSaving = false;
	}

	public void doSaveAs() {
		performSaveAs();
	}

	public Object getAdapter(Class type) {
		/*if (type == IContentOutlinePage.class) {
			outlinePage = new OutlinePage(new TreeViewer());
			return outlinePage;
		}*/
		if (type == IPropertySheetPage.class) {
			return new SimulatorPropertySheetPage();
		}
		if (type == ZoomManager.class)
			return getGraphicalViewer().getProperty(
					ZoomManager.class.toString());

		return super.getAdapter(type);
	}

	protected Control getGraphicalControl() {
		return rulerComp;
	}

	/**
	 * Returns the KeyHandler with common bindings for both the Outline and
	 * Graphical Views. For example, delete is a common action.
	 */
	protected KeyHandler getCommonKeyHandler() {
		if (sharedKeyHandler == null) {
			sharedKeyHandler = new KeyHandler();
			sharedKeyHandler.put(
					KeyStroke.getPressed(SWT.F2, 0),
					getActionRegistry().getAction(
							GEFActionConstants.DIRECT_EDIT));
		}
		return sharedKeyHandler;
	}

	protected SimulatorDiagram getSimulatorDiagram() {
		return simulatorDiagram;
	}

	protected PaletteRoot getPaletteRoot() {
		if (root == null) {
			root = SimulatorEditorPaletteFactory.createPalette();
		}
		return root;
	}

	public void gotoMarker(IMarker marker) {
	}

	protected void handleActivationChanged(Event event) {
		IAction copy = null;
		if (event.type == SWT.Deactivate)
			copy = getActionRegistry().getAction(ActionFactory.COPY.getId());
		if (getEditorSite().getActionBars().getGlobalActionHandler(
				ActionFactory.COPY.getId()) != copy) {
			getEditorSite().getActionBars().setGlobalActionHandler(
					ActionFactory.COPY.getId(), copy);
			getEditorSite().getActionBars().updateActionBars();
		}
	}

	protected void initializeGraphicalViewer() {
		super.initializeGraphicalViewer();
		getGraphicalViewer().setContents(getSimulatorDiagram());

		getGraphicalViewer()
				.addDropTargetListener(
						(TransferDropTargetListener) new TemplateTransferDropTargetListener(
								getGraphicalViewer()));
		getGraphicalViewer()
				.addDropTargetListener(
						(TransferDropTargetListener) new TextTransferDropTargetListener(
								getGraphicalViewer(), TextTransfer
										.getInstance()));
	}

	protected void createActions() {
		super.createActions();
		ActionRegistry registry = getActionRegistry();
		IAction action;

		action = new CopyTemplateAction(this);
		registry.registerAction(action);

		action = new MatchSizeAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new MatchWidthAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new MatchHeightAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new PasteTemplateAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		/*action = new IncrementDecrementAction(this, true);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new IncrementDecrementAction(this, false);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());*/

		action = new DirectEditAction((IWorkbenchPart) this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new AlignmentAction((IWorkbenchPart) this,
				PositionConstants.LEFT);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new AlignmentAction((IWorkbenchPart) this,
				PositionConstants.RIGHT);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new AlignmentAction((IWorkbenchPart) this,
				PositionConstants.TOP);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new AlignmentAction((IWorkbenchPart) this,
				PositionConstants.BOTTOM);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new AlignmentAction((IWorkbenchPart) this,
				PositionConstants.CENTER);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new AlignmentAction((IWorkbenchPart) this,
				PositionConstants.MIDDLE);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.ui.parts.GraphicalEditor#createGraphicalViewer(org.eclipse
	 * .swt.widgets.Composite)
	 */
	protected void createGraphicalViewer(Composite parent) {
		rulerComp = new RulerComposite(parent, SWT.NONE);
		super.createGraphicalViewer(rulerComp);
		rulerComp
				.setGraphicalViewer((ScrollingGraphicalViewer) getGraphicalViewer());
	}

	protected FigureCanvas getEditor() {
		return (FigureCanvas) getGraphicalViewer().getControl();
	}

	public boolean isSaveAsAllowed() {
		return true;
	}

	protected void loadProperties() {
		// Ruler properties
		SimulatorRuler ruler = getSimulatorDiagram().getRuler(PositionConstants.WEST);
		RulerProvider provider = null;
		if (ruler != null) {
			provider = new SimulatorRulerProvider(ruler);
		}
		getGraphicalViewer().setProperty(RulerProvider.PROPERTY_VERTICAL_RULER,
				provider);
		ruler = getSimulatorDiagram().getRuler(PositionConstants.NORTH);
		provider = null;
		if (ruler != null) {
			provider = new SimulatorRulerProvider(ruler);
		}
		getGraphicalViewer().setProperty(
				RulerProvider.PROPERTY_HORIZONTAL_RULER, provider);
		getGraphicalViewer().setProperty(
				RulerProvider.PROPERTY_RULER_VISIBILITY,
				new Boolean(getSimulatorDiagram().getRulerVisibility()));

		// Snap to Geometry property
		getGraphicalViewer().setProperty(SnapToGeometry.PROPERTY_SNAP_ENABLED,
				new Boolean(getSimulatorDiagram().isSnapToGeometryEnabled()));

		// Grid properties
		getGraphicalViewer().setProperty(SnapToGrid.PROPERTY_GRID_ENABLED,
				new Boolean(getSimulatorDiagram().isGridEnabled()));
		// We keep grid visibility and enablement in sync
		getGraphicalViewer().setProperty(SnapToGrid.PROPERTY_GRID_VISIBLE,
				new Boolean(getSimulatorDiagram().isGridEnabled()));

		// Zoom
		ZoomManager manager = (ZoomManager) getGraphicalViewer().getProperty(
				ZoomManager.class.toString());
		if (manager != null)
			manager.setZoom(getSimulatorDiagram().getZoom());
		// Scroll-wheel Zoom
		getGraphicalViewer().setProperty(
				MouseWheelHandler.KeyGenerator.getKey(SWT.MOD1),
				MouseWheelZoomHandler.SINGLETON);

	}

	protected boolean performSaveAs() {
		SaveAsDialog dialog = new SaveAsDialog(getSite().getWorkbenchWindow()
				.getShell());
		dialog.setOriginalFile(((IFileEditorInput) getEditorInput()).getFile());
		dialog.open();
		IPath path = dialog.getResult();

		if (path == null)
			return false;

		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		final IFile file = workspace.getRoot().getFile(path);

		if (!file.exists()) {
			WorkspaceModifyOperation op = new WorkspaceModifyOperation() {
				public void execute(final IProgressMonitor monitor) {
					saveProperties();
					try {
						ByteArrayOutputStream out = new ByteArrayOutputStream();
						writeToOutputStream(out);
						file.create(
								new ByteArrayInputStream(out.toByteArray()),
								true, monitor);
						out.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			try {
				new ProgressMonitorDialog(getSite().getWorkbenchWindow()
						.getShell()).run(false, true, op);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		try {
			superSetInput(new FileEditorInput(file));
			getCommandStack().markSaveLocation();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	protected void saveProperties() {
		getSimulatorDiagram().setRulerVisibility(
				((Boolean) getGraphicalViewer().getProperty(
						RulerProvider.PROPERTY_RULER_VISIBILITY))
						.booleanValue());
		getSimulatorDiagram().setGridEnabled(
				((Boolean) getGraphicalViewer().getProperty(
						SnapToGrid.PROPERTY_GRID_ENABLED)).booleanValue());
		getSimulatorDiagram().setSnapToGeometry(
				((Boolean) getGraphicalViewer().getProperty(
						SnapToGeometry.PROPERTY_SNAP_ENABLED)).booleanValue());
		ZoomManager manager = (ZoomManager) getGraphicalViewer().getProperty(
				ZoomManager.class.toString());
		if (manager != null)
			getSimulatorDiagram().setZoom(manager.getZoom());
	}

	protected void setInput(IEditorInput input) {
		superSetInput(input);

		IFile file = ((IFileEditorInput) input).getFile();
		try {
			InputStream is = file.getContents(false);
			ObjectInputStream ois = new ObjectInputStream(is);
			setSimulatorDiagram((SimulatorDiagram) ois.readObject());
			ois.close();
		} catch (Exception e) {
			// This is just an example. All exceptions caught here.
			e.printStackTrace();
		}

		if (!editorSaving) {
			if (getGraphicalViewer() != null) {
				getGraphicalViewer().setContents(getSimulatorDiagram());
				loadProperties();
			}
			/*if (outlinePage != null) {
				outlinePage.setContents(getSimulatorDiagram());
			}*/
		}
	}

	public void setSimulatorDiagram(SimulatorDiagram diagram) {
		simulatorDiagram = diagram;
	}

	protected void superSetInput(IEditorInput input) {
		// The workspace never changes for an editor. So, removing and re-adding
		// the
		// resourceListener is not necessary. But it is being done here for the
		// sake
		// of proper implementation. Plus, the resourceListener needs to be
		// added
		// to the workspace the first time around.
		if (getEditorInput() != null) {
			IFile file = ((IFileEditorInput) getEditorInput()).getFile();
			file.getWorkspace().removeResourceChangeListener(resourceListener);
		}

		super.setInput(input);

		if (getEditorInput() != null) {
			IFile file = ((IFileEditorInput) getEditorInput()).getFile();
			file.getWorkspace().addResourceChangeListener(resourceListener);
			setPartName(file.getName());
		}
	}

	protected void setSite(IWorkbenchPartSite site) {
		super.setSite(site);
		getSite().getWorkbenchWindow().getPartService()
				.addPartListener(partListener);
	}

}
