package org.sidiff.difference.technical.ui.jobs;


import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.swt.widgets.Display;
import org.sidiff.common.emf.exceptions.InvalidModelException;
import org.sidiff.common.emf.exceptions.NoCorrespondencesException;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.common.logging.StatusWrapper;
import org.sidiff.common.ui.util.Exceptions;
import org.sidiff.common.ui.util.UIUtil;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.difference.technical.api.TechnicalDifferenceFacade;
import org.sidiff.difference.technical.api.settings.DifferenceSettings;
import org.sidiff.difference.technical.api.util.TechnicalDifferenceUtils;
import org.sidiff.difference.technical.ui.Activator;
import org.sidiff.difference.technical.ui.validation.ValidateDialog;
import org.sidiff.matching.input.InputModels;

public class CreateDifferenceJob extends Job {

	private InputModels inputModels;
	private DifferenceSettings settings;

	public CreateDifferenceJob(InputModels inputModels, DifferenceSettings settings) {
		super("Creating Symmetric Difference");
		this.inputModels = inputModels;
		this.settings = settings;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		/*
		 * Semantic Lifting
		 */
		return StatusWrapper.wrap(() -> {
			Resource resourceA = inputModels.getResources().get(0);
			Resource resourceB = inputModels.getResources().get(1);
			SymmetricDifference symmetricDiff = calculateDifference(resourceA, resourceB);

			/*
			 * Serialize lifted symmetricDiff
			 */

			String fileName = TechnicalDifferenceUtils.generateDifferenceFileName(resourceA, resourceB, settings);
			String diffSavePath = inputModels.getFiles().get(0).getParent().getLocation().toOSString();
			TechnicalDifferenceFacade.serializeTechnicalDifference(symmetricDiff, diffSavePath, fileName);

			/*
			 * Update workspace UI
			 */

			final String diffPath = EMFStorage.uriToPath(symmetricDiff.eResource().getURI());

			Display.getDefault().asyncExec(() -> Exceptions.log(() -> {
				inputModels.getProject().refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
				UIUtil.openEditor(diffPath);
				return Status.OK_STATUS;
			}));
			return Status.OK_STATUS;
		});
	}

	private SymmetricDifference calculateDifference(Resource resourceA, Resource resourceB) throws NoCorrespondencesException, InvalidModelException {
		SymmetricDifference symmetricDiff = null;
		try {
			symmetricDiff = TechnicalDifferenceFacade.deriveTechnicalDifference(resourceA, resourceB, settings);
		} catch(InvalidModelException e){
			ValidateDialog validateDialog = new ValidateDialog();
			boolean skipValidation = validateDialog.openErrorDialog(Activator.PLUGIN_ID, e);
			
			if (skipValidation) {
				// Retry without validation:
				settings.setValidate(false);
				symmetricDiff = calculateDifference(resourceA, resourceB);
			} else {
				throw e;
			}
		}
		TechnicalDifferenceUtils.sortDifference(symmetricDiff);
		return symmetricDiff;
	}
}
