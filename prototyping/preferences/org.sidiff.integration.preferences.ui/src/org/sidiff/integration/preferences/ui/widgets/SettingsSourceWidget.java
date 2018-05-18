package org.sidiff.integration.preferences.ui.widgets;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
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
import org.sidiff.common.settings.AbstractSettings;
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
 * @author Robert Müller
 *
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
	private AbstractSettings settings;
	private IProject project;
	private Set<String> documentTypes;

	// outputs
	private Source source;
	private ValidationMessage message;
	private Diagnostic diagnostic;

	public SettingsSourceWidget(AbstractSettings settings, InputModels inputModels) {
		for(IFile file : inputModels.getFiles()) {
			if(this.project == null) {
				this.project = file.getProject();
			} else if(!this.project.equals(file.getProject())) {
				PreferencesUiPlugin.logWarning("Input models are not in the same project. "
											 + "Using project specific settings of first one.");
			}
		}
		// some other widgets change the original set, so a local copy is created here
		this.documentTypes = new HashSet<String>(inputModels.getDocumentTypes());
		this.settings = settings;
		this.selectionListeners = new LinkedList<SelectionListener>();
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
		for(SelectionListener listener : selectionListeners) {
			Event e = new Event();
			e.widget = buttons.get(source);
			listener.widgetSelected(new SelectionEvent(e));
		}

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
				diagnostic = SettingsAdapterUtil.adaptSettingsGlobal(settings, documentTypes);
				break;

			case PROJECT:
				diagnostic = SettingsAdapterUtil.adaptSettingsProject(settings, project, documentTypes);
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
		if(source != Source.CUSTOM && (!settings.validateSettings()
				|| (diagnostic != null && diagnostic.getSeverity() >= Diagnostic.ERROR))) {
			// WARNING instead of ERROR so other validation messages take precedence over this one, as it is pretty vague
			message = new ValidationMessage(ValidationType.WARNING,
					source == Source.GLOBAL ? "The global settings are not valid."
								: "The project specific settings are not valid.");
			return false;
		}
		message = new ValidationMessage(ValidationType.OK, "");
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
}
