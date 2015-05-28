package com.rail.electric.simulator.model.commands;

import org.eclipse.gef.commands.Command;

import com.rail.electric.simulator.SimulatorMessages;
import com.rail.electric.simulator.model.SimulatorDiagram;
import com.rail.electric.simulator.model.SimulatorSubpart;

public class ReorderPartCommand extends Command {

	private int oldIndex, newIndex;
	private SimulatorSubpart child;
	private SimulatorDiagram parent;

	public ReorderPartCommand(SimulatorSubpart child, SimulatorDiagram parent,
			int newIndex) {
		super(SimulatorMessages.ReorderPartCommand_Label);
		this.child = child;
		this.parent = parent;
		this.newIndex = newIndex;
	}

	public void execute() {
		oldIndex = parent.getChildren().indexOf(child);
		parent.removeChild(child);
		parent.addChild(child, newIndex);
	}

	public void undo() {
		parent.removeChild(child);
		parent.addChild(child, oldIndex);
	}

}
