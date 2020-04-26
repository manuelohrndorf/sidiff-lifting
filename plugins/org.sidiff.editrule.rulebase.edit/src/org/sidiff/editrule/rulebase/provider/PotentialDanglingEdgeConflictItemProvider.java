/**
 */
package org.sidiff.editrule.rulebase.provider;


import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;

import org.sidiff.editrule.rulebase.PotentialConflictKind;
import org.sidiff.editrule.rulebase.PotentialDanglingEdgeConflict;
import org.sidiff.editrule.rulebase.RulebasePackage;

/**
 * This is the item provider adapter for a {@link org.sidiff.editrule.rulebase.PotentialDanglingEdgeConflict} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class PotentialDanglingEdgeConflictItemProvider extends PotentialConflictItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PotentialDanglingEdgeConflictItemProvider(AdapterFactory adapterFactory) {
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

			addRuleBasePropertyDescriptor(object);
			addDeletionNodePropertyDescriptor(object);
			addCreationEdgePropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Rule Base feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addRuleBasePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_PotentialDanglingEdgeConflict_ruleBase_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_PotentialDanglingEdgeConflict_ruleBase_feature", "_UI_PotentialDanglingEdgeConflict_type"),
				 RulebasePackage.Literals.POTENTIAL_DANGLING_EDGE_CONFLICT__RULE_BASE,
				 true,
				 false,
				 true,
				 null,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Deletion Node feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addDeletionNodePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_PotentialDanglingEdgeConflict_deletionNode_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_PotentialDanglingEdgeConflict_deletionNode_feature", "_UI_PotentialDanglingEdgeConflict_type"),
				 RulebasePackage.Literals.POTENTIAL_DANGLING_EDGE_CONFLICT__DELETION_NODE,
				 true,
				 false,
				 true,
				 null,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Creation Edge feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addCreationEdgePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_PotentialDanglingEdgeConflict_creationEdge_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_PotentialDanglingEdgeConflict_creationEdge_feature", "_UI_PotentialDanglingEdgeConflict_type"),
				 RulebasePackage.Literals.POTENTIAL_DANGLING_EDGE_CONFLICT__CREATION_EDGE,
				 true,
				 false,
				 true,
				 null,
				 null,
				 null));
	}

	/**
	 * This returns PotentialDanglingEdgeConflict.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/PotentialDanglingEdgeConflict"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		PotentialConflictKind labelValue = ((PotentialDanglingEdgeConflict)object).getKind();
		String label = labelValue == null ? null : labelValue.toString();
		return label == null || label.length() == 0 ?
			getString("_UI_PotentialDanglingEdgeConflict_type") :
			getString("_UI_PotentialDanglingEdgeConflict_type") + " " + label;
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
	}

}
