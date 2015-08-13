package com.rail.electric.simulator.parts;

import java.beans.PropertyChangeEvent;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.AccessibleEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.swt.accessibility.AccessibleEvent;

import com.rail.electric.simulator.SimulatorMessages;
import com.rail.electric.simulator.figures.GroundFigure;
import com.rail.electric.simulator.policies.SimulatorGroundEditPolicy;

public class SimulatorGroundEditPart extends SimulatorEditPart {
	
	protected AccessibleEditPart createAccessible() {
		return new AccessibleGraphicalEditPart() {
			
			public void getName(AccessibleEvent e) {
				e.result = SimulatorMessages.SimulatorPlugin_Tool_CreationTool_LogicLabel;
			}
		};
	}

	protected void createEditPolicies() {
		super.createEditPolicies();
		
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new SimulatorGroundEditPolicy());
	}

	protected IFigure createFigure() {
		GroundFigure resist = new GroundFigure();
		return resist;		
	}

	public void performRequest(Request request) {
		if (RequestConstants.REQ_OPEN.equals(request.getType()))
			((GroundFigure)getFigure()).switchImage();
	}

	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equalsIgnoreCase("changeStatus"))//$NON-NLS-1$
			((GroundFigure)getFigure()).switchImage();
		else
			super.propertyChange(evt);
	}
	

}
