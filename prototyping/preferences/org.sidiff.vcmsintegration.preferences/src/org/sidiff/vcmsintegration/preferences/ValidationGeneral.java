package org.sidiff.vcmsintegration.preferences;

import java.util.ArrayList;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.sidiff.vcmsintegration.preferences.fieldeditors.CheckBoxPreferenceField;
import org.sidiff.vcmsintegration.preferences.fieldeditors.PreferenceField;
import org.sidiff.vcmsintegration.preferences.interfaces.ISiDiffValidationPreferenceTab;

/**
 * Class for general validation settings
 * @author Daniel Roedder
 *
 */
public class ValidationGeneral implements ISiDiffValidationPreferenceTab {
	
	/**
	 * List to hold all Preference Fields
	 */
	private ArrayList<PreferenceField> fieldList;
	
	/**
	 * The used preference store
	 */
	private IPreferenceStore store;
	
	/**
	 * The {@link org.sidiff.vcmsintegration.preferences.fieldeditors.CheckBoxPreferenceField} for the validate models setting
	 */
	private CheckBoxPreferenceField validateModelsField;

	/**
	 * @see org.sidiff.vcmsintegration.preferences.interfaces.ISiDiffOrderableTab#getStepInPipeline()
	 */
	@Override
	public int getStepInPipeline() {
		return 0;
	}

	/**
	 * @see org.sidiff.vcmsintegration.preferences.interfaces.ISiDiffValidationPreferenceTab#getTitle()
	 */
	@Override
	public String getTitle() {
		return "General";
	}

	/**
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
		
		validateModelsField = new CheckBoxPreferenceField("validateModels", "Validate Models");
		
		fieldList.add(validateModelsField);
		
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
