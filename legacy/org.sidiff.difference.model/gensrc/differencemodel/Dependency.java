/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package differencemodel;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Dependency</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link differencemodel.Dependency#getKind <em>Kind</em>}</li>
 *   <li>{@link differencemodel.Dependency#getSource <em>Source</em>}</li>
 *   <li>{@link differencemodel.Dependency#getTarget <em>Target</em>}</li>
 * </ul>
 * </p>
 *
 * @see differencemodel.DifferencemodelPackage#getDependency()
 * @model
 * @generated
 */
public interface Dependency extends EObject {
	/**
	 * Returns the value of the '<em><b>Kind</b></em>' attribute.
	 * The literals are from the enumeration {@link differencemodel.DependencyKind}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Kind</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Kind</em>' attribute.
	 * @see differencemodel.DependencyKind
	 * @see #setKind(DependencyKind)
	 * @see differencemodel.DifferencemodelPackage#getDependency_Kind()
	 * @model
	 * @generated
	 */
	DependencyKind getKind();

	/**
	 * Sets the value of the '{@link differencemodel.Dependency#getKind <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Kind</em>' attribute.
	 * @see differencemodel.DependencyKind
	 * @see #getKind()
	 * @generated
	 */
	void setKind(DependencyKind value);

	/**
	 * Returns the value of the '<em><b>Source</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link differencemodel.SemanticChangeSet#getOutgoing <em>Outgoing</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' reference.
	 * @see #setSource(SemanticChangeSet)
	 * @see differencemodel.DifferencemodelPackage#getDependency_Source()
	 * @see differencemodel.SemanticChangeSet#getOutgoing
	 * @model opposite="outgoing" required="true"
	 * @generated
	 */
	SemanticChangeSet getSource();

	/**
	 * Sets the value of the '{@link differencemodel.Dependency#getSource <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(SemanticChangeSet value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link differencemodel.SemanticChangeSet#getIncoming <em>Incoming</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(SemanticChangeSet)
	 * @see differencemodel.DifferencemodelPackage#getDependency_Target()
	 * @see differencemodel.SemanticChangeSet#getIncoming
	 * @model opposite="incoming" required="true"
	 * @generated
	 */
	SemanticChangeSet getTarget();

	/**
	 * Sets the value of the '{@link differencemodel.Dependency#getTarget <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(SemanticChangeSet value);

} // Dependency
