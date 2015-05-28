package com.rail.electric.simulator.model.commands;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.commands.Command;

import com.rail.electric.simulator.SimulatorMessages;
import com.rail.electric.simulator.model.SimulatorDiagram;
import com.rail.electric.simulator.model.SimulatorSubpart;
import com.rail.electric.simulator.model.Wire;

public class DeleteCommand extends Command {

	private SimulatorSubpart child;
	private SimulatorDiagram parent;
	//private LogicGuide vGuide, hGuide;
	private int vAlign, hAlign;
	private int index = -1;
	private List sourceConnections = new ArrayList();
	private List targetConnections = new ArrayList();

	public DeleteCommand() {
		super(SimulatorMessages.DeleteCommand_Label);
	}

	private void deleteConnections(SimulatorSubpart part) {
		if (part instanceof SimulatorDiagram) {
			List children = ((SimulatorDiagram) part).getChildren();
			for (int i = 0; i < children.size(); i++)
				deleteConnections((SimulatorSubpart) children.get(i));
		}
		sourceConnections.addAll(part.getSourceConnections());
		for (int i = 0; i < sourceConnections.size(); i++) {
			Wire wire = (Wire) sourceConnections.get(i);
			wire.detachSource();
			wire.detachTarget();
		}
		targetConnections.addAll(part.getTargetConnections());
		for (int i = 0; i < targetConnections.size(); i++) {
			Wire wire = (Wire) targetConnections.get(i);
			wire.detachSource();
			wire.detachTarget();
		}
	}

	/*private void detachFromGuides(LogicSubpart part) {
		if (part.getVerticalGuide() != null) {
			vGuide = part.getVerticalGuide();
			vAlign = vGuide.getAlignment(part);
			vGuide.detachPart(part);
		}
		if (part.getHorizontalGuide() != null) {
			hGuide = part.getHorizontalGuide();
			hAlign = hGuide.getAlignment(part);
			hGuide.detachPart(part);
		}

	}*/

	public void execute() {
		primExecute();
	}

	protected void primExecute() {
		deleteConnections(child);
		//detachFromGuides(child);
		index = parent.getChildren().indexOf(child);
		parent.removeChild(child);
	}

	/*private void reattachToGuides(LogicSubpart part) {
		if (vGuide != null)
			vGuide.attachPart(part, vAlign);
		if (hGuide != null)
			hGuide.attachPart(part, hAlign);
	}*/

	public void redo() {
		primExecute();
	}

	private void restoreConnections() {
		for (int i = 0; i < sourceConnections.size(); i++) {
			Wire wire = (Wire) sourceConnections.get(i);
			wire.attachSource();
			wire.attachTarget();
		}
		sourceConnections.clear();
		for (int i = 0; i < targetConnections.size(); i++) {
			Wire wire = (Wire) targetConnections.get(i);
			wire.attachSource();
			wire.attachTarget();
		}
		targetConnections.clear();
	}

	public void setChild(SimulatorSubpart c) {
		child = c;
	}

	public void setParent(SimulatorDiagram p) {
		parent = p;
	}

	public void undo() {
		parent.addChild(child, index);
		restoreConnections();
		//reattachToGuides(child);
	}

}
