package org.sidiff.serge.generators.actions;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Module;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.serge.core.Common;
import org.sidiff.serge.core.Configuration;
import org.sidiff.serge.core.Configuration.OperationType;
import org.sidiff.serge.core.GlobalConstants;
import org.sidiff.serge.exceptions.OperationTypeNotImplementedException;

public class MoveGenerator {
	
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
	public MoveGenerator(EClassifier eClassifier, EReference eReference,
			EClassifier oldContext, EClassifier newContext) {

		this.eClassifier = eClassifier;
		this.eReference = eReference;
		this.oldContext = oldContext;
		this.newContext = newContext;
	}
	
	public Module generate() throws OperationTypeNotImplementedException {
		
		
		String name = GlobalConstants.MOVE_prefix + eClassifier.getName()
						+ GlobalConstants.FROM + oldContext.getName()
						+"_("+eReference.getName()+")"
						+ GlobalConstants.TO+newContext.getName()
						+"_("+eReference.getName()+")"; 
		
		LogUtil.log(LogEvent.NOTICE, "Generating Move : " + name);

		Module MOVE_Module = HenshinFactory.eINSTANCE.createModule();
		MOVE_Module.setName(name);

		MOVE_Module.setDescription("MOVEs "+eClassifier.getName()
									+ " from " + oldContext.getName()
									+"(Reference:"+eReference.getName()+")"
									+ " to "+newContext.getName()
									+"(Reference:"+eReference.getName());

		// add imports
		MOVE_Module.getImports().addAll(config.EPACKAGESSTACK);
		
		// create rule
		Common.createBasicRule(MOVE_Module, eReference, eClassifier, oldContext, eReference, newContext, OperationType.MOVE);
		
		return MOVE_Module;
	}
}
