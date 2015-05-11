/**
 */
package org.sidiff.difference.symmetricprofiled.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.sidiff.difference.symmetric.SemanticChangeSet;
import org.sidiff.difference.symmetricprofiled.AppliedStereotype;
import org.sidiff.difference.symmetricprofiled.ProfiledSCS;
import org.sidiff.difference.symmetricprofiled.SymmetricProfiledPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Profiled SCS</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.sidiff.difference.symmetricprofiled.impl.ProfiledSCSImpl#getScs <em>Scs</em>}</li>
 *   <li>{@link org.sidiff.difference.symmetricprofiled.impl.ProfiledSCSImpl#getAppliedStereotypes <em>Applied Stereotypes</em>}</li>
 *   <li>{@link org.sidiff.difference.symmetricprofiled.impl.ProfiledSCSImpl#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProfiledSCSImpl extends MinimalEObjectImpl.Container implements
		ProfiledSCS {
	/**
	 * The cached value of the '{@link #getScs() <em>Scs</em>}' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getScs()
	 * @generated
	 * @ordered
	 */
	protected SemanticChangeSet scs;

	/**
	 * The cached value of the '{@link #getAppliedStereotypes()
	 * <em>Applied Stereotypes</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getAppliedStereotypes()
	 * @generated
	 * @ordered
	 */
	protected EList<AppliedStereotype> appliedStereotypes;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected ProfiledSCSImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SymmetricProfiledPackage.Literals.PROFILED_SCS;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public SemanticChangeSet getScs() {
		if (scs != null && scs.eIsProxy()) {
			InternalEObject oldScs = (InternalEObject)scs;
			scs = (SemanticChangeSet)eResolveProxy(oldScs);
			if (scs != oldScs) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, SymmetricProfiledPackage.PROFILED_SCS__SCS, oldScs, scs));
			}
		}
		return scs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public SemanticChangeSet basicGetScs() {
		return scs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setScs(SemanticChangeSet newScs) {
		SemanticChangeSet oldScs = scs;
		scs = newScs;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SymmetricProfiledPackage.PROFILED_SCS__SCS, oldScs, scs));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public String getName() {
		if (name == null) {
			String result = "";
			if (getScs() != null && getScs().getName() != null) {
				result = getScs().getName();
				result += " <<";
				for (AppliedStereotype st : getAppliedStereotypes()) {
					String name=st.getName();
					result += name.substring(name.lastIndexOf(".")+1);
					result += ", ";
				}
				result = result.substring(0, result.length() - 2);
				result += ">>";
			}
			name = result;
		}
		return name;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AppliedStereotype> getAppliedStereotypes() {
		if (appliedStereotypes == null) {
			appliedStereotypes = new EObjectContainmentEList<AppliedStereotype>(AppliedStereotype.class, this, SymmetricProfiledPackage.PROFILED_SCS__APPLIED_STEREOTYPES);
		}
		return appliedStereotypes;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public void addAppliedStereotype(AppliedStereotype appliedStereotype) {
		if (appliedStereotypes == null) {
			appliedStereotypes = new EObjectContainmentEList<AppliedStereotype>(
					AppliedStereotype.class, this,
					SymmetricProfiledPackage.PROFILED_SCS__APPLIED_STEREOTYPES);
		}
		appliedStereotypes.add(appliedStereotype);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SymmetricProfiledPackage.PROFILED_SCS__APPLIED_STEREOTYPES:
				return ((InternalEList<?>)getAppliedStereotypes()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SymmetricProfiledPackage.PROFILED_SCS__SCS:
				if (resolve) return getScs();
				return basicGetScs();
			case SymmetricProfiledPackage.PROFILED_SCS__APPLIED_STEREOTYPES:
				return getAppliedStereotypes();
			case SymmetricProfiledPackage.PROFILED_SCS__NAME:
				return getName();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case SymmetricProfiledPackage.PROFILED_SCS__SCS:
				setScs((SemanticChangeSet)newValue);
				return;
			case SymmetricProfiledPackage.PROFILED_SCS__APPLIED_STEREOTYPES:
				getAppliedStereotypes().clear();
				getAppliedStereotypes().addAll((Collection<? extends AppliedStereotype>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case SymmetricProfiledPackage.PROFILED_SCS__SCS:
				setScs((SemanticChangeSet)null);
				return;
			case SymmetricProfiledPackage.PROFILED_SCS__APPLIED_STEREOTYPES:
				getAppliedStereotypes().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case SymmetricProfiledPackage.PROFILED_SCS__SCS:
				return scs != null;
			case SymmetricProfiledPackage.PROFILED_SCS__APPLIED_STEREOTYPES:
				return appliedStereotypes != null && !appliedStereotypes.isEmpty();
			case SymmetricProfiledPackage.PROFILED_SCS__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments)
			throws InvocationTargetException {
		switch (operationID) {
			case SymmetricProfiledPackage.PROFILED_SCS___ADD_APPLIED_STEREOTYPE__APPLIEDSTEREOTYPE:
				addAppliedStereotype((AppliedStereotype)arguments.get(0));
				return null;
		}
		return super.eInvoke(operationID, arguments);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} // ProfiledSCSImpl
