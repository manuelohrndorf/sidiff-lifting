package org.sidiff.integration.preferences.ui.pages;

import org.sidiff.common.settings.AbstractSettings;
import org.sidiff.common.ui.pages.AbstractWizardPage;
import org.sidiff.integration.preferences.ui.widgets.SettingsSourceWidget;
import org.sidiff.matching.input.InputModels;

/**
 * 
 * @author Robert Müller
 *
 */
public class SettingsSourceWizardPage extends AbstractWizardPage {

	private SettingsSourceWidget settingsSourceWidget;

	private InputModels inputModels;
	private AbstractSettings settings;

	public SettingsSourceWizardPage(String pageName, String title, InputModels inputModels, AbstractSettings settings) {
		super(pageName, title);
		this.inputModels = inputModels;
		this.settings = settings;
	}

	@Override
	protected void createWidgets() {
		settingsSourceWidget = new SettingsSourceWidget(settings, inputModels);
		addWidget(container, settingsSourceWidget);
	}

	@Override
	protected String getDefaultMessage() {
		return "Select a source for the settings";
	}
}
