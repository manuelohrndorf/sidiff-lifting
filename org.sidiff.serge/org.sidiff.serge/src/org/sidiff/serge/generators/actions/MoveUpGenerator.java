package org.sidiff.serge.generators.actions;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Module;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.serge.configuration.Configuration;
import org.sidiff.serge.configuration.GlobalConstants;
import org.sidiff.serge.configuration.Configuration.OperationType;
import org.sidiff.serge.core.ModuleInternalsApplicator;
import org.sidiff.serge.exceptions.OperationTypeNotImplementedException;

public class MoveUpGenerator {
	
	/**
	 * The eClassifier to be moved.
	 */
	private EClassifier eClassifier;
	
	/**
	 * The EReference that that points from context classifiers to the
	 * eClassifier
	 */
	private EReference 	eReference;
	
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
	 * @param eReference
	 * @param oldContext
	 * @param newContext
	 */
	public MoveUpGenerator(EClassifier eClassifier, EReference eReference,
			EClassifier oldContext, EClassifier newContext) {

		this.eClassifier = eClassifier;
		this.eReference = eReference;
		this.oldContext = oldContext;
		this.newContext = newContext;
	}
	
	public Module generate() throws OperationTypeNotImplementedException {
		
		String name = GlobalConstants.MOVE_UP_prefix + eClassifier.getName()
				+ GlobalConstants.FROM + oldContext.getName()
				+"_("+eReference.getName()+")"
				+ GlobalConstants.TO+newContext.getName()
				+"_("+eReference.getName()+")"; 

		LogUtil.log(LogEvent.NOTICE, "Generating Move Up: " + name);
		
		Module MOVE_UP_Module = HenshinFactory.eINSTANCE.createModule();
		MOVE_UP_Module.setName(name);
		
		MOVE_UP_Module.setDescription("MOVE UPWARDs "+eClassifier.getName()
									+ " from " + oldContext.getName()
									+"(Reference:"+eReference.getName()+")"
									+ " to "+newContext.getName()
									+"(Reference:"+eReference.getName());
		
		// add imports
		MOVE_UP_Module.getImports().addAll(config.EPACKAGESSTACK);
		
		// create rule
		ModuleInternalsApplicator.createBasicRule(MOVE_UP_Module, eReference, eClassifier, oldContext, eReference, newContext, OperationType.MOVE_DOWN);
		
		return MOVE_UP_Module;
	}
	
}
