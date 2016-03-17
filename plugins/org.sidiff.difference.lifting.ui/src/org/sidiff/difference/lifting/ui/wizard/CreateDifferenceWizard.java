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
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.common.ui.util.UIUtil;
import org.sidiff.difference.lifting.api.LiftingFacade;
import org.sidiff.difference.lifting.api.settings.LiftingSettings;
import org.sidiff.difference.lifting.api.util.PipelineUtils;
import org.sidiff.difference.lifting.ui.Activator;
import org.sidiff.difference.lifting.ui.pages.AdvancedCompareSettingsPage;
import org.sidiff.difference.lifting.ui.pages.BasicCompareSettingsPage;
import org.sidiff.difference.lifting.ui.util.InputModels;
import org.sidiff.difference.lifting.ui.util.ValidateDialog;
import org.sidiff.difference.symmetric.SymmetricDifference;

public class CreateDifferenceWizard extends Wizard {

	/**
	 * The {@link InputModels}
	 */
	private InputModels inputModels;
	
	/**
	 * The {@link LiftingSettings}
	 */
	private LiftingSettings settings;
	
	/**
	 * the save path of the {@link SymmetricDifference}
	 */
	private String diffSavePath;
	
	// ---------- UI Elements ----------
	
	/**
	 * The {@link BasicCompareSettingsPage}
	 */
	private BasicCompareSettingsPage basicCompareSettingsPage;
	
	/**
	 * The {@link AdvancedCompareSettingsPage}
	 */
	private AdvancedCompareSettingsPage advancedCompareSettingsPage;

	// ---------- Constructor ----------
	
	public CreateDifferenceWizard(IFile fileA, IFile fileB) {
		
		this.setWindowTitle("New Symmetric Difference Wizard");

		this.inputModels = new InputModels(fileA, fileB);
		this.settings = new LiftingSettings(inputModels.getCharacteristicDocumentType());
		this.diffSavePath = fileA.getParent().getLocation().toOSString();
	}

	// ---------- Wizard ----------
	
	@Override
	public void addPages() {
		basicCompareSettingsPage = new BasicCompareSettingsPage("Basic Compare Settings Page","Compare models with each other",inputModels, settings);
		addPage(basicCompareSettingsPage);

		advancedCompareSettingsPage = new AdvancedCompareSettingsPage("Advanced Compare Settings Page", "Compare models with each other", inputModels, settings);
		addPage(advancedCompareSettingsPage);
	}

	@Override
	public boolean canFinish() {
		return basicCompareSettingsPage.isPageComplete() && advancedCompareSettingsPage.isPageComplete();
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

		String fileName = PipelineUtils.generateDifferenceFileName(resourceA, resourceB, settings);
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
//			LogUtil.setLogChannel("EclipseConsoleLogChannel");
			symmetricDiff = LiftingFacade.liftMeUp(resourceA, resourceB, settings);
		} catch(InvalidModelException e){
			ValidateDialog validateDialog = new ValidateDialog();
			boolean skipValidation = validateDialog.openErrorDialog(Activator.PLUGIN_ID, e);
			
			if (skipValidation) {
				// Retry without validation:
				settings.setValidate(false);
				symmetricDiff = calculateDifference(resourceA, resourceB);
			}
		}
		
		if (symmetricDiff != null) {
			PipelineUtils.sortDifference(symmetricDiff);
		}
		
		return symmetricDiff;
	}
}
