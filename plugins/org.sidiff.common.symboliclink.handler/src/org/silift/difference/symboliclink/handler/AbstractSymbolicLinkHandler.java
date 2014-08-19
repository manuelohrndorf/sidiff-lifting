package org.silift.difference.symboliclink.handler;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
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
import org.sidiff.difference.symmetric.RemoveObject;
import org.sidiff.difference.symmetric.RemoveReference;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.silift.common.util.access.EMFModelAccessEx;
import org.silift.common.util.emf.EMFResourceUtil;
import org.silift.common.util.emf.EObjectLocation;
import org.silift.difference.symboliclink.SymbolicLink;
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
	 * Symbolic links of {@link #modelA}
	 */
	private SymbolicLinks sls_modelA;
	
	/**
	 * Symbolic links of {@link #modelB}
	 */
	private SymbolicLinks sls_modelB;
	
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
	 * using the template method pattern. The method {@link #generateSymbolicLink(SymbolicLinks, EObject)}
	 * has to be implemented by a subclass.
	 */
	@Override
	public Collection<SymbolicLinks> generateSymbolicLinks(AsymmetricDifference asymmetricDifference, boolean calculateReliability) {
		
		Collection<SymbolicLinks> c_sls = generateSymmetricSymbolicLinks(asymmetricDifference.getSymmetricDifference());
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
		for(DependencyContainer dc : asymmetricDifference.getDepContainers()){
			for(Dependency d : dc.getDependencies()){
				SymbolicLink symbolicLink = null;
				if(d instanceof NodeDependency){
					NodeDependency nodeDependency = (NodeDependency) d;
					if(nodeDependency.getObject().eResource().equals(modelA)){
						symbolicLink = generateSymbolicLink(sls_modelA, nodeDependency.getObject());
					}else if(nodeDependency.getObject().eResource().equals(modelB)){
						symbolicLink = generateSymbolicLink(sls_modelB, nodeDependency.getObject());
					}
					nodeDependency.setObject(symbolicLink);
				}else if(d instanceof EdgeDependency){
					EdgeDependency edgeDependency = (EdgeDependency) d;
					if(edgeDependency.getSrcObject().eResource().equals(modelA)){
						symbolicLink = generateSymbolicLink(sls_modelA, edgeDependency.getSrcObject());
					}else if(edgeDependency.getSrcObject().eResource().equals(modelB)){
						symbolicLink = generateSymbolicLink(sls_modelB, edgeDependency.getSrcObject());
					}
					edgeDependency.setSrcObject(symbolicLink);
					if(edgeDependency.getTgtObject().eResource().equals(modelA)){
						symbolicLink = generateSymbolicLink(sls_modelA, edgeDependency.getTgtObject());
					}else if(edgeDependency.getTgtObject().eResource().equals(modelB)){
						symbolicLink = generateSymbolicLink(sls_modelB, edgeDependency.getTgtObject());
					}
					edgeDependency.setTgtObject(symbolicLink);
				}else if(d instanceof AttributeDependency){
					AttributeDependency attributeDependency = (AttributeDependency) d;
					if(attributeDependency.getObject().eResource().equals(modelA)){ 
						symbolicLink = generateSymbolicLink(sls_modelA, attributeDependency.getObject());
					}else if(attributeDependency.getObject().eResource().equals(modelB)){
						symbolicLink = generateSymbolicLink(sls_modelB, attributeDependency.getObject());
					}
					attributeDependency.setObject(symbolicLink);
				}
			}
		}
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
			SymbolicLink symbolicLink = generateSymbolicLink(sls_modelA, ob.getActualA());
			ob.setActualA(symbolicLink);
		}
		if(ob.getActualB()!=null && (EMFResourceUtil.locate(modelB, ob.getActualB()).equals(EObjectLocation.RESOURCE_INTERNAL))){
			SymbolicLink symbolicLink = generateSymbolicLink(sls_modelB, ob.getActualB());
			ob.setActualB(symbolicLink);
		}
	}

	/**
	 * Help method to calculate symbolic links for the model elements of the models the {@link SymmetricDifference}
	 * is derived from.
	 * 
	 * @param symmetricDifference
	 * 						the {@link SymmetricDifference} contained in the {@link AsymmetricDifference}
	 * @return a collection with {@link SymbolicLinks} objects for the models the {@link SymmetricDifference} is derived from
	 */
	private Collection<SymbolicLinks> generateSymmetricSymbolicLinks(SymmetricDifference symmetricDifference) {
		
		modelA = symmetricDifference.getModelA();
		modelB = symmetricDifference.getModelB();
		
		Collection<SymbolicLinks> c_sls = new ArrayList<SymbolicLinks>();
		sls_modelA = SymboliclinkFactory.eINSTANCE.createSymbolicLinks();
		sls_modelA.setDocType(EMFModelAccessEx.getCharacteristicDocumentType(modelA));
		sls_modelB = SymboliclinkFactory.eINSTANCE.createSymbolicLinks();
		sls_modelB.setDocType(EMFModelAccessEx.getCharacteristicDocumentType(modelB));
		EObjectLocation location;
		
		for (Change change : symmetricDifference.getChanges()) {
			if (change instanceof RemoveReference) {
				RemoveReference removeReference = (RemoveReference) change;
				location = EMFResourceUtil.locate(modelA, removeReference.getSrc());
				if(location.equals(EObjectLocation.RESOURCE_INTERNAL))
				{
					SymbolicLink sl_modelA_src = generateSymbolicLink(sls_modelA, removeReference.getSrc());
					removeReference.setSrc(sl_modelA_src);
				}
				location = EMFResourceUtil.locate(modelA, removeReference.getTgt());
				if(location.equals(EObjectLocation.RESOURCE_INTERNAL)){
					SymbolicLink sl_modelA_tgt = generateSymbolicLink(sls_modelA, removeReference.getTgt());
					removeReference.setTgt(sl_modelA_tgt);
				}
			}
			
			if (change instanceof AddReference) {
				AddReference addReference = (AddReference) change;
				location = EMFResourceUtil.locate(modelB, addReference.getSrc());
				if(location.equals(EObjectLocation.RESOURCE_INTERNAL)){
					SymbolicLink sl_modelB_src = generateSymbolicLink(sls_modelB, addReference.getSrc());
					addReference.setSrc(sl_modelB_src);
				}
				location = EMFResourceUtil.locate(modelB, addReference.getTgt());
				if(location.equals(EObjectLocation.RESOURCE_INTERNAL)){
					SymbolicLink sl_modelB_tgt = generateSymbolicLink(sls_modelB, addReference.getTgt());
					addReference.setTgt(sl_modelB_tgt);
				}
			}
			
			if(change instanceof AttributeValueChange){
				AttributeValueChange attributeValueChange = (AttributeValueChange)change;
				SymbolicLink sl_modelA = generateSymbolicLink(sls_modelA, attributeValueChange.getObjA());
				attributeValueChange.setObjA(sl_modelA);
				
				SymbolicLink sl_modelB = generateSymbolicLink(sls_modelB, attributeValueChange.getObjB());
				attributeValueChange.setObjB(sl_modelB);
			}
			
			if(change instanceof RemoveObject){
				RemoveObject removeObject = (RemoveObject)change;
				SymbolicLink sl_modelA = generateSymbolicLink(sls_modelA, removeObject.getObj());
				removeObject.setObj(sl_modelA);
			}
			
			if(change instanceof AddObject){
				AddObject addObject = (AddObject)change;
				SymbolicLink sl_modelB = generateSymbolicLink(sls_modelB, addObject.getObj());
				addObject.setObj(sl_modelB);
			}
		}
		
		for(Correspondence c : symmetricDifference.getCorrespondences()){
			SymbolicLink sl_modelA = generateSymbolicLink(sls_modelA, c.getObjA());
			c.setObjA(sl_modelA);
			
			SymbolicLink sl_modelB = generateSymbolicLink(sls_modelB, c.getObjB());
			c.setObjB(sl_modelB);	
		}
		
		c_sls.add(sls_modelA);
		c_sls.add(sls_modelB);
		
		return c_sls;
	}
	
	/**
	 * An implementation of this method has to create a {@link SymbolicLink} for an object, 
	 * put the link into the container {@link SymbolicLinks} and return the created link.
	 * @param symbolicLinks
	 * 				container to which the generated {@link SymbolicLink} will be added
	 * @param eObject
	 * 				an object for which a {@link SymbolicLink} shall be created
	 * @return a {@link SymbolicLink} of an object
	 */
	public abstract SymbolicLink generateSymbolicLink(SymbolicLinks symbolicLinks, EObject eObject);
	
	/**
	* An implementation of 
	* {@link ISymbolicLinkHandler#resolveSymbolicLinks(SymbolicLinks, Resource, boolean)}
	* using the template method pattern. The method {@link #resolveSymbolicLink(SymbolicLink, Resource)}
	* has to be implemented by a subclass.
	*/
	@Override
	public Map<SymbolicLink, EObject> resolveSymbolicLinks(SymbolicLinks symbolicLinks, Resource targetModel, boolean calculateReliability){
		Map<SymbolicLink, EObject> symbl_2_eObject = new HashMap<SymbolicLink, EObject>();
		for(SymbolicLink symbolicLink : symbolicLinks.getLinks()){
			symbl_2_eObject.put(symbolicLink, resolveSymbolicLink(symbolicLink, targetModel));
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
	public abstract EObject resolveSymbolicLink(SymbolicLink symbolicLink, Resource targetModel);
	
}
