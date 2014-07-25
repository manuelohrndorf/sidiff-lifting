package org.sidiff.serge.generators.actions;

import org.eclipse.emf.henshin.model.Module;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.serge.configuration.Configuration;
import org.sidiff.serge.configuration.Configuration.OperationType;
import org.sidiff.serge.core.Common;
import org.sidiff.serge.core.InverseModuleMapper;
import org.sidiff.serge.exceptions.OperationTypeNotImplementedException;

public class UnsetAttributeGenerator {

	/**
	 * Configuration access
	 */
	private Configuration config = Configuration.getInstance();
	
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
		
		return UNSET_ATTRIBUTE_Module;
	}
}
