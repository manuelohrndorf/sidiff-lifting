package org.silift.difference.rulereviser;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.impl.EcorePackageImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Action;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.ParameterMapping;
import org.eclipse.emf.henshin.model.PriorityUnit;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.emf.henshin.model.impl.HenshinFactoryImpl;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;


public class ERReviser {

	enum ParameterDirection{
		IN, OUT
	}
	
	String workingDirectory;
	HenshinResourceSet resourceSet;
	Module module;

	
	Map<Rule, Map<Parameter, ParameterDirection>> rule2parameterDirections;
	
	int toBeDeletedIterator = 0;
	int preservedIterator = 0;
	int newIterator = 0;
	
	public void initModule(String workingDirectory, String moduleName){
		this.workingDirectory = workingDirectory;

		EcorePackageImpl.init();
		
		resourceSet = new HenshinResourceSet(workingDirectory);
		module = resourceSet.getModule(moduleName);
		
		EcoreUtil.resolveAll(module);
		

		rule2parameterDirections = new HashMap<>();
	}
	
	public void reviseHenshinModule(){
		
		for(Unit unit: module.getUnits()){
			if(unit instanceof Rule){
				Rule rule =(Rule) EcoreUtil.copy(unit);
				for(Node node : rule.getLhs().getNodes()){
					checkNodeParameter(rule, node, node.getAction().getType());
					for(Attribute attribute : node.getAttributes()){
						System.out.println(attribute.getValue());
						checkAttributeParameter(rule, attribute, attribute.getAction().getType());
					}
				}
				for(Node node : rule.getRhs().getNodes()){
					if(node.getAction() != null && node.getAction().getType().equals(Action.Type.CREATE)){
					checkNodeParameter(rule, node, node.getAction().getType());
					for(Attribute attribute : node.getAttributes()){
						System.out.println(attribute.getValue());
						checkAttributeParameter(rule, attribute, attribute.getAction().getType());
					}
				}
				}
				Unit mainUnit = createMainUnit(rule);
				Module newModule = createModule(mainUnit);
				saveRule(newModule);
				toBeDeletedIterator = preservedIterator =newIterator = 0;
			}
		}
	}
	
	private Module createModule(Unit unit){
		Module module = HenshinFactoryImpl.eINSTANCE.createModule();
		module.getUnits().add(unit);
		for(Unit subUnit: unit.getSubUnits(false)){
			module.getUnits().add(subUnit);
			if(subUnit instanceof Rule){
				module.setName(subUnit.getName());
				break;
			}
		}
		return module;
	}
	
	private Unit createMainUnit(Rule rule){
		PriorityUnit mainUnit = HenshinFactoryImpl.eINSTANCE.createPriorityUnit();
		mainUnit.setName("mainUnit");
		mainUnit.getSubUnits().add(rule);
		for(Parameter ruleParameter:rule.getParameters()){
			Parameter unitParameter = HenshinFactoryImpl.eINSTANCE.createParameter();
			unitParameter.setName(ruleParameter.getName());
			mainUnit.getParameters().add(unitParameter);
			ParameterMapping mapping = HenshinFactoryImpl.eINSTANCE.createParameterMapping();
			mainUnit.getParameterMappings().add(mapping);
			if(rule2parameterDirections.get(rule).get(ruleParameter).equals(ParameterDirection.IN)){
				mapping.setSource(unitParameter);
				mapping.setTarget(ruleParameter);
			}else{
				mapping.setSource(ruleParameter);
				mapping.setTarget(unitParameter);
			}
		}
		return mainUnit;
	}
	
	
	private void checkAttributeParameter(Rule rule, Attribute attribute, Action.Type type){
		Map<Parameter, ParameterDirection> parameterDirections;
		if(rule2parameterDirections.get(rule)!=null){
			parameterDirections = rule2parameterDirections.get(rule);
		}else{
			parameterDirections = new HashMap<Parameter, ParameterDirection>();
		}
		
		Parameter parameter = rule.getParameter(attribute.getValue());
		
		if(parameter!=null){
			if(type.equals(Action.Type.CREATE)){
				parameterDirections.put(parameter, ParameterDirection.IN);
			}else{
				parameterDirections.put(parameter, ParameterDirection.OUT);
			}
		}
		
		rule2parameterDirections.put(rule, parameterDirections);
		
	}
	
	private void checkNodeParameter(Rule rule, Node node, Action.Type type){
		Map<Parameter, ParameterDirection> parameterDirections;
		if(rule2parameterDirections.get(rule)!=null){
			parameterDirections = rule2parameterDirections.get(rule);
		}else{
			parameterDirections = new HashMap<Parameter, ParameterDirection>();
		}
		String name ="";
		
		if(node.getName()==null){
			switch(type){
				case PRESERVE:
					name = "preserved_"+node.getType().getName()+preservedIterator++;
					rule.getMappings().getImage(node, rule.getRhs()).setName(name);
					break;
				case DELETE:
					name = "toBeDeleted_"+node.getType().getName()+toBeDeletedIterator++;
					break;
				case CREATE:
					name = "new_"+node.getType().getName()+newIterator++;
					break;
				default:
			}
			node.setName(name);
			
		}else{
			name = node.getName();
		}
		
		Parameter parameter = rule.getParameter(node.getName());
		if(parameter == null){
			parameter = HenshinFactoryImpl.eINSTANCE.createParameter(name);
			rule.getParameters().add(parameter);
		}
		if(type.equals(Action.Type.CREATE)){
			parameterDirections.put(parameter, ParameterDirection.OUT);
		}else{
			parameterDirections.put(parameter, ParameterDirection.IN);
		}
		rule2parameterDirections.put(rule, parameterDirections);
		
	}

	
	
	
	
	private void saveRule(Module module){
		HenshinResourceSet henshinResourceSet = new HenshinResourceSet();
		Resource resource = henshinResourceSet.createResource(URI.createFileURI(workingDirectory+System.getProperty("file.separator")+module.getName() +"_execute.henshin"));
		resource.getContents().add(module);
		try {
			resource.save(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
