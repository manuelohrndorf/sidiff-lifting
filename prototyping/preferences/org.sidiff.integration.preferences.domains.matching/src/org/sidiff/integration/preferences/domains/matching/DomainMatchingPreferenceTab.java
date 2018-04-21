package org.sidiff.integration.preferences.domains.matching;

import java.util.ArrayList;
import java.util.List;

import org.sidiff.integration.preferences.domains.AbstractDomainPreferenceTab;
import org.sidiff.integration.preferences.fieldeditors.PreferenceField;

/**
 * 
 * Abstract class for the domain specific matching settings.
 * @author Daniel Roedder, Robert Müller
 */
public class DomainMatchingPreferenceTab extends AbstractDomainPreferenceTab {

	/**
	 * List to hold all {@link org.sidiff.integration.preferences.fieldeditors.PreferenceField}
	 */
	private List<PreferenceField> fieldList;

	/**
	 * @see org.sidiff.integration.preferences.domains.interfaces.ISiDiffDomainPreferenceTab#getTitle()
	 */
	@Override
	public String getTitle() {
		return "Matching";
	}

	/**
	 * @see org.sidiff.integration.preferences.domains.interfaces.ISiDiffDomainPreferenceTab#getTabContent()
	 */
	@Override
	public Iterable<PreferenceField> getTabContent() {
		fieldList = new ArrayList<PreferenceField>();
		
		// Add domain specific matching settings objects when they exist
		
		return fieldList;
	}

	@Override
	public int getStepInPipeline() {
		return 0;
	}
}
