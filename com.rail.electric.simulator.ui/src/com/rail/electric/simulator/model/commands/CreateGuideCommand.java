package com.rail.electric.simulator.model.commands;

import org.eclipse.gef.commands.Command;

import com.rail.electric.simulator.SimulatorMessages;
import com.rail.electric.simulator.model.SimulatorGuide;
import com.rail.electric.simulator.model.SimulatorRuler;

public class CreateGuideCommand extends Command {

	private SimulatorGuide guide;
	private SimulatorRuler parent;
	private int position;

	public CreateGuideCommand(SimulatorRuler parent, int position) {
		super(SimulatorMessages.CreateGuideCommand_Label);
		this.parent = parent;
		this.position = position;
	}

	public boolean canUndo() {
		return true;
	}

	public void execute() {
		if (guide == null)
			guide = new SimulatorGuide(!parent.isHorizontal());
		guide.setPosition(position);
		parent.addGuide(guide);
	}

	public void undo() {
		parent.removeGuide(guide);
	}

}
