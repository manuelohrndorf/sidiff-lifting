/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.difference.rulebase.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;
import org.sidiff.difference.rulebase.*;
import org.sidiff.difference.rulebase.EditRule;
import org.sidiff.difference.rulebase.Parameter;
import org.sidiff.difference.rulebase.PotentialAttributeDependency;
import org.sidiff.difference.rulebase.PotentialDependency;
import org.sidiff.difference.rulebase.PotentialEdgeDependency;
import org.sidiff.difference.rulebase.PotentialNodeDependency;
import org.sidiff.difference.rulebase.RecognitionRule;
import org.sidiff.difference.rulebase.RuleBase;
import org.sidiff.difference.rulebase.RuleBaseItem;
import org.sidiff.difference.rulebase.RulebasePackage;
import org.sidiff.difference.rulebase.Trace;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.sidiff.difference.rulebase.RulebasePackage
 * @generated
 */
public class RulebaseSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static RulebasePackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RulebaseSwitch() {
		if (modelPackage == null) {
			modelPackage = RulebasePackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @parameter ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case RulebasePackage.RULE_BASE: {
				RuleBase ruleBase = (RuleBase)theEObject;
				T result = caseRuleBase(ruleBase);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RulebasePackage.EDIT_RULE: {
				EditRule editRule = (EditRule)theEObject;
				T result = caseEditRule(editRule);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RulebasePackage.RECOGNITION_RULE: {
				RecognitionRule recognitionRule = (RecognitionRule)theEObject;
				T result = caseRecognitionRule(recognitionRule);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RulebasePackage.TRACE: {
				Trace trace = (Trace)theEObject;
				T result = caseTrace(trace);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RulebasePackage.RULE_BASE_ITEM: {
				RuleBaseItem ruleBaseItem = (RuleBaseItem)theEObject;
				T result = caseRuleBaseItem(ruleBaseItem);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RulebasePackage.POTENTIAL_DEPENDENCY: {
				PotentialDependency potentialDependency = (PotentialDependency)theEObject;
				T result = casePotentialDependency(potentialDependency);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RulebasePackage.POTENTIAL_NODE_DEPENDENCY: {
				PotentialNodeDependency potentialNodeDependency = (PotentialNodeDependency)theEObject;
				T result = casePotentialNodeDependency(potentialNodeDependency);
				if (result == null) result = casePotentialDependency(potentialNodeDependency);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RulebasePackage.POTENTIAL_EDGE_DEPENDENCY: {
				PotentialEdgeDependency potentialEdgeDependency = (PotentialEdgeDependency)theEObject;
				T result = casePotentialEdgeDependency(potentialEdgeDependency);
				if (result == null) result = casePotentialDependency(potentialEdgeDependency);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RulebasePackage.POTENTIAL_ATTRIBUTE_DEPENDENCY: {
				PotentialAttributeDependency potentialAttributeDependency = (PotentialAttributeDependency)theEObject;
				T result = casePotentialAttributeDependency(potentialAttributeDependency);
				if (result == null) result = casePotentialDependency(potentialAttributeDependency);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RulebasePackage.PARAMETER: {
				Parameter parameter = (Parameter)theEObject;
				T result = caseParameter(parameter);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Rule Base</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Rule Base</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRuleBase(RuleBase object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Edit Rule</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Edit Rule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEditRule(EditRule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Recognition Rule</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Recognition Rule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRecognitionRule(RecognitionRule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Trace</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Trace</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTrace(Trace object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Rule Base Item</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Rule Base Item</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRuleBaseItem(RuleBaseItem object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Potential Dependency</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Potential Dependency</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePotentialDependency(PotentialDependency object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Potential Node Dependency</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Potential Node Dependency</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePotentialNodeDependency(PotentialNodeDependency object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Potential Edge Dependency</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Potential Edge Dependency</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePotentialEdgeDependency(PotentialEdgeDependency object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Potential Attribute Dependency</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Potential Attribute Dependency</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePotentialAttributeDependency(PotentialAttributeDependency object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Parameter</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Parameter</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseParameter(Parameter object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //RulebaseSwitch
