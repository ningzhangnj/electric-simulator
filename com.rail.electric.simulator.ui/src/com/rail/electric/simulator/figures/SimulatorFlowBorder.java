package com.rail.electric.simulator.figures;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;

public class SimulatorFlowBorder extends org.eclipse.draw2d.LineBorder {

	protected int grabBarWidth = 20;
	protected Dimension grabBarSize = new Dimension(grabBarWidth, 18);

	public SimulatorFlowBorder() {
	}

	public SimulatorFlowBorder(int width) {
		setGrabBarWidth(width);
		grabBarSize = new Dimension(width, 18);
	}

	public Insets getInsets(IFigure figure) {
		return new Insets(getWidth() + 2, grabBarWidth + 2, getWidth() + 2,
				getWidth() + 2);
	}

	public Dimension getPreferredSize() {
		return grabBarSize;
	}

	public void paint(IFigure figure, Graphics graphics, Insets insets) {
		Rectangle bounds = figure.getBounds();
		tempRect.setBounds(new Rectangle(bounds.x, bounds.y, grabBarWidth,
				bounds.height));
		graphics.setBackgroundColor(SimulatorColorConstants.logicGreen);
		graphics.fillRectangle(tempRect);
		super.paint(figure, graphics, insets);
	}

	public void setGrabBarWidth(int width) {
		grabBarWidth = width;
	}

}
