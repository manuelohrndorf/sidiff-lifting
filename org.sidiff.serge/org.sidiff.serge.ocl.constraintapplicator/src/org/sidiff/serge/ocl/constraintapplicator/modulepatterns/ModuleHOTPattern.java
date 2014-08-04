package org.sidiff.serge.ocl.constraintapplicator.modulepatterns;

import java.util.Map;

import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Parameter;
/**
 * An OCLExpressionPattern can refer to such a ModuleHOTPattern.
 * A ModuleHOTPattern can be created to define expected Patterns
 * to be found in an edit rule (e.g.  occurrences of "create"
 * EAttribute/EReference/EClassifier types).
 * 
 * @author mrindt
 *
 */
public class ModuleHOTPattern extends ModuleMatchPattern {

	/**
	 * The path to the HOT module
	 */
	private Module matchingHOT;
	
	/**
	 * Input values for this HOT module
	 */
	private Map<Parameter, Object> hotInputValues;


	/**
	 * @return the HOT module.
	 */
	public Module getMatchingHOT() {
		return matchingHOT;
	}

	/**
	 * Sets the HOT module.
	 * @param matchingHOT
	 */
	public void setMatchingHOT(Module matchingHOT) {
		this.matchingHOT = matchingHOT;
	}

	/**
	 * @return returns input Parameter values of the HOT module.
	 */
	public Map<Parameter, Object> getHotInputValues() {
		return hotInputValues;
	}

	/**
	 * Adds input Parameter values for the HOT module.
	 * @param hotInputValues
	 */
	public void addHotInputValues(Map<Parameter, Object> hotInputValues) {
		this.hotInputValues = hotInputValues;
	}
}
