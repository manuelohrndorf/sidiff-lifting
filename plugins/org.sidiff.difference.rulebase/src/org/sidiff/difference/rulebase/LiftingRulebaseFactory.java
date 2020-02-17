/**
 */
package org.sidiff.difference.rulebase;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.sidiff.difference.rulebase.LiftingRulebasePackage
 * @generated
 */
public interface LiftingRulebaseFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	LiftingRulebaseFactory eINSTANCE = org.sidiff.difference.rulebase.impl.LiftingRulebaseFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Recognition Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Recognition Rule</em>'.
	 * @generated
	 */
	RecognitionRule createRecognitionRule();

	/**
	 * Returns a new object of class '<em>Trace</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Trace</em>'.
	 * @generated
	 */
	Trace createTrace();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	LiftingRulebasePackage getLiftingRulebasePackage();

} //LiftingRulebaseFactory
