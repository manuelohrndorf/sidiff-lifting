package org.sidiff.patching.ui.wizard;

import org.eclipse.emf.common.util.URI;
import org.sidiff.common.settings.BaseSettingsItem;
import org.sidiff.common.ui.pages.AbstractWizardPage;
import org.sidiff.difference.technical.ui.widgets.ScopeWidget;
import org.sidiff.integration.preferences.ui.widgets.SettingsSourceWidget;
import org.sidiff.matching.api.settings.MatchingSettingsItem;
import org.sidiff.matching.input.InputModels;
import org.sidiff.patching.api.settings.PatchingSettings;
import org.sidiff.patching.api.settings.PatchingSettingsItem;
import org.sidiff.patching.ui.Activator;
import org.sidiff.patching.ui.widgets.TargetModelWidget;
import org.sidiff.patching.ui.widgets.ValidationModeWidget;

public class ApplyPatchPage01 extends AbstractWizardPage {

	private SettingsSourceWidget settingsSourceWidget;
	private TargetModelWidget targetWidget;
	private ScopeWidget scopeWidget;
	private ValidationModeWidget validationWidget;

	private InputModels inputModels;
	private PatchingSettings settings;

	public ApplyPatchPage01(InputModels inputModels, String title, PatchingSettings settings) {
		super("ApplyPatchPage01", title, Activator.getImageDescriptor("icon.png"));
		this.inputModels = inputModels;
		this.settings = settings;
	}

	@Override
	protected void createWidgets() {
		// Settings Source:
		// Note that InputModels is not used here, because it is not compatible with Archive-URIs (i.e. Patch files)
		settingsSourceWidget = new SettingsSourceWidget(settings, inputModels);
		settingsSourceWidget.addConsideredSettings(BaseSettingsItem.values());
		settingsSourceWidget.addConsideredSettings(MatchingSettingsItem.values());
		settingsSourceWidget.addConsideredSettings(
				PatchingSettingsItem.VALIDATION_MODE,
				PatchingSettingsItem.RELIABILITY,
				PatchingSettingsItem.SYMBOLIC_LINK_HANDLER);
		addWidget(container, settingsSourceWidget);

		// Target model:
		targetWidget = new TargetModelWidget();
		addWidget(container, targetWidget);

		// Comparison mode:
		scopeWidget = new ScopeWidget();
		scopeWidget.setSettings(this.settings);
		scopeWidget.setDependency(settingsSourceWidget);
		addWidget(container, scopeWidget);

		// Validation:
		validationWidget = new ValidationModeWidget();
		validationWidget.setSettings(this.settings);
		validationWidget.setDependency(settingsSourceWidget);
		addWidget(container, validationWidget);
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
