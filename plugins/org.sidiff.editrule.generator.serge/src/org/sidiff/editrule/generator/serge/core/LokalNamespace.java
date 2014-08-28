package org.sidiff.editrule.generator.serge.core;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EReference;

public class LokalNamespace {
	
	private EAttribute referenceType;
	private EReference context;
	
	public LokalNamespace(EAttribute referenceType, EReference context) {
		super();
		this.referenceType = referenceType;
		this.context = context;
	}

	public EAttribute getReferenceType() {
		return referenceType;
	}

	public EReference getContext() {
		return context;
	}
	
}
