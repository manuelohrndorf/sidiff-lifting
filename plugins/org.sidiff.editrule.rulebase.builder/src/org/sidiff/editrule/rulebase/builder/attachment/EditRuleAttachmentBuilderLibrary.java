package org.sidiff.editrule.rulebase.builder.attachment;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.sidiff.editrule.rulebase.RuleBase;

/**
 * Access registered attachment builder.
 */
public class EditRuleAttachmentBuilderLibrary {

	/**
	 * The registration ID for a attachment builder.
	 */
	public static final String EXTENSION_POINT_ID_CORULE_BUILDER = "org.sidiff.editrule.rulebase.builder.attachment";
	
	/**
	 * @return All registered attachment builder.
	 */
	private static Set<EditRuleAttachmentBuilder> getAttachmentBuilders() {
		
		// Collect all registered rulebases:
		Set<EditRuleAttachmentBuilder>  attachmentBuilders = new HashSet<EditRuleAttachmentBuilder>();

		for (IConfigurationElement configurationElement : Platform.getExtensionRegistry()
				.getConfigurationElementsFor(EXTENSION_POINT_ID_CORULE_BUILDER)) {

			try {
				EditRuleAttachmentBuilder coRuleBuilder = (EditRuleAttachmentBuilder) configurationElement.createExecutableExtension("class");
				attachmentBuilders.add(coRuleBuilder);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return attachmentBuilders;
	}
	
	/**
	 * @return All registered attachment builder.
	 */
	public static Set<EditRuleAttachmentBuilder> getAttachmentBuilders(IProject project, RuleBase rulebase) {
		
		// Collect all registered builders:
		Set<EditRuleAttachmentBuilder>  attachmentBuilders = getAttachmentBuilders();
		
		// Initialize builders:
		for (EditRuleAttachmentBuilder attachmentBuilder : attachmentBuilders) {
			attachmentBuilder.init(project, rulebase);
		}
		
		return attachmentBuilders;
	}
	
	/**
	 * @return All attachment folders for the build settings.
	 */
	public static String[] getAttachmentNewFolders() {
		String[] newFolders = null;
		
		// Collect new folders from attachment builders:
		for (EditRuleAttachmentBuilder attachmentBuilder : getAttachmentBuilders()) {
			String[] attachmentFolder = attachmentBuilder.getNewFiles();
			newFolders = Arrays.copyOf(newFolders, newFolders.length + attachmentFolder.length);

			for (int i = 0; i < attachmentFolder.length; i++) {
				newFolders[newFolders.length + i] = attachmentFolder[i];
			}
		}
		
		return newFolders;
	}
}