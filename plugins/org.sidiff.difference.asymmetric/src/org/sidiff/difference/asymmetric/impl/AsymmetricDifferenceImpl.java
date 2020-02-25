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
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.sidiff.common.emf.modelstorage.SiDiffResourceSet;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.AsymmetricPackage;
import org.sidiff.difference.asymmetric.DependencyContainer;
import org.sidiff.difference.asymmetric.Execution;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.asymmetric.ParameterMapping;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.editrule.rulebase.EditRule;
import org.sidiff.editrule.rulebase.RuleBase;
import org.sidiff.editrule.rulebase.RuleBaseItem;
import org.sidiff.editrule.rulebase.RulebaseFactory;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Difference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.difference.asymmetric.impl.AsymmetricDifferenceImpl#getOperationInvocations <em>Operation Invocations</em>}</li>
 *   <li>{@link org.sidiff.difference.asymmetric.impl.AsymmetricDifferenceImpl#getOriginModel <em>Origin Model</em>}</li>
 *   <li>{@link org.sidiff.difference.asymmetric.impl.AsymmetricDifferenceImpl#getChangedModel <em>Changed Model</em>}</li>
 *   <li>{@link org.sidiff.difference.asymmetric.impl.AsymmetricDifferenceImpl#getDepContainers <em>Dep Containers</em>}</li>
 *   <li>{@link org.sidiff.difference.asymmetric.impl.AsymmetricDifferenceImpl#getParameterMappings <em>Parameter Mappings</em>}</li>
 *   <li>{@link org.sidiff.difference.asymmetric.impl.AsymmetricDifferenceImpl#getExecutions <em>Executions</em>}</li>
 *   <li>{@link org.sidiff.difference.asymmetric.impl.AsymmetricDifferenceImpl#getSymmetricDifference <em>Symmetric Difference</em>}</li>
 *   <li>{@link org.sidiff.difference.asymmetric.impl.AsymmetricDifferenceImpl#getUriOriginModel <em>Uri Origin Model</em>}</li>
 *   <li>{@link org.sidiff.difference.asymmetric.impl.AsymmetricDifferenceImpl#getUriChangedModel <em>Uri Changed Model</em>}</li>
 *   <li>{@link org.sidiff.difference.asymmetric.impl.AsymmetricDifferenceImpl#getRulebase <em>Rulebase</em>}</li>
 * </ul>
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
	 * The cached value of the '{@link #getOriginModel() <em>Origin Model</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOriginModel()
	 * @generated
	 * @ordered
	 */
	protected Resource originModel = ORIGIN_MODEL_EDEFAULT;

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
	 * The cached value of the '{@link #getChangedModel() <em>Changed Model</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChangedModel()
	 * @generated
	 * @ordered
	 */
	protected Resource changedModel = CHANGED_MODEL_EDEFAULT;

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
	 * The default value of the '{@link #getUriOriginModel() <em>Uri Origin Model</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUriOriginModel()
	 * @generated
	 * @ordered
	 */
	protected static final String URI_ORIGIN_MODEL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getUriOriginModel() <em>Uri Origin Model</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUriOriginModel()
	 * @generated
	 * @ordered
	 */
	protected String uriOriginModel = URI_ORIGIN_MODEL_EDEFAULT;

	/**
	 * The default value of the '{@link #getUriChangedModel() <em>Uri Changed Model</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUriChangedModel()
	 * @generated
	 * @ordered
	 */
	protected static final String URI_CHANGED_MODEL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getUriChangedModel() <em>Uri Changed Model</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUriChangedModel()
	 * @generated
	 * @ordered
	 */
	protected String uriChangedModel = URI_CHANGED_MODEL_EDEFAULT;

	/**
	 * The cached value of the '{@link #getRulebase() <em>Rulebase</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRulebase()
	 * @generated
	 * @ordered
	 */
	protected RuleBase rulebase;

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
			operationInvocations = new EObjectContainmentWithInverseEList<OperationInvocation>(OperationInvocation.class, this, AsymmetricPackage.ASYMMETRIC_DIFFERENCE__OPERATION_INVOCATIONS, AsymmetricPackage.OPERATION_INVOCATION__ASYMMETRIC_DIFFERENCE);
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
		
		// Try to derive the model from the symmetric difference: (-> same resource)
		if (originModel == null && getSymmetricDifference() != null) {
			originModel = getSymmetricDifference().getModelA();
		}

		// Load the model from the relative URI: (-> new resource)
		if (originModel == null) {
			URI uri = URI.createURI(getUriOriginModel());
			Resource res = eResource();
			if (uri.isRelative() && res != null) {
				uri = uri.resolve(res.getURI());
			}
			ResourceSet resSet = res == null ? null : res.getResourceSet();
			if(resSet == null) {
				resSet = SiDiffResourceSet.create();
			}
			originModel = resSet.getResource(uri, true);
		}

		return originModel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public Resource getChangedModel() {
		
		// Try to derive the model from the symmetric difference: (-> same resource)
		if (changedModel == null && getSymmetricDifference() != null) {
			changedModel = getSymmetricDifference().getModelB();
		}

		// Load the model from the relative URI: (-> new resource)
		if (changedModel == null) {
			URI uri = URI.createURI(getUriChangedModel());
			Resource res = eResource();
			if (uri.isRelative() && res != null) {
				uri = uri.resolve(res.getURI());
			}
			ResourceSet resSet = res == null ? null : res.getResourceSet();
			if(resSet == null) {
				resSet = SiDiffResourceSet.create();
			}
			changedModel = resSet.getResource(uri, true);
		}

		return changedModel;
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
	 * @generated NOT
	 */
	@Override
	public String getUriOriginModel() {
		
		// Try to derive the URI from the symmetric difference:
		if ((uriOriginModel == null) && (getSymmetricDifference() != null)) {
			uriOriginModel = getSymmetricDifference().getUriModelA();
		}
		
		return uriOriginModel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setUriOriginModel(String newUriOriginModel) {
		String oldUriOriginModel = uriOriginModel;
		uriOriginModel = newUriOriginModel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AsymmetricPackage.ASYMMETRIC_DIFFERENCE__URI_ORIGIN_MODEL, oldUriOriginModel, uriOriginModel));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public String getUriChangedModel() {
		
		// Try to derive the URI from the symmetric difference:
		if ((uriChangedModel == null) && (getSymmetricDifference() != null)) {
			uriChangedModel = getSymmetricDifference().getUriModelB();
		}
		
		return uriChangedModel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setUriChangedModel(String newUriChangedModel) {
		String oldUriChangedModel = uriChangedModel;
		uriChangedModel = newUriChangedModel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AsymmetricPackage.ASYMMETRIC_DIFFERENCE__URI_CHANGED_MODEL, oldUriChangedModel, uriChangedModel));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public RuleBase getRulebase() {
		return rulebase;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRulebase(RuleBase newRulebase, NotificationChain msgs) {
		RuleBase oldRulebase = rulebase;
		rulebase = newRulebase;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, AsymmetricPackage.ASYMMETRIC_DIFFERENCE__RULEBASE, oldRulebase, newRulebase);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRulebase(RuleBase newRulebase) {
		if (newRulebase != rulebase) {
			NotificationChain msgs = null;
			if (rulebase != null)
				msgs = ((InternalEObject)rulebase).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - AsymmetricPackage.ASYMMETRIC_DIFFERENCE__RULEBASE, null, msgs);
			if (newRulebase != null)
				msgs = ((InternalEObject)newRulebase).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - AsymmetricPackage.ASYMMETRIC_DIFFERENCE__RULEBASE, null, msgs);
			msgs = basicSetRulebase(newRulebase, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AsymmetricPackage.ASYMMETRIC_DIFFERENCE__RULEBASE, newRulebase, newRulebase));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public void initializeRuleBase() {
		assert (getRulebase() == null) : "ruleBases have already been initialized!";

		RuleBase rulebase = RulebaseFactory.eINSTANCE.createRuleBase();
		HashMap<String, RuleBaseItem> ruleBaseItems = new HashMap<String, RuleBaseItem>();

		for (OperationInvocation op : this.getOperationInvocations()) {
			ruleBaseItems.put(
					op.resolveEditRule().getExecuteModule().getName(), 
					op.resolveEditRule().getRuleBaseItem());
		}

		for (String ri : ruleBaseItems.keySet()) {
			RuleBaseItem item = RulebaseFactory.eINSTANCE.createRuleBaseItem();
			EObject o = EcoreUtil.copy(ruleBaseItems.get(ri).getEditRule());
			item.setEditRule((EditRule) o);
			rulebase.getItems().add(item);
		}

		setRulebase(rulebase);
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
			case AsymmetricPackage.ASYMMETRIC_DIFFERENCE__OPERATION_INVOCATIONS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getOperationInvocations()).basicAdd(otherEnd, msgs);
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
			case AsymmetricPackage.ASYMMETRIC_DIFFERENCE__OPERATION_INVOCATIONS:
				return ((InternalEList<?>)getOperationInvocations()).basicRemove(otherEnd, msgs);
			case AsymmetricPackage.ASYMMETRIC_DIFFERENCE__DEP_CONTAINERS:
				return ((InternalEList<?>)getDepContainers()).basicRemove(otherEnd, msgs);
			case AsymmetricPackage.ASYMMETRIC_DIFFERENCE__PARAMETER_MAPPINGS:
				return ((InternalEList<?>)getParameterMappings()).basicRemove(otherEnd, msgs);
			case AsymmetricPackage.ASYMMETRIC_DIFFERENCE__EXECUTIONS:
				return ((InternalEList<?>)getExecutions()).basicRemove(otherEnd, msgs);
			case AsymmetricPackage.ASYMMETRIC_DIFFERENCE__RULEBASE:
				return basicSetRulebase(null, msgs);
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
			case AsymmetricPackage.ASYMMETRIC_DIFFERENCE__URI_ORIGIN_MODEL:
				return getUriOriginModel();
			case AsymmetricPackage.ASYMMETRIC_DIFFERENCE__URI_CHANGED_MODEL:
				return getUriChangedModel();
			case AsymmetricPackage.ASYMMETRIC_DIFFERENCE__RULEBASE:
				return getRulebase();
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
			case AsymmetricPackage.ASYMMETRIC_DIFFERENCE__URI_ORIGIN_MODEL:
				setUriOriginModel((String)newValue);
				return;
			case AsymmetricPackage.ASYMMETRIC_DIFFERENCE__URI_CHANGED_MODEL:
				setUriChangedModel((String)newValue);
				return;
			case AsymmetricPackage.ASYMMETRIC_DIFFERENCE__RULEBASE:
				setRulebase((RuleBase)newValue);
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
			case AsymmetricPackage.ASYMMETRIC_DIFFERENCE__URI_ORIGIN_MODEL:
				setUriOriginModel(URI_ORIGIN_MODEL_EDEFAULT);
				return;
			case AsymmetricPackage.ASYMMETRIC_DIFFERENCE__URI_CHANGED_MODEL:
				setUriChangedModel(URI_CHANGED_MODEL_EDEFAULT);
				return;
			case AsymmetricPackage.ASYMMETRIC_DIFFERENCE__RULEBASE:
				setRulebase((RuleBase)null);
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
				return ORIGIN_MODEL_EDEFAULT == null ? originModel != null : !ORIGIN_MODEL_EDEFAULT.equals(originModel);
			case AsymmetricPackage.ASYMMETRIC_DIFFERENCE__CHANGED_MODEL:
				return CHANGED_MODEL_EDEFAULT == null ? changedModel != null : !CHANGED_MODEL_EDEFAULT.equals(changedModel);
			case AsymmetricPackage.ASYMMETRIC_DIFFERENCE__DEP_CONTAINERS:
				return depContainers != null && !depContainers.isEmpty();
			case AsymmetricPackage.ASYMMETRIC_DIFFERENCE__PARAMETER_MAPPINGS:
				return parameterMappings != null && !parameterMappings.isEmpty();
			case AsymmetricPackage.ASYMMETRIC_DIFFERENCE__EXECUTIONS:
				return executions != null && !executions.isEmpty();
			case AsymmetricPackage.ASYMMETRIC_DIFFERENCE__SYMMETRIC_DIFFERENCE:
				return symmetricDifference != null;
			case AsymmetricPackage.ASYMMETRIC_DIFFERENCE__URI_ORIGIN_MODEL:
				return URI_ORIGIN_MODEL_EDEFAULT == null ? uriOriginModel != null : !URI_ORIGIN_MODEL_EDEFAULT.equals(uriOriginModel);
			case AsymmetricPackage.ASYMMETRIC_DIFFERENCE__URI_CHANGED_MODEL:
				return URI_CHANGED_MODEL_EDEFAULT == null ? uriChangedModel != null : !URI_CHANGED_MODEL_EDEFAULT.equals(uriChangedModel);
			case AsymmetricPackage.ASYMMETRIC_DIFFERENCE__RULEBASE:
				return rulebase != null;
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
		result.append(" (originModel: ");
		result.append(originModel);
		result.append(", changedModel: ");
		result.append(changedModel);
		result.append(", uriOriginModel: ");
		result.append(uriOriginModel);
		result.append(", uriChangedModel: ");
		result.append(uriChangedModel);
		result.append(')');
		return result.toString();
	}

} //AsymmetricDifferenceImpl
