package com.rail.electric.simulator.figures;

import org.eclipse.draw2d.ImageFigure;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.rail.electric.simulator.model.SimulatorGround;


public class ThreePhaseTransformerFigure extends NodeFigure {
	public static final Image THREE_PHASE_TRANSFORMER_ON = 
			new Image(Display.getCurrent(),
					SimulatorGround.class.getResourceAsStream("icons/trip_trans_on_40X72.png"));	
	
	public static final Image THREE_PHASE_TRANSFORMER_OFF = 
			new Image(Display.getCurrent(),
					SimulatorGround.class.getResourceAsStream("icons/trip_trans_off_40X72.png"));
	
	public ThreePhaseTransformerFigure() {
		final ToolbarLayout layout = new ToolbarLayout();
		setLayoutManager(layout);
				
		ImageFigure imageFigure = new ImageFigure(THREE_PHASE_TRANSFORMER_ON);
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
