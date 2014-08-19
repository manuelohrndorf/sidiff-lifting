/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.difference.rulebase.provider;


import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.sidiff.difference.rulebase.RuleBase;
import org.sidiff.difference.rulebase.RulebaseFactory;
import org.sidiff.difference.rulebase.RulebasePackage;

/**
 * This is the item provider adapter for a {@link org.sidiff.difference.rulebase.RuleBase} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class RuleBaseItemProvider
	extends ItemProviderAdapter
	implements
		IEditingDomainItemProvider,
		IStructuredItemContentProvider,
		ITreeItemContentProvider,
		IItemLabelProvider,
		IItemPropertySource {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RuleBaseItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
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

			addNamePropertyDescriptor(object);
			addDocumentTypesPropertyDescriptor(object);
			addCharacteristicDocumentTypePropertyDescriptor(object);
			addEditRulesPropertyDescriptor(object);
			addRecognitionRulesPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Name feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addNamePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_RuleBase_name_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_RuleBase_name_feature", "_UI_RuleBase_type"),
				 RulebasePackage.Literals.RULE_BASE__NAME,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Document Types feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addDocumentTypesPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_RuleBase_documentTypes_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_RuleBase_documentTypes_feature", "_UI_RuleBase_type"),
				 RulebasePackage.Literals.RULE_BASE__DOCUMENT_TYPES,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Characteristic Document Type feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addCharacteristicDocumentTypePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_RuleBase_characteristicDocumentType_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_RuleBase_characteristicDocumentType_feature", "_UI_RuleBase_type"),
				 RulebasePackage.Literals.RULE_BASE__CHARACTERISTIC_DOCUMENT_TYPE,
				 false,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Edit Rules feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addEditRulesPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_RuleBase_editRules_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_RuleBase_editRules_feature", "_UI_RuleBase_type"),
				 RulebasePackage.Literals.RULE_BASE__EDIT_RULES,
				 false,
				 false,
				 false,
				 null,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Recognition Rules feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addRecognitionRulesPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_RuleBase_recognitionRules_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_RuleBase_recognitionRules_feature", "_UI_RuleBase_type"),
				 RulebasePackage.Literals.RULE_BASE__RECOGNITION_RULES,
				 false,
				 false,
				 false,
				 null,
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
			childrenFeatures.add(RulebasePackage.Literals.RULE_BASE__ITEMS);
			childrenFeatures.add(RulebasePackage.Literals.RULE_BASE__POTENTIAL_NODE_DEPENDENCIES);
			childrenFeatures.add(RulebasePackage.Literals.RULE_BASE__POTENTIAL_EDGE_DEPENDENCIES);
			childrenFeatures.add(RulebasePackage.Literals.RULE_BASE__POTENTIAL_ATTRIBUTE_DEPENDENCIES);
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
	 * This returns RuleBase.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/RuleBase"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((RuleBase)object).getName();
		return label == null || label.length() == 0 ?
			getString("_UI_RuleBase_type") :
			getString("_UI_RuleBase_type") + " " + label;
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

		switch (notification.getFeatureID(RuleBase.class)) {
			case RulebasePackage.RULE_BASE__NAME:
			case RulebasePackage.RULE_BASE__DOCUMENT_TYPES:
			case RulebasePackage.RULE_BASE__CHARACTERISTIC_DOCUMENT_TYPE:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case RulebasePackage.RULE_BASE__ITEMS:
			case RulebasePackage.RULE_BASE__POTENTIAL_NODE_DEPENDENCIES:
			case RulebasePackage.RULE_BASE__POTENTIAL_EDGE_DEPENDENCIES:
			case RulebasePackage.RULE_BASE__POTENTIAL_ATTRIBUTE_DEPENDENCIES:
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
				(RulebasePackage.Literals.RULE_BASE__ITEMS,
				 RulebaseFactory.eINSTANCE.createRuleBaseItem()));

		newChildDescriptors.add
			(createChildParameter
				(RulebasePackage.Literals.RULE_BASE__POTENTIAL_NODE_DEPENDENCIES,
				 RulebaseFactory.eINSTANCE.createPotentialNodeDependency()));

		newChildDescriptors.add
			(createChildParameter
				(RulebasePackage.Literals.RULE_BASE__POTENTIAL_EDGE_DEPENDENCIES,
				 RulebaseFactory.eINSTANCE.createPotentialEdgeDependency()));

		newChildDescriptors.add
			(createChildParameter
				(RulebasePackage.Literals.RULE_BASE__POTENTIAL_ATTRIBUTE_DEPENDENCIES,
				 RulebaseFactory.eINSTANCE.createPotentialAttributeDependency()));
	}

	/**
	 * Return the resource locator for this item provider's resources.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return RuleBaseEditPlugin.INSTANCE;
	}

}
