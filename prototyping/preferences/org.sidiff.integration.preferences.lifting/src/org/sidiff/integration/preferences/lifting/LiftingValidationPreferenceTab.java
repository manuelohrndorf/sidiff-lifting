package org.sidiff.integration.preferences.lifting;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.sidiff.integration.preferences.fieldeditors.PreferenceField;
import org.sidiff.integration.preferences.interfaces.ISiDiffValidationPreferenceTab;

/**
 * Class for the lifting validation settings
 * @author Daniel Roedder, Robert Müller
 *
 */
public class LiftingValidationPreferenceTab implements ISiDiffValidationPreferenceTab {

	/**
	 * List to hold all {@link org.sidiff.integration.preferences.fieldeditors.PreferenceField}
	 */
	private List<PreferenceField> fieldList;

	/**
	 * The {@link IPreferenceStore} to be used
	 */
	private IPreferenceStore store;

	/**
	 * @see org.sidiff.integration.preferences.interfaces.ISiDiffOrderableTab#getStepInPipeline()
	 */
	@Override
	public int getStepInPipeline() {
		return 3;
	}

	/**
	 * @see org.sidiff.integration.preferences.interfaces.ISiDiffValidationPreferenceTab#getTitle()
	 */
	@Override
	public String getTitle() {
		return "Lifting";
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
