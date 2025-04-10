package org.sidiff.difference.rulebase.builder;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.URI;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.common.henshin.exceptions.NoMainUnitFoundException;
import org.sidiff.difference.lifting.edit2recognition.exceptions.EditToRecognitionException;
import org.sidiff.difference.rulebase.view.ILiftingRuleBase;
import org.sidiff.difference.rulebase.wrapper.RecognitionRuleGeneratorUtil;
import org.sidiff.editrule.rulebase.RuleBaseItem;
import org.sidiff.editrule.rulebase.builder.EditRuleBaseWrapper;
import org.sidiff.editrule.rulebase.builder.attachment.AbstractEditRuleAttachmentBuilder;
import org.sidiff.editrule.rulebase.view.editrule.IEditRuleBase;

public class RecognitionRuleBuilder extends AbstractEditRuleAttachmentBuilder {

	private static final String PLUGIN_ID = "org.sidiff.difference.rulebase.builder";

	@Override
	public void buildAttachment(IProgressMonitor monitor, RuleBaseItem item, EditRuleBaseWrapper wrapper) throws CoreException {

		// Get paths:
		IFolder recognitionRuleFolderFolder = wrapper.getProject().getFolder(ILiftingRuleBase.RECOGNITION_RULE_FOLDER);
		URI rrFolder = EMFStorage.toPlatformURI(recognitionRuleFolderFolder);
		
		IFolder editRuleFolder = wrapper.getProject().getFolder(IEditRuleBase.EDIT_RULE_FOLDER);
		URI eoFolder = EMFStorage.toPlatformURI(editRuleFolder);
		
		// Build recognition rule:
		try {
			RecognitionRuleGeneratorUtil.generateRecognitionRule(item, wrapper, eoFolder, rrFolder);
		} catch (NoMainUnitFoundException | EditToRecognitionException e) {
			throw new CoreException(new Status(IStatus.ERROR, PLUGIN_ID, "Failed to generate recognition rule", e));
		}
	}
	
	public void createRecognitionRuleFolder(IProgressMonitor monitor, IProject project) throws CoreException {
		
		// Check if build folder exists, create it otherwise
		IFolder buildFolder = project.getFolder(ILiftingRuleBase.RECOGNITION_RULE_FOLDER);

		SubMonitor progress = SubMonitor.convert(monitor, 2);
		if (!buildFolder.exists()) {
			buildFolder.create(true, true, progress.split(1));
		}
		buildFolder.setDerived(true, progress.split(1));
	}

	@Override
	public void deleteAttachment(IProgressMonitor monitor, RuleBaseItem item, EditRuleBaseWrapper wrapper) throws CoreException {
		
		// Remove recognition rule (file and item):
		RecognitionRuleGeneratorUtil.removeRecognitionRule(item);
		
		// Refresh build folder
		try {
			wrapper.getProject().getFolder(ILiftingRuleBase.RECOGNITION_RULE_FOLDER).refreshLocal(IResource.DEPTH_INFINITE, monitor);
		} catch (CoreException e) {
			// Something went wrong
		}
	}

	@Override
	public void cleanAttachments(IProgressMonitor monitor, EditRuleBaseWrapper wrapper) throws CoreException {
		
		// Delete all elements in Build Folder if already existent:
		IFolder buildFolder = wrapper.getProject().getFolder(ILiftingRuleBase.RECOGNITION_RULE_FOLDER);

		if (buildFolder.exists()) {
			IResource members[] = buildFolder.members();
			SubMonitor progress = SubMonitor.convert(monitor, members.length);
			for (IResource builtRR : members) {
				builtRR.delete(true, progress.split(1));
			}
		}
	}

	@Override
	public Collection<String> getNewFiles() {
		return Collections.singleton(ILiftingRuleBase.RECOGNITION_RULE_FOLDER  + "/");
	}
}
