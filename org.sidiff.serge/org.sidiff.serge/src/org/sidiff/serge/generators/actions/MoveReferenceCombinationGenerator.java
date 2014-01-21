package org.sidiff.serge.generators.actions;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Module;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.serge.core.Common;
import org.sidiff.serge.core.Configuration;
import org.sidiff.serge.core.GlobalConstants;
import org.sidiff.serge.core.Configuration.OperationType;
import org.sidiff.serge.exceptions.OperationTypeNotImplementedException;

public class MoveReferenceCombinationGenerator {

	/**
	 * The eClassifier to be moved.
	 */
	private EClassifier eClassifier;
	
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
	 * The configuration.
	 */
	private static Configuration config = Configuration.getInstance();

	/**
	 * Constructor
	 * @param eClassifier
	 * @param oldEReference
	 * @param oldContext
	 * @param newContext
	 */
	public MoveReferenceCombinationGenerator(EClassifier eClassifier, EReference oldEReference,
			EClassifier oldContext, EClassifier newContext, EReference newEReference) {


		this.eClassifier = eClassifier;
		this.oldEReference = oldEReference;
		this.newEReference = newEReference;
		this.oldContext = oldContext;
		this.newContext = newContext;
	}
	
	public Module generate() throws OperationTypeNotImplementedException {

		String name = GlobalConstants.MOVE_REF_COMBI_prefix + eClassifier.getName()
				+ GlobalConstants.FROM + oldContext.getName()
				+"_("+oldEReference.getName()+")"
				+ GlobalConstants.TO+newContext.getName()
				+"_("+newEReference.getName()+")"; 

		LogUtil.log(LogEvent.NOTICE, "Generating Move Reference Combination : " + name);

		Module MOVE_Module = HenshinFactory.eINSTANCE.createModule();
		MOVE_Module.setName(name);

		MOVE_Module.setDescription("MOVEs "+eClassifier.getName()
				+ " from " + oldContext.getName()
				+"(Reference:"+oldEReference.getName()+")"
				+ " to "+newContext.getName()
				+"(Reference:"+newEReference.getName());

		// add imports
		MOVE_Module.getImports().addAll(config.EPACKAGESSTACK);

		// create rule
		Common.createBasicRule(MOVE_Module, oldEReference, eClassifier, oldContext, newEReference, newContext, OperationType.MOVE_REFERENCE_COMBINATION);

		return MOVE_Module;
	}
}
