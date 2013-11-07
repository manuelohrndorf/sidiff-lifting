package org.silift.patching.patch;

import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.difference.asymmetric.facade.util.Difference;



public class PatchCreator {
	
	private Resource resourceA;
	private Resource resourceB;
	private Difference asymmetricDifference;
	
	public PatchCreator(Resource resourceA, Resource resourceB){
		this.resourceA = resourceA;
		this.resourceB = resourceB;
		
		//asymmetricDifference = AsymmetricDiffFacade.liftMeUp(resourceA, resourceB, new AsymmetricDiffSettings(settings));
		
	}

}
