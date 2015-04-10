package org.sidiff.editrule.generator.serge.generators.actions;

import org.eclipse.emf.henshin.model.Module;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.editrule.generator.exceptions.OperationTypeNotImplementedException;
import org.sidiff.editrule.generator.serge.configuration.GlobalConstants;
import org.sidiff.editrule.generator.types.OperationType;

public class DetachGenerator {

	private final Module attachModule;

	public DetachGenerator(Module attachModule) {
		this.attachModule = attachModule;
	}

	public Module generate() throws OperationTypeNotImplementedException{	
		
		// inverse creation and string replaces
		LogUtil.log(LogEvent.NOTICE, "Generating DETACH: " 
								+ attachModule.getName().replace(GlobalConstants.ATTACH_prefix, GlobalConstants.DETACH_prefix));
		InverseGenerator ivg = new InverseGenerator(attachModule, OperationType.ATTACH);
		return ivg.generate();
	}
}
