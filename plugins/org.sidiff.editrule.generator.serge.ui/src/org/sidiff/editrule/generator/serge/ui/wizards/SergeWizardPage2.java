package org.sidiff.editrule.generator.serge.ui.wizards;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
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
		Composite gridContainer = new Composite(super.container, SWT.NONE);
		gridContainer.setLayout(new GridLayout(2, false));

		SergeConfigSerializationWidget configSerializationWidget = new SergeConfigSerializationWidget();
		configSerializationWidget.setSettings(settings);
		addWidget(gridContainer, configSerializationWidget);

		SergeTransformationSettingsWidget transformationSettingsWidget = new SergeTransformationSettingsWidget();
		transformationSettingsWidget.setSettings(settings);
		addWidget(gridContainer, transformationSettingsWidget);
	}

	@Override
	protected String getDefaultMessage() {
		return "Please define advanced settings for the generation.";
	}
}