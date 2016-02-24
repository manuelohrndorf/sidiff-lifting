/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package differencemodel;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Remove Reference</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link differencemodel.RemoveReference#getSrc <em>Src</em>}</li>
 *   <li>{@link differencemodel.RemoveReference#getTgt <em>Tgt</em>}</li>
 *   <li>{@link differencemodel.RemoveReference#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see differencemodel.DifferencemodelPackage#getRemoveReference()
 * @model
 * @generated
 */
public interface RemoveReference extends Change {
	/**
	 * Returns the value of the '<em><b>Src</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Src</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Src</em>' reference.
	 * @see #setSrc(EObject)
	 * @see differencemodel.DifferencemodelPackage#getRemoveReference_Src()
	 * @model
	 * @generated
	 */
	EObject getSrc();

	/**
	 * Sets the value of the '{@link differencemodel.RemoveReference#getSrc <em>Src</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Src</em>' reference.
	 * @see #getSrc()
	 * @generated
	 */
	void setSrc(EObject value);

	/**
	 * Returns the value of the '<em><b>Tgt</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tgt</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tgt</em>' reference.
	 * @see #setTgt(EObject)
	 * @see differencemodel.DifferencemodelPackage#getRemoveReference_Tgt()
	 * @model
	 * @generated
	 */
	EObject getTgt();

	/**
	 * Sets the value of the '{@link differencemodel.RemoveReference#getTgt <em>Tgt</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tgt</em>' reference.
	 * @see #getTgt()
	 * @generated
	 */
	void setTgt(EObject value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' reference.
	 * @see #setType(EReference)
	 * @see differencemodel.DifferencemodelPackage#getRemoveReference_Type()
	 * @model
	 * @generated
	 */
	EReference getType();

	/**
	 * Sets the value of the '{@link differencemodel.RemoveReference#getType <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(EReference value);

} // RemoveReference
