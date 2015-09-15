/**
 */
package de.imotep.core.datamodel.de_imotep_core_datamodel;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>MAttribute</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.imotep.core.datamodel.de_imotep_core_datamodel.MAttribute#isIsStatic <em>Is Static</em>}</li>
 *   <li>{@link de.imotep.core.datamodel.de_imotep_core_datamodel.MAttribute#getVisibility <em>Visibility</em>}</li>
 * </ul>
 *
 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.De_imotep_core_datamodelPackage#getMAttribute()
 * @model abstract="true"
 * @generated
 */
public interface MAttribute extends MNamedEntity {
	/**
	 * Returns the value of the '<em><b>Is Static</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Is Static</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Static</em>' attribute.
	 * @see #setIsStatic(boolean)
	 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.De_imotep_core_datamodelPackage#getMAttribute_IsStatic()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	boolean isIsStatic();

	/**
	 * Sets the value of the '{@link de.imotep.core.datamodel.de_imotep_core_datamodel.MAttribute#isIsStatic <em>Is Static</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Static</em>' attribute.
	 * @see #isIsStatic()
	 * @generated
	 */
	void setIsStatic(boolean value);

	/**
	 * Returns the value of the '<em><b>Visibility</b></em>' attribute.
	 * The literals are from the enumeration {@link de.imotep.core.datamodel.de_imotep_core_datamodel.MEVisibility}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Visibility</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Visibility</em>' attribute.
	 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.MEVisibility
	 * @see #setVisibility(MEVisibility)
	 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.De_imotep_core_datamodelPackage#getMAttribute_Visibility()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	MEVisibility getVisibility();

	/**
	 * Sets the value of the '{@link de.imotep.core.datamodel.de_imotep_core_datamodel.MAttribute#getVisibility <em>Visibility</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Visibility</em>' attribute.
	 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.MEVisibility
	 * @see #getVisibility()
	 * @generated
	 */
	void setVisibility(MEVisibility value);

} // MAttribute
