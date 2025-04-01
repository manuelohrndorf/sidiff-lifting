package org.silift.difference.lifting.testdriver;

import java.io.File;
import java.util.Collections;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.osgi.framework.BundleContext;
import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.difference.lifting.api.LiftingFacade;
import org.sidiff.difference.lifting.api.settings.LiftingSettings;
import org.sidiff.difference.lifting.api.util.PipelineUtils;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.matcher.IMatcher;
import org.silift.difference.lifting.testdriver.LiftingTestAppUtil.SettingsInitializationException;

public class LiftingTestApp implements IApplication {

	@SuppressWarnings("unused")
	private static BundleContext context = null;

	// **************** OSGi Lifecycle Management ****************
	
	public static void start(BundleContext context) {
		LiftingTestApp.context = context;
	}

	public static void stop(BundleContext context) {
		LiftingTestApp.context = null;
	}

	@Override
	public Object start(IApplicationContext context) throws Exception {
		application((String[]) context.getArguments().get(IApplicationContext.APPLICATION_ARGS));
		return IApplication.EXIT_OK;
	}

	@Override
	public void stop() {
	}
	
	// **************** Main ****************
	
	public static void application(String[] args) throws SettingsInitializationException {
		Resource modelA = LiftingTestAppUtil.loadModel(args[0]);
		Resource modelB = LiftingTestAppUtil.loadModel(args[1]);
		String documentType = EMFModelAccess.getCharacteristicDocumentType(modelA);
		
		// Matcher is random!:
		IMatcher matcher = PipelineUtils.getAvailableMatchers(modelA, modelB).iterator().next();
//		LiftingSettings settings = new LiftingSettings(Collections.singleton(documentType));
		LiftingSettings settings = LiftingTestAppUtil.threadSafeSettings(Collections.singleton(documentType), matcher);
		
		try {
			SymmetricDifference symmetricDiff = LiftingFacade.liftTechnicalDifference(modelA, modelB, settings);
			
			String diffSaveFile = PipelineUtils.generateDifferenceFileName(modelA, modelB, settings);
			String diffSavePath = (new File(args[0])).getParent();
			
			LiftingFacade.serializeLiftedDifference(symmetricDiff, diffSavePath, diffSaveFile);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
