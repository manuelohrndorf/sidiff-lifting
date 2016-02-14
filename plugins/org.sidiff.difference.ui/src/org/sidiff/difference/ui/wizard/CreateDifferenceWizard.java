package org.sidiff.difference.ui.wizard;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.wizard.Wizard;
import org.sidiff.difference.settings.settings.MatchingSettings;

public class CreateDifferenceWizard extends Wizard{

	private CreateDifferencePage createDifferencePage;
	private MatchingEnginePage matchingEnginePage;
	private MatchingSettings settings = new MatchingSettings();
	
	public CreateDifferenceWizard() {
		this.setWindowTitle("SiDiff Configuration Wizard");
	}


	@Override
	public void addPages() {
		matchingEnginePage = new MatchingEnginePage((Collection<Resource>)(new ArrayList<Resource>()), settings);
		addPage(matchingEnginePage);
		createDifferencePage=new CreateDifferencePage(settings);
		addPage(createDifferencePage);
	}


	@Override
	public boolean canFinish() {
		return createDifferencePage.isPageComplete();
	}

	@Override
	public boolean performFinish() {
//		createLiftingPage.updateSettings();
//		Job job = new Job("Lifting technical Difference") {
//			@Override
//			protected IStatus run(IProgressMonitor monitor) {
//				finish(settings);
//				return Status.OK_STATUS;
//			}
//		};
//		job.schedule();
		return true;
	}
	
//	private void finish(LiftingSettings settings) {
//		/*
//		 * Semantic Lifting
//		 */
//
//		symmetricDiff = LiftingFacade.liftMeUp(symmetricDiff, settings);
//		PipelineUtils.sortDifference(symmetricDiff);
//
//		/*
//		 * Serialize lifted symmetricDiff
//		 */
//
//		LiftingFacade.serializeDifference(symmetricDiff, diffSavePath,
//		LiftingFacade.generateDifferenceFileName(symmetricDiff.eResource(), settings));
//
//		/*
//		 * Update workspace UI
//		 */
//		
//		final String diffPath = EMFStorage.uriToPath(symmetricDiff.eResource().getURI());
//		
//		Display.getDefault().asyncExec(new Runnable() {
//			@Override
//			public void run() {
//				try {
//					differenceFile.getProject().refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
//					UIUtil.openEditor(diffPath);	
//				} catch (CoreException e) {
//					e.printStackTrace();
//				} catch (OperationCanceledException e) {
//
//				} catch (FileNotFoundException e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
}
