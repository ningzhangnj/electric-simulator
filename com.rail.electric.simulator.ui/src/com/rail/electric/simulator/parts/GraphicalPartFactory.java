package com.rail.electric.simulator.parts;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import com.rail.electric.simulator.model.LED;
import com.rail.electric.simulator.model.SimulatorComplexCircuit;
import com.rail.electric.simulator.model.SimulatorDiagram;
import com.rail.electric.simulator.model.SimulatorFlowContainer;
import com.rail.electric.simulator.model.SimulatorGround;
import com.rail.electric.simulator.model.SimulatorGroundWithResist;
import com.rail.electric.simulator.model.SimulatorLabel;
import com.rail.electric.simulator.model.SimulatorMainSwitch;
import com.rail.electric.simulator.model.SimulatorResist;
import com.rail.electric.simulator.model.SimulatorSwitch;
import com.rail.electric.simulator.model.SimulatorThreePhaseTransformer;
import com.rail.electric.simulator.model.SimulatorTwoPhaseTransformer;
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
		else if (model instanceof SimulatorResist)
			child = new  SimulatorResistEditPart();	
		else if (model instanceof SimulatorGround)
			child = new  SimulatorGroundEditPart();	
		else if (model instanceof SimulatorComplexCircuit)
			child = new  SimulatorComplexCircuitEditPart();	
		else if (model instanceof SimulatorGroundWithResist)
			child = new  SimulatorGroundWithResistEditPart();	
		else if (model instanceof SimulatorTwoPhaseTransformer)
			child = new  SimulatorTwoPhaseTransformerEditPart();	
		else if (model instanceof SimulatorThreePhaseTransformer)
			child = new  SimulatorThreePhaseTransformerEditPart();
		else if (model instanceof SimulatorSwitch)
			child = new  SimulatorSwitchEditPart();	
		else if (model instanceof SimulatorMainSwitch)
			child = new  SimulatorMainSwitchEditPart();	
		// Note that subclasses of LogicDiagram have already been matched above,
		// like Circuit
		else if (model instanceof SimulatorDiagram)
			child = new SimulatorDiagramEditPart();
		child.setModel(model);
		return child;
	}

}
