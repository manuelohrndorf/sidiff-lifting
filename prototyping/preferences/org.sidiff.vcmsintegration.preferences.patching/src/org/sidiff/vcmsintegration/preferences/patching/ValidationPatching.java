package org.sidiff.vcmsintegration.preferences.patching;

import java.util.ArrayList;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.sidiff.patching.settings.PatchingSettings;
import org.sidiff.patching.settings.PatchingSettings.ValidationMode;
import org.sidiff.vcmsintegration.preferences.fieldeditors.PreferenceField;
import org.sidiff.vcmsintegration.preferences.fieldeditors.RadioBoxPreferenceField;
import org.sidiff.vcmsintegration.preferences.fieldeditors.ValueAndLabelProvider;
import org.sidiff.vcmsintegration.preferences.interfaces.ISiDiffValidationPreferenceTab;

/**
 * Class for the validation patching settings.
 * @author Daniel Roedder
 *
 */
public class ValidationPatching implements ISiDiffValidationPreferenceTab {
	
	/**
	 * List to hold all {@link org.sidiff.vcmsintegration.preferences.fieldeditors.PreferenceField}
	 */
	private ArrayList<PreferenceField> fieldList;
	
	/**
	 * The {@link IPreferenceStore} to be used
	 */
	private IPreferenceStore store;
	
	/**
	 * The {@link PreferenceField} for the validation mode setting
	 */
	private PreferenceField validationMode;

	/**
	 * @see org.sidiff.vcmsintegration.preferences.interfaces.ISiDiffOrderableTab#getStepInPipeline()
	 */
	@Override
	public int getStepInPipeline() {
		return 4;
	}

	/**
	 * @see org.sidiff.vcmsintegration.preferences.interfaces.ISiDiffValidationPreferenceTab#getTitle()
	 */
	@Override
	public String getTitle() {
		return "Patching";
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
		
		validationMode = RadioBoxPreferenceField.createFromArray("validationMode", "Validation Mode", PatchingSettings.ValidationMode.values(), new ValueAndLabelProvider<ValidationMode>() {
			@Override public String[] get(ValidationMode input) {return new String[]{input.toString(), input.toString()};}
		});
		fieldList.add(validationMode);
		
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
