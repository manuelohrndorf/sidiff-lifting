package org.sidiff.uml2.classification;

import java.util.Collections;

import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.fieldassist.IContentProposal;
import org.sidiff.slicer.structural.configuration.Constraint;
import org.sidiff.slicer.structural.configuration.IConstraintInterpreter;
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
	public String getID() {
		return "NameBasedConstraintInterpreter";
	}

	@Override
	public ConstraintResult evaluate(Constraint constraint, EObject eObject) {
		if(eObject instanceof ENamedElement) {
			String name = ((ENamedElement)eObject).getName();
			return new ConstraintResult(constraint.getExpression().matches(".*\\b" + name + "\\b.*"), Collections.emptyList());
		}
		throw new IllegalArgumentException("context object is not of type ENamedElement, given: " + eObject);
	}

	@Override
	public IContentProposal[] getSyntaxHelp(EObject context, String expression, int cursorPosition) {
		return new IContentProposal[0];
	}
}
