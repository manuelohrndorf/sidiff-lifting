package org.sidiff.integration.preferences.ui.pages;

import java.io.IOException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ControlEnableState;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.preference.IPersistentPreferenceStore;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbenchPropertyPage;
import org.eclipse.ui.PlatformUI;
import org.sidiff.integration.preferences.ui.internal.PreferencesUiPlugin;
import org.sidiff.integration.preferences.util.PreferenceStoreUtil;
import org.sidiff.integration.preferences.util.PreferenceStoreUtil.IProjectSpecificSettingsToggleListener;

/**
 * Abstract superclass for all preference pages, that are both a property and a preference page.
 * The page will automatically use the correct preference store.
 * If the page is a property page, a button to enable project specific settings is shown (only in root page).
 * @author rmueller
 */
public abstract class PropertyAndPreferencePage extends PreferencePage
					implements IWorkbenchPreferencePage, IWorkbenchPropertyPage {

	private PropertyAndPreferencePage parentPage;
	private IProject project;
	private boolean useSpecificSettings;
	private Button useSpecificSettingsButton;
	private Control preferenceControl;
	private ControlEnableState enableState;
	private IProjectSpecificSettingsToggleListener projectSpecificSettingsListener;

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

	/**
	 * Returns the ID of the help context to display for this page.
	 * @return the help context id
	 */
	protected abstract String getHelpContextId();

	@Override
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().applyTo(container);

		if(isPropertiesPage()) {
			if(isTopLevelPage()) {
				createControlButton(container);
			} else {
				useSpecificSettings = parentPage.useSpecificSettings();
			}
			registerSpecificSettingsListener();
		}

		preferenceControl = doCreateContents(container);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(preferenceControl);
		reloadPreferences();

		if(isPropertiesPage() && isTopLevelPage()) {
			updateEnabledState();
		}

		return container;
	}
	
	@Override
	protected Point doComputeSize() {
		Point computedSize = super.doComputeSize();
		Point minSize = new Point(600, 400);
		if(computedSize.x < minSize.x || computedSize.y < minSize.y) {
			return minSize;
		}
		return computedSize;
	}

	private void createControlButton(Composite container) {
		useSpecificSettingsButton = new Button(container, SWT.CHECK);
		try {
			useSpecificSettings = PreferenceStoreUtil.useSpecificSettings(project, getPreferenceQualifier());
			useSpecificSettingsButton.setSelection(useSpecificSettings);
			useSpecificSettingsButton.setText("Use project specific settings");
			useSpecificSettingsButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent event) {
					try {
						setUseSpecificSettings(useSpecificSettingsButton.getSelection());
					} catch (CoreException e) {
						ErrorDialog.openError(getShell(), "Enabling/disabling project specific settings failed", null, e.getStatus());
						useSpecificSettingsButton.setSelection(!useSpecificSettingsButton.getSelection());
					}
				}
			});
		} catch (CoreException e) {
			useSpecificSettingsButton.setEnabled(false);
			useSpecificSettingsButton.setText("Use project specific settings [not possible: " + e.getMessage() + "]");
		}
		useSpecificSettingsButton.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
	}

	private void registerSpecificSettingsListener() {
		projectSpecificSettingsListener = (IProject project, boolean use) -> {
			if(getElement() != project)
				return;
			useSpecificSettings = use;
			if(useSpecificSettingsButton != null) {
				useSpecificSettingsButton.setSelection(use);
			}
			setPreferenceStore(null); // update preference store
			reloadPreferences();
			if(isTopLevelPage()) {
				updateEnabledState();
			}
		};
		PreferenceStoreUtil.addProjectSpecificSettingsListener(projectSpecificSettingsListener);
	}

	protected void setUseSpecificSettings(boolean selection) throws CoreException {
		if(!isPropertiesPage())
			return;
		PreferenceStoreUtil.setUseSpecificSettings(project, getPreferenceQualifier(), selection);
	}

	protected boolean useSpecificSettings() {
		return isPropertiesPage() && useSpecificSettings;
	}

	protected void setParentPage(PropertyAndPreferencePage parentPage) {
		this.parentPage = parentPage;
	}

	protected boolean isTopLevelPage() {
		return parentPage == null;
	}

	private void updateEnabledState() {
		if(useSpecificSettings) {
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
	 * Returns whether the page is a property page, i.e. if it is associated with a project.
	 * @return whether the page is a property page
	 */
	protected boolean isPropertiesPage() {
		return project != null;
	}

	/**
	 * Returns the preference store of this preference/property page.
	 * If this page has a parent page, the call is delegated to the parent page.
	 * Else, the default preference store for project specific / global preferences is returned.
	 */
	@Override
	protected IPreferenceStore doGetPreferenceStore() {
		if(parentPage != null) {
			return parentPage.doGetPreferenceStore();
		} else if(isPropertiesPage()) {
			return PreferenceStoreUtil.getPreferenceStore(project, getPreferenceQualifier());
		} else {
			return PreferenceStoreUtil.getPreferenceStore(getPreferenceQualifier());
		}
	}

	public String getPreferenceQualifier() {
		return PreferenceStoreUtil.PREFERENCE_QUALIFIER;
	}

	@Override
	public void init(IWorkbench workbench) {
	}

	@Override
	public boolean performOk() {
		validatePreferences();
		if(isValid()) {
			savePreferences();
			try {
				IPreferenceStore store = doGetPreferenceStore();
				if(store instanceof IPersistentPreferenceStore) {
					((IPersistentPreferenceStore)store).save();
				}
				return true;
			} catch (IOException e) {
				ErrorDialog.openError(getShell(), "Saving preferences failed", null,
						new Status(IStatus.ERROR, PreferencesUiPlugin.PLUGIN_ID, "Preference store could not be saved", e));
			}
		}
		return false;
	}

	@Override
	protected void performDefaults() {
		defaultPreferences();
		super.performDefaults();
	}

	@Override
	public void performHelp() {
		final String id = getHelpContextId();
		if(id != null) {
			PlatformUI.getWorkbench().getHelpSystem().displayHelp(id);
		}
	}

	@Override
	public void dispose() {
		if(projectSpecificSettingsListener != null) {
			PreferenceStoreUtil.removeProjectSpecificSettingsListener(projectSpecificSettingsListener);
		}
		super.dispose();
	}
}
