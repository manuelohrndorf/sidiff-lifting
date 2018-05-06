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
	 * The created control
	 */
	private Control control;

	/**
	 * The layout data of the control. Must be {@link GridData} for {@link #setVisible(boolean)} to work.
	 */
	private GridData layoutData;

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
		control = doCreateControls(parent, title);
		layoutData = createLayoutData();
		control.setLayoutData(layoutData);
	}

	/**
	 * Creates layout data for the control created by {@link #doCreateControls(Composite, String)}.
	 * The default layout data will let the control fill the horizontal space.
	 * @return grid data
	 */
	protected GridData createLayoutData() {
		return new GridData(SWT.FILL, SWT.TOP, true, false);
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
	 * Makes the preferences field visible/invisible. When it is invisible, it takes up no space.
	 * @param visible <code>true</code> to make it visible, <code>false</code> to hide it
	 */
	public void setVisible(boolean visible) {
		control.setVisible(visible);
		layoutData.exclude = !visible;
		control.pack();
	}

	/**
	 * subclasses may override this if the need to check for validity before saving
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
