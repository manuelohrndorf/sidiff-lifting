package org.sidiff.matcher;

import java.util.Map;

import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.emf.access.Scope;
import org.sidiff.difference.symmetric.SymmetricDifference;

public interface IMatcher {

	/**
	 * The shared extension point id.
	 */
	public static final String extensionPointID = "org.sidiff.matcher.matcher_extension";

	/**
	 * Add matches (i.e. correspondences) between two models A and B. Here, we
	 * assume that the symmetric difference which contains the correspondences
	 * already exists. This way, this matcher can incrementally add
	 * correspondences to the set of already existing correspondences.
	 * 
	 * 
	 * @param matching
	 *            the matching between model A and model B (difference that
	 *            contains only correspondences).
	 * @param scope
	 *            RESOURCE or RESOURCE_SET
	 */
	public void addMatches(Resource modelA, Resource modelB, SymmetricDifference matching, Scope scope,
			boolean calculateReliability);

	/**
	 * Calculates a matching between model A and model B. That means a
	 * Correspondence for each preserved object between model A and model B.
	 * 
	 * @param modelA
	 *            the original version of the model to compare to.
	 * @param modelB
	 *            the changed version of the model to compare to.
	 * @param scope
	 *            RESOURCE or RESOURCE_SET
	 * @return the matching between model A and model B (difference that
	 *         contains only correspondences).
	 */
	public SymmetricDifference createMatching(Resource modelA, Resource modelB, Scope scope,
			boolean calculateReliability);

	/**
	 * Returns the description name of the matcher.
	 * 
	 * @return the matcher name.
	 */
	public String getName();

	/**
	 * Returns the short name (used as a key) of the matcher.
	 * 
	 * @return the matcher short name (used as key).
	 */
	public String getKey();

	/**
	 * Returns whether this matcher can handle (i.e. match) models of the given
	 * documentType.
	 * 
	 * @param modelA
	 * @param modelB
	 * @return
	 */
	public boolean canHandle(Resource modelA, Resource modelB);

	/**
	 * @return the document type the matcher is primarily implemented for.
	 */
	public String getDocumentType();

	/**
	 * Returns whether this matcher is principally capable of comparing two
	 * models in comparsionMode {@link Scope#RESOURCE_SET}.
	 * 
	 * @return
	 */
	public boolean isResourceSetCapable();

	/**
	 * Returns whether this matcher supports the computation of reliability.
	 * 
	 * @return true if realibiliy is supported
	 */
	public boolean canComputeReliability();
	
	/**
	 * Returns the configuration options of the current matcher
	 * 
	 * @return 
	 */
	public Map<String, Object> getConfigurationOptions();

}
