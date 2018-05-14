package org.sidiff.difference.lifting.ui.pages;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Group;
import org.sidiff.common.ui.pages.AbstractWizardPage;
import org.sidiff.difference.lifting.api.settings.LiftingSettings;
import org.sidiff.difference.lifting.ui.widgets.RecognitionEngineWidget;
import org.sidiff.difference.lifting.ui.widgets.RecognitionRuleSorterWidget;
import org.sidiff.difference.technical.ui.widgets.DifferenceBuilderWidget;
import org.sidiff.difference.technical.ui.widgets.MatchingEngineWidget;
import org.sidiff.matching.input.InputModels;

public class AdvancedCompareSettingsPage extends AbstractWizardPage{

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
	
	public AdvancedCompareSettingsPage(String pageName, String title, InputModels inputModels, LiftingSettings settings) {
		super(pageName, title);
		
		this.inputModels = inputModels;
		this.settings = settings;
	}
	
	public AdvancedCompareSettingsPage(String pageName, String title, ImageDescriptor titleImage, InputModels inputModels, LiftingSettings settings) {
		super(pageName, title, titleImage);
		
		this.inputModels = inputModels;
		this.settings = settings;
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
		matcherWidget = new MatchingEngineWidget(inputModels.getResources(), true);
		matcherWidget.setSettings(this.settings);
		matcherWidget.setPageChangedListener(this);
		addWidget(algorithmsGroup, matcherWidget);
		
		// Technical Difference Builder:
		builderWidget = new DifferenceBuilderWidget(inputModels);
		builderWidget.setSettings(this.settings);
// FIXME
//		if (builderWidget.getDifferenceBuilders().size() > 1) {
//			addWidget(algorithmsGroup, builderWidget);
//		}
		addWidget(algorithmsGroup, builderWidget);
		
		// Recognition Rule Sorter:
		rrSorterWidget = new RecognitionRuleSorterWidget(inputModels);
		rrSorterWidget.setSettings(this.settings);
		addWidget(algorithmsGroup, rrSorterWidget);
		
		// Recognition engine:
		recognitionWidget = new RecognitionEngineWidget();
		recognitionWidget.setSettings(this.settings);
		addWidget(algorithmsGroup, recognitionWidget);
		
	}

	@Override
	protected String getDefaultMessage() {
		return "Compare two versions of a model: origin -> changed";
	}

}
