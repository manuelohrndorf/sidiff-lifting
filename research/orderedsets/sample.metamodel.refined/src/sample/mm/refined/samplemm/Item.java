/**
 */
package sample.mm.refined.samplemm;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Item</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link sample.mm.refined.samplemm.Item#getName <em>Name</em>}</li>
 *   <li>{@link sample.mm.refined.samplemm.Item#getPre <em>Pre</em>}</li>
 *   <li>{@link sample.mm.refined.samplemm.Item#getSucc <em>Succ</em>}</li>
 * </ul>
 * </p>
 *
 * @see sample.mm.refined.samplemm.SamplemmPackage#getItem()
 * @model
 * @generated
 */
public interface Item extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see sample.mm.refined.samplemm.SamplemmPackage#getItem_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link sample.mm.refined.samplemm.Item#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Pre</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link sample.mm.refined.samplemm.Item#getSucc <em>Succ</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pre</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pre</em>' reference.
	 * @see #setPre(Item)
	 * @see sample.mm.refined.samplemm.SamplemmPackage#getItem_Pre()
	 * @see sample.mm.refined.samplemm.Item#getSucc
	 * @model opposite="succ" ordered="false"
	 * @generated
	 */
	Item getPre();

	/**
	 * Sets the value of the '{@link sample.mm.refined.samplemm.Item#getPre <em>Pre</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pre</em>' reference.
	 * @see #getPre()
	 * @generated
	 */
	void setPre(Item value);

	/**
	 * Returns the value of the '<em><b>Succ</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link sample.mm.refined.samplemm.Item#getPre <em>Pre</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Succ</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Succ</em>' reference.
	 * @see #setSucc(Item)
	 * @see sample.mm.refined.samplemm.SamplemmPackage#getItem_Succ()
	 * @see sample.mm.refined.samplemm.Item#getPre
	 * @model opposite="pre" ordered="false"
	 * @generated
	 */
	Item getSucc();

	/**
	 * Sets the value of the '{@link sample.mm.refined.samplemm.Item#getSucc <em>Succ</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Succ</em>' reference.
	 * @see #getSucc()
	 * @generated
	 */
	void setSucc(Item value);

} // Item
