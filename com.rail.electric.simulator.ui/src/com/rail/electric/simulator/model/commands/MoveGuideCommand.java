package com.rail.electric.simulator.model.commands;

import java.util.Iterator;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;

import com.rail.electric.simulator.SimulatorMessages;
import com.rail.electric.simulator.model.SimulatorGuide;
import com.rail.electric.simulator.model.SimulatorSubpart;

/**
 * @author Pratik Shah
 */
public class MoveGuideCommand extends Command {

	private int pDelta;
	private SimulatorGuide guide;

	public MoveGuideCommand(SimulatorGuide guide, int positionDelta) {
		super(SimulatorMessages.MoveGuideCommand_Label);
		this.guide = guide;
		pDelta = positionDelta;
	}

	public void execute() {
		guide.setPosition(guide.getPosition() + pDelta);
		Iterator iter = guide.getParts().iterator();
		while (iter.hasNext()) {
			SimulatorSubpart part = (SimulatorSubpart) iter.next();
			Point location = part.getLocation().getCopy();
			if (guide.isHorizontal()) {
				location.y += pDelta;
			} else {
				location.x += pDelta;
			}
			part.setLocation(location);
		}
	}

	public void undo() {
		guide.setPosition(guide.getPosition() - pDelta);
		Iterator iter = guide.getParts().iterator();
		while (iter.hasNext()) {
			SimulatorSubpart part = (SimulatorSubpart) iter.next();
			Point location = part.getLocation().getCopy();
			if (guide.isHorizontal()) {
				location.y -= pDelta;
			} else {
				location.x -= pDelta;
			}
			part.setLocation(location);
		}
	}

}
