package org.sidiff.patching.testdriver;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.osgi.framework.BundleContext;
import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.common.emf.access.Scope;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.common.file.ZipUtil;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.facade.AsymmetricDiffFacade;
import org.sidiff.matcher.IMatcher;
import org.sidiff.patching.PatchEngine;
import org.sidiff.patching.arguments.IArgumentManager;
import org.sidiff.patching.batch.arguments.BatchMatcherBasedArgumentManager;
import org.sidiff.patching.batch.handler.BatchInterruptHandler;
import org.sidiff.patching.interrupt.IPatchInterruptHandler;
import org.sidiff.patching.patch.patch.Patch;
import org.sidiff.patching.patch.patch.PatchFactory;
import org.sidiff.patching.settings.ExecutionMode;
import org.sidiff.patching.settings.PatchMode;
import org.sidiff.patching.settings.PatchingSettings;
import org.sidiff.patching.settings.PatchingSettings.ValidationMode;
import org.sidiff.patching.transformation.ITransformationEngine;
import org.sidiff.patching.transformation.TransformationEngineUtil;

public class ApplyPatch implements IApplication {

	// TODO: Refactor, also in patch bundles (shift to facade)
	public static final String ARCHIVE_URI_PREFIX = "archive:file:///";
	public static final String ARCHIVE_SEPERATOR = "!/";

	@SuppressWarnings("unused")
	private static BundleContext context = null;

	// **************** OSGi Lifecycle Management ****************

	public static void start(BundleContext context) {
		ApplyPatch.context = context;
	}

	public static void stop(BundleContext context) {
		ApplyPatch.context = null;
	}

	@Override
	public Object start(IApplicationContext context) throws Exception {
		main((String[]) context.getArguments().get(IApplicationContext.APPLICATION_ARGS));
		return IApplication.EXIT_OK;
	}

	@Override
	public void stop() {
	}

	// **************** Main ****************

	public static void main(String[] args) throws IOException {
		LogUtil.log(LogEvent.MESSAGE,"Apply patch...");		

		// Static information
		final ValidationMode validationMode = ValidationMode.NO_VALIDATION;
		final ExecutionMode executionMode = ExecutionMode.BATCH;
		final PatchMode patchMode = PatchMode.PATCHING;
		final Integer reliability = 0;
		final IPatchInterruptHandler patchInterruptHandler = new BatchInterruptHandler();
		final Scope scope = Scope.RESOURCE;

		// Gather all information
		final String patchFilename = args[0];
		final Patch patch = getPatch(patchFilename);
		final Resource originResource = patch.getAsymmetricDifference().getOriginModel();

		final String targetFilename = args[1];
		final Resource targetResource = EMFStorage.eLoad(URI.createFileURI(targetFilename)).eResource();

		final String documentType = getDocumentType(targetResource);

		final IMatcher matcher = getMatcher(originResource, targetResource);

		final IArgumentManager argumentManager = new BatchMatcherBasedArgumentManager(matcher);
		argumentManager.setMinReliability(reliability);

		ITransformationEngine transformationEngine = TransformationEngineUtil
				.getFirstTransformationEngine(documentType);

		// Copy target model
		String savePath = EMFStorage.uriToPath(targetResource.getURI());
		savePath = savePath.replace(targetResource.getURI().lastSegment(), "patched");
		String saveFile = savePath + File.separator + targetResource.getURI().lastSegment();
		EMFStorage.eSaveAs(EMFStorage.pathToUri(saveFile), targetResource.getContents().get(0), true);

		// Load copy again and work on this copy of the target model
		Resource resourcePatched = EMFStorage.eLoad(URI.createFileURI(saveFile)).eResource();

		// Prepare patch settings
		PatchingSettings patchingSettings = new PatchingSettings();
		patchingSettings.setArgumentManager(argumentManager);
		patchingSettings.setExecutionMode(executionMode);
		patchingSettings.setInterruptHandler(patchInterruptHandler);
		patchingSettings.setPatchMode(patchMode);
		patchingSettings.setScope(scope);
		patchingSettings.setTransformationEngine(transformationEngine);
		patchingSettings.setValidationMode(validationMode);

		LogUtil.log(LogEvent.MESSAGE, patchingSettings.toString());

		final PatchEngine patchEngine = new PatchEngine(patch.getAsymmetricDifference(), resourcePatched, patchingSettings);

		// TODO: We want to get some feedback..
		// patchEngine.getPatchReportManager().addPatchReportListener();

		// apply
		patchEngine.applyPatch(true);

		// Save result
		// Variante (1)
		for (Iterator<EObject> iterator = resourcePatched.getContents().iterator(); iterator.hasNext();) {
			EObject root = iterator.next();
			if (root instanceof EGenericType) {
				iterator.remove();
			}
		}
		resourcePatched.save(Collections.EMPTY_MAP);
		
		// Variante (2)
//		EMFStorage.eSaveAs(resourcePatched.getURI(), resourcePatched.getAllContents().next());

		LogUtil.log(LogEvent.MESSAGE, "Patch application one...");
	}

	private static Patch getPatch(String patchFilename) {
		// Search asymmetric difference:
		URI uri = null;
		for (String entry : ZipUtil.getEntries(patchFilename)) {
			if (entry.endsWith(AsymmetricDiffFacade.ASYMMETRIC_DIFF_EXT)) {
				uri = URI.createURI(ARCHIVE_URI_PREFIX + patchFilename + ARCHIVE_SEPERATOR + entry);
			}
		}
		// Load AsymmetricDifference
		ResourceSet resourceSet = new ResourceSetImpl();
		Resource patchResource = resourceSet.getResource(uri, true);

		if (patchResource.getContents().size() == 0) {
			System.err.println("Error in patch model: There is something wrong with this patch!");

		}
		EObject root = patchResource.getContents().get(0);
		if (root instanceof AsymmetricDifference) {
			final AsymmetricDifference difference = (AsymmetricDifference) root;
			final Patch patch = PatchFactory.eINSTANCE.createPatch();
			patch.setAsymmetricDifference(difference);
			return patch;
		} else {
			System.err.println("Patch Model Error: This is no patch model");
		}

		return null;
	}

	private static String getDocumentType(Resource targetResource) {
		String documentType = null;
		if (EMFModelAccess.isProfiled(targetResource)) {
			documentType = EMFModelAccess.getCharacteristicDocumentType(targetResource);
		} else {
			documentType = EMFModelAccess.getCharacteristicDocumentType(targetResource);
		}

		return documentType;
	}

	private static IMatcher getMatcher(Resource modelA, Resource modelB) {
		return org.sidiff.matcher.MatcherUtil.getAvailableMatchers(Arrays.asList(modelA, modelB)).iterator().next();
	}
}
