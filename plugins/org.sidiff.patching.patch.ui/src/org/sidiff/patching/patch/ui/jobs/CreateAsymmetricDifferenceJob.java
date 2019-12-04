package org.sidiff.patching.patch.ui.jobs;

import java.util.Collection;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.swt.widgets.Display;
import org.sidiff.common.emf.exceptions.InvalidModelException;
import org.sidiff.common.emf.exceptions.NoCorrespondencesException;
import org.sidiff.common.emf.input.InputModels;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.common.logging.StatusWrapper;
import org.sidiff.common.ui.util.Exceptions;
import org.sidiff.common.ui.util.UIUtil;
import org.sidiff.difference.asymmetric.api.AsymmetricDiffFacade;
import org.sidiff.difference.asymmetric.api.util.Difference;
import org.sidiff.difference.lifting.api.util.PipelineUtils;
import org.sidiff.difference.technical.ui.validation.ValidateDialog;
import org.sidiff.patching.api.settings.PatchingSettings;
import org.sidiff.patching.patch.ui.internal.PatchingPatchUiPlugin;
import org.silift.difference.symboliclink.SymbolicLinks;
import org.silift.difference.symboliclink.handler.ISymbolicLinkHandler;
import org.silift.difference.symboliclink.handler.util.SymbolicLinkHandlerUtil;

public class CreateAsymmetricDifferenceJob extends Job {

	private InputModels inputModels;
	private PatchingSettings settings;
	private boolean serialize;
	
	private Difference difference;
	
	public CreateAsymmetricDifferenceJob(InputModels inputModels, PatchingSettings settings) {
		this(inputModels, settings, true);
	}
	
	public CreateAsymmetricDifferenceJob(InputModels inputModels, PatchingSettings settings, boolean serialize) {
		super("Creating Asymmetric Difference");
		this.inputModels = inputModels;
		this.settings = settings;
		this.serialize = serialize;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {

		/*
		 * Create patch:
		 */

		return StatusWrapper.wrap(() -> {		
			// Print report:
			LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");
			LogUtil.log(LogEvent.NOTICE, "---------------------- Create Difference -------------------");
			LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");

			Resource resourceA = inputModels.getResources().get(0);
			Resource resourceB = inputModels.getResources().get(1);
			difference = calculateDifference(resourceA, resourceB);

			if(serialize) {
				URI outputDir = EMFStorage.toPlatformURI(inputModels.getFiles().get(0).getParent());
				
				if(settings.useSymbolicLinks()) {
					ISymbolicLinkHandler symbolicLinkHandler = settings.getSymbolicLinkHandler();
					// TODO add reliability setting to lifting settings
					Collection<SymbolicLinks> symbolicLinksCollection =  symbolicLinkHandler.generateSymbolicLinks(difference.getAsymmetric(), false);
					SymbolicLinkHandlerUtil.serializeSymbolicLinks(inputModels.getResourceSet(),
							symbolicLinksCollection, difference.getAsymmetric(), outputDir);
				}
				
				final String fileName = PipelineUtils.generateDifferenceFileName(resourceA, resourceB, settings);
				difference.serialize(inputModels.getResourceSet(), outputDir, fileName);
				
				/*
				 * Update workspace UI
				 */
				Display.getDefault().asyncExec(() -> Exceptions.log(() -> {
					inputModels.getProject().refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
					UIUtil.openEditor(EMFStorage.toFile(difference.getAsymmetric().eResource().getURI()));
					return Status.OK_STATUS;
				}));
			}

			return Status.OK_STATUS;
		});
	}

	private Difference calculateDifference(Resource resourceA, Resource resourceB) throws NoCorrespondencesException, InvalidModelException {
		Difference fullDiff = null;
		try {
			fullDiff = AsymmetricDiffFacade.deriveLiftedAsymmetricDifference(resourceA, resourceB, settings);
		} catch(InvalidModelException e){
			boolean skipValidation = ValidateDialog.openErrorDialog(PatchingPatchUiPlugin.PLUGIN_ID, e);
			if (skipValidation) {
				// Retry without validation:
				settings.setValidate(false);
				fullDiff = calculateDifference(resourceA, resourceB);
			} else {
				throw e;
			}
		}
		PipelineUtils.sortDifference(fullDiff.getSymmetric());
		return fullDiff;
	}

	public Difference getDifference() {
		return difference;
	}
}
