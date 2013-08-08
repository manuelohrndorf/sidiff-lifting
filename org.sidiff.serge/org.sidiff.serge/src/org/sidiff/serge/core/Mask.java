package org.sidiff.serge.core;

import org.eclipse.emf.ecore.*;

public class Mask {

	private String name;
	private EClass eClass;
	private EAttribute eAttribute;
	private EEnumLiteral eAttributeValue;
	
	public Mask(String name, EClass eClass, EAttribute eAttribute, EEnumLiteral eAttributeValue) {
		this.name = name;
		this.eClass = eClass;
		this.eAttribute = eAttribute;
		this.eAttributeValue = eAttributeValue;
	}
	
	/**
	 * Returns the name of a mask as set on the SERGe configuration file.
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the container EClass which beholds the concealed type information.
	 * @return
	 */
	public EClass getOriginalEClass() {
		return eClass;
	}

	/**
	 * Returns the attribute that contains the type.
	 * @return
	 */
	public EAttribute getEAttributeContainingFakeType() {
		return eAttribute;
	}

	/**
	 * Returns which type is actually meant.
	 * @return
	 */
	public EEnumLiteral getTypeExtension() {
		return eAttributeValue;
	}
	
}
