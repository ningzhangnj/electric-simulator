package com.rail.electric.simulator.parts;

import java.util.List;

import org.eclipse.gef.AccessibleEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.swt.accessibility.AccessibleEvent;

import com.rail.electric.simulator.model.SimulatorDiagram;
import com.rail.electric.simulator.policies.SimulatorContainerEditPolicy;

/**
 * Provides support for Container EditParts.
 */
abstract public class SimulatorContainerEditPart extends SimulatorEditPart {
	protected AccessibleEditPart createAccessible() {
		return new AccessibleGraphicalEditPart() {
			public void getName(AccessibleEvent e) {
				e.result = getSimulatorDiagram().toString();
			}
		};
	}

	/**
	 * Installs the desired EditPolicies for this.
	 */
	protected void createEditPolicies() {
		super.createEditPolicies();
		installEditPolicy(EditPolicy.CONTAINER_ROLE,
				new SimulatorContainerEditPolicy());
	}

	/**
	 * Returns the model of this as a LogicDiagram.
	 * 
	 * @return LogicDiagram of this.
	 */
	protected SimulatorDiagram getSimulatorDiagram() {
		return (SimulatorDiagram) getModel();
	}

	/**
	 * Returns the children of this through the model.
	 * 
	 * @return Children of this as a List.
	 */
	protected List getModelChildren() {
		return getSimulatorDiagram().getChildren();
	}

}
