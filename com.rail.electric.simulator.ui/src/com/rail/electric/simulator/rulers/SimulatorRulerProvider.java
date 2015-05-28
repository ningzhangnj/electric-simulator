package com.rail.electric.simulator.rulers;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.rulers.RulerChangeListener;
import org.eclipse.gef.rulers.RulerProvider;

import com.rail.electric.simulator.model.SimulatorGuide;
import com.rail.electric.simulator.model.SimulatorRuler;
import com.rail.electric.simulator.model.commands.CreateGuideCommand;
import com.rail.electric.simulator.model.commands.DeleteGuideCommand;
import com.rail.electric.simulator.model.commands.MoveGuideCommand;

public class SimulatorRulerProvider extends RulerProvider {

	private SimulatorRuler ruler;
	private PropertyChangeListener rulerListener = new PropertyChangeListener() {
		public void propertyChange(PropertyChangeEvent evt) {
			if (evt.getPropertyName().equals(SimulatorRuler.PROPERTY_CHILDREN)) {
				SimulatorGuide guide = (SimulatorGuide) evt.getNewValue();
				if (getGuides().contains(guide)) {
					guide.addPropertyChangeListener(guideListener);
				} else {
					guide.removePropertyChangeListener(guideListener);
				}
				for (int i = 0; i < listeners.size(); i++) {
					((RulerChangeListener) listeners.get(i))
							.notifyGuideReparented(guide);
				}
			} else {
				for (int i = 0; i < listeners.size(); i++) {
					((RulerChangeListener) listeners.get(i))
							.notifyUnitsChanged(ruler.getUnit());
				}
			}
		}
	};
	private PropertyChangeListener guideListener = new PropertyChangeListener() {
		public void propertyChange(PropertyChangeEvent evt) {
			if (evt.getPropertyName().equals(SimulatorGuide.PROPERTY_CHILDREN)) {
				for (int i = 0; i < listeners.size(); i++) {
					((RulerChangeListener) listeners.get(i))
							.notifyPartAttachmentChanged(evt.getNewValue(),
									evt.getSource());
				}
			} else {
				for (int i = 0; i < listeners.size(); i++) {
					((RulerChangeListener) listeners.get(i))
							.notifyGuideMoved(evt.getSource());
				}
			}
		}
	};

	public SimulatorRulerProvider(SimulatorRuler ruler) {
		this.ruler = ruler;
		this.ruler.addPropertyChangeListener(rulerListener);
		List guides = getGuides();
		for (int i = 0; i < guides.size(); i++) {
			((SimulatorGuide) guides.get(i))
					.addPropertyChangeListener(guideListener);
		}
	}

	public List getAttachedModelObjects(Object guide) {
		return new ArrayList(((SimulatorGuide) guide).getParts());
	}

	public Command getCreateGuideCommand(int position) {
		return new CreateGuideCommand(ruler, position);
	}

	public Command getDeleteGuideCommand(Object guide) {
		return new DeleteGuideCommand((SimulatorGuide) guide, ruler);
	}

	public Command getMoveGuideCommand(Object guide, int pDelta) {
		return new MoveGuideCommand((SimulatorGuide) guide, pDelta);
	}

	public int[] getGuidePositions() {
		List guides = getGuides();
		int[] result = new int[guides.size()];
		for (int i = 0; i < guides.size(); i++) {
			result[i] = ((SimulatorGuide) guides.get(i)).getPosition();
		}
		return result;
	}

	public Object getRuler() {
		return ruler;
	}

	public int getUnit() {
		return ruler.getUnit();
	}

	public void setUnit(int newUnit) {
		ruler.setUnit(newUnit);
	}

	public int getGuidePosition(Object guide) {
		return ((SimulatorGuide) guide).getPosition();
	}

	public List getGuides() {
		return ruler.getGuides();
	}

}
