package org.sidiff.integration.preferences.ui.widgets;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.ui.dialogs.DiagnosticDialog;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.sidiff.common.settings.ISettings;
import org.sidiff.common.ui.widgets.AbstractWidget;
import org.sidiff.common.ui.widgets.IWidgetSelection;
import org.sidiff.common.ui.widgets.IWidgetValidation;
import org.sidiff.common.ui.widgets.IWidgetValidation.ValidationMessage.ValidationType;
import org.sidiff.integration.preferences.settingsadapter.SettingsAdapterUtil;
import org.sidiff.integration.preferences.ui.PreferencesUiPlugin;
import org.sidiff.integration.preferences.util.PreferenceStoreUtil;
import org.sidiff.matching.input.InputModels;

/**
 * 
 * @author rmueller
 */
public class SettingsSourceWidget extends AbstractWidget implements IWidgetValidation, IWidgetSelection {

	public static enum Source {
		GLOBAL,
		PROJECT,
		CUSTOM
	}

	// UI components
	private Composite container;
	private Group group;
	private Map<Source, Button> buttons;
	private SelectionListener buttonSelectionListener;
	private List<SelectionListener> selectionListeners;

	// inputs
	private ISettings settings;
	private IProject project;
	private Set<String> documentTypes;
	private Set<Enum<?>> consideredSettings;
	private String preferenceQualifier = PreferenceStoreUtil.PREFERENCE_QUALIFIER;

	// outputs
	private Source source;
	private ValidationMessage message;
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
		this.documentTypes = new HashSet<String>(documentTypes);
		this.consideredSettings = new HashSet<Enum<?>>();
		this.selectionListeners = new LinkedList<SelectionListener>();
	}
	
	public void setPreferenceQualifier(String preferenceQualifier) {
		this.preferenceQualifier = Objects.requireNonNull(preferenceQualifier);
	}
	
	public String getPreferenceQualifier() {
		return preferenceQualifier;
	}

	@Override
	public Composite createControl(Composite parent) {
		container = new Composite(parent, SWT.NONE);
		{
			GridLayout grid = new GridLayout(1, false);
			grid.marginWidth = 0;
			grid.marginHeight = 0;
			container.setLayout(grid);
		}

		group = new Group(container, SWT.SHADOW_IN);
		group.setText("Settings Source:");
		group.setLayout(new RowLayout(SWT.VERTICAL));
		group.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		buttons = new EnumMap<Source, Button>(Source.class);

		Button radioGlobal = new Button(group, SWT.RADIO);
		radioGlobal.setText("Use global settings");
		radioGlobal.addSelectionListener(getButtonSelectionListener());
		buttons.put(Source.GLOBAL, radioGlobal);

		Button radioProject = new Button(group, SWT.RADIO);
		radioProject.addSelectionListener(getButtonSelectionListener());
		try {
			if(project == null) {
				throw new CoreException(new Status(IStatus.ERROR, PreferencesUiPlugin.PLUGIN_ID,
						"No project was specified / deduced from the input models."));
			}
			radioProject.setEnabled(PreferenceStoreUtil.useSpecificSettings(project, getPreferenceQualifier()));
			radioProject.setText("Use settings of project");
		} catch (CoreException e) {
			radioProject.setEnabled(false);
			radioProject.setText("Use settings of project [not possible: " + e.getMessage() + "]");
			PreferencesUiPlugin.logWarning("Checking if project specific settings exist failed.", e);
		}
		buttons.put(Source.PROJECT, radioProject);

		Button radioCustom = new Button(group, SWT.RADIO);
		radioCustom.setText("Use custom settings");
		radioCustom.addSelectionListener(getButtonSelectionListener());
		buttons.put(Source.CUSTOM, radioCustom);

		// set default selection and show dialog with settings validation result
		setSource(radioProject.isEnabled() ? Source.PROJECT : Source.CUSTOM);
		showDiagnosticDialog();

		return container;
	}

	@Override
	public Composite getWidget() {
		return container;
	}

	public void setSource(Source source) {
		Assert.isNotNull(source);
		if(this.source == source) {
			return;
		}
		this.source = source;

		// update state of all radio buttons
		for(Map.Entry<Source, Button> entry : buttons.entrySet()) {
			entry.getValue().setSelection(entry.getKey() == source);
		}

		// adapt the settings using the changed settings source
		updateSettings();

		// notify listeners
		for(SelectionListener listener : selectionListeners) {
			Event e = new Event();
			e.widget = buttons.get(source);
			listener.widgetSelected(new SelectionEvent(e));
		}
		getWidgetCallback().requestValidation();

		// update enabled state of dependents
		propagateEnabledState();
	}

	public Source getSource() {
		return source;
	}

	@Override
	public boolean areDependentsEnabled() {
		return super.areDependentsEnabled() && source == Source.CUSTOM;
	}

	private void updateSettings() {
		switch(source) {	
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
		Display.getCurrent().asyncExec(() -> DiagnosticDialog.open(container.getShell(), "Validation of settings", null, diagnostic));
	}

	private SelectionListener getButtonSelectionListener() {
		if(buttonSelectionListener == null) {
			buttonSelectionListener = new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					for(Map.Entry<Source, Button> entry : buttons.entrySet()) {
						if(entry.getValue() == e.getSource() && entry.getValue().getSelection()) {
							setSource(entry.getKey());
							showDiagnosticDialog();
							break;
						}
					}
				}
			};
		}
		return buttonSelectionListener;
	}

	@Override
	public boolean validate() {
		if(source != Source.CUSTOM && diagnostic != null && diagnostic.getSeverity() >= Diagnostic.ERROR) {
			// WARNING instead of ERROR so other validation messages take precedence over this one, as it is pretty vague
			message = new ValidationMessage(ValidationType.WARNING,
					source == Source.GLOBAL ? "The global settings are not valid."
											: "The project specific settings are not valid.");
			return false;
		}
		message = ValidationMessage.OK;
		return true;
	}

	@Override
	public ValidationMessage getValidationMessage() {
		return message;
	}

	@Override
	public void addSelectionListener(SelectionListener listener) {
		selectionListeners.add(listener);
	}

	@Override
	public void removeSelectionListener(SelectionListener listener) {
		selectionListeners.remove(listener);
	}

	/**
	 * Adds the specified settings items (<code>*SettingsItem</code>) to the set of
	 * settings that will be adapted. All items will be considered, if no item is added to this widget.
	 * @param consideredSettings settings that should be adapted, not empty, not <code>null</code>
	 */
	public void addConsideredSettings(Enum<?> ...consideredSettings) {
		Assert.isNotNull(consideredSettings);
		Assert.isLegal(consideredSettings.length > 0, "consideredSettings must not be empty");
		for(Enum<?> item : consideredSettings) {
			this.consideredSettings.add(item);
		}
	}

	/**
	 * Removes the specified settings items (<code>*SettingsItem</code>) from the set of
	 * settings that will be adapted. All items will be considered, if no item is added to this widget.
	 * Removing items not added to this widget does nothing.
	 * @param consideredSettings settings that should be adapted, not empty, not <code>null</code>
	 */
	public void removeConsideredSettings(Enum<?> ...consideredSettings) {
		Assert.isNotNull(consideredSettings);
		Assert.isLegal(consideredSettings.length > 0, "consideredSettings must not be empty");
		for(Enum<?> item : consideredSettings) {
			this.consideredSettings.remove(item);
		}
	}
}
