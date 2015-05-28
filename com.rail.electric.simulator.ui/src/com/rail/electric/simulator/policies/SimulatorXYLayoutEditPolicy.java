package com.rail.electric.simulator.policies;

import java.util.Iterator;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.SnapToGuides;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.rulers.RulerProvider;

import com.rail.electric.simulator.SimulatorMessages;
import com.rail.electric.simulator.figures.LabelFeedbackFigure;
import com.rail.electric.simulator.figures.SimulatorColorConstants;
import com.rail.electric.simulator.figures.SimulatorFlowFeedbackFigure;
import com.rail.electric.simulator.model.LED;
import com.rail.electric.simulator.model.SimulatorDiagram;
import com.rail.electric.simulator.model.SimulatorFlowContainer;
import com.rail.electric.simulator.model.SimulatorGuide;
import com.rail.electric.simulator.model.SimulatorLabel;
import com.rail.electric.simulator.model.SimulatorSubpart;
import com.rail.electric.simulator.model.commands.AddCommand;
import com.rail.electric.simulator.model.commands.ChangeGuideCommand;
import com.rail.electric.simulator.model.commands.CloneCommand;
import com.rail.electric.simulator.model.commands.CreateCommand;
import com.rail.electric.simulator.model.commands.SetConstraintCommand;

public class SimulatorXYLayoutEditPolicy extends
		org.eclipse.gef.editpolicies.XYLayoutEditPolicy {

	public SimulatorXYLayoutEditPolicy(XYLayout layout) {
		super();
		setXyLayout(layout);
	}

	protected Command chainGuideAttachmentCommand(Request request,
			SimulatorSubpart part, Command cmd, boolean horizontal) {
		Command result = cmd;

		// Attach to guide, if one is given
		Integer guidePos = (Integer) request.getExtendedData().get(
				horizontal ? SnapToGuides.KEY_HORIZONTAL_GUIDE
						: SnapToGuides.KEY_VERTICAL_GUIDE);
		if (guidePos != null) {
			int alignment = ((Integer) request.getExtendedData().get(
					horizontal ? SnapToGuides.KEY_HORIZONTAL_ANCHOR
							: SnapToGuides.KEY_VERTICAL_ANCHOR)).intValue();
			ChangeGuideCommand cgm = new ChangeGuideCommand(part, horizontal);
			cgm.setNewGuide(findGuideAt(guidePos.intValue(), horizontal),
					alignment);
			result = result.chain(cgm);
		}

		return result;
	}

	protected Command chainGuideDetachmentCommand(Request request,
			SimulatorSubpart part, Command cmd, boolean horizontal) {
		Command result = cmd;

		// Detach from guide, if none is given
		Integer guidePos = (Integer) request.getExtendedData().get(
				horizontal ? SnapToGuides.KEY_HORIZONTAL_GUIDE
						: SnapToGuides.KEY_VERTICAL_GUIDE);
		if (guidePos == null)
			result = result.chain(new ChangeGuideCommand(part, horizontal));

		return result;
	}

	protected Command createAddCommand(ChangeBoundsRequest request,
			EditPart child, Object constraint) {
		SimulatorSubpart part = (SimulatorSubpart) child.getModel();
		Rectangle rect = (Rectangle) constraint;

		AddCommand add = new AddCommand();
		add.setParent((SimulatorDiagram) getHost().getModel());
		add.setChild(part);
		add.setLabel(SimulatorMessages.SimulatorXYLayoutEditPolicy_AddCommandLabelText);
		add.setDebugLabel("LogicXYEP add subpart");//$NON-NLS-1$

		SetConstraintCommand setConstraint = new SetConstraintCommand();
		setConstraint.setLocation(rect);
		setConstraint.setPart(part);
		setConstraint
				.setLabel(SimulatorMessages.SimulatorXYLayoutEditPolicy_AddCommandLabelText);
		setConstraint.setDebugLabel("LogicXYEP setConstraint");//$NON-NLS-1$

		Command cmd = add.chain(setConstraint);
		cmd = chainGuideAttachmentCommand(request, part, cmd, true);
		cmd = chainGuideAttachmentCommand(request, part, cmd, false);
		cmd = chainGuideDetachmentCommand(request, part, cmd, true);
		return chainGuideDetachmentCommand(request, part, cmd, false);
	}

	/**
	 * @see org.eclipse.gef.editpolicies.ConstrainedLayoutEditPolicy#createChangeConstraintCommand(org.eclipse.gef.EditPart,
	 *      java.lang.Object)
	 */
	protected Command createChangeConstraintCommand(EditPart child,
			Object constraint) {
		return null;
	}

	protected Command createChangeConstraintCommand(
			ChangeBoundsRequest request, EditPart child, Object constraint) {
		SetConstraintCommand cmd = new SetConstraintCommand();
		SimulatorSubpart part = (SimulatorSubpart) child.getModel();
		cmd.setPart(part);
		cmd.setLocation((Rectangle) constraint);
		Command result = cmd;

		if ((request.getResizeDirection() & PositionConstants.NORTH_SOUTH) != 0) {
			Integer guidePos = (Integer) request.getExtendedData().get(
					SnapToGuides.KEY_HORIZONTAL_GUIDE);
			if (guidePos != null) {
				result = chainGuideAttachmentCommand(request, part, result,
						true);
			} else if (part.getHorizontalGuide() != null) {
				// SnapToGuides didn't provide a horizontal guide, but this part
				// is attached
				// to a horizontal guide. Now we check to see if the part is
				// attached to
				// the guide along the edge being resized. If that is the case,
				// we need to
				// detach the part from the guide; otherwise, we leave it alone.
				int alignment = part.getHorizontalGuide().getAlignment(part);
				int edgeBeingResized = 0;
				if ((request.getResizeDirection() & PositionConstants.NORTH) != 0)
					edgeBeingResized = -1;
				else
					edgeBeingResized = 1;
				if (alignment == edgeBeingResized)
					result = result.chain(new ChangeGuideCommand(part, true));
			}
		}

		if ((request.getResizeDirection() & PositionConstants.EAST_WEST) != 0) {
			Integer guidePos = (Integer) request.getExtendedData().get(
					SnapToGuides.KEY_VERTICAL_GUIDE);
			if (guidePos != null) {
				result = chainGuideAttachmentCommand(request, part, result,
						false);
			} else if (part.getVerticalGuide() != null) {
				int alignment = part.getVerticalGuide().getAlignment(part);
				int edgeBeingResized = 0;
				if ((request.getResizeDirection() & PositionConstants.WEST) != 0)
					edgeBeingResized = -1;
				else
					edgeBeingResized = 1;
				if (alignment == edgeBeingResized)
					result = result.chain(new ChangeGuideCommand(part, false));
			}
		}

		if (request.getType().equals(REQ_MOVE_CHILDREN)
				|| request.getType().equals(REQ_ALIGN_CHILDREN)) {
			result = chainGuideAttachmentCommand(request, part, result, true);
			result = chainGuideAttachmentCommand(request, part, result, false);
			result = chainGuideDetachmentCommand(request, part, result, true);
			result = chainGuideDetachmentCommand(request, part, result, false);
		}

		return result;
	}

	protected EditPolicy createChildEditPolicy(EditPart child) {
		SimulatorResizableEditPolicy editPolicy = new SimulatorResizableEditPolicy();
		editPolicy.setResizeDirections(getResizeDirections(child));
		return editPolicy;
	}

	protected int getResizeDirections(EditPart child) {
		return getResizeDirections(child.getModel().getClass());
	}

	private int getResizeDirections(Class modelClass) {
		if (LED.class.equals(modelClass)) {
			return PositionConstants.NONE;
		} else if (SimulatorLabel.class.equals(modelClass)) {
			return PositionConstants.EAST | PositionConstants.WEST;
		} else {
			return PositionConstants.NSEW;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editpolicies.LayoutEditPolicy#createSizeOnDropFeedback
	 * (org.eclipse.gef.requests.CreateRequest)
	 */
	protected IFigure createSizeOnDropFeedback(CreateRequest createRequest) {
		IFigure figure;

		if (createRequest.getNewObject() instanceof SimulatorFlowContainer)
			figure = new SimulatorFlowFeedbackFigure();
		else if (createRequest.getNewObject() instanceof SimulatorLabel)
			figure = new LabelFeedbackFigure();
		else {
			figure = new RectangleFigure();
			((RectangleFigure) figure).setXOR(true);
			((RectangleFigure) figure).setFill(true);
			figure.setBackgroundColor(SimulatorColorConstants.ghostFillColor);
			figure.setForegroundColor(ColorConstants.white);
		}

		addFeedback(figure);

		return figure;
	}

	protected SimulatorGuide findGuideAt(int pos, boolean horizontal) {
		RulerProvider provider = ((RulerProvider) getHost().getViewer()
				.getProperty(
						horizontal ? RulerProvider.PROPERTY_VERTICAL_RULER
								: RulerProvider.PROPERTY_HORIZONTAL_RULER));
		return (SimulatorGuide) provider.getGuideAt(pos);
	}

	/**
	 * Override to return the <code>Command</code> to perform an
	 * {@link RequestConstants#REQ_CLONE CLONE}. By default, <code>null</code>
	 * is returned.
	 * 
	 * @param request
	 *            the Clone Request
	 * @return A command to perform the Clone.
	 */
	protected Command getCloneCommand(ChangeBoundsRequest request) {
		CloneCommand clone = new CloneCommand();

		clone.setParent((SimulatorDiagram) getHost().getModel());

		Iterator i = request.getEditParts().iterator();
		GraphicalEditPart currPart = null;

		while (i.hasNext()) {
			currPart = (GraphicalEditPart) i.next();
			clone.addPart((SimulatorSubpart) currPart.getModel(),
					(Rectangle) getConstraintFor(request, currPart));
		}

		// Attach to horizontal guide, if one is given
		Integer guidePos = (Integer) request.getExtendedData().get(
				SnapToGuides.KEY_HORIZONTAL_GUIDE);
		if (guidePos != null) {
			int hAlignment = ((Integer) request.getExtendedData().get(
					SnapToGuides.KEY_HORIZONTAL_ANCHOR)).intValue();
			clone.setGuide(findGuideAt(guidePos.intValue(), true), hAlignment,
					true);
		}

		// Attach to vertical guide, if one is given
		guidePos = (Integer) request.getExtendedData().get(
				SnapToGuides.KEY_VERTICAL_GUIDE);
		if (guidePos != null) {
			int vAlignment = ((Integer) request.getExtendedData().get(
					SnapToGuides.KEY_VERTICAL_ANCHOR)).intValue();
			clone.setGuide(findGuideAt(guidePos.intValue(), false), vAlignment,
					false);
		}

		return clone;
	}

	protected Command getCreateCommand(CreateRequest request) {
		CreateCommand create = new CreateCommand();
		create.setParent((SimulatorDiagram) getHost().getModel());
		SimulatorSubpart newPart = (SimulatorSubpart) request.getNewObject();
		create.setChild(newPart);
		Rectangle constraint = (Rectangle) getConstraintFor(request);
		create.setLocation(constraint);
		create.setLabel(SimulatorMessages.SimulatorXYLayoutEditPolicy_CreateCommandLabelText);

		Command cmd = chainGuideAttachmentCommand(request, newPart, create,
				true);
		return chainGuideAttachmentCommand(request, newPart, cmd, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editpolicies.LayoutEditPolicy#getCreationFeedbackOffset
	 * (org.eclipse.gef.requests.CreateRequest)
	 */
	protected Insets getCreationFeedbackOffset(CreateRequest request) {
		if (request.getNewObject() instanceof LED)
			return new Insets(2, 0, 2, 0);
		return new Insets();
	}

	/**
	 * Returns the layer used for displaying feedback.
	 * 
	 * @return the feedback layer
	 */
	protected IFigure getFeedbackLayer() {
		return getLayer(LayerConstants.SCALED_FEEDBACK_LAYER);
	}

}
