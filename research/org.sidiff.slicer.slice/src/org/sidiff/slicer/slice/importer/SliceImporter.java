package org.sidiff.slicer.slice.importer;

import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.sidiff.common.emf.EMFUtil;
import org.sidiff.entities.Attribute;
import org.sidiff.entities.Reference;
import org.sidiff.entities.importer.EntitiesImporter;
import org.sidiff.entities.importer.ImportFailedException;
import org.sidiff.entities.importer.factory.IImporterFactory;
import org.sidiff.entities.importer.uuid.IImporterUuidResolver;
import org.sidiff.entities.importer.uuid.XmiIdImporterUuidResolver;
import org.sidiff.slicer.slice.ModelSlice;
import org.sidiff.slicer.slice.SlicedElement;

/**
 * 
 * @author rmueller, cpietsch
 *
 */
public class SliceImporter extends EntitiesImporter<SlicedElement,Reference,Attribute> {

	/**
	 * The model slice that is being created by this importer.
	 */
	private ModelSlice modelSlice;

	/**
	 * Create a SliceImporter to import a new {@link ModelSlice}.
	 * The model slice must be initialized with {@link #init(ModelSlice)}.
	 */
	public SliceImporter() {
		super(SlicedElement.class, Reference.class, Attribute.class);
	}

	public void init(ModelSlice modelSlice) {
		this.modelSlice = modelSlice;

		clearIndices();
		for(SlicedElement element : modelSlice.getSlicedElements()) {
			updateIndices(element);
			for(Reference reference : element.getSlicedReferences()) {
				updateIndices(reference);
			}
			for(Attribute attribute : element.getSlicedAttributes()) {
				updateIndices(attribute);
			}
		}
	}

	@Override
	protected SlicedElement doImportEObject(EObject eObject) throws ImportFailedException {
		SlicedElement element = (SlicedElement)getUuidIndex().get(EMFUtil.getXmiId(eObject));
		if(element == null) {
			element = (SlicedElement)super.doImportEObject(eObject);
		}
		modelSlice.getType().add(eObject.eClass().getEPackage());
		modelSlice.getSlicedElements().add(element);
		return element;
	}

	@Override
	protected Reference doImportEReference(EReference eReference, SlicedElement srcElement, SlicedElement tgtElement) throws ImportFailedException {
		Reference reference = super.doImportEReference(eReference, srcElement, tgtElement);
		srcElement.getSlicedReferences().add(reference);
		return reference;
	}

	@Override
	protected Attribute doImportEAttribute(EAttribute eAttribute, SlicedElement element, List<String> values) throws ImportFailedException {
		Attribute attribute = super.doImportEAttribute(eAttribute, element, values);
		element.getSlicedAttributes().add(attribute);
		return attribute;
	}

	public ModelSlice getModelSlice() {
		return modelSlice;
	}

	@Override
	protected IImporterFactory<? extends SlicedElement, ? extends Reference, ? extends Attribute> createFactory() {
		return new SliceImporterFactory();
	}

	@Override
	protected IImporterUuidResolver createUuidResolver() {
		return new XmiIdImporterUuidResolver();
	}
}
