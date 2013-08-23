package org.sidiff.profileapplicator.impl;

import java.util.HashMap;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;

public class StereoType {

	

	/**
	 * The name of this stereotype
	 */
	private EClass stereoTypeClass = null;

	/**
	 * The Map which holds the baseType and its corresponding baseReference
	 */
	private HashMap<EClass, EReference> baseTypeMap = new HashMap<EClass, EReference>();

	
	/**
	 * @return the stereoTypeClass
	 */
	public EClass getStereoTypeClass() {
		return stereoTypeClass;
	}

	/**
	 * @param stereoTypeClass the stereoTypeClass to set
	 */
	public void setStereoTypeClass(EClass stereoTypeClass) {
		this.stereoTypeClass = stereoTypeClass;
	}
	
	/**
	 * Constructor with initialization of stereotype attributes
	 * 
	 * @param stereoTypeClass
	 *            the class of the stereotype
	 * @param baseTypeMap
	 *            the mapping between basetypes and basereferences
	 */
	public StereoType(EClass stereoTypeClass,
			HashMap<EClass, EReference> baseTypeMap) {
		this.stereoTypeClass = stereoTypeClass;
		this.baseTypeMap = baseTypeMap;
	}

	/**
	 * Constructor with initialization of stereotype itself
	 * 
	 * @param stereoTypeClass
	 *            the class of the stereotype
	 */
	public StereoType(EClass stereoTypeClass) {
		this.stereoTypeClass = stereoTypeClass;
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
	public void addBaseType(EClass baseType, EReference baseReference) {

		if (!this.baseTypeMap.containsKey(baseType))
			this.baseTypeMap.put(baseType, baseReference);
	}

	/**
	 * @return the baseTypeMap
	 */
	public HashMap<EClass, EReference> getBaseTypeMap() {
		return baseTypeMap;
	}

	/**
	 * @param baseTypeMap
	 *            the baseTypeMap to set
	 */
	public void setBaseTypeMap(HashMap<EClass, EReference> baseTypeMap) {
		this.baseTypeMap = baseTypeMap;
	}
	
	/**
	 * Convenience method, delegates name method
	 * @return name of the class of this stereotype
	 */
	public String getName(){
		
		return this.getStereoTypeClass().getName();
	}
	

}
