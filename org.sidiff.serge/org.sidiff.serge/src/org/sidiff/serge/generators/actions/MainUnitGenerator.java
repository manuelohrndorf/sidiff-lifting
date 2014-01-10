package org.sidiff.serge.generators.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.ParameterMapping;
import org.eclipse.emf.henshin.model.PriorityUnit;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.sidiff.common.henshin.HenshinModuleAnalysis;
import org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx;
import org.sidiff.common.henshin.INamingConventions;
import org.sidiff.serge.core.Common;
import org.sidiff.serge.core.Configuration;
import org.sidiff.serge.core.GlobalConstants;
import org.sidiff.serge.core.Configuration.OperationType;
import org.sidiff.serge.exceptions.EPackageNotFoundException;

public class MainUnitGenerator {


	/**
	 * The module to generate a main unit inside.
	 */
	private Module module;
	
	/**
	 * The operationType to generate a mainUnit for.
	 */
	private OperationType opType;

	/**
	 * Imports to EPackages that need to be included.
	 */
	private Stack<EPackage> imports = Configuration.getInstance().EPACKAGESSTACK;

	/**
	 * Constructor
	 * @param module
	 * @param opType
	 */
	public MainUnitGenerator(Module module,  OperationType opType) {
		this.module = module;
		this.opType = opType;
	}

	public void generate() {
		
		// remove all existing units because we only support one.
		removeAllNonRuleUnits(module);
		List<Rule> rulesUnderModule = HenshinModuleAnalysis.getAllRules(module);
		
		/** Unit creation *************************************************/	
		PriorityUnit prioUnit = HenshinFactory.eINSTANCE.createPriorityUnit();
		prioUnit.setActivated(true);
		prioUnit.setName(INamingConventions.MAIN_UNIT);		
				
		/** Parameter and Mapping creation ********************************/
		
		// In case of DELETE module, remove unnecessary parameters
		if(opType==OperationType.DELETE) {
			removeUnnecessaryParametersForDELETE(module, prioUnit);
		}
		
		// Create the mandatory "selectedEObject"-Parameter with type information
		Parameter selectedEObjectParameter = HenshinFactory.eINSTANCE.createParameter(GlobalConstants.SELEO);
		Node selectedEObjectNode = HenshinRuleAnalysisUtilEx.getNodeByName(rulesUnderModule.get(0), GlobalConstants.SEL, true);
		selectedEObjectParameter.setType(selectedEObjectNode.getType());
		prioUnit.getParameters().add(selectedEObjectParameter);
			
		for(Rule rule: rulesUnderModule) {
					
			//we only need to consider RHS (it covers <<preserved>> and <<create>> Nodes/Attributes)
			//Since <<delete>> Node Parameters are renamed <<create>> Node Parameters and therefore
			//we don't have to check LHS here. Also because <<delete>> Attributes never appear
			//(even not in UNSETs because they only revert Attribute values to Default, if any).
			for(Node nInRHS : rule.getRhs().getNodes()) {
				String nodeName = nInRHS.getName();
				EClass nodeType = nInRHS.getType();
				/** Add Parameter for RHS Nodes ********************************/
				if(nodeName!=null && !nodeName.equals("")) {
					// ..to rule
					Parameter pForRule = HenshinFactory.eINSTANCE.createParameter(nodeName);
					pForRule.setType(nodeType);
					if(HenshinRuleAnalysisUtilEx.getParameterByName(rule, nodeName)==null) {
						pForRule.setUnit(rule);
						rule.getParameters().add(pForRule);
						// ..to unit
						if(!pForRule.getName().equals(GlobalConstants.SEL)
								&& HenshinRuleAnalysisUtilEx.getParameterByName(prioUnit, pForRule.getName())==null) {
							Parameter pForUnit = HenshinFactory.eINSTANCE.createParameter(nodeName);
							pForUnit.setType(nodeType);
							prioUnit.getParameters().add(pForUnit);
						}
					}
				}
				
				/** Add Parameter for RHS Attributes ***************************/
				for(Attribute a: nInRHS.getAttributes()) {
					EClassifier attributeType = a.getType().getEType();
					Object defaultValue = a.getType().getDefaultValue();
					String defaultValueName = null;
					if(defaultValue!=null) {
						defaultValueName = defaultValue.toString();
					}
					
					//Don't create Parameter if:
					// attribute is in quotation marks "..." (like specific literal values, e.g. "initial").
					// Else create Parameter.
					if((a.getValue()!="null" 
							&& !a.getValue().startsWith("\"") 
							&& ((defaultValueName!=null && !a.getValue().equals(defaultValueName))
							|| defaultValueName==null))) {
						Parameter pForRule = HenshinFactory.eINSTANCE.createParameter(a.getValue());
						pForRule.setType(attributeType);
						Parameter pForUnit = HenshinFactory.eINSTANCE.createParameter(a.getValue());
						pForUnit.setType(attributeType);
						if(HenshinRuleAnalysisUtilEx.getParameterByName(rule, pForRule.getName())==null) {
							// ..to rule
							rule.getParameters().add(pForRule);
							pForRule.setUnit(rule);
							// ..to unit
							if(HenshinRuleAnalysisUtilEx.getParameterByName(prioUnit, pForUnit.getName())==null) {
								prioUnit.getParameters().add(pForUnit);
								pForUnit.setUnit(prioUnit);
							}
						}
					}
				}
			}			
			
			// Create Mappings
			for(Parameter p :rule.getParameters()) {
				// == selectedEObject
				assert(p.getName()!=null): rule.getName();
				if(p.getName().equals(GlobalConstants.SEL)) {
					ParameterMapping selEObjectMapping = HenshinFactory.eINSTANCE.createParameterMapping();
					selEObjectMapping.setSource(selectedEObjectParameter);
					selEObjectMapping.setTarget(p);			
					prioUnit.getParameterMappings().add(selEObjectMapping);
				}
				// == selected element is the toBeDeleted (in case there is no context to delete from)
				else if(HenshinRuleAnalysisUtilEx.getParameterByName(rule, GlobalConstants.SEL)==null && p.getName().equals(GlobalConstants.DEL)) {
					ParameterMapping selEObjectMapping = HenshinFactory.eINSTANCE.createParameterMapping();
					selEObjectMapping.setSource(selectedEObjectParameter);
					selEObjectMapping.setTarget(p);			
					prioUnit.getParameterMappings().add(selEObjectMapping);
				}
				// == new / out-parameter
				else if(p.getName().matches(GlobalConstants.NEW+"[0-9]*") || (rule.getName().startsWith("create") && p.getName().matches(GlobalConstants.CHILD+"[0-9]*"))) {
					ParameterMapping pm = HenshinFactory.eINSTANCE.createParameterMapping();
					pm.setSource(p);
					pm.setTarget(HenshinRuleAnalysisUtilEx.getParameterByName(prioUnit, p.getName()));
					if(!prioUnit.getParameterMappings().contains(pm)) {
						prioUnit.getParameterMappings().add(pm);
					}
				}
				else if(p.getName().matches(GlobalConstants.NEW+"[0-9]*") || (rule.getName().startsWith("delete") && p.getName().matches(GlobalConstants.CHILD+"[0-9]*"))) {
					ParameterMapping pm = HenshinFactory.eINSTANCE.createParameterMapping();
					pm.setSource(HenshinRuleAnalysisUtilEx.getParameterByName(prioUnit, p.getName()));
					pm.setTarget(p);
					if(!prioUnit.getParameterMappings().contains(pm)) {
						prioUnit.getParameterMappings().add(pm);
					}
				}
				
				else if(p.getName().matches(GlobalConstants.NEWTGT+"[0-9]*")|| p.getName().matches(GlobalConstants.NEWSRC+"[0-9]*")) {
					ParameterMapping pm = HenshinFactory.eINSTANCE.createParameterMapping();
					pm.setTarget(p);
					pm.setSource(HenshinRuleAnalysisUtilEx.getParameterByName(prioUnit, p.getName()));
					if(!prioUnit.getParameterMappings().contains(pm)) {
						prioUnit.getParameterMappings().add(pm);
					}
					
				// == every other in-parameter
				}else{
					ParameterMapping pm = HenshinFactory.eINSTANCE.createParameterMapping();
					pm.setSource(HenshinRuleAnalysisUtilEx.getParameterByName(prioUnit, p.getName()));
					pm.setTarget(p);
					if(!prioUnit.getParameterMappings().contains(pm)) {
						prioUnit.getParameterMappings().add(pm);
					}		
				}
			}					
			// Add rule to unit
			prioUnit.getSubUnits().add(rule);						
		}		
		// Add unit to module
		module.getUnits().add(prioUnit);
		
		// kick out unnecessary sub package imports when super package import available
		// and set main meta-model packages as first import
		organizeImports(module);
	}
	
	
	/**
	 * This method removes unnecessary imports of EPackages that are
	 * sub packages of imported super packages. Additionally, this method
	 * removes all EPackage imports whose model elements have not been used.
	 * Also the meta model EPackage will always be set as the first import.
	 * @param module
	 */
	private static void organizeImports(Module module) {

		// find out which (sub) packages have actually been used (via node type usage)
		List<EPackage> actuallyUsedEPackages = new ArrayList<EPackage>();
		for(Rule rule: HenshinModuleAnalysis.getAllRules(module)) {
			List<Node> allNodesInRule = new ArrayList<Node>();
			allNodesInRule.addAll(rule.getRhs().getNodes());
			allNodesInRule.addAll(rule.getLhs().getNodes());
			for(Node node: allNodesInRule) {
				EPackage usedEPackage = node.getType().getEPackage();
				if(!actuallyUsedEPackages.contains(usedEPackage)) {
					actuallyUsedEPackages.add(usedEPackage);
				}
			}
		}
		
		// get EPackage of main meta-model
		EPackage mainMetaModel = Configuration.getInstance().EPACKAGESSTACK.firstElement();
		// get sub EPackages of main meta-model
		List<EPackage> subsOfMain = new ArrayList<EPackage>();
		try {
			subsOfMain.addAll(Common.getAllSubEPackages(mainMetaModel));
		} catch (EPackageNotFoundException e) {
			e.printStackTrace();
		}
	
		// remove the following EPackages:
		// a) unused EPackages which are not the EPackage of the meta-model
		// b) sub EPackages of the meta-model
		Iterator<EPackage> itImports = module.getImports().iterator();
		while(itImports.hasNext()) {
			
			EPackage currentEPackage = itImports.next();				
			// if currentEPackage is not the meta-model itself....
			if(!mainMetaModel.equals(currentEPackage)) {
				// ..but a sub package of the meta-model			
				// ..or actually not used: remove it.					
				boolean actuallyUsed = actuallyUsedEPackages.contains(currentEPackage);
				if( subsOfMain.contains(currentEPackage) || !actuallyUsed) {
					itImports.remove();
				}				
			}		
		}		
	}
	
	
	/**
	 * Method that removes parameters which are not needed for an inverse.
	 * E.g. a DELETE-Module does not need EAttributes which were necessary for the
	 * creation of an EClassifier inside a CREATE-Module.
	 * @param module
	 * @param mainUnit
	 */
	private static void removeUnnecessaryParametersForDELETE(Module module, Unit mainUnit) {
		//retain only ChildX/ExistingX/ToBeDeleted Parameters
		List<Parameter> unnecessaryParameters = new ArrayList<Parameter>();

		for(Rule r: HenshinModuleAnalysis.getAllRules(module)) {
			for(Parameter p: r.getParameters()) {
				
				if(p.getName().startsWith(GlobalConstants.CHILD) || p.getName().startsWith(GlobalConstants.EX) || p.getName().startsWith(GlobalConstants.DEL)) {
					
					boolean alreadyContained = false;
					for(Parameter pInInverseUnit: mainUnit.getParameters()) {
						if(pInInverseUnit.equals(p.getName())) {
							alreadyContained = true;
							break;
						}
					}
					
					if(!alreadyContained) {
						Parameter newEClassParam = HenshinFactory.eINSTANCE.createParameter(p.getName());
						newEClassParam.setType(p.getType());
						mainUnit.getParameters().add(newEClassParam);
					}												
				}else{
					unnecessaryParameters.add(p);
				}
			}
		}			
		//remove unnecessary parameters
		HenshinModuleAnalysis.getAllRules(module).get(0).getParameters().removeAll(unnecessaryParameters);
	}
	
	
	/**
	 * Method that removes all units (not rules) inside a module.
	 * @param module
	 */
	private static void removeAllNonRuleUnits(Module module) {
		Iterator<Unit> itUnit = module.getUnits().iterator();
		while(itUnit.hasNext()) {
			Unit unit = itUnit.next();
			if(!(unit instanceof Rule)) {
				itUnit.remove(); //removes the current unit (not the iterator)
			}
		}		
	}
}
