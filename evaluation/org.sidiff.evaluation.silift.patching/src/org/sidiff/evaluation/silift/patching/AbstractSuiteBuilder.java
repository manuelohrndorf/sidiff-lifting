package org.sidiff.evaluation.silift.patching;

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
import org.sidiff.matcher.IMatcher;
import org.sidiff.patching.batch.handler.BatchInterruptHandler;
import org.sidiff.patching.interrupt.IPatchInterruptHandler;
import org.sidiff.patching.transformation.ITransformationEngine;
import org.sidiff.patching.transformation.TransformationEngineUtil;

public abstract class AbstractSuiteBuilder {
	private File modelFolder;
	private IMatcher matcher;

	private ITransformationEngine transformationEngine;
	private IPatchInterruptHandler patchInterruptHandler;

	public AbstractSuiteBuilder(File modelFolder) {
		this.modelFolder = modelFolder;
	}

	public List<TestSuite> getTestSuites() {
		List<TestSuite> testsuits = new ArrayList<TestSuite>();
		FilenameFilter filter = new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				String fileExt = getFileExtension();
				return name.toLowerCase().endsWith(fileExt) && !name.contains("patch");
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
			matcher = getMatcher(original, modified);
		}
		
		if (transformationEngine == null){	
			String documentType = EMFModelAccess.getDocumentType(original);
			transformationEngine = TransformationEngineUtil.getFirstTransformationEngine(documentType);
			if (transformationEngine == null) {
				LogUtil.log(LogEvent.ERROR, "No Transformation Engine found!");
				return null;
			}
		}
		
		String id = originalFile.getName() + "->" + modifiedFile.getName();
		LogUtil.log(LogEvent.NOTICE, id);
		
		Difference difference = AsymmetricDiffFacade.liftMeUp(original, modified, matcher);

		AbstractBatchArgumentManager correspondence = getArgumentManager(difference);
		
		if(patchInterruptHandler == null){
			patchInterruptHandler = new BatchInterruptHandler();
		}
		
		return new TestSuite(id, difference, original, modified, correspondence, transformationEngine, patchInterruptHandler);
	}
	
	protected abstract String getFileExtension();
	
	protected abstract IMatcher getMatcher(Resource original, Resource modified);
	
	protected abstract AbstractBatchArgumentManager getArgumentManager(Difference patch);

}
