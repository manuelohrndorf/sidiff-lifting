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
import org.sidiff.difference.matcher.IMatcher;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.silift.common.util.emf.Scope;

public class ModifiedDetector {

	public final static String HASH_KEY = "HASH";

	// Origin model
	private Resource modelA;

	// Target model
	private Resource modelB;

	// Data structures used by this class
	private SymmetricDifference correspondenceMap;
	private Map<EObject, Boolean> modifiedMap;

	public ModifiedDetector(Resource modelA, Resource modelB, IMatcher matcher, Scope scope) {
		this.modelA = modelA;
		this.modelB = modelB;
		this.correspondenceMap = matcher.createMatching(modelA, modelB, scope, false);
		this.modifiedMap = new HashMap<EObject, Boolean>();
	}

	/**
	 * Initialize method: 
	 * Needs matcher for initialization of annotation service
	 * - Configure AnnotationService 
	 * - Remove Annotations added previously by 
	 * - Annotate both models 
	 * - Fill up the modified map
	 */
	public void initialize() {

		// Configure AnnotationService
		AnnotationService annotator = new AnnotationServiceImpl();
		annotator.configure("platform:/plugin/org.silift.patching.core.correspondence.modifieddetector/config/"
				+ "org.sidiff.ecore.core.annotations.xml");		 
		    
		// If created in matching phase beforehand, remove Annotations
		annotator.removeAnnotations(this.modelA);
		annotator.removeAnnotations(this.modelB);
		
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
	 * @param targetObject to test if it has been modified
	 * @return whether the object has been modified
	 */
	public boolean isModified(EObject targetObject) {

		if (modifiedMap.get(targetObject) != null)
			return modifiedMap.get(targetObject);
		else
			return false;
	}
}
