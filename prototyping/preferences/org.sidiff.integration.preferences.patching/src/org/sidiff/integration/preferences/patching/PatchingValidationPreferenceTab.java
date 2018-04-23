package org.sidiff.integration.preferences.patching;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.sidiff.integration.preferences.fieldeditors.PreferenceField;
import org.sidiff.integration.preferences.fieldeditors.RadioBoxPreferenceField;
import org.sidiff.integration.preferences.interfaces.ISiDiffValidationPreferenceTab;
import org.sidiff.integration.preferences.valueconverters.EnumPreferenceValueConverter;
import org.sidiff.patching.settings.PatchingSettings;

/**
 * Class for the validation patching settings.
 * @author Daniel Roedder, Robert Müller
 *
 */
public class PatchingValidationPreferenceTab implements ISiDiffValidationPreferenceTab {
	
	/**
	 * List to hold all {@link org.sidiff.integration.preferences.fieldeditors.PreferenceField}
	 */
	private List<PreferenceField> fieldList;
	
	/**
	 * The {@link IPreferenceStore} to be used
	 */
	private IPreferenceStore store;
	
	/**
	 * The {@link PreferenceField} for the validation mode setting
	 */
	private PreferenceField validationMode;

	/**
	 * @see org.sidiff.integration.preferences.interfaces.ISiDiffOrderableTab#getStepInPipeline()
	 */
	@Override
	public int getStepInPipeline() {
		return 4;
	}

	/**
	 * @see org.sidiff.integration.preferences.interfaces.ISiDiffValidationPreferenceTab#getTitle()
	 */
	@Override
	public String getTitle() {
		return "Patching";
	}

	/**
	 * Superclass method, not needed here
	 * @see org.sidiff.integration.preferences.interfaces.ISiDiffValidationPreferenceTab#propertyChange(org.eclipse.jface.util.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent event) {
	}

	/**
	 * @see org.sidiff.integration.preferences.interfaces.ISiDiffValidationPreferenceTab#getTabContent()
	 */
	@Override
	public Iterable<PreferenceField> getTabContent() {
		fieldList = new ArrayList<PreferenceField>();

		validationMode = RadioBoxPreferenceField.create("validationMode", "Validation Mode",
				PatchingSettings.ValidationMode.values(), new EnumPreferenceValueConverter());
		fieldList.add(validationMode);
		
		for (PreferenceField field : fieldList) {
			field.setPreferenceStore(store);
		}
		return fieldList;
	}

	/**
	 * @see org.sidiff.integration.preferences.interfaces.ISiDiffValidationPreferenceTab#setPreferenceStore(org.eclipse.jface.preference.IPreferenceStore)
	 */
	@Override
	public void setPreferenceStore(IPreferenceStore store) {
		this.store = store;
	}
}
