package org.sidiff.slicer.structural.configuration.adapters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.provider.EClassItemProvider;
import org.eclipse.emf.ecore.provider.EPackageItemProvider;
import org.eclipse.emf.ecore.provider.EReferenceItemProvider;
import org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory;
import org.sidiff.slicer.structural.configuration.util.ConfigurationUtil;
import org.sidiff.slicer.structural.configuration.util.OverridingEReference;

/**
 * This extends {@link EcoreItemProviderAdapterFactory} and hides the following model elements from the model tree:
 * <ul>
 * <li>{@link EAttribute}</li>
 * <li>{@link EAnnotation}</li>
 * <li>{@link EOperation}</li>
 * <li>{@link EGenericType}</li>
 * <li>All {@link EClassifier}s that are not {@link EClass}</li>
 * <li>All {@link EReference}s that are not {@link ConfigurationUtil#isSliceable(EReference) sliceable}
 * </ul>
 * For each class, new {@link OverridingEReference}s are created for all sliceable references of the class' super types.
 * @author rmueller
 *
 */
public class ImportedModelsEcoreItemProviderAdapterFactory extends EcoreItemProviderAdapterFactory
{
	@Override
	public Adapter createEPackageAdapter()
	{
		if(ePackageItemProvider == null)
		{
			ePackageItemProvider = new EPackageItemProvider(this)
			{
				@SuppressWarnings("unchecked")
				@Override
				public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object)
				{
					if(childrenFeatures == null)
					{
						childrenFeatures = (List<EStructuralFeature>)super.getChildrenFeatures(object);

						// hide annotations
						childrenFeatures.remove(EcorePackage.Literals.EMODEL_ELEMENT__EANNOTATIONS);
					}

					return childrenFeatures;
				}

				@Override
				public Collection<?> getChildren(Object object)
				{
					List<Object> newChildren = new LinkedList<Object>();

					// only show elements that are not EClassifier, or EClass
					for(Object o : super.getChildren(object))
					{
						if(!(o instanceof EClassifier) || o instanceof EClass)
						{
							newChildren.add(o);
						}
					}

					return newChildren;
				}
			};
		}

		return ePackageItemProvider;
	}

	@Override
	public Adapter createEClassAdapter()
	{
		if(eClassItemProvider == null)
		{
			eClassItemProvider = new EClassItemProvider(this)
			{
				@SuppressWarnings("unchecked")
				@Override
				public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object)
				{
					if(childrenFeatures == null)
					{
						childrenFeatures = (List<EStructuralFeature>)super.getChildrenFeatures(object);

						// hide operations
						childrenFeatures.remove(EcorePackage.Literals.ECLASS__EOPERATIONS);

						// hide generic super types
						childrenFeatures.remove(EcorePackage.Literals.ECLASS__EGENERIC_SUPER_TYPES);

						// hide annotations
						childrenFeatures.remove(EcorePackage.Literals.EMODEL_ELEMENT__EANNOTATIONS);

						// hide structural features (attributes and references)
						childrenFeatures.remove(EcorePackage.Literals.ECLASS__ESTRUCTURAL_FEATURES);

						// show references again
						// ECLASS__EALL_REFERENCES also shows the references of super classes
						childrenFeatures.add(EcorePackage.Literals.ECLASS__EALL_REFERENCES);
					}

					return childrenFeatures;
				}

				@Override
				public Collection<?> getChildren(Object object)
				{
					Collection<Object> children = new ArrayList<Object>(super.getChildren(object));

					// remove references that are not sliceable
					for(Iterator<Object> it = children.iterator(); it.hasNext(); )
					{
						Object child = it.next();
						if(child instanceof EReference)
						{
							if(!ConfigurationUtil.isSliceable((EReference)child))
							{
								it.remove();
							}
						}
					}

					// the references that are overriding others are wrapped in OverridingEReference
					// so that the references can be distinguished, because without the wrapping,
					// the _same_ reference occurs multiple times for different classes
					if(object instanceof EClass)
					{
						EClass eClass = (EClass)object;

						// set of references that need to be wrapped
						// references will first be collected and then wrapped,
						// because the collection may not be modified during iteration
						Set<EReference> toBeWrapped = new HashSet<EReference>();

						for(Object child : children)
						{
							if(child instanceof EReference)
							{
								EReference ref = (EReference)child;
								if(eClass.getEAllReferences().contains(ref) && !eClass.getEReferences().contains(ref))
								{
									toBeWrapped.add(ref);
								}
							}
						}

						// wrap the references
						for(EReference ref : toBeWrapped)
						{
							children.remove(ref);
							children.add(OverridingEReference.get(ref, eClass));
						}
					}

					return children;
				}
			};
		}

		return eClassItemProvider;
	}

	@Override
	public Adapter createEReferenceAdapter()
	{
		if(eReferenceItemProvider == null)
		{
			eReferenceItemProvider = new EReferenceItemProvider(this)
			{
				@SuppressWarnings("unchecked")
				@Override
				public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object)
				{
					if(childrenFeatures == null)
					{
						childrenFeatures = (List<EStructuralFeature>)super.getChildrenFeatures(object);

						// hide generic type
						childrenFeatures.remove(EcorePackage.Literals.ETYPED_ELEMENT__EGENERIC_TYPE);

						// hide annotations
						childrenFeatures.remove(EcorePackage.Literals.EMODEL_ELEMENT__EANNOTATIONS);
					}

					return childrenFeatures;
				}
			};
		}

		return eReferenceItemProvider;
	}
}
