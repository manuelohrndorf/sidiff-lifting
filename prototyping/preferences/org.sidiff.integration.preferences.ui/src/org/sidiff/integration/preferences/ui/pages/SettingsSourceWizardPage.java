package org.sidiff.integration.preferences.ui.pages;

import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.sidiff.common.settings.AbstractSettings;
import org.sidiff.common.ui.pages.AbstractWizardPage;
import org.sidiff.integration.preferences.PreferencesPlugin;
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
	private Set<String> documentTypes;
	private AbstractSettings settings;

	public SettingsSourceWizardPage(String pageName, String title, InputModels inputModels, AbstractSettings settings) {
		super(pageName, title);

		for(IFile file : inputModels.getFiles()) {
			if(this.project == null) {
				this.project = file.getProject();
			} else if(!this.project.equals(file.getProject())) {
				PreferencesPlugin.logWarning("Input models are not in the same project.");
			}
		}
		this.documentTypes = inputModels.getDocumentTypes();
		this.settings = settings;
	}

	@Override
	protected void createWidgets() {
		settingsSourceWidget = new SettingsSourceWidget(settings, project, documentTypes);
		addWidget(container, settingsSourceWidget);
	}

	@Override
	protected String getDefaultMessage() {
		return "Select a source for the settings";
	}
}
