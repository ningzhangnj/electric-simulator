package com.rail.electric.simulator.actions;

import org.eclipse.draw2d.PrintFigureOperation;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.rail.electric.simulator.SimulatorMessages;

public class PrintModeDialog extends Dialog {

	private Button tile, fitPage, fitWidth, fitHeight;

	public PrintModeDialog(Shell shell) {
		super(shell);
	}

	protected void cancelPressed() {
		setReturnCode(-1);
		close();
	}

	protected void configureShell(Shell newShell) {
		newShell.setText(SimulatorMessages.PrintDialog_Title);
		super.configureShell(newShell);
	}

	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);

		tile = new Button(composite, SWT.RADIO);
		tile.setText(SimulatorMessages.PrintDialog_Tile);
		tile.setSelection(true);

		fitPage = new Button(composite, SWT.RADIO);
		fitPage.setText(SimulatorMessages.PrintDialog_Page);

		fitWidth = new Button(composite, SWT.RADIO);
		fitWidth.setText(SimulatorMessages.PrintDialog_Width);

		fitHeight = new Button(composite, SWT.RADIO);
		fitHeight.setText(SimulatorMessages.PrintDialog_Height);

		return composite;
	}

	protected void okPressed() {
		int returnCode = -1;
		if (tile.getSelection())
			returnCode = PrintFigureOperation.TILE;
		else if (fitPage.getSelection())
			returnCode = PrintFigureOperation.FIT_PAGE;
		else if (fitHeight.getSelection())
			returnCode = PrintFigureOperation.FIT_HEIGHT;
		else if (fitWidth.getSelection())
			returnCode = PrintFigureOperation.FIT_WIDTH;
		setReturnCode(returnCode);
		close();
	}
}
