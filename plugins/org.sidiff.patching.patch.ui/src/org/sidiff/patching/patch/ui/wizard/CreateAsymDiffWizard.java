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
import org.sidiff.common.emf.exceptions.NoCorrespondencesException;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.common.ui.util.UIUtil;
import org.sidiff.difference.asymmetric.api.AsymmetricDiffFacade;
import org.sidiff.difference.asymmetric.api.util.Difference;
import org.sidiff.difference.lifting.api.settings.LiftingSettings.RecognitionEngineMode;
import org.sidiff.difference.lifting.api.util.PipelineUtils;
import org.sidiff.difference.lifting.ui.util.ValidateDialog;
import org.sidiff.matching.input.InputModels;
import org.sidiff.patching.api.settings.PatchingSettings;
import org.sidiff.patching.patch.ui.Activator;
import org.silift.difference.symboliclink.SymbolicLinks;
import org.silift.difference.symboliclink.handler.ISymbolicLinkHandler;
import org.silift.difference.symboliclink.handler.util.SymbolicLinkHandlerUtil;

public class CreateAsymDiffWizard extends Wizard {

	private CreatePatchPage01 createPatchPage01;
	private CreatePatchPage02 createPatchPage02;

	private InputModels inputModels;
	private PatchingSettings settings;

	public CreateAsymDiffWizard(IFile fileA, IFile fileB) {
		this.setWindowTitle("New Asymmetric Difference Wizard");

		inputModels = new InputModels(fileA, fileB);
		settings = new PatchingSettings(inputModels.getDocumentTypes());
		settings.setRecognitionEngineMode(RecognitionEngineMode.LIFTING_AND_POST_PROCESSING);
		settings.setCalculateEditRuleMatch(true);
	}

	@Override
	public void addPages() {
		createPatchPage01 = new CreatePatchPage01(
				inputModels, "CreateDifferencePage01", "Create Asymmetric Difference",
				null, settings, Mode.ASYMMETRIC_DIFFERENCE);
		addPage(createPatchPage01);

		createPatchPage02 = new CreatePatchPage02(
				inputModels, "CreateDifferencePage02", "Create Asymmetric Difference",
				null, settings, Mode.ASYMMETRIC_DIFFERENCE, createPatchPage01);
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
	
	private boolean finish(PatchingSettings settings) {

		/*
		 *  Start calculation:
		 */
		
		Resource resourceA = inputModels.getResources().get(0);
		Resource resourceB = inputModels.getResources().get(1);
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

			Difference difference = AsymmetricDiffFacade.deriveLiftedAsymmetricDifference(inputModels.getResources().get(0), inputModels.getResources().get(1), settings);
			final String savePath = inputModels.getFiles().get(0).getLocation().toOSString().replace(inputModels.getFiles().get(0).getLocation().lastSegment(), "");
			final String fileName = PipelineUtils.generateDifferenceFileName(inputModels.getResources().get(0), inputModels.getResources().get(1), settings);
			
			
			
			if(settings.useSymbolicLinks()){
				ISymbolicLinkHandler symbolicLinkHandler = settings.getSymbolicLinkHandler();
				// TODO add reliability setting to lifting settings
				Collection<SymbolicLinks> symbolicLinksCollection =  symbolicLinkHandler.generateSymbolicLinks(difference.getAsymmetric(), false);
				SymbolicLinkHandlerUtil.serializeSymbolicLinks(symbolicLinksCollection, difference.getAsymmetric(), savePath);
			}
			
			AsymmetricDiffFacade.serializeLiftedDifference(difference, savePath, fileName);
			LogUtil.log(LogEvent.NOTICE, "done...");
			
			/*
			 * Update workspace UI
			 */
			
			Display.getDefault().asyncExec(new Runnable() {
				@Override
				public void run() {
					try {
						inputModels.getFiles().get(0).getProject().refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
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
		} catch (NoCorrespondencesException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
//		catch (NoCorrespondencesException e) {
//			e.printStackTrace();
//
//			MessageDialog dialog = new MessageDialog(getShell(),
//					"Error", null, "No Correspondences found!", MessageDialog.ERROR,
//					new String[] { "OK" }, 0);
//			dialog.open();
//			return false;
//		}
		
		
		return true;
	}
	
	private Difference calculateDifference(Resource resourceA, Resource resourceB) {
		Difference fullDiff = null;
		
		try{
			fullDiff = AsymmetricDiffFacade.deriveLiftedAsymmetricDifference(resourceA, resourceB, settings);
		} catch(InvalidModelException e){
			ValidateDialog validateDialog = new ValidateDialog();
			boolean skipValidation = validateDialog.openErrorDialog(Activator.PLUGIN_ID, e);
			
			if (skipValidation) {
				// Retry without validation:
				settings.setValidate(false);
				fullDiff = calculateDifference(resourceA, resourceB);
			}
		} catch (NoCorrespondencesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
