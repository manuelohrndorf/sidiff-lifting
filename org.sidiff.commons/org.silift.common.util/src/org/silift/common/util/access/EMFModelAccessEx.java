package org.silift.common.util.access;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;

public class EMFModelAccessEx {

	/**
	 * Selects, given a set of document types, the most characteristic document
	 * type. Most often, docTypes will contain only one element. However, there
	 * may be cases with multiple document types. In case of UML profiled
	 * models, for example, there are two document types; representing the
	 * reference metamodel and the profile definition. In such a case, the
	 * document type of the profile definition is to be considered
	 * characteristic. If there are even more than two document types, the first
	 * is returned.
	 * 
	 * @param docTypes
	 * @return The characteristic document type. Note: If docTypes is empty,
	 *         <code>null</code> will be returned.
	 */
	public static String getCharacteristicDocumentType(Set<String> docTypes) {
		if (docTypes.isEmpty()) {
			return null;
		}

		List<String> documentTypes = new LinkedList<String>();
		documentTypes.addAll(docTypes);	
		
		if (documentTypes.size() == 1) {
			// documentType is nonambiguous
			return documentTypes.get(0);
		}

		// Remove irrelevant docTypes
		for (Iterator<String> iterator = documentTypes.iterator(); iterator.hasNext();) {
			String docType = (String) iterator.next();
			if (docType.contains("UML") || docType.contains("Henshin/Trace")){
				iterator.remove();
			}
		}
		
		if (documentTypes.size() == 1){
			return documentTypes.get(0);
		} else {
			return docTypes.iterator().next();
		}
	}

	/**
	 * Returns the document type (i.e. the package namespace URI) of the model
	 * of the given element.
	 * 
	 * @param eObject
	 * @return
	 */
	public static String getDocumentType(EObject object) {
		EPackage pkg = object.eClass().getEPackage();
		while (pkg != null && pkg.getESuperPackage() != null) {
			pkg = pkg.getESuperPackage();
		}
		return pkg.getNsURI();
	}

	/**
	 * First, all document types are extracted from the given model resource.
	 * Afterwards, the most characteristic one is selected and returned.
	 * 
	 * @param model
	 * @return
	 */
	public static String getCharacteristicDocumentType(Resource model) {
		if (model.getContents().isEmpty()) {
			return null;
		}

		// return most proper docType
		return getCharacteristicDocumentType(getDocumentTypes(model));
	}

	/**
	 * For each of the root objects of the given package, the retrieval of the
	 * document type is simply delegated to the method
	 * {@link EMFModelAccessEx#getDocumentType(EObject)}. The set of document
	 * types which is obtained this way is finally returned.
	 * 
	 * @param model
	 * @return
	 */
	public static Set<String> getDocumentTypes(Resource model) {
		// Collect all document types of root objects
		Set<String> documentTypes = new HashSet<String>();
		for (EObject root : model.getContents()) {
			documentTypes.add(getDocumentType(root));
		}

		return documentTypes;
	}

	// TODO: Prüfen und dokumentieren!!

	public static boolean isProfiled(Resource model) {
		return getDocumentTypes(model).size() > 1;
	}

	public static String getBaseDocumentType(Resource model) {
		assert (isProfiled(model)) : model + " is not a profile!";

		String profileDocType = getProfileDocumentType(model);
		for (String docType : getDocumentTypes(model)) {
			if (!docType.equals(profileDocType)) {
				return docType;
			}
		}

		// should never happen
		return null;
	}

	public static String getProfileDocumentType(Resource model) {
		assert (isProfiled(model)) : model + " is not a profile!";

		return getCharacteristicDocumentType(model);
	}

}
