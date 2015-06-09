package org.sidiff.difference.matcher.copier;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.sidiff.difference.matcher.BaseMatcher;
import org.silift.common.util.access.EMFModelAccessEx;

/**
 * Concrete matcher stub that checks whether one element has
 * been created by copying another. If so, they are corresponding.
 * 
 * @author dreuling
 */
public class CopierMatcher extends BaseMatcher {

	public static final String KEY = "Copier";
	
	private Copier copier;
	
	/**
	 * Initialize copier.
	 */
	public void setCopier(Copier copier) {
		this.copier = copier;
	}

	@Override
	protected boolean isCorresponding(EObject elementA, EObject elementB) {
		assert (elementA != null && elementB != null) : "One of the elements to check for correspondence is null!";

		assert (copier != null) : "The used copier is null!";
		
		// Both EObjects are corresponding if elementB has been created through copying elementA or vice versa.			
		EObject BfromA = this.copier.get(elementA);
		EObject AfromB = this.copier.get(elementB);
		
		// Elements have been created/deleted without using a copier
		if(BfromA == null && AfromB == null)
			return false;
		
		return ((BfromA != null && BfromA.equals(elementB)) ||
				(AfromB != null && AfromB.equals(elementA)));		
	
	}

	@Override
	public String getName() {
		return "Copier Matcher";
	}

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public String getDocumentType() {
		// can handle every documentType 
		return EMFModelAccessEx.GENERIC_DOCUMENT_TYPE;
	}

	@Override
	public boolean canComputeReliability() {
		return false;	
		
	}

	@Override
	public Map<String, Object> getConfigurationOptions() {
		
		//Nothing
		return null;
	}

	
}
