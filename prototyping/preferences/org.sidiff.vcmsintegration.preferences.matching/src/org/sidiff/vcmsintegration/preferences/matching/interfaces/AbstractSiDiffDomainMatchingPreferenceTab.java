package org.sidiff.vcmsintegration.preferences.matching.interfaces;

import java.util.ArrayList;

import org.eclipse.jface.util.PropertyChangeEvent;
import org.sidiff.vcmsintegration.preferences.domains.interfaces.ISiDiffDomainPreferenceTab;
import org.sidiff.vcmsintegration.preferences.fieldeditors.PreferenceField;

/**
 * 
 * Abstract class for the domain specific matching settings.
 * @author Daniel Roedder
 */
public abstract class AbstractSiDiffDomainMatchingPreferenceTab implements ISiDiffDomainPreferenceTab {

	/**
	 * List to hold all {@link org.sidiff.vcmsintegration.preferences.fieldeditors.PreferenceField}
	 */
	private ArrayList<PreferenceField> fieldList;
	

	/**
	 * @see org.sidiff.vcmsintegration.preferences.domains.interfaces.ISiDiffDomainPreferenceTab#getTitle()
	 */
	@Override
	public String getTitle() {
		return "Matching";
	}

	/**
	 * Superclass method, not needed here
	 * @see org.sidiff.vcmsintegration.preferences.domains.interfaces.ISiDiffDomainPreferenceTab#propertyChange(org.eclipse.jface.util.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent event) {

	}

	/**
	 * @see org.sidiff.vcmsintegration.preferences.domains.interfaces.ISiDiffDomainPreferenceTab#getTabContent()
	 */
	@Override
	public Iterable<PreferenceField> getTabContent() {
		fieldList = new ArrayList<PreferenceField>();
		
		// Add domain specific matching settings objects when they exist
		
		return fieldList;
	}

	/**
	 * Not implemented interface method.
	 * Subclasses must implement.
	 */
	@Override
	public abstract String getDocumentType();
	
	

}
