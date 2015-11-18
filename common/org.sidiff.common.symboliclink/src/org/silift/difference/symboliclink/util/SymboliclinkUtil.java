package org.silift.difference.symboliclink.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.emf.access.EMFModelAccess;
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
		
		String characteristicDocumentType = EMFModelAccess.getDocumentType(model);
		
		// If the model is a symbolic link file, get the doc type information from the appropriate field:
		if ((characteristicDocumentType != null) && (characteristicDocumentType.equals(SymboliclinkPackage.eNS_URI))) {
			characteristicDocumentType = ((SymbolicLinks) model.getContents().get(0)).getDocType();
		}
		
		return characteristicDocumentType;
	}
	
	/**
	 * Searches for the characteristic document type of the eObject
	 * 
	 * @param eObject
	 *            The eObject to analyze.
	 * @return The characteristic document type
	 */
	public static String resolveCharacteristicDocumentType(EObject eObject){
		EObject root = eObject;
		while(root.eContainer() != null){
			root = root.eContainer();
		}
		EPackage pkg = root.eClass().getEPackage();
		String characteristicDocumentType = pkg.getNsURI();
		
		// If the model is a symbolic link file, get the doc type information from the appropriate field:
		if ((characteristicDocumentType != null) && (characteristicDocumentType.equals(SymboliclinkPackage.eNS_URI))) {
			characteristicDocumentType = ((SymbolicLinks) root).getDocType();
		}
		
		return characteristicDocumentType;
	}
	
}
