package com.rail.electric.simulator.parts;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

public class TreePartFactory implements EditPartFactory {

	public EditPart createEditPart(EditPart context, Object model) {
		/*if (model instanceof LED)
			return new LogicTreeEditPart(model);
		if (model instanceof LogicDiagram)
			return new LogicContainerTreeEditPart(model);*/
		return new SimulatorTreeEditPart(model);
	}

}
