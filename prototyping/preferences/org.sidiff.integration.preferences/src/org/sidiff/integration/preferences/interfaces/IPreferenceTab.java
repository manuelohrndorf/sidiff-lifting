package org.sidiff.integration.preferences.interfaces;

import java.util.List;

import org.sidiff.integration.preferences.fieldeditors.PreferenceField;

/**
 * 
 * @author Daniel Roedder, Robert Müller
 *
 */
public interface IPreferenceTab extends IOrderableStep {

	String EXTENSION_POINT_ID = "org.sidiff.integration.preferences.preferenceTabs";
	String EXTENSION_POINT_ATTRIBUTE = "class";

	/**
	 * Used to specify the page that a tab resides in.
	 */
	enum TabPage {
		/**
		 * The "General" page
		 */
		GENERAL,

		/**
		 * The "Engines" page
		 */
		ENGINES,

		/**
		 * The "Validation" page
		 */
		VALIDATION,

		/**
		 * The "Domains" page.
		 * The preference tab must also implement {@link IPreferenceTab.DomainSpecific} when using this type.
		 */
		DOMAINS
	}

	/**
	 * The {@link TabPage page} that this tab resides in.
	 * @return the page
	 */
	TabPage getPage();

	/**
	 * Returns the title of the tab.
	 * @return the title of the tab.
	 */
	String getTitle();

	/**
	 * Creates the preferences fields and adds them to the list.
	 * @param list list to add preference fields to
	 */
	void createPreferenceFields(List<PreferenceField> list);

	/**
	 * This interface may also be implemented by classes implementing {@link IPreferenceTab},
	 * when the preferences managed by it depend on the current domain.
	 */
	interface DomainSpecific {

		/**
		 * Called before {@link IPreferenceTab#createPreferenceFields(List)} with the document type of the current domain.
		 * @param documentType the document type nsURI
		 */
		void setDocumentType(String documentType);
	}
}
