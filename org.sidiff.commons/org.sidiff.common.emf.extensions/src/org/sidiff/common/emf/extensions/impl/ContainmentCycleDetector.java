package org.sidiff.common.emf.extensions.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;

public class ContainmentCycleDetector {

	private EClassifierInfoManagement ECM = null;
	
	private List<Stack<HashMap<EReference, EClassifier>>> tmpPathList = null;
	
	public ContainmentCycleDetector() {
		
		ECM =  EClassifierInfoManagement.getInstance();
		tmpPathList =  new ArrayList<Stack<HashMap<EReference,EClassifier>>>();
	}
	
	/**
	 * This method detects containment cycles.
	 * Note: It indirectly accesses the EClassInfoManagement for quering sub types.
	 * Thus, EClassInfoManagement should have been initialized already.
	 * @param ePackagesStack
	 * @return
	 */
	public ContainmentCycle detectContainmentCycles(Stack<EPackage> ePackagesStack) {
	
		for (EPackage ePackage : ePackagesStack) {
			for (EClassifier eClassifier : ePackage.getEClassifiers()) {	

				if(eClassifier instanceof EClass) {
					EClass eClass = (EClass) eClassifier;				
					findCycles(eClass);				
				}
			}
			
		}	
		return null;
	}

	/**
	 * This method tries to find containment cycles for a given EClass.
	 * If some are found, the paths are stored in the corresponding EClassifierInfos.
	 * @param eClass
	 */
	private void findCycles(EClass eClass) {
		
		tmpPathList.clear();
		
		for(EReference eRef: eClass.getEReferences()) {
			if(eRef.isContainment()) {
				EClass target = eRef.getEReferenceType();
				
				//Path for direct target
				Stack<HashMap<EReference,EClassifier>> path = new Stack<HashMap<EReference,EClassifier>>();
				HashMap<EReference,EClassifier> pDirectPair = new HashMap<EReference, EClassifier>();
				pDirectPair.put(eRef, target);
				path.push(pDirectPair);
				tmpPathList.add(path);
				findNextStep(path);

				// Paths for subtypes if any
				Set<EClassifier> subTypes = ECM.getAllSubTypes(target);				
				for(EClassifier subType: subTypes) {
					
					Stack<HashMap<EReference,EClassifier>> pathForSubtype = new Stack<HashMap<EReference,EClassifier>>();
					HashMap<EReference,EClassifier> pSubPair = new HashMap<EReference, EClassifier>();
					pSubPair.put(eRef, subType);
					pathForSubtype.push(pSubPair);
					tmpPathList.add(pathForSubtype);
					findNextStep(pathForSubtype);
					
				}
			}			
		}
	}
	
	/**
	 * Starting from the last entry in the path stack, this method considers
	 * every further step and checks if a containment cycle is present.
	 * If so, the paths are stored in the corresponding EClassifierInfos.
	 * This method also considers EReferences of sub types inside a path.
	 * A sub type results in a new path.
	 * @param currentPath
	 */
	private void findNextStep(Stack<HashMap<EReference, EClassifier>> currentPath) {
		
		HashMap<EReference,EClassifier> lastEntry = currentPath.lastElement();

		EClassifier  eClassifier = lastEntry.values().iterator().next();

		for(EReference containedERef: ((EClass)eClassifier).getEReferences()) {
			if(containedERef.isContainment()) {
				
				// target already contained in path --> containment cycle
				if(checkTargetIsContainedInPath(currentPath, eClassifier)) {
					
					// add last step where target equals the container of the first
					// EReference in the path.
					HashMap<EReference,EClassifier> step = new HashMap<EReference, EClassifier>();
					step.put(containedERef, eClassifier);
					currentPath.push(step);
					
					// create ContainmentCycle object and link it to the EClassifierInfo
					// for the affected EClassifier
					ContainmentCycle cc = new ContainmentCycle(currentPath);
					ECM.getEClassifierInfo(eClassifier).addContainmentCycle(cc);
					
					logCC(currentPath);

				}
				// otherwise no cycle --> keep adding steps
				else{

					EClass target = containedERef.getEReferenceType();

					// push direct target + reference
					HashMap<EReference,EClassifier> step = new HashMap<EReference, EClassifier>();
					step.put(containedERef, target);
					currentPath.push(step);
					findNextStep(currentPath);

					// create new paths for subtypes if any
					Set<EClassifier> subTypes = ECM.getAllSubTypes(target);				
					for(EClassifier subType: subTypes) {
						
						// subtype already contained in Path --> containment cycle
						if(checkTargetIsContainedInPath(currentPath, subType)) {
							// create ContainmentCycle object and link it to the EClassifierInfo
							// for the affected EClassifier
							ContainmentCycle cc = new ContainmentCycle(currentPath);
							ECM.getEClassifierInfo(subType).addContainmentCycle(cc);
							
							logCC(currentPath);
							
						}
						// otherwise no cycle --> keep adding steps
						else{	
							Stack<HashMap<EReference,EClassifier>> pathForSubtype = new Stack<HashMap<EReference,EClassifier>>();
							HashMap<EReference,EClassifier> stepForSubtype = new HashMap<EReference, EClassifier>();
							stepForSubtype.put(containedERef, subType);
							pathForSubtype.push(stepForSubtype);
							tmpPathList.add(pathForSubtype);
							findNextStep(pathForSubtype);
						}
					}							
				}			
			}	
		}
	}
	
	private boolean checkTargetIsContainedInPath(Stack<HashMap<EReference, EClassifier>> currentPath, EClassifier eClassifier) {
		boolean contained = false;
		
		for(HashMap<EReference,EClassifier> entry: currentPath) {
			if(entry.values().contains(eClassifier)) {
				contained = true;
				break;
			}
		}
		return contained;
	}
	
	/**
	 * Logs and prints out the paths of a containmnet cycle
	 * @param path
	 */
	private void logCC(Stack<HashMap<EReference,EClassifier>> path ) {
		
		String message = "Found Containment-Cycle: ";
		
		for(HashMap<EReference,EClassifier> step: path) {
			EClassifier eClassifier = (EClassifier) step.values().iterator().next();
			EReference eReference = (EReference) step.keySet().iterator().next();
			message +=" > " + "(" + eReference.getName() +")" + eClassifier.getName();
		}
		
		LogUtil.log(LogEvent.NOTICE, message);
	}
}
