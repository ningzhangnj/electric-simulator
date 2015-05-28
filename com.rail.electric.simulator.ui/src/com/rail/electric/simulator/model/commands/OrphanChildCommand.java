package com.rail.electric.simulator.model.commands;

import java.util.List;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;

import com.rail.electric.simulator.SimulatorMessages;
import com.rail.electric.simulator.model.SimulatorDiagram;
import com.rail.electric.simulator.model.SimulatorSubpart;

public class OrphanChildCommand extends Command {

	private Point oldLocation;
	private SimulatorDiagram diagram;
	private SimulatorSubpart child;
	private int index;

	public OrphanChildCommand() {
		super(SimulatorMessages.OrphanChildCommand_Label);
	}

	public void execute() {
		List children = diagram.getChildren();
		index = children.indexOf(child);
		oldLocation = child.getLocation();
		diagram.removeChild(child);
	}

	public void redo() {
		diagram.removeChild(child);
	}

	public void setChild(SimulatorSubpart child) {
		this.child = child;
	}

	public void setParent(SimulatorDiagram parent) {
		diagram = parent;
	}

	public void undo() {
		child.setLocation(oldLocation);
		diagram.addChild(child, index);
	}

}
