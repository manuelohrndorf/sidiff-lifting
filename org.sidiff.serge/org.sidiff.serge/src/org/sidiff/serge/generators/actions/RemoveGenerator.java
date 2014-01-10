package org.sidiff.serge.generators.actions;

import org.eclipse.emf.henshin.model.Module;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.serge.core.Common;
import org.sidiff.serge.core.Configuration.OperationType;
import org.sidiff.serge.exceptions.OperationTypeNotImplementedException;

public class RemoveGenerator {
	
	private Module addModule;

	public RemoveGenerator(Module addModule) {
		super();
		this.addModule = addModule;
	}
	
	public Module generate() throws OperationTypeNotImplementedException{
		
		Module REMOVE_Module = Common.createInverse(addModule, OperationType.ADD);
		LogUtil.log(LogEvent.NOTICE, "Generating REMOVE : " + REMOVE_Module.getName());

		// create mainUnit
		MainUnitGenerator mainUnitGenerator = new MainUnitGenerator(REMOVE_Module, OperationType.REMOVE);
		mainUnitGenerator.generate();
		
		return REMOVE_Module;
	}
}
