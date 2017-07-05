package org.sidiff.editrule.rulebase.project.runtime.library;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.sidiff.editrule.rulebase.view.basic.IBasicRuleBase;

/**
 * Access registered rulebase projects.
 */
public class RuleBaseProjectLibrary {

	/**
	 * Cache of all actually registered rulebase projects (proxies for the EMF data models).
	 */
	private static Set<IRuleBaseProject> rulebases;
	
	/**
	 * @return All registered rulebases (EMF data models).
	 */
	public static Set<? extends IBasicRuleBase> getRuleBases() {
		Set<IBasicRuleBase> rulebases = new HashSet<IBasicRuleBase>();
		
		// Collect all rulebases of the given view-type:
		for (IRuleBaseProject rulebaseProject : getRuleBaseProjects()) {
			IBasicRuleBase rulebase = rulebaseProject.getRuleBaseView(IBasicRuleBase.VIEW_TYPE);
			
			if (rulebase != null) {
				rulebases.add(rulebase);
			}
		}
		
		return rulebases;
	}
	
	/**
	 * @param documentTypes
	 *            The document types of the rulebases.
	 * @param viewType
	 *            A specific or abstract rulebase type ((sub)class of {@link IBasicRuleBase}).
	 * @return All registered rulebases (EMF data models) of the given view-type.
	 */
	public static <R extends IBasicRuleBase> Set<R> getRuleBases(Set<String> documentTypes, Class<R> viewType) {
		
		// Collect all rulebases for a specific document type:
		Set<R> rulebasesForDocType = new HashSet<R>();
		
		for (IRuleBaseProject rulebaseProject : getRuleBaseProjects(documentTypes, viewType)) {
			R rulebase = rulebaseProject.getRuleBaseView(viewType);

			if (rulebase != null) {
				rulebasesForDocType.add(rulebase);
			}
		}
		
		return rulebasesForDocType;
	}
	
	/**
	 * Clear rulebase cache.
	 */
	public static void clearRuleBaseCache() {
		rulebases = null;
	}
	
	/**
	 * @return All registered rulebase projects.
	 */
	public static Set<IRuleBaseProject> getRuleBaseProjects() {
		
		// Collect all registered rulebases:
		if (rulebases == null) {
			rulebases = new HashSet<IRuleBaseProject>();
			
			for (IConfigurationElement configurationElement : Platform.getExtensionRegistry()
					.getConfigurationElementsFor(IRuleBaseProject.EXTENSION_POINT_ID_RULEBASE_PROJECT)) {
				
				try {
					IRuleBaseProject rulebaseProject = (IRuleBaseProject) configurationElement
							.createExecutableExtension(IRuleBaseProject.EXTENSION_POINT_ATTRIBUTE_RULEBASE_PROJECT);
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
	public static <R extends IBasicRuleBase> Set<IRuleBaseProject> getRuleBaseProjects(Set<String> documentTypes, Class<R> viewType) {
		
		// Collect all rulebases for all document types in the input resources:
		Set<IRuleBaseProject> rulebases = new HashSet<IRuleBaseProject>();
		
		for (IRuleBaseProject rulebaseProject : getRuleBaseProjects()) {
			
			for(String docTypeInResources: documentTypes) {
				
				if(rulebaseProject.getDocumentTypes().contains(docTypeInResources)
						&& rulebaseProject.supportsRuleBaseView(viewType)) {
					rulebases.add(rulebaseProject);
				}
				
			}
			
		}
		
		return rulebases;
	}
}