package org.sidiff.patching.interrupt;

import java.util.Collection;
import java.util.List;

import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.patching.validation.IValidationError;

public interface IPatchInterruptHandler {
	
	public PatchInterruptOption getInterruptOption(Boolean revertedOperation, OperationInvocation operationInvocation,
			Collection<IValidationError> validationErrors );
	
}
