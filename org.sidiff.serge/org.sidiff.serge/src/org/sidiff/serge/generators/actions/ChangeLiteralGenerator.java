package org.sidiff.serge.generators.actions;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx;
import org.sidiff.common.henshin.NodePair;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.serge.core.Common;
import org.sidiff.serge.core.Configuration;
import org.sidiff.serge.core.GlobalConstants;

public class ChangeLiteralGenerator {

	/**
	 * The EAttribute that will have another EEnumLiteral as value.
	 */
	private EAttribute eAttribute;

	/**
	 * The context of the EAttribute.
	 */
	private EClassifier contextOfEAttribute;
	
	/**
	 * The old literal.
	 */
	private EEnumLiteral oldEENumliteral;
	
	/**
	 * The new literal.
	 */
	private EEnumLiteral newEENumliteral;
	
	/**
	 * The configuration.
	 */
	private static Configuration config = Configuration.getInstance();
	
	/**
	 * Constructor
	 * @param eAttribute
	 * @param eEnumLiteral
	 * @param contextOfEAttribute
	 * @param oldEENumliteral
	 * @param newEENumliteral
	 */
	public ChangeLiteralGenerator(EAttribute eAttribute, EEnumLiteral eEnumLiteral, EClassifier contextOfEAttribute, EEnumLiteral oldEENumliteral, EEnumLiteral newEENumliteral) {

		assert(eAttribute.getLowerBound() == 1 && eAttribute.getUpperBound() == 1);		
		this.eAttribute = eAttribute;
		this.contextOfEAttribute = contextOfEAttribute;
		this.oldEENumliteral = oldEENumliteral;
		this.newEENumliteral = newEENumliteral;		
	}

	public Module generate() {

		// CHANGE for EAttribute with EEnumLiteral as EType ****************************************************************/
		LogUtil.log(LogEvent.NOTICE, "Generating CHANGE_LITERAL : "
				+ contextOfEAttribute.getName() + eAttribute.getName()+ "From"
				+ oldEENumliteral.getName()
				+ "To "+newEENumliteral.getName());

		// create CHANGE_Module
		Module CHANGE_LITERAL_Module = HenshinFactory.eINSTANCE.createModule();

		// Add imports for meta model
		CHANGE_LITERAL_Module.getImports().addAll(config.EPACKAGESSTACK);

		// create rule
		Rule rule = HenshinFactory.eINSTANCE.createRule();
		rule.setActivated(true);
		rule.setName("change"+contextOfEAttribute.getName()
				+Common.toCamelCase(eAttribute.getName())+GlobalConstants.FROM
				+Common.toCamelCase(oldEENumliteral.getName())
				+GlobalConstants.TO+newEENumliteral.getName());
		rule.setDescription("Changes the attribute value of "
				+eAttribute.getName() +GlobalConstants.FROM
				+oldEENumliteral.getName()
				+GlobalConstants.TO+newEENumliteral.getName());
		CHANGE_LITERAL_Module.getUnits().add(rule);

		NodePair containerNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, GlobalConstants.SEL, eAttribute.eContainer().eClass());

		HenshinRuleAnalysisUtilEx.createPreservedAttribute(containerNodePair, eAttribute, "\""+oldEENumliteral.getName()+"\"", false);
		containerNodePair.getRhsNode().getAttribute(eAttribute).setValue("\""+newEENumliteral.getName()+"\"");

		// set outputFilename, Module name and description
		String name = GlobalConstants.CHANGE_LITERAL_prefix + contextOfEAttribute.getName() 
				+"_"+eAttribute.getName()
				+GlobalConstants.FROM+oldEENumliteral.getName()
				+GlobalConstants.TO+newEENumliteral.getName();
		CHANGE_LITERAL_Module.setName(name);
		CHANGE_LITERAL_Module.setDescription(rule.getDescription());

		return CHANGE_LITERAL_Module;
	}
	
}
