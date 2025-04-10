package org.sidiff.difference.lifting.ui.wizard;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.wizard.Wizard;
import org.sidiff.common.emf.input.InputModels;
import org.sidiff.difference.lifting.api.settings.LiftingSettings;
import org.sidiff.difference.lifting.ui.jobs.CreateLiftedDifferenceJob;
import org.sidiff.difference.lifting.ui.pages.AdvancedCompareSettingsPage;
import org.sidiff.difference.lifting.ui.pages.BasicCompareSettingsPage;

public class CreateLiftedDifferenceWizard extends Wizard {

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
	
	public CreateLiftedDifferenceWizard(InputModels inputModels) {
		this.setWindowTitle("New Symmetric Difference Wizard");
		Assert.isLegal(inputModels != null && inputModels.getFiles().size() == 2,
				"Invalid InputModels, exactly two files are required");
		this.inputModels = inputModels;
		this.settings = LiftingSettings.defaultSettings(inputModels.getDocumentTypes());
	}

	// ---------- Wizard ----------

	@Override
	public void addPages() {
		basicCompareSettingsPage = new BasicCompareSettingsPage(inputModels, settings);
		addPage(basicCompareSettingsPage);

		advancedCompareSettingsPage = new AdvancedCompareSettingsPage(inputModels, settings, basicCompareSettingsPage);
		addPage(advancedCompareSettingsPage);
	}

	@Override
	public boolean canFinish() {
		return basicCompareSettingsPage.isPageComplete() && advancedCompareSettingsPage.isPageComplete();
	}

	@Override
	public boolean performFinish() {
		new CreateLiftedDifferenceJob(inputModels, settings).schedule();
		return true;
	}
}
