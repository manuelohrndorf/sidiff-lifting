package org.sidiff.slicer.slice.util;

import java.util.HashMap;
import java.util.HashSet;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.sidiff.entities.Attribute;
import org.sidiff.entities.Element;
import org.sidiff.entities.Entity;
import org.sidiff.entities.Reference;
import org.sidiff.entities.util.EntitiesImporter;
import org.sidiff.entities.util.ImportFailedException;
import org.sidiff.slicer.slice.ModelSlice;
import org.sidiff.slicer.slice.SliceFactory;
import org.sidiff.slicer.slice.SlicedElement;

/**
 * 
 * @author rmueller
 *
 */
public class SliceImporter extends EntitiesImporter
{
	/**
	 * The model slice that is being created by this importer.
	 */
	protected ModelSlice modelSlice;

	/**
	 * Create a SliceImporter to import a new {@link ModelSlice}.
	 */
	public SliceImporter()
	{
		this.modelSlice = SliceFactory.eINSTANCE.createModelSlice();
		this.uuidIndex = new HashMap<>();
		this.signatureIndex = new HashMap<>();
		this.eObject2Element = new HashMap<>();
	}

	@Override
	protected String computeSignature(Entity entity)
	{
		if(entity instanceof SlicedElement)
		{
			return computeEObjectSignature(((SlicedElement)entity).getObject());
		}
		else if(entity instanceof Reference)
		{
			Reference reference = (Reference)entity;
			return reference.getSource().getSignature()
					+ "." + reference.getType().getName()
					+ "." + reference.getTarget().getSignature();
		}
		else if(entity instanceof Attribute)
		{
			Attribute attribute = (Attribute)entity;
			return attribute.getContainer().getSignature()
					+ "." + attribute.getType().getName()
					+ "." + attribute.getValue();
		}
		return null;
	}

	private String computeEObjectSignature(EObject object)
	{
		String name;
		try
		{
			name = object.eGet(object.eClass().getEStructuralFeature("name")).toString();
		}
		catch (Exception e)
		{
			name = object.eClass().getName();
		}

		if(object.eContainer() != null)
		{
			return computeEObjectSignature(object.eContainer()) + "." + name;
		}
		return name;
	}

	@Override
	protected Element createElement()
	{
		return SliceFactory.eINSTANCE.createSlicedElement();
	}

	@Override
	public SlicedElement importEObject(EObject eObject) throws ImportFailedException
	{
		SlicedElement element = (SlicedElement)super.importEObject(eObject);
		modelSlice.getSlicedElements().add(element);
		return element;
	}

	@Override
	public Reference importEReference(EReference eReference, EObject srcEObject, EObject tgtEObject)
			throws ImportFailedException
	{
		Reference reference = super.importEReference(eReference, srcEObject, tgtEObject);
		SlicedElement srcElement = (SlicedElement)eObject2Element.get(srcEObject);
		srcElement.getSlicedReferences().add(reference);
		return reference;
	}

	@Override
	public Attribute importEAttribute(EAttribute eAttribute, EObject eObject) throws ImportFailedException
	{
		Attribute attribute = super.importEAttribute(eAttribute, eObject);
		SlicedElement element = (SlicedElement)eObject2Element.get(eObject);
		element.getSlicedAttributes().add(attribute);
		return attribute;
	}

	public ModelSlice getModelSlice()
	{
		return modelSlice;
	}

	public void setModelSlice(ModelSlice modelSlice)
	{
		this.modelSlice = modelSlice;

		// update the indices
		this.uuidIndex.clear();
		this.signatureIndex.clear();
		this.eObject2Element.clear();
		for(SlicedElement element : this.modelSlice.getSlicedElements())
		{
			this.uuidIndex.put(element.getUuid(), element);
			if(this.signatureIndex.get(element.getSignature()) == null) {
				this.signatureIndex.put(element.getSignature(), new HashSet<Entity>());
			}
			this.signatureIndex.get(element.getSignature()).add(element);
			this.eObject2Element.put(element.getObject(), element);
		}
	}
}
