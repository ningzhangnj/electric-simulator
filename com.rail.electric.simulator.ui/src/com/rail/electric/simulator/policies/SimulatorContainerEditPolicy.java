package com.rail.electric.simulator.policies;

import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editpolicies.ContainerEditPolicy;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.requests.GroupRequest;

import com.rail.electric.simulator.SimulatorMessages;
import com.rail.electric.simulator.model.SimulatorDiagram;
import com.rail.electric.simulator.model.SimulatorSubpart;
import com.rail.electric.simulator.model.commands.OrphanChildCommand;

public class SimulatorContainerEditPolicy extends ContainerEditPolicy {

	protected Command getCreateCommand(CreateRequest request) {
		return null;
	}

	public Command getOrphanChildrenCommand(GroupRequest request) {
		List parts = request.getEditParts();
		CompoundCommand result = new CompoundCommand(
				SimulatorMessages.SimulatorContainerEditPolicy_OrphanCommandLabelText);
		for (int i = 0; i < parts.size(); i++) {
			OrphanChildCommand orphan = new OrphanChildCommand();
			orphan.setChild((SimulatorSubpart) ((EditPart) parts.get(i)).getModel());
			orphan.setParent((SimulatorDiagram) getHost().getModel());
			orphan.setLabel(SimulatorMessages.SimulatorElementEditPolicy_OrphanCommandLabelText);
			result.add(orphan);
		}
		return result.unwrap();
	}

}
