package org.sidiff.editrule.generator.serge.generators.actions;

import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.common.henshin.HenshinModuleAnalysis;
import org.sidiff.common.henshin.HenshinModuleUtil;
import org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx;
import org.sidiff.editrule.generator.exceptions.OperationTypeNotImplementedException;
import org.sidiff.editrule.generator.serge.configuration.GlobalConstants;
import org.sidiff.editrule.generator.serge.core.InverseTracker;
import org.sidiff.editrule.generator.types.OperationType;

public class InverseGenerator {
	
	/**
	 * The input module.
	 */
	private Module inputModule;
	
	/**
	 * The operation type of the input module.
	 */
	private OperationType opType;
	
	/**
	 * Constructor
	 * @param inputModule
	 * @param inputOperationType
	 */
	public InverseGenerator(Module inputModule, OperationType inputOperationType) {
		this.inputModule = inputModule;
		this.opType = inputOperationType;
	}
	
	/**
	 * Generates an inverse of a module. E.g. DELETE for CREATE or REMOVE for ADD
	 * etc.
	 * 
	 * @return the inverted module.
	 * @throws OperationTypeNotImplementedException
	 */
	public Module generate() 	throws OperationTypeNotImplementedException {

		String name = "";
		String description = "";
		Module inverse = null;
		Rule firstRule = null;

		switch (opType) {

		case CREATE:

			name = inputModule.getName().replaceFirst(GlobalConstants.CREATE_prefix, GlobalConstants.DELETE_prefix);
			description = inputModule.getDescription().replaceFirst("Creates", "Deletes");
			inverse = HenshinModuleUtil.createInverse(name, description, inputModule);
			firstRule = HenshinModuleAnalysis.getAllRules(inverse).get(0);
			firstRule.setName(firstRule.getName().replaceFirst("create", "delete"));
			firstRule.setDescription(firstRule.getDescription().replaceFirst("create", "delete"));
			break;
			
		case ATTACH:
			
			name = inputModule.getName().replaceFirst(GlobalConstants.ATTACH_prefix, GlobalConstants.DETACH_prefix);
			description = inputModule.getDescription().replaceFirst("Attaches", "Detaches");
			inverse = HenshinModuleUtil.createInverse(name, description, inputModule);
			firstRule = HenshinModuleAnalysis.getAllRules(inverse).get(0);
			firstRule.setName(firstRule.getName().replaceFirst("attach", "detach"));
			firstRule.setDescription(firstRule.getDescription().replaceFirst("attach", "detach"));
			break;

		case ADD:

			name = inputModule.getName().replaceFirst(GlobalConstants.ADD_prefix, GlobalConstants.REMOVE_prefix);
			description = inputModule.getDescription().replaceFirst("Adds to", "Removes");
			inverse = HenshinModuleUtil.createInverse(name, description, inputModule);
			firstRule = HenshinModuleAnalysis.getAllRules(inverse).get(0);
			firstRule.setName(firstRule.getName().replaceFirst("addTo", "removeFrom"));
			firstRule.setDescription(firstRule.getDescription().replaceFirst("Adds to", "Removes from"));
			HenshinRuleAnalysisUtilEx.getNodeByName(firstRule, GlobalConstants.NEWTGT, true).setName(
					GlobalConstants.OLDTGT); // rename Node in LHS
			HenshinRuleAnalysisUtilEx.getNodeByName(firstRule, GlobalConstants.NEWTGT, false).setName(
					GlobalConstants.OLDTGT); // rename Node in RHS

			break;

		case SET_ATTRIBUTE:

			name = inputModule.getName().replaceFirst(GlobalConstants.SET_ATTRIBUTE_prefix, GlobalConstants.UNSET_ATTRIBUTE_prefix);
			description = inputModule.getDescription().replaceFirst("Sets (an attribute)", "Unsets (an attribute)");
			inverse = HenshinModuleUtil.createInverse(name, description, inputModule);
			firstRule = HenshinModuleAnalysis.getAllRules(inverse).get(0);
			firstRule.setName(firstRule.getName().replaceFirst("setAttribute", "unsetAtttribute"));
			firstRule.setDescription(firstRule.getDescription().replaceFirst("Set (an attribute)", "Unset (an attribute)"));
			break;

		case SET_REFERENCE:

			name = inputModule.getName().replaceFirst(GlobalConstants.SET_REFERENCE_prefix, GlobalConstants.UNSET_REFERENCE_prefix);
			description = inputModule.getDescription().replaceFirst("Sets (a reference)", "Unsets (a reference)");
			inverse = HenshinModuleUtil.createInverse(name, description, inputModule);
			firstRule = HenshinModuleAnalysis.getAllRules(inverse).get(0);
			firstRule.setName(firstRule.getName().replaceFirst("setReference", "unsetReference"));
			firstRule.setDescription(firstRule.getDescription().replaceFirst("Set (a reference)", "Unset (a reference)"));
			break;

		default:
			throw new OperationTypeNotImplementedException(opType);
		}
		
		//Find node/attribute equivalents
		InverseTracker.INSTANCE.addInversePair(inputModule, inverse);
		mapNodesAndAttributesByName(HenshinModuleAnalysis.getAllRules(inputModule).get(0),
				HenshinModuleAnalysis.getAllRules(inverse).get(0));
		
		// adjust Strings inside
		replaceNewsWithToBeDeleted(inverse);
		return inverse;
	}
	
	private void mapNodesAndAttributesByName(Rule rule, Rule inverseRule){
		mapNodesAndAttributesByName(rule.getRhs(), inverseRule.getLhs());
		mapNodesAndAttributesByName(rule.getLhs(), inverseRule.getRhs());
		mapNodesAndAttributesByName(rule.getLhs(), inverseRule.getLhs());
	}
	
	private void mapNodesAndAttributesByName(Graph graphA, Graph graphB){
		//Nodes
		for (Node n : graphA.getNodes()) {
			if (graphA.isRhs() && HenshinRuleAnalysisUtilEx.isPreservedNode(n)) continue;
			String nN = n.getName();
			if (nN == null || nN.equals("")) continue;
			for (Node iN : graphB.getNodes()){
				if (graphB.isRhs() && HenshinRuleAnalysisUtilEx.isPreservedNode(iN)) continue;
				String inN = iN.getName();
				if (inN != null && nN.equals(inN)) InverseTracker.INSTANCE.addInversePair(n, iN);
			}
		}
		
		//Attributes
		//TODO Braucht es hier noch einen Preserve-RHS-Schutz wie bei den Nodes?
		for (Node n : graphA.getNodes()) {
			for (Attribute a : n.getAttributes()){
				String aN = a.getValue();
				if (aN == null || aN.equals("")) continue;
				for (Node iN : graphB.getNodes()){
					for (Attribute iA : iN.getAttributes()){
						String iaN = iA.getValue();
						if (iaN != null && aN.equals(iaN)) InverseTracker.INSTANCE.addInversePair(a, iA);
					}
				}
			}
		}
	}
	
	/**
	 * Inverts every parameter and node name beginning beginning or equal to
	 * "New" to "ToBeDeleted"
	 * 
	 * @param module
	 */
	private void replaceNewsWithToBeDeleted(Module module) {

		for (Rule r : HenshinModuleAnalysis.getAllRules(module)) {
			for (Node n : r.getLhs().getNodes()) {
				String nN = n.getName();
				if (nN != null && nN.equals(GlobalConstants.NEW)) {
					n.setName(GlobalConstants.DEL);
				}
			}
			for (Node n : r.getRhs().getNodes()) {
				if (n.getName().equals(GlobalConstants.NEW)) {
					n.setName(GlobalConstants.DEL);
				}
			}
			//TODO An dieser Stelle sollte es keine Parameter geben
			for (Parameter p : r.getParameters()) {
				String pN = p.getName();
				if (pN != null && pN.equals(GlobalConstants.NEW)) {
					p.setName(GlobalConstants.DEL);
				}
			}
		}

	}
	
}
