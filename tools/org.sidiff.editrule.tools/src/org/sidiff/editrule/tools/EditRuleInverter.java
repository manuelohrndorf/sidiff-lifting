package org.sidiff.editrule.tools;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.ParameterMapping;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx;

public class EditRuleInverter implements IApplication {

	private String editRuleURI;
	
	private String fileName;
	
	private Module module;
	
	@Override
	public Object start(IApplicationContext context) throws Exception {
		System.out.println("--------------- START ---------------");
		
		String[] args = (String[]) context.getArguments().get(IApplicationContext.APPLICATION_ARGS);
		
		editRuleURI = args[0];
		
		// Create a resource set with a base directory:
		HenshinResourceSet resourceSet = new HenshinResourceSet();

		// Load the module:
		module = resourceSet.getModule(editRuleURI, false);

		fileName = module.eResource().getURI().lastSegment();
		
		System.out.println("create inverse module ...");
		Module invertModule = createInverseModule(module);
	
		resourceSet.saveEObject(invertModule, editRuleURI.replace(fileName, invertModule.getName()+"_inverted.henshin"));
		
		
		System.out.println("--------------- FINISH ---------------");
		return IApplication.EXIT_OK;
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 
	 * @param module
	 * @return
	 */
	private static Module createInverseModule(Module module){
		List<Parameter> newParameters = new LinkedList<Parameter>();
		// create inverse
		Module invertedModule = EcoreUtil.copy(module);
		invertedModule.setName(invertModuleName(module.getName()));
		invertedModule.setDescription("");
		for(Unit unit: invertedModule.getUnits()) {
			if(unit instanceof Rule){
				createInverseRule((Rule)unit, newParameters);			
			}
		}
		if(invertedModule.getUnit("mainUnit") != null){
			createInverseMainUnit(invertedModule.getUnit("mainUnit"), newParameters);
		}
		return invertedModule;
	}
	
	/**
	 * 
	 * @param mainUnit
	 * @param parameters
	 */
	private static void createInverseMainUnit(Unit mainUnit, List<Parameter> parameters){
		
		List<Parameter> outputParameters = new ArrayList<Parameter>();
		List<Parameter> inputParameters = new ArrayList<Parameter>();
		
		for(Unit unit : mainUnit.getModule().getUnits()){
			if(unit instanceof Rule){
				Rule rule = (Rule)unit;
				// gather all <<delete>> nodes
				for(Node node : rule.getLhs().getNodes()){
					if(HenshinRuleAnalysisUtilEx.isDeletionNode(node)){
						inputParameters.add(rule.getParameter(node.getName()));
					}
				}
				
				// gather all <<create>> nodes
				for(Node node : rule.getRhs().getNodes()){
					if(HenshinRuleAnalysisUtilEx.isCreationNode(node)){
						outputParameters.add(rule.getParameter(node.getName()));
					}
				}
			}
		}
		
		// switch mappings
		for(Parameter outputParameter : outputParameters){
			for(ParameterMapping parameterMapping : mainUnit.getParameterMappings()){
				if(parameterMapping.getTarget().equals(outputParameter)){
					parameterMapping.setTarget(parameterMapping.getSource());
					parameterMapping.setSource(outputParameter);
					break;
				}
			}
		}
		
		// add parameters for attributes
		for(Parameter inputParameter : inputParameters){
			for(ParameterMapping parameterMapping : mainUnit.getParameterMappings()){
				if(parameterMapping.getSource().equals(inputParameter)){
					parameterMapping.setSource(parameterMapping.getTarget());
					parameterMapping.setTarget(inputParameter);
				}
			}
		}
		
		for(Parameter ruleParameter : parameters){
			Parameter unitParameter = HenshinFactory.eINSTANCE.createParameter(ruleParameter.getName());
			mainUnit.getParameters().add(unitParameter);
			ParameterMapping parameterMapping = HenshinFactory.eINSTANCE.createParameterMapping();
			parameterMapping.setSource(unitParameter);
			parameterMapping.setTarget(ruleParameter);
			mainUnit.getParameterMappings().add(parameterMapping);
		}
	}
	
	/**
	 * 
	 * @param rule
	 * @param parameters
	 */
	private static void createInverseRule(Rule rule, List<Parameter> parameters){
		
		Graph lhs = rule.getRhs();
		lhs.setName("LHS");
		Graph rhs = rule.getLhs();
		rhs.setName("RHS");
		rule.setLhs(lhs);
		rule.setRhs(rhs);
		
		for(Mapping m: rule.getMappings()) {
			Node origin = m.getImage();
			Graph orginGraph = m.getImage().getGraph();
			origin.setGraph(orginGraph);
			
			Node image = m.getOrigin();
			Graph imageGraph = m.getOrigin().getGraph();
			image.setGraph(imageGraph);
			
			m.setImage(image);
			m.setOrigin(origin);
			
		}
		
		// remove attributes under <<delete>> nodes and their ParameterMappings
		// and not used Parameters will be deleted automatically then.
		for(Node n:rule.getLhs().getNodes()) {
			List<ParameterMapping> removableMappings = new ArrayList<ParameterMapping>();
		
			if(HenshinRuleAnalysisUtilEx.isDeletionNode(n)) {
				for(Attribute a:n.getAttributes()) {
				
					for(ParameterMapping pm:rule.getParameterMappings()) {
						if(	pm.getTarget().getName().equals(a.getValue()) ||
								pm.getSource().getName().equals(a.getValue())) {
						
							removableMappings.add(pm);
						}
					}
				}
				n.getAttributes().clear();
			}
			rule.getParameterMappings().removeAll(removableMappings);
		}
		
		// create attributes under <<create>> nodes and their parameters (only for kernel rules)
		if(!rule.isMultiRule()){
			for(Node node : rule.getRhs().getNodes()){
				if(HenshinRuleAnalysisUtilEx.isCreationNode(node)){
					for(EAttribute eAttribute : node.getType().eClass().getEAllAttributes()){
						String parameterName = node.getName()+"_"+eAttribute.getName();
						HenshinFactory.eINSTANCE.createAttribute(node, eAttribute, parameterName);
						Parameter ruleParameter = HenshinFactory.eINSTANCE.createParameter(parameterName);
						rule.getParameters().add(ruleParameter);
						if(parameters != null){
							parameters.add(ruleParameter);
						}
					}
				}
			}
		}
		
		// create the inverse for each multirule
		for(Rule multiRule : rule.getMultiRules()){
			createInverseRule(multiRule, parameters);
			for(Node node : multiRule.getRhs().getNodes()){
				if(HenshinRuleAnalysisUtilEx.isCreationNode(node)){
					Parameter parameter = HenshinFactory.eINSTANCE.createParameter(node.getName());
					Rule kernelRule = multiRule.getKernelRule();
					kernelRule.getParameters().add(parameter);
					// map the parameter to the outer rule
					while(kernelRule.isMultiRule()){
						kernelRule = kernelRule.getKernelRule();
						Parameter outerParameter = HenshinFactory.eINSTANCE.createParameter(node.getName());
						kernelRule.getParameters().add(outerParameter);
					}
					parameters.add(parameter);
				}
			}
		}
	}
	
	/**
	 * Works only for modules which fulfill the conventions of SERGe
	 * 
	 * @param moduleName
	 * @return
	 */
	public static String invertModuleName(String moduleName){
		String invertedName = moduleName + "_inverse";
		if(moduleName.startsWith("CREATE")){
			invertedName = moduleName.replaceFirst("CREATE", "DELETE");
		}else if(moduleName.startsWith("DELETE")){
			invertedName = moduleName.replaceFirst("DELETE", "CREATE");
		}else if(moduleName.startsWith("SET")){
			invertedName = moduleName.replaceFirst("SET", "UNSET");
		}else if(moduleName.startsWith("UNSET")){
			invertedName = moduleName.replace("UNSET", "SET");
		}else if(moduleName.startsWith("ADD")){
			invertedName = moduleName.replaceFirst("ADD", "REMOVE");
		}else if(moduleName.startsWith("REMOVE")){
			invertedName = moduleName.replaceFirst("REMOVE", "ADD");
		}
		return invertedName;
	}
}
