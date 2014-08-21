package org.sidiff.common.emf.extensions.impl;

import java.util.HashMap;
import java.util.Iterator;
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
 * The ContainmentCyleDetector also considers all inherited
 * references for each EClass.
 * 
 * @author mrindt
 *
 */
public class ContainmentCycleDetector {

	/**
	 * Instance of EClassifierInfoManagement for sub type access.
	 */
	private EClassifierInfoManagement ECM = null;
	
	/**
	 * Whether inner containment circles inside a path should be
	 * marked as ContainmentCylces or if they should be ignored.
	 */
	private Boolean considerInnerContainmentCycles;
	
	/**
	 * Constructor. It's argument determines
	 * if inner containment cycles (inside a containment hierarchy path) should be
	 * marked as ContainmentCylces or if they should be ignored.
	 * <br/>
	 * Inner = last entry in path points back to some other entry which is not the beginning.
	 * <br/>
	 * Outer = last entry in path points back to the entry at the beginning.
	 * @param considerInnerContainmentCycles
	 */
	public ContainmentCycleDetector(Boolean considerInnerContainmentCycles) {
		
		ECM =  EClassifierInfoManagement.getInstance();
		this.considerInnerContainmentCycles = considerInnerContainmentCycles;
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
	
					// note: it is not necessary to check paths of sub types
					// since eAllReferences of each eClass in the meta-model
					// (and thus sub types also) are checked.
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

		// iterate over  eReferences of the last saved eClassifier target in last step
		Iterator<EReference> refIterator = ((EClass)eClassifier).getEAllReferences().iterator();
		if(refIterator.hasNext()) {
			
			// for the first EReference create a step along the already existing path
			EReference firstRef = refIterator.next();				
			EClassifier targetOfFirstRef = firstRef.getEReferenceType(); 
			checkAndStoreCycles(currentPath, firstRef, targetOfFirstRef);
			
			
			// for each further EReference create a new individual path with the backed up path as basis
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
		
		boolean producesOuterCycle = checkTargetIsOriginOfPath(currentPath, eClassifier);
		boolean producesInnerCycle = checkInnerCircle(currentPath, eClassifier);

		// ** on containment cycle (target is origin of Path) **********************************************/
		if(producesOuterCycle) {			
			ContainmentCycle cc = createAndStoreContainmentCycle(currentPath, eRef, eClassifier, false);					
			logCC(cc);
		}
		// ** on inner containment cycle ****************************************************************/
		else if(considerInnerContainmentCycles && producesInnerCycle) {						
			ContainmentCycle cc = createAndStoreContainmentCycle(currentPath, eRef, eClassifier, true);			
			logCC(cc);			
		}
		else if(producesInnerCycle && !considerInnerContainmentCycles ) {
			//do nothing
		}
		// ** otherwise no cycle --> keep adding steps *****************************************************/
		else if(!producesOuterCycle && !producesInnerCycle && !considerInnerContainmentCycles){
			// keep the currentPath for later usage of sub types
			Stack<HashMap<EReference, EClassifier>> backedUpPath = new Stack<HashMap<EReference, EClassifier>>();
			backedUpPath.addAll(currentPath);

			// create next step (eRef, eClassifier)
			HashMap<EReference,EClassifier> step = new HashMap<EReference, EClassifier>();
			step.put(eRef, eClassifier);
			currentPath.push(step);
			findNextStep(currentPath);

			// note: it is not necessary to check paths of sub types
			// since eAllReferences of each eClass in the meta-model
			// (and thus sub types also) are checked.
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
		// for first entry in the path (namely the EClassifier, which was first considered)
		EClassifier firstConsideration = (EClassifier) path.get(0).values().iterator().next();
		ContainmentCycle cc = new ContainmentCycle(path, isInnerCircle);
		ECM.getEClassifierInfo(firstConsideration).addContainmentCycle(cc);
		
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
