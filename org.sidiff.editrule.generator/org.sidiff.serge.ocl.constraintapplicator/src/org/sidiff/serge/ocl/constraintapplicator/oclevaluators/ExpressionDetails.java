package org.sidiff.serge.ocl.constraintapplicator.oclevaluators;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.ocl.expressions.OCLExpression;

public class ExpressionDetails {

	/**
	 * The context involved in an OCLExpression.
	 */
	private EClassifier involvedContext;
	
	/**
	 * The EAttribute involved in an OCLExpression.
	 */
	private EAttribute involvedEAttribute;
	
	/**
	 * The EReference involved in an OCLExpression.
	 */
	private EReference involvedEReference;
	
	/**
	 * The operator involved in an OCLExpression.
	 */
	private String operator;
	
	/**
	 * Constructor.
	 * @param involvedContext
	 * @param oclxp
	 */
	public ExpressionDetails(EClassifier involvedContext, OCLExpression<EClassifier> oclxp) {

		checkDetails();
	}
	
	/**
	 * This method fetches the involved elements inside a OCLExpression
	 */
	private void checkDetails() {
		
		// ..
		
	}
	
	/**
	 * @return involved context
	 */
	public EClassifier getInvolvedContext() {
		return involvedContext;
	}
	
	/**
	 * @return involved attribute
	 */
	public EAttribute getInvolvedEAttribute() {
		return involvedEAttribute;
	}
	
	/**
	 * @return involved reference
	 */
	public EReference getInvolvedEReference() {
		return involvedEReference;
	}
	
	/**
	 * @return involved operator
	 */
	public String getOperator() {
		return operator;
	}
	
}
