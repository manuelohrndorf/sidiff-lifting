package org.sidiff.uml2.v4.profile.handler;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.uml2.uml.ProfileApplication;
import org.eclipse.uml2.uml.UMLPackage;
import org.sidiff.difference.profiles.handler.IDifferenceProfileHandler;
import org.silift.common.util.access.EMFModelAccessEx;
import org.silift.common.util.emf.Scope;

/**
 * An {@link IDifferenceProfileHandler} to handle profiled UML models
 * 
 * @author cpietsch
 *
 */
public class UML2v4ProfileHandler implements IDifferenceProfileHandler {

	private final String baseType = UMLPackage.eINSTANCE.getNsURI();
	
	public UML2v4ProfileHandler() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isProfiled(Resource resource) {
		if(EMFModelAccessEx.getDocumentTypes(resource, Scope.RESOURCE).contains(baseType)){
			for (Iterator<EObject> iterator = resource.getAllContents(); iterator.hasNext();) {
				EObject eObject = iterator.next();
				if(eObject instanceof ProfileApplication){
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public String getBaseType() {
		return baseType;
	}

	@Override
	public Set<String> getProfileTypes(Resource resource) {
		Set<String> profileDocTypes = new HashSet<String>();
		for (Iterator<EObject> iterator = resource.getAllContents(); iterator.hasNext();) {
			EObject eObject = iterator.next();
			if(eObject instanceof ProfileApplication){
				profileDocTypes.add(((ProfileApplication)eObject).getAppliedProfile().getURI());
			}
		}
		return profileDocTypes;
	}

}
