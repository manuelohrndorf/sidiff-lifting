/**
 */
package org.sidiff.slicing.configuration;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
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
	String eNS_URI = "http://www.sidiff.org/slicing/configuration/0.1";

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
	 * The feature id for the '<em><b>Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICING_CONFIGURATION__CONSTRAINTS = 7;

	/**
	 * The feature id for the '<em><b>Constraintinterpreter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICING_CONFIGURATION__CONSTRAINTINTERPRETER = 8;

	/**
	 * The number of structural features of the '<em>Slicing Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICING_CONFIGURATION_FEATURE_COUNT = 9;

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
	 * The feature id for the '<em><b>Constraints</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICED_ECLASS__CONSTRAINTS = 2;

	/**
	 * The number of structural features of the '<em>Sliced EClass</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICED_ECLASS_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Sliced EClass</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICED_ECLASS_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.sidiff.slicing.configuration.impl.ConstraintImpl <em>Constraint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.slicing.configuration.impl.ConstraintImpl
	 * @see org.sidiff.slicing.configuration.impl.ConfigurationPackageImpl#getConstraint()
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
	 * The meta object id for the '{@link org.sidiff.slicing.configuration.IConstraintInterpreter <em>IConstraint Interpreter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.slicing.configuration.IConstraintInterpreter
	 * @see org.sidiff.slicing.configuration.impl.ConfigurationPackageImpl#getIConstraintInterpreter()
	 * @generated
	 */
	int ICONSTRAINT_INTERPRETER = 3;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICONSTRAINT_INTERPRETER__KEY = 0;

	/**
	 * The number of structural features of the '<em>IConstraint Interpreter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICONSTRAINT_INTERPRETER_FEATURE_COUNT = 1;

	/**
	 * The operation id for the '<em>Evaluate</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICONSTRAINT_INTERPRETER___EVALUATE__CONSTRAINT_EOBJECT = 0;

	/**
	 * The operation id for the '<em>Evaluate</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICONSTRAINT_INTERPRETER___EVALUATE__ELIST_EOBJECT = 1;

	/**
	 * The number of operations of the '<em>IConstraint Interpreter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICONSTRAINT_INTERPRETER_OPERATION_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.sidiff.slicing.configuration.impl.OCLConstraintInterpreterImpl <em>OCL Constraint Interpreter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.slicing.configuration.impl.OCLConstraintInterpreterImpl
	 * @see org.sidiff.slicing.configuration.impl.ConfigurationPackageImpl#getOCLConstraintInterpreter()
	 * @generated
	 */
	int OCL_CONSTRAINT_INTERPRETER = 4;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OCL_CONSTRAINT_INTERPRETER__KEY = ICONSTRAINT_INTERPRETER__KEY;

	/**
	 * The feature id for the '<em><b>Ocl</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OCL_CONSTRAINT_INTERPRETER__OCL = ICONSTRAINT_INTERPRETER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Ocl Helper</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OCL_CONSTRAINT_INTERPRETER__OCL_HELPER = ICONSTRAINT_INTERPRETER_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>OCL Constraint Interpreter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OCL_CONSTRAINT_INTERPRETER_FEATURE_COUNT = ICONSTRAINT_INTERPRETER_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Evaluate</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OCL_CONSTRAINT_INTERPRETER___EVALUATE__CONSTRAINT_EOBJECT = ICONSTRAINT_INTERPRETER___EVALUATE__CONSTRAINT_EOBJECT;

	/**
	 * The operation id for the '<em>Evaluate</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OCL_CONSTRAINT_INTERPRETER___EVALUATE__ELIST_EOBJECT = ICONSTRAINT_INTERPRETER___EVALUATE__ELIST_EOBJECT;

	/**
	 * The number of operations of the '<em>OCL Constraint Interpreter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OCL_CONSTRAINT_INTERPRETER_OPERATION_COUNT = ICONSTRAINT_INTERPRETER_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.sidiff.slicing.configuration.SlicingMode <em>Slicing Mode</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.slicing.configuration.SlicingMode
	 * @see org.sidiff.slicing.configuration.impl.ConfigurationPackageImpl#getSlicingMode()
	 * @generated
	 */
	int SLICING_MODE = 5;


	/**
	 * The meta object id for the '<em>EOCL</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.ocl.ecore.OCL
	 * @see org.sidiff.slicing.configuration.impl.ConfigurationPackageImpl#getEOCL()
	 * @generated
	 */
	int EOCL = 6;

	/**
	 * The meta object id for the '<em>EOCL Helper</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.ocl.helper.OCLHelper
	 * @see org.sidiff.slicing.configuration.impl.ConfigurationPackageImpl#getEOCLHelper()
	 * @generated
	 */
	int EOCL_HELPER = 7;


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
	 * Returns the meta object for the attribute list '{@link org.sidiff.slicing.configuration.SlicingConfiguration#getDocumentTypes <em>Document Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Document Types</em>'.
	 * @see org.sidiff.slicing.configuration.SlicingConfiguration#getDocumentTypes()
	 * @see #getSlicingConfiguration()
	 * @generated
	 */
	EAttribute getSlicingConfiguration_DocumentTypes();

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
	 * Returns the meta object for the attribute '{@link org.sidiff.slicing.configuration.SlicingConfiguration#getOppositeSlicedEClassType <em>Opposite Sliced EClass Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Opposite Sliced EClass Type</em>'.
	 * @see org.sidiff.slicing.configuration.SlicingConfiguration#getOppositeSlicedEClassType()
	 * @see #getSlicingConfiguration()
	 * @generated
	 */
	EAttribute getSlicingConfiguration_OppositeSlicedEClassType();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.slicing.configuration.SlicingConfiguration#getConstraints <em>Constraints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Constraints</em>'.
	 * @see org.sidiff.slicing.configuration.SlicingConfiguration#getConstraints()
	 * @see #getSlicingConfiguration()
	 * @generated
	 */
	EReference getSlicingConfiguration_Constraints();

	/**
	 * Returns the meta object for the containment reference '{@link org.sidiff.slicing.configuration.SlicingConfiguration#getConstraintinterpreter <em>Constraintinterpreter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Constraintinterpreter</em>'.
	 * @see org.sidiff.slicing.configuration.SlicingConfiguration#getConstraintinterpreter()
	 * @see #getSlicingConfiguration()
	 * @generated
	 */
	EReference getSlicingConfiguration_Constraintinterpreter();

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
	 * Returns the meta object for the reference list '{@link org.sidiff.slicing.configuration.SlicedEClass#getConstraints <em>Constraints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Constraints</em>'.
	 * @see org.sidiff.slicing.configuration.SlicedEClass#getConstraints()
	 * @see #getSlicedEClass()
	 * @generated
	 */
	EReference getSlicedEClass_Constraints();

	/**
	 * Returns the meta object for class '{@link org.sidiff.slicing.configuration.Constraint <em>Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Constraint</em>'.
	 * @see org.sidiff.slicing.configuration.Constraint
	 * @generated
	 */
	EClass getConstraint();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.slicing.configuration.Constraint#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Expression</em>'.
	 * @see org.sidiff.slicing.configuration.Constraint#getExpression()
	 * @see #getConstraint()
	 * @generated
	 */
	EAttribute getConstraint_Expression();

	/**
	 * Returns the meta object for class '{@link org.sidiff.slicing.configuration.IConstraintInterpreter <em>IConstraint Interpreter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IConstraint Interpreter</em>'.
	 * @see org.sidiff.slicing.configuration.IConstraintInterpreter
	 * @generated
	 */
	EClass getIConstraintInterpreter();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.slicing.configuration.IConstraintInterpreter#getKey <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see org.sidiff.slicing.configuration.IConstraintInterpreter#getKey()
	 * @see #getIConstraintInterpreter()
	 * @generated
	 */
	EAttribute getIConstraintInterpreter_Key();

	/**
	 * Returns the meta object for the '{@link org.sidiff.slicing.configuration.IConstraintInterpreter#evaluate(org.sidiff.slicing.configuration.Constraint, org.eclipse.emf.ecore.EObject) <em>Evaluate</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Evaluate</em>' operation.
	 * @see org.sidiff.slicing.configuration.IConstraintInterpreter#evaluate(org.sidiff.slicing.configuration.Constraint, org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	EOperation getIConstraintInterpreter__Evaluate__Constraint_EObject();

	/**
	 * Returns the meta object for the '{@link org.sidiff.slicing.configuration.IConstraintInterpreter#evaluate(org.eclipse.emf.common.util.EList, org.eclipse.emf.ecore.EObject) <em>Evaluate</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Evaluate</em>' operation.
	 * @see org.sidiff.slicing.configuration.IConstraintInterpreter#evaluate(org.eclipse.emf.common.util.EList, org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	EOperation getIConstraintInterpreter__Evaluate__EList_EObject();

	/**
	 * Returns the meta object for class '{@link org.sidiff.slicing.configuration.OCLConstraintInterpreter <em>OCL Constraint Interpreter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>OCL Constraint Interpreter</em>'.
	 * @see org.sidiff.slicing.configuration.OCLConstraintInterpreter
	 * @generated
	 */
	EClass getOCLConstraintInterpreter();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.slicing.configuration.OCLConstraintInterpreter#getOcl <em>Ocl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ocl</em>'.
	 * @see org.sidiff.slicing.configuration.OCLConstraintInterpreter#getOcl()
	 * @see #getOCLConstraintInterpreter()
	 * @generated
	 */
	EAttribute getOCLConstraintInterpreter_Ocl();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.slicing.configuration.OCLConstraintInterpreter#getOclHelper <em>Ocl Helper</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ocl Helper</em>'.
	 * @see org.sidiff.slicing.configuration.OCLConstraintInterpreter#getOclHelper()
	 * @see #getOCLConstraintInterpreter()
	 * @generated
	 */
	EAttribute getOCLConstraintInterpreter_OclHelper();

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
	 * Returns the meta object for data type '{@link org.eclipse.ocl.ecore.OCL <em>EOCL</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>EOCL</em>'.
	 * @see org.eclipse.ocl.ecore.OCL
	 * @model instanceClass="org.eclipse.ocl.ecore.OCL"
	 * @generated
	 */
	EDataType getEOCL();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.ocl.helper.OCLHelper <em>EOCL Helper</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>EOCL Helper</em>'.
	 * @see org.eclipse.ocl.helper.OCLHelper
	 * @model instanceClass="org.eclipse.ocl.helper.OCLHelper"
	 * @generated
	 */
	EDataType getEOCLHelper();

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
		 * The meta object literal for the '<em><b>Constraints</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SLICING_CONFIGURATION__CONSTRAINTS = eINSTANCE.getSlicingConfiguration_Constraints();

		/**
		 * The meta object literal for the '<em><b>Constraintinterpreter</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SLICING_CONFIGURATION__CONSTRAINTINTERPRETER = eINSTANCE.getSlicingConfiguration_Constraintinterpreter();

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
		 * The meta object literal for the '<em><b>Constraints</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SLICED_ECLASS__CONSTRAINTS = eINSTANCE.getSlicedEClass_Constraints();

		/**
		 * The meta object literal for the '{@link org.sidiff.slicing.configuration.impl.ConstraintImpl <em>Constraint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.slicing.configuration.impl.ConstraintImpl
		 * @see org.sidiff.slicing.configuration.impl.ConfigurationPackageImpl#getConstraint()
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
		 * The meta object literal for the '{@link org.sidiff.slicing.configuration.IConstraintInterpreter <em>IConstraint Interpreter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.slicing.configuration.IConstraintInterpreter
		 * @see org.sidiff.slicing.configuration.impl.ConfigurationPackageImpl#getIConstraintInterpreter()
		 * @generated
		 */
		EClass ICONSTRAINT_INTERPRETER = eINSTANCE.getIConstraintInterpreter();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ICONSTRAINT_INTERPRETER__KEY = eINSTANCE.getIConstraintInterpreter_Key();

		/**
		 * The meta object literal for the '<em><b>Evaluate</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ICONSTRAINT_INTERPRETER___EVALUATE__CONSTRAINT_EOBJECT = eINSTANCE.getIConstraintInterpreter__Evaluate__Constraint_EObject();

		/**
		 * The meta object literal for the '<em><b>Evaluate</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ICONSTRAINT_INTERPRETER___EVALUATE__ELIST_EOBJECT = eINSTANCE.getIConstraintInterpreter__Evaluate__EList_EObject();

		/**
		 * The meta object literal for the '{@link org.sidiff.slicing.configuration.impl.OCLConstraintInterpreterImpl <em>OCL Constraint Interpreter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.slicing.configuration.impl.OCLConstraintInterpreterImpl
		 * @see org.sidiff.slicing.configuration.impl.ConfigurationPackageImpl#getOCLConstraintInterpreter()
		 * @generated
		 */
		EClass OCL_CONSTRAINT_INTERPRETER = eINSTANCE.getOCLConstraintInterpreter();

		/**
		 * The meta object literal for the '<em><b>Ocl</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OCL_CONSTRAINT_INTERPRETER__OCL = eINSTANCE.getOCLConstraintInterpreter_Ocl();

		/**
		 * The meta object literal for the '<em><b>Ocl Helper</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OCL_CONSTRAINT_INTERPRETER__OCL_HELPER = eINSTANCE.getOCLConstraintInterpreter_OclHelper();

		/**
		 * The meta object literal for the '{@link org.sidiff.slicing.configuration.SlicingMode <em>Slicing Mode</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.slicing.configuration.SlicingMode
		 * @see org.sidiff.slicing.configuration.impl.ConfigurationPackageImpl#getSlicingMode()
		 * @generated
		 */
		EEnum SLICING_MODE = eINSTANCE.getSlicingMode();

		/**
		 * The meta object literal for the '<em>EOCL</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.ocl.ecore.OCL
		 * @see org.sidiff.slicing.configuration.impl.ConfigurationPackageImpl#getEOCL()
		 * @generated
		 */
		EDataType EOCL = eINSTANCE.getEOCL();

		/**
		 * The meta object literal for the '<em>EOCL Helper</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.ocl.helper.OCLHelper
		 * @see org.sidiff.slicing.configuration.impl.ConfigurationPackageImpl#getEOCLHelper()
		 * @generated
		 */
		EDataType EOCL_HELPER = eINSTANCE.getEOCLHelper();

	}

} //ConfigurationPackage
