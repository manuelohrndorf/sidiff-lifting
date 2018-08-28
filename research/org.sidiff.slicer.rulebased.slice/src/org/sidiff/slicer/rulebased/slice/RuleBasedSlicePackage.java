/**
 */
package org.sidiff.slicer.rulebased.slice;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.sidiff.slicer.slice.SlicePackage;

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
 * @see org.sidiff.slicer.rulebased.slice.RuleBasedSliceFactory
 * @model kind="package"
 * @generated
 */
public interface RuleBasedSlicePackage extends EPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "(c), Christopher Pietsch, Software Engineering Group, University of Siegen 2017 all rights reserved";

	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "slice";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.example.org/RuleBasedSlice";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "RuleBasedSlice";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	RuleBasedSlicePackage eINSTANCE = org.sidiff.slicer.rulebased.slice.impl.RuleBasedSlicePackageImpl.init();

	/**
	 * The meta object id for the '{@link org.sidiff.slicer.rulebased.slice.impl.ExecutableModelSliceImpl <em>Executable Model Slice</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.slicer.rulebased.slice.impl.ExecutableModelSliceImpl
	 * @see org.sidiff.slicer.rulebased.slice.impl.RuleBasedSlicePackageImpl#getExecutableModelSlice()
	 * @generated
	 */
	int EXECUTABLE_MODEL_SLICE = 0;

	/**
	 * The feature id for the '<em><b>Sliced Elements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXECUTABLE_MODEL_SLICE__SLICED_ELEMENTS = SlicePackage.MODEL_SLICE__SLICED_ELEMENTS;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXECUTABLE_MODEL_SLICE__TYPE = SlicePackage.MODEL_SLICE__TYPE;

	/**
	 * The feature id for the '<em><b>Asymmetric Difference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXECUTABLE_MODEL_SLICE__ASYMMETRIC_DIFFERENCE = SlicePackage.MODEL_SLICE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Executable Model Slice</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXECUTABLE_MODEL_SLICE_FEATURE_COUNT = SlicePackage.MODEL_SLICE_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Export</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXECUTABLE_MODEL_SLICE___EXPORT__RESOURCE = SlicePackage.MODEL_SLICE___EXPORT__RESOURCE;

	/**
	 * The operation id for the '<em>Serialize</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXECUTABLE_MODEL_SLICE___SERIALIZE__STRING = SlicePackage.MODEL_SLICE___SERIALIZE__STRING;

	/**
	 * The operation id for the '<em>Serialize</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXECUTABLE_MODEL_SLICE___SERIALIZE__STRING_BOOLEAN = SlicePackage.MODEL_SLICE_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Executable Model Slice</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXECUTABLE_MODEL_SLICE_OPERATION_COUNT = SlicePackage.MODEL_SLICE_OPERATION_COUNT + 1;


	/**
	 * Returns the meta object for class '{@link org.sidiff.slicer.rulebased.slice.ExecutableModelSlice <em>Executable Model Slice</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Executable Model Slice</em>'.
	 * @see org.sidiff.slicer.rulebased.slice.ExecutableModelSlice
	 * @generated
	 */
	EClass getExecutableModelSlice();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.slicer.rulebased.slice.ExecutableModelSlice#getAsymmetricDifference <em>Asymmetric Difference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Asymmetric Difference</em>'.
	 * @see org.sidiff.slicer.rulebased.slice.ExecutableModelSlice#getAsymmetricDifference()
	 * @see #getExecutableModelSlice()
	 * @generated
	 */
	EReference getExecutableModelSlice_AsymmetricDifference();

	/**
	 * Returns the meta object for the '{@link org.sidiff.slicer.rulebased.slice.ExecutableModelSlice#serialize(java.lang.String, boolean) <em>Serialize</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Serialize</em>' operation.
	 * @see org.sidiff.slicer.rulebased.slice.ExecutableModelSlice#serialize(java.lang.String, boolean)
	 * @generated
	 */
	EOperation getExecutableModelSlice__Serialize__String_boolean();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	RuleBasedSliceFactory getRuleBasedSliceFactory();

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
		 * The meta object literal for the '{@link org.sidiff.slicer.rulebased.slice.impl.ExecutableModelSliceImpl <em>Executable Model Slice</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.slicer.rulebased.slice.impl.ExecutableModelSliceImpl
		 * @see org.sidiff.slicer.rulebased.slice.impl.RuleBasedSlicePackageImpl#getExecutableModelSlice()
		 * @generated
		 */
		EClass EXECUTABLE_MODEL_SLICE = eINSTANCE.getExecutableModelSlice();

		/**
		 * The meta object literal for the '<em><b>Asymmetric Difference</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXECUTABLE_MODEL_SLICE__ASYMMETRIC_DIFFERENCE = eINSTANCE.getExecutableModelSlice_AsymmetricDifference();

		/**
		 * The meta object literal for the '<em><b>Serialize</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation EXECUTABLE_MODEL_SLICE___SERIALIZE__STRING_BOOLEAN = eINSTANCE.getExecutableModelSlice__Serialize__String_boolean();

	}

} //RuleBasedSlicePackage
