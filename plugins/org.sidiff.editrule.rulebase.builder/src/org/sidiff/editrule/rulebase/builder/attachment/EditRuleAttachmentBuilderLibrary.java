package org.sidiff.editrule.rulebase.builder.attachment;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

/**
 * Access registered attachment builder.
 */
public class EditRuleAttachmentBuilderLibrary {

	/**
	 * The registration ID for a attachment builder.
	 */
	public static final String EXTENSION_POINT_ID_ATTACHMENT_BUILDER = "org.sidiff.editrule.rulebase.builder.attachment";
	
	/**
	 * @return All registered attachment builder.
	 */
	public static Set<IEditRuleAttachmentBuilder> getAttachmentBuilders() {
		
		// Collect all registered rulebases:
		Set<IEditRuleAttachmentBuilder> attachmentBuilders = new HashSet<IEditRuleAttachmentBuilder>();

		for (IConfigurationElement configurationElement : Platform.getExtensionRegistry()
				.getConfigurationElementsFor(EXTENSION_POINT_ID_ATTACHMENT_BUILDER)) {

			try {
				IEditRuleAttachmentBuilder attachmentBuilder = (IEditRuleAttachmentBuilder) configurationElement.createExecutableExtension("builder");
				attachmentBuilder.init(configurationElement.getAttribute("attachmentID"));
				attachmentBuilders.add(attachmentBuilder);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return attachmentBuilders;
	}
	
	/**
	 * @return All attachment folders for the build settings.
	 */
	public static String[] getAttachmentNewFolders() {
		String[] newFolders = new String[0];
		int newFolderSize = newFolders.length; 
		
		// Collect new folders from attachment builders:
		for (IEditRuleAttachmentBuilder attachmentBuilder : getAttachmentBuilders()) {
			String[] attachmentFolder = attachmentBuilder.getNewFiles();
			newFolders = Arrays.copyOf(newFolders, newFolderSize + attachmentFolder.length);

			for (int i = 0; i < attachmentFolder.length; i++) {
				newFolders[newFolderSize + i] = attachmentFolder[i];
			}
		}
		
		return newFolders;
	}
}