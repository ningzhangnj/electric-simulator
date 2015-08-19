package com.rail.electric.simulator.properties;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.part.Page;
import org.eclipse.ui.views.properties.IPropertySheetPage;

import com.rail.electric.simulator.parts.SimulatorDiagramEditPart;
import com.rail.electric.simulator.parts.SimulatorEditPart;

public class SimulatorPropertySheetPage extends Page implements
        IPropertySheetPage{
	
	private static final int DEFAULT_SECTION_WIDTH = 100;
	private Composite composite;
	private WidgetFactory widgetFactory;
    private ScrolledForm form;
	
    private List<IPropertySectionPart> sections = new ArrayList<IPropertySectionPart>();
    
        
	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		if (selection instanceof StructuredSelection ) {
			sections.clear();
			if (form != null && !form.isDisposed()) {
				for (Control control : form.getBody().getChildren()) {
			        control.dispose();
			    }
	        }
			Object editPart = ((StructuredSelection)selection).getFirstElement();
			if (editPart instanceof SimulatorEditPart) {
				sections.addAll(((SimulatorEditPart)editPart).getPropertySections());
				if (form != null && !form.isDisposed()) {
		           createSectionControls(form.getBody());
		           reflow();
		        }
			}
		}
	}

	@Override
	public void createControl(Composite parent) {
		composite = new Composite(parent, SWT.NO_FOCUS);
        GridLayout layout = new GridLayout(1, true);
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        layout.horizontalSpacing = 0;
        layout.verticalSpacing = 0;
        composite.setLayout(layout);

        this.widgetFactory = new WidgetFactory(composite.getDisplay());

        form = widgetFactory.createScrolledForm(composite);
        form.setLayoutData(new GridData(GridData.FILL_BOTH));
        form.setMinWidth(DEFAULT_SECTION_WIDTH); // TODO this not working???
        form.addDisposeListener(new DisposeListener() {
            public void widgetDisposed(DisposeEvent e) {
                if (widgetFactory != null) {
                    widgetFactory.dispose();
                    widgetFactory = null;
                }
            }
        });
        GridLayout layout1 = new GridLayout();
        layout1.numColumns = 2;
        form.getBody().setLayout(layout1);
        
        createSectionControls(form.getBody());        
        
        form.addControlListener(new ControlListener() {
            public void controlResized(ControlEvent e) {
                relayout(form, form.getBody());
            }

            public void controlMoved(ControlEvent e) {
            }
        });
        
        form.reflow(true);		
	}
	
	private void createSectionControls(final Composite formBody) {
		for (IPropertySectionPart section:sections) {
			createSectionControl(formBody, section);
		}		
	}
	
	private void createSectionControl(Composite parent, IPropertySectionPart rec) {
		rec.createControl(parent);
	}
	
	private void relayout(ScrolledForm form, Composite formBody) {
        Rectangle area = form.getClientArea();
        GridLayout layout = (GridLayout) formBody.getLayout();
        int newNumColumns = Math.max(1, area.width / DEFAULT_SECTION_WIDTH);
        boolean change = newNumColumns != layout.numColumns
                && newNumColumns >= 0
                && newNumColumns <= formBody.getChildren().length;
        if (change) {
            layout.numColumns = newNumColumns;
            formBody.layout();
        }
    }

	@Override
	public Control getControl() {
		return composite;
	}

	@Override
	public void setFocus() {
		if (form != null && !form.isDisposed())
            form.setFocus();
        else if (composite != null && !composite.isDisposed())
            composite.setFocus();
	}

	private void reflow() {
        if (form != null && !form.isDisposed()) {
            form.reflow(true);
            form.getParent().layout();
        }
    }
   

}