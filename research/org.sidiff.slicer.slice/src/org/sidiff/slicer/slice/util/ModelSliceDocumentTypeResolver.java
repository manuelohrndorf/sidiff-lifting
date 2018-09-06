package org.sidiff.slicer.slice.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.common.emf.doctype.IDocumentTypeResolver;
import org.sidiff.slicer.slice.ModelSlice;
import org.sidiff.slicer.slice.SlicePackage;

/**
 * 
 * @author piets
 *
 */
public class ModelSliceDocumentTypeResolver implements IDocumentTypeResolver {

	@Override
	public List<String> resolve(Resource resource) {
		List<String> docTypes = new ArrayList<String>();
		if(EMFModelAccess.getDocumentType(resource).equals(SlicePackage.eNS_URI)) {
			ModelSlice modelSlice = (ModelSlice) resource.getContents().get(0);
			for(EPackage ePackage : modelSlice.getType()) {
				docTypes.add(ePackage.getNsURI());
			}
		}
		
		return docTypes;
	}

	@Override
	public Set<String> getDocumentTypes() {
		return Collections.singleton(SlicePackage.eNS_URI);
	}
}
