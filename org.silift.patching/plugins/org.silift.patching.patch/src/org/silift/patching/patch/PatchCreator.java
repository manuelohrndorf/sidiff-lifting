package org.silift.patching.patch;

import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.difference.asymmetric.AsymmetricDifference;


public class PatchCreator {
	
	private Resource resourceA;
	private Resource resourceB;
	private AsymmetricDifference asymmetricDifference;
	
	public PatchCreator(Resource resourceA, Resource resourceB){
		this.resourceA = resourceA;
		this.resourceB = resourceB;
	}
	
	
	public Resource getResourceA() {
		return resourceA;
	}

	public void setResourceA(Resource resourceA) {
		this.resourceA = resourceA;
	}

	
	public Resource getResourceB() {
		return resourceB;
	}

	public void setResourceB(Resource resourceB) {
		this.resourceB = resourceB;
	}

	
	public AsymmetricDifference getAsymmetricDifference() {
		return asymmetricDifference;
	}

	public void setAsymmetricDifference(AsymmetricDifference asymmetricDifference) {
		this.asymmetricDifference = asymmetricDifference;
	}

	
	public void serializePatch(){
		
	}

}
