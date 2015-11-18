package org.sidiff.patching.patch.ui.wizard;

import java.io.FileNotFoundException;
import java.util.Collection;

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
import org.sidiff.common.ui.util.UIUtil;
import org.sidiff.difference.asymmetric.facade.AsymmetricDiffFacade;
import org.sidiff.difference.asymmetric.facade.util.Difference;
import org.sidiff.difference.lifting.facade.LiftingFacade;
import org.sidiff.difference.lifting.facade.util.PipelineUtils;
import org.sidiff.difference.lifting.settings.LiftingSettings;
import org.sidiff.difference.lifting.settings.LiftingSettings.RecognitionEngineMode;
import org.sidiff.difference.lifting.ui.util.InputModels;
import org.sidiff.difference.lifting.ui.util.ValidateDialog;
import org.sidiff.patching.patch.ui.Activator;
import org.silift.difference.symboliclink.SymbolicLinks;
import org.silift.difference.symboliclink.handler.ISymbolicLinkHandler;
import org.silift.difference.symboliclink.handler.util.SymbolicLinkHandlerUtil;

public class CreateAsymDiffWizard extends Wizard {

	private CreatePatchPage01 createPatchPage01;
	private CreatePatchPage02 createPatchPage02;

	private InputModels inputModels;
	private LiftingSettings settings;

	public CreateAsymDiffWizard(IFile fileA, IFile fileB) {
		this.setWindowTitle("New Asymmetric Difference Wizard");

		inputModels = new InputModels(fileA, fileB);
		settings = new LiftingSettings(inputModels.getCharacteristicDocumentType());
		settings.setRecognitionEngineMode(RecognitionEngineMode.LIFTING_AND_POST_PROCESSING);
		settings.setCalculateEditRuleMatch(true);
	}

	@Override
	public void addPages() {
		createPatchPage01 = new CreatePatchPage01(
				inputModels, 
				"CreateDifferencePage", "Create Asymmetric Difference", null, settings, Mode.ASYMMETRIC_DIFFERENCE);
		addPage(createPatchPage01);
		
		createPatchPage02 = new CreatePatchPage02(
				inputModels,
				"CreateDifferencePage", "Create Asymmetric Difference", null, settings, Mode.ASYMMETRIC_DIFFERENCE);
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
			// Print report:
			LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");
			LogUtil.log(LogEvent.NOTICE, "---------------------- Create Difference -----------------");
			LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");

			Difference difference = AsymmetricDiffFacade.liftMeUp(inputModels.getResourceA(), inputModels.getResourceB(), settings);
			final String savePath = inputModels.getFileA().getLocation().toOSString().replace(inputModels.getFileA().getLocation().lastSegment(), "");
			final String fileName = LiftingFacade.generateDifferenceFileName(inputModels.getResourceA(), inputModels.getResourceB(), settings);
			
			
			
			if(settings.useSymbolicLinks()){
				ISymbolicLinkHandler symbolicLinkHandler = settings.getSymbolicLinkHandler();
				// TODO add reliability setting to lifting settings
				Collection<SymbolicLinks> symbolicLinksCollection =  symbolicLinkHandler.generateSymbolicLinks(difference.getAsymmetric(), false);
				SymbolicLinkHandlerUtil.serializeSymbolicLinks(symbolicLinksCollection, difference.getAsymmetric(), savePath);
			}
			
			AsymmetricDiffFacade.serializeDifference(difference, savePath, fileName);
			LogUtil.log(LogEvent.NOTICE, "done...");
			
			/*
			 * Update workspace UI
			 */
			
			Display.getDefault().asyncExec(new Runnable() {
				@Override
				public void run() {
					try {
						inputModels.getFileA().getProject().refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
						UIUtil.openEditor(savePath + fileName + "." + AsymmetricDiffFacade.ASYMMETRIC_DIFF_EXT);	
					} catch (CoreException e) {
						e.printStackTrace();
					} catch (OperationCanceledException e) {

					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
			});
		} catch (InvalidModelException e) {
			e.printStackTrace();

			MessageDialog dialog = new MessageDialog(getShell(),
					"Error", null, "Invalid model!", MessageDialog.ERROR,
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
