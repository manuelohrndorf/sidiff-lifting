package org.sidiff.vcmsintegration.preferences.matching;

import java.util.ArrayList;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.sidiff.vcmsintegration.preferences.fieldeditors.PreferenceField;
import org.sidiff.vcmsintegration.preferences.interfaces.ISiDiffValidationPreferenceTab;

/**
 * Class for the validation matching settings.
 * @author Daniel Roedder
 *
 */
public class ValidationMatching implements ISiDiffValidationPreferenceTab {
	
	/**
	 * List to hold all {@link org.sidiff.vcmsintegration.preferences.fieldeditors.PreferenceField}
	 */
	private ArrayList<PreferenceField> fieldList;
	
	/**
	 * The {@link IPreferenceStore} to be used
	 */
	private IPreferenceStore store;

	/**
	 * @see org.sidiff.vcmsintegration.preferences.interfaces.ISiDiffOrderableTab#getStepInPipeline()
	 */
	@Override
	public int getStepInPipeline() {
		return 1;
	}

	/**
	 * @see org.sidiff.vcmsintegration.preferences.interfaces.ISiDiffValidationPreferenceTab#getTitle()
	 */
	@Override
	public String getTitle() {
		return "Matching";
	}

	/**
	 * Superclass method, not needed here
	 * @see org.sidiff.vcmsintegration.preferences.interfaces.ISiDiffValidationPreferenceTab#propertyChange(org.eclipse.jface.util.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent event) {
	}

	/**
	 * @see org.sidiff.vcmsintegration.preferences.interfaces.ISiDiffValidationPreferenceTab#getTabContent()
	 */
	@Override
	public Iterable<PreferenceField> getTabContent() {
		fieldList = new ArrayList<PreferenceField>();
		
		for (PreferenceField field : fieldList) {
			field.setPreferenceStore(store);
		}
		return fieldList;
	}

	/**
	 * @see org.sidiff.vcmsintegration.preferences.interfaces.ISiDiffValidationPreferenceTab#setPreferenceStore(org.eclipse.jface.preference.IPreferenceStore)
	 */
	@Override
	public void setPreferenceStore(IPreferenceStore store) {
		this.store = store;
	}

}
