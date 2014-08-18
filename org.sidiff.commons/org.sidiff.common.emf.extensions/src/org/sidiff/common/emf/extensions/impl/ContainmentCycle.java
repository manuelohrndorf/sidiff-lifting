package org.sidiff.common.emf.extensions.impl;

import java.util.HashMap;
import java.util.Stack;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EReference;

public class ContainmentCycle {

	/**
	 * Path, which is a stack of pairs [reference, target classifier].
	 * The top-most entry in the stack is the last reachable pair, which consists of the classifier
	 * that is the container of the first reference in the stack.
	 */
	private Stack<HashMap<EReference,EClassifier>> path = new  Stack<HashMap<EReference,EClassifier>>();
	
	/**
	 * Constructor
	 * @param eClass
	 */
	public ContainmentCycle(Stack<HashMap<EReference,EClassifier>> path) {
		this.path = path;
	}
	
	/**
	 * Returns the path of a containment cycle.
	 * The path is build up as a stack of pairs [reference, target classifier].
	 * The top-most entry in the stack is the last reachable pair, which consists of the classifier
	 * that is the container of the first  reference (bottom entry) in the stack.
	 * @return
	 */
	public Stack<HashMap<EReference,EClassifier>> path() {
		return path;
	}

	
	
}
