package org.sidiff.editrule.generator.serge.generators.actions;

import org.eclipse.emf.henshin.model.Module;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.editrule.generator.exceptions.OperationTypeNotImplementedException;
import org.sidiff.editrule.generator.serge.configuration.GlobalConstants;
import org.sidiff.editrule.generator.types.OperationType;

public class DeleteGenerator {
	
	/**
	 * The input create module.
	 */
	private Module createModule;

	
	/**
	 * Constructor
	 * @param createModule
	 */
	public DeleteGenerator(Module createModule) {
		super();
		this.createModule = createModule;
	}
	
	public Module generate() throws OperationTypeNotImplementedException{	
		
		// inverse creation and string replaces
		LogUtil.log(LogEvent.NOTICE, "Generating DELETE : " 
								+ createModule.getName().replace(GlobalConstants.CREATE_prefix, GlobalConstants.DELETE_prefix));
		InverseGenerator inverseGenerator = new InverseGenerator(createModule, OperationType.CREATE);
		Module inverseModule = inverseGenerator.generate();
		
		return inverseModule;
	}
}
