package org.sidiff.serge.generators.actions;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Module;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.serge.core.Common;
import org.sidiff.serge.core.Configuration;
import org.sidiff.serge.core.GlobalConstants;
import org.sidiff.serge.core.Configuration.OperationType;
import org.sidiff.serge.exceptions.OperationTypeNotImplementedException;

public class ChangeReferenceGenerator {

	/**
	 * The reference whose target should be changed
	 */
	private EReference reference;
	/**
	 * The class from which the reference is outgoing.
	 */
	private EClassifier contextClass;
	
	private Configuration config = Configuration.getInstance();

	public ChangeReferenceGenerator(EReference reference, EClass contextClass) {
		assert(reference.getLowerBound() == 1 && reference.getUpperBound() == 1);
		
		this.reference = reference;
		this.contextClass = contextClass;
	}

	public Module generate(EClassifier target) throws OperationTypeNotImplementedException {

		String name = GlobalConstants.CHANGE_REFERENCE_prefix + contextClass.getName() + "_(" + reference.getName()+ ")" + GlobalConstants.TGT+target.getName(); 
		LogUtil.log(LogEvent.NOTICE, "Generating CHANGE : " + name);

		Module CHANGE_REFERENCE_Module = HenshinFactory.eINSTANCE.createModule();
		CHANGE_REFERENCE_Module.setName(name);

		CHANGE_REFERENCE_Module.setDescription("CHANGEs "+contextClass.getName() +"'s reference "+ reference.getName() + " the target "+ target.getName());

		// add imports
		CHANGE_REFERENCE_Module.getImports().addAll(config.EPACKAGESSTACK);

		// create rule
		Common.createBasicRule(CHANGE_REFERENCE_Module, reference, contextClass, target, null, null, OperationType.CHANGE_REFERENCE);

		// create mainUnit
		MainUnitGenerator mainUnitGenerator = new MainUnitGenerator(CHANGE_REFERENCE_Module, OperationType.CHANGE_REFERENCE);
		mainUnitGenerator.generate();

		return CHANGE_REFERENCE_Module;
	}
}
