package org.sidiff.serge.core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Parameter;
import org.sidiff.serge.configuration.Configuration.OperationType;
import org.sidiff.serge.configuration.GlobalConstants;

/**
 * The InverseModuleMap contains a map for modules, which are inverses of each other.
 * Also, each mapped pair is extended with  a set of corresponding parameters of each mapped pair.
 * 
 * @author mrindt
 *
 */
public class InverseModuleMapper{

	/**
	 * A Map containing all pairs of modules which are inverses of each other
	 * inclusive a set of corresponding parameters
	 * 
	 * [ModuleA <--> ModuleB ] <--> [[parameterA1 <--> parameterB1], [parameterA2 <--> parameterB2], [...]]
	 * 
	 */
	private Map<Map<Module, Module>,Set<Map<Parameter, Parameter>>> inverseModulePairMap = null;
			
	/**
	 * Adds two inverses of each other maps the corresponding parameters.
	 * Note: First input module should be the one from which the inverse is created,
	 * (i.e. CREATE, SET, ADD)
	 * @param m1
	 * @param m2
	 */
	private void addInverseModulePair(Module m1, Module m2) {
		Map<Module, Module> modulePair= new HashMap<Module, Module>();
		modulePair.put(m1, m2);
	
		Map<Parameter,Parameter> mappedParameters = new HashMap<Parameter, Parameter>();
		Set<Map<Parameter,Parameter>> mappedParameterSet = new HashSet<Map<Parameter,Parameter>>();	
		for(Parameter p1: m1.getUnit(GlobalConstants.UNITNAME).getParameters()) {
			String p1Name = p1.getName();
			Parameter correspondingParameter = null;
			
			if(p1Name.matches("New[0-9]*")) {
				String number = p1Name.replace(GlobalConstants.NEW, "");
				correspondingParameter = m2.getUnit(GlobalConstants.UNITNAME).getParameter(GlobalConstants.DEL+number);
			}
			else if (p1Name.startsWith(GlobalConstants.NEWTGT)){
				correspondingParameter = m2.getUnit(GlobalConstants.UNITNAME).getParameter(GlobalConstants.OLDTGT);
			}
			else{
				correspondingParameter = m2.getUnit(GlobalConstants.UNITNAME).getParameter(p1.getName());
			}
			
			// if a corresponding Parameter could be found, add pair
			// if there is still none, then this Parameter is irrelevant for the inverse
			if(correspondingParameter!=null) {
				mappedParameters.put(p1,correspondingParameter);
				mappedParameterSet.add(mappedParameters);
			}		
		}
		//finally fill the main map
		inverseModulePairMap.put(modulePair, mappedParameterSet);	
	}

	/**
	 * Constructor
	 */
	public InverseModuleMapper() {		
		inverseModulePairMap = new HashMap<Map<Module,Module>, Set<Map<Parameter,Parameter>>>();
	}
	
	/**
	 * Returns a set of mapped inverse module pairs 
	 * and the respective set of corresponding parameters
	 * 
	 * [ModuleA <--> ModuleB ] <--> [[parameterA1 <--> parameterB1], [parameterA2 <--> parameterB2], [...]]
	 * @return
	 */
	public Set<Entry<Map<Module, Module>, Set<Map<Parameter, Parameter>>>> getAllInverseModulePairs() {
		return inverseModulePairMap.entrySet();
	}
	
	/**
	 * This method finds every pair in the given module set and delegates the mapping
	 * to {@link #addInverseModulePair(...)}
	 * @param allModules
	 */
	public void findAndMapInversePairs(Map<OperationType, Set<Module>> allModules) {
		
		
		//Create/Delete
		Set<Module> creates = allModules.get(OperationType.CREATE);
		Set<Module> deletes = allModules.get(OperationType.DELETE);
		
		Set<Map<Module,Module>> set1 = getInverseModulesByName(creates, deletes, OperationType.CREATE);
		for(Map<Module,Module> pair: set1) {
			Module a = pair.entrySet().iterator().next().getKey();
			Module b= pair.entrySet().iterator().next().getValue();
			addInverseModulePair(a, b);
		}
		
		//Add/Remove
		Set<Module> adds = allModules.get(OperationType.ADD);
		Set<Module> removes = allModules.get(OperationType.REMOVE);
		
		Set<Map<Module,Module>> set2 = getInverseModulesByName(adds, removes, OperationType.ADD);
		for(Map<Module,Module> pair: set2) {
			Module a = pair.entrySet().iterator().next().getKey();
			Module b= pair.entrySet().iterator().next().getValue();
			addInverseModulePair(a, b);
		}
				
		//Set_Attribute/Unset_Attribute
		Set<Module> setAttributes = allModules.get(OperationType.SET_ATTRIBUTE);
		Set<Module> unsetAttributes = allModules.get(OperationType.UNSET_ATTRIBUTE);

		Set<Map<Module,Module>> set3 = getInverseModulesByName(setAttributes, unsetAttributes, OperationType.SET_ATTRIBUTE);
		for(Map<Module,Module> pair: set3) {
			Module a = pair.entrySet().iterator().next().getKey();
			Module b= pair.entrySet().iterator().next().getValue();
			addInverseModulePair(a, b);
		}
		
		//Set_Reference/Unset_Reference
		Set<Module> setReferences = allModules.get(OperationType.SET_REFERENCE);
		Set<Module> unsetReferences = allModules.get(OperationType.UNSET_REFERENCE);
		
		Set<Map<Module,Module>> set4 = getInverseModulesByName(setReferences, unsetReferences, OperationType.SET_REFERENCE);
		for(Map<Module,Module> pair: set4) {
			Module a = pair.entrySet().iterator().next().getKey();
			Module b= pair.entrySet().iterator().next().getValue();
			addInverseModulePair(a, b);
		}
		
	}

	/**
	 * Convenience method to find and map inverse modules in given sets by their names.
	 * No corresponding parameter mapping will be done here. Only corresponding modules are found.
	 * @param sm1
	 * @param sm2
	 * @param orginalOPType
	 * @return
	 */
	private Set<Map<Module,Module>> getInverseModulesByName(Set<Module> sm1, Set<Module> sm2, OperationType orginalOPType) {

		Set<Map<Module,Module>> pairs = new HashSet<Map<Module,Module>>();

		switch(orginalOPType) {

		case CREATE:
			for(Module m1: sm1) {
				for(Module m2: sm2) {
					String name1 = m1.getName();
					String expectedName2 = name1.replaceFirst(GlobalConstants.CREATE_prefix, GlobalConstants.DELETE_prefix);
					if(m2.getName().equals(expectedName2)) {
						Map<Module,Module> pair = new HashMap<Module, Module>();
						pair.put(m1,m2);
						pairs.add(pair);
						break;
					}
				}
			}
		case ADD:		
			for(Module m1: sm1) {
				for(Module m2: sm2) {
					String name1 = m1.getName();
					String expectedName2 = name1.replaceFirst(GlobalConstants.ADD_prefix, GlobalConstants.REMOVE_prefix);
					if(m2.getName().equals(expectedName2)) {
						Map<Module,Module> pair = new HashMap<Module, Module>();
						pair.put(m1,m2);
						pairs.add(pair);
						break;
					}
				}
			}

		case SET_ATTRIBUTE:	
			for(Module m1: sm1) {
				for(Module m2: sm2) {
					String name1 = m1.getName();
					String expectedName2 = name1.replaceFirst(GlobalConstants.SET_ATTRIBUTE_prefix, GlobalConstants.UNSET_ATTRIBUTE_prefix);
					if(m2.getName().equals(expectedName2)) {
						Map<Module,Module> pair = new HashMap<Module, Module>();
						pair.put(m1,m2);
						pairs.add(pair);
						break;
					}
				}
			}

		case SET_REFERENCE:		
			for(Module m1: sm1) {
				for(Module m2: sm2) {
					String name1 = m1.getName();
					String expectedName2 = name1.replaceFirst(GlobalConstants.SET_REFERENCE_prefix, GlobalConstants.UNSET_REFERENCE_prefix);
					if(m2.getName().equals(expectedName2)) {
						Map<Module,Module> pair = new HashMap<Module, Module>();
						pair.put(m1,m2);
						pairs.add(pair);
						break;
					}
				}
			}
		default:
			break;	
		}
		return pairs;
	}

}
