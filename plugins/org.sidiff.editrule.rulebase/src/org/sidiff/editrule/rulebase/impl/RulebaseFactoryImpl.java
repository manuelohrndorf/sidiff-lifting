/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.editrule.rulebase.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.sidiff.editrule.rulebase.Classification;
import org.sidiff.editrule.rulebase.EditRule;
import org.sidiff.editrule.rulebase.Parameter;
import org.sidiff.editrule.rulebase.ParameterDirection;
import org.sidiff.editrule.rulebase.ParameterKind;
import org.sidiff.editrule.rulebase.PotentialAttributeDependency;
import org.sidiff.editrule.rulebase.PotentialDependencyKind;
import org.sidiff.editrule.rulebase.PotentialEdgeDependency;
import org.sidiff.editrule.rulebase.PotentialNodeDependency;
import org.sidiff.editrule.rulebase.RuleBase;
import org.sidiff.editrule.rulebase.RuleBaseItem;
import org.sidiff.editrule.rulebase.RulebaseFactory;
import org.sidiff.editrule.rulebase.RulebasePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class RulebaseFactoryImpl extends EFactoryImpl implements RulebaseFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static RulebaseFactory init() {
		try {
			RulebaseFactory theRulebaseFactory = (RulebaseFactory)EPackage.Registry.INSTANCE.getEFactory(RulebasePackage.eNS_URI);
			if (theRulebaseFactory != null) {
				return theRulebaseFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new RulebaseFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RulebaseFactoryImpl() {
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
			case RulebasePackage.RULE_BASE: return createRuleBase();
			case RulebasePackage.EDIT_RULE: return createEditRule();
			case RulebasePackage.RULE_BASE_ITEM: return createRuleBaseItem();
			case RulebasePackage.POTENTIAL_NODE_DEPENDENCY: return createPotentialNodeDependency();
			case RulebasePackage.POTENTIAL_EDGE_DEPENDENCY: return createPotentialEdgeDependency();
			case RulebasePackage.POTENTIAL_ATTRIBUTE_DEPENDENCY: return createPotentialAttributeDependency();
			case RulebasePackage.PARAMETER: return createParameter();
			case RulebasePackage.CLASSIFICATION: return createClassification();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case RulebasePackage.POTENTIAL_DEPENDENCY_KIND:
				return createPotentialDependencyKindFromString(eDataType, initialValue);
			case RulebasePackage.PARAMETER_DIRECTION:
				return createParameterDirectionFromString(eDataType, initialValue);
			case RulebasePackage.PARAMETER_KIND:
				return createParameterKindFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case RulebasePackage.POTENTIAL_DEPENDENCY_KIND:
				return convertPotentialDependencyKindToString(eDataType, instanceValue);
			case RulebasePackage.PARAMETER_DIRECTION:
				return convertParameterDirectionToString(eDataType, instanceValue);
			case RulebasePackage.PARAMETER_KIND:
				return convertParameterKindToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RuleBase createRuleBase() {
		RuleBaseImpl ruleBase = new RuleBaseImpl();
		return ruleBase;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EditRule createEditRule() {
		EditRuleImpl editRule = new EditRuleImpl();
		return editRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RuleBaseItem createRuleBaseItem() {
		RuleBaseItemImpl ruleBaseItem = new RuleBaseItemImpl();
		return ruleBaseItem;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PotentialNodeDependency createPotentialNodeDependency() {
		PotentialNodeDependencyImpl potentialNodeDependency = new PotentialNodeDependencyImpl();
		return potentialNodeDependency;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PotentialEdgeDependency createPotentialEdgeDependency() {
		PotentialEdgeDependencyImpl potentialEdgeDependency = new PotentialEdgeDependencyImpl();
		return potentialEdgeDependency;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PotentialAttributeDependency createPotentialAttributeDependency() {
		PotentialAttributeDependencyImpl potentialAttributeDependency = new PotentialAttributeDependencyImpl();
		return potentialAttributeDependency;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Parameter createParameter() {
		ParameterImpl parameter = new ParameterImpl();
		return parameter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Classification createClassification() {
		ClassificationImpl classification = new ClassificationImpl();
		return classification;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PotentialDependencyKind createPotentialDependencyKindFromString(EDataType eDataType, String initialValue) {
		PotentialDependencyKind result = PotentialDependencyKind.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertPotentialDependencyKindToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ParameterDirection createParameterDirectionFromString(EDataType eDataType, String initialValue) {
		ParameterDirection result = ParameterDirection.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertParameterDirectionToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ParameterKind createParameterKindFromString(EDataType eDataType, String initialValue) {
		ParameterKind result = ParameterKind.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertParameterKindToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RulebasePackage getRulebasePackage() {
		return (RulebasePackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static RulebasePackage getPackage() {
		return RulebasePackage.eINSTANCE;
	}

} //RulebaseFactoryImpl
