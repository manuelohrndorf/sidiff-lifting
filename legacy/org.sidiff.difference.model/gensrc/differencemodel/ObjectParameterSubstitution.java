/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package differencemodel;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Object Parameter Substitution</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link differencemodel.ObjectParameterSubstitution#getActualA <em>Actual A</em>}</li>
 *   <li>{@link differencemodel.ObjectParameterSubstitution#getActualB <em>Actual B</em>}</li>
 * </ul>
 * </p>
 *
 * @see differencemodel.DifferencemodelPackage#getObjectParameterSubstitution()
 * @model
 * @generated
 */
public interface ObjectParameterSubstitution extends ParameterSubstitution {
	/**
	 * Returns the value of the '<em><b>Actual A</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Actual A</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Actual A</em>' reference.
	 * @see #setActualA(EObject)
	 * @see differencemodel.DifferencemodelPackage#getObjectParameterSubstitution_ActualA()
	 * @model
	 * @generated
	 */
	EObject getActualA();

	/**
	 * Sets the value of the '{@link differencemodel.ObjectParameterSubstitution#getActualA <em>Actual A</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Actual A</em>' reference.
	 * @see #getActualA()
	 * @generated
	 */
	void setActualA(EObject value);

	/**
	 * Returns the value of the '<em><b>Actual B</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Actual B</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Actual B</em>' reference.
	 * @see #setActualB(EObject)
	 * @see differencemodel.DifferencemodelPackage#getObjectParameterSubstitution_ActualB()
	 * @model
	 * @generated
	 */
	EObject getActualB();

	/**
	 * Sets the value of the '{@link differencemodel.ObjectParameterSubstitution#getActualB <em>Actual B</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Actual B</em>' reference.
	 * @see #getActualB()
	 * @generated
	 */
	void setActualB(EObject value);

} // ObjectParameterSubstitution
