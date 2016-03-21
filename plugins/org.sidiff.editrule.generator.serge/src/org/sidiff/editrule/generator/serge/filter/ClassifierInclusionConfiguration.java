package org.sidiff.editrule.generator.serge.filter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.ecore.EClassifier;

public class ClassifierInclusionConfiguration {

	
	public static enum InclusionType {
		NEVER, IF_REQUIRED, ALWAYS;
	}
	
	/**
	 * Singleton instance
	 */
	private static ClassifierInclusionConfiguration instance = null;
	
	/**
	 * A map that stores the configured inclusion behavior for each classifier
	 * in case it is a dangling element
	 */
	private final Map<EClassifier, InclusionType> danglingInclusionTypes = new HashMap<>();
	
	/**
	 * A variable that stores the configured default inclusion behavior for dangling elements
	 */
	private InclusionType defaultDanglingInclusionType;
	
	/**
	 * A map that stores the configured inclusion behavior for each classifier
	 * in case it is a focal element
	 */
	private final Map<EClassifier, InclusionType> focusInclusionTypes = new HashMap<>();
	
	/**
	 * A variable that stores the configured default inclusion behavior for focal elements
	 */
	private InclusionType defaultFocusInclusionType;
	
	/**
	 * A set that holds all required, recursive mandatory children and direct mandatory neighbors
	 * and parent contexts that may occur as danglings in a rule.
	 * 
	 */
	private final Set<EClassifier> requiredDanglings = new HashSet<>();

	
	/**
	 * A set that holds all required parent contexts that may occur as danglings in a rule.
	 * 
	 */
	private final Set<EClassifier> requiredDanglingParentContexts = new HashSet<>();
	
	/**
	 * A set that holds all required, recursive mandatory children and recursive mandatory neighbors
	 * and parent contexts that may occur as focal element in a rule.
	 * 
	 */
	private final Set<EClassifier> requiredFocalElements = new HashSet<>();
	
	/**
	 * singleton
	 * @return ClassifierInclusionConfiguration
	 */
	public static ClassifierInclusionConfiguration getInstance() {
		
		if(instance==null) {
			instance =  new ClassifierInclusionConfiguration();
		}
		return instance;		
	}
	
	/**
	 * Method that configures the inclusion type behavior for a classifier
	 * in case it is considered a dangling element
	 * 
	 * @param type
	 * @param it
	 */
	public void setDanglingInclusionType(EClassifier type, InclusionType it) {
		if (it != null) {
			danglingInclusionTypes.put(type, it);
		} else {
			danglingInclusionTypes.remove(type);
		}
	}

	/**
	 * Method that configures the inclusion type behavior for a classifier
	 * in case it is considered a focal element
	 * @param type
	 * @param it
	 */
	public void setFocusInclusionType(EClassifier type, InclusionType it) {
		if (it != null) {
			focusInclusionTypes.put(type, it);
		} else {
			focusInclusionTypes.remove(type);
		}
	}

	/**
	 * Method that gets the inclusion type behavior for each classifier
	 * in case it is considered a dangling element
	 * @return
	 */
	public Map<EClassifier, InclusionType> getDanglingClassifierInclusionTypes() {
		return danglingInclusionTypes;
	}

	/**
	 * Method that gets the inclusion type behavior for each classifier
	 * in case it is considered a focal element
	 * @return
	 */
	public Map<EClassifier, InclusionType> getFocusClassifierInclusionTypes() {
		return focusInclusionTypes;
	}
	
	/**
	 * Method that gets a set of focal element EClassifiers by their
	 * user defined inclusion type
	 * @param it
	 * @return
	 */
	public Set<EClassifier> getFocusClassifiersByInclusionType(InclusionType it) {
		 Map<EClassifier, InclusionType> map = getFocusClassifierInclusionTypes();
		 Set<EClassifier> result = new HashSet<EClassifier>();
		 
		for(Entry<EClassifier,InclusionType> entry: map.entrySet()) {
			if(entry.getValue().equals(it)) {
				result.add(entry.getKey());
			}
		}		
		return result;
	}
	
	/**
	 * Method that gets a set of dangling element EClassifiers by their
	 * user defined inclusion type
	 * @param it
	 * @return
	 */
	public Set<EClassifier> getDanglingClassifiersByInclusionType(InclusionType it) {
		 Map<EClassifier, InclusionType> map = getDanglingClassifierInclusionTypes();
		 Set<EClassifier> result = new HashSet<EClassifier>();
		 
		for(Entry<EClassifier,InclusionType> entry: map.entrySet()) {
			if(entry.getValue().equals(it)) {
				result.add(entry.getKey());
			}
		}		
		return result;
	}
	

	/**
	 * Method that gets the inclusion type behavior for a classifier
	 * in case it is considered a focus element
	 * @param type
	 * @return
	 */
	public InclusionType getFocusInclusuionType(EClassifier type) {
		InclusionType it = focusInclusionTypes.get(type);
		if (it == null)
			return getDefaultFocusInclusionType();
		return it;
	}

	/**
	 * Method that gets the inclusion type behavior for a classifier
	 * in case it is considered a dangling element
	 * @param type
	 * @return
	 */
	public InclusionType getDanglingInclusuionType(EClassifier type) {
		InclusionType it = danglingInclusionTypes.get(type);
		if (it == null)
			return getDefaultDanglingInclusionType();
		return it;
	}

	
	/**
	 * Method that gets the default inclusion type behavior for danglings 
	 * @return InclusionType
	 */
	public InclusionType getDefaultDanglingInclusionType() {
		return defaultDanglingInclusionType;
	}

	/**
	 * Method that sets the default inclusion type behavior for danglings 
	 */
	public void setDefaultDanglingInclusionType(InclusionType defaultDanglingInclusionType) {
		this.defaultDanglingInclusionType = defaultDanglingInclusionType;
	}

	/**
	 * Method that gets the default inclusion type behavior for focal elements 
	 * @return InclusionType
	 */
	public InclusionType getDefaultFocusInclusionType() {
		return defaultFocusInclusionType;
	}

	/**
	 * Method that sets the default inclusion type behavior for focal elements 
	 */
	public void setDefaultFocusInclusionType(InclusionType defaultFocusInclusionType) {
		this.defaultFocusInclusionType = defaultFocusInclusionType;
	}

	/**
	 * Method that returns all required, recursive mandatory children and direct mandatory neighbors
	 * that may occur as danglings in a rule.
	 */
	public Set<EClassifier> getRequiredDanglings() {
		return requiredDanglings;
	}
	
	/**
	 * Method that returns all required parent contexts that may occur as danglings in a rule.
	 */
	public Set<EClassifier> getRequiredDanglingParentContexts() {
		return requiredDanglingParentContexts;
	}
	
	/**
	 * Method that returns all required, recursive mandatory children and recursive mandatory neighbors
	 * and parent contexts that may occur as focal element in a rule..
	 */
	public Set<EClassifier> getRequiredFocalElements() {
		return requiredFocalElements;
	}

	/**
	 * Method that collects all required EClassfiers that may occur as rule danglings.
	 * Whether they are required or not is decided by meta-model structure and user configuration
	 */
	public void collectConfiguredAndRequiredDanglingClassifiers() {
		requiredDanglings.addAll(MandatoryDanglingsCollector.collectConfiguredAndRequiredDanglingClassifiers());
	}
	
	
	/**
	 * Method that collects all required EClassfiers that may occur as parent context rule dangling.
	 * Whether they are required or not is decided by meta-model structure and user configuration
	 */
	public void collectConfiguredAndRequiredDanglingParentContextClassifiers() {
		requiredDanglingParentContexts.addAll(MandatoryDanglingsCollector.collectConfiguredAndRequiredDanglingParentContextClassifiers());
	}
	
	
	/**
	 * Method that collects all required, recursive mandatory children and recursive mandatory neighbors
	 * and parent contexts that may occur as focal element in a rule.
	 */
	public void collectConfiguredAndRequiredFocalClassifiers() {
		requiredFocalElements.addAll(MandatoryFocalElementCollector.collectConfiguredAndRequiredFocalClassifiers());
	}

}
