package org.sidiff.integration.preferences.domains.uml;

import org.eclipse.jface.util.PropertyChangeEvent;
import org.sidiff.integration.preferences.domains.AbstractSidiffDomainSettingsPluginPage;

/**
 * 
 * Main class for the UML specific settings.
 * @author Daniel Roedder
 */
public class Uml extends AbstractSidiffDomainSettingsPluginPage {
	
	
	/**
	 * Superclass method, not needed here
	 * @see org.eclipse.jface.util.IPropertyChangeListener#propertyChange(org.eclipse.jface.util.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent event) {
	}

	/**
	 * @see org.sidiff.integration.preferences.domains.AbstractSidiffDomainSettingsPluginPage#getDocumentType()
	 */
	@Override
	protected String getDocumentType() {
		return "http://www.eclipse.org/uml2/5.0.0/UML";
	}

}
