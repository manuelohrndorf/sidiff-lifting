package org.silift.difference.lifting.testdriver;

import java.util.Set;

import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.difference.lifting.api.settings.LiftingSettings;
import org.sidiff.matcher.IMatcher;

public class LiftingTestAppUtil {
	
	@SuppressWarnings("serial")
	public static class SettingsInitializationException extends Exception {
		public SettingsInitializationException(Set<String> documentType, Throwable exception) {
			super(documentType.toString(), exception);
		}
	}

	public static LiftingSettings threadSafeSettings(Set<String> documentType) throws SettingsInitializationException  {
		try {
			LiftingSettings liftingSettings = new LiftingSettings(documentType);
			makeThreadSafeSettings(liftingSettings);
			return liftingSettings;
		} catch (Throwable e) {
			throw new SettingsInitializationException(documentType, e);
		}
	}
	
	public static LiftingSettings threadSafeSettings(Set<String> documentType, IMatcher matcher) throws SettingsInitializationException {
		LiftingSettings liftingSettings = new LiftingSettings(documentType);
		liftingSettings.setMatcher(matcher);
		try {
			makeThreadSafeSettings(liftingSettings);
		} catch (ReflectiveOperationException e) {
			throw new SettingsInitializationException(documentType, e);
		}
		return liftingSettings;
	}
	
	private static void makeThreadSafeSettings(LiftingSettings settings) throws ReflectiveOperationException {
		settings.setCorrespondencesService(settings.getCorrespondencesService().getClass().getDeclaredConstructor().newInstance());
		settings.setCandidatesService(settings.getCandidatesService().getClass().getDeclaredConstructor().newInstance());
		settings.setMatcher(settings.getMatcher().getClass().getDeclaredConstructor().newInstance());
		
		// Just to be sure...
		settings.getMatcher().setCandidatesService(settings.getCandidatesService());
		settings.getMatcher().setCorrespondencesService(settings.getCorrespondencesService());
	}
	
	public static Resource loadModel(String path) {
		return EMFStorage.eLoad(EMFStorage.pathToUri(path)).eResource();
	}
}
