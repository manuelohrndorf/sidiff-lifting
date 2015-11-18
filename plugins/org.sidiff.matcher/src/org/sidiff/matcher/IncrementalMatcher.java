package org.sidiff.matcher;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.inject.Scope;

import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.difference.symmetric.SymmetricFactory;

/**
 * Incremental matcher which must be initialized with a list of IMatchers. When
 * the matching is computed, this incremental matcher invokes all sub-matchers
 * in the order which is given by the list of matchers. Before a sub-matcher is
 * invoked to incrementally add more correspondences, it is checked whether the
 * sub-matcher can really handle the given models A and B. Otherwise, the
 * sub-matcher is skipped.
 * 
 * Note that this matcher is not registered via an extension point and will not
 * be shown in the SiLift UI. If the user selects more than one @link{IMatcher} in the 
 * @link{MatchingEngineWidget} this class will be created dynamically and all chosen matchers will
 * be added.
 * 
 * 
 * @author kehrer
 */
public class IncrementalMatcher implements IMatcher {

	public static final String NAME = "Incremental Matcher";
	public static final String KEY = "Incremental";

	/**
	 * The list of matchers which will be executed in the order given by the
	 * List.
	 */
	private List<IMatcher> matchers;

	/**
	 * Constructs a new incremental matcher based on a list of sub-matchers.
	 * These sub-matchers will be executed in the order given by the list.
	 * 
	 * @param matchers
	 */
	public IncrementalMatcher(List<IMatcher> matchers) {
		super();
		this.matchers = matchers;
	}

	@Override
	public SymmetricDifference createMatching(Resource modelA, Resource modelB, Scope scope,
			boolean calculateReliability) {

		SymmetricDifference matching = SymmetricFactory.eINSTANCE.createSymmetricDifference();
		matching.setUriModelA(modelA.getURI().toString());
		matching.setUriModelB(modelB.getURI().toString());

		addMatches(modelA, modelB, matching, scope, calculateReliability);

		return matching;
	}

	@Override
	public void addMatches(Resource modelA, Resource modelB, SymmetricDifference matching, Scope scope,
			boolean calculateReliability) {

		LogUtil.log(LogEvent.NOTICE, "Starting incremental matching");

		for (int i = 0; i < matchers.size(); i++) {
			IMatcher nextMatcher = matchers.get(i);

			if (nextMatcher.canHandle(modelA, modelB)) {
				LogUtil.log(LogEvent.NOTICE, "Next matcher (" + i + "): " + nextMatcher.getName() + ": Add matches");
				nextMatcher.addMatches(modelA, modelB, matching, scope, calculateReliability);
			} else {
				LogUtil.log(LogEvent.NOTICE, "Next matcher (" + i + "): " + nextMatcher.getName()
						+ ": Skip because cannot handle resources " + modelA + " and " + modelB);
			}
		}

		LogUtil.log(LogEvent.NOTICE, "Finished incremental matching");
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public boolean canHandle(Resource modelA, Resource modelB) {
		// true if at least one of the matchers can handle modelA/modelB
		for (IMatcher matcher : matchers) {
			if (matcher.canHandle(modelA, modelB)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean isResourceSetCapable() {
		// true if at least one of the matchers is resource set capable
		for (IMatcher matcher : matchers) {
			if (matcher.isResourceSetCapable()) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean canComputeReliability() {
		// Full reliability computation only, if all matchers can compute
		// reliabilities
		for (IMatcher matcher : matchers) {
			if (!matcher.canComputeReliability()) {
				return false;
			}
		}

		return true;
	}

	@Override
	public String getDocumentType() {
		// depends on the sub-matchers
		return EMFModelAccess.GENERIC_DOCUMENT_TYPE;
	}
	
	public void setMatchers(List<IMatcher> matchers){
		this.matchers = matchers;
	}

	@Override
	public Map<String, Object> getConfigurationOptions() {
		// TODO Auto-generated method stub
		return Collections.emptyMap();
	}

}
