package org.sidiff.uml2.v4.profile.handler;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.ProfileApplication;
import org.eclipse.uml2.uml.UMLPackage;
import org.sidiff.common.collections.CollectionUtil;
import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.common.emf.access.Scope;
import org.sidiff.difference.profiles.handler.IDifferenceProfileHandler;

/**
 * An {@link IDifferenceProfileHandler} to handle profiled UML models
 * 
 * @author cpietsch
 *
 */
public class UML2v4ProfileHandler implements IDifferenceProfileHandler {

	@Override
	public boolean isProfiled(Resource resource) {
		if(EMFModelAccess.getDocumentTypes(resource, Scope.RESOURCE).contains(UMLPackage.eNS_URI)) {
			return CollectionUtil.asStream(resource.getAllContents()).anyMatch(ProfileApplication.class::isInstance);
		}
		return false;
	}

	@Override
	public Set<String> getDocumentTypes() {
		return Collections.singleton(UMLPackage.eNS_URI);
	}

	@Override
	public Set<String> getProfileTypes(Resource resource) {
		return CollectionUtil.asStream(resource.getAllContents())
				.filter(ProfileApplication.class::isInstance)
				.map(ProfileApplication.class::cast)
				.map(ProfileApplication::getAppliedProfile)
				.map(Profile::getURI)
				.collect(Collectors.toSet());
	}
}
