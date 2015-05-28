package com.rail.electric.simulator.tools;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.tools.CreationTool;

import com.rail.electric.simulator.SimulatorPlugin;

/**
 * Creation tool that ensures size constraints for size-on-drop
 * 
 * 
 */
public class SimulatorCreationTool extends CreationTool {

	protected Dimension getMaximumSizeFor(CreateRequest request) {
		return SimulatorPlugin.getMaximumSizeFor(request.getNewObject().getClass());
	}

	protected Dimension getMinimumSizeFor(CreateRequest request) {
		return SimulatorPlugin.getMinimumSizeFor(request.getNewObject().getClass());
	}
}