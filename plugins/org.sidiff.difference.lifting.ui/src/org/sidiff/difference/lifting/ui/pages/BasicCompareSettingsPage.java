package org.sidiff.difference.lifting.ui.pages;

import org.eclipse.jface.resource.ImageDescriptor;
import org.sidiff.common.emf.input.InputModels;
import org.sidiff.common.emf.input.ui.InputModelsWidget;
import org.sidiff.common.emf.settings.BaseSettingsItem;
import org.sidiff.common.ui.pages.AbstractWizardPage;
import org.sidiff.difference.lifting.api.settings.LiftingSettings;
import org.sidiff.difference.lifting.api.settings.LiftingSettingsItem;
import org.sidiff.difference.lifting.ui.internal.DifferenceLiftingUiPlugin;
import org.sidiff.difference.lifting.ui.widgets.RulebaseWidget;
import org.sidiff.difference.technical.api.settings.DifferenceSettingsItem;
import org.sidiff.difference.technical.ui.widgets.ScopeWidget;
import org.sidiff.difference.technical.ui.widgets.ValidateModelsWidget;
import org.sidiff.integration.preferences.ui.widgets.SettingsSourceWidget;
import org.sidiff.matching.api.settings.MatchingSettingsItem;

public class BasicCompareSettingsPage extends AbstractWizardPage {

	/**
	 * The {@link LiftingSettings}
	 */
	private LiftingSettings settings;
	
	/**
	 * The {@link InputModels}
	 */
	private InputModels inputModels;
	
	// ---------- UI Elements ----------
	
	/**
	 * The {@link SettingsSourceWidget} for loading global and project specific settings.
	 */
	private SettingsSourceWidget settingsSourceWidget;
	
	/**
	 * The {@link InputModelsWidget} for loading the models being compared.
	 */
	private InputModelsWidget sourceWidget;

	/**
	 * The {@link ValidateModelsWidget} for toggling input model validation.
	 */
	private ValidateModelsWidget validateWidget;

	/**
	 * The {@link ScopeWidget} for determining the scope of the comparison.
	 */
	private ScopeWidget scopeWidget;
	
	/**
	 * The {@link RulebaseWidget} for choosing the rule bases used by the lifting engine.
	 */
	private RulebaseWidget rulebaseWidget;
	
	
	// ---------- Constructor ----------

	public BasicCompareSettingsPage(String pageName, String title, InputModels inputModels, LiftingSettings settings) {
		this(pageName, title, DifferenceLiftingUiPlugin.getImageDescriptor("icon.png"), inputModels, settings);
	}

	public BasicCompareSettingsPage(String pageName, String title, ImageDescriptor titleImage,
			InputModels inputModels, LiftingSettings settings) {
		super(pageName, title, titleImage);
		this.inputModels = inputModels;
		this.settings = settings;
	}

	// ---------- AbstractWizardPage ----------

	@Override
	protected void createWidgets() {
		// Settings Source:
		settingsSourceWidget = new SettingsSourceWidget(settings, inputModels);
		settingsSourceWidget.addConsideredSettings(BaseSettingsItem.values());
		settingsSourceWidget.addConsideredSettings(MatchingSettingsItem.values());
		settingsSourceWidget.addConsideredSettings(DifferenceSettingsItem.TECH_BUILDER);
		settingsSourceWidget.addConsideredSettings(LiftingSettingsItem.values());
		addWidget(container, settingsSourceWidget);

		// Models:
		sourceWidget = new InputModelsWidget(inputModels);
		sourceWidget.setArrowLabel("Comparison Direction");
		addWidget(container, sourceWidget);

		// Model Validation:
		validateWidget = new ValidateModelsWidget();
		validateWidget.setSettings(settings);
		validateWidget.setDependency(settingsSourceWidget);
		addWidget(container, validateWidget, NUM_COLUMNS/2);

		// Comparison mode:
		scopeWidget = new ScopeWidget();
		scopeWidget.setSettings(settings);
		scopeWidget.setDependency(settingsSourceWidget);
		addWidget(container, scopeWidget, NUM_COLUMNS/2);

		// Rulebases:
		rulebaseWidget = new RulebaseWidget(inputModels, settings);
		rulebaseWidget.setDependency(settingsSourceWidget);
		addWidget(container, rulebaseWidget);
	}

	@Override
	protected String getDefaultMessage() {
		return "Compare two versions of a model: origin -> changed";
	}

	// internal access method for other wizard page
	SettingsSourceWidget getSettingsSourceWidget() {
		return settingsSourceWidget;
	}
}
