package org.sidiff.imotep.behavior.annotated.difference.technical;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.sidiff.difference.technical.AbstractTechnicalDifferenceBuilder;

import de.imotep.core.datamodel.DatamodelPackage;
import de.imotep.variability.annotatedBehavior.AnnotatedBehaviorPackage;

/**
 * @author dreuling 
 */
public class TechnicalDifferenceBuilderImotepAnnotatedBehavior extends AbstractTechnicalDifferenceBuilder {

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
		unconsideredAttributeTypes.add(DatamodelPackage.eINSTANCE.getMNamedEntity_Name());

		return unconsideredAttributeTypes;
	}

	@Override
	protected String getObjectName(EObject obj) {		
		return obj.toString();
	}

	@Override
	public String getDocumentType() {
		return AnnotatedBehaviorPackage.eNS_URI;
	}

	@Override
	public String getName() {
		return "IMoTEP Annotated Behavior TDB";
	}
}
