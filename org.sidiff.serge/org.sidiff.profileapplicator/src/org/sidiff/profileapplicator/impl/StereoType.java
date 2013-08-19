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
	 * Boolean for checking if baseTypes are allowed without applied stereotype
	 */
	private boolean baseTypeInstancesAllowed = false;

	/**
	 * Constructor with initialization of stereotype attributes
	 * 
	 * @param name
	 *            the name of the stereotype
	 * @param baseTypeMap
	 *            the mapping between basetypes and basereferences
	 * @param baseTypeInstancesAllowed
	 *            boolean for basetype instances
	 */
	public StereoType(String name, HashMap<String, String> baseTypeMap,
			boolean baseTypeInstancesAllowed) {
		this.name = name;
		this.baseTypeMap = baseTypeMap;
		this.baseTypeInstancesAllowed = baseTypeInstancesAllowed;
	}

	/**
	 * Constructor with initialization of stereotype attributes
	 * 
	 * @param name
	 *            the name of the stereotype
	 * @param baseTypeInstancesAllowed
	 *            boolean for basetype instances
	 */
	public StereoType(String name, boolean baseTypeInstancesAllowed) {
		this.name = name;
		this.baseTypeInstancesAllowed = baseTypeInstancesAllowed;
	}
	
	/**
	 * Convenience constructor
	 */
	public StereoType(){
		
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

	/**
	 * @return the baseTypeInstancesAllowed
	 */
	public boolean isBaseTypeInstancesAllowed() {
		return baseTypeInstancesAllowed;
	}

	/**
	 * @param baseTypeInstancesAllowed
	 *            the baseTypeInstancesAllowed to set
	 */
	public void setBaseTypeInstancesAllowed(boolean baseTypeInstancesAllowed) {
		this.baseTypeInstancesAllowed = baseTypeInstancesAllowed;
	}

}
