/**
 */
package org.sidiff.difference.rulebase.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.henshin.model.Node;
import org.sidiff.difference.rulebase.LiftingRulebasePackage;
import org.sidiff.difference.rulebase.Trace;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Trace</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.difference.rulebase.impl.TraceImpl#getEditRuleTrace <em>Edit Rule Trace</em>}</li>
 *   <li>{@link org.sidiff.difference.rulebase.impl.TraceImpl#getRecognitionRuleTrace <em>Recognition Rule Trace</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TraceImpl extends EObjectImpl implements Trace {
	/**
	 * The cached value of the '{@link #getEditRuleTrace() <em>Edit Rule Trace</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEditRuleTrace()
	 * @generated
	 * @ordered
	 */
	protected Node editRuleTrace;

	/**
	 * The cached value of the '{@link #getRecognitionRuleTrace() <em>Recognition Rule Trace</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRecognitionRuleTrace()
	 * @generated
	 * @ordered
	 */
	protected Node recognitionRuleTrace;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TraceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LiftingRulebasePackage.Literals.TRACE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Node getEditRuleTrace() {
		if (editRuleTrace != null && editRuleTrace.eIsProxy()) {
			InternalEObject oldEditRuleTrace = (InternalEObject)editRuleTrace;
			editRuleTrace = (Node)eResolveProxy(oldEditRuleTrace);
			if (editRuleTrace != oldEditRuleTrace) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, LiftingRulebasePackage.TRACE__EDIT_RULE_TRACE, oldEditRuleTrace, editRuleTrace));
			}
		}
		return editRuleTrace;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Node basicGetEditRuleTrace() {
		return editRuleTrace;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEditRuleTrace(Node newEditRuleTrace) {
		Node oldEditRuleTrace = editRuleTrace;
		editRuleTrace = newEditRuleTrace;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LiftingRulebasePackage.TRACE__EDIT_RULE_TRACE, oldEditRuleTrace, editRuleTrace));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Node getRecognitionRuleTrace() {
		if (recognitionRuleTrace != null && recognitionRuleTrace.eIsProxy()) {
			InternalEObject oldRecognitionRuleTrace = (InternalEObject)recognitionRuleTrace;
			recognitionRuleTrace = (Node)eResolveProxy(oldRecognitionRuleTrace);
			if (recognitionRuleTrace != oldRecognitionRuleTrace) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, LiftingRulebasePackage.TRACE__RECOGNITION_RULE_TRACE, oldRecognitionRuleTrace, recognitionRuleTrace));
			}
		}
		return recognitionRuleTrace;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Node basicGetRecognitionRuleTrace() {
		return recognitionRuleTrace;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRecognitionRuleTrace(Node newRecognitionRuleTrace) {
		Node oldRecognitionRuleTrace = recognitionRuleTrace;
		recognitionRuleTrace = newRecognitionRuleTrace;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LiftingRulebasePackage.TRACE__RECOGNITION_RULE_TRACE, oldRecognitionRuleTrace, recognitionRuleTrace));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case LiftingRulebasePackage.TRACE__EDIT_RULE_TRACE:
				if (resolve) return getEditRuleTrace();
				return basicGetEditRuleTrace();
			case LiftingRulebasePackage.TRACE__RECOGNITION_RULE_TRACE:
				if (resolve) return getRecognitionRuleTrace();
				return basicGetRecognitionRuleTrace();
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
			case LiftingRulebasePackage.TRACE__EDIT_RULE_TRACE:
				setEditRuleTrace((Node)newValue);
				return;
			case LiftingRulebasePackage.TRACE__RECOGNITION_RULE_TRACE:
				setRecognitionRuleTrace((Node)newValue);
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
			case LiftingRulebasePackage.TRACE__EDIT_RULE_TRACE:
				setEditRuleTrace((Node)null);
				return;
			case LiftingRulebasePackage.TRACE__RECOGNITION_RULE_TRACE:
				setRecognitionRuleTrace((Node)null);
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
			case LiftingRulebasePackage.TRACE__EDIT_RULE_TRACE:
				return editRuleTrace != null;
			case LiftingRulebasePackage.TRACE__RECOGNITION_RULE_TRACE:
				return recognitionRuleTrace != null;
		}
		return super.eIsSet(featureID);
	}

} //TraceImpl
