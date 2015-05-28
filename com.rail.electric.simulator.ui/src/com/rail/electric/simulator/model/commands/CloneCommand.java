package com.rail.electric.simulator.model.commands;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;

import com.rail.electric.simulator.SimulatorMessages;
import com.rail.electric.simulator.model.LED;
import com.rail.electric.simulator.model.SimulatorDiagram;
import com.rail.electric.simulator.model.SimulatorGuide;
import com.rail.electric.simulator.model.SimulatorSubpart;
import com.rail.electric.simulator.model.Wire;
import com.rail.electric.simulator.model.WireBendpoint;

public class CloneCommand extends Command {

	private List parts, newTopLevelParts, newConnections;
	private SimulatorDiagram parent;
	private Map bounds, indices, connectionPartMap;
	private ChangeGuideCommand vGuideCommand, hGuideCommand;
	private SimulatorGuide hGuide, vGuide;
	private int hAlignment, vAlignment;

	public CloneCommand() {
		super(SimulatorMessages.CloneCommand_Label);
		parts = new LinkedList();
	}

	public void addPart(SimulatorSubpart part, Rectangle newBounds) {
		parts.add(part);
		if (bounds == null) {
			bounds = new HashMap();
		}
		bounds.put(part, newBounds);
	}

	public void addPart(SimulatorSubpart part, int index) {
		parts.add(part);
		if (indices == null) {
			indices = new HashMap();
		}
		indices.put(part, new Integer(index));
	}

	protected void clonePart(SimulatorSubpart oldPart, SimulatorDiagram newParent,
			Rectangle newBounds, List newConnections, Map connectionPartMap,
			int index) {
		SimulatorSubpart newPart = null;

		if (oldPart instanceof LED) {
			newPart = new LED();
			newPart.setPropertyValue(LED.P_VALUE,
					oldPart.getPropertyValue(LED.P_VALUE));
		} 

		if (oldPart instanceof SimulatorDiagram) {
			Iterator i = ((SimulatorDiagram) oldPart).getChildren().iterator();
			while (i.hasNext()) {
				// for children they will not need new bounds
				clonePart((SimulatorSubpart) i.next(), (SimulatorDiagram) newPart,
						null, newConnections, connectionPartMap, -1);
			}
		}

		Iterator i = oldPart.getTargetConnections().iterator();
		while (i.hasNext()) {
			Wire connection = (Wire) i.next();
			Wire newConnection = new Wire();
			newConnection.setValue(connection.getValue());
			newConnection.setTarget(newPart);
			newConnection.setTargetTerminal(connection.getTargetTerminal());
			newConnection.setSourceTerminal(connection.getSourceTerminal());
			newConnection.setSource(connection.getSource());

			Iterator b = connection.getBendpoints().iterator();
			Vector newBendPoints = new Vector();

			while (b.hasNext()) {
				WireBendpoint bendPoint = (WireBendpoint) b.next();
				WireBendpoint newBendPoint = new WireBendpoint();
				newBendPoint.setRelativeDimensions(
						bendPoint.getFirstRelativeDimension(),
						bendPoint.getSecondRelativeDimension());
				newBendPoint.setWeight(bendPoint.getWeight());
				newBendPoints.add(newBendPoint);
			}

			newConnection.setBendpoints(newBendPoints);
			newConnections.add(newConnection);
		}

		if (index < 0) {
			newParent.addChild(newPart);
		} else {
			newParent.addChild(newPart, index);
		}

		newPart.setSize(oldPart.getSize());

		if (newBounds != null) {
			newPart.setLocation(newBounds.getTopLeft());
		} else {
			newPart.setLocation(oldPart.getLocation());
		}

		// keep track of the new parts so we can delete them in undo
		// keep track of the oldpart -> newpart map so that we can properly
		// attach
		// all connections.
		if (newParent == parent)
			newTopLevelParts.add(newPart);
		connectionPartMap.put(oldPart, newPart);
	}

	public void execute() {
		connectionPartMap = new HashMap();
		newConnections = new LinkedList();
		newTopLevelParts = new LinkedList();

		Iterator i = parts.iterator();

		SimulatorSubpart part = null;
		while (i.hasNext()) {
			part = (SimulatorSubpart) i.next();
			if (bounds != null && bounds.containsKey(part)) {
				clonePart(part, parent, (Rectangle) bounds.get(part),
						newConnections, connectionPartMap, -1);
			} else if (indices != null && indices.containsKey(part)) {
				clonePart(part, parent, null, newConnections,
						connectionPartMap,
						((Integer) indices.get(part)).intValue());
			} else {
				clonePart(part, parent, null, newConnections,
						connectionPartMap, -1);
			}
		}

		// go through and set the source of each connection to the proper
		// source.
		Iterator c = newConnections.iterator();

		while (c.hasNext()) {
			Wire conn = (Wire) c.next();
			SimulatorSubpart source = conn.getSource();
			if (connectionPartMap.containsKey(source)) {
				conn.setSource((SimulatorSubpart) connectionPartMap.get(source));
				conn.attachSource();
				conn.attachTarget();
			}
		}
		
		if (hGuide != null) {
			hGuideCommand = new ChangeGuideCommand(
					(SimulatorSubpart) connectionPartMap.get(parts.get(0)), true);
			hGuideCommand.setNewGuide(hGuide, hAlignment);
			hGuideCommand.execute();
		}

		if (vGuide != null) {
			vGuideCommand = new ChangeGuideCommand(
					(SimulatorSubpart) connectionPartMap.get(parts.get(0)), false);
			vGuideCommand.setNewGuide(vGuide, vAlignment);
			vGuideCommand.execute();
		}
	}

	public void setParent(SimulatorDiagram parent) {
		this.parent = parent;
	}

	public void redo() {
		for (Iterator iter = newTopLevelParts.iterator(); iter.hasNext();)
			parent.addChild((SimulatorSubpart) iter.next());
		for (Iterator iter = newConnections.iterator(); iter.hasNext();) {
			Wire conn = (Wire) iter.next();
			SimulatorSubpart source = conn.getSource();
			if (connectionPartMap.containsKey(source)) {
				conn.setSource((SimulatorSubpart) connectionPartMap.get(source));
				conn.attachSource();
				conn.attachTarget();
			}
		}
		if (hGuideCommand != null)
			hGuideCommand.redo();
		if (vGuideCommand != null)
			vGuideCommand.redo();
	}

	public void setGuide(SimulatorGuide guide, int alignment, boolean isHorizontal) {
		if (isHorizontal) {
			hGuide = guide;
			hAlignment = alignment;
		} else {
			vGuide = guide;
			vAlignment = alignment;
		}
	}
	
	public void undo() {
		if (hGuideCommand != null)
			hGuideCommand.undo();
		if (vGuideCommand != null)
			vGuideCommand.undo();
		for (Iterator iter = newTopLevelParts.iterator(); iter.hasNext();)
			parent.removeChild((SimulatorSubpart) iter.next());
	}

}
