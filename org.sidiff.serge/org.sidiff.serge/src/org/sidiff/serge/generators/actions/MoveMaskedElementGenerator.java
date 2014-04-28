package org.sidiff.serge.generators.actions;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.common.emf.extensions.impl.Mask;
import org.sidiff.common.henshin.HenshinModuleAnalysis;
import org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx;
import org.sidiff.common.henshin.NodePair;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.serge.configuration.Configuration;
import org.sidiff.serge.configuration.Configuration.OperationType;
import org.sidiff.serge.configuration.GlobalConstants;
import org.sidiff.serge.core.Common;
import org.sidiff.serge.exceptions.OperationTypeNotImplementedException;

public class MoveMaskedElementGenerator {
	
	/**
	 * The old EReference that that points from the old context classifier to the
	 * eClassifier
	 */
	private EReference 	oldEReference;
	
	/**
	 * The new EReference that that points from the old context classifier to the
	 * eClassifier
	 */
	private EReference 	newEReference;
	
	/**
	 * The old context.
	 */
	private EClassifier oldContext;
	
	/**
	 * The new context.
	 */
	private EClassifier newContext;
	
	/**
	 * The mask, more details see {@link Mask}
	 */
	private Mask mask;
	
	/**
	 * The configuration.
	 */
	private static Configuration config = Configuration.getInstance();

	/**
	 * The current Operation Type to generate for
	 */
	private OperationType opType;
	
	/**
	 * Constructor
	 * @param eClassifier
	 * @param oldEReference
	 * @param oldContext
	 * @param newContext
	 */
	public MoveMaskedElementGenerator(Mask mask, EReference oldEReference,
			EClassifier oldContext, EClassifier newContext, EReference newEReference, OperationType opType) {

		this.mask = mask;
		this.oldEReference = oldEReference;
		this.newEReference = newEReference;
		this.oldContext = oldContext;
		this.newContext = newContext;
		this.opType = opType;
		
	}
	
	public Module generate() throws OperationTypeNotImplementedException {

		String name = GlobalConstants.MOVE_MASKED_prefix 
					+ mask.getName() + GlobalConstants.FROM
					+ oldContext.getName()
					+"_(" + oldEReference.getName()
					+ ")"+ GlobalConstants.TO
					+ newContext.getName()
					+"_("+newEReference.getName()+")";
		
		LogUtil.log(LogEvent.NOTICE, "Generating Move Masked ELement : " + name);

		Module MOVE_Module = HenshinFactory.eINSTANCE.createModule();
		MOVE_Module.setName(name);

		MOVE_Module.setDescription("Moves "+ mask.getName()
					+ " from "+oldContext.getName()
					+"(Reference:" + oldEReference.getName()
					+ ") to "+newContext.getName()
					+("(Reference:"+newEReference.getName()+")"));

		// add imports
		MOVE_Module.getImports().addAll(config.EPACKAGESSTACK);

		// create rule
		Common.createBasicRule(MOVE_Module, oldEReference, mask.getOriginalEClassifier(), oldContext, newEReference, newContext, opType);

		// create Attribute, containing the masked type
		Rule rule = HenshinModuleAnalysis.getAllRules(MOVE_Module).get(0);
		NodePair np = HenshinRuleAnalysisUtilEx.getNodePair(rule, (EClass)mask.getOriginalEClassifier(), GlobalConstants.SEL);
		HenshinRuleAnalysisUtilEx.createPreservedAttribute(np, mask.getEAttributeContainingFakeType(), "\""+mask.getTypeExtension().getName()+"\"", false);

		return MOVE_Module;
	}
	
}
