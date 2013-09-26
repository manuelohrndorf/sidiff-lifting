package org.sidiff.common.emf.metamodelslicer.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.sidiff.common.emf.EMFUtil;
import org.sidiff.common.emf.extensions.impl.EClassifierInfoManagement;
import org.sidiff.common.emf.extensions.impl.EcoreHelper;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;

public class MetaModelSlicer {

	private Registry ePackageRegistry		 = EPackageRegistryImpl.INSTANCE;
	private final ResourceSet resourceSet	 = new ResourceSetImpl();
	private EPackage origMetaModel 			 = null;
	private EPackage slicedMetaModel		 = null;
	private Resource slicedMetaModelResource = null;
	private Map<EObject,EObject> eObjectMap  = new HashMap<EObject, EObject>();
	
	public void slice(EPackage mainMetaModel, List<EPackage> requiredMetaModels, List<EClassifier> keyElements, List<EClassifier> excludableElements, String newNS_URI, String outputPath) {
	
		origMetaModel = ePackageRegistry.getEPackage(mainMetaModel.getNsURI());
		slicedMetaModelResource = resourceSet.createResource(URI.createFileURI(outputPath));
	
		// create independent copy of the original meta model and mapping between their EObjects
		try {
			eObjectMap = EcoreHelper.createIndependantMetaModelCopy(origMetaModel, slicedMetaModelResource, newNS_URI);
		} catch (Exception e1) {
			e1.printStackTrace();
			System.exit(1);
		}
		slicedMetaModel = (EPackage) slicedMetaModelResource.getContents().get(0);
		
		
		LogUtil.log(LogEvent.NOTICE, "Initialize ClassifierInfoManagement for slicedMetaModel.");
		Stack<EPackage> myStack = new Stack<EPackage>();
		myStack.push(slicedMetaModel);
		EClassifierInfoManagement slicedMetaModelInfo = EClassifierInfoManagement.getInstance(false, myStack);
		
		
		LogUtil.log(LogEvent.NOTICE, "Mark whiteListed Classifiers in meta-model copy to be kept");

		// Identify keyElement Classifiers in slicedMetaModel 
		List<String> keyElementNames = new ArrayList<String>();
		for (EClassifier eClassifier : keyElements)
			keyElementNames.add(eClassifier.getName());
		
		HashSet<EClassifier> mandatoryClassifiers = new HashSet<EClassifier>();
		
		for(EClassifier eClassifier: slicedMetaModel.getEClassifiers())
				if (keyElementNames.contains(eClassifier.getName()))
					mandatoryClassifiers.add(eClassifier);
		

		for(EClassifier eClassifier: slicedMetaModel.getEClassifiers())
			if (eClassifier.eClass().equals(EEnum.class))
				mandatoryClassifiers.add(eClassifier);
		
		
		LogUtil.log(LogEvent.NOTICE, "Phase 1: identify all mandatory classifiers");
		HashSet<EClassifier> newMandatoryClassifiers = new HashSet<EClassifier>();
		newMandatoryClassifiers.addAll(mandatoryClassifiers);
		int currentSize = 0;
		while (currentSize != mandatoryClassifiers.size())
		{			
			currentSize = mandatoryClassifiers.size();
						
			HashSet<EClassifier> currentMandatoryClassifiers = new HashSet<EClassifier>();
			for (EClassifier eClassifier : newMandatoryClassifiers)
					currentMandatoryClassifiers.addAll(slicedMetaModelInfo.getEClassifierInfo(eClassifier).getAllMandatoryClassifiers());

			currentMandatoryClassifiers.removeAll(mandatoryClassifiers);
			currentMandatoryClassifiers.removeAll(newMandatoryClassifiers);
	
			newMandatoryClassifiers.clear();
			newMandatoryClassifiers.addAll(currentMandatoryClassifiers);
			mandatoryClassifiers.addAll(currentMandatoryClassifiers);
			
		}
		
		
		System.out.println("******** Marked Classifiers ********");
		ArrayList<String> markedClassifiers = new ArrayList<String>(); 
		for (EClassifier eClassifier : mandatoryClassifiers)
			 markedClassifiers.add(eClassifier.getName());
		
		
		
		
		LogUtil.log(LogEvent.NOTICE, "Phase 2: mark key Classifiers");
		for(EClassifier eClassifier: slicedMetaModel.getEClassifiers()) {

//TODO this fails *always* because the eClassifiers are copied (new instance) and therefore are not 
//	   contained in keyElements. Funny thing is the enumerations are still reported as marked with slicer. 
			
			
//			if(keyElements.contains(eClassifier)) {	
			if(mandatoryClassifiers.contains(eClassifier)) {
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

		System.out.println("Size original metamodel: " + origMetaModel.getEClassifiers().size());
		System.out.println("Size sliced metamodel: " + slicedMetaModel.getEClassifiers().size());
		
		// serialize sliced meta model:
		LogUtil.log(LogEvent.NOTICE, "Serializing sliced meta-model");
		try {
			slicedMetaModelResource.save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	

	
}

