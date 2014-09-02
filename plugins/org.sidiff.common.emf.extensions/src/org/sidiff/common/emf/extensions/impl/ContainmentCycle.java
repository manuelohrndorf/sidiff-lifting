package org.sidiff.common.emf.extensions.impl;

import java.util.Stack;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EReference;

/**
 * An instance of this class depicts a containment cycle which can be outer or inner.
 * outer = the end of a path points back to the first entry (the origin of a path).
 * inner = the end of a path points back to a previous but not the first entry in the path.
 * 
 * @author mrindt
 *
 */
public class ContainmentCycle {

	/**
	 * Path, which is a stack of pairs [reference, target classifier].
	 * The top-most entry in the stack is the last reachable pair, which consists of the classifier
	 * and a reference, which points back to the first (bottom) entry in the stack.
	 * The entry at the very bottom consists of (null, eClassifier) and marks the origin of the path.
	 */
	private Stack<ContainmentCyclePathStep> path = new  Stack<ContainmentCyclePathStep>();
	
	/**
	 * Constructor
	 * @param eClass
	 */
	public ContainmentCycle(Stack<ContainmentCyclePathStep> path, Boolean containsInnerCircle) {
		this.path = path;
		this.containsInnerCircle = containsInnerCircle;
	}
	
	/**
	 * Set this to true in case there are inner containment cycles which do not point
	 * back to the origin but to other entries in the path.
	 */
	private Boolean containsInnerCircle;
	
	
	/**
	 * Returns the path of a containment cycle.
	 * The path is build up as a stack of ContainmentCyclePaths (i.e., pair like [reference, target classifier]).
	 * The top-most entry in the stack is the last reachable pair, which consists of the classifier
	 * that is the container of the first  reference (bottom entry) in the stack.
	 * @return
	 */
	@Deprecated
	public Stack<ContainmentCyclePathStep> getPath() {
		return path;
	}

	/**
	 * Returns all the path as String
	 * @return
	 */
	public String getPathAsString() {
		
		String message = "";
		for(ContainmentCyclePathStep step: path) {
			EClassifier eClassifier = step.getTargetedEClassifier();
			EReference eRef = step.getTargetingReference();	

			if(eRef==null) { //->origin of the path
				message +="[" + eClassifier.getName() + "]";
			}else{
				message +=" > " + "(" + eRef.getName() +")" + eClassifier.getName();
			}
	
		}
		return message;
	}
	
	/**
	 * Returns true if the containment cycle path has an inner circle,
	 * i.e., if the end of a path points not back to the beginning
	 * but to somewhere else in the path. Inner circle detection has
	 * to be turned on before (@see ContainmentCycle Constructor)
	 * @return
	 */
	public Boolean isInnerCircle() {
		return containsInnerCircle;
	}
	
	/**
	 * Returns true, if there are no steps between origin and last entry in path;
	 * i.e. an EClass has a containment reference to itself or its super type.
	 * @return
	 */
	public Boolean isDirectCycle() {
		
		return (path.size()==2);
	}
	
	public ContainmentCyclePathStep getStartingPoint() {
		return path.firstElement();
	}
	
	public ContainmentCyclePathStep getBackwardPointingStep() {
		return path.lastElement();
	}
	
	public Stack<ContainmentCyclePathStep> getIntermediateSteps() {
		Stack<ContainmentCyclePathStep> intermediates = new Stack<ContainmentCyclePathStep>();
		
		intermediates.addAll(path);
		intermediates.remove(path.firstElement());
		intermediates.remove(path.lastElement());
		
		return intermediates;
		
	}
	
	public Integer getNumberOfIntermediateSteps() {
		return getIntermediateSteps().size();
	}
	
	public Boolean isLastIntermediateStep(ContainmentCyclePathStep step) {
		int numberOfIntermediates = getNumberOfIntermediateSteps();
		ContainmentCyclePathStep lastIntermediate = getIntermediateSteps().get(numberOfIntermediates-1);
		return lastIntermediate.equals(step);
	}
}
