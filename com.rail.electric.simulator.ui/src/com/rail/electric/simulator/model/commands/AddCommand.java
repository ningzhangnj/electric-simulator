package com.rail.electric.simulator.model.commands;

import com.rail.electric.simulator.SimulatorMessages;
import com.rail.electric.simulator.model.SimulatorDiagram;
import com.rail.electric.simulator.model.SimulatorSubpart;

public class AddCommand extends org.eclipse.gef.commands.Command {

	private SimulatorSubpart child;
	private SimulatorDiagram parent;
	private int index = -1;

	public AddCommand() {
		super(SimulatorMessages.AddCommand_Label);
	}

	public void execute() {
		if (index < 0)
			parent.addChild(child);
		else
			parent.addChild(child, index);
	}

	public SimulatorDiagram getParent() {
		return parent;
	}

	public void redo() {
		if (index < 0)
			parent.addChild(child);
		else
			parent.addChild(child, index);
	}

	public void setChild(SimulatorSubpart subpart) {
		child = subpart;
	}

	public void setIndex(int i) {
		index = i;
	}

	public void setParent(SimulatorDiagram newParent) {
		parent = newParent;
	}

	public void undo() {
		parent.removeChild(child);
	}

}
