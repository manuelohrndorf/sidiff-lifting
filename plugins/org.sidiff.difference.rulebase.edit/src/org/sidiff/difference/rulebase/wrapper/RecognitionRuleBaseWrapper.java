package org.sidiff.difference.rulebase.wrapper;

import java.io.File;
import java.util.Observable;

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
 * Encapsulates a RuleBase instance and provides some convenience functions for the RuleBase management.
 * 
 * @author Manuel Ohrndorf
 */
public class RecognitionRuleBaseWrapper extends Observable {
	
	/**
	 * The folder to store the Recognition-Rules.
	 */
	private URI recognitionRuleFolderURI;
	
	/**
	 * The folder of the corresponding Edit-Rules. 
	 */
	private URI editRuleFolderURI;
	
	/**
	 * The (Ecore model) rulebase root element. 
	 */
	private RuleBase rulebase;

	/**
	 * Initializes an existing rulebase.
	 * 
	 * @param rulebase
	 *            The rulebase model.
	 * @see RuleBaseWrapper#saveRuleBase()
	 */
	public RecognitionRuleBaseWrapper(RuleBase rulebase, URI editRuleFolderURI, URI recognitionRuleFolderURI) {
		this.rulebase = rulebase;
		this.recognitionRuleFolderURI = recognitionRuleFolderURI;
		this.editRuleFolderURI = editRuleFolderURI;
	}

	/**
	 * Generates a new Recognition-Rule and adds it to the rulebase item.
	 * 
	 * @param item
	 *            The rulebase item which contains the corresponding edit-rule.
	 * @throws NoMainUnitFoundException 
	 * @throws Edit2RecognitionException
	 */
	public void generateRecognitionRule(RuleBaseItem item) 
			throws EditToRecognitionException, NoMainUnitFoundException {

		EditWrapper2RecognitionWrapper generator = null;
		
		// Generate new rule base item from given Module:
		generator = new EditWrapper2RecognitionWrapper();
		generator.transform(item);
		
		// Save the recognition rule:
		saveRecognitionModules(item.getEditRuleAttachment(RecognitionRule.class));
	}
	
	/**
	 * Removes an recognition-rule from the file system.
	 * 
	 * @param item
	 *            The item which contains the recognition-rule.
	 */
	public void removeRecognitionRule(RuleBaseItem item) {

		// Remove recognition rule from file system
		URI recognitionRuleURI = EcoreUtil.getURI(
				item.getEditRuleAttachment(RecognitionRule.class).getRecognitionMainUnit()).trimFragment();
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
	 * @param recognitionModule
	 *            The Henshin Recognition-Rule.
	 * @return The corresponding Recognition-Rule rulebase wrapper.
	 */
	public RecognitionRule findRecognitionRule(Module recognitionModule) {
		for (RecognitionRule rr_rule : rulebase.getEditRuleAttachments(RecognitionRule.class)) {
			if (recognitionModule == rr_rule.getRecognitionModule()) {
				return rr_rule;
			}
		}
		return null;
	}
	
	/**
	 * @return The target folder where the Recognition-Rules are stored.
	 */
	public URI getRecognitionRuleFolder() {
		return recognitionRuleFolderURI;
	}
	
	/**
	 * Saves all (Henshin) Recognition-Rules.
	 */
	private void saveRecognitionModules(RecognitionRule rrRule) {

		if (rrRule.getRecognitionMainUnit().eResource() != null) {
			// Existing recognition rule:
			EMFStorage.eSave(rrRule.getRecognitionModule());
		} else {
			// New recognition rule:
			Edit2RecognitionUtil.saveRecognitionRule(
					rrRule.getRecognitionModule(),
					rrRule.getEditRule().getExecuteModule(),
					getRecognitionRuleSaveURI(rrRule.getEditRule().getExecuteModule()));
		}
	}

	/**
	 * Helper method to get corresponding output save URI for given EditRule. This is necessary if
	 * there is some subfolder structure beneath the recognitionRuleFolder.
	 * 
	 * 
	 * @param editModule
	 *            The corresponding Edit-Rule.
	 * @return The Recognition-Rule save path.
	 */
	private URI getRecognitionRuleSaveURI(Module editModule){
		
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
