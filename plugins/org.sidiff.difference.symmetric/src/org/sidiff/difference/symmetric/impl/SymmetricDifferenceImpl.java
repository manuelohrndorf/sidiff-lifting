/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.difference.symmetric.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.sidiff.difference.symmetric.Change;
import org.sidiff.difference.symmetric.Correspondence;
import org.sidiff.difference.symmetric.SemanticChangeSet;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.difference.symmetric.SymmetricFactory;
import org.sidiff.difference.symmetric.SymmetricPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Difference</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.sidiff.difference.symmetric.impl.SymmetricDifferenceImpl#getChanges <em>Changes</em>}</li>
 *   <li>{@link org.sidiff.difference.symmetric.impl.SymmetricDifferenceImpl#getChangeSets <em>Change Sets</em>}</li>
 *   <li>{@link org.sidiff.difference.symmetric.impl.SymmetricDifferenceImpl#getCorrespondences <em>Correspondences</em>}</li>
 *   <li>{@link org.sidiff.difference.symmetric.impl.SymmetricDifferenceImpl#getModelA <em>Model A</em>}</li>
 *   <li>{@link org.sidiff.difference.symmetric.impl.SymmetricDifferenceImpl#getModelB <em>Model B</em>}</li>
 *   <li>{@link org.sidiff.difference.symmetric.impl.SymmetricDifferenceImpl#getUriModelA <em>Uri Model A</em>}</li>
 *   <li>{@link org.sidiff.difference.symmetric.impl.SymmetricDifferenceImpl#getUriModelB <em>Uri Model B</em>}</li>
 *   <li>{@link org.sidiff.difference.symmetric.impl.SymmetricDifferenceImpl#getNotOverlappings <em>Not Overlappings</em>}</li>
 *   <li>{@link org.sidiff.difference.symmetric.impl.SymmetricDifferenceImpl#getUnusedChangeSets <em>Unused Change Sets</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SymmetricDifferenceImpl extends EObjectImpl implements SymmetricDifference {

	/**
	 * Indexed access to correspondences
	 */
	private Map<EObject, EObject> correspondencesA2B;
	private Map<EObject, EObject> correspondencesB2A;
	private Map<EObject, Correspondence> correspondencesA;
	private Map<EObject, Correspondence> correspondencesB;

	/**
	 * The cached value of the '{@link #getChanges() <em>Changes</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getChanges()
	 * @generated
	 * @ordered
	 */
	protected EList<Change> changes;

	/**
	 * The cached value of the '{@link #getChangeSets() <em>Change Sets</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getChangeSets()
	 * @generated
	 * @ordered
	 */
	protected EList<SemanticChangeSet> changeSets;

	/**
	 * The cached value of the '{@link #getCorrespondences()
	 * <em>Correspondences</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getCorrespondences()
	 * @generated
	 * @ordered
	 */
	protected EList<Correspondence> correspondences;

	/**
	 * The default value of the '{@link #getModelA() <em>Model A</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getModelA()
	 * @generated
	 * @ordered
	 */
	protected static final Resource MODEL_A_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getModelB() <em>Model B</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getModelB()
	 * @generated
	 * @ordered
	 */
	protected static final Resource MODEL_B_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getUriModelA() <em>Uri Model A</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getUriModelA()
	 * @generated
	 * @ordered
	 */
	protected static final String URI_MODEL_A_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getUriModelA() <em>Uri Model A</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getUriModelA()
	 * @generated
	 * @ordered
	 */
	protected String uriModelA = URI_MODEL_A_EDEFAULT;
	/**
	 * The default value of the '{@link #getUriModelB() <em>Uri Model B</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getUriModelB()
	 * @generated
	 * @ordered
	 */
	protected static final String URI_MODEL_B_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getUriModelB() <em>Uri Model B</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getUriModelB()
	 * @generated
	 * @ordered
	 */
	protected String uriModelB = URI_MODEL_B_EDEFAULT;

	/**
	 * The cached value of the '{@link #getNotOverlappings() <em>Not Overlappings</em>}' reference list.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getNotOverlappings()
	 * @generated
	 * @ordered
	 */
	protected EList<SemanticChangeSet> notOverlappings;
	/**
	 * The cached value of the '{@link #getUnusedChangeSets()
	 * <em>Unused Change Sets</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getUnusedChangeSets()
	 * @generated
	 * @ordered
	 */
	protected EList<SemanticChangeSet> unusedChangeSets;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	protected SymmetricDifferenceImpl() {
		super();

		initCorrespondenceIndex();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SymmetricPackage.Literals.SYMMETRIC_DIFFERENCE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Change> getChanges() {
		if (changes == null) {
			changes = new EObjectContainmentEList<Change>(Change.class, this, SymmetricPackage.SYMMETRIC_DIFFERENCE__CHANGES);
		}
		return changes;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<SemanticChangeSet> getChangeSets() {
		if (changeSets == null) {
			changeSets = new EObjectContainmentEList<SemanticChangeSet>(SemanticChangeSet.class, this, SymmetricPackage.SYMMETRIC_DIFFERENCE__CHANGE_SETS);
		}
		return changeSets;
	}

	/**
	 * <!-- begin-user-doc --> Do not modify the returned List of
	 * correspondences. Use {@link #addCorrespondence(Correspondence)} and
	 * {@link #removeCorrespondence(Correspondence)} instead. <!-- end-user-doc
	 * -->
	 * 
	 * @generated NOT
	 */
	public EList<Correspondence> getCorrespondences() {
		return intl_getCorrespondences();
	}

	/**
	 * Lazy initialization of {@link #correspondences}.
	 * 
	 * @return
	 */
	private EList<Correspondence> intl_getCorrespondences() {
		if (correspondences == null) {
			correspondences = new EObjectContainmentEList<Correspondence>(Correspondence.class, this,
					SymmetricPackage.SYMMETRIC_DIFFERENCE__CORRESPONDENCES);
		}
		return correspondences;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public Resource getModelA() {
		// FIXME: Sollte keine Assertion sein, da Information einen Mehrwert für
		// Endbenutzer darstellt.
		// Daher lieber eine Exception werfen, die dann von der UI gefangen und
		// in ErrorDialog angezeigt wird.
		assert (getCorrespondences().size() > 0) : "This is an empty difference.";

		for (Correspondence c : getCorrespondences()) {
			// TODO cpietsch, relative vs. absolute path
			// if (c.getObjA().eResource() != null &&
			// c.getObjA().eResource().getURI().toString().equals(getUriModelA())){
			if (c.getObjA().eResource() != null && c.getObjA().eResource().getURI().toString().contains(getUriModelA())) {
				return c.getObjA().eResource();
			}
		}

		assert (false) : "No Resource found for modelA";
		return null;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public Resource getModelB() {
		assert (getCorrespondences().size() > 0) : "This is an empty difference.";

		for (Correspondence c : getCorrespondences()) {
			// TODO cpietsch, relative vs. absolute path
			// if (c.getObjB().eResource() != null &&
			// c.getObjB().eResource().getURI().toString().equals(getUriModelB())){
			if (c.getObjB().eResource() != null && c.getObjB().eResource().getURI().toString().contains(getUriModelB())) {
				return c.getObjB().eResource();
			}
		}

		assert (false) : "No Resource found for modelB";
		return null;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String getUriModelA() {
		return uriModelA;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setUriModelA(String newUriModelA) {
		String oldUriModelA = uriModelA;
		uriModelA = newUriModelA;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SymmetricPackage.SYMMETRIC_DIFFERENCE__URI_MODEL_A, oldUriModelA, uriModelA));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String getUriModelB() {
		return uriModelB;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setUriModelB(String newUriModelB) {
		String oldUriModelB = uriModelB;
		uriModelB = newUriModelB;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SymmetricPackage.SYMMETRIC_DIFFERENCE__URI_MODEL_B, oldUriModelB, uriModelB));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<SemanticChangeSet> getNotOverlappings() {
		if (notOverlappings == null) {
			notOverlappings = new EObjectResolvingEList<SemanticChangeSet>(SemanticChangeSet.class, this, SymmetricPackage.SYMMETRIC_DIFFERENCE__NOT_OVERLAPPINGS);
		}
		return notOverlappings;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<SemanticChangeSet> getUnusedChangeSets() {
		if (unusedChangeSets == null) {
			unusedChangeSets = new EObjectContainmentEList<SemanticChangeSet>(SemanticChangeSet.class, this, SymmetricPackage.SYMMETRIC_DIFFERENCE__UNUSED_CHANGE_SETS);
		}
		return unusedChangeSets;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public EObject getCorrespondingObjectInA(EObject objectInB) {
		return correspondencesB2A.get(objectInB);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public EObject getCorrespondingObjectInB(EObject objectInA) {
		return correspondencesA2B.get(objectInA);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public void addCorrespondence(EObject objectA, EObject objectB) {
		Correspondence c = SymmetricFactory.eINSTANCE.createCorrespondence(objectA, objectB);
		addCorrespondence(c);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public void removeCorrespondence(Correspondence correspondence) {
		intl_getCorrespondences().remove(correspondence);
		correspondencesA2B.remove(correspondence.getObjA());
		correspondencesB2A.remove(correspondence.getObjB());
		correspondencesA.remove(correspondence.getObjA());
		correspondencesB.remove(correspondence.getObjB());
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public void addCorrespondence(Correspondence correspondence) {
		intl_getCorrespondences().add(correspondence);
		correspondencesA2B.put(correspondence.getObjA(), correspondence.getObjB());
		correspondencesB2A.put(correspondence.getObjB(), correspondence.getObjA());
		correspondencesA.put(correspondence.getObjA(), correspondence);
		correspondencesB.put(correspondence.getObjB(), correspondence);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public void addCorrespondence(EObject objectA, EObject objectB, float reliability) {
		Correspondence c = SymmetricFactory.eINSTANCE.createCorrespondence(objectA, objectB);
		c.setReliability(reliability);
		addCorrespondence(c);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public float getReliability(EObject objectA, EObject objectB) {
		if (objectA != null && objectB != null && getCorrespondingObjectInB(objectA) == objectB) {
			return correspondencesA.get(objectA).getReliability();
		} else {
			return 0.0f;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void removeCorrespondenceA(EObject objectInA) {
		Correspondence c = correspondencesA.get(objectInA);
		if (c != null){
			removeCorrespondence(c);
		}		
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void removeCorrespondenceB(EObject objectInB) {
		Correspondence c = correspondencesB.get(objectInB);
		if (c != null){
			removeCorrespondence(c);
		}		
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SymmetricPackage.SYMMETRIC_DIFFERENCE__CHANGES:
				return ((InternalEList<?>)getChanges()).basicRemove(otherEnd, msgs);
			case SymmetricPackage.SYMMETRIC_DIFFERENCE__CHANGE_SETS:
				return ((InternalEList<?>)getChangeSets()).basicRemove(otherEnd, msgs);
			case SymmetricPackage.SYMMETRIC_DIFFERENCE__CORRESPONDENCES:
				return ((InternalEList<?>)getCorrespondences()).basicRemove(otherEnd, msgs);
			case SymmetricPackage.SYMMETRIC_DIFFERENCE__UNUSED_CHANGE_SETS:
				return ((InternalEList<?>)getUnusedChangeSets()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SymmetricPackage.SYMMETRIC_DIFFERENCE__CHANGES:
				return getChanges();
			case SymmetricPackage.SYMMETRIC_DIFFERENCE__CHANGE_SETS:
				return getChangeSets();
			case SymmetricPackage.SYMMETRIC_DIFFERENCE__CORRESPONDENCES:
				return getCorrespondences();
			case SymmetricPackage.SYMMETRIC_DIFFERENCE__MODEL_A:
				return getModelA();
			case SymmetricPackage.SYMMETRIC_DIFFERENCE__MODEL_B:
				return getModelB();
			case SymmetricPackage.SYMMETRIC_DIFFERENCE__URI_MODEL_A:
				return getUriModelA();
			case SymmetricPackage.SYMMETRIC_DIFFERENCE__URI_MODEL_B:
				return getUriModelB();
			case SymmetricPackage.SYMMETRIC_DIFFERENCE__NOT_OVERLAPPINGS:
				return getNotOverlappings();
			case SymmetricPackage.SYMMETRIC_DIFFERENCE__UNUSED_CHANGE_SETS:
				return getUnusedChangeSets();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case SymmetricPackage.SYMMETRIC_DIFFERENCE__CHANGES:
				getChanges().clear();
				getChanges().addAll((Collection<? extends Change>)newValue);
				return;
			case SymmetricPackage.SYMMETRIC_DIFFERENCE__CHANGE_SETS:
				getChangeSets().clear();
				getChangeSets().addAll((Collection<? extends SemanticChangeSet>)newValue);
				return;
			case SymmetricPackage.SYMMETRIC_DIFFERENCE__URI_MODEL_A:
				setUriModelA((String)newValue);
				return;
			case SymmetricPackage.SYMMETRIC_DIFFERENCE__URI_MODEL_B:
				setUriModelB((String)newValue);
				return;
			case SymmetricPackage.SYMMETRIC_DIFFERENCE__NOT_OVERLAPPINGS:
				getNotOverlappings().clear();
				getNotOverlappings().addAll((Collection<? extends SemanticChangeSet>)newValue);
				return;
			case SymmetricPackage.SYMMETRIC_DIFFERENCE__UNUSED_CHANGE_SETS:
				getUnusedChangeSets().clear();
				getUnusedChangeSets().addAll((Collection<? extends SemanticChangeSet>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case SymmetricPackage.SYMMETRIC_DIFFERENCE__CHANGES:
				getChanges().clear();
				return;
			case SymmetricPackage.SYMMETRIC_DIFFERENCE__CHANGE_SETS:
				getChangeSets().clear();
				return;
			case SymmetricPackage.SYMMETRIC_DIFFERENCE__URI_MODEL_A:
				setUriModelA(URI_MODEL_A_EDEFAULT);
				return;
			case SymmetricPackage.SYMMETRIC_DIFFERENCE__URI_MODEL_B:
				setUriModelB(URI_MODEL_B_EDEFAULT);
				return;
			case SymmetricPackage.SYMMETRIC_DIFFERENCE__NOT_OVERLAPPINGS:
				getNotOverlappings().clear();
				return;
			case SymmetricPackage.SYMMETRIC_DIFFERENCE__UNUSED_CHANGE_SETS:
				getUnusedChangeSets().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case SymmetricPackage.SYMMETRIC_DIFFERENCE__CHANGES:
				return changes != null && !changes.isEmpty();
			case SymmetricPackage.SYMMETRIC_DIFFERENCE__CHANGE_SETS:
				return changeSets != null && !changeSets.isEmpty();
			case SymmetricPackage.SYMMETRIC_DIFFERENCE__CORRESPONDENCES:
				return correspondences != null && !correspondences.isEmpty();
			case SymmetricPackage.SYMMETRIC_DIFFERENCE__MODEL_A:
				return MODEL_A_EDEFAULT == null ? getModelA() != null : !MODEL_A_EDEFAULT.equals(getModelA());
			case SymmetricPackage.SYMMETRIC_DIFFERENCE__MODEL_B:
				return MODEL_B_EDEFAULT == null ? getModelB() != null : !MODEL_B_EDEFAULT.equals(getModelB());
			case SymmetricPackage.SYMMETRIC_DIFFERENCE__URI_MODEL_A:
				return URI_MODEL_A_EDEFAULT == null ? uriModelA != null : !URI_MODEL_A_EDEFAULT.equals(uriModelA);
			case SymmetricPackage.SYMMETRIC_DIFFERENCE__URI_MODEL_B:
				return URI_MODEL_B_EDEFAULT == null ? uriModelB != null : !URI_MODEL_B_EDEFAULT.equals(uriModelB);
			case SymmetricPackage.SYMMETRIC_DIFFERENCE__NOT_OVERLAPPINGS:
				return notOverlappings != null && !notOverlappings.isEmpty();
			case SymmetricPackage.SYMMETRIC_DIFFERENCE__UNUSED_CHANGE_SETS:
				return unusedChangeSets != null && !unusedChangeSets.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (uriModelA: ");
		result.append(uriModelA);
		result.append(", uriModelB: ");
		result.append(uriModelB);
		result.append(')');
		return result.toString();
	}

	// init correspondence index
	private void initCorrespondenceIndex() {
		correspondencesA2B = new HashMap<EObject, EObject>();
		correspondencesB2A = new HashMap<EObject, EObject>();
		correspondencesA = new HashMap<EObject, Correspondence>();
		correspondencesB = new HashMap<EObject, Correspondence>();
	}

} // SymmetricDifferenceImpl
