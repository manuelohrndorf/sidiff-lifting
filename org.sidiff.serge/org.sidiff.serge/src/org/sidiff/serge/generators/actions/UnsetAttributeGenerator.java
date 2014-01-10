package org.sidiff.serge.generators.actions;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.henshin.model.Module;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.serge.core.Common;
import org.sidiff.serge.core.Configuration.OperationType;
import org.sidiff.serge.exceptions.OperationTypeNotImplementedException;

public class UnsetAttributeGenerator {

	/**
	 * The input SET_ATTRIBUTE Module.
	 */
	private Module inputSetModule;

	/**
	 * Constructor
	 * @param inputSetAttributeModule
	 */
	public UnsetAttributeGenerator(Module inputSetModule) {
		this.inputSetModule = inputSetModule;
	}

	public Module generate() throws OperationTypeNotImplementedException {
		
		Module UNSET_ATTRIBUTE_Module = Common.createInverse(inputSetModule, OperationType.SET_ATTRIBUTE);
		LogUtil.log(LogEvent.NOTICE, "Generating UNSET_ATTRIBUTE : " + UNSET_ATTRIBUTE_Module.getName());

		// create mainUnit
		MainUnitGenerator mainUnitGenerator = new MainUnitGenerator(UNSET_ATTRIBUTE_Module, OperationType.UNSET_ATTRIBUTE);
		mainUnitGenerator.generate();
		
		return UNSET_ATTRIBUTE_Module;
	}
}
