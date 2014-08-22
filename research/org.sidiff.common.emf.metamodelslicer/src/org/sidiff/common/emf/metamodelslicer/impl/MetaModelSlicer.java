package org.sidiff.common.emf.metamodelslicer.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClassifier;
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

/**
 * This class will allow the slicing of a meta-model.
 * Still unfinished.
 * 
 * @author mrindt
 *
 */
public class MetaModelSlicer {

	private Registry ePackageRegistry		 = EPackageRegistryImpl.INSTANCE;
	private final ResourceSet resourceSet	 = new ResourceSetImpl();
	private EPackage origMetaModel 			 = null;
	private EPackage slicedMetaModel		 = null;
	private Resource slicedMetaModelResource = null;
	EClassifierInfoManagement slicedMetaModelInfo = null;
	private Map<EObject,EObject> eObjectMap  = new HashMap<EObject, EObject>();
	
	public void slice(EPackage mainMetaModel, List<EPackage> requiredMetaModels, List<String> mandatoryElementNames, List<EClassifier> excludableElements, String newNS_URI, String outputPath) {
	
		LogUtil.log(LogEvent.NOTICE, "Phase 0: Preparation");
		
		createSlicedMetaModel(mainMetaModel, newNS_URI, outputPath);
			
		// Identify mandatory Classifiers in slicedMetaModel based on names 
		HashSet<EClassifier> mandatoryClassifiers = new HashSet<EClassifier>();
		for(EClassifier eClassifier: slicedMetaModel.getEClassifiers())
				if (mandatoryElementNames.contains(eClassifier.getName()))
					mandatoryClassifiers.add(eClassifier);
		
		sliceMetaModel(slicedMetaModelInfo, mandatoryClassifiers);
	}

	public void slice(EPackage mainMetaModel, List<EPackage> requiredMetaModels, Resource modelInstance, List<EClassifier> excludableElements, String newNS_URI, String outputPath) {
			
			createSlicedMetaModel(mainMetaModel, newNS_URI, outputPath);
				
			// Identify mandatory Classifiers in slicedMetaModel based on instances in model 
			HashSet<EClassifier> mandatoryClassifiers = new HashSet<EClassifier>();
			Iterator<EObject> modelIter = modelInstance.getAllContents(); 
			while(modelIter.hasNext())
			{   
				mandatoryClassifiers.add(slicedMetaModel.getEClassifier(modelIter.next().eClass().getName()));
			}	
			
			sliceMetaModel(slicedMetaModelInfo, mandatoryClassifiers);
			
		}
	
	
	private void createSlicedMetaModel(
			EPackage mainMetaModel, String newNS_URI, String outputPath) {
		
		LogUtil.log(LogEvent.NOTICE, "Phase 0: Preparation");
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
		this.slicedMetaModelInfo = EClassifierInfoManagement.getInstance(false, myStack);
	}

	

	private void sliceMetaModel(EClassifierInfoManagement slicedMetaModelInfo,
			HashSet<EClassifier> mandatoryClassifiers) {
		
		
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
		
		
		LogUtil.log(LogEvent.NOTICE, "Phase 2: Mark Key Classifiers");
		for(EClassifier eClassifier: slicedMetaModel.getEClassifiers()) {
			if(mandatoryClassifiers.contains(eClassifier)) {
				EAnnotation eanno = EcoreFactory.eINSTANCE.createEAnnotation();
				eanno.setSource("SlicerMark");
				eanno.getDetails().put("SlicerMark", "keep");
				eClassifier.getEAnnotations().add(eanno);		
			}
		}
		

		
		LogUtil.log(LogEvent.NOTICE, "Phase 3: Remove Unmarked Classifiers");
		//TODO remove unmarked EEModelelements
		Iterator<EClassifier> it = slicedMetaModel.getEClassifiers().iterator();
		while (it.hasNext()) {
			EClassifier eClassifier = it.next();
			if(!EMFUtil.isAnnotatedWith(eClassifier, "SlicerMark")) {				
				it.remove();
			}
		}
		
		
		
		LogUtil.log(LogEvent.NOTICE, "Phase 4: Cleanup Sliced MetaModel");
		for(EClassifier eClassifier: slicedMetaModel.getEClassifiers()) {
				eClassifier.getEAnnotations().remove(eClassifier.getEAnnotation("SlicerMark"));
				eClassifier.getEAnnotations().remove(eClassifier.getEAnnotation("path4ModelSlice"));
			}
		
		//TODO delete printlns once tested
		System.out.println("Size original metamodel: " + origMetaModel.getEClassifiers().size());
		System.out.println("Size sliced metamodel: " + slicedMetaModel.getEClassifiers().size());
		
		
		LogUtil.log(LogEvent.NOTICE, "Phase 5: Serializing Sliced MetaModel");
		try {
			slicedMetaModelResource.save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}

