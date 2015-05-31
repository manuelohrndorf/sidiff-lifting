/**
 */
package org.sidiff.difference.symmetricprofiled.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EObject;
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
import org.sidiff.difference.symmetricprofiled.AppliedStereotype;
import org.sidiff.difference.symmetricprofiled.SymmetricProfiledPackage;

/**
 * This is the item provider adapter for a {@link org.sidiff.difference.symmetricprofiled.AppliedStereotype} object.
 * <!-- begin-user-doc --> <!-- end-user-doc -->
 * @generated
 */
public class AppliedStereotypeItemProvider extends ItemProviderAdapter
		implements IEditingDomainItemProvider, IStructuredItemContentProvider,
		ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
	/**
	 * This constructs an instance from a factory and a notifier. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public AppliedStereotypeItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			//addStereoTypePropertyDescriptor(object);
			addBaseObjectPropertyDescriptor(object);
			addNamePropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Stereo Type feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addStereoTypePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_AppliedStereotype_stereoType_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_AppliedStereotype_stereoType_feature", "_UI_AppliedStereotype_type"),
				 SymmetricProfiledPackage.Literals.APPLIED_STEREOTYPE__STEREO_TYPE,
				 false,
				 false,
				 true,
				 null,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Name feature.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 */
	protected void addNamePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_AppliedStereotype_name_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_AppliedStereotype_name_feature", "_UI_AppliedStereotype_type"),
				 SymmetricProfiledPackage.Literals.APPLIED_STEREOTYPE__NAME,
				 false,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Base Object feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addBaseObjectPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_AppliedStereotype_baseObject_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_AppliedStereotype_baseObject_feature", "_UI_AppliedStereotype_type"),
				 SymmetricProfiledPackage.Literals.APPLIED_STEREOTYPE__BASE_OBJECT,
				 false,
				 false,
				 true,
				 null,
				 null,
				 null));
	}

	/**
	 * This returns AppliedStereotype.gif.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("AppliedStereotype"));
	}

	/**
	 * This returns the label text for the adapted class. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public String getText(Object object) {
		String name = ((AppliedStereotype) object).getName();
		String label = (name != null ? name.substring(name.lastIndexOf(".") + 1) : "");
		EObject baseObject=((AppliedStereotype) object).getBaseObject();
		String baseObjectType="";
		String baseObjectName=null;
		if (baseObject != null){
			baseObjectType=((AppliedStereotype) object).getBaseObject().eClass().getName();
			if (baseObjectType == null) baseObjectType="";
			EStructuralFeature nameFeature=baseObject.eClass().getEStructuralFeature("name");
			if (nameFeature != null){
				baseObjectName = (String)baseObject.eGet(nameFeature);
			}
		}
		
		label = "<<"+label+">> "+"<"+baseObjectType+">"
				+ (baseObjectName != null ? " "+baseObjectName : "");
		return (label == null || label.length() == 0 ? getString("_UI_AppliedStereotype_type")
				: getString("_UI_AppliedStereotype_type") + " " + label);
	}
	

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(AppliedStereotype.class)) {
			case SymmetricProfiledPackage.APPLIED_STEREOTYPE__NAME:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
		}
		super.notifyChanged(notification);
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s
	 * describing the children that can be created under this object. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(
			Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);
	}

	/**
	 * Return the resource locator for this item provider's resources. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return ProfiledSDEditPlugin.INSTANCE;
	}

}
