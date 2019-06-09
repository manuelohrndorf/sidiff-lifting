/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.editrule.rulebase.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.sidiff.editrule.rulebase.Classification;
import org.sidiff.editrule.rulebase.EditRule;
import org.sidiff.editrule.rulebase.EditRuleAttachment;
import org.sidiff.editrule.rulebase.Parameter;
import org.sidiff.editrule.rulebase.ParameterDirection;
import org.sidiff.editrule.rulebase.ParameterKind;
import org.sidiff.editrule.rulebase.PotentialAttributeConflict;
import org.sidiff.editrule.rulebase.PotentialAttributeDependency;
import org.sidiff.editrule.rulebase.PotentialConflict;
import org.sidiff.editrule.rulebase.PotentialConflictKind;
import org.sidiff.editrule.rulebase.PotentialDependency;
import org.sidiff.editrule.rulebase.PotentialDependencyKind;
import org.sidiff.editrule.rulebase.PotentialEdgeConflict;
import org.sidiff.editrule.rulebase.PotentialEdgeDependency;
import org.sidiff.editrule.rulebase.PotentialNodeConflict;
import org.sidiff.editrule.rulebase.PotentialNodeDependency;
import org.sidiff.editrule.rulebase.RuleBase;
import org.sidiff.editrule.rulebase.RuleBaseItem;
import org.sidiff.editrule.rulebase.RulebaseFactory;
import org.sidiff.editrule.rulebase.RulebasePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class RulebasePackageImpl extends EPackageImpl implements RulebasePackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ruleBaseEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass editRuleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ruleBaseItemEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass potentialDependencyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass potentialNodeDependencyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass potentialEdgeDependencyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass potentialAttributeDependencyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass parameterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass classificationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass editRuleAttachmentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass potentialConflictEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass potentialNodeConflictEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass potentialEdgeConflictEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass potentialAttributeConflictEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum potentialDependencyKindEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum parameterDirectionEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum parameterKindEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum potentialConflictKindEEnum = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.sidiff.editrule.rulebase.RulebasePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private RulebasePackageImpl() {
		super(eNS_URI, RulebaseFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link RulebasePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static RulebasePackage init() {
		if (isInited) return (RulebasePackage)EPackage.Registry.INSTANCE.getEPackage(RulebasePackage.eNS_URI);

		// Obtain or create and register package
		RulebasePackageImpl theRulebasePackage = (RulebasePackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof RulebasePackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new RulebasePackageImpl());

		isInited = true;

		// Initialize simple dependencies
		HenshinPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theRulebasePackage.createPackageContents();

		// Initialize created meta-data
		theRulebasePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theRulebasePackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(RulebasePackage.eNS_URI, theRulebasePackage);
		return theRulebasePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRuleBase() {
		return ruleBaseEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRuleBase_Name() {
		return (EAttribute)ruleBaseEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRuleBase_DocumentTypes() {
		return (EAttribute)ruleBaseEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRuleBase_PotentialNodeConflicts() {
		return (EReference)ruleBaseEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRuleBase_PotentialEdgeConflicts() {
		return (EReference)ruleBaseEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRuleBase_PotentialAttributeConflicts() {
		return (EReference)ruleBaseEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRuleBase_Items() {
		return (EReference)ruleBaseEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRuleBase_EditRules() {
		return (EReference)ruleBaseEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRuleBase_PotentialNodeDependencies() {
		return (EReference)ruleBaseEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRuleBase_PotentialEdgeDependencies() {
		return (EReference)ruleBaseEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRuleBase_PotentialAttributeDependencies() {
		return (EReference)ruleBaseEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEditRule() {
		return editRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEditRule_RuleBaseItem() {
		return (EReference)editRuleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEditRule_Parameters() {
		return (EReference)editRuleEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEditRule_UseDerivedFeatures() {
		return (EAttribute)editRuleEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEditRule_Inverse() {
		return (EReference)editRuleEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEditRule_Classification() {
		return (EReference)editRuleEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEditRule_ExecuteMainUnit() {
		return (EReference)editRuleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRuleBaseItem() {
		return ruleBaseItemEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRuleBaseItem_EditRule() {
		return (EReference)ruleBaseItemEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRuleBaseItem_RuleBase() {
		return (EReference)ruleBaseItemEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRuleBaseItem_Active() {
		return (EAttribute)ruleBaseItemEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRuleBaseItem_EditRuleAttachments() {
		return (EReference)ruleBaseItemEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPotentialDependency() {
		return potentialDependencyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPotentialDependency_SourceRule() {
		return (EReference)potentialDependencyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPotentialDependency_TargetRule() {
		return (EReference)potentialDependencyEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPotentialDependency_Transient() {
		return (EAttribute)potentialDependencyEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPotentialDependency_Kind() {
		return (EAttribute)potentialDependencyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPotentialNodeDependency() {
		return potentialNodeDependencyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPotentialNodeDependency_RuleBase() {
		return (EReference)potentialNodeDependencyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPotentialNodeDependency_SourceNode() {
		return (EReference)potentialNodeDependencyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPotentialNodeDependency_TargetNode() {
		return (EReference)potentialNodeDependencyEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPotentialEdgeDependency() {
		return potentialEdgeDependencyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPotentialEdgeDependency_RuleBase() {
		return (EReference)potentialEdgeDependencyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPotentialEdgeDependency_SourceEdge() {
		return (EReference)potentialEdgeDependencyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPotentialEdgeDependency_TargetEdge() {
		return (EReference)potentialEdgeDependencyEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPotentialAttributeDependency() {
		return potentialAttributeDependencyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPotentialAttributeDependency_RuleBase() {
		return (EReference)potentialAttributeDependencyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPotentialAttributeDependency_SourceAttribute() {
		return (EReference)potentialAttributeDependencyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPotentialAttributeDependency_TargetAttribute() {
		return (EReference)potentialAttributeDependencyEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPotentialAttributeDependency_SourceNode() {
		return (EReference)potentialAttributeDependencyEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPotentialAttributeDependency_TargetNode() {
		return (EReference)potentialAttributeDependencyEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getParameter() {
		return parameterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getParameter_Name() {
		return (EAttribute)parameterEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getParameter_Direction() {
		return (EAttribute)parameterEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getParameter_Kind() {
		return (EAttribute)parameterEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getParameter_Type() {
		return (EReference)parameterEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getParameter_Multi() {
		return (EAttribute)parameterEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getClassification() {
		return classificationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getClassification_Name() {
		return (EAttribute)classificationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getClassification_ClassificatorID() {
		return (EAttribute)classificationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEditRuleAttachment() {
		return editRuleAttachmentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEditRuleAttachment_RuleBaseItem() {
		return (EReference)editRuleAttachmentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEditRuleAttachment_EditRule() {
		return (EReference)editRuleAttachmentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPotentialConflict() {
		return potentialConflictEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPotentialConflict_PotentialConflictKind() {
		return (EAttribute)potentialConflictEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPotentialConflict_Resolvable() {
		return (EAttribute)potentialConflictEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPotentialConflict_SourceRule() {
		return (EReference)potentialConflictEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPotentialConflict_TargetRule() {
		return (EReference)potentialConflictEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPotentialConflict_ConflictResolution() {
		return (EReference)potentialConflictEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPotentialNodeConflict() {
		return potentialNodeConflictEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPotentialNodeConflict_RuleBase() {
		return (EReference)potentialNodeConflictEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPotentialNodeConflict_SourceNode() {
		return (EReference)potentialNodeConflictEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPotentialNodeConflict_TargetNode() {
		return (EReference)potentialNodeConflictEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPotentialEdgeConflict() {
		return potentialEdgeConflictEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPotentialEdgeConflict_RuleBase() {
		return (EReference)potentialEdgeConflictEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPotentialEdgeConflict_SourceEdge() {
		return (EReference)potentialEdgeConflictEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPotentialEdgeConflict_TargetEdge() {
		return (EReference)potentialEdgeConflictEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPotentialAttributeConflict() {
		return potentialAttributeConflictEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPotentialAttributeConflict_RuleBase() {
		return (EReference)potentialAttributeConflictEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPotentialAttributeConflict_SourceAttribute() {
		return (EReference)potentialAttributeConflictEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPotentialAttributeConflict_TargetAttribute() {
		return (EReference)potentialAttributeConflictEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPotentialAttributeConflict_SourceNode() {
		return (EReference)potentialAttributeConflictEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPotentialAttributeConflict_TargetNode() {
		return (EReference)potentialAttributeConflictEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getPotentialDependencyKind() {
		return potentialDependencyKindEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getParameterDirection() {
		return parameterDirectionEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getParameterKind() {
		return parameterKindEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getPotentialConflictKind() {
		return potentialConflictKindEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RulebaseFactory getRulebaseFactory() {
		return (RulebaseFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		ruleBaseEClass = createEClass(RULE_BASE);
		createEAttribute(ruleBaseEClass, RULE_BASE__NAME);
		createEReference(ruleBaseEClass, RULE_BASE__ITEMS);
		createEReference(ruleBaseEClass, RULE_BASE__EDIT_RULES);
		createEReference(ruleBaseEClass, RULE_BASE__POTENTIAL_NODE_DEPENDENCIES);
		createEReference(ruleBaseEClass, RULE_BASE__POTENTIAL_EDGE_DEPENDENCIES);
		createEReference(ruleBaseEClass, RULE_BASE__POTENTIAL_ATTRIBUTE_DEPENDENCIES);
		createEAttribute(ruleBaseEClass, RULE_BASE__DOCUMENT_TYPES);
		createEReference(ruleBaseEClass, RULE_BASE__POTENTIAL_NODE_CONFLICTS);
		createEReference(ruleBaseEClass, RULE_BASE__POTENTIAL_EDGE_CONFLICTS);
		createEReference(ruleBaseEClass, RULE_BASE__POTENTIAL_ATTRIBUTE_CONFLICTS);

		editRuleEClass = createEClass(EDIT_RULE);
		createEReference(editRuleEClass, EDIT_RULE__EXECUTE_MAIN_UNIT);
		createEReference(editRuleEClass, EDIT_RULE__RULE_BASE_ITEM);
		createEReference(editRuleEClass, EDIT_RULE__PARAMETERS);
		createEAttribute(editRuleEClass, EDIT_RULE__USE_DERIVED_FEATURES);
		createEReference(editRuleEClass, EDIT_RULE__INVERSE);
		createEReference(editRuleEClass, EDIT_RULE__CLASSIFICATION);

		ruleBaseItemEClass = createEClass(RULE_BASE_ITEM);
		createEReference(ruleBaseItemEClass, RULE_BASE_ITEM__EDIT_RULE);
		createEReference(ruleBaseItemEClass, RULE_BASE_ITEM__RULE_BASE);
		createEAttribute(ruleBaseItemEClass, RULE_BASE_ITEM__ACTIVE);
		createEReference(ruleBaseItemEClass, RULE_BASE_ITEM__EDIT_RULE_ATTACHMENTS);

		potentialDependencyEClass = createEClass(POTENTIAL_DEPENDENCY);
		createEAttribute(potentialDependencyEClass, POTENTIAL_DEPENDENCY__KIND);
		createEReference(potentialDependencyEClass, POTENTIAL_DEPENDENCY__SOURCE_RULE);
		createEReference(potentialDependencyEClass, POTENTIAL_DEPENDENCY__TARGET_RULE);
		createEAttribute(potentialDependencyEClass, POTENTIAL_DEPENDENCY__TRANSIENT);

		potentialNodeDependencyEClass = createEClass(POTENTIAL_NODE_DEPENDENCY);
		createEReference(potentialNodeDependencyEClass, POTENTIAL_NODE_DEPENDENCY__RULE_BASE);
		createEReference(potentialNodeDependencyEClass, POTENTIAL_NODE_DEPENDENCY__SOURCE_NODE);
		createEReference(potentialNodeDependencyEClass, POTENTIAL_NODE_DEPENDENCY__TARGET_NODE);

		potentialEdgeDependencyEClass = createEClass(POTENTIAL_EDGE_DEPENDENCY);
		createEReference(potentialEdgeDependencyEClass, POTENTIAL_EDGE_DEPENDENCY__RULE_BASE);
		createEReference(potentialEdgeDependencyEClass, POTENTIAL_EDGE_DEPENDENCY__SOURCE_EDGE);
		createEReference(potentialEdgeDependencyEClass, POTENTIAL_EDGE_DEPENDENCY__TARGET_EDGE);

		potentialAttributeDependencyEClass = createEClass(POTENTIAL_ATTRIBUTE_DEPENDENCY);
		createEReference(potentialAttributeDependencyEClass, POTENTIAL_ATTRIBUTE_DEPENDENCY__RULE_BASE);
		createEReference(potentialAttributeDependencyEClass, POTENTIAL_ATTRIBUTE_DEPENDENCY__SOURCE_ATTRIBUTE);
		createEReference(potentialAttributeDependencyEClass, POTENTIAL_ATTRIBUTE_DEPENDENCY__TARGET_ATTRIBUTE);
		createEReference(potentialAttributeDependencyEClass, POTENTIAL_ATTRIBUTE_DEPENDENCY__SOURCE_NODE);
		createEReference(potentialAttributeDependencyEClass, POTENTIAL_ATTRIBUTE_DEPENDENCY__TARGET_NODE);

		parameterEClass = createEClass(PARAMETER);
		createEAttribute(parameterEClass, PARAMETER__NAME);
		createEAttribute(parameterEClass, PARAMETER__DIRECTION);
		createEAttribute(parameterEClass, PARAMETER__KIND);
		createEReference(parameterEClass, PARAMETER__TYPE);
		createEAttribute(parameterEClass, PARAMETER__MULTI);

		classificationEClass = createEClass(CLASSIFICATION);
		createEAttribute(classificationEClass, CLASSIFICATION__NAME);
		createEAttribute(classificationEClass, CLASSIFICATION__CLASSIFICATOR_ID);

		editRuleAttachmentEClass = createEClass(EDIT_RULE_ATTACHMENT);
		createEReference(editRuleAttachmentEClass, EDIT_RULE_ATTACHMENT__RULE_BASE_ITEM);
		createEReference(editRuleAttachmentEClass, EDIT_RULE_ATTACHMENT__EDIT_RULE);

		potentialConflictEClass = createEClass(POTENTIAL_CONFLICT);
		createEAttribute(potentialConflictEClass, POTENTIAL_CONFLICT__POTENTIAL_CONFLICT_KIND);
		createEAttribute(potentialConflictEClass, POTENTIAL_CONFLICT__RESOLVABLE);
		createEReference(potentialConflictEClass, POTENTIAL_CONFLICT__SOURCE_RULE);
		createEReference(potentialConflictEClass, POTENTIAL_CONFLICT__TARGET_RULE);
		createEReference(potentialConflictEClass, POTENTIAL_CONFLICT__CONFLICT_RESOLUTION);

		potentialNodeConflictEClass = createEClass(POTENTIAL_NODE_CONFLICT);
		createEReference(potentialNodeConflictEClass, POTENTIAL_NODE_CONFLICT__RULE_BASE);
		createEReference(potentialNodeConflictEClass, POTENTIAL_NODE_CONFLICT__SOURCE_NODE);
		createEReference(potentialNodeConflictEClass, POTENTIAL_NODE_CONFLICT__TARGET_NODE);

		potentialEdgeConflictEClass = createEClass(POTENTIAL_EDGE_CONFLICT);
		createEReference(potentialEdgeConflictEClass, POTENTIAL_EDGE_CONFLICT__RULE_BASE);
		createEReference(potentialEdgeConflictEClass, POTENTIAL_EDGE_CONFLICT__SOURCE_EDGE);
		createEReference(potentialEdgeConflictEClass, POTENTIAL_EDGE_CONFLICT__TARGET_EDGE);

		potentialAttributeConflictEClass = createEClass(POTENTIAL_ATTRIBUTE_CONFLICT);
		createEReference(potentialAttributeConflictEClass, POTENTIAL_ATTRIBUTE_CONFLICT__RULE_BASE);
		createEReference(potentialAttributeConflictEClass, POTENTIAL_ATTRIBUTE_CONFLICT__SOURCE_ATTRIBUTE);
		createEReference(potentialAttributeConflictEClass, POTENTIAL_ATTRIBUTE_CONFLICT__TARGET_ATTRIBUTE);
		createEReference(potentialAttributeConflictEClass, POTENTIAL_ATTRIBUTE_CONFLICT__SOURCE_NODE);
		createEReference(potentialAttributeConflictEClass, POTENTIAL_ATTRIBUTE_CONFLICT__TARGET_NODE);

		// Create enums
		potentialDependencyKindEEnum = createEEnum(POTENTIAL_DEPENDENCY_KIND);
		parameterDirectionEEnum = createEEnum(PARAMETER_DIRECTION);
		parameterKindEEnum = createEEnum(PARAMETER_KIND);
		potentialConflictKindEEnum = createEEnum(POTENTIAL_CONFLICT_KIND);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		HenshinPackage theHenshinPackage = (HenshinPackage)EPackage.Registry.INSTANCE.getEPackage(HenshinPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		potentialNodeDependencyEClass.getESuperTypes().add(this.getPotentialDependency());
		potentialEdgeDependencyEClass.getESuperTypes().add(this.getPotentialDependency());
		potentialAttributeDependencyEClass.getESuperTypes().add(this.getPotentialDependency());
		potentialNodeConflictEClass.getESuperTypes().add(this.getPotentialConflict());
		potentialEdgeConflictEClass.getESuperTypes().add(this.getPotentialConflict());
		potentialAttributeConflictEClass.getESuperTypes().add(this.getPotentialConflict());

		// Initialize classes and features; add operations and parameters
		initEClass(ruleBaseEClass, RuleBase.class, "RuleBase", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRuleBase_Name(), ecorePackage.getEString(), "name", null, 0, 1, RuleBase.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRuleBase_Items(), this.getRuleBaseItem(), this.getRuleBaseItem_RuleBase(), "items", null, 0, -1, RuleBase.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRuleBase_EditRules(), this.getEditRule(), null, "editRules", null, 0, -1, RuleBase.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getRuleBase_PotentialNodeDependencies(), this.getPotentialNodeDependency(), this.getPotentialNodeDependency_RuleBase(), "potentialNodeDependencies", null, 0, -1, RuleBase.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRuleBase_PotentialEdgeDependencies(), this.getPotentialEdgeDependency(), this.getPotentialEdgeDependency_RuleBase(), "potentialEdgeDependencies", null, 0, -1, RuleBase.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRuleBase_PotentialAttributeDependencies(), this.getPotentialAttributeDependency(), this.getPotentialAttributeDependency_RuleBase(), "potentialAttributeDependencies", null, 0, -1, RuleBase.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRuleBase_DocumentTypes(), ecorePackage.getEString(), "documentTypes", null, 0, -1, RuleBase.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRuleBase_PotentialNodeConflicts(), this.getPotentialNodeConflict(), this.getPotentialNodeConflict_RuleBase(), "potentialNodeConflicts", null, 0, -1, RuleBase.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRuleBase_PotentialEdgeConflicts(), this.getPotentialEdgeConflict(), this.getPotentialEdgeConflict_RuleBase(), "potentialEdgeConflicts", null, 0, -1, RuleBase.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRuleBase_PotentialAttributeConflicts(), this.getPotentialAttributeConflict(), this.getPotentialAttributeConflict_RuleBase(), "potentialAttributeConflicts", null, 0, -1, RuleBase.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(editRuleEClass, EditRule.class, "EditRule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEditRule_ExecuteMainUnit(), theHenshinPackage.getUnit(), null, "executeMainUnit", null, 0, 1, EditRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEditRule_RuleBaseItem(), this.getRuleBaseItem(), this.getRuleBaseItem_EditRule(), "ruleBaseItem", null, 0, 1, EditRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEditRule_Parameters(), this.getParameter(), null, "parameters", null, 0, -1, EditRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEditRule_UseDerivedFeatures(), ecorePackage.getEBoolean(), "useDerivedFeatures", null, 0, 1, EditRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEditRule_Inverse(), this.getEditRule(), null, "inverse", null, 0, 1, EditRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEditRule_Classification(), this.getClassification(), null, "classification", null, 0, -1, EditRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(editRuleEClass, theHenshinPackage.getModule(), "getExecuteModule", 0, 1, IS_UNIQUE, IS_ORDERED);

		EOperation op = addEOperation(editRuleEClass, this.getParameter(), "getParameterByName", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "name", 1, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(ruleBaseItemEClass, RuleBaseItem.class, "RuleBaseItem", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRuleBaseItem_EditRule(), this.getEditRule(), this.getEditRule_RuleBaseItem(), "editRule", null, 0, 1, RuleBaseItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRuleBaseItem_RuleBase(), this.getRuleBase(), this.getRuleBase_Items(), "ruleBase", null, 0, 1, RuleBaseItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRuleBaseItem_Active(), ecorePackage.getEBoolean(), "active", null, 0, 1, RuleBaseItem.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getRuleBaseItem_EditRuleAttachments(), this.getEditRuleAttachment(), this.getEditRuleAttachment_RuleBaseItem(), "editRuleAttachments", null, 0, -1, RuleBaseItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(potentialDependencyEClass, PotentialDependency.class, "PotentialDependency", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPotentialDependency_Kind(), this.getPotentialDependencyKind(), "kind", null, 1, 1, PotentialDependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPotentialDependency_SourceRule(), this.getEditRule(), null, "sourceRule", null, 1, 1, PotentialDependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPotentialDependency_TargetRule(), this.getEditRule(), null, "targetRule", null, 1, 1, PotentialDependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPotentialDependency_Transient(), ecorePackage.getEBoolean(), "transient", null, 0, 1, PotentialDependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(potentialNodeDependencyEClass, PotentialNodeDependency.class, "PotentialNodeDependency", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPotentialNodeDependency_RuleBase(), this.getRuleBase(), this.getRuleBase_PotentialNodeDependencies(), "ruleBase", null, 0, 1, PotentialNodeDependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPotentialNodeDependency_SourceNode(), theHenshinPackage.getNode(), null, "sourceNode", null, 0, 1, PotentialNodeDependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPotentialNodeDependency_TargetNode(), theHenshinPackage.getNode(), null, "targetNode", null, 0, 1, PotentialNodeDependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(potentialEdgeDependencyEClass, PotentialEdgeDependency.class, "PotentialEdgeDependency", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPotentialEdgeDependency_RuleBase(), this.getRuleBase(), this.getRuleBase_PotentialEdgeDependencies(), "ruleBase", null, 0, 1, PotentialEdgeDependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPotentialEdgeDependency_SourceEdge(), theHenshinPackage.getEdge(), null, "sourceEdge", null, 0, 1, PotentialEdgeDependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPotentialEdgeDependency_TargetEdge(), theHenshinPackage.getEdge(), null, "targetEdge", null, 0, 1, PotentialEdgeDependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(potentialAttributeDependencyEClass, PotentialAttributeDependency.class, "PotentialAttributeDependency", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPotentialAttributeDependency_RuleBase(), this.getRuleBase(), this.getRuleBase_PotentialAttributeDependencies(), "ruleBase", null, 0, 1, PotentialAttributeDependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPotentialAttributeDependency_SourceAttribute(), theHenshinPackage.getAttribute(), null, "sourceAttribute", null, 0, 1, PotentialAttributeDependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPotentialAttributeDependency_TargetAttribute(), theHenshinPackage.getAttribute(), null, "targetAttribute", null, 0, 1, PotentialAttributeDependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPotentialAttributeDependency_SourceNode(), theHenshinPackage.getNode(), null, "sourceNode", null, 0, 1, PotentialAttributeDependency.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getPotentialAttributeDependency_TargetNode(), theHenshinPackage.getNode(), null, "targetNode", null, 0, 1, PotentialAttributeDependency.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(parameterEClass, Parameter.class, "Parameter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getParameter_Name(), ecorePackage.getEString(), "name", null, 1, 1, Parameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getParameter_Direction(), this.getParameterDirection(), "direction", null, 1, 1, Parameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getParameter_Kind(), this.getParameterKind(), "kind", null, 1, 1, Parameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getParameter_Type(), ecorePackage.getEClassifier(), null, "type", null, 1, 1, Parameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getParameter_Multi(), ecorePackage.getEBoolean(), "multi", null, 1, 1, Parameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(classificationEClass, Classification.class, "Classification", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getClassification_Name(), ecorePackage.getEString(), "name", null, 0, 1, Classification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getClassification_ClassificatorID(), ecorePackage.getEInt(), "classificatorID", null, 0, 1, Classification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(editRuleAttachmentEClass, EditRuleAttachment.class, "EditRuleAttachment", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEditRuleAttachment_RuleBaseItem(), this.getRuleBaseItem(), this.getRuleBaseItem_EditRuleAttachments(), "ruleBaseItem", null, 0, 1, EditRuleAttachment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEditRuleAttachment_EditRule(), this.getEditRule(), null, "editRule", null, 0, 1, EditRuleAttachment.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(potentialConflictEClass, PotentialConflict.class, "PotentialConflict", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPotentialConflict_PotentialConflictKind(), this.getPotentialConflictKind(), "potentialConflictKind", null, 1, 1, PotentialConflict.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPotentialConflict_Resolvable(), ecorePackage.getEBoolean(), "resolvable", null, 0, 1, PotentialConflict.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getPotentialConflict_SourceRule(), this.getEditRule(), null, "sourceRule", null, 1, 1, PotentialConflict.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPotentialConflict_TargetRule(), this.getEditRule(), null, "targetRule", null, 1, 1, PotentialConflict.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPotentialConflict_ConflictResolution(), this.getPotentialDependency(), null, "conflictResolution", null, 0, 1, PotentialConflict.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(potentialNodeConflictEClass, PotentialNodeConflict.class, "PotentialNodeConflict", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPotentialNodeConflict_RuleBase(), this.getRuleBase(), this.getRuleBase_PotentialNodeConflicts(), "ruleBase", null, 0, 1, PotentialNodeConflict.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPotentialNodeConflict_SourceNode(), theHenshinPackage.getNode(), null, "sourceNode", null, 1, 1, PotentialNodeConflict.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPotentialNodeConflict_TargetNode(), theHenshinPackage.getNode(), null, "targetNode", null, 1, 1, PotentialNodeConflict.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(potentialEdgeConflictEClass, PotentialEdgeConflict.class, "PotentialEdgeConflict", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPotentialEdgeConflict_RuleBase(), this.getRuleBase(), this.getRuleBase_PotentialEdgeConflicts(), "ruleBase", null, 0, 1, PotentialEdgeConflict.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPotentialEdgeConflict_SourceEdge(), theHenshinPackage.getEdge(), null, "sourceEdge", null, 1, 1, PotentialEdgeConflict.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPotentialEdgeConflict_TargetEdge(), theHenshinPackage.getEdge(), null, "targetEdge", null, 1, 1, PotentialEdgeConflict.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(potentialAttributeConflictEClass, PotentialAttributeConflict.class, "PotentialAttributeConflict", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPotentialAttributeConflict_RuleBase(), this.getRuleBase(), this.getRuleBase_PotentialAttributeConflicts(), "ruleBase", null, 0, 1, PotentialAttributeConflict.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPotentialAttributeConflict_SourceAttribute(), theHenshinPackage.getAttribute(), null, "sourceAttribute", null, 1, 1, PotentialAttributeConflict.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPotentialAttributeConflict_TargetAttribute(), theHenshinPackage.getAttribute(), null, "targetAttribute", null, 1, 1, PotentialAttributeConflict.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPotentialAttributeConflict_SourceNode(), theHenshinPackage.getNode(), null, "sourceNode", null, 1, 1, PotentialAttributeConflict.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getPotentialAttributeConflict_TargetNode(), theHenshinPackage.getNode(), null, "targetNode", null, 1, 1, PotentialAttributeConflict.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(potentialDependencyKindEEnum, PotentialDependencyKind.class, "PotentialDependencyKind");
		addEEnumLiteral(potentialDependencyKindEEnum, PotentialDependencyKind.USE_DELETE);
		addEEnumLiteral(potentialDependencyKindEEnum, PotentialDependencyKind.CREATE_USE);
		addEEnumLiteral(potentialDependencyKindEEnum, PotentialDependencyKind.CHANGE_USE);
		addEEnumLiteral(potentialDependencyKindEEnum, PotentialDependencyKind.USE_CHANGE);
		addEEnumLiteral(potentialDependencyKindEEnum, PotentialDependencyKind.FORBID_CREATE);
		addEEnumLiteral(potentialDependencyKindEEnum, PotentialDependencyKind.DELETE_FORBID);
		addEEnumLiteral(potentialDependencyKindEEnum, PotentialDependencyKind.FORBID_CHANGE);
		addEEnumLiteral(potentialDependencyKindEEnum, PotentialDependencyKind.CHANGE_FORBID);

		initEEnum(parameterDirectionEEnum, ParameterDirection.class, "ParameterDirection");
		addEEnumLiteral(parameterDirectionEEnum, ParameterDirection.IN);
		addEEnumLiteral(parameterDirectionEEnum, ParameterDirection.OUT);

		initEEnum(parameterKindEEnum, ParameterKind.class, "ParameterKind");
		addEEnumLiteral(parameterKindEEnum, ParameterKind.OBJECT);
		addEEnumLiteral(parameterKindEEnum, ParameterKind.VALUE);

		initEEnum(potentialConflictKindEEnum, PotentialConflictKind.class, "PotentialConflictKind");
		addEEnumLiteral(potentialConflictKindEEnum, PotentialConflictKind.DELETE_USE);
		addEEnumLiteral(potentialConflictKindEEnum, PotentialConflictKind.CHANGE_USE);
		addEEnumLiteral(potentialConflictKindEEnum, PotentialConflictKind.CREATE_FORBID);
		addEEnumLiteral(potentialConflictKindEEnum, PotentialConflictKind.CHANGE_FORBID);
		addEEnumLiteral(potentialConflictKindEEnum, PotentialConflictKind.CHANGE_CHANGE);

		// Create resource
		createResource(eNS_URI);
	}

} //RulebasePackageImpl
