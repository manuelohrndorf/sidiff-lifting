/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.editrule.rulebase;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Potential Dependency</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.editrule.rulebase.PotentialDependency#getKind <em>Kind</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.PotentialDependency#getSourceRule <em>Source Rule</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.PotentialDependency#getTargetRule <em>Target Rule</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.PotentialDependency#isTransient <em>Transient</em>}</li>
 * </ul>
 *
 * @see org.sidiff.editrule.rulebase.RulebasePackage#getPotentialDependency()
 * @model abstract="true"
 * @generated
 */
public interface PotentialDependency extends EObject {
	/**
	 * Returns the value of the '<em><b>Source Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source Rule</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source Rule</em>' reference.
	 * @see #setSourceRule(EditRule)
	 * @see org.sidiff.editrule.rulebase.RulebasePackage#getPotentialDependency_SourceRule()
	 * @model required="true"
	 * @generated
	 */
	EditRule getSourceRule();

	/**
	 * Sets the value of the '{@link org.sidiff.editrule.rulebase.PotentialDependency#getSourceRule <em>Source Rule</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source Rule</em>' reference.
	 * @see #getSourceRule()
	 * @generated
	 */
	void setSourceRule(EditRule value);

	/**
	 * Returns the value of the '<em><b>Target Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target Rule</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target Rule</em>' reference.
	 * @see #setTargetRule(EditRule)
	 * @see org.sidiff.editrule.rulebase.RulebasePackage#getPotentialDependency_TargetRule()
	 * @model required="true"
	 * @generated
	 */
	EditRule getTargetRule();

	/**
	 * Sets the value of the '{@link org.sidiff.editrule.rulebase.PotentialDependency#getTargetRule <em>Target Rule</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target Rule</em>' reference.
	 * @see #getTargetRule()
	 * @generated
	 */
	void setTargetRule(EditRule value);

	/**
	 * Returns the value of the '<em><b>Transient</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Transient</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Transient</em>' attribute.
	 * @see #setTransient(boolean)
	 * @see org.sidiff.editrule.rulebase.RulebasePackage#getPotentialDependency_Transient()
	 * @model
	 * @generated
	 */
	boolean isTransient();

	/**
	 * Sets the value of the '{@link org.sidiff.editrule.rulebase.PotentialDependency#isTransient <em>Transient</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Transient</em>' attribute.
	 * @see #isTransient()
	 * @generated
	 */
	void setTransient(boolean value);

	/**
	 * Returns the value of the '<em><b>Kind</b></em>' attribute.
	 * The literals are from the enumeration {@link org.sidiff.editrule.rulebase.PotentialDependencyKind}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Kind</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Kind</em>' attribute.
	 * @see org.sidiff.editrule.rulebase.PotentialDependencyKind
	 * @see #setKind(PotentialDependencyKind)
	 * @see org.sidiff.editrule.rulebase.RulebasePackage#getPotentialDependency_Kind()
	 * @model
	 * @generated
	 */
	PotentialDependencyKind getKind();

	/**
	 * Sets the value of the '{@link org.sidiff.editrule.rulebase.PotentialDependency#getKind <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Kind</em>' attribute.
	 * @see org.sidiff.editrule.rulebase.PotentialDependencyKind
	 * @see #getKind()
	 * @generated
	 */
	void setKind(PotentialDependencyKind value);

} // PotentialDependency
