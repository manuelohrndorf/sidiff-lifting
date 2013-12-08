package org.sidiff.patching.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.difference.matcher.IMatcher;
import org.sidiff.difference.symmetric.Correspondence;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.difference.symmetric.SymmetricFactory;
import org.sidiff.patching.IPatchCorrespondence;
import org.silift.common.util.access.EMFMetaAccessEx;
import org.silift.common.util.emf.EMFResourceUtil;
import org.silift.common.util.emf.ExternalReferenceCalculator;
import org.silift.common.util.emf.ExternalReferenceContainer;
import org.silift.patching.core.correspondence.modifieddetector.ModifiedDetector;

/**
 * An implementation of {@link IPatchCorrespondence} that internally delegates
 * the computation of the initial correspondences to a SiLift {@link IMatcher}.
 * Corrections to the correspondences (e.g. in interactive mode) are managed.
 * 
 * @author kehrer
 */
public class DelegatingPatchCorrespondence implements IPatchCorrespondence {

	/**
	 * The SiLift matcher to which the initial correspondence calculation is
	 * delegated to.
	 */
	private IMatcher matcher;

	/**
	 * Reliability threshold.
	 */
	private float minReliability;

	/**
	 * The origin model.
	 */
	private Resource originModel;

	/**
	 * The target model.
	 */
	private Resource targetModel;

	/**
	 * Correspondences between originModel and targetModel.
	 */
	private SymmetricDifference matching;

	/**
	 * The PackageRegistry resources that seem to be actually used.
	 */
	private Set<Resource> packageRegistryResources;

	/**
	 * The ResourceSet resources that seem to be actually used.
	 */
	private Set<Resource> resourceSetResources;

	/**
	 * The ModifiedDetector to which we delegate when we check if an object of
	 * the origin model has been modified in the target model.
	 */
	private ModifiedDetector modDetector;

	public DelegatingPatchCorrespondence(IMatcher matcher) {
		this.matcher = matcher;
	}

	@Override
	public void set(Resource originModel, Resource targetModel) {
		this.originModel = originModel;
		this.targetModel = targetModel;

		// now we initialize the internal state...

		// do matching
		matching = matcher.createMatching(originModel, targetModel, EMFResourceUtil.COMPARE_RESOURCE, true);

		// collect referenced registry and ResourceSet resources
		ExternalReferenceCalculator refCalculator = new ExternalReferenceCalculator();
		ExternalReferenceContainer extContainer = refCalculator.calculate(originModel, EMFResourceUtil.COMPARISON_MODE);
		packageRegistryResources = extContainer.getReferencedRegistryModels();
		resourceSetResources = extContainer.getReferencedResourceSetModels();

		modDetector = new ModifiedDetector(originModel, targetModel, matching);
		modDetector.initialize();
	}

	@Override
	public Resource getOriginModel() {
		return originModel;
	}

	@Override
	public Resource getTargetModel() {
		return targetModel;
	}

	@Override
	public EObject getCorrespondence(EObject originObject) {

		if (matching.getCorrespondingObjectInB(originObject) != null) {
			return matching.getCorrespondingObjectInB(originObject);
		}

		int location = EMFResourceUtil.locate(originModel, originObject);

		if (location == EMFResourceUtil.PACKAGE_REGISTRY) {
			Correspondence c = SymmetricFactory.eINSTANCE.createCorrespondence(originObject, originObject);
			c.setReliability(1.0f);
			;
			matching.addCorrespondence(c);
			return originObject;
		}
		if (location == EMFResourceUtil.RESOURCE_SET_INTERNAL) {
			// TODO (TK)
		}

		return null;
	}

	@Override
	public Map<Resource, Collection<EObject>> getPotentialArguments(EObject originObject) {
		Map<Resource, Collection<EObject>> res = new HashMap<Resource, Collection<EObject>>();
		
		// from target model.
		List<EObject> args = new ArrayList<>();
		addPossibleArgument(args, targetModel, originObject);
		res.put(targetModel, args);
		
		// from package registry
		for (Resource r : packageRegistryResources) {
			args = new ArrayList<>();
			addPossibleArgument(args, r, originObject);
			res.put(r, args);
		}
		
		// from ResourceSet
		for (Resource r : resourceSetResources) {
			args = new ArrayList<>();
			addPossibleArgument(args, r, originObject);
			res.put(r, args);
		}

		return res;
	}

	private void addPossibleArgument(List<EObject> args, Resource resource, EObject originObject) {
		// Do not include elements defined in Ecore Meta-model itself
		// (ausgenommen EDataTypes).
		// They would only be needed if the Ecore-Metamodel itself shall be
		// patched (we probably will never do that).
		if (resource.getURI().toString().equals(EcorePackage.eNS_URI) && !(originObject instanceof EDataType)) {
			return;
		}

		// Otherwise, we only check type compatibility
		for (Iterator<EObject> it = resource.getAllContents(); it.hasNext();) {
			EObject obj = it.next();
			if (EMFMetaAccessEx.isAssignableTo(obj.eClass(), originObject.eClass())) {
				args.add(obj);
			}
		}
	}

	@Override
	public void addCorrespondence(EObject elementA, EObject elementB) {
		Correspondence c = SymmetricFactory.eINSTANCE.createCorrespondence(elementA, elementB);
		c.setReliability(1.0f);
		matching.addCorrespondence(c);
	}

	@Override
	public void removeCorrespondence(EObject originObject) {
		matching.removeCorrespondenceA(originObject);
	}

	@Override
	public void addNewTargetObject(EObject targetObject) {
		// nothing to do here
	}

	@Override
	public void removeTargetObject(EObject targetObject) {
		if (matching.getCorrespondingObjectInA(targetObject) != null){
			matching.removeCorrespondenceB(targetObject);
		}
	}

	@Override
	public void setMinReliability(float minReliability) {
		this.minReliability = minReliability;
	}

	@Override
	public float getReliability(EObject objectA, EObject objectB) {
		return matching.getReliability(objectA, objectB);
	}

	@Override
	public boolean isModified(EObject object) {
		return modDetector.isModified(object);
	}

}
