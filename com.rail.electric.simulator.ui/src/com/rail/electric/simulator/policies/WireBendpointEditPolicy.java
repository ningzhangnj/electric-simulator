package com.rail.electric.simulator.policies;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.BendpointRequest;

import com.rail.electric.simulator.model.Wire;
import com.rail.electric.simulator.model.commands.BendpointCommand;
import com.rail.electric.simulator.model.commands.CreateBendpointCommand;
import com.rail.electric.simulator.model.commands.DeleteBendpointCommand;
import com.rail.electric.simulator.model.commands.MoveBendpointCommand;

public class WireBendpointEditPolicy extends
		org.eclipse.gef.editpolicies.BendpointEditPolicy {

	protected Command getCreateBendpointCommand(BendpointRequest request) {
		CreateBendpointCommand com = new CreateBendpointCommand();
		Point p = request.getLocation();
		Connection conn = getConnection();

		conn.translateToRelative(p);

		com.setLocation(p);
		Point ref1 = getConnection().getSourceAnchor().getReferencePoint();
		Point ref2 = getConnection().getTargetAnchor().getReferencePoint();

		conn.translateToRelative(ref1);
		conn.translateToRelative(ref2);

		com.setRelativeDimensions(p.getDifference(ref1), p.getDifference(ref2));
		com.setWire((Wire) request.getSource().getModel());
		com.setIndex(request.getIndex());
		return com;
	}

	protected Command getMoveBendpointCommand(BendpointRequest request) {
		MoveBendpointCommand com = new MoveBendpointCommand();
		Point p = request.getLocation();
		Connection conn = getConnection();

		conn.translateToRelative(p);

		com.setLocation(p);

		Point ref1 = getConnection().getSourceAnchor().getReferencePoint();
		Point ref2 = getConnection().getTargetAnchor().getReferencePoint();

		conn.translateToRelative(ref1);
		conn.translateToRelative(ref2);

		com.setRelativeDimensions(p.getDifference(ref1), p.getDifference(ref2));
		com.setWire((Wire) request.getSource().getModel());
		com.setIndex(request.getIndex());
		return com;
	}

	protected Command getDeleteBendpointCommand(BendpointRequest request) {
		BendpointCommand com = new DeleteBendpointCommand();
		Point p = request.getLocation();
		com.setLocation(p);
		com.setWire((Wire) request.getSource().getModel());
		com.setIndex(request.getIndex());
		return com;
	}

}
