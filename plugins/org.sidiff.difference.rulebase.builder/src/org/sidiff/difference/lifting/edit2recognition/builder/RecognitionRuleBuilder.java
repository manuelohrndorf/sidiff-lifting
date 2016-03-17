package org.sidiff.difference.lifting.edit2recognition.builder;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.common.henshin.exceptions.NoMainUnitFoundException;
import org.sidiff.difference.lifting.edit2recognition.exceptions.EditToRecognitionException;
import org.sidiff.difference.rulebase.type.ILiftingRuleBase;
import org.sidiff.difference.rulebase.wrapper.RecognitionRuleBaseWrapper;
import org.sidiff.editrule.rulebase.RuleBase;
import org.sidiff.editrule.rulebase.RuleBaseItem;
import org.sidiff.editrule.rulebase.builder.attachment.EditRuleAttachmentBuilder;
import org.sidiff.editrule.rulebase.type.IEditRuleBase;

public class RecognitionRuleBuilder implements EditRuleAttachmentBuilder {

	/**
	 * The corresponding plug-in project.
	 */
	private IProject project;
	
	/**
	 * Rulebase wrapper to manage the build process.
	 */
	private RecognitionRuleBaseWrapper rbWrapper;
	
	@Override
	public void init(IProject project, RuleBase rulebase) {
		this.project = project;
		
		// Initialize rulebase wrapper:
		IFolder recognitionRuleFolderFolder = project.getFolder(ILiftingRuleBase.RECOGNITION_RULE_FOLDER);
		URI recognitionRuleFolderURI = EMFStorage.fileToFileUri(recognitionRuleFolderFolder.getFullPath().toFile());
		
		IFolder editRuleFolder = project.getFolder(IEditRuleBase.EDIT_RULE_FOLDER);
		URI editRuleFolderURI = EMFStorage.fileToFileUri(editRuleFolder.getFullPath().toFile());
		
		this.rbWrapper = new RecognitionRuleBaseWrapper(
				rulebase, editRuleFolderURI, recognitionRuleFolderURI);
	}
	
	@Override
	public void buildAttachment(IProgressMonitor monitor, RuleBaseItem item) {
		
		// Build recognition rule:
		try {
			rbWrapper.generateRecognitionRule(item);
		} catch (EditToRecognitionException e1) {
			e1.printStackTrace();
		} catch (NoMainUnitFoundException e1) {
			e1.printStackTrace();
		}
		
		// Refresh build folder
		try {
			project.getFolder(ILiftingRuleBase.RECOGNITION_RULE_FOLDER).refreshLocal(IResource.DEPTH_INFINITE, monitor);
		} catch (CoreException e) {
			e.printStackTrace();
		}
		
	}
	
	public void createRecognitionRuleFolder(IProgressMonitor monitor) {
		
		// Check if build folder exists, create it otherwise
		IFolder buildFolder = project.getFolder(ILiftingRuleBase.RECOGNITION_RULE_FOLDER);

		if (!buildFolder.exists()) {
			try {
				buildFolder.create(true, true, monitor);
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}

		try {
			buildFolder.setDerived(true, monitor);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteAttachment(IProgressMonitor monitor, RuleBaseItem item) {
		
		// Remove recognition rule (file and item):
		rbWrapper.removeRecognitionRule(item);
		
		// Refresh build folder
		try {
			project.getFolder(ILiftingRuleBase.RECOGNITION_RULE_FOLDER).refreshLocal(IResource.DEPTH_INFINITE, monitor);
		} catch (CoreException e) {
			// Something went wrong
		}
	}

	@Override
	public void cleanAttachments(IProgressMonitor monitor) {
		
		// Delete all elements in Build Folder if already existent:
		IFolder buildFolder = project.getFolder(ILiftingRuleBase.RECOGNITION_RULE_FOLDER);

		if (buildFolder.exists()) {
			try {
				for (IResource builtRR : buildFolder.members()) {
					builtRR.delete(true, monitor);
				}
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public String[] getNewFiles() {
		return new String[] {ILiftingRuleBase.RECOGNITION_RULE_FOLDER  + "/"};
	}
}
