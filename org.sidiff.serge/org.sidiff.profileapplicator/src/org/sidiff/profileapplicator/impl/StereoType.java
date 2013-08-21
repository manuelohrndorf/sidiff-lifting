package org.sidiff.profileapplicator.impl;

import java.util.HashMap;

public class StereoType {

	/**
	 * The name of this stereotype
	 */
	private String name = null;

	/**
	 * The Map which holds the baseType and its corresponding baseReference
	 */
	private HashMap<String, String> baseTypeMap = new HashMap<String, String>();

	/**
	 * Constructor with initialization of stereotype attributes
	 * 
	 * @param name
	 *            the name of the stereotype
	 * @param baseTypeMap
	 *            the mapping between basetypes and basereferences
	 */
	public StereoType(String name, HashMap<String, String> baseTypeMap) {
		this.name = name;
		this.baseTypeMap = baseTypeMap;
	}

	/**
	 * Constructor with initialization of stereotype attributes
	 * 
	 * @param name
	 *            the name of the stereotype
	 */
	public StereoType(String name) {
		this.name = name;
	}

	/**
	 * Convenience constructor
	 */
	public StereoType() {

	}

	/**
	 * Add a basetype<->baseReference Mapping
	 * 
	 * @param baseType
	 *            the baseType to add
	 * @param baseReference
	 *            the corresponding baseReference to add
	 */
	public void addBaseType(String baseType, String baseReference) {

		if (!this.baseTypeMap.containsKey(baseType))
			this.baseTypeMap.put(baseType, baseReference);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the baseTypeMap
	 */
	public HashMap<String, String> getBaseTypeMap() {
		return baseTypeMap;
	}

	/**
	 * @param baseTypeMap
	 *            the baseTypeMap to set
	 */
	public void setBaseTypeMap(HashMap<String, String> baseTypeMap) {
		this.baseTypeMap = baseTypeMap;
	}

}
