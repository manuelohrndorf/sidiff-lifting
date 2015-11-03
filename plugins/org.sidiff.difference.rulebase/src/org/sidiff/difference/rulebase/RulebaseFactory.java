/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.difference.rulebase;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.sidiff.difference.rulebase.RulebasePackage
 * @generated
 */
public interface RulebaseFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	RulebaseFactory eINSTANCE = org.sidiff.difference.rulebase.impl.RulebaseFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Rule Base</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Rule Base</em>'.
	 * @generated
	 */
	RuleBase createRuleBase();

	/**
	 * Returns a new object of class '<em>Edit Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Edit Rule</em>'.
	 * @generated
	 */
	EditRule createEditRule();

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
	 * Returns a new object of class '<em>Rule Base Item</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Rule Base Item</em>'.
	 * @generated
	 */
	RuleBaseItem createRuleBaseItem();

	/**
	 * Returns a new object of class '<em>Potential Node Dependency</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Potential Node Dependency</em>'.
	 * @generated
	 */
	PotentialNodeDependency createPotentialNodeDependency();

	/**
	 * Returns a new object of class '<em>Potential Edge Dependency</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Potential Edge Dependency</em>'.
	 * @generated
	 */
	PotentialEdgeDependency createPotentialEdgeDependency();

	/**
	 * Returns a new object of class '<em>Potential Attribute Dependency</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Potential Attribute Dependency</em>'.
	 * @generated
	 */
	PotentialAttributeDependency createPotentialAttributeDependency();

	/**
	 * Returns a new object of class '<em>Parameter</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Parameter</em>'.
	 * @generated
	 */
	Parameter createParameter();

	/**
	 * Returns a new object of class '<em>Classification</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Classification</em>'.
	 * @generated
	 */
	Classification createClassification();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	RulebasePackage getRulebasePackage();

} //RulebaseFactory
