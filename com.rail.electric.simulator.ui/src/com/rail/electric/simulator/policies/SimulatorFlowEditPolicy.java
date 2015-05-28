package com.rail.electric.simulator.policies;

import java.util.Iterator;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;

import com.rail.electric.simulator.model.SimulatorDiagram;
import com.rail.electric.simulator.model.SimulatorFlowContainer;
import com.rail.electric.simulator.model.SimulatorSubpart;
import com.rail.electric.simulator.model.commands.AddCommand;
import com.rail.electric.simulator.model.commands.CloneCommand;
import com.rail.electric.simulator.model.commands.CreateCommand;
import com.rail.electric.simulator.model.commands.ReorderPartCommand;

public class SimulatorFlowEditPolicy extends
		org.eclipse.gef.editpolicies.FlowLayoutEditPolicy {

	/**
	 * Override to return the <code>Command</code> to perform an
	 * {@link RequestConstants#REQ_CLONE CLONE}. By default, <code>null</code>
	 * is returned.
	 * 
	 * @param request
	 *            the Clone Request
	 * @return A command to perform the Clone.
	 */
	protected Command getCloneCommand(ChangeBoundsRequest request) {
		CloneCommand clone = new CloneCommand();
		clone.setParent((SimulatorDiagram) getHost().getModel());

		EditPart after = getInsertionReference(request);
		int index = getHost().getChildren().indexOf(after);

		Iterator i = request.getEditParts().iterator();
		GraphicalEditPart currPart = null;

		while (i.hasNext()) {
			currPart = (GraphicalEditPart) i.next();
			clone.addPart((SimulatorSubpart) currPart.getModel(), index++);
		}

		return clone;
	}

	protected Command createAddCommand(EditPart child, EditPart after) {
		AddCommand command = new AddCommand();
		command.setChild((SimulatorSubpart) child.getModel());
		command.setParent((SimulatorFlowContainer) getHost().getModel());
		int index = getHost().getChildren().indexOf(after);
		command.setIndex(index);
		return command;
	}

	/**
	 * @see org.eclipse.gef.editpolicies.LayoutEditPolicy#createChildEditPolicy(org.eclipse.gef.EditPart)
	 */
	protected EditPolicy createChildEditPolicy(EditPart child) {
		SimulatorResizableEditPolicy policy = new SimulatorResizableEditPolicy();
		policy.setResizeDirections(0);
		return policy;
	}

	protected Command createMoveChildCommand(EditPart child, EditPart after) {
		SimulatorSubpart childModel = (SimulatorSubpart) child.getModel();
		SimulatorDiagram parentModel = (SimulatorDiagram) getHost().getModel();
		int oldIndex = getHost().getChildren().indexOf(child);
		int newIndex = getHost().getChildren().indexOf(after);
		if (newIndex > oldIndex)
			newIndex--;
		ReorderPartCommand command = new ReorderPartCommand(childModel,
				parentModel, newIndex);
		return command;
	}

	protected Command getCreateCommand(CreateRequest request) {
		CreateCommand command = new CreateCommand();
		EditPart after = getInsertionReference(request);
		command.setChild((SimulatorSubpart) request.getNewObject());
		command.setParent((SimulatorFlowContainer) getHost().getModel());
		int index = getHost().getChildren().indexOf(after);
		command.setIndex(index);
		return command;
	}

}
