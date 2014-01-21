package org.sidiff.patching.test;

import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.sysml.SysmlPackage;
import org.sidiff.patching.test.ft.FtNormalizer;
import org.sidiff.patching.test.sa.SaNormalizer;
import org.sidiff.patching.test.sysml.SysMLNormalizer;
import org.silift.common.util.access.EMFModelAccessEx;

import FaultTree.FaultTreePackage;
import SA.SAPackage;

public class NormalizerFactory {

	/**
	 * Create a suitable normalizer.
	 * 
	 * @param model
	 * @return
	 */
	public static INormalizer createNormalizer(Resource model) {
		if (EMFModelAccessEx.getCharacteristicDocumentType(model).equals(EcorePackage.eINSTANCE.getNsURI())) {
			return new EcoreNormalizer();
		} else if (EMFModelAccessEx.getCharacteristicDocumentType(model).equals(SysmlPackage.eINSTANCE.getNsURI())) {
			return new SysMLNormalizer();
		} else if (EMFModelAccessEx.getCharacteristicDocumentType(model).equals(SAPackage.eINSTANCE.getNsURI())) {
			return new SaNormalizer();
		} else if (EMFModelAccessEx.getCharacteristicDocumentType(model).equals(FaultTreePackage.eINSTANCE.getNsURI())) {
			return new FtNormalizer();
		} else {
			return null;
		}
	}
}
