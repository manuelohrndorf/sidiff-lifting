package org.sidiff.common.emf.extensions.impl;

import java.util.HashMap;
import java.util.Iterator;
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
	private Stack<HashMap<EReference,EClassifier>> path = new  Stack<HashMap<EReference,EClassifier>>();
	
	/**
	 * Constructor
	 * @param eClass
	 */
	public ContainmentCycle(Stack<HashMap<EReference,EClassifier>> path, Boolean containsInnerCircle) {
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
	 * The path is build up as a stack of pairs [reference, target classifier].
	 * The top-most entry in the stack is the last reachable pair, which consists of the classifier
	 * that is the container of the first  reference (bottom entry) in the stack.
	 * @return
	 */
	public Stack<HashMap<EReference,EClassifier>> getPath() {
		return path;
	}

	public String getPathAsString() {
		
		String message = "";
		for(HashMap<EReference,EClassifier> step: path) {
			EClassifier eClassifier = (EClassifier) step.values().iterator().next();
			Iterator<EReference> eRefIt = step.keySet().iterator();
			while(eRefIt.hasNext()) {
				EReference eRef = eRefIt.next();
				if(eRef==null) { //->origin of the path
					message +="[" + eClassifier.getName() + "]";
				}else{
					message +=" > " + "(" + eRef.getName() +")" + eClassifier.getName();
				}
			}	
		}
		return message;
	}
	
	public Boolean isInnerCircle() {
		return containsInnerCircle;
	}
}
