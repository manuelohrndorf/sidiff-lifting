/**
 */
package org.sidiff.coevolution.example.swml.crossreferences.datalayer;

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
 * @see org.sidiff.coevolution.example.swml.crossreferences.datalayer.DatalayerFactory
 * @model kind="package"
 * @generated
 */
public interface DatalayerPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "datalayer";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://datalayer/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "datalayer";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	DatalayerPackage eINSTANCE = org.sidiff.coevolution.example.swml.crossreferences.datalayer.impl.DatalayerPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.sidiff.coevolution.example.swml.crossreferences.datalayer.impl.DataLayerImpl <em>Data Layer</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.coevolution.example.swml.crossreferences.datalayer.impl.DataLayerImpl
	 * @see org.sidiff.coevolution.example.swml.crossreferences.datalayer.impl.DatalayerPackageImpl#getDataLayer()
	 * @generated
	 */
	int DATA_LAYER = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_LAYER__NAME = 0;

	/**
	 * The feature id for the '<em><b>Entities</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_LAYER__ENTITIES = 1;

	/**
	 * The number of structural features of the '<em>Data Layer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_LAYER_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Data Layer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_LAYER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.sidiff.coevolution.example.swml.crossreferences.datalayer.impl.EntityImpl <em>Entity</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.coevolution.example.swml.crossreferences.datalayer.impl.EntityImpl
	 * @see org.sidiff.coevolution.example.swml.crossreferences.datalayer.impl.DatalayerPackageImpl#getEntity()
	 * @generated
	 */
	int ENTITY = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY__NAME = 0;

	/**
	 * The feature id for the '<em><b>References</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY__REFERENCES = 1;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY__ATTRIBUTES = 2;

	/**
	 * The number of structural features of the '<em>Entity</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Entity</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.sidiff.coevolution.example.swml.crossreferences.datalayer.impl.ReferenceImpl <em>Reference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.coevolution.example.swml.crossreferences.datalayer.impl.ReferenceImpl
	 * @see org.sidiff.coevolution.example.swml.crossreferences.datalayer.impl.DatalayerPackageImpl#getReference()
	 * @generated
	 */
	int REFERENCE = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE__TYPE = 1;

	/**
	 * The number of structural features of the '<em>Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.sidiff.coevolution.example.swml.crossreferences.datalayer.impl.AttributeImpl <em>Attribute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.coevolution.example.swml.crossreferences.datalayer.impl.AttributeImpl
	 * @see org.sidiff.coevolution.example.swml.crossreferences.datalayer.impl.DatalayerPackageImpl#getAttribute()
	 * @generated
	 */
	int ATTRIBUTE = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE__TYPE = 1;

	/**
	 * The number of structural features of the '<em>Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.sidiff.coevolution.example.swml.crossreferences.datalayer.SimpleType <em>Simple Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.coevolution.example.swml.crossreferences.datalayer.SimpleType
	 * @see org.sidiff.coevolution.example.swml.crossreferences.datalayer.impl.DatalayerPackageImpl#getSimpleType()
	 * @generated
	 */
	int SIMPLE_TYPE = 4;


	/**
	 * Returns the meta object for class '{@link org.sidiff.coevolution.example.swml.crossreferences.datalayer.DataLayer <em>Data Layer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Data Layer</em>'.
	 * @see org.sidiff.coevolution.example.swml.crossreferences.datalayer.DataLayer
	 * @generated
	 */
	EClass getDataLayer();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.coevolution.example.swml.crossreferences.datalayer.DataLayer#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.sidiff.coevolution.example.swml.crossreferences.datalayer.DataLayer#getName()
	 * @see #getDataLayer()
	 * @generated
	 */
	EAttribute getDataLayer_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.coevolution.example.swml.crossreferences.datalayer.DataLayer#getEntities <em>Entities</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Entities</em>'.
	 * @see org.sidiff.coevolution.example.swml.crossreferences.datalayer.DataLayer#getEntities()
	 * @see #getDataLayer()
	 * @generated
	 */
	EReference getDataLayer_Entities();

	/**
	 * Returns the meta object for class '{@link org.sidiff.coevolution.example.swml.crossreferences.datalayer.Entity <em>Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Entity</em>'.
	 * @see org.sidiff.coevolution.example.swml.crossreferences.datalayer.Entity
	 * @generated
	 */
	EClass getEntity();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.coevolution.example.swml.crossreferences.datalayer.Entity#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.sidiff.coevolution.example.swml.crossreferences.datalayer.Entity#getName()
	 * @see #getEntity()
	 * @generated
	 */
	EAttribute getEntity_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.coevolution.example.swml.crossreferences.datalayer.Entity#getReferences <em>References</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>References</em>'.
	 * @see org.sidiff.coevolution.example.swml.crossreferences.datalayer.Entity#getReferences()
	 * @see #getEntity()
	 * @generated
	 */
	EReference getEntity_References();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.coevolution.example.swml.crossreferences.datalayer.Entity#getAttributes <em>Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attributes</em>'.
	 * @see org.sidiff.coevolution.example.swml.crossreferences.datalayer.Entity#getAttributes()
	 * @see #getEntity()
	 * @generated
	 */
	EReference getEntity_Attributes();

	/**
	 * Returns the meta object for class '{@link org.sidiff.coevolution.example.swml.crossreferences.datalayer.Reference <em>Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Reference</em>'.
	 * @see org.sidiff.coevolution.example.swml.crossreferences.datalayer.Reference
	 * @generated
	 */
	EClass getReference();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.coevolution.example.swml.crossreferences.datalayer.Reference#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.sidiff.coevolution.example.swml.crossreferences.datalayer.Reference#getName()
	 * @see #getReference()
	 * @generated
	 */
	EAttribute getReference_Name();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.coevolution.example.swml.crossreferences.datalayer.Reference#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see org.sidiff.coevolution.example.swml.crossreferences.datalayer.Reference#getType()
	 * @see #getReference()
	 * @generated
	 */
	EReference getReference_Type();

	/**
	 * Returns the meta object for class '{@link org.sidiff.coevolution.example.swml.crossreferences.datalayer.Attribute <em>Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attribute</em>'.
	 * @see org.sidiff.coevolution.example.swml.crossreferences.datalayer.Attribute
	 * @generated
	 */
	EClass getAttribute();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.coevolution.example.swml.crossreferences.datalayer.Attribute#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.sidiff.coevolution.example.swml.crossreferences.datalayer.Attribute#getName()
	 * @see #getAttribute()
	 * @generated
	 */
	EAttribute getAttribute_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.coevolution.example.swml.crossreferences.datalayer.Attribute#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.sidiff.coevolution.example.swml.crossreferences.datalayer.Attribute#getType()
	 * @see #getAttribute()
	 * @generated
	 */
	EAttribute getAttribute_Type();

	/**
	 * Returns the meta object for enum '{@link org.sidiff.coevolution.example.swml.crossreferences.datalayer.SimpleType <em>Simple Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Simple Type</em>'.
	 * @see org.sidiff.coevolution.example.swml.crossreferences.datalayer.SimpleType
	 * @generated
	 */
	EEnum getSimpleType();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	DatalayerFactory getDatalayerFactory();

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
		 * The meta object literal for the '{@link org.sidiff.coevolution.example.swml.crossreferences.datalayer.impl.DataLayerImpl <em>Data Layer</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.coevolution.example.swml.crossreferences.datalayer.impl.DataLayerImpl
		 * @see org.sidiff.coevolution.example.swml.crossreferences.datalayer.impl.DatalayerPackageImpl#getDataLayer()
		 * @generated
		 */
		EClass DATA_LAYER = eINSTANCE.getDataLayer();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DATA_LAYER__NAME = eINSTANCE.getDataLayer_Name();

		/**
		 * The meta object literal for the '<em><b>Entities</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DATA_LAYER__ENTITIES = eINSTANCE.getDataLayer_Entities();

		/**
		 * The meta object literal for the '{@link org.sidiff.coevolution.example.swml.crossreferences.datalayer.impl.EntityImpl <em>Entity</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.coevolution.example.swml.crossreferences.datalayer.impl.EntityImpl
		 * @see org.sidiff.coevolution.example.swml.crossreferences.datalayer.impl.DatalayerPackageImpl#getEntity()
		 * @generated
		 */
		EClass ENTITY = eINSTANCE.getEntity();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENTITY__NAME = eINSTANCE.getEntity_Name();

		/**
		 * The meta object literal for the '<em><b>References</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENTITY__REFERENCES = eINSTANCE.getEntity_References();

		/**
		 * The meta object literal for the '<em><b>Attributes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENTITY__ATTRIBUTES = eINSTANCE.getEntity_Attributes();

		/**
		 * The meta object literal for the '{@link org.sidiff.coevolution.example.swml.crossreferences.datalayer.impl.ReferenceImpl <em>Reference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.coevolution.example.swml.crossreferences.datalayer.impl.ReferenceImpl
		 * @see org.sidiff.coevolution.example.swml.crossreferences.datalayer.impl.DatalayerPackageImpl#getReference()
		 * @generated
		 */
		EClass REFERENCE = eINSTANCE.getReference();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REFERENCE__NAME = eINSTANCE.getReference_Name();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REFERENCE__TYPE = eINSTANCE.getReference_Type();

		/**
		 * The meta object literal for the '{@link org.sidiff.coevolution.example.swml.crossreferences.datalayer.impl.AttributeImpl <em>Attribute</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.coevolution.example.swml.crossreferences.datalayer.impl.AttributeImpl
		 * @see org.sidiff.coevolution.example.swml.crossreferences.datalayer.impl.DatalayerPackageImpl#getAttribute()
		 * @generated
		 */
		EClass ATTRIBUTE = eINSTANCE.getAttribute();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATTRIBUTE__NAME = eINSTANCE.getAttribute_Name();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATTRIBUTE__TYPE = eINSTANCE.getAttribute_Type();

		/**
		 * The meta object literal for the '{@link org.sidiff.coevolution.example.swml.crossreferences.datalayer.SimpleType <em>Simple Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.coevolution.example.swml.crossreferences.datalayer.SimpleType
		 * @see org.sidiff.coevolution.example.swml.crossreferences.datalayer.impl.DatalayerPackageImpl#getSimpleType()
		 * @generated
		 */
		EEnum SIMPLE_TYPE = eINSTANCE.getSimpleType();

	}

} //DatalayerPackage
