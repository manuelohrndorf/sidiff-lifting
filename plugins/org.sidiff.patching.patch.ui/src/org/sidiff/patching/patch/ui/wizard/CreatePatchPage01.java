package org.sidiff.patching.patch.ui.wizard;

import org.eclipse.jface.resource.ImageDescriptor;
import org.sidiff.common.emf.input.InputModels;
import org.sidiff.common.emf.input.ui.InputModelsWidget;
import org.sidiff.common.settings.BaseSettingsItem;
import org.sidiff.common.ui.pages.AbstractWizardPage;
import org.sidiff.difference.lifting.api.settings.LiftingSettings;
import org.sidiff.difference.lifting.api.settings.LiftingSettingsItem;
import org.sidiff.difference.lifting.ui.widgets.RulebaseWidget;
import org.sidiff.difference.technical.api.settings.DifferenceSettingsItem;
import org.sidiff.difference.technical.ui.widgets.ScopeWidget;
import org.sidiff.difference.technical.ui.widgets.ValidateModelsWidget;
import org.sidiff.integration.preferences.ui.widgets.SettingsSourceWidget;
import org.sidiff.matching.api.settings.MatchingSettingsItem;
import org.sidiff.patching.api.settings.PatchingSettingsItem;
import org.sidiff.patching.patch.ui.widgets.EditRuleMatchWidget;

public class CreatePatchPage01 extends AbstractWizardPage {

	private final String mode;

	private SettingsSourceWidget settingsSourceWidget;
	private org.sidiff.common.emf.input.ui.InputModelsWidget sourceWidget;
	private ValidateModelsWidget validateWidget;
	private ScopeWidget scopeWidget;
	private EditRuleMatchWidget erMatchWidget;
	private RulebaseWidget rulebaseWidget;

	private InputModels inputModels;
	private LiftingSettings settings;

	public CreatePatchPage01(InputModels inputModels,
			String pageName, String title, ImageDescriptor titleImage, LiftingSettings settings, Mode mode) {
		super(pageName, title, titleImage);

		this.inputModels = inputModels;
		this.settings = settings;

		if(mode == Mode.PATCH) {
			this.mode = "Patch";
		} else {
			this.mode = "Asymmetric Difference";
		}
	}

	@Override
	protected void createWidgets() {
		// Settings Source:
		settingsSourceWidget = new SettingsSourceWidget(this.settings, inputModels);
		settingsSourceWidget.addConsideredSettings(BaseSettingsItem.values());
		settingsSourceWidget.addConsideredSettings(MatchingSettingsItem.values());
		settingsSourceWidget.addConsideredSettings(DifferenceSettingsItem.TECH_BUILDER);
		settingsSourceWidget.addConsideredSettings(LiftingSettingsItem.values());
		settingsSourceWidget.addConsideredSettings(PatchingSettingsItem.SYMBOLIC_LINK_HANDLER);
		addWidget(container, settingsSourceWidget);

		// Models:
		sourceWidget = new InputModelsWidget(inputModels);
		sourceWidget.setArrowLabel(mode + " Direction");
		addWidget(container, sourceWidget);

		// Model Validation:
		validateWidget = new ValidateModelsWidget();
		validateWidget.setSettings(settings);
		validateWidget.setDependency(settingsSourceWidget);
		addWidget(container, validateWidget, NUM_COLUMNS/3);

		// Comparison mode:
		scopeWidget = new ScopeWidget();
		scopeWidget.setSettings(this.settings);
		scopeWidget.setDependency(settingsSourceWidget);
		addWidget(container, scopeWidget, NUM_COLUMNS/3);

		// Edit-Rule Matches:
		erMatchWidget = new EditRuleMatchWidget();
		erMatchWidget.setSettings(settings);
		erMatchWidget.setDependency(settingsSourceWidget);
		addWidget(container, erMatchWidget, NUM_COLUMNS/3);

		// Rulebases:
		rulebaseWidget = new RulebaseWidget(inputModels);
		rulebaseWidget.setSettings(this.settings);
		rulebaseWidget.setDependency(settingsSourceWidget);
		addWidget(container, rulebaseWidget);
	}

	@Override
	protected String getDefaultMessage() {
		return "Create a " + mode + " from the changes between the models: origin -> changed";
	}

	// internal access method for other wizard page
	SettingsSourceWidget getSettingsSourceWidget() {
		return settingsSourceWidget;
	}
}
