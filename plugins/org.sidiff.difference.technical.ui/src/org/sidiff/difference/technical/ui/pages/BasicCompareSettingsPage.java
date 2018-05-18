package org.sidiff.difference.technical.ui.pages;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Group;
import org.sidiff.common.ui.pages.AbstractWizardPage;
import org.sidiff.difference.technical.api.settings.DifferenceSettings;
import org.sidiff.difference.technical.ui.widgets.DifferenceBuilderWidget;
import org.sidiff.difference.technical.ui.widgets.InputModelsWidget;
import org.sidiff.difference.technical.ui.widgets.MatchingEngineWidget;
import org.sidiff.difference.technical.ui.widgets.ScopeWidget;
import org.sidiff.integration.preferences.ui.widgets.SettingsSourceWidget;
import org.sidiff.matching.input.InputModels;

public class BasicCompareSettingsPage extends AbstractWizardPage {

	/**
	 * The {@link DifferenceSettings}
	 */
	private DifferenceSettings settings;

	/**
	 * The {@link SettingsSourceWidget} for loading global and project specific settings.
	 */
	private SettingsSourceWidget settingsSourceWidget;

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
	 * The {@link InputModelsWidget} for loading the models being compared.
	 */
	private InputModelsWidget sourceWidget;
	
	/**
	 * The {@link ScopeWidget} for determining the scope of the comparison.
	 */
	private ScopeWidget scopeWidget;
	
	
	
	// ---------- Constructor ----------
	
	public BasicCompareSettingsPage(String pageName, String title, InputModels inputModels, DifferenceSettings settings) {
		super(pageName, title);
		
		
		this.inputModels = inputModels;
		this.settings = settings;
	}
	
	

	public BasicCompareSettingsPage(String pageName, String title, ImageDescriptor titleImage, InputModels inputModels, DifferenceSettings settings) {
		super(pageName, title, titleImage);

		this.inputModels = inputModels;
		this.settings = settings;
	}

	// ---------- AbstractWizardPage ----------
	
	@Override
	protected void createWidgets() {
		// Settings Source:
		settingsSourceWidget = new SettingsSourceWidget(this.settings, inputModels);
		addWidget(container, settingsSourceWidget);

		// Models:
		sourceWidget = new InputModelsWidget(inputModels, "Comparison Direction");
		sourceWidget.setSettings(this.settings);
		sourceWidget.setDependency(settingsSourceWidget);
		addWidget(container, sourceWidget);

		// Comparison mode:
		scopeWidget = new ScopeWidget();
		scopeWidget.setSettings(this.settings);
		scopeWidget.setPageChangedListener(this);
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
		matcherWidget = new MatchingEngineWidget(inputModels.getResources(), true);
		matcherWidget.setSettings(this.settings);
		matcherWidget.setPageChangedListener(this);
		matcherWidget.setDependency(settingsSourceWidget);
		addWidget(algorithmsGroup, matcherWidget);

		// Technical Difference Builder:
		builderWidget = new DifferenceBuilderWidget(inputModels);
		builderWidget.setSettings(this.settings);
		builderWidget.setDependency(settingsSourceWidget);
		addWidget(algorithmsGroup, builderWidget);
	}

	@Override
	protected String getDefaultMessage() {
		return "Compare two versions of a model: origin -> changed";
	}

}
