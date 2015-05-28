package com.rail.electric.simulator.figures;

import org.eclipse.draw2d.RectangleFigure;

public class SimulatorFlowFeedbackFigure extends RectangleFigure {

	public SimulatorFlowFeedbackFigure() {
		this.setFill(false);
		this.setXOR(true);
		setBorder(new SimulatorFlowFeedbackBorder());
	}

}
