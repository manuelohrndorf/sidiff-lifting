package org.sidiff.patching.test;

import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.patching.test.ft.FtNormalizer;
import org.sidiff.patching.test.sa.SaNormalizer;
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
		String docType = EMFModelAccessEx.getCharacteristicDocumentType(model);
		
		if (docType.equals("http://www.eclipse.org/emf/2002/Ecore")) {
			return new EcoreNormalizer();
		} else if (docType.equals("http://www.eclipse.org/papyrus/0.7.0/SysML")) {
			return new SysMLNormalizer();
		} else if (docType.equals("http://SA/1.0")) {
			return new SaNormalizer();
		} else if (docType.equals("http://FaultTree/1.0")) {
			return new FtNormalizer();
		} else {
			assert(false) : "No Normalizer available for characterisitc model type " + docType;
			return null;
		}
	}
}
