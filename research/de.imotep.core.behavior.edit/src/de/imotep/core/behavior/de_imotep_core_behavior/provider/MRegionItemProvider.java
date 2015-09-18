/**
 */
package de.imotep.core.behavior.de_imotep_core_behavior.provider;


import de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorFactory;
import de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorPackage;
import de.imotep.core.behavior.de_imotep_core_behavior.MRegion;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

/**
 * This is the item provider adapter for a {@link de.imotep.core.behavior.de_imotep_core_behavior.MRegion} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class MRegionItemProvider extends MBehaviorEntityItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MRegionItemProvider(AdapterFactory adapterFactory) {
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

			addInitialStatePropertyDescriptor(object);
			addParentStateMachinePropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Initial State feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addInitialStatePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_MRegion_initialState_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_MRegion_initialState_feature", "_UI_MRegion_type"),
				 De_imotep_core_behaviorPackage.Literals.MREGION__INITIAL_STATE,
				 true,
				 false,
				 true,
				 null,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Parent State Machine feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addParentStateMachinePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_MRegion_parentStateMachine_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_MRegion_parentStateMachine_feature", "_UI_MRegion_type"),
				 De_imotep_core_behaviorPackage.Literals.MREGION__PARENT_STATE_MACHINE,
				 true,
				 false,
				 true,
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
			childrenFeatures.add(De_imotep_core_behaviorPackage.Literals.MREGION__STATE_GROUPS);
			childrenFeatures.add(De_imotep_core_behaviorPackage.Literals.MREGION__STATES);
			childrenFeatures.add(De_imotep_core_behaviorPackage.Literals.MREGION__TRANSITIONS);
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
	 * This returns MRegion.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/MRegion"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((MRegion)object).getName();
		return label == null || label.length() == 0 ?
			getString("_UI_MRegion_type") :
			getString("_UI_MRegion_type") + " " + label;
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

		switch (notification.getFeatureID(MRegion.class)) {
			case De_imotep_core_behaviorPackage.MREGION__STATE_GROUPS:
			case De_imotep_core_behaviorPackage.MREGION__STATES:
			case De_imotep_core_behaviorPackage.MREGION__TRANSITIONS:
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
				(De_imotep_core_behaviorPackage.Literals.MREGION__STATE_GROUPS,
				 De_imotep_core_behaviorFactory.eINSTANCE.createMStateGroup()));

		newChildDescriptors.add
			(createChildParameter
				(De_imotep_core_behaviorPackage.Literals.MREGION__STATES,
				 De_imotep_core_behaviorFactory.eINSTANCE.createMFinalState()));

		newChildDescriptors.add
			(createChildParameter
				(De_imotep_core_behaviorPackage.Literals.MREGION__STATES,
				 De_imotep_core_behaviorFactory.eINSTANCE.createMState()));

		newChildDescriptors.add
			(createChildParameter
				(De_imotep_core_behaviorPackage.Literals.MREGION__STATES,
				 De_imotep_core_behaviorFactory.eINSTANCE.createMErrorState()));

		newChildDescriptors.add
			(createChildParameter
				(De_imotep_core_behaviorPackage.Literals.MREGION__STATES,
				 De_imotep_core_behaviorFactory.eINSTANCE.createMStateMachineState()));

		newChildDescriptors.add
			(createChildParameter
				(De_imotep_core_behaviorPackage.Literals.MREGION__STATES,
				 De_imotep_core_behaviorFactory.eINSTANCE.createMTerminateState()));

		newChildDescriptors.add
			(createChildParameter
				(De_imotep_core_behaviorPackage.Literals.MREGION__STATES,
				 De_imotep_core_behaviorFactory.eINSTANCE.createMInitialState()));

		newChildDescriptors.add
			(createChildParameter
				(De_imotep_core_behaviorPackage.Literals.MREGION__STATES,
				 De_imotep_core_behaviorFactory.eINSTANCE.createMHistoryState()));

		newChildDescriptors.add
			(createChildParameter
				(De_imotep_core_behaviorPackage.Literals.MREGION__TRANSITIONS,
				 De_imotep_core_behaviorFactory.eINSTANCE.createMTransition()));

		newChildDescriptors.add
			(createChildParameter
				(De_imotep_core_behaviorPackage.Literals.MREGION__TRANSITIONS,
				 De_imotep_core_behaviorFactory.eINSTANCE.createMErrorTransition()));
	}

}
