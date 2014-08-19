/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.difference.asymmetric.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.AsymmetricPackage;
import org.sidiff.difference.asymmetric.DependencyContainer;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.asymmetric.ParameterBinding;
import org.sidiff.difference.rulebase.EditRule;
import org.sidiff.difference.rulebase.ParameterDirection;
import org.sidiff.difference.rulebase.RuleBase;
import org.sidiff.difference.symmetric.SemanticChangeSet;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Operation Invocation</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>
 * {@link org.sidiff.difference.asymmetric.impl.OperationInvocationImpl#getParameterBindings
 * <em>Parameter Bindings</em>}</li>
 * <li>
 * {@link org.sidiff.difference.asymmetric.impl.OperationInvocationImpl#getOutgoing
 * <em>Outgoing</em>}</li>
 * <li>
 * {@link org.sidiff.difference.asymmetric.impl.OperationInvocationImpl#getIncoming
 * <em>Incoming</em>}</li>
 * <li>
 * {@link org.sidiff.difference.asymmetric.impl.OperationInvocationImpl#isApply
 * <em>Apply</em>}</li>
 * <li>
 * {@link org.sidiff.difference.asymmetric.impl.OperationInvocationImpl#getChangeSet
 * <em>Change Set</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class OperationInvocationImpl extends ExecutionImpl implements OperationInvocation {
	/**
	 * The cached value of the '{@link #getParameterBindings()
	 * <em>Parameter Bindings</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getParameterBindings()
	 * @generated
	 * @ordered
	 */
	protected EList<ParameterBinding> parameterBindings;

	/**
	 * The cached value of the '{@link #getOutgoing() <em>Outgoing</em>}'
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getOutgoing()
	 * @generated
	 * @ordered
	 */
	protected EList<DependencyContainer> outgoing;

	/**
	 * The cached value of the '{@link #getIncoming() <em>Incoming</em>}'
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getIncoming()
	 * @generated
	 * @ordered
	 */
	protected EList<DependencyContainer> incoming;

	/**
	 * The default value of the '{@link #isApply() <em>Apply</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isApply()
	 * @generated
	 * @ordered
	 */
	protected static final boolean APPLY_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isApply() <em>Apply</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isApply()
	 * @generated
	 * @ordered
	 */
	protected boolean apply = APPLY_EDEFAULT;

	/**
	 * The cached value of the '{@link #getChangeSet() <em>Change Set</em>}'
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getChangeSet()
	 * @generated
	 * @ordered
	 */
	protected SemanticChangeSet changeSet;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected OperationInvocationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AsymmetricPackage.Literals.OPERATION_INVOCATION;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<ParameterBinding> getParameterBindings() {
		if (parameterBindings == null) {
			parameterBindings = new EObjectContainmentEList<ParameterBinding>(ParameterBinding.class, this,
					AsymmetricPackage.OPERATION_INVOCATION__PARAMETER_BINDINGS);
		}
		return parameterBindings;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<DependencyContainer> getOutgoing() {
		if (outgoing == null) {
			outgoing = new EObjectWithInverseResolvingEList<DependencyContainer>(DependencyContainer.class, this,
					AsymmetricPackage.OPERATION_INVOCATION__OUTGOING, AsymmetricPackage.DEPENDENCY_CONTAINER__SOURCE);
		}
		return outgoing;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<DependencyContainer> getIncoming() {
		if (incoming == null) {
			incoming = new EObjectWithInverseResolvingEList<DependencyContainer>(DependencyContainer.class, this,
					AsymmetricPackage.OPERATION_INVOCATION__INCOMING, AsymmetricPackage.DEPENDENCY_CONTAINER__TARGET);
		}
		return incoming;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean isApply() {
		return apply;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setApply(boolean newApply) {
		boolean oldApply = apply;
		apply = newApply;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AsymmetricPackage.OPERATION_INVOCATION__APPLY,
					oldApply, apply));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public SemanticChangeSet getChangeSet() {
		if (changeSet != null && changeSet.eIsProxy()) {
			InternalEObject oldChangeSet = (InternalEObject) changeSet;
			changeSet = (SemanticChangeSet) eResolveProxy(oldChangeSet);
			if (changeSet != oldChangeSet) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							AsymmetricPackage.OPERATION_INVOCATION__CHANGE_SET, oldChangeSet, changeSet));
			}
		}
		return changeSet;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public SemanticChangeSet basicGetChangeSet() {
		return changeSet;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setChangeSet(SemanticChangeSet newChangeSet) {
		SemanticChangeSet oldChangeSet = changeSet;
		changeSet = newChangeSet;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AsymmetricPackage.OPERATION_INVOCATION__CHANGE_SET,
					oldChangeSet, changeSet));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case AsymmetricPackage.OPERATION_INVOCATION__OUTGOING:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getOutgoing()).basicAdd(otherEnd, msgs);
		case AsymmetricPackage.OPERATION_INVOCATION__INCOMING:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getIncoming()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case AsymmetricPackage.OPERATION_INVOCATION__PARAMETER_BINDINGS:
			return ((InternalEList<?>) getParameterBindings()).basicRemove(otherEnd, msgs);
		case AsymmetricPackage.OPERATION_INVOCATION__OUTGOING:
			return ((InternalEList<?>) getOutgoing()).basicRemove(otherEnd, msgs);
		case AsymmetricPackage.OPERATION_INVOCATION__INCOMING:
			return ((InternalEList<?>) getIncoming()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case AsymmetricPackage.OPERATION_INVOCATION__PARAMETER_BINDINGS:
			return getParameterBindings();
		case AsymmetricPackage.OPERATION_INVOCATION__OUTGOING:
			return getOutgoing();
		case AsymmetricPackage.OPERATION_INVOCATION__INCOMING:
			return getIncoming();
		case AsymmetricPackage.OPERATION_INVOCATION__APPLY:
			return isApply();
		case AsymmetricPackage.OPERATION_INVOCATION__CHANGE_SET:
			if (resolve)
				return getChangeSet();
			return basicGetChangeSet();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case AsymmetricPackage.OPERATION_INVOCATION__PARAMETER_BINDINGS:
			getParameterBindings().clear();
			getParameterBindings().addAll((Collection<? extends ParameterBinding>) newValue);
			return;
		case AsymmetricPackage.OPERATION_INVOCATION__OUTGOING:
			getOutgoing().clear();
			getOutgoing().addAll((Collection<? extends DependencyContainer>) newValue);
			return;
		case AsymmetricPackage.OPERATION_INVOCATION__INCOMING:
			getIncoming().clear();
			getIncoming().addAll((Collection<? extends DependencyContainer>) newValue);
			return;
		case AsymmetricPackage.OPERATION_INVOCATION__APPLY:
			setApply((Boolean) newValue);
			return;
		case AsymmetricPackage.OPERATION_INVOCATION__CHANGE_SET:
			setChangeSet((SemanticChangeSet) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case AsymmetricPackage.OPERATION_INVOCATION__PARAMETER_BINDINGS:
			getParameterBindings().clear();
			return;
		case AsymmetricPackage.OPERATION_INVOCATION__OUTGOING:
			getOutgoing().clear();
			return;
		case AsymmetricPackage.OPERATION_INVOCATION__INCOMING:
			getIncoming().clear();
			return;
		case AsymmetricPackage.OPERATION_INVOCATION__APPLY:
			setApply(APPLY_EDEFAULT);
			return;
		case AsymmetricPackage.OPERATION_INVOCATION__CHANGE_SET:
			setChangeSet((SemanticChangeSet) null);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case AsymmetricPackage.OPERATION_INVOCATION__PARAMETER_BINDINGS:
			return parameterBindings != null && !parameterBindings.isEmpty();
		case AsymmetricPackage.OPERATION_INVOCATION__OUTGOING:
			return outgoing != null && !outgoing.isEmpty();
		case AsymmetricPackage.OPERATION_INVOCATION__INCOMING:
			return incoming != null && !incoming.isEmpty();
		case AsymmetricPackage.OPERATION_INVOCATION__APPLY:
			return apply != APPLY_EDEFAULT;
		case AsymmetricPackage.OPERATION_INVOCATION__CHANGE_SET:
			return changeSet != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (apply: ");
		result.append(apply);
		result.append(')');
		return result.toString();
	}

	@Override
	public boolean isComplex() {
		// TODO: Use TransformationConstants for Literal instead of Literal
		// "editrules.complex"
		return resolveEditRule().getExecuteModule().eResource().getURI().toString().contains("editrules.complex");
	}

	@Override
	public List<OperationInvocation> getPredecessors() {
		ArrayList<OperationInvocation> res = new ArrayList<OperationInvocation>();
		for (DependencyContainer dep : getOutgoing()) {
			res.add(dep.getTarget());
		}

		return Collections.unmodifiableList(res);
	}

	@Override
	public List<OperationInvocation> getSuccessors() {
		ArrayList<OperationInvocation> res = new ArrayList<OperationInvocation>();
		for (DependencyContainer dep : getIncoming()) {
			res.add(dep.getSource());
		}

		return Collections.unmodifiableList(res);
	}

	@Override
	public List<OperationInvocation> getAllPredecessors() {
		ArrayList<OperationInvocation> res = new ArrayList<OperationInvocation>();
		addAllPredecessors(res, this);
		return Collections.unmodifiableList(res);
	}

	private void addAllPredecessors(List<OperationInvocation> list, OperationInvocation op) {
		list.add(op);
		for (OperationInvocation opPre : op.getPredecessors()) {
			addAllPredecessors(list, opPre);
		}
	}

	@Override
	public List<OperationInvocation> getAllSuccessors() {
		ArrayList<OperationInvocation> res = new ArrayList<OperationInvocation>();
		addAllSuccessors(res, this);
		return Collections.unmodifiableList(res);
	}

	private void addAllSuccessors(List<OperationInvocation> list, OperationInvocation op) {
		list.add(op);
		for (OperationInvocation opSucc : op.getSuccessors()) {
			addAllPredecessors(list, opSucc);
		}
	}

	@Override
	public List<ParameterBinding> getInParameterBindings() {
		List<ParameterBinding> res = new ArrayList<ParameterBinding>();
		for (ParameterBinding parameterBinding : getParameterBindings()) {
			if (parameterBinding.getFormalParameter().getDirection().equals(ParameterDirection.IN)) {
				res.add(parameterBinding);
			}
		}

		return res;
	}

	@Override
	public List<ParameterBinding> getOutParameterBindings() {
		List<ParameterBinding> res = new ArrayList<ParameterBinding>();
		for (ParameterBinding parameterBinding : getParameterBindings()) {
			if (parameterBinding.getFormalParameter().getDirection().equals(ParameterDirection.OUT)) {
				res.add(parameterBinding);
			}
		}

		return res;
	}

	private EditRule editRule;

	@Override
	public EditRule resolveEditRule() {
		// Already resolved -> just return it
		if (editRule != null) {
			return editRule;
		}

		// Get it from the patch
		AsymmetricDifference diff = (AsymmetricDifference) this.eContainer();
		if (diff.getRuleBases() != null) {
			for (RuleBase rb : diff.getRuleBases()) {
				for (EditRule editrule : rb.getEditRules()) {
					if (editrule.getExecuteModule().getName().equals(this.getChangeSet().getEditRName())) {
						this.editRule = editrule;
						return editrule;
					}
				}
			}
		}

		// If there is no rule physically available, we delegate the resolution
		// to the SemanticChangeSet (which finally tries to derive it via the
		// available rulebases)
		this.editRule = this.getChangeSet().resolveEditRule(); 
		return this.editRule;

	}

} // OperationInvocationImpl
