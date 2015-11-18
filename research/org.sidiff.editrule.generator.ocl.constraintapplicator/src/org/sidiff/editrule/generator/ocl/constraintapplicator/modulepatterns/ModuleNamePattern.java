package org.sidiff.editrule.generator.ocl.constraintapplicator.modulepatterns;

import org.sidiff.editrule.generator.types.OperationType;

public class ModuleNamePattern extends ModuleMatchPattern {

	/**
	 * Expected Name pattern. We should
	 * use GlobalConstant.java {@link GlobalConstants} of SERGe and RegEx.
	 */
	private String namePattern;
	
	/**
	 * The expected OperationType {@link OperationType}
	 */
	private OperationType opType;
	
	/**
	 * @return returns the name pattern.
	 */
	public String getNamePattern() {
		return namePattern;
	}
	
	/**
	 * Sets the Name pattern of a module. We should
	 * use GlobalConstants {@link GlobalConstants} of SERGe and RegEx.
	 * @param namePattern
	 */
	public void setNamePattern(String namePattern) {
		this.namePattern = namePattern;
	}
	
	/**
	 * @return the expected OperationType {@link OperationType}
	 */
	public OperationType getOpType() {
		return opType;
	}
	
	/**
	 * Sets the expected OperationType {@link OperationType}
	 * @param opType
	 */
	public void setOpType(OperationType opType) {
		this.opType = opType;
	}

	
	
	
}
