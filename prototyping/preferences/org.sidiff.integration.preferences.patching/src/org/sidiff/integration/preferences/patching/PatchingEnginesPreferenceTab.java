package org.sidiff.integration.preferences.patching;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.sidiff.integration.preferences.fieldeditors.NumberPreferenceField;
import org.sidiff.integration.preferences.fieldeditors.PreferenceField;
import org.sidiff.integration.preferences.fieldeditors.RadioBoxPreferenceField;
import org.sidiff.integration.preferences.interfaces.ISiDiffEnginesPreferenceTab;
import org.sidiff.integration.preferences.valueconverters.EnumPreferenceValueConverter;
import org.sidiff.patching.settings.ExecutionMode;
import org.sidiff.patching.settings.PatchMode;

/**
 * 
 * Class for the patching settings tab.
 * @author Daniel Roedder, Robert Müller
 */
public class PatchingEnginesPreferenceTab implements ISiDiffEnginesPreferenceTab{
	
	/**
	 * The {@link IPreferenceStore} to be used
	 */
	private IPreferenceStore store;
	
	/**
	 * List to hold all {@link org.sidiff.integration.preferences.fieldeditors.PreferenceField}
	 */
	private List<PreferenceField> fieldList;
	
	/**
	 * The {@link PreferenceField} for the execution Mode setting
	 */
	private PreferenceField executionMode;
	
	/**
	 * The {@link PreferenceField} for the patch mode setting
	 */
	private PreferenceField patchMode;
	
	/**
	 * The {@link PreferenceField} for the minimum reliability setting
	 */
	private PreferenceField minReliability;

	/**
	 * @see org.sidiff.integration.preferences.interfaces.ISiDiffEnginesPreferenceTab#getTitle()
	 */
	@Override
	public String getTitle() {
		return "Patching";
	}

	/**
	 * Superclass method, not needed here
	 * @see org.sidiff.integration.preferences.interfaces.ISiDiffEnginesPreferenceTab#propertyChange(org.eclipse.jface.util.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent event) {
	}

	/**
	 * @see org.sidiff.integration.preferences.interfaces.ISiDiffEnginesPreferenceTab#getTabContent()
	 */
	@Override
	public Iterable<PreferenceField> getTabContent() {
		fieldList = new ArrayList<PreferenceField>();

		executionMode = RadioBoxPreferenceField.create("executionMode", "Execution Mode",
				ExecutionMode.values(), new EnumPreferenceValueConverter());
		fieldList.add(executionMode);
		
		patchMode = RadioBoxPreferenceField.create("patchMode", "Patch Mode",
				PatchMode.values(), new EnumPreferenceValueConverter());
		fieldList.add(patchMode);
		
		minReliability = new NumberPreferenceField("minReliability", "Minimum Reliability");
		fieldList.add(minReliability);
		
		for (PreferenceField field : fieldList) {
			field.setPreferenceStore(store);
		}

		return fieldList;
	}

	/**
	 * @see org.sidiff.integration.preferences.interfaces.ISiDiffEnginesPreferenceTab#setPreferenceStore(org.eclipse.jface.preference.IPreferenceStore)
	 */
	@Override
	public void setPreferenceStore(IPreferenceStore store) {
		this.store = store;
	}

	/**
	 * @see org.sidiff.integration.preferences.interfaces.ISiDiffEnginesPreferenceTab#getStepInPipeline()
	 */
	@Override
	public int getStepInPipeline() {
		return 3;
	}
}
