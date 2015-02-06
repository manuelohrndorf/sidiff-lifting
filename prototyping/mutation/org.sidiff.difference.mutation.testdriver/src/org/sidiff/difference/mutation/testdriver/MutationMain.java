package org.sidiff.difference.mutation.testdriver;

import java.util.LinkedList;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.osgi.framework.BundleContext;
import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.difference.mutation.Mutator;
import org.sidiff.difference.mutation.config.MutationConfig;
import org.sidiff.difference.mutation.selection.IntactSelection;
import org.sidiff.difference.rulebase.EditRule;
import org.silift.common.util.emf.EMFStorage;


/**
 * Main program of a controlled model mutation.
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
			
		Resource inputModel = EMFStorage.eLoad(EMFStorage.pathToUri(args[0])).eResource();	
		String docType = EMFModelAccess.getDocumentType(inputModel);
		
		// Edit Rules
		LinkedList<EditRule> ers = new LinkedList<EditRule>();
		EditRule er0 = EditRuleUtil.getEditRule(docType,"CreateRequireConstraint");
		EditRule er1 = EditRuleUtil.getEditRule(docType,"CREATE_ExcludeConstraint_IN_FeatureModel_(constraints)");
		EditRule er2 = EditRuleUtil.getEditRule(docType,"SET_Feature_Concrete");
		EditRule er3 = EditRuleUtil.getEditRule(docType,"SET_Feature_Optional");

		ers.add(er0);
	//	ers.add(er1);
	//	ers.add(er2);
	//	ers.add(er3);		
		
		// Context Selection
		IntactSelection<Match> cs = new IntactSelection<Match>(null, null, 10, false);
		//RandomSelection<Match> cs = new RandomSelection<EditRule>(null, null, 10, false);
		//SimilarityContextSelection cs = new SimilarityContextSelection(null, null, 10, false, true);
		
		// Operator Selection
		IntactSelection<EditRule> os = new IntactSelection<EditRule>(null, null, 100, false);
		//RandomSelection<EditRule> os = new RandomSelection<EditRule>(null, null, 100, false);
		//SimilarityOperatorSelection os = new SimilarityOperatorSelection(null, null, 100, false, true);
		
		// Order of Mutation
		int order = 2;
		
		// Validate mutants
		boolean validateMutants = false;
		
		MutationConfig mc = new MutationConfig("CRQ" + "O"+ order,inputModel, ers, os, cs, order, validateMutants);		
		
		Mutator mutator = new Mutator(mc);
		mutator.mutate(new NullProgressMonitor());
		
		
		
	}
}
