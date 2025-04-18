package org.sidiff.difference.symmetric.mergeimports;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.ETypedElement;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.sidiff.common.emf.EMFUtil;
import org.sidiff.common.emf.access.ExternalManyReference;
import org.sidiff.common.emf.access.ExternalReference;
import org.sidiff.common.emf.access.ExternalReferenceContainer;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.difference.symmetric.AddReference;
import org.sidiff.difference.symmetric.Change;
import org.sidiff.difference.symmetric.EObjectSet;
import org.sidiff.difference.symmetric.EditRuleMatch;
import org.sidiff.difference.symmetric.SemanticChangeSet;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.matching.model.Correspondence;
import org.sidiff.matching.model.util.MatchingModelUtil;

public class PackageRegistryAdapter {
	
	/**
	 * Copies the import packages and contains the copy traces.
	 */
	private Copier copier;

	/**
	 * The symmetric model difference
	 */
	private SymmetricDifference difference;

	/**
	 * External references in A to REGISTRY model objects.
	 */
	private ExternalReferenceContainer registryReferencesA;

	/**
	 * External references in B to REGISTRY model objects.
	 */
	private ExternalReferenceContainer registryReferencesB;
	
	private Map<ETypedElement, EGenericType> externalGenerics; 

	/**
	 * Whether registry objects shall be copied and relinked or not.
	 */
	protected boolean relink;

	/**
	 * Indexed access to <b>all</b> (not only the necessary) REGISTRY
	 * Correspondences that are created during copy of registry models (i.e.
	 * during the relinking phase)
	 */
	protected Map<EObject, Correspondence> original2Correspondence;
	protected Map<EObject, Correspondence> copy2Correspondence;

	/**
	 * The really necessary REGISTRY correspondences
	 */
	private Set<Correspondence> registryCorrespondences;

	/**
	 * REGISTRY objects imported into A
	 */
	private Set<EObject> importsA = Collections.emptySet();
	
	/**
	 * REGISTRY objects imported into B
	 */
	private Set<EObject> importsB = Collections.emptySet();

	/**
	 * Creates a package registry adapter which also takes care of the symmetric
	 * difference That is, references from low-level changes (AddObject,
	 * DeleteObject, ...) to external copies are relinked after unmerge if
	 * necessary.
	 * 
	 * Note: <br/>
	 * - Creation of a ResourceSetAdapter must be followed by a call to init(). <br/>
	 * - Before a ResourceSetAdapter is destroyed, call cleanup().
	 * 
	 * @param difference
	 * @param registryReferencesA
	 * @param registryReferencesB
	 * @param relink
	 */
	public PackageRegistryAdapter(SymmetricDifference difference, ExternalReferenceContainer registryReferencesA,
			ExternalReferenceContainer registryReferencesB, boolean relink) {

		this.difference = difference;
		this.registryReferencesA = registryReferencesA;
		this.registryReferencesB = registryReferencesB;
		this.relink = relink;
		this.externalGenerics = new HashMap<>();
	}

	public void init() {
		if (relink) {
			original2Correspondence = new HashMap<>();
			copy2Correspondence = new HashMap<>();
		} else {
			original2Correspondence = null;
			copy2Correspondence = null;
		}
		registryCorrespondences = new HashSet<>();
		importsA = new HashSet<>();
		importsB = new HashSet<>();

		// Find models which are imported from PackageRegistry
		Set<Resource> registryModels = new HashSet<>();
		registryModels.addAll(registryReferencesA.getReferencedRegistryModels());
		registryModels.addAll(registryReferencesB.getReferencedRegistryModels());

		if (relink) {
			// Copy the imported models
			copyAll(registryModels);

			// Create the correspondences between originals and copies.
			createCorrespondences();

			// Relink model B
			relink();
		}

		// Add the necessary correspondences
		addNecessaryCorrespondences();
	}

	public void cleanup() {
		if (relink) {
			// Link external References in B back to their originals
			undoRelink();
		}

		// Remove the correspondences between original and copies
		undoRegistryCorrespondences();
	}

	public Set<EObject> getImportsModelA() {
		return importsA;
	}
	
	public Set<EObject> getImportsModelB() {
		return importsB;
	}

	public Set<Correspondence> getCorrespondences() {
		return registryCorrespondences;
	}

	public ExternalReferenceContainer getRegistryReferencesA() {
		return registryReferencesA;
	}

	public ExternalReferenceContainer getRegistryReferencesB() {
		return registryReferencesB;
	}

	/**
	 * Creates a deep copy of each Reference in the given set of models.
	 * 
	 * @param importedModels
	 *            the collection of Resources to copy.
	 * @see Copier
	 */
	private void copyAll(Set<Resource> importedModels) {
		assertRelinkEnabled();

		Collection<EObject> eObjects = new ArrayList<>();
		for (Resource resource : importedModels) {
			eObjects.addAll(resource.getContents());
		}

		copier = new Copier();
		copier.copyAll(eObjects);
		copier.copyReferences();
	}

	/**
	 * Creates correspondences between the imported objects and the
	 * corresponding copies.
	 */
	private void createCorrespondences() {
		assertRelinkEnabled();

		copier.forEach((mergeObjOriginal, mergeObjCopy) -> {
			// Create new Correspondence
			Correspondence correspondence = MatchingModelUtil.createCorrespondence(
					Objects.requireNonNull(mergeObjOriginal, "mergeObjOriginal is null"),
					Objects.requireNonNull(mergeObjCopy, "mergeObjCopy is null"));

			// Save new Correspondence
			original2Correspondence.put(mergeObjOriginal, correspondence);
			copy2Correspondence.put(mergeObjCopy, correspondence);
		});
	}

	/**
	 * Links the original external references to their copies.
	 * 
	 * @param externalReferences
	 *            the external references to relink.
	 */
	protected void relink() {
		assertRelinkEnabled();

		// Relink all external references
		for (ExternalReference extRef : registryReferencesB.getRegistryReferences()) {
			if (extRef.getEReference().isMany()) {
				ExternalManyReference extManyRef = (ExternalManyReference)extRef;
				
				// Get copy
				List<EObject> list = EMFUtil.getReferenceTargets(extRef.getSourceObject(), extRef.getEReference());
				EObject original = extRef.getTargetObject();
				if(original != list.get(extManyRef.getPosition())) {
					throw new IllegalStateException("Mismatch between original " + original
							+ " and existing " + list.get(extManyRef.getPosition()));
				}
				EObject copy = getCorrespondingCopy(original);
				if(copy == null) {
					throw new IllegalStateException("No copy available to relink " + original);
				}

				// Relink original to corresponding copy
				list.set(extManyRef.getPosition(), copy);

			} else {
				// Get copy
				EObject original = (EObject) extRef.getSourceObject().eGet(extRef.getEReference());
				if (copier.containsValue(original)) {
					// we have a generic type and relink has been done
					// implicitly
					assert (extRef.getSourceObject() instanceof EGenericType);
					continue;
				}

				EObject copy = getCorrespondingCopy(original);
				assert (copy != null);

				if(extRef.getSourceObject() instanceof ETypedElement) {
					EStructuralFeature feature = extRef.getSourceObject().eClass().getEStructuralFeature("name");
					if(feature != null && extRef.getEReference().eGet(feature).equals("eType")){
						ETypedElement eTypedElement = (ETypedElement) extRef.getSourceObject();
						externalGenerics.put(eTypedElement, eTypedElement.getEGenericType());
					}
				}
				// Relink original to corresponding copy
				extRef.getSourceObject().eSet(extRef.getEReference(), copy);
				for(Change change : difference.getChanges()) {
					if(change instanceof AddReference) {
						AddReference addReference = (AddReference)change;
						if(addReference.getSrc() == extRef.getSourceObject() && addReference.getTgt() == original){
							addReference.setTgt(copy);
						}
					}
				}
			}
		}
//		
//		// EditRuleMatches (occurrences B)
//		for (SemanticChangeSet cs : difference.getChangeSets()) {
//			EditRuleMatch erMatch = cs.getEditRuleMatch();
//
//			if (erMatch != null) {
//				for (String nodeURI : erMatch.getNodeOccurrencesB().keySet()) {
//
//					// (1) Calculate replacements in occurrence set (to avoid
//					// concurrent modification exception)
//					EObjectSet occurrences = erMatch.getNodeOccurrencesB().get(
//							nodeURI);
//					Map<EObject, EObject> replacements = new HashMap<EObject, EObject>();
//					for (EObject occurrence : occurrences.getElements()) {
//						Correspondence c = original2Correspondence.get(occurrence);
//						if (c != null) {
//							replacements.put(occurrence, c.getMatchedB());
//						}
//					}
//
//					// (2) Do perform replacement
//					for (EObject oldElement : replacements.keySet()) {
//						EObject newElement = replacements.get(oldElement);
//						occurrences.replaceElement(oldElement, newElement);
//					}
//				}
//			}
//		}
	}

	/**
	 * Add correspondences for all registry EObjects that are externally
	 * referenced by model A or model B. Correspondences for eContainers will
	 * also be established.
	 */
	private void addNecessaryCorrespondences() {
		// Get registry references from model A
		for (ExternalReference externalReference : registryReferencesA.getRegistryReferences()) {
			EObject obj = externalReference.getTargetObject();
			addCorrespondence(obj);
			if (obj.eContainer() != null) {
				addCorrespondence(obj.eContainer());
			}
		}

		// Get registry merges from model B
		for (ExternalReference externalReference : registryReferencesB.getRegistryReferences()) {
			EObject obj = externalReference.getTargetObject();
			addCorrespondence(obj);
			if (obj.eContainer() != null) {
				addCorrespondence(obj.eContainer());
			}
		}
	}

	/**
	 * Adds a correspondence
	 * <ul>
	 * <li>orig <-> copy (in case of relinking), or</li>
	 * <li>orig <-> orig (else).</li>
	 * </ul>
	 * 
	 * 
	 * @param orig
	 */
	private void addCorrespondence(EObject orig) {
		if (relink) {
			// orig <-> copy
			EObject copy = getCorrespondingCopy(orig);
			Correspondence correspondence = original2Correspondence.get(orig);

			assert (correspondence.getMatchedA() == orig && correspondence.getMatchedB() == copy);

			if (!difference.getMatching().getCorrespondences().contains(correspondence)) {
				difference.addCorrespondence(correspondence);
				registryCorrespondences.add(correspondence);
				importsA.add(correspondence.getMatchedA());
				importsB.add(correspondence.getMatchedB());
			}
		} else {
			// orig <-> orig
			if (!importsA.contains(orig) || !importsB.contains(orig)) {
				// Create new Correspondence
				Correspondence correspondence = MatchingModelUtil.createCorrespondence(orig, orig);
				difference.addCorrespondence(correspondence);
				registryCorrespondences.add(correspondence);
				importsA.add(orig);
				importsB.add(orig);
			}
		}
	}

	/**
	 * Links the relinked external references back to their originals.
	 * 
	 */
	protected void undoRelink() {
		assertRelinkEnabled();

		LogUtil.log(LogEvent.DEBUG, "Relink (copy -> original)");

		// Relink all external references
		for (ExternalReference extRef : registryReferencesB.getRegistryReferences()) {
			LogUtil.log(LogEvent.DEBUG, extRef.getSourceObject() + "::" + extRef.getEReference());

			if (extRef.getEReference().isMany()) {
				// Get original
				List<EObject> list = EMFUtil.getReferenceTargets(extRef.getSourceObject(), extRef.getEReference());
				EObject copy = list.get(((ExternalManyReference) extRef).getPosition());
				EObject original = getCorrespondingOriginal(copy);
				if(original == null) {
					throw new IllegalStateException("No original available to relink " + copy);
				}

				// Relink copy to corresponding original
				list.set(((ExternalManyReference) extRef).getPosition(), original);
			} else {
				// Get Original
				EObject copy = (EObject) extRef.getSourceObject().eGet(extRef.getEReference());
				if (copier.containsKey(copy)) {
					// we have a generic type and relink has been done
					// implicitly
					assert (extRef.getSourceObject() instanceof EGenericType);
					continue;
				}

				EObject original = getCorrespondingOriginal(copy);
				assert (original != null);

				// Relink copy to corresponding original
				extRef.getSourceObject().eSet(extRef.getEReference(), original);
				
				if(extRef.getSourceObject() instanceof ETypedElement) {
					EStructuralFeature feature = extRef.getSourceObject().eClass().getEStructuralFeature("name");
					if(feature != null && extRef.getEReference().eGet(feature).equals("eType")) {
						ETypedElement eTypedElement = (ETypedElement) extRef.getSourceObject();
						eTypedElement.setEGenericType(null);
						eTypedElement.setEGenericType(externalGenerics.get(eTypedElement));
					}
				}
				
				LogUtil.log(LogEvent.DEBUG, "  " + copy + " -> " + original);
			}
		}

		// AddReference to external Object
		for (Change change : difference.getChanges()) {
			if (change instanceof AddReference) {
				AddReference addReference = (AddReference) change;
				Correspondence c_src = copy2Correspondence.get(addReference.getSrc());
				Correspondence c_tgt = copy2Correspondence.get(addReference.getTgt());
				if (c_src != null || c_tgt != null) {
					LogUtil.log(LogEvent.DEBUG, addReference);
				}

				if (c_src != null) {
					addReference.setSrc(c_src.getMatchedA());
					LogUtil.log(LogEvent.DEBUG, "  src: " + c_src.getMatchedB() + " -> " + c_src.getMatchedA());
				}
				if (c_tgt != null) {
					addReference.setTgt(c_tgt.getMatchedA());
					LogUtil.log(LogEvent.DEBUG, "  tgt: " + c_tgt.getMatchedB() + " -> " + c_tgt.getMatchedA());
				}
			}
		}

		// EditRuleMatches (occurrences B)
		for (SemanticChangeSet cs : difference.getChangeSets()) {
			EditRuleMatch erMatch = cs.getEditRuleMatch();
			if (erMatch != null) {
				for (String nodeURI : erMatch.getNodeOccurrencesB().keySet()) {

					// (1) Calculate replacements in occurrence set (to avoid
					// concurrent modification exception)
					EObjectSet occurrences = erMatch.getNodeOccurrencesB().get(nodeURI);
					Map<EObject, EObject> replacements = new HashMap<>();
					for (EObject occurrence : occurrences.getElements()) {
						Correspondence c = copy2Correspondence.get(occurrence);
						if (c != null) {
							replacements.put(occurrence, c.getMatchedA());
						}
					}

					// (2) Do perform replacement
					replacements.forEach(occurrences::replaceElement);
				}
			}
		}
	}

	/**
	 * Removes correspondences between the imports and the corresponding copies.
	 */
	private void undoRegistryCorrespondences() {
		if (relink) {
			assert (original2Correspondence.keySet().size() == copy2Correspondence.keySet().size());
		}

		LogUtil.log(LogEvent.DEBUG, "\nRemove Registry Correspondences (original <-> copy):");
		for (Correspondence c : registryCorrespondences) {
			LogUtil.log(LogEvent.DEBUG, "  " + c.getMatchedA() + " <-> " + c.getMatchedB());
			difference.removeCorrespondence(c);
		}
	}

	/**
	 * Get the copy corresponding with the given original.
	 * 
	 * @param original
	 * @return
	 */
	private EObject getCorrespondingCopy(EObject original) {
		assertRelinkEnabled();
		EObject copy = copier.get(original);
		Correspondence correspondence = original2Correspondence.get(original);
		if(correspondence == null) {
			throw new IllegalArgumentException("original has no correspondence: " + original);
		}
		if(correspondence.getMatchedB() != copy) {
			throw new IllegalStateException("mismatch between correspondences and copier for " + original);
		}
		return copy;
	}

	/**
	 * Get the original corresponding with the given copy.
	 * 
	 * @param copy
	 * @return
	 */
	private EObject getCorrespondingOriginal(EObject copy) {
		assertRelinkEnabled();
		return copy2Correspondence.get(copy).getMatchedA();
	}
	
	private void assertRelinkEnabled() {
		if(!relink) {
			throw new IllegalStateException("relink not enabled");
		}
	}
}
