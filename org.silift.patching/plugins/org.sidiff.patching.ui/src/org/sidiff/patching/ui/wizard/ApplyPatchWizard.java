package org.sidiff.patching.ui.wizard;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecoretools.diagram.part.EcoreDiagramEditor;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.sidiff.common.emf.EMFValidate;
import org.sidiff.common.emf.exceptions.InvalidModelException;
import org.sidiff.difference.lifting.ui.util.ValidateDialog;
import org.sidiff.difference.matcher.IMatcher;
import org.sidiff.patching.PatchEngine;
import org.sidiff.patching.PatchEngine.ExecutionMode;
import org.sidiff.patching.arguments.IArgumentManager;
import org.sidiff.patching.transformation.ITransformationEngine;
import org.sidiff.patching.transformation.TransformationEngineUtil;
import org.sidiff.patching.ui.Activator;
import org.sidiff.patching.ui.adapter.ModelAdapter;
import org.sidiff.patching.ui.adapter.ModelChangeHandler;
import org.sidiff.patching.ui.arguments.InteractiveArgumentManager;
import org.sidiff.patching.ui.view.PatchView;
import org.sidiff.patching.ui.view.ReportView;
import org.sidiff.patching.validation.ValidationMode;
import org.silift.common.util.access.EMFModelAccessEx;
import org.silift.common.util.emf.ComparisonMode;
import org.silift.common.util.emf.EMFStorage;
import org.silift.patching.patch.Patch;
import org.silift.patching.patch.PatchCreator;

public class ApplyPatchWizard extends Wizard {

	private ApplyPatchPage01 applyPatchPage01;
	private ApplyPatchPage02 applyPatchPage02;

	private Patch patch;
	private String patchName;
	private boolean validationState;

	public ApplyPatchWizard(Patch patch, String patchName) {
		this.setWindowTitle("Apply Patch Wizard");
		this.patch = patch;
		this.patchName = patchName;

	}

	@Override
	public void addPages() {
		applyPatchPage01 = new ApplyPatchPage01("ApplyPatchPage", "Apply Patch: " + patchName,
				getImageDescriptor("icon.png"));
		addPage(applyPatchPage01);

		applyPatchPage02 = new ApplyPatchPage02(patch, "ApplyPatchPage", "Apply Patch: " + patchName,
				getImageDescriptor("icon.png"));
		addPage(applyPatchPage02);
	}

	@Override
	public boolean canFinish() {
		return applyPatchPage01.isPageComplete();
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

		// Gather all information
		// TODO Implement wrapper class like in Lifting, could be named
		// PatchingSettings
		final String separator = System.getProperty("file.separator");
		final String filename = this.applyPatchPage01.getTargetWidget().getFilename();
		final ValidationMode validationMode = this.applyPatchPage01.getValidationWidget().getSelection();
		final ComparisonMode comparisonMode = this.applyPatchPage01.getComparisonMode();
		final Integer reliability = this.applyPatchPage02.getReliabilityWidget().getReliability();
		final IMatcher matcher = this.applyPatchPage02.getSelectedMatchingEngine();

		final URI fileURI = URI.createFileURI(filename);
		Resource targetResource = EMFStorage.eLoad(fileURI).eResource();
		ResourceSet diagramResourceSet = PatchCreator.deriveDiagrammFile(targetResource);
		String savePath = EMFStorage.uriToPath(targetResource.getURI());
		savePath = savePath.replace(targetResource.getURI().lastSegment(), "patched");
		EMFStorage.eSaveAs(EMFStorage.pathToUri(savePath + separator + targetResource.getURI().lastSegment()),
				targetResource.getContents().get(0), true);

		if (!diagramResourceSet.getResources().isEmpty()) {
			for (Resource resource : diagramResourceSet.getResources()) {
				EMFStorage.eSaveAs(EMFStorage.pathToUri(savePath + separator + resource.getURI().lastSegment()),
						resource.getContents().get(0), false);
			}
		}

		final File fileToOpen = new File(savePath + separator + targetResource.getURI().lastSegment());

		Job job = new Job("Patching Model") {
			private EditingDomain editingDomain;

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					monitor.beginTask("Patching", 120);
					monitor.subTask("Opening editor for target resource");
					final AtomicReference<Resource> resourceResult = new AtomicReference<Resource>();
					Display.getDefault().syncExec(new Runnable() {
						@Override
						public void run() {
							try {
								IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
										.getActivePage();
								IFileStore fileStore = EFS.getLocalFileSystem().getStore(fileToOpen.toURI());
								IEditorPart editorPart = IDE.openEditorOnFileStore(page, fileStore);
								Resource resource = null;
								if (editorPart instanceof EcoreDiagramEditor) {
									EcoreDiagramEditor editor = (EcoreDiagramEditor) editorPart;
									resource = editor.getDiagram().getElement().eResource();
									editingDomain = editor.getEditingDomain();

									// FIXME: Diagram animation:
									// GMFAnimation.enableAnimation(resource,false);
								} else if (editorPart instanceof IEditingDomainProvider) {
									IEditingDomainProvider editor = (IEditingDomainProvider) editorPart;
									resource = editor.getEditingDomain().getResourceSet().getResources().get(0);
									editingDomain = editor.getEditingDomain();
								}
								resourceResult.set(resource);
								if (validationMode != ValidationMode.NO)
									EMFValidate.validateObject(resourceResult.get().getContents().get(0));
								validationState = true;
							} catch (PartInitException e) {
								e.printStackTrace();
							} catch (InvalidModelException e) {
								ValidateDialog.openErrorDialog(Activator.PLUGIN_ID, e);
								validationState = false;
							}
						}
					});
					if (!validationState) {
						return Status.CANCEL_STATUS;
					}
					monitor.worked(20);

					// Use interactive argument manager
					IArgumentManager argumentManager = new InteractiveArgumentManager(matcher);

					// Find transformation engine (no other available right now)
					String documentType = null;
					if (EMFModelAccessEx.isProfiled(resourceResult.get())) {
						documentType = EMFModelAccessEx.getBaseDocumentType(resourceResult.get());
					} else {
						documentType = EMFModelAccessEx.getCharacteristicDocumentType(resourceResult.get());
					}
					ITransformationEngine transformationEngine = TransformationEngineUtil
							.getFirstTransformationEngine(documentType);
					if (transformationEngine == null) {
						MessageDialog.openError(Display.getCurrent().getActiveShell(),
								"No Transformator Service found!", "No suitable Transformator Service found!");
						return Status.CANCEL_STATUS;
					}

					monitor.subTask("Initialize PatchEngine");
					argumentManager.setMinReliability(reliability);
					final PatchEngine patchEngine = new PatchEngine(patch.getDifference(), resourceResult.get(),
							argumentManager, transformationEngine, ExecutionMode.INTERACTIVE, validationMode,
							comparisonMode, matcher.canComputeReliability());
					patchEngine.setPatchedEditingDomain(editingDomain);
					monitor.worked(40);

					monitor.subTask("Open Patch View");
					final AtomicReference<PatchView> patchViewReference = new AtomicReference<PatchView>();
					Display.getDefault().syncExec(new Runnable() {

						@Override
						public void run() {
							try {
								PatchView patchView = (PatchView) PlatformUI.getWorkbench().getActiveWorkbenchWindow()
										.getActivePage().showView(PatchView.ID);
								patchView.setPatchEngine(patchEngine);
								patchViewReference.set(patchView);
							} catch (PartInitException e) {
								e.printStackTrace();
							}
						}
					});
					PatchView patchView = patchViewReference.get();
					monitor.worked(20);

					monitor.subTask("Open Report View");
					final AtomicReference<ReportView> reportViewReference = new AtomicReference<ReportView>();
					Display.getDefault().syncExec(new Runnable() {

						@Override
						public void run() {
							try {
								ReportView reportView = (ReportView) PlatformUI.getWorkbench()
										.getActiveWorkbenchWindow().getActivePage().showView(ReportView.ID);
								reportView.setPatchReportManager(patchEngine.getPatchReportManager());
								reportViewReference.set(reportView);
							} catch (PartInitException e) {
								e.printStackTrace();
							}
						}
					});
					ReportView reportView = reportViewReference.get();
					monitor.worked(20);

					// ModelChangeHandler works independent; PatchView is
					// interested in model changes
					monitor.subTask("Adding Modellistener");
					ModelAdapter adapter = new ModelAdapter(resourceResult.get());
					adapter.addListener(new ModelChangeHandler(argumentManager));
					adapter.addListener(patchView);
					monitor.worked(20);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					monitor.done();
				}

				return Status.OK_STATUS;
			}
		};
		job.schedule();
	}

	/*
	 * public LiftingSettings readSettings() {
	 * 
	 * 
	 * LiftingSettings liftingSettings = new LiftingSettings();
	 * 
	 * liftingSettings.setValidate(applyPatchPage01.isValidateModels()); // Used
	 * matcher
	 * liftingSettings.setMatcher(applyPatchPage02.getSelectedMatchingEngine());
	 * 
	 * //Used technical difference builder
	 * liftingSettings.setTechnicalDifferenceBuilder
	 * (applyPatchPage02.getSelectedTechnicalDifferenceBuilder());
	 * 
	 * // Do lifting..? liftingSettings.setDoLifting(true);
	 * 
	 * // Use Postprocessor..? liftingSettings.setPostProcess(true);
	 * 
	 * // Used rulebases
	 * liftingSettings.setUsedRulebases(applyPatchPage01.getSelectedRulebases
	 * ());
	 * 
	 * return liftingSettings; }
	 */

	protected ImageDescriptor getImageDescriptor(String name) {
		return ImageDescriptor.createFromURL(FileLocator.find(Platform.getBundle(Activator.PLUGIN_ID),
				new Path(String.format("icons/%s", name)), null));
	}
}
