package org.sidiff.difference.matcher;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.silift.common.util.emf.Scope;

/**
 * An abstract signature based matcher.
 * A subclass has to implement the method {@link #calculateSignature(EObject)},
 * {@link #getDocumentType()}, {@link #canHandle(Resource, Resource)} and 
 * {@link #canComputeReliability()}
 * 
 * @author cpietsch
 *
 */
public abstract class SignatureBasedMatcher extends AbstractMatcher {

	protected Map<Integer,Set<EObject>> signatures;
	
	@Override
	protected void compareResources(){
		signatures = new HashMap<Integer, Set<EObject>>();
		if(scope.equals(Scope.RESOURCE_SET)){
			for(Resource r : modelA.getResourceSet().getResources()){
				calculateSignatures(r);
			}
		}else{
			calculateSignatures(modelA);
		}
		calculateSignatures(modelA);
		for (Iterator<EObject> iterator= modelB.getAllContents(); iterator.hasNext();) {
			EObject eObjectB =  iterator.next();
			int signature = calculateSignature(eObjectB);
			if(signatures.containsKey(signature)){
				EObject eObjectA = signatures.get(signature).iterator().next();
				if(!hasCorrespondence(eObjectA) && !hasCorrespondence(eObjectB)){
					// TODO there can be multiple objects with the same signature
					
					matching.addCorrespondence(eObjectA, eObjectB);
				}
			}
		}
	}
	
	private void calculateSignatures(Resource model){

		for (Iterator<EObject> iterator = model.getAllContents(); iterator
				.hasNext();) {
			EObject eObject = iterator.next();
			int signature = calculateSignature(eObject);
			if (signature == 0)
				continue;
			if (signatures.containsKey(signature)) {
				signatures.get(signature).add(eObject);
			} else {
				Set<EObject> eObjects = new HashSet<EObject>();
				eObjects.add(eObject);
				signatures.put(signature, eObjects);
			}

		}
		
	}
	
	protected abstract int calculateSignature(EObject eObject);
}
