package org.sidiff.integration.preferences.ui.pages;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Status;
import org.sidiff.common.settings.AbstractSettings;
import org.sidiff.common.ui.pages.AbstractWizardPage;
import org.sidiff.integration.preferences.ui.Activator;
import org.sidiff.integration.preferences.ui.widgets.SettingsSourceWidget;
import org.sidiff.matching.input.InputModels;

/**
 * 
 * @author Robert Müller
 *
 */
public class SettingsSourceWizardPage extends AbstractWizardPage {

	private SettingsSourceWidget settingsSourceWidget;

	private IProject project;
	private String documentType;
	private AbstractSettings settings;

	public SettingsSourceWizardPage(String pageName, String title, InputModels inputModels, AbstractSettings settings) {
		super(pageName, title);

		for(IFile file : inputModels.getFiles()) {
			if(this.project == null) {
				this.project = file.getProject();
			} else if(!this.project.equals(file.getProject())) {
				Activator.getDefault().getLog().log(new Status(Status.WARNING, Activator.PLUGIN_ID,
						"Input models are not in the same project"));
			}
		}
		this.documentType = inputModels.getCharacteristicDocumentType();
		this.settings = settings;
	}

	@Override
	protected void createWidgets() {
		settingsSourceWidget = new SettingsSourceWidget(settings, project, documentType);
		addWidget(container, settingsSourceWidget);
	}

	@Override
	protected String getDefaultMessage() {
		return "Select a source for the settings";
	}
}
