/**
 */
package de.imotep.core.behavior.de_imotep_core_behavior.provider;

import de.imotep.core.behavior.de_imotep_core_behavior.util.De_imotep_core_behaviorAdapterFactory;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.edit.provider.ChangeNotifier;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IChangeNotifier;
import org.eclipse.emf.edit.provider.IDisposable;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;

/**
 * This is the factory that is used to provide the interfaces needed to support Viewers.
 * The adapters generated by this factory convert EMF adapter notifications into calls to {@link #fireNotifyChanged fireNotifyChanged}.
 * The adapters also support Eclipse property sheets.
 * Note that most of the adapters are shared among multiple instances.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class De_imotep_core_behaviorItemProviderAdapterFactory extends De_imotep_core_behaviorAdapterFactory implements ComposeableAdapterFactory, IChangeNotifier, IDisposable {
	/**
	 * This keeps track of the root adapter factory that delegates to this adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ComposedAdapterFactory parentAdapterFactory;

	/**
	 * This is used to implement {@link org.eclipse.emf.edit.provider.IChangeNotifier}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IChangeNotifier changeNotifier = new ChangeNotifier();

	/**
	 * This keeps track of all the supported types checked by {@link #isFactoryForType isFactoryForType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Collection<Object> supportedTypes = new ArrayList<Object>();

	/**
	 * This constructs an instance.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public De_imotep_core_behaviorItemProviderAdapterFactory() {
		supportedTypes.add(IEditingDomainItemProvider.class);
		supportedTypes.add(IStructuredItemContentProvider.class);
		supportedTypes.add(ITreeItemContentProvider.class);
		supportedTypes.add(IItemLabelProvider.class);
		supportedTypes.add(IItemPropertySource.class);
	}

	/**
	 * This keeps track of the one adapter used for all {@link de.imotep.core.behavior.de_imotep_core_behavior.MStateMachine} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MStateMachineItemProvider mStateMachineItemProvider;

	/**
	 * This creates an adapter for a {@link de.imotep.core.behavior.de_imotep_core_behavior.MStateMachine}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createMStateMachineAdapter() {
		if (mStateMachineItemProvider == null) {
			mStateMachineItemProvider = new MStateMachineItemProvider(this);
		}

		return mStateMachineItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link de.imotep.core.behavior.de_imotep_core_behavior.MFinalState} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MFinalStateItemProvider mFinalStateItemProvider;

	/**
	 * This creates an adapter for a {@link de.imotep.core.behavior.de_imotep_core_behavior.MFinalState}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createMFinalStateAdapter() {
		if (mFinalStateItemProvider == null) {
			mFinalStateItemProvider = new MFinalStateItemProvider(this);
		}

		return mFinalStateItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link de.imotep.core.behavior.de_imotep_core_behavior.MErrorState} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MErrorStateItemProvider mErrorStateItemProvider;

	/**
	 * This creates an adapter for a {@link de.imotep.core.behavior.de_imotep_core_behavior.MErrorState}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createMErrorStateAdapter() {
		if (mErrorStateItemProvider == null) {
			mErrorStateItemProvider = new MErrorStateItemProvider(this);
		}

		return mErrorStateItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link de.imotep.core.behavior.de_imotep_core_behavior.MStateMachineState} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MStateMachineStateItemProvider mStateMachineStateItemProvider;

	/**
	 * This creates an adapter for a {@link de.imotep.core.behavior.de_imotep_core_behavior.MStateMachineState}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createMStateMachineStateAdapter() {
		if (mStateMachineStateItemProvider == null) {
			mStateMachineStateItemProvider = new MStateMachineStateItemProvider(this);
		}

		return mStateMachineStateItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link de.imotep.core.behavior.de_imotep_core_behavior.MTerminateState} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MTerminateStateItemProvider mTerminateStateItemProvider;

	/**
	 * This creates an adapter for a {@link de.imotep.core.behavior.de_imotep_core_behavior.MTerminateState}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createMTerminateStateAdapter() {
		if (mTerminateStateItemProvider == null) {
			mTerminateStateItemProvider = new MTerminateStateItemProvider(this);
		}

		return mTerminateStateItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link de.imotep.core.behavior.de_imotep_core_behavior.MTransition} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MTransitionItemProvider mTransitionItemProvider;

	/**
	 * This creates an adapter for a {@link de.imotep.core.behavior.de_imotep_core_behavior.MTransition}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createMTransitionAdapter() {
		if (mTransitionItemProvider == null) {
			mTransitionItemProvider = new MTransitionItemProvider(this);
		}

		return mTransitionItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link de.imotep.core.behavior.de_imotep_core_behavior.MInitialState} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MInitialStateItemProvider mInitialStateItemProvider;

	/**
	 * This creates an adapter for a {@link de.imotep.core.behavior.de_imotep_core_behavior.MInitialState}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createMInitialStateAdapter() {
		if (mInitialStateItemProvider == null) {
			mInitialStateItemProvider = new MInitialStateItemProvider(this);
		}

		return mInitialStateItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link de.imotep.core.behavior.de_imotep_core_behavior.MState} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MStateItemProvider mStateItemProvider;

	/**
	 * This creates an adapter for a {@link de.imotep.core.behavior.de_imotep_core_behavior.MState}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createMStateAdapter() {
		if (mStateItemProvider == null) {
			mStateItemProvider = new MStateItemProvider(this);
		}

		return mStateItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link de.imotep.core.behavior.de_imotep_core_behavior.MEvent} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MEventItemProvider mEventItemProvider;

	/**
	 * This creates an adapter for a {@link de.imotep.core.behavior.de_imotep_core_behavior.MEvent}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createMEventAdapter() {
		if (mEventItemProvider == null) {
			mEventItemProvider = new MEventItemProvider(this);
		}

		return mEventItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link de.imotep.core.behavior.de_imotep_core_behavior.MHistoryState} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MHistoryStateItemProvider mHistoryStateItemProvider;

	/**
	 * This creates an adapter for a {@link de.imotep.core.behavior.de_imotep_core_behavior.MHistoryState}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createMHistoryStateAdapter() {
		if (mHistoryStateItemProvider == null) {
			mHistoryStateItemProvider = new MHistoryStateItemProvider(this);
		}

		return mHistoryStateItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link de.imotep.core.behavior.de_imotep_core_behavior.MGuard} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MGuardItemProvider mGuardItemProvider;

	/**
	 * This creates an adapter for a {@link de.imotep.core.behavior.de_imotep_core_behavior.MGuard}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createMGuardAdapter() {
		if (mGuardItemProvider == null) {
			mGuardItemProvider = new MGuardItemProvider(this);
		}

		return mGuardItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link de.imotep.core.behavior.de_imotep_core_behavior.MRegion} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MRegionItemProvider mRegionItemProvider;

	/**
	 * This creates an adapter for a {@link de.imotep.core.behavior.de_imotep_core_behavior.MRegion}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createMRegionAdapter() {
		if (mRegionItemProvider == null) {
			mRegionItemProvider = new MRegionItemProvider(this);
		}

		return mRegionItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link de.imotep.core.behavior.de_imotep_core_behavior.MAction} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MActionItemProvider mActionItemProvider;

	/**
	 * This creates an adapter for a {@link de.imotep.core.behavior.de_imotep_core_behavior.MAction}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createMActionAdapter() {
		if (mActionItemProvider == null) {
			mActionItemProvider = new MActionItemProvider(this);
		}

		return mActionItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link de.imotep.core.behavior.de_imotep_core_behavior.MCodeFragment} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MCodeFragmentItemProvider mCodeFragmentItemProvider;

	/**
	 * This creates an adapter for a {@link de.imotep.core.behavior.de_imotep_core_behavior.MCodeFragment}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createMCodeFragmentAdapter() {
		if (mCodeFragmentItemProvider == null) {
			mCodeFragmentItemProvider = new MCodeFragmentItemProvider(this);
		}

		return mCodeFragmentItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link de.imotep.core.behavior.de_imotep_core_behavior.MStateGroup} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MStateGroupItemProvider mStateGroupItemProvider;

	/**
	 * This creates an adapter for a {@link de.imotep.core.behavior.de_imotep_core_behavior.MStateGroup}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createMStateGroupAdapter() {
		if (mStateGroupItemProvider == null) {
			mStateGroupItemProvider = new MStateGroupItemProvider(this);
		}

		return mStateGroupItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link de.imotep.core.behavior.de_imotep_core_behavior.MErrorTransition} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MErrorTransitionItemProvider mErrorTransitionItemProvider;

	/**
	 * This creates an adapter for a {@link de.imotep.core.behavior.de_imotep_core_behavior.MErrorTransition}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createMErrorTransitionAdapter() {
		if (mErrorTransitionItemProvider == null) {
			mErrorTransitionItemProvider = new MErrorTransitionItemProvider(this);
		}

		return mErrorTransitionItemProvider;
	}

	/**
	 * This returns the root adapter factory that contains this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComposeableAdapterFactory getRootAdapterFactory() {
		return parentAdapterFactory == null ? this : parentAdapterFactory.getRootAdapterFactory();
	}

	/**
	 * This sets the composed adapter factory that contains this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParentAdapterFactory(ComposedAdapterFactory parentAdapterFactory) {
		this.parentAdapterFactory = parentAdapterFactory;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object type) {
		return supportedTypes.contains(type) || super.isFactoryForType(type);
	}

	/**
	 * This implementation substitutes the factory itself as the key for the adapter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter adapt(Notifier notifier, Object type) {
		return super.adapt(notifier, this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object adapt(Object object, Object type) {
		if (isFactoryForType(type)) {
			Object adapter = super.adapt(object, type);
			if (!(type instanceof Class<?>) || (((Class<?>)type).isInstance(adapter))) {
				return adapter;
			}
		}

		return null;
	}

	/**
	 * This adds a listener.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void addListener(INotifyChangedListener notifyChangedListener) {
		changeNotifier.addListener(notifyChangedListener);
	}

	/**
	 * This removes a listener.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void removeListener(INotifyChangedListener notifyChangedListener) {
		changeNotifier.removeListener(notifyChangedListener);
	}

	/**
	 * This delegates to {@link #changeNotifier} and to {@link #parentAdapterFactory}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void fireNotifyChanged(Notification notification) {
		changeNotifier.fireNotifyChanged(notification);

		if (parentAdapterFactory != null) {
			parentAdapterFactory.fireNotifyChanged(notification);
		}
	}

	/**
	 * This disposes all of the item providers created by this factory. 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void dispose() {
		if (mStateMachineItemProvider != null) mStateMachineItemProvider.dispose();
		if (mFinalStateItemProvider != null) mFinalStateItemProvider.dispose();
		if (mErrorStateItemProvider != null) mErrorStateItemProvider.dispose();
		if (mStateMachineStateItemProvider != null) mStateMachineStateItemProvider.dispose();
		if (mTerminateStateItemProvider != null) mTerminateStateItemProvider.dispose();
		if (mTransitionItemProvider != null) mTransitionItemProvider.dispose();
		if (mInitialStateItemProvider != null) mInitialStateItemProvider.dispose();
		if (mStateItemProvider != null) mStateItemProvider.dispose();
		if (mEventItemProvider != null) mEventItemProvider.dispose();
		if (mHistoryStateItemProvider != null) mHistoryStateItemProvider.dispose();
		if (mGuardItemProvider != null) mGuardItemProvider.dispose();
		if (mRegionItemProvider != null) mRegionItemProvider.dispose();
		if (mActionItemProvider != null) mActionItemProvider.dispose();
		if (mCodeFragmentItemProvider != null) mCodeFragmentItemProvider.dispose();
		if (mStateGroupItemProvider != null) mStateGroupItemProvider.dispose();
		if (mErrorTransitionItemProvider != null) mErrorTransitionItemProvider.dispose();
	}

}
