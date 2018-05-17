package org.sidiff.difference.lifting.ui.pages;

import org.eclipse.jface.resource.ImageDescriptor;
import org.sidiff.common.ui.pages.AbstractWizardPage;
import org.sidiff.difference.lifting.api.settings.LiftingSettings;
import org.sidiff.difference.lifting.ui.Activator;
import org.sidiff.difference.lifting.ui.widgets.RulebaseWidget;
import org.sidiff.difference.technical.ui.widgets.InputModelsWidget;
import org.sidiff.difference.technical.ui.widgets.ScopeWidget;
import org.sidiff.integration.preferences.ui.widgets.SettingsSourceWidget;
import org.sidiff.matching.input.InputModels;

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
	 * The {@link ScopeWidget} for determining the scope of the comparison.
	 */
	private ScopeWidget scopeWidget;
	
	/**
	 * The {@link RulebaseWidget} for choosing the rule bases used by the lifting engine.
	 */
	private RulebaseWidget rulebaseWidget;
	
	
	// ---------- Constructor ----------
	
	public BasicCompareSettingsPage(String pageName, String title, InputModels inputModels, LiftingSettings settings) {
		super(pageName, title);
		
		this.setImageDescriptor(Activator.getImageDescriptor("icon.png"));
		
		this.inputModels = inputModels;
		this.settings = settings;
	}
	
	

	public BasicCompareSettingsPage(String pageName, String title, ImageDescriptor titleImage, InputModels inputModels, LiftingSettings settings) {
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
		settingsSourceWidget.addDependent(sourceWidget);
		addWidget(container, sourceWidget);

		// Comparison mode:
		scopeWidget = new ScopeWidget();
		scopeWidget.setSettings(this.settings);
		scopeWidget.setPageChangedListener(this);
		settingsSourceWidget.addDependent(scopeWidget);
		addWidget(container, scopeWidget);

		// Rulebases:
		rulebaseWidget = new RulebaseWidget(inputModels);
		rulebaseWidget.setSettings(this.settings);
		settingsSourceWidget.addDependent(rulebaseWidget);
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
