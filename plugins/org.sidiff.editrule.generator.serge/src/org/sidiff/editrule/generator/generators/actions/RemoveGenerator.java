package org.sidiff.editrule.generator.generators.actions;

import org.eclipse.emf.henshin.model.Module;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.editrule.generator.configuration.GlobalConstants;
import org.sidiff.editrule.generator.configuration.Configuration.OperationType;
import org.sidiff.editrule.generator.exceptions.OperationTypeNotImplementedException;

public class RemoveGenerator {
	
	/**
	 * The input add module.
	 */
	private Module addModule;

	/**
	 * Constructor
	 * @param addModule
	 */
	public RemoveGenerator(Module addModule) {
		super();
		this.addModule = addModule;
	}
	
	public Module generate() throws OperationTypeNotImplementedException{
		
		InverseGenerator inverseGenerator = new InverseGenerator(addModule, OperationType.ADD);
		LogUtil.log(LogEvent.NOTICE, "Generating REMOVE : "
							+ addModule.getName().replace(GlobalConstants.ADD_prefix,GlobalConstants.REMOVE_prefix));
		Module REMOVE_Module = inverseGenerator.generate();
		
		
		return REMOVE_Module;
	}
}
