package org.sidiff.editrule.generator.ocl.constraintapplicator.store;

import java.util.Set;

import org.eclipse.emf.henshin.model.Module;
import org.sidiff.editrule.generator.ocl.constraintapplicator.oclpatterns.OCLExpressionPattern;

/**
 * This class represents a pair of an OCLExpressionPattern
 * and one affected module.
 * 
 * @author mrindt
 *
 */
public class ConstraintModulePair {

	/**
	 * The affected module.
	 */
	private Module module;
	
	/**
	 * The OCLExpressionPattern.
	 */
	private OCLExpressionPattern oclxpp;
	
	private Set<ConstraintModulePair> additonalModuleAffections;
	
	/**
	 * Constructor.
	 * @param m
	 * @param oclxpp
	 */
	public ConstraintModulePair(Module m, OCLExpressionPattern oclxpp) {
		
		this.module = m;
		this.oclxpp = oclxpp;
		
	}
	
	public Module getModule() {
		return module;
	}
	
	public OCLExpressionPattern getOCLExpressionPattern() {
		return oclxpp;
	}
}
