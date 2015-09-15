/**
 */
package de.imotep.core.datamodel.de_imotep_core_datamodel.util;

import de.imotep.core.datamodel.de_imotep_core_datamodel.*;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

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
 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.De_imotep_core_datamodelPackage
 * @generated
 */
public class De_imotep_core_datamodelSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static De_imotep_core_datamodelPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public De_imotep_core_datamodelSwitch() {
		if (modelPackage == null) {
			modelPackage = De_imotep_core_datamodelPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param ePackage the package in question.
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
			case De_imotep_core_datamodelPackage.MATTRIBUTE: {
				MAttribute mAttribute = (MAttribute)theEObject;
				T result = caseMAttribute(mAttribute);
				if (result == null) result = caseMNamedEntity(mAttribute);
				if (result == null) result = caseMEntity(mAttribute);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case De_imotep_core_datamodelPackage.MNAMED_ENTITY: {
				MNamedEntity mNamedEntity = (MNamedEntity)theEObject;
				T result = caseMNamedEntity(mNamedEntity);
				if (result == null) result = caseMEntity(mNamedEntity);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case De_imotep_core_datamodelPackage.MINTEGER_ATTRIBUTE: {
				MIntegerAttribute mIntegerAttribute = (MIntegerAttribute)theEObject;
				T result = caseMIntegerAttribute(mIntegerAttribute);
				if (result == null) result = caseMAttribute(mIntegerAttribute);
				if (result == null) result = caseMNamedEntity(mIntegerAttribute);
				if (result == null) result = caseMEntity(mIntegerAttribute);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case De_imotep_core_datamodelPackage.MSTRING_ATTRIBUTE: {
				MStringAttribute mStringAttribute = (MStringAttribute)theEObject;
				T result = caseMStringAttribute(mStringAttribute);
				if (result == null) result = caseMAttribute(mStringAttribute);
				if (result == null) result = caseMNamedEntity(mStringAttribute);
				if (result == null) result = caseMEntity(mStringAttribute);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case De_imotep_core_datamodelPackage.MENTITY: {
				MEntity mEntity = (MEntity)theEObject;
				T result = caseMEntity(mEntity);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case De_imotep_core_datamodelPackage.MRANGED_INTEGER_ATTRIBUTE: {
				MRangedIntegerAttribute mRangedIntegerAttribute = (MRangedIntegerAttribute)theEObject;
				T result = caseMRangedIntegerAttribute(mRangedIntegerAttribute);
				if (result == null) result = caseMIntegerAttribute(mRangedIntegerAttribute);
				if (result == null) result = caseMAttribute(mRangedIntegerAttribute);
				if (result == null) result = caseMNamedEntity(mRangedIntegerAttribute);
				if (result == null) result = caseMEntity(mRangedIntegerAttribute);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case De_imotep_core_datamodelPackage.MBOOLEAN_ATTRIBUTE: {
				MBooleanAttribute mBooleanAttribute = (MBooleanAttribute)theEObject;
				T result = caseMBooleanAttribute(mBooleanAttribute);
				if (result == null) result = caseMAttribute(mBooleanAttribute);
				if (result == null) result = caseMNamedEntity(mBooleanAttribute);
				if (result == null) result = caseMEntity(mBooleanAttribute);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>MAttribute</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>MAttribute</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMAttribute(MAttribute object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>MNamed Entity</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>MNamed Entity</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMNamedEntity(MNamedEntity object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>MInteger Attribute</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>MInteger Attribute</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMIntegerAttribute(MIntegerAttribute object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>MString Attribute</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>MString Attribute</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMStringAttribute(MStringAttribute object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>MEntity</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>MEntity</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMEntity(MEntity object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>MRanged Integer Attribute</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>MRanged Integer Attribute</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMRangedIntegerAttribute(MRangedIntegerAttribute object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>MBoolean Attribute</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>MBoolean Attribute</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMBooleanAttribute(MBooleanAttribute object) {
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

} //De_imotep_core_datamodelSwitch
