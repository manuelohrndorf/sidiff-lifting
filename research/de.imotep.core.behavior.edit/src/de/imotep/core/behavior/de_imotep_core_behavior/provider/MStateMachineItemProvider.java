/**
 */
package de.imotep.core.behavior.de_imotep_core_behavior.provider;


import de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorFactory;
import de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorPackage;
import de.imotep.core.behavior.de_imotep_core_behavior.MStateMachine;

import de.imotep.core.datamodel.de_imotep_core_datamodel.De_imotep_core_datamodelFactory;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

/**
 * This is the item provider adapter for a {@link de.imotep.core.behavior.de_imotep_core_behavior.MStateMachine} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class MStateMachineItemProvider extends MBehaviorEntityItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MStateMachineItemProvider(AdapterFactory adapterFactory) {
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

			addRegionsPropertyDescriptor(object);
			addPositionPropertyDescriptor(object);
			addTypePropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Regions feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addRegionsPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_MStateMachine_regions_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_MStateMachine_regions_feature", "_UI_MStateMachine_type"),
				 De_imotep_core_behaviorPackage.Literals.MSTATE_MACHINE__REGIONS,
				 true,
				 false,
				 true,
				 null,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Position feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addPositionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_MStateMachine_position_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_MStateMachine_position_feature", "_UI_MStateMachine_type"),
				 De_imotep_core_behaviorPackage.Literals.MSTATE_MACHINE__POSITION,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Type feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addTypePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_MStateMachine_type_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_MStateMachine_type_feature", "_UI_MStateMachine_type"),
				 De_imotep_core_behaviorPackage.Literals.MSTATE_MACHINE__TYPE,
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
			childrenFeatures.add(De_imotep_core_behaviorPackage.Literals.MSTATE_MACHINE__ACTIONS);
			childrenFeatures.add(De_imotep_core_behaviorPackage.Literals.MSTATE_MACHINE__ATTRIBUTES);
			childrenFeatures.add(De_imotep_core_behaviorPackage.Literals.MSTATE_MACHINE__ROOT_REGION);
			childrenFeatures.add(De_imotep_core_behaviorPackage.Literals.MSTATE_MACHINE__GUARDS);
			childrenFeatures.add(De_imotep_core_behaviorPackage.Literals.MSTATE_MACHINE__EVENTS);
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
	 * This returns MStateMachine.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/MStateMachine"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((MStateMachine)object).getName();
		return label == null || label.length() == 0 ?
			getString("_UI_MStateMachine_type") :
			getString("_UI_MStateMachine_type") + " " + label;
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

		switch (notification.getFeatureID(MStateMachine.class)) {
			case De_imotep_core_behaviorPackage.MSTATE_MACHINE__POSITION:
			case De_imotep_core_behaviorPackage.MSTATE_MACHINE__TYPE:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case De_imotep_core_behaviorPackage.MSTATE_MACHINE__ACTIONS:
			case De_imotep_core_behaviorPackage.MSTATE_MACHINE__ATTRIBUTES:
			case De_imotep_core_behaviorPackage.MSTATE_MACHINE__ROOT_REGION:
			case De_imotep_core_behaviorPackage.MSTATE_MACHINE__GUARDS:
			case De_imotep_core_behaviorPackage.MSTATE_MACHINE__EVENTS:
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
				(De_imotep_core_behaviorPackage.Literals.MSTATE_MACHINE__ACTIONS,
				 De_imotep_core_behaviorFactory.eINSTANCE.createMAction()));

		newChildDescriptors.add
			(createChildParameter
				(De_imotep_core_behaviorPackage.Literals.MSTATE_MACHINE__ATTRIBUTES,
				 De_imotep_core_datamodelFactory.eINSTANCE.createMIntegerAttribute()));

		newChildDescriptors.add
			(createChildParameter
				(De_imotep_core_behaviorPackage.Literals.MSTATE_MACHINE__ATTRIBUTES,
				 De_imotep_core_datamodelFactory.eINSTANCE.createMStringAttribute()));

		newChildDescriptors.add
			(createChildParameter
				(De_imotep_core_behaviorPackage.Literals.MSTATE_MACHINE__ATTRIBUTES,
				 De_imotep_core_datamodelFactory.eINSTANCE.createMRangedIntegerAttribute()));

		newChildDescriptors.add
			(createChildParameter
				(De_imotep_core_behaviorPackage.Literals.MSTATE_MACHINE__ATTRIBUTES,
				 De_imotep_core_datamodelFactory.eINSTANCE.createMBooleanAttribute()));

		newChildDescriptors.add
			(createChildParameter
				(De_imotep_core_behaviorPackage.Literals.MSTATE_MACHINE__ROOT_REGION,
				 De_imotep_core_behaviorFactory.eINSTANCE.createMRegion()));

		newChildDescriptors.add
			(createChildParameter
				(De_imotep_core_behaviorPackage.Literals.MSTATE_MACHINE__GUARDS,
				 De_imotep_core_behaviorFactory.eINSTANCE.createMGuard()));

		newChildDescriptors.add
			(createChildParameter
				(De_imotep_core_behaviorPackage.Literals.MSTATE_MACHINE__EVENTS,
				 De_imotep_core_behaviorFactory.eINSTANCE.createMEvent()));
	}

}
