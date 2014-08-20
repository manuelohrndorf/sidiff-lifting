package org.sidiff.editrule.fixing;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.editrule.validation.EditRuleValidation;
import org.sidiff.editrule.validation.ValidationType;



public class ERFixingEngine {

	private List<EditRuleValidation> validations;

	public void fixit() throws UnsupportedValidationType {

		for (EditRuleValidation erValidation : validations) {
			String type = erValidation.validationType.toString();
			Module module = (Module)erValidation.editModule;
			EObject eObject = (EObject)erValidation.violatings.get(erValidation.violatings.size()-1);
			List<EObject> eObjects = new ArrayList<EObject>();
			for(int i = 0; i < erValidation.violatings.size(); i++){
				eObjects.add((EObject)erValidation.violatings.get(i));
			}
			fixit(type, module, eObject, eObjects);
		}
	}


	/**
	 * 
	 * @param type
	 * @param module
	 * @param eObject
	 * @param eObjects
	 * @throws UnsupportedValidationType 
	 */
	public void fixit(String type, Module module, EObject eObject, List<EObject> eObjects) throws UnsupportedValidationType{

		if(!canFixValidationType(type)){			
			throw new UnsupportedValidationType(type);
		}
		else{
			
			if(type.equals(ValidationType.mainUnit.toString())){
				EditRuleFixer.fix_mainUnit(module);
			}
			else if(type.equals(ValidationType.lhsBoundaries.toString())){				
				Node originNode = (Node)eObject;
				Node imageNode = (Node) eObjects.get(eObjects.size()-2);
				EditRuleFixer.fix_lhsBoundaries(originNode, imageNode);
			}
			else if(type.equals(ValidationType.mappedAllCreateNodes.toString())){
				Node node = (Node)eObject;
				EditRuleFixer.fix_mappedAllCreateNodes(module, node);
			}
			else if(type.equals(ValidationType.mappedAllRuleObjectInParameters.toString())){
				Parameter parameter = (Parameter)eObject;
				EditRuleFixer.fix_mappedAllRuleObjectInParameters(module, parameter);
			}
			else if(type.equals(ValidationType.multiRuleParameterEmbedding.toString())){
				Parameter parameter = (Parameter)eObject;
				Rule multiRule = (Rule)eObjects.get(0);
				EditRuleFixer.fix_multiRuleParameterEmbedding(multiRule, parameter);
			}
			else if(type.equals(ValidationType.multiRuleAttributeEmbedding.toString())){
				Attribute attribute = (Attribute) eObject;
				Node node = null;
				Rule multiRule = null;
				for(EObject obj : eObjects){
					if(obj instanceof Node){
						node = (Node)obj;
					}else if(obj instanceof Rule){
						multiRule = (Rule)obj;
					}
				}
				EditRuleFixer.fix_multiRuleAttributeEmbedding(multiRule, node, attribute);
			}
			else if(type.equals(ValidationType.multiRuleNodeEmbedding.toString())){
				Rule kernelRule = null;
				Rule multiRule = null;
				Node node = (Node)eObject;
				for(EObject obj : eObjects){
					if(obj instanceof Rule){
						Rule rule = (Rule)obj;
						if(rule.eContainer() instanceof Module){
							kernelRule = rule;
						}else if(rule.eContainer() instanceof Rule){
							multiRule = rule;
						}
					}
				}
				EditRuleFixer.fix_multiRuleNodeEmbedding(kernelRule, multiRule, node);
			}
			else if(type.equals(ValidationType.consistentEOpposite.toString())){
				Edge edge = (Edge) eObject;
				EditRuleFixer.fix_consistentEOpposite(edge);
			}
		}
	}

	/**
	 * Returns whether the ERFixingEngine is capable of
	 * fixing this type of Validation
	 * @param type
	 * @return
	 */
	public boolean canFixValidationType(String type){
		// Use Java Reflection to see whether a fixing method for this type is available
		Method[] fixerMethods = EditRuleFixer.class.getDeclaredMethods();
		for (int i = 0; i < fixerMethods.length; i++) {
			if (fixerMethods[i].getName().equals("fix_" + type)){
				return true;
			}
		}
		
		return false;
	}

	public List<EditRuleValidation> getValidations() {
		return validations;
	}

	public void setValidations(List<EditRuleValidation> validations) {
		this.validations = validations;
	}
}
