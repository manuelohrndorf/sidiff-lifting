package org.silift.difference.symboliclink.handler;

import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.silift.difference.symboliclink.SymbolicLink;
import org.silift.difference.symboliclink.SymbolicLinks;

/**
 * A SymbolicLinkHandler is responsible for calculating and resolving
 * symbolic links.
 * 
 * @author cpietsch
 *
 */
public interface ISymbolicLinkHandler {
	
	/**
	 * The shared extension point id.
	 */
	public static final String extensionPointID = "org.sidiff.common.symboliclink.handler.symbolic_link_handler_extension";

	/**
	 * Returns the name of the symbolic-link-handler.
	 * 
	 * @return the handler name
	 */
	public String getName();
	
	/**
	 * Returns the short name (used as a key) of the symbolic-link-handler.
	 * 
	 * @return the handler short name (used as key)
	 */
	public String getKey();
	
	/**
	 * Calculates a {@link SymbolicLink} for each referenced object located in the origin or target model
	 * of the {@link AsymmetricDifference}.
	 * 
	 * @param asymmetricDifference
	 * 					an {@link AsymmetricDifference}
	 * @param calculateReliability
	 * 					flag to determine if a reliability shall be calculated
	 * @return a collection with a {@link SymbolicLinks} object for each model
	 */
	public Collection<SymbolicLinks> generateSymbolicLinks(AsymmetricDifference asymmetricDifference, boolean calculateReliability);
	
	/**
	 * Resolves the symbolic links.
	 * 
	 * @param symbolicLinks
	 * 					the container object of a {@link SymbolicLink}
	 * @param targetModel
	 * 					the {@link Resource} containing the target model with objects the symbolic links will be solved to
	 * @param calculateReliability
	 * 					flag to determine if a reliability shall be calculated
	 * @return a map, which assigns an object to the appropriate {@link SymbolicLink}
	 */
	public Map<SymbolicLink, EObject> resolveSymbolicLinks(SymbolicLinks symbolicLinks, Resource targetModel, boolean calculateReliability);

	/**
	 * Determines if the handler is able to compute reliabilities
	 * 
	 * @return <code>true</code> if the symbolic-link-handler is able to compute reliabilities, otherwise <code>false</code>
	 */
	public boolean canComputeReliability();
}
