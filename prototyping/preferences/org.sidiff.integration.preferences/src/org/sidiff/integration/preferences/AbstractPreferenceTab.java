package org.sidiff.integration.preferences;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.sidiff.integration.preferences.fieldeditors.PreferenceField;
import org.sidiff.integration.preferences.interfaces.ISiDiffPreferenceTab;

/**
 * 
 * @author Daniel Roedder, Robert Müller
 *
 */
public abstract class AbstractPreferenceTab implements ISiDiffPreferenceTab {
	/**
	 * The {@link IPreferenceStore} to be used
	 */
	private IPreferenceStore store;

	/**
	 * List to hold all {@link org.sidiff.integration.preferences.fieldeditors.PreferenceField}
	 */
	private List<PreferenceField> fieldList;

	@Override
	public void setPreferenceStore(IPreferenceStore store) {
		this.store = store;
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		// default implementation does nothing
	}

	@Override
	public Iterable<PreferenceField> getTabContent() {
		if(fieldList == null) {
			fieldList = new ArrayList<PreferenceField>();
			createPreferenceFields();
		}
		return fieldList;
	}

	protected void addField(PreferenceField field) {
		field.setPreferenceStore(store);
		fieldList.add(field);
	}

	protected abstract void createPreferenceFields();
}
