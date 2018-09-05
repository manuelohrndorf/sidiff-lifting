package org.sidiff.slicer.rulebased.slice.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.common.emf.doctype.IDocumentTypeResolver;
import org.sidiff.slicer.rulebased.slice.RuleBasedSlicePackage;
import org.sidiff.slicer.slice.ModelSlice;

/**
 * 
 * @author piets
 *
 */
public class ModelSliceDocumentTypeResolver implements IDocumentTypeResolver {

	private static final String NS_URI = RuleBasedSlicePackage.eNS_URI;
	
	@Override
	public List<String> resolve(Resource resource) {
		List<String> docTypes = new ArrayList<String>();
		if(EMFModelAccess.getDocumentType(resource).equals(NS_URI)) {
			ModelSlice modelSlice = (ModelSlice) resource.getContents().get(0);
			for(EPackage ePackage : modelSlice.getType()) {
				docTypes.add(ePackage.getNsURI());
			}
		}
		
		return docTypes;
	}

	@Override
	public String getDocumentType() {
		return NS_URI;
	}

}
