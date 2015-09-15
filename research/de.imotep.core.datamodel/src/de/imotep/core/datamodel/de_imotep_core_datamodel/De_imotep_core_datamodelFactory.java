/**
 */
package de.imotep.core.datamodel.de_imotep_core_datamodel;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.De_imotep_core_datamodelPackage
 * @generated
 */
public interface De_imotep_core_datamodelFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	De_imotep_core_datamodelFactory eINSTANCE = de.imotep.core.datamodel.de_imotep_core_datamodel.impl.De_imotep_core_datamodelFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>MNamed Entity</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>MNamed Entity</em>'.
	 * @generated
	 */
	MNamedEntity createMNamedEntity();

	/**
	 * Returns a new object of class '<em>MInteger Attribute</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>MInteger Attribute</em>'.
	 * @generated
	 */
	MIntegerAttribute createMIntegerAttribute();

	/**
	 * Returns a new object of class '<em>MString Attribute</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>MString Attribute</em>'.
	 * @generated
	 */
	MStringAttribute createMStringAttribute();

	/**
	 * Returns a new object of class '<em>MEntity</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>MEntity</em>'.
	 * @generated
	 */
	MEntity createMEntity();

	/**
	 * Returns a new object of class '<em>MRanged Integer Attribute</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>MRanged Integer Attribute</em>'.
	 * @generated
	 */
	MRangedIntegerAttribute createMRangedIntegerAttribute();

	/**
	 * Returns a new object of class '<em>MBoolean Attribute</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>MBoolean Attribute</em>'.
	 * @generated
	 */
	MBooleanAttribute createMBooleanAttribute();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	De_imotep_core_datamodelPackage getDe_imotep_core_datamodelPackage();

} //De_imotep_core_datamodelFactory
