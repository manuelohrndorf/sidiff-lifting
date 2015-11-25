package org.sidiff.merging.twoway.facade;

import static org.sidiff.difference.asymmetric.util.AsymmetricDifferenceUtil.deriveAsymmetricDifference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.sidiff.common.emf.EMFValidate;
import org.sidiff.common.emf.exceptions.InvalidModelException;
import org.sidiff.common.emf.exceptions.NoCorrespondencesException;
import org.sidiff.correspondences.ICorrespondences;
import org.sidiff.correspondences.matchingmodel.MatchingModelCorrespondences;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.AsymmetricFactory;
import org.sidiff.difference.asymmetric.dependencies.real.DependencyAnalyzer;
import org.sidiff.difference.asymmetric.facade.util.Difference;
import org.sidiff.difference.asymmetric.facade.util.RuleBaseFilter;
import org.sidiff.difference.asymmetric.paramretrieval.ParameterMapper;
import org.sidiff.difference.asymmetric.paramretrieval.ParameterRetriever;
import org.sidiff.difference.lifting.facade.util.PipelineUtils;
import org.sidiff.difference.lifting.postprocessing.PostProcessor;
import org.sidiff.difference.lifting.recognitionengine.ruleapplication.RecognitionEngine;
import org.sidiff.difference.rulebase.extension.IRuleBase;
import org.sidiff.difference.symmetric.Change;
import org.sidiff.difference.symmetric.RemoveObject;
import org.sidiff.difference.symmetric.RemoveReference;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.difference.symmetric.SymmetricFactory;
import org.sidiff.difference.technical.ITechnicalDifferenceBuilder;
import org.sidiff.difference.technical.MergeImports;
import org.sidiff.matcher.IMatcher;
import org.sidiff.matching.model.Matching;

/**
 * 
 * @author cpietsch
 *
 */
public class TwoWayMergingFacade {

	/**
	 * 
	 * @param modelA
	 * @param modelB
	 * @param settings
	 * @return
	 * @throws InvalidModelException
	 * @throws NoCorrespondencesException 
	 */
	public static Difference liftMeUp(Resource modelA, Resource modelB, TwoWayMergingSettings settings) throws InvalidModelException, NoCorrespondencesException{
		
		// Be sure proxy resolution is done
		EcoreUtil.resolveAll(modelA.getResourceSet());
		EcoreUtil.resolveAll(modelB.getResourceSet());

		// Validate models
		if (settings.isValidate()){
			EMFValidate.validateObject(modelA.getContents().get(0), modelB.getContents().get(0));
		}

		// Create matching
		IMatcher matcher = settings.getMatcher();
		matcher.startMatching(Arrays.asList(modelA, modelB), settings.getScope());
		
		// Get Matching
		// In SiLift we assume support of @see{MatchingModelCorrespondences}
		ICorrespondences correspondences = matcher.getCorrespondencesService();
		Matching matching = ((MatchingModelCorrespondences)correspondences).getMatching();	

		if (!(matching.getCorrespondences().size() != 0)) {
			throw new NoCorrespondencesException();
		}

		//Contain Matching in Difference
		SymmetricDifference symmetricDiff = SymmetricFactory.eINSTANCE.createSymmetricDifference();
		symmetricDiff.setMatching(matching);

		// Create empty asymmetric difference
		AsymmetricDifference asymmetricDiff = AsymmetricFactory.eINSTANCE.createAsymmetricDifference();
		asymmetricDiff.setSymmetricDifference(symmetricDiff);

		// Merge Imports
		MergeImports importMerger = new MergeImports(symmetricDiff, asymmetricDiff, settings.getScope(), true);
		importMerger.merge();

		// Derive technical difference
		ITechnicalDifferenceBuilder diffBuilder = settings.getTechBuilder();
		symmetricDiff = diffBuilder.deriveTechDiff(symmetricDiff, settings.getScope());

		// Remove changes
		List<Change> changes = new ArrayList<Change>();
		for(Change change : symmetricDiff.getChanges()){
			if(change instanceof RemoveObject || change instanceof RemoveReference){
				changes.add(change);
			}
		}
		symmetricDiff.getChanges().removeAll(changes);
		
		// Lift difference
		assert (matching.getCorrespondences().size() > 0) : "Empty Correspondence!";

		// Filter out all rules with derived references

		RuleBaseFilter filter = new RuleBaseFilter(settings.getRuleBases());
		filter.filterDerivedReferences();


		// Start recognition engine
		RecognitionEngine engine = new RecognitionEngine(symmetricDiff, importMerger.getImports(), settings);
		engine.execute();

		// Revert all filters
		filter.rollback();

		// Postprocess
		PostProcessor postProcessor = new PostProcessor(engine);
		postProcessor.postProcess();


		// Derive Asymmetric-Difference from Symmetric-Difference
		deriveAsymmetricDifference(symmetricDiff, asymmetricDiff);

		// Retrieve dependencies of operation invocations
		DependencyAnalyzer dependencyAnalyzer = new DependencyAnalyzer(engine, asymmetricDiff);
		dependencyAnalyzer.analyze();

		// Retrieve actual parameter values of operation invocations
		ParameterRetriever paramRetriever = new ParameterRetriever(engine, asymmetricDiff);
		paramRetriever.retrieveParameters();


		// Map OUT-Parameters to respective IN-Parameters
		ParameterMapper paramMapper = new ParameterMapper(asymmetricDiff);
		paramMapper.mapParameters();

		/*
		 * FINISHED
		 */

		// Unmerge Imports
		importMerger.unmerge();

		// Cleanup
//		cleanup(symmetricDiff, importMerger.getImports());

		// Create new difference container
		Difference fullDiff = new Difference(symmetricDiff, asymmetricDiff);

		return fullDiff;
	}
	
	public static Set<IRuleBase> getAvailableAtomicRuleBases(String docType){
		Set<IRuleBase> rulebases = new HashSet<IRuleBase>();
		for(IRuleBase rulebase : PipelineUtils.getAvailableRulebases(docType)){
			if(rulebase.getName().toLowerCase().contains("atomic")){
				rulebases.add(rulebase);
			}
		}
		return rulebases;
	}
}
