package org.sidiff.patching.testdriver;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.osgi.framework.BundleContext;
import org.sidiff.common.emf.exceptions.InvalidModelException;
import org.sidiff.difference.lifting.facade.LiftingFacade;
import org.sidiff.difference.lifting.recognitionengine.ruleapplication.RecognitionEngineStatistics;
import org.sidiff.difference.lifting.settings.LiftingSettings;
import org.sidiff.difference.matcher.IMatcher;
import org.silift.common.util.access.EMFModelAccessEx;
import org.silift.common.util.exceptions.NoCorrespondencesException;

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

		System.out.println("create patch ....");
	}
	
}
