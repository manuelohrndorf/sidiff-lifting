package org.sidiff.patching.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.difference.asymmetric.DependencyContainer;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.symmetric.Change;
import org.sidiff.patching.PatchEngine;
import org.sidiff.patching.PatchEngine.PatchResult;
import org.sidiff.patching.exceptions.PatchNotExecuteableException;
import org.sidiff.patching.report.PatchReport.Status;
import org.sidiff.patching.report.ReportEntry;
import org.sidiff.patching.test.gmf.GMFTestSuitBuilder;
import org.sidiff.patching.test.smg.FileToModelConverter;
import org.sidiff.patching.test.smg.SMGFileManager;
import org.sidiff.patching.test.smg.SMGFileManager.TestFileGroup;
import org.sidiff.patching.test.sysml.SysMLResourceFactory;
import org.sidiff.patching.test.sysml.SysMLTestSuitBuilder;

public class PatchEvaluationApplication implements IApplication {

	@SuppressWarnings("unchecked")
	@Override
	public Object start(IApplicationContext context) throws Exception {
		String[] args = (String[]) context.getArguments().get(IApplicationContext.APPLICATION_ARGS);
		if (args.length != 4) {
			LogUtil.log(LogEvent.ERROR, "Invalid number of application arguments! " + Arrays.toString(args));
			System.out.println("Argument must be a folder containing Models and a type!");
			return IApplication.EXIT_OK;
		}
		
		File modelFolder = null;
		String type = null;
		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("-folder")) {
				modelFolder = new File(args[++i]);
			} else if (args[i].equals("-type")) {
				type = args[++i];
			}
		}
		

		//Set experimental variables
		String xAxisLabel = "RevisionChange";
		Boolean threeD = true;
		Boolean transposed = false;
		Boolean showLabels = true;
		String experimentRun = "02->03";
		String experimentFile = "SiLift Pipeline_2013-10-22_10:48:23";
		String filename = modelFolder.getAbsolutePath() + "/charts/" + "SiLiftPipeLine.png";
		String filename2 = modelFolder.getAbsolutePath() + "/charts/" + experimentFile + "_cwoa.png";
		
		//ExperimentalUtil.loadExperiment(modelFolder.getAbsolutePath()+ "/" + experimentFile + ".ser");				

		/*
		Map<StatisticType, Boolean> showStatistic = new HashMap<StatisticsUtil.StatisticType, Boolean>();
		showStatistic.put(StatisticType.Count, false);
		showStatistic.put(StatisticType.Size, true);
		showStatistic.put(StatisticType.Time, false);
		Map<StatisticType, Boolean> stacked = new HashMap<StatisticsUtil.StatisticType, Boolean>();
		stacked.put(StatisticType.Count, false);
		stacked.put(StatisticType.Size, false);
		stacked.put(StatisticType.Time, false);
		Map<StatisticType, Boolean> logarithmic = new HashMap<StatisticsUtil.StatisticType, Boolean>();
		logarithmic.put(StatisticType.Count, false);
		logarithmic.put(StatisticType.Size, false);
		logarithmic.put(StatisticType.Time, false);
		Map<StatisticType, Boolean> percentage = new HashMap<StatisticsUtil.StatisticType, Boolean>();
		percentage.put(StatisticType.Count, false);
		percentage.put(StatisticType.Size, false);
		percentage.put(StatisticType.Time, false);
		Map<StatisticType, Boolean> showLabels = new HashMap<StatisticsUtil.StatisticType, Boolean>();
		showLabels.put(StatisticType.Count, false);
		showLabels.put(StatisticType.Size, false);
		showLabels.put(StatisticType.Time, false);
		Map<StatisticType, SeriesWithAxesType> seriesType = new HashMap<StatisticsUtil.StatisticType, ChartsUtil.SeriesWithAxesType>();
		seriesType.put(StatisticType.Count, SeriesWithAxesType.LineChart);
		seriesType.put(StatisticType.Size, SeriesWithAxesType.LineChart);
		seriesType.put(StatisticType.Time, SeriesWithAxesType.BarChart);		
		
		String yaxis1 = "# Elements";
		String yaxis2 = "TimeConsumption(ms)";
		
		String series1 = "Differences";
		String series2 = "Operations";
		
		
		HashMap<String, Boolean> stacked = new HashMap<String, Boolean>();
		stacked.put(yaxis1, false);
		stacked.put(yaxis2, false);

		HashMap<String, Boolean> logarithmic = new HashMap<String, Boolean>();
		logarithmic.put(yaxis1, false);
		logarithmic.put(yaxis2, true);
		
		HashMap<String, Boolean> percentage = new HashMap<String, Boolean>();
		percentage.put(yaxis1, false);
		percentage.put(yaxis2, false);
		
		HashMap<String, Boolean> showLabels = new HashMap<String, Boolean>();
		showLabels.put(series1, true);
		showLabels.put(series2, false);
	
		ArrayList<String> measurements = new ArrayList<String>();
		measurements.add("Scen02->Scen01");
		measurements.add("Scen01->Scen02");
		measurements.add("Scen03->Scen04");
		measurements.add("Scen04->Scen05");
		
		HashMap<String, SeriesWithAxesType> seriesType = new HashMap<String, SeriesWithAxesType>();
		seriesType.put(series1, SeriesWithAxesType.BarChart);
		seriesType.put(series2, SeriesWithAxesType.BarChart);

		
		HashMap<String, Number[]> valueMap1 = new HashMap<String, Number[]>();
		valueMap1.put(series1, new Number[]{12310,1233,12313,3001});
		HashMap<String, Number[]> valueMap2 = new HashMap<String, Number[]>();
		valueMap2.put(series2, new Number[]{120,88,13,234});
		
		HashMap<String, Number> valueMap3 = new HashMap<String, Number>();
		valueMap3.put("PatchApplication", 30231);
		valueMap3.put("Lifting", 2132);
		valueMap3.put("Matching", 23132);

		
		HashMap<String, Map<String, Number[]>> axisTovalueMap = new HashMap<String, Map<String,Number[]>>();
		axisTovalueMap.put(yaxis1, valueMap1);		
		axisTovalueMap.put(yaxis2, valueMap2);

		
		String filename1 = modelFolder.getAbsolutePath() + "/charts/GenericTest1.png";
		String filename2 = modelFolder.getAbsolutePath() + "/charts/GenericTest2.png";

		
		ChartsUtil.getInstance().writeChartWithAxes("SiLift", "RevisionChange",
				true, false, stacked, logarithmic, percentage, showLabels, measurements, seriesType,
				axisTovalueMap, filename1);
		
		ChartsUtil.getInstance().writeChartWithoutAxes("SiLift", "TimeConsumption(ms)", true,
				true, SeriesWithoutAxesType.PieChart, valueMap3, filename2);
		
		*/
		
		if (!modelFolder.exists() && !modelFolder.isDirectory()) {
			throw new FileNotFoundException(modelFolder.getPath());
		}
		
		if (type == null || !(type.equals("gmf") || !(type.equals("smg")) || !(type.equals("sysml")))) {
			throw new InvalidParameterException("Unkown type!");
		}	
		
		List<TestSuite> testSuites = null;

		// Preparing SMG Testsuites
		if (type.equals("smg")) {
			// Group files by name
			SMGFileManager fileManager = new SMGFileManager(modelFolder);
			Collection<TestFileGroup> testFileGroups = fileManager.getTestFileGroups();
			for (TestFileGroup testFileGroup : testFileGroups) {
				LogUtil.log(LogEvent.NOTICE, testFileGroup.toString());
			}

			FileToModelConverter converter = new FileToModelConverter(testFileGroups);
			testSuites = converter.getTestSuites();
		}
		
		// Preparing GMF Testsuites
		if (type.equals("gmf")) {
			GMFTestSuitBuilder builder = new GMFTestSuitBuilder(modelFolder);
			testSuites = builder.getTestSuites();
		}
		
		// Preparing SysML Testsuites
		if (type.equals("sysml")) {		
			// test suite
			SysMLTestSuitBuilder builder = new SysMLTestSuitBuilder(modelFolder);
			testSuites = builder.getTestSuites();
			
			// Eigene Subklasse von UMLResourceImpl (bzw.Factory dazu) mit useUUID-> false hier registrieren				
			Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
			Map<String, Object> m = reg.getExtensionToFactoryMap();
			m.put("sysml", new SysMLResourceFactory());
			m.put("uml", new SysMLResourceFactory());
		}
		
		//ExperimentalUtil.getInstance(null).generateChartWithAxes(xAxisLabel, threeD, transposed, filename);
		//System.exit(0);
		
		// Convert filegroups to modelgroups
		StringBuffer buffer = new StringBuffer();
		StringBuffer latexTable = new StringBuffer("Version & Corresp. & Differ. & Opera. & LDC & Duration(ms)\\\\\n");
		for (TestSuite testSuite : testSuites) {				
			
			try {
				LogUtil.log(LogEvent.NOTICE, "Testing " + testSuite.getId());
				PatchEngine patchEngine = new PatchEngine(testSuite.getAsymmetricDifference(), testSuite.getCorrespondence().getModelB(), testSuite.getCorrespondence(), testSuite.getTransformationEngine());
				buffer.append("--- Test " + testSuite.getId() + " ---\n");
				LogUtil.log(LogEvent.NOTICE, "Applying patch");	
				
			
				// Some patch metrics
				int cor = testSuite.getDifference().getSymmetric().getCorrespondences().size();
				int dif = testSuite.getDifference().getSymmetric().getChanges().size();
				int op = testSuite.getAsymmetricDifference().getOperationInvocations().size();
				int max = getLongestDependencyChain(testSuite.getAsymmetricDifference().getDepContainers());
				buffer.append("Correspondences: " + cor + 
							  "\nDifferences: " + dif + 
							  "\nOperations: " + op + 
							  "\nLongest dependency chain: " + max + "\n");	
				
				// Time to apply patch
				long start = System.currentTimeMillis();
				PatchResult result = patchEngine.applyPatchOperationValidation();

				long delta = System.currentTimeMillis() - start;				
				LogUtil.log(LogEvent.NOTICE, "Time to apply: " + delta + "ms");
				buffer.append("Time to apply: " + delta + "seconds\n\n");
				
				latexTable.append(testSuite.getId() + " & " + cor + " & " + dif + " & " + op + " & " + max + " & " + delta +"\\\\\n");
				
				// Distribution of Operations
				buffer.append("Operation & amount\\\\\n");
				Map<String, Integer> dist = getOperationDistribution(testSuite.getAsymmetricDifference().getOperationInvocations());
				buffer.append(mapToLatexTable(dist)+"\n");
				
				for (ReportEntry entry : result.getReport().getEntries()) {
					Status status = entry.getStatus();
					String description = entry.getDescription();
					buffer.append("ReportEntry: " + status + ": " + description + "\n");
				}

				// Saving patch
//				AsymmetricDiffFacade.serializeDifference(testSuite.getDifference(), folder, testSuite.getId()+".patch");
				
				Resource resourcePatched = null;
				Resource resourceModified = null;
				
				if (!type.equals("sysml")){
					// Saving patched Resource
					String folder = new File(testSuite.getOriginal().getURI().toFileString()).getParentFile().getAbsolutePath() + "/";
					String patchedFileBase = folder + new File(testSuite.getOriginal().getURI().toFileString()).getName();
					ResourceSet resourceSetPatched = new ResourceSetImpl();
					URI patchedUri = URI.createFileURI(patchedFileBase +".patched.xmi");
					resourcePatched = resourceSetPatched.createResource(patchedUri);
					resourcePatched.getContents().add(result.getPatchedResource().getContents().get(0));
					resourcePatched.save(Collections.EMPTY_MAP);
					
					// Saving modified Resource (into simple XMI without ids)
					String modifiedFileBase = folder + new File(testSuite.getModified().getURI().toFileString()).getName();
					ResourceSet resourceSetModified = new ResourceSetImpl();
					URI modifiedUri = URI.createFileURI(modifiedFileBase +".xmi");
					resourceModified = resourceSetModified.createResource(modifiedUri);
					resourceModified.getContents().addAll(testSuite.getModified().getContents());
					resourceModified.save(Collections.EMPTY_MAP);
					
				} else {					
					// Saving patched Resource
					resourcePatched = result.getPatchedResource();
					resourcePatched.save(Collections.EMPTY_MAP);
					
					// Saving modified Resource (into "my sysml" without ids)
					String folder = new File(testSuite.getOriginal().getURI().toFileString()).getParentFile().getAbsolutePath() + "/";
					String modifiedFileBase = folder + new File(testSuite.getModified().getURI().toFileString()).getName();
					ResourceSet resourceSetModified = new ResourceSetImpl();
					URI modifiedUri = URI.createFileURI(modifiedFileBase +".sysml");
					resourceModified = resourceSetModified.createResource(modifiedUri);
					resourceModified.getContents().addAll(testSuite.getModified().getContents());
					resourceModified.save(Collections.EMPTY_MAP);
				}
				
				// ... and now reload resources again
				ResourceSet resourceSetPatched = new ResourceSetImpl();
				resourcePatched = resourceSetPatched.getResource(resourcePatched.getURI(), true);
				ResourceSet resourceSetModified = new ResourceSetImpl();
				resourceModified = resourceSetModified.getResource(resourceModified.getURI(), true);
				
				resourcePatched.save(Collections.EMPTY_MAP);
				resourceModified.save(Collections.EMPTY_MAP);
				
				boolean normalize = true;
				EList<Change> changes = ModelCompare.technicalEqual(resourceModified, resourcePatched, normalize);
				
				// Saving patched Resource without IDs and normalized (just to inspect the output manually)
				resourcePatched.save(Collections.EMPTY_MAP);
				resourceModified.save(Collections.EMPTY_MAP);
				
				if (changes.isEmpty()) {
					buffer.append("Patched model is equal to modified!\n");
				} else {
					buffer.append("ERROR: Patched model is not equal to modified!\n" + ModelCompare.getFormatedList(changes));
				}			

				
			} catch (PatchNotExecuteableException e) {
				e.printStackTrace();
				LogUtil.log(LogEvent.ERROR, "Test " + testSuite.getId() + " failed with exception!", e.getCause());
				buffer.append("Failed with exception! Henshin rule cannot be applied! (" + e.getMessage() + ")\n");	
			}
			buffer.append("--------------\n\n");	
			


			
		}
		
		FileWriter writer = new FileWriter(modelFolder.getAbsolutePath()+"/report.txt");
		// Add summary table
		buffer.append(latexTable);
		writer.write(buffer.toString());
		writer.close();
		
	

		System.out.println("Test finished. Report: " + modelFolder.getAbsolutePath()+"/report.txt");
		return IApplication.EXIT_OK;
	}	
	
	private Map<String, Integer> getOperationDistribution(EList<OperationInvocation> operationInvocations) {
		Map <String, Integer> result = new HashMap<String, Integer>();
		for (OperationInvocation operationInvocation : operationInvocations) {
			String name = operationInvocation.getChangeSet().getName();
			if (result.containsKey(name)) {
				Integer integer = result.get(name);
				result.put(name, integer + 1);
			} else {
				result.put(name, 1);
			}
		}
		return result;
	}
	
	public String mapToLatexTable(Map<?, ?> map) {
		String result = "";
		for (Object object : map.keySet()) {
			String string = object.toString().replace("_", "\\_");
			result += string + " & " + map.get(object)+"\\\\\n";
		}
		return result;
	}

	private int getLongestDependencyChain(EList<DependencyContainer> dependencies) {
		if (dependencies.isEmpty()) {
			return 1;
		}
		int max = 0;
		for (DependencyContainer dependency : dependencies) {
			max = Math.max(max, getDependencyChain(dependency, 1));
		}
		return max;
	}
	
	private int getDependencyChain(DependencyContainer dependency, int stage) {
		stage++;
		int base = stage;
		for (DependencyContainer dep : dependency.getSource().getIncoming()) {
			stage = Math.max(stage, getDependencyChain(dep, base));
		}
		return stage;
	}
	
	@Override
	public void stop() {

	}

}
