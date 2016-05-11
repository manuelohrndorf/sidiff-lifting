package org.sidiff.editrule.generator.serge.metamodelanalysis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.impl.EStringToStringMapEntryImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.sidiff.common.emf.EMFUtil;

public class EcoreHelper {
	
	private static String EANNO_KEY_PATH = "EANNO_KEY_PATH";
	
	public static List<EAnnotation> getAllEAnnotations(EPackage rootpackage) {
		
		List<EAnnotation> annoList = new ArrayList<EAnnotation>();
		
		Iterator<EObject> eoIt = EMFUtil.getEAllContentAsIterable(rootpackage).iterator();
		while(eoIt.hasNext()) {
			EObject eo = eoIt.next();
			
			if(eo instanceof EAnnotation) {
				EAnnotation eanno = (EAnnotation) eo;
				annoList.add(eanno);
			}	
		}	
		return annoList;
	}
	
	
	public static String getEObjectPositionEANNO_KEY_PATH(EObject eob) {
		
		String EANNO_KEY_PATHFragment = "";
		
		if(eob instanceof EAnnotation) {
			EANNO_KEY_PATHFragment = ((EAnnotation) eob).getSource();
		}
		
		else if( eob instanceof ENamedElement) {
			EPackage runtimeEcoreModel = EcoreFactory.eINSTANCE.getEcorePackage();
			EClass eClass = (EClass) runtimeEcoreModel.getEClassifier("EClass");
			EStructuralFeature feature = eClass.getEStructuralFeature("name");
			EANNO_KEY_PATHFragment = (String) eob.eGet(feature);
		}

		if(eob.eContainer()!=null) {
			EANNO_KEY_PATHFragment = getEObjectPositionEANNO_KEY_PATH(eob.eContainer()) + "/" +EANNO_KEY_PATHFragment;
		}
		
		return EANNO_KEY_PATHFragment;
	}
	
	/**
	 * Checks if a given EReference is inherited
	 * @param the EReference
	 * @param concerningEClassifier is the class to check on
	 * @return true if inherited
	 */
	public static boolean isInheritedReference(EReference eRef, EClassifier concerningEClassifier) {
		
		if(concerningEClassifier instanceof EClass) {
			EClass eClass = (EClass) concerningEClassifier;
			return !eClass.getEReferences().contains(eRef);
		}
		
		return true;
	}

	public static Map<EObject,EObject> createIndependantMetaModelCopy(EPackage mainMetaModel, Resource newResourceToContainCopy, String newNS_URI) throws Exception {
				
		//TODO Copy eAnntoations (except EANNO_KEY_PATH annotation), too, since Copier ignores them
		//TODO not all EGeneric types are copied
		//TODO not all EStringToSTringMapEntries are copied
		Map<EObject,EObject> eObjectMap  = new HashMap<EObject, EObject>();
		
		
		// use Ecore-Copier to copy the most parts of the meta model
		Copier copier = new Copier(false, true);
		Collection<EPackage> copy = copier.copyAll(Collections.singleton(mainMetaModel));
		newResourceToContainCopy.getContents().addAll(copy);
		copier.copyReferences();
		EcoreUtil.resolveAll(newResourceToContainCopy);		
		
		// gather all EAnnotations in original source, to copy them later to the sliced Meta Model
		// (because Ecore-Copier ignores EAnnotations)
		List<EAnnotation> eannos_Orig = getAllEAnnotations(mainMetaModel);

		// annotate all elements with their EANNO_KEY_PATH in original meta model
		for(EObject eob_Orig: EMFUtil.getEAllContentAsIterable(mainMetaModel)) {
			if(eob_Orig instanceof EGenericType
					|| eob_Orig instanceof EStringToStringMapEntryImpl) {
				continue;
			}	
			
			EAnnotation eanno = EcoreFactory.eINSTANCE.createEAnnotation();
			eanno.setSource(EANNO_KEY_PATH);
			eanno.getDetails().put(EANNO_KEY_PATH, getEObjectPositionEANNO_KEY_PATH(eob_Orig));
			((EModelElement) eob_Orig).getEAnnotations().add(eanno);
		}

		// annotate all elements with their EANNO_KEY_PATH in sliced meta model
		for(EObject eob_Slice: EMFUtil.getAllContentAsIterable(newResourceToContainCopy)) {
			if(eob_Slice instanceof EGenericType
					|| eob_Slice instanceof EStringToStringMapEntryImpl
					|| eob_Slice instanceof EAnnotation) {
				continue;
			}
			EAnnotation eanno = EcoreFactory.eINSTANCE.createEAnnotation();
			eanno.setSource(EANNO_KEY_PATH);
			eanno.getDetails().put(EANNO_KEY_PATH, getEObjectPositionEANNO_KEY_PATH(eob_Slice));
			((EModelElement) eob_Slice).getEAnnotations().add(eanno);
		}
		
		// Map EObjects of both Resources 
		for(EObject eob_Orig: EMFUtil.getEAllContentAsIterable(mainMetaModel)) { 
			if( eob_Orig instanceof EGenericType 
					|| eob_Orig instanceof EStringToStringMapEntryImpl) {
				continue;
			}
	
			for(EObject eob_Slice: EMFUtil.getAllContentAsIterable(newResourceToContainCopy)) { 
				if( eob_Slice instanceof EGenericType 
						|| eob_Slice instanceof EStringToStringMapEntryImpl
						|| eob_Slice instanceof EAnnotation) {
					continue;
				}
								
				EAnnotation eanno_orig = ((EModelElement) eob_Orig).getEAnnotation(EANNO_KEY_PATH);
				String EANNO_KEY_PATHValue_orig = null;
				if(eanno_orig!=null) {
					EANNO_KEY_PATHValue_orig = eanno_orig.getDetails().get(EANNO_KEY_PATH);
				}
				EAnnotation eanno_slice = ((EModelElement) eob_Slice).getEAnnotation(EANNO_KEY_PATH);				
				String EANNO_KEY_PATHValue_slice = null;
				if(eanno_slice!=null) {
					EANNO_KEY_PATHValue_slice = eanno_slice.getDetails().get(EANNO_KEY_PATH);
				}

				// if EANNO_KEY_PATHs are equal, map eobs
				if((EANNO_KEY_PATHValue_orig!=null && EANNO_KEY_PATHValue_slice!=null) && (EANNO_KEY_PATHValue_orig.equals(EANNO_KEY_PATHValue_slice))) {
					eObjectMap.put(eob_Orig, eob_Slice);
				}				
			}			
		}

		// Inserting original EAnnotations and internal content into the copy meta model
		for(EAnnotation eanno_Orig: eannos_Orig) {			
			copyEAnnotationAndInternalContents(eanno_Orig, eObjectMap);
		}		
		
		assert(EcoreUtil.UnresolvedProxyCrossReferencer.find(newResourceToContainCopy).isEmpty()) : "There are still some unresolved proxies";

		EPackage copyMetaModel = (EPackage) newResourceToContainCopy.getContents().get(0);
		copyMetaModel.setNsURI(newNS_URI);
		

		// remove EANNO_KEY_PATH annotation		
		removeAnnotation(EANNO_KEY_PATH, copyMetaModel);
		
		return eObjectMap;
	}
	
	private static void removeAnnotation(String source, EObject eObjectOrEPackage) {
		
		Iterator<EObject> elemIter = eObjectOrEPackage.eAllContents();
		while(elemIter.hasNext()) {
			EObject eObject = elemIter.next();
			if(eObject instanceof EAnnotation) {
				EAnnotation eAnno = (EAnnotation) eObject;
				if(eAnno.getSource().equals(source)) {
					EcoreUtil.remove(eAnno);
				}
			}			
		}
		
	}

	private static void copyEAnnotationAndInternalContents(EAnnotation eanno_Orig, Map<EObject,EObject> eObjectMap) throws Exception {

		EAnnotation eanno_Slice = null;
		
		EObject annoContainer_Orig = eanno_Orig.eContainer();
		EObject annoContainer_Slice = eObjectMap.get(annoContainer_Orig);
		if(annoContainer_Slice!=null) {
			
			eanno_Slice = EcoreFactory.eINSTANCE.createEAnnotation();
			eanno_Slice.setSource(eanno_Orig.getSource());
			
			// add contents of original EAnnotation to copy
			// Contents can e.g. be EAttributs. EAnnotation-Details will be handled below.
			for(EObject contentEObject: eanno_Orig.getContents()) {
				
				if(contentEObject instanceof EAttribute) {
					// create new EAtttribute and set all Properties like in the original
					EAttribute origEAttrib = (EAttribute) contentEObject;
					EAttribute newEAttrib = EcoreFactory.eINSTANCE.createEAttribute();
					newEAttrib.setDerived(origEAttrib.isDerived());
					newEAttrib.setChangeable(origEAttrib.isChangeable());
					newEAttrib.setEType(origEAttrib.getEType());
					newEAttrib.setDefaultValue(origEAttrib.getDefaultValue());
					newEAttrib.setDefaultValueLiteral(origEAttrib.getDefaultValueLiteral());
					newEAttrib.setEGenericType(origEAttrib.getEGenericType());
					newEAttrib.setID(origEAttrib.isID());
					newEAttrib.setLowerBound(origEAttrib.getLowerBound());
					newEAttrib.setName(origEAttrib.getName());
					newEAttrib.setOrdered(origEAttrib.isOrdered());
					newEAttrib.setTransient(origEAttrib.isTransient());
					newEAttrib.setUnique(origEAttrib.isUnique());
					newEAttrib.setUnsettable(origEAttrib.isUnsettable());
					newEAttrib.setUpperBound(origEAttrib.getUpperBound());
					newEAttrib.setVolatile(origEAttrib.isVolatile());
					
					// add "copied" (new) EAnnotation to the copied meta-model
					eanno_Slice.getContents().add(newEAttrib);
					
					// copy sub EAnnotations of the original EAttribute
					for(EAnnotation subEAnno: origEAttrib.getEAnnotations()) {
						copyEAnnotationAndInternalContents(subEAnno, eObjectMap);
					}
					
				}
				else if(contentEObject instanceof EReference) {
					// create new EReference and set all Properties like in the original
					EReference origERef = (EReference) contentEObject;
					EReference newERef = EcoreFactory.eINSTANCE.createEReference();
					newERef.setDerived(origERef.isDerived());
					newERef.setChangeable(origERef.isChangeable());
					newERef.setEType(origERef.getEType());
					newERef.setDefaultValueLiteral(origERef.getDefaultValueLiteral());
					if(newERef.getDefaultValueLiteral()!=null) {
						newERef.setDefaultValue(origERef.getDefaultValue());
					}
					newERef.setEGenericType(origERef.getEGenericType());
					newERef.setLowerBound(origERef.getLowerBound());
					newERef.setName(origERef.getName());
					newERef.setOrdered(origERef.isOrdered());
					newERef.setTransient(origERef.isTransient());
					newERef.setUnique(origERef.isUnique());
					newERef.setUnsettable(origERef.isUnsettable());
					newERef.setUpperBound(origERef.getUpperBound());
					newERef.setVolatile(origERef.isVolatile());
					newERef.setContainment(origERef.isContainment());
					newERef.setEOpposite(origERef.getEOpposite());
					newERef.setResolveProxies(origERef.isResolveProxies());	
					
					// add "copied" (new) EAnnotation to the copied meta-model
					eanno_Slice.getContents().add(newERef);
					
					// copy sub EAnnotations of the original EReference
					for(EAnnotation subEAnno: origERef.getEAnnotations()) {
						copyEAnnotationAndInternalContents(subEAnno, eObjectMap);
					}
				}
				else if(contentEObject instanceof EOperation){
					// create new EOperation and set all Properties like in the original
					EOperation origEOp = (EOperation) contentEObject;
					EOperation newEOP = EcoreFactory.eINSTANCE.createEOperation();
					newEOP.setEGenericType(origEOp.getEGenericType());
					newEOP.setEType(origEOp.getEType());
					newEOP.setLowerBound(origEOp.getLowerBound());
					newEOP.setName(origEOp.getName());
					newEOP.setOrdered(origEOp.isOrdered());
					newEOP.setUnique(origEOp.isUnique());
					newEOP.setUpperBound(origEOp.getUpperBound());
					
					// add "copied" (new) EAnnotation to the copied meta-model
					eanno_Slice.getContents().add(newEOP);
					
					// copy sub EAnnotations of the original EOperation
					for(EAnnotation subEAnno: origEOp.getEAnnotations()) {
						copyEAnnotationAndInternalContents(subEAnno, eObjectMap);
					}
				}
				else if(contentEObject instanceof EParameter) {
					// create new EParameter and set all Properties like in the original
					EParameter origEParam = (EParameter) contentEObject;
					EParameter newEParam = EcoreFactory.eINSTANCE.createEParameter();
					newEParam.setEGenericType(origEParam.getEGenericType());
					newEParam.setEType(origEParam.getEType());
					newEParam.setLowerBound(origEParam.getLowerBound());
					newEParam.setName(origEParam.getName());
					newEParam.setOrdered(origEParam.isOrdered());
					newEParam.setUnique(origEParam.isUnique());
					newEParam.setUpperBound(origEParam.getUpperBound());
					
					// add "copied" (new) EAnnotation to the copied meta-model
					eanno_Slice.getContents().add(newEParam);
					
					// copy sub EAnnotations of the original EParameter
					for(EAnnotation subEAnno: origEParam.getEAnnotations()) {
						copyEAnnotationAndInternalContents(subEAnno, eObjectMap);
					}
					
				}
				else if(contentEObject instanceof EEnum) {
					// create new EEnum and set all Properties like in the original
					EEnum origEEnum = (EEnum) contentEObject;
					EEnum newEEnum = EcoreFactory.eINSTANCE.createEEnum();
					newEEnum.setName(origEEnum.getName());
					newEEnum.setInstanceClass(origEEnum.getInstanceClass());				//?
					newEEnum.setInstanceClassName(origEEnum.getInstanceClassName());		//?
					newEEnum.setSerializable(origEEnum.isSerializable());					//?
					
					// add "copied" (new) EAnnotation to the copied meta-model
					eanno_Slice.getContents().add(newEEnum);
					
					// copy sub EAnnotations of the original EEnum
					for(EAnnotation subEAnno: origEEnum.getEAnnotations()) {
						copyEAnnotationAndInternalContents(subEAnno, eObjectMap);
					}
				}
				else if(contentEObject instanceof EDataType) {
					// create new EDataType and set all Properties like in the original
					EDataType origEDatatype = (EDataType) contentEObject;
					EDataType newEDatatype = EcoreFactory.eINSTANCE.createEDataType();
					newEDatatype.setName(origEDatatype.getName());
					newEDatatype.setInstanceClass(origEDatatype.getInstanceClass());		//?
					newEDatatype.setInstanceClassName(origEDatatype.getInstanceClassName());//?
					newEDatatype.setSerializable(origEDatatype.isSerializable());			//?
					
					// add "copied" (new) EAnnotation to the copied meta-model
					eanno_Slice.getContents().add(newEDatatype);
					
					// copy sub EAnnotations of the original EDataType
					for(EAnnotation subEAnno: origEDatatype.getEAnnotations()) {
						copyEAnnotationAndInternalContents(subEAnno, eObjectMap);
					}
				}
				else{
					throw new Exception("Case not covered: "+ contentEObject.eClass().getName()+ " contained in an EAnnotation.");
				}

			}
			
			// copy details of original EAnnotation
			for(Entry<String, String> detailEntry_Orig: eanno_Orig.getDetails().entrySet()) {
				eanno_Slice.getDetails().put(detailEntry_Orig.getKey(), detailEntry_Orig.getValue());//real copy?
			}
			
			// add EAnnotation to copy of meta-model
			eanno_Slice.getEAnnotations().add(eanno_Slice);
			
			
		}
		
	}
	
}
