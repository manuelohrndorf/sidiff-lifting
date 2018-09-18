/**
 */
package org.sidiff.slicer.rulebased.slice;

import org.sidiff.difference.asymmetric.AsymmetricDifference;

import org.sidiff.slicer.slice.ModelSlice;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Executable Model Slice</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.slicer.rulebased.slice.ExecutableModelSlice#getAsymmetricDifference <em>Asymmetric Difference</em>}</li>
 * </ul>
 *
 * @see org.sidiff.slicer.rulebased.slice.RuleBasedSlicePackage#getExecutableModelSlice()
 * @model
 * @generated
 */
public interface ExecutableModelSlice extends ModelSlice {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "(c), Christopher Pietsch, Software Engineering Group, University of Siegen 2017 all rights reserved";

	/**
	 * Returns the value of the '<em><b>Asymmetric Difference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Asymmetric Difference</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Asymmetric Difference</em>' reference.
	 * @see #setAsymmetricDifference(AsymmetricDifference)
	 * @see org.sidiff.slicer.rulebased.slice.RuleBasedSlicePackage#getExecutableModelSlice_AsymmetricDifference()
	 * @model required="true"
	 * @generated
	 */
	AsymmetricDifference getAsymmetricDifference();

	/**
	 * Sets the value of the '{@link org.sidiff.slicer.rulebased.slice.ExecutableModelSlice#getAsymmetricDifference <em>Asymmetric Difference</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Asymmetric Difference</em>' reference.
	 * @see #getAsymmetricDifference()
	 * @generated
	 */
	void setAsymmetricDifference(AsymmetricDifference value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model required="true" pathRequired="true" zipRequired="true"
	 * @generated
	 */
	String serialize(String path, boolean zip);

} // ExecutableModelSlice
