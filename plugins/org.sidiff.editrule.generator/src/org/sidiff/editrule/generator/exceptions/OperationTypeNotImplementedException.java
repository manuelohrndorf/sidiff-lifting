package org.sidiff.editrule.generator.exceptions;

import org.sidiff.editrule.generator.types.OperationType;

@SuppressWarnings("serial")
public class OperationTypeNotImplementedException extends Exception{

	public OperationTypeNotImplementedException(OperationType opType) {
		super("The operation type '"+ opType +"' is not implemented completely");
	}
}
