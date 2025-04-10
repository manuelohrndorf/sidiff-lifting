package org.sidiff.evaluation.silift.patching.sysml;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.difference.asymmetric.facade.AsymmetricDiffFacade;
import org.sidiff.difference.asymmetric.facade.util.Difference;
import org.sidiff.evaluation.silift.patching.TestSuite;
import org.sidiff.matcher.IMatcher;
import org.sidiff.matcher.MatcherUtil;
import org.sidiff.patching.batch.handler.BatchInterruptHandler;
import org.sidiff.patching.interrupt.IPatchInterruptHandler;
import org.sidiff.patching.transformation.ITransformationEngine;
import org.sidiff.patching.transformation.TransformationEngineUtil;

public class SysMLTestSuitBuilder {
	private File modelFolder;
	private IMatcher matcher;

	private ITransformationEngine transformationEngine;
	private IPatchInterruptHandler patchInterruptHandler;

	public SysMLTestSuitBuilder(File modelFolder) {
		this.modelFolder = modelFolder;
	}

	public List<TestSuite> getTestSuites() {
		List<TestSuite> testsuits = new ArrayList<TestSuite>();
		FilenameFilter filter = new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith("uml") && !name.contains("patch");
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
		
		// adapt models for batch test
		deleteEAnnotations(original);
		deleteEAnnotations(modified);
		
		if (matcher == null) {
			String matcherKey = "UUIDMatcher";
			//String matcherKey = "SiDiff";
			matcher = MatcherUtil.getMatcher(matcherKey);
			if (matcher == null) {
				LogUtil.log(LogEvent.ERROR, "Matcher " + matcherKey + " not found!");
				return null;
			}
		}
		
		if (transformationEngine == null){	
			String documentType = EMFModelAccess.getCharacteristicDocumentType(original);
			transformationEngine = TransformationEngineUtil.getFirstTransformationEngine(documentType);
			if (transformationEngine == null) {
				LogUtil.log(LogEvent.ERROR, "No Transformation Engine found!");
				return null;
			}
		}
		
		String id = originalFile.getName() + "->" + modifiedFile.getName();
		LogUtil.log(LogEvent.NOTICE, id);
		
		Difference difference = AsymmetricDiffFacade.liftMeUp(original, modified, matcher);

		SysMLCorrespondence correspondence = new SysMLCorrespondence(difference);
		
		if(patchInterruptHandler == null){
			patchInterruptHandler = new BatchInterruptHandler();
		}
		
		return new TestSuite(id, difference, original, modified, correspondence, transformationEngine, patchInterruptHandler);
	}
	
	/**
	 * EAnnotations are conceptually irrelevant for SysML models. Just delete them for batch test
	 * 
	 * @param model
	 */
	private void deleteEAnnotations(Resource model){
		for (Iterator<EObject> iterator = model.getAllContents(); iterator.hasNext();) {
			EObject obj = iterator.next();
			if (obj instanceof EModelElement){
				EModelElement eModelElement = (EModelElement) obj;
				eModelElement.getEAnnotations().clear();
			}
		}
	}

}
