package com.rail.electric.simulator.parts;

import java.beans.PropertyChangeEvent;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.gef.AccessibleEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.swt.accessibility.AccessibleEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.rail.electric.simulator.SimulatorMessages;
import com.rail.electric.simulator.figures.ResistFigure;
import com.rail.electric.simulator.model.SimulatorResist;
import com.rail.electric.simulator.policies.SimulatorResistEditPolicy;

public class SimulatorResistEditPart extends SimulatorEditPart {

	public static final Image RESIST = 
			new Image(Display.getCurrent(),
					SimulatorResist.class.getResourceAsStream("icons/resist24.gif"));
	
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
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new SimulatorResistEditPolicy());
	}

	protected IFigure createFigure() {
		ResistFigure resist = new ResistFigure();
		return resist;
		/*Label l = new Label();
		l.setLabelAlignment(PositionConstants.LEFT);
		l.setIcon(RESIST);
		return l;*/
	}

	private SimulatorResist getSimulatorResist() {
		return (SimulatorResist) getModel();
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
