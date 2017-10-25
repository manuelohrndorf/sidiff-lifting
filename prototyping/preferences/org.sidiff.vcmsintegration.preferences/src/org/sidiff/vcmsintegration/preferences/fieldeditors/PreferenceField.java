package org.sidiff.vcmsintegration.preferences.fieldeditors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.widgets.Group;

/**
 * PreferenceField for use in a PreferenceFieldPage
 * responsible for storing and loading a preference as well as creating controls for changing the preference
 * @author Felix Breitweiser
 */
public abstract class PreferenceField {
	
	/**
	 * all preferences will be saved into/loaded from this store 
	 */
	private IPreferenceStore store;
	
	/**
	 * name of the preference in the store
	 */
	private String preferenceName;
	
	/**
	 * human readable title of the preference 
	 */
	private String title;
	
	/**
	 * Groupt into which controls can be created
	 */
	private Group parent;
	
	/**
	 * true, if the preference can be changed
	 */
	private boolean enabled;
	
	/**
	 * all registered IPropertyChangeListener
	 */
	private List<IPropertyChangeListener> listeners;
	
	/**
	 * @param preferenceName name of the preference in the store 
	 * @param title human readable title of the preference
	 */
	public PreferenceField(String preferenceName, String title) {
		this.preferenceName = preferenceName;
		this.title = title;
		listeners = new ArrayList<IPropertyChangeListener>();
		enabled = true;
	}
	
	/**
	 * changes the preference store to be used
	 * @param store the preference store to use
	 */
	public void setPreferenceStore(IPreferenceStore store) {
		this.store = store;
	}
	
	/**
	 * load preference from store into the field
	 */
	public void load() {
		if(store == null) return;
		doLoad(store, preferenceName);
	}
	
	/**
	 * load default preference from store into the field
	 */
	public void loadDefault() {
		if(store == null) return;
		doLoadDefault(store, preferenceName);
	}
	
	/**
	 * save current preference into store
	 */
	public void save() {
		if(store == null) return;
		doSave(store, preferenceName);
	}
	
	/**
	 * creates the controls for this field
	 * @param parent groutp into which controls can be created
	 */
	public void createControls(Group parent) {
		this.parent = parent;
		doCreateControls(parent, title);
		updateEnabledState();
	}
	
	/**
	 * sublcasses should implement this to load the preference into their control
	 * @param store the store to be used
	 * @param preferenceName the name of the preference to be loaded
	 */
	protected abstract void doLoad(IPreferenceStore store, String preferenceName);
	
	/**
	 * sublcasses should implement this to load the default preference into their control
	 * @param store the store to be used
	 * @param preferenceName the name of the preference to be loaded
	 */
	protected abstract void doLoadDefault(IPreferenceStore store, String preferenceName);
	
	/**
	 * sublcasses should implement this to save the preference from their control
	 * @param store the store to be used
	 * @param preferenceName the name of the preference to be stored
	 */
	protected abstract void doSave(IPreferenceStore store, String preferenceName);
	
	/**
	 * sublcasses should implement this to create all their fields
	 * @param parent the Group, into which controls can be created
	 * @param title human readable title to be used
	 */
	protected abstract void doCreateControls(Group parent, String title);
	
	/**
	 * enables/disables the field 
	 * @param enabled true if the field is enabled
	 */
	public abstract void setEnabled(boolean enabled);
	
	/**
	 * sublcasses may override this if the need to check for validity before saving
	 * @return true if the field contains a valid value
	 */
	public boolean isValid() { return true; }
	
	/**
	 * helper method to fire a PropertyChangedEvent
	 * @param oldValue the old value
	 * @param newValue the new value
	 */
	protected void firePropertyChanged(Object oldValue, Object newValue) {
		if(oldValue != newValue) {
			for(IPropertyChangeListener listener : listeners) {
				listener.propertyChange(new PropertyChangeEvent(this, preferenceName, oldValue, newValue));
			}
		}
	}
	
	/**
	 * registers an IPropertyChangeListener
	 * @param listener the IPropertyChangeListener to be called in case of changes
	 */
	public void addPropertyChangeListener(IPropertyChangeListener listener) {
		listeners.add(listener);
	}
	
	/**
	 * removes an IPropertyChangeListener
	 * @param listener the IPropertyChangeListener to be removed
	 */
	public void removePropertyChangeListener(IPropertyChangeListener listener) {
		listeners.remove(listener);
	}
	
	/**
	 * @return the name of the Preference in the store
	 */
	protected String getPreferenceName() {
		return preferenceName;
	}
	
	/**
	 * @return the human readable title of this preference
	 */
	protected String getTitle() {
		return title;
	}
	
	/**
	 * disables the PreferenceField
	 */
	public void disable() {
		enabled = false;
		updateEnabledState();
	}
	
	/**
	 * enables the PreferenceField
	 */
	public void enable() {
		enabled = true;
		updateEnabledState();
	}
	
	/**
	 * refreshes the controls enbale state
	 */
	public void updateEnabledState() {
		if(parent != null) {
			parent.setEnabled(enabled);
			setEnabled(enabled);
		}
	}

}
