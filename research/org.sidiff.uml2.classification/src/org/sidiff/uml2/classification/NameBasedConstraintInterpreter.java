package org.sidiff.uml2.classification;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.sidiff.slicer.structural.configuration.Constraint;
import org.sidiff.slicer.structural.configuration.IConstraintInterpreter;
import org.sidiff.slicer.structural.configuration.IConstraintProposal;
import org.sidiff.slicer.structural.configuration.IConstraintResult;
import org.sidiff.slicer.structural.configuration.impl.ConstraintResult;

/**
 * <p>A constraint interpreter for basic constraints containing a list
 * of the names of all the permitted {@link ENamedElement}s.</p>
 * <p>The names are separated by spaces. An empty constraint matches no object.</p>
 * <p>This kind of constraint must not be used for types that do not implement ENamedElement.<p>
 * @author Robert Müller
 *
 */
public class NameBasedConstraintInterpreter implements IConstraintInterpreter {

	public NameBasedConstraintInterpreter() {
	}

	@Override
	public String getKey() {
		return NameBasedConstraintInterpreter.class.getSimpleName();
	}

	@Override
	public IConstraintResult evaluate(Constraint constraint, EObject eObject) {
		if(eObject instanceof ENamedElement) {
			String name = ((ENamedElement)eObject).getName();
			return new ConstraintResult(constraint.getExpression().matches(".*\\b" + name + "\\b.*"), Collections.emptyList());
		}
		throw new IllegalArgumentException("context object is not of type ENamedElement, given: " + eObject);
	}

	@Override
	public List<IConstraintProposal> getSyntaxHelp(EObject context, String expression, int cursorPosition) {
		return Collections.emptyList();
	}
}
