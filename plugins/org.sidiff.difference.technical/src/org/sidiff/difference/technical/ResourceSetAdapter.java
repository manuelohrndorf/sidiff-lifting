package org.sidiff.difference.technical;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.difference.symmetric.Correspondence;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.difference.symmetric.SymmetricFactory;
import org.silift.common.util.emf.EObjectLocation;
import org.silift.common.util.emf.ExternalReference;
import org.silift.common.util.emf.Scope;

/**
 * Establishes missing correspondences between EObjects located at
 * {@link EObjectLocation#RESOURCE_SET_INTERNAL}. In this sense, the ResourceSet
 * is adapted (additional information is provided).
 * 
 * Note that this adapter must only be used if the comparison mode is
 * {@link Scope#RESOURCE} or comparison mode is {@link Scope#RESOURCE_SET} and
 * the matcher cannot handle it.
 * 
 * @author kehrer
 */
public class ResourceSetAdapter {

	/**
	 * The matching to be adapted.
	 */
	private SymmetricDifference matching;

	/**
	 * External references in A to RESOURCE_SET model objects.
	 */
	private List<ExternalReference> resourceSetReferencesA;

	/**
	 * External references in B to RESOURCE_SET model objects.
	 */
	private List<ExternalReference> resourceSetReferencesB;

	/**
	 * The additionally created RESOURCE_SET correspondences
	 */
	private Set<Correspondence> resourceSetCorrespondences;

	/**
	 * ResourceSet objects imported into A and B
	 */
	private Set<EObject> imports;

	/**
	 * Constructor.
	 * 
	 * Note: <br/>
	 * - Creation of a ResourceSetAdapter must be followed by a call to init(). <br/>
	 * - Before a ResourceSetAdapter is destroyed, call cleanup().
	 * 
	 * @param matching
	 *            The matching to be adapted
	 * @param resourceSetReferencesA
	 *            External references in A to RESOURCE_SET model objects
	 * @param resourceSetReferencesB
	 *            External references in B to RESOURCE_SET model objects
	 */
	public ResourceSetAdapter(SymmetricDifference matching, List<ExternalReference> resourceSetReferencesA,
			List<ExternalReference> resourceSetReferencesB) {
		super();

		this.matching = matching;
		this.resourceSetReferencesA = resourceSetReferencesA;
		this.resourceSetReferencesB = resourceSetReferencesB;
	}

	/**
	 * Add correspondences for all EObjects that are externally referenced by
	 * model A or model B. Correspondences for eContainers will also be
	 * established.
	 */
	public void init() {
		resourceSetCorrespondences = new HashSet<Correspondence>();
		imports = new HashSet<EObject>();

		// A
		for (ExternalReference externalReference : resourceSetReferencesA) {
			EObject obj = (EObject) externalReference.getTargetObject();
			addCorrespondenceA(obj);
			if (obj.eContainer() != null) {
				addCorrespondenceA(obj.eContainer());
			}
		}
		// B
		for (ExternalReference externalReference : resourceSetReferencesB) {
			EObject obj = (EObject) externalReference.getTargetObject();
			addCorrespondenceB(obj);
			if (obj.eContainer() != null) {
				addCorrespondenceB(obj.eContainer());
			}
		}

		// Collect imports
		for (Correspondence c : resourceSetCorrespondences) {
			imports.add(c.getObjA());
			imports.add(c.getObjB());
		}
	}

	/**
	 * Removes Correspondences which have been created between objects in
	 * location RESOURCE_SET. Only necessary when mode is
	 * "compare resource only".
	 */
	public void cleanup() {
		LogUtil.log(LogEvent.DEBUG, "\nRemove ResourceSet Correspondences (A <-> B):");
		for (Correspondence c : resourceSetCorrespondences) {
			if (matching.getCorrespondences().contains(c)) {
				matching.removeCorrespondence(c);
				LogUtil.log(LogEvent.DEBUG, "  " + c.getObjA() + " <-> " + c.getObjB());
			}
		}
	}

	public Set<EObject> getImports() {
		return imports;
	}

	public Set<Correspondence> getCorrespondences() {
		return resourceSetCorrespondences;
	}

	public List<ExternalReference> getResourceSetReferencesA() {
		return resourceSetReferencesA;
	}

	public List<ExternalReference> getResourceSetReferencesB() {
		return resourceSetReferencesB;
	}

	/**
	 * Adds a correspondence for objA.
	 * 
	 * @param objA
	 */
	private void addCorrespondenceA(EObject objA) {

		assert (objA.eResource() != null);

		if (matching.getCorrespondingObjectInB(objA) == null) {
			EObject objB = null;

			// (1) First (and better) option: get corresponding resource and
			// locate correspondence of objA within the corresponding resource
			// (by URI fragement).
			Resource resourceB = null;
			for (Resource r : matching.getModelB().getResourceSet().getResources()) {
				if (r == matching.getModelB()) {
					continue;
				}
				if (r.getURI().equals(objA.eResource().getURI())) {
					resourceB = r;
				}
			}

			if (resourceB != null) {
				objB = resourceB.getEObject(objA.eResource().getURIFragment(objA));
				assert (objB != null) : "Object with URI " + objA.eResource().getURIFragment(objA)
						+ "not found in corresponding resource " + resourceB.getURI()
						+ "of model B. Maybe you should use COMPARISON_MODE = RESOURCE_SET.";
			}

			if (objB == null) {
				// (2) Second option: Try to locate correspondence of objA
				// within any resource of the ResourceSet of model B (by URI
				// fragement).
				List<EObject> objsB = new LinkedList<EObject>();
				for (Resource r : matching.getModelB().getResourceSet().getResources()) {
					if (r == matching.getModelB()) {
						continue;
					}
					EObject o = r.getEObject(objA.eResource().getURIFragment(objA));
					if (o != null) {
						objsB.add(o);
					}
				}

				assert (objsB.size() > 0) : "Object with URI " + objA.eResource().getURIFragment(objA)
						+ "not found in model B. Maybe you should use COMPARISON_MODE = RESOURCE_SET.";
				assert (objsB.size() == 1) : "Multiple occurrences with URI " + objA.eResource().getURIFragment(objA)
						+ "in ResourceSet of model B. Maybe you should use COMPARISON_MODE = RESOURCE_SET.";

				objB = objsB.get(0);
			}

			// Create new Correspondence
			Correspondence correspondence = SymmetricFactory.eINSTANCE.createCorrespondence(objA, objB);
			matching.addCorrespondence(correspondence);
			resourceSetCorrespondences.add(correspondence);

			assert (matching.getCorrespondingObjectInB(objA) != null);
		}
	}

	/**
	 * Adds a correspondence for objB.
	 * 
	 * @param objB
	 */
	private void addCorrespondenceB(EObject objB) {

		assert (objB.eResource() != null);

		if (matching.getCorrespondingObjectInA(objB) == null) {
			Resource resourceA = null;
			for (Resource r : matching.getModelA().getResourceSet().getResources()) {
				if (r == matching.getModelA()) {
					continue;
				}
				if (r.getURI().equals(objB.eResource().getURI())) {
					resourceA = r;
				}
			}

			assert (resourceA != null) : "Resource with URI '" + objB.eResource().getURI()
					+ "' not found in ResourceSet A. Maybe you should use COMPARISON_MODE = RESOURCE_SET.";
			EObject objA = resourceA.getEObject(objB.eResource().getURIFragment(objB));
			assert (objA != null);

			// Create new Correspondence
			Correspondence correspondence = SymmetricFactory.eINSTANCE.createCorrespondence(objA, objB);
			matching.addCorrespondence(correspondence);
			resourceSetCorrespondences.add(correspondence);

			assert (matching.getCorrespondingObjectInA(objB) != null);
		}
	}

}
