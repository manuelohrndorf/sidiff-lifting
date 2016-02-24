/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package differencemodel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.resource.Resource;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Difference</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link differencemodel.Difference#getChanges <em>Changes</em>}</li>
 *   <li>{@link differencemodel.Difference#getChangeSets <em>Change Sets</em>}</li>
 *   <li>{@link differencemodel.Difference#getCorrespondences <em>Correspondences</em>}</li>
 *   <li>{@link differencemodel.Difference#getModelA <em>Model A</em>}</li>
 *   <li>{@link differencemodel.Difference#getModelB <em>Model B</em>}</li>
 *   <li>{@link differencemodel.Difference#getDependencies <em>Dependencies</em>}</li>
 * </ul>
 * </p>
 *
 * @see differencemodel.DifferencemodelPackage#getDifference()
 * @model
 * @generated
 */
public interface Difference extends EObject {
	/**
	 * Returns the value of the '<em><b>Changes</b></em>' containment reference list.
	 * The list contents are of type {@link differencemodel.Change}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Changes</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Changes</em>' containment reference list.
	 * @see differencemodel.DifferencemodelPackage#getDifference_Changes()
	 * @model containment="true"
	 * @generated
	 */
	EList<Change> getChanges();

	/**
	 * Returns the value of the '<em><b>Change Sets</b></em>' containment reference list.
	 * The list contents are of type {@link differencemodel.SemanticChangeSet}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Change Sets</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Change Sets</em>' containment reference list.
	 * @see differencemodel.DifferencemodelPackage#getDifference_ChangeSets()
	 * @model containment="true"
	 * @generated
	 */
	EList<SemanticChangeSet> getChangeSets();

	/**
	 * Returns the value of the '<em><b>Correspondences</b></em>' containment reference list.
	 * The list contents are of type {@link differencemodel.Correspondence}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Correspondences</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Correspondences</em>' containment reference list.
	 * @see differencemodel.DifferencemodelPackage#getDifference_Correspondences()
	 * @model containment="true"
	 * @generated
	 */
	EList<Correspondence> getCorrespondences();

	/**
	 * Returns the value of the '<em><b>Model A</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Model A</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Model A</em>' attribute.
	 * @see differencemodel.DifferencemodelPackage#getDifference_ModelA()
	 * @model transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	Resource getModelA();

	/**
	 * Returns the value of the '<em><b>Model B</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Model B</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Model B</em>' attribute.
	 * @see differencemodel.DifferencemodelPackage#getDifference_ModelB()
	 * @model transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	Resource getModelB();

	/**
	 * Returns the value of the '<em><b>Dependencies</b></em>' containment reference list.
	 * The list contents are of type {@link differencemodel.Dependency}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Dependencies</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dependencies</em>' containment reference list.
	 * @see differencemodel.DifferencemodelPackage#getDifference_Dependencies()
	 * @model containment="true"
	 * @generated
	 */
	EList<Dependency> getDependencies();

} // Difference
