package com.rail.electric.simulator.figures;

import org.eclipse.draw2d.ImageFigure;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.rail.electric.simulator.model.SimulatorComplexCircuit;


public class ComplexCircuitFigure extends NodeFigure {
	public static final Image COMPLEX_CIRCUIT = 
			new Image(Display.getCurrent(),
					SimulatorComplexCircuit.class.getResourceAsStream("icons/comp_on_64X64.png"));
	
	public ComplexCircuitFigure() {
		final ToolbarLayout layout = new ToolbarLayout();
		//final FlowLayout layout = new FlowLayout();
		setLayoutManager(layout);
				
		ImageFigure imageFigure = new ImageFigure(COMPLEX_CIRCUIT);
		imageFigure.setSize(64,64);
		this.setPreferredSize(64, 64);
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
