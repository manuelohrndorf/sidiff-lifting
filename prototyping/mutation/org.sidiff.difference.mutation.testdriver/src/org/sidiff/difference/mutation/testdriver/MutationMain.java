package org.sidiff.difference.mutation.testdriver;

import java.util.LinkedList;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.osgi.framework.BundleContext;
import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.difference.lifting.facade.LiftingFacade;
import org.sidiff.difference.mutation.Mutator;
import org.sidiff.difference.mutation.config.MutationConfig;
import org.sidiff.difference.mutation.selection.IntactSelection;
import org.sidiff.difference.mutation.selection.RandomSelection;
import org.sidiff.difference.rulebase.EditRule;


/**
 * Main propram of a controlled model mutation.
 * 
 * @author dreuling
 */
public class MutationMain implements IApplication {

	// **************** OSGi Lifecycle Management ****************
	
	@SuppressWarnings("unused")
	private static BundleContext context = null;
	
	public static void start(BundleContext context) {
		MutationMain.context = context;
	}

	public static void stop(BundleContext context) {
		MutationMain.context = null;
	}

	@Override
	public Object start(IApplicationContext context) throws Exception {
		main((String[]) context.getArguments().get(IApplicationContext.APPLICATION_ARGS));
		return IApplication.EXIT_OK;
	}

	@Override
	public void stop() {
	}
	
	// **************** Main ****************
	
	public static void main(String[] args) throws Exception {
			
		Resource inputModel = LiftingFacade.loadModel(args[0]);	
		String docType = EMFModelAccess.getDocumentType(inputModel);
		
		// Edit Rules
		LinkedList<EditRule> ers = new LinkedList<EditRule>();
		EditRule er = EditRuleUtil.getEditRule(docType,"CreateRequireConstraint");
		ers.add(er);
		
		
		// Context Selection
		RandomSelection<Match> cs = new RandomSelection<Match>(null, null, 10, false);
		
		// Operator Selection
		IntactSelection<EditRule> os = new IntactSelection<EditRule>(null, null, 100, false);
				
		// Order of Mutation
		int order = 2;
		
		MutationConfig mc = new MutationConfig("RQC",inputModel, ers, os, cs, order);		
		LogUtil.log(LogEvent.NOTICE, mc);
		
		Mutator mutator = new Mutator(mc);
		mutator.mutate();
		
		
		
	}
}
