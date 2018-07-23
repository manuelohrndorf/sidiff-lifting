package org.sidiff.integration.preferences.tabs;

import java.util.List;

import org.sidiff.integration.preferences.fieldeditors.IPreferenceField;

/**
 * Classes implementing this interface represent preferences tabs that will be shown
 * on the respective preference pages.
 * Extensions must specify the page and pipeline step that this tab will be shown for.
 * @author Daniel Roedder, Robert Müller
 *
 */
public interface IPreferenceTab {

	String EXTENSION_POINT_ID = "org.sidiff.integration.preferences.preferenceTabs";
	String EXTENSION_POINT_ATTRIBUTE_PAGE = "page";
	String EXTENSION_POINT_ATTRIBUTE_HELP_CONTEXT_ID = "helpContextId";
	String PAGE_GENERAL = "general";
	String PAGE_ENGINES = "engines";
	String PAGE_VALIDATION = "validation";
	String EXTENSION_POINT_ATTRIBUTE_PIPELINE_STEP = "pipelineStep";
	String EXTENSION_POINT_ATTRIBUTE_CLASS = "class";

	/**
	 * Creates the preferences fields and adds them to the list.
	 * @param list list to add preference fields to
	 */
	void createPreferenceFields(List<IPreferenceField> list);

	/**
	 * Returns the help context for this preference tab.
	 * @return the help context id, <code>null</code> if none
	 */
	String getHelpContextId();

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
