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
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Display;
import org.sidiff.common.emf.exceptions.InvalidModelException;
import org.sidiff.difference.lifting.facade.LiftingFacade;
import org.sidiff.difference.lifting.facade.util.PipelineUtils;
import org.sidiff.difference.lifting.settings.LiftingSettings;
import org.sidiff.difference.lifting.ui.Activator;
import org.sidiff.difference.lifting.ui.util.CorrespondenceDialog;
import org.sidiff.difference.lifting.ui.util.InputModels;
import org.sidiff.difference.lifting.ui.util.ValidateDialog;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.silift.common.util.emf.EMFStorage;
import org.silift.common.util.exceptions.NoCorrespondencesException;
import org.silift.common.util.ui.UIUtil;

public class CreateDifferenceWizard extends Wizard {

	private CreateDifferencePage01 createDifferencePage01;
	private CreateDifferencePage02 createDifferencePage02;

	private InputModels inputModels;
	private LiftingSettings settings;
	private String diffSavePath;

	/**
	 * @param fileA
	 * @param fileB
	 */
	public CreateDifferenceWizard(IFile fileA, IFile fileB) {
		this.setWindowTitle("New Symmetric Difference Wizard");

		inputModels = new InputModels(fileA, fileB);
		settings = new LiftingSettings(inputModels.getDocumentType());
		diffSavePath = fileA.getParent().getLocation().toOSString();
	}

	@Override
	public void addPages() {
		createDifferencePage01 = new CreateDifferencePage01(inputModels, settings);
		addPage(createDifferencePage01);

		createDifferencePage02 = new CreateDifferencePage02(inputModels, settings);
		addPage(createDifferencePage02);
	}

	@Override
	public boolean canFinish() {
		return createDifferencePage01.isPageComplete() && createDifferencePage02.isPageComplete();
	}

	@Override
	public boolean performFinish() {
		Job job = new Job("Creating Symmetric Difference") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				boolean status = finish(settings);

				if (status) {
					return Status.OK_STATUS;
				} else {
					return Status.CANCEL_STATUS;
				}
			}
		};
		job.schedule();
		return true;
	}

	private boolean finish(LiftingSettings settings) {

		/*
		 * Semantic Lifting
		 */

		Resource resourceA = inputModels.getResourceA();
		Resource resourceB = inputModels.getResourceB();
		SymmetricDifference symmetricDiff = calculateDifference(resourceA, resourceB);

		if (symmetricDiff == null) {
			return false;
		}

		/*
		 * Serialize lifted symmetricDiff
		 */

		String fileName = LiftingFacade.generateDifferenceFileName(resourceA, resourceB, settings);
		LiftingFacade.serializeDifference(symmetricDiff, diffSavePath, fileName);

		/*
		 * Update workspace UI
		 */

		final String diffPath = EMFStorage.uriToPath(symmetricDiff.eResource().getURI());

		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				try {
					inputModels.getFileA().getProject()
							.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
					UIUtil.openEditor(diffPath);
				} catch (CoreException e) {
					e.printStackTrace();
				} catch (OperationCanceledException e) {

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		});

		return true;
	}
	
	private SymmetricDifference calculateDifference(Resource resourceA, Resource resourceB) {
		SymmetricDifference symmetricDiff = null;
		
		try{
			symmetricDiff = LiftingFacade.liftMeUp(resourceA, resourceB, settings);
		} catch(InvalidModelException e){
			ValidateDialog validateDialog = new ValidateDialog();
			boolean skipValidation = validateDialog.openErrorDialog(Activator.PLUGIN_ID, e);
			
			if (skipValidation) {
				// Retry without validation:
				settings.setValidate(false);
				symmetricDiff = calculateDifference(resourceA, resourceB);
			}
		} catch (NoCorrespondencesException e) {
			CorrespondenceDialog.openErrorDialog(Activator.PLUGIN_ID, e);			
		}
		
		if (symmetricDiff != null) {
			PipelineUtils.sortDifference(symmetricDiff);
		}
		
		return symmetricDiff;
	}
}
