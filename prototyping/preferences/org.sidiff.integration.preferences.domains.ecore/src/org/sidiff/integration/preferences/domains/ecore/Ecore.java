package org.sidiff.integration.preferences.domains.ecore;

import org.eclipse.jface.util.PropertyChangeEvent;
import org.sidiff.integration.preferences.domains.AbstractSidiffDomainSettingsPluginPage;

/**
 * 
 * Main class for the Ecore specific settings.
 * @author Daniel Roedder
 */
public class Ecore extends AbstractSidiffDomainSettingsPluginPage {

	/**
	 * @see org.sidiff.integration.preferences.domains.AbstractSidiffDomainSettingsPluginPage#getDocumentType()
	 */
	@Override
	protected String getDocumentType() {
		return "http://www.eclipse.org/emf/2002/Ecore";
	}

	/**
	 * Superclass method, not needed here
	 * @see org.eclipse.jface.util.IPropertyChangeListener#propertyChange(org.eclipse.jface.util.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		
	}

}
