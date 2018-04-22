package org.sidiff.integration.preferences.lifting;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.sidiff.difference.lifting.api.settings.LiftingSettings.RecognitionEngineMode;
import org.sidiff.integration.preferences.fieldeditors.CheckBoxPreferenceField;
import org.sidiff.integration.preferences.fieldeditors.PreferenceField;
import org.sidiff.integration.preferences.fieldeditors.RadioBoxPreferenceField;
import org.sidiff.integration.preferences.interfaces.ISiDiffEnginesPreferenceTab;
import org.sidiff.integration.preferences.valueconverters.EnumPreferenceValueConverter;

/**
 * 
 * Class for the lifting settings tab.
 * @author Daniel Roedder, Robert Müller
 */
public class LiftingEnginesPreferenceTab implements ISiDiffEnginesPreferenceTab {
	
	/**
	 * The {@link IPreferenceStore} to be used
	 */
	private IPreferenceStore store;
	
	/**
	 * List to hold all {@link org.sidiff.integration.preferences.fieldeditors.PreferenceField}
	 */
	private List<PreferenceField> fieldList;
	
	/**
	 * The {@link PreferenceField} for the recognition engine mode selection
	 */
	private PreferenceField recognitionEngineModes;
	
	/**
	 * The {@link PreferenceField} for the calculate edit rule match setting
	 */
	private PreferenceField calculateEditRuleMatch;
	
	/**
	 * The {@link PreferenceField} for the serialize edit rule match setting
	 */
	private PreferenceField serializeEditRuleMatch;
	
	/**
	 * The {@link PreferenceField} for the merge imports setting
	 */
	private PreferenceField mergeImports;

	/**
	 * @see org.sidiff.integration.preferences.interfaces.ISiDiffEnginesPreferenceTab#getTitle()
	 */
	@Override
	public String getTitle() {
		return "Lifting";
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

		recognitionEngineModes = RadioBoxPreferenceField.create("recognitionEngineModes", "Recognition Engine Modes Services",
				RecognitionEngineMode.values(), new EnumPreferenceValueConverter());
		fieldList.add(recognitionEngineModes);

		calculateEditRuleMatch = new CheckBoxPreferenceField("calculateEditRuleMatch", "Calculate Edit Rule Match");
		fieldList.add(calculateEditRuleMatch);

		serializeEditRuleMatch = new CheckBoxPreferenceField("serializeEditRuleMatch", "Serialize Edit Rule Match");
		fieldList.add(serializeEditRuleMatch);

		for (PreferenceField field : fieldList) {
			field.setPreferenceStore(store);
		}

		mergeImports = new CheckBoxPreferenceField("mergeImports", "Merge Imports");
		fieldList.add(mergeImports);

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
		return 2;
	}
}
