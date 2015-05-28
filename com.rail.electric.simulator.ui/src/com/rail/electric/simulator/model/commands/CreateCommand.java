package com.rail.electric.simulator.model.commands;

import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;

import com.rail.electric.simulator.SimulatorMessages;
import com.rail.electric.simulator.model.LED;
import com.rail.electric.simulator.model.SimulatorDiagram;
import com.rail.electric.simulator.model.SimulatorSubpart;

public class CreateCommand extends org.eclipse.gef.commands.Command {

	private SimulatorSubpart child;
	private Rectangle rect;
	private SimulatorDiagram parent;
	private int index = -1;

	public CreateCommand() {
		super(SimulatorMessages.CreateCommand_Label);
	}

	public boolean canExecute() {
		return child != null && parent != null;
	}

	public void execute() {
		if (rect != null) {
			Insets expansion = getInsets();
			if (!rect.isEmpty())
				rect.expand(expansion);
			else {
				rect.x -= expansion.left;
				rect.y -= expansion.top;
			}
			child.setLocation(rect.getLocation());
			if (!rect.isEmpty())
				child.setSize(rect.getSize());
		}
		redo();
	}

	private Insets getInsets() {
		if (child instanceof LED )
			return new Insets(2, 0, 2, 0);
		return new Insets();
	}

	public void redo() {
		parent.addChild(child, index);
	}

	public void setChild(SimulatorSubpart subpart) {
		child = subpart;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void setLocation(Rectangle r) {
		rect = r;
	}

	public void setParent(SimulatorDiagram newParent) {
		parent = newParent;
	}

	public void undo() {
		parent.removeChild(child);
	}

}
