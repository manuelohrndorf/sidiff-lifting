package org.sidiff.serge.generators.actions;

import org.eclipse.emf.henshin.model.Module;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.serge.configuration.Configuration.OperationType;
import org.sidiff.serge.configuration.GlobalConstants;
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
		
		InverseGenerator inverseGenerator = new InverseGenerator(inputSetModule, OperationType.SET_ATTRIBUTE);
		LogUtil.log(LogEvent.NOTICE, "Generating UNSET_ATTRIBUTE : " 
									+ inputSetModule.getName().replace(GlobalConstants.SET_ATTRIBUTE_prefix, GlobalConstants.UNSET_ATTRIBUTE_prefix));
		Module UNSET_ATTRIBUTE_Module = inverseGenerator.generate();
		
		
		return UNSET_ATTRIBUTE_Module;
	}
}
