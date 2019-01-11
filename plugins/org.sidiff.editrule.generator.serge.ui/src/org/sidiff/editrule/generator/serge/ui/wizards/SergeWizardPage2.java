package org.sidiff.editrule.generator.serge.ui.wizards;

import org.sidiff.common.ui.pages.AbstractWizardPage;
import org.sidiff.editrule.generator.serge.settings.SergeSettings;
import org.sidiff.editrule.generator.serge.ui.widgets.SergeConfigSerializationWidget;
import org.sidiff.editrule.generator.serge.ui.widgets.SergeTransformationSettingsWidget;

/**
 * The "New" wizard page allows setting the container for the new file as well
 * as the file name. The page will only accept file name without the extension
 * OR with the extension that matches the expected one (mpe).
 */
public class SergeWizardPage2 extends AbstractWizardPage {
	
	private SergeSettings settings;

	public SergeWizardPage2(SergeSettings settings) {
		super("SergeWizardPage2", "SERGe CPEO Generation Wizard");
		this.settings = settings;
	}

	@Override
	protected void createWidgets() {
		SergeConfigSerializationWidget configSerializationWidget = new SergeConfigSerializationWidget();
		configSerializationWidget.setSettings(settings);
		addWidget(container, configSerializationWidget, NUM_COLUMNS/2);

		SergeTransformationSettingsWidget transformationSettingsWidget = new SergeTransformationSettingsWidget();
		transformationSettingsWidget.setSettings(settings);
		addWidget(container, transformationSettingsWidget, NUM_COLUMNS/2);
	}

	@Override
	protected String getDefaultMessage() {
		return "Please define advanced settings for the generation.";
	}
}