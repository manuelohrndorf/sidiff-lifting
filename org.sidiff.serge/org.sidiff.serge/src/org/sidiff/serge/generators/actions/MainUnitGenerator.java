package org.sidiff.serge.generators.actions;

import java.util.Stack;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.model.Module;

public class MainUnitGenerator {

	private Module module;

	// TODO: evtl. müssen imports schon von jeweiligen Generators gesetzt
	// werden, bevor irgendwelche Regeln/Modules erzeugt werden..??
	private Stack<EPackage> imports;

	public MainUnitGenerator(Module module, Stack<EPackage> imports) {
		super();
		this.module = module;
		this.imports = imports;
	}

	public void generate() {
		// TODO
		// Umschließende mainUnit
		// Parameter Mappings
		// imports (organize)
	}
}
