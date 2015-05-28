package com.rail.electric.simulator.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collections;
import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import com.rail.electric.simulator.model.LED;
import com.rail.electric.simulator.model.SimulatorDiagram;
import com.rail.electric.simulator.model.SimulatorSubpart;
import com.rail.electric.simulator.policies.LEDEditPolicy;
import com.rail.electric.simulator.policies.SimulatorElementEditPolicy;
import com.rail.electric.simulator.policies.SimulatorTreeEditPolicy;

/**
 * EditPart for Logic components in the Tree.
 */
public class SimulatorTreeEditPart extends
		org.eclipse.gef.editparts.AbstractTreeEditPart implements
		PropertyChangeListener {

	/**
	 * Constructor initializes this with the given model.
	 * 
	 * @param model
	 *            Model for this.
	 */
	public SimulatorTreeEditPart(Object model) {
		super(model);
	}

	public void activate() {
		super.activate();
		getSimulatorSubpart().addPropertyChangeListener(this);
	}

	/**
	 * Creates and installs pertinent EditPolicies for this.
	 */
	protected void createEditPolicies() {
		EditPolicy component;
		if (getModel() instanceof LED)
			component = new LEDEditPolicy();
		else
			component = new SimulatorElementEditPolicy();
		installEditPolicy(EditPolicy.COMPONENT_ROLE, component);
		installEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE,
				new SimulatorTreeEditPolicy());
	}

	public void deactivate() {
		getSimulatorSubpart().removePropertyChangeListener(this);
		super.deactivate();
	}

	/**
	 * Returns the model of this as a LogicSubPart.
	 * 
	 * @return Model of this.
	 */
	protected SimulatorSubpart getSimulatorSubpart() {
		return (SimulatorSubpart) getModel();
	}

	/**
	 * Returns <code>null</code> as a Tree EditPart holds no children under it.
	 * 
	 * @return <code>null</code>
	 */
	protected List getModelChildren() {
		return Collections.EMPTY_LIST;
	}

	public void propertyChange(PropertyChangeEvent change) {
		if (change.getPropertyName().equals(SimulatorDiagram.CHILDREN)) {
			if (change.getOldValue() instanceof Integer)
				// new child
				addChild(createChild(change.getNewValue()),
						((Integer) change.getOldValue()).intValue());
			else
				// remove child
				removeChild((EditPart) getViewer().getEditPartRegistry().get(
						change.getOldValue()));
		} else
			refreshVisuals();
	}

	/**
	 * Refreshes the visual properties of the TreeItem for this part.
	 */
	protected void refreshVisuals() {
		if (getWidget() instanceof Tree)
			return;
		Image image = getSimulatorSubpart().getIcon();
		TreeItem item = (TreeItem) getWidget();
		if (image != null)
			image.setBackground(item.getParent().getBackground());
		setWidgetImage(image);
		setWidgetText(getSimulatorSubpart().toString());
	}

}
