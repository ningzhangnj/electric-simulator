package com.rail.electric.simulator.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.GroupRequest;

import com.rail.electric.simulator.model.SimulatorDiagram;
import com.rail.electric.simulator.model.SimulatorSubpart;
import com.rail.electric.simulator.model.commands.DeleteCommand;

public class SimulatorElementEditPolicy extends
		org.eclipse.gef.editpolicies.ComponentEditPolicy {

	protected Command createDeleteCommand(GroupRequest request) {
		Object parent = getHost().getParent().getModel();
		DeleteCommand deleteCmd = new DeleteCommand();
		deleteCmd.setParent((SimulatorDiagram) parent);
		deleteCmd.setChild((SimulatorSubpart) getHost().getModel());
		return deleteCmd;
	}

}
