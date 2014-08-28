package org.sidiff.editrule.generator.serge.generators.actions;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx;
import org.sidiff.common.henshin.NodePair;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.editrule.generator.exceptions.OperationTypeNotImplementedException;
import org.sidiff.editrule.generator.serge.configuration.Configuration;
import org.sidiff.editrule.generator.serge.configuration.GlobalConstants;

public class AddGenerator {

	/**
	 * Outgoing non-containment EReference of the contextClass.
	 */
	private EReference outReference;

	/**
	 * Context class whose outgoing EReference shall point to a further
	 * eClassifier.
	 */
	private EClass contextClass;

	/**
	 * The eClassifier which should be added.
	 */
	private EClass targetClass;

	/**
	 * The configuration.
	 */
	private static Configuration config = Configuration.getInstance();

	/**
	 * The Constructor
	 * 
	 * @param outReference
	 * @param contextClassifier
	 * @param eClassifier
	 */
	public AddGenerator(EReference outReference, EClass contextClassifier) {
		super();
		this.outReference = outReference;
		this.contextClass = contextClassifier;
		this.targetClass = outReference.getEReferenceType();
	}

	public Module generate() throws OperationTypeNotImplementedException {
		String name = GlobalConstants.ADD_prefix + contextClass.getName() + "_(" + outReference.getName() + ")"
				+ GlobalConstants.TGT + targetClass.getName();
		LogUtil.log(LogEvent.NOTICE, "Generating ADD : " + name);

		Module module = HenshinFactory.eINSTANCE.createModule();
		module.setName(name);

		module.setDescription("Adds to " + contextClass.getName() + "'s reference " + outReference.getName()
				+ " the target " + targetClass.getName());

		// add imports
		module.getImports().addAll(config.EPACKAGESSTACK);

		// create Rule
		Rule rule = createRule();
		module.getUnits().add(rule);

		return module;
	}

	private Rule createRule() {
		Rule rule = HenshinFactory.eINSTANCE.createRule();
		rule.setActivated(true);
		rule.setName("addTo" + contextClass.getName() + "_" + outReference.getName() + "_" + targetClass.getName());
		rule.setDescription("Adds to " + contextClass.getName() + "'s reference " + outReference.getName()
				+ " the target " + targetClass.getName());

		// create preserved node for eClass
		NodePair selectedNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, GlobalConstants.SEL,
				contextClass);
		NodePair newNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, GlobalConstants.NEWTGT, targetClass);

		// create <<create>> edge for new target for EReference and it's
		// EOpposite, if any
		HenshinRuleAnalysisUtilEx.createCreateEdge(selectedNodePair.getRhsNode(), newNodePair.getRhsNode(),
				outReference);
		
		return rule;
	}
}
