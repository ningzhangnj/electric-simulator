package com.rail.electric.simulator.figures;

import org.eclipse.draw2d.ImageFigure;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.rail.electric.simulator.model.SimulatorTwoPhaseTransformer;


public class MainSwitchFigure extends NodeFigure {
	public static final Image MAIN_SWITCH = 
			new Image(Display.getCurrent(),
					SimulatorTwoPhaseTransformer.class.getResourceAsStream("icons/main_switch_off_48X48.png"));	
	
	public MainSwitchFigure() {
		final ToolbarLayout layout = new ToolbarLayout();
		setLayoutManager(layout);
				
		ImageFigure imageFigure = new ImageFigure(MAIN_SWITCH);
		imageFigure.setPreferredSize(25, 25);
		add(imageFigure);
				
		FixedConnectionAnchor connectionAnchor = new FixedConnectionAnchor(this);
		connectionAnchor.offsetH = 24;
		inputConnectionAnchors.addElement(connectionAnchor);
		connectionAnchors.put(SimulatorTwoPhaseTransformer.TERMINAL_IN_1, connectionAnchor );
		outputConnectionAnchors.addElement(connectionAnchor);
		connectionAnchors.put(SimulatorTwoPhaseTransformer.TERMINAL_OUT_1, connectionAnchor );
		
		connectionAnchor = new FixedConnectionAnchor(this);
		connectionAnchor.offsetH = 24;
		connectionAnchor.topDown = false;
		inputConnectionAnchors.addElement(connectionAnchor);
		connectionAnchors.put(SimulatorTwoPhaseTransformer.TERMINAL_IN_2, connectionAnchor );
		outputConnectionAnchors.addElement(connectionAnchor);
		connectionAnchors.put(SimulatorTwoPhaseTransformer.TERMINAL_OUT_2, connectionAnchor );
		
		connectionAnchor = new FixedConnectionAnchor(this);
		connectionAnchor.offsetV = 16;
		connectionAnchor.leftToRight = false;
		inputConnectionAnchors.addElement(connectionAnchor);
		connectionAnchors.put(SimulatorTwoPhaseTransformer.TERMINAL_IN_3, connectionAnchor );
		outputConnectionAnchors.addElement(connectionAnchor);
		connectionAnchors.put(SimulatorTwoPhaseTransformer.TERMINAL_OUT_3, connectionAnchor );		
	}
	
	
}
