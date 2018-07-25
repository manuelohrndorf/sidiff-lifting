package de.unisiegen.informatik.pi.henshin.interpreter;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.henshin.model.Parameter;

public class Argument {

	private Parameter parameter;
	
	private boolean unset;
	
	private Object value;

	public Argument(Parameter parameter) {
		this.parameter = parameter;
		this.unset = false;
		this.value = "";
	}
	
	public boolean isUnset() {
		return unset;
	}

	public void setUnset(boolean unset) {
		this.unset = unset;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Parameter getParameter() {
		return parameter;
	}
	
	public EClassifier getType() {
		return parameter.getType();
	}
}
