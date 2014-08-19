package org.sidiff.difference.matcher.emfcompare;

import java.util.Iterator;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.EMFCompare;
import org.eclipse.emf.compare.Match;
import org.eclipse.emf.compare.scope.DefaultComparisonScope;
import org.eclipse.emf.compare.scope.IComparisonScope;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.difference.matcher.IMatcher;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.difference.symmetric.SymmetricFactory;
import org.silift.common.util.emf.Scope;

/**
 * Concrete matcher that delegates to EMFCompare matching engine.
 * 
 * @author kehrer / reuling
 */
public class EMFCompareMatchingAdapter implements IMatcher {

	/**
	 * Initialize
	 */
	public EMFCompareMatchingAdapter() {
		super();
	}

	@Override
	public SymmetricDifference createMatching(Resource modelA, Resource modelB, Scope scope,
			boolean calculateReliability) {
		// TODO(DR): consider scope for EMF compare options (compare resource or compare resource set)
		
		// Specify options
		/*
		 * Map<String, Object> matchOptions = new HashMap<String, Object>();
		 * matchOptions.put(MatchOptions.OPTION_IGNORE_ID, false);
		 * matchOptions.put(MatchOptions.OPTION_IGNORE_XMI_ID, false);
		 * matchOptions.put(MatchOptions.OPTION_SEARCH_WINDOW, 100);
		 */

		assert (!modelA.getContents().isEmpty()) : "modelA is empty!";
		assert (!modelB.getContents().isEmpty()) : "modelB is empty!";

		EList<Match> matches = null;
		IComparisonScope emfScope = null;
		
		// Just two way matching, two cases of scopes
		if(scope == Scope.RESOURCE_SET){
			emfScope = new DefaultComparisonScope(modelA.getResourceSet(), modelB.getResourceSet(), null);
		}
		else{
			emfScope = new DefaultComparisonScope(modelA, modelB, null);
		}

		// Compare and get all matches
		Comparison comparison = EMFCompare.builder().build().compare(emfScope);
		matches = comparison.getMatches();

		SymmetricDifference difference = SymmetricFactory.eINSTANCE.createSymmetricDifference();
		difference.setUriModelA(modelA.getURI().toString());
		difference.setUriModelB(modelB.getURI().toString());
		populateDifference(difference, matches);

		return difference;

	}

	private void populateDifference(SymmetricDifference difference, EList<Match> matches) {
		for (Iterator<Match> it = matches.iterator(); it.hasNext();) {
			Match match = it.next();
			populateDifference(difference, match);
		}
	}

	private void populateDifference(SymmetricDifference difference, Match match) {

		if (match.getLeft() != null && match.getRight() != null) {
			difference.addCorrespondence(match.getLeft(), match.getRight());			
		}
		for (Match subMatch : match.getSubmatches()) {
			populateDifference(difference, subMatch);
		}
	}

	@Override
	public String getName() {
		return "EMFCompare Generic Matcher";
	}

	@Override
	public String getKey() {
		return "EMFCompare";
	}

	@Override
	public boolean canHandle(Resource modelA, Resource modelB) {
		// EMFCompare can handle every model type, at least with the generic
		// matching engine.
		return true;
	}

	@Override
	public boolean isResourceSetCapable() {
		// EMFC is resourceSetCapable if called properly
		return true;
	}

	@Override
	public boolean canComputeReliability() {
		return false;
	}
}
