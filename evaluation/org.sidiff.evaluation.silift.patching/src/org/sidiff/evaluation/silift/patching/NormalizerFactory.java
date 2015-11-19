package org.sidiff.evaluation.silift.patching;

import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.evaluation.silift.patching.ft.FtNormalizer;
import org.sidiff.evaluation.silift.patching.sa.SaNormalizer;
import org.sidiff.evaluation.silift.patching.sysml.SysMLNormalizer;

public class NormalizerFactory {

	/**
	 * Create a suitable normalizer.
	 * 
	 * @param model
	 * @return
	 */
	public static INormalizer createNormalizer(Resource model) {
		String docType = EMFModelAccess.getCharacteristicDocumentType(model);
		
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
