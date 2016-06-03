package org.sidiff.difference.rulebase.builder;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.common.henshin.exceptions.NoMainUnitFoundException;
import org.sidiff.difference.lifting.edit2recognition.exceptions.EditToRecognitionException;
import org.sidiff.difference.rulebase.view.ILiftingRuleBase;
import org.sidiff.difference.rulebase.wrapper.RecognitionRuleGeneratorUtil;
import org.sidiff.editrule.rulebase.RuleBaseItem;
import org.sidiff.editrule.rulebase.builder.attachment.AbstractEditRuleAttachmentBuilder;
import org.sidiff.editrule.rulebase.view.editrule.IEditRuleBase;

public class RecognitionRuleBuilder extends AbstractEditRuleAttachmentBuilder {

	@Override
	public void buildAttachment(IProgressMonitor monitor, IProject project, RuleBaseItem item) {

		// Get paths:
		IFolder recognitionRuleFolderFolder = project.getFolder(ILiftingRuleBase.RECOGNITION_RULE_FOLDER);
		URI rrFolder = EMFStorage.fileToFileUri(recognitionRuleFolderFolder.getFullPath().toFile());
		
		IFolder editRuleFolder = project.getFolder(IEditRuleBase.EDIT_RULE_FOLDER);
		URI eoFolder = EMFStorage.fileToFileUri(editRuleFolder.getFullPath().toFile());
		
		// Build recognition rule:
		try {
			RecognitionRuleGeneratorUtil.generateRecognitionRule(item, eoFolder, rrFolder);
		} catch (EditToRecognitionException e1) {
			e1.printStackTrace();
		} catch (NoMainUnitFoundException e1) {
			e1.printStackTrace();
		}
	}
	
	public void createRecognitionRuleFolder(IProgressMonitor monitor, IProject project) {
		
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
	public void deleteAttachment(IProgressMonitor monitor, IProject project, RuleBaseItem item) {
		
		// Remove recognition rule (file and item):
		RecognitionRuleGeneratorUtil.removeRecognitionRule(item);
		
		// Refresh build folder
		try {
			project.getFolder(ILiftingRuleBase.RECOGNITION_RULE_FOLDER).refreshLocal(IResource.DEPTH_INFINITE, monitor);
		} catch (CoreException e) {
			// Something went wrong
		}
	}

	@Override
	public void cleanAttachments(IProgressMonitor monitor, IProject project) {
		
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
