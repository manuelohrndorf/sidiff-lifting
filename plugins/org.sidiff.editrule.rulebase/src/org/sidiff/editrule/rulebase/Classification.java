/**
 */
package org.sidiff.editrule.rulebase;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Classification</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.editrule.rulebase.Classification#getName <em>Name</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.Classification#getClassificatorID <em>Classificator ID</em>}</li>
 * </ul>
 *
 * @see org.sidiff.editrule.rulebase.RulebasePackage#getClassification()
 * @model
 * @generated
 */
public interface Classification extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.sidiff.editrule.rulebase.RulebasePackage#getClassification_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.sidiff.editrule.rulebase.Classification#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Classificator ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Classificator ID</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Classificator ID</em>' attribute.
	 * @see #setClassificatorID(int)
	 * @see org.sidiff.editrule.rulebase.RulebasePackage#getClassification_ClassificatorID()
	 * @model
	 * @generated
	 */
	int getClassificatorID();

	/**
	 * Sets the value of the '{@link org.sidiff.editrule.rulebase.Classification#getClassificatorID <em>Classificator ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Classificator ID</em>' attribute.
	 * @see #getClassificatorID()
	 * @generated
	 */
	void setClassificatorID(int value);

} // Classification
