package org.sidiff.profileapplicator.ui;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.sidiff.profileapplicator.settings.ProfileApplicatorSettings;

public class ApplyProfilesWizardPage0 extends WizardPage {

	
	private final ProfileApplicatorSettings settings;
	private ApplyProfilesWidget applyProfilesWidget = null;
	
	protected ApplyProfilesWizardPage0(ProfileApplicatorSettings settings) {
		super("Main Page");
		this.settings = settings;
		setTitle("ProfileApplicator configuration");
		setDescription("");
	}


	@Override
	public void createControl(Composite parent) {
		//parent.setLayout(new FillLayout());
		Composite comp = new Composite(parent, SWT.NONE);
		setControl(comp);
		comp.setLayout(new FillLayout());
		applyProfilesWidget = new ApplyProfilesWidget(settings);
		applyProfilesWidget.createControl(comp);
		applyProfilesWidget.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				updatePageState();
			}
		});
		updatePageState();
	}

	private void updatePageState() {
		boolean valid=applyProfilesWidget.validate();
		setErrorMessage(applyProfilesWidget.getValidationMessage());
		setPageComplete(valid);
	}
}
