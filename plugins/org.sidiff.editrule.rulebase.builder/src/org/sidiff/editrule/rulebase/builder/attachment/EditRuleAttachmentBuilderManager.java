package org.sidiff.editrule.rulebase.builder.attachment;

import java.util.Collection;
import java.util.stream.Collectors;

import org.sidiff.common.extension.ExtensionManager;
import org.sidiff.common.extension.IExtension.Description;

public class EditRuleAttachmentBuilderManager extends ExtensionManager<IEditRuleAttachmentBuilder>{

	public EditRuleAttachmentBuilderManager(final Description<? extends IEditRuleAttachmentBuilder> description) {
		super(description);
	}

	/**
	 * @return All attachment folders for the build settings.
	 */
	public Collection<String> getAttachmentNewFolders() {
		// Collect new folders from attachment builders:
		return getExtensions().stream()
				.map(IEditRuleAttachmentBuilder::getNewFiles)
				.flatMap(Collection::stream)
				.collect(Collectors.toSet());
	}
}
