package org.sidiff.editrule.rulebase.builder.attachment;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.pde.ui.templates.ITemplateSection;
import org.sidiff.editrule.rulebase.RuleBase;
import org.sidiff.editrule.rulebase.RuleBaseItem;

public interface EditRuleAttachmentBuilder {

	/**
	 * Initializes a new co-rule builder.
	 * 
	 * @param project
	 *            The corresponding plug-in project.
	 * @param rulebase
	 *            The rulebase (EMF) model. 
	 */
	public void init(IProject project, RuleBase rulebase);

	/**
	 * Builds a co-rules for a corresponding edit-rule.
	 * 
	 * @param monitor
	 *            progress monitor.
	 * @param item
	 *            The rulebase item which contains the corresponding edit-rule.
	 */
	public void buildAttachment(IProgressMonitor monitor, RuleBaseItem item);

	/**
	 * Deletes a co-rule for a corresponding edit-rule.
	 * 
	 * @param monitor
	 *            progress monitor.
	 * @param item
	 *            The rulebase item which contains the corresponding edit-rule.
	 */
	public void deleteAttachment(IProgressMonitor monitor, RuleBaseItem item);

	/**
	 * Removes all derived resources for a clean build.
	 * 
	 * @param monitor
	 *            progress monitor.
	 */
	public void cleanAttachments(IProgressMonitor monitor);
	
	/**
	 * @return {@link ITemplateSection#getNewFiles()}
	 */
	public String[] getNewFiles();
}
