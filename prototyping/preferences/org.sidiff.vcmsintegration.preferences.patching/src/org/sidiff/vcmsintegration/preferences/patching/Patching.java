package org.sidiff.vcmsintegration.preferences.patching;

import java.util.ArrayList;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.sidiff.patching.settings.ExecutionMode;
import org.sidiff.patching.settings.PatchMode;
import org.sidiff.vcmsintegration.preferences.fieldeditors.NumberPreferenceField;
import org.sidiff.vcmsintegration.preferences.fieldeditors.PreferenceField;
import org.sidiff.vcmsintegration.preferences.fieldeditors.RadioBoxPreferenceField;
import org.sidiff.vcmsintegration.preferences.fieldeditors.ValueAndLabelProvider;
import org.sidiff.vcmsintegration.preferences.interfaces.ISiDiffEnginesPreferenceTab;

/**
 * 
 * Class for the patching settings tab.
 * @author Daniel Roedder
 */
public class Patching implements ISiDiffEnginesPreferenceTab{
	
	/**
	 * The {@link IPreferenceStore} to be used
	 */
	private IPreferenceStore store;
	
	/**
	 * List to hold all {@link org.sidiff.vcmsintegration.preferences.fieldeditors.PreferenceField}
	 */
	private ArrayList<PreferenceField> fieldList;
	
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
	 * @see org.sidiff.vcmsintegration.preferences.interfaces.ISiDiffEnginesPreferenceTab#getTitle()
	 */
	@Override
	public String getTitle() {
		return "Patching";
	}

	/**
	 * Superclass method, not needed here
	 * @see org.sidiff.vcmsintegration.preferences.interfaces.ISiDiffEnginesPreferenceTab#propertyChange(org.eclipse.jface.util.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent event) {

	}

	/**
	 * @see org.sidiff.vcmsintegration.preferences.interfaces.ISiDiffEnginesPreferenceTab#getTabContent()
	 */
	@Override
	public Iterable<PreferenceField> getTabContent() {
		fieldList = new ArrayList<PreferenceField>();
	
		
		
		executionMode = RadioBoxPreferenceField.createFromArray("executionMode", "Execution Mode", ExecutionMode.values(), new ValueAndLabelProvider<ExecutionMode>() {
			@Override public String[] get(ExecutionMode input) {return new String[]{input.toString(), input.toString()};}
		});
		
		fieldList.add(executionMode);
		
		
		patchMode = RadioBoxPreferenceField.createFromArray("patchMode", "Patch Mode", PatchMode.values(), new ValueAndLabelProvider<PatchMode>() {
			@Override public String[] get(PatchMode input) {return new String[]{input.toString(), input.toString()};}
		});
		
		fieldList.add(patchMode);
		

		minReliability = new NumberPreferenceField("minReliability", "Minimum Reliability");
		fieldList.add(minReliability);
		
		

		
		for (PreferenceField field : fieldList) {
			field.setPreferenceStore(store);
		}
		
		return fieldList;
		
		
		
		
	}

	/**
	 * @see org.sidiff.vcmsintegration.preferences.interfaces.ISiDiffEnginesPreferenceTab#setPreferenceStore(org.eclipse.jface.preference.IPreferenceStore)
	 */
	@Override
	public void setPreferenceStore(IPreferenceStore store) {
		this.store = store;
	}

	/**
	 * @see org.sidiff.vcmsintegration.preferences.interfaces.ISiDiffEnginesPreferenceTab#getStepInPipeline()
	 */
	@Override
	public int getStepInPipeline() {
		return 3;
	}

}
