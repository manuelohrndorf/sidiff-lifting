/**
 * Copyright (c) 2011 Software Engineering Institute, TU Dortmund.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     {SecSE group} - initial API and implementation and/or initial documentation
 */
package carisma.modeltype.owl2.model.owl;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Functional Data Property</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link carisma.modeltype.owl2.model.owl.FunctionalDataProperty#getDataPropertyExpression <em>Data Property Expression</em>}</li>
 * </ul>
 * </p>
 *
 * @see carisma.modeltype.owl2.model.owl.OwlPackage#getFunctionalDataProperty()
 * @model
 * @generated
 */
public interface FunctionalDataProperty extends DataPropertyAxiom {
	/**
	 * Returns the value of the '<em><b>Data Property Expression</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Data Property Expression</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Data Property Expression</em>' reference.
	 * @see #setDataPropertyExpression(DataPropertyExpression)
	 * @see carisma.modeltype.owl2.model.owl.OwlPackage#getFunctionalDataProperty_DataPropertyExpression()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	DataPropertyExpression getDataPropertyExpression();

	/**
	 * Sets the value of the '{@link carisma.modeltype.owl2.model.owl.FunctionalDataProperty#getDataPropertyExpression <em>Data Property Expression</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Data Property Expression</em>' reference.
	 * @see #getDataPropertyExpression()
	 * @generated
	 */
	void setDataPropertyExpression(DataPropertyExpression value);

} // FunctionalDataProperty
