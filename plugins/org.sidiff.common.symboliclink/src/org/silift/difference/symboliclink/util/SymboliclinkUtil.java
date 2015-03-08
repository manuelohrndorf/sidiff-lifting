package org.silift.difference.symboliclink.util;

import org.eclipse.emf.ecore.resource.Resource;
import org.silift.common.util.access.EMFModelAccessEx;
import org.silift.difference.symboliclink.SymbolicLinks;
import org.silift.difference.symboliclink.SymboliclinkPackage;


public class SymboliclinkUtil {

	/**
	 * Searches for the characteristic document type of the (symbolic) model.
	 * 
	 * @param model
	 *            The (symbolic) model to analyze.
	 * @return The characteristic document type. Note: If docTypes is empty,
	 *         <code>null</code> will be returned.
	 * 
	 * @see EMFModelAccessEx#getCharacteristicDocumentType(java.util.Set)
	 */
	public static String resolveCharacteristicDocumentType(Resource model) {
		
		String characteristicDocumentType = EMFModelAccessEx.getCharacteristicDocumentType(model);
		
		// If the model is a symbolic link file, get the doc type information from the appropriate field:
		if ((characteristicDocumentType != null) && (characteristicDocumentType.equals(SymboliclinkPackage.eNS_URI))) {
			characteristicDocumentType = ((SymbolicLinks) model.getContents().get(0)).getDocType();
		}
		
		return characteristicDocumentType;
	}
	
}
