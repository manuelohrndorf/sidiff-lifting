package org.sidiff.difference.rulebase.wrapper;

import java.io.File;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Module;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.common.henshin.exceptions.NoMainUnitFoundException;
import org.sidiff.difference.lifting.edit2recognition.exceptions.EditToRecognitionException;
import org.sidiff.difference.lifting.edit2recognition.util.TransformationConstants;
import org.sidiff.difference.rulebase.RecognitionRule;
import org.sidiff.editrule.rulebase.RuleBase;
import org.sidiff.editrule.rulebase.RuleBaseItem;
import org.sidiff.editrule.rulebase.builder.EditRuleBaseWrapper;

/**
 * Convenience functions for the rulebase management.
 * 
 * @author Manuel Ohrndorf
 */
public class RecognitionRuleGeneratorUtil {

	/**
	 * Generates a new Recognition-Rule and adds it to the rulebase item.
	 * 
	 * @param item
	 *            The rulebase item which contains the corresponding edit-rule.
	 * @throws NoMainUnitFoundException 
	 * @throws Edit2RecognitionException
	 */
	public static void generateRecognitionRule(RuleBaseItem item, EditRuleBaseWrapper wrapper,
			URI editRuleFolderURI, URI recognitionRuleFolderURI) 
			throws EditToRecognitionException, NoMainUnitFoundException {
		
		// Generate new rule base item from given Module:
		EditWrapper2RecognitionWrapper generator = new EditWrapper2RecognitionWrapper();
		generator.transform(item);
		
		// Save the recognition rule:
		createRecognitionModuleResource(item, wrapper, editRuleFolderURI, recognitionRuleFolderURI);
	}
	
	/**
	 * Removes a recognition-rule from the file system.
	 * 
	 * @param item
	 *            The item which contains the recognition-rule.
	 */
	public static void removeRecognitionRule(RuleBaseItem item) {

		// Remove recognition rule from file system
		URI recognitionRuleURI = EcoreUtil.getURI(
				item.getEditRuleAttachment(RecognitionRule.class)
				.getRecognitionModule()).trimFragment();
		File recognitionRuleFile = EMFStorage.toFile(recognitionRuleURI);
		recognitionRuleFile.delete();

		//Delete parent folder if it is empty now
		File parentFolder = recognitionRuleFile.getParentFile();			
		File[] files = parentFolder.listFiles();
		if(files == null || files.length == 0) {
			parentFolder.delete();					
		}
	}
	
	/**
	 * Searches an Recognition-Rule rulebase wrapper by its corresponding Henshin Recognition-Rule.
	 * 
	 * @param rulebase
	 *            The rulebase to search.
	 * @param recognitionModule
	 *            The Henshin Recognition-Rule.
	 * @return The corresponding Recognition-Rule rulebase wrapper.
	 */
	public static RecognitionRule findRecognitionRule(RuleBase rulebase, Module recognitionModule) {
		for (RecognitionRule rr_rule : rulebase.getEditRuleAttachments(RecognitionRule.class)) {
			if (recognitionModule == rr_rule.getRecognitionModule()) {
				return rr_rule;
			}
		}
		return null;
	}
	
	/**
	 * Creates Resources for (Henshin) Recognition-Rules.
	 */
	private static void createRecognitionModuleResource(RuleBaseItem item, EditRuleBaseWrapper wrapper,
			URI editRuleFolderURI, URI recognitionRuleFolderURI) {

		RecognitionRule recognitionRule = item.getEditRuleAttachment(RecognitionRule.class);
		URI moduleUri = getRecognitionRuleSaveURI(recognitionRule.getEditRule().getExecuteModule(), 
			editRuleFolderURI, recognitionRuleFolderURI);
		wrapper.getResourceSet().createResource(moduleUri)
			.getContents().add(recognitionRule.getRecognitionModule());
	}

	/**
	 * Helper method to get corresponding output save URI for given EditRule.
	 * This is necessary if there is some subfolder structure beneath the
	 * recognitionRuleFolder.
	 */
	private static URI getRecognitionRuleSaveURI(Module editModule, 
			URI editRuleFolderURI, URI recognitionRuleFolderURI) {

		URI editRuleURI = EcoreUtil.getURI(editModule).trimFragment();
		URI saveURI = EMFStorage.toPlatformURI(editRuleURI);
		// replace edit rule folder with recognition rule folder
		// append empty segments and convert to platform URI so the URIs are prefix URIs
		URI replaced = saveURI.replacePrefix(
			editRuleFolderURI.appendSegment(""),
			recognitionRuleFolderURI.appendSegment(""));
		return replaced.trimSegments(1).appendSegment(TransformationConstants.FILE_PREFIX + editRuleURI.lastSegment());
	}
}
