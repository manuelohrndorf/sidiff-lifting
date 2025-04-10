package org.sidiff.editrule.rulebase.project.runtime.library;

import java.util.Set;
import org.sidiff.common.extension.ITypedExtension;
import org.sidiff.editrule.rulebase.RuleBase;
import org.sidiff.editrule.rulebase.project.runtime.storage.RuleBaseStorage;
import org.sidiff.editrule.rulebase.view.basic.IBasicRuleBase;

/**
 * This interface accesses the information of a registered rulebase project.
 */
public interface IRuleBaseProject extends ITypedExtension {

	Description<IRuleBaseProject> DESCRIPTION = Description.of(IRuleBaseProject.class,
			"org.sidiff.editrule.rulebase.project.runtime.extension", "project", "rulebase");

	RuleBaseProjectManager MANAGER = new RuleBaseProjectManager(DESCRIPTION);

	/**
	 * The plug-in relative path to the rulebase file.
	 */
	public static final String RULEBASE_FILE = "sidiff" + "." + RuleBaseStorage.EXTENSION_RULEBASE;
	
	/**
	 * The base folder which contains the edit-rules.
	 */
	public static final String EDIT_RULE_FOLDER = "editrules";
	
	/**
	 * @return A unique key which describes this rulebase, suitable for serialization.
	 */
	@Override
	public String getKey();
	
	/**
	 * @return A name which describes this rulebase, suitable for the user-interface.
	 */
	@Override
	public String getName();
	
	/**
	 * @return All supported edit-rule attachments.
	 */
	public Set<String> getAttachmentTypes();
	
	/**
	 * @return All supported document types of this rulebase.
	 */
	@Override
	public Set<String> getDocumentTypes();

	/**
	 * @param type
	 *            A specific rulebase view.
	 * @return The corresponding rulebase view.
	 */
	public <R extends IBasicRuleBase> R getRuleBaseView(Class<R> viewType);

	/**
	 * @param type
	 *            A specific rulebase view.
	 * @return <code>true</code> if this rulebase supports the given view-type;
	 *         <code>false</code> otherwise
	 */
	public boolean supportsRuleBaseView(Class<? extends IBasicRuleBase> viewType);
	
	/**
	 * @return The rulebase instance.
	 */
	public RuleBase getRuleBaseData();
	
	/**
	 * Unloads the rulebase model.
	 */
	public void unloadRuleBaseData();

	/**
	 * @return The plug-in relative path to the rulebase file.
	 */
	public String getRuleBasePath();
}
