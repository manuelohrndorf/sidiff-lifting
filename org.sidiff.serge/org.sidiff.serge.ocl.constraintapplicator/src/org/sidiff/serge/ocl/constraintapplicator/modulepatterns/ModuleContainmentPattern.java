package org.sidiff.serge.ocl.constraintapplicator.modulepatterns;

import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;

public class ModuleContainmentPattern extends ModuleMatchPattern {

	private List<EAttribute> containedCreateEAttributes;
	private List<EReference> containedCreateEReferences;
	private List<EClass> containedCreateEClasses;
}
