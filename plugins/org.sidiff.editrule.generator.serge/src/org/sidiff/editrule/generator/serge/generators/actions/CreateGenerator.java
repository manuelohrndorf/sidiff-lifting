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
import org.sidiff.editrule.generator.serge.core.ModuleInternalsApplicator;
import org.sidiff.editrule.generator.types.OperationType;

public class CreateGenerator {
	
	/**
	 * Incoming containment reference type (if available) 
	 */
	private EReference containmentReference;
	
	/**
	 * Context EClassifier (Can be a sub type of the original type set in a reference).
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
	 * @param containmentReference
	 * @param contextClassifier
	 * @param childInfo
	 */
	public CreateGenerator(EReference containmentReference, EClassifier contextClassifier, EClassifierInfo childInfo) {
		assert(containmentReference.isContainment());
		
		this.containmentReference = containmentReference;
		this.contextClassifier = contextClassifier;
		this.childInfo = childInfo;
		this.child = childInfo.getTheEClassifier();
	}
	
	public Module generate() throws OperationTypeNotImplementedException{	

		// Create Module				
		Module module = HenshinFactory.eINSTANCE.createModule();
		String name = GlobalConstants.CREATE_prefix + child.getName()+ GlobalConstants.IN + contextClassifier.getName()+"_("+containmentReference.getName()+")";
		LogUtil.log(LogEvent.NOTICE, "Generating " + name);					
		module.setDescription("Creates one "+child.getName()+" in " + contextClassifier.getName());
		module.setName(name);

		// Add imports for meta model
		module.getImports().addAll(config.EPACKAGESSTACK);

		// create rule
		Rule rule = ModuleInternalsApplicator.createBasicRule(module, containmentReference, child, contextClassifier, null, null, OperationType.CREATE);
		Node newNode = HenshinRuleAnalysisUtilEx.getRHSMinusLHSNodes(rule).get(0);

		// create mandatories if any
		if(childInfo.hasMandatories()) {

			ModuleInternalsApplicator.createMandatoryChildren(rule, childInfo, newNode, OperationType.CREATE, config.REDUCETOSUPERTYPE_CREATEDELETE);			
			ModuleInternalsApplicator.createMandatoryNeighbours(rule, childInfo, newNode, OperationType.CREATE, config.REDUCETOSUPERTYPE_CREATEDELETE);

		}
		
		return module;
	}
	
}
