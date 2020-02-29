package org.sidiff.difference.technical.ui.pages;

import org.sidiff.common.emf.input.InputModels;
import org.sidiff.common.emf.input.ui.InputModelsWidget;
import org.sidiff.common.emf.settings.BaseSettingsItem;
import org.sidiff.common.extension.ui.widgets.ConfigurableExtensionWidget;
import org.sidiff.common.ui.pages.AbstractWizardPage;
import org.sidiff.difference.technical.api.settings.DifferenceSettings;
import org.sidiff.difference.technical.api.settings.DifferenceSettingsItem;
import org.sidiff.difference.technical.ui.internal.TechnicalDifferenceUiPlugin;
import org.sidiff.difference.technical.ui.widgets.DifferenceBuilderWidget;
import org.sidiff.difference.technical.ui.widgets.MatchingEngineWidget;
import org.sidiff.difference.technical.ui.widgets.ScopeWidget;
import org.sidiff.difference.technical.ui.widgets.ValidateModelsWidget;
import org.sidiff.integration.preferences.ui.widgets.SettingsSourceWidget;
import org.sidiff.matching.api.settings.MatchingSettingsItem;

public class BasicCompareSettingsPage extends AbstractWizardPage {

	/**
	 * The {@link DifferenceSettings}
	 */
	private DifferenceSettings settings;

	/**
	 * The {@link InputModels}
	 */
	private InputModels inputModels;

	/**
	 * The {@link MatchingEngineWidget} for choosing a matcher
	 */
	private MatchingEngineWidget matcherWidget;

	/**
	 * The {@link DifferenceBuilderWidget} for choosing a technical difference builder
	 */
	private DifferenceBuilderWidget builderWidget;

	// ---------- UI Elements ----------

	/**
	 * The {@link SettingsSourceWidget} for loading global and project specific settings.
	 */
	private SettingsSourceWidget settingsSourceWidget;

	/**
	 * The {@link ValidateModelsWidget} for toggling input model validation.
	 */
	private ValidateModelsWidget validateWidget;

	/**
	 * The {@link InputModelsWidget} for loading the models being compared.
	 */
	private InputModelsWidget sourceWidget;

	/**
	 * The {@link ScopeWidget} for determining the scope of the comparison.
	 */
	private ScopeWidget scopeWidget;

	// ---------- Constructor ----------

	public BasicCompareSettingsPage(InputModels inputModels, DifferenceSettings settings) {
		super("Basic Compare Settings Page", "Compare models with each other",
				TechnicalDifferenceUiPlugin.getImageDescriptor("icon.gif"));
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

		// Matcher:
		matcherWidget = new MatchingEngineWidget(inputModels, settings);
		matcherWidget.setDependency(settingsSourceWidget);
		addWidget(container, matcherWidget);
		ConfigurableExtensionWidget.addAllForWidget(container, matcherWidget, this::addWidget);

		// Technical Difference Builder:
		builderWidget = new DifferenceBuilderWidget(inputModels, settings);
		builderWidget.setDependency(settingsSourceWidget);
		addWidget(container, builderWidget);
	}

	@Override
	protected String getDefaultMessage() {
		return "Compare two versions of a model: origin -> changed";
	}
}
