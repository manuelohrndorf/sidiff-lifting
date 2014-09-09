package org.sidiff.editrule.consistency.fixing.ide;

import org.eclipse.core.resources.IMarker;
import org.eclipse.ui.IMarkerResolution2;
import org.eclipse.ui.IMarkerResolutionGenerator2;
import org.sidiff.editrule.consistency.fixing.ERFixingEngine;

/**
 * This class is capable of returning a suitable
 * @link{EditRuleFix} according to the given @link{ValidationType}.
 * The fix can be executed by the User in the Eclipse IDE QuickFix Dialog.
 * 
 * This implementation is very simple, as it permits more than one
 * resolution to a given validation error and all information
 * according to the resolution is generic (label,description,icon).
 * 
 * @author dreuling
 *
 */
public class EditRuleMarkerResolutionGenerator implements IMarkerResolutionGenerator2  {
	
	@Override
	public IMarkerResolution2[] getResolutions(IMarker marker) {
		String validationType = marker.getAttribute("rule", null);

		// Just create a resolution if the ERFixingEngine is capable of
		// this type of validation error
		if(hasResolutions(marker)){		
			if(validationType != null	){

				//Return exactly one resolution
				return new IMarkerResolution2[] {					
						new EditRuleFix(validationType, marker),
				};
			}
		}
		return null;

	}

	@Override
	public boolean hasResolutions(IMarker marker) {
		String validationType = marker.getAttribute("rule", null);
		ERFixingEngine fixEngine = new ERFixingEngine();
		if(fixEngine.canFixValidationType(validationType)){
			return true;
		}
		else{
			return false;			
		}		
	}
}
