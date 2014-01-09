package org.sidiff.serge.generators.actions;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Module;

public class ChangeLiteralGenerator {

	private EAttribute eAttribute;

	public ChangeLiteralGenerator(EAttribute eAttribute, EEnumLiteral eEnumLiteral) {
		assert(eAttribute.getLowerBound() == 1 && eAttribute.getUpperBound() == 1);
		
		this.eAttribute = eAttribute;
	}

	public Module generate(EClassifier contextOfEAttribute, EEnumLiteral eEnumLiteral) {
		return null;
	}
	
}
