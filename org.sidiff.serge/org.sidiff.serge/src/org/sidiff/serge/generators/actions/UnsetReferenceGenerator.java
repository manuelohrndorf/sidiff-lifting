package org.sidiff.serge.generators.actions;

import org.eclipse.emf.henshin.model.Module;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.serge.configuration.Configuration.OperationType;
import org.sidiff.serge.core.Common;
import org.sidiff.serge.exceptions.OperationTypeNotImplementedException;

public class UnsetReferenceGenerator {

	/**
	 * The input set reference module.
	 */
	private Module setReferenceModule;
	
	/**
	 * Constructor.
	 * @param setReferenceModule
	 */
	public UnsetReferenceGenerator(Module setReferenceModule) {
		this.setReferenceModule = setReferenceModule;
		
	}
	
	public Module generate() throws OperationTypeNotImplementedException {
		
		// inverse creation and string replaces
		Module inverseModule = Common.createInverse(setReferenceModule, OperationType.SET_REFERENCE);
		LogUtil.log(LogEvent.NOTICE, "Generating UNSET_REFERENCE : " + inverseModule.getName());			
		Common.replaceNewsWithToBeDeleted(inverseModule);
	
		return inverseModule;
	}
}
