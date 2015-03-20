/**
 */
package org.sidiff.patching.patch.patch;

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
 * @see org.sidiff.patching.patch.patch.PatchFactory
 * @model kind="package"
 * @generated
 */
public interface PatchPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "patch";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.sidiff.org/patching/patch/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "patch";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	PatchPackage eINSTANCE = org.sidiff.patching.patch.patch.impl.PatchPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.sidiff.patching.patch.patch.impl.PatchImpl <em>Patch</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.patching.patch.patch.impl.PatchImpl
	 * @see org.sidiff.patching.patch.patch.impl.PatchPackageImpl#getPatch()
	 * @generated
	 */
	int PATCH = 0;

	/**
	 * The feature id for the '<em><b>Asymmetric Difference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATCH__ASYMMETRIC_DIFFERENCE = 0;

	/**
	 * The feature id for the '<em><b>Settings</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATCH__SETTINGS = 1;

	/**
	 * The feature id for the '<em><b>Edit Rules</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATCH__EDIT_RULES = 2;

	/**
	 * The number of structural features of the '<em>Patch</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATCH_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Patch</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATCH_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.sidiff.patching.patch.patch.impl.SettingImpl <em>Setting</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.patching.patch.patch.impl.SettingImpl
	 * @see org.sidiff.patching.patch.patch.impl.PatchPackageImpl#getSetting()
	 * @generated
	 */
	int SETTING = 1;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SETTING__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SETTING__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Setting</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SETTING_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Setting</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SETTING_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link org.sidiff.patching.patch.patch.Patch <em>Patch</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Patch</em>'.
	 * @see org.sidiff.patching.patch.patch.Patch
	 * @generated
	 */
	EClass getPatch();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.patching.patch.patch.Patch#getAsymmetricDifference <em>Asymmetric Difference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Asymmetric Difference</em>'.
	 * @see org.sidiff.patching.patch.patch.Patch#getAsymmetricDifference()
	 * @see #getPatch()
	 * @generated
	 */
	EReference getPatch_AsymmetricDifference();

	/**
	 * Returns the meta object for the map '{@link org.sidiff.patching.patch.patch.Patch#getSettings <em>Settings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Settings</em>'.
	 * @see org.sidiff.patching.patch.patch.Patch#getSettings()
	 * @see #getPatch()
	 * @generated
	 */
	EReference getPatch_Settings();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.patching.patch.patch.Patch#getEditRules <em>Edit Rules</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Edit Rules</em>'.
	 * @see org.sidiff.patching.patch.patch.Patch#getEditRules()
	 * @see #getPatch()
	 * @generated
	 */
	EReference getPatch_EditRules();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>Setting</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Setting</em>'.
	 * @see java.util.Map.Entry
	 * @model keyDataType="org.eclipse.emf.ecore.EString"
	 *        valueDataType="org.eclipse.emf.ecore.EString"
	 * @generated
	 */
	EClass getSetting();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getSetting()
	 * @generated
	 */
	EAttribute getSetting_Key();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getSetting()
	 * @generated
	 */
	EAttribute getSetting_Value();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	PatchFactory getPatchFactory();

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
		 * The meta object literal for the '{@link org.sidiff.patching.patch.patch.impl.PatchImpl <em>Patch</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.patching.patch.patch.impl.PatchImpl
		 * @see org.sidiff.patching.patch.patch.impl.PatchPackageImpl#getPatch()
		 * @generated
		 */
		EClass PATCH = eINSTANCE.getPatch();

		/**
		 * The meta object literal for the '<em><b>Asymmetric Difference</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PATCH__ASYMMETRIC_DIFFERENCE = eINSTANCE.getPatch_AsymmetricDifference();

		/**
		 * The meta object literal for the '<em><b>Settings</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PATCH__SETTINGS = eINSTANCE.getPatch_Settings();

		/**
		 * The meta object literal for the '<em><b>Edit Rules</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PATCH__EDIT_RULES = eINSTANCE.getPatch_EditRules();

		/**
		 * The meta object literal for the '{@link org.sidiff.patching.patch.patch.impl.SettingImpl <em>Setting</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.patching.patch.patch.impl.SettingImpl
		 * @see org.sidiff.patching.patch.patch.impl.PatchPackageImpl#getSetting()
		 * @generated
		 */
		EClass SETTING = eINSTANCE.getSetting();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SETTING__KEY = eINSTANCE.getSetting_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SETTING__VALUE = eINSTANCE.getSetting_Value();

	}

} //PatchPackage
