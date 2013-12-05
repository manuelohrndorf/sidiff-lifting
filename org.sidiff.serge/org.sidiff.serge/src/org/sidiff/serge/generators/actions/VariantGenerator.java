package org.sidiff.serge.generators.actions;

import java.util.Set;

import org.eclipse.emf.henshin.model.Module;

public class VariantGenerator {

	/**
	 * The original create module in which we want to replace supertypes with
	 * subtypes and abstract types with concrete types.
	 */
	private Module createModule;

	public VariantGenerator(Module createModule) {
		super();
		this.createModule = createModule;
	}

	public Set<Module> generate(){
		// TODO: puhhh, type replacements
		return null;
	}
}
