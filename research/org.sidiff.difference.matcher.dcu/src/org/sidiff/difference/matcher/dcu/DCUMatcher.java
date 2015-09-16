package org.sidiff.difference.matcher.dcu;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.sidiff.difference.matcher.SignatureBasedMatcher;

import de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorPackage;
import de.imotep.core.behavior.de_imotep_core_behavior.MRegion;
import de.imotep.core.behavior.de_imotep_core_behavior.MStateMachine;
import de.imotep.core.behavior.de_imotep_core_behavior.MTransition;

/**
 * Concrete matcher stub for DCU. 
 * 
 * @author dreuling
 */
public class DCUMatcher extends SignatureBasedMatcher {

	public static final String KEY = "DCU";	

	private Map<String, Object> configuration;


	/**
	 * Initialize matcher and start matching.
	 */
	public DCUMatcher() {
		super();
		this.configuration = new HashMap<String, Object>();

	}

		

	@Override
	public String getName() {
		return "DCU Matcher (Signature)";
	}

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public String getDocumentType() {
		// can handle IMoTEP SMM
		return De_imotep_core_behaviorPackage.eNS_URI;
	}

	@Override
	public boolean canComputeReliability() {
		return false;
	}

	@Override
	public Map<String, Object> getConfigurationOptions() {
		return configuration;
		
	}
	   

	@Override
	protected int calculateSignature(EObject eObject) {
		
		// Statemachines have the same signature, as there is only one
		if(eObject instanceof MStateMachine)
		return MStateMachine.class.hashCode();

		// Regions have the same signature, as there is only one
		if(eObject instanceof MRegion)
		return MRegion.class.hashCode();
		
		// Transitions are "really" signatured
		if(eObject instanceof MTransition){
			int ts = ((MTransition)eObject).getSourceState().getName().hashCode();
			ts += ((MTransition)eObject).getTargetState().getName().hashCode();
			ts += ((MTransition)eObject).getGuard().getName().hashCode();
			//Maybe priority has to be taken into consideration
			return ts;
		}
		
		// Check for attribute "name" and its values equality
			EStructuralFeature attrName = eObject.eClass().getEStructuralFeature("name");
			if (attrName != null && attrName instanceof EAttribute) {
				
				String nameA = (String)eObject.eGet(attrName);			
				return nameA.hashCode();

		}
			
			//No meaningful signature otherwise
			return 0;
	}
		
}
