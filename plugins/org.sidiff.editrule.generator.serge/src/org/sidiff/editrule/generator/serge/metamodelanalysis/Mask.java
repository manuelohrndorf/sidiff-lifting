package org.sidiff.editrule.generator.serge.metamodelanalysis;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnumLiteral;

/**
 * A Mask object can be created for an EClassifier. In some meta-models, EClassifiers  contain
 * EAttributes which declare additional type information. However, EMF will only return
 * the EClassifier type if  getType()  is called allthough one might more specific
 * information about the type maintained inside the EAttribute.
 * <br/><br/>
 * Thus, this class can be used to create Masks for EClassifiers. A Mask contains the respective EClassifier,
 * the more specific type, the respective EAttribute and a name one would normaly expect the containing EClassifier
 * in its specific representation to have.
 * <br/><br/>
 * An example for the UML: a Mask for a 'Pseudostate' can be created for its EAttribute(-Value) 'kind=initial'. The Mask's
 * name would then be 'InitialPseudostate'.
 * 
 * @author mrindt
 *
 */
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
