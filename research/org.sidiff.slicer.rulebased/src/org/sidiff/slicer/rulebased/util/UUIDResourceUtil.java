package org.sidiff.slicer.rulebased.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.sidiff.common.emf.EMFUtil;
import org.sidiff.common.emf.modelstorage.UUIDResource;

/**
 * 
 * @author cpietsch
 *
 */
public class UUIDResourceUtil {

	public static UUIDResource copyResource(UUIDResource resource_origin, URI uri_copy) {
		
		UUIDResource resource_copy = UUIDResource.createUUIDResource(uri_copy);
		
		Copier copier = new Copier();
		copier.copyAll(resource_origin.getContents());
		copier.copyReferences();
		
		List<EObject> contents_copy = new ArrayList<EObject>();
		
		for(EObject content_origin : resource_origin.getContents()) {
			EObject content_copy = copier.get(content_origin);
			contents_copy.add(content_copy);
		}
		
		resource_copy.getContents().addAll(contents_copy);
		
		for (EObject origin : copier.keySet()) {
			String id = EMFUtil.getXmiId(origin);
			EMFUtil.setXmiId(copier.get(origin), id);
		}
		
		return resource_copy;
	}
	
	@SuppressWarnings("unchecked")
	public static UUIDResource copyMinimalResource(UUIDResource resource_origin, URI uri_copy) {
		UUIDResource resource_copy = UUIDResource.createUUIDResource(uri_copy);
	
		Map<EObject,EObject> copies = new HashMap<EObject, EObject>();
		Set<EObject> remaining = new HashSet<EObject>(resource_origin.getContents());
		while(!remaining.isEmpty()){
			Set<EObject> dependencies = new HashSet<EObject>();
			for (Iterator<EObject> iterator = remaining.iterator(); iterator.hasNext();) {
				EObject eObject = iterator.next();
				copies.put(eObject, EMFUtil.copyWithoutReferences(eObject));
				for(EReference eReference : eObject.eClass().getEAllReferences()){
					if(eReference.isChangeable() && !eReference.isDerived() && eReference.getLowerBound() > 0){
						for(EObject target : EMFUtil.getReferenceTargets(eObject, eReference)) {
							if(target.eResource() != null && target.eResource().equals(resource_origin)) {
								dependencies.add(target);
							}
						}
					}
				}
				iterator.remove();
			}
			
			dependencies.removeAll(copies.keySet());
			
			remaining.addAll(dependencies);
		}

		for(EObject eObject_origin : copies.keySet()){
			for(EReference eReference : eObject_origin.eClass().getEAllReferences()){
				if(eReference.isChangeable() && !eReference.isDerived() && eReference.isChangeable()){
					for(EObject target : EMFUtil.getReferenceTargets(eObject_origin, eReference)) {
						EObject target_copy = null;
						if(copies.containsKey(target)) {
							target_copy = copies.get(target);
						}else if(target.eResource()!=resource_origin) {
							target_copy = target;
						}else {
							continue;
						}
						
						if(eReference.isMany()) {
							((Collection<EObject>) copies.get(eObject_origin).eGet(eReference)).add(target_copy);
						}else {
							copies.get(eObject_origin).eSet(eReference, target_copy);
						}
					}
				}
			}
		}
		
		List<EObject> contents_copy = new ArrayList<EObject>();
		
		for(EObject content_origin : resource_origin.getContents()) {
			EObject content_copy = copies.get(content_origin);
			contents_copy.add(content_copy);
		}
		
		resource_copy.getContents().addAll(contents_copy);
		
		for (EObject origin : copies.keySet()) {
			String id = EMFUtil.getXmiId(origin);
			EMFUtil.setXmiId(copies.get(origin), id);
		}
		
		return resource_copy;
	}
}
