/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.difference.asymmetric.provider;


import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.AsymmetricPackage;
import org.sidiff.difference.asymmetric.ObjectParameterBinding;
import org.sidiff.difference.asymmetric.ParameterMapping;
import org.sidiff.editrule.rulebase.ParameterDirection;

/**
 * This is the item provider adapter for a {@link org.sidiff.difference.asymmetric.ObjectParameterBinding} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class ObjectParameterBindingItemProvider
	extends ParameterBindingItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ObjectParameterBindingItemProvider(AdapterFactory adapterFactory) {
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

			addActualAPropertyDescriptor(object);
			addActualBPropertyDescriptor(object);
			addOutgoingPropertyDescriptor(object);
			addIncomingPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Actual A feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addActualAPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_ObjectParameterBinding_actualA_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_ObjectParameterBinding_actualA_feature", "_UI_ObjectParameterBinding_type"),
				 AsymmetricPackage.Literals.OBJECT_PARAMETER_BINDING__ACTUAL_A,
				 true,
				 false,
				 true,
				 null,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Actual B feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addActualBPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_ObjectParameterBinding_actualB_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_ObjectParameterBinding_actualB_feature", "_UI_ObjectParameterBinding_type"),
				 AsymmetricPackage.Literals.OBJECT_PARAMETER_BINDING__ACTUAL_B,
				 true,
				 false,
				 true,
				 null,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Outgoing feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addOutgoingPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_ObjectParameterBinding_outgoing_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_ObjectParameterBinding_outgoing_feature", "_UI_ObjectParameterBinding_type"),
				 AsymmetricPackage.Literals.OBJECT_PARAMETER_BINDING__OUTGOING,
				 true,
				 false,
				 true,
				 null,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Incoming feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addIncomingPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_ObjectParameterBinding_incoming_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_ObjectParameterBinding_incoming_feature", "_UI_ObjectParameterBinding_type"),
				 AsymmetricPackage.Literals.OBJECT_PARAMETER_BINDING__INCOMING,
				 true,
				 false,
				 true,
				 null,
				 null,
				 null));
	}

	/**
	 * This returns ObjectParameterBinding.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public Object getImage(Object object) {
		ObjectParameterBinding parameter = (ObjectParameterBinding) object;
		if(parameter.getFormalParameter().getDirection() == ParameterDirection.OUT){
			return overlayImage(object, getResourceLocator().getImage("full/obj16/ObjectParameterBinding_out"));
		} else {
			// is this in argument mapped from another out argument?
			EObject parent = parameter.eContainer();
			while (!(parent instanceof AsymmetricDifference)){
				parent = parent.eContainer();
			}
			AsymmetricDifference difference = (AsymmetricDifference) parent;
			for(ParameterMapping mapping : difference.getParameterMappings()){
				if(mapping.getTarget() == parameter){
					// ..yes, mapped.
					return overlayImage(object, getResourceLocator().getImage("full/obj16/ObjectParameterBinding_in2"));
				}
			}
			
			// ..not mapped
			return overlayImage(object, getResourceLocator().getImage("full/obj16/ObjectParameterBinding_in"));
		}
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public String getText(Object object) {
		ObjectParameterBinding parameter = (ObjectParameterBinding) object;
		
		String label = ((ObjectParameterBinding)object).getFormalName();
		
		String name = null;
		EObject eObject = parameter.getActualA() != null ? parameter.getActualA() : parameter.getActualB();
		for(EAttribute attribute : eObject.eClass().getEAllAttributes()){
			if(attribute.getName().equalsIgnoreCase("name")){
				try {
					name = (String) eObject.eGet(attribute);
				} catch (ClassCastException e){
					// do nothing
				}
			}
		}
		
		if(name == null){
			name = EcoreUtil.getID(eObject);
		}
		
		return label == null || label.length() == 0 ?
			getString("_UI_ObjectParameterBinding_type") :
			String.format("%s %s -> %s", "ObjectParameterBinding:", label, name);
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
