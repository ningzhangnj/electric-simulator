package com.rail.electric.simulator.model;

import java.io.IOException;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.swt.graphics.Image;

import com.rail.electric.simulator.SimulatorMessages;

public class SimulatorLabel extends SimulatorSubpart {

	private static final int DEFAULT_WIDTH = 50;

	static final long serialVersionUID = 1;

	private String text = SimulatorMessages.SimulatorPlugin_Tool_CreationTool_LogicLabel;

	private static Image LOGIC_LABEL_ICON = createImage(LED.class,
			"icons/label16.gif"); //$NON-NLS-1$

	private static int count;

	public SimulatorLabel() {
		super();
		size.width = DEFAULT_WIDTH;
	}

	public String getLabelContents() {
		return text;
	}

	public Image getIconImage() {
		return LOGIC_LABEL_ICON;
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

	public void setLabelContents(String s) {
		text = s;
		firePropertyChange("labelContents", null, text); //$NON-NLS-2$//$NON-NLS-1$
	}

	public String toString() {
		return SimulatorMessages.SimulatorPlugin_Tool_CreationTool_LogicLabel
				+ " #" + getID() + " " //$NON-NLS-1$ //$NON-NLS-2$
				+ SimulatorMessages.PropertyDescriptor_Label_Text
				+ "=" + getLabelContents(); //$NON-NLS-1$ 
	}

}
