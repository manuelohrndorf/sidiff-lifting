package org.sidiff.editrule.rulebase.project.runtime.library;

import java.util.Set;

import org.sidiff.editrule.rulebase.RuleBase;
import org.sidiff.editrule.rulebase.project.runtime.storage.RuleBaseStorage;
import org.sidiff.editrule.rulebase.type.basic.IBasicRuleBase;

/**
 * This interface accesses the information of a registered rulebase project.
 */
public interface IRuleBaseProject {

	/**
	 * The registration ID for a rulebase project.
	 */
	public static final String EXTENSION_POINT_ID_RULEBASE_PROJECT = "org.sidiff.editrule.rulebase.project.runtime.extension";
	
	/**
	 * The plug-in relative path to the rulebase file.
	 */
	public static final String RULEBASE_FILE = "sidiff" + "." + RuleBaseStorage.EXTENSION_RULEBASE;
	
	/**
	 * The plug-in relative path to the rulebase meta file.
	 */
	public static final String RULEBASE_META_FILE = RULEBASE_FILE + ".mf";
	
	/**
	 * The base folder which contains the edit-rules.
	 */
	public static final String EDIT_RULE_FOLDER = "editrules";
	
	/**
	 * @return A name which describes this rulebase.
	 */
	public String getRuleBaseName();

	/**
	 * @return All contained rulebase types.
	 */
	public Set<String> getRuleBaseTypes();
	
	/**
	 * @return All supported document types of this rulebase.
	 */
	public Set<String> getDocumentTypes();

	/**
	 * @param type
	 *            A specific rulebase type.
	 * @return The corresponding rulebase wrapper.
	 */
	public <R extends IBasicRuleBase> R getRuleBase(Class<R> type);

	/**
	 * @return The rulebase instance.
	 */
	public RuleBase getRuleBaseData();

	/**
	 * @return The plug-in relative path to the rulebase file.
	 */
	public String getRuleBasePath();
}
