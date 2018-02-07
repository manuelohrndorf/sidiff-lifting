/**
 */
package org.sidiff.slicer.structural.configuration;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.fieldassist.IContentProposal;
import org.sidiff.slicer.structural.configuration.impl.ConstraintResult;

/**
 * <!-- begin-user-doc -->
 * A representation of the object '<em><b>IConstraint Interpreter</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.slicer.structural.configuration.IConstraintInterpreter#getID <em>ID</em>}</li>
 * </ul>
 *
 */
public interface IConstraintInterpreter {
	
	/**
	 * The extension point id
	 */
	public static final String EXTENSION_POINT_ID = "org.sidiff.slicer.structural.configuration.constraintinterpreter";

	/**
	 * Returns the value of the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>ID</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>ID</em>' attribute.
	 */
	String getID();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	ConstraintResult evaluate(Constraint constraint, EObject eObject);

	/**
	 * Provides syntax help for the given context and constraint expression
	 * @param context the model element that has the constraint
	 * @param expression the constraint expression
	 * @param cursorPosition the position of the cursor in the expression
	 * @return array of content proposals, a selected proposal will be <u>appended</u>
	 * to the current expression, may be empty but may not be <code>null</code>
	 */
	IContentProposal[] getSyntaxHelp(EObject context, String expression, int cursorPosition);
} // IConstraintInterpreter
