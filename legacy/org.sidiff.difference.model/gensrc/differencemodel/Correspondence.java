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
 * A representation of the model object '<em><b>Correspondence</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link differencemodel.Correspondence#getObjA <em>Obj A</em>}</li>
 *   <li>{@link differencemodel.Correspondence#getObjB <em>Obj B</em>}</li>
 * </ul>
 * </p>
 *
 * @see differencemodel.DifferencemodelPackage#getCorrespondence()
 * @model
 * @generated
 */
public interface Correspondence extends EObject {
	/**
	 * Returns the value of the '<em><b>Obj A</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Obj A</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Obj A</em>' reference.
	 * @see #setObjA(EObject)
	 * @see differencemodel.DifferencemodelPackage#getCorrespondence_ObjA()
	 * @model
	 * @generated
	 */
	EObject getObjA();

	/**
	 * Sets the value of the '{@link differencemodel.Correspondence#getObjA <em>Obj A</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Obj A</em>' reference.
	 * @see #getObjA()
	 * @generated
	 */
	void setObjA(EObject value);

	/**
	 * Returns the value of the '<em><b>Obj B</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Obj B</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Obj B</em>' reference.
	 * @see #setObjB(EObject)
	 * @see differencemodel.DifferencemodelPackage#getCorrespondence_ObjB()
	 * @model
	 * @generated
	 */
	EObject getObjB();

	/**
	 * Sets the value of the '{@link differencemodel.Correspondence#getObjB <em>Obj B</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Obj B</em>' reference.
	 * @see #getObjB()
	 * @generated
	 */
	void setObjB(EObject value);

} // Correspondence
