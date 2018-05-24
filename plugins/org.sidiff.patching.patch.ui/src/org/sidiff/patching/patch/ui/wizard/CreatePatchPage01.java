package org.sidiff.patching.patch.ui.wizard;

import java.util.Set;

import org.eclipse.jface.resource.ImageDescriptor;
import org.sidiff.common.ui.pages.AbstractWizardPage;
import org.sidiff.difference.lifting.api.settings.LiftingSettings;
import org.sidiff.difference.lifting.ui.widgets.RulebaseWidget;
import org.sidiff.difference.rulebase.view.ILiftingRuleBase;
import org.sidiff.difference.technical.ui.widgets.InputModelsWidget;
import org.sidiff.difference.technical.ui.widgets.ScopeWidget;
import org.sidiff.integration.preferences.ui.widgets.SettingsSourceWidget;
import org.sidiff.matching.input.InputModels;
import org.sidiff.patching.patch.ui.widgets.EditRuleMatchWidget;

public class CreatePatchPage01 extends AbstractWizardPage {

	private String default_message;

	private SettingsSourceWidget settingsSourceWidget;
	private InputModelsWidget sourceWidget;
	private ScopeWidget scopeWidget;
	private EditRuleMatchWidget erMatchWidget;
	private RulebaseWidget rulebaseWidget;

	private InputModels inputModels;
	private LiftingSettings settings;

	private String mode;

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

		default_message = "Create a " + this.mode + " from the changes between the models: origin -> changed";
	}

	@Override
	protected void createWidgets() {
		// Settings Source:
		settingsSourceWidget = new SettingsSourceWidget(this.settings, inputModels);
		addWidget(container, settingsSourceWidget);

		// Models:
		sourceWidget = new InputModelsWidget(inputModels, mode + " Direction");
		sourceWidget.setSettings(this.settings);
		sourceWidget.setDependency(settingsSourceWidget);
		addWidget(container, sourceWidget);

		// Comparison mode:
		scopeWidget = new ScopeWidget();
		scopeWidget.setSettings(this.settings);
		scopeWidget.setDependency(settingsSourceWidget);
		addWidget(container, scopeWidget);

		// Edit-Rule Matches:
		erMatchWidget = new EditRuleMatchWidget();
		erMatchWidget.setSettings(settings);
		erMatchWidget.setDependency(settingsSourceWidget);
		addWidget(container, erMatchWidget);

		// Rulebases:
		rulebaseWidget = new RulebaseWidget(inputModels);
		rulebaseWidget.setSettings(this.settings);
		rulebaseWidget.setDependency(settingsSourceWidget);
		addWidget(container, rulebaseWidget);
	}

	public boolean isValidateModels() {
		return sourceWidget.isValidateModels();
	}

	public boolean isInverseDirection() {
		return sourceWidget.isInverseDirection();
	}

	public ScopeWidget getScopeWidget() {
		return scopeWidget;
	}

	public Set<ILiftingRuleBase> getSelectedRulebases() {
		return rulebaseWidget.getSelection();
	}

	@Override
	protected String getDefaultMessage() {
		return default_message;
	}

	// internal access method for other wizard page
	SettingsSourceWidget getSettingsSourceWidget() {
		return settingsSourceWidget;
	}
}
