/**
 */
package org.sidiff.slicing.slicingmodel;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
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
 * @see org.sidiff.slicing.slicingmodel.SlicingmodelFactory
 * @model kind="package"
 * @generated
 */
public interface SlicingmodelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "slicingmodel";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.sidiff.org/slicing/model/0.1";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "slicingmodel";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SlicingmodelPackage eINSTANCE = org.sidiff.slicing.slicingmodel.impl.SlicingmodelPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.sidiff.slicing.slicingmodel.impl.SlicingImpl <em>Slicing</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.slicing.slicingmodel.impl.SlicingImpl
	 * @see org.sidiff.slicing.slicingmodel.impl.SlicingmodelPackageImpl#getSlicing()
	 * @generated
	 */
	int SLICING = 0;

	/**
	 * The feature id for the '<em><b>Uri</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICING__URI = 0;

	/**
	 * The feature id for the '<em><b>Sliced Context Elements</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICING__SLICED_CONTEXT_ELEMENTS = 1;

	/**
	 * The feature id for the '<em><b>Sliced Boundary Elements</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICING__SLICED_BOUNDARY_ELEMENTS = 2;

	/**
	 * The number of structural features of the '<em>Slicing</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICING_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Slicing</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLICING_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link org.sidiff.slicing.slicingmodel.Slicing <em>Slicing</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Slicing</em>'.
	 * @see org.sidiff.slicing.slicingmodel.Slicing
	 * @generated
	 */
	EClass getSlicing();

	/**
	 * Returns the meta object for the attribute list '{@link org.sidiff.slicing.slicingmodel.Slicing#getUri <em>Uri</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Uri</em>'.
	 * @see org.sidiff.slicing.slicingmodel.Slicing#getUri()
	 * @see #getSlicing()
	 * @generated
	 */
	EAttribute getSlicing_Uri();

	/**
	 * Returns the meta object for the reference list '{@link org.sidiff.slicing.slicingmodel.Slicing#getSlicedContextElements <em>Sliced Context Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Sliced Context Elements</em>'.
	 * @see org.sidiff.slicing.slicingmodel.Slicing#getSlicedContextElements()
	 * @see #getSlicing()
	 * @generated
	 */
	EReference getSlicing_SlicedContextElements();

	/**
	 * Returns the meta object for the reference list '{@link org.sidiff.slicing.slicingmodel.Slicing#getSlicedBoundaryElements <em>Sliced Boundary Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Sliced Boundary Elements</em>'.
	 * @see org.sidiff.slicing.slicingmodel.Slicing#getSlicedBoundaryElements()
	 * @see #getSlicing()
	 * @generated
	 */
	EReference getSlicing_SlicedBoundaryElements();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	SlicingmodelFactory getSlicingmodelFactory();

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
		 * The meta object literal for the '{@link org.sidiff.slicing.slicingmodel.impl.SlicingImpl <em>Slicing</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.slicing.slicingmodel.impl.SlicingImpl
		 * @see org.sidiff.slicing.slicingmodel.impl.SlicingmodelPackageImpl#getSlicing()
		 * @generated
		 */
		EClass SLICING = eINSTANCE.getSlicing();

		/**
		 * The meta object literal for the '<em><b>Uri</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SLICING__URI = eINSTANCE.getSlicing_Uri();

		/**
		 * The meta object literal for the '<em><b>Sliced Context Elements</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SLICING__SLICED_CONTEXT_ELEMENTS = eINSTANCE.getSlicing_SlicedContextElements();

		/**
		 * The meta object literal for the '<em><b>Sliced Boundary Elements</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SLICING__SLICED_BOUNDARY_ELEMENTS = eINSTANCE.getSlicing_SlicedBoundaryElements();

	}

} //SlicingmodelPackage
