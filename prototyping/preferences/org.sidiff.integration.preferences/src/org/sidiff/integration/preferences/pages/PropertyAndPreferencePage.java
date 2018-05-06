package org.sidiff.integration.preferences.pages;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.dialogs.ControlEnableState;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbenchPropertyPage;
import org.sidiff.integration.preferences.util.PreferenceStoreUtil;
import org.sidiff.integration.preferences.util.ProjectSpecificSettingsListener;

/**
 * Abstract superclass for all preference pages, that are both a property and a preference page.
 * If the page is a property page, a button to enable project specific settings is shown.
 * @author Robert Müller
 *
 */
public abstract class PropertyAndPreferencePage extends PreferencePage
					implements IWorkbenchPreferencePage, IWorkbenchPropertyPage {

	private IProject project;
	private Button useSpecificSettingsButton;
	private Control preferenceControl;
	private ControlEnableState enableState;
	private ProjectSpecificSettingsListener projectSpecificSettingsListener;

	public PropertyAndPreferencePage() {
		super();
	}

	public PropertyAndPreferencePage(String title) {
		super(title);
	}

	public PropertyAndPreferencePage(String title, ImageDescriptor image) {
		super(title, image);
	}

	/**
	 * Creates and returns the control for the preferences
	 * @param parent the parent composite
	 * @return the new control
	 */
	protected abstract Control doCreateContents(Composite parent);

	/**
	 * Loads/Reloads preference values from the {@link #getPreferenceStore() preference store}.
	 * Called after {@link doCreateControls} and when the enable button is selected.
	 */
	protected abstract void reloadPreferences();

	/**
	 * Resets all preferences to their default values using the {@link #getPreferenceStore() preference store}.
	 */
	protected abstract void defaultPreferences();

	/**
	 * Saves all preferences to the {@link #getPreferenceStore() preference store}.
	 */
	protected abstract void savePreferences();

	/**
	 * Validates the preferences and sets the result using {@link #setValid(boolean)} and {@link #setErrorMessage(String)}.
	 */
	protected abstract void validatePreferences();

	@Override
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(1, true));

		if(isPropertiesPage()) {
			createControlButton(container);
		}

		preferenceControl = doCreateContents(container);
		preferenceControl.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		reloadPreferences();

		if(isPropertiesPage()) {
			updateEnabledState();
		}

		return container;
	}
	
	private void createControlButton(Composite container) {
		useSpecificSettingsButton = new Button(container, SWT.CHECK);
		useSpecificSettingsButton.setText("Use project specific settings");
		useSpecificSettingsButton.setSelection(PreferenceStoreUtil.useSpecificSettings(project));
		useSpecificSettingsButton.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
		useSpecificSettingsButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				try {
					setUseSpecificSettings(useSpecificSettingsButton.getSelection());
				} catch (CoreException e) {
					event.doit = false;
					ErrorDialog.openError(getShell(), "Enabling resource specific settings failed", null, e.getStatus());
				}
			}
		});

		projectSpecificSettingsListener = new ProjectSpecificSettingsListener() {
			@Override
			public void useProjectSpecificSettingsChanged(IProject project, boolean use) {
				if(getElement() != project)
					return;
				useSpecificSettingsButton.setSelection(use);
				setPreferenceStore(null); // update preference store
				reloadPreferences();
				updateEnabledState();
			}
		};
		PreferenceStoreUtil.addProjectSpecificSettingsListener(projectSpecificSettingsListener);
	}

	protected void setUseSpecificSettings(boolean selection) throws CoreException {
		if(!isPropertiesPage())
			return;
		PreferenceStoreUtil.setUseSpecificSettings(project, selection);
	}

	protected boolean useSpecificSettings() {
		return isPropertiesPage() && useSpecificSettingsButton.getSelection();
	}

	private void updateEnabledState() {
		if(useSpecificSettingsButton.getSelection()) {
			if(enableState != null) {
				enableState.restore();
				enableState = null;
			}
		} else {
			if(enableState == null) {
				enableState = ControlEnableState.disable(preferenceControl);
			}
		}
	}
	
	@Override
	public IAdaptable getElement() {
		return project;
	}

	@Override
	public void setElement(IAdaptable element) {
		project = Adapters.adapt(element, IProject.class);
		setPreferenceStore(null); // update preference store
	}

	/**
	 * Returns whether the page is a property page, i.e. it is associated with a project.
	 * @return whether the page is a property page
	 */
	protected boolean isPropertiesPage() {
		return project != null;
	}

	@Override
	protected IPreferenceStore doGetPreferenceStore() {
		if(isPropertiesPage()) {
			return PreferenceStoreUtil.getPreferenceStore(project);
		} else {
			return PreferenceStoreUtil.getPreferenceStore();
		}
	}

	@Override
	public void init(IWorkbench workbench) {
	}

	@Override
	public boolean performOk() {
		validatePreferences();
		if(isValid()) {
			savePreferences();
			return true;
		}
		return false;
	}

	@Override
	protected void performDefaults() {
		defaultPreferences();
		super.performDefaults();
	}

	@Override
	public void dispose() {
		if(projectSpecificSettingsListener != null) {
			PreferenceStoreUtil.removeProjectSpecificSettingsListener(projectSpecificSettingsListener);
		}
		super.dispose();
	}
}
