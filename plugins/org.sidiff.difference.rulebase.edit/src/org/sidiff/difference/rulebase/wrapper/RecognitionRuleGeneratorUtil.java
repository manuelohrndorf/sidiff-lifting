package org.sidiff.difference.rulebase.wrapper;

import java.io.File;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Module;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.common.henshin.exceptions.NoMainUnitFoundException;
import org.sidiff.difference.lifting.edit2recognition.exceptions.EditToRecognitionException;
import org.sidiff.difference.lifting.edit2recognition.util.Edit2RecognitionUtil;
import org.sidiff.difference.rulebase.RecognitionRule;
import org.sidiff.editrule.rulebase.RuleBase;
import org.sidiff.editrule.rulebase.RuleBaseItem;

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
	public static void generateRecognitionRule(RuleBaseItem item, 
			URI editRuleFolderURI, URI recognitionRuleFolderURI) 
			throws EditToRecognitionException, NoMainUnitFoundException {

		EditWrapper2RecognitionWrapper generator = null;
		
		// Generate new rule base item from given Module:
		generator = new EditWrapper2RecognitionWrapper();
		generator.transform(item);
		
		// Save the recognition rule:
		saveRecognitionModules(item.getEditRuleAttachment(RecognitionRule.class),
				editRuleFolderURI, recognitionRuleFolderURI);
	}
	
	/**
	 * Removes an recognition-rule from the file system.
	 * 
	 * @param item
	 *            The item which contains the recognition-rule.
	 */
	public static void removeRecognitionRule(RuleBaseItem item) {

		// Remove recognition rule from file system
		URI recognitionRuleURI = EcoreUtil.getURI(
				item.getEditRuleAttachment(RecognitionRule.class)
				.getRecognitionMainUnit()).trimFragment();
		File recognitionRuleFile = EMFStorage.uriToFile(recognitionRuleURI);
		recognitionRuleFile.delete();

		//Delete parent folder if it is empty now
		File parentFolder =  recognitionRuleFile.getParentFile();			
		if(parentFolder.listFiles() == null || parentFolder.listFiles().length == 0){
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
	 * Saves all (Henshin) Recognition-Rules.
	 */
	private static void saveRecognitionModules(RecognitionRule rrRule,
			URI editRuleFolderURI, URI recognitionRuleFolderURI) {

		if (rrRule.getRecognitionMainUnit().eResource() != null) {
			// Existing recognition rule:
			EMFStorage.eSave(rrRule.getRecognitionModule());
		} else {
			// New recognition rule:
			Edit2RecognitionUtil.saveRecognitionRule(
					rrRule.getRecognitionModule(),
					rrRule.getEditRule().getExecuteModule(),
					getRecognitionRuleSaveURI(rrRule.getEditRule().getExecuteModule(), 
							editRuleFolderURI, recognitionRuleFolderURI));
		}
	}

	/**
	 * Helper method to get corresponding output save URI for given EditRule.
	 * This is necessary if there is some subfolder structure beneath the
	 * recognitionRuleFolder.
	 */
	private static URI getRecognitionRuleSaveURI(Module editModule, 
			URI editRuleFolderURI, URI recognitionRuleFolderURI){
		
		// Replace Edit-Rule with Recognition-Rule to keep folder structure:
		String editRuleFolderString = editRuleFolderURI.lastSegment();
		String recognitionRuleFolderString = recognitionRuleFolderURI.lastSegment();
		
		String savePath = EMFStorage.uriToPath(editModule.eResource().getURI());
		
		// Get URI without filename
		savePath = savePath.substring(0, savePath.lastIndexOf(File.separator));
		
		// Replace EditRuleFolder with RecognitionRuleFolder
		savePath = savePath.replace(
				File.separator + editRuleFolderString, File.separator + recognitionRuleFolderString);
		
		return EMFStorage.pathToUri(savePath);
	}
}
