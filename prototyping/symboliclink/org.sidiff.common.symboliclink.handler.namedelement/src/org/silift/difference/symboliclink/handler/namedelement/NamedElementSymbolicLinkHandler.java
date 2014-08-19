package org.silift.difference.symboliclink.handler.namedelement;

import java.util.Iterator;

import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.silift.common.util.emf.EMFResourceUtil;
import org.silift.common.util.emf.EObjectLocation;
import org.silift.difference.namedelementsymboliclink.NamedElementSymbolicLink;
import org.silift.difference.namedelementsymboliclink.NamedelementsymboliclinkFactory;
import org.silift.difference.symboliclink.SymbolicLink;
import org.silift.difference.symboliclink.SymbolicLinks;
import org.silift.difference.symboliclink.handler.AbstractSymbolicLinkHandler;

/**
 * A name-based symbolic-link-handler.
 * @author cpietsch
 *
 */
public class NamedElementSymbolicLinkHandler extends AbstractSymbolicLinkHandler {

	private static final String NAME = "Named Element Symbolic Link Handler";
	private static final String KEY = "NamedElementSymbolicLinkHandler";
	
	@Override
	public SymbolicLink generateSymbolicLink(SymbolicLinks symbolicLinks,
			EObject eObject) {
		String name = deriveQualifiedName(eObject);
		for(SymbolicLink l : symbolicLinks.getLinks()){
			NamedElementSymbolicLink link = (NamedElementSymbolicLink) l;
			if(link.getQualifiedName() != null && link.getQualifiedName().equals(name)){
				return link;
			}
		}
		NamedElementSymbolicLink link = NamedelementsymboliclinkFactory.eINSTANCE.createNamedElementSymbolicLink();
		link.setQualifiedName(name);
		link.setReliability(1.f);
		symbolicLinks.getLinks().add(link);
		return link;
	}

	@Override
	public EObject resolveSymbolicLink(SymbolicLink symbolicLink, Resource targetModel) {
		NamedElementSymbolicLink namedElementSymbolicLink = (NamedElementSymbolicLink)symbolicLink;
		for (Iterator<EObject> iterator = targetModel.getAllContents(); iterator.hasNext();) {
			EObject eObject = (EObject) iterator.next();
			EObjectLocation location = EMFResourceUtil.locate(targetModel, eObject);
			String name = deriveQualifiedName(eObject);
			if(location.equals(EObjectLocation.RESOURCE_INTERNAL) && namedElementSymbolicLink.getQualifiedName().equals(name)){
				return eObject;
			}
		}
		return null;
	}

	/**
	 * Help method that derives the qualified name of a model element.
	 * 
	 * @param eObject
	 * 			a model element
	 *
	 * @return a String representation of the qualified Name
	 */
	private String deriveQualifiedName(EObject eObject){
		String featureName = "";
		if(eObject instanceof ENamedElement){
			featureName = eObject.eGet(eObject.eClass().getEStructuralFeature("name")).toString();
			assert (featureName != ""): eObject + "has no name";
			while (eObject.eContainer() != null){
				eObject = eObject.eContainer();
				featureName = eObject.eGet(eObject.eClass().getEStructuralFeature("name")) + "." + featureName;
			}
		}
		return featureName;
	}

	@Override
	public boolean canComputeReliability() {
		return false;
	}
	
	@Override
	public String getName(){
		return NAME;
	}
	
	@Override
	public String getKey(){
		return KEY;
	}
}
