package com.rail.electric.simulator.model;

import java.io.IOException;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.swt.graphics.Image;

import com.rail.electric.simulator.SimulatorMessages;

public class SimulatorGround extends SimulatorSubpart {

	public static String TERMINAL_IN = "IN";
	public static String TERMINAL_OUT = "OUT";
			
	private static final int DEFAULT_WIDTH = 48;

	static final long serialVersionUID = 1;

	
	private static Image SIMULATOR_GROUND_ICON = createImage(LED.class,
			"icons/ground_on_18X24.png"); //$NON-NLS-1$

	private static int count;

	public SimulatorGround() {
		super();
		size.width = DEFAULT_WIDTH;
	}

	public Image getIconImage() {
		return SIMULATOR_GROUND_ICON;
	}

	protected String getNewID() {
		return Integer.toString(count++);
	}

	public Dimension getSize() {
		return new Dimension(size.width, -1);
	}

	private void readObject(java.io.ObjectInputStream s) throws IOException,
			ClassNotFoundException {
		s.defaultReadObject();
	}

	public void setSize(Dimension d) {
		d.height = -1;
		super.setSize(d);
	}

	@Override
	public void changeStatus() {
		firePropertyChange("changeStatus", null, null);		
	}

}
