package org.sidiff.difference.rulebase.wrapper;

import java.io.File;
import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.common.henshin.exceptions.NoMainUnitFoundException;
import org.sidiff.difference.lifting.edit2recognition.exceptions.EditToRecognitionException;
import org.sidiff.difference.lifting.edit2recognition.util.TransformationConstants;
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
		
		// Generate new rule base item from given Module:
		EditWrapper2RecognitionWrapper generator = new EditWrapper2RecognitionWrapper();
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
		File recognitionRuleFile = EMFStorage.toFile(recognitionRuleURI);
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

		HenshinResourceSet resourceSet = new HenshinResourceSet();
		if (rrRule.getRecognitionMainUnit().eResource() != null) {
			// Existing recognition rule:
			try {
				rrRule.getRecognitionMainUnit().eResource().save(null);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		} else {
			// New recognition rule:
			resourceSet.saveEObject(rrRule.getRecognitionModule(),
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
