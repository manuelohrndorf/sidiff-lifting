package org.sidiff.editrule.rulebase.builder.attachment;

import java.util.Collection;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.sidiff.common.extension.IExtension;
import org.sidiff.editrule.rulebase.RuleBaseItem;
import org.sidiff.editrule.rulebase.builder.EditRuleBaseWrapper;

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
	 * @param item
	 *            The rulebase item which contains the corresponding edit-rule.
	 * @param wrapper
	 * 			  The rulebase wrapper
	 */
	public void buildAttachment(IProgressMonitor monitor, RuleBaseItem item, EditRuleBaseWrapper wrapper) throws CoreException;

	/**
	 * Deletes a co-rule for a corresponding edit-rule.
	 * 
	 * @param monitor
	 *            progress monitor.
	 * @param item
	 *            The rulebase item which contains the corresponding edit-rule.
	 * @param wrapper
	 * 			  The rulebase wrapper
	 */
	public void deleteAttachment(IProgressMonitor monitor, RuleBaseItem item, EditRuleBaseWrapper wrapper) throws CoreException;

	/**
	 * Removes all derived resources for a clean build.
	 * 
	 * @param monitor
	 *            progress monitor.
	 * @param wrapper
	 * 			  The rulebase wrapper
	 */
	public void cleanAttachments(IProgressMonitor monitor, EditRuleBaseWrapper wrapper) throws CoreException;
	
	/**
	 * @return {@link ITemplateSection#getNewFiles()}
	 */
	public Collection<String> getNewFiles();
}
