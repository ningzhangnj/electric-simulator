package com.rail.electric.simulator.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;

public class SimulatorFlowFeedbackBorder extends SimulatorFlowBorder {

	public SimulatorFlowFeedbackBorder() {
	}

	public SimulatorFlowFeedbackBorder(int width) {
		super(width);
	}

	public void paint(IFigure figure, Graphics graphics, Insets insets) {
		graphics.setForegroundColor(ColorConstants.white);
		graphics.setBackgroundColor(SimulatorColorConstants.ghostFillColor);
		graphics.setXORMode(true);

		Rectangle r = figure.getBounds();

		graphics.drawRectangle(r.x, r.y, r.width - 1, r.height - 1);
		// graphics.drawLine(r.x, r.y + 1, r.right() - 1, r.y + 1);
		// graphics.drawLine(r.x, r.bottom() - 1, r.right() - 1, r.bottom() -
		// 1);
		// graphics.drawLine(r.x, r.y + 1, r.x, r.bottom() - 1);
		// graphics.drawLine(r.right() - 1, r.bottom() - 1, r.right() - 1, r.y +
		// 1);

		tempRect.setBounds(new Rectangle(r.x, r.y, grabBarWidth, r.height));

		graphics.fillRectangle(tempRect);
	}
}
