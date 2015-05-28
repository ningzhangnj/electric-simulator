package com.rail.electric.simulator.parts;

import java.beans.PropertyChangeEvent;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.OrderedLayout;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.gef.EditPolicy;

import com.rail.electric.simulator.figures.SimulatorFlowBorder;
import com.rail.electric.simulator.model.SimulatorFlowContainer;
import com.rail.electric.simulator.policies.ContainerHighlightEditPolicy;
import com.rail.electric.simulator.policies.SimulatorFlowEditPolicy;

public class SimulatorFlowContainerEditPart extends SimulatorContainerEditPart {

	protected void createEditPolicies() {
		super.createEditPolicies();
		installEditPolicy(EditPolicy.NODE_ROLE, null);
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, null);
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new SimulatorFlowEditPolicy());
		installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE,
				new ContainerHighlightEditPolicy());
	}

	public void activate() {
		if (isActive()) {
			return;
		}
		super.activate();
		updateLayout(getSimulatorFlowContainer().getLayout());
	}

	public void propertyChange(PropertyChangeEvent evt) {
		String prop = evt.getPropertyName();
		if (SimulatorFlowContainer.ID_LAYOUT.equals(prop)) {
			updateLayout((Integer) evt.getNewValue());
		} else {
			super.propertyChange(evt);
		}
	}

	protected void updateLayout(Integer newLayout) {
		getFigure().setLayoutManager(createLayout(newLayout));
	}

	private OrderedLayout createLayout(Integer newLayout) {
		OrderedLayout layout;
		if (newLayout.equals(SimulatorFlowContainer.LAYOUT_SINGLE_ROW)) {
			layout = new ToolbarLayout(false);
			((ToolbarLayout) layout).setSpacing(5);
		} else {
			layout = new FlowLayout();
		}
		return layout;
	}

	protected SimulatorFlowContainer getSimulatorFlowContainer() {
		return (SimulatorFlowContainer) getModel();
	}

	protected IFigure createFigure() {
		Figure figure = new Figure();
		figure.setLayoutManager(createLayout(getSimulatorFlowContainer()
				.getLayout()));
		figure.setBorder(new SimulatorFlowBorder());
		figure.setOpaque(true);
		return figure;
	}

}
