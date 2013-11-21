package org.sidiff.patching;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.patching.exceptions.OperationNotExecutableException;
import org.sidiff.patching.exceptions.OperationNotUndoableException;
import org.sidiff.patching.exceptions.ParameterMissingException;

public interface ITransformationEngine {
	
	String EXTENSION_POINT_ID = "org.sidiff.patching.transformationengine";
	String DOCUMENT_TYPE = "documentType";
	String EXECUTEBALE = "class";
	String DEFAULT_DOCUMENT_TYPE = "*";

	/**
	 * Sets the resource to perform operations on.
	 * 
	 * @param resource
	 */
	public void setResource(Resource resource);

	/**
	 * Executes operation of model
	 * 
	 * @param operationInvocation
	 * @param parameters
	 * @return The result objects of operation execution.
	 * @throws ParameterMissingException
	 * @throws OperationNotExecutableException
	 */
	public Map<String, Object> execute(OperationInvocation operationInvocation, Map<String, Object> parameters)
			throws ParameterMissingException, OperationNotExecutableException;
	
	/**
	 * Undoes an operation executed beforehand
	 * @param operationInvocation operation to be undone
	 * @throws OperationNotUndoableException
	 */
	public void undo(OperationInvocation operationInvocation)throws OperationNotUndoableException;

}
