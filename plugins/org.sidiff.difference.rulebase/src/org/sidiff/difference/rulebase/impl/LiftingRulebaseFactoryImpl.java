/**
 */
package org.sidiff.difference.rulebase.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.sidiff.difference.rulebase.LiftingRulebaseFactory;
import org.sidiff.difference.rulebase.LiftingRulebasePackage;
import org.sidiff.difference.rulebase.RecognitionRule;
import org.sidiff.difference.rulebase.Trace;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class LiftingRulebaseFactoryImpl extends EFactoryImpl implements LiftingRulebaseFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static LiftingRulebaseFactory init() {
		try {
			LiftingRulebaseFactory theLiftingRulebaseFactory = (LiftingRulebaseFactory)EPackage.Registry.INSTANCE.getEFactory(LiftingRulebasePackage.eNS_URI);
			if (theLiftingRulebaseFactory != null) {
				return theLiftingRulebaseFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new LiftingRulebaseFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LiftingRulebaseFactoryImpl() {
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
			case LiftingRulebasePackage.RECOGNITION_RULE: return createRecognitionRule();
			case LiftingRulebasePackage.TRACE: return createTrace();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RecognitionRule createRecognitionRule() {
		RecognitionRuleImpl recognitionRule = new RecognitionRuleImpl();
		return recognitionRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Trace createTrace() {
		TraceImpl trace = new TraceImpl();
		return trace;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LiftingRulebasePackage getLiftingRulebasePackage() {
		return (LiftingRulebasePackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static LiftingRulebasePackage getPackage() {
		return LiftingRulebasePackage.eINSTANCE;
	}

} //LiftingRulebaseFactoryImpl
