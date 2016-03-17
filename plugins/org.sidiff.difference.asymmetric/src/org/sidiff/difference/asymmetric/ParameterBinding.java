/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.difference.asymmetric;

import org.eclipse.emf.ecore.EObject;
import org.sidiff.editrule.rulebase.Parameter;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Parameter Binding</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.sidiff.difference.asymmetric.ParameterBinding#getFormalName <em>Formal Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.sidiff.difference.asymmetric.AsymmetricPackage#getParameterBinding()
 * @model abstract="true"
 * @generated
 */
public interface ParameterBinding extends EObject {
	/**
	 * Returns the value of the '<em><b>Formal Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Formal Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Formal Name</em>' attribute.
	 * @see #setFormalName(String)
	 * @see org.sidiff.difference.asymmetric.AsymmetricPackage#getParameterBinding_FormalName()
	 * @model required="true"
	 * @generated
	 */
	String getFormalName();

	/**
	 * Sets the value of the '{@link org.sidiff.difference.asymmetric.ParameterBinding#getFormalName <em>Formal Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Formal Name</em>' attribute.
	 * @see #getFormalName()
	 * @generated
	 */
	void setFormalName(String value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	boolean isDefaultValue();
	
	Parameter getFormalParameter();

} // ParameterBinding
