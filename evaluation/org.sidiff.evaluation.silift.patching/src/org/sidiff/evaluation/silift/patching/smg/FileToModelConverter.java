package org.sidiff.evaluation.silift.patching.smg;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.common.emf.exceptions.InvalidModelException;
import org.sidiff.common.emf.exceptions.NoCorrespondencesException;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.difference.asymmetric.facade.AsymmetricDiffFacade;
import org.sidiff.difference.asymmetric.facade.util.Difference;
import org.sidiff.difference.lifting.settings.LiftingSettings;
import org.sidiff.evaluation.silift.patching.TestSuite;
import org.sidiff.evaluation.silift.patching.smg.SMGFileManager.TestFileGroup;
import org.sidiff.matching.model.Correspondence;
import org.sidiff.patching.batch.handler.BatchInterruptHandler;
import org.sidiff.patching.interrupt.IPatchInterruptHandler;
import org.sidiff.patching.transformation.ITransformationEngine;
import org.sidiff.patching.transformation.TransformationEngineUtil;

public class FileToModelConverter {
	
	private Collection<TestFileGroup> testFileGroups;

	public FileToModelConverter(Collection<TestFileGroup> testFileGroups) {
		this.testFileGroups = testFileGroups;
	}

	public List<TestSuite> getTestSuites() throws InvalidModelException {
		List<TestSuite> testSuites = new ArrayList<TestSuite>();
		for (TestFileGroup testFileGroup : testFileGroups) {
			LogUtil.log(LogEvent.NOTICE, "Converting Test " + testFileGroup.id);
			ResourceSet resourceSet = new ResourceSetImpl();
			String id = testFileGroup.id;
			Resource original = getResource(resourceSet, testFileGroup.original);
			Resource modified = getResource(resourceSet, testFileGroup.modified);

			String documentType = EMFModelAccess.getDocumentType(original);
			LiftingSettings liftingSettings = new LiftingSettings(documentType);			
			liftingSettings.setCalculateEditRuleMatch(true);
			liftingSettings.setMatcher(new SMGMatcher(getCorrespondences(resourceSet, testFileGroup.matching)));
			Difference difference = null;
			try {
				difference = AsymmetricDiffFacade.liftMeUp(original, modified, liftingSettings);
			} catch (NoCorrespondencesException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			SMGPatchCorrespondence correspondence = new SMGPatchCorrespondence(difference.getAsymmetric().getOriginModel());
			
			ITransformationEngine transformationEngine = TransformationEngineUtil.getFirstTransformationEngine(documentType);
			if (transformationEngine == null) {
				LogUtil.log(LogEvent.ERROR, "No Transformation Engine found!");
				return null;
			}
			
			IPatchInterruptHandler	patchInterruptHandler = new BatchInterruptHandler();
			
			testSuites.add(new TestSuite(id, difference, original, modified, correspondence, transformationEngine, patchInterruptHandler));
		}
		return testSuites;
	}

	private Resource getResource(ResourceSet resourceSet, File file) {
		Resource resource = resourceSet.getResource(URI.createFileURI(file.getAbsolutePath()), true);
		return resource;
	}

	private List<Correspondence> getCorrespondences(ResourceSet resourceSet, File matchingFile) {
//		prepareResource(matchingFile);
//		Matching matching = (Matching) getResource(resourceSet, matchingFile).getContents().get(0);
		List<Correspondence> correspondences = new ArrayList<Correspondence>();
//		for (org.sidiff.pipeline.correspondences.model.Correspondence smgCor : matching.getCorrespondences()) {
//			Correspondence correspondence = SymmetricFactory.eINSTANCE.createCorrespondence(smgCor.getMatchedA(), smgCor.getMatchedB());			
//			correspondences.add(correspondence);
//		}
		return correspondences;
	}

	private void prepareResource(File matching) {
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			FileReader fr = new FileReader(matching);
			br = new BufferedReader(fr);
			StringBuffer stringBuffer = new StringBuffer();
			String thisLine;
			while ((thisLine = br.readLine()) != null) {
				thisLine = thisLine.replaceAll(" uri[A,B]=\".+?\"", "");
				stringBuffer.append(thisLine + "\n");
			}

			FileWriter fw = new FileWriter(matching);
			bw = new BufferedWriter(fw);
			bw.write(stringBuffer.toString());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
				if (bw != null)
					bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
