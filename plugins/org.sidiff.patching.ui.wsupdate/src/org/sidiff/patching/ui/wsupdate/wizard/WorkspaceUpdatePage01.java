package org.sidiff.patching.ui.wsupdate.wizard;

import org.sidiff.common.emf.settings.BaseSettingsItem;
import org.sidiff.common.ui.pages.AbstractWizardPage;
import org.sidiff.difference.lifting.api.settings.LiftingSettingsItem;
import org.sidiff.difference.lifting.ui.widgets.RulebaseWidget;
import org.sidiff.difference.technical.api.settings.DifferenceSettingsItem;
import org.sidiff.difference.technical.ui.widgets.ScopeWidget;
import org.sidiff.integration.preferences.ui.widgets.SettingsSourceWidget;
import org.sidiff.matching.api.settings.MatchingSettingsItem;
import org.sidiff.patching.api.settings.PatchingSettings;
import org.sidiff.patching.api.settings.PatchingSettingsItem;
import org.sidiff.patching.ui.widgets.ValidationModeWidget;
import org.sidiff.patching.ui.wsupdate.internal.PatchingWsUpdateUiPlugin;
import org.sidiff.patching.ui.wsupdate.util.WSUModels;
import org.sidiff.patching.ui.wsupdate.widgets.WSUModelsWidget;

public class WorkspaceUpdatePage01 extends AbstractWizardPage {

	private SettingsSourceWidget settingsSourceWidget;
	private WSUModelsWidget mergeModelsWidget;
	private ValidationModeWidget validationWidget;
	private ScopeWidget scopeWidget;
	private RulebaseWidget rulebaseWidget;

	private WSUModels mergeModels;
	private PatchingSettings settings;

	public WorkspaceUpdatePage01(WSUModels mergeModels, PatchingSettings settings) {
		super("WorkspaceUpdatePage01", "Workspace update", PatchingWsUpdateUiPlugin.getImageDescriptor("icon.png"));
		this.mergeModels = mergeModels;
		this.settings = settings;
	}

	@Override
	protected void createWidgets() {
		// Settings Source:
		settingsSourceWidget = new SettingsSourceWidget(settings, mergeModels);
		settingsSourceWidget.addConsideredSettings(BaseSettingsItem.values());
		settingsSourceWidget.addConsideredSettings(MatchingSettingsItem.values());
		settingsSourceWidget.addConsideredSettings(DifferenceSettingsItem.TECH_BUILDER);
		settingsSourceWidget.addConsideredSettings(LiftingSettingsItem.values());
		settingsSourceWidget.addConsideredSettings(
				PatchingSettingsItem.RELIABILITY,
				PatchingSettingsItem.VALIDATION_MODE);
		addWidget(container, settingsSourceWidget);

		// Models:
		mergeModelsWidget = new WSUModelsWidget(mergeModels);
		addWidget(container, mergeModelsWidget);

		// Comparison mode:
		scopeWidget = new ScopeWidget();
		scopeWidget.setSettings(this.settings);
		scopeWidget.setDependency(settingsSourceWidget);
		addWidget(container, scopeWidget, NUM_COLUMNS/2);

		// Validation mode:
		validationWidget = new ValidationModeWidget();
		validationWidget.setSettings(this.settings);
		validationWidget.setDependency(settingsSourceWidget);
		addWidget(container, validationWidget, NUM_COLUMNS/2);

		// Rulebases:
		rulebaseWidget = new RulebaseWidget(mergeModels.getBaseTheirsModels(), settings);
		rulebaseWidget.setDependency(settingsSourceWidget);
		addWidget(container, rulebaseWidget);
	}

	// internal access method for other wizard page
	SettingsSourceWidget getSettingsSourceWidget() {
		return settingsSourceWidget;
	}

	public WSUModels getMergeModels() {
		return mergeModelsWidget.getMergeModels();
	}

	@Override
	protected String getDefaultMessage() {
		return "Propagates parallel changes by other developers, which were checked-in into a common repository, to the local workspace.";
	}
}
