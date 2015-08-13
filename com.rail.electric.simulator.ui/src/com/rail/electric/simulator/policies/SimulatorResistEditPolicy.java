package com.rail.electric.simulator.policies;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;

import com.rail.electric.simulator.model.SimulatorLabel;
import com.rail.electric.simulator.model.commands.SimulatorLabelCommand;

public class SimulatorResistEditPolicy extends SimulatorElementEditPolicy {

	public Command getCommand(Request request) {
		if (NativeDropRequest.ID.equals(request.getType()))
			return getDropTextCommand((NativeDropRequest) request);
		return super.getCommand(request);
	}

	protected Command getDropTextCommand(NativeDropRequest request) {
		SimulatorLabelCommand command = new SimulatorLabelCommand(
				(SimulatorLabel) getHost().getModel(), (String) request.getData());
		return command;
	}

	public EditPart getTargetEditPart(Request request) {
		if (NativeDropRequest.ID.equals(request.getType()))
			return getHost();
		return super.getTargetEditPart(request);
	}

}
