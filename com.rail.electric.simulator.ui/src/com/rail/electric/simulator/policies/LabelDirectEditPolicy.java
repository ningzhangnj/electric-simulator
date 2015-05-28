package com.rail.electric.simulator.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;

import com.rail.electric.simulator.figures.LabelFigure;
import com.rail.electric.simulator.model.SimulatorLabel;
import com.rail.electric.simulator.model.commands.SimulatorLabelCommand;
import com.rail.electric.simulator.parts.SimulatorLabelEditPart;

public class LabelDirectEditPolicy extends DirectEditPolicy {

	/**
	 * @see DirectEditPolicy#getDirectEditCommand(DirectEditRequest)
	 */
	protected Command getDirectEditCommand(DirectEditRequest edit) {
		String labelText = (String) edit.getCellEditor().getValue();
		SimulatorLabelEditPart label = (SimulatorLabelEditPart) getHost();
		SimulatorLabelCommand command = new SimulatorLabelCommand(
				(SimulatorLabel) label.getModel(), labelText);
		return command;
	}

	/**
	 * @see DirectEditPolicy#showCurrentEditValue(DirectEditRequest)
	 */
	protected void showCurrentEditValue(DirectEditRequest request) {
		String value = (String) request.getCellEditor().getValue();
		((LabelFigure) getHostFigure()).setText(value);
		// hack to prevent async layout from placing the cell editor twice.
		getHostFigure().getUpdateManager().performUpdate();

	}

}
