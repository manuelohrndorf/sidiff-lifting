/**
 */
package de.imotep.core.datamodel.de_imotep_core_datamodel;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;

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
 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.De_imotep_core_datamodelFactory
 * @model kind="package"
 * @generated
 */
public interface De_imotep_core_datamodelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "de_imotep_core_datamodel";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.moflon.org.de_imotep_core_datamodel";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "de_imotep_core_datamodel";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	De_imotep_core_datamodelPackage eINSTANCE = de.imotep.core.datamodel.de_imotep_core_datamodel.impl.De_imotep_core_datamodelPackageImpl.init();

	/**
	 * The meta object id for the '{@link de.imotep.core.datamodel.de_imotep_core_datamodel.impl.MEntityImpl <em>MEntity</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.impl.MEntityImpl
	 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.impl.De_imotep_core_datamodelPackageImpl#getMEntity()
	 * @generated
	 */
	int MENTITY = 4;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MENTITY__ID = 0;

	/**
	 * The number of structural features of the '<em>MEntity</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MENTITY_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>MEntity</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MENTITY_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.imotep.core.datamodel.de_imotep_core_datamodel.impl.MNamedEntityImpl <em>MNamed Entity</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.impl.MNamedEntityImpl
	 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.impl.De_imotep_core_datamodelPackageImpl#getMNamedEntity()
	 * @generated
	 */
	int MNAMED_ENTITY = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MNAMED_ENTITY__ID = MENTITY__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MNAMED_ENTITY__NAME = MENTITY_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>MNamed Entity</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MNAMED_ENTITY_FEATURE_COUNT = MENTITY_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>MNamed Entity</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MNAMED_ENTITY_OPERATION_COUNT = MENTITY_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.imotep.core.datamodel.de_imotep_core_datamodel.impl.MAttributeImpl <em>MAttribute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.impl.MAttributeImpl
	 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.impl.De_imotep_core_datamodelPackageImpl#getMAttribute()
	 * @generated
	 */
	int MATTRIBUTE = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATTRIBUTE__ID = MNAMED_ENTITY__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATTRIBUTE__NAME = MNAMED_ENTITY__NAME;

	/**
	 * The feature id for the '<em><b>Is Static</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATTRIBUTE__IS_STATIC = MNAMED_ENTITY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Visibility</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATTRIBUTE__VISIBILITY = MNAMED_ENTITY_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>MAttribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATTRIBUTE_FEATURE_COUNT = MNAMED_ENTITY_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>MAttribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATTRIBUTE_OPERATION_COUNT = MNAMED_ENTITY_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.imotep.core.datamodel.de_imotep_core_datamodel.impl.MIntegerAttributeImpl <em>MInteger Attribute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.impl.MIntegerAttributeImpl
	 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.impl.De_imotep_core_datamodelPackageImpl#getMIntegerAttribute()
	 * @generated
	 */
	int MINTEGER_ATTRIBUTE = 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MINTEGER_ATTRIBUTE__ID = MATTRIBUTE__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MINTEGER_ATTRIBUTE__NAME = MATTRIBUTE__NAME;

	/**
	 * The feature id for the '<em><b>Is Static</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MINTEGER_ATTRIBUTE__IS_STATIC = MATTRIBUTE__IS_STATIC;

	/**
	 * The feature id for the '<em><b>Visibility</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MINTEGER_ATTRIBUTE__VISIBILITY = MATTRIBUTE__VISIBILITY;

	/**
	 * The feature id for the '<em><b>Init Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MINTEGER_ATTRIBUTE__INIT_VALUE = MATTRIBUTE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MINTEGER_ATTRIBUTE__VALUE = MATTRIBUTE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>MInteger Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MINTEGER_ATTRIBUTE_FEATURE_COUNT = MATTRIBUTE_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>MInteger Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MINTEGER_ATTRIBUTE_OPERATION_COUNT = MATTRIBUTE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.imotep.core.datamodel.de_imotep_core_datamodel.impl.MStringAttributeImpl <em>MString Attribute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.impl.MStringAttributeImpl
	 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.impl.De_imotep_core_datamodelPackageImpl#getMStringAttribute()
	 * @generated
	 */
	int MSTRING_ATTRIBUTE = 3;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSTRING_ATTRIBUTE__ID = MATTRIBUTE__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSTRING_ATTRIBUTE__NAME = MATTRIBUTE__NAME;

	/**
	 * The feature id for the '<em><b>Is Static</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSTRING_ATTRIBUTE__IS_STATIC = MATTRIBUTE__IS_STATIC;

	/**
	 * The feature id for the '<em><b>Visibility</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSTRING_ATTRIBUTE__VISIBILITY = MATTRIBUTE__VISIBILITY;

	/**
	 * The feature id for the '<em><b>Init Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSTRING_ATTRIBUTE__INIT_VALUE = MATTRIBUTE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSTRING_ATTRIBUTE__VALUE = MATTRIBUTE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>MString Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSTRING_ATTRIBUTE_FEATURE_COUNT = MATTRIBUTE_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>MString Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSTRING_ATTRIBUTE_OPERATION_COUNT = MATTRIBUTE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.imotep.core.datamodel.de_imotep_core_datamodel.impl.MRangedIntegerAttributeImpl <em>MRanged Integer Attribute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.impl.MRangedIntegerAttributeImpl
	 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.impl.De_imotep_core_datamodelPackageImpl#getMRangedIntegerAttribute()
	 * @generated
	 */
	int MRANGED_INTEGER_ATTRIBUTE = 5;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MRANGED_INTEGER_ATTRIBUTE__ID = MINTEGER_ATTRIBUTE__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MRANGED_INTEGER_ATTRIBUTE__NAME = MINTEGER_ATTRIBUTE__NAME;

	/**
	 * The feature id for the '<em><b>Is Static</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MRANGED_INTEGER_ATTRIBUTE__IS_STATIC = MINTEGER_ATTRIBUTE__IS_STATIC;

	/**
	 * The feature id for the '<em><b>Visibility</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MRANGED_INTEGER_ATTRIBUTE__VISIBILITY = MINTEGER_ATTRIBUTE__VISIBILITY;

	/**
	 * The feature id for the '<em><b>Init Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MRANGED_INTEGER_ATTRIBUTE__INIT_VALUE = MINTEGER_ATTRIBUTE__INIT_VALUE;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MRANGED_INTEGER_ATTRIBUTE__VALUE = MINTEGER_ATTRIBUTE__VALUE;

	/**
	 * The feature id for the '<em><b>Maximum</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MRANGED_INTEGER_ATTRIBUTE__MAXIMUM = MINTEGER_ATTRIBUTE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Minimum</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MRANGED_INTEGER_ATTRIBUTE__MINIMUM = MINTEGER_ATTRIBUTE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>MRanged Integer Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MRANGED_INTEGER_ATTRIBUTE_FEATURE_COUNT = MINTEGER_ATTRIBUTE_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>MRanged Integer Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MRANGED_INTEGER_ATTRIBUTE_OPERATION_COUNT = MINTEGER_ATTRIBUTE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.imotep.core.datamodel.de_imotep_core_datamodel.impl.MBooleanAttributeImpl <em>MBoolean Attribute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.impl.MBooleanAttributeImpl
	 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.impl.De_imotep_core_datamodelPackageImpl#getMBooleanAttribute()
	 * @generated
	 */
	int MBOOLEAN_ATTRIBUTE = 6;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MBOOLEAN_ATTRIBUTE__ID = MATTRIBUTE__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MBOOLEAN_ATTRIBUTE__NAME = MATTRIBUTE__NAME;

	/**
	 * The feature id for the '<em><b>Is Static</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MBOOLEAN_ATTRIBUTE__IS_STATIC = MATTRIBUTE__IS_STATIC;

	/**
	 * The feature id for the '<em><b>Visibility</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MBOOLEAN_ATTRIBUTE__VISIBILITY = MATTRIBUTE__VISIBILITY;

	/**
	 * The feature id for the '<em><b>Init Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MBOOLEAN_ATTRIBUTE__INIT_VALUE = MATTRIBUTE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MBOOLEAN_ATTRIBUTE__VALUE = MATTRIBUTE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>MBoolean Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MBOOLEAN_ATTRIBUTE_FEATURE_COUNT = MATTRIBUTE_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>MBoolean Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MBOOLEAN_ATTRIBUTE_OPERATION_COUNT = MATTRIBUTE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.imotep.core.datamodel.de_imotep_core_datamodel.MEVisibility <em>ME Visibility</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.MEVisibility
	 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.impl.De_imotep_core_datamodelPackageImpl#getMEVisibility()
	 * @generated
	 */
	int ME_VISIBILITY = 7;

	/**
	 * The meta object id for the '<em>JObject</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.Object
	 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.impl.De_imotep_core_datamodelPackageImpl#getJObject()
	 * @generated
	 */
	int JOBJECT = 8;

	/**
	 * The meta object id for the '<em>JCollection</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.Object
	 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.impl.De_imotep_core_datamodelPackageImpl#getJCollection()
	 * @generated
	 */
	int JCOLLECTION = 9;

	/**
	 * The meta object id for the '<em>JList</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.Object
	 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.impl.De_imotep_core_datamodelPackageImpl#getJList()
	 * @generated
	 */
	int JLIST = 10;

	/**
	 * The meta object id for the '<em>JSet</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.Object
	 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.impl.De_imotep_core_datamodelPackageImpl#getJSet()
	 * @generated
	 */
	int JSET = 11;


	/**
	 * Returns the meta object for class '{@link de.imotep.core.datamodel.de_imotep_core_datamodel.MAttribute <em>MAttribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>MAttribute</em>'.
	 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.MAttribute
	 * @generated
	 */
	EClass getMAttribute();

	/**
	 * Returns the meta object for the attribute '{@link de.imotep.core.datamodel.de_imotep_core_datamodel.MAttribute#isIsStatic <em>Is Static</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Static</em>'.
	 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.MAttribute#isIsStatic()
	 * @see #getMAttribute()
	 * @generated
	 */
	EAttribute getMAttribute_IsStatic();

	/**
	 * Returns the meta object for the attribute '{@link de.imotep.core.datamodel.de_imotep_core_datamodel.MAttribute#getVisibility <em>Visibility</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Visibility</em>'.
	 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.MAttribute#getVisibility()
	 * @see #getMAttribute()
	 * @generated
	 */
	EAttribute getMAttribute_Visibility();

	/**
	 * Returns the meta object for class '{@link de.imotep.core.datamodel.de_imotep_core_datamodel.MNamedEntity <em>MNamed Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>MNamed Entity</em>'.
	 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.MNamedEntity
	 * @generated
	 */
	EClass getMNamedEntity();

	/**
	 * Returns the meta object for the attribute '{@link de.imotep.core.datamodel.de_imotep_core_datamodel.MNamedEntity#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.MNamedEntity#getName()
	 * @see #getMNamedEntity()
	 * @generated
	 */
	EAttribute getMNamedEntity_Name();

	/**
	 * Returns the meta object for class '{@link de.imotep.core.datamodel.de_imotep_core_datamodel.MIntegerAttribute <em>MInteger Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>MInteger Attribute</em>'.
	 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.MIntegerAttribute
	 * @generated
	 */
	EClass getMIntegerAttribute();

	/**
	 * Returns the meta object for the attribute '{@link de.imotep.core.datamodel.de_imotep_core_datamodel.MIntegerAttribute#getInitValue <em>Init Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Init Value</em>'.
	 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.MIntegerAttribute#getInitValue()
	 * @see #getMIntegerAttribute()
	 * @generated
	 */
	EAttribute getMIntegerAttribute_InitValue();

	/**
	 * Returns the meta object for the attribute '{@link de.imotep.core.datamodel.de_imotep_core_datamodel.MIntegerAttribute#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.MIntegerAttribute#getValue()
	 * @see #getMIntegerAttribute()
	 * @generated
	 */
	EAttribute getMIntegerAttribute_Value();

	/**
	 * Returns the meta object for class '{@link de.imotep.core.datamodel.de_imotep_core_datamodel.MStringAttribute <em>MString Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>MString Attribute</em>'.
	 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.MStringAttribute
	 * @generated
	 */
	EClass getMStringAttribute();

	/**
	 * Returns the meta object for the attribute '{@link de.imotep.core.datamodel.de_imotep_core_datamodel.MStringAttribute#getInitValue <em>Init Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Init Value</em>'.
	 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.MStringAttribute#getInitValue()
	 * @see #getMStringAttribute()
	 * @generated
	 */
	EAttribute getMStringAttribute_InitValue();

	/**
	 * Returns the meta object for the attribute '{@link de.imotep.core.datamodel.de_imotep_core_datamodel.MStringAttribute#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.MStringAttribute#getValue()
	 * @see #getMStringAttribute()
	 * @generated
	 */
	EAttribute getMStringAttribute_Value();

	/**
	 * Returns the meta object for class '{@link de.imotep.core.datamodel.de_imotep_core_datamodel.MEntity <em>MEntity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>MEntity</em>'.
	 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.MEntity
	 * @generated
	 */
	EClass getMEntity();

	/**
	 * Returns the meta object for the attribute '{@link de.imotep.core.datamodel.de_imotep_core_datamodel.MEntity#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.MEntity#getId()
	 * @see #getMEntity()
	 * @generated
	 */
	EAttribute getMEntity_Id();

	/**
	 * Returns the meta object for class '{@link de.imotep.core.datamodel.de_imotep_core_datamodel.MRangedIntegerAttribute <em>MRanged Integer Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>MRanged Integer Attribute</em>'.
	 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.MRangedIntegerAttribute
	 * @generated
	 */
	EClass getMRangedIntegerAttribute();

	/**
	 * Returns the meta object for the attribute '{@link de.imotep.core.datamodel.de_imotep_core_datamodel.MRangedIntegerAttribute#getMaximum <em>Maximum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Maximum</em>'.
	 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.MRangedIntegerAttribute#getMaximum()
	 * @see #getMRangedIntegerAttribute()
	 * @generated
	 */
	EAttribute getMRangedIntegerAttribute_Maximum();

	/**
	 * Returns the meta object for the attribute '{@link de.imotep.core.datamodel.de_imotep_core_datamodel.MRangedIntegerAttribute#getMinimum <em>Minimum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Minimum</em>'.
	 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.MRangedIntegerAttribute#getMinimum()
	 * @see #getMRangedIntegerAttribute()
	 * @generated
	 */
	EAttribute getMRangedIntegerAttribute_Minimum();

	/**
	 * Returns the meta object for class '{@link de.imotep.core.datamodel.de_imotep_core_datamodel.MBooleanAttribute <em>MBoolean Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>MBoolean Attribute</em>'.
	 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.MBooleanAttribute
	 * @generated
	 */
	EClass getMBooleanAttribute();

	/**
	 * Returns the meta object for the attribute '{@link de.imotep.core.datamodel.de_imotep_core_datamodel.MBooleanAttribute#isInitValue <em>Init Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Init Value</em>'.
	 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.MBooleanAttribute#isInitValue()
	 * @see #getMBooleanAttribute()
	 * @generated
	 */
	EAttribute getMBooleanAttribute_InitValue();

	/**
	 * Returns the meta object for the attribute '{@link de.imotep.core.datamodel.de_imotep_core_datamodel.MBooleanAttribute#isValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.MBooleanAttribute#isValue()
	 * @see #getMBooleanAttribute()
	 * @generated
	 */
	EAttribute getMBooleanAttribute_Value();

	/**
	 * Returns the meta object for enum '{@link de.imotep.core.datamodel.de_imotep_core_datamodel.MEVisibility <em>ME Visibility</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>ME Visibility</em>'.
	 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.MEVisibility
	 * @generated
	 */
	EEnum getMEVisibility();

	/**
	 * Returns the meta object for data type '{@link java.lang.Object <em>JObject</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>JObject</em>'.
	 * @see java.lang.Object
	 * @model instanceClass="java.lang.Object"
	 * @generated
	 */
	EDataType getJObject();

	/**
	 * Returns the meta object for data type '{@link java.lang.Object <em>JCollection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>JCollection</em>'.
	 * @see java.lang.Object
	 * @model instanceClass="java.lang.Object"
	 * @generated
	 */
	EDataType getJCollection();

	/**
	 * Returns the meta object for data type '{@link java.lang.Object <em>JList</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>JList</em>'.
	 * @see java.lang.Object
	 * @model instanceClass="java.lang.Object"
	 * @generated
	 */
	EDataType getJList();

	/**
	 * Returns the meta object for data type '{@link java.lang.Object <em>JSet</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>JSet</em>'.
	 * @see java.lang.Object
	 * @model instanceClass="java.lang.Object"
	 * @generated
	 */
	EDataType getJSet();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	De_imotep_core_datamodelFactory getDe_imotep_core_datamodelFactory();

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
		 * The meta object literal for the '{@link de.imotep.core.datamodel.de_imotep_core_datamodel.impl.MAttributeImpl <em>MAttribute</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.impl.MAttributeImpl
		 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.impl.De_imotep_core_datamodelPackageImpl#getMAttribute()
		 * @generated
		 */
		EClass MATTRIBUTE = eINSTANCE.getMAttribute();

		/**
		 * The meta object literal for the '<em><b>Is Static</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MATTRIBUTE__IS_STATIC = eINSTANCE.getMAttribute_IsStatic();

		/**
		 * The meta object literal for the '<em><b>Visibility</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MATTRIBUTE__VISIBILITY = eINSTANCE.getMAttribute_Visibility();

		/**
		 * The meta object literal for the '{@link de.imotep.core.datamodel.de_imotep_core_datamodel.impl.MNamedEntityImpl <em>MNamed Entity</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.impl.MNamedEntityImpl
		 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.impl.De_imotep_core_datamodelPackageImpl#getMNamedEntity()
		 * @generated
		 */
		EClass MNAMED_ENTITY = eINSTANCE.getMNamedEntity();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MNAMED_ENTITY__NAME = eINSTANCE.getMNamedEntity_Name();

		/**
		 * The meta object literal for the '{@link de.imotep.core.datamodel.de_imotep_core_datamodel.impl.MIntegerAttributeImpl <em>MInteger Attribute</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.impl.MIntegerAttributeImpl
		 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.impl.De_imotep_core_datamodelPackageImpl#getMIntegerAttribute()
		 * @generated
		 */
		EClass MINTEGER_ATTRIBUTE = eINSTANCE.getMIntegerAttribute();

		/**
		 * The meta object literal for the '<em><b>Init Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MINTEGER_ATTRIBUTE__INIT_VALUE = eINSTANCE.getMIntegerAttribute_InitValue();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MINTEGER_ATTRIBUTE__VALUE = eINSTANCE.getMIntegerAttribute_Value();

		/**
		 * The meta object literal for the '{@link de.imotep.core.datamodel.de_imotep_core_datamodel.impl.MStringAttributeImpl <em>MString Attribute</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.impl.MStringAttributeImpl
		 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.impl.De_imotep_core_datamodelPackageImpl#getMStringAttribute()
		 * @generated
		 */
		EClass MSTRING_ATTRIBUTE = eINSTANCE.getMStringAttribute();

		/**
		 * The meta object literal for the '<em><b>Init Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MSTRING_ATTRIBUTE__INIT_VALUE = eINSTANCE.getMStringAttribute_InitValue();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MSTRING_ATTRIBUTE__VALUE = eINSTANCE.getMStringAttribute_Value();

		/**
		 * The meta object literal for the '{@link de.imotep.core.datamodel.de_imotep_core_datamodel.impl.MEntityImpl <em>MEntity</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.impl.MEntityImpl
		 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.impl.De_imotep_core_datamodelPackageImpl#getMEntity()
		 * @generated
		 */
		EClass MENTITY = eINSTANCE.getMEntity();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MENTITY__ID = eINSTANCE.getMEntity_Id();

		/**
		 * The meta object literal for the '{@link de.imotep.core.datamodel.de_imotep_core_datamodel.impl.MRangedIntegerAttributeImpl <em>MRanged Integer Attribute</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.impl.MRangedIntegerAttributeImpl
		 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.impl.De_imotep_core_datamodelPackageImpl#getMRangedIntegerAttribute()
		 * @generated
		 */
		EClass MRANGED_INTEGER_ATTRIBUTE = eINSTANCE.getMRangedIntegerAttribute();

		/**
		 * The meta object literal for the '<em><b>Maximum</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MRANGED_INTEGER_ATTRIBUTE__MAXIMUM = eINSTANCE.getMRangedIntegerAttribute_Maximum();

		/**
		 * The meta object literal for the '<em><b>Minimum</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MRANGED_INTEGER_ATTRIBUTE__MINIMUM = eINSTANCE.getMRangedIntegerAttribute_Minimum();

		/**
		 * The meta object literal for the '{@link de.imotep.core.datamodel.de_imotep_core_datamodel.impl.MBooleanAttributeImpl <em>MBoolean Attribute</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.impl.MBooleanAttributeImpl
		 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.impl.De_imotep_core_datamodelPackageImpl#getMBooleanAttribute()
		 * @generated
		 */
		EClass MBOOLEAN_ATTRIBUTE = eINSTANCE.getMBooleanAttribute();

		/**
		 * The meta object literal for the '<em><b>Init Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MBOOLEAN_ATTRIBUTE__INIT_VALUE = eINSTANCE.getMBooleanAttribute_InitValue();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MBOOLEAN_ATTRIBUTE__VALUE = eINSTANCE.getMBooleanAttribute_Value();

		/**
		 * The meta object literal for the '{@link de.imotep.core.datamodel.de_imotep_core_datamodel.MEVisibility <em>ME Visibility</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.MEVisibility
		 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.impl.De_imotep_core_datamodelPackageImpl#getMEVisibility()
		 * @generated
		 */
		EEnum ME_VISIBILITY = eINSTANCE.getMEVisibility();

		/**
		 * The meta object literal for the '<em>JObject</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.Object
		 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.impl.De_imotep_core_datamodelPackageImpl#getJObject()
		 * @generated
		 */
		EDataType JOBJECT = eINSTANCE.getJObject();

		/**
		 * The meta object literal for the '<em>JCollection</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.Object
		 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.impl.De_imotep_core_datamodelPackageImpl#getJCollection()
		 * @generated
		 */
		EDataType JCOLLECTION = eINSTANCE.getJCollection();

		/**
		 * The meta object literal for the '<em>JList</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.Object
		 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.impl.De_imotep_core_datamodelPackageImpl#getJList()
		 * @generated
		 */
		EDataType JLIST = eINSTANCE.getJList();

		/**
		 * The meta object literal for the '<em>JSet</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.Object
		 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.impl.De_imotep_core_datamodelPackageImpl#getJSet()
		 * @generated
		 */
		EDataType JSET = eINSTANCE.getJSet();

	}

} //De_imotep_core_datamodelPackage
