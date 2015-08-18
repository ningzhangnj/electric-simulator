package com.rail.electric.simulator.figures;

import org.eclipse.draw2d.ImageFigure;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.rail.electric.simulator.model.SimulatorComplexCircuit;


public class ComplexCircuitFigure extends NodeFigure {
	public static final Image SMALL_COMPLEX_CIRCUIT_ON = new Image(Display.getCurrent(),			
			ComplexCircuitFigure.class.getResourceAsStream("icons/comp_on_24X24.png"));	
	public static final Image LARGE_COMPLEX_CIRCUIT_ON = new Image(Display.getCurrent(),			
			ComplexCircuitFigure.class.getResourceAsStream("icons/comp_on_64X64.png"));
	public static final Image SMALL_COMPLEX_CIRCUIT_OFF = new Image(Display.getCurrent(),			
			ComplexCircuitFigure.class.getResourceAsStream("icons/comp_off_24X24.png"));	
	public static final Image LARGE_COMPLEX_CIRCUIT_OFF = new Image(Display.getCurrent(),			
			ComplexCircuitFigure.class.getResourceAsStream("icons/comp_off_64X64.png"));
	
	public ComplexCircuitFigure() {
		final ToolbarLayout layout = new ToolbarLayout();
		setLayoutManager(layout);
				
		ImageFigure imageFigure = new ImageFigure(LARGE_COMPLEX_CIRCUIT_ON);
		add(imageFigure);
				
		FixedConnectionAnchor inputConnectionAnchor = new FixedConnectionAnchor(this);
		inputConnectionAnchor.offsetV = 3;
		inputConnectionAnchors.addElement(inputConnectionAnchor);
		connectionAnchors.put(SimulatorComplexCircuit.TERMINAL_IN, inputConnectionAnchor );
		
		FixedConnectionAnchor outputConnectionAnchor = new FixedConnectionAnchor(
				this);
		outputConnectionAnchor.offsetV = 3;
		outputConnectionAnchors.addElement(outputConnectionAnchor);
		connectionAnchors.put(SimulatorComplexCircuit.TERMINAL_OUT, outputConnectionAnchor );	
		
	}
}
