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
 * A representation of the model object '<em><b>Parameter Substitution</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link differencemodel.ParameterSubstitution#getFormal <em>Formal</em>}</li>
 * </ul>
 * </p>
 *
 * @see differencemodel.DifferencemodelPackage#getParameterSubstitution()
 * @model abstract="true"
 * @generated
 */
public interface ParameterSubstitution extends EObject {
	/**
	 * Returns the value of the '<em><b>Formal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Formal</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Formal</em>' attribute.
	 * @see #setFormal(String)
	 * @see differencemodel.DifferencemodelPackage#getParameterSubstitution_Formal()
	 * @model
	 * @generated
	 */
	String getFormal();

	/**
	 * Sets the value of the '{@link differencemodel.ParameterSubstitution#getFormal <em>Formal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Formal</em>' attribute.
	 * @see #getFormal()
	 * @generated
	 */
	void setFormal(String value);

} // ParameterSubstitution
