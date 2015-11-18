package org.silift.modifieddetector.annotation;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.emf.EMFAdapter;
import org.sidiff.common.emf.EMFUtil;
import org.sidiff.common.emf.annotation.AnnotateableElement;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.core.annotation.AnnotationService;
import org.sidiff.core.annotation.impl.AnnotationServiceImpl;
import org.sidiff.difference.matcher.IMatcher;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.matching.modifieddetector.IModifiedDetector;
import org.silift.common.util.emf.Scope;

/**
 * Modified Detector which shall be used as base for document type specific detectors.
 * @author dreuling
 *
 */
public abstract class AnnotationModifiedDetector implements IModifiedDetector {

	private final static String HASH_KEY = "HASH";	

	// Origin model
	private Resource modelA;

	// Target model
	private Resource modelB;

	// Data structures used by this class
	private SymmetricDifference correspondenceMap;
	private Map<EObject, Boolean> modifiedMap;

	@Override
	public boolean isModified(EObject targetObject) {

		if (modifiedMap.get(targetObject) != null)
			return modifiedMap.get(targetObject);
		else
			return false;
	}

	@Override
	public void init(Resource modelA, Resource modelB,
			IMatcher matcher,
			Scope scope) {
		
		LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");
		LogUtil.log(LogEvent.NOTICE, "--------- Initializing Annotation Modified Detector --------");
		LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");
		LogUtil.log(LogEvent.NOTICE, "Modified detector: " + getClass().getSimpleName());
		LogUtil.log(LogEvent.NOTICE, "Scope: " + scope);
		LogUtil.log(LogEvent.NOTICE, "Matcher: " + matcher.getName());
		
		
		this.modelA = modelA;
		this.modelB = modelB;
		this.correspondenceMap = matcher.createMatching(modelA, modelB, scope, false);
		this.modifiedMap = new HashMap<EObject, Boolean>();
		
		LogUtil.log(LogEvent.DEBUG, "Initializing Annotator...");
		
		AnnotationService annotator = new AnnotationServiceImpl();
		initAnnotator(annotator);
		    
		LogUtil.log(LogEvent.DEBUG, "Removing Annotations...");
		// If created in matching phase beforehand, remove Annotations
		annotator.removeAnnotations(this.modelA);
		annotator.removeAnnotations(this.modelB);
		
		LogUtil.log(LogEvent.DEBUG, "Annotating models...");

		// Annotate both models
		annotator.annotate(this.modelA);
		annotator.annotate(this.modelB);

		LogUtil.log(LogEvent.DEBUG, "Filling modified map...");
		// Fill up the modified map
		fillModifiedMap();
		
		LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");
		LogUtil.log(LogEvent.NOTICE, "------------------- Initializing completed -----------------");
		LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");
		
	}
	

	/**
	 * Convenient method for creating a map between each Object contained in the
	 * target model and a boolean describing the modified bit:
	 * 
	 * EObject <-> IsModified
	 * 
	 * All EObjects are contained in this map even if not modified.
	 * 
	 */
	private void fillModifiedMap() {

		// Iterate through all origin model objects
		for (EObject originObject : EMFUtil
				.getAllContentAsIterable(this.modelA)) {

			// If corresponding object existent
			if (correspondenceMap.getCorrespondingObjectInB(originObject) != null) {
				String hashValueOrigin = null;
				String hashValueTarget = null;

				EObject targetObject = correspondenceMap.getCorrespondingObjectInB(originObject);

				// Get annotation value of origin model
				AnnotateableElement aoOrigin = EMFAdapter.INSTANCE.adapt(
						originObject, AnnotateableElement.class);
				if (aoOrigin.hasAnnotation(HASH_KEY)) {
					hashValueOrigin = aoOrigin.getAnnotation(HASH_KEY,
							String.class);
				}

				// Get annotation value of target model
				AnnotateableElement aoTarget = EMFAdapter.INSTANCE.adapt(
						targetObject, AnnotateableElement.class);
				if (aoTarget.hasAnnotation(HASH_KEY)) {
					hashValueTarget = aoTarget.getAnnotation(HASH_KEY,
							String.class);
				}

				// Mark as modified if target and origin model objects differ in
				// their annotated value
				if (hashValueOrigin != null && hashValueTarget != null
						&& !(hashValueOrigin.equals(hashValueTarget))) {
					modifiedMap.put(targetObject, true);
					LogUtil.log(LogEvent.DEBUG, "Modified object found: " + targetObject);
					
				}

				// Mark as not modified otherwise
				else {
					modifiedMap.put(targetObject, false);
					LogUtil.log(LogEvent.DEBUG, "Unmodified object found: " + targetObject);

				}
			}
		}

	}
	
	/**
	 * Convenient method for initializing the annotation service
	 * @param annotationService
	 */
	public abstract void initAnnotator(AnnotationService annotationService);


}
