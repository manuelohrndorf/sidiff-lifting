/**
 */
package org.eclipse.emf.henshin.model.impl;

import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Split;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Split</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.SplitImpl#getRule <em>Rule</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.SplitImpl#getSplitFrom <em>Split From</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.SplitImpl#getSplitInto <em>Split Into</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SplitImpl extends EObjectImpl implements Split {
	/**
	 * The cached value of the '{@link #getSplitFrom() <em>Split From</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSplitFrom()
	 * @generated
	 * @ordered
	 */
	protected EList<Node> splitFrom;
	/**
	 * The cached value of the '{@link #getSplitInto() <em>Split Into</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSplitInto()
	 * @generated
	 * @ordered
	 */
	protected EList<Node> splitInto;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SplitImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return HenshinPackage.Literals.SPLIT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Rule getRule() {
		if (eContainerFeatureID() != HenshinPackage.SPLIT__RULE) return null;
		return (Rule)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRule(Rule newRule, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newRule, HenshinPackage.SPLIT__RULE, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRule(Rule newRule) {
		if (newRule != eInternalContainer() || (eContainerFeatureID() != HenshinPackage.SPLIT__RULE && newRule != null)) {
			if (EcoreUtil.isAncestor(this, newRule))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newRule != null)
				msgs = ((InternalEObject)newRule).eInverseAdd(this, HenshinPackage.RULE__SPLITS, Rule.class, msgs);
			msgs = basicSetRule(newRule, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HenshinPackage.SPLIT__RULE, newRule, newRule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Node> getSplitFrom() {
		if (splitFrom == null) {
			splitFrom = new EObjectWithInverseResolvingEList<Node>(Node.class, this, HenshinPackage.SPLIT__SPLIT_FROM, HenshinPackage.NODE__SRC_OF_SPLIT);
		}
		return splitFrom;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Node> getSplitInto() {
		if (splitInto == null) {
			splitInto = new EObjectWithInverseResolvingEList<Node>(Node.class, this, HenshinPackage.SPLIT__SPLIT_INTO, HenshinPackage.NODE__TGT_OF_SPLIT);
		}
		return splitInto;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case HenshinPackage.SPLIT__RULE:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetRule((Rule)otherEnd, msgs);
			case HenshinPackage.SPLIT__SPLIT_FROM:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getSplitFrom()).basicAdd(otherEnd, msgs);
			case HenshinPackage.SPLIT__SPLIT_INTO:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getSplitInto()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case HenshinPackage.SPLIT__RULE:
				return basicSetRule(null, msgs);
			case HenshinPackage.SPLIT__SPLIT_FROM:
				return ((InternalEList<?>)getSplitFrom()).basicRemove(otherEnd, msgs);
			case HenshinPackage.SPLIT__SPLIT_INTO:
				return ((InternalEList<?>)getSplitInto()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case HenshinPackage.SPLIT__RULE:
				return eInternalContainer().eInverseRemove(this, HenshinPackage.RULE__SPLITS, Rule.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case HenshinPackage.SPLIT__RULE:
				return getRule();
			case HenshinPackage.SPLIT__SPLIT_FROM:
				return getSplitFrom();
			case HenshinPackage.SPLIT__SPLIT_INTO:
				return getSplitInto();
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
			case HenshinPackage.SPLIT__RULE:
				setRule((Rule)newValue);
				return;
			case HenshinPackage.SPLIT__SPLIT_FROM:
				getSplitFrom().clear();
				getSplitFrom().addAll((Collection<? extends Node>)newValue);
				return;
			case HenshinPackage.SPLIT__SPLIT_INTO:
				getSplitInto().clear();
				getSplitInto().addAll((Collection<? extends Node>)newValue);
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
			case HenshinPackage.SPLIT__RULE:
				setRule((Rule)null);
				return;
			case HenshinPackage.SPLIT__SPLIT_FROM:
				getSplitFrom().clear();
				return;
			case HenshinPackage.SPLIT__SPLIT_INTO:
				getSplitInto().clear();
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
			case HenshinPackage.SPLIT__RULE:
				return getRule() != null;
			case HenshinPackage.SPLIT__SPLIT_FROM:
				return splitFrom != null && !splitFrom.isEmpty();
			case HenshinPackage.SPLIT__SPLIT_INTO:
				return splitInto != null && !splitInto.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //SplitImpl
