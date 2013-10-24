package org.silift.patching.core.correspondence.modifieddetector;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.emf.EMFAdapter;
import org.sidiff.common.emf.EMFUtil;
import org.sidiff.common.emf.annotation.AnnotateableElement;
import org.sidiff.core.annotation.AnnotationService;
import org.sidiff.core.annotation.impl.AnnotationServiceImpl;

public class ModifiedDetector {

	public final static String HASH_KEY = "HASH";

	// Origin model
	private Resource modelA;

	// Target model
	private Resource modelB;

	// Data structures used by this class
	private Map<EObject, EObject> correspondenceMap;
	private Map<EObject, Boolean> modifiedMap;

	public ModifiedDetector(Resource modelA, Resource modelB,
			Map<EObject, EObject> correspondenceMap) {
		this.modelA = modelA;
		this.modelB = modelB;
		this.correspondenceMap = correspondenceMap;
		this.modifiedMap = new HashMap<EObject, Boolean>();
	}

	/**
	 * Initialize method: 
	 * - Configure AnnotationService 
	 * - Remove HASH Annotations added previously by SiDiff 
	 * - Annotate both models 
	 * - Fill up the modified map
	 */
	public void initialize() {

		// Configure AnnotationService
		AnnotationService annotator = new AnnotationServiceImpl();
		annotator.configure("org.sidiff.ecore.core.annotations.xml");

		// Remove HASH Annotations added previously by SiDiff
		annotator.removeAnnotations(modelA, HASH_KEY);
		annotator.removeAnnotations(modelB, HASH_KEY);

		// Annotate both models
		annotator.annotate(this.modelA);
		annotator.annotate(this.modelB);

		// Fill up the modified map
		fillModifiedMap();
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
	public void fillModifiedMap() {

		// Iterate through all origin model objects
		for (EObject originObject : EMFUtil
				.getAllContentAsIterable(this.modelA)) {

			// If corresponding object existent
			if (correspondenceMap.get(originObject) != null) {
				String hashValueOrigin = null;
				String hashValueTarget = null;

				EObject targetObject = correspondenceMap.get(originObject);

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
				}

				// Mark as not modified otherwise
				else {
					modifiedMap.put(targetObject, false);
				}
			}
		}

	}

	/**
	 * 
	 * @param object to test if it has been modified
	 * @return whether the object has been modified
	 */
	public boolean isModified(EObject object) {

		if (modifiedMap.get(object) != null)
			return modifiedMap.get(object);
		else
			return false;
	}
}
