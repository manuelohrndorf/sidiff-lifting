package org.sidiff.difference.technical.ui.wizard;

import org.eclipse.jface.wizard.Wizard;
import org.sidiff.common.emf.input.InputModels;
import org.sidiff.difference.technical.api.settings.DifferenceSettings;
import org.sidiff.difference.technical.ui.jobs.CreateTechnicalDifferenceJob;
import org.sidiff.difference.technical.ui.pages.BasicCompareSettingsPage;

public class CreateTechnicalDifferenceWizard extends Wizard {

	/**
	 * The {@link InputModels}
	 */
	private InputModels inputModels;

	/**
	 * The {@link DifferenceSettings}
	 */
	private DifferenceSettings settings;

	// ---------- UI Elements ----------

	/**
	 * The {@link BasicCompareSettingsPage}
	 */
	private BasicCompareSettingsPage basicCompareSettingsPage;

	// ---------- Constructor ----------

	public CreateTechnicalDifferenceWizard(InputModels inputModels) {
		this.setWindowTitle("New Symmetric Difference Wizard");
		this.inputModels = inputModels;
		this.settings = DifferenceSettings.defaultSettings(inputModels.getDocumentTypes());
	}

	// ---------- Wizard ----------

	@Override
	public void addPages() {
		basicCompareSettingsPage = new BasicCompareSettingsPage(inputModels, settings);
		addPage(basicCompareSettingsPage);
	}

	@Override
	public boolean canFinish() {
		return basicCompareSettingsPage.isPageComplete();
	}

	@Override
	public boolean performFinish() {
		new CreateTechnicalDifferenceJob(inputModels, settings).schedule();
		return true;
	}
}
