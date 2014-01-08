package org.sidiff.patching.test;

import java.util.Collection;
import java.util.List;

import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.patching.interrupt.IPatchInterruptHandler;
import org.sidiff.patching.interrupt.PatchInterruptOption;
import org.sidiff.patching.validation.IValidationError;

public class BatchInterruptHandler implements IPatchInterruptHandler {

	@Override
	public PatchInterruptOption getInterruptOption(Boolean revertedOperation,
			OperationInvocation operationInvocation,
			Collection<IValidationError> validationErrors) {
		return PatchInterruptOption.IGNORE; 
	}

}
