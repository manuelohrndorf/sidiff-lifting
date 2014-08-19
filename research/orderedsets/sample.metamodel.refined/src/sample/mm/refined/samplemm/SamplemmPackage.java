/**
 */
package sample.mm.refined.samplemm;

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
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see sample.mm.refined.samplemm.SamplemmFactory
 * @model kind="package"
 * @generated
 */
public interface SamplemmPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "samplemm";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://samplemm/1.0/refined";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "samplemm";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SamplemmPackage eINSTANCE = sample.mm.refined.samplemm.impl.SamplemmPackageImpl.init();

	/**
	 * The meta object id for the '{@link sample.mm.refined.samplemm.impl.NCListImpl <em>NC List</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see sample.mm.refined.samplemm.impl.NCListImpl
	 * @see sample.mm.refined.samplemm.impl.SamplemmPackageImpl#getNCList()
	 * @generated
	 */
	int NC_LIST = 0;

	/**
	 * The feature id for the '<em><b>Items</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NC_LIST__ITEMS = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NC_LIST__NAME = 1;

	/**
	 * The feature id for the '<em><b>Item links</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NC_LIST__ITEM_LINKS = 2;

	/**
	 * The number of structural features of the '<em>NC List</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NC_LIST_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link sample.mm.refined.samplemm.impl.ItemImpl <em>Item</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see sample.mm.refined.samplemm.impl.ItemImpl
	 * @see sample.mm.refined.samplemm.impl.SamplemmPackageImpl#getItem()
	 * @generated
	 */
	int ITEM = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITEM__NAME = 0;

	/**
	 * The feature id for the '<em><b>Pre</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITEM__PRE = 1;

	/**
	 * The feature id for the '<em><b>Succ</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITEM__SUCC = 2;

	/**
	 * The number of structural features of the '<em>Item</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITEM_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link sample.mm.refined.samplemm.impl.SampleMetamodelImpl <em>Sample Metamodel</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see sample.mm.refined.samplemm.impl.SampleMetamodelImpl
	 * @see sample.mm.refined.samplemm.impl.SamplemmPackageImpl#getSampleMetamodel()
	 * @generated
	 */
	int SAMPLE_METAMODEL = 2;

	/**
	 * The feature id for the '<em><b>Items</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SAMPLE_METAMODEL__ITEMS = 0;

	/**
	 * The feature id for the '<em><b>Nc Lists</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SAMPLE_METAMODEL__NC_LISTS = 1;

	/**
	 * The feature id for the '<em><b>CLists</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SAMPLE_METAMODEL__CLISTS = 2;

	/**
	 * The number of structural features of the '<em>Sample Metamodel</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SAMPLE_METAMODEL_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link sample.mm.refined.samplemm.impl.CListImpl <em>CList</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see sample.mm.refined.samplemm.impl.CListImpl
	 * @see sample.mm.refined.samplemm.impl.SamplemmPackageImpl#getCList()
	 * @generated
	 */
	int CLIST = 3;

	/**
	 * The feature id for the '<em><b>Items</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLIST__ITEMS = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLIST__NAME = 1;

	/**
	 * The number of structural features of the '<em>CList</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLIST_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link sample.mm.refined.samplemm.impl.Item_LinkImpl <em>Item Link</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see sample.mm.refined.samplemm.impl.Item_LinkImpl
	 * @see sample.mm.refined.samplemm.impl.SamplemmPackageImpl#getItem_Link()
	 * @generated
	 */
	int ITEM_LINK = 4;

	/**
	 * The feature id for the '<em><b>Pre</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITEM_LINK__PRE = 0;

	/**
	 * The feature id for the '<em><b>Succ</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITEM_LINK__SUCC = 1;

	/**
	 * The number of structural features of the '<em>Item Link</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITEM_LINK_FEATURE_COUNT = 2;


	/**
	 * Returns the meta object for class '{@link sample.mm.refined.samplemm.NCList <em>NC List</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>NC List</em>'.
	 * @see sample.mm.refined.samplemm.NCList
	 * @generated
	 */
	EClass getNCList();

	/**
	 * Returns the meta object for the reference list '{@link sample.mm.refined.samplemm.NCList#getItems <em>Items</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Items</em>'.
	 * @see sample.mm.refined.samplemm.NCList#getItems()
	 * @see #getNCList()
	 * @generated
	 */
	EReference getNCList_Items();

	/**
	 * Returns the meta object for the attribute '{@link sample.mm.refined.samplemm.NCList#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see sample.mm.refined.samplemm.NCList#getName()
	 * @see #getNCList()
	 * @generated
	 */
	EAttribute getNCList_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link sample.mm.refined.samplemm.NCList#getItem_links <em>Item links</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Item links</em>'.
	 * @see sample.mm.refined.samplemm.NCList#getItem_links()
	 * @see #getNCList()
	 * @generated
	 */
	EReference getNCList_Item_links();

	/**
	 * Returns the meta object for class '{@link sample.mm.refined.samplemm.Item <em>Item</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Item</em>'.
	 * @see sample.mm.refined.samplemm.Item
	 * @generated
	 */
	EClass getItem();

	/**
	 * Returns the meta object for the attribute '{@link sample.mm.refined.samplemm.Item#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see sample.mm.refined.samplemm.Item#getName()
	 * @see #getItem()
	 * @generated
	 */
	EAttribute getItem_Name();

	/**
	 * Returns the meta object for the reference '{@link sample.mm.refined.samplemm.Item#getPre <em>Pre</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Pre</em>'.
	 * @see sample.mm.refined.samplemm.Item#getPre()
	 * @see #getItem()
	 * @generated
	 */
	EReference getItem_Pre();

	/**
	 * Returns the meta object for the reference '{@link sample.mm.refined.samplemm.Item#getSucc <em>Succ</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Succ</em>'.
	 * @see sample.mm.refined.samplemm.Item#getSucc()
	 * @see #getItem()
	 * @generated
	 */
	EReference getItem_Succ();

	/**
	 * Returns the meta object for class '{@link sample.mm.refined.samplemm.SampleMetamodel <em>Sample Metamodel</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Sample Metamodel</em>'.
	 * @see sample.mm.refined.samplemm.SampleMetamodel
	 * @generated
	 */
	EClass getSampleMetamodel();

	/**
	 * Returns the meta object for the containment reference list '{@link sample.mm.refined.samplemm.SampleMetamodel#getItems <em>Items</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Items</em>'.
	 * @see sample.mm.refined.samplemm.SampleMetamodel#getItems()
	 * @see #getSampleMetamodel()
	 * @generated
	 */
	EReference getSampleMetamodel_Items();

	/**
	 * Returns the meta object for the containment reference list '{@link sample.mm.refined.samplemm.SampleMetamodel#getNcLists <em>Nc Lists</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Nc Lists</em>'.
	 * @see sample.mm.refined.samplemm.SampleMetamodel#getNcLists()
	 * @see #getSampleMetamodel()
	 * @generated
	 */
	EReference getSampleMetamodel_NcLists();

	/**
	 * Returns the meta object for the containment reference list '{@link sample.mm.refined.samplemm.SampleMetamodel#getCLists <em>CLists</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>CLists</em>'.
	 * @see sample.mm.refined.samplemm.SampleMetamodel#getCLists()
	 * @see #getSampleMetamodel()
	 * @generated
	 */
	EReference getSampleMetamodel_CLists();

	/**
	 * Returns the meta object for class '{@link sample.mm.refined.samplemm.CList <em>CList</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>CList</em>'.
	 * @see sample.mm.refined.samplemm.CList
	 * @generated
	 */
	EClass getCList();

	/**
	 * Returns the meta object for the containment reference list '{@link sample.mm.refined.samplemm.CList#getItems <em>Items</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Items</em>'.
	 * @see sample.mm.refined.samplemm.CList#getItems()
	 * @see #getCList()
	 * @generated
	 */
	EReference getCList_Items();

	/**
	 * Returns the meta object for the attribute '{@link sample.mm.refined.samplemm.CList#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see sample.mm.refined.samplemm.CList#getName()
	 * @see #getCList()
	 * @generated
	 */
	EAttribute getCList_Name();

	/**
	 * Returns the meta object for class '{@link sample.mm.refined.samplemm.Item_Link <em>Item Link</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Item Link</em>'.
	 * @see sample.mm.refined.samplemm.Item_Link
	 * @generated
	 */
	EClass getItem_Link();

	/**
	 * Returns the meta object for the reference '{@link sample.mm.refined.samplemm.Item_Link#getPre <em>Pre</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Pre</em>'.
	 * @see sample.mm.refined.samplemm.Item_Link#getPre()
	 * @see #getItem_Link()
	 * @generated
	 */
	EReference getItem_Link_Pre();

	/**
	 * Returns the meta object for the reference '{@link sample.mm.refined.samplemm.Item_Link#getSucc <em>Succ</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Succ</em>'.
	 * @see sample.mm.refined.samplemm.Item_Link#getSucc()
	 * @see #getItem_Link()
	 * @generated
	 */
	EReference getItem_Link_Succ();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	SamplemmFactory getSamplemmFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link sample.mm.refined.samplemm.impl.NCListImpl <em>NC List</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see sample.mm.refined.samplemm.impl.NCListImpl
		 * @see sample.mm.refined.samplemm.impl.SamplemmPackageImpl#getNCList()
		 * @generated
		 */
		EClass NC_LIST = eINSTANCE.getNCList();

		/**
		 * The meta object literal for the '<em><b>Items</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NC_LIST__ITEMS = eINSTANCE.getNCList_Items();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NC_LIST__NAME = eINSTANCE.getNCList_Name();

		/**
		 * The meta object literal for the '<em><b>Item links</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NC_LIST__ITEM_LINKS = eINSTANCE.getNCList_Item_links();

		/**
		 * The meta object literal for the '{@link sample.mm.refined.samplemm.impl.ItemImpl <em>Item</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see sample.mm.refined.samplemm.impl.ItemImpl
		 * @see sample.mm.refined.samplemm.impl.SamplemmPackageImpl#getItem()
		 * @generated
		 */
		EClass ITEM = eINSTANCE.getItem();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ITEM__NAME = eINSTANCE.getItem_Name();

		/**
		 * The meta object literal for the '<em><b>Pre</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ITEM__PRE = eINSTANCE.getItem_Pre();

		/**
		 * The meta object literal for the '<em><b>Succ</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ITEM__SUCC = eINSTANCE.getItem_Succ();

		/**
		 * The meta object literal for the '{@link sample.mm.refined.samplemm.impl.SampleMetamodelImpl <em>Sample Metamodel</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see sample.mm.refined.samplemm.impl.SampleMetamodelImpl
		 * @see sample.mm.refined.samplemm.impl.SamplemmPackageImpl#getSampleMetamodel()
		 * @generated
		 */
		EClass SAMPLE_METAMODEL = eINSTANCE.getSampleMetamodel();

		/**
		 * The meta object literal for the '<em><b>Items</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SAMPLE_METAMODEL__ITEMS = eINSTANCE.getSampleMetamodel_Items();

		/**
		 * The meta object literal for the '<em><b>Nc Lists</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SAMPLE_METAMODEL__NC_LISTS = eINSTANCE.getSampleMetamodel_NcLists();

		/**
		 * The meta object literal for the '<em><b>CLists</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SAMPLE_METAMODEL__CLISTS = eINSTANCE.getSampleMetamodel_CLists();

		/**
		 * The meta object literal for the '{@link sample.mm.refined.samplemm.impl.CListImpl <em>CList</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see sample.mm.refined.samplemm.impl.CListImpl
		 * @see sample.mm.refined.samplemm.impl.SamplemmPackageImpl#getCList()
		 * @generated
		 */
		EClass CLIST = eINSTANCE.getCList();

		/**
		 * The meta object literal for the '<em><b>Items</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CLIST__ITEMS = eINSTANCE.getCList_Items();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CLIST__NAME = eINSTANCE.getCList_Name();

		/**
		 * The meta object literal for the '{@link sample.mm.refined.samplemm.impl.Item_LinkImpl <em>Item Link</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see sample.mm.refined.samplemm.impl.Item_LinkImpl
		 * @see sample.mm.refined.samplemm.impl.SamplemmPackageImpl#getItem_Link()
		 * @generated
		 */
		EClass ITEM_LINK = eINSTANCE.getItem_Link();

		/**
		 * The meta object literal for the '<em><b>Pre</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ITEM_LINK__PRE = eINSTANCE.getItem_Link_Pre();

		/**
		 * The meta object literal for the '<em><b>Succ</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ITEM_LINK__SUCC = eINSTANCE.getItem_Link_Succ();

	}

} //SamplemmPackage
