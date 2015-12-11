package org.sidiff.editrule.generator.serge.generators.actions;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.common.emf.extensions.impl.EClassifierInfo;
import org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.editrule.generator.exceptions.OperationTypeNotImplementedException;
import org.sidiff.editrule.generator.serge.configuration.Configuration;
import org.sidiff.editrule.generator.serge.configuration.GlobalConstants;
import org.sidiff.editrule.generator.serge.configuration.Configuration.OperationTypeGroup;
import org.sidiff.editrule.generator.serge.core.ModuleInternalsApplicator;
import org.sidiff.editrule.generator.types.OperationType;

public class CreateGenerator {

	/**
	 * Incoming containment reference type (if available)
	 */
	private EReference containmentReference;

	/**
	 * Context EClassifier (Can be a sub type of the original type set in a
	 * reference).
	 */
	private EClassifier contextClassifier;

	/**
	 * The EClassifier to be created.
	 */
	private EClassifier child;

	/**
	 * The EClassifierinfo of the child to be created.
	 */
	private EClassifierInfo childInfo;

	/**
	 * Configuration access
	 */
	private Configuration config = Configuration.getInstance();

	/**
	 * Constructor
	 * 
	 * @param containmentReference
	 * @param contextClassifier
	 * @param childInfo
	 */
	public CreateGenerator(EReference containmentReference, EClassifier contextClassifier, EClassifierInfo childInfo) {

		this.containmentReference = containmentReference;
		this.contextClassifier = contextClassifier;
		this.childInfo = childInfo;
		this.child = childInfo.getTheEClassifier();
	}

	public Module generate() throws OperationTypeNotImplementedException {

		// Create different CREATE-Modules depending on whether or not a context
		// for the object to create is null
		// This can be if
		// (a) there is no container for this type in the meta-model
		// (b) the object to create is a stereotype

		// common variables for both cases:
		Module module = null;
		Rule rule = null;
		Node newNode = null;

		// check for available contextClassifier and produce modules accordingly
		if (contextClassifier != null) {

			// Create Module
			module = HenshinFactory.eINSTANCE.createModule();
			String name = GlobalConstants.CREATE_prefix + child.getName() + GlobalConstants.IN
					+ contextClassifier.getName() + "_(" + containmentReference.getName() + ")";
			LogUtil.log(LogEvent.NOTICE, "Generating " + name);
			module.setDescription("Creates one " + child.getName() + " in " + contextClassifier.getName());
			module.setName(name);

			// Add imports for meta model
			module.getImports().addAll(config.EPACKAGESSTACK);

			// create rule
			rule = ModuleInternalsApplicator.createBasicRule(module, containmentReference, child, contextClassifier,
					null, null, OperationType.CREATE);
			newNode = HenshinRuleAnalysisUtilEx.getRHSMinusLHSNodes(rule).get(0);

		} else {

			// Create Module
			module = HenshinFactory.eINSTANCE.createModule();
			String name = GlobalConstants.CREATE_prefix + child.getName();
			LogUtil.log(LogEvent.NOTICE, "Generating " + name);
			module.setDescription("Creates one " + child.getName());
			module.setName(name);

			// Add imports for meta model
			module.getImports().addAll(config.EPACKAGESSTACK);

			// create rule
			rule = ModuleInternalsApplicator.createBasicRule(module, null, child, null, null, null,
					OperationType.CREATE);
			newNode = HenshinRuleAnalysisUtilEx.getRHSMinusLHSNodes(rule).get(0);

		}

		// create mandatories if any
		if (childInfo.hasMandatories()) {

			if (config.CREATE_MANDATORY_CHILDREN)
				ModuleInternalsApplicator.createMandatoryChildren(rule, childInfo, newNode, OperationType.CREATE);
			if (config.CREATE_MANDATORY_NEIGHBOURS)
				ModuleInternalsApplicator.createMandatoryNeighbours(rule, childInfo, newNode, OperationType.CREATE);

		}

		return module;
	}

}
