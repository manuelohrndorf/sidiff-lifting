/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package differencemodel;

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
 * @see differencemodel.DifferencemodelFactory
 * @model kind="package"
 * @generated
 */
public interface DifferencemodelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "differencemodel";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://differencemodel/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "differencemodel";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	DifferencemodelPackage eINSTANCE = differencemodel.impl.DifferencemodelPackageImpl.init();

	/**
	 * The meta object id for the '{@link differencemodel.impl.DifferenceImpl <em>Difference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see differencemodel.impl.DifferenceImpl
	 * @see differencemodel.impl.DifferencemodelPackageImpl#getDifference()
	 * @generated
	 */
	int DIFFERENCE = 0;

	/**
	 * The feature id for the '<em><b>Changes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIFFERENCE__CHANGES = 0;

	/**
	 * The feature id for the '<em><b>Change Sets</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIFFERENCE__CHANGE_SETS = 1;

	/**
	 * The feature id for the '<em><b>Correspondences</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIFFERENCE__CORRESPONDENCES = 2;

	/**
	 * The feature id for the '<em><b>Model A</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIFFERENCE__MODEL_A = 3;

	/**
	 * The feature id for the '<em><b>Model B</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIFFERENCE__MODEL_B = 4;

	/**
	 * The feature id for the '<em><b>Dependencies</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIFFERENCE__DEPENDENCIES = 5;

	/**
	 * The number of structural features of the '<em>Difference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIFFERENCE_FEATURE_COUNT = 6;

	/**
	 * The meta object id for the '{@link differencemodel.impl.ChangeImpl <em>Change</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see differencemodel.impl.ChangeImpl
	 * @see differencemodel.impl.DifferencemodelPackageImpl#getChange()
	 * @generated
	 */
	int CHANGE = 5;

	/**
	 * The number of structural features of the '<em>Change</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link differencemodel.impl.AddObjectImpl <em>Add Object</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see differencemodel.impl.AddObjectImpl
	 * @see differencemodel.impl.DifferencemodelPackageImpl#getAddObject()
	 * @generated
	 */
	int ADD_OBJECT = 1;

	/**
	 * The feature id for the '<em><b>Obj</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADD_OBJECT__OBJ = CHANGE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Add Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADD_OBJECT_FEATURE_COUNT = CHANGE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link differencemodel.impl.RemoveObjectImpl <em>Remove Object</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see differencemodel.impl.RemoveObjectImpl
	 * @see differencemodel.impl.DifferencemodelPackageImpl#getRemoveObject()
	 * @generated
	 */
	int REMOVE_OBJECT = 2;

	/**
	 * The feature id for the '<em><b>Obj</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_OBJECT__OBJ = CHANGE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Remove Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_OBJECT_FEATURE_COUNT = CHANGE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link differencemodel.impl.AddReferenceImpl <em>Add Reference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see differencemodel.impl.AddReferenceImpl
	 * @see differencemodel.impl.DifferencemodelPackageImpl#getAddReference()
	 * @generated
	 */
	int ADD_REFERENCE = 3;

	/**
	 * The feature id for the '<em><b>Src</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADD_REFERENCE__SRC = CHANGE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Tgt</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADD_REFERENCE__TGT = CHANGE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADD_REFERENCE__TYPE = CHANGE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Add Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADD_REFERENCE_FEATURE_COUNT = CHANGE_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link differencemodel.impl.RemoveReferenceImpl <em>Remove Reference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see differencemodel.impl.RemoveReferenceImpl
	 * @see differencemodel.impl.DifferencemodelPackageImpl#getRemoveReference()
	 * @generated
	 */
	int REMOVE_REFERENCE = 4;

	/**
	 * The feature id for the '<em><b>Src</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_REFERENCE__SRC = CHANGE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Tgt</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_REFERENCE__TGT = CHANGE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_REFERENCE__TYPE = CHANGE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Remove Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_REFERENCE_FEATURE_COUNT = CHANGE_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link differencemodel.impl.SemanticChangeSetImpl <em>Semantic Change Set</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see differencemodel.impl.SemanticChangeSetImpl
	 * @see differencemodel.impl.DifferencemodelPackageImpl#getSemanticChangeSet()
	 * @generated
	 */
	int SEMANTIC_CHANGE_SET = 6;

	/**
	 * The feature id for the '<em><b>Changes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEMANTIC_CHANGE_SET__CHANGES = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEMANTIC_CHANGE_SET__NAME = 1;

	/**
	 * The feature id for the '<em><b>Priority</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEMANTIC_CHANGE_SET__PRIORITY = 2;

	/**
	 * The feature id for the '<em><b>Refinement Level</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEMANTIC_CHANGE_SET__REFINEMENT_LEVEL = 3;

	/**
	 * The feature id for the '<em><b>Edit RName</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEMANTIC_CHANGE_SET__EDIT_RNAME = 4;

	/**
	 * The feature id for the '<em><b>Parameter Substitutions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEMANTIC_CHANGE_SET__PARAMETER_SUBSTITUTIONS = 5;

	/**
	 * The feature id for the '<em><b>Recognition RName</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEMANTIC_CHANGE_SET__RECOGNITION_RNAME = 6;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEMANTIC_CHANGE_SET__OUTGOING = 7;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEMANTIC_CHANGE_SET__INCOMING = 8;

	/**
	 * The number of structural features of the '<em>Semantic Change Set</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEMANTIC_CHANGE_SET_FEATURE_COUNT = 9;

	/**
	 * The meta object id for the '{@link differencemodel.impl.CorrespondenceImpl <em>Correspondence</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see differencemodel.impl.CorrespondenceImpl
	 * @see differencemodel.impl.DifferencemodelPackageImpl#getCorrespondence()
	 * @generated
	 */
	int CORRESPONDENCE = 7;

	/**
	 * The feature id for the '<em><b>Obj A</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORRESPONDENCE__OBJ_A = 0;

	/**
	 * The feature id for the '<em><b>Obj B</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORRESPONDENCE__OBJ_B = 1;

	/**
	 * The number of structural features of the '<em>Correspondence</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORRESPONDENCE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link differencemodel.impl.AttributeValueChangeImpl <em>Attribute Value Change</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see differencemodel.impl.AttributeValueChangeImpl
	 * @see differencemodel.impl.DifferencemodelPackageImpl#getAttributeValueChange()
	 * @generated
	 */
	int ATTRIBUTE_VALUE_CHANGE = 8;

	/**
	 * The feature id for the '<em><b>Obj A</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_VALUE_CHANGE__OBJ_A = CHANGE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Obj B</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_VALUE_CHANGE__OBJ_B = CHANGE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_VALUE_CHANGE__TYPE = CHANGE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Attribute Value Change</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_VALUE_CHANGE_FEATURE_COUNT = CHANGE_FEATURE_COUNT + 3;


	/**
	 * The meta object id for the '{@link differencemodel.impl.ParameterSubstitutionImpl <em>Parameter Substitution</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see differencemodel.impl.ParameterSubstitutionImpl
	 * @see differencemodel.impl.DifferencemodelPackageImpl#getParameterSubstitution()
	 * @generated
	 */
	int PARAMETER_SUBSTITUTION = 9;

	/**
	 * The feature id for the '<em><b>Formal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_SUBSTITUTION__FORMAL = 0;

	/**
	 * The number of structural features of the '<em>Parameter Substitution</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_SUBSTITUTION_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link differencemodel.impl.ObjectParameterSubstitutionImpl <em>Object Parameter Substitution</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see differencemodel.impl.ObjectParameterSubstitutionImpl
	 * @see differencemodel.impl.DifferencemodelPackageImpl#getObjectParameterSubstitution()
	 * @generated
	 */
	int OBJECT_PARAMETER_SUBSTITUTION = 10;

	/**
	 * The feature id for the '<em><b>Formal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_PARAMETER_SUBSTITUTION__FORMAL = PARAMETER_SUBSTITUTION__FORMAL;

	/**
	 * The feature id for the '<em><b>Actual A</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_PARAMETER_SUBSTITUTION__ACTUAL_A = PARAMETER_SUBSTITUTION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Actual B</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_PARAMETER_SUBSTITUTION__ACTUAL_B = PARAMETER_SUBSTITUTION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Object Parameter Substitution</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_PARAMETER_SUBSTITUTION_FEATURE_COUNT = PARAMETER_SUBSTITUTION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link differencemodel.impl.ValueParameterSubstitutionImpl <em>Value Parameter Substitution</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see differencemodel.impl.ValueParameterSubstitutionImpl
	 * @see differencemodel.impl.DifferencemodelPackageImpl#getValueParameterSubstitution()
	 * @generated
	 */
	int VALUE_PARAMETER_SUBSTITUTION = 11;

	/**
	 * The feature id for the '<em><b>Formal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_PARAMETER_SUBSTITUTION__FORMAL = PARAMETER_SUBSTITUTION__FORMAL;

	/**
	 * The feature id for the '<em><b>Actual</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_PARAMETER_SUBSTITUTION__ACTUAL = PARAMETER_SUBSTITUTION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Value Parameter Substitution</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_PARAMETER_SUBSTITUTION_FEATURE_COUNT = PARAMETER_SUBSTITUTION_FEATURE_COUNT + 1;


	/**
	 * The meta object id for the '{@link differencemodel.impl.DependencyImpl <em>Dependency</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see differencemodel.impl.DependencyImpl
	 * @see differencemodel.impl.DifferencemodelPackageImpl#getDependency()
	 * @generated
	 */
	int DEPENDENCY = 12;

	/**
	 * The feature id for the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPENDENCY__KIND = 0;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPENDENCY__SOURCE = 1;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPENDENCY__TARGET = 2;

	/**
	 * The number of structural features of the '<em>Dependency</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPENDENCY_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link differencemodel.DependencyKind <em>Dependency Kind</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see differencemodel.DependencyKind
	 * @see differencemodel.impl.DifferencemodelPackageImpl#getDependencyKind()
	 * @generated
	 */
	int DEPENDENCY_KIND = 13;


	/**
	 * Returns the meta object for class '{@link differencemodel.Difference <em>Difference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Difference</em>'.
	 * @see differencemodel.Difference
	 * @generated
	 */
	EClass getDifference();

	/**
	 * Returns the meta object for the containment reference list '{@link differencemodel.Difference#getChanges <em>Changes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Changes</em>'.
	 * @see differencemodel.Difference#getChanges()
	 * @see #getDifference()
	 * @generated
	 */
	EReference getDifference_Changes();

	/**
	 * Returns the meta object for the containment reference list '{@link differencemodel.Difference#getChangeSets <em>Change Sets</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Change Sets</em>'.
	 * @see differencemodel.Difference#getChangeSets()
	 * @see #getDifference()
	 * @generated
	 */
	EReference getDifference_ChangeSets();

	/**
	 * Returns the meta object for the containment reference list '{@link differencemodel.Difference#getCorrespondences <em>Correspondences</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Correspondences</em>'.
	 * @see differencemodel.Difference#getCorrespondences()
	 * @see #getDifference()
	 * @generated
	 */
	EReference getDifference_Correspondences();

	/**
	 * Returns the meta object for the attribute '{@link differencemodel.Difference#getModelA <em>Model A</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Model A</em>'.
	 * @see differencemodel.Difference#getModelA()
	 * @see #getDifference()
	 * @generated
	 */
	EAttribute getDifference_ModelA();

	/**
	 * Returns the meta object for the attribute '{@link differencemodel.Difference#getModelB <em>Model B</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Model B</em>'.
	 * @see differencemodel.Difference#getModelB()
	 * @see #getDifference()
	 * @generated
	 */
	EAttribute getDifference_ModelB();

	/**
	 * Returns the meta object for the containment reference list '{@link differencemodel.Difference#getDependencies <em>Dependencies</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Dependencies</em>'.
	 * @see differencemodel.Difference#getDependencies()
	 * @see #getDifference()
	 * @generated
	 */
	EReference getDifference_Dependencies();

	/**
	 * Returns the meta object for class '{@link differencemodel.AddObject <em>Add Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Add Object</em>'.
	 * @see differencemodel.AddObject
	 * @generated
	 */
	EClass getAddObject();

	/**
	 * Returns the meta object for the reference '{@link differencemodel.AddObject#getObj <em>Obj</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Obj</em>'.
	 * @see differencemodel.AddObject#getObj()
	 * @see #getAddObject()
	 * @generated
	 */
	EReference getAddObject_Obj();

	/**
	 * Returns the meta object for class '{@link differencemodel.RemoveObject <em>Remove Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Remove Object</em>'.
	 * @see differencemodel.RemoveObject
	 * @generated
	 */
	EClass getRemoveObject();

	/**
	 * Returns the meta object for the reference '{@link differencemodel.RemoveObject#getObj <em>Obj</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Obj</em>'.
	 * @see differencemodel.RemoveObject#getObj()
	 * @see #getRemoveObject()
	 * @generated
	 */
	EReference getRemoveObject_Obj();

	/**
	 * Returns the meta object for class '{@link differencemodel.AddReference <em>Add Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Add Reference</em>'.
	 * @see differencemodel.AddReference
	 * @generated
	 */
	EClass getAddReference();

	/**
	 * Returns the meta object for the reference '{@link differencemodel.AddReference#getSrc <em>Src</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Src</em>'.
	 * @see differencemodel.AddReference#getSrc()
	 * @see #getAddReference()
	 * @generated
	 */
	EReference getAddReference_Src();

	/**
	 * Returns the meta object for the reference '{@link differencemodel.AddReference#getTgt <em>Tgt</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Tgt</em>'.
	 * @see differencemodel.AddReference#getTgt()
	 * @see #getAddReference()
	 * @generated
	 */
	EReference getAddReference_Tgt();

	/**
	 * Returns the meta object for the reference '{@link differencemodel.AddReference#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see differencemodel.AddReference#getType()
	 * @see #getAddReference()
	 * @generated
	 */
	EReference getAddReference_Type();

	/**
	 * Returns the meta object for class '{@link differencemodel.RemoveReference <em>Remove Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Remove Reference</em>'.
	 * @see differencemodel.RemoveReference
	 * @generated
	 */
	EClass getRemoveReference();

	/**
	 * Returns the meta object for the reference '{@link differencemodel.RemoveReference#getSrc <em>Src</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Src</em>'.
	 * @see differencemodel.RemoveReference#getSrc()
	 * @see #getRemoveReference()
	 * @generated
	 */
	EReference getRemoveReference_Src();

	/**
	 * Returns the meta object for the reference '{@link differencemodel.RemoveReference#getTgt <em>Tgt</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Tgt</em>'.
	 * @see differencemodel.RemoveReference#getTgt()
	 * @see #getRemoveReference()
	 * @generated
	 */
	EReference getRemoveReference_Tgt();

	/**
	 * Returns the meta object for the reference '{@link differencemodel.RemoveReference#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see differencemodel.RemoveReference#getType()
	 * @see #getRemoveReference()
	 * @generated
	 */
	EReference getRemoveReference_Type();

	/**
	 * Returns the meta object for class '{@link differencemodel.Change <em>Change</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Change</em>'.
	 * @see differencemodel.Change
	 * @generated
	 */
	EClass getChange();

	/**
	 * Returns the meta object for class '{@link differencemodel.SemanticChangeSet <em>Semantic Change Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Semantic Change Set</em>'.
	 * @see differencemodel.SemanticChangeSet
	 * @generated
	 */
	EClass getSemanticChangeSet();

	/**
	 * Returns the meta object for the reference list '{@link differencemodel.SemanticChangeSet#getChanges <em>Changes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Changes</em>'.
	 * @see differencemodel.SemanticChangeSet#getChanges()
	 * @see #getSemanticChangeSet()
	 * @generated
	 */
	EReference getSemanticChangeSet_Changes();

	/**
	 * Returns the meta object for the attribute '{@link differencemodel.SemanticChangeSet#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see differencemodel.SemanticChangeSet#getName()
	 * @see #getSemanticChangeSet()
	 * @generated
	 */
	EAttribute getSemanticChangeSet_Name();

	/**
	 * Returns the meta object for the attribute '{@link differencemodel.SemanticChangeSet#getPriority <em>Priority</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Priority</em>'.
	 * @see differencemodel.SemanticChangeSet#getPriority()
	 * @see #getSemanticChangeSet()
	 * @generated
	 */
	EAttribute getSemanticChangeSet_Priority();

	/**
	 * Returns the meta object for the attribute '{@link differencemodel.SemanticChangeSet#getRefinementLevel <em>Refinement Level</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Refinement Level</em>'.
	 * @see differencemodel.SemanticChangeSet#getRefinementLevel()
	 * @see #getSemanticChangeSet()
	 * @generated
	 */
	EAttribute getSemanticChangeSet_RefinementLevel();

	/**
	 * Returns the meta object for the attribute '{@link differencemodel.SemanticChangeSet#getEditRName <em>Edit RName</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Edit RName</em>'.
	 * @see differencemodel.SemanticChangeSet#getEditRName()
	 * @see #getSemanticChangeSet()
	 * @generated
	 */
	EAttribute getSemanticChangeSet_EditRName();

	/**
	 * Returns the meta object for the containment reference list '{@link differencemodel.SemanticChangeSet#getParameterSubstitutions <em>Parameter Substitutions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameter Substitutions</em>'.
	 * @see differencemodel.SemanticChangeSet#getParameterSubstitutions()
	 * @see #getSemanticChangeSet()
	 * @generated
	 */
	EReference getSemanticChangeSet_ParameterSubstitutions();

	/**
	 * Returns the meta object for the attribute '{@link differencemodel.SemanticChangeSet#getRecognitionRName <em>Recognition RName</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Recognition RName</em>'.
	 * @see differencemodel.SemanticChangeSet#getRecognitionRName()
	 * @see #getSemanticChangeSet()
	 * @generated
	 */
	EAttribute getSemanticChangeSet_RecognitionRName();

	/**
	 * Returns the meta object for the reference list '{@link differencemodel.SemanticChangeSet#getOutgoing <em>Outgoing</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Outgoing</em>'.
	 * @see differencemodel.SemanticChangeSet#getOutgoing()
	 * @see #getSemanticChangeSet()
	 * @generated
	 */
	EReference getSemanticChangeSet_Outgoing();

	/**
	 * Returns the meta object for the reference list '{@link differencemodel.SemanticChangeSet#getIncoming <em>Incoming</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Incoming</em>'.
	 * @see differencemodel.SemanticChangeSet#getIncoming()
	 * @see #getSemanticChangeSet()
	 * @generated
	 */
	EReference getSemanticChangeSet_Incoming();

	/**
	 * Returns the meta object for class '{@link differencemodel.Correspondence <em>Correspondence</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Correspondence</em>'.
	 * @see differencemodel.Correspondence
	 * @generated
	 */
	EClass getCorrespondence();

	/**
	 * Returns the meta object for the reference '{@link differencemodel.Correspondence#getObjA <em>Obj A</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Obj A</em>'.
	 * @see differencemodel.Correspondence#getObjA()
	 * @see #getCorrespondence()
	 * @generated
	 */
	EReference getCorrespondence_ObjA();

	/**
	 * Returns the meta object for the reference '{@link differencemodel.Correspondence#getObjB <em>Obj B</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Obj B</em>'.
	 * @see differencemodel.Correspondence#getObjB()
	 * @see #getCorrespondence()
	 * @generated
	 */
	EReference getCorrespondence_ObjB();

	/**
	 * Returns the meta object for class '{@link differencemodel.AttributeValueChange <em>Attribute Value Change</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attribute Value Change</em>'.
	 * @see differencemodel.AttributeValueChange
	 * @generated
	 */
	EClass getAttributeValueChange();

	/**
	 * Returns the meta object for the reference '{@link differencemodel.AttributeValueChange#getObjA <em>Obj A</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Obj A</em>'.
	 * @see differencemodel.AttributeValueChange#getObjA()
	 * @see #getAttributeValueChange()
	 * @generated
	 */
	EReference getAttributeValueChange_ObjA();

	/**
	 * Returns the meta object for the reference '{@link differencemodel.AttributeValueChange#getObjB <em>Obj B</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Obj B</em>'.
	 * @see differencemodel.AttributeValueChange#getObjB()
	 * @see #getAttributeValueChange()
	 * @generated
	 */
	EReference getAttributeValueChange_ObjB();

	/**
	 * Returns the meta object for the reference '{@link differencemodel.AttributeValueChange#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see differencemodel.AttributeValueChange#getType()
	 * @see #getAttributeValueChange()
	 * @generated
	 */
	EReference getAttributeValueChange_Type();

	/**
	 * Returns the meta object for class '{@link differencemodel.ParameterSubstitution <em>Parameter Substitution</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Parameter Substitution</em>'.
	 * @see differencemodel.ParameterSubstitution
	 * @generated
	 */
	EClass getParameterSubstitution();

	/**
	 * Returns the meta object for the attribute '{@link differencemodel.ParameterSubstitution#getFormal <em>Formal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Formal</em>'.
	 * @see differencemodel.ParameterSubstitution#getFormal()
	 * @see #getParameterSubstitution()
	 * @generated
	 */
	EAttribute getParameterSubstitution_Formal();

	/**
	 * Returns the meta object for class '{@link differencemodel.ObjectParameterSubstitution <em>Object Parameter Substitution</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Object Parameter Substitution</em>'.
	 * @see differencemodel.ObjectParameterSubstitution
	 * @generated
	 */
	EClass getObjectParameterSubstitution();

	/**
	 * Returns the meta object for the reference '{@link differencemodel.ObjectParameterSubstitution#getActualA <em>Actual A</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Actual A</em>'.
	 * @see differencemodel.ObjectParameterSubstitution#getActualA()
	 * @see #getObjectParameterSubstitution()
	 * @generated
	 */
	EReference getObjectParameterSubstitution_ActualA();

	/**
	 * Returns the meta object for the reference '{@link differencemodel.ObjectParameterSubstitution#getActualB <em>Actual B</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Actual B</em>'.
	 * @see differencemodel.ObjectParameterSubstitution#getActualB()
	 * @see #getObjectParameterSubstitution()
	 * @generated
	 */
	EReference getObjectParameterSubstitution_ActualB();

	/**
	 * Returns the meta object for class '{@link differencemodel.ValueParameterSubstitution <em>Value Parameter Substitution</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Value Parameter Substitution</em>'.
	 * @see differencemodel.ValueParameterSubstitution
	 * @generated
	 */
	EClass getValueParameterSubstitution();

	/**
	 * Returns the meta object for the attribute '{@link differencemodel.ValueParameterSubstitution#getActual <em>Actual</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Actual</em>'.
	 * @see differencemodel.ValueParameterSubstitution#getActual()
	 * @see #getValueParameterSubstitution()
	 * @generated
	 */
	EAttribute getValueParameterSubstitution_Actual();

	/**
	 * Returns the meta object for class '{@link differencemodel.Dependency <em>Dependency</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Dependency</em>'.
	 * @see differencemodel.Dependency
	 * @generated
	 */
	EClass getDependency();

	/**
	 * Returns the meta object for the attribute '{@link differencemodel.Dependency#getKind <em>Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Kind</em>'.
	 * @see differencemodel.Dependency#getKind()
	 * @see #getDependency()
	 * @generated
	 */
	EAttribute getDependency_Kind();

	/**
	 * Returns the meta object for the reference '{@link differencemodel.Dependency#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see differencemodel.Dependency#getSource()
	 * @see #getDependency()
	 * @generated
	 */
	EReference getDependency_Source();

	/**
	 * Returns the meta object for the reference '{@link differencemodel.Dependency#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see differencemodel.Dependency#getTarget()
	 * @see #getDependency()
	 * @generated
	 */
	EReference getDependency_Target();

	/**
	 * Returns the meta object for enum '{@link differencemodel.DependencyKind <em>Dependency Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Dependency Kind</em>'.
	 * @see differencemodel.DependencyKind
	 * @generated
	 */
	EEnum getDependencyKind();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	DifferencemodelFactory getDifferencemodelFactory();

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
		 * The meta object literal for the '{@link differencemodel.impl.DifferenceImpl <em>Difference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see differencemodel.impl.DifferenceImpl
		 * @see differencemodel.impl.DifferencemodelPackageImpl#getDifference()
		 * @generated
		 */
		EClass DIFFERENCE = eINSTANCE.getDifference();

		/**
		 * The meta object literal for the '<em><b>Changes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DIFFERENCE__CHANGES = eINSTANCE.getDifference_Changes();

		/**
		 * The meta object literal for the '<em><b>Change Sets</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DIFFERENCE__CHANGE_SETS = eINSTANCE.getDifference_ChangeSets();

		/**
		 * The meta object literal for the '<em><b>Correspondences</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DIFFERENCE__CORRESPONDENCES = eINSTANCE.getDifference_Correspondences();

		/**
		 * The meta object literal for the '<em><b>Model A</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DIFFERENCE__MODEL_A = eINSTANCE.getDifference_ModelA();

		/**
		 * The meta object literal for the '<em><b>Model B</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DIFFERENCE__MODEL_B = eINSTANCE.getDifference_ModelB();

		/**
		 * The meta object literal for the '<em><b>Dependencies</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DIFFERENCE__DEPENDENCIES = eINSTANCE.getDifference_Dependencies();

		/**
		 * The meta object literal for the '{@link differencemodel.impl.AddObjectImpl <em>Add Object</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see differencemodel.impl.AddObjectImpl
		 * @see differencemodel.impl.DifferencemodelPackageImpl#getAddObject()
		 * @generated
		 */
		EClass ADD_OBJECT = eINSTANCE.getAddObject();

		/**
		 * The meta object literal for the '<em><b>Obj</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ADD_OBJECT__OBJ = eINSTANCE.getAddObject_Obj();

		/**
		 * The meta object literal for the '{@link differencemodel.impl.RemoveObjectImpl <em>Remove Object</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see differencemodel.impl.RemoveObjectImpl
		 * @see differencemodel.impl.DifferencemodelPackageImpl#getRemoveObject()
		 * @generated
		 */
		EClass REMOVE_OBJECT = eINSTANCE.getRemoveObject();

		/**
		 * The meta object literal for the '<em><b>Obj</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REMOVE_OBJECT__OBJ = eINSTANCE.getRemoveObject_Obj();

		/**
		 * The meta object literal for the '{@link differencemodel.impl.AddReferenceImpl <em>Add Reference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see differencemodel.impl.AddReferenceImpl
		 * @see differencemodel.impl.DifferencemodelPackageImpl#getAddReference()
		 * @generated
		 */
		EClass ADD_REFERENCE = eINSTANCE.getAddReference();

		/**
		 * The meta object literal for the '<em><b>Src</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ADD_REFERENCE__SRC = eINSTANCE.getAddReference_Src();

		/**
		 * The meta object literal for the '<em><b>Tgt</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ADD_REFERENCE__TGT = eINSTANCE.getAddReference_Tgt();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ADD_REFERENCE__TYPE = eINSTANCE.getAddReference_Type();

		/**
		 * The meta object literal for the '{@link differencemodel.impl.RemoveReferenceImpl <em>Remove Reference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see differencemodel.impl.RemoveReferenceImpl
		 * @see differencemodel.impl.DifferencemodelPackageImpl#getRemoveReference()
		 * @generated
		 */
		EClass REMOVE_REFERENCE = eINSTANCE.getRemoveReference();

		/**
		 * The meta object literal for the '<em><b>Src</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REMOVE_REFERENCE__SRC = eINSTANCE.getRemoveReference_Src();

		/**
		 * The meta object literal for the '<em><b>Tgt</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REMOVE_REFERENCE__TGT = eINSTANCE.getRemoveReference_Tgt();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REMOVE_REFERENCE__TYPE = eINSTANCE.getRemoveReference_Type();

		/**
		 * The meta object literal for the '{@link differencemodel.impl.ChangeImpl <em>Change</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see differencemodel.impl.ChangeImpl
		 * @see differencemodel.impl.DifferencemodelPackageImpl#getChange()
		 * @generated
		 */
		EClass CHANGE = eINSTANCE.getChange();

		/**
		 * The meta object literal for the '{@link differencemodel.impl.SemanticChangeSetImpl <em>Semantic Change Set</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see differencemodel.impl.SemanticChangeSetImpl
		 * @see differencemodel.impl.DifferencemodelPackageImpl#getSemanticChangeSet()
		 * @generated
		 */
		EClass SEMANTIC_CHANGE_SET = eINSTANCE.getSemanticChangeSet();

		/**
		 * The meta object literal for the '<em><b>Changes</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SEMANTIC_CHANGE_SET__CHANGES = eINSTANCE.getSemanticChangeSet_Changes();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SEMANTIC_CHANGE_SET__NAME = eINSTANCE.getSemanticChangeSet_Name();

		/**
		 * The meta object literal for the '<em><b>Priority</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SEMANTIC_CHANGE_SET__PRIORITY = eINSTANCE.getSemanticChangeSet_Priority();

		/**
		 * The meta object literal for the '<em><b>Refinement Level</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SEMANTIC_CHANGE_SET__REFINEMENT_LEVEL = eINSTANCE.getSemanticChangeSet_RefinementLevel();

		/**
		 * The meta object literal for the '<em><b>Edit RName</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SEMANTIC_CHANGE_SET__EDIT_RNAME = eINSTANCE.getSemanticChangeSet_EditRName();

		/**
		 * The meta object literal for the '<em><b>Parameter Substitutions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SEMANTIC_CHANGE_SET__PARAMETER_SUBSTITUTIONS = eINSTANCE.getSemanticChangeSet_ParameterSubstitutions();

		/**
		 * The meta object literal for the '<em><b>Recognition RName</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SEMANTIC_CHANGE_SET__RECOGNITION_RNAME = eINSTANCE.getSemanticChangeSet_RecognitionRName();

		/**
		 * The meta object literal for the '<em><b>Outgoing</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SEMANTIC_CHANGE_SET__OUTGOING = eINSTANCE.getSemanticChangeSet_Outgoing();

		/**
		 * The meta object literal for the '<em><b>Incoming</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SEMANTIC_CHANGE_SET__INCOMING = eINSTANCE.getSemanticChangeSet_Incoming();

		/**
		 * The meta object literal for the '{@link differencemodel.impl.CorrespondenceImpl <em>Correspondence</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see differencemodel.impl.CorrespondenceImpl
		 * @see differencemodel.impl.DifferencemodelPackageImpl#getCorrespondence()
		 * @generated
		 */
		EClass CORRESPONDENCE = eINSTANCE.getCorrespondence();

		/**
		 * The meta object literal for the '<em><b>Obj A</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CORRESPONDENCE__OBJ_A = eINSTANCE.getCorrespondence_ObjA();

		/**
		 * The meta object literal for the '<em><b>Obj B</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CORRESPONDENCE__OBJ_B = eINSTANCE.getCorrespondence_ObjB();

		/**
		 * The meta object literal for the '{@link differencemodel.impl.AttributeValueChangeImpl <em>Attribute Value Change</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see differencemodel.impl.AttributeValueChangeImpl
		 * @see differencemodel.impl.DifferencemodelPackageImpl#getAttributeValueChange()
		 * @generated
		 */
		EClass ATTRIBUTE_VALUE_CHANGE = eINSTANCE.getAttributeValueChange();

		/**
		 * The meta object literal for the '<em><b>Obj A</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATTRIBUTE_VALUE_CHANGE__OBJ_A = eINSTANCE.getAttributeValueChange_ObjA();

		/**
		 * The meta object literal for the '<em><b>Obj B</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATTRIBUTE_VALUE_CHANGE__OBJ_B = eINSTANCE.getAttributeValueChange_ObjB();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATTRIBUTE_VALUE_CHANGE__TYPE = eINSTANCE.getAttributeValueChange_Type();

		/**
		 * The meta object literal for the '{@link differencemodel.impl.ParameterSubstitutionImpl <em>Parameter Substitution</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see differencemodel.impl.ParameterSubstitutionImpl
		 * @see differencemodel.impl.DifferencemodelPackageImpl#getParameterSubstitution()
		 * @generated
		 */
		EClass PARAMETER_SUBSTITUTION = eINSTANCE.getParameterSubstitution();

		/**
		 * The meta object literal for the '<em><b>Formal</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAMETER_SUBSTITUTION__FORMAL = eINSTANCE.getParameterSubstitution_Formal();

		/**
		 * The meta object literal for the '{@link differencemodel.impl.ObjectParameterSubstitutionImpl <em>Object Parameter Substitution</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see differencemodel.impl.ObjectParameterSubstitutionImpl
		 * @see differencemodel.impl.DifferencemodelPackageImpl#getObjectParameterSubstitution()
		 * @generated
		 */
		EClass OBJECT_PARAMETER_SUBSTITUTION = eINSTANCE.getObjectParameterSubstitution();

		/**
		 * The meta object literal for the '<em><b>Actual A</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OBJECT_PARAMETER_SUBSTITUTION__ACTUAL_A = eINSTANCE.getObjectParameterSubstitution_ActualA();

		/**
		 * The meta object literal for the '<em><b>Actual B</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OBJECT_PARAMETER_SUBSTITUTION__ACTUAL_B = eINSTANCE.getObjectParameterSubstitution_ActualB();

		/**
		 * The meta object literal for the '{@link differencemodel.impl.ValueParameterSubstitutionImpl <em>Value Parameter Substitution</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see differencemodel.impl.ValueParameterSubstitutionImpl
		 * @see differencemodel.impl.DifferencemodelPackageImpl#getValueParameterSubstitution()
		 * @generated
		 */
		EClass VALUE_PARAMETER_SUBSTITUTION = eINSTANCE.getValueParameterSubstitution();

		/**
		 * The meta object literal for the '<em><b>Actual</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VALUE_PARAMETER_SUBSTITUTION__ACTUAL = eINSTANCE.getValueParameterSubstitution_Actual();

		/**
		 * The meta object literal for the '{@link differencemodel.impl.DependencyImpl <em>Dependency</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see differencemodel.impl.DependencyImpl
		 * @see differencemodel.impl.DifferencemodelPackageImpl#getDependency()
		 * @generated
		 */
		EClass DEPENDENCY = eINSTANCE.getDependency();

		/**
		 * The meta object literal for the '<em><b>Kind</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DEPENDENCY__KIND = eINSTANCE.getDependency_Kind();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DEPENDENCY__SOURCE = eINSTANCE.getDependency_Source();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DEPENDENCY__TARGET = eINSTANCE.getDependency_Target();

		/**
		 * The meta object literal for the '{@link differencemodel.DependencyKind <em>Dependency Kind</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see differencemodel.DependencyKind
		 * @see differencemodel.impl.DifferencemodelPackageImpl#getDependencyKind()
		 * @generated
		 */
		EEnum DEPENDENCY_KIND = eINSTANCE.getDependencyKind();

	}

} //DifferencemodelPackage
