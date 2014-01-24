package org.sidiff.serge.util;

import org.eclipse.emf.henshin.model.Module;

public class ModuleFilenamePair {
	
	private String outputFileName = null;
	private Module module = null;
	
	public ModuleFilenamePair(String outputFileName, Module module) {
		this.outputFileName = outputFileName;
		this.module = module;
	}
	
	public String getOutputFileName() {
		return outputFileName;
	}

	public Module getModule() {
		return module;
	}

}
