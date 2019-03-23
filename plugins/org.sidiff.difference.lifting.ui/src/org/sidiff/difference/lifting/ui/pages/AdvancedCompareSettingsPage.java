package org.sidiff.difference.lifting.ui.pages;

import org.eclipse.jface.resource.ImageDescriptor;
import org.sidiff.common.emf.input.InputModels;
import org.sidiff.common.extension.ui.widgets.ConfigurableExtensionWidget;
import org.sidiff.common.ui.pages.AbstractWizardPage;
import org.sidiff.difference.lifting.api.settings.LiftingSettings;
import org.sidiff.difference.lifting.ui.Activator;
import org.sidiff.difference.lifting.ui.widgets.RecognitionEngineWidget;
import org.sidiff.difference.lifting.ui.widgets.RecognitionRuleSorterWidget;
import org.sidiff.difference.technical.ui.widgets.DifferenceBuilderWidget;
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
	private RecognitionEngineWidget recognitionWidget;
	
	/**
	 * The {@link MatchingEngineWidget} for choosing a matcher
	 */
	private MatchingEngineWidget matcherWidget;
	
	/**
	 * The {@link DifferenceBuilderWidget} for choosing a technical difference builder
	 */
	private DifferenceBuilderWidget builderWidget;
	
	/**
	 * The {@link RecognitionEngineWidget} for choosing a recognition rule sorter
	 */
	private RecognitionRuleSorterWidget rrSorterWidget;
	
	// ---------- Constructor ----------

	public AdvancedCompareSettingsPage(String pageName, String title, InputModels inputModels,
			LiftingSettings settings, BasicCompareSettingsPage basicCompareSettingsPage) {
		this(pageName, title, Activator.getImageDescriptor("icon.png"), inputModels, settings, basicCompareSettingsPage);
	}

	public AdvancedCompareSettingsPage(String pageName, String title, ImageDescriptor titleImage,
			InputModels inputModels, LiftingSettings settings, BasicCompareSettingsPage basicCompareSettingsPage) {
		super(pageName, title, titleImage);

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
		builderWidget = new DifferenceBuilderWidget(inputModels, settings);
		builderWidget.setDependency(basicCompareSettingsPage.getSettingsSourceWidget());
		addWidget(container, builderWidget);
		
		// Recognition Rule Sorter:
		rrSorterWidget = new RecognitionRuleSorterWidget(inputModels);
		rrSorterWidget.setSettings(this.settings);
		rrSorterWidget.setDependency(basicCompareSettingsPage.getSettingsSourceWidget());
		addWidget(container, rrSorterWidget);
		
		// Recognition engine:
		recognitionWidget = new RecognitionEngineWidget();
		recognitionWidget.setSettings(this.settings);
		recognitionWidget.setDependency(basicCompareSettingsPage.getSettingsSourceWidget());
		addWidget(container, recognitionWidget);
	}

	@Override
	protected String getDefaultMessage() {
		return "Compare two versions of a model: origin -> changed";
	}
}
