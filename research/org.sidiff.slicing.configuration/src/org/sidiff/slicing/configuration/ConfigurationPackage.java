/**
 */
package org.sidiff.slicing.configuration;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.sidiff.slicing.configuration.ConfigurationFactory
 * @model kind="package"
 * @generated
 */
public interface ConfigurationPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "configuration";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.sidiff.org/slicing/configuration/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "configuration";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ConfigurationPackage eINSTANCE = org.sidiff.slicing.configuration.impl.ConfigurationPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.sidiff.slicing.configuration.impl.SlicingConfigurationImpl <em>Slicing Configuration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.slicing.configuration.impl.SlicingConfigurationImpl
	 * @see org.sidiff.slicing.configuration.impl.ConfigurationPackageImpl#getSlicingConfiguration()
	 * @generated
	 */
	int SLICING_CONFIGURATION = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICING_CONFIGURATION__NAME = 0;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICING_CONFIGURATION__DESCRIPTION = 1;

	/**
	 * The feature id for the '<em><b>Document Type</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICING_CONFIGURATION__DOCUMENT_TYPE = 2;

	/**
	 * The feature id for the '<em><b>Imports</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICING_CONFIGURATION__IMPORTS = 3;

	/**
	 * The feature id for the '<em><b>Slicing Mode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICING_CONFIGURATION__SLICING_MODE = 4;

	/**
	 * The feature id for the '<em><b>Sliced EClasses</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICING_CONFIGURATION__SLICED_ECLASSES = 5;

	/**
	 * The number of structural features of the '<em>Slicing Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICING_CONFIGURATION_FEATURE_COUNT = 6;

	/**
	 * The number of operations of the '<em>Slicing Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICING_CONFIGURATION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.sidiff.slicing.configuration.impl.SlicedEClassImpl <em>Sliced EClass</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.slicing.configuration.impl.SlicedEClassImpl
	 * @see org.sidiff.slicing.configuration.impl.ConfigurationPackageImpl#getSlicedEClass()
	 * @generated
	 */
	int SLICED_ECLASS = 1;

	/**
	 * The feature id for the '<em><b>Boundary</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICED_ECLASS__BOUNDARY = 0;

	/**
	 * The feature id for the '<em><b>Slicing Configuration</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICED_ECLASS__SLICING_CONFIGURATION = 1;

	/**
	 * The feature id for the '<em><b>Outgoings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICED_ECLASS__OUTGOINGS = 2;

	/**
	 * The feature id for the '<em><b>Incomings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICED_ECLASS__INCOMINGS = 3;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICED_ECLASS__TYPE = 4;

	/**
	 * The number of structural features of the '<em>Sliced EClass</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICED_ECLASS_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Sliced EClass</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICED_ECLASS_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.sidiff.slicing.configuration.impl.SlicedBoundaryEReferenceImpl <em>Sliced Boundary EReference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.slicing.configuration.impl.SlicedBoundaryEReferenceImpl
	 * @see org.sidiff.slicing.configuration.impl.ConfigurationPackageImpl#getSlicedBoundaryEReference()
	 * @generated
	 */
	int SLICED_BOUNDARY_EREFERENCE = 2;

	/**
	 * The feature id for the '<em><b>Source</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICED_BOUNDARY_EREFERENCE__SOURCE = 0;

	/**
	 * The feature id for the '<em><b>Target</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICED_BOUNDARY_EREFERENCE__TARGET = 1;

	/**
	 * The feature id for the '<em><b>Src Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICED_BOUNDARY_EREFERENCE__SRC_TYPE = 2;

	/**
	 * The feature id for the '<em><b>Tgt Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICED_BOUNDARY_EREFERENCE__TGT_TYPE = 3;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICED_BOUNDARY_EREFERENCE__TYPE = 4;

	/**
	 * The number of structural features of the '<em>Sliced Boundary EReference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICED_BOUNDARY_EREFERENCE_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Sliced Boundary EReference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICED_BOUNDARY_EREFERENCE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.sidiff.slicing.configuration.SlicingMode <em>Slicing Mode</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.slicing.configuration.SlicingMode
	 * @see org.sidiff.slicing.configuration.impl.ConfigurationPackageImpl#getSlicingMode()
	 * @generated
	 */
	int SLICING_MODE = 3;


	/**
	 * Returns the meta object for class '{@link org.sidiff.slicing.configuration.SlicingConfiguration <em>Slicing Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Slicing Configuration</em>'.
	 * @see org.sidiff.slicing.configuration.SlicingConfiguration
	 * @generated
	 */
	EClass getSlicingConfiguration();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.slicing.configuration.SlicingConfiguration#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.sidiff.slicing.configuration.SlicingConfiguration#getName()
	 * @see #getSlicingConfiguration()
	 * @generated
	 */
	EAttribute getSlicingConfiguration_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.slicing.configuration.SlicingConfiguration#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.sidiff.slicing.configuration.SlicingConfiguration#getDescription()
	 * @see #getSlicingConfiguration()
	 * @generated
	 */
	EAttribute getSlicingConfiguration_Description();

	/**
	 * Returns the meta object for the attribute list '{@link org.sidiff.slicing.configuration.SlicingConfiguration#getDocumentType <em>Document Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Document Type</em>'.
	 * @see org.sidiff.slicing.configuration.SlicingConfiguration#getDocumentType()
	 * @see #getSlicingConfiguration()
	 * @generated
	 */
	EAttribute getSlicingConfiguration_DocumentType();

	/**
	 * Returns the meta object for the reference list '{@link org.sidiff.slicing.configuration.SlicingConfiguration#getImports <em>Imports</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Imports</em>'.
	 * @see org.sidiff.slicing.configuration.SlicingConfiguration#getImports()
	 * @see #getSlicingConfiguration()
	 * @generated
	 */
	EReference getSlicingConfiguration_Imports();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.slicing.configuration.SlicingConfiguration#getSlicingMode <em>Slicing Mode</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Slicing Mode</em>'.
	 * @see org.sidiff.slicing.configuration.SlicingConfiguration#getSlicingMode()
	 * @see #getSlicingConfiguration()
	 * @generated
	 */
	EAttribute getSlicingConfiguration_SlicingMode();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.slicing.configuration.SlicingConfiguration#getSlicedEClasses <em>Sliced EClasses</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Sliced EClasses</em>'.
	 * @see org.sidiff.slicing.configuration.SlicingConfiguration#getSlicedEClasses()
	 * @see #getSlicingConfiguration()
	 * @generated
	 */
	EReference getSlicingConfiguration_SlicedEClasses();

	/**
	 * Returns the meta object for class '{@link org.sidiff.slicing.configuration.SlicedEClass <em>Sliced EClass</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Sliced EClass</em>'.
	 * @see org.sidiff.slicing.configuration.SlicedEClass
	 * @generated
	 */
	EClass getSlicedEClass();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.slicing.configuration.SlicedEClass#isBoundary <em>Boundary</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Boundary</em>'.
	 * @see org.sidiff.slicing.configuration.SlicedEClass#isBoundary()
	 * @see #getSlicedEClass()
	 * @generated
	 */
	EAttribute getSlicedEClass_Boundary();

	/**
	 * Returns the meta object for the container reference '{@link org.sidiff.slicing.configuration.SlicedEClass#getSlicingConfiguration <em>Slicing Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Slicing Configuration</em>'.
	 * @see org.sidiff.slicing.configuration.SlicedEClass#getSlicingConfiguration()
	 * @see #getSlicedEClass()
	 * @generated
	 */
	EReference getSlicedEClass_SlicingConfiguration();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.slicing.configuration.SlicedEClass#getOutgoings <em>Outgoings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Outgoings</em>'.
	 * @see org.sidiff.slicing.configuration.SlicedEClass#getOutgoings()
	 * @see #getSlicedEClass()
	 * @generated
	 */
	EReference getSlicedEClass_Outgoings();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.slicing.configuration.SlicedEClass#getIncomings <em>Incomings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Incomings</em>'.
	 * @see org.sidiff.slicing.configuration.SlicedEClass#getIncomings()
	 * @see #getSlicedEClass()
	 * @generated
	 */
	EReference getSlicedEClass_Incomings();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.slicing.configuration.SlicedEClass#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see org.sidiff.slicing.configuration.SlicedEClass#getType()
	 * @see #getSlicedEClass()
	 * @generated
	 */
	EReference getSlicedEClass_Type();

	/**
	 * Returns the meta object for class '{@link org.sidiff.slicing.configuration.SlicedBoundaryEReference <em>Sliced Boundary EReference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Sliced Boundary EReference</em>'.
	 * @see org.sidiff.slicing.configuration.SlicedBoundaryEReference
	 * @generated
	 */
	EClass getSlicedBoundaryEReference();

	/**
	 * Returns the meta object for the container reference '{@link org.sidiff.slicing.configuration.SlicedBoundaryEReference#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Source</em>'.
	 * @see org.sidiff.slicing.configuration.SlicedBoundaryEReference#getSource()
	 * @see #getSlicedBoundaryEReference()
	 * @generated
	 */
	EReference getSlicedBoundaryEReference_Source();

	/**
	 * Returns the meta object for the container reference '{@link org.sidiff.slicing.configuration.SlicedBoundaryEReference#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Target</em>'.
	 * @see org.sidiff.slicing.configuration.SlicedBoundaryEReference#getTarget()
	 * @see #getSlicedBoundaryEReference()
	 * @generated
	 */
	EReference getSlicedBoundaryEReference_Target();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.slicing.configuration.SlicedBoundaryEReference#getSrcType <em>Src Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Src Type</em>'.
	 * @see org.sidiff.slicing.configuration.SlicedBoundaryEReference#getSrcType()
	 * @see #getSlicedBoundaryEReference()
	 * @generated
	 */
	EReference getSlicedBoundaryEReference_SrcType();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.slicing.configuration.SlicedBoundaryEReference#getTgtType <em>Tgt Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Tgt Type</em>'.
	 * @see org.sidiff.slicing.configuration.SlicedBoundaryEReference#getTgtType()
	 * @see #getSlicedBoundaryEReference()
	 * @generated
	 */
	EReference getSlicedBoundaryEReference_TgtType();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.slicing.configuration.SlicedBoundaryEReference#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see org.sidiff.slicing.configuration.SlicedBoundaryEReference#getType()
	 * @see #getSlicedBoundaryEReference()
	 * @generated
	 */
	EReference getSlicedBoundaryEReference_Type();

	/**
	 * Returns the meta object for enum '{@link org.sidiff.slicing.configuration.SlicingMode <em>Slicing Mode</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Slicing Mode</em>'.
	 * @see org.sidiff.slicing.configuration.SlicingMode
	 * @generated
	 */
	EEnum getSlicingMode();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ConfigurationFactory getConfigurationFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.sidiff.slicing.configuration.impl.SlicingConfigurationImpl <em>Slicing Configuration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.slicing.configuration.impl.SlicingConfigurationImpl
		 * @see org.sidiff.slicing.configuration.impl.ConfigurationPackageImpl#getSlicingConfiguration()
		 * @generated
		 */
		EClass SLICING_CONFIGURATION = eINSTANCE.getSlicingConfiguration();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SLICING_CONFIGURATION__NAME = eINSTANCE.getSlicingConfiguration_Name();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SLICING_CONFIGURATION__DESCRIPTION = eINSTANCE.getSlicingConfiguration_Description();

		/**
		 * The meta object literal for the '<em><b>Document Type</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SLICING_CONFIGURATION__DOCUMENT_TYPE = eINSTANCE.getSlicingConfiguration_DocumentType();

		/**
		 * The meta object literal for the '<em><b>Imports</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SLICING_CONFIGURATION__IMPORTS = eINSTANCE.getSlicingConfiguration_Imports();

		/**
		 * The meta object literal for the '<em><b>Slicing Mode</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SLICING_CONFIGURATION__SLICING_MODE = eINSTANCE.getSlicingConfiguration_SlicingMode();

		/**
		 * The meta object literal for the '<em><b>Sliced EClasses</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SLICING_CONFIGURATION__SLICED_ECLASSES = eINSTANCE.getSlicingConfiguration_SlicedEClasses();

		/**
		 * The meta object literal for the '{@link org.sidiff.slicing.configuration.impl.SlicedEClassImpl <em>Sliced EClass</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.slicing.configuration.impl.SlicedEClassImpl
		 * @see org.sidiff.slicing.configuration.impl.ConfigurationPackageImpl#getSlicedEClass()
		 * @generated
		 */
		EClass SLICED_ECLASS = eINSTANCE.getSlicedEClass();

		/**
		 * The meta object literal for the '<em><b>Boundary</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SLICED_ECLASS__BOUNDARY = eINSTANCE.getSlicedEClass_Boundary();

		/**
		 * The meta object literal for the '<em><b>Slicing Configuration</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SLICED_ECLASS__SLICING_CONFIGURATION = eINSTANCE.getSlicedEClass_SlicingConfiguration();

		/**
		 * The meta object literal for the '<em><b>Outgoings</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SLICED_ECLASS__OUTGOINGS = eINSTANCE.getSlicedEClass_Outgoings();

		/**
		 * The meta object literal for the '<em><b>Incomings</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SLICED_ECLASS__INCOMINGS = eINSTANCE.getSlicedEClass_Incomings();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SLICED_ECLASS__TYPE = eINSTANCE.getSlicedEClass_Type();

		/**
		 * The meta object literal for the '{@link org.sidiff.slicing.configuration.impl.SlicedBoundaryEReferenceImpl <em>Sliced Boundary EReference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.slicing.configuration.impl.SlicedBoundaryEReferenceImpl
		 * @see org.sidiff.slicing.configuration.impl.ConfigurationPackageImpl#getSlicedBoundaryEReference()
		 * @generated
		 */
		EClass SLICED_BOUNDARY_EREFERENCE = eINSTANCE.getSlicedBoundaryEReference();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SLICED_BOUNDARY_EREFERENCE__SOURCE = eINSTANCE.getSlicedBoundaryEReference_Source();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SLICED_BOUNDARY_EREFERENCE__TARGET = eINSTANCE.getSlicedBoundaryEReference_Target();

		/**
		 * The meta object literal for the '<em><b>Src Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SLICED_BOUNDARY_EREFERENCE__SRC_TYPE = eINSTANCE.getSlicedBoundaryEReference_SrcType();

		/**
		 * The meta object literal for the '<em><b>Tgt Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SLICED_BOUNDARY_EREFERENCE__TGT_TYPE = eINSTANCE.getSlicedBoundaryEReference_TgtType();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SLICED_BOUNDARY_EREFERENCE__TYPE = eINSTANCE.getSlicedBoundaryEReference_Type();

		/**
		 * The meta object literal for the '{@link org.sidiff.slicing.configuration.SlicingMode <em>Slicing Mode</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.slicing.configuration.SlicingMode
		 * @see org.sidiff.slicing.configuration.impl.ConfigurationPackageImpl#getSlicingMode()
		 * @generated
		 */
		EEnum SLICING_MODE = eINSTANCE.getSlicingMode();

	}

} //ConfigurationPackage
