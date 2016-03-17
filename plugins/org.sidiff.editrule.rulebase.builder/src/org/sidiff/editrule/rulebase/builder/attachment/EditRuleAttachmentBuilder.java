package org.sidiff.editrule.rulebase.builder.attachment;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.pde.ui.templates.ITemplateSection;
import org.sidiff.editrule.rulebase.RuleBaseItem;

public interface EditRuleAttachmentBuilder {

	/**
	 * Builds a co-rules for a corresponding edit-rule.
	 * 
	 * @param monitor
	 *            progress monitor.
	 * @param project
	 *            The corresponding plug-in project.
	 * @param item
	 *            The rulebase item which contains the corresponding edit-rule.
	 */
	public void buildAttachment(IProgressMonitor monitor, IProject project, RuleBaseItem item);

	/**
	 * Deletes a co-rule for a corresponding edit-rule.
	 * 
	 * @param monitor
	 *            progress monitor.
	 * @param project
	 *            The corresponding plug-in project.
	 * @param item
	 *            The rulebase item which contains the corresponding edit-rule.
	 */
	public void deleteAttachment(IProgressMonitor monitor, IProject project, RuleBaseItem item);

	/**
	 * Removes all derived resources for a clean build.
	 * 
	 * @param monitor
	 *            progress monitor.
	 * @param project
	 *            The corresponding plug-in project.
	 */
	public void cleanAttachments(IProgressMonitor monitor, IProject project);
	
	/**
	 * @return {@link ITemplateSection#getNewFiles()}
	 */
	public String[] getNewFiles();
}
