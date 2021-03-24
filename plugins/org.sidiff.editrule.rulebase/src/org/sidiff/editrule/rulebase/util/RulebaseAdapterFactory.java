/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.editrule.rulebase.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;
import org.sidiff.editrule.rulebase.*;
import org.sidiff.editrule.rulebase.Classification;
import org.sidiff.editrule.rulebase.EditRule;
import org.sidiff.editrule.rulebase.EditRuleAttachment;
import org.sidiff.editrule.rulebase.Parameter;
import org.sidiff.editrule.rulebase.PotentialAttributeDependency;
import org.sidiff.editrule.rulebase.PotentialDependency;
import org.sidiff.editrule.rulebase.PotentialEdgeDependency;
import org.sidiff.editrule.rulebase.PotentialNodeDependency;
import org.sidiff.editrule.rulebase.RuleBase;
import org.sidiff.editrule.rulebase.RuleBaseItem;
import org.sidiff.editrule.rulebase.RulebasePackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.sidiff.editrule.rulebase.RulebasePackage
 * @generated
 */
public class RulebaseAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static RulebasePackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RulebaseAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = RulebasePackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RulebaseSwitch<Adapter> modelSwitch =
		new RulebaseSwitch<Adapter>() {
			@Override
			public Adapter caseRuleBase(RuleBase object) {
				return createRuleBaseAdapter();
			}
			@Override
			public Adapter caseEditRule(EditRule object) {
				return createEditRuleAdapter();
			}
			@Override
			public Adapter caseRuleBaseItem(RuleBaseItem object) {
				return createRuleBaseItemAdapter();
			}
			@Override
			public Adapter casePotentialDependency(PotentialDependency object) {
				return createPotentialDependencyAdapter();
			}
			@Override
			public Adapter casePotentialNodeDependency(PotentialNodeDependency object) {
				return createPotentialNodeDependencyAdapter();
			}
			@Override
			public Adapter casePotentialEdgeDependency(PotentialEdgeDependency object) {
				return createPotentialEdgeDependencyAdapter();
			}
			@Override
			public Adapter casePotentialAttributeDependency(PotentialAttributeDependency object) {
				return createPotentialAttributeDependencyAdapter();
			}
			@Override
			public Adapter caseParameter(Parameter object) {
				return createParameterAdapter();
			}
			@Override
			public Adapter caseClassification(Classification object) {
				return createClassificationAdapter();
			}
			@Override
			public Adapter caseEditRuleAttachment(EditRuleAttachment object) {
				return createEditRuleAttachmentAdapter();
			}
			@Override
			public Adapter casePotentialConflict(PotentialConflict object) {
				return createPotentialConflictAdapter();
			}
			@Override
			public Adapter casePotentialNodeConflict(PotentialNodeConflict object) {
				return createPotentialNodeConflictAdapter();
			}
			@Override
			public Adapter casePotentialEdgeConflict(PotentialEdgeConflict object) {
				return createPotentialEdgeConflictAdapter();
			}
			@Override
			public Adapter casePotentialAttributeConflict(PotentialAttributeConflict object) {
				return createPotentialAttributeConflictAdapter();
			}
			@Override
			public Adapter casePotentialDanglingEdgeConflict(PotentialDanglingEdgeConflict object) {
				return createPotentialDanglingEdgeConflictAdapter();
			}
			@Override
			public Adapter casePotentialDanglingEdgeDependency(PotentialDanglingEdgeDependency object) {
				return createPotentialDanglingEdgeDependencyAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link org.sidiff.editrule.rulebase.RuleBase <em>Rule Base</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sidiff.editrule.rulebase.RuleBase
	 * @generated
	 */
	public Adapter createRuleBaseAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.sidiff.editrule.rulebase.EditRule <em>Edit Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sidiff.editrule.rulebase.EditRule
	 * @generated
	 */
	public Adapter createEditRuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.sidiff.editrule.rulebase.RuleBaseItem <em>Rule Base Item</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sidiff.editrule.rulebase.RuleBaseItem
	 * @generated
	 */
	public Adapter createRuleBaseItemAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.sidiff.editrule.rulebase.PotentialDependency <em>Potential Dependency</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sidiff.editrule.rulebase.PotentialDependency
	 * @generated
	 */
	public Adapter createPotentialDependencyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.sidiff.editrule.rulebase.PotentialNodeDependency <em>Potential Node Dependency</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sidiff.editrule.rulebase.PotentialNodeDependency
	 * @generated
	 */
	public Adapter createPotentialNodeDependencyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.sidiff.editrule.rulebase.PotentialEdgeDependency <em>Potential Edge Dependency</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sidiff.editrule.rulebase.PotentialEdgeDependency
	 * @generated
	 */
	public Adapter createPotentialEdgeDependencyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.sidiff.editrule.rulebase.PotentialAttributeDependency <em>Potential Attribute Dependency</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sidiff.editrule.rulebase.PotentialAttributeDependency
	 * @generated
	 */
	public Adapter createPotentialAttributeDependencyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.sidiff.editrule.rulebase.Parameter <em>Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sidiff.editrule.rulebase.Parameter
	 * @generated
	 */
	public Adapter createParameterAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.sidiff.editrule.rulebase.Classification <em>Classification</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sidiff.editrule.rulebase.Classification
	 * @generated
	 */
	public Adapter createClassificationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.sidiff.editrule.rulebase.EditRuleAttachment <em>Edit Rule Attachment</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sidiff.editrule.rulebase.EditRuleAttachment
	 * @generated
	 */
	public Adapter createEditRuleAttachmentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.sidiff.editrule.rulebase.PotentialConflict <em>Potential Conflict</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sidiff.editrule.rulebase.PotentialConflict
	 * @generated
	 */
	public Adapter createPotentialConflictAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.sidiff.editrule.rulebase.PotentialNodeConflict <em>Potential Node Conflict</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sidiff.editrule.rulebase.PotentialNodeConflict
	 * @generated
	 */
	public Adapter createPotentialNodeConflictAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.sidiff.editrule.rulebase.PotentialEdgeConflict <em>Potential Edge Conflict</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sidiff.editrule.rulebase.PotentialEdgeConflict
	 * @generated
	 */
	public Adapter createPotentialEdgeConflictAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.sidiff.editrule.rulebase.PotentialAttributeConflict <em>Potential Attribute Conflict</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sidiff.editrule.rulebase.PotentialAttributeConflict
	 * @generated
	 */
	public Adapter createPotentialAttributeConflictAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.sidiff.editrule.rulebase.PotentialDanglingEdgeConflict <em>Potential Dangling Edge Conflict</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sidiff.editrule.rulebase.PotentialDanglingEdgeConflict
	 * @generated
	 */
	public Adapter createPotentialDanglingEdgeConflictAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.sidiff.editrule.rulebase.PotentialDanglingEdgeDependency <em>Potential Dangling Edge Dependency</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sidiff.editrule.rulebase.PotentialDanglingEdgeDependency
	 * @generated
	 */
	public Adapter createPotentialDanglingEdgeDependencyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //RulebaseAdapterFactory
