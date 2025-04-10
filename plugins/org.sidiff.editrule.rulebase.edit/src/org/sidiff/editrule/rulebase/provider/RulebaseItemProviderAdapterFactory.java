/**
 */
package org.sidiff.editrule.rulebase.provider;

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
import org.sidiff.editrule.rulebase.util.RulebaseAdapterFactory;

/**
 * This is the factory that is used to provide the interfaces needed to support Viewers.
 * The adapters generated by this factory convert EMF adapter notifications into calls to {@link #fireNotifyChanged fireNotifyChanged}.
 * The adapters also support Eclipse property sheets.
 * Note that most of the adapters are shared among multiple instances.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class RulebaseItemProviderAdapterFactory extends RulebaseAdapterFactory implements ComposeableAdapterFactory, IChangeNotifier, IDisposable {
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
	public RulebaseItemProviderAdapterFactory() {
		supportedTypes.add(IEditingDomainItemProvider.class);
		supportedTypes.add(IStructuredItemContentProvider.class);
		supportedTypes.add(ITreeItemContentProvider.class);
		supportedTypes.add(IItemLabelProvider.class);
		supportedTypes.add(IItemPropertySource.class);
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.sidiff.editrule.rulebase.RuleBase} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RuleBaseItemProvider ruleBaseItemProvider;

	/**
	 * This creates an adapter for a {@link org.sidiff.editrule.rulebase.RuleBase}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createRuleBaseAdapter() {
		if (ruleBaseItemProvider == null) {
			ruleBaseItemProvider = new RuleBaseItemProvider(this);
		}

		return ruleBaseItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.sidiff.editrule.rulebase.EditRule} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EditRuleItemProvider editRuleItemProvider;

	/**
	 * This creates an adapter for a {@link org.sidiff.editrule.rulebase.EditRule}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createEditRuleAdapter() {
		if (editRuleItemProvider == null) {
			editRuleItemProvider = new EditRuleItemProvider(this);
		}

		return editRuleItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.sidiff.editrule.rulebase.RuleBaseItem} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RuleBaseItemItemProvider ruleBaseItemItemProvider;

	/**
	 * This creates an adapter for a {@link org.sidiff.editrule.rulebase.RuleBaseItem}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createRuleBaseItemAdapter() {
		if (ruleBaseItemItemProvider == null) {
			ruleBaseItemItemProvider = new RuleBaseItemItemProvider(this);
		}

		return ruleBaseItemItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.sidiff.editrule.rulebase.PotentialNodeDependency} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PotentialNodeDependencyItemProvider potentialNodeDependencyItemProvider;

	/**
	 * This creates an adapter for a {@link org.sidiff.editrule.rulebase.PotentialNodeDependency}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createPotentialNodeDependencyAdapter() {
		if (potentialNodeDependencyItemProvider == null) {
			potentialNodeDependencyItemProvider = new PotentialNodeDependencyItemProvider(this);
		}

		return potentialNodeDependencyItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.sidiff.editrule.rulebase.PotentialEdgeDependency} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PotentialEdgeDependencyItemProvider potentialEdgeDependencyItemProvider;

	/**
	 * This creates an adapter for a {@link org.sidiff.editrule.rulebase.PotentialEdgeDependency}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createPotentialEdgeDependencyAdapter() {
		if (potentialEdgeDependencyItemProvider == null) {
			potentialEdgeDependencyItemProvider = new PotentialEdgeDependencyItemProvider(this);
		}

		return potentialEdgeDependencyItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.sidiff.editrule.rulebase.PotentialAttributeDependency} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PotentialAttributeDependencyItemProvider potentialAttributeDependencyItemProvider;

	/**
	 * This creates an adapter for a {@link org.sidiff.editrule.rulebase.PotentialAttributeDependency}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createPotentialAttributeDependencyAdapter() {
		if (potentialAttributeDependencyItemProvider == null) {
			potentialAttributeDependencyItemProvider = new PotentialAttributeDependencyItemProvider(this);
		}

		return potentialAttributeDependencyItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.sidiff.editrule.rulebase.Parameter} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ParameterItemProvider parameterItemProvider;

	/**
	 * This creates an adapter for a {@link org.sidiff.editrule.rulebase.Parameter}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createParameterAdapter() {
		if (parameterItemProvider == null) {
			parameterItemProvider = new ParameterItemProvider(this);
		}

		return parameterItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.sidiff.editrule.rulebase.Classification} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ClassificationItemProvider classificationItemProvider;

	/**
	 * This creates an adapter for a {@link org.sidiff.editrule.rulebase.Classification}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createClassificationAdapter() {
		if (classificationItemProvider == null) {
			classificationItemProvider = new ClassificationItemProvider(this);
		}

		return classificationItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.sidiff.editrule.rulebase.PotentialNodeConflict} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PotentialNodeConflictItemProvider potentialNodeConflictItemProvider;

	/**
	 * This creates an adapter for a {@link org.sidiff.editrule.rulebase.PotentialNodeConflict}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createPotentialNodeConflictAdapter() {
		if (potentialNodeConflictItemProvider == null) {
			potentialNodeConflictItemProvider = new PotentialNodeConflictItemProvider(this);
		}

		return potentialNodeConflictItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.sidiff.editrule.rulebase.PotentialEdgeConflict} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PotentialEdgeConflictItemProvider potentialEdgeConflictItemProvider;

	/**
	 * This creates an adapter for a {@link org.sidiff.editrule.rulebase.PotentialEdgeConflict}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createPotentialEdgeConflictAdapter() {
		if (potentialEdgeConflictItemProvider == null) {
			potentialEdgeConflictItemProvider = new PotentialEdgeConflictItemProvider(this);
		}

		return potentialEdgeConflictItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.sidiff.editrule.rulebase.PotentialAttributeConflict} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PotentialAttributeConflictItemProvider potentialAttributeConflictItemProvider;

	/**
	 * This creates an adapter for a {@link org.sidiff.editrule.rulebase.PotentialAttributeConflict}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createPotentialAttributeConflictAdapter() {
		if (potentialAttributeConflictItemProvider == null) {
			potentialAttributeConflictItemProvider = new PotentialAttributeConflictItemProvider(this);
		}

		return potentialAttributeConflictItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.sidiff.editrule.rulebase.PotentialDanglingEdgeConflict} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PotentialDanglingEdgeConflictItemProvider potentialDanglingEdgeConflictItemProvider;

	/**
	 * This creates an adapter for a {@link org.sidiff.editrule.rulebase.PotentialDanglingEdgeConflict}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createPotentialDanglingEdgeConflictAdapter() {
		if (potentialDanglingEdgeConflictItemProvider == null) {
			potentialDanglingEdgeConflictItemProvider = new PotentialDanglingEdgeConflictItemProvider(this);
		}

		return potentialDanglingEdgeConflictItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.sidiff.editrule.rulebase.PotentialDanglingEdgeDependency} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PotentialDanglingEdgeDependencyItemProvider potentialDanglingEdgeDependencyItemProvider;

	/**
	 * This creates an adapter for a {@link org.sidiff.editrule.rulebase.PotentialDanglingEdgeDependency}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createPotentialDanglingEdgeDependencyAdapter() {
		if (potentialDanglingEdgeDependencyItemProvider == null) {
			potentialDanglingEdgeDependencyItemProvider = new PotentialDanglingEdgeDependencyItemProvider(this);
		}

		return potentialDanglingEdgeDependencyItemProvider;
	}

	/**
	 * This returns the root adapter factory that contains this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ComposeableAdapterFactory getRootAdapterFactory() {
		return parentAdapterFactory == null ? this : parentAdapterFactory.getRootAdapterFactory();
	}

	/**
	 * This sets the composed adapter factory that contains this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
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
	@Override
	public void addListener(INotifyChangedListener notifyChangedListener) {
		changeNotifier.addListener(notifyChangedListener);
	}

	/**
	 * This removes a listener.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void removeListener(INotifyChangedListener notifyChangedListener) {
		changeNotifier.removeListener(notifyChangedListener);
	}

	/**
	 * This delegates to {@link #changeNotifier} and to {@link #parentAdapterFactory}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
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
	@Override
	public void dispose() {
		if (ruleBaseItemProvider != null) ruleBaseItemProvider.dispose();
		if (editRuleItemProvider != null) editRuleItemProvider.dispose();
		if (ruleBaseItemItemProvider != null) ruleBaseItemItemProvider.dispose();
		if (potentialNodeDependencyItemProvider != null) potentialNodeDependencyItemProvider.dispose();
		if (potentialEdgeDependencyItemProvider != null) potentialEdgeDependencyItemProvider.dispose();
		if (potentialAttributeDependencyItemProvider != null) potentialAttributeDependencyItemProvider.dispose();
		if (parameterItemProvider != null) parameterItemProvider.dispose();
		if (classificationItemProvider != null) classificationItemProvider.dispose();
		if (potentialNodeConflictItemProvider != null) potentialNodeConflictItemProvider.dispose();
		if (potentialEdgeConflictItemProvider != null) potentialEdgeConflictItemProvider.dispose();
		if (potentialAttributeConflictItemProvider != null) potentialAttributeConflictItemProvider.dispose();
		if (potentialDanglingEdgeConflictItemProvider != null) potentialDanglingEdgeConflictItemProvider.dispose();
		if (potentialDanglingEdgeDependencyItemProvider != null) potentialDanglingEdgeDependencyItemProvider.dispose();
	}

}
