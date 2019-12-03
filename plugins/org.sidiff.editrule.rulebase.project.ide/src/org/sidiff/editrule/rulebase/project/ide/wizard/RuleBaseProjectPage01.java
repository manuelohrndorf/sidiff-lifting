package org.sidiff.editrule.rulebase.project.ide.wizard;

import org.sidiff.common.ui.pages.AbstractWizardPage;
import org.sidiff.editrule.generator.settings.EditRuleGenerationSettings;
import org.sidiff.editrule.generator.ui.widgets.EditRuleGeneratorChooserWidget;
import org.sidiff.editrule.generator.ui.widgets.EditRuleGeneratorSettingsWidget;
import org.sidiff.editrule.generator.ui.widgets.EditRuleGeneratorWidget;
import org.sidiff.editrule.rulebase.project.ide.internal.Activator;

public class RuleBaseProjectPage01 extends AbstractWizardPage {

	private EditRuleGenerationSettings settings;

	public RuleBaseProjectPage01(EditRuleGenerationSettings settings) {
		super("RuleBaseProjectPage1", "Advanced RuleBase Project Settings");
		this.setImageDescriptor(Activator.imageDescriptorFromPlugin(Activator.getPluginId(), "16x16_rulebase.gif"));
		this.settings = settings;
	}

	@Override
	protected void createWidgets() {
		// Chooser Widget
		EditRuleGeneratorChooserWidget chooserWidget = new EditRuleGeneratorChooserWidget();
		addWidget(container, chooserWidget);

		// EditRule Generator
		EditRuleGeneratorWidget generatorWidget = new EditRuleGeneratorWidget();
		generatorWidget.setDependency(chooserWidget);
		generatorWidget.setSettings(settings);
		addWidget(container, generatorWidget);

		// EditRule Generator Settings:
		EditRuleGeneratorSettingsWidget generatorSettingsWidget = new EditRuleGeneratorSettingsWidget();
		generatorSettingsWidget.setDependency(chooserWidget);
		generatorSettingsWidget.setSettings(settings);
		addWidget(container, generatorSettingsWidget);
	}

	@Override
	protected String getDefaultMessage() {
		return "Advanced RuleBase Project Settings";
	}
}
