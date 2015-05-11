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
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.difference.symmetric.AddObject;
import org.sidiff.difference.symmetric.Change;
import org.sidiff.difference.symmetric.Correspondence;
import org.sidiff.difference.symmetric.RemoveObject;
import org.sidiff.difference.symmetric.SemanticChangeSet;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.difference.symmetricprofiled.AppliedStereotype;
import org.sidiff.difference.symmetricprofiled.ProfiledSCS;
import org.sidiff.difference.symmetricprofiled.ProfiledSD;
import org.sidiff.difference.symmetricprofiled.SymmetricProfiledPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Profiled SD</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.sidiff.difference.symmetricprofiled.impl.ProfiledSDImpl#getProfiledscss <em>Profiledscss</em>}</li>
 *   <li>{@link org.sidiff.difference.symmetricprofiled.impl.ProfiledSDImpl#getSd <em>Sd</em>}</li>
 *   <li>{@link org.sidiff.difference.symmetricprofiled.impl.ProfiledSDImpl#getUnprofiledscss <em>Unprofiledscss</em>}</li>
 *   <li>{@link org.sidiff.difference.symmetricprofiled.impl.ProfiledSDImpl#getCorrespondences <em>Correspondences</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProfiledSDImpl extends MinimalEObjectImpl.Container implements
		ProfiledSD {
	/**
	 * The cached value of the '{@link #getProfiledscss() <em>Profiledscss</em>}
	 * ' containment reference list. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see #getProfiledscss()
	 * @generated
	 * @ordered
	 */
	protected EList<ProfiledSCS> profiledscss;

	/**
	 * The cached value of the '{@link #getSd() <em>Sd</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getSd()
	 * @generated
	 * @ordered
	 */
	protected SymmetricDifference sd;

	/**
	 * The cached value of the '{@link #getUnprofiledscss() <em>Unprofiledscss</em>}' reference list.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getUnprofiledscss()
	 * @generated
	 * @ordered
	 */
	protected EList<SemanticChangeSet> unprofiledscss;

	/**
	 * The cached value of the '{@link #getCorrespondences() <em>Correspondences</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCorrespondences()
	 * @generated
	 * @ordered
	 */
	protected EList<Correspondence> correspondences;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected ProfiledSDImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SymmetricProfiledPackage.Literals.PROFILED_SD;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ProfiledSCS> getProfiledscss() {
		if (profiledscss == null) {
			profiledscss = new EObjectContainmentEList<ProfiledSCS>(ProfiledSCS.class, this, SymmetricProfiledPackage.PROFILED_SD__PROFILEDSCSS);
		}
		return profiledscss;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public SymmetricDifference getSd() {
		return sd;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSd(SymmetricDifference newSd,
			NotificationChain msgs) {
		SymmetricDifference oldSd = sd;
		sd = newSd;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SymmetricProfiledPackage.PROFILED_SD__SD, oldSd, newSd);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setSd(SymmetricDifference newSd) {
		if (newSd != sd) {
			NotificationChain msgs = null;
			if (sd != null)
				msgs = ((InternalEObject)sd).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SymmetricProfiledPackage.PROFILED_SD__SD, null, msgs);
			if (newSd != null)
				msgs = ((InternalEObject)newSd).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SymmetricProfiledPackage.PROFILED_SD__SD, null, msgs);
			msgs = basicSetSd(newSd, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SymmetricProfiledPackage.PROFILED_SD__SD, newSd, newSd));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<SemanticChangeSet> getUnprofiledscss() {
		if (unprofiledscss == null) {
			unprofiledscss = new EObjectResolvingEList<SemanticChangeSet>(SemanticChangeSet.class, this, SymmetricProfiledPackage.PROFILED_SD__UNPROFILEDSCSS);
		}
		return unprofiledscss;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList<Correspondence> getCorrespondences() {
		return getSd().getCorrespondences();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public void derive(SymmetricDifference symmetricDifference) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		if (symmetricDifference == null)
			throw new IllegalArgumentException();
		SymmetricDifference copyedSymmetricDifference = EcoreUtil
				.copy(symmetricDifference);
		setSd(copyedSymmetricDifference);
		getProfiledscss().clear();
		getUnprofiledscss().clear();
		for (SemanticChangeSet scs : copyedSymmetricDifference
				.getNotOverlappings()) {
			Set<AppliedStereotype> sts = new HashSet<>();
			for (Change c : scs.getChanges()) {
				EObject[] src = { null, null };
				if (c instanceof RemoveObject)
					src[0] = ((RemoveObject) c).getObj();
				if (c instanceof AddObject)
					src[0] = ((AddObject) c).getObj();
//				if (c instanceof AddReference) {
//					src[0] = ((AddReference) c).getSrc();
//					src[1] = ((AddReference) c).getTgt();
//				}
//				if (c instanceof RemoveReference) {
//					src[0] = ((RemoveReference) c).getSrc();
//					src[1] = ((RemoveReference) c).getTgt();
//				}
				// TODO Add/RemoveReference
				for (EObject s : src) {
					if (s != null) {
						for (EObject stereotype : EMFModelAccess
								.getStereoTypes(s)) {
							AppliedStereotype st = new AppliedStereotypeImpl();
							st.setStereoType(stereotype);
							st.setBaseObject(s);
							sts.add(st);
						}
					}
				}
			}
			if (!sts.isEmpty()) {
				ProfiledSCS pscs = new ProfiledSCSImpl();
				pscs.setScs(scs);
				for (AppliedStereotype st : sts) {
					pscs.addAppliedStereotype(st);
				}
				getProfiledscss().add(pscs);
			} else {
				getUnprofiledscss().add(scs);
			}
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SymmetricProfiledPackage.PROFILED_SD__PROFILEDSCSS:
				return ((InternalEList<?>)getProfiledscss()).basicRemove(otherEnd, msgs);
			case SymmetricProfiledPackage.PROFILED_SD__SD:
				return basicSetSd(null, msgs);
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
			case SymmetricProfiledPackage.PROFILED_SD__PROFILEDSCSS:
				return getProfiledscss();
			case SymmetricProfiledPackage.PROFILED_SD__SD:
				return getSd();
			case SymmetricProfiledPackage.PROFILED_SD__UNPROFILEDSCSS:
				return getUnprofiledscss();
			case SymmetricProfiledPackage.PROFILED_SD__CORRESPONDENCES:
				return getCorrespondences();
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
			case SymmetricProfiledPackage.PROFILED_SD__PROFILEDSCSS:
				getProfiledscss().clear();
				getProfiledscss().addAll((Collection<? extends ProfiledSCS>)newValue);
				return;
			case SymmetricProfiledPackage.PROFILED_SD__SD:
				setSd((SymmetricDifference)newValue);
				return;
			case SymmetricProfiledPackage.PROFILED_SD__UNPROFILEDSCSS:
				getUnprofiledscss().clear();
				getUnprofiledscss().addAll((Collection<? extends SemanticChangeSet>)newValue);
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
			case SymmetricProfiledPackage.PROFILED_SD__PROFILEDSCSS:
				getProfiledscss().clear();
				return;
			case SymmetricProfiledPackage.PROFILED_SD__SD:
				setSd((SymmetricDifference)null);
				return;
			case SymmetricProfiledPackage.PROFILED_SD__UNPROFILEDSCSS:
				getUnprofiledscss().clear();
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
			case SymmetricProfiledPackage.PROFILED_SD__PROFILEDSCSS:
				return profiledscss != null && !profiledscss.isEmpty();
			case SymmetricProfiledPackage.PROFILED_SD__SD:
				return sd != null;
			case SymmetricProfiledPackage.PROFILED_SD__UNPROFILEDSCSS:
				return unprofiledscss != null && !unprofiledscss.isEmpty();
			case SymmetricProfiledPackage.PROFILED_SD__CORRESPONDENCES:
				return correspondences != null && !correspondences.isEmpty();
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
			case SymmetricProfiledPackage.PROFILED_SD___DERIVE__SYMMETRICDIFFERENCE:
				derive((SymmetricDifference)arguments.get(0));
				return null;
		}
		return super.eInvoke(operationID, arguments);
	}

} // ProfiledSDImpl
