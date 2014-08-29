package org.silift.difference.symboliclink.handler;


import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
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
import org.sidiff.difference.symmetric.Correspondence;
import org.sidiff.difference.symmetric.EObjectSet;
import org.sidiff.difference.symmetric.EditRuleMatch;
import org.sidiff.difference.symmetric.RemoveObject;
import org.sidiff.difference.symmetric.RemoveReference;
import org.sidiff.difference.symmetric.SemanticChangeSet;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.difference.symmetric.SymmetricFactory;
import org.silift.common.util.access.EMFModelAccessEx;
import org.silift.common.util.emf.EMFResourceUtil;
import org.silift.common.util.emf.EObjectLocation;
import org.silift.difference.symboliclink.ExternalSymbolicLinkObject;
import org.silift.difference.symboliclink.SymbolicLinkObject;
import org.silift.difference.symboliclink.SymbolicLinkReference;
import org.silift.difference.symboliclink.SymbolicLinks;
import org.silift.difference.symboliclink.SymboliclinkFactory;
import org.silift.difference.symboliclink.handler.ISymbolicLinkHandler;

/**
 * Implements the {@link ISymbolicLinkHandler} using the template method pattern.
 * So a concrete symbolic-link-handler must only implement the methods 
 * {@link #generateSymbolicLink(SymbolicLinks, EObject)} and
 * {@link #resolveSymbolicLink(SymbolicLink, Resource)} used by
 * the template methods
 * {@link #generateSymbolicLinks(AsymmetricDifference, boolean)} and
 * {@link #resolveSymbolicLinks(SymbolicLinks, Resource, boolean)}.
 * 
 * @author cpietsch
 *
 */
public abstract class AbstractSymbolicLinkHandler implements ISymbolicLinkHandler {
	
	/**
	 * {@link SymbolicLinks} of {@link #modelA}
	 */
	private SymbolicLinks sls_modelA;
	
	/**
	 * {@link SymbolicLinks} of {@link #modelB}
	 */
	private SymbolicLinks sls_modelB;
	
	/**
	 * 
	 */
	private Map<EObject,SymbolicLinkObject> obj2symbl_A;
	
	/**
	 * 
	 */
	private Map<EObject,SymbolicLinkObject> obj2symbl_B;
	
	/**
	 * 
	 */
	private List<SymbolicLinkReference> symblReferences_A;
	
	/**
	 * 
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
	 * {@link ISymbolicLinkHandler#generateSymbolicLinks(AsymmetricDifference, boolean)}
	 * using the template method pattern. The method {@link #generateSymbolicLinkObject(Map, EObject)}
	 * has to be implemented by a subclass.
	 */
	@Override
	public Collection<SymbolicLinks> generateSymbolicLinks(AsymmetricDifference asymmetricDifference, boolean calculateReliability) {
		
		// initialize all fields
		modelA = asymmetricDifference.getSymmetricDifference().getModelA();
		modelB = asymmetricDifference.getSymmetricDifference().getModelB();
		
		sls_modelA = SymboliclinkFactory.eINSTANCE.createSymbolicLinks();
		sls_modelB = SymboliclinkFactory.eINSTANCE.createSymbolicLinks();
		
		obj2symbl_A = new HashMap<EObject,SymbolicLinkObject>();
		obj2symbl_B = new HashMap<EObject,SymbolicLinkObject>();
		
		symblReferences_A = new LinkedList<SymbolicLinkReference>();
		symblReferences_B = new LinkedList<SymbolicLinkReference>();
		
		// the collection returned by this method
		Collection<SymbolicLinks> c_sls = new LinkedList<SymbolicLinks>();
		
		// calculate symbolic links for all objects referenced by the symmetric difference
		generateSymmetricSymbolicLinks(asymmetricDifference.getSymmetricDifference());
		
		// calculate symbolic links for all objects referenced by an operation invocation
		for(OperationInvocation o : asymmetricDifference.getOperationInvocations()){
			for(ParameterBinding binding : o.getParameterBindings()){
				if(binding instanceof ObjectParameterBinding){
					ObjectParameterBinding ob = (ObjectParameterBinding) binding;
					generateSL_for_ObjPB(ob);
				}else if(binding instanceof MultiParameterBinding){
					MultiParameterBinding mb = (MultiParameterBinding) binding;
					for(ParameterBinding binding_of_mb : mb.getParameterBindings()){
						if(binding_of_mb instanceof ObjectParameterBinding){
							ObjectParameterBinding ob = (ObjectParameterBinding) binding_of_mb;
							generateSL_for_ObjPB(ob);
						}
					}
				}
			}
		}
		
		// calculate symbolic links for all objects referenced by a dependency
		for(DependencyContainer dc : asymmetricDifference.getDepContainers()){
			for(Dependency d : dc.getDependencies()){
				SymbolicLinkObject symbolicLink = null;
				if(d instanceof NodeDependency){
					NodeDependency nodeDependency = (NodeDependency) d;
					if(nodeDependency.getObject().eResource().equals(modelA)){
						symbolicLink = generateSymbolicLinkObject(obj2symbl_A, nodeDependency.getObject());
					}else if(nodeDependency.getObject().eResource().equals(modelB)){
						symbolicLink = generateSymbolicLinkObject(obj2symbl_B, nodeDependency.getObject());
					}
					nodeDependency.setObject(symbolicLink);
				}else if(d instanceof EdgeDependency){
					EdgeDependency edgeDependency = (EdgeDependency) d;
					if(edgeDependency.getSrcObject().eResource().equals(modelA)){
						symbolicLink = generateSymbolicLinkObject(obj2symbl_A, edgeDependency.getSrcObject());
					}else if(edgeDependency.getSrcObject().eResource().equals(modelB)){
						symbolicLink = generateSymbolicLinkObject(obj2symbl_B, edgeDependency.getSrcObject());
					}
					edgeDependency.setSrcObject(symbolicLink);
					if(edgeDependency.getTgtObject().eResource().equals(modelA)){
						symbolicLink = generateSymbolicLinkObject(obj2symbl_A, edgeDependency.getTgtObject());
					}else if(edgeDependency.getTgtObject().eResource().equals(modelB)){
						symbolicLink = generateSymbolicLinkObject(obj2symbl_B, edgeDependency.getTgtObject());
					}
					edgeDependency.setTgtObject(symbolicLink);
				}else if(d instanceof AttributeDependency){
					AttributeDependency attributeDependency = (AttributeDependency) d;
					if(attributeDependency.getObject().eResource().equals(modelA)){ 
						symbolicLink = generateSymbolicLinkObject(obj2symbl_A, attributeDependency.getObject());
					}else if(attributeDependency.getObject().eResource().equals(modelB)){
						symbolicLink = generateSymbolicLinkObject(obj2symbl_B, attributeDependency.getObject());
					}
					attributeDependency.setObject(symbolicLink);
				}
			}
		}
		
		sls_modelA.getLinkObjects().addAll(obj2symbl_A.values());
		sls_modelA.getLinkReferences().addAll(symblReferences_A);
		sls_modelB.getLinkObjects().addAll(obj2symbl_B.values());
		sls_modelB.getLinkReferences().addAll(symblReferences_B);
		
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
		if(ob.getActualA()!= null && (EMFResourceUtil.locate(modelA, ob.getActualA()).equals(EObjectLocation.RESOURCE_INTERNAL))){
			SymbolicLinkObject symbolicLink = generateSymbolicLinkObject(obj2symbl_A, ob.getActualA());
			ob.setActualA(symbolicLink);
		}
		if(ob.getActualB()!=null && (EMFResourceUtil.locate(modelB, ob.getActualB()).equals(EObjectLocation.RESOURCE_INTERNAL))){
			SymbolicLinkObject symbolicLink = generateSymbolicLinkObject(obj2symbl_B, ob.getActualB());
			ob.setActualB(symbolicLink);
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
		
		
		sls_modelA = SymboliclinkFactory.eINSTANCE.createSymbolicLinks();
		sls_modelA.setDocType(EMFModelAccessEx.getCharacteristicDocumentType(modelA));
		sls_modelB = SymboliclinkFactory.eINSTANCE.createSymbolicLinks();
		sls_modelB.setDocType(EMFModelAccessEx.getCharacteristicDocumentType(modelB));
		EObjectLocation location;
		
		// changes
		for (Change change : symmetricDifference.getChanges()) {
			if (change instanceof RemoveReference) {
				RemoveReference removeReference = (RemoveReference) change;
				location = EMFResourceUtil.locate(modelA, removeReference.getSrc());
				if(location.equals(EObjectLocation.RESOURCE_INTERNAL))
				{
					SymbolicLinkObject sl_modelA_src = generateSymbolicLinkObject(obj2symbl_A, removeReference.getSrc());
					removeReference.setSrc(sl_modelA_src);
				}
				location = EMFResourceUtil.locate(modelA, removeReference.getTgt());
				if(location.equals(EObjectLocation.RESOURCE_INTERNAL)){
					SymbolicLinkObject sl_modelA_tgt = generateSymbolicLinkObject(obj2symbl_A, removeReference.getTgt());
					removeReference.setTgt(sl_modelA_tgt);
				}
			}
			
			if (change instanceof AddReference) {
				AddReference addReference = (AddReference) change;
				location = EMFResourceUtil.locate(modelB, addReference.getSrc());
				if(location.equals(EObjectLocation.RESOURCE_INTERNAL)){
					SymbolicLinkObject sl_modelB_src = generateSymbolicLinkObject(obj2symbl_B, addReference.getSrc());
					addReference.setSrc(sl_modelB_src);
				}
				location = EMFResourceUtil.locate(modelB, addReference.getTgt());
				if(location.equals(EObjectLocation.RESOURCE_INTERNAL)){
					SymbolicLinkObject sl_modelB_tgt = generateSymbolicLinkObject(obj2symbl_B, addReference.getTgt());
					addReference.setTgt(sl_modelB_tgt);
				}
			}
			
			if(change instanceof AttributeValueChange){
				AttributeValueChange attributeValueChange = (AttributeValueChange)change;
				SymbolicLinkObject sl_modelA = generateSymbolicLinkObject(obj2symbl_A, attributeValueChange.getObjA());
				attributeValueChange.setObjA(sl_modelA);
				
				SymbolicLinkObject sl_modelB = generateSymbolicLinkObject(obj2symbl_B, attributeValueChange.getObjB());
				attributeValueChange.setObjB(sl_modelB);
			}
			
			if(change instanceof RemoveObject){
				RemoveObject removeObject = (RemoveObject)change;
				SymbolicLinkObject sl_modelA = generateSymbolicLinkObject(obj2symbl_A, removeObject.getObj());
				removeObject.setObj(sl_modelA);
			}
			
			if(change instanceof AddObject){
				AddObject addObject = (AddObject)change;
				SymbolicLinkObject sl_modelB = generateSymbolicLinkObject(obj2symbl_B, addObject.getObj());
				addObject.setObj(sl_modelB);
			}
		}
		
		for(Correspondence c : symmetricDifference.getCorrespondences()){
			SymbolicLinkObject sl_modelA = generateSymbolicLinkObject(obj2symbl_A, c.getObjA());
			c.setObjA(sl_modelA);
			
			SymbolicLinkObject sl_modelB = generateSymbolicLinkObject(obj2symbl_B, c.getObjB());
			c.setObjB(sl_modelB);	
		}
		
		// edit rule matches
		for(SemanticChangeSet scs : symmetricDifference.getChangeSets()){
			EditRuleMatch editRuleMatch = scs.getEditRuleMatch();
			if(editRuleMatch != null){
				generateEditRuleMatchSymboicLinks(editRuleMatch);
			}
		}
	}
	
	/**
	 * Help method to calculate symbolic links for the {@link EditRuleMatch}es.
	 * 
	 * @param editRuleMatch
	 */
	private void generateEditRuleMatchSymboicLinks(EditRuleMatch editRuleMatch){
		// node occurrences in a
		for (String nodeOccurrenceA_key : editRuleMatch.getNodeOccurrencesA().keySet()) {
			EObjectSet eObjectSet = SymmetricFactory.eINSTANCE.createEObjectSet();
			
			for (EObject srcObject : editRuleMatch.getNodeOccurrencesA().get(nodeOccurrenceA_key).getElements()) {
				SymbolicLinkObject sl_modelA = obj2symbl_A.get(srcObject);
				eObjectSet.addElement(sl_modelA);
				
				// edge occurrences in a
				for (Iterator<EReference> iterator = srcObject.eClass().getEAllReferences().iterator(); iterator.hasNext();) {
					EReference eReference = iterator.next();
					if (!eReference.isDerived() && !eReference.getName().equals("eGenericType") && !eReference.getName().equals("eFactoryInstance")){
						if (eReference.isMany()) {
							@SuppressWarnings("unchecked")
							List<EObject> list = (List<EObject>)srcObject.eGet(eReference);
							for(EObject tgt : list){
								if(tgt != null){
									generateSymbolicLinkReference(obj2symbl_A, symblReferences_A, modelA, srcObject, tgt, eReference);
								}
							}
						}else{
							EObject tgtObject = (EObject)srcObject.eGet(eReference);
							if (tgtObject != null) {
								generateSymbolicLinkReference(obj2symbl_A, symblReferences_A, modelA, srcObject, tgtObject, eReference);
							}
						}
					}
				}
			}
			editRuleMatch.getNodeOccurrencesA().put(nodeOccurrenceA_key,
					eObjectSet);
		}
		
		// node occurrences in b
		for (String nodeOccurrenceB_key : editRuleMatch.getNodeOccurrencesB().keySet()) {
			EObjectSet eObjectSet = SymmetricFactory.eINSTANCE.createEObjectSet();

			for (EObject srcObject : editRuleMatch.getNodeOccurrencesB().get(nodeOccurrenceB_key).getElements()) {
				SymbolicLinkObject sl_modelB = obj2symbl_B.get(srcObject);
				eObjectSet.addElement(sl_modelB);

				// edge occurrences in b
				for (Iterator<EReference> iterator = srcObject.eClass().getEAllReferences().iterator(); iterator.hasNext();) {
					EReference eReference = iterator.next();
					if (!eReference.isDerived()	&& !eReference.getName().equals("eGenericType")	&& !eReference.getName().equals("eFactoryInstance")) {
						if (eReference.isMany()) {
							@SuppressWarnings("unchecked")
							List<EObject> list = (List<EObject>)srcObject.eGet(eReference);
							for(EObject tgt : list){
								if(tgt != null){
									generateSymbolicLinkReference(obj2symbl_B, symblReferences_B, modelB, srcObject, tgt, eReference);
								}
							}
						} else {
							EObject tgtObject = (EObject)srcObject.eGet(eReference);
							if (tgtObject != null) {
								generateSymbolicLinkReference(obj2symbl_B, symblReferences_B, modelB, srcObject, tgtObject, eReference);
							}
						}
					}
				}
			}
			editRuleMatch.getNodeOccurrencesB().put(nodeOccurrenceB_key, eObjectSet);
		}
	}
	
	/**
	 * 
	 * @param obj2symbl
	 * @param symblReferences
	 * @param model
	 * @param srcObject
	 * @param tgtObject
	 * @param eReference
	 */
	private void generateSymbolicLinkReference(Map<EObject, SymbolicLinkObject> obj2symbl,
			List<SymbolicLinkReference> symblReferences, Resource model,
			EObject srcObject, EObject tgtObject, EReference eReference) {
		
		boolean alreadyExists = false;
		
		for(SymbolicLinkReference symblReference : symblReferences){
			if(symblReference.getSource().equals(obj2symbl.get(srcObject)) && symblReference.getTarget().equals(obj2symbl.get(tgtObject)) && symblReference.getType().equals(eReference)){
				alreadyExists = true;
			}
		}
		
		if(!alreadyExists){
			EObjectLocation location;
			
			SymbolicLinkReference symblReference = SymboliclinkFactory.eINSTANCE.createSymbolicLinkReference();
			symblReference.setSource(obj2symbl.get(srcObject));
			location = EMFResourceUtil.locate(model, tgtObject);
			switch(location){
				case RESOURCE_INTERNAL:
					symblReference.setTarget(obj2symbl.get(tgtObject));
					break;
				case PACKAGE_REGISTRY:
					if(obj2symbl.containsKey(tgtObject)){
						symblReference.setTarget(obj2symbl.get(tgtObject));
					}else{
						ExternalSymbolicLinkObject ext_symbl = SymboliclinkFactory.eINSTANCE.createExternalSymbolicLinkObject();
						ext_symbl.setEObject(tgtObject);
						symblReference.setTarget(ext_symbl);
						obj2symbl.put(tgtObject, ext_symbl);
					}
					break;
				default:
					break;
			}
			symblReference.setType(eReference);
			symblReferences.add(symblReference);
		}
	}

	/**
	 * An implementation of this method has to create a {@link SymbolicLink} for an object, 
	 * put the link into the map and return the created link.
	 * @param obj2symbl
	 * 			a {@link Map} 
	 * @param eObject
	 * 				an object for which a {@link SymbolicLink} shall be created
	 * @return a {@link SymbolicLink} of an object
	 */
	public abstract SymbolicLinkObject generateSymbolicLinkObject(Map<EObject,SymbolicLinkObject> obj2symbl, EObject eObject);
	
	/**
	* An implementation of 
	* {@link ISymbolicLinkHandler#resolveSymbolicLinks(SymbolicLinks, Resource, boolean)}
	* using the template method pattern. The method {@link #resolveSymbolicLink(SymbolicLink, Resource)}
	* has to be implemented by a subclass.
	*/
	@Override
	public Map<SymbolicLinkObject, EObject> resolveSymbolicLinkObjects(SymbolicLinks symbolicLinks, Resource targetModel, boolean calculateReliability){
		Map<SymbolicLinkObject, EObject> symbl_2_eObject = new HashMap<SymbolicLinkObject, EObject>();
		for(SymbolicLinkObject symbolicLink : symbolicLinks.getLinkObjects()){
			if(symbolicLink instanceof ExternalSymbolicLinkObject){
				//TODO not needed at the moment unless we want to resolve edge occurrences of the edit rule matches
			}else{
				symbl_2_eObject.put(symbolicLink, resolveSymbolicLink(symbolicLink, targetModel));
			}
		}
		return symbl_2_eObject;
	}
	
	/**
	 * An implementation of this method has to solve a {@link SymbolicLink} to an object 
	 * of the target model.
	 * 
	 * @param symbolicLink
	 * 				a {@link SymbolicLink} that shall be resolved
	 * @param targetModel
	 * 				a {@link Resource} that contains the target model
	 * @return the resolved object
	 */
	public abstract EObject resolveSymbolicLink(SymbolicLinkObject symbolicLink, Resource targetModel);
	
}
