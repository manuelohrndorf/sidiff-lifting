package org.silift.difference.symboliclink.handler.namedelement;

import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.collections.CollectionUtil;
import org.sidiff.common.emf.EMFResourceUtil;
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

		NamedElementSymbolicLinkObject link = null;
		String name = deriveQualifiedName(eObject);
		
		if(name != null){
			link = NamedelementsymboliclinkFactory.eINSTANCE.createNamedElementSymbolicLinkObject();
			link.setQualifiedName(name);
			link.setReliability(1.f);
		}
		return link;
	}

	@Override
	public EObject resolveSymbolicLinkObject(SymbolicLinkObject symbolicLinkObject, Resource targetModel) {
		
		NamedElementSymbolicLinkObject namedElementSymbolicLink = (NamedElementSymbolicLinkObject)symbolicLinkObject;
		
		for (EObject eObject : CollectionUtil.asIterable(targetModel.getAllContents())) {
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
	private static String deriveQualifiedName(EObject eObject){
		
		String featureName = null;
		
		if(eObject instanceof ENamedElement){
			
			featureName = eObject.eGet(eObject.eClass().getEStructuralFeature("name")).toString();
			
			assert (featureName != ""): eObject + "isn't set";
			
			while (eObject.eContainer() != null){
			
				eObject = eObject.eContainer();
				featureName = eObject.eGet(eObject.eClass().getEStructuralFeature("name")) + "." + featureName;
			}
		}
		
		assert (featureName != null): eObject +"("+eObject.eClass().getName()+")" + "has no name";
		
		return featureName;
	}

	@Override
	public boolean canComputeReliability() {
		return false;
	}
}
