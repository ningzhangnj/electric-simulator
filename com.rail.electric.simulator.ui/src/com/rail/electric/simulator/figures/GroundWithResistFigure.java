package com.rail.electric.simulator.figures;

import org.eclipse.draw2d.ImageFigure;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.rail.electric.simulator.model.SimulatorGround;


public class GroundWithResistFigure extends NodeFigure {
	public static final Image GROUND_WITH_RESIST = 
			new Image(Display.getCurrent(),
					SimulatorGround.class.getResourceAsStream("icons/ground_resist_32X64.png"));	
	
	public GroundWithResistFigure() {
		final ToolbarLayout layout = new ToolbarLayout();
		setLayoutManager(layout);
				
		ImageFigure imageFigure = new ImageFigure(GROUND_WITH_RESIST);
		imageFigure.setPreferredSize(25, 25);
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
}
