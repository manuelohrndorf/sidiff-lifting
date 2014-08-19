/**
 */
package sample.mm.refined.samplemm;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Item Link</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link sample.mm.refined.samplemm.Item_Link#getPre <em>Pre</em>}</li>
 *   <li>{@link sample.mm.refined.samplemm.Item_Link#getSucc <em>Succ</em>}</li>
 * </ul>
 * </p>
 *
 * @see sample.mm.refined.samplemm.SamplemmPackage#getItem_Link()
 * @model
 * @generated
 */
public interface Item_Link extends EObject {
	/**
	 * Returns the value of the '<em><b>Pre</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pre</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pre</em>' reference.
	 * @see #setPre(Item)
	 * @see sample.mm.refined.samplemm.SamplemmPackage#getItem_Link_Pre()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	Item getPre();

	/**
	 * Sets the value of the '{@link sample.mm.refined.samplemm.Item_Link#getPre <em>Pre</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pre</em>' reference.
	 * @see #getPre()
	 * @generated
	 */
	void setPre(Item value);

	/**
	 * Returns the value of the '<em><b>Succ</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Succ</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Succ</em>' reference.
	 * @see #setSucc(Item)
	 * @see sample.mm.refined.samplemm.SamplemmPackage#getItem_Link_Succ()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	Item getSucc();

	/**
	 * Sets the value of the '{@link sample.mm.refined.samplemm.Item_Link#getSucc <em>Succ</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Succ</em>' reference.
	 * @see #getSucc()
	 * @generated
	 */
	void setSucc(Item value);

} // Item_Link
