/**
 */
package org.sidiff.difference.symmetricprofiled.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.difference.symmetric.AddObject;
import org.sidiff.difference.symmetric.AddReference;
import org.sidiff.difference.symmetric.Change;
import org.sidiff.difference.symmetric.RemoveObject;
import org.sidiff.difference.symmetric.RemoveReference;
import org.sidiff.difference.symmetric.SemanticChangeSet;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.difference.symmetricprofiled.AppliedStereotype;
import org.sidiff.difference.symmetricprofiled.ProfiledSemanticChangeSet;
import org.sidiff.difference.symmetricprofiled.ProfiledSymmetricDifference;
import org.sidiff.difference.symmetricprofiled.SymmetricProfiledPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Profiled Symmetric Difference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.sidiff.difference.symmetricprofiled.impl.ProfiledSymmetricDifferenceImpl#getProfiledSemanticChangeSets <em>Profiled Semantic Change Sets</em>}</li>
 *   <li>{@link org.sidiff.difference.symmetricprofiled.impl.ProfiledSymmetricDifferenceImpl#getSymmetricDifference <em>Symmetric Difference</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProfiledSymmetricDifferenceImpl extends MinimalEObjectImpl.Container implements ProfiledSymmetricDifference {
	/**
	 * The cached value of the '{@link #getProfiledSemanticChangeSets() <em>Profiled Semantic Change Sets</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProfiledSemanticChangeSets()
	 * @generated
	 * @ordered
	 */
	protected EList<ProfiledSemanticChangeSet> profiledSemanticChangeSets;

	/**
	 * The cached value of the '{@link #getSymmetricDifference() <em>Symmetric Difference</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSymmetricDifference()
	 * @generated
	 * @ordered
	 */
	protected SymmetricDifference symmetricDifference;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProfiledSymmetricDifferenceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SymmetricProfiledPackage.Literals.PROFILED_SYMMETRIC_DIFFERENCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ProfiledSemanticChangeSet> getProfiledSemanticChangeSets() {
		if (profiledSemanticChangeSets == null) {
			profiledSemanticChangeSets = new EObjectContainmentEList<ProfiledSemanticChangeSet>(ProfiledSemanticChangeSet.class, this, SymmetricProfiledPackage.PROFILED_SYMMETRIC_DIFFERENCE__PROFILED_SEMANTIC_CHANGE_SETS);
		}
		return profiledSemanticChangeSets;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SymmetricDifference getSymmetricDifference() {
		return symmetricDifference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSymmetricDifference(SymmetricDifference newSymmetricDifference, NotificationChain msgs) {
		SymmetricDifference oldSymmetricDifference = symmetricDifference;
		symmetricDifference = newSymmetricDifference;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SymmetricProfiledPackage.PROFILED_SYMMETRIC_DIFFERENCE__SYMMETRIC_DIFFERENCE, oldSymmetricDifference, newSymmetricDifference);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSymmetricDifference(SymmetricDifference newSymmetricDifference) {
		if (newSymmetricDifference != symmetricDifference) {
			NotificationChain msgs = null;
			if (symmetricDifference != null)
				msgs = ((InternalEObject)symmetricDifference).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SymmetricProfiledPackage.PROFILED_SYMMETRIC_DIFFERENCE__SYMMETRIC_DIFFERENCE, null, msgs);
			if (newSymmetricDifference != null)
				msgs = ((InternalEObject)newSymmetricDifference).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SymmetricProfiledPackage.PROFILED_SYMMETRIC_DIFFERENCE__SYMMETRIC_DIFFERENCE, null, msgs);
			msgs = basicSetSymmetricDifference(newSymmetricDifference, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SymmetricProfiledPackage.PROFILED_SYMMETRIC_DIFFERENCE__SYMMETRIC_DIFFERENCE, newSymmetricDifference, newSymmetricDifference));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void derive(SymmetricDifference symmetricDifference) {
		if (symmetricDifference == null)
			throw new IllegalArgumentException();
		EcoreUtil.resolveAll(symmetricDifference);
		setSymmetricDifference(symmetricDifference);
		getProfiledSemanticChangeSets().clear();
		for (SemanticChangeSet scs : symmetricDifference.getChangeSets()) {
			Set<AppliedStereotype> sts = new HashSet<>();
			for (Change c : scs.getChanges()) {
				EObject[] src = { null, null };
				if (c instanceof RemoveObject)
					src[0] = ((RemoveObject) c).getObj();
				if (c instanceof AddObject)
					src[0] = ((AddObject) c).getObj();
				if (c instanceof AddReference) {
					src[0] = ((AddReference) c).getSrc();
					src[1] = ((AddReference) c).getTgt();
				}
				if (c instanceof RemoveReference) {
					src[0] = ((RemoveReference) c).getSrc();
					src[1] = ((RemoveReference) c).getTgt();
				}
				for (EObject s : src) {
					if (s != null &&s.eResource() != null) {
						for (EObject stereotype : EMFModelAccess
								.getStereoTypes(s)) {
							AppliedStereotype st = new AppliedStereotypeImpl();
							st.setStereoType(stereotype);
							st.setBaseObject(s);
							boolean found=false;
							for (AppliedStereotype ast : sts){
								if (ast.getBaseObject().equals(s) && ast.getStereoType().equals(stereotype)){
									found=true;
									break;
								}
							}
							if (!found) sts.add(st);
						}
					}
				}
			}
			if (!sts.isEmpty()) {
				ProfiledSemanticChangeSet pscs = new ProfiledSemanticChangeSetImpl();
				pscs.setSemanticChangeSet(scs);
				for (AppliedStereotype st : sts) {
					pscs.addAppliedStereotype(st);
				}
				getProfiledSemanticChangeSets().add(pscs);
			}
		}
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SymmetricProfiledPackage.PROFILED_SYMMETRIC_DIFFERENCE__PROFILED_SEMANTIC_CHANGE_SETS:
				return ((InternalEList<?>)getProfiledSemanticChangeSets()).basicRemove(otherEnd, msgs);
			case SymmetricProfiledPackage.PROFILED_SYMMETRIC_DIFFERENCE__SYMMETRIC_DIFFERENCE:
				return basicSetSymmetricDifference(null, msgs);
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
			case SymmetricProfiledPackage.PROFILED_SYMMETRIC_DIFFERENCE__PROFILED_SEMANTIC_CHANGE_SETS:
				return getProfiledSemanticChangeSets();
			case SymmetricProfiledPackage.PROFILED_SYMMETRIC_DIFFERENCE__SYMMETRIC_DIFFERENCE:
				return getSymmetricDifference();
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
			case SymmetricProfiledPackage.PROFILED_SYMMETRIC_DIFFERENCE__PROFILED_SEMANTIC_CHANGE_SETS:
				getProfiledSemanticChangeSets().clear();
				getProfiledSemanticChangeSets().addAll((Collection<? extends ProfiledSemanticChangeSet>)newValue);
				return;
			case SymmetricProfiledPackage.PROFILED_SYMMETRIC_DIFFERENCE__SYMMETRIC_DIFFERENCE:
				setSymmetricDifference((SymmetricDifference)newValue);
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
			case SymmetricProfiledPackage.PROFILED_SYMMETRIC_DIFFERENCE__PROFILED_SEMANTIC_CHANGE_SETS:
				getProfiledSemanticChangeSets().clear();
				return;
			case SymmetricProfiledPackage.PROFILED_SYMMETRIC_DIFFERENCE__SYMMETRIC_DIFFERENCE:
				setSymmetricDifference((SymmetricDifference)null);
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
			case SymmetricProfiledPackage.PROFILED_SYMMETRIC_DIFFERENCE__PROFILED_SEMANTIC_CHANGE_SETS:
				return profiledSemanticChangeSets != null && !profiledSemanticChangeSets.isEmpty();
			case SymmetricProfiledPackage.PROFILED_SYMMETRIC_DIFFERENCE__SYMMETRIC_DIFFERENCE:
				return symmetricDifference != null;
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
			case SymmetricProfiledPackage.PROFILED_SYMMETRIC_DIFFERENCE___DERIVE__SYMMETRICDIFFERENCE:
				derive((SymmetricDifference)arguments.get(0));
				return null;
		}
		return super.eInvoke(operationID, arguments);
	}

} //ProfiledSymmetricDifferenceImpl
