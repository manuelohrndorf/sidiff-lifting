/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package differencemodel.impl;

import differencemodel.Change;
import differencemodel.Correspondence;
import differencemodel.Dependency;
import differencemodel.Difference;
import differencemodel.DifferencemodelPackage;
import differencemodel.SemanticChangeSet;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.resource.Resource;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Difference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link differencemodel.impl.DifferenceImpl#getChanges <em>Changes</em>}</li>
 *   <li>{@link differencemodel.impl.DifferenceImpl#getChangeSets <em>Change Sets</em>}</li>
 *   <li>{@link differencemodel.impl.DifferenceImpl#getCorrespondences <em>Correspondences</em>}</li>
 *   <li>{@link differencemodel.impl.DifferenceImpl#getModelA <em>Model A</em>}</li>
 *   <li>{@link differencemodel.impl.DifferenceImpl#getModelB <em>Model B</em>}</li>
 *   <li>{@link differencemodel.impl.DifferenceImpl#getDependencies <em>Dependencies</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DifferenceImpl extends EObjectImpl implements Difference {
	/**
	 * The cached value of the '{@link #getChanges() <em>Changes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChanges()
	 * @generated
	 * @ordered
	 */
	protected EList<Change> changes;

	/**
	 * The cached value of the '{@link #getChangeSets() <em>Change Sets</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChangeSets()
	 * @generated
	 * @ordered
	 */
	protected EList<SemanticChangeSet> changeSets;

	/**
	 * The cached value of the '{@link #getCorrespondences() <em>Correspondences</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCorrespondences()
	 * @generated
	 * @ordered
	 */
	protected EList<Correspondence> correspondences;

	/**
	 * The default value of the '{@link #getModelA() <em>Model A</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModelA()
	 * @generated
	 * @ordered
	 */
	protected static final Resource MODEL_A_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getModelB() <em>Model B</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModelB()
	 * @generated
	 * @ordered
	 */
	protected static final Resource MODEL_B_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDependencies() <em>Dependencies</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDependencies()
	 * @generated
	 * @ordered
	 */
	protected EList<Dependency> dependencies;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DifferenceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DifferencemodelPackage.Literals.DIFFERENCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Change> getChanges() {
		if (changes == null) {
			changes = new EObjectContainmentEList<Change>(Change.class, this, DifferencemodelPackage.DIFFERENCE__CHANGES);
		}
		return changes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<SemanticChangeSet> getChangeSets() {
		if (changeSets == null) {
			changeSets = new EObjectContainmentEList<SemanticChangeSet>(SemanticChangeSet.class, this, DifferencemodelPackage.DIFFERENCE__CHANGE_SETS);
		}
		return changeSets;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Correspondence> getCorrespondences() {
		if (correspondences == null) {
			correspondences = new EObjectContainmentEList<Correspondence>(Correspondence.class, this, DifferencemodelPackage.DIFFERENCE__CORRESPONDENCES);
		}
		return correspondences;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Resource getModelA() {
		//TODO: this implementation is a little bit quick and dirty.
		// it would be better to store the uriA of the compared models in order to retrieve the EResource
		assert(getCorrespondences().size() > 0) : "This is an empty difference.";
		
		return getCorrespondences().get(0).getObjA().eResource();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Resource getModelB() {
		//TODO: this implementation is a little bit quick and dirty.
		// it would be better to store the uriB of the compared models in order to retrieve the EResource
		assert(getCorrespondences().size() > 0) : "This is an empty difference.";
		
		return getCorrespondences().get(0).getObjB().eResource();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Dependency> getDependencies() {
		if (dependencies == null) {
			dependencies = new EObjectContainmentEList<Dependency>(Dependency.class, this, DifferencemodelPackage.DIFFERENCE__DEPENDENCIES);
		}
		return dependencies;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DifferencemodelPackage.DIFFERENCE__CHANGES:
				return ((InternalEList<?>)getChanges()).basicRemove(otherEnd, msgs);
			case DifferencemodelPackage.DIFFERENCE__CHANGE_SETS:
				return ((InternalEList<?>)getChangeSets()).basicRemove(otherEnd, msgs);
			case DifferencemodelPackage.DIFFERENCE__CORRESPONDENCES:
				return ((InternalEList<?>)getCorrespondences()).basicRemove(otherEnd, msgs);
			case DifferencemodelPackage.DIFFERENCE__DEPENDENCIES:
				return ((InternalEList<?>)getDependencies()).basicRemove(otherEnd, msgs);
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
			case DifferencemodelPackage.DIFFERENCE__CHANGES:
				return getChanges();
			case DifferencemodelPackage.DIFFERENCE__CHANGE_SETS:
				return getChangeSets();
			case DifferencemodelPackage.DIFFERENCE__CORRESPONDENCES:
				return getCorrespondences();
			case DifferencemodelPackage.DIFFERENCE__MODEL_A:
				return getModelA();
			case DifferencemodelPackage.DIFFERENCE__MODEL_B:
				return getModelB();
			case DifferencemodelPackage.DIFFERENCE__DEPENDENCIES:
				return getDependencies();
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
			case DifferencemodelPackage.DIFFERENCE__CHANGES:
				getChanges().clear();
				getChanges().addAll((Collection<? extends Change>)newValue);
				return;
			case DifferencemodelPackage.DIFFERENCE__CHANGE_SETS:
				getChangeSets().clear();
				getChangeSets().addAll((Collection<? extends SemanticChangeSet>)newValue);
				return;
			case DifferencemodelPackage.DIFFERENCE__CORRESPONDENCES:
				getCorrespondences().clear();
				getCorrespondences().addAll((Collection<? extends Correspondence>)newValue);
				return;
			case DifferencemodelPackage.DIFFERENCE__DEPENDENCIES:
				getDependencies().clear();
				getDependencies().addAll((Collection<? extends Dependency>)newValue);
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
			case DifferencemodelPackage.DIFFERENCE__CHANGES:
				getChanges().clear();
				return;
			case DifferencemodelPackage.DIFFERENCE__CHANGE_SETS:
				getChangeSets().clear();
				return;
			case DifferencemodelPackage.DIFFERENCE__CORRESPONDENCES:
				getCorrespondences().clear();
				return;
			case DifferencemodelPackage.DIFFERENCE__DEPENDENCIES:
				getDependencies().clear();
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
			case DifferencemodelPackage.DIFFERENCE__CHANGES:
				return changes != null && !changes.isEmpty();
			case DifferencemodelPackage.DIFFERENCE__CHANGE_SETS:
				return changeSets != null && !changeSets.isEmpty();
			case DifferencemodelPackage.DIFFERENCE__CORRESPONDENCES:
				return correspondences != null && !correspondences.isEmpty();
			case DifferencemodelPackage.DIFFERENCE__MODEL_A:
				return MODEL_A_EDEFAULT == null ? getModelA() != null : !MODEL_A_EDEFAULT.equals(getModelA());
			case DifferencemodelPackage.DIFFERENCE__MODEL_B:
				return MODEL_B_EDEFAULT == null ? getModelB() != null : !MODEL_B_EDEFAULT.equals(getModelB());
			case DifferencemodelPackage.DIFFERENCE__DEPENDENCIES:
				return dependencies != null && !dependencies.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //DifferenceImpl
