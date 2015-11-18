package org.sidiff.evaluation.silift.patching;

import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.facade.util.Difference;
import org.sidiff.patching.interrupt.IPatchInterruptHandler;
import org.sidiff.patching.transformation.ITransformationEngine;

public class TestSuite {
	String id;
	Difference difference;
	Resource original;
	Resource modified;
	AbstractBatchArgumentManager argumentManager;
	ITransformationEngine transformationEngine;
	IPatchInterruptHandler patchInterruptHandler;

	public TestSuite(String id, Difference difference, Resource original, Resource modified,
			AbstractBatchArgumentManager argumentManager, ITransformationEngine transformationEngine, IPatchInterruptHandler patchInterruptHandler) {
		super();
		this.id = id;
		this.difference = difference;
		this.original = original;
		this.modified = modified;
		this.argumentManager = argumentManager;
		this.transformationEngine = transformationEngine;
		this.patchInterruptHandler = patchInterruptHandler;
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

	public AbstractBatchArgumentManager getCorrespondence() {
		return argumentManager;
	}
	
	public ITransformationEngine getTransformationEngine() {
		return transformationEngine;
	}

	public IPatchInterruptHandler getPatchInterruptHandler(){
		return patchInterruptHandler;
	}

}