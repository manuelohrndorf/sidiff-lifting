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
		
		// create mainUnit
		MainUnitGenerator mainUnitGenerator = new MainUnitGenerator(MOVE_Module, OperationType.MOVE);
		mainUnitGenerator.generate();

		// TODO need new way handling move generation using masks for normal, combinations, ups and downs, see below		
//		// if EClass has Masks, also create MOVES for them
//		for(Mask mask: ecm.getEClassifierInfo(eClassifier).getMasks()) {				
//			map.putAll(createMOVE_UsingMask(eRefA, mask, contextA, eRefB, contextB));			
//		}
		
		return MOVE_Module;
		
		
	}
	
//	private HashMap<Module,String> createMOVE_UsingMask(EReference eRefA, Mask mask, EClass contextA, EReference eRefB, EClass contextB) {
//
//		HashMap<Module,String> map = new HashMap<Module,String>();
//
//		String name = MOVE_prefix + mask.getName() + FROM+contextA.getName()+"_(" + eRefA.getName()+ ")"+ TO+contextB.getName()+"_("+eRefB.getName()+")"; 
//		LogUtil.log(LogEvent.NOTICE, "Generating Move : " + name);
//
//		// MOVE file name
//		String outputFileName = outputFolderPath + name+ EXECUTE_suffix;
//
//		Module MOVE_Module = henshinFactory.createModule();
//		MOVE_Module.setName(name);
//
//		MOVE_Module.setDescription("Moves "+ mask.getName() + " from "+contextA.getName()+"(Reference:" + eRefA.getName()+ ") to "+contextB.getName()+("(Reference:"+eRefB.getName()+")"));
//
//		// add imports
//		MOVE_Module.getImports().addAll(ePackagesStack);
//
//		// create rule
//		createBasicRule(MOVE_Module, eRefA, mask.getOriginalEClassifier(), contextA, eRefB, contextB);
//
//		// create Attribute, containing the masked type
//		Rule rule = HenshinModuleAnalysis.getAllRules(MOVE_Module).get(0);
//		NodePair np = HenshinRuleAnalysisUtilEx.getNodePair(rule, (EClass)mask.getOriginalEClassifier(), SEL);
//		HenshinRuleAnalysisUtilEx.createPreservedAttribute(np, mask.getEAttributeContainingFakeType(), "\""+mask.getTypeExtension().getName()+"\"", false);
//
//		// create all elements necessary for constraints
//		createElementsForConstraints_MOVE(MOVE_Module);
//
//		// create mainUnit and put in map
//		mainUnitCreation(MOVE_Module, mask.getOriginalEClassifier(), OperationType.MOVE);
//		map.put(MOVE_Module, outputFileName);
//
//		// create multiplicity preconditions, if any
//		if(multiplicityPreconditionsIntegrated) {
//			createIntegratedPreconditionsForMultiplicities(rule, OperationType.MOVE);
//		}
//		if(multiplicityPreconditionsSeparately) {
//			createInitialChecksForMultiplicities(MOVE_Module.getName(), contextA, mask.getOriginalEClassifier(), eRefA, contextB, eRefB, OperationType.MOVE);	
//			//TODO separate initial check for masked eclass
//		}
//
//		return map;
//	}
}
