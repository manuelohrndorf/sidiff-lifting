package org.sidiff.patching.patch.ui.wizard;

import java.io.FileNotFoundException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Display;
import org.sidiff.common.emf.exceptions.InvalidModelException;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.difference.asymmetric.facade.AsymmetricDiffFacade;
import org.sidiff.difference.asymmetric.facade.util.Difference;
import org.sidiff.difference.lifting.facade.util.PipelineUtils;
import org.sidiff.difference.lifting.settings.LiftingSettings;
import org.sidiff.difference.lifting.settings.LiftingSettings.RecognitionEngineMode;
import org.sidiff.difference.lifting.ui.util.InputModels;
import org.sidiff.difference.lifting.ui.util.ValidateDialog;
import org.silift.common.util.ui.UIUtil;
import org.silift.patching.patch.PatchCreator;
import org.silift.patching.patch.ui.Activator;

public class CreatePatchWizard extends Wizard {

	private CreatePatchPage01 createPatchPage01;
	private CreatePatchPage02 createPatchPage02;

	private InputModels inputModels;
	private LiftingSettings settings;

	public CreatePatchWizard(IFile fileA, IFile fileB) {
		this.setWindowTitle("New Patch Wizard");

		inputModels = new InputModels(fileA, fileB);
		settings = new LiftingSettings(inputModels.getDocumentType());
		settings.setRecognitionEngineMode(RecognitionEngineMode.LIFTING_AND_POST_PROCESSING);
	}

	@Override
	public void addPages() {
		createPatchPage01 = new CreatePatchPage01(
				inputModels, 
				"CreateDifferencePage", "Create a Patch", getImageDescriptor("icon.png"), settings);
		addPage(createPatchPage01);
		
		createPatchPage02 = new CreatePatchPage02(
				inputModels,
				"CreateDifferencePage", "Create a Patch", getImageDescriptor("icon.png"), settings);
		addPage(createPatchPage02);
	}

	@Override
	public boolean canFinish() {
		return createPatchPage01.isPageComplete() && createPatchPage02.isPageComplete();
	}
	

	@Override
	public boolean performFinish() {
		
		Job job = new Job("Creating Patch") {
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
		 *  Start calculation:
		 */
		
		Resource resourceA = inputModels.getResourceA();
		Resource resourceB = inputModels.getResourceB();
		Difference fullDiff = calculateDifference(resourceA, resourceB);
		
		if (fullDiff == null) {
			return false;
		}
		
		/*
		 * Create patch:
		 */

		try {
			PatchCreator patchCreator = new PatchCreator(fullDiff.getAsymmetric(), settings);
			
			// Print report:
			LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");
			LogUtil.log(LogEvent.NOTICE, "---------------------- Create Patch Bundle -----------------");
			LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");

			final String patchPath = patchCreator.serializePatch(inputModels.getFileA().getParent().getLocation().toOSString());

			LogUtil.log(LogEvent.NOTICE, "done...");
			
			/*
			 * Update workspace UI
			 */
			
			Display.getDefault().asyncExec(new Runnable() {
				@Override
				public void run() {
					try {
						inputModels.getFileA().getProject().refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
						UIUtil.openEditor(patchPath);	
					} catch (CoreException e) {
						e.printStackTrace();
					} catch (OperationCanceledException e) {

					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
			});
		} catch (FileNotFoundException e) {
			e.printStackTrace();

			MessageDialog dialog = new MessageDialog(getShell(),
					"Error", null, "File not found!", MessageDialog.ERROR,
					new String[] { "OK" }, 0);
			dialog.open();
			return false;
		}
		
		return true;
	}
	
	private Difference calculateDifference(Resource resourceA, Resource resourceB) {
		Difference fullDiff = null;
		
		try{
			fullDiff = AsymmetricDiffFacade.liftMeUp(resourceA, resourceB, settings);
		} catch(InvalidModelException e){
			ValidateDialog validateDialog = new ValidateDialog();
			boolean skipValidation = validateDialog.openErrorDialog(Activator.PLUGIN_ID, e);
			
			if (skipValidation) {
				// Retry without validation:
				settings.setValidate(false);
				fullDiff = calculateDifference(resourceA, resourceB);
			}
		}
		
		if (fullDiff != null) {
			PipelineUtils.sortDifference(fullDiff.getSymmetric());
		}
		
		return fullDiff;
	}

	protected ImageDescriptor getImageDescriptor(String name) {
		return ImageDescriptor.createFromURL(FileLocator.find(Platform.getBundle(Activator.PLUGIN_ID),
				new Path(String.format("icons/%s", name)), null));
	}
}
