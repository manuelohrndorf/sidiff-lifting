package org.sidiff.patching.ui.wizard;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.sidiff.common.emf.doctype.util.EMFDocumentTypeUtil;
import org.sidiff.common.settings.BaseSettingsItem;
import org.sidiff.common.ui.pages.AbstractWizardPage;
import org.sidiff.difference.technical.ui.widgets.ScopeWidget;
import org.sidiff.integration.preferences.ui.widgets.SettingsSourceWidget;
import org.sidiff.matching.api.settings.MatchingSettingsItem;
import org.sidiff.patching.api.settings.PatchingSettings;
import org.sidiff.patching.api.settings.PatchingSettingsItem;
import org.sidiff.patching.patch.patch.Patch;
import org.sidiff.patching.ui.Activator;
import org.sidiff.patching.ui.widgets.TargetModelWidget;
import org.sidiff.patching.ui.widgets.ValidationModeWidget;

public class ApplyPatchPage01 extends AbstractWizardPage {

	private String DEFAULT_MESSAGE = "Apply a patch to a model";

	private SettingsSourceWidget settingsSourceWidget;
	private TargetModelWidget targetWidget;
	private ScopeWidget scopeWidget;
	private ValidationModeWidget validationWidget;

	private IProject project;
	private Set<String> documentTypes;
	private PatchingSettings settings;

	public ApplyPatchPage01(Patch patch, String pageName, String title, PatchingSettings settings, IProject project) {
		super(pageName, title, Activator.getImageDescriptor("icon.png"));
		this.settings = settings;
		this.documentTypes = new HashSet<String>(EMFDocumentTypeUtil.resolve(Arrays.asList(
				patch.getAsymmetricDifference().getOriginModel(),
				patch.getAsymmetricDifference().getChangedModel())));
		this.project = project;
	}

	@Override
	protected void createWidgets() {
		// Settings Source:
		// Note that InputModels is not used here, because it is not compatible with Archive-URIs (i.e. Patch files)
		settingsSourceWidget = new SettingsSourceWidget(settings, project, documentTypes);
		settingsSourceWidget.addConsideredSettings(BaseSettingsItem.values());
		settingsSourceWidget.addConsideredSettings(MatchingSettingsItem.values());
		settingsSourceWidget.addConsideredSettings(
				PatchingSettingsItem.VALIDATION_MODE,
				PatchingSettingsItem.RELIABILITY,
				PatchingSettingsItem.SYMBOLIC_LINK_HANDLER);
		addWidget(container, settingsSourceWidget);

		// Target model:
		targetWidget = new TargetModelWidget();
		addWidget(container, targetWidget);

		// Comparison mode:
		scopeWidget = new ScopeWidget();
		scopeWidget.setSettings(this.settings);
		scopeWidget.setDependency(settingsSourceWidget);
		addWidget(container, scopeWidget);

		// Validation:
		validationWidget = new ValidationModeWidget();
		validationWidget.setSettings(this.settings);
		validationWidget.setDependency(settingsSourceWidget);
		addWidget(container, validationWidget);
	}

	public TargetModelWidget getTargetWidget(){
		return targetWidget;
	}

	public ScopeWidget getScopeWidget() {
		return scopeWidget;
	}

	public ValidationModeWidget getValidationWidget() {
		return validationWidget;
	}

	// internal access method for other wizard page
	SettingsSourceWidget getSettingsSourceWidget() {
		return settingsSourceWidget;
	}

	@Override
	protected String getDefaultMessage() {
		return DEFAULT_MESSAGE;
	}	
}
