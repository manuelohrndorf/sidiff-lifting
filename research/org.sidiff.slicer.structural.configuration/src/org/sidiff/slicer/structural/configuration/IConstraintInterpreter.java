/**
 */
package org.sidiff.slicer.structural.configuration;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.sidiff.common.extension.ExtensionManager;
import org.sidiff.common.extension.IExtension;

/**
 * A constraint interpreter for slicing configurations.
 */
public interface IConstraintInterpreter extends IExtension {

	Description<IConstraintInterpreter> DESCRIPTION = Description.of(IConstraintInterpreter.class,
			"org.sidiff.slicer.structural.configuration.constraintinterpreter", "client", "class");

	ExtensionManager<IConstraintInterpreter> MANAGER = new ExtensionManager<>(DESCRIPTION);


	/**
	 * Evaluates the constraint using the given context object.
	 * @param constraint the constraint
	 * @param eObject the context object
	 * @return constraint evaluation result
	 */
	IConstraintResult evaluate(Constraint constraint, EObject eObject);

	/**
	 * Provides syntax help for the given context and constraint expression
	 * @param context the model element that has the constraint
	 * @param expression the constraint expression
	 * @param cursorPosition the position of the cursor in the expression
	 * @return list of content proposals, a selected proposal will be <u>appended</u>
	 * to the current expression, may be empty but may not be <code>null</code>
	 */
	List<IConstraintProposal> getSyntaxHelp(EObject context, String expression, int cursorPosition);
}
