package com.rail.electric.simulator.parts;

import java.beans.PropertyChangeEvent;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.AccessibleEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.swt.accessibility.AccessibleControlEvent;
import org.eclipse.swt.accessibility.AccessibleEvent;

import com.rail.electric.simulator.SimulatorMessages;
import com.rail.electric.simulator.figures.LabelFigure;
import com.rail.electric.simulator.model.SimulatorLabel;
import com.rail.electric.simulator.policies.LabelCellEditorLocator;
import com.rail.electric.simulator.policies.LabelDirectEditPolicy;
import com.rail.electric.simulator.policies.SimulatorLabelEditPolicy;

public class SimulatorLabelEditPart extends SimulatorEditPart {

	protected AccessibleEditPart createAccessible() {
		return new AccessibleGraphicalEditPart() {
			public void getValue(AccessibleControlEvent e) {
				e.result = getSimulatorLabel().getLabelContents();
			}

			public void getName(AccessibleEvent e) {
				e.result = SimulatorMessages.SimulatorPlugin_Tool_CreationTool_LogicLabel;
			}
		};
	}

	protected void createEditPolicies() {
		super.createEditPolicies();
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, null);
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE,
				new LabelDirectEditPolicy());
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new SimulatorLabelEditPolicy());
	}

	protected IFigure createFigure() {
		LabelFigure label = new LabelFigure();
		return label;
	}

	private SimulatorLabel getSimulatorLabel() {
		return (SimulatorLabel) getModel();
	}

	private void performDirectEdit() {
		new SimulatorLabelEditManager(this, new LabelCellEditorLocator(
				(LabelFigure) getFigure())).show();
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

	protected void refreshVisuals() {
		((LabelFigure) getFigure()).setText(getSimulatorLabel()
				.getLabelContents());
		super.refreshVisuals();
	}

}
