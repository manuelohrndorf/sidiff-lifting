/**
 */
package org.sidiff.difference.symmetricprofiled.provider;


import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.sidiff.difference.symmetric.SemanticChangeSet;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.difference.symmetric.SymmetricFactory;
import org.sidiff.difference.symmetric.SymmetricPackage;
import org.sidiff.difference.symmetric.util.DifferenceAnalysisUtil;
import org.sidiff.difference.symmetricprofiled.ProfiledSemanticChangeSet;
import org.sidiff.difference.symmetricprofiled.ProfiledSymmetricDifference;
import org.sidiff.difference.symmetricprofiled.SymmetricProfiledFactory;
import org.sidiff.difference.symmetricprofiled.SymmetricProfiledPackage;

/**
 * This is the item provider adapter for a {@link org.sidiff.difference.symmetricprofiled.ProfiledSymmetricDifference} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class ProfiledSymmetricDifferenceItemProvider
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
	public ProfiledSymmetricDifferenceItemProvider(AdapterFactory adapterFactory) {
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

		}
		return itemPropertyDescriptors;
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
			childrenFeatures.add(SymmetricProfiledPackage.Literals.PROFILED_SYMMETRIC_DIFFERENCE__PROFILED_SEMANTIC_CHANGE_SETS);
			childrenFeatures.add(SymmetricProfiledPackage.Literals.PROFILED_SYMMETRIC_DIFFERENCE__SYMMETRIC_DIFFERENCE);
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
	 * This returns ProfiledSymmetricDifference.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NIT
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("PSD"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public String getText(Object object) {
		SymmetricDifference difference = ((ProfiledSymmetricDifference)object).getSymmetricDifference();
		String modelAName = new File(URI.createURI(difference.getUriModelA()).devicePath()).getName();
		String modelBName = new File(URI.createURI(difference.getUriModelB()).devicePath()).getName();
		String labelValue = modelAName + " <-> " + modelBName;
		
		String label = labelValue == null ? null : labelValue.toString();
		return label == null || label.length() == 0 ?
			getString("_UI_SymmetricDifference_type") :
			getString("_UI_SymmetricDifference_type") + ": " + label;
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

		switch (notification.getFeatureID(ProfiledSymmetricDifference.class)) {
			case SymmetricProfiledPackage.PROFILED_SYMMETRIC_DIFFERENCE__PROFILED_SEMANTIC_CHANGE_SETS:
			case SymmetricProfiledPackage.PROFILED_SYMMETRIC_DIFFERENCE__SYMMETRIC_DIFFERENCE:
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
				(SymmetricProfiledPackage.Literals.PROFILED_SYMMETRIC_DIFFERENCE__PROFILED_SEMANTIC_CHANGE_SETS,
				 SymmetricProfiledFactory.eINSTANCE.createProfiledSemanticChangeSet()));

		newChildDescriptors.add
			(createChildParameter
				(SymmetricProfiledPackage.Literals.PROFILED_SYMMETRIC_DIFFERENCE__SYMMETRIC_DIFFERENCE,
				 SymmetricFactory.eINSTANCE.createSymmetricDifference()));
	}

	/**
	 * Return the resource locator for this item provider's resources.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return ProfiledSDEditPlugin.INSTANCE;
	}
	
	protected List<Object> children = null;
	@Override
	public Collection<?> getChildren(Object object) {
		
		if(children == null){
			ProfiledSymmetricDifference sd = (ProfiledSymmetricDifference) object;
			
			// We can be sure we have a difference here
			SymmetricDifference difference = sd.getSymmetricDifference();
			
			// Create a result-list
			children = new ArrayList<Object>();		
			
			// These children must be filtered
			List<SemanticChangeSet> css = getNonAggregatedChangeSets(difference);
			children.addAll(getProfiledChangeSets(sd, css));
			children.addAll(getUnprofiledChangeSets(sd, css));
			children.addAll(DifferenceAnalysisUtil.getRemainingChanges(difference));
			children.add(new CorrespondencesItemProvider(adapterFactory, sd));

		}
		return children;
	}
	
	/**
	 * @generated NOT
	 */
	public Object getCorrespondences(){
		for(Object obj : children){
			if(obj instanceof CorrespondencesItemProvider){
				return children.get(children.indexOf(obj));
			}
			
		}
		return null;
	}

	
	/**
	 * @param difference
	 * @return all changes which are not grouped by a semantic change set.
	 * @generated NOT
	 */
	private List<SemanticChangeSet> getUnprofiledChangeSets(ProfiledSymmetricDifference sd, List<SemanticChangeSet> changeSets){		
		ArrayList<SemanticChangeSet> res = new ArrayList<SemanticChangeSet>();
		for (SemanticChangeSet cs : changeSets){
			boolean found=false;
			for (ProfiledSemanticChangeSet pcs : sd.getProfiledSemanticChangeSets()){
				if (cs.equals(pcs.getSemanticChangeSet())){
					found = true;
					break;
				}
			}
			if (!found) res.add(cs);
		}
		return res;
	}
	
	/**
	 * 
	 * @param sd
	 * @param changeSets
	 * @return
	 * @generated NOT
	 */
	private List<ProfiledSemanticChangeSet> getProfiledChangeSets(ProfiledSymmetricDifference sd, List<SemanticChangeSet> changeSets){		
		ArrayList<ProfiledSemanticChangeSet> res = new ArrayList<ProfiledSemanticChangeSet>();
		for (ProfiledSemanticChangeSet pcs : sd.getProfiledSemanticChangeSets()){
			for (SemanticChangeSet cs : changeSets){
				if (pcs.getSemanticChangeSet().equals(cs)){
					res.add(pcs);
					break;
				}
			}
		}
		return res;
	}
	
	/**
	 * @param difference
	 * @generated NOT
	 */
	private List<SemanticChangeSet> getNonAggregatedChangeSets(SymmetricDifference difference){		
		ArrayList<SemanticChangeSet> res = new ArrayList<SemanticChangeSet>();
		for (SemanticChangeSet cs : difference.getChangeSets()) {
			if (cs.eContainer() instanceof SymmetricDifference){
				res.add(cs);
			}
		}
		
		return res;
	}

}
