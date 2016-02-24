/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package differencemodel.util;

import differencemodel.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see differencemodel.DifferencemodelPackage
 * @generated
 */
public class DifferencemodelAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static DifferencemodelPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DifferencemodelAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = DifferencemodelPackage.eINSTANCE;
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
	protected DifferencemodelSwitch<Adapter> modelSwitch =
		new DifferencemodelSwitch<Adapter>() {
			@Override
			public Adapter caseDifference(Difference object) {
				return createDifferenceAdapter();
			}
			@Override
			public Adapter caseAddObject(AddObject object) {
				return createAddObjectAdapter();
			}
			@Override
			public Adapter caseRemoveObject(RemoveObject object) {
				return createRemoveObjectAdapter();
			}
			@Override
			public Adapter caseAddReference(AddReference object) {
				return createAddReferenceAdapter();
			}
			@Override
			public Adapter caseRemoveReference(RemoveReference object) {
				return createRemoveReferenceAdapter();
			}
			@Override
			public Adapter caseChange(Change object) {
				return createChangeAdapter();
			}
			@Override
			public Adapter caseSemanticChangeSet(SemanticChangeSet object) {
				return createSemanticChangeSetAdapter();
			}
			@Override
			public Adapter caseCorrespondence(Correspondence object) {
				return createCorrespondenceAdapter();
			}
			@Override
			public Adapter caseAttributeValueChange(AttributeValueChange object) {
				return createAttributeValueChangeAdapter();
			}
			@Override
			public Adapter caseParameterSubstitution(ParameterSubstitution object) {
				return createParameterSubstitutionAdapter();
			}
			@Override
			public Adapter caseObjectParameterSubstitution(ObjectParameterSubstitution object) {
				return createObjectParameterSubstitutionAdapter();
			}
			@Override
			public Adapter caseValueParameterSubstitution(ValueParameterSubstitution object) {
				return createValueParameterSubstitutionAdapter();
			}
			@Override
			public Adapter caseDependency(Dependency object) {
				return createDependencyAdapter();
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
	 * Creates a new adapter for an object of class '{@link differencemodel.Difference <em>Difference</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see differencemodel.Difference
	 * @generated
	 */
	public Adapter createDifferenceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link differencemodel.AddObject <em>Add Object</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see differencemodel.AddObject
	 * @generated
	 */
	public Adapter createAddObjectAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link differencemodel.RemoveObject <em>Remove Object</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see differencemodel.RemoveObject
	 * @generated
	 */
	public Adapter createRemoveObjectAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link differencemodel.AddReference <em>Add Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see differencemodel.AddReference
	 * @generated
	 */
	public Adapter createAddReferenceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link differencemodel.RemoveReference <em>Remove Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see differencemodel.RemoveReference
	 * @generated
	 */
	public Adapter createRemoveReferenceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link differencemodel.Change <em>Change</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see differencemodel.Change
	 * @generated
	 */
	public Adapter createChangeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link differencemodel.SemanticChangeSet <em>Semantic Change Set</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see differencemodel.SemanticChangeSet
	 * @generated
	 */
	public Adapter createSemanticChangeSetAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link differencemodel.Correspondence <em>Correspondence</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see differencemodel.Correspondence
	 * @generated
	 */
	public Adapter createCorrespondenceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link differencemodel.AttributeValueChange <em>Attribute Value Change</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see differencemodel.AttributeValueChange
	 * @generated
	 */
	public Adapter createAttributeValueChangeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link differencemodel.ParameterSubstitution <em>Parameter Substitution</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see differencemodel.ParameterSubstitution
	 * @generated
	 */
	public Adapter createParameterSubstitutionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link differencemodel.ObjectParameterSubstitution <em>Object Parameter Substitution</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see differencemodel.ObjectParameterSubstitution
	 * @generated
	 */
	public Adapter createObjectParameterSubstitutionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link differencemodel.ValueParameterSubstitution <em>Value Parameter Substitution</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see differencemodel.ValueParameterSubstitution
	 * @generated
	 */
	public Adapter createValueParameterSubstitutionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link differencemodel.Dependency <em>Dependency</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see differencemodel.Dependency
	 * @generated
	 */
	public Adapter createDependencyAdapter() {
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

} //DifferencemodelAdapterFactory
