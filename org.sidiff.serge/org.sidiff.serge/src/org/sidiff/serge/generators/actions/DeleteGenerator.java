package org.sidiff.serge.generators.actions;

import org.eclipse.emf.henshin.model.Module;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.serge.core.Common;
import org.sidiff.serge.core.Configuration.OperationType;

public class DeleteGenerator {
	
	private Module createModule;

	public DeleteGenerator(Module createModule) {
		super();
		this.createModule = createModule;
	}
	
	public Module generate(){	
		
		// inverse creation and string replaces
		Module inverseModule = Common.createInverse(createModule);
		LogUtil.log(LogEvent.NOTICE, "Generating DELETE : " + inverseModule.getName());			
		Common.replaceNewsWithToBeDeleted(inverseModule);

		// remove old mainUnit and re-create mainUnit
		Common.removeAllNonRuleUnits(inverseModule);	
		Common.mainUnitCreation(inverseModule, OperationType.DELETE);		
				
		return inverseModule;
	}
}
