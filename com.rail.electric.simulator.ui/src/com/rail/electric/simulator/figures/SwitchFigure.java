package com.rail.electric.simulator.figures;

import org.eclipse.draw2d.ImageFigure;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.rail.electric.simulator.model.SimulatorTwoPhaseTransformer;


public class SwitchFigure extends NodeFigure {
	public static final Image SMALL_SWITCH_OFF = new Image(Display.getCurrent(),
			SwitchFigure.class.getResourceAsStream("icons/switch_off_24X24.png"));
	public static final Image LARGE_SWITCH_OFF = new Image(Display.getCurrent(),
			SwitchFigure.class.getResourceAsStream("icons/switch_off_48X48.png"));	
	public static final Image LARGE_SWITCH_ON = new Image(Display.getCurrent(),
			SwitchFigure.class.getResourceAsStream("icons/switch_on_48X48.png"));
	
	public SwitchFigure() {
		final ToolbarLayout layout = new ToolbarLayout();
		setLayoutManager(layout);
				
		ImageFigure imageFigure = new ImageFigure(LARGE_SWITCH_OFF);
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
