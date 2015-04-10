package org.sidiff.editrule.generator.serge.core;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.ModelElement;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;

public class InverseTracker {
	public static final InverseTracker INSTANCE = new InverseTracker();

	private Map<ModelElement, ModelElement> inverseMap = new HashMap<>();
	private Map<ModelElement, ModelElement> parameterMap = new HashMap<>();

	private InverseTracker() {

	}

	public void addInversePair(Module a, Module b) {
		inverseMap.put(a, b);
		inverseMap.put(b, a);
	}

	public void addInversePair(Node a, Node b) {
		inverseMap.put(a, b);
		inverseMap.put(b, a);
	}

	public void addInversePair(Attribute a, Attribute b) {
		inverseMap.put(a, b);
		inverseMap.put(b, a);
	}

	public void addParameter(ModelElement me, Parameter parameter) {
		parameterMap.put(me, parameter);
		parameterMap.put(parameter, me);
	}

	public void overrideParameter(Parameter oldP, Parameter newP) {
		ModelElement me = parameterMap.get(oldP);
		if (me == null)
			throw new IllegalArgumentException("Parameter " + oldP
					+ " not present");
		addParameter(me, newP);
	}

	public Module getInverse(Module module) {
		return (Module) inverseMap.get(module);
	}

	public Node getInverse(Node node) {
		return (Node) inverseMap.get(node);
	}

	public Attribute getInverse(Attribute attribute) {
		return (Attribute) inverseMap.get(attribute);
	}

	public Parameter getInverse(Parameter parameter) {
		ModelElement me = parameterMap.get(parameter);
		if (me == null) {
			return null;
		}
		ModelElement inverseMe = inverseMap.get(me);
		if (inverseMe == null) {
			return null;
		}
		return (Parameter) parameterMap.get(inverseMe);
	}

	public void printInverses() {
		for (Map.Entry<ModelElement, ModelElement> e : inverseMap.entrySet()) {
			System.out.println(parameterMap.get(e.getKey()) + " -> "
					+ e.getKey() + " <-> " + e.getValue() + " <- "
					+ parameterMap.get(e.getValue()));
			if (!(e.getKey() instanceof Module)) {
				if (parameterMap.get(e.getKey()) == null) {
					System.out.println("***ERROR: No Parameter for "
							+ e.getKey() + " (mapped to " + e.getValue() + ")");
				}
				if (parameterMap.get(e.getValue()) == null) {
					System.out.println("***ERROR: No Parameter for "
							+ e.getValue() + " (mapped to " + e.getKey() + ")");
				}
			}
		}
	}

	public void printParameterInverses() {
		for (ModelElement me : parameterMap.keySet()) {
			if (me instanceof Parameter) {
				System.out.println(((Parameter) me) + "_["
						+ parameterMap.get(me) + "] <-> "
						+ getInverse((Parameter) me));
			}
		}
	}
}
