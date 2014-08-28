package org.sidiff.editrule.generator.serge.generators.actions;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx;
import org.sidiff.common.henshin.NodePair;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.editrule.generator.serge.configuration.Configuration;
import org.sidiff.editrule.generator.serge.configuration.GlobalConstants;
import org.sidiff.editrule.generator.serge.core.ModuleInternalsApplicator;

public class SetAttributeGenerator {
	
	/**
	 * The EAttribute to set.
	 */
	private EAttribute eAttribute;
	
	/**
	 *The context of the EAttribute.
	 */
	private EClassifier contextClassifier;
	
	/**
	 * The configuration.
	 */
	private Configuration config = Configuration.getInstance();
	
	/**
	 * Constructor
	 * @param contextClassifier
	 * @param eAttribute
	 */
	public SetAttributeGenerator(EClassifier contextClassifier, EAttribute eAttribute) {
		this.contextClassifier = contextClassifier;
		this.eAttribute = eAttribute;
	}
	
	public Module generate() {
		// SET for EAttributes ***************************************************************************/
		LogUtil.log(LogEvent.NOTICE, "Generating SET_ATTRIBUTE: " + contextClassifier.getName() + " attribute "+ eAttribute.getName());

		// create SET_Module
		Module SET_ATTRIBUTE_Module = HenshinFactory.eINSTANCE.createModule();

		// Add imports for meta model
		SET_ATTRIBUTE_Module.getImports().addAll(config.EPACKAGESSTACK);

		// create rule
		Rule rule = HenshinFactory.eINSTANCE.createRule();
		rule.setActivated(true);
		rule.setName("set"+contextClassifier.getName()+ModuleInternalsApplicator.toCamelCase(eAttribute.getName()));
		rule.setDescription("Sets the EAttribute "+eAttribute.getName());
		SET_ATTRIBUTE_Module.getUnits().add(rule);

		// create preserved node for eClass
		NodePair selectedNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, GlobalConstants.SEL, (EClass) contextClassifier);
		Node rhsNode = selectedNodePair.getRhsNode();

		// create attribute
		HenshinRuleAnalysisUtilEx.createCreateAttribute(rhsNode, eAttribute, ModuleInternalsApplicator.toCamelCase(eAttribute.getName()));

		// set module name and description
		String name = GlobalConstants.SET_ATTRIBUTE_prefix + contextClassifier.getName() +"_"+ModuleInternalsApplicator.toCamelCase(eAttribute.getName());
		SET_ATTRIBUTE_Module.setName(name);
		SET_ATTRIBUTE_Module.setDescription("Sets "+contextClassifier.getName()+" "+ModuleInternalsApplicator.toCamelCase(eAttribute.getName()));
		
		return SET_ATTRIBUTE_Module;
	}
}
