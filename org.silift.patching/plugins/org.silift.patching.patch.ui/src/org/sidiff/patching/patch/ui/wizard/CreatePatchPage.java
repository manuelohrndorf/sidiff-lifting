package org.sidiff.patching.patch.ui.wizard;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

public class CreatePatchPage extends WizardPage {

	public CreatePatchPage(String pageName, String title, ImageDescriptor titleImage) {
		super(pageName, title, titleImage);
	}

	@Override
	public void createControl(Composite parent) {
		// TODO: Settings
		setControl(parent);
		{
			GridLayout grid = new GridLayout(1, false);
			grid.marginWidth = 10;
			parent.setLayout(grid);
		}

		Group algorithmsGroup = new Group(parent, SWT.NONE);
		{
			GridLayout grid = new GridLayout(1, false);
			grid.marginWidth = 10;
			grid.marginHeight = 10;
			algorithmsGroup.setLayout(grid);

			GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
			algorithmsGroup.setLayoutData(data);
		}
		algorithmsGroup.setText("Algorithms");

		Label matchingLabel = new Label(algorithmsGroup, SWT.NONE);
		matchingLabel.setText("TODO: Settings!");
	}
}
