package org.sidiff.serge.generators.actions;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Module;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.serge.configuration.Configuration;
import org.sidiff.serge.configuration.Configuration.OperationType;
import org.sidiff.serge.configuration.GlobalConstants;
import org.sidiff.serge.core.ModuleInternalsApplicator;
import org.sidiff.serge.exceptions.OperationTypeNotImplementedException;
import org.silift.common.util.access.EMFMetaAccessEx;

public class ChangeReferenceGenerator {

	/**
	 * The class from which the reference is outgoing.
	 */
	private EClassifier contextClass;
	
	/**
	 * The reference whose target should be changed
	 */
	private EReference reference;

	/**
	 * The target.
	 */
	private EClassifier target;
	
	/**
	 * The configuration
	 */
	private Configuration config = Configuration.getInstance();

	/**
	 * Constructor
	 * @param reference
	 * @param contextClass
	 * @param target
	 */
	public ChangeReferenceGenerator(EReference reference, EClass contextClass) {
		assert((reference.getLowerBound() >=1) && (reference.getLowerBound() == reference.getUpperBound()));
		assert(EMFMetaAccessEx.isAssignableTo(contextClass, (EClass) reference.eContainer()));
		
		this.reference = reference;
		this.contextClass = contextClass;
		this.target = reference.getEReferenceType();
	}

	public Module generate() throws OperationTypeNotImplementedException {

		String name = GlobalConstants.CHANGE_REFERENCE_prefix + contextClass.getName() + "_(" + reference.getName()+ ")" + GlobalConstants.TGT+target.getName(); 
		LogUtil.log(LogEvent.NOTICE, "Generating CHANGE_REFERENCE : " + name);

		Module CHANGE_REFERENCE_Module = HenshinFactory.eINSTANCE.createModule();
		CHANGE_REFERENCE_Module.setName(name);

		CHANGE_REFERENCE_Module.setDescription("CHANGEs "+contextClass.getName() +"'s reference "+ reference.getName() + " the target "+ target.getName());

		// add imports
		CHANGE_REFERENCE_Module.getImports().addAll(config.EPACKAGESSTACK);

		// create rule
		ModuleInternalsApplicator.createBasicRule(CHANGE_REFERENCE_Module, reference, contextClass, target, null, null, OperationType.CHANGE_REFERENCE);

		return CHANGE_REFERENCE_Module;
	}
	
}
