package org.sidiff.merging.twoway.ui.wizards;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.wizard.Wizard;
import org.sidiff.common.emf.exceptions.InvalidModelException;
import org.sidiff.difference.lifting.ui.util.InputModels;
import org.sidiff.merging.twoway.MergingEngine;
import org.sidiff.merging.twoway.facade.TwoWayMergingFacade;
import org.sidiff.merging.twoway.facade.TwoWayMergingSettings;
import org.sidiff.merging.twoway.ui.pages.TwoWayMergeConfigurationPage;
import org.sidiff.patching.PatchEngine;
import org.silift.common.util.access.EMFModelAccessEx;
import org.silift.common.util.emf.EMFStorage;
import org.silift.patching.settings.ExecutionMode;

public class TwoWayMergeWizard extends Wizard {

	private InputModels inputModels;
	
	private TwoWayMergingSettings settings;
	
	private TwoWayMergeConfigurationPage page;
	
	private String savePath;

	public TwoWayMergeWizard(IFile fileA, IFile fileB){
		inputModels = new InputModels(fileA, fileB);
		savePath = fileA.getLocation().toOSString().replace(fileA.getLocation().lastSegment(), "");
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
				
				settings.setRuleBases(TwoWayMergingFacade.getAvailableAtomicRuleBases(inputModels.getCharacteristicDocumentType()));
				Resource mergedResource = EMFModelAccessEx.copyResource(inputModels.getResourceA(), savePath, "merged"+inputModels.getResourceA().getURI().lastSegment()+ inputModels.getResourceB().getURI().lastSegment());
				
				MergingEngine engine = new MergingEngine(inputModels.getResourceA(), inputModels.getResourceB(), mergedResource, settings);
				try {
					engine.init();
					PatchEngine interactiveMergeEngine = engine.merge();
					if(settings.getExecutionMode().equals(ExecutionMode.INTERACTIVE)){
						//TODO initialize Patching UI
					}else{
						EMFStorage.eSave(mergedResource.getContents().get(0));
					}
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