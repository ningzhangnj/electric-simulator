package com.rail.electric.simulator.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.GroupRequest;

import com.rail.electric.simulator.model.Wire;
import com.rail.electric.simulator.model.commands.ConnectionCommand;

public class WireEditPolicy extends
		org.eclipse.gef.editpolicies.ConnectionEditPolicy {

	protected Command getDeleteCommand(GroupRequest request) {
		ConnectionCommand c = new ConnectionCommand();
		c.setWire((Wire) getHost().getModel());
		return c;
	}

}
