package org.sidiff.patching.ui.wizard;

import org.eclipse.emf.common.util.URI;
import org.sidiff.common.emf.input.InputModels;
import org.sidiff.common.emf.settings.BaseSettingsItem;
import org.sidiff.common.ui.pages.AbstractWizardPage;
import org.sidiff.difference.technical.ui.widgets.ScopeWidget;
import org.sidiff.integration.preferences.ui.widgets.SettingsSourceWidget;
import org.sidiff.matching.api.settings.MatchingSettingsItem;
import org.sidiff.patching.api.settings.PatchingSettings;
import org.sidiff.patching.api.settings.PatchingSettingsItem;
import org.sidiff.patching.ui.internal.PatchingUiPlugin;
import org.sidiff.patching.ui.widgets.TargetModelWidget;
import org.sidiff.patching.ui.widgets.ValidationModeWidget;

public class ApplyAsymmetricDifferencePage01 extends AbstractWizardPage {

	private SettingsSourceWidget settingsSourceWidget;
	private TargetModelWidget targetWidget;
	private ScopeWidget scopeWidget;
	private ValidationModeWidget validationWidget;

	private InputModels inputModels;
	private PatchingSettings settings;

	public ApplyAsymmetricDifferencePage01(InputModels inputModels, String title, PatchingSettings settings) {
		super("ApplyAsymmetricDifferencePage01", title, PatchingUiPlugin.getImageDescriptor("icon.png"));
		this.settings = settings;
		this.inputModels = inputModels;
	}

	@Override
	protected void createWidgets() {
		// Settings Source:
		settingsSourceWidget = new SettingsSourceWidget(settings, inputModels);
		settingsSourceWidget.addConsideredSettings(BaseSettingsItem.values());
		settingsSourceWidget.addConsideredSettings(MatchingSettingsItem.values());
		settingsSourceWidget.addConsideredSettings(
				PatchingSettingsItem.VALIDATION_MODE,
				PatchingSettingsItem.RELIABILITY);
		addWidget(container, settingsSourceWidget);

		// Target model:
		targetWidget = new TargetModelWidget();
		addWidget(container, targetWidget);

		// Comparison mode:
		scopeWidget = new ScopeWidget();
		scopeWidget.setSettings(this.settings);
		scopeWidget.setDependency(settingsSourceWidget);
		addWidget(container, scopeWidget, NUM_COLUMNS/2);

		// Validation:
		validationWidget = new ValidationModeWidget();
		validationWidget.setSettings(this.settings);
		validationWidget.setDependency(settingsSourceWidget);
		addWidget(container, validationWidget, NUM_COLUMNS/2);
	}

	public URI getTargetModel() {
		return targetWidget.getSelectedModel();
	}

	// internal access method for other wizard page
	SettingsSourceWidget getSettingsSourceWidget() {
		return settingsSourceWidget;
	}

	@Override
	protected String getDefaultMessage() {
		return "Apply a patch to a model";
	}	
}
