package org.sidiff.merging.twoway.ui.wizards;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.wizard.Wizard;
import org.sidiff.common.emf.exceptions.InvalidModelException;
import org.sidiff.difference.lifting.facade.util.PipelineUtils;
import org.sidiff.difference.lifting.ui.util.InputModels;
import org.sidiff.difference.rulebase.extension.IRuleBase;
import org.sidiff.merging.twoway.MergingEngine;
import org.sidiff.merging.twoway.facade.TwoWayMergingSettings;
import org.sidiff.merging.twoway.ui.pages.TwoWayMergeConfigurationPage;

public class TwoWayMergeWizard extends Wizard {

	InputModels inputModels;
	
	TwoWayMergingSettings settings;
	
	TwoWayMergeConfigurationPage page;

	public TwoWayMergeWizard(IFile fileA, IFile fileB){
		inputModels = new InputModels(fileA, fileB);
		settings = new TwoWayMergingSettings(inputModels.getCharacteristicDocumentType());
	}
	
	@Override
	public void addPages() {
		super.addPages();
		page = new TwoWayMergeConfigurationPage("TwoWayMergeConfigurationPage", "Two-Way Merge", inputModels, settings);
		addPage(page);
	}
	
	@Override
	public boolean performFinish() {
		Job job = new Job("Merging two models") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				Set<IRuleBase> rulebases = new HashSet<IRuleBase>();
				for(IRuleBase rulebase : PipelineUtils.getAvailableRulebases(inputModels.getCharacteristicDocumentType())){
					if(rulebase.getName().equalsIgnoreCase("atomic")){
						rulebases.add(rulebase);
					}
				}
				settings.setRuleBases(rulebases);
				MergingEngine engine = new MergingEngine(inputModels.getResourceA(), inputModels.getResourceB(), settings);
				try {
					engine.init();
					engine.merge();
					engine.serializeMergedModel(inputModels.getFileA().getLocation().toOSString().replace(inputModels.getFileA().getName(), "Merged." + inputModels.getFileA().getFileExtension()));
				} catch (InvalidModelException e) {
					e.printStackTrace();
					return Status.CANCEL_STATUS;				
				}
				
				return Status.OK_STATUS;
			}
		};
		job.schedule();
		return true;
	}
	
}