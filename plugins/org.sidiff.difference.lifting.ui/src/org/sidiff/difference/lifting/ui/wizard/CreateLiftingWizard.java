package org.sidiff.difference.lifting.ui.wizard;

import java.io.FileNotFoundException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Display;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.common.ui.util.UIUtil;
import org.sidiff.difference.lifting.api.LiftingFacade;
import org.sidiff.difference.lifting.api.settings.LiftingSettings;
import org.sidiff.difference.lifting.api.util.PipelineUtils;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.matching.input.InputModels;

public class CreateLiftingWizard extends Wizard{

	private CreateLiftingPage createLiftingPage;

	private IFile differenceFile;
	private SymmetricDifference symmetricDiff;
	private String diffSavePath;
	private LiftingSettings settings;
	private InputModels inputModels;

	public CreateLiftingWizard(IFile differenceFile) {
		this.setWindowTitle("Difference Lift Up Wizard");

		this.differenceFile = differenceFile;
		symmetricDiff = LiftingFacade.loadLiftedDifference(differenceFile.getLocation().toOSString());
		diffSavePath = differenceFile.getParent().getLocation().toOSString();

		inputModels = new InputModels(symmetricDiff.getModelA(), symmetricDiff.getModelB());
		settings = new LiftingSettings(inputModels.getDocumentTypes());
	}


	@Override
	public void addPages() {
		createLiftingPage = new CreateLiftingPage(differenceFile, inputModels, settings);
		addPage(createLiftingPage);
	}


	@Override
	public boolean canFinish() {
		return createLiftingPage.isPageComplete();
	}

	@Override
	public boolean performFinish() {
		Job job = new Job("Lifting technical Difference") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				finish(settings);
				return Status.OK_STATUS;
			}
		};
		job.schedule();
		return true;
	}
	
	private void finish(LiftingSettings settings) {
		/*
		 * Semantic Lifting
		 */

		symmetricDiff = LiftingFacade.liftTechnicalDifference(symmetricDiff, settings);
		PipelineUtils.sortDifference(symmetricDiff);

		/*
		 * Serialize lifted symmetricDiff
		 */

		LiftingFacade.serializeLiftedDifference(symmetricDiff, diffSavePath,
		PipelineUtils.generateDifferenceFileName(symmetricDiff.eResource(), settings));

		/*
		 * Update workspace UI
		 */
		
		final String diffPath = EMFStorage.uriToPath(symmetricDiff.eResource().getURI());
		
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				try {
					differenceFile.getProject().refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
					UIUtil.openEditor(diffPath);	
				} catch (CoreException e) {
					e.printStackTrace();
				} catch (OperationCanceledException e) {

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
