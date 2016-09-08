package org.eclipse.emf.henshin.interpreter.util;

import java.util.Collection;

import org.eclipse.emf.henshin.interpreter.matching.constraints.AttributeConstraint;
import org.eclipse.emf.henshin.interpreter.matching.constraints.Variable;

public class PrintUtil {

	public static String printVariables(Collection<Variable> variables) {
		StringBuffer print = new StringBuffer();
		
		for (Variable variable : variables) {
			print.append("Type: " + variable.typeConstraint.type + "\n");
			
			for (AttributeConstraint attribtue : variable.attributeConstraints) {
				print.append("  Attribute: " + attribtue + "\n");
			}
		}
		
		return print.toString();
	}
}
