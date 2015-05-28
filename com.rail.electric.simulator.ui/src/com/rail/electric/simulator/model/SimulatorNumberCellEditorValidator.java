package com.rail.electric.simulator.model;

import org.eclipse.jface.viewers.ICellEditorValidator;

import com.rail.electric.simulator.SimulatorMessages;

public class SimulatorNumberCellEditorValidator implements ICellEditorValidator {

	private static SimulatorNumberCellEditorValidator instance;

	public static SimulatorNumberCellEditorValidator instance() {
		if (instance == null)
			instance = new SimulatorNumberCellEditorValidator();
		return instance;
	}

	public String isValid(Object value) {
		try {
			new Integer((String) value);
			return null;
		} catch (NumberFormatException exc) {
			return SimulatorMessages.CellEditorValidator_NotANumberMessage;
		}
	}

}
