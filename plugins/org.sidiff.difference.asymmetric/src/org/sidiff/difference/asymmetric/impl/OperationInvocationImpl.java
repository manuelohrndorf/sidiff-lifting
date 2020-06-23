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
import java.util.HashSet;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.sidiff.common.emf.doctype.util.EMFDocumentTypeUtil;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.AsymmetricPackage;
import org.sidiff.difference.asymmetric.DependencyContainer;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.asymmetric.ParameterBinding;
import org.sidiff.difference.lifting.edit2recognition.util.TransformationConstants;
import org.sidiff.difference.symmetric.SemanticChangeSet;
import org.sidiff.editrule.rulebase.EditRule;
import org.sidiff.editrule.rulebase.ParameterDirection;
import org.sidiff.editrule.rulebase.RuleBase;
import org.sidiff.editrule.rulebase.project.runtime.library.IRuleBaseProject;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Operation Invocation</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.difference.asymmetric.impl.OperationInvocationImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.sidiff.difference.asymmetric.impl.OperationInvocationImpl#getEditRuleName <em>Edit Rule Name</em>}</li>
 *   <li>{@link org.sidiff.difference.asymmetric.impl.OperationInvocationImpl#isApply <em>Apply</em>}</li>
 *   <li>{@link org.sidiff.difference.asymmetric.impl.OperationInvocationImpl#getParameterBindings <em>Parameter Bindings</em>}</li>
 *   <li>{@link org.sidiff.difference.asymmetric.impl.OperationInvocationImpl#getOutgoing <em>Outgoing</em>}</li>
 *   <li>{@link org.sidiff.difference.asymmetric.impl.OperationInvocationImpl#getIncoming <em>Incoming</em>}</li>
 *   <li>{@link org.sidiff.difference.asymmetric.impl.OperationInvocationImpl#getChangeSet <em>Change Set</em>}</li>
 *   <li>{@link org.sidiff.difference.asymmetric.impl.OperationInvocationImpl#getAsymmetricDifference <em>Asymmetric Difference</em>}</li>
 * </ul>
 *
 * @generated
 */
public class OperationInvocationImpl extends ExecutionImpl implements OperationInvocation {
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
	 * The default value of the '{@link #getEditRuleName() <em>Edit Rule Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEditRuleName()
	 * @generated
	 * @ordered
	 */
	protected static final String EDIT_RULE_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEditRuleName() <em>Edit Rule Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEditRuleName()
	 * @generated
	 * @ordered
	 */
	protected String editRuleName = EDIT_RULE_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #isApply() <em>Apply</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #isApply()
	 * @generated
	 * @ordered
	 */
	protected static final boolean APPLY_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isApply() <em>Apply</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #isApply()
	 * @generated
	 * @ordered
	 */
	protected boolean apply = APPLY_EDEFAULT;

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
	 * The cached value of the '{@link #getOutgoing() <em>Outgoing</em>}' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getOutgoing()
	 * @generated
	 * @ordered
	 */
	protected EList<DependencyContainer> outgoing;

	/**
	 * The cached value of the '{@link #getIncoming() <em>Incoming</em>}' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getIncoming()
	 * @generated
	 * @ordered
	 */
	protected EList<DependencyContainer> incoming;

	/**
	 * The cached value of the '{@link #getChangeSet() <em>Change Set</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getChangeSet()
	 * @generated
	 * @ordered
	 */
	protected SemanticChangeSet changeSet;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected OperationInvocationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AsymmetricPackage.Literals.OPERATION_INVOCATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public String getName() {
		
		// Try to derive the name from the semantic change set:
		if ((name == null) && (getChangeSet() != null)) {
			name = getChangeSet().getName();
		}
		
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AsymmetricPackage.OPERATION_INVOCATION__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public String getEditRuleName() {
		
		// Try to derive the edit rule name from the semantic change set:
		if ((editRuleName == null) && (getChangeSet() != null)) {
			editRuleName = getChangeSet().getEditRName();
		}
		
		return editRuleName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setEditRuleName(String newEditRuleName) {
		String oldEditRuleName = editRuleName;
		editRuleName = newEditRuleName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AsymmetricPackage.OPERATION_INVOCATION__EDIT_RULE_NAME, oldEditRuleName, editRuleName));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ParameterBinding> getParameterBindings() {
		if (parameterBindings == null) {
			parameterBindings = new EObjectContainmentEList<ParameterBinding>(ParameterBinding.class, this, AsymmetricPackage.OPERATION_INVOCATION__PARAMETER_BINDINGS);
		}
		return parameterBindings;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<DependencyContainer> getOutgoing() {
		if (outgoing == null) {
			outgoing = new EObjectWithInverseResolvingEList<DependencyContainer>(DependencyContainer.class, this, AsymmetricPackage.OPERATION_INVOCATION__OUTGOING, AsymmetricPackage.DEPENDENCY_CONTAINER__SOURCE);
		}
		return outgoing;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<DependencyContainer> getIncoming() {
		if (incoming == null) {
			incoming = new EObjectWithInverseResolvingEList<DependencyContainer>(DependencyContainer.class, this, AsymmetricPackage.OPERATION_INVOCATION__INCOMING, AsymmetricPackage.DEPENDENCY_CONTAINER__TARGET);
		}
		return incoming;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isApply() {
		return apply;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setApply(boolean newApply) {
		boolean oldApply = apply;
		apply = newApply;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AsymmetricPackage.OPERATION_INVOCATION__APPLY, oldApply, apply));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SemanticChangeSet getChangeSet() {
		if (changeSet != null && changeSet.eIsProxy()) {
			InternalEObject oldChangeSet = (InternalEObject)changeSet;
			changeSet = (SemanticChangeSet)eResolveProxy(oldChangeSet);
			if (changeSet != oldChangeSet) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, AsymmetricPackage.OPERATION_INVOCATION__CHANGE_SET, oldChangeSet, changeSet));
			}
		}
		return changeSet;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public SemanticChangeSet basicGetChangeSet() {
		return changeSet;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setChangeSet(SemanticChangeSet newChangeSet) {
		SemanticChangeSet oldChangeSet = changeSet;
		changeSet = newChangeSet;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AsymmetricPackage.OPERATION_INVOCATION__CHANGE_SET, oldChangeSet, changeSet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AsymmetricDifference getAsymmetricDifference() {
		if (eContainerFeatureID() != AsymmetricPackage.OPERATION_INVOCATION__ASYMMETRIC_DIFFERENCE) return null;
		return (AsymmetricDifference)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAsymmetricDifference(AsymmetricDifference newAsymmetricDifference, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newAsymmetricDifference, AsymmetricPackage.OPERATION_INVOCATION__ASYMMETRIC_DIFFERENCE, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAsymmetricDifference(AsymmetricDifference newAsymmetricDifference) {
		if (newAsymmetricDifference != eInternalContainer() || (eContainerFeatureID() != AsymmetricPackage.OPERATION_INVOCATION__ASYMMETRIC_DIFFERENCE && newAsymmetricDifference != null)) {
			if (EcoreUtil.isAncestor(this, newAsymmetricDifference))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newAsymmetricDifference != null)
				msgs = ((InternalEObject)newAsymmetricDifference).eInverseAdd(this, AsymmetricPackage.ASYMMETRIC_DIFFERENCE__OPERATION_INVOCATIONS, AsymmetricDifference.class, msgs);
			msgs = basicSetAsymmetricDifference(newAsymmetricDifference, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AsymmetricPackage.OPERATION_INVOCATION__ASYMMETRIC_DIFFERENCE, newAsymmetricDifference, newAsymmetricDifference));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AsymmetricPackage.OPERATION_INVOCATION__OUTGOING:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getOutgoing()).basicAdd(otherEnd, msgs);
			case AsymmetricPackage.OPERATION_INVOCATION__INCOMING:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getIncoming()).basicAdd(otherEnd, msgs);
			case AsymmetricPackage.OPERATION_INVOCATION__ASYMMETRIC_DIFFERENCE:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetAsymmetricDifference((AsymmetricDifference)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AsymmetricPackage.OPERATION_INVOCATION__PARAMETER_BINDINGS:
				return ((InternalEList<?>)getParameterBindings()).basicRemove(otherEnd, msgs);
			case AsymmetricPackage.OPERATION_INVOCATION__OUTGOING:
				return ((InternalEList<?>)getOutgoing()).basicRemove(otherEnd, msgs);
			case AsymmetricPackage.OPERATION_INVOCATION__INCOMING:
				return ((InternalEList<?>)getIncoming()).basicRemove(otherEnd, msgs);
			case AsymmetricPackage.OPERATION_INVOCATION__ASYMMETRIC_DIFFERENCE:
				return basicSetAsymmetricDifference(null, msgs);
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
			case AsymmetricPackage.OPERATION_INVOCATION__ASYMMETRIC_DIFFERENCE:
				return eInternalContainer().eInverseRemove(this, AsymmetricPackage.ASYMMETRIC_DIFFERENCE__OPERATION_INVOCATIONS, AsymmetricDifference.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case AsymmetricPackage.OPERATION_INVOCATION__NAME:
				return getName();
			case AsymmetricPackage.OPERATION_INVOCATION__EDIT_RULE_NAME:
				return getEditRuleName();
			case AsymmetricPackage.OPERATION_INVOCATION__APPLY:
				return isApply();
			case AsymmetricPackage.OPERATION_INVOCATION__PARAMETER_BINDINGS:
				return getParameterBindings();
			case AsymmetricPackage.OPERATION_INVOCATION__OUTGOING:
				return getOutgoing();
			case AsymmetricPackage.OPERATION_INVOCATION__INCOMING:
				return getIncoming();
			case AsymmetricPackage.OPERATION_INVOCATION__CHANGE_SET:
				if (resolve) return getChangeSet();
				return basicGetChangeSet();
			case AsymmetricPackage.OPERATION_INVOCATION__ASYMMETRIC_DIFFERENCE:
				return getAsymmetricDifference();
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
			case AsymmetricPackage.OPERATION_INVOCATION__NAME:
				setName((String)newValue);
				return;
			case AsymmetricPackage.OPERATION_INVOCATION__EDIT_RULE_NAME:
				setEditRuleName((String)newValue);
				return;
			case AsymmetricPackage.OPERATION_INVOCATION__APPLY:
				setApply((Boolean)newValue);
				return;
			case AsymmetricPackage.OPERATION_INVOCATION__PARAMETER_BINDINGS:
				getParameterBindings().clear();
				getParameterBindings().addAll((Collection<? extends ParameterBinding>)newValue);
				return;
			case AsymmetricPackage.OPERATION_INVOCATION__OUTGOING:
				getOutgoing().clear();
				getOutgoing().addAll((Collection<? extends DependencyContainer>)newValue);
				return;
			case AsymmetricPackage.OPERATION_INVOCATION__INCOMING:
				getIncoming().clear();
				getIncoming().addAll((Collection<? extends DependencyContainer>)newValue);
				return;
			case AsymmetricPackage.OPERATION_INVOCATION__CHANGE_SET:
				setChangeSet((SemanticChangeSet)newValue);
				return;
			case AsymmetricPackage.OPERATION_INVOCATION__ASYMMETRIC_DIFFERENCE:
				setAsymmetricDifference((AsymmetricDifference)newValue);
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
			case AsymmetricPackage.OPERATION_INVOCATION__NAME:
				setName(NAME_EDEFAULT);
				return;
			case AsymmetricPackage.OPERATION_INVOCATION__EDIT_RULE_NAME:
				setEditRuleName(EDIT_RULE_NAME_EDEFAULT);
				return;
			case AsymmetricPackage.OPERATION_INVOCATION__APPLY:
				setApply(APPLY_EDEFAULT);
				return;
			case AsymmetricPackage.OPERATION_INVOCATION__PARAMETER_BINDINGS:
				getParameterBindings().clear();
				return;
			case AsymmetricPackage.OPERATION_INVOCATION__OUTGOING:
				getOutgoing().clear();
				return;
			case AsymmetricPackage.OPERATION_INVOCATION__INCOMING:
				getIncoming().clear();
				return;
			case AsymmetricPackage.OPERATION_INVOCATION__CHANGE_SET:
				setChangeSet((SemanticChangeSet)null);
				return;
			case AsymmetricPackage.OPERATION_INVOCATION__ASYMMETRIC_DIFFERENCE:
				setAsymmetricDifference((AsymmetricDifference)null);
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
			case AsymmetricPackage.OPERATION_INVOCATION__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case AsymmetricPackage.OPERATION_INVOCATION__EDIT_RULE_NAME:
				return EDIT_RULE_NAME_EDEFAULT == null ? editRuleName != null : !EDIT_RULE_NAME_EDEFAULT.equals(editRuleName);
			case AsymmetricPackage.OPERATION_INVOCATION__APPLY:
				return apply != APPLY_EDEFAULT;
			case AsymmetricPackage.OPERATION_INVOCATION__PARAMETER_BINDINGS:
				return parameterBindings != null && !parameterBindings.isEmpty();
			case AsymmetricPackage.OPERATION_INVOCATION__OUTGOING:
				return outgoing != null && !outgoing.isEmpty();
			case AsymmetricPackage.OPERATION_INVOCATION__INCOMING:
				return incoming != null && !incoming.isEmpty();
			case AsymmetricPackage.OPERATION_INVOCATION__CHANGE_SET:
				return changeSet != null;
			case AsymmetricPackage.OPERATION_INVOCATION__ASYMMETRIC_DIFFERENCE:
				return getAsymmetricDifference() != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(", editRuleName: ");
		result.append(editRuleName);
		result.append(", apply: ");
		result.append(apply);
		result.append(')');
		return result.toString();
	}

	@Override
	public boolean isComplex() {
		return resolveEditRule().getExecuteModule().eResource().getURI().toString().contains(TransformationConstants.PATH_SEGMENT_COMPLEX_EDITRULES);
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
		List<OperationInvocation> res = new ArrayList<OperationInvocation>();
		addAllPredecessors(res, this);
		return Collections.unmodifiableList(res);
	}

	private void addAllPredecessors(List<OperationInvocation> list, OperationInvocation op) {
		if(!list.contains(op)) {
			list.add(op);
			for (OperationInvocation opPre : op.getPredecessors()) {
				addAllPredecessors(list, opPre);
			}
		}
	}

	@Override
	public List<OperationInvocation> getAllSuccessors() {
		List<OperationInvocation> res = new ArrayList<OperationInvocation>();
		addAllSuccessors(res, this);
		return Collections.unmodifiableList(res);
	}

	private void addAllSuccessors(List<OperationInvocation> list, OperationInvocation op) {
		if(!list.contains(op)) {
			list.add(op);
			for (OperationInvocation opSucc : op.getSuccessors()) {
				addAllPredecessors(list, opSucc);
			}
		}
	}

	@Override
	public List<ParameterBinding> getInParameterBindings() {
		List<ParameterBinding> res = new ArrayList<ParameterBinding>();
		for (ParameterBinding parameterBinding : getParameterBindings()) {
			if (parameterBinding.getFormalParameter().getDirection() == ParameterDirection.IN) {
				res.add(parameterBinding);
			}
		}

		return res;
	}

	@Override
	public List<ParameterBinding> getOutParameterBindings() {
		List<ParameterBinding> res = new ArrayList<ParameterBinding>();
		for (ParameterBinding parameterBinding : getParameterBindings()) {
			if (parameterBinding.getFormalParameter().getDirection() == ParameterDirection.OUT) {
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
		if (diff.getRulebase() != null) {
			RuleBase rb = diff.getRulebase();
			for (EditRule editrule : rb.getEditRules()) {
				if (editrule.getExecuteModule().getName().equals(this.getChangeSet().getEditRName())) {
					this.editRule = editrule;
					return editrule;
				}
			}

		}

		// Try to derive the EditRule via the available rulebases:
		AsymmetricDifference difference = (AsymmetricDifference) this.eContainer();
		List<String> docTypes = EMFDocumentTypeUtil.resolve(difference.getOriginModel());
		 
		this.editRule = IRuleBaseProject.MANAGER.resolveEditRule(new HashSet<String>(docTypes), this.getEditRuleName());

		return this.editRule;
	}

} // OperationInvocationImpl
