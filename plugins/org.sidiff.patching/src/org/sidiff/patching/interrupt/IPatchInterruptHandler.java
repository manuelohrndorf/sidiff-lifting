package org.sidiff.patching.interrupt;

import java.util.Collection;

import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.patching.validation.IValidationError;

public interface IPatchInterruptHandler {

	PatchInterruptOption getInterruptOption(boolean revertedOperation,
			OperationInvocation operationInvocation,
			Collection<IValidationError> validationErrors);
}
