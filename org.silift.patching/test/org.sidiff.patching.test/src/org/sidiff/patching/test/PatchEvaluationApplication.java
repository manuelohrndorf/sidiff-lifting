package org.sidiff.patching.test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
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
import org.sidiff.patching.test.sysml.SysMLTestSuitBuilder;
import org.sidiff.patching.util.PatchUtil;

public class PatchEvaluationApplication implements IApplication {

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

			SysMLTestSuitBuilder builder = new SysMLTestSuitBuilder(modelFolder);
			testSuites = builder.getTestSuites();
		}
		
		// Convert filegroups to modelgroups
		StringBuffer buffer = new StringBuffer();
		StringBuffer latexTable = new StringBuffer("Version & Korresp. & Differ. & Operationen & Längste Abhängigkeitskette\\\\\n");
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
				latexTable.append(testSuite.getId() + " & " + cor + " & " + dif + " & " + op + " & " + max + "\\\\\n");
				
				// Time to apply patch
				long start = System.currentTimeMillis();
				PatchResult result = patchEngine.applyPatchOperationValidation();
				long delta = System.currentTimeMillis() - start;
				LogUtil.log(LogEvent.NOTICE, "Time to apply: " + delta + "ms");
				buffer.append("Time to apply: " + delta/1000 + "seconds\n\n");
				
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
				
								
				//Folder and file structure
				String folder = new File(testSuite.getOriginal().getURI().toFileString()).getParentFile().getAbsolutePath() + "/";
				String patchedFileBase = folder + new File(testSuite.getOriginal().getURI().toFileString()).getName();
				String modifiedFileBase = folder + new File(testSuite.getModified().getURI().toFileString()).getName();
				
				String patchedSuffix = "_patched";
				String modifiedSuffix = "";
				
				if (type.equals("sysml")) {		
					patchedSuffix += ".uml";
					modifiedSuffix += ".uml";

				}
				else{
					patchedSuffix += ".xmi";
					modifiedSuffix += ".xmi";	
				}		
				
				URI patchedUri = URI.createFileURI(patchedFileBase + patchedSuffix);
				URI modifiedUri = URI.createFileURI(modifiedFileBase + modifiedSuffix);		
				
				// strip ids
				boolean keepIDs = false;
								
				Resource resourcePatchedStripped = PatchUtil.copyWithId(result.getPatchedResource(), patchedUri, keepIDs, new Copier());
				Resource resourceModifiedStripped = PatchUtil.copyWithId(testSuite.getModified(), modifiedUri, keepIDs, new Copier());				
			
				// Strip special characters in href references
				// Hard coded for SysML case study
				if (type.equals("sysml")) {

					stripSpecialCharactersAndSave(resourcePatchedStripped, patchedUri);
					stripSpecialCharactersAndSave(resourceModifiedStripped,modifiedUri);

				} else {

					// Saving patched Resource without IDs
					resourcePatchedStripped.save(null);

					// Saving modified Resource without IDs
					resourceModifiedStripped.save(null);

				}			
				
				boolean normalize = !type.equals("sysml");
				EList<Change> changes = ModelCompare.technicalEqual(resourceModifiedStripped, resourcePatchedStripped, normalize);
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
	
	private void stripSpecialCharactersAndSave(Resource resource, URI uri) throws IOException{
		
		//Load patched resource as string
		String resourceStrippedContent = null;
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			resource.save(outputStream, null);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}				
		resourceStrippedContent = outputStream.toString();	
		
		//Define patterns for replacement
		//Hard coded for sysml casestudy
		Map<Pattern,String> patternMap = new HashMap<Pattern, String>();
		patternMap.put(Pattern.compile("%5B"), "[");	
		patternMap.put(Pattern.compile("%5D"), "]");	
		patternMap.put(Pattern.compile("%23"), "#");	
		patternMap.put(Pattern.compile("%20"), " ");	
		patternMap.put(Pattern.compile("%3C"), "");	
		patternMap.put(Pattern.compile("%3E"), "");	
		patternMap.put(Pattern.compile("&lt;"), "");	
		patternMap.put(Pattern.compile(">>"), "");
		patternMap.put(Pattern.compile("<<"), "");
		patternMap.put(Pattern.compile(" AND "), "_AND_");
		patternMap.put(Pattern.compile("%20AND%20"), "_AND_");	
		patternMap.put(Pattern.compile(" OR "), "_OR_");
		patternMap.put(Pattern.compile("%20OR%20"), "_OR_");	
		patternMap.put(Pattern.compile("NOT "), "NOT_");
		patternMap.put(Pattern.compile("NOT%20"), "NOT_");
		patternMap.put(Pattern.compile("Separate "), "Separate");
		patternMap.put(Pattern.compile("Separate%20 "), "Separate");
		patternMap.put(Pattern.compile(" = "), "=");
		patternMap.put(Pattern.compile("%20=%20"), "=");
		patternMap.put(Pattern.compile(" := "), ":=");
		patternMap.put(Pattern.compile("%20:=%20"), ":=");
		patternMap.put(Pattern.compile(" := "), ":=");
		patternMap.put(Pattern.compile("Drehwinkel>"), "Drehwinkel");

		
		//Replace all patterns with corresponding replacement string
		for(Pattern pattern : patternMap.keySet()){
			Matcher m = pattern.matcher(resourceStrippedContent); 
			resourceStrippedContent = m.replaceAll(patternMap.get(pattern));					
		}
		
		patternMap.put(Pattern.compile("t#"), "t=");	
		patternMap.put(Pattern.compile("&"), "");	

		//Again to be sure
		for(Pattern pattern : patternMap.keySet()){
			Matcher m = pattern.matcher(resourceStrippedContent); 
			resourceStrippedContent = m.replaceAll(patternMap.get(pattern));					
		}
		
		//Write stripped resource contents as file
		Writer writer = new FileWriter(uri.toFileString());
	    writer.write(resourceStrippedContent);
		writer.close();		
		
	}

	@Override
	public void stop() {

	}

}
