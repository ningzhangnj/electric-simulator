package com.rail.electric.simulator.parts;

import java.beans.PropertyChangeEvent;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.AccessibleEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.swt.accessibility.AccessibleEvent;

import com.rail.electric.simulator.SimulatorMessages;
import com.rail.electric.simulator.figures.ComplexCircuitFigure;
import com.rail.electric.simulator.policies.SimulatorComplexCircuitEditPolicy;

public class SimulatorComplexCircuitEditPart extends SimulatorEditPart {
	
	protected AccessibleEditPart createAccessible() {
		return new AccessibleGraphicalEditPart() {
			
			public void getName(AccessibleEvent e) {
				e.result = SimulatorMessages.SimulatorPlugin_Tool_CreationTool_LogicLabel;
			}
		};
	}

	protected void createEditPolicies() {
		super.createEditPolicies();
		//installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, null);
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new SimulatorComplexCircuitEditPolicy());
	}

	protected IFigure createFigure() {
		ComplexCircuitFigure figure = new ComplexCircuitFigure();
		return figure;
	}


	private void performDirectEdit() {
		
	}

	public void performRequest(Request request) {
		if (request.getType() == RequestConstants.REQ_DIRECT_EDIT)
			performDirectEdit();
	}

	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equalsIgnoreCase("labelContents"))//$NON-NLS-1$
			refreshVisuals();
		else
			super.propertyChange(evt);
	}
	

}
