/**
 */
package org.sidiff.slicer.rulebased.slice.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.sidiff.slicer.rulebased.slice.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class RuleBasedSliceFactoryImpl extends EFactoryImpl implements RuleBasedSliceFactory {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "(c), Christopher Pietsch, Software Engineering Group, University of Siegen 2017 all rights reserved";

	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static RuleBasedSliceFactory init() {
		try {
			RuleBasedSliceFactory theRuleBasedSliceFactory = (RuleBasedSliceFactory)EPackage.Registry.INSTANCE.getEFactory(RuleBasedSlicePackage.eNS_URI);
			if (theRuleBasedSliceFactory != null) {
				return theRuleBasedSliceFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new RuleBasedSliceFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RuleBasedSliceFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case RuleBasedSlicePackage.EXECUTABLE_MODEL_SLICE: return createExecutableModelSlice();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExecutableModelSlice createExecutableModelSlice() {
		ExecutableModelSliceImpl executableModelSlice = new ExecutableModelSliceImpl();
		return executableModelSlice;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RuleBasedSlicePackage getRuleBasedSlicePackage() {
		return (RuleBasedSlicePackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static RuleBasedSlicePackage getPackage() {
		return RuleBasedSlicePackage.eINSTANCE;
	}

} //RuleBasedSliceFactoryImpl
