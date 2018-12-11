/**
 */
package org.sidiff.slicer.slice;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.sidiff.entities.EntitiesPackage;

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
 * @see org.sidiff.slicer.slice.SliceFactory
 * @model kind="package"
 * @generated
 */
public interface SlicePackage extends EPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "(c), Robert Müller and Christopher Pietsch, Software Engineering Group, University of Siegen 2017 all rights reserved";

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
	String eNS_URI = "http://www.sidiff.org/slice/model/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "slice";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SlicePackage eINSTANCE = org.sidiff.slicer.slice.impl.SlicePackageImpl.init();

	/**
	 * The meta object id for the '{@link org.sidiff.slicer.slice.impl.ModelSliceImpl <em>Model Slice</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.slicer.slice.impl.ModelSliceImpl
	 * @see org.sidiff.slicer.slice.impl.SlicePackageImpl#getModelSlice()
	 * @generated
	 */
	int MODEL_SLICE = 0;

	/**
	 * The feature id for the '<em><b>Sliced Elements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_SLICE__SLICED_ELEMENTS = 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_SLICE__TYPE = 1;

	/**
	 * The number of structural features of the '<em>Model Slice</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_SLICE_FEATURE_COUNT = 2;

	/**
	 * The operation id for the '<em>Export</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_SLICE___EXPORT__PREDICATE = 0;

	/**
	 * The operation id for the '<em>Serialize</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_SLICE___SERIALIZE__STRING = 1;

	/**
	 * The number of operations of the '<em>Model Slice</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_SLICE_OPERATION_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.sidiff.slicer.slice.impl.SlicedElementImpl <em>Sliced Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.slicer.slice.impl.SlicedElementImpl
	 * @see org.sidiff.slicer.slice.impl.SlicePackageImpl#getSlicedElement()
	 * @generated
	 */
	int SLICED_ELEMENT = 1;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICED_ELEMENT__ANNOTATIONS = EntitiesPackage.ELEMENT__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Signature</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICED_ELEMENT__SIGNATURE = EntitiesPackage.ELEMENT__SIGNATURE;

	/**
	 * The feature id for the '<em><b>Uuid</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICED_ELEMENT__UUID = EntitiesPackage.ELEMENT__UUID;

	/**
	 * The feature id for the '<em><b>Outgoings</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICED_ELEMENT__OUTGOINGS = EntitiesPackage.ELEMENT__OUTGOINGS;

	/**
	 * The feature id for the '<em><b>Incomings</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICED_ELEMENT__INCOMINGS = EntitiesPackage.ELEMENT__INCOMINGS;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICED_ELEMENT__ATTRIBUTES = EntitiesPackage.ELEMENT__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Object</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICED_ELEMENT__OBJECT = EntitiesPackage.ELEMENT__OBJECT;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICED_ELEMENT__TYPE = EntitiesPackage.ELEMENT__TYPE;

	/**
	 * The feature id for the '<em><b>External</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICED_ELEMENT__EXTERNAL = EntitiesPackage.ELEMENT__EXTERNAL;

	/**
	 * The feature id for the '<em><b>Sliced References</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICED_ELEMENT__SLICED_REFERENCES = EntitiesPackage.ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Sliced Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICED_ELEMENT__SLICED_ATTRIBUTES = EntitiesPackage.ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Sliced Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICED_ELEMENT_FEATURE_COUNT = EntitiesPackage.ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Get Attributes</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICED_ELEMENT___GET_ATTRIBUTES__EATTRIBUTE = EntitiesPackage.ELEMENT___GET_ATTRIBUTES__EATTRIBUTE;

	/**
	 * The operation id for the '<em>Get References</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICED_ELEMENT___GET_REFERENCES__EREFERENCE = EntitiesPackage.ELEMENT___GET_REFERENCES__EREFERENCE;

	/**
	 * The operation id for the '<em>Update External</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICED_ELEMENT___UPDATE_EXTERNAL = EntitiesPackage.ELEMENT___UPDATE_EXTERNAL;

	/**
	 * The number of operations of the '<em>Sliced Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICED_ELEMENT_OPERATION_COUNT = EntitiesPackage.ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '<em>ICopy Selector</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.util.function.Predicate
	 * @see org.sidiff.slicer.slice.impl.SlicePackageImpl#getICopySelector()
	 * @generated
	 */
	int ICOPY_SELECTOR = 2;

	/**
	 * Returns the meta object for class '{@link org.sidiff.slicer.slice.ModelSlice <em>Model Slice</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Model Slice</em>'.
	 * @see org.sidiff.slicer.slice.ModelSlice
	 * @generated
	 */
	EClass getModelSlice();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.slicer.slice.ModelSlice#getSlicedElements <em>Sliced Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Sliced Elements</em>'.
	 * @see org.sidiff.slicer.slice.ModelSlice#getSlicedElements()
	 * @see #getModelSlice()
	 * @generated
	 */
	EReference getModelSlice_SlicedElements();

	/**
	 * Returns the meta object for the reference list '{@link org.sidiff.slicer.slice.ModelSlice#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Type</em>'.
	 * @see org.sidiff.slicer.slice.ModelSlice#getType()
	 * @see #getModelSlice()
	 * @generated
	 */
	EReference getModelSlice_Type();

	/**
	 * Returns the meta object for the '{@link org.sidiff.slicer.slice.ModelSlice#export(java.util.function.Predicate) <em>Export</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Export</em>' operation.
	 * @see org.sidiff.slicer.slice.ModelSlice#export(java.util.function.Predicate)
	 * @generated
	 */
	EOperation getModelSlice__Export__Predicate();

	/**
	 * Returns the meta object for the '{@link org.sidiff.slicer.slice.ModelSlice#serialize(java.lang.String) <em>Serialize</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Serialize</em>' operation.
	 * @see org.sidiff.slicer.slice.ModelSlice#serialize(java.lang.String)
	 * @generated
	 */
	EOperation getModelSlice__Serialize__String();

	/**
	 * Returns the meta object for class '{@link org.sidiff.slicer.slice.SlicedElement <em>Sliced Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Sliced Element</em>'.
	 * @see org.sidiff.slicer.slice.SlicedElement
	 * @generated
	 */
	EClass getSlicedElement();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.slicer.slice.SlicedElement#getSlicedReferences <em>Sliced References</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Sliced References</em>'.
	 * @see org.sidiff.slicer.slice.SlicedElement#getSlicedReferences()
	 * @see #getSlicedElement()
	 * @generated
	 */
	EReference getSlicedElement_SlicedReferences();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.slicer.slice.SlicedElement#getSlicedAttributes <em>Sliced Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Sliced Attributes</em>'.
	 * @see org.sidiff.slicer.slice.SlicedElement#getSlicedAttributes()
	 * @see #getSlicedElement()
	 * @generated
	 */
	EReference getSlicedElement_SlicedAttributes();

	/**
	 * Returns the meta object for data type '{@link java.util.function.Predicate <em>ICopy Selector</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>ICopy Selector</em>'.
	 * @see java.util.function.Predicate
	 * @model instanceClass="java.util.function.Predicate&lt;org.eclipse.emf.ecore.EObject&gt;" serializeable="false"
	 * @generated
	 */
	EDataType getICopySelector();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	SliceFactory getSliceFactory();

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
		 * The meta object literal for the '{@link org.sidiff.slicer.slice.impl.ModelSliceImpl <em>Model Slice</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.slicer.slice.impl.ModelSliceImpl
		 * @see org.sidiff.slicer.slice.impl.SlicePackageImpl#getModelSlice()
		 * @generated
		 */
		EClass MODEL_SLICE = eINSTANCE.getModelSlice();

		/**
		 * The meta object literal for the '<em><b>Sliced Elements</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MODEL_SLICE__SLICED_ELEMENTS = eINSTANCE.getModelSlice_SlicedElements();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MODEL_SLICE__TYPE = eINSTANCE.getModelSlice_Type();

		/**
		 * The meta object literal for the '<em><b>Export</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation MODEL_SLICE___EXPORT__PREDICATE = eINSTANCE.getModelSlice__Export__Predicate();

		/**
		 * The meta object literal for the '<em><b>Serialize</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation MODEL_SLICE___SERIALIZE__STRING = eINSTANCE.getModelSlice__Serialize__String();

		/**
		 * The meta object literal for the '{@link org.sidiff.slicer.slice.impl.SlicedElementImpl <em>Sliced Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.slicer.slice.impl.SlicedElementImpl
		 * @see org.sidiff.slicer.slice.impl.SlicePackageImpl#getSlicedElement()
		 * @generated
		 */
		EClass SLICED_ELEMENT = eINSTANCE.getSlicedElement();

		/**
		 * The meta object literal for the '<em><b>Sliced References</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SLICED_ELEMENT__SLICED_REFERENCES = eINSTANCE.getSlicedElement_SlicedReferences();

		/**
		 * The meta object literal for the '<em><b>Sliced Attributes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SLICED_ELEMENT__SLICED_ATTRIBUTES = eINSTANCE.getSlicedElement_SlicedAttributes();

		/**
		 * The meta object literal for the '<em>ICopy Selector</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.util.function.Predicate
		 * @see org.sidiff.slicer.slice.impl.SlicePackageImpl#getICopySelector()
		 * @generated
		 */
		EDataType ICOPY_SELECTOR = eINSTANCE.getICopySelector();

	}

} //SlicePackage
