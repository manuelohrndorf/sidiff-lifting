package org.sidiff.difference.technical.ui.wizard;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.wizard.Wizard;
import org.sidiff.difference.technical.api.settings.DifferenceSettings;
import org.sidiff.difference.technical.ui.jobs.CreateDifferenceJob;
import org.sidiff.difference.technical.ui.pages.BasicCompareSettingsPage;
import org.sidiff.matching.input.InputModels;

public class CreateDifferenceWizard extends Wizard {

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
	
	public CreateDifferenceWizard(IFile fileA, IFile fileB) {
		
		this.setWindowTitle("New Symmetric Difference Wizard");

		this.inputModels = new InputModels(fileA, fileB);
		this.settings = new DifferenceSettings();
	}

	// ---------- Wizard ----------
	
	@Override
	public void addPages() {
		basicCompareSettingsPage = new BasicCompareSettingsPage("Basic Compare Settings Page",
				"Compare models with each other", inputModels, settings);
		addPage(basicCompareSettingsPage);
	}

	@Override
	public boolean canFinish() {
		return basicCompareSettingsPage.isPageComplete();
	}

	@Override
	public boolean performFinish() {
		Job job = new CreateDifferenceJob(inputModels, settings);
		job.schedule();
		return true;
	}
}
