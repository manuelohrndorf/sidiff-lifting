/**
 */
package org.sidiff.difference.symmetricprofiled;

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
	 * The meta object id for the '{@link org.sidiff.difference.symmetricprofiled.impl.ProfiledSDImpl <em>Profiled SD</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.difference.symmetricprofiled.impl.ProfiledSDImpl
	 * @see org.sidiff.difference.symmetricprofiled.impl.SymmetricProfiledPackageImpl#getProfiledSD()
	 * @generated
	 */
	int PROFILED_SD = 0;

	/**
	 * The feature id for the '<em><b>Profiledscss</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROFILED_SD__PROFILEDSCSS = 0;

	/**
	 * The feature id for the '<em><b>Sd</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROFILED_SD__SD = 1;

	/**
	 * The feature id for the '<em><b>Unprofiledscss</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROFILED_SD__UNPROFILEDSCSS = 2;

	/**
	 * The number of structural features of the '<em>Profiled SD</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROFILED_SD_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Profiled SD</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROFILED_SD_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.sidiff.difference.symmetricprofiled.impl.ProfiledSCSImpl <em>Profiled SCS</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.difference.symmetricprofiled.impl.ProfiledSCSImpl
	 * @see org.sidiff.difference.symmetricprofiled.impl.SymmetricProfiledPackageImpl#getProfiledSCS()
	 * @generated
	 */
	int PROFILED_SCS = 1;

	/**
	 * The feature id for the '<em><b>Scs</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROFILED_SCS__SCS = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROFILED_SCS__NAME = 1;

	/**
	 * The feature id for the '<em><b>Applied Stereotypes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROFILED_SCS__APPLIED_STEREOTYPES = 2;

	/**
	 * The number of structural features of the '<em>Profiled SCS</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROFILED_SCS_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Profiled SCS</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROFILED_SCS_OPERATION_COUNT = 0;

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
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLIED_STEREOTYPE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Stereo Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLIED_STEREOTYPE__STEREO_TYPE = 1;

	/**
	 * The feature id for the '<em><b>Base Object</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLIED_STEREOTYPE__BASE_OBJECT = 2;

	/**
	 * The feature id for the '<em><b>Base Reference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLIED_STEREOTYPE__BASE_REFERENCE = 3;

	/**
	 * The feature id for the '<em><b>Profile</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLIED_STEREOTYPE__PROFILE = 4;

	/**
	 * The number of structural features of the '<em>Applied Stereotype</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLIED_STEREOTYPE_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Applied Stereotype</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLIED_STEREOTYPE_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link org.sidiff.difference.symmetricprofiled.ProfiledSD <em>Profiled SD</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Profiled SD</em>'.
	 * @see org.sidiff.difference.symmetricprofiled.ProfiledSD
	 * @generated
	 */
	EClass getProfiledSD();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.difference.symmetricprofiled.ProfiledSD#getProfiledscss <em>Profiledscss</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Profiledscss</em>'.
	 * @see org.sidiff.difference.symmetricprofiled.ProfiledSD#getProfiledscss()
	 * @see #getProfiledSD()
	 * @generated
	 */
	EReference getProfiledSD_Profiledscss();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.difference.symmetricprofiled.ProfiledSD#getSd <em>Sd</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Sd</em>'.
	 * @see org.sidiff.difference.symmetricprofiled.ProfiledSD#getSd()
	 * @see #getProfiledSD()
	 * @generated
	 */
	EReference getProfiledSD_Sd();

	/**
	 * Returns the meta object for the reference list '{@link org.sidiff.difference.symmetricprofiled.ProfiledSD#getUnprofiledscss <em>Unprofiledscss</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Unprofiledscss</em>'.
	 * @see org.sidiff.difference.symmetricprofiled.ProfiledSD#getUnprofiledscss()
	 * @see #getProfiledSD()
	 * @generated
	 */
	EReference getProfiledSD_Unprofiledscss();

	/**
	 * Returns the meta object for class '{@link org.sidiff.difference.symmetricprofiled.ProfiledSCS <em>Profiled SCS</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Profiled SCS</em>'.
	 * @see org.sidiff.difference.symmetricprofiled.ProfiledSCS
	 * @generated
	 */
	EClass getProfiledSCS();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.difference.symmetricprofiled.ProfiledSCS#getScs <em>Scs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Scs</em>'.
	 * @see org.sidiff.difference.symmetricprofiled.ProfiledSCS#getScs()
	 * @see #getProfiledSCS()
	 * @generated
	 */
	EReference getProfiledSCS_Scs();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.difference.symmetricprofiled.ProfiledSCS#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.sidiff.difference.symmetricprofiled.ProfiledSCS#getName()
	 * @see #getProfiledSCS()
	 * @generated
	 */
	EAttribute getProfiledSCS_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.difference.symmetricprofiled.ProfiledSCS#getAppliedStereotypes <em>Applied Stereotypes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Applied Stereotypes</em>'.
	 * @see org.sidiff.difference.symmetricprofiled.ProfiledSCS#getAppliedStereotypes()
	 * @see #getProfiledSCS()
	 * @generated
	 */
	EReference getProfiledSCS_AppliedStereotypes();

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
	 * Returns the meta object for the reference '{@link org.sidiff.difference.symmetricprofiled.AppliedStereotype#getBaseReference <em>Base Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Base Reference</em>'.
	 * @see org.sidiff.difference.symmetricprofiled.AppliedStereotype#getBaseReference()
	 * @see #getAppliedStereotype()
	 * @generated
	 */
	EReference getAppliedStereotype_BaseReference();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.difference.symmetricprofiled.AppliedStereotype#getProfile <em>Profile</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Profile</em>'.
	 * @see org.sidiff.difference.symmetricprofiled.AppliedStereotype#getProfile()
	 * @see #getAppliedStereotype()
	 * @generated
	 */
	EReference getAppliedStereotype_Profile();

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
		 * The meta object literal for the '{@link org.sidiff.difference.symmetricprofiled.impl.ProfiledSDImpl <em>Profiled SD</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.difference.symmetricprofiled.impl.ProfiledSDImpl
		 * @see org.sidiff.difference.symmetricprofiled.impl.SymmetricProfiledPackageImpl#getProfiledSD()
		 * @generated
		 */
		EClass PROFILED_SD = eINSTANCE.getProfiledSD();

		/**
		 * The meta object literal for the '<em><b>Profiledscss</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROFILED_SD__PROFILEDSCSS = eINSTANCE.getProfiledSD_Profiledscss();

		/**
		 * The meta object literal for the '<em><b>Sd</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROFILED_SD__SD = eINSTANCE.getProfiledSD_Sd();

		/**
		 * The meta object literal for the '<em><b>Unprofiledscss</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROFILED_SD__UNPROFILEDSCSS = eINSTANCE.getProfiledSD_Unprofiledscss();

		/**
		 * The meta object literal for the '{@link org.sidiff.difference.symmetricprofiled.impl.ProfiledSCSImpl <em>Profiled SCS</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.difference.symmetricprofiled.impl.ProfiledSCSImpl
		 * @see org.sidiff.difference.symmetricprofiled.impl.SymmetricProfiledPackageImpl#getProfiledSCS()
		 * @generated
		 */
		EClass PROFILED_SCS = eINSTANCE.getProfiledSCS();

		/**
		 * The meta object literal for the '<em><b>Scs</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROFILED_SCS__SCS = eINSTANCE.getProfiledSCS_Scs();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROFILED_SCS__NAME = eINSTANCE.getProfiledSCS_Name();

		/**
		 * The meta object literal for the '<em><b>Applied Stereotypes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROFILED_SCS__APPLIED_STEREOTYPES = eINSTANCE.getProfiledSCS_AppliedStereotypes();

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
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute APPLIED_STEREOTYPE__NAME = eINSTANCE.getAppliedStereotype_Name();

		/**
		 * The meta object literal for the '<em><b>Stereo Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference APPLIED_STEREOTYPE__STEREO_TYPE = eINSTANCE.getAppliedStereotype_StereoType();

		/**
		 * The meta object literal for the '<em><b>Base Object</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference APPLIED_STEREOTYPE__BASE_OBJECT = eINSTANCE.getAppliedStereotype_BaseObject();

		/**
		 * The meta object literal for the '<em><b>Base Reference</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference APPLIED_STEREOTYPE__BASE_REFERENCE = eINSTANCE.getAppliedStereotype_BaseReference();

		/**
		 * The meta object literal for the '<em><b>Profile</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference APPLIED_STEREOTYPE__PROFILE = eINSTANCE.getAppliedStereotype_Profile();

	}

} //SymmetricProfiledPackage
