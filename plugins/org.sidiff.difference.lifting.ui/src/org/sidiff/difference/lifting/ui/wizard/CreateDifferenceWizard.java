package org.sidiff.difference.lifting.ui.wizard;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.wizard.Wizard;
import org.sidiff.difference.lifting.api.settings.LiftingSettings;
import org.sidiff.difference.lifting.ui.jobs.CreateDifferenceJob;
import org.sidiff.difference.lifting.ui.pages.AdvancedCompareSettingsPage;
import org.sidiff.difference.lifting.ui.pages.BasicCompareSettingsPage;
import org.sidiff.matching.input.InputModels;

public class CreateDifferenceWizard extends Wizard {

	/**
	 * The {@link InputModels}
	 */
	private InputModels inputModels;
	
	/**
	 * The {@link LiftingSettings}
	 */
	private LiftingSettings settings;

	// ---------- UI Elements ----------

	/**
	 * The {@link BasicCompareSettingsPage}
	 */
	private BasicCompareSettingsPage basicCompareSettingsPage;
	
	/**
	 * The {@link AdvancedCompareSettingsPage}
	 */
	private AdvancedCompareSettingsPage advancedCompareSettingsPage;

	// ---------- Constructor ----------
	
	public CreateDifferenceWizard(IFile fileA, IFile fileB) {
		this.setWindowTitle("New Symmetric Difference Wizard");
		this.inputModels = new InputModels(fileA, fileB);
		this.settings = new LiftingSettings(inputModels.getDocumentTypes());
	}

	// ---------- Wizard ----------

	@Override
	public void addPages() {
		basicCompareSettingsPage = new BasicCompareSettingsPage("Basic Compare Settings Page",
				"Compare models with each other", inputModels, settings);
		addPage(basicCompareSettingsPage);

		advancedCompareSettingsPage = new AdvancedCompareSettingsPage("Advanced Compare Settings Page",
				"Compare models with each other", inputModels, settings, basicCompareSettingsPage);
		addPage(advancedCompareSettingsPage);
	}

	@Override
	public boolean canFinish() {
		return basicCompareSettingsPage.isPageComplete() && advancedCompareSettingsPage.isPageComplete();
	}

	@Override
	public boolean performFinish() {
		Job job = new CreateDifferenceJob(inputModels, settings);
		job.schedule();
		return true;
	}
}
