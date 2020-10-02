package org.sidiff.difference.lifting.ui.pages;

import org.sidiff.common.emf.input.InputModels;
import org.sidiff.common.extension.ui.widgets.ConfigurableExtensionWidget;
import org.sidiff.common.ui.pages.AbstractWizardPage;
import org.sidiff.difference.lifting.api.settings.LiftingSettings;
import org.sidiff.difference.lifting.ui.internal.DifferenceLiftingUiPlugin;
import org.sidiff.difference.lifting.ui.widgets.RecognitionEngineModeWidget;
import org.sidiff.difference.lifting.ui.widgets.RecognitionRuleSorterWidget;
import org.sidiff.difference.technical.ui.widgets.TechnicalDifferenceBuilderWidget;
import org.sidiff.difference.technical.ui.widgets.MatchingEngineWidget;

public class AdvancedCompareSettingsPage extends AbstractWizardPage {

	/**
	 * The {@link LiftingSettings}
	 */
	private LiftingSettings settings;

	/**
	 * The {@link InputModels}
	 */
	private InputModels inputModels;

	/**
	 * The {@link BasicCompareSettingsPage}
	 */
	private BasicCompareSettingsPage basicCompareSettingsPage;

	// ---------- UI Elements ----------

	/**
	 * The {@link RecognitionEngineWidget} determining the recognition mode
	 */
	private RecognitionEngineModeWidget recognitionWidget;

	/**
	 * The {@link MatchingEngineWidget} for choosing a matcher
	 */
	private MatchingEngineWidget matcherWidget;

	/**
	 * The {@link DifferenceBuilderWidget} for choosing a technical difference builder
	 */
	private TechnicalDifferenceBuilderWidget builderWidget;

	/**
	 * The {@link RecognitionEngineWidget} for choosing a recognition rule sorter
	 */
	private RecognitionRuleSorterWidget rrSorterWidget;

	// ---------- Constructor ----------

	public AdvancedCompareSettingsPage(InputModels inputModels,
			LiftingSettings settings, BasicCompareSettingsPage basicCompareSettingsPage) {

		super("Advanced Compare Settings Page", "Compare models with each other",
				DifferenceLiftingUiPlugin.getImageDescriptor("icon.png"));
		this.inputModels = inputModels;
		this.settings = settings;
		this.basicCompareSettingsPage = basicCompareSettingsPage;
	}

	// ---------- AbstractWizardPage ----------

	@Override
	protected void createWidgets() {
		// Matcher:
		matcherWidget = new MatchingEngineWidget(inputModels, settings);
		matcherWidget.setDependency(basicCompareSettingsPage.getSettingsSourceWidget());
		addWidget(container, matcherWidget);
		ConfigurableExtensionWidget.addAllForWidget(container, matcherWidget, this::addWidget);

		// Technical Difference Builder:
		builderWidget = new TechnicalDifferenceBuilderWidget(inputModels, settings);
		builderWidget.setDependency(basicCompareSettingsPage.getSettingsSourceWidget());
		addWidget(container, builderWidget);

		// Recognition Rule Sorter:
		rrSorterWidget = new RecognitionRuleSorterWidget(inputModels, settings);
		rrSorterWidget.setDependency(basicCompareSettingsPage.getSettingsSourceWidget());
		addWidget(container, rrSorterWidget);

		// Recognition engine:
		recognitionWidget = new RecognitionEngineModeWidget(settings);
		recognitionWidget.setDependency(basicCompareSettingsPage.getSettingsSourceWidget());
		addWidget(container, recognitionWidget);
	}

	@Override
	protected String getDefaultMessage() {
		return "Compare two versions of a model: origin -> changed";
	}
}
