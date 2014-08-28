package org.sidiff.editrule.generator.serge.generators.actions;

import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.common.henshin.HenshinModuleAnalysis;
import org.sidiff.common.henshin.HenshinModuleUtil;
import org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx;
import org.sidiff.editrule.generator.exceptions.OperationTypeNotImplementedException;
import org.sidiff.editrule.generator.serge.configuration.GlobalConstants;
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
			throw new OperationTypeNotImplementedException(opType.toString());
		}
		
		// adjust Strings inside
		replaceNewsWithToBeDeleted(inverse);
		return inverse;
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
			for (Parameter p : r.getParameters()) {
				String pN = p.getName();
				if (pN != null && pN.equals(GlobalConstants.NEW)) {
					p.setName(GlobalConstants.DEL);
				}
			}
		}

	}
	
}
