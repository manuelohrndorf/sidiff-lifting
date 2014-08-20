package org.sidiff.common.emf.extensions.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;

/**
 * This class provides the detection of cycles in given EPackages.
 * For each containment cycle the involved containment path
 * is stored in each EClassifierInfo of the involved EClasses.
 * The path will represent either an "outer" containment cycles, where the
 * last entry in the path points back to the beginning
 * or and "inner" containment cycle, where the last entry in the
 * path points back to a position in the path other than the beginning.
 * The ContainmentCyleDetector also considers sub types of each EClass.
 * @author mrindt
 *
 */
public class ContainmentCycleDetector {

	/**
	 * Instance of EClassifierInfoManagement for sub type access.
	 */
	private EClassifierInfoManagement ECM = null;
	
	/**
	 * Temporary path, which will be cleared after each considered EClass
	 * inside an EPackage
	 */
	private List<Stack<HashMap<EReference, EClassifier>>> tmpPathList = null;
	
	/**
	 * Constructor
	 */
	public ContainmentCycleDetector() {
		
		ECM =  EClassifierInfoManagement.getInstance();
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
		
		
		for(EReference eRef: eClass.getEAllReferences()) {
			if(eRef.isContainment()) {
				EClass target = eRef.getEReferenceType();
				
				// Build up path, starting from eClass as orgin (reference=null to indicate origin)
				Stack<HashMap<EReference,EClassifier>> path = new Stack<HashMap<EReference,EClassifier>>();
				HashMap<EReference,EClassifier> originPair = new HashMap<EReference, EClassifier>();
				originPair.put(null,eClass); // the origin (eClass)
				path.push(originPair);
				
				if(checkTargetIsOriginOfPath(path, target)) {
								
					ContainmentCycle cc = createAndStoreContainmentCycle(path, eRef, target, false);										
					logCC(cc);		
				}
				else{
					//******* Continue path for target of EReference ********************************/
					
					// backup the path, which was established up to this point
					Stack<HashMap<EReference,EClassifier>> backedUpPath = new Stack<HashMap<EReference,EClassifier>>();
					backedUpPath.addAll(path);
					
					//Continue on path: create new step for the direct target, not its indirect subtypes yet
					HashMap<EReference,EClassifier> pDirectPair = new HashMap<EReference, EClassifier>();
					pDirectPair.put(eRef, target);
					path.push(pDirectPair);

					// Continue finding next steps along this path
					findNextStep(path);
	
					//******* Copied path for each sub type of target of EReference ********************/
					Set<EClassifier> subTypes = ECM.getAllSubTypes(target);				
					for(EClassifier subType: subTypes) {
						
						// Use the previously backed up path and create a copy for each subtype to continue on
						Stack<HashMap<EReference,EClassifier>> copyOfBackedUpPath = new Stack<HashMap<EReference,EClassifier>>();
						copyOfBackedUpPath.addAll(backedUpPath);
	
						//Continue this path, starting with a sub type of the target type of the eRef (indirect target)
						HashMap<EReference,EClassifier> pSubPair = new HashMap<EReference, EClassifier>();
						pSubPair.put(eRef, subType);
						copyOfBackedUpPath.push(pSubPair);
						
						// Continue finding next step along this path
						findNextStep(copyOfBackedUpPath);
						
					}
				}
			}			
		}
	}
	
	/**
	 * Starting from the last entry in the path stack, this method considers
	 * every further step and checks if a containment cycle or inner containment circle is present.
	 * If so, the paths are stored in the corresponding EClassifierInfos.
	 * This method also considers EReferences of sub types inside a path.
	 * A sub type results in a new path.
	 * @param currentPath
	 */
	private void findNextStep(Stack<HashMap<EReference, EClassifier>> currentPath) {
		
		// back up current path
		Stack<HashMap<EReference, EClassifier>> backedUpPath = new Stack<HashMap<EReference, EClassifier>>();
		backedUpPath.addAll(currentPath);
		
		// get last step
		HashMap<EReference,EClassifier> lastEntry = currentPath.lastElement();
		EClassifier  eClassifier = lastEntry.values().iterator().next();

		// iterate over  eAllReferences of the last saved eClassifier target in last step
		Iterator<EReference> refIterator = ((EClass)eClassifier).getEAllReferences().iterator();
		if(refIterator.hasNext()) {
			
			// for the first EReference create a step along the input path
			EReference firstRef = refIterator.next();				
			EClassifier targetOfFirstRef = firstRef.getEReferenceType(); 
			checkAndStoreCycles(currentPath, firstRef, targetOfFirstRef);
			
			// for each following EReference create an individual path with the backed up path as basis
			while(refIterator.hasNext()) {				
				Stack<HashMap<EReference, EClassifier>> copyOfInitialPath = new Stack<HashMap<EReference, EClassifier>>();
				copyOfInitialPath.addAll(backedUpPath);
				
				EReference nextRef = refIterator.next();
				if(nextRef.isContainment()) {	
					EClassifier targetOfNextRef = nextRef.getEReferenceType(); 
					checkAndStoreCycles(copyOfInitialPath, nextRef, targetOfNextRef);		
				}
			}	
		}
	}
	
	
	
	private void checkAndStoreCycles(Stack<HashMap<EReference, EClassifier>> currentPath, EReference eRef, EClassifier eClassifier) {
		
		// ** on containment cycle (target is origin of Path) **********************************************/
		if(checkTargetIsOriginOfPath(currentPath, eClassifier)) {			
			ContainmentCycle cc = createAndStoreContainmentCycle(currentPath, eRef, eClassifier, false);					
			logCC(cc);
		}
		// ** on inner containment cycle ****************************************************************/
		else if(checkInnerCircle(currentPath, eClassifier)) {						
			ContainmentCycle cc = createAndStoreContainmentCycle(currentPath, eRef, eClassifier, true);			
			logCC(cc);			
		}		
		// ** otherwise no cycle --> keep adding steps *****************************************************/
		else{
			// keep the currentPath for later usage of sub types
			Stack<HashMap<EReference, EClassifier>> backedUpPath = new Stack<HashMap<EReference, EClassifier>>();
			backedUpPath.addAll(currentPath);

			// create next step (eRef, eClassifier)
			HashMap<EReference,EClassifier> step = new HashMap<EReference, EClassifier>();
			step.put(eRef, eClassifier);
			currentPath.push(step);
			findNextStep(currentPath);

			//******* Copied path for each sub type of eClassifier target of EReference ********************/
			Set<EClassifier> subTypes = ECM.getAllSubTypes(eClassifier);				
			for(EClassifier subType: subTypes) {
				
				// Use the previously backed up path and create a copy for each subtype to continue on
				Stack<HashMap<EReference,EClassifier>> copyOfBackedUpPath = new Stack<HashMap<EReference,EClassifier>>();
				copyOfBackedUpPath.addAll(backedUpPath);

				//Continue this path, starting with a sub type of the target type of the eRef (indirect target)
				HashMap<EReference,EClassifier> pSubPair = new HashMap<EReference, EClassifier>();
				pSubPair.put(eRef, subType);
				copyOfBackedUpPath.push(pSubPair);
				
				// Continue finding next step along this path
				findNextStep(copyOfBackedUpPath);
				
			}
		}		
	}
	
	
	/**
	 * This method checks if a given eClassifier is the origin of a path or the super type of the origin.
	 * If so, this inclines a containment cycle.
	 * @param currentPath
	 * @param eClassifier
	 * @return
	 */
	private boolean checkTargetIsOriginOfPath(Stack<HashMap<EReference, EClassifier>> currentPath, EClassifier eClassifier) {
		
		EClass origin = (EClass) currentPath.firstElement().entrySet().iterator().next().getValue();
		EClass target = (EClass) eClassifier;
		
		return ( (target.equals(origin))  ||  (origin.getEAllSuperTypes().contains(target)) );	
	}
	
	/**
	 * This method checks if a given eClassifier or its sub type is already contained in the path (not concerning the origin).
	 * If so, this inclines an inner containment cycle (i.e. an inner circle),
	 * @param currentPath
	 * @param eClassifier
	 * @return
	 */
	private boolean checkInnerCircle(Stack<HashMap<EReference, EClassifier>> currentPath, EClassifier eClassifier) {
		
		boolean alreadyContained = false;
		
		EClass target = (EClass) eClassifier;
		
		Iterator<HashMap<EReference,EClassifier>> stepIt = currentPath.iterator();
		stepIt.next(); //skip first (bottom) entry which indicates the origin.
		while(stepIt.hasNext()) {
			HashMap<EReference,EClassifier> step = stepIt.next();
			EClass stepTarget = (EClass) step.values().iterator().next();
			
			if(target.equals(stepTarget) || stepTarget.getEAllSuperTypes().contains(target)) {
				alreadyContained = true;
				break;
			}
		}
		
		return alreadyContained;	
	}
	
	
	private ContainmentCycle createAndStoreContainmentCycle(Stack<HashMap<EReference, EClassifier>> path,
			EReference eRef,
			EClassifier eClassifier,
			Boolean isInnerCircle) {
		
		// create step in path
		HashMap<EReference,EClassifier> step = new HashMap<EReference, EClassifier>();
		step.put(eRef, eClassifier);
		path.push(step);
		
		// create ContainmentCycle object and link it to the EClassifierInfo
		// for the affected EClassifier
		ContainmentCycle cc = new ContainmentCycle(path, isInnerCircle);
		ECM.getEClassifierInfo(eClassifier).addContainmentCycle(cc);
		
		return cc;
	}
	
	
	/**
	 * Logs and prints out the paths of a containmnet cycle
	 * @param path
	 */
	private void logCC(ContainmentCycle cc) {
		
		String message = "Found"+ ((cc.isInnerCircle())?" (inner )":"") + " Containment-Cycle ";
		message += cc.getPathAsString();
		
		LogUtil.log(LogEvent.NOTICE, message);
	}

}
