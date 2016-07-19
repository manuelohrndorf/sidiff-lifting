package org.sidiff.obeo.database.typelibrary.difference.technical;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.obeonetwork.dsl.typeslibrary.TypesLibraryPackage;
import org.sidiff.difference.technical.AbstractTechnicalDifferenceBuilder;

public class TDiffBuilderObeoDatabaseTypeLibrary  extends AbstractTechnicalDifferenceBuilder{

	public TDiffBuilderObeoDatabaseTypeLibrary() {
	}

	@Override
	public String getName() {
		return "TechnicalDifferenceBuilder For Obeo Database Type Library";
	}

	@Override
	public String getDocumentType() {
		return TypesLibraryPackage.eNS_URI;
	}

	@Override
	protected String getObjectName(EObject obj) {
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
