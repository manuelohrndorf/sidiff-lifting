/**
 */
package org.sidiff.slicer.structural.configuration.provider;


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

import org.sidiff.slicer.structural.configuration.ConfigurationFactory;
import org.sidiff.slicer.structural.configuration.ConfigurationPackage;
import org.sidiff.slicer.structural.configuration.SlicingConfiguration;
import org.sidiff.slicer.structural.configuration.descriptor.SlicingConfigurationConstraintInterpreterIDPropertyDescriptor;
import org.sidiff.slicer.structural.configuration.descriptor.SlicingConfigurationImportsPropertyDescriptor;
import org.sidiff.slicer.structural.configuration.descriptor.SlicingConfigurationOppositeSlicedEClassTypePropertyDescriptor;

/**
 * This is the item provider adapter for a {@link org.sidiff.slicer.structural.configuration.SlicingConfiguration} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class SlicingConfigurationItemProvider 
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
	public SlicingConfigurationItemProvider(AdapterFactory adapterFactory) {
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
		if (itemPropertyDescriptors == null)
		{
			super.getPropertyDescriptors(object);

			addNamePropertyDescriptor(object);
			addDescriptionPropertyDescriptor(object);
			addDocumentTypesPropertyDescriptor(object);
			addImportsPropertyDescriptor(object);
			addSlicingModePropertyDescriptor(object);
			addOppositeSlicedEClassTypePropertyDescriptor(object);
			addCheckMultiplicityPropertyDescriptor(object);
			addConstraintInterpreterIDPropertyDescriptor(object);
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
				 getString("_UI_SlicingConfiguration_name_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_SlicingConfiguration_name_feature", "_UI_SlicingConfiguration_type"),
				 ConfigurationPackage.Literals.SLICING_CONFIGURATION__NAME,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Description feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addDescriptionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_SlicingConfiguration_description_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_SlicingConfiguration_description_feature", "_UI_SlicingConfiguration_type"),
				 ConfigurationPackage.Literals.SLICING_CONFIGURATION__DESCRIPTION,
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
				 getString("_UI_SlicingConfiguration_documentTypes_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_SlicingConfiguration_documentTypes_feature", "_UI_SlicingConfiguration_type"),
				 ConfigurationPackage.Literals.SLICING_CONFIGURATION__DOCUMENT_TYPES,
				 false,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Imports feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	protected void addImportsPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(new SlicingConfigurationImportsPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_SlicingConfiguration_imports_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_SlicingConfiguration_imports_feature", "_UI_SlicingConfiguration_type"),
				 ConfigurationPackage.Literals.SLICING_CONFIGURATION__IMPORTS,
				 true,
				 false,
				 true,
				 null,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Slicing Mode feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addSlicingModePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_SlicingConfiguration_slicingMode_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_SlicingConfiguration_slicingMode_feature", "_UI_SlicingConfiguration_type"),
				 ConfigurationPackage.Literals.SLICING_CONFIGURATION__SLICING_MODE,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Opposite Sliced EClass Type feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	protected void addOppositeSlicedEClassTypePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(new SlicingConfigurationOppositeSlicedEClassTypePropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_SlicingConfiguration_oppositeSlicedEClassType_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_SlicingConfiguration_oppositeSlicedEClassType_feature", "_UI_SlicingConfiguration_type"),
				 ConfigurationPackage.Literals.SLICING_CONFIGURATION__OPPOSITE_SLICED_ECLASS_TYPE,
				 false,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Check Multiplicity feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addCheckMultiplicityPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_SlicingConfiguration_checkMultiplicity_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_SlicingConfiguration_checkMultiplicity_feature", "_UI_SlicingConfiguration_type"),
				 ConfigurationPackage.Literals.SLICING_CONFIGURATION__CHECK_MULTIPLICITY,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Constraint Interpreter ID feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	protected void addConstraintInterpreterIDPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(new SlicingConfigurationConstraintInterpreterIDPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_SlicingConfiguration_constraintInterpreterID_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_SlicingConfiguration_constraintInterpreterID_feature", "_UI_SlicingConfiguration_type"),
				 ConfigurationPackage.Literals.SLICING_CONFIGURATION__CONSTRAINT_INTERPRETER_ID,
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
		if (childrenFeatures == null)
		{
			super.getChildrenFeatures(object);
			childrenFeatures.add(ConfigurationPackage.Literals.SLICING_CONFIGURATION__SLICED_ECLASSES);
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
	 * This returns SlicingConfiguration.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/SlicingConfiguration"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((SlicingConfiguration)object).getName();
		return label == null || label.length() == 0 ?
			getString("_UI_SlicingConfiguration_type") :
			getString("_UI_SlicingConfiguration_type") + " " + label;
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

		switch (notification.getFeatureID(SlicingConfiguration.class))
		{
			case ConfigurationPackage.SLICING_CONFIGURATION__NAME:
			case ConfigurationPackage.SLICING_CONFIGURATION__DESCRIPTION:
			case ConfigurationPackage.SLICING_CONFIGURATION__DOCUMENT_TYPES:
			case ConfigurationPackage.SLICING_CONFIGURATION__SLICING_MODE:
			case ConfigurationPackage.SLICING_CONFIGURATION__OPPOSITE_SLICED_ECLASS_TYPE:
			case ConfigurationPackage.SLICING_CONFIGURATION__CHECK_MULTIPLICITY:
			case ConfigurationPackage.SLICING_CONFIGURATION__CONSTRAINT_INTERPRETER_ID:
			case ConfigurationPackage.SLICING_CONFIGURATION__CONSTRAINT_INTERPRETER:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case ConfigurationPackage.SLICING_CONFIGURATION__SLICED_ECLASSES:
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
				(ConfigurationPackage.Literals.SLICING_CONFIGURATION__SLICED_ECLASSES,
				 ConfigurationFactory.eINSTANCE.createSlicedEClass()));
	}

	/**
	 * Return the resource locator for this item provider's resources.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return ConfigurationEditPlugin.INSTANCE;
	}

}
