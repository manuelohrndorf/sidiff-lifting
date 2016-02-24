/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.javaast.model;

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
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.sidiff.javaast.model.JavaModelFactory
 * @model kind="package"
 * @generated
 */
public interface JavaModelPackage extends EPackage
{
  /**
	 * The package name.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  String eNAME = "model";

  /**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  String eNS_URI = "http://www.sidiff.org/org.sidiff.javaast.model";

  /**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  String eNS_PREFIX = "JavaModel";

  /**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  JavaModelPackage eINSTANCE = org.sidiff.javaast.model.impl.JavaModelPackageImpl.init();

  /**
	 * The meta object id for the '{@link org.sidiff.javaast.model.impl.JIdentifiableElementImpl <em>JIdentifiable Element</em>}' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see org.sidiff.javaast.model.impl.JIdentifiableElementImpl
	 * @see org.sidiff.javaast.model.impl.JavaModelPackageImpl#getJIdentifiableElement()
	 * @generated
	 */
  int JIDENTIFIABLE_ELEMENT = 2;

  /**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JIDENTIFIABLE_ELEMENT__ID = 0;

  /**
	 * The feature id for the '<em><b>Compileable</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JIDENTIFIABLE_ELEMENT__COMPILEABLE = 1;

  /**
	 * The number of structural features of the '<em>JIdentifiable Element</em>' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JIDENTIFIABLE_ELEMENT_FEATURE_COUNT = 2;

  /**
	 * The meta object id for the '{@link org.sidiff.javaast.model.impl.JProjectImpl <em>JProject</em>}' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see org.sidiff.javaast.model.impl.JProjectImpl
	 * @see org.sidiff.javaast.model.impl.JavaModelPackageImpl#getJProject()
	 * @generated
	 */
  int JPROJECT = 0;

  /**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JPROJECT__ID = JIDENTIFIABLE_ELEMENT__ID;

  /**
	 * The feature id for the '<em><b>Compileable</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JPROJECT__COMPILEABLE = JIDENTIFIABLE_ELEMENT__COMPILEABLE;

  /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JPROJECT__NAME = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 0;

  /**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JPROJECT__QUALIFIED_NAME = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 1;

		/**
	 * The feature id for the '<em><b>Packages</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JPROJECT__PACKAGES = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 2;

  /**
	 * The feature id for the '<em><b>Simple Types</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JPROJECT__SIMPLE_TYPES = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 3;

  /**
	 * The number of structural features of the '<em>JProject</em>' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JPROJECT_FEATURE_COUNT = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 4;

  /**
	 * The meta object id for the '{@link org.sidiff.javaast.model.impl.JNamedElementImpl <em>JNamed Element</em>}' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see org.sidiff.javaast.model.impl.JNamedElementImpl
	 * @see org.sidiff.javaast.model.impl.JavaModelPackageImpl#getJNamedElement()
	 * @generated
	 */
  int JNAMED_ELEMENT = 1;

  /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JNAMED_ELEMENT__NAME = 0;

  /**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JNAMED_ELEMENT__QUALIFIED_NAME = 1;

		/**
	 * The number of structural features of the '<em>JNamed Element</em>' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JNAMED_ELEMENT_FEATURE_COUNT = 2;

  /**
	 * The meta object id for the '{@link org.sidiff.javaast.model.impl.JPackageImpl <em>JPackage</em>}' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see org.sidiff.javaast.model.impl.JPackageImpl
	 * @see org.sidiff.javaast.model.impl.JavaModelPackageImpl#getJPackage()
	 * @generated
	 */
  int JPACKAGE = 3;

  /**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JPACKAGE__ID = JIDENTIFIABLE_ELEMENT__ID;

  /**
	 * The feature id for the '<em><b>Compileable</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JPACKAGE__COMPILEABLE = JIDENTIFIABLE_ELEMENT__COMPILEABLE;

  /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JPACKAGE__NAME = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 0;

  /**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JPACKAGE__QUALIFIED_NAME = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 1;

		/**
	 * The feature id for the '<em><b>Sub Packages</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JPACKAGE__SUB_PACKAGES = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 2;

  /**
	 * The feature id for the '<em><b>Classes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JPACKAGE__CLASSES = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 3;

  /**
	 * The feature id for the '<em><b>Interfaces</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JPACKAGE__INTERFACES = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 4;

  /**
	 * The number of structural features of the '<em>JPackage</em>' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JPACKAGE_FEATURE_COUNT = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 5;

  /**
	 * The meta object id for the '{@link org.sidiff.javaast.model.impl.JClassifierImpl <em>JClassifier</em>}' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see org.sidiff.javaast.model.impl.JClassifierImpl
	 * @see org.sidiff.javaast.model.impl.JavaModelPackageImpl#getJClassifier()
	 * @generated
	 */
  int JCLASSIFIER = 11;

  /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JCLASSIFIER__NAME = JNAMED_ELEMENT__NAME;

  /**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JCLASSIFIER__QUALIFIED_NAME = JNAMED_ELEMENT__QUALIFIED_NAME;

		/**
	 * The feature id for the '<em><b>Inner Classifiers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JCLASSIFIER__INNER_CLASSIFIERS = JNAMED_ELEMENT_FEATURE_COUNT + 0;

  /**
	 * The number of structural features of the '<em>JClassifier</em>' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JCLASSIFIER_FEATURE_COUNT = JNAMED_ELEMENT_FEATURE_COUNT + 1;

  /**
	 * The meta object id for the '{@link org.sidiff.javaast.model.impl.JClassImpl <em>JClass</em>}' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see org.sidiff.javaast.model.impl.JClassImpl
	 * @see org.sidiff.javaast.model.impl.JavaModelPackageImpl#getJClass()
	 * @generated
	 */
  int JCLASS = 4;

  /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JCLASS__NAME = JCLASSIFIER__NAME;

  /**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JCLASS__QUALIFIED_NAME = JCLASSIFIER__QUALIFIED_NAME;

		/**
	 * The feature id for the '<em><b>Inner Classifiers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JCLASS__INNER_CLASSIFIERS = JCLASSIFIER__INNER_CLASSIFIERS;

  /**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JCLASS__ID = JCLASSIFIER_FEATURE_COUNT + 0;

  /**
	 * The feature id for the '<em><b>Compileable</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JCLASS__COMPILEABLE = JCLASSIFIER_FEATURE_COUNT + 1;

  /**
	 * The feature id for the '<em><b>Java Doc</b></em>' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JCLASS__JAVA_DOC = JCLASSIFIER_FEATURE_COUNT + 2;

  /**
	 * The feature id for the '<em><b>Generic Types</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JCLASS__GENERIC_TYPES = JCLASSIFIER_FEATURE_COUNT + 3;

  /**
	 * The feature id for the '<em><b>Is External</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JCLASS__IS_EXTERNAL = JCLASSIFIER_FEATURE_COUNT + 4;

  /**
	 * The feature id for the '<em><b>Visibility Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JCLASS__VISIBILITY_KIND = JCLASSIFIER_FEATURE_COUNT + 5;

  /**
	 * The feature id for the '<em><b>Fields</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JCLASS__FIELDS = JCLASSIFIER_FEATURE_COUNT + 6;

  /**
	 * The feature id for the '<em><b>Is Abstract</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JCLASS__IS_ABSTRACT = JCLASSIFIER_FEATURE_COUNT + 7;

  /**
	 * The feature id for the '<em><b>Is Final</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JCLASS__IS_FINAL = JCLASSIFIER_FEATURE_COUNT + 8;

  /**
	 * The feature id for the '<em><b>Methods</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JCLASS__METHODS = JCLASSIFIER_FEATURE_COUNT + 9;

  /**
	 * The feature id for the '<em><b>Super Class</b></em>' reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JCLASS__SUPER_CLASS = JCLASSIFIER_FEATURE_COUNT + 10;

  /**
	 * The feature id for the '<em><b>Sub Classes</b></em>' reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JCLASS__SUB_CLASSES = JCLASSIFIER_FEATURE_COUNT + 11;

  /**
	 * The feature id for the '<em><b>Implemented Interfaces</b></em>' reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JCLASS__IMPLEMENTED_INTERFACES = JCLASSIFIER_FEATURE_COUNT + 12;

  /**
	 * The feature id for the '<em><b>Static Initialization Block</b></em>' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JCLASS__STATIC_INITIALIZATION_BLOCK = JCLASSIFIER_FEATURE_COUNT + 13;

  /**
	 * The number of structural features of the '<em>JClass</em>' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JCLASS_FEATURE_COUNT = JCLASSIFIER_FEATURE_COUNT + 14;

  /**
	 * The meta object id for the '{@link org.sidiff.javaast.model.impl.JVisibleElementImpl <em>JVisible Element</em>}' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see org.sidiff.javaast.model.impl.JVisibleElementImpl
	 * @see org.sidiff.javaast.model.impl.JavaModelPackageImpl#getJVisibleElement()
	 * @generated
	 */
  int JVISIBLE_ELEMENT = 5;

  /**
	 * The feature id for the '<em><b>Visibility Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JVISIBLE_ELEMENT__VISIBILITY_KIND = 0;

  /**
	 * The number of structural features of the '<em>JVisible Element</em>' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JVISIBLE_ELEMENT_FEATURE_COUNT = 1;

  /**
	 * The meta object id for the '{@link org.sidiff.javaast.model.impl.JTypeImpl <em>JType</em>}' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see org.sidiff.javaast.model.impl.JTypeImpl
	 * @see org.sidiff.javaast.model.impl.JavaModelPackageImpl#getJType()
	 * @generated
	 */
  int JTYPE = 6;

  /**
	 * The feature id for the '<em><b>Is External</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JTYPE__IS_EXTERNAL = 0;

  /**
	 * The number of structural features of the '<em>JType</em>' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JTYPE_FEATURE_COUNT = 1;

  /**
	 * The meta object id for the '{@link org.sidiff.javaast.model.impl.JTemplateImpl <em>JTemplate</em>}' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see org.sidiff.javaast.model.impl.JTemplateImpl
	 * @see org.sidiff.javaast.model.impl.JavaModelPackageImpl#getJTemplate()
	 * @generated
	 */
  int JTEMPLATE = 7;

  /**
	 * The feature id for the '<em><b>Generic Types</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JTEMPLATE__GENERIC_TYPES = 0;

  /**
	 * The number of structural features of the '<em>JTemplate</em>' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JTEMPLATE_FEATURE_COUNT = 1;

  /**
	 * The meta object id for the '{@link org.sidiff.javaast.model.impl.JGenericTypeImpl <em>JGeneric Type</em>}' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see org.sidiff.javaast.model.impl.JGenericTypeImpl
	 * @see org.sidiff.javaast.model.impl.JavaModelPackageImpl#getJGenericType()
	 * @generated
	 */
  int JGENERIC_TYPE = 8;

  /**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JGENERIC_TYPE__ID = JIDENTIFIABLE_ELEMENT__ID;

  /**
	 * The feature id for the '<em><b>Compileable</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JGENERIC_TYPE__COMPILEABLE = JIDENTIFIABLE_ELEMENT__COMPILEABLE;

  /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JGENERIC_TYPE__NAME = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 0;

  /**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JGENERIC_TYPE__QUALIFIED_NAME = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 1;

		/**
	 * The feature id for the '<em><b>Is External</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JGENERIC_TYPE__IS_EXTERNAL = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 2;

  /**
	 * The number of structural features of the '<em>JGeneric Type</em>' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JGENERIC_TYPE_FEATURE_COUNT = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 3;

  /**
	 * The meta object id for the '{@link org.sidiff.javaast.model.impl.JDocumentableElementImpl <em>JDocumentable Element</em>}' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see org.sidiff.javaast.model.impl.JDocumentableElementImpl
	 * @see org.sidiff.javaast.model.impl.JavaModelPackageImpl#getJDocumentableElement()
	 * @generated
	 */
  int JDOCUMENTABLE_ELEMENT = 9;

  /**
	 * The feature id for the '<em><b>Java Doc</b></em>' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JDOCUMENTABLE_ELEMENT__JAVA_DOC = 0;

  /**
	 * The number of structural features of the '<em>JDocumentable Element</em>' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JDOCUMENTABLE_ELEMENT_FEATURE_COUNT = 1;

  /**
	 * The meta object id for the '{@link org.sidiff.javaast.model.impl.JavaDocImpl <em>Java Doc</em>}' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see org.sidiff.javaast.model.impl.JavaDocImpl
	 * @see org.sidiff.javaast.model.impl.JavaModelPackageImpl#getJavaDoc()
	 * @generated
	 */
  int JAVA_DOC = 10;

  /**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JAVA_DOC__ID = JIDENTIFIABLE_ELEMENT__ID;

  /**
	 * The feature id for the '<em><b>Compileable</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JAVA_DOC__COMPILEABLE = JIDENTIFIABLE_ELEMENT__COMPILEABLE;

  /**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JAVA_DOC__TEXT = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 0;

  /**
	 * The number of structural features of the '<em>Java Doc</em>' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JAVA_DOC_FEATURE_COUNT = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 1;

  /**
	 * The meta object id for the '{@link org.sidiff.javaast.model.impl.JFieldImpl <em>JField</em>}' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see org.sidiff.javaast.model.impl.JFieldImpl
	 * @see org.sidiff.javaast.model.impl.JavaModelPackageImpl#getJField()
	 * @generated
	 */
  int JFIELD = 12;

  /**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JFIELD__ID = JIDENTIFIABLE_ELEMENT__ID;

  /**
	 * The feature id for the '<em><b>Compileable</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JFIELD__COMPILEABLE = JIDENTIFIABLE_ELEMENT__COMPILEABLE;

  /**
	 * The feature id for the '<em><b>Java Doc</b></em>' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JFIELD__JAVA_DOC = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 0;

  /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JFIELD__NAME = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 1;

  /**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JFIELD__QUALIFIED_NAME = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 2;

		/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JFIELD__TYPE = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 3;

  /**
	 * The feature id for the '<em><b>Visibility Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JFIELD__VISIBILITY_KIND = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 4;

  /**
	 * The feature id for the '<em><b>Is Final</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JFIELD__IS_FINAL = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 5;

  /**
	 * The feature id for the '<em><b>Is Static</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JFIELD__IS_STATIC = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 6;

  /**
	 * The feature id for the '<em><b>Accessed By</b></em>' reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JFIELD__ACCESSED_BY = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 7;

  /**
	 * The feature id for the '<em><b>Is Transient</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JFIELD__IS_TRANSIENT = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 8;

  /**
	 * The feature id for the '<em><b>Initialization</b></em>' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JFIELD__INITIALIZATION = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 9;

  /**
	 * The number of structural features of the '<em>JField</em>' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JFIELD_FEATURE_COUNT = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 10;

  /**
	 * The meta object id for the '{@link org.sidiff.javaast.model.impl.JTypedElementImpl <em>JTyped Element</em>}' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see org.sidiff.javaast.model.impl.JTypedElementImpl
	 * @see org.sidiff.javaast.model.impl.JavaModelPackageImpl#getJTypedElement()
	 * @generated
	 */
  int JTYPED_ELEMENT = 13;

  /**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JTYPED_ELEMENT__TYPE = 0;

  /**
	 * The number of structural features of the '<em>JTyped Element</em>' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JTYPED_ELEMENT_FEATURE_COUNT = 1;

  /**
	 * The meta object id for the '{@link org.sidiff.javaast.model.impl.JMethodImpl <em>JMethod</em>}' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see org.sidiff.javaast.model.impl.JMethodImpl
	 * @see org.sidiff.javaast.model.impl.JavaModelPackageImpl#getJMethod()
	 * @generated
	 */
  int JMETHOD = 14;

  /**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JMETHOD__ID = JIDENTIFIABLE_ELEMENT__ID;

  /**
	 * The feature id for the '<em><b>Compileable</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JMETHOD__COMPILEABLE = JIDENTIFIABLE_ELEMENT__COMPILEABLE;

  /**
	 * The feature id for the '<em><b>Java Doc</b></em>' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JMETHOD__JAVA_DOC = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 0;

  /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JMETHOD__NAME = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 1;

  /**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JMETHOD__QUALIFIED_NAME = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 2;

		/**
	 * The feature id for the '<em><b>Generic Types</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JMETHOD__GENERIC_TYPES = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 3;

  /**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JMETHOD__TYPE = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 4;

  /**
	 * The feature id for the '<em><b>Visibility Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JMETHOD__VISIBILITY_KIND = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 5;

  /**
	 * The feature id for the '<em><b>Is Abstract</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JMETHOD__IS_ABSTRACT = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 6;

  /**
	 * The feature id for the '<em><b>Is Final</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JMETHOD__IS_FINAL = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 7;

  /**
	 * The feature id for the '<em><b>Is Static</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JMETHOD__IS_STATIC = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 8;

  /**
	 * The feature id for the '<em><b>Is Constructor</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JMETHOD__IS_CONSTRUCTOR = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 9;

  /**
	 * The feature id for the '<em><b>Is Synchronized</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JMETHOD__IS_SYNCHRONIZED = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 10;

  /**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JMETHOD__PARAMETERS = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 11;

  /**
	 * The feature id for the '<em><b>Calls</b></em>' reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JMETHOD__CALLS = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 12;

  /**
	 * The feature id for the '<em><b>Called By</b></em>' reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JMETHOD__CALLED_BY = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 13;

  /**
	 * The feature id for the '<em><b>Raised Exception</b></em>' reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JMETHOD__RAISED_EXCEPTION = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 14;

  /**
	 * The feature id for the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JMETHOD__BODY = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 15;

  /**
	 * The feature id for the '<em><b>Accesses</b></em>' reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JMETHOD__ACCESSES = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 16;

  /**
	 * The number of structural features of the '<em>JMethod</em>' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JMETHOD_FEATURE_COUNT = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 17;

  /**
	 * The meta object id for the '{@link org.sidiff.javaast.model.impl.JParameterImpl <em>JParameter</em>}' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see org.sidiff.javaast.model.impl.JParameterImpl
	 * @see org.sidiff.javaast.model.impl.JavaModelPackageImpl#getJParameter()
	 * @generated
	 */
  int JPARAMETER = 15;

  /**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JPARAMETER__ID = JIDENTIFIABLE_ELEMENT__ID;

  /**
	 * The feature id for the '<em><b>Compileable</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JPARAMETER__COMPILEABLE = JIDENTIFIABLE_ELEMENT__COMPILEABLE;

  /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JPARAMETER__NAME = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 0;

  /**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JPARAMETER__QUALIFIED_NAME = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 1;

		/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JPARAMETER__TYPE = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 2;

  /**
	 * The feature id for the '<em><b>Is Final</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JPARAMETER__IS_FINAL = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 3;

  /**
	 * The number of structural features of the '<em>JParameter</em>' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JPARAMETER_FEATURE_COUNT = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 4;

  /**
	 * The meta object id for the '{@link org.sidiff.javaast.model.impl.JAdressableFragmentImpl <em>JAdressable Fragment</em>}' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see org.sidiff.javaast.model.impl.JAdressableFragmentImpl
	 * @see org.sidiff.javaast.model.impl.JavaModelPackageImpl#getJAdressableFragment()
	 * @generated
	 */
  int JADRESSABLE_FRAGMENT = 17;

  /**
	 * The feature id for the '<em><b>Start Byte</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JADRESSABLE_FRAGMENT__START_BYTE = 0;

  /**
	 * The feature id for the '<em><b>Byte Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JADRESSABLE_FRAGMENT__BYTE_LENGTH = 1;

  /**
	 * The number of structural features of the '<em>JAdressable Fragment</em>' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JADRESSABLE_FRAGMENT_FEATURE_COUNT = 2;

  /**
	 * The meta object id for the '{@link org.sidiff.javaast.model.impl.JCodeBlockImpl <em>JCode Block</em>}' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see org.sidiff.javaast.model.impl.JCodeBlockImpl
	 * @see org.sidiff.javaast.model.impl.JavaModelPackageImpl#getJCodeBlock()
	 * @generated
	 */
  int JCODE_BLOCK = 16;

  /**
	 * The feature id for the '<em><b>Start Byte</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JCODE_BLOCK__START_BYTE = JADRESSABLE_FRAGMENT__START_BYTE;

  /**
	 * The feature id for the '<em><b>Byte Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JCODE_BLOCK__BYTE_LENGTH = JADRESSABLE_FRAGMENT__BYTE_LENGTH;

  /**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JCODE_BLOCK__ID = JADRESSABLE_FRAGMENT_FEATURE_COUNT + 0;

  /**
	 * The feature id for the '<em><b>Compileable</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JCODE_BLOCK__COMPILEABLE = JADRESSABLE_FRAGMENT_FEATURE_COUNT + 1;

  /**
	 * The feature id for the '<em><b>Code Fragments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JCODE_BLOCK__CODE_FRAGMENTS = JADRESSABLE_FRAGMENT_FEATURE_COUNT + 2;

  /**
	 * The feature id for the '<em><b>Sub Blocks</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JCODE_BLOCK__SUB_BLOCKS = JADRESSABLE_FRAGMENT_FEATURE_COUNT + 3;

  /**
	 * The feature id for the '<em><b>Defined Variables</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JCODE_BLOCK__DEFINED_VARIABLES = JADRESSABLE_FRAGMENT_FEATURE_COUNT + 4;

  /**
	 * The number of structural features of the '<em>JCode Block</em>' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JCODE_BLOCK_FEATURE_COUNT = JADRESSABLE_FRAGMENT_FEATURE_COUNT + 5;

  /**
	 * The meta object id for the '{@link org.sidiff.javaast.model.impl.JCodeFragmentImpl <em>JCode Fragment</em>}' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see org.sidiff.javaast.model.impl.JCodeFragmentImpl
	 * @see org.sidiff.javaast.model.impl.JavaModelPackageImpl#getJCodeFragment()
	 * @generated
	 */
  int JCODE_FRAGMENT = 18;

  /**
	 * The feature id for the '<em><b>Start Byte</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JCODE_FRAGMENT__START_BYTE = JADRESSABLE_FRAGMENT__START_BYTE;

  /**
	 * The feature id for the '<em><b>Byte Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JCODE_FRAGMENT__BYTE_LENGTH = JADRESSABLE_FRAGMENT__BYTE_LENGTH;

  /**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JCODE_FRAGMENT__ID = JADRESSABLE_FRAGMENT_FEATURE_COUNT + 0;

  /**
	 * The feature id for the '<em><b>Compileable</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JCODE_FRAGMENT__COMPILEABLE = JADRESSABLE_FRAGMENT_FEATURE_COUNT + 1;

  /**
	 * The feature id for the '<em><b>Used Types</b></em>' reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JCODE_FRAGMENT__USED_TYPES = JADRESSABLE_FRAGMENT_FEATURE_COUNT + 2;

  /**
	 * The feature id for the '<em><b>Called Methods</b></em>' reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JCODE_FRAGMENT__CALLED_METHODS = JADRESSABLE_FRAGMENT_FEATURE_COUNT + 3;

  /**
	 * The feature id for the '<em><b>Code</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JCODE_FRAGMENT__CODE = JADRESSABLE_FRAGMENT_FEATURE_COUNT + 4;

  /**
	 * The feature id for the '<em><b>Used Fields</b></em>' reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JCODE_FRAGMENT__USED_FIELDS = JADRESSABLE_FRAGMENT_FEATURE_COUNT + 5;

  /**
	 * The feature id for the '<em><b>Used Parameters</b></em>' reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JCODE_FRAGMENT__USED_PARAMETERS = JADRESSABLE_FRAGMENT_FEATURE_COUNT + 6;

  /**
	 * The feature id for the '<em><b>Used Local Variables</b></em>' reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JCODE_FRAGMENT__USED_LOCAL_VARIABLES = JADRESSABLE_FRAGMENT_FEATURE_COUNT + 7;

  /**
	 * The number of structural features of the '<em>JCode Fragment</em>' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JCODE_FRAGMENT_FEATURE_COUNT = JADRESSABLE_FRAGMENT_FEATURE_COUNT + 8;

  /**
	 * The meta object id for the '{@link org.sidiff.javaast.model.impl.JLocalVariableImpl <em>JLocal Variable</em>}' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see org.sidiff.javaast.model.impl.JLocalVariableImpl
	 * @see org.sidiff.javaast.model.impl.JavaModelPackageImpl#getJLocalVariable()
	 * @generated
	 */
  int JLOCAL_VARIABLE = 19;

  /**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JLOCAL_VARIABLE__ID = JIDENTIFIABLE_ELEMENT__ID;

  /**
	 * The feature id for the '<em><b>Compileable</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JLOCAL_VARIABLE__COMPILEABLE = JIDENTIFIABLE_ELEMENT__COMPILEABLE;

  /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JLOCAL_VARIABLE__NAME = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 0;

  /**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JLOCAL_VARIABLE__QUALIFIED_NAME = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 1;

		/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JLOCAL_VARIABLE__TYPE = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 2;

  /**
	 * The number of structural features of the '<em>JLocal Variable</em>' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JLOCAL_VARIABLE_FEATURE_COUNT = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 3;

  /**
	 * The meta object id for the '{@link org.sidiff.javaast.model.impl.JInterfaceImpl <em>JInterface</em>}' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see org.sidiff.javaast.model.impl.JInterfaceImpl
	 * @see org.sidiff.javaast.model.impl.JavaModelPackageImpl#getJInterface()
	 * @generated
	 */
  int JINTERFACE = 20;

  /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JINTERFACE__NAME = JCLASSIFIER__NAME;

  /**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JINTERFACE__QUALIFIED_NAME = JCLASSIFIER__QUALIFIED_NAME;

		/**
	 * The feature id for the '<em><b>Inner Classifiers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JINTERFACE__INNER_CLASSIFIERS = JCLASSIFIER__INNER_CLASSIFIERS;

  /**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JINTERFACE__ID = JCLASSIFIER_FEATURE_COUNT + 0;

  /**
	 * The feature id for the '<em><b>Compileable</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JINTERFACE__COMPILEABLE = JCLASSIFIER_FEATURE_COUNT + 1;

  /**
	 * The feature id for the '<em><b>Java Doc</b></em>' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JINTERFACE__JAVA_DOC = JCLASSIFIER_FEATURE_COUNT + 2;

  /**
	 * The feature id for the '<em><b>Generic Types</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JINTERFACE__GENERIC_TYPES = JCLASSIFIER_FEATURE_COUNT + 3;

  /**
	 * The feature id for the '<em><b>Is External</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JINTERFACE__IS_EXTERNAL = JCLASSIFIER_FEATURE_COUNT + 4;

  /**
	 * The feature id for the '<em><b>Visibility Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JINTERFACE__VISIBILITY_KIND = JCLASSIFIER_FEATURE_COUNT + 5;

  /**
	 * The feature id for the '<em><b>Constants</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JINTERFACE__CONSTANTS = JCLASSIFIER_FEATURE_COUNT + 6;

  /**
	 * The feature id for the '<em><b>Method Signatures</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JINTERFACE__METHOD_SIGNATURES = JCLASSIFIER_FEATURE_COUNT + 7;

  /**
	 * The feature id for the '<em><b>Super Interfaces</b></em>' reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JINTERFACE__SUPER_INTERFACES = JCLASSIFIER_FEATURE_COUNT + 8;

  /**
	 * The feature id for the '<em><b>Sub Interfaces</b></em>' reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JINTERFACE__SUB_INTERFACES = JCLASSIFIER_FEATURE_COUNT + 9;

  /**
	 * The feature id for the '<em><b>Implementing Classes</b></em>' reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JINTERFACE__IMPLEMENTING_CLASSES = JCLASSIFIER_FEATURE_COUNT + 10;

  /**
	 * The number of structural features of the '<em>JInterface</em>' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JINTERFACE_FEATURE_COUNT = JCLASSIFIER_FEATURE_COUNT + 11;

  /**
	 * The meta object id for the '{@link org.sidiff.javaast.model.impl.JConstantImpl <em>JConstant</em>}' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see org.sidiff.javaast.model.impl.JConstantImpl
	 * @see org.sidiff.javaast.model.impl.JavaModelPackageImpl#getJConstant()
	 * @generated
	 */
  int JCONSTANT = 21;

  /**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JCONSTANT__ID = JFIELD__ID;

  /**
	 * The feature id for the '<em><b>Compileable</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JCONSTANT__COMPILEABLE = JFIELD__COMPILEABLE;

  /**
	 * The feature id for the '<em><b>Java Doc</b></em>' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JCONSTANT__JAVA_DOC = JFIELD__JAVA_DOC;

  /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JCONSTANT__NAME = JFIELD__NAME;

  /**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JCONSTANT__QUALIFIED_NAME = JFIELD__QUALIFIED_NAME;

		/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JCONSTANT__TYPE = JFIELD__TYPE;

  /**
	 * The feature id for the '<em><b>Visibility Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JCONSTANT__VISIBILITY_KIND = JFIELD__VISIBILITY_KIND;

  /**
	 * The feature id for the '<em><b>Is Final</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JCONSTANT__IS_FINAL = JFIELD__IS_FINAL;

  /**
	 * The feature id for the '<em><b>Is Static</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JCONSTANT__IS_STATIC = JFIELD__IS_STATIC;

  /**
	 * The feature id for the '<em><b>Accessed By</b></em>' reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JCONSTANT__ACCESSED_BY = JFIELD__ACCESSED_BY;

  /**
	 * The feature id for the '<em><b>Is Transient</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JCONSTANT__IS_TRANSIENT = JFIELD__IS_TRANSIENT;

  /**
	 * The feature id for the '<em><b>Initialization</b></em>' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JCONSTANT__INITIALIZATION = JFIELD__INITIALIZATION;

  /**
	 * The number of structural features of the '<em>JConstant</em>' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JCONSTANT_FEATURE_COUNT = JFIELD_FEATURE_COUNT + 0;

  /**
	 * The meta object id for the '{@link org.sidiff.javaast.model.impl.JArrayTypeImpl <em>JArray Type</em>}' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see org.sidiff.javaast.model.impl.JArrayTypeImpl
	 * @see org.sidiff.javaast.model.impl.JavaModelPackageImpl#getJArrayType()
	 * @generated
	 */
  int JARRAY_TYPE = 22;

  /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JARRAY_TYPE__NAME = JCLASS__NAME;

  /**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JARRAY_TYPE__QUALIFIED_NAME = JCLASS__QUALIFIED_NAME;

		/**
	 * The feature id for the '<em><b>Inner Classifiers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JARRAY_TYPE__INNER_CLASSIFIERS = JCLASS__INNER_CLASSIFIERS;

  /**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JARRAY_TYPE__ID = JCLASS__ID;

  /**
	 * The feature id for the '<em><b>Compileable</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JARRAY_TYPE__COMPILEABLE = JCLASS__COMPILEABLE;

  /**
	 * The feature id for the '<em><b>Java Doc</b></em>' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JARRAY_TYPE__JAVA_DOC = JCLASS__JAVA_DOC;

  /**
	 * The feature id for the '<em><b>Generic Types</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JARRAY_TYPE__GENERIC_TYPES = JCLASS__GENERIC_TYPES;

  /**
	 * The feature id for the '<em><b>Is External</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JARRAY_TYPE__IS_EXTERNAL = JCLASS__IS_EXTERNAL;

  /**
	 * The feature id for the '<em><b>Visibility Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JARRAY_TYPE__VISIBILITY_KIND = JCLASS__VISIBILITY_KIND;

  /**
	 * The feature id for the '<em><b>Fields</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JARRAY_TYPE__FIELDS = JCLASS__FIELDS;

  /**
	 * The feature id for the '<em><b>Is Abstract</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JARRAY_TYPE__IS_ABSTRACT = JCLASS__IS_ABSTRACT;

  /**
	 * The feature id for the '<em><b>Is Final</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JARRAY_TYPE__IS_FINAL = JCLASS__IS_FINAL;

  /**
	 * The feature id for the '<em><b>Methods</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JARRAY_TYPE__METHODS = JCLASS__METHODS;

  /**
	 * The feature id for the '<em><b>Super Class</b></em>' reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JARRAY_TYPE__SUPER_CLASS = JCLASS__SUPER_CLASS;

  /**
	 * The feature id for the '<em><b>Sub Classes</b></em>' reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JARRAY_TYPE__SUB_CLASSES = JCLASS__SUB_CLASSES;

  /**
	 * The feature id for the '<em><b>Implemented Interfaces</b></em>' reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JARRAY_TYPE__IMPLEMENTED_INTERFACES = JCLASS__IMPLEMENTED_INTERFACES;

  /**
	 * The feature id for the '<em><b>Static Initialization Block</b></em>' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JARRAY_TYPE__STATIC_INITIALIZATION_BLOCK = JCLASS__STATIC_INITIALIZATION_BLOCK;

  /**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JARRAY_TYPE__TYPE = JCLASS_FEATURE_COUNT + 0;

  /**
	 * The feature id for the '<em><b>Array Dimensions</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JARRAY_TYPE__ARRAY_DIMENSIONS = JCLASS_FEATURE_COUNT + 1;

  /**
	 * The number of structural features of the '<em>JArray Type</em>' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JARRAY_TYPE_FEATURE_COUNT = JCLASS_FEATURE_COUNT + 2;

  /**
	 * The meta object id for the '{@link org.sidiff.javaast.model.impl.JSimpleTypeImpl <em>JSimple Type</em>}' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see org.sidiff.javaast.model.impl.JSimpleTypeImpl
	 * @see org.sidiff.javaast.model.impl.JavaModelPackageImpl#getJSimpleType()
	 * @generated
	 */
  int JSIMPLE_TYPE = 23;

  /**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JSIMPLE_TYPE__ID = JIDENTIFIABLE_ELEMENT__ID;

  /**
	 * The feature id for the '<em><b>Compileable</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JSIMPLE_TYPE__COMPILEABLE = JIDENTIFIABLE_ELEMENT__COMPILEABLE;

  /**
	 * The feature id for the '<em><b>Is External</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JSIMPLE_TYPE__IS_EXTERNAL = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 0;

  /**
	 * The feature id for the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JSIMPLE_TYPE__KIND = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 1;

  /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSIMPLE_TYPE__NAME = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 2;

		/**
	 * The number of structural features of the '<em>JSimple Type</em>' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JSIMPLE_TYPE_FEATURE_COUNT = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 3;

  /**
	 * The meta object id for the '{@link org.sidiff.javaast.model.impl.JTemplateWrapperImpl <em>JTemplate Wrapper</em>}' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see org.sidiff.javaast.model.impl.JTemplateWrapperImpl
	 * @see org.sidiff.javaast.model.impl.JavaModelPackageImpl#getJTemplateWrapper()
	 * @generated
	 */
  int JTEMPLATE_WRAPPER = 24;

  /**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JTEMPLATE_WRAPPER__ID = JIDENTIFIABLE_ELEMENT__ID;

  /**
	 * The feature id for the '<em><b>Compileable</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JTEMPLATE_WRAPPER__COMPILEABLE = JIDENTIFIABLE_ELEMENT__COMPILEABLE;

  /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JTEMPLATE_WRAPPER__NAME = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 0;

  /**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JTEMPLATE_WRAPPER__QUALIFIED_NAME = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 1;

		/**
	 * The feature id for the '<em><b>Is External</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JTEMPLATE_WRAPPER__IS_EXTERNAL = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 2;

  /**
	 * The feature id for the '<em><b>Bindings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JTEMPLATE_WRAPPER__BINDINGS = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 3;

  /**
	 * The number of structural features of the '<em>JTemplate Wrapper</em>' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JTEMPLATE_WRAPPER_FEATURE_COUNT = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 4;

  /**
	 * The meta object id for the '{@link org.sidiff.javaast.model.impl.JTemplateBindingImpl <em>JTemplate Binding</em>}' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see org.sidiff.javaast.model.impl.JTemplateBindingImpl
	 * @see org.sidiff.javaast.model.impl.JavaModelPackageImpl#getJTemplateBinding()
	 * @generated
	 */
  int JTEMPLATE_BINDING = 25;

  /**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JTEMPLATE_BINDING__ID = JIDENTIFIABLE_ELEMENT__ID;

  /**
	 * The feature id for the '<em><b>Compileable</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JTEMPLATE_BINDING__COMPILEABLE = JIDENTIFIABLE_ELEMENT__COMPILEABLE;

  /**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JTEMPLATE_BINDING__TYPE = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 0;

  /**
	 * The feature id for the '<em><b>Generic Type</b></em>' reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JTEMPLATE_BINDING__GENERIC_TYPE = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 1;

  /**
	 * The number of structural features of the '<em>JTemplate Binding</em>' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JTEMPLATE_BINDING_FEATURE_COUNT = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 2;

  /**
	 * The meta object id for the '{@link org.sidiff.javaast.model.impl.JStructuralCodeFragmentImpl <em>JStructural Code Fragment</em>}' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see org.sidiff.javaast.model.impl.JStructuralCodeFragmentImpl
	 * @see org.sidiff.javaast.model.impl.JavaModelPackageImpl#getJStructuralCodeFragment()
	 * @generated
	 */
  int JSTRUCTURAL_CODE_FRAGMENT = 26;

  /**
	 * The feature id for the '<em><b>Start Byte</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JSTRUCTURAL_CODE_FRAGMENT__START_BYTE = JCODE_FRAGMENT__START_BYTE;

  /**
	 * The feature id for the '<em><b>Byte Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JSTRUCTURAL_CODE_FRAGMENT__BYTE_LENGTH = JCODE_FRAGMENT__BYTE_LENGTH;

  /**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JSTRUCTURAL_CODE_FRAGMENT__ID = JCODE_FRAGMENT__ID;

  /**
	 * The feature id for the '<em><b>Compileable</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JSTRUCTURAL_CODE_FRAGMENT__COMPILEABLE = JCODE_FRAGMENT__COMPILEABLE;

  /**
	 * The feature id for the '<em><b>Used Types</b></em>' reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JSTRUCTURAL_CODE_FRAGMENT__USED_TYPES = JCODE_FRAGMENT__USED_TYPES;

  /**
	 * The feature id for the '<em><b>Called Methods</b></em>' reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JSTRUCTURAL_CODE_FRAGMENT__CALLED_METHODS = JCODE_FRAGMENT__CALLED_METHODS;

  /**
	 * The feature id for the '<em><b>Code</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JSTRUCTURAL_CODE_FRAGMENT__CODE = JCODE_FRAGMENT__CODE;

  /**
	 * The feature id for the '<em><b>Used Fields</b></em>' reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JSTRUCTURAL_CODE_FRAGMENT__USED_FIELDS = JCODE_FRAGMENT__USED_FIELDS;

  /**
	 * The feature id for the '<em><b>Used Parameters</b></em>' reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JSTRUCTURAL_CODE_FRAGMENT__USED_PARAMETERS = JCODE_FRAGMENT__USED_PARAMETERS;

  /**
	 * The feature id for the '<em><b>Used Local Variables</b></em>' reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JSTRUCTURAL_CODE_FRAGMENT__USED_LOCAL_VARIABLES = JCODE_FRAGMENT__USED_LOCAL_VARIABLES;

  /**
	 * The feature id for the '<em><b>Fragment Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JSTRUCTURAL_CODE_FRAGMENT__FRAGMENT_TYPE = JCODE_FRAGMENT_FEATURE_COUNT + 0;

  /**
	 * The feature id for the '<em><b>Code Blocks</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JSTRUCTURAL_CODE_FRAGMENT__CODE_BLOCKS = JCODE_FRAGMENT_FEATURE_COUNT + 1;

  /**
	 * The number of structural features of the '<em>JStructural Code Fragment</em>' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JSTRUCTURAL_CODE_FRAGMENT_FEATURE_COUNT = JCODE_FRAGMENT_FEATURE_COUNT + 2;

  /**
	 * The meta object id for the '{@link org.sidiff.javaast.model.impl.JEnumerationImpl <em>JEnumeration</em>}' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see org.sidiff.javaast.model.impl.JEnumerationImpl
	 * @see org.sidiff.javaast.model.impl.JavaModelPackageImpl#getJEnumeration()
	 * @generated
	 */
  int JENUMERATION = 27;

  /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JENUMERATION__NAME = JCLASS__NAME;

  /**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JENUMERATION__QUALIFIED_NAME = JCLASS__QUALIFIED_NAME;

		/**
	 * The feature id for the '<em><b>Inner Classifiers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JENUMERATION__INNER_CLASSIFIERS = JCLASS__INNER_CLASSIFIERS;

  /**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JENUMERATION__ID = JCLASS__ID;

  /**
	 * The feature id for the '<em><b>Compileable</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JENUMERATION__COMPILEABLE = JCLASS__COMPILEABLE;

  /**
	 * The feature id for the '<em><b>Java Doc</b></em>' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JENUMERATION__JAVA_DOC = JCLASS__JAVA_DOC;

  /**
	 * The feature id for the '<em><b>Generic Types</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JENUMERATION__GENERIC_TYPES = JCLASS__GENERIC_TYPES;

  /**
	 * The feature id for the '<em><b>Is External</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JENUMERATION__IS_EXTERNAL = JCLASS__IS_EXTERNAL;

  /**
	 * The feature id for the '<em><b>Visibility Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JENUMERATION__VISIBILITY_KIND = JCLASS__VISIBILITY_KIND;

  /**
	 * The feature id for the '<em><b>Fields</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JENUMERATION__FIELDS = JCLASS__FIELDS;

  /**
	 * The feature id for the '<em><b>Is Abstract</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JENUMERATION__IS_ABSTRACT = JCLASS__IS_ABSTRACT;

  /**
	 * The feature id for the '<em><b>Is Final</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JENUMERATION__IS_FINAL = JCLASS__IS_FINAL;

  /**
	 * The feature id for the '<em><b>Methods</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JENUMERATION__METHODS = JCLASS__METHODS;

  /**
	 * The feature id for the '<em><b>Super Class</b></em>' reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JENUMERATION__SUPER_CLASS = JCLASS__SUPER_CLASS;

  /**
	 * The feature id for the '<em><b>Sub Classes</b></em>' reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JENUMERATION__SUB_CLASSES = JCLASS__SUB_CLASSES;

  /**
	 * The feature id for the '<em><b>Implemented Interfaces</b></em>' reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JENUMERATION__IMPLEMENTED_INTERFACES = JCLASS__IMPLEMENTED_INTERFACES;

  /**
	 * The feature id for the '<em><b>Static Initialization Block</b></em>' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JENUMERATION__STATIC_INITIALIZATION_BLOCK = JCLASS__STATIC_INITIALIZATION_BLOCK;

  /**
	 * The feature id for the '<em><b>Literals</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JENUMERATION__LITERALS = JCLASS_FEATURE_COUNT + 0;

  /**
	 * The number of structural features of the '<em>JEnumeration</em>' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JENUMERATION_FEATURE_COUNT = JCLASS_FEATURE_COUNT + 1;

  /**
	 * The meta object id for the '{@link org.sidiff.javaast.model.impl.JEnumerationLiteralImpl <em>JEnumeration Literal</em>}' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see org.sidiff.javaast.model.impl.JEnumerationLiteralImpl
	 * @see org.sidiff.javaast.model.impl.JavaModelPackageImpl#getJEnumerationLiteral()
	 * @generated
	 */
  int JENUMERATION_LITERAL = 28;

  /**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JENUMERATION_LITERAL__ID = JIDENTIFIABLE_ELEMENT__ID;

  /**
	 * The feature id for the '<em><b>Compileable</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JENUMERATION_LITERAL__COMPILEABLE = JIDENTIFIABLE_ELEMENT__COMPILEABLE;

  /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JENUMERATION_LITERAL__NAME = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 0;

  /**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JENUMERATION_LITERAL__QUALIFIED_NAME = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 1;

		/**
	 * The number of structural features of the '<em>JEnumeration Literal</em>' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int JENUMERATION_LITERAL_FEATURE_COUNT = JIDENTIFIABLE_ELEMENT_FEATURE_COUNT + 2;

  /**
	 * The meta object id for the '{@link org.sidiff.javaast.model.JVisibilityKind <em>JVisibility Kind</em>}' enum.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see org.sidiff.javaast.model.JVisibilityKind
	 * @see org.sidiff.javaast.model.impl.JavaModelPackageImpl#getJVisibilityKind()
	 * @generated
	 */
  int JVISIBILITY_KIND = 29;

  /**
	 * The meta object id for the '{@link org.sidiff.javaast.model.JSimpleTypeKind <em>JSimple Type Kind</em>}' enum.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see org.sidiff.javaast.model.JSimpleTypeKind
	 * @see org.sidiff.javaast.model.impl.JavaModelPackageImpl#getJSimpleTypeKind()
	 * @generated
	 */
  int JSIMPLE_TYPE_KIND = 30;

  /**
	 * The meta object id for the '{@link org.sidiff.javaast.model.StructuralFragmentType <em>Structural Fragment Type</em>}' enum.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see org.sidiff.javaast.model.StructuralFragmentType
	 * @see org.sidiff.javaast.model.impl.JavaModelPackageImpl#getStructuralFragmentType()
	 * @generated
	 */
  int STRUCTURAL_FRAGMENT_TYPE = 31;


  /**
	 * Returns the meta object for class '{@link org.sidiff.javaast.model.JProject <em>JProject</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for class '<em>JProject</em>'.
	 * @see org.sidiff.javaast.model.JProject
	 * @generated
	 */
  EClass getJProject();

  /**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.javaast.model.JProject#getPackages <em>Packages</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Packages</em>'.
	 * @see org.sidiff.javaast.model.JProject#getPackages()
	 * @see #getJProject()
	 * @generated
	 */
  EReference getJProject_Packages();

  /**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.javaast.model.JProject#getSimpleTypes <em>Simple Types</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Simple Types</em>'.
	 * @see org.sidiff.javaast.model.JProject#getSimpleTypes()
	 * @see #getJProject()
	 * @generated
	 */
  EReference getJProject_SimpleTypes();

  /**
	 * Returns the meta object for class '{@link org.sidiff.javaast.model.JNamedElement <em>JNamed Element</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for class '<em>JNamed Element</em>'.
	 * @see org.sidiff.javaast.model.JNamedElement
	 * @generated
	 */
  EClass getJNamedElement();

  /**
	 * Returns the meta object for the attribute '{@link org.sidiff.javaast.model.JNamedElement#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.sidiff.javaast.model.JNamedElement#getName()
	 * @see #getJNamedElement()
	 * @generated
	 */
  EAttribute getJNamedElement_Name();

  /**
	 * Returns the meta object for the attribute '{@link org.sidiff.javaast.model.JNamedElement#getQualifiedName <em>Qualified Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Qualified Name</em>'.
	 * @see org.sidiff.javaast.model.JNamedElement#getQualifiedName()
	 * @see #getJNamedElement()
	 * @generated
	 */
	EAttribute getJNamedElement_QualifiedName();

		/**
	 * Returns the meta object for class '{@link org.sidiff.javaast.model.JIdentifiableElement <em>JIdentifiable Element</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for class '<em>JIdentifiable Element</em>'.
	 * @see org.sidiff.javaast.model.JIdentifiableElement
	 * @generated
	 */
  EClass getJIdentifiableElement();

  /**
	 * Returns the meta object for the attribute '{@link org.sidiff.javaast.model.JIdentifiableElement#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.sidiff.javaast.model.JIdentifiableElement#getId()
	 * @see #getJIdentifiableElement()
	 * @generated
	 */
  EAttribute getJIdentifiableElement_Id();

  /**
	 * Returns the meta object for the attribute '{@link org.sidiff.javaast.model.JIdentifiableElement#isCompileable <em>Compileable</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Compileable</em>'.
	 * @see org.sidiff.javaast.model.JIdentifiableElement#isCompileable()
	 * @see #getJIdentifiableElement()
	 * @generated
	 */
  EAttribute getJIdentifiableElement_Compileable();

  /**
	 * Returns the meta object for class '{@link org.sidiff.javaast.model.JPackage <em>JPackage</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for class '<em>JPackage</em>'.
	 * @see org.sidiff.javaast.model.JPackage
	 * @generated
	 */
  EClass getJPackage();

  /**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.javaast.model.JPackage#getSubPackages <em>Sub Packages</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Sub Packages</em>'.
	 * @see org.sidiff.javaast.model.JPackage#getSubPackages()
	 * @see #getJPackage()
	 * @generated
	 */
  EReference getJPackage_SubPackages();

  /**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.javaast.model.JPackage#getClasses <em>Classes</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Classes</em>'.
	 * @see org.sidiff.javaast.model.JPackage#getClasses()
	 * @see #getJPackage()
	 * @generated
	 */
  EReference getJPackage_Classes();

  /**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.javaast.model.JPackage#getInterfaces <em>Interfaces</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Interfaces</em>'.
	 * @see org.sidiff.javaast.model.JPackage#getInterfaces()
	 * @see #getJPackage()
	 * @generated
	 */
  EReference getJPackage_Interfaces();

  /**
	 * Returns the meta object for class '{@link org.sidiff.javaast.model.JClass <em>JClass</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for class '<em>JClass</em>'.
	 * @see org.sidiff.javaast.model.JClass
	 * @generated
	 */
  EClass getJClass();

  /**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.javaast.model.JClass#getFields <em>Fields</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Fields</em>'.
	 * @see org.sidiff.javaast.model.JClass#getFields()
	 * @see #getJClass()
	 * @generated
	 */
  EReference getJClass_Fields();

  /**
	 * Returns the meta object for the attribute '{@link org.sidiff.javaast.model.JClass#isIsAbstract <em>Is Abstract</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Abstract</em>'.
	 * @see org.sidiff.javaast.model.JClass#isIsAbstract()
	 * @see #getJClass()
	 * @generated
	 */
  EAttribute getJClass_IsAbstract();

  /**
	 * Returns the meta object for the attribute '{@link org.sidiff.javaast.model.JClass#isIsFinal <em>Is Final</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Final</em>'.
	 * @see org.sidiff.javaast.model.JClass#isIsFinal()
	 * @see #getJClass()
	 * @generated
	 */
  EAttribute getJClass_IsFinal();

  /**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.javaast.model.JClass#getMethods <em>Methods</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Methods</em>'.
	 * @see org.sidiff.javaast.model.JClass#getMethods()
	 * @see #getJClass()
	 * @generated
	 */
  EReference getJClass_Methods();

  /**
	 * Returns the meta object for the reference '{@link org.sidiff.javaast.model.JClass#getSuperClass <em>Super Class</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Super Class</em>'.
	 * @see org.sidiff.javaast.model.JClass#getSuperClass()
	 * @see #getJClass()
	 * @generated
	 */
  EReference getJClass_SuperClass();

  /**
	 * Returns the meta object for the reference list '{@link org.sidiff.javaast.model.JClass#getSubClasses <em>Sub Classes</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Sub Classes</em>'.
	 * @see org.sidiff.javaast.model.JClass#getSubClasses()
	 * @see #getJClass()
	 * @generated
	 */
  EReference getJClass_SubClasses();

  /**
	 * Returns the meta object for the reference list '{@link org.sidiff.javaast.model.JClass#getImplementedInterfaces <em>Implemented Interfaces</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Implemented Interfaces</em>'.
	 * @see org.sidiff.javaast.model.JClass#getImplementedInterfaces()
	 * @see #getJClass()
	 * @generated
	 */
  EReference getJClass_ImplementedInterfaces();

  /**
	 * Returns the meta object for the containment reference '{@link org.sidiff.javaast.model.JClass#getStaticInitializationBlock <em>Static Initialization Block</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Static Initialization Block</em>'.
	 * @see org.sidiff.javaast.model.JClass#getStaticInitializationBlock()
	 * @see #getJClass()
	 * @generated
	 */
  EReference getJClass_StaticInitializationBlock();

  /**
	 * Returns the meta object for class '{@link org.sidiff.javaast.model.JVisibleElement <em>JVisible Element</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for class '<em>JVisible Element</em>'.
	 * @see org.sidiff.javaast.model.JVisibleElement
	 * @generated
	 */
  EClass getJVisibleElement();

  /**
	 * Returns the meta object for the attribute '{@link org.sidiff.javaast.model.JVisibleElement#getVisibilityKind <em>Visibility Kind</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Visibility Kind</em>'.
	 * @see org.sidiff.javaast.model.JVisibleElement#getVisibilityKind()
	 * @see #getJVisibleElement()
	 * @generated
	 */
  EAttribute getJVisibleElement_VisibilityKind();

  /**
	 * Returns the meta object for class '{@link org.sidiff.javaast.model.JType <em>JType</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for class '<em>JType</em>'.
	 * @see org.sidiff.javaast.model.JType
	 * @generated
	 */
  EClass getJType();

  /**
	 * Returns the meta object for the attribute '{@link org.sidiff.javaast.model.JType#isIsExternal <em>Is External</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is External</em>'.
	 * @see org.sidiff.javaast.model.JType#isIsExternal()
	 * @see #getJType()
	 * @generated
	 */
  EAttribute getJType_IsExternal();

  /**
	 * Returns the meta object for class '{@link org.sidiff.javaast.model.JTemplate <em>JTemplate</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for class '<em>JTemplate</em>'.
	 * @see org.sidiff.javaast.model.JTemplate
	 * @generated
	 */
  EClass getJTemplate();

  /**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.javaast.model.JTemplate#getGenericTypes <em>Generic Types</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Generic Types</em>'.
	 * @see org.sidiff.javaast.model.JTemplate#getGenericTypes()
	 * @see #getJTemplate()
	 * @generated
	 */
  EReference getJTemplate_GenericTypes();

  /**
	 * Returns the meta object for class '{@link org.sidiff.javaast.model.JGenericType <em>JGeneric Type</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for class '<em>JGeneric Type</em>'.
	 * @see org.sidiff.javaast.model.JGenericType
	 * @generated
	 */
  EClass getJGenericType();

  /**
	 * Returns the meta object for class '{@link org.sidiff.javaast.model.JDocumentableElement <em>JDocumentable Element</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for class '<em>JDocumentable Element</em>'.
	 * @see org.sidiff.javaast.model.JDocumentableElement
	 * @generated
	 */
  EClass getJDocumentableElement();

  /**
	 * Returns the meta object for the containment reference '{@link org.sidiff.javaast.model.JDocumentableElement#getJavaDoc <em>Java Doc</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Java Doc</em>'.
	 * @see org.sidiff.javaast.model.JDocumentableElement#getJavaDoc()
	 * @see #getJDocumentableElement()
	 * @generated
	 */
  EReference getJDocumentableElement_JavaDoc();

  /**
	 * Returns the meta object for class '{@link org.sidiff.javaast.model.JavaDoc <em>Java Doc</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Java Doc</em>'.
	 * @see org.sidiff.javaast.model.JavaDoc
	 * @generated
	 */
  EClass getJavaDoc();

  /**
	 * Returns the meta object for the attribute '{@link org.sidiff.javaast.model.JavaDoc#getText <em>Text</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Text</em>'.
	 * @see org.sidiff.javaast.model.JavaDoc#getText()
	 * @see #getJavaDoc()
	 * @generated
	 */
  EAttribute getJavaDoc_Text();

  /**
	 * Returns the meta object for class '{@link org.sidiff.javaast.model.JClassifier <em>JClassifier</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for class '<em>JClassifier</em>'.
	 * @see org.sidiff.javaast.model.JClassifier
	 * @generated
	 */
  EClass getJClassifier();

  /**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.javaast.model.JClassifier#getInnerClassifiers <em>Inner Classifiers</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Inner Classifiers</em>'.
	 * @see org.sidiff.javaast.model.JClassifier#getInnerClassifiers()
	 * @see #getJClassifier()
	 * @generated
	 */
  EReference getJClassifier_InnerClassifiers();

  /**
	 * Returns the meta object for class '{@link org.sidiff.javaast.model.JField <em>JField</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for class '<em>JField</em>'.
	 * @see org.sidiff.javaast.model.JField
	 * @generated
	 */
  EClass getJField();

  /**
	 * Returns the meta object for the attribute '{@link org.sidiff.javaast.model.JField#isIsFinal <em>Is Final</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Final</em>'.
	 * @see org.sidiff.javaast.model.JField#isIsFinal()
	 * @see #getJField()
	 * @generated
	 */
  EAttribute getJField_IsFinal();

  /**
	 * Returns the meta object for the attribute '{@link org.sidiff.javaast.model.JField#isIsStatic <em>Is Static</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Static</em>'.
	 * @see org.sidiff.javaast.model.JField#isIsStatic()
	 * @see #getJField()
	 * @generated
	 */
  EAttribute getJField_IsStatic();

  /**
	 * Returns the meta object for the reference list '{@link org.sidiff.javaast.model.JField#getAccessedBy <em>Accessed By</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Accessed By</em>'.
	 * @see org.sidiff.javaast.model.JField#getAccessedBy()
	 * @see #getJField()
	 * @generated
	 */
  EReference getJField_AccessedBy();

  /**
	 * Returns the meta object for the attribute '{@link org.sidiff.javaast.model.JField#isIsTransient <em>Is Transient</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Transient</em>'.
	 * @see org.sidiff.javaast.model.JField#isIsTransient()
	 * @see #getJField()
	 * @generated
	 */
  EAttribute getJField_IsTransient();

  /**
	 * Returns the meta object for the containment reference '{@link org.sidiff.javaast.model.JField#getInitialization <em>Initialization</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Initialization</em>'.
	 * @see org.sidiff.javaast.model.JField#getInitialization()
	 * @see #getJField()
	 * @generated
	 */
  EReference getJField_Initialization();

  /**
	 * Returns the meta object for class '{@link org.sidiff.javaast.model.JTypedElement <em>JTyped Element</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for class '<em>JTyped Element</em>'.
	 * @see org.sidiff.javaast.model.JTypedElement
	 * @generated
	 */
  EClass getJTypedElement();

  /**
	 * Returns the meta object for the reference '{@link org.sidiff.javaast.model.JTypedElement#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see org.sidiff.javaast.model.JTypedElement#getType()
	 * @see #getJTypedElement()
	 * @generated
	 */
  EReference getJTypedElement_Type();

  /**
	 * Returns the meta object for class '{@link org.sidiff.javaast.model.JMethod <em>JMethod</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for class '<em>JMethod</em>'.
	 * @see org.sidiff.javaast.model.JMethod
	 * @generated
	 */
  EClass getJMethod();

  /**
	 * Returns the meta object for the attribute '{@link org.sidiff.javaast.model.JMethod#isIsAbstract <em>Is Abstract</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Abstract</em>'.
	 * @see org.sidiff.javaast.model.JMethod#isIsAbstract()
	 * @see #getJMethod()
	 * @generated
	 */
  EAttribute getJMethod_IsAbstract();

  /**
	 * Returns the meta object for the attribute '{@link org.sidiff.javaast.model.JMethod#isIsFinal <em>Is Final</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Final</em>'.
	 * @see org.sidiff.javaast.model.JMethod#isIsFinal()
	 * @see #getJMethod()
	 * @generated
	 */
  EAttribute getJMethod_IsFinal();

  /**
	 * Returns the meta object for the attribute '{@link org.sidiff.javaast.model.JMethod#isIsStatic <em>Is Static</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Static</em>'.
	 * @see org.sidiff.javaast.model.JMethod#isIsStatic()
	 * @see #getJMethod()
	 * @generated
	 */
  EAttribute getJMethod_IsStatic();

  /**
	 * Returns the meta object for the attribute '{@link org.sidiff.javaast.model.JMethod#isIsConstructor <em>Is Constructor</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Constructor</em>'.
	 * @see org.sidiff.javaast.model.JMethod#isIsConstructor()
	 * @see #getJMethod()
	 * @generated
	 */
  EAttribute getJMethod_IsConstructor();

  /**
	 * Returns the meta object for the attribute '{@link org.sidiff.javaast.model.JMethod#isIsSynchronized <em>Is Synchronized</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Synchronized</em>'.
	 * @see org.sidiff.javaast.model.JMethod#isIsSynchronized()
	 * @see #getJMethod()
	 * @generated
	 */
  EAttribute getJMethod_IsSynchronized();

  /**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.javaast.model.JMethod#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameters</em>'.
	 * @see org.sidiff.javaast.model.JMethod#getParameters()
	 * @see #getJMethod()
	 * @generated
	 */
  EReference getJMethod_Parameters();

  /**
	 * Returns the meta object for the reference list '{@link org.sidiff.javaast.model.JMethod#getCalls <em>Calls</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Calls</em>'.
	 * @see org.sidiff.javaast.model.JMethod#getCalls()
	 * @see #getJMethod()
	 * @generated
	 */
  EReference getJMethod_Calls();

  /**
	 * Returns the meta object for the reference list '{@link org.sidiff.javaast.model.JMethod#getCalledBy <em>Called By</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Called By</em>'.
	 * @see org.sidiff.javaast.model.JMethod#getCalledBy()
	 * @see #getJMethod()
	 * @generated
	 */
  EReference getJMethod_CalledBy();

  /**
	 * Returns the meta object for the reference '{@link org.sidiff.javaast.model.JMethod#getRaisedException <em>Raised Exception</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Raised Exception</em>'.
	 * @see org.sidiff.javaast.model.JMethod#getRaisedException()
	 * @see #getJMethod()
	 * @generated
	 */
  EReference getJMethod_RaisedException();

  /**
	 * Returns the meta object for the containment reference '{@link org.sidiff.javaast.model.JMethod#getBody <em>Body</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Body</em>'.
	 * @see org.sidiff.javaast.model.JMethod#getBody()
	 * @see #getJMethod()
	 * @generated
	 */
  EReference getJMethod_Body();

  /**
	 * Returns the meta object for the reference list '{@link org.sidiff.javaast.model.JMethod#getAccesses <em>Accesses</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Accesses</em>'.
	 * @see org.sidiff.javaast.model.JMethod#getAccesses()
	 * @see #getJMethod()
	 * @generated
	 */
  EReference getJMethod_Accesses();

  /**
	 * Returns the meta object for class '{@link org.sidiff.javaast.model.JParameter <em>JParameter</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for class '<em>JParameter</em>'.
	 * @see org.sidiff.javaast.model.JParameter
	 * @generated
	 */
  EClass getJParameter();

  /**
	 * Returns the meta object for the attribute '{@link org.sidiff.javaast.model.JParameter#isIsFinal <em>Is Final</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Final</em>'.
	 * @see org.sidiff.javaast.model.JParameter#isIsFinal()
	 * @see #getJParameter()
	 * @generated
	 */
  EAttribute getJParameter_IsFinal();

  /**
	 * Returns the meta object for class '{@link org.sidiff.javaast.model.JCodeBlock <em>JCode Block</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for class '<em>JCode Block</em>'.
	 * @see org.sidiff.javaast.model.JCodeBlock
	 * @generated
	 */
  EClass getJCodeBlock();

  /**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.javaast.model.JCodeBlock#getCodeFragments <em>Code Fragments</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Code Fragments</em>'.
	 * @see org.sidiff.javaast.model.JCodeBlock#getCodeFragments()
	 * @see #getJCodeBlock()
	 * @generated
	 */
  EReference getJCodeBlock_CodeFragments();

  /**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.javaast.model.JCodeBlock#getSubBlocks <em>Sub Blocks</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Sub Blocks</em>'.
	 * @see org.sidiff.javaast.model.JCodeBlock#getSubBlocks()
	 * @see #getJCodeBlock()
	 * @generated
	 */
  EReference getJCodeBlock_SubBlocks();

  /**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.javaast.model.JCodeBlock#getDefinedVariables <em>Defined Variables</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Defined Variables</em>'.
	 * @see org.sidiff.javaast.model.JCodeBlock#getDefinedVariables()
	 * @see #getJCodeBlock()
	 * @generated
	 */
  EReference getJCodeBlock_DefinedVariables();

  /**
	 * Returns the meta object for class '{@link org.sidiff.javaast.model.JAdressableFragment <em>JAdressable Fragment</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for class '<em>JAdressable Fragment</em>'.
	 * @see org.sidiff.javaast.model.JAdressableFragment
	 * @generated
	 */
  EClass getJAdressableFragment();

  /**
	 * Returns the meta object for the attribute '{@link org.sidiff.javaast.model.JAdressableFragment#getStartByte <em>Start Byte</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Start Byte</em>'.
	 * @see org.sidiff.javaast.model.JAdressableFragment#getStartByte()
	 * @see #getJAdressableFragment()
	 * @generated
	 */
  EAttribute getJAdressableFragment_StartByte();

  /**
	 * Returns the meta object for the attribute '{@link org.sidiff.javaast.model.JAdressableFragment#getByteLength <em>Byte Length</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Byte Length</em>'.
	 * @see org.sidiff.javaast.model.JAdressableFragment#getByteLength()
	 * @see #getJAdressableFragment()
	 * @generated
	 */
  EAttribute getJAdressableFragment_ByteLength();

  /**
	 * Returns the meta object for class '{@link org.sidiff.javaast.model.JCodeFragment <em>JCode Fragment</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for class '<em>JCode Fragment</em>'.
	 * @see org.sidiff.javaast.model.JCodeFragment
	 * @generated
	 */
  EClass getJCodeFragment();

  /**
	 * Returns the meta object for the reference list '{@link org.sidiff.javaast.model.JCodeFragment#getUsedTypes <em>Used Types</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Used Types</em>'.
	 * @see org.sidiff.javaast.model.JCodeFragment#getUsedTypes()
	 * @see #getJCodeFragment()
	 * @generated
	 */
  EReference getJCodeFragment_UsedTypes();

  /**
	 * Returns the meta object for the reference list '{@link org.sidiff.javaast.model.JCodeFragment#getCalledMethods <em>Called Methods</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Called Methods</em>'.
	 * @see org.sidiff.javaast.model.JCodeFragment#getCalledMethods()
	 * @see #getJCodeFragment()
	 * @generated
	 */
  EReference getJCodeFragment_CalledMethods();

  /**
	 * Returns the meta object for the attribute '{@link org.sidiff.javaast.model.JCodeFragment#getCode <em>Code</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Code</em>'.
	 * @see org.sidiff.javaast.model.JCodeFragment#getCode()
	 * @see #getJCodeFragment()
	 * @generated
	 */
  EAttribute getJCodeFragment_Code();

  /**
	 * Returns the meta object for the reference list '{@link org.sidiff.javaast.model.JCodeFragment#getUsedFields <em>Used Fields</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Used Fields</em>'.
	 * @see org.sidiff.javaast.model.JCodeFragment#getUsedFields()
	 * @see #getJCodeFragment()
	 * @generated
	 */
  EReference getJCodeFragment_UsedFields();

  /**
	 * Returns the meta object for the reference list '{@link org.sidiff.javaast.model.JCodeFragment#getUsedParameters <em>Used Parameters</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Used Parameters</em>'.
	 * @see org.sidiff.javaast.model.JCodeFragment#getUsedParameters()
	 * @see #getJCodeFragment()
	 * @generated
	 */
  EReference getJCodeFragment_UsedParameters();

  /**
	 * Returns the meta object for the reference list '{@link org.sidiff.javaast.model.JCodeFragment#getUsedLocalVariables <em>Used Local Variables</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Used Local Variables</em>'.
	 * @see org.sidiff.javaast.model.JCodeFragment#getUsedLocalVariables()
	 * @see #getJCodeFragment()
	 * @generated
	 */
  EReference getJCodeFragment_UsedLocalVariables();

  /**
	 * Returns the meta object for class '{@link org.sidiff.javaast.model.JLocalVariable <em>JLocal Variable</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for class '<em>JLocal Variable</em>'.
	 * @see org.sidiff.javaast.model.JLocalVariable
	 * @generated
	 */
  EClass getJLocalVariable();

  /**
	 * Returns the meta object for class '{@link org.sidiff.javaast.model.JInterface <em>JInterface</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for class '<em>JInterface</em>'.
	 * @see org.sidiff.javaast.model.JInterface
	 * @generated
	 */
  EClass getJInterface();

  /**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.javaast.model.JInterface#getConstants <em>Constants</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Constants</em>'.
	 * @see org.sidiff.javaast.model.JInterface#getConstants()
	 * @see #getJInterface()
	 * @generated
	 */
  EReference getJInterface_Constants();

  /**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.javaast.model.JInterface#getMethodSignatures <em>Method Signatures</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Method Signatures</em>'.
	 * @see org.sidiff.javaast.model.JInterface#getMethodSignatures()
	 * @see #getJInterface()
	 * @generated
	 */
  EReference getJInterface_MethodSignatures();

  /**
	 * Returns the meta object for the reference list '{@link org.sidiff.javaast.model.JInterface#getSuperInterfaces <em>Super Interfaces</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Super Interfaces</em>'.
	 * @see org.sidiff.javaast.model.JInterface#getSuperInterfaces()
	 * @see #getJInterface()
	 * @generated
	 */
  EReference getJInterface_SuperInterfaces();

  /**
	 * Returns the meta object for the reference list '{@link org.sidiff.javaast.model.JInterface#getSubInterfaces <em>Sub Interfaces</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Sub Interfaces</em>'.
	 * @see org.sidiff.javaast.model.JInterface#getSubInterfaces()
	 * @see #getJInterface()
	 * @generated
	 */
  EReference getJInterface_SubInterfaces();

  /**
	 * Returns the meta object for the reference list '{@link org.sidiff.javaast.model.JInterface#getImplementingClasses <em>Implementing Classes</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Implementing Classes</em>'.
	 * @see org.sidiff.javaast.model.JInterface#getImplementingClasses()
	 * @see #getJInterface()
	 * @generated
	 */
  EReference getJInterface_ImplementingClasses();

  /**
	 * Returns the meta object for class '{@link org.sidiff.javaast.model.JConstant <em>JConstant</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for class '<em>JConstant</em>'.
	 * @see org.sidiff.javaast.model.JConstant
	 * @generated
	 */
  EClass getJConstant();

  /**
	 * Returns the meta object for class '{@link org.sidiff.javaast.model.JArrayType <em>JArray Type</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for class '<em>JArray Type</em>'.
	 * @see org.sidiff.javaast.model.JArrayType
	 * @generated
	 */
  EClass getJArrayType();

  /**
	 * Returns the meta object for the attribute '{@link org.sidiff.javaast.model.JArrayType#getArrayDimensions <em>Array Dimensions</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Array Dimensions</em>'.
	 * @see org.sidiff.javaast.model.JArrayType#getArrayDimensions()
	 * @see #getJArrayType()
	 * @generated
	 */
  EAttribute getJArrayType_ArrayDimensions();

  /**
	 * Returns the meta object for class '{@link org.sidiff.javaast.model.JSimpleType <em>JSimple Type</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for class '<em>JSimple Type</em>'.
	 * @see org.sidiff.javaast.model.JSimpleType
	 * @generated
	 */
  EClass getJSimpleType();

  /**
	 * Returns the meta object for the attribute '{@link org.sidiff.javaast.model.JSimpleType#getKind <em>Kind</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Kind</em>'.
	 * @see org.sidiff.javaast.model.JSimpleType#getKind()
	 * @see #getJSimpleType()
	 * @generated
	 */
  EAttribute getJSimpleType_Kind();

  /**
	 * Returns the meta object for the attribute '{@link org.sidiff.javaast.model.JSimpleType#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.sidiff.javaast.model.JSimpleType#getName()
	 * @see #getJSimpleType()
	 * @generated
	 */
	EAttribute getJSimpleType_Name();

		/**
	 * Returns the meta object for class '{@link org.sidiff.javaast.model.JTemplateWrapper <em>JTemplate Wrapper</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for class '<em>JTemplate Wrapper</em>'.
	 * @see org.sidiff.javaast.model.JTemplateWrapper
	 * @generated
	 */
  EClass getJTemplateWrapper();

  /**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.javaast.model.JTemplateWrapper#getBindings <em>Bindings</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Bindings</em>'.
	 * @see org.sidiff.javaast.model.JTemplateWrapper#getBindings()
	 * @see #getJTemplateWrapper()
	 * @generated
	 */
  EReference getJTemplateWrapper_Bindings();

  /**
	 * Returns the meta object for class '{@link org.sidiff.javaast.model.JTemplateBinding <em>JTemplate Binding</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for class '<em>JTemplate Binding</em>'.
	 * @see org.sidiff.javaast.model.JTemplateBinding
	 * @generated
	 */
  EClass getJTemplateBinding();

  /**
	 * Returns the meta object for the reference '{@link org.sidiff.javaast.model.JTemplateBinding#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see org.sidiff.javaast.model.JTemplateBinding#getType()
	 * @see #getJTemplateBinding()
	 * @generated
	 */
  EReference getJTemplateBinding_Type();

  /**
	 * Returns the meta object for the reference '{@link org.sidiff.javaast.model.JTemplateBinding#getGenericType <em>Generic Type</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Generic Type</em>'.
	 * @see org.sidiff.javaast.model.JTemplateBinding#getGenericType()
	 * @see #getJTemplateBinding()
	 * @generated
	 */
  EReference getJTemplateBinding_GenericType();

  /**
	 * Returns the meta object for class '{@link org.sidiff.javaast.model.JStructuralCodeFragment <em>JStructural Code Fragment</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for class '<em>JStructural Code Fragment</em>'.
	 * @see org.sidiff.javaast.model.JStructuralCodeFragment
	 * @generated
	 */
  EClass getJStructuralCodeFragment();

  /**
	 * Returns the meta object for the attribute '{@link org.sidiff.javaast.model.JStructuralCodeFragment#getFragmentType <em>Fragment Type</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Fragment Type</em>'.
	 * @see org.sidiff.javaast.model.JStructuralCodeFragment#getFragmentType()
	 * @see #getJStructuralCodeFragment()
	 * @generated
	 */
  EAttribute getJStructuralCodeFragment_FragmentType();

  /**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.javaast.model.JStructuralCodeFragment#getCodeBlocks <em>Code Blocks</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Code Blocks</em>'.
	 * @see org.sidiff.javaast.model.JStructuralCodeFragment#getCodeBlocks()
	 * @see #getJStructuralCodeFragment()
	 * @generated
	 */
  EReference getJStructuralCodeFragment_CodeBlocks();

  /**
	 * Returns the meta object for class '{@link org.sidiff.javaast.model.JEnumeration <em>JEnumeration</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for class '<em>JEnumeration</em>'.
	 * @see org.sidiff.javaast.model.JEnumeration
	 * @generated
	 */
  EClass getJEnumeration();

  /**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.javaast.model.JEnumeration#getLiterals <em>Literals</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Literals</em>'.
	 * @see org.sidiff.javaast.model.JEnumeration#getLiterals()
	 * @see #getJEnumeration()
	 * @generated
	 */
  EReference getJEnumeration_Literals();

  /**
	 * Returns the meta object for class '{@link org.sidiff.javaast.model.JEnumerationLiteral <em>JEnumeration Literal</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for class '<em>JEnumeration Literal</em>'.
	 * @see org.sidiff.javaast.model.JEnumerationLiteral
	 * @generated
	 */
  EClass getJEnumerationLiteral();

  /**
	 * Returns the meta object for enum '{@link org.sidiff.javaast.model.JVisibilityKind <em>JVisibility Kind</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>JVisibility Kind</em>'.
	 * @see org.sidiff.javaast.model.JVisibilityKind
	 * @generated
	 */
  EEnum getJVisibilityKind();

  /**
	 * Returns the meta object for enum '{@link org.sidiff.javaast.model.JSimpleTypeKind <em>JSimple Type Kind</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>JSimple Type Kind</em>'.
	 * @see org.sidiff.javaast.model.JSimpleTypeKind
	 * @generated
	 */
  EEnum getJSimpleTypeKind();

  /**
	 * Returns the meta object for enum '{@link org.sidiff.javaast.model.StructuralFragmentType <em>Structural Fragment Type</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Structural Fragment Type</em>'.
	 * @see org.sidiff.javaast.model.StructuralFragmentType
	 * @generated
	 */
  EEnum getStructuralFragmentType();

  /**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
  JavaModelFactory getJavaModelFactory();

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
  interface Literals
  {
    /**
		 * The meta object literal for the '{@link org.sidiff.javaast.model.impl.JProjectImpl <em>JProject</em>}' class.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @see org.sidiff.javaast.model.impl.JProjectImpl
		 * @see org.sidiff.javaast.model.impl.JavaModelPackageImpl#getJProject()
		 * @generated
		 */
    EClass JPROJECT = eINSTANCE.getJProject();

    /**
		 * The meta object literal for the '<em><b>Packages</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EReference JPROJECT__PACKAGES = eINSTANCE.getJProject_Packages();

    /**
		 * The meta object literal for the '<em><b>Simple Types</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EReference JPROJECT__SIMPLE_TYPES = eINSTANCE.getJProject_SimpleTypes();

    /**
		 * The meta object literal for the '{@link org.sidiff.javaast.model.impl.JNamedElementImpl <em>JNamed Element</em>}' class.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @see org.sidiff.javaast.model.impl.JNamedElementImpl
		 * @see org.sidiff.javaast.model.impl.JavaModelPackageImpl#getJNamedElement()
		 * @generated
		 */
    EClass JNAMED_ELEMENT = eINSTANCE.getJNamedElement();

    /**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EAttribute JNAMED_ELEMENT__NAME = eINSTANCE.getJNamedElement_Name();

    /**
		 * The meta object literal for the '<em><b>Qualified Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute JNAMED_ELEMENT__QUALIFIED_NAME = eINSTANCE.getJNamedElement_QualifiedName();

				/**
		 * The meta object literal for the '{@link org.sidiff.javaast.model.impl.JIdentifiableElementImpl <em>JIdentifiable Element</em>}' class.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @see org.sidiff.javaast.model.impl.JIdentifiableElementImpl
		 * @see org.sidiff.javaast.model.impl.JavaModelPackageImpl#getJIdentifiableElement()
		 * @generated
		 */
    EClass JIDENTIFIABLE_ELEMENT = eINSTANCE.getJIdentifiableElement();

    /**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EAttribute JIDENTIFIABLE_ELEMENT__ID = eINSTANCE.getJIdentifiableElement_Id();

    /**
		 * The meta object literal for the '<em><b>Compileable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EAttribute JIDENTIFIABLE_ELEMENT__COMPILEABLE = eINSTANCE.getJIdentifiableElement_Compileable();

    /**
		 * The meta object literal for the '{@link org.sidiff.javaast.model.impl.JPackageImpl <em>JPackage</em>}' class.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @see org.sidiff.javaast.model.impl.JPackageImpl
		 * @see org.sidiff.javaast.model.impl.JavaModelPackageImpl#getJPackage()
		 * @generated
		 */
    EClass JPACKAGE = eINSTANCE.getJPackage();

    /**
		 * The meta object literal for the '<em><b>Sub Packages</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EReference JPACKAGE__SUB_PACKAGES = eINSTANCE.getJPackage_SubPackages();

    /**
		 * The meta object literal for the '<em><b>Classes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EReference JPACKAGE__CLASSES = eINSTANCE.getJPackage_Classes();

    /**
		 * The meta object literal for the '<em><b>Interfaces</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EReference JPACKAGE__INTERFACES = eINSTANCE.getJPackage_Interfaces();

    /**
		 * The meta object literal for the '{@link org.sidiff.javaast.model.impl.JClassImpl <em>JClass</em>}' class.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @see org.sidiff.javaast.model.impl.JClassImpl
		 * @see org.sidiff.javaast.model.impl.JavaModelPackageImpl#getJClass()
		 * @generated
		 */
    EClass JCLASS = eINSTANCE.getJClass();

    /**
		 * The meta object literal for the '<em><b>Fields</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EReference JCLASS__FIELDS = eINSTANCE.getJClass_Fields();

    /**
		 * The meta object literal for the '<em><b>Is Abstract</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EAttribute JCLASS__IS_ABSTRACT = eINSTANCE.getJClass_IsAbstract();

    /**
		 * The meta object literal for the '<em><b>Is Final</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EAttribute JCLASS__IS_FINAL = eINSTANCE.getJClass_IsFinal();

    /**
		 * The meta object literal for the '<em><b>Methods</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EReference JCLASS__METHODS = eINSTANCE.getJClass_Methods();

    /**
		 * The meta object literal for the '<em><b>Super Class</b></em>' reference feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EReference JCLASS__SUPER_CLASS = eINSTANCE.getJClass_SuperClass();

    /**
		 * The meta object literal for the '<em><b>Sub Classes</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EReference JCLASS__SUB_CLASSES = eINSTANCE.getJClass_SubClasses();

    /**
		 * The meta object literal for the '<em><b>Implemented Interfaces</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EReference JCLASS__IMPLEMENTED_INTERFACES = eINSTANCE.getJClass_ImplementedInterfaces();

    /**
		 * The meta object literal for the '<em><b>Static Initialization Block</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EReference JCLASS__STATIC_INITIALIZATION_BLOCK = eINSTANCE.getJClass_StaticInitializationBlock();

    /**
		 * The meta object literal for the '{@link org.sidiff.javaast.model.impl.JVisibleElementImpl <em>JVisible Element</em>}' class.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @see org.sidiff.javaast.model.impl.JVisibleElementImpl
		 * @see org.sidiff.javaast.model.impl.JavaModelPackageImpl#getJVisibleElement()
		 * @generated
		 */
    EClass JVISIBLE_ELEMENT = eINSTANCE.getJVisibleElement();

    /**
		 * The meta object literal for the '<em><b>Visibility Kind</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EAttribute JVISIBLE_ELEMENT__VISIBILITY_KIND = eINSTANCE.getJVisibleElement_VisibilityKind();

    /**
		 * The meta object literal for the '{@link org.sidiff.javaast.model.impl.JTypeImpl <em>JType</em>}' class.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @see org.sidiff.javaast.model.impl.JTypeImpl
		 * @see org.sidiff.javaast.model.impl.JavaModelPackageImpl#getJType()
		 * @generated
		 */
    EClass JTYPE = eINSTANCE.getJType();

    /**
		 * The meta object literal for the '<em><b>Is External</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EAttribute JTYPE__IS_EXTERNAL = eINSTANCE.getJType_IsExternal();

    /**
		 * The meta object literal for the '{@link org.sidiff.javaast.model.impl.JTemplateImpl <em>JTemplate</em>}' class.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @see org.sidiff.javaast.model.impl.JTemplateImpl
		 * @see org.sidiff.javaast.model.impl.JavaModelPackageImpl#getJTemplate()
		 * @generated
		 */
    EClass JTEMPLATE = eINSTANCE.getJTemplate();

    /**
		 * The meta object literal for the '<em><b>Generic Types</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EReference JTEMPLATE__GENERIC_TYPES = eINSTANCE.getJTemplate_GenericTypes();

    /**
		 * The meta object literal for the '{@link org.sidiff.javaast.model.impl.JGenericTypeImpl <em>JGeneric Type</em>}' class.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @see org.sidiff.javaast.model.impl.JGenericTypeImpl
		 * @see org.sidiff.javaast.model.impl.JavaModelPackageImpl#getJGenericType()
		 * @generated
		 */
    EClass JGENERIC_TYPE = eINSTANCE.getJGenericType();

    /**
		 * The meta object literal for the '{@link org.sidiff.javaast.model.impl.JDocumentableElementImpl <em>JDocumentable Element</em>}' class.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @see org.sidiff.javaast.model.impl.JDocumentableElementImpl
		 * @see org.sidiff.javaast.model.impl.JavaModelPackageImpl#getJDocumentableElement()
		 * @generated
		 */
    EClass JDOCUMENTABLE_ELEMENT = eINSTANCE.getJDocumentableElement();

    /**
		 * The meta object literal for the '<em><b>Java Doc</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EReference JDOCUMENTABLE_ELEMENT__JAVA_DOC = eINSTANCE.getJDocumentableElement_JavaDoc();

    /**
		 * The meta object literal for the '{@link org.sidiff.javaast.model.impl.JavaDocImpl <em>Java Doc</em>}' class.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @see org.sidiff.javaast.model.impl.JavaDocImpl
		 * @see org.sidiff.javaast.model.impl.JavaModelPackageImpl#getJavaDoc()
		 * @generated
		 */
    EClass JAVA_DOC = eINSTANCE.getJavaDoc();

    /**
		 * The meta object literal for the '<em><b>Text</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EAttribute JAVA_DOC__TEXT = eINSTANCE.getJavaDoc_Text();

    /**
		 * The meta object literal for the '{@link org.sidiff.javaast.model.impl.JClassifierImpl <em>JClassifier</em>}' class.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @see org.sidiff.javaast.model.impl.JClassifierImpl
		 * @see org.sidiff.javaast.model.impl.JavaModelPackageImpl#getJClassifier()
		 * @generated
		 */
    EClass JCLASSIFIER = eINSTANCE.getJClassifier();

    /**
		 * The meta object literal for the '<em><b>Inner Classifiers</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EReference JCLASSIFIER__INNER_CLASSIFIERS = eINSTANCE.getJClassifier_InnerClassifiers();

    /**
		 * The meta object literal for the '{@link org.sidiff.javaast.model.impl.JFieldImpl <em>JField</em>}' class.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @see org.sidiff.javaast.model.impl.JFieldImpl
		 * @see org.sidiff.javaast.model.impl.JavaModelPackageImpl#getJField()
		 * @generated
		 */
    EClass JFIELD = eINSTANCE.getJField();

    /**
		 * The meta object literal for the '<em><b>Is Final</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EAttribute JFIELD__IS_FINAL = eINSTANCE.getJField_IsFinal();

    /**
		 * The meta object literal for the '<em><b>Is Static</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EAttribute JFIELD__IS_STATIC = eINSTANCE.getJField_IsStatic();

    /**
		 * The meta object literal for the '<em><b>Accessed By</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EReference JFIELD__ACCESSED_BY = eINSTANCE.getJField_AccessedBy();

    /**
		 * The meta object literal for the '<em><b>Is Transient</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EAttribute JFIELD__IS_TRANSIENT = eINSTANCE.getJField_IsTransient();

    /**
		 * The meta object literal for the '<em><b>Initialization</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EReference JFIELD__INITIALIZATION = eINSTANCE.getJField_Initialization();

    /**
		 * The meta object literal for the '{@link org.sidiff.javaast.model.impl.JTypedElementImpl <em>JTyped Element</em>}' class.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @see org.sidiff.javaast.model.impl.JTypedElementImpl
		 * @see org.sidiff.javaast.model.impl.JavaModelPackageImpl#getJTypedElement()
		 * @generated
		 */
    EClass JTYPED_ELEMENT = eINSTANCE.getJTypedElement();

    /**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EReference JTYPED_ELEMENT__TYPE = eINSTANCE.getJTypedElement_Type();

    /**
		 * The meta object literal for the '{@link org.sidiff.javaast.model.impl.JMethodImpl <em>JMethod</em>}' class.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @see org.sidiff.javaast.model.impl.JMethodImpl
		 * @see org.sidiff.javaast.model.impl.JavaModelPackageImpl#getJMethod()
		 * @generated
		 */
    EClass JMETHOD = eINSTANCE.getJMethod();

    /**
		 * The meta object literal for the '<em><b>Is Abstract</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EAttribute JMETHOD__IS_ABSTRACT = eINSTANCE.getJMethod_IsAbstract();

    /**
		 * The meta object literal for the '<em><b>Is Final</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EAttribute JMETHOD__IS_FINAL = eINSTANCE.getJMethod_IsFinal();

    /**
		 * The meta object literal for the '<em><b>Is Static</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EAttribute JMETHOD__IS_STATIC = eINSTANCE.getJMethod_IsStatic();

    /**
		 * The meta object literal for the '<em><b>Is Constructor</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EAttribute JMETHOD__IS_CONSTRUCTOR = eINSTANCE.getJMethod_IsConstructor();

    /**
		 * The meta object literal for the '<em><b>Is Synchronized</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EAttribute JMETHOD__IS_SYNCHRONIZED = eINSTANCE.getJMethod_IsSynchronized();

    /**
		 * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EReference JMETHOD__PARAMETERS = eINSTANCE.getJMethod_Parameters();

    /**
		 * The meta object literal for the '<em><b>Calls</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EReference JMETHOD__CALLS = eINSTANCE.getJMethod_Calls();

    /**
		 * The meta object literal for the '<em><b>Called By</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EReference JMETHOD__CALLED_BY = eINSTANCE.getJMethod_CalledBy();

    /**
		 * The meta object literal for the '<em><b>Raised Exception</b></em>' reference feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EReference JMETHOD__RAISED_EXCEPTION = eINSTANCE.getJMethod_RaisedException();

    /**
		 * The meta object literal for the '<em><b>Body</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EReference JMETHOD__BODY = eINSTANCE.getJMethod_Body();

    /**
		 * The meta object literal for the '<em><b>Accesses</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EReference JMETHOD__ACCESSES = eINSTANCE.getJMethod_Accesses();

    /**
		 * The meta object literal for the '{@link org.sidiff.javaast.model.impl.JParameterImpl <em>JParameter</em>}' class.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @see org.sidiff.javaast.model.impl.JParameterImpl
		 * @see org.sidiff.javaast.model.impl.JavaModelPackageImpl#getJParameter()
		 * @generated
		 */
    EClass JPARAMETER = eINSTANCE.getJParameter();

    /**
		 * The meta object literal for the '<em><b>Is Final</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EAttribute JPARAMETER__IS_FINAL = eINSTANCE.getJParameter_IsFinal();

    /**
		 * The meta object literal for the '{@link org.sidiff.javaast.model.impl.JCodeBlockImpl <em>JCode Block</em>}' class.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @see org.sidiff.javaast.model.impl.JCodeBlockImpl
		 * @see org.sidiff.javaast.model.impl.JavaModelPackageImpl#getJCodeBlock()
		 * @generated
		 */
    EClass JCODE_BLOCK = eINSTANCE.getJCodeBlock();

    /**
		 * The meta object literal for the '<em><b>Code Fragments</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EReference JCODE_BLOCK__CODE_FRAGMENTS = eINSTANCE.getJCodeBlock_CodeFragments();

    /**
		 * The meta object literal for the '<em><b>Sub Blocks</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EReference JCODE_BLOCK__SUB_BLOCKS = eINSTANCE.getJCodeBlock_SubBlocks();

    /**
		 * The meta object literal for the '<em><b>Defined Variables</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EReference JCODE_BLOCK__DEFINED_VARIABLES = eINSTANCE.getJCodeBlock_DefinedVariables();

    /**
		 * The meta object literal for the '{@link org.sidiff.javaast.model.impl.JAdressableFragmentImpl <em>JAdressable Fragment</em>}' class.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @see org.sidiff.javaast.model.impl.JAdressableFragmentImpl
		 * @see org.sidiff.javaast.model.impl.JavaModelPackageImpl#getJAdressableFragment()
		 * @generated
		 */
    EClass JADRESSABLE_FRAGMENT = eINSTANCE.getJAdressableFragment();

    /**
		 * The meta object literal for the '<em><b>Start Byte</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EAttribute JADRESSABLE_FRAGMENT__START_BYTE = eINSTANCE.getJAdressableFragment_StartByte();

    /**
		 * The meta object literal for the '<em><b>Byte Length</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EAttribute JADRESSABLE_FRAGMENT__BYTE_LENGTH = eINSTANCE.getJAdressableFragment_ByteLength();

    /**
		 * The meta object literal for the '{@link org.sidiff.javaast.model.impl.JCodeFragmentImpl <em>JCode Fragment</em>}' class.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @see org.sidiff.javaast.model.impl.JCodeFragmentImpl
		 * @see org.sidiff.javaast.model.impl.JavaModelPackageImpl#getJCodeFragment()
		 * @generated
		 */
    EClass JCODE_FRAGMENT = eINSTANCE.getJCodeFragment();

    /**
		 * The meta object literal for the '<em><b>Used Types</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EReference JCODE_FRAGMENT__USED_TYPES = eINSTANCE.getJCodeFragment_UsedTypes();

    /**
		 * The meta object literal for the '<em><b>Called Methods</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EReference JCODE_FRAGMENT__CALLED_METHODS = eINSTANCE.getJCodeFragment_CalledMethods();

    /**
		 * The meta object literal for the '<em><b>Code</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EAttribute JCODE_FRAGMENT__CODE = eINSTANCE.getJCodeFragment_Code();

    /**
		 * The meta object literal for the '<em><b>Used Fields</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EReference JCODE_FRAGMENT__USED_FIELDS = eINSTANCE.getJCodeFragment_UsedFields();

    /**
		 * The meta object literal for the '<em><b>Used Parameters</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EReference JCODE_FRAGMENT__USED_PARAMETERS = eINSTANCE.getJCodeFragment_UsedParameters();

    /**
		 * The meta object literal for the '<em><b>Used Local Variables</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EReference JCODE_FRAGMENT__USED_LOCAL_VARIABLES = eINSTANCE.getJCodeFragment_UsedLocalVariables();

    /**
		 * The meta object literal for the '{@link org.sidiff.javaast.model.impl.JLocalVariableImpl <em>JLocal Variable</em>}' class.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @see org.sidiff.javaast.model.impl.JLocalVariableImpl
		 * @see org.sidiff.javaast.model.impl.JavaModelPackageImpl#getJLocalVariable()
		 * @generated
		 */
    EClass JLOCAL_VARIABLE = eINSTANCE.getJLocalVariable();

    /**
		 * The meta object literal for the '{@link org.sidiff.javaast.model.impl.JInterfaceImpl <em>JInterface</em>}' class.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @see org.sidiff.javaast.model.impl.JInterfaceImpl
		 * @see org.sidiff.javaast.model.impl.JavaModelPackageImpl#getJInterface()
		 * @generated
		 */
    EClass JINTERFACE = eINSTANCE.getJInterface();

    /**
		 * The meta object literal for the '<em><b>Constants</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EReference JINTERFACE__CONSTANTS = eINSTANCE.getJInterface_Constants();

    /**
		 * The meta object literal for the '<em><b>Method Signatures</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EReference JINTERFACE__METHOD_SIGNATURES = eINSTANCE.getJInterface_MethodSignatures();

    /**
		 * The meta object literal for the '<em><b>Super Interfaces</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EReference JINTERFACE__SUPER_INTERFACES = eINSTANCE.getJInterface_SuperInterfaces();

    /**
		 * The meta object literal for the '<em><b>Sub Interfaces</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EReference JINTERFACE__SUB_INTERFACES = eINSTANCE.getJInterface_SubInterfaces();

    /**
		 * The meta object literal for the '<em><b>Implementing Classes</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EReference JINTERFACE__IMPLEMENTING_CLASSES = eINSTANCE.getJInterface_ImplementingClasses();

    /**
		 * The meta object literal for the '{@link org.sidiff.javaast.model.impl.JConstantImpl <em>JConstant</em>}' class.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @see org.sidiff.javaast.model.impl.JConstantImpl
		 * @see org.sidiff.javaast.model.impl.JavaModelPackageImpl#getJConstant()
		 * @generated
		 */
    EClass JCONSTANT = eINSTANCE.getJConstant();

    /**
		 * The meta object literal for the '{@link org.sidiff.javaast.model.impl.JArrayTypeImpl <em>JArray Type</em>}' class.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @see org.sidiff.javaast.model.impl.JArrayTypeImpl
		 * @see org.sidiff.javaast.model.impl.JavaModelPackageImpl#getJArrayType()
		 * @generated
		 */
    EClass JARRAY_TYPE = eINSTANCE.getJArrayType();

    /**
		 * The meta object literal for the '<em><b>Array Dimensions</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EAttribute JARRAY_TYPE__ARRAY_DIMENSIONS = eINSTANCE.getJArrayType_ArrayDimensions();

    /**
		 * The meta object literal for the '{@link org.sidiff.javaast.model.impl.JSimpleTypeImpl <em>JSimple Type</em>}' class.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @see org.sidiff.javaast.model.impl.JSimpleTypeImpl
		 * @see org.sidiff.javaast.model.impl.JavaModelPackageImpl#getJSimpleType()
		 * @generated
		 */
    EClass JSIMPLE_TYPE = eINSTANCE.getJSimpleType();

    /**
		 * The meta object literal for the '<em><b>Kind</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EAttribute JSIMPLE_TYPE__KIND = eINSTANCE.getJSimpleType_Kind();

    /**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute JSIMPLE_TYPE__NAME = eINSTANCE.getJSimpleType_Name();

				/**
		 * The meta object literal for the '{@link org.sidiff.javaast.model.impl.JTemplateWrapperImpl <em>JTemplate Wrapper</em>}' class.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @see org.sidiff.javaast.model.impl.JTemplateWrapperImpl
		 * @see org.sidiff.javaast.model.impl.JavaModelPackageImpl#getJTemplateWrapper()
		 * @generated
		 */
    EClass JTEMPLATE_WRAPPER = eINSTANCE.getJTemplateWrapper();

    /**
		 * The meta object literal for the '<em><b>Bindings</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EReference JTEMPLATE_WRAPPER__BINDINGS = eINSTANCE.getJTemplateWrapper_Bindings();

    /**
		 * The meta object literal for the '{@link org.sidiff.javaast.model.impl.JTemplateBindingImpl <em>JTemplate Binding</em>}' class.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @see org.sidiff.javaast.model.impl.JTemplateBindingImpl
		 * @see org.sidiff.javaast.model.impl.JavaModelPackageImpl#getJTemplateBinding()
		 * @generated
		 */
    EClass JTEMPLATE_BINDING = eINSTANCE.getJTemplateBinding();

    /**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EReference JTEMPLATE_BINDING__TYPE = eINSTANCE.getJTemplateBinding_Type();

    /**
		 * The meta object literal for the '<em><b>Generic Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EReference JTEMPLATE_BINDING__GENERIC_TYPE = eINSTANCE.getJTemplateBinding_GenericType();

    /**
		 * The meta object literal for the '{@link org.sidiff.javaast.model.impl.JStructuralCodeFragmentImpl <em>JStructural Code Fragment</em>}' class.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @see org.sidiff.javaast.model.impl.JStructuralCodeFragmentImpl
		 * @see org.sidiff.javaast.model.impl.JavaModelPackageImpl#getJStructuralCodeFragment()
		 * @generated
		 */
    EClass JSTRUCTURAL_CODE_FRAGMENT = eINSTANCE.getJStructuralCodeFragment();

    /**
		 * The meta object literal for the '<em><b>Fragment Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EAttribute JSTRUCTURAL_CODE_FRAGMENT__FRAGMENT_TYPE = eINSTANCE.getJStructuralCodeFragment_FragmentType();

    /**
		 * The meta object literal for the '<em><b>Code Blocks</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EReference JSTRUCTURAL_CODE_FRAGMENT__CODE_BLOCKS = eINSTANCE.getJStructuralCodeFragment_CodeBlocks();

    /**
		 * The meta object literal for the '{@link org.sidiff.javaast.model.impl.JEnumerationImpl <em>JEnumeration</em>}' class.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @see org.sidiff.javaast.model.impl.JEnumerationImpl
		 * @see org.sidiff.javaast.model.impl.JavaModelPackageImpl#getJEnumeration()
		 * @generated
		 */
    EClass JENUMERATION = eINSTANCE.getJEnumeration();

    /**
		 * The meta object literal for the '<em><b>Literals</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EReference JENUMERATION__LITERALS = eINSTANCE.getJEnumeration_Literals();

    /**
		 * The meta object literal for the '{@link org.sidiff.javaast.model.impl.JEnumerationLiteralImpl <em>JEnumeration Literal</em>}' class.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @see org.sidiff.javaast.model.impl.JEnumerationLiteralImpl
		 * @see org.sidiff.javaast.model.impl.JavaModelPackageImpl#getJEnumerationLiteral()
		 * @generated
		 */
    EClass JENUMERATION_LITERAL = eINSTANCE.getJEnumerationLiteral();

    /**
		 * The meta object literal for the '{@link org.sidiff.javaast.model.JVisibilityKind <em>JVisibility Kind</em>}' enum.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @see org.sidiff.javaast.model.JVisibilityKind
		 * @see org.sidiff.javaast.model.impl.JavaModelPackageImpl#getJVisibilityKind()
		 * @generated
		 */
    EEnum JVISIBILITY_KIND = eINSTANCE.getJVisibilityKind();

    /**
		 * The meta object literal for the '{@link org.sidiff.javaast.model.JSimpleTypeKind <em>JSimple Type Kind</em>}' enum.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @see org.sidiff.javaast.model.JSimpleTypeKind
		 * @see org.sidiff.javaast.model.impl.JavaModelPackageImpl#getJSimpleTypeKind()
		 * @generated
		 */
    EEnum JSIMPLE_TYPE_KIND = eINSTANCE.getJSimpleTypeKind();

    /**
		 * The meta object literal for the '{@link org.sidiff.javaast.model.StructuralFragmentType <em>Structural Fragment Type</em>}' enum.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @see org.sidiff.javaast.model.StructuralFragmentType
		 * @see org.sidiff.javaast.model.impl.JavaModelPackageImpl#getStructuralFragmentType()
		 * @generated
		 */
    EEnum STRUCTURAL_FRAGMENT_TYPE = eINSTANCE.getStructuralFragmentType();

  }

} //JavaModelPackage
