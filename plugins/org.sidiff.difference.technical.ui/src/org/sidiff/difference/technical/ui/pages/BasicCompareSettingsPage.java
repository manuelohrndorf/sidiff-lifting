package org.sidiff.difference.technical.ui.pages;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Group;
import org.sidiff.common.extension.ui.widgets.ConfigurableExtensionWidget;
import org.sidiff.common.settings.BaseSettingsItem;
import org.sidiff.common.ui.pages.AbstractWizardPage;
import org.sidiff.difference.technical.api.settings.DifferenceSettings;
import org.sidiff.difference.technical.api.settings.DifferenceSettingsItem;
import org.sidiff.difference.technical.ui.widgets.DifferenceBuilderWidget;
import org.sidiff.difference.technical.ui.widgets.InputModelsWidget;
import org.sidiff.difference.technical.ui.widgets.MatchingEngineWidget;
import org.sidiff.difference.technical.ui.widgets.ScopeWidget;
import org.sidiff.difference.technical.ui.widgets.ValidateModelsWidget;
import org.sidiff.integration.preferences.ui.widgets.SettingsSourceWidget;
import org.sidiff.matching.api.settings.MatchingSettingsItem;
import org.sidiff.matching.input.InputModels;

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

	public BasicCompareSettingsPage(String pageName, String title, InputModels inputModels, DifferenceSettings settings) {
		this(pageName, title, null, inputModels, settings);
	}

	public BasicCompareSettingsPage(String pageName, String title, ImageDescriptor titleImage,
			InputModels inputModels, DifferenceSettings settings) {
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
		addWidget(container, settingsSourceWidget);

		// Models:
		sourceWidget = new InputModelsWidget(inputModels, "Comparison Direction");
		addWidget(container, sourceWidget);

		// Model Validation:
		validateWidget = new ValidateModelsWidget();
		validateWidget.setSettings(settings);
		validateWidget.setDependency(settingsSourceWidget);
		addWidget(container, validateWidget);

		// Comparison mode:
		scopeWidget = new ScopeWidget();
		scopeWidget.setSettings(settings);
		scopeWidget.setDependency(settingsSourceWidget);
		addWidget(container, scopeWidget);

		// Algorithms:
		Group algorithmsGroup = new Group(container, SWT.NONE);
		{
			GridLayout grid = new GridLayout(1, false);
			grid.marginWidth = 10;
			grid.marginHeight = 10;
			algorithmsGroup.setLayout(grid);

			GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
			algorithmsGroup.setLayoutData(data);
			algorithmsGroup.setText("Algorithms:");
		}

		// Matcher:
		matcherWidget = new MatchingEngineWidget(inputModels);
		matcherWidget.setSettings(settings);
		matcherWidget.setDependency(settingsSourceWidget);
		addWidget(algorithmsGroup, matcherWidget);
		ConfigurableExtensionWidget.addAllForWidget(algorithmsGroup, matcherWidget, this::addWidget);

		// Technical Difference Builder:
		builderWidget = new DifferenceBuilderWidget(inputModels);
		builderWidget.setSettings(settings);
		builderWidget.setDependency(settingsSourceWidget);
		addWidget(algorithmsGroup, builderWidget);
	}

	@Override
	protected String getDefaultMessage() {
		return "Compare two versions of a model: origin -> changed";
	}
}
