package com.rail.electric.simulator.dnd;

import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.Request;
import org.eclipse.gef.dnd.AbstractTransferDropTargetListener;
import org.eclipse.swt.dnd.Transfer;

import com.rail.electric.simulator.policies.NativeDropRequest;

public class TextTransferDropTargetListener extends
		AbstractTransferDropTargetListener {

	public TextTransferDropTargetListener(EditPartViewer viewer, Transfer xfer) {
		super(viewer, xfer);
	}

	protected Request createTargetRequest() {
		return new NativeDropRequest();
	}

	protected NativeDropRequest getNativeDropRequest() {
		return (NativeDropRequest) getTargetRequest();
	}

	protected void updateTargetRequest() {
		getNativeDropRequest().setData(getCurrentEvent().data);
	}

}
