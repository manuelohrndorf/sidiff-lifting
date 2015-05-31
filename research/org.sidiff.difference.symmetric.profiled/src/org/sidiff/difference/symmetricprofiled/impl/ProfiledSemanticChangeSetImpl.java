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
import org.sidiff.difference.symmetricprofiled.ProfiledSemanticChangeSet;
import org.sidiff.difference.symmetricprofiled.SymmetricProfiledPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Profiled Semantic Change Set</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.sidiff.difference.symmetricprofiled.impl.ProfiledSemanticChangeSetImpl#getSemanticChangeSet <em>Semantic Change Set</em>}</li>
 *   <li>{@link org.sidiff.difference.symmetricprofiled.impl.ProfiledSemanticChangeSetImpl#getAppliedStereotypes <em>Applied Stereotypes</em>}</li>
 *   <li>{@link org.sidiff.difference.symmetricprofiled.impl.ProfiledSemanticChangeSetImpl#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProfiledSemanticChangeSetImpl extends MinimalEObjectImpl.Container implements ProfiledSemanticChangeSet {
	/**
	 * The cached value of the '{@link #getSemanticChangeSet() <em>Semantic Change Set</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSemanticChangeSet()
	 * @generated
	 * @ordered
	 */
	protected SemanticChangeSet semanticChangeSet;

	/**
	 * The cached value of the '{@link #getAppliedStereotypes() <em>Applied Stereotypes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAppliedStereotypes()
	 * @generated
	 * @ordered
	 */
	protected EList<AppliedStereotype> appliedStereotypes;

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
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProfiledSemanticChangeSetImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SymmetricProfiledPackage.Literals.PROFILED_SEMANTIC_CHANGE_SET;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SemanticChangeSet getSemanticChangeSet() {
		if (semanticChangeSet != null && semanticChangeSet.eIsProxy()) {
			InternalEObject oldSemanticChangeSet = (InternalEObject)semanticChangeSet;
			semanticChangeSet = (SemanticChangeSet)eResolveProxy(oldSemanticChangeSet);
			if (semanticChangeSet != oldSemanticChangeSet) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, SymmetricProfiledPackage.PROFILED_SEMANTIC_CHANGE_SET__SEMANTIC_CHANGE_SET, oldSemanticChangeSet, semanticChangeSet));
			}
		}
		return semanticChangeSet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SemanticChangeSet basicGetSemanticChangeSet() {
		return semanticChangeSet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSemanticChangeSet(SemanticChangeSet newSemanticChangeSet) {
		SemanticChangeSet oldSemanticChangeSet = semanticChangeSet;
		semanticChangeSet = newSemanticChangeSet;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SymmetricProfiledPackage.PROFILED_SEMANTIC_CHANGE_SET__SEMANTIC_CHANGE_SET, oldSemanticChangeSet, semanticChangeSet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AppliedStereotype> getAppliedStereotypes() {
		if (appliedStereotypes == null) {
			appliedStereotypes = new EObjectContainmentEList<AppliedStereotype>(AppliedStereotype.class, this, SymmetricProfiledPackage.PROFILED_SEMANTIC_CHANGE_SET__APPLIED_STEREOTYPES);
		}
		return appliedStereotypes;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public String getName() {
		if (name == null) {
			String result = "";
			if (getSemanticChangeSet() != null && getSemanticChangeSet().getName() != null) {
				result = getSemanticChangeSet().getName();
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
	 * 
	 * @generated NOT
	 */
	public void addAppliedStereotype(AppliedStereotype appliedStereotype) {
		if (appliedStereotypes == null) {
			appliedStereotypes = new EObjectContainmentEList<AppliedStereotype>(
					AppliedStereotype.class, this,
					SymmetricProfiledPackage.PROFILED_SEMANTIC_CHANGE_SET__APPLIED_STEREOTYPES);
		}
		appliedStereotypes.add(appliedStereotype);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SymmetricProfiledPackage.PROFILED_SEMANTIC_CHANGE_SET__APPLIED_STEREOTYPES:
				return ((InternalEList<?>)getAppliedStereotypes()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SymmetricProfiledPackage.PROFILED_SEMANTIC_CHANGE_SET__SEMANTIC_CHANGE_SET:
				if (resolve) return getSemanticChangeSet();
				return basicGetSemanticChangeSet();
			case SymmetricProfiledPackage.PROFILED_SEMANTIC_CHANGE_SET__APPLIED_STEREOTYPES:
				return getAppliedStereotypes();
			case SymmetricProfiledPackage.PROFILED_SEMANTIC_CHANGE_SET__NAME:
				return getName();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case SymmetricProfiledPackage.PROFILED_SEMANTIC_CHANGE_SET__SEMANTIC_CHANGE_SET:
				setSemanticChangeSet((SemanticChangeSet)newValue);
				return;
			case SymmetricProfiledPackage.PROFILED_SEMANTIC_CHANGE_SET__APPLIED_STEREOTYPES:
				getAppliedStereotypes().clear();
				getAppliedStereotypes().addAll((Collection<? extends AppliedStereotype>)newValue);
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
			case SymmetricProfiledPackage.PROFILED_SEMANTIC_CHANGE_SET__SEMANTIC_CHANGE_SET:
				setSemanticChangeSet((SemanticChangeSet)null);
				return;
			case SymmetricProfiledPackage.PROFILED_SEMANTIC_CHANGE_SET__APPLIED_STEREOTYPES:
				getAppliedStereotypes().clear();
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
			case SymmetricProfiledPackage.PROFILED_SEMANTIC_CHANGE_SET__SEMANTIC_CHANGE_SET:
				return semanticChangeSet != null;
			case SymmetricProfiledPackage.PROFILED_SEMANTIC_CHANGE_SET__APPLIED_STEREOTYPES:
				return appliedStereotypes != null && !appliedStereotypes.isEmpty();
			case SymmetricProfiledPackage.PROFILED_SEMANTIC_CHANGE_SET__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case SymmetricProfiledPackage.PROFILED_SEMANTIC_CHANGE_SET___ADD_APPLIED_STEREOTYPE__APPLIEDSTEREOTYPE:
				addAppliedStereotype((AppliedStereotype)arguments.get(0));
				return null;
		}
		return super.eInvoke(operationID, arguments);
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
		result.append(" (name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} //ProfiledSemanticChangeSetImpl
