package org.sidiff.editrule.generator.serge.ui.wizards;

import org.sidiff.common.ui.pages.AbstractWizardPage;
import org.sidiff.editrule.generator.serge.settings.SergeSettings;
import org.sidiff.editrule.generator.ui.widgets.EditRuleGeneratorSettingsWidget;

/**
 * The "New" wizard page allows setting the container for the new file as well
 * as the file name. The page will only accept file name without the extension
 * OR with the extension that matches the expected one (mpe).
 */
public class SergeWizardPage1 extends AbstractWizardPage {

	private SergeSettings settings;
	private EditRuleGeneratorSettingsWidget configWidget;

	public SergeWizardPage1(SergeSettings settings) {
		super("SergeWizardPage1", "SERGe CPEO Generation Wizard");
		this.settings = settings;
	}

	@Override
	protected void createWidgets() {
		configWidget = new EditRuleGeneratorSettingsWidget();
		configWidget.setSettings(settings);
		addWidget(container, configWidget);
	}

	@Override
	protected String getDefaultMessage() {
		return "Please define general settings for the generation.";
	}
}