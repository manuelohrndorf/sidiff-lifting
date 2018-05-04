package org.sidiff.integration.preferences.fieldeditors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * PreferenceField for use in a PreferenceFieldPage
 * responsible for storing and loading a preference as well as creating controls for changing the preference
 * @author Felix Breitweiser, Robert Müller
 */
public abstract class PreferenceField {

	/**
	 * name of the preference in the store
	 */
	private String preferenceName;

	/**
	 * human readable title of the preference 
	 */
	private String title;

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
		this.listeners = new ArrayList<IPropertyChangeListener>();
	}

	/**
	 * creates the controls for this field
	 * @param parent composite into which controls can be created
	 */
	public void createControls(Composite parent) {
		Control control = doCreateControls(parent, title);
		control.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
	}

	/**
	 * subclasses should implement this to load the preference into their control
	 * @param store the store to be used
	 */
	public abstract void load(IPreferenceStore store);

	/**
	 * subclasses should implement this to load the default preference into their control
	 * @param store the store to be used
	 */
	public abstract void loadDefault(IPreferenceStore store);

	/**
	 * subclasses should implement this to save the preference from their control
	 * @param store the store to be used
	 */
	public abstract void save(IPreferenceStore store);

	/**
	 * subclasses should implement this to create all their fields
	 * @param parent the Composite, into which controls can be created
	 * @param title human readable title to be used
	 * @return the created control
	 */
	protected abstract Control doCreateControls(Composite parent, String title);

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
				listener.propertyChange(new PropertyChangeEvent(this, getPreferenceName(), oldValue, newValue));
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
	 * Sets the preference name to the given value.
	 * @param preferenceName the new preference name
	 */
	protected void setPreferenceName(String preferenceName) {
		this.preferenceName = preferenceName;
	}

	/**
	 * @return the human readable title of this preference
	 */
	protected String getTitle() {
		return title;
	}
}
