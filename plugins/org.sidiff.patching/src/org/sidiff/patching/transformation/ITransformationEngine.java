package org.sidiff.patching.transformation;

import java.util.Map;

import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.emf.access.Scope;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.asymmetric.ParameterBinding;
import org.sidiff.patching.ExecutionMode;
import org.sidiff.patching.exceptions.OperationNotExecutableException;
import org.sidiff.patching.exceptions.OperationNotUndoableException;
import org.sidiff.patching.exceptions.ParameterMissingException;

public interface ITransformationEngine {

	String EXTENSION_POINT_ID = "org.sidiff.patching.transformationengine";
	String DOCUMENT_TYPE = "documentType";
	String EXECUTABLE = "class";
	String DEFAULT_DOCUMENT_TYPE = "*";

	/**
	 * Initialization:
	 * <ul>
	 * <li>Set the resource to perform operations on</li>
	 * <li>Set the execution mode (interactive or batch)</li>
	 * </ul>
	 * 
	 * @param targetResource
	 */
	public void init(Resource targetResource, ExecutionMode executionMode, Scope scope);

	/**
	 * Executes an operation with the given arguments. 
	 * 
	 * @param operationInvocation
	 * @param parameters
	 * @return The result objects of operation execution.
	 * @throws ParameterMissingException
	 * @throws OperationNotExecutableException
	 */
	public Map<ParameterBinding, Object> execute(OperationInvocation operationInvocation, Map<ParameterBinding, Object> parameters)
			throws ParameterMissingException, OperationNotExecutableException;

	/**
	 * Undoes an operation executed beforehand
	 * 
	 * @param operationInvocation
	 *            operation to be undone
	 * @throws OperationNotUndoableException
	 */
	public void undo(OperationInvocation operationInvocation) throws OperationNotUndoableException;
	
	public String getKey();
	
	public String getName();

}
