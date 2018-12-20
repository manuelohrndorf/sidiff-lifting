package org.sidiff.editrule.rulebase.builder.attachment;

import java.util.Collection;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.sidiff.common.extension.IExtension;
import org.sidiff.editrule.rulebase.RuleBaseItem;

public interface IEditRuleAttachmentBuilder extends IExtension {

	Description<IEditRuleAttachmentBuilder> DESCRIPTION = Description.of(IEditRuleAttachmentBuilder.class,
			"org.sidiff.editrule.rulebase.builder.attachment", "builder", "builder");

	EditRuleAttachmentBuilderManager MANAGER = new EditRuleAttachmentBuilderManager(DESCRIPTION);

	String ATTRIBUTE_ATTACHMENT_ID = "attachmentID";

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
	public void buildAttachment(IProgressMonitor monitor, IProject project, RuleBaseItem item) throws CoreException;

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
	public void deleteAttachment(IProgressMonitor monitor, IProject project, RuleBaseItem item) throws CoreException;

	/**
	 * Removes all derived resources for a clean build.
	 * 
	 * @param monitor
	 *            progress monitor.
	 * @param project
	 *            The corresponding plug-in project.
	 */
	public void cleanAttachments(IProgressMonitor monitor, IProject project) throws CoreException;
	
	/**
	 * @return {@link ITemplateSection#getNewFiles()}
	 */
	public Collection<String> getNewFiles();
}
