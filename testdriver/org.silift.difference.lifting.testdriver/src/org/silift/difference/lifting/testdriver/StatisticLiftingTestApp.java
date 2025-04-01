package org.silift.difference.lifting.testdriver;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.osgi.framework.BundleContext;
import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.common.emf.exceptions.InvalidModelException;
import org.sidiff.common.emf.exceptions.NoCorrespondencesException;
import org.sidiff.difference.lifting.api.LiftingFacade;
import org.sidiff.difference.lifting.api.settings.LiftingSettings;
import org.sidiff.difference.lifting.api.util.PipelineUtils;
import org.sidiff.difference.lifting.recognitionengine.util.RecognitionEngineUtil;
import org.sidiff.matcher.IMatcher;
import org.silift.difference.lifting.testdriver.LiftingTestAppUtil.SettingsInitializationException;

public class StatisticLiftingTestApp implements IApplication {

	// **************** Test Cases ****************
	
	/**
	 * Do a "warm up" lifting on the start (to neglect initializations).
	 */
	private static boolean warmUp = true;
	
	/**
	 * Stop statistic after given count of revisions. -1 means all in the given folder. 
	 */
	private static int maxRevisionCount = -1;
	
	/**
	 * Start test case column. (see below)
	 */
	private static int startTestCase = 0;
	
	/**
	 * Stop test case column. (see below)
	 */
	private static int stopTestCase = 0; // 5
	
	// Optimizations: (see settings): (one case per column)
	private static boolean[] ruleSetReduction 	= {true, false, false, true , true , false};
	private static boolean[] buildGraphPerRule 	= {true, true , false, false, true , false};
	private static boolean[] useThreadPool 		= {true, true , true,  true , false, false};
	
	// **************** Settings ****************
	
	private static String csvFolder = "D:\\Git\\sidiff\\lifting\\test\\org.silift.difference.lifting.testdriver\\Statistics\\";
	
	private static String modelFolder = "D:\\Git\\sidiff\\lifting\\testdata\\org.sidiff.mixeddomains.testdata\\gmf\\gmfgen";
	private static String modelExtension = "ecore";
	
	private static File[] modelRevisions = readFolder(modelFolder);
	
	private static String matcherName = "XMI ID Matcher";
	
	private static int garbageCollectorBreak = 0; // seconds // 2

	// **************** OSGi Lifecycle Management ****************
	
	@SuppressWarnings("unused")
	private static BundleContext context = null;
	
	public static void start(BundleContext context) {
		StatisticLiftingTestApp.context = context;
	}

	public static void stop(BundleContext context) {
		StatisticLiftingTestApp.context = null;
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

		for (int i = startTestCase; i <= stopTestCase; i++) {
			System.out.println("\n-------------------------------------------------------------\n");

			String csvFile = csvFolder + getCSVFile();
			runTestCase(i, csvFile);
			
			System.out.println("-------------------------------------------------------------");
		}
	}
	
	private static void runTestCase(int testCase, String csvFile) throws SettingsInitializationException {
		String lastSettings = null;
		
		if (warmUp) {
			Resource modelA = LiftingTestAppUtil.loadModel(modelRevisions[0].getAbsolutePath());
			Resource modelB = LiftingTestAppUtil.loadModel(modelRevisions[1].getAbsolutePath());
			System.out.println("Warm up lifting: " + modelA.getURI().lastSegment() + " <-> " + modelB.getURI().lastSegment());
			startLifting(modelA, modelB, testCase, csvFile);
		}
		
		for (int i = 0; i < (modelRevisions.length - 1); i++) {
			Resource modelA = LiftingTestAppUtil.loadModel(modelRevisions[i].getAbsolutePath());
			Resource modelB = LiftingTestAppUtil.loadModel(modelRevisions[i + 1].getAbsolutePath());
			
			System.out.println("Lifting: " + modelA.getURI().lastSegment() + " <-> " + modelB.getURI().lastSegment());
			
			lastSettings = startLifting(modelA, modelB, testCase, csvFile).toString();
			
			if ((maxRevisionCount != -1) && ((i + 2) > maxRevisionCount)) {
				break;
			}

			try {
				System.gc();
				Thread.sleep(garbageCollectorBreak*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		appendSettings(lastSettings, csvFile);
	}
	
	private static LiftingSettings startLifting(Resource modelA, Resource modelB, int testCase, String csvFile) throws SettingsInitializationException {
		try {
			
			// Create lifting settings:
			LiftingSettings settings =  buildSettings(modelA, modelB, testCase);
			settings.setRecognitionEngine(RecognitionEngineUtil.getDefaultRecognitionEngine(
					PipelineUtils.createRecognitionEngineSetup(settings)));
			
			settings.getRecognitionEngine().getStatistic().enable(csvFile);
			
			// Run lifting:
			LiftingFacade.liftTechnicalDifference(modelA, modelB, settings);
			settings.getRecognitionEngine().getStatistic().disable();
			
			return settings;
		} catch (InvalidModelException e) {
			e.printStackTrace();
		} catch (NoCorrespondencesException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static void appendSettings(String settings, String csvFile) {
		System.out.println("\nSettings:\n");
		System.out.println(settings);
		
		BufferedWriter writer = null;
		
		try {
			writer = new BufferedWriter(new FileWriter(csvFile, true));
			writer.write("\n" + settings);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private static String getCSVFile() {
		Calendar cal = Calendar.getInstance();
		Date time = cal.getTime();
		DateFormat formatter = new SimpleDateFormat("yy-MM-dd hh-mm-ss");
		String fileName = formatter.format(time) + ".csv";
		
		try {
			(new File(csvFolder + fileName)).createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return fileName;
	}
	
	private static LiftingSettings buildSettings(Resource modelA, Resource modelB, int testCase) throws SettingsInitializationException {
		String documentType = EMFModelAccess.getCharacteristicDocumentType(modelA);
		IMatcher matcher = getMatcher(modelA, modelB);
		
//		LiftingSettings settings = new LiftingSettings(Collections.singleton(documentType));
		LiftingSettings settings = LiftingTestAppUtil.threadSafeSettings(Collections.singleton(documentType), matcher);
		settings.setMatcher(matcher);
		settings.setTechBuilder(PipelineUtils.getDefaultTechnicalDifferenceBuilder(Collections.singleton(documentType)));
		settings.setRuleSetReduction(ruleSetReduction[testCase]);
		settings.setBuildGraphPerRule(buildGraphPerRule[testCase]);
		settings.setUseThreadPool(useThreadPool[testCase]);
		
		return settings;
	}
	
	private static IMatcher getMatcher(Resource modelA, Resource modelB) {
		List<IMatcher> matchers = PipelineUtils.getAvailableMatchers(modelA, modelB);
		
		for (IMatcher matcher : matchers) {
			if (matcher.getName().toLowerCase().contains(matcherName.toLowerCase())) {
				return matcher;
			}
		}
		
		throw new RuntimeException("Matcher not found: " + matcherName);
	}
	
	private static File[] readFolder(String path) {
		File[] files = (new File(path)).listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(modelExtension);
			}
		});
		if (files == null){
			System.err.println("Cannot find path: " + path);
			files = new File[0];
		}
		
		Arrays.sort(files);
		
		return files;
	}
}
