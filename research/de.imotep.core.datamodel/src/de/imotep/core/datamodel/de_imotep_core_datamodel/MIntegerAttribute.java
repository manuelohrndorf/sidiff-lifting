/**
 */
package de.imotep.core.datamodel.de_imotep_core_datamodel;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>MInteger Attribute</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.imotep.core.datamodel.de_imotep_core_datamodel.MIntegerAttribute#getInitValue <em>Init Value</em>}</li>
 *   <li>{@link de.imotep.core.datamodel.de_imotep_core_datamodel.MIntegerAttribute#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.De_imotep_core_datamodelPackage#getMIntegerAttribute()
 * @model
 * @generated
 */
public interface MIntegerAttribute extends MAttribute {
	/**
	 * Returns the value of the '<em><b>Init Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Init Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Init Value</em>' attribute.
	 * @see #setInitValue(int)
	 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.De_imotep_core_datamodelPackage#getMIntegerAttribute_InitValue()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	int getInitValue();

	/**
	 * Sets the value of the '{@link de.imotep.core.datamodel.de_imotep_core_datamodel.MIntegerAttribute#getInitValue <em>Init Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Init Value</em>' attribute.
	 * @see #getInitValue()
	 * @generated
	 */
	void setInitValue(int value);

	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(int)
	 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.De_imotep_core_datamodelPackage#getMIntegerAttribute_Value()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	int getValue();

	/**
	 * Sets the value of the '{@link de.imotep.core.datamodel.de_imotep_core_datamodel.MIntegerAttribute#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(int value);

} // MIntegerAttribute
