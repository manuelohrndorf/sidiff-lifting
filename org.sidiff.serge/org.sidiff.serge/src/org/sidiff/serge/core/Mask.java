package org.sidiff.serge.core;

import org.eclipse.emf.ecore.*;

public class Mask {

	private String name;
	private EClassifier eClassifier;
	private EAttribute eAttribute;
	private EEnumLiteral eAttributeValue;
	
	public Mask(String name, EClassifier eClassifier, EAttribute eAttribute, EEnumLiteral eAttributeValue) {
		this.name = name;
		this.eClassifier = eClassifier;
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
	 * Returns the container EClassifier which beholds the concealed type information.
	 * @return
	 */
	public EClassifier getOriginalEClassifier() {
		return eClassifier;
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
