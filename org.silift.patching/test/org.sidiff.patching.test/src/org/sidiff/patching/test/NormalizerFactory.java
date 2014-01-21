package org.sidiff.patching.test;

import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.sysml.SysmlPackage;
import org.sidiff.patching.test.sysml.SysMLNormalizer;
import org.silift.common.util.access.EMFModelAccessEx;

public class NormalizerFactory {

	/**
	 * Create a suitable normalizer.
	 * 
	 * @param model
	 * @return
	 */
	public static INormalizer createNormalizer(Resource model) {
		if (EMFModelAccessEx.getCharacteristicDocumentType(model).equals(
				EcorePackage.eINSTANCE.getNsURI())) {
			return new EcoreNormalizer();
		} else if (EMFModelAccessEx.getCharacteristicDocumentType(model)
				.equals(SysmlPackage.eINSTANCE.getNsURI())) {
			return new SysMLNormalizer();

		} else
			return null;
	}
}
