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
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.sidiff.difference.asymmetric.Dependency;
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

public class PatchEvaluationApplication implements IApplication {
	private Logger logger = Logger.getLogger(Activator.class.getName());

	@Override
	public Object start(IApplicationContext context) throws Exception {
		String[] args = (String[]) context.getArguments().get(IApplicationContext.APPLICATION_ARGS);
		if (args.length != 4) {
			logger.log(Level.SEVERE, "Invalid number of application arguments! " + Arrays.toString(args));
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
		
		if (type == null || !(type.equals("gmf") || type.equals("smg"))) {
			throw new InvalidParameterException("Unkown type!");
		}
		
		List<TestSuite> testSuites = null;

		// Preparing SMG Testsuits
		if (type.equals("smg")) {
			// Group files by name
			SMGFileManager fileManager = new SMGFileManager(modelFolder);
			Collection<TestFileGroup> testFileGroups = fileManager.getTestFileGroups();
			for (TestFileGroup testFileGroup : testFileGroups) {
				logger.log(Level.FINE, testFileGroup.toString());
			}

			FileToModelConverter converter = new FileToModelConverter(testFileGroups);
			testSuites = converter.getTestSuites();
		}
		
		// Preparing GMF Testsuits
		if (type.equals("gmf")) {
			GMFTestSuitBuilder builder = new GMFTestSuitBuilder(modelFolder);
			testSuites = builder.getTestSuites();
		}
		
		// Convert filegroups to modelgroups
		StringBuffer buffer = new StringBuffer();
		StringBuffer latexTable = new StringBuffer("Version & Korresp. & Differ. & Operationen & Längste Abhängigkeitskette\\\\\n");
		for (TestSuite testSuite : testSuites) {
			try {
				logger.log(Level.INFO, "Testing " + testSuite.getId());
				PatchEngine patchEngine = new PatchEngine(testSuite.getAsymmetricDifference(), testSuite.getCorrespondence().getModelB(), testSuite.getCorrespondence(), testSuite.getTransformationEngine());
				buffer.append("--- Test " + testSuite.getId() + " ---\n");
				logger.log(Level.INFO, "Appling patch");
				
				// Some patch metrics
				int cor = testSuite.getDifference().getSymmetric().getCorrespondences().size();
				int dif = testSuite.getDifference().getSymmetric().getChanges().size();
				int op = testSuite.getAsymmetricDifference().getOperationInvocations().size();
				int max = getLongestDependencyChain(testSuite.getAsymmetricDifference().getDependencies());
				buffer.append("Correspondences: " + cor + 
							  "\nDifferences: " + dif + 
							  "\nOperations: " + op + 
							  "\nLongest dependency chain: " + max + "\n");
				latexTable.append(testSuite.getId() + " & " + cor + " & " + dif + " & " + op + " & " + max + "\\\\\n");
				
				// Time to apply patch
				long start = System.currentTimeMillis();
				PatchResult result = patchEngine.applyPatchOperationValidation();
				long delta = System.currentTimeMillis() - start;
				logger.log(Level.INFO, "Time to apply: " + delta + "ms");
				buffer.append("Time to apply: " + delta + "ms\n\n");
				
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
				
				// strip ids
//				Resource resourcePatched = PatchUtil.copyWithId(result.getPatchedResource(), result.getPatchedResource().getURI(), false, new Copier());
//				URI uri = PatchUtil.createURI(testSuite.getModified().getURI(), "id_removed_patch");
//				Resource resourceModified = PatchUtil.copyWithId(testSuite.getModified(), uri, false, new Copier());
				
				// Saving patched Resource
				String folder = new File(testSuite.getOriginal().getURI().toFileString()).getParentFile().getAbsolutePath() + "/";
				String patchedFileBase = folder + new File(testSuite.getOriginal().getURI().toFileString()).getName();
				ResourceSet resourceSetPatched = new ResourceSetImpl();
				URI patchedUri = URI.createFileURI(patchedFileBase +".patched.xmi");
				Resource resourcePatched = resourceSetPatched.createResource(patchedUri);
				resourcePatched.getContents().add(result.getPatchedResource().getContents().get(0));
				resourcePatched.save(Collections.EMPTY_MAP);
				
				// Saving modified Resource (into simple XMI without ids)
				String modifiedFileBase = folder + new File(testSuite.getModified().getURI().toFileString()).getName();
				ResourceSet resourceSetModified = new ResourceSetImpl();
				URI modifiedUri = URI.createFileURI(modifiedFileBase +".xmi");
				Resource resourceModified = resourceSetModified.createResource(modifiedUri);
				resourceModified.getContents().addAll(testSuite.getModified().getContents());
				resourceModified.save(Collections.EMPTY_MAP);
				
				// ... and now reload resources again
				resourceSetPatched = new ResourceSetImpl();
				resourcePatched = resourceSetPatched.getResource(patchedUri, true);
				resourceSetModified = new ResourceSetImpl();
				resourceModified = resourceSetModified.getResource(modifiedUri, true);
				
				
				EList<Change> changes = ModelCompare.technicalEqual(resourceModified, resourcePatched);
				if (changes.isEmpty()) {
					buffer.append("Patched model is equal to modified!\n");
				} else {
					buffer.append("ERROR: Patched model is not equal to modified!\n" + ModelCompare.getFormatedList(changes));
				}
			} catch (PatchNotExecuteableException e) {
				e.printStackTrace();
				logger.log(Level.SEVERE, "Test " + testSuite.getId() + " failed with exception!", e.getCause());
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

	private int getLongestDependencyChain(EList<Dependency> dependencies) {
		if (dependencies.isEmpty()) {
			return 1;
		}
		int max = 0;
		for (Dependency dependency : dependencies) {
			max = Math.max(max, getDependencyChain(dependency, 1));
		}
		return max;
	}
	
	private int getDependencyChain(Dependency dependency, int stage) {
		stage++;
		int base = stage;
		for (Dependency cDependency : dependency.getSource().getIncoming()) {
			stage = Math.max(stage, getDependencyChain(cDependency, base));
		}
		return stage;
	}

	@Override
	public void stop() {

	}

}
