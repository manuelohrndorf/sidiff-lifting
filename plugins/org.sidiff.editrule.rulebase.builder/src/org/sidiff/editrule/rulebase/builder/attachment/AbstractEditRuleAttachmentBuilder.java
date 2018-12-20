package org.sidiff.editrule.rulebase.builder.attachment;

import java.util.Objects;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;

public abstract class AbstractEditRuleAttachmentBuilder implements IEditRuleAttachmentBuilder, IExecutableExtension {

	/**
	 * The unique ID of this attachment builder.
	 */
	private String id;

	@Override
	public void setInitializationData(IConfigurationElement config, String propertyName, Object data) throws CoreException {
		this.id = Objects.requireNonNull(config.getAttribute(ATTRIBUTE_ATTACHMENT_ID), "attachmentID must be set for extension element");
	}

	@Override
	public String getKey() {
		return id;
	}
}
