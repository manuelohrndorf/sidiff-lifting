package org.sidiff.editrule.generator.generators.actions;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Module;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.editrule.generator.configuration.Configuration;
import org.sidiff.editrule.generator.configuration.GlobalConstants;
import org.sidiff.editrule.generator.configuration.Configuration.OperationType;
import org.sidiff.editrule.generator.core.ModuleInternalsApplicator;
import org.sidiff.editrule.generator.exceptions.OperationTypeNotImplementedException;

public class SetReferenceGenerator {

	/**
	 * The outgoing EReference that should be set to a target.
	 */
	private EReference eReference;

	/**
	 * The EClassifier that has the outgoing EReference.
	 */
	private EClassifier contextEClassifier;
	
	/**
	 * The new target of the EReference.
	 */
	private EClassifier target;
	
	/**
	 * The configuration.
	 */
	private Configuration config = Configuration.getInstance();
	
	/**
	 * Constructor
	 * @param eReference
	 * @param contextEClassifier
	 * @param target
	 */
	public SetReferenceGenerator(EReference eReference, EClassifier contextEClassifier, EClassifier target) {
		
		assert (!eReference.isContainment() && eReference.getLowerBound() == 0 && eReference
				.getUpperBound() == 1);

		this.eReference = eReference;
		this.contextEClassifier = contextEClassifier;
		this.target = target;
	}
	
	public Module generate() throws OperationTypeNotImplementedException {
		
		// SET *******************************************************************************************************/
		String name = GlobalConstants.SET_REFERENCE_prefix + contextEClassifier.getName() + "_(" + eReference.getName()+ ")"+GlobalConstants.TGT+target.getName(); 
		LogUtil.log(LogEvent.NOTICE, "Generating SET_REFERENCE : " + name);

		Module SET_REFERENCE_Module = HenshinFactory.eINSTANCE.createModule();
		SET_REFERENCE_Module.setName(name);

		SET_REFERENCE_Module.setDescription("Sets "+contextEClassifier.getName()+"'s reference "+eReference.getName()+" the target "+target.getName());

		// add imports
		SET_REFERENCE_Module.getImports().addAll(config.EPACKAGESSTACK);

		// create rule
		ModuleInternalsApplicator.createBasicRule(SET_REFERENCE_Module, eReference, contextEClassifier, target, null, null, OperationType.SET_REFERENCE);

		return SET_REFERENCE_Module;
	}

}
