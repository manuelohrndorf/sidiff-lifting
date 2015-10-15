/**
 */
package org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Map;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EObjectValidator;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.ConnectionPointReference;
import org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.MagicDrawStatechartsEffectivePackage;
import org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Pseudostate;
import org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Region;
import org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State;
import org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.StateMachine;
import org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Transition;
import org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Vertex;
import org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.util.MagicDrawStatechartsEffectiveValidator;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>State</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.StateImpl#getContainer <em>Container</em>}</li>
 *   <li>{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.StateImpl#getIncoming <em>Incoming</em>}</li>
 *   <li>{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.StateImpl#getOutgoing <em>Outgoing</em>}</li>
 *   <li>{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.StateImpl#getConnection <em>Connection</em>}</li>
 *   <li>{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.StateImpl#getConnectionPoint <em>Connection Point</em>}</li>
 *   <li>{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.StateImpl#isIsComposite <em>Is Composite</em>}</li>
 *   <li>{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.StateImpl#isIsOrthogonal <em>Is Orthogonal</em>}</li>
 *   <li>{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.StateImpl#isIsSimple <em>Is Simple</em>}</li>
 *   <li>{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.StateImpl#isIsSubmachineState <em>Is Submachine State</em>}</li>
 *   <li>{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.StateImpl#getRedefinedState <em>Redefined State</em>}</li>
 *   <li>{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.StateImpl#getSubmachine <em>Submachine</em>}</li>
 *   <li>{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.StateImpl#getRegion <em>Region</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class StateImpl extends NamespaceImpl implements State {
	/**
	 * The cached value of the '{@link #getConnection() <em>Connection</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConnection()
	 * @generated
	 * @ordered
	 */
	protected EList<ConnectionPointReference> connection;

	/**
	 * The cached value of the '{@link #getConnectionPoint() <em>Connection Point</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConnectionPoint()
	 * @generated
	 * @ordered
	 */
	protected EList<Pseudostate> connectionPoint;

	/**
	 * The default value of the '{@link #isIsComposite() <em>Is Composite</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsComposite()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_COMPOSITE_EDEFAULT = false;

	/**
	 * The default value of the '{@link #isIsOrthogonal() <em>Is Orthogonal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsOrthogonal()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_ORTHOGONAL_EDEFAULT = false;

	/**
	 * The default value of the '{@link #isIsSimple() <em>Is Simple</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsSimple()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_SIMPLE_EDEFAULT = true;

	/**
	 * The default value of the '{@link #isIsSubmachineState() <em>Is Submachine State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsSubmachineState()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_SUBMACHINE_STATE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #getRedefinedState() <em>Redefined State</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRedefinedState()
	 * @generated
	 * @ordered
	 */
	protected State redefinedState;

	/**
	 * The cached value of the '{@link #getSubmachine() <em>Submachine</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubmachine()
	 * @generated
	 * @ordered
	 */
	protected StateMachine submachine;

	/**
	 * The cached value of the '{@link #getRegion() <em>Region</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRegion()
	 * @generated
	 * @ordered
	 */
	protected EList<Region> region;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StateImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MagicDrawStatechartsEffectivePackage.Literals.STATE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Region getContainer() {
		if (eContainerFeatureID() != MagicDrawStatechartsEffectivePackage.STATE__CONTAINER) return null;
		return (Region)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetContainer(Region newContainer, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newContainer, MagicDrawStatechartsEffectivePackage.STATE__CONTAINER, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setContainer(Region newContainer) {
		if (newContainer != eInternalContainer() || (eContainerFeatureID() != MagicDrawStatechartsEffectivePackage.STATE__CONTAINER && newContainer != null)) {
			if (EcoreUtil.isAncestor(this, newContainer))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newContainer != null)
				msgs = ((InternalEObject)newContainer).eInverseAdd(this, MagicDrawStatechartsEffectivePackage.REGION__SUBVERTEX, Region.class, msgs);
			msgs = basicSetContainer(newContainer, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MagicDrawStatechartsEffectivePackage.STATE__CONTAINER, newContainer, newContainer));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Transition> getIncoming() {
		// TODO: implement this method to return the 'Incoming' reference list
		// Ensure that you remove @generated or mark it @generated NOT
		// The list is expected to implement org.eclipse.emf.ecore.util.InternalEList and org.eclipse.emf.ecore.EStructuralFeature.Setting
		// so it's likely that an appropriate subclass of org.eclipse.emf.ecore.util.EcoreEList should be used.
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Transition> getOutgoing() {
		// TODO: implement this method to return the 'Outgoing' reference list
		// Ensure that you remove @generated or mark it @generated NOT
		// The list is expected to implement org.eclipse.emf.ecore.util.InternalEList and org.eclipse.emf.ecore.EStructuralFeature.Setting
		// so it's likely that an appropriate subclass of org.eclipse.emf.ecore.util.EcoreEList should be used.
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ConnectionPointReference> getConnection() {
		if (connection == null) {
			connection = new EObjectContainmentWithInverseEList<ConnectionPointReference>(ConnectionPointReference.class, this, MagicDrawStatechartsEffectivePackage.STATE__CONNECTION, MagicDrawStatechartsEffectivePackage.CONNECTION_POINT_REFERENCE__STATE);
		}
		return connection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Pseudostate> getConnectionPoint() {
		if (connectionPoint == null) {
			connectionPoint = new EObjectContainmentWithInverseEList<Pseudostate>(Pseudostate.class, this, MagicDrawStatechartsEffectivePackage.STATE__CONNECTION_POINT, MagicDrawStatechartsEffectivePackage.PSEUDOSTATE__STATE);
		}
		return connectionPoint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isIsComposite() {
		// TODO: implement this method to return the 'Is Composite' attribute
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isIsOrthogonal() {
		// TODO: implement this method to return the 'Is Orthogonal' attribute
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isIsSimple() {
		// TODO: implement this method to return the 'Is Simple' attribute
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isIsSubmachineState() {
		// TODO: implement this method to return the 'Is Submachine State' attribute
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public State getRedefinedState() {
		if (redefinedState != null && redefinedState.eIsProxy()) {
			InternalEObject oldRedefinedState = (InternalEObject)redefinedState;
			redefinedState = (State)eResolveProxy(oldRedefinedState);
			if (redefinedState != oldRedefinedState) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MagicDrawStatechartsEffectivePackage.STATE__REDEFINED_STATE, oldRedefinedState, redefinedState));
			}
		}
		return redefinedState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public State basicGetRedefinedState() {
		return redefinedState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRedefinedState(State newRedefinedState) {
		State oldRedefinedState = redefinedState;
		redefinedState = newRedefinedState;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MagicDrawStatechartsEffectivePackage.STATE__REDEFINED_STATE, oldRedefinedState, redefinedState));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StateMachine getSubmachine() {
		if (submachine != null && submachine.eIsProxy()) {
			InternalEObject oldSubmachine = (InternalEObject)submachine;
			submachine = (StateMachine)eResolveProxy(oldSubmachine);
			if (submachine != oldSubmachine) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MagicDrawStatechartsEffectivePackage.STATE__SUBMACHINE, oldSubmachine, submachine));
			}
		}
		return submachine;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StateMachine basicGetSubmachine() {
		return submachine;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSubmachine(StateMachine newSubmachine, NotificationChain msgs) {
		StateMachine oldSubmachine = submachine;
		submachine = newSubmachine;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, MagicDrawStatechartsEffectivePackage.STATE__SUBMACHINE, oldSubmachine, newSubmachine);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSubmachine(StateMachine newSubmachine) {
		if (newSubmachine != submachine) {
			NotificationChain msgs = null;
			if (submachine != null)
				msgs = ((InternalEObject)submachine).eInverseRemove(this, MagicDrawStatechartsEffectivePackage.STATE_MACHINE__SUBMACHINE_STATE, StateMachine.class, msgs);
			if (newSubmachine != null)
				msgs = ((InternalEObject)newSubmachine).eInverseAdd(this, MagicDrawStatechartsEffectivePackage.STATE_MACHINE__SUBMACHINE_STATE, StateMachine.class, msgs);
			msgs = basicSetSubmachine(newSubmachine, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MagicDrawStatechartsEffectivePackage.STATE__SUBMACHINE, newSubmachine, newSubmachine));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Region> getRegion() {
		if (region == null) {
			region = new EObjectContainmentWithInverseEList<Region>(Region.class, this, MagicDrawStatechartsEffectivePackage.STATE__REGION, MagicDrawStatechartsEffectivePackage.REGION__STATE);
		}
		return region;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean entry_or_exit(DiagnosticChain diagnostics, Map<Object, Object> context) {
		// TODO: implement this method
		// -> specify the condition that violates the invariant
		// -> verify the details of the diagnostic, including severity and message
		// Ensure that you remove @generated or mark it @generated NOT
		if (false) {
			if (diagnostics != null) {
				diagnostics.add
					(new BasicDiagnostic
						(Diagnostic.ERROR,
						 MagicDrawStatechartsEffectiveValidator.DIAGNOSTIC_SOURCE,
						 MagicDrawStatechartsEffectiveValidator.STATE__ENTRY_OR_EXIT,
						 EcorePlugin.INSTANCE.getString("_UI_GenericInvariant_diagnostic", new Object[] { "entry_or_exit", EObjectValidator.getObjectLabel(this, context) }),
						 new Object [] { this }));
			}
			return false;
		}
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean composite_states(DiagnosticChain diagnostics, Map<Object, Object> context) {
		// TODO: implement this method
		// -> specify the condition that violates the invariant
		// -> verify the details of the diagnostic, including severity and message
		// Ensure that you remove @generated or mark it @generated NOT
		if (false) {
			if (diagnostics != null) {
				diagnostics.add
					(new BasicDiagnostic
						(Diagnostic.ERROR,
						 MagicDrawStatechartsEffectiveValidator.DIAGNOSTIC_SOURCE,
						 MagicDrawStatechartsEffectiveValidator.STATE__COMPOSITE_STATES,
						 EcorePlugin.INSTANCE.getString("_UI_GenericInvariant_diagnostic", new Object[] { "composite_states", EObjectValidator.getObjectLabel(this, context) }),
						 new Object [] { this }));
			}
			return false;
		}
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean destinations_or_sources_of_transitions(DiagnosticChain diagnostics, Map<Object, Object> context) {
		// TODO: implement this method
		// -> specify the condition that violates the invariant
		// -> verify the details of the diagnostic, including severity and message
		// Ensure that you remove @generated or mark it @generated NOT
		if (false) {
			if (diagnostics != null) {
				diagnostics.add
					(new BasicDiagnostic
						(Diagnostic.ERROR,
						 MagicDrawStatechartsEffectiveValidator.DIAGNOSTIC_SOURCE,
						 MagicDrawStatechartsEffectiveValidator.STATE__DESTINATIONS_OR_SOURCES_OF_TRANSITIONS,
						 EcorePlugin.INSTANCE.getString("_UI_GenericInvariant_diagnostic", new Object[] { "destinations_or_sources_of_transitions", EObjectValidator.getObjectLabel(this, context) }),
						 new Object [] { this }));
			}
			return false;
		}
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean submachine_or_regions(DiagnosticChain diagnostics, Map<Object, Object> context) {
		// TODO: implement this method
		// -> specify the condition that violates the invariant
		// -> verify the details of the diagnostic, including severity and message
		// Ensure that you remove @generated or mark it @generated NOT
		if (false) {
			if (diagnostics != null) {
				diagnostics.add
					(new BasicDiagnostic
						(Diagnostic.ERROR,
						 MagicDrawStatechartsEffectiveValidator.DIAGNOSTIC_SOURCE,
						 MagicDrawStatechartsEffectiveValidator.STATE__SUBMACHINE_OR_REGIONS,
						 EcorePlugin.INSTANCE.getString("_UI_GenericInvariant_diagnostic", new Object[] { "submachine_or_regions", EObjectValidator.getObjectLabel(this, context) }),
						 new Object [] { this }));
			}
			return false;
		}
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean submachine_states(DiagnosticChain diagnostics, Map<Object, Object> context) {
		// TODO: implement this method
		// -> specify the condition that violates the invariant
		// -> verify the details of the diagnostic, including severity and message
		// Ensure that you remove @generated or mark it @generated NOT
		if (false) {
			if (diagnostics != null) {
				diagnostics.add
					(new BasicDiagnostic
						(Diagnostic.ERROR,
						 MagicDrawStatechartsEffectiveValidator.DIAGNOSTIC_SOURCE,
						 MagicDrawStatechartsEffectiveValidator.STATE__SUBMACHINE_STATES,
						 EcorePlugin.INSTANCE.getString("_UI_GenericInvariant_diagnostic", new Object[] { "submachine_states", EObjectValidator.getObjectLabel(this, context) }),
						 new Object [] { this }));
			}
			return false;
		}
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isComposite() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isOrthogonal() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isRedefinitionContextValid(State redefined) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSimple() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSubmachineState() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void redefinitionContext() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StateMachine containingStateMachine() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Transition> getIncomings() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Transition> getOutgoings() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
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
			case MagicDrawStatechartsEffectivePackage.STATE__CONTAINER:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetContainer((Region)otherEnd, msgs);
			case MagicDrawStatechartsEffectivePackage.STATE__CONNECTION:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getConnection()).basicAdd(otherEnd, msgs);
			case MagicDrawStatechartsEffectivePackage.STATE__CONNECTION_POINT:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getConnectionPoint()).basicAdd(otherEnd, msgs);
			case MagicDrawStatechartsEffectivePackage.STATE__SUBMACHINE:
				if (submachine != null)
					msgs = ((InternalEObject)submachine).eInverseRemove(this, MagicDrawStatechartsEffectivePackage.STATE_MACHINE__SUBMACHINE_STATE, StateMachine.class, msgs);
				return basicSetSubmachine((StateMachine)otherEnd, msgs);
			case MagicDrawStatechartsEffectivePackage.STATE__REGION:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getRegion()).basicAdd(otherEnd, msgs);
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
			case MagicDrawStatechartsEffectivePackage.STATE__CONTAINER:
				return basicSetContainer(null, msgs);
			case MagicDrawStatechartsEffectivePackage.STATE__CONNECTION:
				return ((InternalEList<?>)getConnection()).basicRemove(otherEnd, msgs);
			case MagicDrawStatechartsEffectivePackage.STATE__CONNECTION_POINT:
				return ((InternalEList<?>)getConnectionPoint()).basicRemove(otherEnd, msgs);
			case MagicDrawStatechartsEffectivePackage.STATE__SUBMACHINE:
				return basicSetSubmachine(null, msgs);
			case MagicDrawStatechartsEffectivePackage.STATE__REGION:
				return ((InternalEList<?>)getRegion()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case MagicDrawStatechartsEffectivePackage.STATE__CONTAINER:
				return eInternalContainer().eInverseRemove(this, MagicDrawStatechartsEffectivePackage.REGION__SUBVERTEX, Region.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MagicDrawStatechartsEffectivePackage.STATE__CONTAINER:
				return getContainer();
			case MagicDrawStatechartsEffectivePackage.STATE__INCOMING:
				return getIncoming();
			case MagicDrawStatechartsEffectivePackage.STATE__OUTGOING:
				return getOutgoing();
			case MagicDrawStatechartsEffectivePackage.STATE__CONNECTION:
				return getConnection();
			case MagicDrawStatechartsEffectivePackage.STATE__CONNECTION_POINT:
				return getConnectionPoint();
			case MagicDrawStatechartsEffectivePackage.STATE__IS_COMPOSITE:
				return isIsComposite();
			case MagicDrawStatechartsEffectivePackage.STATE__IS_ORTHOGONAL:
				return isIsOrthogonal();
			case MagicDrawStatechartsEffectivePackage.STATE__IS_SIMPLE:
				return isIsSimple();
			case MagicDrawStatechartsEffectivePackage.STATE__IS_SUBMACHINE_STATE:
				return isIsSubmachineState();
			case MagicDrawStatechartsEffectivePackage.STATE__REDEFINED_STATE:
				if (resolve) return getRedefinedState();
				return basicGetRedefinedState();
			case MagicDrawStatechartsEffectivePackage.STATE__SUBMACHINE:
				if (resolve) return getSubmachine();
				return basicGetSubmachine();
			case MagicDrawStatechartsEffectivePackage.STATE__REGION:
				return getRegion();
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
			case MagicDrawStatechartsEffectivePackage.STATE__CONTAINER:
				setContainer((Region)newValue);
				return;
			case MagicDrawStatechartsEffectivePackage.STATE__CONNECTION:
				getConnection().clear();
				getConnection().addAll((Collection<? extends ConnectionPointReference>)newValue);
				return;
			case MagicDrawStatechartsEffectivePackage.STATE__CONNECTION_POINT:
				getConnectionPoint().clear();
				getConnectionPoint().addAll((Collection<? extends Pseudostate>)newValue);
				return;
			case MagicDrawStatechartsEffectivePackage.STATE__REDEFINED_STATE:
				setRedefinedState((State)newValue);
				return;
			case MagicDrawStatechartsEffectivePackage.STATE__SUBMACHINE:
				setSubmachine((StateMachine)newValue);
				return;
			case MagicDrawStatechartsEffectivePackage.STATE__REGION:
				getRegion().clear();
				getRegion().addAll((Collection<? extends Region>)newValue);
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
			case MagicDrawStatechartsEffectivePackage.STATE__CONTAINER:
				setContainer((Region)null);
				return;
			case MagicDrawStatechartsEffectivePackage.STATE__CONNECTION:
				getConnection().clear();
				return;
			case MagicDrawStatechartsEffectivePackage.STATE__CONNECTION_POINT:
				getConnectionPoint().clear();
				return;
			case MagicDrawStatechartsEffectivePackage.STATE__REDEFINED_STATE:
				setRedefinedState((State)null);
				return;
			case MagicDrawStatechartsEffectivePackage.STATE__SUBMACHINE:
				setSubmachine((StateMachine)null);
				return;
			case MagicDrawStatechartsEffectivePackage.STATE__REGION:
				getRegion().clear();
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
			case MagicDrawStatechartsEffectivePackage.STATE__CONTAINER:
				return getContainer() != null;
			case MagicDrawStatechartsEffectivePackage.STATE__INCOMING:
				return !getIncoming().isEmpty();
			case MagicDrawStatechartsEffectivePackage.STATE__OUTGOING:
				return !getOutgoing().isEmpty();
			case MagicDrawStatechartsEffectivePackage.STATE__CONNECTION:
				return connection != null && !connection.isEmpty();
			case MagicDrawStatechartsEffectivePackage.STATE__CONNECTION_POINT:
				return connectionPoint != null && !connectionPoint.isEmpty();
			case MagicDrawStatechartsEffectivePackage.STATE__IS_COMPOSITE:
				return isIsComposite() != IS_COMPOSITE_EDEFAULT;
			case MagicDrawStatechartsEffectivePackage.STATE__IS_ORTHOGONAL:
				return isIsOrthogonal() != IS_ORTHOGONAL_EDEFAULT;
			case MagicDrawStatechartsEffectivePackage.STATE__IS_SIMPLE:
				return isIsSimple() != IS_SIMPLE_EDEFAULT;
			case MagicDrawStatechartsEffectivePackage.STATE__IS_SUBMACHINE_STATE:
				return isIsSubmachineState() != IS_SUBMACHINE_STATE_EDEFAULT;
			case MagicDrawStatechartsEffectivePackage.STATE__REDEFINED_STATE:
				return redefinedState != null;
			case MagicDrawStatechartsEffectivePackage.STATE__SUBMACHINE:
				return submachine != null;
			case MagicDrawStatechartsEffectivePackage.STATE__REGION:
				return region != null && !region.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == Vertex.class) {
			switch (derivedFeatureID) {
				case MagicDrawStatechartsEffectivePackage.STATE__CONTAINER: return MagicDrawStatechartsEffectivePackage.VERTEX__CONTAINER;
				case MagicDrawStatechartsEffectivePackage.STATE__INCOMING: return MagicDrawStatechartsEffectivePackage.VERTEX__INCOMING;
				case MagicDrawStatechartsEffectivePackage.STATE__OUTGOING: return MagicDrawStatechartsEffectivePackage.VERTEX__OUTGOING;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == Vertex.class) {
			switch (baseFeatureID) {
				case MagicDrawStatechartsEffectivePackage.VERTEX__CONTAINER: return MagicDrawStatechartsEffectivePackage.STATE__CONTAINER;
				case MagicDrawStatechartsEffectivePackage.VERTEX__INCOMING: return MagicDrawStatechartsEffectivePackage.STATE__INCOMING;
				case MagicDrawStatechartsEffectivePackage.VERTEX__OUTGOING: return MagicDrawStatechartsEffectivePackage.STATE__OUTGOING;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedOperationID(int baseOperationID, Class<?> baseClass) {
		if (baseClass == Vertex.class) {
			switch (baseOperationID) {
				case MagicDrawStatechartsEffectivePackage.VERTEX___CONTAINING_STATE_MACHINE: return MagicDrawStatechartsEffectivePackage.STATE___CONTAINING_STATE_MACHINE;
				case MagicDrawStatechartsEffectivePackage.VERTEX___GET_INCOMINGS: return MagicDrawStatechartsEffectivePackage.STATE___GET_INCOMINGS;
				case MagicDrawStatechartsEffectivePackage.VERTEX___GET_OUTGOINGS: return MagicDrawStatechartsEffectivePackage.STATE___GET_OUTGOINGS;
				default: return -1;
			}
		}
		return super.eDerivedOperationID(baseOperationID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case MagicDrawStatechartsEffectivePackage.STATE___ENTRY_OR_EXIT__DIAGNOSTICCHAIN_MAP:
				return entry_or_exit((DiagnosticChain)arguments.get(0), (Map<Object, Object>)arguments.get(1));
			case MagicDrawStatechartsEffectivePackage.STATE___COMPOSITE_STATES__DIAGNOSTICCHAIN_MAP:
				return composite_states((DiagnosticChain)arguments.get(0), (Map<Object, Object>)arguments.get(1));
			case MagicDrawStatechartsEffectivePackage.STATE___DESTINATIONS_OR_SOURCES_OF_TRANSITIONS__DIAGNOSTICCHAIN_MAP:
				return destinations_or_sources_of_transitions((DiagnosticChain)arguments.get(0), (Map<Object, Object>)arguments.get(1));
			case MagicDrawStatechartsEffectivePackage.STATE___SUBMACHINE_OR_REGIONS__DIAGNOSTICCHAIN_MAP:
				return submachine_or_regions((DiagnosticChain)arguments.get(0), (Map<Object, Object>)arguments.get(1));
			case MagicDrawStatechartsEffectivePackage.STATE___SUBMACHINE_STATES__DIAGNOSTICCHAIN_MAP:
				return submachine_states((DiagnosticChain)arguments.get(0), (Map<Object, Object>)arguments.get(1));
			case MagicDrawStatechartsEffectivePackage.STATE___IS_COMPOSITE:
				return isComposite();
			case MagicDrawStatechartsEffectivePackage.STATE___IS_ORTHOGONAL:
				return isOrthogonal();
			case MagicDrawStatechartsEffectivePackage.STATE___IS_REDEFINITION_CONTEXT_VALID__STATE:
				return isRedefinitionContextValid((State)arguments.get(0));
			case MagicDrawStatechartsEffectivePackage.STATE___IS_SIMPLE:
				return isSimple();
			case MagicDrawStatechartsEffectivePackage.STATE___IS_SUBMACHINE_STATE:
				return isSubmachineState();
			case MagicDrawStatechartsEffectivePackage.STATE___REDEFINITION_CONTEXT:
				redefinitionContext();
				return null;
			case MagicDrawStatechartsEffectivePackage.STATE___CONTAINING_STATE_MACHINE:
				return containingStateMachine();
			case MagicDrawStatechartsEffectivePackage.STATE___GET_INCOMINGS:
				return getIncomings();
			case MagicDrawStatechartsEffectivePackage.STATE___GET_OUTGOINGS:
				return getOutgoings();
		}
		return super.eInvoke(operationID, arguments);
	}

} //StateImpl
