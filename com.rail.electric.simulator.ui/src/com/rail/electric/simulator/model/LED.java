package com.rail.electric.simulator.model;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import com.rail.electric.simulator.SimulatorMessages;

public class LED extends SimulatorSubpart {

	static final long serialVersionUID = 1;

	private static final Dimension DEFAULT_SIZE = new Dimension(61, 47);

	private static Image LED_ICON = createImage(LED.class,
			"icons/ledicon16.gif"); //$NON-NLS-1$
	private static int count;
	public static String P_VALUE = "value"; //$NON-NLS-1$
	protected static IPropertyDescriptor[] newDescriptors = null;

	// Inputs
	public static String TERMINAL_1_IN = "A", //$NON-NLS-1$
			TERMINAL_2_IN = "B", //$NON-NLS-1$
			TERMINAL_3_IN = "C", //$NON-NLS-1$
			TERMINAL_4_IN = "D"; //$NON-NLS-1$
	// Outputs
	public static String TERMINAL_1_OUT = "1", //$NON-NLS-1$
			TERMINAL_2_OUT = "2", //$NON-NLS-1$
			TERMINAL_3_OUT = "3", //$NON-NLS-1$
			TERMINAL_4_OUT = "4"; //$NON-NLS-1$

	protected static String[] IN_TERMINALS = new String[] { TERMINAL_1_IN,
			TERMINAL_2_IN, TERMINAL_3_IN, TERMINAL_4_IN };
	protected boolean bits[] = new boolean[4];

	static {
		PropertyDescriptor pValueProp = new TextPropertyDescriptor(P_VALUE,
				SimulatorMessages.PropertyDescriptor_LED_Value);
		pValueProp.setValidator(SimulatorNumberCellEditorValidator.instance());
		if (descriptors != null) {
			newDescriptors = new IPropertyDescriptor[descriptors.length + 1];
			for (int i = 0; i < descriptors.length; i++)
				newDescriptors[i] = descriptors[i];
			newDescriptors[descriptors.length] = pValueProp;
		} else
			newDescriptors = new IPropertyDescriptor[] { pValueProp };
	}

	public LED() {
		size.width = DEFAULT_SIZE.width;
		size.height = DEFAULT_SIZE.height;
		location.x = 20;
		location.y = 20;
	}

	public Image getIconImage() {
		return LED_ICON;
	}

	public String getNewID() {
		return Integer.toString(count++);
	}

	public IPropertyDescriptor[] getPropertyDescriptors() {
		return newDescriptors;
	}

	public Object getPropertyValue(Object propName) {
		if (P_VALUE.equals(propName))
			return new Integer(getValue()).toString();
		if (ID_SIZE.equals(propName)) {
			return new String(
					"(" + getSize().width + "," + getSize().height + ")");//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
		}
		return super.getPropertyValue(propName);
	}

	public int getValue() {
		int val = 0;
		if (bits[0])
			val += 1;
		if (bits[1])
			val += 2;
		if (bits[2])
			val += 4;
		if (bits[3])
			val += 8;
		return val;
	}

	/**
	 * Nulls out any changes to this's size as it is fixed.
	 */
	public void setPropertyValue(Object id, Object value) {
		if (P_VALUE.equals(id))
			setValue(Integer.parseInt((String) value));
		else
			super.setPropertyValue(id, value);
	}

	public void setSize(Dimension d) {
		super.setSize(DEFAULT_SIZE);
	}

	public void resetPropertyValue(Object id) {
		if (P_VALUE.equals(id))
			setValue(0);
		super.resetPropertyValue(id);
	}

	public String toString() {
		return SimulatorMessages.LED_LabelText + " #" + getID() //$NON-NLS-1$
				+ " " + SimulatorMessages.PropertyDescriptor_LED_Value //$NON-NLS-1$
				+ "=" + getValue(); //$NON-NLS-1$
	}

	public void setValue(int v) {
		int val = v % 16;
		bits = new boolean[4]; // Shorthand to set all bits to false
		if (val >= 8)
			bits[3] = true;
		if (val % 8 > 3)
			bits[2] = true;
		if (val % 4 > 1)
			bits[1] = true;
		bits[0] = val % 2 == 1;
		update();
		firePropertyChange(P_VALUE, null, null);
	}

	public void update() {
		boolean changed = false;
		boolean[] oldBits = new boolean[bits.length];
		for (int i = 0; i < oldBits.length; i++) {
			oldBits[i] = bits[i];
			bits[i] = getInput(IN_TERMINALS[i]);
			if (bits[i] != oldBits[i]) {
				changed = true;
			}
		}
		if (changed) {
			firePropertyChange(P_VALUE, null, null);
		}

		setOutput(TERMINAL_1_OUT, bits[0]);
		setOutput(TERMINAL_2_OUT, bits[1]);
		setOutput(TERMINAL_3_OUT, bits[2]);
		setOutput(TERMINAL_4_OUT, bits[3]);
	}

}
