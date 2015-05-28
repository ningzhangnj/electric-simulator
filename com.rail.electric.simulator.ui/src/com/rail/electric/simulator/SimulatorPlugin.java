package com.rail.electric.simulator;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gef.palette.CombinedTemplateCreationEntry;
import org.eclipse.gef.palette.ConnectionCreationToolEntry;
import org.eclipse.gef.palette.MarqueeToolEntry;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteEntry;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.PaletteSeparator;
import org.eclipse.gef.palette.PaletteStack;
import org.eclipse.gef.palette.PanningSelectionToolEntry;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gef.requests.SimpleFactory;
import org.eclipse.gef.tools.MarqueeSelectionTool;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.rail.electric.simulator.figures.LEDFigure;
import com.rail.electric.simulator.model.LED;
import com.rail.electric.simulator.model.SimulatorFlowContainer;
import com.rail.electric.simulator.model.SimulatorLabel;
import com.rail.electric.simulator.tools.SimulatorCreationTool;

public class SimulatorPlugin extends AbstractUIPlugin {
	// The plug-in ID
	public static final String PLUGIN_ID = "com.ericsson.wrbs.viewer.ui";
	
	// The shared instance
	private static SimulatorPlugin plugin;
	
	public static Dimension getMaximumSizeFor(Class modelClass) {
		if (LED.class.equals(modelClass)) {
			return LEDFigure.SIZE;
		} 
		return IFigure.MAX_DIMENSION;
	}
	
	public static Dimension getMinimumSizeFor(Class modelClass) {
		if (SimulatorLabel.class.equals(modelClass)) {
			return new Dimension(IFigure.MIN_DIMENSION.width, 30);
		} else if (LED.class.equals(modelClass)) {
			return LEDFigure.SIZE;
		} 
		return IFigure.MIN_DIMENSION;
	}
	
	static private List createCategories(PaletteRoot root) {
		List categories = new ArrayList();

		categories.add(createControlGroup(root));
		categories.add(createComponentsDrawer());
		//categories.add(createComplexPartsDrawer());
		// categories.add(createTemplateComponentsDrawer());
		// categories.add(createComplexTemplatePartsDrawer());

		return categories;
	}

	/*static private PaletteContainer createComplexPartsDrawer() {
		PaletteDrawer drawer = new PaletteDrawer(
				SimulatorMessages.LogicPlugin_Category_ComplexParts_Label,
				ImageDescriptor.createFromFile(LED.class, "icons/can.gif")); //$NON-NLS-1$

		List entries = new ArrayList();

		CombinedTemplateCreationEntry combined = new CombinedTemplateCreationEntry(
				SimulatorMessages.LogicPlugin_Tool_CreationTool_HalfAdder_Label,
				SimulatorMessages.LogicPlugin_Tool_CreationTool_HalfAdder_Description,
				SimulatorDiagramFactory.getHalfAdderFactory(),
				ImageDescriptor.createFromFile(LED.class,
						"icons/halfadder16.gif"), //$NON-NLS-1$
				ImageDescriptor.createFromFile(LED.class,
						"icons/halfadder24.gif") //$NON-NLS-1$
		);
		entries.add(combined);

		combined = new CombinedTemplateCreationEntry(
				SimulatorMessages.LogicPlugin_Tool_CreationTool_FullAdder_Label,
				SimulatorMessages.LogicPlugin_Tool_CreationTool_FullAdder_Description,
				SimulatorDiagramFactory.getFullAdderFactory(),
				ImageDescriptor.createFromFile(LED.class,
						"icons/fulladder16.gif"), //$NON-NLS-1$
				ImageDescriptor.createFromFile(LED.class,
						"icons/fulladder24.gif") //$NON-NLS-1$
		);
		entries.add(combined);

		drawer.addAll(entries);
		return drawer;
	}*/

	static private PaletteContainer createComponentsDrawer() {

		PaletteDrawer drawer = new PaletteDrawer(
				SimulatorMessages.SimulatorPlugin_Category_Components_Label,
				ImageDescriptor.createFromFile(LED.class, "icons/comp.gif"));//$NON-NLS-1$

		List entries = new ArrayList();

		CombinedTemplateCreationEntry combined = new CombinedTemplateCreationEntry(
				SimulatorMessages.SimulatorPlugin_Tool_CreationTool_FlowContainer_Label,
				SimulatorMessages.SimulatorPlugin_Tool_CreationTool_FlowContainer_Description,
				new SimpleFactory(SimulatorFlowContainer.class),
				ImageDescriptor.createFromFile(LED.class,
						"icons/logicflow16.gif"), //$NON-NLS-1$
				ImageDescriptor.createFromFile(LED.class,
						"icons/logicflow24.gif")//$NON-NLS-1$
		);
		combined.setToolClass(SimulatorCreationTool.class);
		entries.add(combined);

		combined = new CombinedTemplateCreationEntry(
				SimulatorMessages.SimulatorPlugin_Tool_CreationTool_LED_Label,
				SimulatorMessages.SimulatorPlugin_Tool_CreationTool_LED_Description,
				new SimpleFactory(LED.class), ImageDescriptor
						.createFromFile(LED.class, "icons/circuit16.gif"),//$NON-NLS-1$
				ImageDescriptor.createFromFile(LED.class,
						"icons/circuit24.gif")//$NON-NLS-1$
		);
		combined.setToolClass(SimulatorCreationTool.class);
		entries.add(combined);

		entries.add(new PaletteSeparator());

		combined = new CombinedTemplateCreationEntry(
				SimulatorMessages.SimulatorPlugin_Tool_CreationTool_Label_Label,
				SimulatorMessages.SimulatorPlugin_Tool_CreationTool_Label_Description,
				new SimpleFactory(SimulatorLabel.class),
				ImageDescriptor.createFromFile(LED.class,
						"icons/label16.gif"), //$NON-NLS-1$
				ImageDescriptor.createFromFile(LED.class,
						"icons/label24.gif")//$NON-NLS-1$
		);
		combined.setToolClass(SimulatorCreationTool.class);
		entries.add(combined);		

		drawer.addAll(entries);
		return drawer;
	}

	static private PaletteContainer createControlGroup(PaletteRoot root) {
		PaletteGroup controlGroup = new PaletteGroup(
				SimulatorMessages.SimulatorPlugin_Category_ControlGroup_Label);

		List entries = new ArrayList();

		ToolEntry tool = new PanningSelectionToolEntry();
		entries.add(tool);
		root.setDefaultEntry(tool);

		PaletteStack marqueeStack = new PaletteStack(
				SimulatorMessages.Marquee_Stack, "", null); //$NON-NLS-1$

		// NODES CONTAINED (default)
		marqueeStack.add(new MarqueeToolEntry());

		// NODES TOUCHED
		MarqueeToolEntry marquee = new MarqueeToolEntry();
		marquee.setToolProperty(MarqueeSelectionTool.PROPERTY_MARQUEE_BEHAVIOR,
				new Integer(MarqueeSelectionTool.BEHAVIOR_NODES_TOUCHED));
		marqueeStack.add(marquee);

		// NODES CONTAINED AND RELATED CONNECTIONS

		marquee = new MarqueeToolEntry();
		marquee.setToolProperty(
				MarqueeSelectionTool.PROPERTY_MARQUEE_BEHAVIOR,
				new Integer(
						MarqueeSelectionTool.BEHAVIOR_NODES_CONTAINED_AND_RELATED_CONNECTIONS));
		marqueeStack.add(marquee);

		// NODES TOUCHED AND RELATED CONNECTIONS
		marquee = new MarqueeToolEntry();
		marquee.setToolProperty(
				MarqueeSelectionTool.PROPERTY_MARQUEE_BEHAVIOR,
				new Integer(
						MarqueeSelectionTool.BEHAVIOR_NODES_TOUCHED_AND_RELATED_CONNECTIONS));
		marqueeStack.add(marquee);

		// CONNECTIONS CONTAINED
		marquee = new MarqueeToolEntry();
		marquee.setToolProperty(
				MarqueeSelectionTool.PROPERTY_MARQUEE_BEHAVIOR,
				new Integer(MarqueeSelectionTool.BEHAVIOR_CONNECTIONS_CONTAINED));
		marqueeStack.add(marquee);

		// CONNECTIONS TOUCHED
		marquee = new MarqueeToolEntry();
		marquee.setToolProperty(MarqueeSelectionTool.PROPERTY_MARQUEE_BEHAVIOR,
				new Integer(MarqueeSelectionTool.BEHAVIOR_CONNECTIONS_TOUCHED));
		marqueeStack.add(marquee);

		marqueeStack
				.setUserModificationPermission(PaletteEntry.PERMISSION_NO_MODIFICATION);
		entries.add(marqueeStack);

		tool = new ConnectionCreationToolEntry(
				SimulatorMessages.SimulatorPlugin_Tool_ConnectionCreationTool_ConnectionCreationTool_Label,
				SimulatorMessages.SimulatorPlugin_Tool_ConnectionCreationTool_ConnectionCreationTool_Description,
				null, ImageDescriptor.createFromFile(LED.class,
						"icons/connection16.gif"),//$NON-NLS-1$
				ImageDescriptor.createFromFile(LED.class,
						"icons/connection24.gif")//$NON-NLS-1$
		);
		entries.add(tool);
		controlGroup.addAll(entries);
		return controlGroup;
	}

	public static PaletteRoot createPalette() {
		PaletteRoot logicPalette = new PaletteRoot();
		logicPalette.addAll(createCategories(logicPalette));
		return logicPalette;
	}
	
	/**
	 * The constructor
	 */
	public SimulatorPlugin() {
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}
	
	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static SimulatorPlugin getDefault() {
		return plugin;
	}
	
	 public static Image getImage(String path) {
		 ImageRegistry imageRegistry = getDefault().getImageRegistry();
		 Image image = imageRegistry.get(path);
		 if (image == null) {
		 	if (path.startsWith("http")) {
		 		try {
		 			ImageDescriptor imageDescriptor = ImageDescriptor.createFromURL(new URL(path));
		 			if (imageDescriptor == null) {
		 				imageDescriptor = ImageDescriptor.getMissingImageDescriptor();
		 			}
		 			image = imageDescriptor.createImage(true);
		 			imageRegistry.put(path, image);
		 		}
		 		catch (Exception localException)
		 		{
		 			ImageDescriptor imageDescriptor = ImageDescriptor.getMissingImageDescriptor();
		 			image = imageDescriptor.createImage(true);
		 			imageRegistry.put(path, image);
		 		}
		 	}
		 	else {
		 		ImageDescriptor imageDescriptor = getImageDescriptor(path);
		 		if (imageDescriptor == null) {
		 			imageDescriptor = ImageDescriptor.getMissingImageDescriptor();
		 		}
		 		image = imageDescriptor.createImage(true);
		 		imageRegistry.put(path, image);
		 	}
		 }
		 return image;
	 }
	
	 public static ImageDescriptor getImageDescriptor(String path) {
		 return imageDescriptorFromPlugin(PLUGIN_ID, "icons/" + path);
	 }
}
