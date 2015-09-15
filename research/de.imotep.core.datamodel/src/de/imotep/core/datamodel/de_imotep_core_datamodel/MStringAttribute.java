/**
 */
package de.imotep.core.datamodel.de_imotep_core_datamodel;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>MString Attribute</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.imotep.core.datamodel.de_imotep_core_datamodel.MStringAttribute#getInitValue <em>Init Value</em>}</li>
 *   <li>{@link de.imotep.core.datamodel.de_imotep_core_datamodel.MStringAttribute#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.De_imotep_core_datamodelPackage#getMStringAttribute()
 * @model
 * @generated
 */
public interface MStringAttribute extends MAttribute {
	/**
	 * Returns the value of the '<em><b>Init Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Init Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Init Value</em>' attribute.
	 * @see #setInitValue(String)
	 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.De_imotep_core_datamodelPackage#getMStringAttribute_InitValue()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	String getInitValue();

	/**
	 * Sets the value of the '{@link de.imotep.core.datamodel.de_imotep_core_datamodel.MStringAttribute#getInitValue <em>Init Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Init Value</em>' attribute.
	 * @see #getInitValue()
	 * @generated
	 */
	void setInitValue(String value);

	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(String)
	 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.De_imotep_core_datamodelPackage#getMStringAttribute_Value()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	String getValue();

	/**
	 * Sets the value of the '{@link de.imotep.core.datamodel.de_imotep_core_datamodel.MStringAttribute#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(String value);

} // MStringAttribute
