package org.sidiff.patching.test;

import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.facade.util.Difference;
import org.sidiff.patching.IArgumentManager;
import org.sidiff.patching.ITransformationEngine;

public class TestSuite {
	String id;
	Difference difference;
	Resource original;
	Resource modified;
	IArgumentManager correspondence;
	ITransformationEngine transformationEngine;

	public TestSuite(String id, Difference difference, Resource original, Resource modified,
			IArgumentManager correspondence, ITransformationEngine transformationEngine) {
		super();
		this.id = id;
		this.difference = difference;
		this.original = original;
		this.modified = modified;
		this.correspondence = correspondence;
		this.transformationEngine = transformationEngine;
	}


	public String getId() {
		return id;
	}
	
	public Difference getDifference() {
		return difference;
	}

	public AsymmetricDifference getAsymmetricDifference() {
		return difference.getAsymmetric();
	}

	public Resource getOriginal() {
		return difference.getAsymmetric().getOriginModel();
	}

	public Resource getModified() {
		return modified;
	}

	public IArgumentManager getCorrespondence() {
		return correspondence;
	}
	
	public ITransformationEngine getTransformationEngine() {
		return transformationEngine;
	}

}