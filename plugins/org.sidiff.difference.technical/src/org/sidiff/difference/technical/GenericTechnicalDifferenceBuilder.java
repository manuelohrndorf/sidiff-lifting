package org.sidiff.difference.technical;

import java.util.Collections;
import java.util.Set;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * Generic technical difference builder. <br/>
 * Accepts any model type and filters nothing.
 * 
 * @author kehrer
 */
public class GenericTechnicalDifferenceBuilder extends TechnicalDifferenceBuilder {

	@Override
	protected Set<EClass> getUnconsideredNodeTypes() {
		return Collections.emptySet();
	}

	@Override
	protected Set<EReference> getUnconsideredEdgeTypes() {
		return Collections.emptySet();
	}

	@Override
	protected Set<EAttribute> getUnconsideredAttributeTypes() {
		return Collections.emptySet();
	}
	
	@Override
	protected String getObjectName(EObject obj) {
		return obj.toString();
	}

	@Override
	protected void checkDocumentType(Resource model) {
		// accept any model type
	}

	@Override
	public String getDocumentType() {
		return "generic";
	}


}
