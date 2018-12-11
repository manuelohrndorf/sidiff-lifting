package org.sidiff.slicer.slice.importer;

import org.sidiff.entities.importer.factory.AbstractImporterFactory;
import org.sidiff.slicer.slice.SliceFactory;
import org.sidiff.slicer.slice.SlicedElement;

public class SliceImporterFactory extends AbstractImporterFactory<SlicedElement> {

	@Override
	public SlicedElement createElement() {
		return SliceFactory.eINSTANCE.createSlicedElement();
	}
}
