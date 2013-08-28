package org.sidiff.patching.test;

import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.emf.access.EMFModelAccess;

public class NormalizerFactory {
	
	/**
	 * Create a suitable normalizer.
	 * 
	 * @param model
	 * @return
	 */
	public static INormalizer createNormalizer(Resource model){
		if (EMFModelAccess.getDocumentType(model).equals(EcorePackage.eINSTANCE.getNsURI())){
			return new EcoreNormalizer();
		}
		
		// TODO: SysML normalizer
		
		return null;
	}
}
