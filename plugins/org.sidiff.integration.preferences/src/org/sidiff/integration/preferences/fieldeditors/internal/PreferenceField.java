package org.sidiff.integration.preferences.fieldeditors.internal;

import java.util.Objects;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.sidiff.integration.preferences.fieldeditors.IPreferenceField;
import org.sidiff.integration.preferences.util.PreferenceFieldUtil;

/**
 * PreferenceField for use in a PreferenceFieldPage.
 * Responsible for storing and loading a preference as well as creating controls for changing the preference.
 * @author Felix Breitweiser
 * @author rmueller
 */
public abstract class PreferenceField implements IPreferenceField {

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
	private ListenerList<IPropertyChangeListener> listeners = new ListenerList<>();

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
	}

	/**
	 * creates the controls for this field
	 * @param parent composite into which controls can be created
	 */
	@Override
	public Control createControls(Composite parent) {
		control = doCreateControls(parent);
		layoutData = createLayoutData();
		control.setLayoutData(layoutData);
		return control;
	}

	/**
	 * Returns this preference field's control, or <code>null</code> if it was not created yet.
	 * @return the preference field's control
	 */
	@Override
	public Control getControl() {
		return control;
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
	 * subclasses should implement this to create all their fields
	 * @param parent the Composite, into which controls can be created
	 * @return the created control
	 */
	protected abstract Control doCreateControls(Composite parent);

	/**
	 * Makes the preferences field visible/invisible. When it is invisible, it takes up no space.
	 * @param visible <code>true</code> to make it visible, <code>false</code> to hide it
	 */
	@Override
	public void setVisible(boolean visible) {
		if(control.isVisible() == visible) {
			return;
		}
		control.setVisible(visible);
		layoutData.exclude = !visible;
		PreferenceFieldUtil.requestLayout(control);
	}

	/**
	 * subclasses may override this if the need to check for validity before saving
	 * @return true if the field contains a valid value
	 */
	@Override
	public boolean isValid() { return true; }

	/**
	 * helper method to fire a PropertyChangedEvent
	 * @param oldValue the old value
	 * @param newValue the new value
	 */
	protected void firePropertyChanged(Object oldValue, Object newValue) {
		if(!Objects.equals(oldValue, newValue)) {
			for(IPropertyChangeListener listener : listeners) {
				listener.propertyChange(new PropertyChangeEvent(this, getPreferenceName(), oldValue, newValue));
			}
		}
	}

	/**
	 * registers an IPropertyChangeListener
	 * @param listener the IPropertyChangeListener to be called in case of changes
	 */
	@Override
	public void addPropertyChangeListener(IPropertyChangeListener listener) {
		listeners.add(listener);
	}

	/**
	 * removes an IPropertyChangeListener
	 * @param listener the IPropertyChangeListener to be removed
	 */
	@Override
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
}
