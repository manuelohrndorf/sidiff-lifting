package org.silift.common.util.access;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.silift.common.util.emf.EMFStorage;
import org.silift.common.util.emf.Scope;

/**
 * 
 * 
 * @author kehrer, mohrndorf, dreuling, cpietsch
 */
public class EMFModelAccessEx {

	/**
	 * Constant that represents a "generic document type". If functions that are
	 * usually designed for a specific document type (e.g., matchers or
	 * technical difference builders) are generic in the sense that they can
	 * handle any document type, the y can indicate this genericity by using
	 * this constant as supported document type.
	 */
	public static final String GENERIC_DOCUMENT_TYPE = "generic";

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
			if (docType.contains("UML") || docType.contains("Henshin/Trace")) {
				iterator.remove();
			}
		}

		if (documentTypes.size() == 1) {
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
	 * In other words: Given a set of document types, we try to select the most
	 * characteristic document type. Most often, docTypes will contain only one
	 * element. However, there may be cases with multiple document types. Two
	 * examples are currently supported, feel free to add (and implement) more
	 * of them:
	 * 
	 * (a) In case of UML profiled models, there are two document types; one
	 * representing the reference meta-model and the other one representing the
	 * profile definition. In such a case, the document type of the profile
	 * definition is to be considered characteristic. If there are even more
	 * than two document types, the first one is returned.
	 * 
	 * (b) In case of multi-view models that are connected via Henshin trace
	 * links, we have at least two documents types; the Henshin/Trace document
	 * type and the docType of the model that holds the trace links.
	 * 
	 * @param model
	 * @return The characteristic document type. Note: If model is empty,
	 *         <code>null</code> will be returned.
	 * 
	 * @param model
	 * @return
	 */
	public static String getCharacteristicDocumentType(Resource model) {
		// empty model -> return null
		if (model.getContents().isEmpty()) {
			return null;
		}

		// no docTypes -> return null
		Set<String> docTypes = getDocumentTypes(model, Scope.RESOURCE);
		if (docTypes.isEmpty()) {
			return null;
		}

		List<String> documentTypes = new LinkedList<String>();
		documentTypes.addAll(docTypes);

		if (documentTypes.size() == 1) {
			// documentType is non-ambiguous
			return documentTypes.get(0);
		}

		boolean containsUML = containsDocType(documentTypes, "UML");
		boolean containsHenshinTrace = containsDocType(documentTypes, "Henshin/Trace");

		if (containsHenshinTrace) {
			removeDocType(documentTypes, "Henshin/Trace");
		}
		if (containsUML && documentTypes.size() > 1) {
			removeDocType(documentTypes, "UML");
		}

		// default
		if (documentTypes.size() == 1) {
			return documentTypes.get(0);
		} else {
			return docTypes.iterator().next();
		}
	}

	private static boolean containsDocType(Collection<String> documentTypes, String docTypeFragment) {
		for (Iterator<String> iterator = documentTypes.iterator(); iterator.hasNext();) {
			String docType = (String) iterator.next();
			if (docType.contains(docTypeFragment)) {
				return true;
			}
		}

		return false;
	}

	private static void removeDocType(List<String> documentTypes, String docTypeFragment) {
		for (Iterator<String> iterator = documentTypes.iterator(); iterator.hasNext();) {
			String docType = (String) iterator.next();
			if (docType.contains(docTypeFragment)) {
				iterator.remove();
			}
		}
	}

	/**
	 * For each of the root objects of the given modelResource, the retrieval of
	 * the document type is simply delegated to the method
	 * {@link EMFModelAccessEx#getDocumentType(EObject)}. The set of document
	 * types which is obtained this way is finally returned.
	 * 
	 * @param modelResource
	 * @return
	 */
	public static Set<String> getDocumentTypes(Resource modelResource, Scope scope) {
		List<Resource> resources = new ArrayList<Resource>();
		if (scope == Scope.RESOURCE_SET) {
			resources.addAll(modelResource.getResourceSet().getResources());
		} else {
			resources.add(modelResource);
		}

		// Collect all document types of root objects
		Set<String> documentTypes = new HashSet<String>();
		for (Resource resource : resources) {
			for (EObject root : resource.getContents()) {
				documentTypes.add(getDocumentType(root));
			}
		}

		return documentTypes;
	}

	/**
	 * Simple heuristic to check whether a model is profiled or not:
	 * <ul>
	 * <li>more than one docType</li>
	 * <li>containsUML</li>
	 * <li>!containsHenshinTrace</li>
	 * </ul>
	 * 
	 * Feel free to refine the above heuristic.
	 * 
	 * @return true if the model contained by the given resource is profiled.
	 */
	public static boolean isProfiled(Resource model) {
		Set<String> documentTypes = getDocumentTypes(model, Scope.RESOURCE);
		boolean containsUML = containsDocType(documentTypes, "UML");
		boolean containsHenshinTrace = containsDocType(documentTypes, "Henshin/Trace");

		return (documentTypes.size() > 1) && containsUML && !containsHenshinTrace;
	}


	/**
	 * derives the diagram file and all other files if exist
	 * 
	 * @param model
	 * @return
	 */
	public static ResourceSet deriveDiagramFile(Resource model) {
		String path = EMFStorage.uriToPath(model.getURI());
		ResourceSet resourceSet = new ResourceSetImpl();
		try {
			if (EMFModelAccessEx.getCharacteristicDocumentType(model).contains("Ecore")) {
				path += "diag";
				resourceSet.getResources().add(EMFStorage.eLoad(EMFStorage.pathToUri(path)).eResource());
			} else if (EMFModelAccessEx.getCharacteristicDocumentType(model).contains("SysML")) {
				path = path.replace(".uml", ".di");
				resourceSet.getResources().add(EMFStorage.eLoad(EMFStorage.pathToUri(path)).eResource());
				path = path.replace(".di", ".notation");
				resourceSet.getResources().add(EMFStorage.eLoad(EMFStorage.pathToUri(path)).eResource());
			}
			// TODO other domains
		} catch (Exception e) {
			LogUtil.log(LogEvent.NOTICE, e.getMessage());
		}
		return resourceSet;
	}

	/**
	 * Returns the URI fragment of the given object.
	 * 
	 * @param eObject
	 *            the object
	 * @return the object URI fragment.
	 */
	public static String getURIFragment(EObject eObject) {
		if (eObject.eIsProxy()) {
			return ((InternalEObject) eObject).eProxyURI().fragment();
		} else {
			return eObject.eResource().getURIFragment(eObject);
		}
	}

}
