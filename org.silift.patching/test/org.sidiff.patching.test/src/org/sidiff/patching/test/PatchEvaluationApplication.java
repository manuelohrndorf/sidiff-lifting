package org.sidiff.patching.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
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
import org.sidiff.difference.asymmetric.facade.AsymmetricDiffFacade;
import org.sidiff.difference.lifting.facade.LiftingFacade;
import org.sidiff.difference.symmetric.Change;
import org.sidiff.patching.PatchEngine;
import org.sidiff.patching.PatchEngine.ExecutionMode;
import org.sidiff.patching.PatchEngine.PatchMode;
import org.sidiff.patching.report.OperationExecutionEntry;
import org.sidiff.patching.report.OperationExecutionKind;
import org.sidiff.patching.report.ReportEntry;
import org.sidiff.patching.report.ValidationEntry;
import org.sidiff.patching.test.ft.FtTestSuiteBuilder;
import org.sidiff.patching.test.gmf.GMFTestSuiteBuilder;
import org.sidiff.patching.test.sa.SaTestSuiteBuilder;
import org.sidiff.patching.test.smg.FileToModelConverter;
import org.sidiff.patching.test.smg.SMGFileManager;
import org.sidiff.patching.test.smg.SMGFileManager.TestFileGroup;
import org.sidiff.patching.test.sysml.SysMLResourceFactory;
import org.sidiff.patching.test.sysml.SysMLTestSuitBuilder;
import org.sidiff.patching.validation.IValidationError;
import org.sidiff.patching.validation.ValidationMode;
import org.silift.common.util.emf.EMFStorage;
import org.silift.common.util.emf.Scope;

public class PatchEvaluationApplication implements IApplication {

	static final boolean INSPECT_VALIDATION_ERRORS = false;
	static final boolean INSPECT_PASSED_OPERATIONS = false;
	static final boolean SAVE_ASYM_DIFFERENCE = false;
	static final boolean SAVE_SYM_DIFFERENCE = true;
	static final boolean SAVE_OPERATION_DISTRIBUTION = true;

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

		if (!modelFolder.exists() && !modelFolder.isDirectory()) {
			throw new FileNotFoundException(modelFolder.getPath());
		}

		assert (type != null);
		assert (type.equals("gmf") || type.equals("smg") || type.equals("sysml") || type.equals("sa") || type
				.equals("ft"));

		// // Experiment run
		// String experimentRun = "01->02";
		// ExperimentalUtil.getInstance(null).startRun(experimentRun);

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
			GMFTestSuiteBuilder builder = new GMFTestSuiteBuilder(modelFolder);
			testSuites = builder.getTestSuites();
		}

		// Preparing SA Testsuites
		if (type.equals("sa")) {
			SaTestSuiteBuilder builder = new SaTestSuiteBuilder(modelFolder);
			testSuites = builder.getTestSuites();
		}

		// Preparing FT Testsuites
		if (type.equals("ft")) {
			FtTestSuiteBuilder builder = new FtTestSuiteBuilder(modelFolder);
			testSuites = builder.getTestSuites();
		}

		// Preparing SysML Testsuites
		if (type.equals("sysml")) {
			// test suite
			SysMLTestSuitBuilder builder = new SysMLTestSuitBuilder(modelFolder);
			testSuites = builder.getTestSuites();

			// Eigene Subklasse von UMLResourceImpl (bzw.Factory dazu) mit
			// useUUID-> false hier registrieren
			Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
			Map<String, Object> m = reg.getExtensionToFactoryMap();
			m.put("sysml", new SysMLResourceFactory());
			m.put("uml", new SysMLResourceFactory());
		}

		// Distribution of operation invocations
		OperationDistribution distribution = new OperationDistribution();

		// Convert filegroups to modelgroups
		StringBuffer buffer = new StringBuffer();
		StringBuffer latexTable = new StringBuffer("Version & Corresp. & Differ. & Opera. & LDC & Duration(ms)\\\\\n");
		for (TestSuite testSuite : testSuites) {

			LogUtil.log(LogEvent.NOTICE, "Testing " + testSuite.getId());
			PatchEngine patchEngine = new PatchEngine(testSuite.getAsymmetricDifference(), testSuite
					.getCorrespondence().getTargetModel(), testSuite.getCorrespondence(),
					testSuite.getTransformationEngine(), ExecutionMode.BATCH, PatchMode.PATCHING, ValidationMode.ITERATIVE, Scope.RESOURCE,
					false, testSuite.getPatchInterruptHandler());
			buffer.append("--- Test " + testSuite.getId() + " ---\n");
			LogUtil.log(LogEvent.NOTICE, "Applying patch");

			// Some patch metrics
			int cor = testSuite.getDifference().getSymmetric().getCorrespondences().size();
			int dif = testSuite.getDifference().getSymmetric().getChanges().size();
			int op = testSuite.getAsymmetricDifference().getOperationInvocations().size();
			int max = getLongestDependencyChain(testSuite.getAsymmetricDifference().getDepContainers());
			buffer.append("Correspondences: " + cor + "\nDifferences: " + dif + "\nOperations: " + op
					+ "\nLongest dependency chain: " + max + "\n");

			distribution.append(testSuite.getId(), testSuite.getDifference());

			// Time to apply patch
			long start = System.currentTimeMillis();
			patchEngine.applyPatch();

			long delta = System.currentTimeMillis() - start;
			LogUtil.log(LogEvent.NOTICE, "Time to apply: " + delta + "ms");
			buffer.append("Time to apply: " + delta + "seconds\n\n");

			latexTable.append(testSuite.getId() + " & " + cor + " & " + dif + " & " + op + " & " + max + " & " + delta
					+ "\\\\\n");

			// Distribution of Operations
			buffer.append("Operation & amount\\\\\n");
			Map<String, Integer> dist = getOperationDistribution(testSuite.getAsymmetricDifference()
					.getOperationInvocations());
			buffer.append(mapToLatexTable(dist) + "\n");

			// More Details from report..
			for (ReportEntry entry : patchEngine.getPatchReportManager().getLastReport().getEntries()) {
				if (entry instanceof ValidationEntry) {
					ValidationEntry validationEntry = (ValidationEntry) entry;
					buffer.append("=> ValidationEntry: " + validationEntry.getDescription() + "\n");
					if (INSPECT_VALIDATION_ERRORS) {
						for (IValidationError error : validationEntry.getCurrentValidationErrors()) {
							buffer.append("\t" + "msg: " + error.getMessage() + " | src: " + error.getSource());
							if (error.getException() != null) {
								buffer.append(" | exception: " + error.getException().getMessage());
							}
							buffer.append("\n");
						}
					}
				} else {
					OperationExecutionEntry executionEntry = (OperationExecutionEntry) entry;
					if (executionEntry.getKind() == OperationExecutionKind.EXEC_FAILED) {
						buffer.append("Failed OperationInvocation: " + executionEntry.getDescription() + "\n");
					}
					if (executionEntry.getKind() == OperationExecutionKind.PASSED && INSPECT_PASSED_OPERATIONS) {
						buffer.append("Executed OperationInvocation: " + executionEntry.getDescription() + "\n");
					}
					if (executionEntry.getKind() == OperationExecutionKind.EXEC_WARNING && INSPECT_PASSED_OPERATIONS) {
						buffer.append("Executed OperationInvocation (with warning): " + executionEntry.getDescription()
								+ "\n");
					}
				}

			}
			buffer.append("\n");

			// Save various out files..
			String folder = new File(testSuite.getOriginal().getURI().toFileString()).getParentFile().getAbsolutePath()
					+ "/";

			// Saving patch
			if (SAVE_SYM_DIFFERENCE) {
				File symOut = new File(folder + System.getProperty("file.separator")
						+ testSuite.getId().replace("->", "-") + "." + LiftingFacade.SYMMETRIC_DIFF_EXT);
				URI uriSymOut = URI.createFileURI(symOut.getAbsolutePath());
				EMFStorage.eSaveAs(uriSymOut, testSuite.getDifference().getSymmetric());
			}
			if (SAVE_ASYM_DIFFERENCE) {
				File asymOut = new File(folder + System.getProperty("file.separator")
						+ testSuite.getId().replace("->", "-") + "." + AsymmetricDiffFacade.ASYMMETRIC_DIFF_EXT);
				URI uriAsymOut = URI.createFileURI(asymOut.getAbsolutePath());
				EMFStorage.eSaveAs(uriAsymOut, testSuite.getDifference().getAsymmetric());
			}

			Resource resourcePatched = null;
			Resource resourceModified = null;

			if (!type.equals("sysml")) {
				// Saving patched Resource
				String patchedFileBase = folder + new File(testSuite.getOriginal().getURI().toFileString()).getName();
				ResourceSet resourceSetPatched = new ResourceSetImpl();
				URI patchedUri = URI.createFileURI(patchedFileBase + ".patched.xmi");
				resourcePatched = resourceSetPatched.createResource(patchedUri);
				resourcePatched.getContents().add(patchEngine.getPatchedResource().getContents().get(0));
				resourcePatched.save(Collections.EMPTY_MAP);

				// Saving modified Resource (into simple XMI without ids)
				String modifiedFileBase = folder + new File(testSuite.getModified().getURI().toFileString()).getName();
				ResourceSet resourceSetModified = new ResourceSetImpl();
				URI modifiedUri = URI.createFileURI(modifiedFileBase + ".xmi");
				resourceModified = resourceSetModified.createResource(modifiedUri);
				resourceModified.getContents().addAll(testSuite.getModified().getContents());
				resourceModified.save(Collections.EMPTY_MAP);

			} else {
				// Saving patched Resource
				resourcePatched = patchEngine.getPatchedResource();
				resourcePatched.save(Collections.EMPTY_MAP);

				// Saving modified Resource (into "my sysml" without ids)
				String modifiedFileBase = folder + new File(testSuite.getModified().getURI().toFileString()).getName();
				ResourceSet resourceSetModified = new ResourceSetImpl();
				URI modifiedUri = URI.createFileURI(modifiedFileBase + ".sysml");
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

			// Saving patched Resource without IDs and normalized (just to
			// inspect the output manually)
			resourcePatched.save(Collections.EMPTY_MAP);
			resourceModified.save(Collections.EMPTY_MAP);

			if (changes.isEmpty()) {
				buffer.append("Patched model is equal to modified!\n");
			} else {
				buffer.append("ERROR: Patched model is not equal to modified!\n"
						+ ModelCompare.getFormatedList(changes));
			}

			buffer.append("------------------------------------------------------\n\n\n");
		}

		FileWriter writer = new FileWriter(modelFolder.getAbsolutePath() + "/report.txt");
		// Add summary table
		buffer.append(latexTable);
		writer.write(buffer.toString());
		writer.close();

		// Distribution
		if (SAVE_OPERATION_DISTRIBUTION) {
			distribution.toCSV(modelFolder.getAbsolutePath() + "/distribution.csv");
		}
		
		// Generate PieChart
		// String filename = modelFolder.getAbsolutePath() + "/charts/" +
		// "SiLiftPipeLine_timeRatio.png";
		// ExperimentalUtil.getInstance(null).generateChartWithoutAxes(experimentRun,
		// StatisticType.Time, false, true, SeriesWithoutAxesType.PieChart,
		// filename);

		System.out.println("Test finished. Report: " + modelFolder.getAbsolutePath() + "/report.txt");
		return IApplication.EXIT_OK;
	}

	private Map<String, Integer> getOperationDistribution(EList<OperationInvocation> operationInvocations) {
		Map<String, Integer> result = new HashMap<String, Integer>();
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
			result += string + " & " + map.get(object) + "\\\\\n";
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
