package org.silift.difference.symboliclink.handler.namedelement;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.collections.CollectionUtil;
import org.sidiff.common.emf.EMFResourceUtil;
import org.sidiff.common.emf.EMFUtil;
import org.sidiff.common.emf.access.EObjectLocation;
import org.silift.difference.namedelementsymboliclink.NamedElementSymbolicLinkObject;
import org.silift.difference.namedelementsymboliclink.NamedelementsymboliclinkFactory;
import org.silift.difference.symboliclink.SymbolicLinkObject;
import org.silift.difference.symboliclink.handler.AbstractDifferenceSymbolicLinkHandler;

/**
 * A name-based symbolic-link-handler using the qualified name of an object.
 * 
 * @author cpietsch
 *
 */
public class NamedElementSymbolicLinkHandler extends AbstractDifferenceSymbolicLinkHandler {

	@Override
	public SymbolicLinkObject generateInternalSymbolicLinkObject(EObject eObject) {
		String name = deriveQualifiedName(eObject);
		if(name == null) {
			return null;
		}
		NamedElementSymbolicLinkObject link = NamedelementsymboliclinkFactory.eINSTANCE.createNamedElementSymbolicLinkObject();
		link.setQualifiedName(name);
		link.setReliability(1.f);
		return link;
	}

	@Override
	public EObject resolveSymbolicLinkObject(SymbolicLinkObject symbolicLinkObject, Resource targetModel) {
		NamedElementSymbolicLinkObject namedElementSymbolicLink = (NamedElementSymbolicLinkObject)symbolicLinkObject;
		for (EObject eObject : CollectionUtil.asIterable(targetModel.getAllContents())) {
			if(EMFResourceUtil.locate(targetModel, eObject) == EObjectLocation.RESOURCE_INTERNAL
					&& namedElementSymbolicLink.getQualifiedName().equals(deriveQualifiedName(eObject))){
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
	private static String deriveQualifiedName(EObject eObject){
		String name = EMFUtil.getEObjectSignatureName(eObject);
		Assert.isNotNull(name, eObject + " has no name");
		if(eObject.eContainer() != null){
			return deriveQualifiedName(eObject.eContainer()) + "." + name;
		}
		return name;
	}

	@Override
	public boolean canComputeReliability() {
		return false;
	}
}
