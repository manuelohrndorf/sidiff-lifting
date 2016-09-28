/**
 */
package org.sidiff.slicing.configuration;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ocl.ParserException;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IConstraint Interpreter</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.slicing.configuration.IConstraintInterpreter#getKey <em>Key</em>}</li>
 * </ul>
 *
 * @see org.sidiff.slicing.configuration.ConfigurationPackage#getIConstraintInterpreter()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface IConstraintInterpreter extends EObject {
	/**
	 * Returns the value of the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Key</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Key</em>' attribute.
	 * @see org.sidiff.slicing.configuration.ConfigurationPackage#getIConstraintInterpreter_Key()
	 * @model changeable="false"
	 * @generated
	 */
	String getKey();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated NOT
	 */
	boolean evaluate(Constraint constraint, EObject context) throws Exception;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @throws ParserException 
	 * @model constraintsMany="false"
	 * @generated NOT
	 */
	boolean evaluate(EList<Constraint> constraints, EObject context) throws ParserException;

} // IConstraintInterpreter
