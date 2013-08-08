package org.sidiff.serge.core;

import java.io.IOException;
import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.sidiff.common.emf.EMFUtil;
import org.sidiff.common.emf.access.path.EMFPath;

public class MetaModelSlicer {

	private Registry ePackageRegistry = EPackageRegistryImpl.INSTANCE;
	private EClassInfoManagement ecm = EClassInfoManagement.getInstance(false);
	
	
	public void slice(EPackage mainMetaModel, List<EPackage> requiredMetaModels, List<EClass> keyElements, List<EClass> excludableElements, String newNS_URI) {
	
		String testingOutputPath = "/media/mrindt/data/Workspaces/Linux/SERx/RESULTS/sliced.ecore";
		
		final ResourceSet resourceSet = new ResourceSetImpl();	
		EPackage origMetaModel = ePackageRegistry.getEPackage(mainMetaModel.getNsURI());
		Resource slicedMetaModelResource = resourceSet.createResource(URI.createFileURI(testingOutputPath));

		
		Copier copier = new Copier(false, false);
		Collection<EPackage> copy = copier.copyAll(Collections.singleton(origMetaModel));
		slicedMetaModelResource.getContents().addAll(copy);
		copier.copyReferences();
		EcoreUtil.resolveAll(slicedMetaModelResource);
	
		
		// Map EObjects of both Resources 
		//TODO find out how Maiks TypePathAnnotator works
//		Map<EObject,EObject> eObjectMap = new HashMap<EObject, EObject>();
//		for(EObject eob_Orig: EMFUtil.getEAllContentAsIterable(origMetaModel)) {
//			for(EObject eob_Slice: EMFUtil.getEAllContentAsIterable(slicedMetaModelResource.getContents().get(0))) {
//
//				//path comparison instead of id
//				
//
//			}
//		}
		
		
		//TODO Copy eAnntoations (except path annotation), too, since Copier ignores them
		//TODO References to other Metamodels must be copied, too.
		
		
		assert(EcoreUtil.UnresolvedProxyCrossReferencer.find(slicedMetaModelResource).isEmpty()) : "There are still some unresolved proxies";
		
		
		EPackage slicedMetaModel = (EPackage) slicedMetaModelResource.getContents().get(0);
		slicedMetaModel.setNsURI(newNS_URI);
		
					
		Map<EClassifier,EClassifier> eClassifierMap = new HashMap<EClassifier, EClassifier>();
		
		// Create Map between Elements of both EPackages
		for(EClassifier eClassifier_Orig: origMetaModel.getEClassifiers()) {
			for(EClassifier eClassifier_Slice: slicedMetaModel.getEClassifiers()) {
				
				if(eClassifier_Orig.getClassifierID()==eClassifier_Slice.getClassifierID()) {
					eClassifierMap.put(eClassifier_Orig,eClassifier_Slice);
				}
			}
		}
		

		//temp workaround, since whitelist only contains EClasses, not EClassifiers
		List<EClassifier> keyElementsWorkaround = new ArrayList<EClassifier>();
		for(EClass eClass: keyElements) {
			EClassifier correspondingMapClassifier = eClassifierMap.get((EClassifier)eClass);
			keyElementsWorkaround.add(correspondingMapClassifier);
		}
		
		
		// Phase 1: mark key Classifiers
		for(EClassifier eClassifier: slicedMetaModel.getEClassifiers()) {
			if(keyElementsWorkaround.contains(eClassifier)) {
				EAnnotation eanno = EcoreFactory.eINSTANCE.createEAnnotation();
				eanno.setSource("SlicerMark");
				eanno.getDetails().put("SlicerMark", "keep");
				eClassifier.getEAnnotations().add(eanno);
			}
		}
		
		 //TODO Phase 2 mark direct mandatory classifiers of key classifiers
		 //TODO Phase 3 repeat 2. until no additions to the newly added classifiers left
		 //TODO Phase 4 mark direct supertypes
		 //TODO Phase 5 repeat 3.+4. until no new additions left
		 //TODO Phase 6 (EAttributes?)
		
		 
		// Remove unmarked eClassifiers
		Iterator<EClassifier> it = slicedMetaModel.getEClassifiers().iterator();
		while (it.hasNext()) {
			EClassifier eClassifier = it.next();
			
			if(!EMFUtil.isAnnotatedWith(eClassifier, "SlicerMark")) {				
				it.remove();
			}
		}
		
		//TODO remove slicer marks
		
		// serialize sliced meta model:
		try {
			Map<String, String> options = new HashMap<String, String>();
			 //TODO on dangling erefs, use exception instead
			options.put(XMLResource.OPTION_PROCESS_DANGLING_HREF, "DISCARD");
			slicedMetaModelResource.save(options);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}

