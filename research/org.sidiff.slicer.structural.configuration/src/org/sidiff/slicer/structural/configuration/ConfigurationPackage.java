/**
 */
package org.sidiff.slicer.structural.configuration;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
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
 * @see org.sidiff.slicer.structural.configuration.ConfigurationFactory
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
	String eNS_URI = "http://www.sidiff.org/slicer/structural/configuration/1.0";

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
	ConfigurationPackage eINSTANCE = org.sidiff.slicer.structural.configuration.impl.ConfigurationPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.sidiff.slicer.structural.configuration.impl.SlicingConfigurationImpl <em>Slicing Configuration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.slicer.structural.configuration.impl.SlicingConfigurationImpl
	 * @see org.sidiff.slicer.structural.configuration.impl.ConfigurationPackageImpl#getSlicingConfiguration()
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
	 * The feature id for the '<em><b>Document Types</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICING_CONFIGURATION__DOCUMENT_TYPES = 2;

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
	 * The feature id for the '<em><b>Opposite Sliced EClass Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICING_CONFIGURATION__OPPOSITE_SLICED_ECLASS_TYPE = 6;

	/**
	 * The feature id for the '<em><b>Check Multiplicity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICING_CONFIGURATION__CHECK_MULTIPLICITY = 7;

	/**
	 * The feature id for the '<em><b>Constraint Interpreter ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICING_CONFIGURATION__CONSTRAINT_INTERPRETER_ID = 8;

	/**
	 * The feature id for the '<em><b>Constraint Interpreter</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICING_CONFIGURATION__CONSTRAINT_INTERPRETER = 9;

	/**
	 * The number of structural features of the '<em>Slicing Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICING_CONFIGURATION_FEATURE_COUNT = 10;

	/**
	 * The number of operations of the '<em>Slicing Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICING_CONFIGURATION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.sidiff.slicer.structural.configuration.impl.SlicedEClassImpl <em>Sliced EClass</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.slicer.structural.configuration.impl.SlicedEClassImpl
	 * @see org.sidiff.slicer.structural.configuration.impl.ConfigurationPackageImpl#getSlicedEClass()
	 * @generated
	 */
	int SLICED_ECLASS = 1;

	/**
	 * The feature id for the '<em><b>Slicing Configuration</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICED_ECLASS__SLICING_CONFIGURATION = 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICED_ECLASS__TYPE = 1;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICED_ECLASS__CONSTRAINT = 2;

	/**
	 * The feature id for the '<em><b>Sliced EReferences</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICED_ECLASS__SLICED_EREFERENCES = 3;

	/**
	 * The feature id for the '<em><b>Opposite Sliced EReference Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICED_ECLASS__OPPOSITE_SLICED_EREFERENCE_TYPE = 4;

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
	 * The meta object id for the '{@link org.sidiff.slicer.structural.configuration.impl.ConstraintImpl <em>Constraint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.slicer.structural.configuration.impl.ConstraintImpl
	 * @see org.sidiff.slicer.structural.configuration.impl.ConfigurationPackageImpl#getConstraint()
	 * @generated
	 */
	int CONSTRAINT = 2;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT__EXPRESSION = 0;

	/**
	 * The number of structural features of the '<em>Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.sidiff.slicer.structural.configuration.impl.SlicedEReferenceImpl <em>Sliced EReference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.slicer.structural.configuration.impl.SlicedEReferenceImpl
	 * @see org.sidiff.slicer.structural.configuration.impl.ConfigurationPackageImpl#getSlicedEReference()
	 * @generated
	 */
	int SLICED_EREFERENCE = 3;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICED_EREFERENCE__TYPE = 0;

	/**
	 * The feature id for the '<em><b>Sliced EClass</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICED_EREFERENCE__SLICED_ECLASS = 1;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICED_EREFERENCE__CONSTRAINT = 2;

	/**
	 * The feature id for the '<em><b>Overwrite</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICED_EREFERENCE__OVERWRITE = 3;

	/**
	 * The number of structural features of the '<em>Sliced EReference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICED_EREFERENCE_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Sliced EReference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICED_EREFERENCE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.sidiff.slicer.structural.configuration.SlicingMode <em>Slicing Mode</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.slicer.structural.configuration.SlicingMode
	 * @see org.sidiff.slicer.structural.configuration.impl.ConfigurationPackageImpl#getSlicingMode()
	 * @generated
	 */
	int SLICING_MODE = 4;


	/**
	 * The meta object id for the '<em>IConstraint Interpreter</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.slicer.structural.configuration.IConstraintInterpreter
	 * @see org.sidiff.slicer.structural.configuration.impl.ConfigurationPackageImpl#getIConstraintInterpreter()
	 * @generated
	 */
	int ICONSTRAINT_INTERPRETER = 5;


	/**
	 * Returns the meta object for class '{@link org.sidiff.slicer.structural.configuration.SlicingConfiguration <em>Slicing Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Slicing Configuration</em>'.
	 * @see org.sidiff.slicer.structural.configuration.SlicingConfiguration
	 * @generated
	 */
	EClass getSlicingConfiguration();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.slicer.structural.configuration.SlicingConfiguration#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.sidiff.slicer.structural.configuration.SlicingConfiguration#getName()
	 * @see #getSlicingConfiguration()
	 * @generated
	 */
	EAttribute getSlicingConfiguration_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.slicer.structural.configuration.SlicingConfiguration#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.sidiff.slicer.structural.configuration.SlicingConfiguration#getDescription()
	 * @see #getSlicingConfiguration()
	 * @generated
	 */
	EAttribute getSlicingConfiguration_Description();

	/**
	 * Returns the meta object for the attribute list '{@link org.sidiff.slicer.structural.configuration.SlicingConfiguration#getDocumentTypes <em>Document Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Document Types</em>'.
	 * @see org.sidiff.slicer.structural.configuration.SlicingConfiguration#getDocumentTypes()
	 * @see #getSlicingConfiguration()
	 * @generated
	 */
	EAttribute getSlicingConfiguration_DocumentTypes();

	/**
	 * Returns the meta object for the reference list '{@link org.sidiff.slicer.structural.configuration.SlicingConfiguration#getImports <em>Imports</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Imports</em>'.
	 * @see org.sidiff.slicer.structural.configuration.SlicingConfiguration#getImports()
	 * @see #getSlicingConfiguration()
	 * @generated
	 */
	EReference getSlicingConfiguration_Imports();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.slicer.structural.configuration.SlicingConfiguration#getSlicingMode <em>Slicing Mode</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Slicing Mode</em>'.
	 * @see org.sidiff.slicer.structural.configuration.SlicingConfiguration#getSlicingMode()
	 * @see #getSlicingConfiguration()
	 * @generated
	 */
	EAttribute getSlicingConfiguration_SlicingMode();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.slicer.structural.configuration.SlicingConfiguration#getSlicedEClasses <em>Sliced EClasses</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Sliced EClasses</em>'.
	 * @see org.sidiff.slicer.structural.configuration.SlicingConfiguration#getSlicedEClasses()
	 * @see #getSlicingConfiguration()
	 * @generated
	 */
	EReference getSlicingConfiguration_SlicedEClasses();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.slicer.structural.configuration.SlicingConfiguration#getOppositeSlicedEClassType <em>Opposite Sliced EClass Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Opposite Sliced EClass Type</em>'.
	 * @see org.sidiff.slicer.structural.configuration.SlicingConfiguration#getOppositeSlicedEClassType()
	 * @see #getSlicingConfiguration()
	 * @generated
	 */
	EAttribute getSlicingConfiguration_OppositeSlicedEClassType();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.slicer.structural.configuration.SlicingConfiguration#isCheckMultiplicity <em>Check Multiplicity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Check Multiplicity</em>'.
	 * @see org.sidiff.slicer.structural.configuration.SlicingConfiguration#isCheckMultiplicity()
	 * @see #getSlicingConfiguration()
	 * @generated
	 */
	EAttribute getSlicingConfiguration_CheckMultiplicity();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.slicer.structural.configuration.SlicingConfiguration#getConstraintInterpreterID <em>Constraint Interpreter ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Constraint Interpreter ID</em>'.
	 * @see org.sidiff.slicer.structural.configuration.SlicingConfiguration#getConstraintInterpreterID()
	 * @see #getSlicingConfiguration()
	 * @generated
	 */
	EAttribute getSlicingConfiguration_ConstraintInterpreterID();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.slicer.structural.configuration.SlicingConfiguration#getConstraintInterpreter <em>Constraint Interpreter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Constraint Interpreter</em>'.
	 * @see org.sidiff.slicer.structural.configuration.SlicingConfiguration#getConstraintInterpreter()
	 * @see #getSlicingConfiguration()
	 * @generated
	 */
	EAttribute getSlicingConfiguration_ConstraintInterpreter();

	/**
	 * Returns the meta object for class '{@link org.sidiff.slicer.structural.configuration.SlicedEClass <em>Sliced EClass</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Sliced EClass</em>'.
	 * @see org.sidiff.slicer.structural.configuration.SlicedEClass
	 * @generated
	 */
	EClass getSlicedEClass();

	/**
	 * Returns the meta object for the container reference '{@link org.sidiff.slicer.structural.configuration.SlicedEClass#getSlicingConfiguration <em>Slicing Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Slicing Configuration</em>'.
	 * @see org.sidiff.slicer.structural.configuration.SlicedEClass#getSlicingConfiguration()
	 * @see #getSlicedEClass()
	 * @generated
	 */
	EReference getSlicedEClass_SlicingConfiguration();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.slicer.structural.configuration.SlicedEClass#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see org.sidiff.slicer.structural.configuration.SlicedEClass#getType()
	 * @see #getSlicedEClass()
	 * @generated
	 */
	EReference getSlicedEClass_Type();

	/**
	 * Returns the meta object for the containment reference '{@link org.sidiff.slicer.structural.configuration.SlicedEClass#getConstraint <em>Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Constraint</em>'.
	 * @see org.sidiff.slicer.structural.configuration.SlicedEClass#getConstraint()
	 * @see #getSlicedEClass()
	 * @generated
	 */
	EReference getSlicedEClass_Constraint();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.slicer.structural.configuration.SlicedEClass#getSlicedEReferences <em>Sliced EReferences</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Sliced EReferences</em>'.
	 * @see org.sidiff.slicer.structural.configuration.SlicedEClass#getSlicedEReferences()
	 * @see #getSlicedEClass()
	 * @generated
	 */
	EReference getSlicedEClass_SlicedEReferences();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.slicer.structural.configuration.SlicedEClass#getOppositeSlicedEReferenceType <em>Opposite Sliced EReference Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Opposite Sliced EReference Type</em>'.
	 * @see org.sidiff.slicer.structural.configuration.SlicedEClass#getOppositeSlicedEReferenceType()
	 * @see #getSlicedEClass()
	 * @generated
	 */
	EAttribute getSlicedEClass_OppositeSlicedEReferenceType();

	/**
	 * Returns the meta object for class '{@link org.sidiff.slicer.structural.configuration.Constraint <em>Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Constraint</em>'.
	 * @see org.sidiff.slicer.structural.configuration.Constraint
	 * @generated
	 */
	EClass getConstraint();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.slicer.structural.configuration.Constraint#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Expression</em>'.
	 * @see org.sidiff.slicer.structural.configuration.Constraint#getExpression()
	 * @see #getConstraint()
	 * @generated
	 */
	EAttribute getConstraint_Expression();

	/**
	 * Returns the meta object for class '{@link org.sidiff.slicer.structural.configuration.SlicedEReference <em>Sliced EReference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Sliced EReference</em>'.
	 * @see org.sidiff.slicer.structural.configuration.SlicedEReference
	 * @generated
	 */
	EClass getSlicedEReference();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.slicer.structural.configuration.SlicedEReference#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see org.sidiff.slicer.structural.configuration.SlicedEReference#getType()
	 * @see #getSlicedEReference()
	 * @generated
	 */
	EReference getSlicedEReference_Type();

	/**
	 * Returns the meta object for the container reference '{@link org.sidiff.slicer.structural.configuration.SlicedEReference#getSlicedEClass <em>Sliced EClass</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Sliced EClass</em>'.
	 * @see org.sidiff.slicer.structural.configuration.SlicedEReference#getSlicedEClass()
	 * @see #getSlicedEReference()
	 * @generated
	 */
	EReference getSlicedEReference_SlicedEClass();

	/**
	 * Returns the meta object for the containment reference '{@link org.sidiff.slicer.structural.configuration.SlicedEReference#getConstraint <em>Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Constraint</em>'.
	 * @see org.sidiff.slicer.structural.configuration.SlicedEReference#getConstraint()
	 * @see #getSlicedEReference()
	 * @generated
	 */
	EReference getSlicedEReference_Constraint();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.slicer.structural.configuration.SlicedEReference#getOverwrite <em>Overwrite</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Overwrite</em>'.
	 * @see org.sidiff.slicer.structural.configuration.SlicedEReference#getOverwrite()
	 * @see #getSlicedEReference()
	 * @generated
	 */
	EReference getSlicedEReference_Overwrite();

	/**
	 * Returns the meta object for enum '{@link org.sidiff.slicer.structural.configuration.SlicingMode <em>Slicing Mode</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Slicing Mode</em>'.
	 * @see org.sidiff.slicer.structural.configuration.SlicingMode
	 * @generated
	 */
	EEnum getSlicingMode();

	/**
	 * Returns the meta object for data type '{@link org.sidiff.slicer.structural.configuration.IConstraintInterpreter <em>IConstraint Interpreter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>IConstraint Interpreter</em>'.
	 * @see org.sidiff.slicer.structural.configuration.IConstraintInterpreter
	 * @model instanceClass="org.sidiff.slicer.structural.configuration.IConstraintInterpreter" serializeable="false"
	 * @generated
	 */
	EDataType getIConstraintInterpreter();

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
		 * The meta object literal for the '{@link org.sidiff.slicer.structural.configuration.impl.SlicingConfigurationImpl <em>Slicing Configuration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.slicer.structural.configuration.impl.SlicingConfigurationImpl
		 * @see org.sidiff.slicer.structural.configuration.impl.ConfigurationPackageImpl#getSlicingConfiguration()
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
		 * The meta object literal for the '<em><b>Document Types</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SLICING_CONFIGURATION__DOCUMENT_TYPES = eINSTANCE.getSlicingConfiguration_DocumentTypes();

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
		 * The meta object literal for the '<em><b>Opposite Sliced EClass Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SLICING_CONFIGURATION__OPPOSITE_SLICED_ECLASS_TYPE = eINSTANCE.getSlicingConfiguration_OppositeSlicedEClassType();

		/**
		 * The meta object literal for the '<em><b>Check Multiplicity</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SLICING_CONFIGURATION__CHECK_MULTIPLICITY = eINSTANCE.getSlicingConfiguration_CheckMultiplicity();

		/**
		 * The meta object literal for the '<em><b>Constraint Interpreter ID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SLICING_CONFIGURATION__CONSTRAINT_INTERPRETER_ID = eINSTANCE.getSlicingConfiguration_ConstraintInterpreterID();

		/**
		 * The meta object literal for the '<em><b>Constraint Interpreter</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SLICING_CONFIGURATION__CONSTRAINT_INTERPRETER = eINSTANCE.getSlicingConfiguration_ConstraintInterpreter();

		/**
		 * The meta object literal for the '{@link org.sidiff.slicer.structural.configuration.impl.SlicedEClassImpl <em>Sliced EClass</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.slicer.structural.configuration.impl.SlicedEClassImpl
		 * @see org.sidiff.slicer.structural.configuration.impl.ConfigurationPackageImpl#getSlicedEClass()
		 * @generated
		 */
		EClass SLICED_ECLASS = eINSTANCE.getSlicedEClass();

		/**
		 * The meta object literal for the '<em><b>Slicing Configuration</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SLICED_ECLASS__SLICING_CONFIGURATION = eINSTANCE.getSlicedEClass_SlicingConfiguration();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SLICED_ECLASS__TYPE = eINSTANCE.getSlicedEClass_Type();

		/**
		 * The meta object literal for the '<em><b>Constraint</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SLICED_ECLASS__CONSTRAINT = eINSTANCE.getSlicedEClass_Constraint();

		/**
		 * The meta object literal for the '<em><b>Sliced EReferences</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SLICED_ECLASS__SLICED_EREFERENCES = eINSTANCE.getSlicedEClass_SlicedEReferences();

		/**
		 * The meta object literal for the '<em><b>Opposite Sliced EReference Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SLICED_ECLASS__OPPOSITE_SLICED_EREFERENCE_TYPE = eINSTANCE.getSlicedEClass_OppositeSlicedEReferenceType();

		/**
		 * The meta object literal for the '{@link org.sidiff.slicer.structural.configuration.impl.ConstraintImpl <em>Constraint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.slicer.structural.configuration.impl.ConstraintImpl
		 * @see org.sidiff.slicer.structural.configuration.impl.ConfigurationPackageImpl#getConstraint()
		 * @generated
		 */
		EClass CONSTRAINT = eINSTANCE.getConstraint();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONSTRAINT__EXPRESSION = eINSTANCE.getConstraint_Expression();

		/**
		 * The meta object literal for the '{@link org.sidiff.slicer.structural.configuration.impl.SlicedEReferenceImpl <em>Sliced EReference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.slicer.structural.configuration.impl.SlicedEReferenceImpl
		 * @see org.sidiff.slicer.structural.configuration.impl.ConfigurationPackageImpl#getSlicedEReference()
		 * @generated
		 */
		EClass SLICED_EREFERENCE = eINSTANCE.getSlicedEReference();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SLICED_EREFERENCE__TYPE = eINSTANCE.getSlicedEReference_Type();

		/**
		 * The meta object literal for the '<em><b>Sliced EClass</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SLICED_EREFERENCE__SLICED_ECLASS = eINSTANCE.getSlicedEReference_SlicedEClass();

		/**
		 * The meta object literal for the '<em><b>Constraint</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SLICED_EREFERENCE__CONSTRAINT = eINSTANCE.getSlicedEReference_Constraint();

		/**
		 * The meta object literal for the '<em><b>Overwrite</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SLICED_EREFERENCE__OVERWRITE = eINSTANCE.getSlicedEReference_Overwrite();

		/**
		 * The meta object literal for the '{@link org.sidiff.slicer.structural.configuration.SlicingMode <em>Slicing Mode</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.slicer.structural.configuration.SlicingMode
		 * @see org.sidiff.slicer.structural.configuration.impl.ConfigurationPackageImpl#getSlicingMode()
		 * @generated
		 */
		EEnum SLICING_MODE = eINSTANCE.getSlicingMode();

		/**
		 * The meta object literal for the '<em>IConstraint Interpreter</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.slicer.structural.configuration.IConstraintInterpreter
		 * @see org.sidiff.slicer.structural.configuration.impl.ConfigurationPackageImpl#getIConstraintInterpreter()
		 * @generated
		 */
		EDataType ICONSTRAINT_INTERPRETER = eINSTANCE.getIConstraintInterpreter();

	}

} //ConfigurationPackage
