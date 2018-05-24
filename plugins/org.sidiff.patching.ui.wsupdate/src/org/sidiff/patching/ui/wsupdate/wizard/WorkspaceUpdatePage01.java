package org.sidiff.patching.ui.wsupdate.wizard;

import java.util.Set;

import org.eclipse.jface.resource.ImageDescriptor;
import org.sidiff.common.ui.pages.AbstractWizardPage;
import org.sidiff.difference.lifting.api.settings.LiftingSettings;
import org.sidiff.difference.lifting.ui.widgets.RulebaseWidget;
import org.sidiff.difference.rulebase.view.ILiftingRuleBase;
import org.sidiff.difference.technical.ui.widgets.ScopeWidget;
import org.sidiff.matching.input.InputModels;
import org.sidiff.patching.api.settings.PatchingSettings;
import org.sidiff.patching.api.settings.PatchingSettings.ValidationMode;
import org.sidiff.patching.ui.widgets.ValidationModeWidget;
import org.sidiff.patching.ui.wsupdate.util.WSUModels;
import org.sidiff.patching.ui.wsupdate.widgets.WSUModelsWidget;

public class WorkspaceUpdatePage01 extends AbstractWizardPage {

	private WSUModelsWidget mergeModelsWidget;
	private ValidationModeWidget validationWidget;
	private ScopeWidget scopeWidget;
	private RulebaseWidget rulebaseWidget;

	private WSUModels mergeModels;
	private LiftingSettings liftingSettings;
	private PatchingSettings patchingSettings;

	public WorkspaceUpdatePage01(WSUModels mergeModels, String pageName, String title,
			ImageDescriptor titleImage, LiftingSettings liftingSettings, PatchingSettings patchingSettings) {
		super(pageName, title, titleImage);

		this.mergeModels = mergeModels;
		this.liftingSettings = liftingSettings;
		this.patchingSettings = patchingSettings;
	}

	@Override
	protected void createWidgets() {

		// Models:
		mergeModelsWidget = new WSUModelsWidget(mergeModels);
		mergeModelsWidget.setSettings(this.liftingSettings);
		addWidget(container, mergeModelsWidget);

		// Comparison mode:
		scopeWidget = new ScopeWidget();
		scopeWidget.setSettings(this.liftingSettings);
		addWidget(container, scopeWidget);

		// Validation mode:
		validationWidget = new ValidationModeWidget();
		validationWidget.setSettings(this.patchingSettings);
		addWidget(container, validationWidget);

		// Rulebases:
		rulebaseWidget = new RulebaseWidget(new InputModels(mergeModels.getFileBase(), mergeModels.getFileTheirs()));
		rulebaseWidget.setSettings(this.liftingSettings);
		addWidget(container, rulebaseWidget);
	}

	public WSUModelsWidget getMergeModelsWidget() {
		return mergeModelsWidget;
	}

	public WSUModels getMergeModels() {
		return mergeModelsWidget.getMergeModels();
	}

	public ValidationModeWidget getValidationModeWidget() {
		return validationWidget;
	}

	public boolean isValidateModels() {
		return validationWidget.getSelection() != ValidationMode.NO_VALIDATION;
	}

	public ScopeWidget getScopeWidget() {
		return scopeWidget;
	}

	public Set<ILiftingRuleBase> getSelectedRulebases() {
		return rulebaseWidget.getSelection();
	}

	@Override
	protected String getDefaultMessage() {
		return "Propagates parallel changes by other developers, which were checked-in into a common repository, to the local workspace.";
	}
}
