package com.rail.electric.simulator.figures;

import org.eclipse.draw2d.ImageFigure;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.rail.electric.simulator.model.SimulatorGround;


public class GroundFigure extends NodeFigure {
	public static final Image SMALL_GROUND_ON = new Image(Display.getCurrent(),			
			GroundFigure.class.getResourceAsStream("icons/ground_on_18X24.png"));
	public static final Image LARGE_GROUND_ON = new Image(Display.getCurrent(),			
			GroundFigure.class.getResourceAsStream("icons/ground_on_48X64.png"));	
	public static final Image SMALL_GROUND_OFF= new Image(Display.getCurrent(),			
			GroundFigure.class.getResourceAsStream("icons/ground_on_18X24.png"));
	public static final Image LARGE_GROUND_OFF = new Image(Display.getCurrent(),			
			GroundFigure.class.getResourceAsStream("icons/ground_off_48X64.png"));
	
	private boolean isOff = false;
	
	public GroundFigure() {
		final ToolbarLayout layout = new ToolbarLayout();
		setLayoutManager(layout);
				
		ImageFigure imageFigure = new ImageFigure(LARGE_GROUND_ON);
		add(imageFigure);
				
		FixedConnectionAnchor inputConnectionAnchor = new FixedConnectionAnchor(this);
		inputConnectionAnchor.offsetH = 24;
		inputConnectionAnchors.addElement(inputConnectionAnchor);
		connectionAnchors.put(SimulatorGround.TERMINAL_IN, inputConnectionAnchor );
		
		FixedConnectionAnchor outputConnectionAnchor = new FixedConnectionAnchor(
				this);
		outputConnectionAnchor.offsetH = 24;
		outputConnectionAnchors.addElement(outputConnectionAnchor);
		connectionAnchors.put(SimulatorGround.TERMINAL_OUT, outputConnectionAnchor );			
	}
	
	public void switchImage() {
		isOff = !isOff;
		ImageFigure imageFigure = null;
		if (isOff) {
			imageFigure = new ImageFigure(LARGE_GROUND_OFF);
		} else {
			imageFigure = new ImageFigure(LARGE_GROUND_ON);
		}
		imageFigure.setPreferredSize(25, 25);
		this.removeAll();
		this.add(imageFigure);
		this.repaint();
	}
}
