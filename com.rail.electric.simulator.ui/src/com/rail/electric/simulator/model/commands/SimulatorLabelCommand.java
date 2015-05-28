package com.rail.electric.simulator.model.commands;

import org.eclipse.gef.commands.Command;

import com.rail.electric.simulator.model.SimulatorLabel;

public class SimulatorLabelCommand extends Command {

	private String newName, oldName;
	private SimulatorLabel label;

	public SimulatorLabelCommand(SimulatorLabel l, String s) {
		label = l;
		if (s != null)
			newName = s;
		else
			newName = ""; //$NON-NLS-1$
	}

	public void execute() {
		oldName = label.getLabelContents();
		label.setLabelContents(newName);
	}

	public void undo() {
		label.setLabelContents(oldName);
	}

}
