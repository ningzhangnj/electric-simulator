package com.rail.electric.simulator.parts;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import com.rail.electric.simulator.model.LED;
import com.rail.electric.simulator.model.SimulatorDiagram;
import com.rail.electric.simulator.model.SimulatorFlowContainer;
import com.rail.electric.simulator.model.SimulatorLabel;
import com.rail.electric.simulator.model.Wire;

public class GraphicalPartFactory implements EditPartFactory {

	public EditPart createEditPart(EditPart context, Object model) {
		EditPart child = null;

		if (model instanceof SimulatorFlowContainer)
			child = new SimulatorFlowContainerEditPart();
		else if (model instanceof Wire)
			child = new WireEditPart();
		else if (model instanceof LED)
			child = new LEDEditPart();
		else if (model instanceof SimulatorLabel)
			child = new  SimulatorLabelEditPart();		
		// Note that subclasses of LogicDiagram have already been matched above,
		// like Circuit
		else if (model instanceof SimulatorDiagram)
			child = new SimulatorDiagramEditPart();
		child.setModel(model);
		return child;
	}

}
