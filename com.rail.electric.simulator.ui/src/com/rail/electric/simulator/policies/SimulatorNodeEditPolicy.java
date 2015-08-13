package com.rail.electric.simulator.policies;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;

import com.rail.electric.simulator.figures.FigureFactory;
import com.rail.electric.simulator.figures.NodeFigure;
import com.rail.electric.simulator.model.SimulatorSubpart;
import com.rail.electric.simulator.model.Wire;
import com.rail.electric.simulator.model.commands.ConnectionCommand;
import com.rail.electric.simulator.parts.SimulatorEditPart;

public class SimulatorNodeEditPolicy extends
		org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy {

	protected Connection createDummyConnection(Request req) {
		PolylineConnection conn = FigureFactory.createNewWire(null);
		return conn;
	}

	protected Command getConnectionCompleteCommand(
			CreateConnectionRequest request) {
		ConnectionCommand command = (ConnectionCommand) request
				.getStartCommand();
		command.setTarget(getSimulatorSubpart());
		ConnectionAnchor ctor = getSimulatorEditPart().getTargetConnectionAnchor(
				request);
		if (ctor == null)
			return null;
		command.setTargetTerminal(getSimulatorEditPart()
				.mapConnectionAnchorToTerminal(ctor));
		return command;
	}

	protected Command getConnectionCreateCommand(CreateConnectionRequest request) {
		ConnectionCommand command = new ConnectionCommand();
		command.setWire(new Wire());
		command.setSource(getSimulatorSubpart());
		ConnectionAnchor ctor = getSimulatorEditPart().getSourceConnectionAnchor(
				request);
		command.setSourceTerminal(getSimulatorEditPart()
				.mapConnectionAnchorToTerminal(ctor));
		request.setStartCommand(command);
		return command;
	}

	/**
	 * Feedback should be added to the scaled feedback layer.
	 * 
	 * @see org.eclipse.gef.editpolicies.GraphicalEditPolicy#getFeedbackLayer()
	 */
	protected IFigure getFeedbackLayer() {
		/*
		 * Fix for Bug# 66590 Feedback needs to be added to the scaled feedback
		 * layer
		 */
		return getLayer(LayerConstants.SCALED_FEEDBACK_LAYER);
	}

	protected SimulatorEditPart getSimulatorEditPart() {
		return (SimulatorEditPart) getHost();
	}

	protected SimulatorSubpart getSimulatorSubpart() {
		return (SimulatorSubpart) getHost().getModel();
	}

	protected Command getReconnectTargetCommand(ReconnectRequest request) {
		/*if (getSimulatorSubpart() instanceof LiveOutput
				|| getSimulatorSubpart() instanceof GroundOutput)
			return null;*/

		ConnectionCommand cmd = new ConnectionCommand();
		cmd.setWire((Wire) request.getConnectionEditPart().getModel());

		ConnectionAnchor ctor = getSimulatorEditPart().getTargetConnectionAnchor(
				request);
		cmd.setTarget(getSimulatorSubpart());
		cmd.setTargetTerminal(getSimulatorEditPart().mapConnectionAnchorToTerminal(
				ctor));
		return cmd;
	}

	protected Command getReconnectSourceCommand(ReconnectRequest request) {
		ConnectionCommand cmd = new ConnectionCommand();
		cmd.setWire((Wire) request.getConnectionEditPart().getModel());

		ConnectionAnchor ctor = getSimulatorEditPart().getSourceConnectionAnchor(
				request);
		cmd.setSource(getSimulatorSubpart());
		cmd.setSourceTerminal(getSimulatorEditPart().mapConnectionAnchorToTerminal(
				ctor));
		return cmd;
	}

	protected NodeFigure getNodeFigure() {
		return (NodeFigure) ((GraphicalEditPart) getHost()).getFigure();
	}

}
