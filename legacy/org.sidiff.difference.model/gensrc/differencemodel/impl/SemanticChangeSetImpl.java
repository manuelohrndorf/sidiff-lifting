/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package differencemodel.impl;

import differencemodel.Change;
import differencemodel.Dependency;
import differencemodel.DifferencemodelPackage;
import differencemodel.ParameterSubstitution;
import differencemodel.SemanticChangeSet;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Semantic Change Set</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link differencemodel.impl.SemanticChangeSetImpl#getChanges <em>Changes</em>}</li>
 *   <li>{@link differencemodel.impl.SemanticChangeSetImpl#getName <em>Name</em>}</li>
 *   <li>{@link differencemodel.impl.SemanticChangeSetImpl#getPriority <em>Priority</em>}</li>
 *   <li>{@link differencemodel.impl.SemanticChangeSetImpl#getRefinementLevel <em>Refinement Level</em>}</li>
 *   <li>{@link differencemodel.impl.SemanticChangeSetImpl#getEditRName <em>Edit RName</em>}</li>
 *   <li>{@link differencemodel.impl.SemanticChangeSetImpl#getParameterSubstitutions <em>Parameter Substitutions</em>}</li>
 *   <li>{@link differencemodel.impl.SemanticChangeSetImpl#getRecognitionRName <em>Recognition RName</em>}</li>
 *   <li>{@link differencemodel.impl.SemanticChangeSetImpl#getOutgoing <em>Outgoing</em>}</li>
 *   <li>{@link differencemodel.impl.SemanticChangeSetImpl#getIncoming <em>Incoming</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SemanticChangeSetImpl extends EObjectImpl implements SemanticChangeSet {
	/**
	 * The cached value of the '{@link #getChanges() <em>Changes</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChanges()
	 * @generated
	 * @ordered
	 */
	protected EList<Change> changes;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getPriority() <em>Priority</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPriority()
	 * @generated
	 * @ordered
	 */
	protected static final int PRIORITY_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getPriority() <em>Priority</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPriority()
	 * @generated
	 * @ordered
	 */
	protected int priority = PRIORITY_EDEFAULT;

	/**
	 * The default value of the '{@link #getRefinementLevel() <em>Refinement Level</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRefinementLevel()
	 * @generated
	 * @ordered
	 */
	protected static final int REFINEMENT_LEVEL_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getRefinementLevel() <em>Refinement Level</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRefinementLevel()
	 * @generated
	 * @ordered
	 */
	protected int refinementLevel = REFINEMENT_LEVEL_EDEFAULT;

	/**
	 * The default value of the '{@link #getEditRName() <em>Edit RName</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEditRName()
	 * @generated
	 * @ordered
	 */
	protected static final String EDIT_RNAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEditRName() <em>Edit RName</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEditRName()
	 * @generated
	 * @ordered
	 */
	protected String editRName = EDIT_RNAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getParameterSubstitutions() <em>Parameter Substitutions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameterSubstitutions()
	 * @generated
	 * @ordered
	 */
	protected EList<ParameterSubstitution> parameterSubstitutions;

	/**
	 * The default value of the '{@link #getRecognitionRName() <em>Recognition RName</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRecognitionRName()
	 * @generated
	 * @ordered
	 */
	protected static final String RECOGNITION_RNAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRecognitionRName() <em>Recognition RName</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRecognitionRName()
	 * @generated
	 * @ordered
	 */
	protected String recognitionRName = RECOGNITION_RNAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getOutgoing() <em>Outgoing</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutgoing()
	 * @generated
	 * @ordered
	 */
	protected EList<Dependency> outgoing;

	/**
	 * The cached value of the '{@link #getIncoming() <em>Incoming</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIncoming()
	 * @generated
	 * @ordered
	 */
	protected EList<Dependency> incoming;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SemanticChangeSetImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DifferencemodelPackage.Literals.SEMANTIC_CHANGE_SET;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Change> getChanges() {
		if (changes == null) {
			changes = new EObjectResolvingEList<Change>(Change.class, this, DifferencemodelPackage.SEMANTIC_CHANGE_SET__CHANGES);
		}
		return changes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DifferencemodelPackage.SEMANTIC_CHANGE_SET__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPriority(int newPriority) {
		int oldPriority = priority;
		priority = newPriority;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DifferencemodelPackage.SEMANTIC_CHANGE_SET__PRIORITY, oldPriority, priority));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getRefinementLevel() {
		return refinementLevel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRefinementLevel(int newRefinementLevel) {
		int oldRefinementLevel = refinementLevel;
		refinementLevel = newRefinementLevel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DifferencemodelPackage.SEMANTIC_CHANGE_SET__REFINEMENT_LEVEL, oldRefinementLevel, refinementLevel));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getEditRName() {
		return editRName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEditRName(String newEditRName) {
		String oldEditRName = editRName;
		editRName = newEditRName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DifferencemodelPackage.SEMANTIC_CHANGE_SET__EDIT_RNAME, oldEditRName, editRName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ParameterSubstitution> getParameterSubstitutions() {
		if (parameterSubstitutions == null) {
			parameterSubstitutions = new EObjectContainmentEList<ParameterSubstitution>(ParameterSubstitution.class, this, DifferencemodelPackage.SEMANTIC_CHANGE_SET__PARAMETER_SUBSTITUTIONS);
		}
		return parameterSubstitutions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getRecognitionRName() {
		return recognitionRName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRecognitionRName(String newRecognitionRName) {
		String oldRecognitionRName = recognitionRName;
		recognitionRName = newRecognitionRName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DifferencemodelPackage.SEMANTIC_CHANGE_SET__RECOGNITION_RNAME, oldRecognitionRName, recognitionRName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Dependency> getOutgoing() {
		if (outgoing == null) {
			outgoing = new EObjectWithInverseResolvingEList<Dependency>(Dependency.class, this, DifferencemodelPackage.SEMANTIC_CHANGE_SET__OUTGOING, DifferencemodelPackage.DEPENDENCY__SOURCE);
		}
		return outgoing;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Dependency> getIncoming() {
		if (incoming == null) {
			incoming = new EObjectWithInverseResolvingEList<Dependency>(Dependency.class, this, DifferencemodelPackage.SEMANTIC_CHANGE_SET__INCOMING, DifferencemodelPackage.DEPENDENCY__TARGET);
		}
		return incoming;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DifferencemodelPackage.SEMANTIC_CHANGE_SET__OUTGOING:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getOutgoing()).basicAdd(otherEnd, msgs);
			case DifferencemodelPackage.SEMANTIC_CHANGE_SET__INCOMING:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getIncoming()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DifferencemodelPackage.SEMANTIC_CHANGE_SET__PARAMETER_SUBSTITUTIONS:
				return ((InternalEList<?>)getParameterSubstitutions()).basicRemove(otherEnd, msgs);
			case DifferencemodelPackage.SEMANTIC_CHANGE_SET__OUTGOING:
				return ((InternalEList<?>)getOutgoing()).basicRemove(otherEnd, msgs);
			case DifferencemodelPackage.SEMANTIC_CHANGE_SET__INCOMING:
				return ((InternalEList<?>)getIncoming()).basicRemove(otherEnd, msgs);
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
			case DifferencemodelPackage.SEMANTIC_CHANGE_SET__CHANGES:
				return getChanges();
			case DifferencemodelPackage.SEMANTIC_CHANGE_SET__NAME:
				return getName();
			case DifferencemodelPackage.SEMANTIC_CHANGE_SET__PRIORITY:
				return getPriority();
			case DifferencemodelPackage.SEMANTIC_CHANGE_SET__REFINEMENT_LEVEL:
				return getRefinementLevel();
			case DifferencemodelPackage.SEMANTIC_CHANGE_SET__EDIT_RNAME:
				return getEditRName();
			case DifferencemodelPackage.SEMANTIC_CHANGE_SET__PARAMETER_SUBSTITUTIONS:
				return getParameterSubstitutions();
			case DifferencemodelPackage.SEMANTIC_CHANGE_SET__RECOGNITION_RNAME:
				return getRecognitionRName();
			case DifferencemodelPackage.SEMANTIC_CHANGE_SET__OUTGOING:
				return getOutgoing();
			case DifferencemodelPackage.SEMANTIC_CHANGE_SET__INCOMING:
				return getIncoming();
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
			case DifferencemodelPackage.SEMANTIC_CHANGE_SET__CHANGES:
				getChanges().clear();
				getChanges().addAll((Collection<? extends Change>)newValue);
				return;
			case DifferencemodelPackage.SEMANTIC_CHANGE_SET__NAME:
				setName((String)newValue);
				return;
			case DifferencemodelPackage.SEMANTIC_CHANGE_SET__PRIORITY:
				setPriority((Integer)newValue);
				return;
			case DifferencemodelPackage.SEMANTIC_CHANGE_SET__REFINEMENT_LEVEL:
				setRefinementLevel((Integer)newValue);
				return;
			case DifferencemodelPackage.SEMANTIC_CHANGE_SET__EDIT_RNAME:
				setEditRName((String)newValue);
				return;
			case DifferencemodelPackage.SEMANTIC_CHANGE_SET__PARAMETER_SUBSTITUTIONS:
				getParameterSubstitutions().clear();
				getParameterSubstitutions().addAll((Collection<? extends ParameterSubstitution>)newValue);
				return;
			case DifferencemodelPackage.SEMANTIC_CHANGE_SET__RECOGNITION_RNAME:
				setRecognitionRName((String)newValue);
				return;
			case DifferencemodelPackage.SEMANTIC_CHANGE_SET__OUTGOING:
				getOutgoing().clear();
				getOutgoing().addAll((Collection<? extends Dependency>)newValue);
				return;
			case DifferencemodelPackage.SEMANTIC_CHANGE_SET__INCOMING:
				getIncoming().clear();
				getIncoming().addAll((Collection<? extends Dependency>)newValue);
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
			case DifferencemodelPackage.SEMANTIC_CHANGE_SET__CHANGES:
				getChanges().clear();
				return;
			case DifferencemodelPackage.SEMANTIC_CHANGE_SET__NAME:
				setName(NAME_EDEFAULT);
				return;
			case DifferencemodelPackage.SEMANTIC_CHANGE_SET__PRIORITY:
				setPriority(PRIORITY_EDEFAULT);
				return;
			case DifferencemodelPackage.SEMANTIC_CHANGE_SET__REFINEMENT_LEVEL:
				setRefinementLevel(REFINEMENT_LEVEL_EDEFAULT);
				return;
			case DifferencemodelPackage.SEMANTIC_CHANGE_SET__EDIT_RNAME:
				setEditRName(EDIT_RNAME_EDEFAULT);
				return;
			case DifferencemodelPackage.SEMANTIC_CHANGE_SET__PARAMETER_SUBSTITUTIONS:
				getParameterSubstitutions().clear();
				return;
			case DifferencemodelPackage.SEMANTIC_CHANGE_SET__RECOGNITION_RNAME:
				setRecognitionRName(RECOGNITION_RNAME_EDEFAULT);
				return;
			case DifferencemodelPackage.SEMANTIC_CHANGE_SET__OUTGOING:
				getOutgoing().clear();
				return;
			case DifferencemodelPackage.SEMANTIC_CHANGE_SET__INCOMING:
				getIncoming().clear();
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
			case DifferencemodelPackage.SEMANTIC_CHANGE_SET__CHANGES:
				return changes != null && !changes.isEmpty();
			case DifferencemodelPackage.SEMANTIC_CHANGE_SET__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case DifferencemodelPackage.SEMANTIC_CHANGE_SET__PRIORITY:
				return priority != PRIORITY_EDEFAULT;
			case DifferencemodelPackage.SEMANTIC_CHANGE_SET__REFINEMENT_LEVEL:
				return refinementLevel != REFINEMENT_LEVEL_EDEFAULT;
			case DifferencemodelPackage.SEMANTIC_CHANGE_SET__EDIT_RNAME:
				return EDIT_RNAME_EDEFAULT == null ? editRName != null : !EDIT_RNAME_EDEFAULT.equals(editRName);
			case DifferencemodelPackage.SEMANTIC_CHANGE_SET__PARAMETER_SUBSTITUTIONS:
				return parameterSubstitutions != null && !parameterSubstitutions.isEmpty();
			case DifferencemodelPackage.SEMANTIC_CHANGE_SET__RECOGNITION_RNAME:
				return RECOGNITION_RNAME_EDEFAULT == null ? recognitionRName != null : !RECOGNITION_RNAME_EDEFAULT.equals(recognitionRName);
			case DifferencemodelPackage.SEMANTIC_CHANGE_SET__OUTGOING:
				return outgoing != null && !outgoing.isEmpty();
			case DifferencemodelPackage.SEMANTIC_CHANGE_SET__INCOMING:
				return incoming != null && !incoming.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(", priority: ");
		result.append(priority);
		result.append(", refinementLevel: ");
		result.append(refinementLevel);
		result.append(", editRName: ");
		result.append(editRName);
		result.append(", recognitionRName: ");
		result.append(recognitionRName);
		result.append(')');
		return result.toString();
	}

} //SemanticChangeSetImpl
