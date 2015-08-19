package com.rail.electric.simulator.properties;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.rail.electric.simulator.parts.SimulatorEditPart;

public class IdPropertySectionPart implements IPropertySectionPart {
	private SimulatorEditPart editPart;
	
	public IdPropertySectionPart(SimulatorEditPart editPart) {
		this.editPart = editPart;
	}
	
	@Override
	public void createControl(Composite parent) {
        Label idLabel = new Label(parent, SWT.LEFT);
        idLabel.setText("ID");

        final Text idText = new Text(parent, SWT.SINGLE|SWT.BORDER);
        GridData gridData = new GridData();
        gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
        idText.setLayoutData(gridData);
        idText.setText(editPart.getSimulatorSubpart().getID());
        idText.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
							
			}

			@Override
			public void focusLost(FocusEvent e) {
				editPart.getSimulatorSubpart().setID(idText.getText());
			}
        	
        });
	}

}
