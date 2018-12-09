/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.difference.asymmetric.provider;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemColorProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.AsymmetricFactory;
import org.sidiff.difference.asymmetric.AsymmetricPackage;
import org.sidiff.difference.asymmetric.util.TopologicalSorter;
import org.sidiff.editrule.rulebase.RulebaseFactory;

/**
 * This is the item provider adapter for a {@link org.sidiff.difference.asymmetric.AsymmetricDifference} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class AsymmetricDifferenceItemProvider
	extends ItemProviderAdapter
	implements
		IEditingDomainItemProvider, IStructuredItemContentProvider, ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource, IItemColorProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AsymmetricDifferenceItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * @generated NOT
	 */
	@Override
	public Collection<?> getChildren(Object object) {		
		AsymmetricDifference difference = (AsymmetricDifference) object;
		List<Object> children = new ArrayList<Object>();
		children.addAll(TopologicalSorter.sort(difference.getOperationInvocations()));
		children.addAll(difference.getParameterMappings());
		if(!difference.getDepContainers().isEmpty()){
			children.add(new DependenciesItemProvider(adapterFactory, difference));
		}
		return children;
	}

	/**
	 * This returns the property descriptors for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addOriginModelPropertyDescriptor(object);
			addChangedModelPropertyDescriptor(object);
			addSymmetricDifferencePropertyDescriptor(object);
			addUriOriginModelPropertyDescriptor(object);
			addUriChangedModelPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Origin Model feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addOriginModelPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_AsymmetricDifference_originModel_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_AsymmetricDifference_originModel_feature", "_UI_AsymmetricDifference_type"),
				 AsymmetricPackage.Literals.ASYMMETRIC_DIFFERENCE__ORIGIN_MODEL,
				 false,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Changed Model feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addChangedModelPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_AsymmetricDifference_changedModel_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_AsymmetricDifference_changedModel_feature", "_UI_AsymmetricDifference_type"),
				 AsymmetricPackage.Literals.ASYMMETRIC_DIFFERENCE__CHANGED_MODEL,
				 false,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Symmetric Difference feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addSymmetricDifferencePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_AsymmetricDifference_symmetricDifference_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_AsymmetricDifference_symmetricDifference_feature", "_UI_AsymmetricDifference_type"),
				 AsymmetricPackage.Literals.ASYMMETRIC_DIFFERENCE__SYMMETRIC_DIFFERENCE,
				 true,
				 false,
				 true,
				 null,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Uri Origin Model feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addUriOriginModelPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_AsymmetricDifference_uriOriginModel_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_AsymmetricDifference_uriOriginModel_feature", "_UI_AsymmetricDifference_type"),
				 AsymmetricPackage.Literals.ASYMMETRIC_DIFFERENCE__URI_ORIGIN_MODEL,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Uri Changed Model feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addUriChangedModelPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_AsymmetricDifference_uriChangedModel_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_AsymmetricDifference_uriChangedModel_feature", "_UI_AsymmetricDifference_type"),
				 AsymmetricPackage.Literals.ASYMMETRIC_DIFFERENCE__URI_CHANGED_MODEL,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
	 * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(AsymmetricPackage.Literals.ASYMMETRIC_DIFFERENCE__OPERATION_INVOCATIONS);
			childrenFeatures.add(AsymmetricPackage.Literals.ASYMMETRIC_DIFFERENCE__DEP_CONTAINERS);
			childrenFeatures.add(AsymmetricPackage.Literals.ASYMMETRIC_DIFFERENCE__PARAMETER_MAPPINGS);
			childrenFeatures.add(AsymmetricPackage.Literals.ASYMMETRIC_DIFFERENCE__EXECUTIONS);
			childrenFeatures.add(AsymmetricPackage.Literals.ASYMMETRIC_DIFFERENCE__RULEBASE);
		}
		return childrenFeatures;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EStructuralFeature getChildFeature(Object object, Object child) {
		// Check the type of the specified child object and return the proper feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

	/**
	 * This returns AsymmetricDifference.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/AsymmetricDifference"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public String getText(Object object) {
//		Resource labelValue = ((AsymmetricDifference)object).getOriginModel();
//		String label = labelValue == null ? null : labelValue.toString();
//		return label == null || label.length() == 0 ?
//			getString("_UI_AsymmetricDifference_type") :
//			getString("_UI_AsymmetricDifference_type") + " " + label;
		// TODO: Show Resources
		return getString("_UI_AsymmetricDifference_type");
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(AsymmetricDifference.class)) {
			case AsymmetricPackage.ASYMMETRIC_DIFFERENCE__ORIGIN_MODEL:
			case AsymmetricPackage.ASYMMETRIC_DIFFERENCE__CHANGED_MODEL:
			case AsymmetricPackage.ASYMMETRIC_DIFFERENCE__URI_ORIGIN_MODEL:
			case AsymmetricPackage.ASYMMETRIC_DIFFERENCE__URI_CHANGED_MODEL:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case AsymmetricPackage.ASYMMETRIC_DIFFERENCE__OPERATION_INVOCATIONS:
			case AsymmetricPackage.ASYMMETRIC_DIFFERENCE__DEP_CONTAINERS:
			case AsymmetricPackage.ASYMMETRIC_DIFFERENCE__PARAMETER_MAPPINGS:
			case AsymmetricPackage.ASYMMETRIC_DIFFERENCE__EXECUTIONS:
			case AsymmetricPackage.ASYMMETRIC_DIFFERENCE__RULEBASE:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
				return;
		}
		super.notifyChanged(notification);
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
	 * that can be created under this object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors.add
			(createChildParameter
				(AsymmetricPackage.Literals.ASYMMETRIC_DIFFERENCE__OPERATION_INVOCATIONS,
				 AsymmetricFactory.eINSTANCE.createOperationInvocation()));

		newChildDescriptors.add
			(createChildParameter
				(AsymmetricPackage.Literals.ASYMMETRIC_DIFFERENCE__DEP_CONTAINERS,
				 AsymmetricFactory.eINSTANCE.createDependencyContainer()));

		newChildDescriptors.add
			(createChildParameter
				(AsymmetricPackage.Literals.ASYMMETRIC_DIFFERENCE__PARAMETER_MAPPINGS,
				 AsymmetricFactory.eINSTANCE.createParameterMapping()));

		newChildDescriptors.add
			(createChildParameter
				(AsymmetricPackage.Literals.ASYMMETRIC_DIFFERENCE__EXECUTIONS,
				 AsymmetricFactory.eINSTANCE.createOperationInvocation()));

		newChildDescriptors.add
			(createChildParameter
				(AsymmetricPackage.Literals.ASYMMETRIC_DIFFERENCE__EXECUTIONS,
				 AsymmetricFactory.eINSTANCE.createParallelExecution()));

		newChildDescriptors.add
			(createChildParameter
				(AsymmetricPackage.Literals.ASYMMETRIC_DIFFERENCE__EXECUTIONS,
				 AsymmetricFactory.eINSTANCE.createSequentialExecution()));

		newChildDescriptors.add
			(createChildParameter
				(AsymmetricPackage.Literals.ASYMMETRIC_DIFFERENCE__RULEBASE,
				 RulebaseFactory.eINSTANCE.createRuleBase()));
	}

	/**
	 * This returns the label text for {@link org.eclipse.emf.edit.command.CreateChildCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getCreateChildText(Object owner, Object feature, Object child, Collection<?> selection) {
		Object childFeature = feature;
		Object childObject = child;

		boolean qualify =
			childFeature == AsymmetricPackage.Literals.ASYMMETRIC_DIFFERENCE__OPERATION_INVOCATIONS ||
			childFeature == AsymmetricPackage.Literals.ASYMMETRIC_DIFFERENCE__EXECUTIONS;

		if (qualify) {
			return getString
				("_UI_CreateChild_text2",
				 new Object[] { getTypeText(childObject), getFeatureText(childFeature), getTypeText(owner) });
		}
		return super.getCreateChildText(owner, feature, child, selection);
	}

	/**
	 * Return the resource locator for this item provider's resources.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return AsymmetricEditPlugin.INSTANCE;
	}
}
