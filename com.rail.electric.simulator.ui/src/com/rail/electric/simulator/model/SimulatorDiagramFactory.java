package com.rail.electric.simulator.model;

import org.eclipse.draw2d.geometry.Point;


public class SimulatorDiagramFactory {

	SimulatorDiagram root;

	protected static void connect(SimulatorSubpart e1, String t1, SimulatorSubpart e2,
			String t2) {
		Wire wire = new Wire();
		wire.setSource(e1);
		wire.setSourceTerminal(t1);
		wire.setTarget(e2);
		wire.setTargetTerminal(t2);
		wire.attachSource();
		wire.attachTarget();
	}

	public Object createEmptyModel() {
		final SimulatorDiagram root = new SimulatorDiagram();
		return root;
	}	

	public Object getRootElement() {
		if (root == null)
			createEmptyModel();
		return root;
	}
	
	public static Object createSampleModel() {
		final SimulatorDiagram root = new SimulatorDiagram();
		final LED led1, led2;
		led1 = new LED();
		led1.setValue(3);
		led2 = new LED();
		led2.setValue(12);
		led1.setLocation(new Point(170, 16));
		led2.setLocation(new Point(320, 16));
		root.addChild(led1);
		root.addChild(led2);
		
		connect(led1, LED.TERMINAL_1_OUT, led2, LED.TERMINAL_4_IN);
		return root;
	}

}
