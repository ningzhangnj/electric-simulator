package com.rail.electric.simulator.tools;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.tools.ResizeTracker;

import com.rail.electric.simulator.SimulatorPlugin;

public final class SimulatorResizeTracker extends ResizeTracker {

	public SimulatorResizeTracker(GraphicalEditPart owner, int direction) {
		super(owner, direction);
	}

	protected Dimension getMaximumSizeFor(ChangeBoundsRequest request) {
		return SimulatorPlugin.getMaximumSizeFor(getOwner().getModel().getClass());
	}

	protected Dimension getMinimumSizeFor(ChangeBoundsRequest request) {
		return SimulatorPlugin.getMinimumSizeFor(getOwner().getModel().getClass());
	}
}
