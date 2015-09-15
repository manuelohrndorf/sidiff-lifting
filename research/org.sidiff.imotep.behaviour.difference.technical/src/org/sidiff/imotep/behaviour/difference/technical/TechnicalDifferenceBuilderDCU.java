package org.sidiff.imotep.behaviour.difference.technical;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.sidiff.difference.technical.TechnicalDifferenceBuilder;

import de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorPackage;
import de.imotep.core.datamodel.de_imotep_core_datamodel.De_imotep_core_datamodelPackage;

/**
 * @author dreuling 
 */
public class TechnicalDifferenceBuilderDCU extends TechnicalDifferenceBuilder {

	@Override
	protected Set<EClass> getUnconsideredNodeTypes() {
		Set<EClass> unconsideredNodeTypes = new HashSet<EClass>();
		
		return unconsideredNodeTypes;
	}

	@Override
	protected Set<EReference> getUnconsideredEdgeTypes() {
		Set<EReference> unconsideredEdgeTypes = new HashSet<EReference>();
		
		return unconsideredEdgeTypes;
	}

	@Override
	protected Set<EAttribute> getUnconsideredAttributeTypes() {
		Set<EAttribute> unconsideredAttributeTypes = new HashSet<EAttribute>();
		
		unconsideredAttributeTypes.add(De_imotep_core_datamodelPackage.eINSTANCE.getMEntity_Id());

		return unconsideredAttributeTypes;
	}

	@Override
	protected String getObjectName(EObject obj) {		
		return obj.toString();
	}

	@Override
	public String getDocumentType() {
		return De_imotep_core_behaviorPackage.eNS_URI;
	}

	@Override
	public String getName() {
		return "DCU Technical Difference Builder";
	}
}
