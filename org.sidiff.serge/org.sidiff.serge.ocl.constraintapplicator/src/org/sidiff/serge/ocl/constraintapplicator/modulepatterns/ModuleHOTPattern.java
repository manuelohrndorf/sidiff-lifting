package org.sidiff.serge.ocl.constraintapplicator.modulepatterns;

import java.util.Map;

import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Parameter;

public class ModuleHOTPattern extends ModuleMatchPattern {

	private Module matchingHOT;
	private Map<Parameter, Object> hotInputValues;
	
}
