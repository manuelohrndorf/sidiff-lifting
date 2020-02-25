package org.sidiff.integration.preferences.ui.widgets;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.ui.dialogs.DiagnosticDialog;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.sidiff.common.emf.input.InputModels;
import org.sidiff.common.emf.settings.ISettings;
import org.sidiff.common.emf.settings.ISettingsItem;
import org.sidiff.common.ui.util.UIUtil;
import org.sidiff.common.ui.widgets.AbstractRadioWidget;
import org.sidiff.common.ui.widgets.IWidgetValidation.ValidationMessage.ValidationType;
import org.sidiff.integration.preferences.settingsadapter.SettingsAdapterUtil;
import org.sidiff.integration.preferences.ui.internal.PreferencesUiPlugin;
import org.sidiff.integration.preferences.util.PreferenceStoreUtil;

/**
 * <p>The settings source widget allows selecting a source from which
 * the {@link ISettings} are to be adapted.</p>
 * <p>The source can be the global preference store, as well as
 * a project specific preference store. If these options are chosen,
 * all widgets dependent on this one are disabled. The third option
 * enables all widgets and allows selection of custom settings.</p>
 * <p>Which properties of the settings are considered/changed, can be configured
 * by using {@link #addConsideredSettings(Enum...)} to add the enum literals
 * from the *SettingsItem enums. If no considered items are set, all items
 * will be considered.</p>
 * @author rmueller
 */
public class SettingsSourceWidget extends AbstractRadioWidget<SettingsSourceWidget.Source> {

	public static enum Source {
		GLOBAL("Use global settings"),
		PROJECT("Use settings of project"),
		CUSTOM("Use custom settings");

		private String label;

		Source(String label) {
			this.label = label;
		}

		public String getLabel() {
			return label;
		}
	}

	// inputs
	private ISettings settings;
	private IProject project;
	private Set<String> documentTypes;
	private Set<ISettingsItem> consideredSettings;
	private String preferenceQualifier = PreferenceStoreUtil.PREFERENCE_QUALIFIER;

	// outputs
	private Diagnostic diagnostic;

	/**
	 * Creates a new SettingsSourceWidget that will adapt the given settings.
	 * The given input models are used to retrieve the document types
	 * and the project that the models reside in.
	 * @param settings the settings that will be adapted
	 * @param inputModels the input models
	 */
	public SettingsSourceWidget(ISettings settings, InputModels inputModels) {
		this(settings, inputModels.getProject(), inputModels.getDocumentTypes());
	}

	/**
	 * Creates a new SettingsSourceWidget that will adapt the given settings.
	 * @param settings the settings that will be adapted
	 * @param project the project whose project specific settings will be used
	 * @param documentTypes the document types of the input models
	 */
	public SettingsSourceWidget(ISettings settings, IProject project, Set<String> documentTypes) {
		this.settings = settings;
		this.project = project;
		// some other widgets change the original set, so a local copy is created here
		this.documentTypes = new HashSet<>(documentTypes);
		this.consideredSettings = new HashSet<>();

		setTitle("Settings Source");
		setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((Source)element).getLabel();
			}
		});
		addModificationListener((oldValues, newValues) -> {
			// adapt the settings using the changed settings source
			updateSettings();
			// update enabled state of dependents
			propagateEnabledState();
			// notify listeners after the enabled state has been updated
			// as disabled widgets are not validated
			getWidgetCallback().requestValidation();
			// show settings validation results
			showDiagnosticDialog();
		});
	}
	
	public void setPreferenceQualifier(String preferenceQualifier) {
		this.preferenceQualifier = Objects.requireNonNull(preferenceQualifier);
	}
	
	public String getPreferenceQualifier() {
		return preferenceQualifier;
	}

	@Override
	protected void hookInitButtons() {
		super.hookInitButtons();

		Button radioProject = getButtons().get(Source.PROJECT);
		try {
			if(project == null) {
				throw new CoreException(new Status(IStatus.ERROR, PreferencesUiPlugin.PLUGIN_ID,
						"No project was specified / deduced from the input models."));
			}
			radioProject.setEnabled(PreferenceStoreUtil.useSpecificSettings(project, preferenceQualifier));
		} catch (CoreException e) {
			radioProject.setEnabled(false);
			radioProject.setText(radioProject.getText() + " [not possible: " + e.getMessage() + "]");
			PreferencesUiPlugin.logWarning("Checking if project specific settings exist failed.", e);
		}

		// set default selection
		setSource(radioProject.isEnabled() ? Source.PROJECT : Source.CUSTOM);
	}

	public void setSource(Source source) {
		Assert.isNotNull(source);
		if(getSource() == source) {
			return;
		}
		setSelection(Collections.singletonList(source));
	}

	public Source getSource() {
		return getSelection().stream().findFirst().orElse(null);
	}

	@Override
	public boolean areDependentsEnabled() {
		return super.areDependentsEnabled() && getSource() == Source.CUSTOM;
	}

	private void updateSettings() {
		switch(getSource()) {	
			case GLOBAL:
				diagnostic = SettingsAdapterUtil.adaptSettingsGlobal(
						settings, documentTypes, consideredSettings, preferenceQualifier);
				break;

			case PROJECT:
				diagnostic = SettingsAdapterUtil.adaptSettingsProject(
						settings, project, documentTypes, consideredSettings, preferenceQualifier);
				break;

			case CUSTOM:
				// settings are not changed
				diagnostic = null;
				break;
		}
	}

	private void showDiagnosticDialog() {
		if(diagnostic == null || diagnostic.getSeverity() == Diagnostic.OK) {
			return;
		}
		Display.getCurrent().asyncExec(() ->
			DiagnosticDialog.open(UIUtil.getActiveShell(), "Validation of settings", null, diagnostic));
	}

	@Override
	protected ValidationMessage doValidate() {
		if(getSource() != Source.CUSTOM && diagnostic != null && diagnostic.getSeverity() >= Diagnostic.ERROR) {
			// WARNING instead of ERROR so other validation messages take precedence over this one, as it is pretty vague
			return new ValidationMessage(ValidationType.WARNING,
					getSource() == Source.GLOBAL ? "The global settings are not valid."
											: "The project specific settings are not valid.");
		}
		return ValidationMessage.OK;
	}

	/**
	 * Adds the specified settings items (<code>*SettingsItem</code>) to the set of
	 * settings that will be adapted. All items will be considered, if no item is added to this widget.
	 * @param consideredSettings settings that should be adapted, not empty, not <code>null</code>
	 */
	public void addConsideredSettings(ISettingsItem ...consideredSettings) {
		Assert.isNotNull(consideredSettings);
		Assert.isLegal(consideredSettings.length > 0, "consideredSettings must not be empty");
		for(ISettingsItem item : consideredSettings) {
			this.consideredSettings.add(item);
		}
	}

	/**
	 * Removes the specified settings items (<code>*SettingsItem</code>) from the set of
	 * settings that will be adapted. All items will be considered, if no item is added to this widget.
	 * Removing items not added to this widget does nothing.
	 * @param consideredSettings settings that should be adapted, not empty, not <code>null</code>
	 */
	public void removeConsideredSettings(ISettingsItem ...consideredSettings) {
		Assert.isNotNull(consideredSettings);
		Assert.isLegal(consideredSettings.length > 0, "consideredSettings must not be empty");
		for(ISettingsItem item : consideredSettings) {
			this.consideredSettings.remove(item);
		}
	}

	@Override
	public List<Source> getSelectableValues() {
		return Arrays.asList(SettingsSourceWidget.Source.values());
	}
}
