package org.sidiff.obeo.database.difference.technical;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.obeonetwork.dsl.database.DatabasePackage;
import org.sidiff.difference.technical.AbstractTechnicalDifferenceBuilder;

public class TechnicalDifferenceBuilderObeoDatabase extends AbstractTechnicalDifferenceBuilder{

	public TechnicalDifferenceBuilderObeoDatabase() {
	}

	@Override
	public String getName() {
		return "TechnicalDifferenceBuilder For Obeo Database";
	}

	@Override
	public String getDocumentType() {
		return DatabasePackage.eNS_URI;
	}

	@Override
	protected String getObjectName(EObject obj) {
		if (obj instanceof org.obeonetwork.dsl.database.NamedElement) {
			return ((org.obeonetwork.dsl.database.NamedElement) obj).getName();
		}
		return obj.toString();
	}

	@Override
	protected Set<EClass> getUnconsideredNodeTypes() {
		Set<EClass> unconsideredNodeTypes = new HashSet<>();	
		return unconsideredNodeTypes;//empty
	}

	@Override
	protected Set<EReference> getUnconsideredEdgeTypes() {
		Set<EReference> unconsideredReferenceTypes = new HashSet<>();
		return unconsideredReferenceTypes;//empty
	}

	@Override
	protected Set<EAttribute> getUnconsideredAttributeTypes() {
		Set<EAttribute> unconsideredAttributeTypes = new HashSet<>();
		return unconsideredAttributeTypes;//empty
	}


}
