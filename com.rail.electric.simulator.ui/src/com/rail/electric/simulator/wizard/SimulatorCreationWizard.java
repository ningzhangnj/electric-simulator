package com.rail.electric.simulator.wizard;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

public class SimulatorCreationWizard extends Wizard implements INewWizard {
	private SimulatorWizardPage1 logicPage = null;
	private IStructuredSelection selection;
	private IWorkbench workbench;

	public void addPages() {
		logicPage = new SimulatorWizardPage1(workbench, selection);
		addPage(logicPage);
	}

	public void init(IWorkbench aWorkbench,
			IStructuredSelection currentSelection) {
		workbench = aWorkbench;
		selection = currentSelection;
	}

	public boolean performFinish() {
		return logicPage.finish();
	}

}
