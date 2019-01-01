package org.sidiff.difference.lifting.ui.pages;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Group;
import org.sidiff.common.extension.ui.widgets.ConfigurableExtensionWidget;
import org.sidiff.common.ui.pages.AbstractWizardPage;
import org.sidiff.difference.lifting.api.settings.LiftingSettings;
import org.sidiff.difference.lifting.ui.Activator;
import org.sidiff.difference.lifting.ui.widgets.RecognitionEngineWidget;
import org.sidiff.difference.lifting.ui.widgets.RecognitionRuleSorterWidget;
import org.sidiff.difference.technical.ui.widgets.DifferenceBuilderWidget;
import org.sidiff.difference.technical.ui.widgets.MatchingEngineWidget;
import org.sidiff.matching.input.InputModels;

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
		matcherWidget = new MatchingEngineWidget(inputModels, settings);
		matcherWidget.setDependency(basicCompareSettingsPage.getSettingsSourceWidget());
		addWidget(algorithmsGroup, matcherWidget);
		ConfigurableExtensionWidget.addAllForWidget(algorithmsGroup, matcherWidget, this::addWidget);

		// Technical Difference Builder:
		builderWidget = new DifferenceBuilderWidget(inputModels);
		builderWidget.setSettings(this.settings);
		builderWidget.setDependency(basicCompareSettingsPage.getSettingsSourceWidget());
		addWidget(algorithmsGroup, builderWidget);
		
		// Recognition Rule Sorter:
		rrSorterWidget = new RecognitionRuleSorterWidget(inputModels);
		rrSorterWidget.setSettings(this.settings);
		rrSorterWidget.setDependency(basicCompareSettingsPage.getSettingsSourceWidget());
		addWidget(algorithmsGroup, rrSorterWidget);
		
		// Recognition engine:
		recognitionWidget = new RecognitionEngineWidget();
		recognitionWidget.setSettings(this.settings);
		recognitionWidget.setDependency(basicCompareSettingsPage.getSettingsSourceWidget());
		addWidget(algorithmsGroup, recognitionWidget);
	}

	@Override
	protected String getDefaultMessage() {
		return "Compare two versions of a model: origin -> changed";
	}
}
