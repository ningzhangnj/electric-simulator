package com.rail.electric.simulator.model.commands;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.gef.commands.Command;

import com.rail.electric.simulator.SimulatorMessages;
import com.rail.electric.simulator.model.SimulatorGuide;
import com.rail.electric.simulator.model.SimulatorRuler;
import com.rail.electric.simulator.model.SimulatorSubpart;

public class DeleteGuideCommand extends Command {

	private SimulatorRuler parent;
	private SimulatorGuide guide;
	private Map oldParts;

	public DeleteGuideCommand(SimulatorGuide guide, SimulatorRuler parent) {
		super(SimulatorMessages.DeleteGuideCommand_Label);
		this.guide = guide;
		this.parent = parent;
	}

	public boolean canUndo() {
		return true;
	}

	public void execute() {
		oldParts = new HashMap(guide.getMap());
		Iterator iter = oldParts.keySet().iterator();
		while (iter.hasNext()) {
			guide.detachPart((SimulatorSubpart) iter.next());
		}
		parent.removeGuide(guide);
	}

	public void undo() {
		parent.addGuide(guide);
		Iterator iter = oldParts.keySet().iterator();
		while (iter.hasNext()) {
			SimulatorSubpart part = (SimulatorSubpart) iter.next();
			guide.attachPart(part, ((Integer) oldParts.get(part)).intValue());
		}
	}
}
