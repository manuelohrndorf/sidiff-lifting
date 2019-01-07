package org.sidiff.editrule.rulebase.project.runtime.library;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.sidiff.common.extension.IExtension.Description;
import org.eclipse.emf.ecore.EPackage;
import org.sidiff.common.extension.TypedExtensionManager;
import org.sidiff.editrule.rulebase.EditRule;
import org.sidiff.editrule.rulebase.view.basic.IBasicRuleBase;
import org.sidiff.editrule.rulebase.view.editrule.IEditRuleBase;

/**
 * Access registered rulebase projects.
 */
public class RuleBaseProjectManager extends TypedExtensionManager<IRuleBaseProject> {

	public RuleBaseProjectManager(final Description<? extends IRuleBaseProject> description) {
		super(description);
	}

	/**
	 * @return All registered rulebases (EMF data models).
	 */
	public Set<? extends IBasicRuleBase> getRuleBases() {
		return getRuleBases(IBasicRuleBase.VIEW_TYPE);
	}
	
	/**
	 * @param documentTypes
	 *            The document types of the rulebases.
	 * @param viewType
	 *            A specific or abstract rulebase type ((sub)class of {@link IBasicRuleBase}).
	 * @return All registered rulebases (EMF data models) of the given view-type.
	 */
	public <R extends IBasicRuleBase> Set<R> getRuleBases(Set<String> documentTypes, Class<R> viewType) {
		return getRuleBaseProjects(documentTypes, viewType).stream()
				.map(project -> project.getRuleBaseView(viewType))
				.filter(Objects::nonNull)
				.collect(Collectors.toSet());
	}
	
	/**
	 * @param viewType
	 *            A specific or abstract rulebase type ((sub)class of {@link IBasicRuleBase}).
	 * @return All registered rulebases (EMF data models) of the given view-type.
	 */
	public <R extends IBasicRuleBase> Set<R> getRuleBases(Class<R> viewType) {
		return getExtensions().stream()
				.map(project -> project.getRuleBaseView(viewType))
				.filter(Objects::nonNull)
				.collect(Collectors.toSet());
	}
	
	/**
	 * @param key rulebase key
	 * @param viewType
	 *            A specific or abstract rulebase type ((sub)class of {@link IBasicRuleBase}).
	 * @return All registered rulebases (EMF data models) of the given view-type.
	 */
	public <R extends IBasicRuleBase> Optional<R> getRuleBase(String key, Class<R> viewType) {
		return getExtension(key)
				.map(project -> project.getRuleBaseView(viewType))
				.filter(Objects::nonNull);
	}
	
	/**
	 * Clear rulebase cache.
	 */
	public void clearRuleBaseCache() {
		getExtensions().forEach(IRuleBaseProject::unloadRuleBaseData);
	}

	/**
	 * @param documentTypes
	 *            The document (metamodel) type of the rulebase.
	 * @return All registered rulebase projects for the given document type.
	 */
	public <R extends IBasicRuleBase> Set<IRuleBaseProject> getRuleBaseProjects(Set<String> documentTypes, Class<R> viewType) {
		// Collect all rulebases for all document types in the input resources:
		return getExtensions().stream()
			.filter(project -> project.supportsRuleBaseView(viewType))
			.filter(project -> documentTypes.stream().anyMatch(project.getDocumentTypes()::contains))
			.collect(Collectors.toSet());
		
	}

	/**
	 * Resolve an EditRule by its name. We lookup in all rulebases which are
	 * suitable for the given document types.
	 * 
	 * @param documentTypes
	 *            The document (metamodel) types of the edit rule.
	 * @param editRuleName
	 *            The identifying name of the edit rule.
	 * @return The edit rule with given name.
	 * @throws NoSuchElementException if no edit rule is found
	 */
	public EditRule resolveEditRule(Set<String> documentTypes, String editRuleName) throws NoSuchElementException {
		return getRuleBases(documentTypes, IEditRuleBase.TYPE)
			.stream()
			.map(rulebase -> rulebase.getEditRule(editRuleName))
			.filter(Objects::nonNull)
			.findFirst().orElseThrow(NoSuchElementException::new);
	}

	/**
	 * Resolves the RuleBase that contains the given EditRule.
	 * @param editRule the edit rule
	 * @param viewType the type of the rule base
	 * @return rulebase that contains the edit rule
	 * @throws NoSuchElementException if no rulebase is found
	 */
	public <R extends IBasicRuleBase> R resolveIEditRuleBase(EditRule editRule, Class<R> viewType) {
		Set<String> documentTypes = editRule.getExecuteModule().getImports().stream().map(EPackage::getNsURI).collect(Collectors.toSet());
		return getRuleBases(documentTypes, viewType)
				.stream()
				.filter(editRuleBase -> editRuleBase.getRuleBase().getEditRules().contains(editRule))
				.findFirst().orElseThrow(NoSuchElementException::new);
	}
}
