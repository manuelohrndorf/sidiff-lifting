package org.sidiff.patching.test.gmf;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.difference.asymmetric.facade.AsymmetricDiffFacade;
import org.sidiff.difference.asymmetric.facade.util.Difference;
import org.sidiff.difference.matcher.IMatcher;
import org.sidiff.difference.matcher.util.MatcherUtil;
import org.sidiff.patching.IPatchCorrespondence;
import org.sidiff.patching.ITransformationEngine;
import org.sidiff.patching.test.TestSuite;
import org.sidiff.patching.util.TransformatorUtil;

public class GMFTestSuitBuilder {
	private File modelFolder;
	private IMatcher matcher;

	private ITransformationEngine transformationEngine;

	public GMFTestSuitBuilder(File modelFolder) {
		this.modelFolder = modelFolder;
	}

	public List<TestSuite> getTestSuites() {
		List<TestSuite> testsuits = new ArrayList<TestSuite>();
		FilenameFilter filter = new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith("ecore") && !name.contains("patch");
			}
		};
		File[] files = modelFolder.listFiles(filter);
		Arrays.sort(files);
		for (int i = 0; i < files.length - 1; i++) {
			File original = files[i];
			File modified = files[i + 1];
			try {
				TestSuite testSuite = buildTestSuite(original, modified);
				testsuits.add(testSuite);
			} catch (Exception e) {
				LogUtil.log(LogEvent.ERROR, "Error while creating patch " + original.getName() + " -> " + modified.getName(), e);
			} catch (AssertionError e) {
				LogUtil.log(LogEvent.ERROR, "AssertionError while creating patch " + original.getName() + " -> " + modified.getName(), e);
			}
		}
		return testsuits;
	}

	private TestSuite buildTestSuite(File originalFile, File modifiedFile) throws AssertionError, Exception {
		ResourceSet resourceSetOriginal = new ResourceSetImpl();
		Resource original = resourceSetOriginal.getResource(URI.createFileURI(originalFile.getAbsolutePath()), true);
		
		ResourceSet resourceSetModified = new ResourceSetImpl();
		Resource modified = resourceSetModified.getResource(URI.createFileURI(modifiedFile.getAbsolutePath()), true);
		
		if (matcher == null) {
			matcher = MatcherUtil.getMatcherByKey("UUIDMatcher", original, modified);
			if (matcher == null) {
				LogUtil.log(LogEvent.ERROR, "UUIDMatcher not found!");
				return null;
			}
		}
		
		if (transformationEngine == null){	
			String documentType = EMFModelAccess.getDocumentType(original);
			transformationEngine = TransformatorUtil.getFirstTransformationEngine(documentType);
			if (transformationEngine == null) {
				LogUtil.log(LogEvent.ERROR, "No Transformation Engine found!");
				return null;
			}
		}
		
		String id = originalFile.getName() + "->" + modifiedFile.getName();
		LogUtil.log(LogEvent.NOTICE, id);
		
		Difference difference = AsymmetricDiffFacade.liftMeUp(original, modified, matcher);

		IPatchCorrespondence correspondence = new GMFCorrespondence(difference);
		
		return new TestSuite(id, difference, original, modified, correspondence, transformationEngine);
	}

}
