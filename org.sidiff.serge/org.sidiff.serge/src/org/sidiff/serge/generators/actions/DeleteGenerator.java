package org.sidiff.serge.generators.actions;

import org.eclipse.emf.henshin.model.Module;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.serge.core.Common;
import org.sidiff.serge.core.Configuration.OperationType;
import org.sidiff.serge.exceptions.OperationTypeNotImplementedException;

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
		Module inverseModule = Common.createInverse(createModule, OperationType.CREATE);
		LogUtil.log(LogEvent.NOTICE, "Generating DELETE : " + inverseModule.getName());			
		Common.replaceNewsWithToBeDeleted(inverseModule);

		// mainUnit and re-create mainUnit
		MainUnitGenerator mainUnitGenerator = new MainUnitGenerator(inverseModule, OperationType.DELETE);
		mainUnitGenerator.generate();		
				
		return inverseModule;
	}
}
