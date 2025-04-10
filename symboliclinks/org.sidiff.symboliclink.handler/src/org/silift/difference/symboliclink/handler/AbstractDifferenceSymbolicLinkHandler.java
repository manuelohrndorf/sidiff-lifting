package org.silift.difference.symboliclink.handler;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.emf.EMFResourceUtil;
import org.sidiff.common.emf.EMFUtil;
import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.common.emf.access.EObjectLocation;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.AttributeDependency;
import org.sidiff.difference.asymmetric.Dependency;
import org.sidiff.difference.asymmetric.DependencyContainer;
import org.sidiff.difference.asymmetric.EdgeDependency;
import org.sidiff.difference.asymmetric.MultiParameterBinding;
import org.sidiff.difference.asymmetric.NodeDependency;
import org.sidiff.difference.asymmetric.ObjectParameterBinding;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.asymmetric.ParameterBinding;
import org.sidiff.difference.symmetric.AddObject;
import org.sidiff.difference.symmetric.AddReference;
import org.sidiff.difference.symmetric.AttributeValueChange;
import org.sidiff.difference.symmetric.Change;
import org.sidiff.difference.symmetric.EObjectSet;
import org.sidiff.difference.symmetric.EditRuleMatch;
import org.sidiff.difference.symmetric.RemoveObject;
import org.sidiff.difference.symmetric.RemoveReference;
import org.sidiff.difference.symmetric.SemanticChangeSet;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.difference.symmetric.SymmetricFactory;
import org.sidiff.matching.model.Correspondence;
import org.silift.difference.symboliclink.ExternalSymbolicLinkObject;
import org.silift.difference.symboliclink.SymbolicLinkAttribute;
import org.silift.difference.symboliclink.SymbolicLinkObject;
import org.silift.difference.symboliclink.SymbolicLinkReference;
import org.silift.difference.symboliclink.SymbolicLinks;
import org.silift.difference.symboliclink.SymboliclinkFactory;

/**
 * Implements the {@link ISymbolicLinkHandler} using the template method pattern.
 * So a concrete symbolic-link-handler must only implement the methods 
 * {@link #generateInternalSymbolicLinkObject(EObject) and
 * {@link #resolveSymbolicLinkObject(SymbolicLinkObject, Resource)} used by
 * the template methods
 * {@link #generateSymbolicLinks(AsymmetricDifference, boolean)} and
 * {@link #resolveSymbolicLinks(SymbolicLinks, Resource, boolean)}.
 * 
 * @author cpietsch
 *
 */
public abstract class AbstractDifferenceSymbolicLinkHandler implements ISymbolicLinkHandler {
	
	/**
	 * {@link SymbolicLinks} of {@link #modelA}
	 */
	private SymbolicLinks sls_modelA;
	
	/**
	 * {@link SymbolicLinks} of {@link #modelB}
	 */
	private SymbolicLinks sls_modelB;
	
	/**
	 * All (internal and external) model elements of {@link #modelA} are mapped to their corresponding {@link SymbolicLinkObject}
	 */
	private Map<EObject,SymbolicLinkObject> obj2symbl_A;
	
	/**
	 * All (internal and external) model elements of {@link #modelB} are mapped to their corresponding {@link SymbolicLinkObject}
	 */
	private Map<EObject,SymbolicLinkObject> obj2symbl_B;
	
	/**
	 * A List of all {@link SymbolicLinkReference} derived from the edges of the {@link EditRuleMatch} of {@link #modelA}
	 */
	private List<SymbolicLinkReference> symblReferences_A;
	
	/**
	 * A List of all {@link SymbolicLinkReference} derived from the edges of the {@link EditRuleMatch} of {@link #modelB}
	 */
	private List<SymbolicLinkReference> symblReferences_B;
	
	/**
	 * {@link SymmetricDifference#getModelA()}
	 */
	private Resource modelA;
	
	/**
	 * {@link SymmetricDifference#getModelB()}
	 */
	private Resource modelB;
	
	/**
	 * An implementation of
	 * {@link ISymbolicLinkHandler#generateSymbolicLinks(AsymmetricDifference, boolean)}.
	 */
	@Override
	public Collection<SymbolicLinks> generateSymbolicLinks(AsymmetricDifference asymmetricDifference, boolean calculateReliability) {

		// initialize all fields
		modelA = asymmetricDifference.getSymmetricDifference().getModelA();
		modelB = asymmetricDifference.getSymmetricDifference().getModelB();

		sls_modelA = SymboliclinkFactory.eINSTANCE.createSymbolicLinks();
		sls_modelA.setDocType(EMFModelAccess.getCharacteristicDocumentType(modelA));

		sls_modelB = SymboliclinkFactory.eINSTANCE.createSymbolicLinks();
		sls_modelB.setDocType(EMFModelAccess.getCharacteristicDocumentType(modelB));

		obj2symbl_A = new HashMap<>();
		obj2symbl_B = new HashMap<>();

		symblReferences_A = new LinkedList<>();
		symblReferences_B = new LinkedList<>();

		// calculate symbolic links for all objects referenced by the symmetric difference
		generateSymmetricSymbolicLinks(asymmetricDifference.getSymmetricDifference());

		// calculate symbolic links for all objects referenced by an operation invocation
		for (OperationInvocation o : asymmetricDifference.getOperationInvocations()) {
			for (ParameterBinding binding : o.getParameterBindings()) {
				if (binding instanceof ObjectParameterBinding) {
					generateSL_for_ObjPB((ObjectParameterBinding)binding);
				} else if (binding instanceof MultiParameterBinding) {
					MultiParameterBinding mb = (MultiParameterBinding)binding;
					for (ParameterBinding binding_of_mb : mb.getParameterBindings()) {
						if (binding_of_mb instanceof ObjectParameterBinding) {
							generateSL_for_ObjPB((ObjectParameterBinding)binding_of_mb);
						}
					}
				}
			}
		}

		// calculate symbolic links for all objects referenced by a dependency
		for (DependencyContainer dependencyContainer : asymmetricDifference.getDepContainers()) {
			for (Dependency dependency : dependencyContainer.getDependencies()) {
				if (dependency instanceof NodeDependency) {
					NodeDependency nodeDependency = (NodeDependency)dependency;
					if (nodeDependency.getObject().eResource() == modelA) {
						nodeDependency.setObject(generateSymbolicLinkObject(obj2symbl_A, modelA, nodeDependency.getObject()));
					} else if (nodeDependency.getObject().eResource() == modelB) {
						nodeDependency.setObject(generateSymbolicLinkObject(obj2symbl_B, modelB, nodeDependency.getObject()));
					}
				} else if (dependency instanceof EdgeDependency) {
					EdgeDependency edgeDependency = (EdgeDependency)dependency;
					if (edgeDependency.getSrcObject().eResource() == modelA) {
						edgeDependency.setSrcObject(generateSymbolicLinkObject(obj2symbl_A, modelA, edgeDependency.getSrcObject()));
					} else if (edgeDependency.getSrcObject().eResource() == modelB) {
						edgeDependency.setSrcObject(generateSymbolicLinkObject(obj2symbl_B, modelB, edgeDependency.getSrcObject()));
					}

					if (edgeDependency.getTgtObject().eResource() == modelA) {
						edgeDependency.setTgtObject(generateSymbolicLinkObject(obj2symbl_A, modelA, edgeDependency.getTgtObject()));
					} else if (edgeDependency.getTgtObject().eResource() == modelB) {
						edgeDependency.setTgtObject(generateSymbolicLinkObject(obj2symbl_B, modelB, edgeDependency.getTgtObject()));
					}
				} else if (dependency instanceof AttributeDependency) {
					AttributeDependency attributeDependency = (AttributeDependency)dependency;
					if (attributeDependency.getObject().eResource() == modelA) {
						attributeDependency.setObject(generateSymbolicLinkObject(obj2symbl_A, modelA, attributeDependency.getObject()));
					} else if (attributeDependency.getObject().eResource() == modelB) {
						attributeDependency.setObject(generateSymbolicLinkObject(obj2symbl_B, modelB, attributeDependency.getObject()));
					}
				}
			}
		}

		sls_modelA.getLinkObjects().addAll(obj2symbl_A.values());
		sls_modelA.getLinkReferences().addAll(symblReferences_A);
		sls_modelB.getLinkObjects().addAll(obj2symbl_B.values());
		sls_modelB.getLinkReferences().addAll(symblReferences_B);

		Collection<SymbolicLinks> c_sls = new LinkedList<>();
		c_sls.add(sls_modelA);
		c_sls.add(sls_modelB);
		return c_sls;
	}

	/**
	 * Help method, to relink an {@link ObjectParameterBinding} to symbolic links 
	 * 
	 * @param ob
	 * 		the {@link ObjectParameterBinding} to relink
	 */
	private void generateSL_for_ObjPB(ObjectParameterBinding ob){
		if (ob.getActualA() != null && EMFResourceUtil.locate(modelA, ob.getActualA()) == EObjectLocation.RESOURCE_INTERNAL) {
			ob.setActualA(generateSymbolicLinkObject(obj2symbl_A, modelA, ob.getActualA()));
		}

		if (ob.getActualB() != null && EMFResourceUtil.locate(modelB, ob.getActualB()) == EObjectLocation.RESOURCE_INTERNAL) {
			ob.setActualB(generateSymbolicLinkObject(obj2symbl_B, modelB, ob.getActualB()));
		}
	}

	/**
	 * Help method to calculate symbolic links for the model elements of the models the {@link SymmetricDifference}
	 * is derived from.
	 * 
	 * @param symmetricDifference
	 * 						the {@link SymmetricDifference} contained in the {@link AsymmetricDifference}
	 */
	private void generateSymmetricSymbolicLinks(SymmetricDifference symmetricDifference) {
		// changes
		for (Change change : symmetricDifference.getChanges()) {
			if (change instanceof RemoveReference) {
				RemoveReference removeReference = (RemoveReference)change;
				if (EMFResourceUtil.locate(modelA, removeReference.getSrc()) == EObjectLocation.RESOURCE_INTERNAL) {
					removeReference.setSrc(generateSymbolicLinkObject(obj2symbl_A, modelA, removeReference.getSrc()));
				}
				if (EMFResourceUtil.locate(modelA, removeReference.getTgt()) == EObjectLocation.RESOURCE_INTERNAL) {
					removeReference.setTgt(generateSymbolicLinkObject(obj2symbl_A, modelA, removeReference.getTgt()));
				}
			} else if (change instanceof AddReference) {
				AddReference addReference = (AddReference)change;
				if (EMFResourceUtil.locate(modelB, addReference.getSrc()) == EObjectLocation.RESOURCE_INTERNAL) {
					addReference.setSrc(generateSymbolicLinkObject(obj2symbl_B, modelB, addReference.getSrc()));
				}
				if (EMFResourceUtil.locate(modelB, addReference.getTgt())  == EObjectLocation.RESOURCE_INTERNAL) {
					addReference.setTgt(generateSymbolicLinkObject(obj2symbl_B, modelB, addReference.getTgt()));
				}
			} else if (change instanceof AttributeValueChange) {
				AttributeValueChange attributeValueChange = (AttributeValueChange)change;
				attributeValueChange.setObjA(generateSymbolicLinkObject(obj2symbl_A, modelA, attributeValueChange.getObjA()));
				attributeValueChange.setObjB(generateSymbolicLinkObject(obj2symbl_B, modelB, attributeValueChange.getObjB()));
			} else if (change instanceof RemoveObject) {
				RemoveObject removeObject = (RemoveObject)change;
				removeObject.setObj(generateSymbolicLinkObject(obj2symbl_A, modelA, removeObject.getObj()));
			} else if (change instanceof AddObject) {
				AddObject addObject = (AddObject)change;
				SymbolicLinkObject sl_modelB = generateSymbolicLinkObject(obj2symbl_B, modelB, addObject.getObj());
				addObject.setObj(sl_modelB);
			}
		}

		// Correspondences
		for(Correspondence c : symmetricDifference.getMatching().getCorrespondences()){
			c.setMatchedA(generateSymbolicLinkObject(obj2symbl_A, modelA, c.getMatchedA()));
			c.setMatchedB(generateSymbolicLinkObject(obj2symbl_B, modelB, c.getMatchedB()));	
		}

		// Unmatched Elements
		List<EObject> unmatchedA = new ArrayList<>();
		for(EObject eObjA : symmetricDifference.getMatching().getUnmatchedA()){
			unmatchedA.add(generateSymbolicLinkObject(obj2symbl_A, modelA, eObjA));
		}
		symmetricDifference.getMatching().getUnmatchedA().clear();
		symmetricDifference.getMatching().getUnmatchedA().addAll(unmatchedA);

		List<EObject> unmatchedB = new ArrayList<>();
		for(EObject eObjB : symmetricDifference.getMatching().getUnmatchedB()){
			unmatchedB.add(generateSymbolicLinkObject(obj2symbl_A, modelA, eObjB));
		}
		symmetricDifference.getMatching().getUnmatchedB().clear();
		symmetricDifference.getMatching().getUnmatchedB().addAll(unmatchedB);

		// edit rule matches
		for(SemanticChangeSet scs : symmetricDifference.getChangeSets()){
			EditRuleMatch editRuleMatch = scs.getEditRuleMatch();
			if(editRuleMatch != null) {
				generateEditRuleMatchSymbolicLinks(editRuleMatch);
			}
		}
	}

	/**
	 * Help method to calculate symbolic links for the {@link EditRuleMatch}es.
	 * 
	 * @param editRuleMatch
	 */
	private void generateEditRuleMatchSymbolicLinks(EditRuleMatch editRuleMatch){

		// add unconsidered reference types
		Set<EReference> unconsideredEReferences = new HashSet<>();
		unconsideredEReferences.add(EcorePackage.eINSTANCE.getEClassifier_ETypeParameters());
		unconsideredEReferences.add(EcorePackage.eINSTANCE.getEOperation_ETypeParameters());
		unconsideredEReferences.add(EcorePackage.eINSTANCE.getEOperation_EGenericExceptions());
		unconsideredEReferences.add(EcorePackage.eINSTANCE.getEClass_EGenericSuperTypes());
		unconsideredEReferences.add(EcorePackage.eINSTANCE.getETypedElement_EGenericType());
		unconsideredEReferences.add(EcorePackage.eINSTANCE.getEPackage_EFactoryInstance());

		// node occurrences in a
		for (String nodeOccurrenceA_key : editRuleMatch.getNodeOccurrencesA().keySet()) {
			EObjectSet eObjectSet = SymmetricFactory.eINSTANCE.createEObjectSet();
			for (EObject srcObject : editRuleMatch.getNodeOccurrencesA().get(nodeOccurrenceA_key).getElements()) {
				SymbolicLinkObject sl_modelA = generateSymbolicLinkObject(obj2symbl_A, modelA, srcObject);
				eObjectSet.addElement(sl_modelA);

				// edge occurrences in a
				for (EReference eReference : srcObject.eClass().getEAllReferences()) {
					if (!eReference.isDerived() && !unconsideredEReferences.contains(eReference)) {
						for(EObject tgtObject : EMFUtil.getReferenceTargets(srcObject, eReference)){
							generateSymbolicLinkReference(obj2symbl_A, symblReferences_A, modelA, srcObject, tgtObject, eReference);
						}
					}
				}

				// attribute occurrences in a
				for(EAttribute eAttribute : srcObject.eClass().getEAllAttributes()) {
					if(!eAttribute.isDerived()) {
						if(eAttribute.isMany()) {
							//TODO
						}else{
							Object value = srcObject.eGet(eAttribute);
							generateSymbolicLinkAttribute(obj2symbl_A, modelA, srcObject,  value, eAttribute);
						}
					}
				}
			}
			editRuleMatch.getNodeOccurrencesA().put(nodeOccurrenceA_key, eObjectSet);
		}

		// node occurrences in b
		for (String nodeOccurrenceB_key : editRuleMatch.getNodeOccurrencesB().keySet()) {
			EObjectSet eObjectSet = SymmetricFactory.eINSTANCE.createEObjectSet();
			for (EObject srcObject : editRuleMatch.getNodeOccurrencesB().get(nodeOccurrenceB_key).getElements()) {
				SymbolicLinkObject sl_modelB = generateSymbolicLinkObject(obj2symbl_B, modelB, srcObject);
				eObjectSet.addElement(sl_modelB);

				// edge occurrences in b
				for (EReference eReference : srcObject.eClass().getEAllReferences()) {
					if (!eReference.isDerived()	&& !unconsideredEReferences.contains(eReference)) {
						for(EObject tgtObject : EMFUtil.getReferenceTargets(srcObject, eReference)) {
							generateSymbolicLinkReference(obj2symbl_B, symblReferences_B, modelB, srcObject, tgtObject, eReference);
						}
					}
				}

				// attribute occurrences in b
				for(EAttribute eAttribute : srcObject.eClass().getEAllAttributes()) {
					if(!eAttribute.isDerived()){
						if(eAttribute.isMany()){
							//TODO
						}else{
							Object value = srcObject.eGet(eAttribute);
							generateSymbolicLinkAttribute(obj2symbl_B, modelB, srcObject, value, eAttribute);
						}
					}
				}
			}
			editRuleMatch.getNodeOccurrencesB().put(nodeOccurrenceB_key, eObjectSet);
		}
	}
	
	/**
	 * Creates a {@link SymbolicLinkReference} for an reference of the type and
	 * between the objects give by the parameters.
	 * 
	 * @param obj2symbl
	 * 			a {@link Map}
	 * @param symblReferences
	 * 			a {@link List} of {@link SymbolicLinkReference}
	 * @param model
	 * 			the resource containing the reference a {@link SymbolicLinkReference} shall be created for
	 * @param srcObject
	 * 			the source of the reference
	 * @param tgtObject
	 * 			the target of the reference (can be an external object)
	 * @param eReference
	 * 			the type of the reference
	 */
	private void generateSymbolicLinkReference(Map<EObject, SymbolicLinkObject> obj2symbl, List<SymbolicLinkReference> symblReferences, Resource model, EObject srcObject, EObject tgtObject, EReference eReference) {

		SymbolicLinkObject srcSymblObject = generateSymbolicLinkObject(obj2symbl, model, srcObject);
		SymbolicLinkObject tgtSymblObject = generateSymbolicLinkObject(obj2symbl, model, tgtObject);

		//check if there is already a reference of the given type and having the same source and target
		boolean alreadyExists = false;
		for(SymbolicLinkReference symblReference : symblReferences){
			if(symblReference.getSource().equals(srcSymblObject) && symblReference.getTarget().equals(tgtSymblObject) && symblReference.getType().equals(eReference)){
				alreadyExists = true;
			}
		}

		if (!alreadyExists) {
			SymbolicLinkReference symblReference = SymboliclinkFactory.eINSTANCE.createSymbolicLinkReference();
			symblReference.setSource(generateSymbolicLinkObject(obj2symbl, model, srcObject));
			symblReference.setTarget(generateSymbolicLinkObject(obj2symbl, model, tgtObject));
			symblReference.setType(eReference);
			symblReferences.add(symblReference);
		}
	}
	
	/**
	 * Creates a {@link SymbolicLinkAttribute} for an attribute of an internal
	 * object. "internal" means, that the object is contained in the resource
	 * {@link #modelA} or {@link #modelB} or by their resource sets.
	 * 
	 * @param obj2symbl
	 * 			a {@link Map}
	 * @param model
	 * 			the resource containing the attribute a {@link SymbolicLinkAttribute} shall be created for
	 * @param eObject
	 * 			the object containing the attribute a {@link SymbolicLinkAttribute} shall be created for
	 * @param value
	 * 			the current value of the attribute
	 * @param eAttribute
	 * 			the attribute a {@link SymbolicLinkAttribute} shall be created for
	 */
	private void generateSymbolicLinkAttribute(Map<EObject, SymbolicLinkObject> obj2symbl, Resource model, EObject eObject, Object value, EAttribute eAttribute){

		SymbolicLinkObject symbolicLinkObject = generateSymbolicLinkObject(obj2symbl, model, eObject);
		// only attributes of internal objects have to be generated
		if(!(symbolicLinkObject instanceof ExternalSymbolicLinkObject)){
	
			// check if there is already a symbolic link of the attribute
			boolean alreadyExists = false;
			for(SymbolicLinkAttribute symblAttribute : symbolicLinkObject.getLinkAttributes()) {
				if(symblAttribute.getType().equals(eAttribute)) {
					alreadyExists = true;
					break;
				}
			}

			if (!alreadyExists) {
				SymbolicLinkAttribute symblAttribute = SymboliclinkFactory.eINSTANCE.createSymbolicLinkAttribute();
				symblAttribute.setValue(value != null ? value.toString() : null);
				symblAttribute.setType(eAttribute);
				symbolicLinkObject.getLinkAttributes().add(symblAttribute);
			}
		}
	}

	/**
	 * Checks if there is already a {@link SymbolicLinkObject} for the given
	 * object, otherwise it delegates the generation of a
	 * {@link SymbolicLinkObject} to the appropriate method and put the result
	 * into the map using the template method pattern. The method
	 * {@link #generateInternalSymbolicLinkObject(EObject)} has to be
	 * implemented by a subclass.
	 * 
	 * @param obj2symbl
	 *            a {@link Map}
	 * @param model
	 *            the resource containing attribute a
	 *            {@link SymbolicLinkAttribute} shall be created for
	 * @param eObject
	 *            an object for which a {@link SymbolicLinkObject} shall be
	 *            created
	 * @return a {@link SymbolicLinkObject} of an object
	 */
	private SymbolicLinkObject generateSymbolicLinkObject(Map<EObject,SymbolicLinkObject> obj2symbl, Resource model, EObject eObject){
		
		SymbolicLinkObject symbolicLinkObject = obj2symbl.get(eObject);
		if(symbolicLinkObject != null) {
			return symbolicLinkObject;
		}
		
		switch(EMFResourceUtil.locate(model, eObject)) {
			case RESOURCE_INTERNAL:
			case RESOURCE_SET_INTERNAL:
				symbolicLinkObject = generateInternalSymbolicLinkObject(eObject);
				break;

			case PACKAGE_REGISTRY:
				symbolicLinkObject = generateExternalSymbolicLinkObject(eObject);
				break;
		}		
		Assert.isNotNull(symbolicLinkObject, "Can't create a SymbolicLinkObject for " + eObject);
		symbolicLinkObject.setType(eObject.eClass());
		obj2symbl.put(eObject, symbolicLinkObject);
		return symbolicLinkObject;
	}
	
	/**
	 * An implementation of this method has to create a
	 * {@link SymbolicLinkObject} for an internal object, return the created
	 * link. "internal" means, that the object is contained in the resource
	 * {@link #modelA} or {@link #modelB} or by their resource sets.
	 * 
	 * @param obj2symbl
	 *            a {@link Map}
	 * @param eObject
	 *            an internal object for which a {@link SymbolicLinkObject}
	 *            shall be created
	 * @return a {@link SymbolicLinkObject} of an internal object
	 */
	protected abstract SymbolicLinkObject generateInternalSymbolicLinkObject(EObject eObject);
	
	/**
	 * Creates a {@link SymbolicLinkObject} for an external object, and return
	 * the created link. "external" means, that the object is contained in a
	 * package from the package registry.
	 * 
	 * @param obj2symbl
	 *            a {@link Map}
	 * @param eObject
	 *            an external object for which a {@link SymbolicLinkObject}
	 *            shall be created
	 * @return a {@link SymbolicLinkObject} of an external object
	 */
	private SymbolicLinkObject generateExternalSymbolicLinkObject(EObject eObject) {
		ExternalSymbolicLinkObject externalSymbolicLinkObject = SymboliclinkFactory.eINSTANCE.createExternalSymbolicLinkObject();
		externalSymbolicLinkObject.setEObject(eObject);
		return externalSymbolicLinkObject;
	}
	
	/**
	* An implementation of 
	* {@link ISymbolicLinkHandler#resolveSymbolicLinks(SymbolicLinks, Resource, boolean)}
	* using the template method pattern. The method {@link #resolveSymbolicLinkObject(SymbolicLinkObject, Resource)}
	* has to be implemented by a subclass.
	*/
	@Override
	public Map<SymbolicLinkObject, EObject> resolveSymbolicLinkObjects(SymbolicLinks symbolicLinks,
			Resource targetModel, boolean calculateReliability) {

		Map<SymbolicLinkObject, EObject> symbl_2_eObject = new HashMap<>();
		for (SymbolicLinkObject symbolicLink : symbolicLinks.getLinkObjects()) {
			if (symbolicLink instanceof ExternalSymbolicLinkObject) {
				// TODO not needed at the moment unless we want to resolve edge occurrences of the edit rule matches
			} else {
				symbl_2_eObject.put(symbolicLink, resolveSymbolicLinkObject(symbolicLink, targetModel));
			}
		}
		return symbl_2_eObject;
	}
	
	/**
	 * An implementation of this method has to solve a {@link SymbolicLinkObject} to an object 
	 * of the target model.
	 * 
	 * @param symbolicLinkObject
	 * 				a {@link SymbolicLinkObject} that shall be resolved
	 * @param targetModel
	 * 				a {@link Resource} that contains the target model
	 * @return the resolved object
	 */
	protected abstract EObject resolveSymbolicLinkObject(SymbolicLinkObject symbolicLinkObject, Resource targetModel);
	
}
