/**
 */
package org.sidiff.slicer.structural.constraint.ocl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.ocl.ParserException;
import org.eclipse.ocl.Query;
import org.eclipse.ocl.ecore.EcoreEnvironmentFactory;
import org.eclipse.ocl.ecore.OCL;
import org.eclipse.ocl.expressions.OCLExpression;
import org.eclipse.ocl.helper.Choice;
import org.eclipse.ocl.helper.ChoiceKind;
import org.eclipse.ocl.helper.ConstraintKind;
import org.eclipse.ocl.helper.OCLHelper;
import org.sidiff.slicer.structural.configuration.Constraint;
import org.sidiff.slicer.structural.configuration.IConstraintInterpreter;
import org.sidiff.slicer.structural.configuration.IConstraintProposal;
import org.sidiff.slicer.structural.configuration.IConstraintResult;
import org.sidiff.slicer.structural.configuration.impl.ConstraintProposal;
import org.sidiff.slicer.structural.configuration.impl.ConstraintResult;


public class OCLConstraintInterpreter implements IConstraintInterpreter {

	public static final String ID = OCLConstraintInterpreter.class.getSimpleName();

	private static final String[] SYNTAX_HELP_SEPARATORS = new String[] { ".", "->" };

	private OCL ocl; 
	private OCLHelper<EClassifier, EOperation, EStructuralFeature, org.eclipse.ocl.ecore.Constraint> oclHelper;

	public OCLConstraintInterpreter() {
		super();
		this.ocl = OCL.newInstance(EcoreEnvironmentFactory.INSTANCE);
		this.oclHelper = ocl.createOCLHelper();
	}

	@Override
	public IConstraintResult evaluate(Constraint constraint, EObject context) {
		ConstraintResult result = new ConstraintResult(false, Collections.emptyList());
		this.oclHelper.setContext(context.eClass());
		OCLExpression<EClassifier> oclConstraint;
		try {
			oclConstraint = oclHelper.createQuery(constraint.getExpression());
			Query<EClassifier, EClass, EObject> query = ocl.createQuery(oclConstraint);
			Object obj = query.evaluate(context);
			if(obj instanceof Collection<?>){
				Collection<EObject> eObjects = new ArrayList<EObject>();
				Collection<?> objects = (Collection<?>) obj;
				for(Object object : objects){
					if(object instanceof EObject){
						eObjects.add((EObject) object);
					}
				}
				if(!eObjects.isEmpty()){
					result = new ConstraintResult(true, eObjects);
				}
			}else if(obj instanceof Boolean){
				result = new ConstraintResult((Boolean)obj, Collections.emptyList());
			}
			
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String getKey() {
		return ID;
	}

	@Override
	public List<IConstraintProposal> getSyntaxHelp(EObject context, String expression, int cursorPosition)
	{
		// ignore everything after the cursor position
		String precedingExpression = expression.substring(0, cursorPosition);

		// set the context
		if(context instanceof EClass)
		{
			this.oclHelper.setContext((EClass)context);
		}
		else if(context instanceof EReference)
		{
			// for EReferences, the context is the containing EClass
			this.oclHelper.setContext(((EReference)context).getEContainingClass());
		}
		else
		{
			this.oclHelper.setInstanceContext(context);
		}

		return this.oclHelper.getSyntaxHelp(ConstraintKind.INVARIANT, precedingExpression).stream()
			.map(choice -> oclChoiceToConstraintProposal(precedingExpression, choice))
			.collect(Collectors.toList());
	}
	
	private static IConstraintProposal oclChoiceToConstraintProposal(String expression, Choice c) {
		String content;
		String label;

		if(c.getKind() == ChoiceKind.OPERATION)
		{
			content = c.getName() + "()";
			label = c.getDescription();
		}
		else
		{
			content = c.getName();
			label = c.getName() + " : " + c.getDescription();
		}

		// find the last activator in the expression
		int lastPosition = -1;
		int lastActivatorIndex = -1; // -1 means from start expression
		for(int j = 0; j < SYNTAX_HELP_SEPARATORS.length; j++)
		{
			int pos = expression.lastIndexOf(SYNTAX_HELP_SEPARATORS[j]);
			if(pos > lastPosition)
			{
				lastPosition = pos;
				lastActivatorIndex = j;
			}
		}

		// truncate the input according to the activator position
		if(lastPosition == -1) // no activator found
		{
			if(content.startsWith(expression))
			{
				content = content.substring(expression.length());
			}
		}
		else
		{
			final int activatorLength = SYNTAX_HELP_SEPARATORS[lastActivatorIndex].length();
			if(content.startsWith(expression.substring(lastPosition + activatorLength)))
			{
				content = content.substring(expression.length() - lastPosition - activatorLength);
			}
		}

		int newCursorPosition;
		if(c.getKind() == ChoiceKind.OPERATION && content.length() >= 1)
		{
			newCursorPosition = content.length()-1; // position cursor inside of parentheses
		}
		else
		{
			newCursorPosition = content.length();
		}

		return new ConstraintProposal(content, newCursorPosition, label, null);
	}
}
