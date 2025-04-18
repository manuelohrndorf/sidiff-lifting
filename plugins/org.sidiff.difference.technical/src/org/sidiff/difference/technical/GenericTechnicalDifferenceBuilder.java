package org.sidiff.difference.technical;

import java.util.Collections;
import java.util.Set;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

/**
 * Generic technical difference builder. <br/>
 * Accepts any model type and filters nothing.
 * 
 * @author kehrer
 */
public class GenericTechnicalDifferenceBuilder extends AbstractTechnicalDifferenceBuilder {

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
	public Set<String> getDocumentTypes() {
		return Collections.singleton(GENERIC_TYPE);
	}

	@Override
	public boolean isGeneric() {
		return true;
	}

	@Override
	public String getKey() {
		return "GenericTechnicalDifferenceBuilder";
	}

	@Override
	public String getName(){
		return "Generic Technical Difference Builder";
	}	
}
