package org.sidiff.difference.symmetric.mergeimports;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.emf.access.ExternalReference;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.matching.model.Correspondence;
import org.sidiff.matching.model.MatchingModelFactory;

/**
 * Establishes missing correspondences between EObjects located at
 * {@link EObjectLocation#RESOURCE_SET_INTERNAL}. In this sense, the ResourceSet
 * is adapted (additional information is provided).
 * 
 * Note that this adapter only has to be used if
 * <ul>
 * <li>(a) the comparison mode is {@link Scope#RESOURCE},</li>
 * <li>(b) the comparison mode is {@link Scope#RESOURCE_SET} AND the matcher
 * cannot handle it.</li>
 * </ul>
 * 
 * @author kehrer
 */
public class ResourceSetAdapter {

	/**
	 * The matching to be adapted.
	 */
	private SymmetricDifference difference;

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
	 * ResourceSet objects imported into A
	 */
	private Set<EObject> importsA;
	
	/**
	 * ResourceSet objects imported into B
	 */
	private Set<EObject> importsB;

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

		this.difference = matching;
		this.resourceSetReferencesA = resourceSetReferencesA;
		this.resourceSetReferencesB = resourceSetReferencesB;
	}

	/**
	 * Add correspondences for all EObjects that are externally referenced by
	 * model A or model B. Correspondences for eContainers will also be
	 * established.
	 */
	public void init() {
		resourceSetCorrespondences = new HashSet<>();

		for (ExternalReference externalReference : resourceSetReferencesA) {
			EObject obj = externalReference.getTargetObject();
			addCorrespondenceA(obj);
			if (obj.eContainer() != null) {
				addCorrespondenceA(obj.eContainer());
			}
		}

		for (ExternalReference externalReference : resourceSetReferencesB) {
			EObject obj = externalReference.getTargetObject();
			addCorrespondenceB(obj);
			if (obj.eContainer() != null) {
				addCorrespondenceB(obj.eContainer());
			}
		}

		// Collect imports
		importsA = new HashSet<>();
		importsB = new HashSet<>();
		for (Correspondence c : resourceSetCorrespondences) {
			importsA.add(c.getMatchedA());
			importsB.add(c.getMatchedB());
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
			if (difference.getMatching().getCorrespondences().contains(c)) {
				difference.removeCorrespondence(c);
				LogUtil.log(LogEvent.DEBUG, "  " + c.getMatchedA() + " <-> " + c.getMatchedB());
			}
		}
	}

	public Set<EObject> getImportsModelA() {
		return importsA;
	}
	
	public Set<EObject> getImportsModelB() {
		return importsB;
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

		assert (objA != null);
		assert (objA.eResource() != null);

		if (difference.getCorrespondingObjectInB(objA) == null) {
			EObject objB = null;

			// (1) First (and better) option: (a) get corresponding resource and
			// (b) locate corresponding object of objA within the corresponding
			// resource
			// (by URI fragement).
			// step (a):
			List<Resource> potentialResourcesB = new ArrayList<>();
			Resource resourceB = null;
			for (Resource r : difference.getModelB().getResourceSet().getResources()) {
				if (r == difference.getModelB()) {
					continue;
				}
				if (r.getURI().equals(objA.eResource().getURI())) {
					// perfect resource match
					resourceB = r;
					break;
				}
				if (r.getURI().lastSegment().equals(objA.eResource().getURI().lastSegment())) {
					// potential resource match (only last segment, i.e. file
					// name, matches)
					potentialResourcesB.add(r);
				}
			}
			if (resourceB == null && potentialResourcesB.size() == 1) {
				// check if there is a potential resource match
				resourceB = potentialResourcesB.get(0);
			}

			// step (b)
			if (resourceB != null) {
				objB = resourceB.getEObject(objA.eResource().getURIFragment(objA));
				assert (objB != null) : "Object with URI " + objA.eResource().getURIFragment(objA) + "not found in corresponding resource "
						+ resourceB.getURI() + "of model B. Maybe you should use COMPARISON_MODE = RESOURCE_SET.";
			}

			if (objB == null) {
				// (2) Second option: Try to locate corresponding object of objA
				// within any resource of the ResourceSet of model B (by URI
				// fragement).
				List<EObject> objsB = new LinkedList<>();
				for (Resource r : difference.getModelB().getResourceSet().getResources()) {
					if (r == difference.getModelB()) {
						continue;
					}
					EObject o = r.getEObject(objA.eResource().getURIFragment(objA));
					if (o != null) {
						objsB.add(o);
					}
				}

				if (objsB.size() == 0) {
					LogUtil.log(LogEvent.DEBUG, "Object with URI " + objA.eResource().getURIFragment(objA)
						+ "not found in model B. Maybe you should use COMPARISON_MODE = RESOURCE_SET.");
				}
				if (objsB.size() > 1) {
					LogUtil.log(LogEvent.DEBUG, "Multiple occurrences with URI " + objA.eResource().getURIFragment(objA)
						+ "in ResourceSet of model B. Maybe you should use COMPARISON_MODE = RESOURCE_SET.");
				}

				if (!objsB.isEmpty()) {
					objB = objsB.get(0);
				}				
			}

			// Create new Correspondence
			if (objB != null){
				Correspondence correspondence = MatchingModelFactory.eINSTANCE.createCorrespondence();
				correspondence.setMatchedA(objA);
				correspondence.setMatchedB(objB);		
				difference.addCorrespondence(correspondence);
				resourceSetCorrespondences.add(correspondence);

				assert (difference.getCorrespondingObjectInB(objA) != null);
			}
		}
	}

	/**
	 * Adds a correspondence for objB.
	 * 
	 * @param objB
	 */
	private void addCorrespondenceB(EObject objB) {

		assert (objB != null);
		assert (objB.eResource() != null);

		if (difference.getCorrespondingObjectInA(objB) == null) {
			EObject objA = null;
			
			// (1) First (and better) option: (a) get corresponding resource and (b) locate corresponding
			// object of objB within the corresponding resource (by URI
			// fragement).

			// step (a):
			List<Resource> potentialResourcesA = new ArrayList<>();
			Resource resourceA = null;
			for (Resource r : difference.getModelA().getResourceSet().getResources()) {
				if (r == difference.getModelA()) {
					continue;
				}
				if (r.getURI().equals(objB.eResource().getURI())) {
					// perfect resource match
					resourceA = r;
					break;
				}
				if (r.getURI().lastSegment().equals(objB.eResource().getURI().lastSegment())) {
					// potential resource match (only last segment, i.e. file
					// name, matches)
					potentialResourcesA.add(r);
				}
			}
			if (resourceA == null && potentialResourcesA.size() == 1) {
				// check if there is a potential resource match
				resourceA = potentialResourcesA.get(0);
			}

			
			// step (b)
			if (resourceA != null) {
				objA = resourceA.getEObject(objB.eResource().getURIFragment(objB));
				assert (objA != null) : "Object with URI " + objB.eResource().getURIFragment(objB) + "not found in corresponding resource "
						+ resourceA.getURI() + "of model A. Maybe you should use COMPARISON_MODE = RESOURCE_SET.";
			}
			
			// (2) As a less exact heuristics, we also apply the second
			// option from addCorrespondenceA here

			if (objA == null) {
				// (2) Second option: Try to locate corresponding object of objA
				// within any resource of the ResourceSet of model B (by URI
				// fragement).
				List<EObject> objsA = new LinkedList<>();
				for (Resource r : difference.getModelA().getResourceSet().getResources()) {
					if (r == difference.getModelA()) {
						continue;
					}
					EObject o = r.getEObject(objB.eResource().getURIFragment(objB));
					if (o != null) {
						objsA.add(o);
					}
				}

				if (objsA.size() == 0) {
					LogUtil.log(LogEvent.DEBUG, "Object with URI " + objB.eResource().getURIFragment(objB)
						+ "not found in model A. Maybe you should use COMPARISON_MODE = RESOURCE_SET.");
				}
				if (objsA.size() > 1) {
					LogUtil.log(LogEvent.DEBUG, "Multiple occurrences with URI " + objB.eResource().getURIFragment(objB)
						+ "in ResourceSet of model A. Maybe you should use COMPARISON_MODE = RESOURCE_SET.");
				}
				if (!objsA.isEmpty()) {
					objA = objsA.get(0);
				}				
			}
			
			// Create new Correspondence
			if (objA != null){
				Correspondence correspondence = MatchingModelFactory.eINSTANCE.createCorrespondence();
				correspondence.setMatchedA(objA);
				correspondence.setMatchedB(objB);		
				difference.addCorrespondence(correspondence);
				resourceSetCorrespondences.add(correspondence);

				assert (difference.getCorrespondingObjectInA(objB) != null);
			}
		}
	}
}
