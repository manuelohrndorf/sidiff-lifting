package org.sidiff.patching.correspondence.sidiff.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.common.util.StringUtil;
import org.sidiff.difference.util.emf.storage.EMFResourceUtil;
import org.sidiff.patching.IPatchCorrespondence;
import org.silift.patching.core.correspondence.modifieddetector.*;

public class SidiffPatchCorrespondenceImpl implements IPatchCorrespondence {
	private Resource modelA;
	private Resource modelB;
	private float minReliability;

	private Map<EObject, EObject> correspondenceMap;
	private Collection<EObject> unmatchedObjects;

	private SidiffCorrespondence sidiff;
	private ModifiedDetector modDetector;
	
	public SidiffPatchCorrespondenceImpl() {
		this.minReliability = 0;
	}


	@Override
	public void set(Resource modelA, Resource modelB) {
		this.modelA = modelA;
		this.modelB = modelB;
		sidiff = new SidiffCorrespondence(modelA, modelB);
		LogUtil.log(LogEvent.NOTICE, "Initializing Sidiff");
		sidiff.initialize();
		fillCorrespondenceMap();
		modDetector = new ModifiedDetector(modelA, modelB, correspondenceMap);
		modDetector.initialize();
	}

	private void fillCorrespondenceMap() {
		this.correspondenceMap = new HashMap<EObject, EObject>();
		this.unmatchedObjects = new ArrayList<EObject>(sidiff.getUnmatchedObjects());

		for (TreeIterator<EObject> iterator = this.modelA.getAllContents(); iterator.hasNext();) {
			EObject eObject = (EObject) iterator.next();
			EObject correspondence = sidiff.getCorrespondence(eObject);
			if (correspondence != null) {
				float reliability = sidiff.getReliabilityOfMatch(eObject, correspondence);
				if (reliability > minReliability) {
					correspondenceMap.put(eObject, correspondence);
				} else {
					correspondenceMap.put(eObject, null);
					unmatchedObjects.add(correspondence);
				}
			}
		}
	}

	public void setMinReliability(float minReliability) {
		this.minReliability = minReliability;
	}

	@Override
	public Resource getModelA() {
		return modelA;
	}

	@Override
	public Resource getModelB() {
		return modelB;
	}

	@Override
	public EObject getCorrespondence(EObject eObject) {
		int location = EMFResourceUtil.locate(modelA, eObject);
		if (location == EMFResourceUtil.PACKAGE_REGISTRY) {	
			// Object is from global PackageRegistry thus being a singleton
			return eObject;
		}
		
		return correspondenceMap.get(eObject);
	}

	@Override
	public void setCorrespondence(EObject elementA, EObject elementB) {
		assert elementA != null && elementB != null;
		EObject oldCorrespondence = correspondenceMap.get(elementA);
		if (oldCorrespondence != null) {
			unmatchedObjects.add(oldCorrespondence);
		}
		unmatchedObjects.remove(elementB);
		correspondenceMap.put(elementA, elementB);
	}

	@Override
	public void removeCorrespondence(EObject eObject) {
		EObject oldCorrespondence = correspondenceMap.get(eObject);
		if (oldCorrespondence != null) {
			unmatchedObjects.add(oldCorrespondence);
		}
		correspondenceMap.put(eObject, null);
	}

	@Override
	public void addNewEObject(EObject newObject) {
		assert newObject != null;
		LogUtil.log(LogEvent.NOTICE, "Adding new eObject: " + StringUtil.resolve(newObject));
		unmatchedObjects.add(newObject);
	}

	@Override
	public void removeEObject(EObject objectToRemove) {
		assert objectToRemove != null;
		LogUtil.log(LogEvent.NOTICE, "Removing eObject: " + EcoreUtil.getURI(objectToRemove).fragment());
		for (EObject key : correspondenceMap.keySet()) {
			EObject eObject = correspondenceMap.get(key);
			if (objectToRemove.equals(eObject)) {
				correspondenceMap.put(key, null);
			}
		}
		unmatchedObjects.remove(objectToRemove);
	}

	@Override
	public Collection<EObject> getAllCorrespondences(EObject eObject) {
		Collection<EObject> result = new HashSet<EObject>();
		EObject correspondence = correspondenceMap.get(eObject);
		if (correspondence != null) {
			result.add(correspondence);
		}
		for (EObject uEObject : unmatchedObjects) {
			if (eObject.eClass().getName().equals(uEObject.eClass().getName())) {
				result.add(uEObject);
			}
		}
		return result;
	}

	@Override
	public float getReliability(EObject objectA, EObject objectB) {
		int location = EMFResourceUtil.locate(modelA, objectA);
		if (location == EMFResourceUtil.PACKAGE_REGISTRY) {	
			// Object is from global PackageRegistry thus being a singleton
			return 1.0f;
		}
		
		float reliability = sidiff.getReliabilityOfMatch(objectA, objectB);
		return reliability;
	}


	@Override
	public boolean isModified(EObject object) {
		return modDetector.isModified(object);
	}
}
