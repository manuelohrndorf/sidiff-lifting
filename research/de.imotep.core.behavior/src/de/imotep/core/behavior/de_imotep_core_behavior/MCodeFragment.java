/**
 */
package de.imotep.core.behavior.de_imotep_core_behavior;

import de.imotep.core.datamodel.de_imotep_core_datamodel.MAttribute;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>MCode Fragment</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.MCodeFragment#getCuse <em>Cuse</em>}</li>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.MCodeFragment#getPuse <em>Puse</em>}</li>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.MCodeFragment#getDuse <em>Duse</em>}</li>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.MCodeFragment#getUsedAttributes <em>Used Attributes</em>}</li>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.MCodeFragment#getExpression <em>Expression</em>}</li>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.MCodeFragment#getLanguage <em>Language</em>}</li>
 * </ul>
 *
 * @see de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorPackage#getMCodeFragment()
 * @model
 * @generated
 */
public interface MCodeFragment extends MBehaviorEntity {
	/**
	 * Returns the value of the '<em><b>Cuse</b></em>' reference list.
	 * The list contents are of type {@link de.imotep.core.datamodel.de_imotep_core_datamodel.MAttribute}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cuse</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cuse</em>' reference list.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorPackage#getMCodeFragment_Cuse()
	 * @model
	 * @generated
	 */
	EList<MAttribute> getCuse();

	/**
	 * Returns the value of the '<em><b>Puse</b></em>' reference list.
	 * The list contents are of type {@link de.imotep.core.datamodel.de_imotep_core_datamodel.MAttribute}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Puse</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Puse</em>' reference list.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorPackage#getMCodeFragment_Puse()
	 * @model
	 * @generated
	 */
	EList<MAttribute> getPuse();

	/**
	 * Returns the value of the '<em><b>Duse</b></em>' reference list.
	 * The list contents are of type {@link de.imotep.core.datamodel.de_imotep_core_datamodel.MAttribute}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Duse</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Duse</em>' reference list.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorPackage#getMCodeFragment_Duse()
	 * @model
	 * @generated
	 */
	EList<MAttribute> getDuse();

	/**
	 * Returns the value of the '<em><b>Used Attributes</b></em>' reference list.
	 * The list contents are of type {@link de.imotep.core.datamodel.de_imotep_core_datamodel.MAttribute}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Used Attributes</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Used Attributes</em>' reference list.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorPackage#getMCodeFragment_UsedAttributes()
	 * @model
	 * @generated
	 */
	EList<MAttribute> getUsedAttributes();

	/**
	 * Returns the value of the '<em><b>Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Expression</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Expression</em>' attribute.
	 * @see #setExpression(String)
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorPackage#getMCodeFragment_Expression()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	String getExpression();

	/**
	 * Sets the value of the '{@link de.imotep.core.behavior.de_imotep_core_behavior.MCodeFragment#getExpression <em>Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Expression</em>' attribute.
	 * @see #getExpression()
	 * @generated
	 */
	void setExpression(String value);

	/**
	 * Returns the value of the '<em><b>Language</b></em>' attribute.
	 * The literals are from the enumeration {@link de.imotep.core.behavior.de_imotep_core_behavior.MELanguages}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Language</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Language</em>' attribute.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MELanguages
	 * @see #setLanguage(MELanguages)
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorPackage#getMCodeFragment_Language()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	MELanguages getLanguage();

	/**
	 * Sets the value of the '{@link de.imotep.core.behavior.de_imotep_core_behavior.MCodeFragment#getLanguage <em>Language</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Language</em>' attribute.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MELanguages
	 * @see #getLanguage()
	 * @generated
	 */
	void setLanguage(MELanguages value);

} // MCodeFragment
