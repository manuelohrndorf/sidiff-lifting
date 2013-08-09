package org.sidiff.patching.test.gmf;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.difference.asymmetric.facade.AsymmetricDiffFacade;
import org.sidiff.difference.asymmetric.facade.util.Difference;
import org.sidiff.difference.matcher.IMatcher;
import org.sidiff.difference.matcher.util.MatcherUtil;
import org.sidiff.patching.IPatchCorrespondence;
import org.sidiff.patching.ITransformationEngine;
import org.sidiff.patching.PatchEngine;
import org.sidiff.patching.test.TestSuite;
import org.sidiff.patching.util.TransformatorUtil;

public class GMFTestSuitBuilder {
	static Logger LOGGER = Logger.getLogger(PatchEngine.class.getName());

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
				LOGGER.log(Level.SEVERE, "Error while creating patch " + original.getName() + " -> " + modified.getName(), e);
			} catch (AssertionError e) {
				LOGGER.log(Level.SEVERE, "AssertionError while creating patch " + original.getName() + " -> " + modified.getName(), e);
			}
		}
		return testsuits;
	}

	private TestSuite buildTestSuite(File originalFile, File modifiedFile) throws AssertionError, Exception {
		ResourceSet resourceSetOriginal = new ResourceSetImpl();
		Resource original = resourceSetOriginal.getResource(URI.createFileURI(originalFile.getAbsolutePath()), true);
		
		ResourceSet resourceSetModified = new ResourceSetImpl();
		Resource modified = resourceSetModified.getResource(URI.createFileURI(modifiedFile.getAbsolutePath()), true);
		
		if (matcher == null || transformationEngine == null) {
			String documentType = EMFModelAccess.getDocumentType(original);
			matcher = MatcherUtil.getMatcherByKey("UUIDMatcher", original, modified);
			transformationEngine = TransformatorUtil.getFirstTransformationEngine(documentType);
			if (transformationEngine == null) {
				LOGGER.log(Level.SEVERE, "No Transformation Engine found!");
				return null;
			}
		}
		
		String id = originalFile.getName() + "->" + modifiedFile.getName();
		LOGGER.log(Level.INFO, id);
		
		Difference difference = AsymmetricDiffFacade.liftMeUp(original, modified, matcher);

		IPatchCorrespondence correspondence = new GMFCorrespondence(difference);
		
		return new TestSuite(id, difference, original, modified, correspondence, transformationEngine);
	}

}
