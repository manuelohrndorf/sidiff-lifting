package de.unisiegen.informatik.pi.henshin.interpreter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.ParameterKind;
import org.eclipse.emf.henshin.model.Unit;

public class ArgumentManager {
	

	List<Argument> arguments;
	
	public ArgumentManager() {
		this.arguments = new ArrayList<Argument>();
	}
	
	public void init(Module module) {
		this.arguments.clear();
		for(Unit unit : module.getUnits()) {
			for(Parameter parameter : unit.getParameters()){
				if(parameter.getKind().equals(ParameterKind.IN) || parameter.getKind().equals(ParameterKind.INOUT)) {
					Argument argument = new Argument(parameter);
					arguments.add(argument);
				}
			}
		}
		this.arguments.sort(new Comparator<Argument>() {

			@Override
			public int compare(Argument o1, Argument o2) {
				return o1.getParameter().getName().compareTo(o2.getParameter().getName());
			}
		});
	}
	
	public List<Argument> getArguments() {
		return Collections.unmodifiableList(arguments);
	}
	
	public Argument getArgument(Parameter parameter) {
		for(Argument argument : arguments) {
			if(argument.getParameter().equals(parameter)) {
				return argument;
			}
		}
		return null;
	}

}
