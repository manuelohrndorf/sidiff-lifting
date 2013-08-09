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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.impl.ENamedElementImpl;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
import org.eclipse.emf.ecore.impl.EStringToStringMapEntryImpl;
import org.eclipse.emf.ecore.impl.EStructuralFeatureImpl;
import org.eclipse.emf.ecore.impl.EcoreFactoryImpl;
import org.eclipse.emf.ecore.impl.EcorePackageImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.query.conditions.eobjects.structuralfeatures.EStructuralFeatureValueGetter;
import org.sidiff.common.emf.EMFAdapter;
import org.sidiff.common.emf.EMFUtil;
import org.sidiff.common.emf.access.EMFMetaAccess;
import org.sidiff.common.emf.access.path.EMFPath;
import org.sidiff.common.emf.annotation.AnnotateableElement;
import org.sidiff.common.emf.annotation.AnnotationKeyList;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.core.annotation.Annotator;
import org.sidiff.core.annotators.TypePathAnnotator;

public class MetaModelSlicer {

	private Registry ePackageRegistry		 = EPackageRegistryImpl.INSTANCE;
	private final ResourceSet resourceSet	 = new ResourceSetImpl();
	private EPackage origMetaModel 			 = null;
	private EPackage slicedMetaModel		 = null;
	private Resource slicedMetaModelResource = null;
	private Map<EObject,EObject> eObjectMap  = new HashMap<EObject, EObject>();
	private EClassInfoManagement ecm 		 = EClassInfoManagement.getInstance(false);
	
	public void slice(EPackage mainMetaModel, List<EPackage> requiredMetaModels, List<EClass> keyElements, List<EClass> excludableElements, String newNS_URI) {
	
		String testingOutputPath = "/media/mrindt/data/Workspaces/Linux/SERx/RESULTS/sliced.ecore";

		origMetaModel = ePackageRegistry.getEPackage(mainMetaModel.getNsURI());
		slicedMetaModelResource = resourceSet.createResource(URI.createFileURI(testingOutputPath));
	
		// create independent copy of the original meta model
		createIndependantMetaModelCopy(newNS_URI);
					

		LogUtil.log(LogEvent.NOTICE, "Mark whiteListed Classifiers in meta-model copy to be kept");
		
		//temp workaround, since whitelist only contains EClasses, not EClassifiers
		List<EClassifier> keyElementsWorkaround = new ArrayList<EClassifier>();
		for(EClass eClass: keyElements) {
			EClassifier correspondingMapClassifier = (EClassifier) eObjectMap.get((EObject)eClass);
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
		
		 
		// Remove unmarked eClassifiers //TODO remove unmarked EEModelelements
		LogUtil.log(LogEvent.NOTICE, "Slice copy of meta-model by removing unmarked Classifiers.");
		Iterator<EClassifier> it = slicedMetaModel.getEClassifiers().iterator();
		while (it.hasNext()) {
			EClassifier eClassifier = it.next();
			
			if(!EMFUtil.isAnnotatedWith(eClassifier, "SlicerMark")) {				
				it.remove();
			}
		}
		
		//TODO remove slicer mark annotation and path annotations
		
		// serialize sliced meta model:
		LogUtil.log(LogEvent.NOTICE, "Serializing sliced meta-model");
		try {
			Map<String, String> options = new HashMap<String, String>();
			 //TODO on dangling erefs, use exception instead
			options.put(XMLResource.OPTION_PROCESS_DANGLING_HREF, "DISCARD");
			slicedMetaModelResource.save(options);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private String getEObjectPositionPath(EObject eob) {
		
		String pathFragment = "";
		
		if(eob instanceof EAnnotation) {
			pathFragment = ((EAnnotation) eob).getSource();
		}
		
		else if( eob instanceof ENamedElement) {
			EPackage runtimeEcoreModel = EcoreFactory.eINSTANCE.getEcorePackage();
			EClass eClass = (EClass) runtimeEcoreModel.getEClassifier("EClass");
			EStructuralFeature feature = eClass.getEStructuralFeature("name");
			pathFragment = (String) eob.eGet(feature);
		}

		if(eob.eContainer()!=null) {
			pathFragment = getEObjectPositionPath(eob.eContainer()) + "/" +pathFragment;
		}
		
		return pathFragment;
	}
	
	private void createIndependantMetaModelCopy(String newNS_URI) {
		
		LogUtil.log(LogEvent.NOTICE, "Creating an identical, independent meta-model copy");
		//TODO Copy eAnntoations (except path annotation), too, since Copier ignores them
		
		// make identical, independent copy of originalMetaModel
		Copier copier = new Copier(false, false);
		Collection<EPackage> copy = copier.copyAll(Collections.singleton(origMetaModel));
		slicedMetaModelResource.getContents().addAll(copy);
		copier.copyReferences();
		EcoreUtil.resolveAll(slicedMetaModelResource);

		/**************************************************************************************************************/
		
		LogUtil.log(LogEvent.NOTICE, "Annotating EEModelelement paths.");
		
		// annotate all elements with their path in original meta model
		for(EObject eob_Orig: EMFUtil.getEAllContentAsIterable(origMetaModel)) {
			if(eob_Orig instanceof EGenericType
					|| eob_Orig instanceof EStringToStringMapEntryImpl
					|| eob_Orig instanceof EAnnotation) {
				continue;
			}			
			EAnnotation eanno = EcoreFactory.eINSTANCE.createEAnnotation();
			eanno.setSource("path4ModelSlice");
			eanno.getDetails().put("path4ModelSlice", getEObjectPositionPath(eob_Orig));
			((EModelElement) eob_Orig).getEAnnotations().add(eanno);
		}

		// annotate all elements with their path in sliced meta model
		for(EObject eob_Slice: EMFUtil.getAllContentAsIterable(slicedMetaModelResource)) {
			if(eob_Slice instanceof EGenericType
					|| eob_Slice instanceof EStringToStringMapEntryImpl
					|| eob_Slice instanceof EAnnotation) {
				continue;
			}
			EAnnotation eanno = EcoreFactory.eINSTANCE.createEAnnotation();
			eanno.setSource("path4ModelSlice");
			eanno.getDetails().put("path4ModelSlice", getEObjectPositionPath(eob_Slice));
			((EModelElement) eob_Slice).getEAnnotations().add(eanno);
		}

		/************************************************************************************************************/
		LogUtil.log(LogEvent.NOTICE, "Mapping elements between original meta-model and copied meta-model. This takes some seconds...");
		
		// Map EObjects of both Resources 
		for(EObject eob_Orig: EMFUtil.getEAllContentAsIterable(origMetaModel)) { 
			if( eob_Orig instanceof EGenericType 
					|| eob_Orig instanceof EStringToStringMapEntryImpl
					|| eob_Orig instanceof EAnnotation) {
				continue;
			}
	
			for(EObject eob_Slice: EMFUtil.getAllContentAsIterable(slicedMetaModelResource)) { 
				if( eob_Slice instanceof EGenericType 
						|| eob_Slice instanceof EStringToStringMapEntryImpl
						|| eob_Slice instanceof EAnnotation) {
					continue;
				}						

				EAnnotation eanno_orig = ((EModelElement) eob_Orig).getEAnnotation("path4ModelSlice");
				String pathValue_orig = eanno_orig.getDetails().get("path4ModelSlice");
				EAnnotation eanno_slice = ((EModelElement) eob_Slice).getEAnnotation("path4ModelSlice");
				String pathValue_slice = null;
				if(eanno_slice!=null) {
					pathValue_slice = eanno_slice.getDetails().get("path4ModelSlice");
				}

				// if paths are equal, map eobs
				if(pathValue_slice!=null && (pathValue_orig.equals(pathValue_slice))) {
					eObjectMap.put(eob_Orig, eob_Slice);
				}				
			}			
		}


		assert(EcoreUtil.UnresolvedProxyCrossReferencer.find(slicedMetaModelResource).isEmpty()) : "There are still some unresolved proxies";

		slicedMetaModel = (EPackage) slicedMetaModelResource.getContents().get(0);
		slicedMetaModel.setNsURI(newNS_URI);

	}
	
}

