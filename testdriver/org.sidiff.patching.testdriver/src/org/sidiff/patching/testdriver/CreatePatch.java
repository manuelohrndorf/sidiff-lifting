package org.sidiff.patching.testdriver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Set;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.osgi.framework.BundleContext;
import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.common.emf.exceptions.InvalidModelException;
import org.sidiff.common.emf.exceptions.NoCorrespondencesException;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.difference.asymmetric.facade.AsymmetricDiffFacade;
import org.sidiff.difference.asymmetric.facade.util.Difference;
import org.sidiff.difference.lifting.facade.LiftingFacade;
import org.sidiff.difference.lifting.settings.LiftingSettings;
import org.sidiff.matcher.IMatcher;
import org.sidiff.patching.patch.patch.PatchCreator;

public class CreatePatch implements IApplication {

	@SuppressWarnings("unused")
	private static BundleContext context = null;

	// **************** OSGi Lifecycle Management ****************

	public static void start(BundleContext context) {
		CreatePatch.context = context;
	}

	public static void stop(BundleContext context) {
		CreatePatch.context = null;
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

	public static void main(String[] args) {
		LogUtil.log(LogEvent.MESSAGE,"Create patch...");
		
		Resource modelA = LiftingFacade.loadModel(args[0]);
		Resource modelB = LiftingFacade.loadModel(args[1]);
		String documentType = EMFModelAccess.getCharacteristicDocumentType(modelA);

		LiftingSettings settings = new LiftingSettings(documentType);
		settings.setCalculateEditRuleMatch(true);
		
		// Matcher is random!:
		Set<IMatcher> matcher = LiftingFacade.getAvailableMatchers(modelA, modelB);
		settings.setMatcher(matcher.iterator().next());

		// Save also EditRuleMatches
		settings.setSerializeEditRuleMatch(true);
		
		// Create an asymmetric difference
		Difference fullDiff;
		try {
			fullDiff = AsymmetricDiffFacade.liftMeUp(modelA, modelB, settings);
		} catch (InvalidModelException | NoCorrespondencesException e) {
			e.printStackTrace();
			return;
		}

		// Save as patch
		PatchCreator patchCreator = new org.sidiff.patching.patch.patch.PatchCreator(fullDiff.getAsymmetric(), settings);
		String diffSavePath = (new File(args[0])).getParent();
		try {
			String saveFile = patchCreator.serializePatch(diffSavePath);
			LogUtil.log(LogEvent.MESSAGE,"Save patch: " + saveFile);			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
	}

}
