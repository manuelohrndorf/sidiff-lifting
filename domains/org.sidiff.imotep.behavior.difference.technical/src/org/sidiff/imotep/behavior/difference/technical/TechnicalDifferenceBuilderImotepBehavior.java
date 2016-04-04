package org.sidiff.imotep.behavior.difference.technical;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.sidiff.difference.technical.AbstractTechnicalDifferenceBuilder;

import de.imotep.core.behavior.BehaviorPackage;
import de.imotep.core.datamodel.DatamodelPackage;

/**
 * @author dreuling 
 */
public class TechnicalDifferenceBuilderImotepBehavior extends AbstractTechnicalDifferenceBuilder {

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
		unconsideredAttributeTypes.add(DatamodelPackage.eINSTANCE.getMEntity_Id());		
		System.err.println("BEHAVIOR");

		return unconsideredAttributeTypes;
	}

	@Override
	protected String getObjectName(EObject obj) {		
		return obj.toString();
	}

	@Override
	public String getDocumentType() {
		return BehaviorPackage.eNS_URI;
	}

	@Override
	public String getName() {
		return "IMoTEP Behavior TDB";
	}
}
