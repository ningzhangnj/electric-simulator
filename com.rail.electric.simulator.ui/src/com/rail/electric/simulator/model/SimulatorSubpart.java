package com.rail.electric.simulator.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import com.rail.electric.simulator.SimulatorMessages;

abstract public class SimulatorSubpart extends SimulatorElement {

	private String id;
	private SimulatorGuide verticalGuide, horizontalGuide;
	protected Hashtable inputs = new Hashtable(7);
	protected Point location = new Point(0, 0);
	protected Vector outputs = new Vector(4, 4);
	static final long serialVersionUID = 1;
	protected Dimension size = new Dimension(-1, -1);

	protected static IPropertyDescriptor[] descriptors = null;
	public static String ID_SIZE = "size"; //$NON-NLS-1$
	public static String ID_LOCATION = "location"; //$NON-NLS-1$

	static {
		descriptors = new IPropertyDescriptor[] {
				new PropertyDescriptor(ID_SIZE,
						SimulatorMessages.PropertyDescriptor_LogicSubPart_Size),
				new PropertyDescriptor(ID_LOCATION,
						SimulatorMessages.PropertyDescriptor_LogicSubPart_Location) };
	}

	protected static Image createImage(Class rsrcClass, String name) {
		InputStream stream = rsrcClass.getResourceAsStream(name);
		Image image = new Image(null, stream);
		try {
			stream.close();
		} catch (IOException ioe) {
		}
		return image;
	}

	public SimulatorSubpart() {
		setID(getNewID());
	}

	public void connectInput(Wire w) {
		inputs.put(w.getTargetTerminal(), w);
		update();
		fireStructureChange(INPUTS, w);
	}

	public void connectOutput(Wire w) {
		outputs.addElement(w);
		update();
		fireStructureChange(OUTPUTS, w);
	}

	public void disconnectInput(Wire w) {
		inputs.remove(w.getTargetTerminal());
		update();
		fireStructureChange(INPUTS, w);
	}

	public void disconnectOutput(Wire w) {
		outputs.removeElement(w);
		update();
		fireStructureChange(OUTPUTS, w);
	}

	public Vector getConnections() {
		Vector v = (Vector) outputs.clone();
		Enumeration ins = inputs.elements();
		while (ins.hasMoreElements())
			v.addElement(ins.nextElement());
		return v;
	}

	public SimulatorGuide getHorizontalGuide() {
		return horizontalGuide;
	}
	
	public String getID() {
		return id;
	}
		
	protected boolean getInput(String terminal) {
		if (inputs.isEmpty()) {
			return false;
		}
		Wire w = (Wire) inputs.get(terminal);
		return (w == null) ? false : w.getValue();

	}

	public Point getLocation() {
		return location;
	}

	abstract protected String getNewID();

	/**
	 * Returns useful property descriptors for the use in property sheets. this
	 * supports location and size.
	 * 
	 * @return Array of property descriptors.
	 */
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return descriptors;
	}

	/**
	 * Returns an Object which represents the appropriate value for the property
	 * name supplied.
	 * 
	 * @param propName
	 *            Name of the property for which the the values are needed.
	 * @return Object which is the value of the property.
	 */
	public Object getPropertyValue(Object propName) {
		if (ID_SIZE.equals(propName))
			return new DimensionPropertySource(getSize());
		else if (ID_LOCATION.equals(propName))
			return new LocationPropertySource(getLocation());
		return null;
	}

	public Dimension getSize() {
		return size;
	}

	public Vector getSourceConnections() {
		return (Vector) outputs.clone();
	}

	public Vector getTargetConnections() {
		Enumeration elements = inputs.elements();
		Vector v = new Vector(inputs.size());
		while (elements.hasMoreElements())
			v.addElement(elements.nextElement());
		return v;
	}

	public SimulatorGuide getVerticalGuide() {
		return verticalGuide;
	}

	/**
 * 
 */
	public boolean isPropertySet() {
		return true;
	}

	public void setHorizontalGuide(SimulatorGuide hGuide) {
		horizontalGuide = hGuide;
		/*
		 * @TODO:Pratik firePropertyChanged?
		 */
	}

	/*
	 * Does nothing for the present, but could be used to reset the properties
	 * of this to whatever values are desired.
	 * 
	 * @param id Parameter which is to be reset.
	 * 
	 * public void resetPropertyValue(Object id){ if(ID_SIZE.equals(id)){;}
	 * if(ID_LOCATION.equals(id)){;} }
	 */

	public void setID(String s) {
		id = s;
	}
	
	public void setLocation(Point p) {
		if (location.equals(p))
			return;
		location = p;
		firePropertyChange("location", null, p); //$NON-NLS-1$
	}

	protected void setOutput(String terminal, boolean val) {
		Enumeration elements = outputs.elements();
		Wire w;
		while (elements.hasMoreElements()) {
			w = (Wire) elements.nextElement();
			if (w.getSourceTerminal().equals(terminal)
					&& this.equals(w.getSource()))
				w.setValue(val);
		}
	}

	/**
	 * Sets the value of a given property with the value supplied. Also fires a
	 * property change if necessary.
	 * 
	 * @param id
	 *            Name of the parameter to be changed.
	 * @param value
	 *            Value to be set to the given parameter.
	 */
	public void setPropertyValue(Object id, Object value) {
		if (ID_SIZE.equals(id))
			setSize((Dimension) value);
		else if (ID_LOCATION.equals(id))
			setLocation((Point) value);
	}

	public void setSize(Dimension d) {
		if (size.equals(d))
			return;
		size = d;
		firePropertyChange("size", null, size); //$NON-NLS-1$
	}

	public void setVerticalGuide(SimulatorGuide vGuide) {
		verticalGuide = vGuide;
	}
		
}
