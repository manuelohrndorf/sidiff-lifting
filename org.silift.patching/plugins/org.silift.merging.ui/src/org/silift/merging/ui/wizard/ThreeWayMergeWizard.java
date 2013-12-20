package org.silift.merging.ui.wizard;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.Wizard;
import org.sidiff.common.emf.exceptions.InvalidModelException;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.difference.asymmetric.facade.AsymmetricDiffFacade;
import org.sidiff.difference.asymmetric.facade.AsymmetricDiffSettings;
import org.sidiff.difference.asymmetric.facade.util.Difference;
import org.sidiff.difference.lifting.facade.LiftingSettings;
import org.sidiff.difference.lifting.facade.util.PipelineUtils;
import org.sidiff.difference.lifting.ui.util.InputModels;
import org.sidiff.difference.lifting.ui.util.ValidateDialog;
import org.silift.merging.ui.Activator;
import org.silift.patching.patch.PatchCreator;

public class ThreeWayMergeWizard extends Wizard {

	private ThreeWayMergePage01 threeWayMergePage01;
	private ThreeWayMergePage02 threeWayMergePage02;

	private InputModels inputModels1;
	private InputModels inputModels2;

	public ThreeWayMergeWizard(IFile fileA, IFile fileB, IFile fileBase) {
		this.setWindowTitle("Three-Way-Merge Wizard");

		inputModels1 = new InputModels(fileBase, fileA);
		inputModels2 = new InputModels(fileBase, fileB);
	}

	@Override
	public void addPages() {
		threeWayMergePage01 = new ThreeWayMergePage01(
				inputModels1,
				"ThreeWayMergePage", "Merge three models", getImageDescriptor("icon.png"));
		addPage(threeWayMergePage01);
		
		threeWayMergePage02 = new ThreeWayMergePage02(
				inputModels1,
				"ThreeWayMergePage", "Merge three models", getImageDescriptor("icon.png"));
		addPage(threeWayMergePage02);
	}

	@Override
	public boolean canFinish() {
		return threeWayMergePage01.isPageComplete();
	}

	@Override
	public boolean performFinish() {
		
		try {
			getContainer().run(false, false, new IRunnableWithProgress() {
				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					finish();
				}
			});
		} catch (InvocationTargetException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		return true;
	}
	
	private void finish() {
		Resource resourceA = inputModels1.getResourceA();
		Resource resourceB = inputModels1.getResourceB();
		PatchCreator patchCreator = new PatchCreator(resourceA, resourceB);

		// Start calculation:
		LiftingSettings settings = readSettings();
		try{
			Difference fullDiff = AsymmetricDiffFacade.liftMeUp(resourceA, resourceB, new AsymmetricDiffSettings(settings));
			PipelineUtils.sortDifference(fullDiff.getSymmetric());

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

			patchCreator.serializePatch(inputModels1.getFileA().getParent().getLocation());

			LogUtil.log(LogEvent.NOTICE, "done...");
		}catch(InvalidModelException e){
			ValidateDialog.openErrorDialog(Activator.PLUGIN_ID, e);
		} catch (FileNotFoundException e) {
			e.printStackTrace();

			MessageDialog dialog = new MessageDialog(getShell(),
					"Error", null, "File not found!", MessageDialog.ERROR,
					new String[] { "OK" }, 0);
			dialog.open();
		}

		// Refresh workspace:
		try {
			inputModels1.getFileA().getProject().refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
		} catch (CoreException e) {
			e.printStackTrace();
		} catch (OperationCanceledException e) {

		}
	}

	public LiftingSettings readSettings() {

		/*
		 * Do lifting settings
		 */

		LiftingSettings liftingSettings = new LiftingSettings();

		liftingSettings.setValidate(threeWayMergePage01.isValidateModels());
		// Used matcher
		liftingSettings.setMatcher(threeWayMergePage02.getSelectedMatchingEngine());

		//Used technical difference builder
		liftingSettings.setTechnicalDifferenceBuilder(threeWayMergePage02.getSelectedTechnicalDifferenceBuilder());

		// Do lifting..?
		liftingSettings.setDoLifting(true);

		// Use Postprocessor..?
		liftingSettings.setPostProcess(true);

		// Used rulebases
		liftingSettings.setUsedRulebases(threeWayMergePage01.getSelectedRulebases());

		return liftingSettings;
	}

	protected ImageDescriptor getImageDescriptor(String name) {
		return ImageDescriptor.createFromURL(FileLocator.find(Platform.getBundle(Activator.PLUGIN_ID),
				new Path(String.format("icons/%s", name)), null));
	}
}
