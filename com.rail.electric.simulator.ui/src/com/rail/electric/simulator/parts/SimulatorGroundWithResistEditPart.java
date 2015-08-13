package com.rail.electric.simulator.parts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.AccessibleEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.swt.accessibility.AccessibleEvent;

import com.rail.electric.simulator.SimulatorMessages;
import com.rail.electric.simulator.figures.GroundWithResistFigure;
import com.rail.electric.simulator.policies.SimulatorGroundWithResistEditPolicy;

public class SimulatorGroundWithResistEditPart extends SimulatorEditPart {
	
	protected AccessibleEditPart createAccessible() {
		return new AccessibleGraphicalEditPart() {
			
			public void getName(AccessibleEvent e) {
				e.result = SimulatorMessages.SimulatorPlugin_Tool_CreationTool_LogicLabel;
			}
		};
	}

	protected void createEditPolicies() {
		super.createEditPolicies();
		
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new SimulatorGroundWithResistEditPolicy());
	}

	protected IFigure createFigure() {
		GroundWithResistFigure figure = new GroundWithResistFigure();
		return figure;		
	}
}
