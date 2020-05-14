/**
 */
package org.silift.difference.namedelementsymboliclink.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.silift.difference.namedelementsymboliclink.NamedElementSymbolicLinkObject;
import org.silift.difference.namedelementsymboliclink.NamedelementsymboliclinkPackage;
import org.silift.difference.symboliclink.impl.SymbolicLinkObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Named Element Symbolic Link Object</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.silift.difference.namedelementsymboliclink.impl.NamedElementSymbolicLinkObjectImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.silift.difference.namedelementsymboliclink.impl.NamedElementSymbolicLinkObjectImpl#getQualifiedName <em>Qualified Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class NamedElementSymbolicLinkObjectImpl extends SymbolicLinkObjectImpl implements NamedElementSymbolicLinkObject {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getQualifiedName() <em>Qualified Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQualifiedName()
	 * @generated
	 * @ordered
	 */
	protected static final String QUALIFIED_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getQualifiedName() <em>Qualified Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQualifiedName()
	 * @generated
	 * @ordered
	 */
	protected String qualifiedName = QUALIFIED_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NamedElementSymbolicLinkObjectImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return NamedelementsymboliclinkPackage.Literals.NAMED_ELEMENT_SYMBOLIC_LINK_OBJECT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public String getName() {
		return qualifiedName.substring(qualifiedName.lastIndexOf('.')+1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setName(String newName) {
		// TODO: implement this method to set the 'Name' attribute
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getQualifiedName() {
		return qualifiedName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setQualifiedName(String newQualifiedName) {
		String oldQualifiedName = qualifiedName;
		qualifiedName = newQualifiedName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, NamedelementsymboliclinkPackage.NAMED_ELEMENT_SYMBOLIC_LINK_OBJECT__QUALIFIED_NAME, oldQualifiedName, qualifiedName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case NamedelementsymboliclinkPackage.NAMED_ELEMENT_SYMBOLIC_LINK_OBJECT__NAME:
				return getName();
			case NamedelementsymboliclinkPackage.NAMED_ELEMENT_SYMBOLIC_LINK_OBJECT__QUALIFIED_NAME:
				return getQualifiedName();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case NamedelementsymboliclinkPackage.NAMED_ELEMENT_SYMBOLIC_LINK_OBJECT__NAME:
				setName((String)newValue);
				return;
			case NamedelementsymboliclinkPackage.NAMED_ELEMENT_SYMBOLIC_LINK_OBJECT__QUALIFIED_NAME:
				setQualifiedName((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case NamedelementsymboliclinkPackage.NAMED_ELEMENT_SYMBOLIC_LINK_OBJECT__NAME:
				setName(NAME_EDEFAULT);
				return;
			case NamedelementsymboliclinkPackage.NAMED_ELEMENT_SYMBOLIC_LINK_OBJECT__QUALIFIED_NAME:
				setQualifiedName(QUALIFIED_NAME_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case NamedelementsymboliclinkPackage.NAMED_ELEMENT_SYMBOLIC_LINK_OBJECT__NAME:
				return NAME_EDEFAULT == null ? getName() != null : !NAME_EDEFAULT.equals(getName());
			case NamedelementsymboliclinkPackage.NAMED_ELEMENT_SYMBOLIC_LINK_OBJECT__QUALIFIED_NAME:
				return QUALIFIED_NAME_EDEFAULT == null ? qualifiedName != null : !QUALIFIED_NAME_EDEFAULT.equals(qualifiedName);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (qualifiedName: ");
		result.append(qualifiedName);
		result.append(')');
		return result.toString();
	}

	/**
	 * @generated NOT
	 */
	@Override
	public boolean equals(Object o){
		if(o instanceof NamedElementSymbolicLinkObject){
			NamedElementSymbolicLinkObject symbl = (NamedElementSymbolicLinkObject)o;
			if(this.getQualifiedName().equals(symbl.getQualifiedName())){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @generated NOT
	 */
	@Override
	public int hashCode() {
		return qualifiedName.hashCode();
	}

} //NamedElementSymbolicLinkObjectImpl
