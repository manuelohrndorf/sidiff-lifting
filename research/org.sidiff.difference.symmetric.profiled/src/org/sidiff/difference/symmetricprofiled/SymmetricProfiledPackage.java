/**
 */
package org.sidiff.difference.symmetricprofiled;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
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
 * @see org.sidiff.difference.symmetricprofiled.SymmetricProfiledFactory
 * @model kind="package"
 * @generated
 */
public interface SymmetricProfiledPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "symmetricprofiled";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.sidiff.org/difference/symmetricprofiled/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "symmetricprofiled";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SymmetricProfiledPackage eINSTANCE = org.sidiff.difference.symmetricprofiled.impl.SymmetricProfiledPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.sidiff.difference.symmetricprofiled.impl.ProfiledSymmetricDifferenceImpl <em>Profiled Symmetric Difference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.difference.symmetricprofiled.impl.ProfiledSymmetricDifferenceImpl
	 * @see org.sidiff.difference.symmetricprofiled.impl.SymmetricProfiledPackageImpl#getProfiledSymmetricDifference()
	 * @generated
	 */
	int PROFILED_SYMMETRIC_DIFFERENCE = 0;

	/**
	 * The feature id for the '<em><b>Profiled Semantic Change Sets</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROFILED_SYMMETRIC_DIFFERENCE__PROFILED_SEMANTIC_CHANGE_SETS = 0;

	/**
	 * The feature id for the '<em><b>Symmetric Difference</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROFILED_SYMMETRIC_DIFFERENCE__SYMMETRIC_DIFFERENCE = 1;

	/**
	 * The number of structural features of the '<em>Profiled Symmetric Difference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROFILED_SYMMETRIC_DIFFERENCE_FEATURE_COUNT = 2;

	/**
	 * The operation id for the '<em>Derive</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROFILED_SYMMETRIC_DIFFERENCE___DERIVE__SYMMETRICDIFFERENCE = 0;

	/**
	 * The number of operations of the '<em>Profiled Symmetric Difference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROFILED_SYMMETRIC_DIFFERENCE_OPERATION_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.sidiff.difference.symmetricprofiled.impl.ProfiledSemanticChangeSetImpl <em>Profiled Semantic Change Set</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.difference.symmetricprofiled.impl.ProfiledSemanticChangeSetImpl
	 * @see org.sidiff.difference.symmetricprofiled.impl.SymmetricProfiledPackageImpl#getProfiledSemanticChangeSet()
	 * @generated
	 */
	int PROFILED_SEMANTIC_CHANGE_SET = 1;

	/**
	 * The feature id for the '<em><b>Semantic Change Set</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROFILED_SEMANTIC_CHANGE_SET__SEMANTIC_CHANGE_SET = 0;

	/**
	 * The feature id for the '<em><b>Applied Stereotypes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROFILED_SEMANTIC_CHANGE_SET__APPLIED_STEREOTYPES = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROFILED_SEMANTIC_CHANGE_SET__NAME = 2;

	/**
	 * The number of structural features of the '<em>Profiled Semantic Change Set</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROFILED_SEMANTIC_CHANGE_SET_FEATURE_COUNT = 3;

	/**
	 * The operation id for the '<em>Add Applied Stereotype</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROFILED_SEMANTIC_CHANGE_SET___ADD_APPLIED_STEREOTYPE__APPLIEDSTEREOTYPE = 0;

	/**
	 * The number of operations of the '<em>Profiled Semantic Change Set</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROFILED_SEMANTIC_CHANGE_SET_OPERATION_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.sidiff.difference.symmetricprofiled.impl.AppliedStereotypeImpl <em>Applied Stereotype</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.difference.symmetricprofiled.impl.AppliedStereotypeImpl
	 * @see org.sidiff.difference.symmetricprofiled.impl.SymmetricProfiledPackageImpl#getAppliedStereotype()
	 * @generated
	 */
	int APPLIED_STEREOTYPE = 2;

	/**
	 * The feature id for the '<em><b>Stereo Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLIED_STEREOTYPE__STEREO_TYPE = 0;

	/**
	 * The feature id for the '<em><b>Base Object</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLIED_STEREOTYPE__BASE_OBJECT = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLIED_STEREOTYPE__NAME = 2;

	/**
	 * The number of structural features of the '<em>Applied Stereotype</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLIED_STEREOTYPE_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Applied Stereotype</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLIED_STEREOTYPE_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link org.sidiff.difference.symmetricprofiled.ProfiledSymmetricDifference <em>Profiled Symmetric Difference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Profiled Symmetric Difference</em>'.
	 * @see org.sidiff.difference.symmetricprofiled.ProfiledSymmetricDifference
	 * @generated
	 */
	EClass getProfiledSymmetricDifference();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.difference.symmetricprofiled.ProfiledSymmetricDifference#getProfiledSemanticChangeSets <em>Profiled Semantic Change Sets</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Profiled Semantic Change Sets</em>'.
	 * @see org.sidiff.difference.symmetricprofiled.ProfiledSymmetricDifference#getProfiledSemanticChangeSets()
	 * @see #getProfiledSymmetricDifference()
	 * @generated
	 */
	EReference getProfiledSymmetricDifference_ProfiledSemanticChangeSets();

	/**
	 * Returns the meta object for the containment reference '{@link org.sidiff.difference.symmetricprofiled.ProfiledSymmetricDifference#getSymmetricDifference <em>Symmetric Difference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Symmetric Difference</em>'.
	 * @see org.sidiff.difference.symmetricprofiled.ProfiledSymmetricDifference#getSymmetricDifference()
	 * @see #getProfiledSymmetricDifference()
	 * @generated
	 */
	EReference getProfiledSymmetricDifference_SymmetricDifference();

	/**
	 * Returns the meta object for the '{@link org.sidiff.difference.symmetricprofiled.ProfiledSymmetricDifference#derive(org.sidiff.difference.symmetric.SymmetricDifference) <em>Derive</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Derive</em>' operation.
	 * @see org.sidiff.difference.symmetricprofiled.ProfiledSymmetricDifference#derive(org.sidiff.difference.symmetric.SymmetricDifference)
	 * @generated
	 */
	EOperation getProfiledSymmetricDifference__Derive__SymmetricDifference();

	/**
	 * Returns the meta object for class '{@link org.sidiff.difference.symmetricprofiled.ProfiledSemanticChangeSet <em>Profiled Semantic Change Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Profiled Semantic Change Set</em>'.
	 * @see org.sidiff.difference.symmetricprofiled.ProfiledSemanticChangeSet
	 * @generated
	 */
	EClass getProfiledSemanticChangeSet();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.difference.symmetricprofiled.ProfiledSemanticChangeSet#getSemanticChangeSet <em>Semantic Change Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Semantic Change Set</em>'.
	 * @see org.sidiff.difference.symmetricprofiled.ProfiledSemanticChangeSet#getSemanticChangeSet()
	 * @see #getProfiledSemanticChangeSet()
	 * @generated
	 */
	EReference getProfiledSemanticChangeSet_SemanticChangeSet();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.difference.symmetricprofiled.ProfiledSemanticChangeSet#getAppliedStereotypes <em>Applied Stereotypes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Applied Stereotypes</em>'.
	 * @see org.sidiff.difference.symmetricprofiled.ProfiledSemanticChangeSet#getAppliedStereotypes()
	 * @see #getProfiledSemanticChangeSet()
	 * @generated
	 */
	EReference getProfiledSemanticChangeSet_AppliedStereotypes();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.difference.symmetricprofiled.ProfiledSemanticChangeSet#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.sidiff.difference.symmetricprofiled.ProfiledSemanticChangeSet#getName()
	 * @see #getProfiledSemanticChangeSet()
	 * @generated
	 */
	EAttribute getProfiledSemanticChangeSet_Name();

	/**
	 * Returns the meta object for the '{@link org.sidiff.difference.symmetricprofiled.ProfiledSemanticChangeSet#addAppliedStereotype(org.sidiff.difference.symmetricprofiled.AppliedStereotype) <em>Add Applied Stereotype</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Add Applied Stereotype</em>' operation.
	 * @see org.sidiff.difference.symmetricprofiled.ProfiledSemanticChangeSet#addAppliedStereotype(org.sidiff.difference.symmetricprofiled.AppliedStereotype)
	 * @generated
	 */
	EOperation getProfiledSemanticChangeSet__AddAppliedStereotype__AppliedStereotype();

	/**
	 * Returns the meta object for class '{@link org.sidiff.difference.symmetricprofiled.AppliedStereotype <em>Applied Stereotype</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Applied Stereotype</em>'.
	 * @see org.sidiff.difference.symmetricprofiled.AppliedStereotype
	 * @generated
	 */
	EClass getAppliedStereotype();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.difference.symmetricprofiled.AppliedStereotype#getStereoType <em>Stereo Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Stereo Type</em>'.
	 * @see org.sidiff.difference.symmetricprofiled.AppliedStereotype#getStereoType()
	 * @see #getAppliedStereotype()
	 * @generated
	 */
	EReference getAppliedStereotype_StereoType();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.difference.symmetricprofiled.AppliedStereotype#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.sidiff.difference.symmetricprofiled.AppliedStereotype#getName()
	 * @see #getAppliedStereotype()
	 * @generated
	 */
	EAttribute getAppliedStereotype_Name();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.difference.symmetricprofiled.AppliedStereotype#getBaseObject <em>Base Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Base Object</em>'.
	 * @see org.sidiff.difference.symmetricprofiled.AppliedStereotype#getBaseObject()
	 * @see #getAppliedStereotype()
	 * @generated
	 */
	EReference getAppliedStereotype_BaseObject();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	SymmetricProfiledFactory getSymmetricProfiledFactory();

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
		 * The meta object literal for the '{@link org.sidiff.difference.symmetricprofiled.impl.ProfiledSymmetricDifferenceImpl <em>Profiled Symmetric Difference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.difference.symmetricprofiled.impl.ProfiledSymmetricDifferenceImpl
		 * @see org.sidiff.difference.symmetricprofiled.impl.SymmetricProfiledPackageImpl#getProfiledSymmetricDifference()
		 * @generated
		 */
		EClass PROFILED_SYMMETRIC_DIFFERENCE = eINSTANCE.getProfiledSymmetricDifference();

		/**
		 * The meta object literal for the '<em><b>Profiled Semantic Change Sets</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROFILED_SYMMETRIC_DIFFERENCE__PROFILED_SEMANTIC_CHANGE_SETS = eINSTANCE.getProfiledSymmetricDifference_ProfiledSemanticChangeSets();

		/**
		 * The meta object literal for the '<em><b>Symmetric Difference</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROFILED_SYMMETRIC_DIFFERENCE__SYMMETRIC_DIFFERENCE = eINSTANCE.getProfiledSymmetricDifference_SymmetricDifference();

		/**
		 * The meta object literal for the '<em><b>Derive</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PROFILED_SYMMETRIC_DIFFERENCE___DERIVE__SYMMETRICDIFFERENCE = eINSTANCE.getProfiledSymmetricDifference__Derive__SymmetricDifference();

		/**
		 * The meta object literal for the '{@link org.sidiff.difference.symmetricprofiled.impl.ProfiledSemanticChangeSetImpl <em>Profiled Semantic Change Set</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.difference.symmetricprofiled.impl.ProfiledSemanticChangeSetImpl
		 * @see org.sidiff.difference.symmetricprofiled.impl.SymmetricProfiledPackageImpl#getProfiledSemanticChangeSet()
		 * @generated
		 */
		EClass PROFILED_SEMANTIC_CHANGE_SET = eINSTANCE.getProfiledSemanticChangeSet();

		/**
		 * The meta object literal for the '<em><b>Semantic Change Set</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROFILED_SEMANTIC_CHANGE_SET__SEMANTIC_CHANGE_SET = eINSTANCE.getProfiledSemanticChangeSet_SemanticChangeSet();

		/**
		 * The meta object literal for the '<em><b>Applied Stereotypes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROFILED_SEMANTIC_CHANGE_SET__APPLIED_STEREOTYPES = eINSTANCE.getProfiledSemanticChangeSet_AppliedStereotypes();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROFILED_SEMANTIC_CHANGE_SET__NAME = eINSTANCE.getProfiledSemanticChangeSet_Name();

		/**
		 * The meta object literal for the '<em><b>Add Applied Stereotype</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PROFILED_SEMANTIC_CHANGE_SET___ADD_APPLIED_STEREOTYPE__APPLIEDSTEREOTYPE = eINSTANCE.getProfiledSemanticChangeSet__AddAppliedStereotype__AppliedStereotype();

		/**
		 * The meta object literal for the '{@link org.sidiff.difference.symmetricprofiled.impl.AppliedStereotypeImpl <em>Applied Stereotype</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.difference.symmetricprofiled.impl.AppliedStereotypeImpl
		 * @see org.sidiff.difference.symmetricprofiled.impl.SymmetricProfiledPackageImpl#getAppliedStereotype()
		 * @generated
		 */
		EClass APPLIED_STEREOTYPE = eINSTANCE.getAppliedStereotype();

		/**
		 * The meta object literal for the '<em><b>Stereo Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference APPLIED_STEREOTYPE__STEREO_TYPE = eINSTANCE.getAppliedStereotype_StereoType();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute APPLIED_STEREOTYPE__NAME = eINSTANCE.getAppliedStereotype_Name();

		/**
		 * The meta object literal for the '<em><b>Base Object</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference APPLIED_STEREOTYPE__BASE_OBJECT = eINSTANCE.getAppliedStereotype_BaseObject();

	}

} //SymmetricProfiledPackage
