package org.sidiff.editrule.rulebase.project.runtime.library;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.sidiff.editrule.rulebase.type.extension.IRuleBase;

/**
 * Access registered rulebase projects.
 */
public class RuleBaseProjectLibrary {

	/**
	 * The registration ID for a rulebase project.
	 */
	public static final String EXTENSION_POINT_ID_RULEBASE_PROJECT = "org.sidiff.editrule.rulebase.project";

	/**
	 * Cache of all actually registered rulebase projects (proxies for the EMF data models).
	 */
	private static Set<IRuleBaseProject> rulebases;
	
	/**
	 * @param rulebaseType
	 *            A specific or abstract rulebase type ((sub)class of
	 *            {@link IRuleBase}).
	 * @param documentTypes
	 *            The document types of the rulebases.
	 * @return All registered rulebases (EMF data models) of the given type.
	 */
	public static <R extends IRuleBase> Set<R> getRuleBases(Class<R> rulebaseType) {
		Set<R> rulebases = new HashSet<R>();
		
		// Collect all rulebases of the given type:
		for (IRuleBaseProject rbProject : getRuleBaseProjects()) {
			R rulebase = rbProject.getRuleBase(rulebaseType);
			
			if (rulebase != null) {
				rulebases.add(rulebase);
			}
		}
		
		return rulebases;
	}
	
	/**
	 * @param documentTypes
	 *            The document (metamodel) type of the rulebase.
	 * @return All registered rulebases for the given document type.
	 */
	public static <R extends IRuleBase> Set<R> getRuleBases(Set<String> documentTypes, Class<R> rulebaseType) {
		
		// Collect all rulebases for a specific document type:
		Set<R> rulebasesForDocType = new HashSet<R>();
		
		for (R editRulebase : RuleBaseProjectLibrary.getRuleBases(rulebaseType)) {
			if (editRulebase.getDocumentTypes().containsAll(documentTypes)) {
				rulebasesForDocType.add(editRulebase);
			}
		}
		
		return rulebasesForDocType;
	}
	
	/**
	 * @return All registered rulebase projects.
	 */
	public static Set<IRuleBaseProject> getRuleBaseProjects() {
		
		// Collect all registered rulebases:
		if (rulebases == null) {
			rulebases = new HashSet<IRuleBaseProject>();
			
			for (IConfigurationElement configurationElement : Platform.getExtensionRegistry()
					.getConfigurationElementsFor(EXTENSION_POINT_ID_RULEBASE_PROJECT)) {
				
				try {
					IRuleBaseProject rulebaseProject = (IRuleBaseProject) configurationElement
							.createExecutableExtension("rulebase");
					rulebases.add(rulebaseProject);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return rulebases;
	}
	
	/**
	 * @param documentTypes
	 *            The document (metamodel) type of the rulebase.
	 * @return All registered rulebase projects for the given document type.
	 */
	public static Set<IRuleBaseProject> getRuleBaseProjects(Set<String> documentTypes, Class<? extends IRuleBase> rulebaseType) {
		
		// Collect all rulebases for a specific document type:
		Set<IRuleBaseProject> rulebasesForDocType = new HashSet<IRuleBaseProject>();
		
		for (IRuleBaseProject rulebaseProject : RuleBaseProjectLibrary.getRuleBaseProjects()) {
			if (rulebaseProject.getRuleBase(rulebaseType).getDocumentTypes().containsAll(documentTypes)) {
				rulebasesForDocType.add(rulebaseProject);
			}
		}
		
		return rulebasesForDocType;
	}
}