package org.sidiff.integration.preferences;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbenchPropertyPage;
import org.sidiff.integration.preferences.fieldeditors.PreferenceField;
import org.sidiff.integration.preferences.util.PropertyStore;

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
	
	private IResource resource;
	private IPreferenceStore store;
	private Button use;

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
		if(allPages == null) allPages = new ArrayList<PreferenceFieldPage>();
		allPages.add(this);
	} 
	
	protected abstract Iterable<PreferenceField> getAllFields();
	protected abstract Control doCreateContents(Composite parent);
	
	/**
	 * creates Checkbox indicating whether or not to use resource specific settings
	 */
	private void createOverrideControls(Composite parent) {
		use = new Button(parent, SWT.CHECK);
		use.setText("Use Properties");
		use.setSelection(getPropertyStore().isUseResourceSettings());
		use.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				useResourceSettings(use.getSelection());
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
			
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
			useResourceSettings(getPropertyStore().isUseResourceSettings());
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
	private boolean useResourceSettings(boolean use) {
		boolean ok = true;
		for(PreferenceFieldPage page : allPages)
			ok &= page.doUseResourceSettings(use);
		return ok;
	}
	
	/**
	 * Toggle usage of Resource specific settings
	 * Saves all resource setting before switching to global settings
	 * 
	 * @param use true if Resource specific settings should be used
	 * @return
	 */
	private boolean doUseResourceSettings(boolean use) {
		if(isPropertiesPage()) {
			this.use.setSelection(use);
			if(getPropertyStore().isUseResourceSettings() && !use) {
				for(PreferenceField pf : getAllFields()) {
					pf.save();
				}
			}
			getPropertyStore().setUseResourceSettings(use);

			use = getPropertyStore().isUseResourceSettings(); // update the value, as the last operation may have failed
			for(PreferenceField pf : getAllFields()) {
				if(!use) {
					pf.disable();
				} else {
					pf.enable();
				}
				pf.load();
			}
			return use;
		}
		else return false;
	}

	/**
	 * Checks all fields for validity
	 */
	protected void checkState() {
		boolean valid = true;
		search:
			for(PreferenceField pf :  getAllFields()) {
				valid = valid && pf.isValid();
				break search;
			}
		setValid(valid);
	}

	/**
	 * @see org.eclipse.jface.preference.PreferencePage#performDefaults()
	 * if this represents a property page resouce specific settings will simply be disabled
	 * otherwise default settings will be loaded
	 */
	@Override
	protected void performDefaults() {
		if(isPropertiesPage())
			useResourceSettings(false);
		else {
			for(PreferenceField pf : getAllFields()) {
				pf.loadDefault();
			}
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
		return resource;
	}

	/**
	 * @see org.eclipse.ui.IWorkbenchPropertyPage#setElement(org.eclipse.core.runtime.IAdaptable)
	 */
	@Override
	public void setElement(IAdaptable element) {
		resource = (IResource) element;
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
	}
	
	/**
	 * @return true if this is a property page (for a resource)
	 */
	private boolean isPropertiesPage() {
		return resource != null;
	}
	
	/**
	 * @see org.eclipse.jface.preference.PreferencePage#getPreferenceStore()
	 * if this represents a property page, a property store will be loaded
	 * otherwise the previously set preference store will be loaded
	 */
	@Override
	public IPreferenceStore getPreferenceStore() {
		if(store == null) {
			if(isPropertiesPage()) {
				store = new PropertyStore(resource, super.getPreferenceStore());
			} else {
				store = super.getPreferenceStore();
			}
		}
		return store;
	}
	
	/**
	 * @return the PropertyStore 
	 */
	public PropertyStore getPropertyStore() {
		if(getPreferenceStore() instanceof PropertyStore) return (PropertyStore) getPreferenceStore();
		return null;
	}

	/**
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
	}
	
	@Override
	public void dispose() {
		super.dispose();
		allPages.remove(this);
	}
}
