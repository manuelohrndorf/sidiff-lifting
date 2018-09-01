package org.sidiff.integration.preferences.significance;

import org.sidiff.common.extension.IExtension;

/**
 * A preference domain significance provider is used to find domains (via document type)
 * that are significant for showing the domain specific settings pages.
 * @author Robert Müller
 *
 */
public interface IDomainSignificance extends IExtension {

	Description<IDomainSignificance> DESCRIPTION = Description.of(IDomainSignificance.class,
					"org.sidiff.integration.preferences.domains.significance", "significanceProvider", "class");

	DomainSignificanceManager MANAGER = new DomainSignificanceManager();

	/**
	 * Returns whether the given domain (document type) has non-default settings (e.g. custom rulebases)
	 * to show on the domain specific settings page.
	 * @param documentType the document type
	 * @return <code>true</code> if this domain should be shown on the domain specific settings page, <code>false</code> otherwise
	 */
	boolean isSignificant(String documentType);
}
