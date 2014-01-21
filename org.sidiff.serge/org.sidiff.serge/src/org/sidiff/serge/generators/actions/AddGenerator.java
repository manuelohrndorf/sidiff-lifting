package org.sidiff.serge.generators.actions;


import org.eclipse.emf.ecore.EClass;
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

public class AddGenerator {

	/**
	 * Outgoing non-containment EReference of the contextClass.
	 */
	private EReference outReference;

	/**
	 * Context class whose outgoing EReference shall point to a further eClassifier.
	 */
	private EClassifier contextClassifier;
	
	/**
	 * The eClassifier which should be added.
	 */
	private EClassifier eClassifier;
	
	/**
	 * The configuration.
	 */
	private static Configuration config = Configuration.getInstance();

	/**
	 * The Constructor
	 * @param outReference
	 * @param contextClassifier
	 * @param eClassifier
	 */
	public AddGenerator(EReference outReference, EClass contextClassifier, EClassifier eClassifier) {
		super();
		this.outReference = outReference;
		this.contextClassifier = contextClassifier;
		this.eClassifier = eClassifier;
	}
	
	public Module generate() throws OperationTypeNotImplementedException{
		
		String name = GlobalConstants.ADD_prefix + eClassifier.getName() + "_(" + outReference.getName()+")"+GlobalConstants.TGT+contextClassifier.getName(); 
		LogUtil.log(LogEvent.NOTICE, "Generating ADD : " + name);

		Module ADD_Module = HenshinFactory.eINSTANCE.createModule();
		ADD_Module.setName(name);

		ADD_Module.setDescription("Adds to "+eClassifier.getName() +"'s reference "+ outReference.getName()
				+ " the target "+ contextClassifier.getName());

		// add imports
		ADD_Module.getImports().addAll(config.EPACKAGESSTACK);

		// create rule
		Common.createBasicRule(ADD_Module, outReference, eClassifier, contextClassifier, null, null, OperationType.ADD);

		return ADD_Module;
	}
}
