package org.sidiff.serge.ocl.constraintapplicator.store;

import java.util.Set;

import org.eclipse.emf.henshin.model.Module;
import org.sidiff.serge.ocl.constraintapplicator.oclpatterns.OCLExpressionPattern;

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
}
