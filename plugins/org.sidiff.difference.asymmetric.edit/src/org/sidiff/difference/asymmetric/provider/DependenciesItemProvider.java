package org.sidiff.difference.asymmetric.provider;

import java.util.Collection;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.AsymmetricFactory;
import org.sidiff.difference.asymmetric.AsymmetricPackage;


/**
 * This is the item provider adapter for a {@link org.sidiff.difference.symmetric.SemanticChangeSet} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated NOT
 */
public class DependenciesItemProvider extends TransientAsymmetricDifferenceItemProvider{

	
	/**
	 * this class is not created in the usual way,
	 * the constructor have to add them to the eAdapters list
	 * @param adapterFactory
	 * @param symmetricDifference
	 * @generated NOT
	 */
	public DependenciesItemProvider(AdapterFactory adapterFactory,
			AsymmetricDifference asymmetricDifference) {
		super(adapterFactory, asymmetricDifference);
	}
	
	
	/**
	 * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
	 * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
	 * @generated NOT
	 */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object){
		if (childrenFeatures == null){
			super.getChildrenFeatures(object);
			childrenFeatures.add(AsymmetricPackage.Literals.ASYMMETRIC_DIFFERENCE__DEP_CONTAINERS);
		}
		return childrenFeatures;
	}

	
	/**
	 * This returns Dependency.gif.
	 * @generated NOT
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/Dependency"));
	}
	
	
	/**
	 * This returns the label text for the adapted class.
	 * @generated NOT
	 */
	@Override
	public String getText(Object object){
		return "Dependencies (" + difference.getDepContainers().size() + ")";
	}
	
	
	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
	 * that can be created under this object.
	 * @generated NOT
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object){
		super.collectNewChildDescriptors(newChildDescriptors, object);
		newChildDescriptors.add (createChildParameter(AsymmetricPackage.Literals.ASYMMETRIC_DIFFERENCE__DEP_CONTAINERS,
			AsymmetricFactory.eINSTANCE.createNodeDependency()));
		newChildDescriptors.add (createChildParameter(AsymmetricPackage.Literals.ASYMMETRIC_DIFFERENCE__DEP_CONTAINERS,
				AsymmetricFactory.eINSTANCE.createEdgeDependency()));
		newChildDescriptors.add (createChildParameter(AsymmetricPackage.Literals.ASYMMETRIC_DIFFERENCE__DEP_CONTAINERS,
				AsymmetricFactory.eINSTANCE.createAttributeDependency()));
	}
	
	
	/**
	 * returns the appropriate transient item provider
	 * @generated NOT
	 */
	@Override
	public Object getParent(Object object){
		Object difference = super.getParent(object);
		AsymmetricDifferenceItemProvider asymmetricDifferenceItemProvider 
		= (AsymmetricDifferenceItemProvider) adapterFactory.adapt (
				difference, IEditingDomainItemProvider.class);
		
		return asymmetricDifferenceItemProvider != null?
				asymmetricDifferenceItemProvider.getDependencies() : null;

	}

	
	/**
	 * 
	 * @generated NOT
	 */
	@Override
	public ResourceLocator getResourceLocator(){
		return  AsymmetricEditPlugin.INSTANCE;
	}

}
