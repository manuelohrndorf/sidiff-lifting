/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.difference.asymmetric.impl;

import java.util.Collection;
import java.util.HashMap;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.AsymmetricPackage;
import org.sidiff.difference.asymmetric.DependencyContainer;
import org.sidiff.difference.asymmetric.Execution;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.asymmetric.ParameterMapping;
import org.sidiff.difference.rulebase.EditRule;
import org.sidiff.difference.rulebase.RuleBase;
import org.sidiff.difference.rulebase.RuleBaseItem;
import org.sidiff.difference.rulebase.RulebaseFactory;
import org.sidiff.difference.symmetric.SymmetricDifference;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Difference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.sidiff.difference.asymmetric.impl.AsymmetricDifferenceImpl#getOperationInvocations <em>Operation Invocations</em>}</li>
 *   <li>{@link org.sidiff.difference.asymmetric.impl.AsymmetricDifferenceImpl#getOriginModel <em>Origin Model</em>}</li>
 *   <li>{@link org.sidiff.difference.asymmetric.impl.AsymmetricDifferenceImpl#getChangedModel <em>Changed Model</em>}</li>
 *   <li>{@link org.sidiff.difference.asymmetric.impl.AsymmetricDifferenceImpl#getDepContainers <em>Dep Containers</em>}</li>
 *   <li>{@link org.sidiff.difference.asymmetric.impl.AsymmetricDifferenceImpl#getParameterMappings <em>Parameter Mappings</em>}</li>
 *   <li>{@link org.sidiff.difference.asymmetric.impl.AsymmetricDifferenceImpl#getExecutions <em>Executions</em>}</li>
 *   <li>{@link org.sidiff.difference.asymmetric.impl.AsymmetricDifferenceImpl#getSymmetricDifference <em>Symmetric Difference</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AsymmetricDifferenceImpl extends EObjectImpl implements AsymmetricDifference {
	/**
	 * The cached value of the '{@link #getOperationInvocations() <em>Operation Invocations</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOperationInvocations()
	 * @generated
	 * @ordered
	 */
	protected EList<OperationInvocation> operationInvocations;

	/**
	 * The default value of the '{@link #getOriginModel() <em>Origin Model</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOriginModel()
	 * @generated
	 * @ordered
	 */
	protected static final Resource ORIGIN_MODEL_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getChangedModel() <em>Changed Model</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChangedModel()
	 * @generated
	 * @ordered
	 */
	protected static final Resource CHANGED_MODEL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDepContainers() <em>Dep Containers</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDepContainers()
	 * @generated
	 * @ordered
	 */
	protected EList<DependencyContainer> depContainers;

	/**
	 * The cached value of the '{@link #getParameterMappings() <em>Parameter Mappings</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameterMappings()
	 * @generated
	 * @ordered
	 */
	protected EList<ParameterMapping> parameterMappings;

	/**
	 * The cached value of the '{@link #getExecutions() <em>Executions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExecutions()
	 * @generated
	 * @ordered
	 */
	protected EList<Execution> executions;

	/**
	 * The cached value of the '{@link #getSymmetricDifference() <em>Symmetric Difference</em>}' reference.
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
	protected AsymmetricDifferenceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AsymmetricPackage.Literals.ASYMMETRIC_DIFFERENCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<OperationInvocation> getOperationInvocations() {
		if (operationInvocations == null) {
			operationInvocations = new EObjectContainmentEList<OperationInvocation>(OperationInvocation.class, this, AsymmetricPackage.ASYMMETRIC_DIFFERENCE__OPERATION_INVOCATIONS);
		}
		return operationInvocations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public Resource getOriginModel() {
		return getSymmetricDifference().getModelA();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public Resource getChangedModel() {
		return getSymmetricDifference().getModelB();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<DependencyContainer> getDepContainers() {
		if (depContainers == null) {
			depContainers = new EObjectContainmentEList<DependencyContainer>(DependencyContainer.class, this, AsymmetricPackage.ASYMMETRIC_DIFFERENCE__DEP_CONTAINERS);
		}
		return depContainers;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ParameterMapping> getParameterMappings() {
		if (parameterMappings == null) {
			parameterMappings = new EObjectContainmentEList<ParameterMapping>(ParameterMapping.class, this, AsymmetricPackage.ASYMMETRIC_DIFFERENCE__PARAMETER_MAPPINGS);
		}
		return parameterMappings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Execution> getExecutions() {
		if (executions == null) {
			executions = new EObjectContainmentEList<Execution>(Execution.class, this, AsymmetricPackage.ASYMMETRIC_DIFFERENCE__EXECUTIONS);
		}
		return executions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SymmetricDifference getSymmetricDifference() {
		if (symmetricDifference != null && symmetricDifference.eIsProxy()) {
			InternalEObject oldSymmetricDifference = (InternalEObject)symmetricDifference;
			symmetricDifference = (SymmetricDifference)eResolveProxy(oldSymmetricDifference);
			if (symmetricDifference != oldSymmetricDifference) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, AsymmetricPackage.ASYMMETRIC_DIFFERENCE__SYMMETRIC_DIFFERENCE, oldSymmetricDifference, symmetricDifference));
			}
		}
		return symmetricDifference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SymmetricDifference basicGetSymmetricDifference() {
		return symmetricDifference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSymmetricDifference(SymmetricDifference newSymmetricDifference) {
		SymmetricDifference oldSymmetricDifference = symmetricDifference;
		symmetricDifference = newSymmetricDifference;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AsymmetricPackage.ASYMMETRIC_DIFFERENCE__SYMMETRIC_DIFFERENCE, oldSymmetricDifference, symmetricDifference));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AsymmetricPackage.ASYMMETRIC_DIFFERENCE__OPERATION_INVOCATIONS:
				return ((InternalEList<?>)getOperationInvocations()).basicRemove(otherEnd, msgs);
			case AsymmetricPackage.ASYMMETRIC_DIFFERENCE__DEP_CONTAINERS:
				return ((InternalEList<?>)getDepContainers()).basicRemove(otherEnd, msgs);
			case AsymmetricPackage.ASYMMETRIC_DIFFERENCE__PARAMETER_MAPPINGS:
				return ((InternalEList<?>)getParameterMappings()).basicRemove(otherEnd, msgs);
			case AsymmetricPackage.ASYMMETRIC_DIFFERENCE__EXECUTIONS:
				return ((InternalEList<?>)getExecutions()).basicRemove(otherEnd, msgs);
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
			case AsymmetricPackage.ASYMMETRIC_DIFFERENCE__OPERATION_INVOCATIONS:
				return getOperationInvocations();
			case AsymmetricPackage.ASYMMETRIC_DIFFERENCE__ORIGIN_MODEL:
				return getOriginModel();
			case AsymmetricPackage.ASYMMETRIC_DIFFERENCE__CHANGED_MODEL:
				return getChangedModel();
			case AsymmetricPackage.ASYMMETRIC_DIFFERENCE__DEP_CONTAINERS:
				return getDepContainers();
			case AsymmetricPackage.ASYMMETRIC_DIFFERENCE__PARAMETER_MAPPINGS:
				return getParameterMappings();
			case AsymmetricPackage.ASYMMETRIC_DIFFERENCE__EXECUTIONS:
				return getExecutions();
			case AsymmetricPackage.ASYMMETRIC_DIFFERENCE__SYMMETRIC_DIFFERENCE:
				if (resolve) return getSymmetricDifference();
				return basicGetSymmetricDifference();
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
			case AsymmetricPackage.ASYMMETRIC_DIFFERENCE__OPERATION_INVOCATIONS:
				getOperationInvocations().clear();
				getOperationInvocations().addAll((Collection<? extends OperationInvocation>)newValue);
				return;
			case AsymmetricPackage.ASYMMETRIC_DIFFERENCE__DEP_CONTAINERS:
				getDepContainers().clear();
				getDepContainers().addAll((Collection<? extends DependencyContainer>)newValue);
				return;
			case AsymmetricPackage.ASYMMETRIC_DIFFERENCE__PARAMETER_MAPPINGS:
				getParameterMappings().clear();
				getParameterMappings().addAll((Collection<? extends ParameterMapping>)newValue);
				return;
			case AsymmetricPackage.ASYMMETRIC_DIFFERENCE__EXECUTIONS:
				getExecutions().clear();
				getExecutions().addAll((Collection<? extends Execution>)newValue);
				return;
			case AsymmetricPackage.ASYMMETRIC_DIFFERENCE__SYMMETRIC_DIFFERENCE:
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
			case AsymmetricPackage.ASYMMETRIC_DIFFERENCE__OPERATION_INVOCATIONS:
				getOperationInvocations().clear();
				return;
			case AsymmetricPackage.ASYMMETRIC_DIFFERENCE__DEP_CONTAINERS:
				getDepContainers().clear();
				return;
			case AsymmetricPackage.ASYMMETRIC_DIFFERENCE__PARAMETER_MAPPINGS:
				getParameterMappings().clear();
				return;
			case AsymmetricPackage.ASYMMETRIC_DIFFERENCE__EXECUTIONS:
				getExecutions().clear();
				return;
			case AsymmetricPackage.ASYMMETRIC_DIFFERENCE__SYMMETRIC_DIFFERENCE:
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
			case AsymmetricPackage.ASYMMETRIC_DIFFERENCE__OPERATION_INVOCATIONS:
				return operationInvocations != null && !operationInvocations.isEmpty();
			case AsymmetricPackage.ASYMMETRIC_DIFFERENCE__ORIGIN_MODEL:
				return ORIGIN_MODEL_EDEFAULT == null ? getOriginModel() != null : !ORIGIN_MODEL_EDEFAULT.equals(getOriginModel());
			case AsymmetricPackage.ASYMMETRIC_DIFFERENCE__CHANGED_MODEL:
				return CHANGED_MODEL_EDEFAULT == null ? getChangedModel() != null : !CHANGED_MODEL_EDEFAULT.equals(getChangedModel());
			case AsymmetricPackage.ASYMMETRIC_DIFFERENCE__DEP_CONTAINERS:
				return depContainers != null && !depContainers.isEmpty();
			case AsymmetricPackage.ASYMMETRIC_DIFFERENCE__PARAMETER_MAPPINGS:
				return parameterMappings != null && !parameterMappings.isEmpty();
			case AsymmetricPackage.ASYMMETRIC_DIFFERENCE__EXECUTIONS:
				return executions != null && !executions.isEmpty();
			case AsymmetricPackage.ASYMMETRIC_DIFFERENCE__SYMMETRIC_DIFFERENCE:
				return symmetricDifference != null;
		}
		return super.eIsSet(featureID);
	}
	
	private RuleBase transientRulebase;
	
	@Override
	public void initTransientRulebase() {
		assert(transientRulebase == null) : "ruleBases have already been initialized!";
		
		transientRulebase = RulebaseFactory.eINSTANCE.createRuleBase();
		HashMap<String, RuleBaseItem> ruleBaseItems = new HashMap<String, RuleBaseItem>();
		for(OperationInvocation op: this.getOperationInvocations()){
			ruleBaseItems.put(op.resolveEditRule().getExecuteModule().getName(), op.resolveEditRule().getRuleBaseItem());
		}
		for(String ri : ruleBaseItems.keySet()){
			RuleBaseItem item = RulebaseFactory.eINSTANCE.createRuleBaseItem();
			EObject o = EcoreUtil.copy(ruleBaseItems.get(ri).getEditRule());
			item.setEditRule((EditRule)o);
			transientRulebase.getItems().add(item);
		}
	}
	
	@Override
	public RuleBase getTransientRulebase() {
		return transientRulebase;
	}

} //AsymmetricDifferenceImpl
