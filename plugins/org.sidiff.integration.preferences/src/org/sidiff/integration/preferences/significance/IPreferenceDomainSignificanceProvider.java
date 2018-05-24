package org.sidiff.integration.preferences.significance;

/**
 * A preference domain significance provider is used to find domains (via document type)
 * that are significant for showing the domain specific settings pages.
 * @author Robert Müller
 *
 */
public interface IPreferenceDomainSignificanceProvider {

	public static final String EXTENSION_POINT_ID = "org.sidiff.integration.preferences.domains.significance";
	public static final String EXTENSION_POINT_ATTRIBUTE = "class";

	/**
	 * Returns whether the given domain (document type) has non-default settings (e.g. custom rulebases)
	 * to show on the domain specific settings page.
	 * @param documentType the document type
	 * @return <code>true</code> if this domain should be shown on the domain specific settings page, <code>false</code> otherwise
	 */
	boolean isSignificant(String documentType);
}
