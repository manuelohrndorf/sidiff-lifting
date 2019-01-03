package org.sidiff.integration.preferences.ui.widgets;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
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
import org.eclipse.swt.widgets.Group;
import org.sidiff.common.settings.ISettings;
import org.sidiff.common.ui.widgets.AbstractWidget;
import org.sidiff.common.ui.widgets.IWidgetValidation;
import org.sidiff.common.ui.widgets.IWidgetValidation.ValidationMessage.ValidationType;
import org.sidiff.integration.preferences.settingsadapter.SettingsAdapterUtil;
import org.sidiff.integration.preferences.ui.PreferencesUiPlugin;
import org.sidiff.integration.preferences.util.PreferenceStoreUtil;
import org.sidiff.matching.input.InputModels;

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
 * @author Robert Müller
 */
public class SettingsSourceWidget extends AbstractWidget implements IWidgetValidation {

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

	// inputs
	private ISettings settings;
	private IProject project;
	private Set<String> documentTypes;
	private Set<Enum<?>> consideredSettings;

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
		this.documentTypes = new HashSet<>(documentTypes);
		this.consideredSettings = new HashSet<>();
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
			radioProject.setEnabled(PreferenceStoreUtil.useSpecificSettings(project));
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
				diagnostic = SettingsAdapterUtil.adaptSettingsGlobal(settings, documentTypes, consideredSettings);
				break;

			case PROJECT:
				diagnostic = SettingsAdapterUtil.adaptSettingsProject(settings, project, documentTypes, consideredSettings);
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

		Display.getCurrent().asyncExec(new Runnable() {
			public void run() {
				DiagnosticDialog.open(container.getShell(), "Validation of settings", null, diagnostic);
			}
		});
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
