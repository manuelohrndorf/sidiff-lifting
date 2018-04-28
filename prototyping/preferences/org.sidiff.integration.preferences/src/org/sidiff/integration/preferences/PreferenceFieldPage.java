package org.sidiff.integration.preferences;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbenchPropertyPage;
import org.sidiff.integration.preferences.fieldeditors.PreferenceField;
import org.sidiff.integration.preferences.util.PreferenceStoreUtil;

/**
 * 
 * Abstract class for a PreferencePage and a PropertyPage
 * Handles loading and saving from and to the PreferenceStore/ResourceProperties
 * @author Felix Breitweiser, Robert Müller
 */
public abstract class PreferenceFieldPage extends PreferencePage implements IWorkbenchPropertyPage, IWorkbenchPreferencePage {

	/**
	 * Static list to hold all pages 
	 */
	private static List<PreferenceFieldPage> allPages;

	private IProject project;
	private Button useButton;

	public PreferenceFieldPage() {
		super();
		register();
	}

	public PreferenceFieldPage(String title) {
		super(title);
		register();
	}

	public PreferenceFieldPage(String title, ImageDescriptor image) {
		super(title, image);
		register();
	}

	/**
	 * register this page in the list of all pages
	 * necessary in order to keep usage of resource specific settings consistent across all pages
	 */
	private void register() {
		if(allPages == null) {
			allPages = new ArrayList<PreferenceFieldPage>();
		}
		allPages.add(this);
	}

	protected abstract Iterable<PreferenceField> getAllFields();
	protected abstract Control doCreateContents(Composite parent);

	/**
	 * creates Checkbox indicating whether or not to use resource specific settings
	 */
	private void createOverrideControls(Composite parent) {
		useButton = new Button(parent, SWT.CHECK);
		useButton.setText("Use project specific settings");
		useButton.setSelection(PreferenceStoreUtil.hasSpecificSettings(project));
		useButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				useResourceSettings(useButton.getSelection());
			}
		});
	}

	/**
	 * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
	 * 
	 * Creates the override checkbox and the contents of the page
	 */
	@Override
	protected Control createContents(Composite parent) {

		if(isPropertiesPage()) {
			createOverrideControls(parent);
		}

		Control c = doCreateContents(parent);

		if(isPropertiesPage()) {
			useResourceSettings(PreferenceStoreUtil.hasSpecificSettings(project));
		}
		return c;
	}

	/**
	 * @param use whether or not to use resource specific settings
	 * if this represents a property page this method enables/disables resource specific settings
	 * upon disabling, existing resource settings get saved to the resource
	 * All controls will be disabled and preference store settings will be loaded
	 * this is done for all other preference pages equally  
	 */
	private void useResourceSettings(boolean use) {
		for(PreferenceFieldPage page : allPages) {
			page.doUseResourceSettings(use);
		}
	}
	
	/**
	 * Toggle usage of Resource specific settings
	 * Saves all resource setting before switching to global settings
	 * 
	 * @param use true if Resource specific settings should be used
	 * @return
	 */
	private boolean doUseResourceSettings(boolean use) {
		if(isPropertiesPage() && useButton != null) {
			useButton.setSelection(use);
			if(PreferenceStoreUtil.hasSpecificSettings(project) && !use) {
				for(PreferenceField pf : getAllFields()) {
					pf.save();
				}
			}

			try {
				PreferenceStoreUtil.setUseSpecificSettings(project, use);
			} catch (CoreException e) {
				e.printStackTrace();
				// setting the property failed, revert changes
				use = !use;
				useButton.setSelection(use);
			}

			for(PreferenceField pf : getAllFields()) {
				pf.setEnabled(use);
				pf.load();
			}
			return use;
		}
		return false;
	}

	/**
	 * Checks all fields for validity
	 */
	protected void checkState() {
		for(PreferenceField pf :  getAllFields()) {
			if(!pf.isValid()) {
				setValid(false);
				return;
			}
		}
		setValid(true);
	}

	/**
	 * @see org.eclipse.jface.preference.PreferencePage#performDefaults()
	 * default settings will be loaded
	 */
	@Override
	protected void performDefaults() {
		for(PreferenceField pf : getAllFields()) {
			pf.loadDefault();
		}
		super.performDefaults();
	}

	/**
	 * @see org.eclipse.jface.preference.PreferencePage#performOk()
	 */
	@Override
	public boolean performOk() {
		for(PreferenceField pf : getAllFields()) {
			pf.save();
		}
        return true;
    }

	/**
	 * @see org.eclipse.ui.IWorkbenchPropertyPage#getElement()
	 */
	@Override
	public IAdaptable getElement() {
		return project;
	}

	/**
	 * @see org.eclipse.ui.IWorkbenchPropertyPage#setElement(org.eclipse.core.runtime.IAdaptable)
	 */
	@Override
	public void setElement(IAdaptable element) {
		project = (IProject)element;
		setPreferenceStore(null); // null so that doGetPreferenceStore will be called again
	}

	/**
	 * @return true if this is a property page (for a resource)
	 */
	private boolean isPropertiesPage() {
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
		// default implementation does nothing
	}

	@Override
	public void dispose() {
		super.dispose();
		allPages.remove(this);
	}
}
