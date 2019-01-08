package org.sidiff.conflicts.annotation;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.annotation.IAnnotation;
import org.sidiff.common.collections.CollectionUtil;
import org.sidiff.common.emf.EMFAdapter;
import org.sidiff.common.emf.access.Scope;
import org.sidiff.common.emf.annotation.AnnotateableElement;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.conflicts.modifieddetector.AbstractModifiedDetector;
import org.sidiff.correspondences.ICorrespondences;
import org.sidiff.matcher.IMatcher;

/**
 * Modified Detector which shall be used as base for document type specific detectors.
 * @author dreuling
 */
public abstract class AnnotationModifiedDetector extends AbstractModifiedDetector {

	private final static String HASH_KEY = "HASH";	

	// Origin model
	private Resource modelA;

	// Target model
	private Resource modelB;

	// Data structures used by this class
	private ICorrespondences correspondences;
	private Map<EObject, Boolean> modifiedMap;

	@Override
	public boolean isModified(EObject targetObject) {
		return modifiedMap.getOrDefault(targetObject, false);
	}

	@Override
	public void init(Resource modelA, Resource modelB,
			IMatcher matcher,
			Scope scope) throws IOException {

		LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");
		LogUtil.log(LogEvent.NOTICE, "--------- Initializing Annotation Modified Detector --------");
		LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");
		LogUtil.log(LogEvent.NOTICE, "Modified detector: " + getClass().getSimpleName());
		LogUtil.log(LogEvent.NOTICE, "Scope: " + scope);
		LogUtil.log(LogEvent.NOTICE, "Matcher: " + matcher.getName());


		this.modelA = modelA;
		this.modelB = modelB;

		// Create matching
		matcher.reset();
		matcher.startMatching(Arrays.asList(modelA,modelB), scope);

		this.correspondences = matcher.getCorrespondencesService();
		this.modifiedMap = new HashMap<EObject, Boolean>();

		LogUtil.log(LogEvent.DEBUG, "Initializing Annotator...");

		IAnnotation annotator = IAnnotation.MANAGER.getDefaultExtension().get();
		initAnnotator(annotator, modelA);

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
		
		matcher.reset();

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
		for (EObject originObject : CollectionUtil.asIterable(modelA.getAllContents())) {

			// If corresponding object existent
			if (correspondences.hasCorrespondences(originObject)) {
				String hashValueOrigin = null;
				String hashValueTarget = null;

				EObject targetObject = correspondences.getCorrespondences(originObject).iterator().next();

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
	 * @throws FileNotFoundException 
	 */
	public abstract void initAnnotator(IAnnotation annotationService, Resource model) throws IOException;


}
