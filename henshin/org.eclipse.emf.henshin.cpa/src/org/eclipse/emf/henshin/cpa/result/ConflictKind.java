/**
 * <copyright>
 * Copyright (c) 2010-2014 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.cpa.result;

/**
 * This class provides the different kinds of supported conflicts
 * 
 * @author Kristopher Born
 */
public enum ConflictKind {

	DELETE_USE_CONFLICT("delete-use-conflict"), 
	PRODUCE_FORBID_CONFLICT("produce-forbid-conflict"), 
	CHANGE_USE_ATTR_CONFLICT("change-use-attr-conflict"), 
	CHANGE_FORBID_ATTR_CONFLICT("change-forbid-attr-conflict");

	private String name;

	/**
	 * Default internal constructor.
	 * 
	 * @param nameOfInvolvedRules The name of the conflict kind.
	 */
	private ConflictKind(String s) {
		name = s;
	}

	/**
	 * Returns the name of the conflict kind.
	 * 
	 * @return The name of the conflict kind.
	 */
	public String toString() {
		return name;
	}

	/**
	 * commented out conflicts are in stock for future use. If necessary they will be implemented and reactivated
	 */
	// DELETE_USE_CONFLICT // r1.LHS --> r2.LHS
	// DELETE_NEED_CONFLICT // r1.LHS --> r2.PAC
	// PRODUCE_FORBID_CONFLICT // r1.RHS --> r2.NAC
	// PRODUCE_EDGE_DELTE_NODE_CONFLICT // r1.RHS --> r2.LHS
	// CHANGE_ATTR_CONFLICT // r1.LHS --> r2.LHS
	// CHANGE_USE_ATTR_CONFLICT // r1.LHS --> r2.LHS (+PAC?)
	// CHANGE_NEED_ATTR_CONFLICT // r1.LHS --> r2.PAC
	// CHANGE_FORBID_ATTR_CONFLICT // r1.LHS --> r2.NAC

}
