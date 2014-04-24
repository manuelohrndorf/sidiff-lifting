package org.sidiff.patching.patch.ui.wizard;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

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
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Display;
import org.sidiff.common.emf.exceptions.InvalidModelException;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.difference.asymmetric.facade.AsymmetricDiffFacade;
import org.sidiff.difference.asymmetric.facade.util.Difference;
import org.sidiff.difference.lifting.facade.util.PipelineUtils;
import org.sidiff.difference.lifting.ui.util.InputModels;
import org.sidiff.difference.lifting.ui.util.ValidateDialog;
import org.silift.common.util.ui.UIUtil;
import org.silift.difference.lifting.settings.LiftingSettings;
import org.silift.difference.lifting.settings.LiftingSettings.RecognitionEngineMode;
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
		settings = new LiftingSettings();
		settings.setRecognitionEngineMode(RecognitionEngineMode.POST_PROCESSED);
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
	 public IWizardPage getNextPage(IWizardPage page){
		if(page instanceof CreatePatchPage01)
			((CreatePatchPage01)page).updateSettings();
		else if (page instanceof CreatePatchPage02)
			((CreatePatchPage02)page).updateSettings();
		return super.getNextPage(page);
	 }
	
	@Override
	public IWizardPage getPreviousPage(IWizardPage page){
		if(page instanceof CreatePatchPage01)
			((CreatePatchPage01)page).updateSettings();
		else if (page instanceof CreatePatchPage02)
			((CreatePatchPage02)page).updateSettings();
		return super.getPreviousPage(page);
	}

	@Override
	public boolean performFinish() {
		createPatchPage01.updateSettings();
		createPatchPage02.updateSettings();
		
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
		
		Difference fullDiff = null;
		Resource resourceA = inputModels.getResourceA();
		Resource resourceB = inputModels.getResourceB();
		PatchCreator patchCreator = new PatchCreator(resourceA, resourceB);

		/*
		 *  Start calculation:
		 */

		try {
			fullDiff = AsymmetricDiffFacade.liftMeUp(resourceA, resourceB, settings);
		} catch (InvalidModelException e) {
			ValidateDialog validateDialog = new ValidateDialog();
			boolean skipValidation = validateDialog.openErrorDialog(Activator.PLUGIN_ID, e);

			if (skipValidation) {
				try {
					// Retry without validation:
					settings.setValidate(false);
					fullDiff = AsymmetricDiffFacade.liftMeUp(resourceA, resourceB, settings);
				} catch (InvalidModelException e1) {
					// We should never get here...
					e1.printStackTrace();
				}
			} else {
				return false;
			}
		}
		PipelineUtils.sortDifference(fullDiff.getSymmetric());
		
		/*
		 * Create patch:
		 */

		try {
			patchCreator.setAsymmetricDifference(fullDiff.getAsymmetric());
			patchCreator.setSymmetricDifference(fullDiff.getSymmetric());

			ArrayList<HashMap<String, String>> conf = new ArrayList<HashMap<String, String>>();
			HashMap<String, String> conf_Matcher = new HashMap<String, String>();
			conf_Matcher.put("matcher", settings.getMatcher().getName());
			conf_Matcher.put("key", settings.getMatcher().getKey());
			conf.add(conf_Matcher);

			patchCreator.setSettings(conf);

			// Print report:
			LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");
			LogUtil.log(LogEvent.NOTICE, "---------------------- Create Patch Bundle -----------------");
			LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");

			final String patchPath = patchCreator.serializePatch(inputModels.getFileA().getParent().getLocation());

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

	protected ImageDescriptor getImageDescriptor(String name) {
		return ImageDescriptor.createFromURL(FileLocator.find(Platform.getBundle(Activator.PLUGIN_ID),
				new Path(String.format("icons/%s", name)), null));
	}
}
