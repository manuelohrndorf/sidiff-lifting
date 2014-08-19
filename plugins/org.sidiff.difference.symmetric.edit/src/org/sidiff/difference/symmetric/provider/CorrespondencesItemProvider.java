package org.sidiff.difference.symmetric.provider;

import java.util.Collection;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.difference.symmetric.SymmetricFactory;
import org.sidiff.difference.symmetric.SymmetricPackage;

public class CorrespondencesItemProvider extends TransientSymmetricDifferenceItemProvider{

	/**
	 * this class is not created in the usual way,
	 * the constructor have to add them to the eAdapters list
	 * @param adapterFactory
	 * @param symmetricDifference
	 * @generated NOT
	 */
	public CorrespondencesItemProvider(AdapterFactory adapterFactory,
			SymmetricDifference symmetricDifference) {
		super(adapterFactory, symmetricDifference);
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
			childrenFeatures.add(SymmetricPackage.Literals.SYMMETRIC_DIFFERENCE__CORRESPONDENCES);
		}
		return childrenFeatures;
	}

	
	/**
	 * This returns unusedchangeset.gif.
	 * @generated NOT
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("correspondence.gif"));
	}
	
	
	/**
	 * This returns the label text for the adapted class.
	 * @generated NOT
	 */
	@Override
	public String getText(Object object){
		return "Correspondences (" + difference.getCorrespondences().size() + ")";
	}
	
	
	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
	 * that can be created under this object.
	 * @generated NOT
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object){
		super.collectNewChildDescriptors(newChildDescriptors, object);
		newChildDescriptors.add (createChildParameter(SymmetricPackage.Literals.SYMMETRIC_DIFFERENCE__CORRESPONDENCES,
			SymmetricFactory.eINSTANCE.createCorrespondence()));
	}
	
	
	/**
	 * returns the appropriate transient item provider
	 * @generated NOT
	 */
	@Override
	public Object getParent(Object object){
		Object difference = super.getParent(object);
		SymmetricDifferenceItemProvider symmetricDifferenceItemProvider 
		= (SymmetricDifferenceItemProvider) adapterFactory.adapt (
				difference, IEditingDomainItemProvider.class);
		
		return symmetricDifferenceItemProvider != null ?
				symmetricDifferenceItemProvider.getCorrespondences() : null;

	}

	
	/**
	 * 
	 * @generated NOT
	 */
	@Override
	public ResourceLocator getResourceLocator(){
		return  SymmetricEditPlugin.INSTANCE;
	}
}
