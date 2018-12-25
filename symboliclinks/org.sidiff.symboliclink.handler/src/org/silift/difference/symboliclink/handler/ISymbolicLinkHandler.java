package org.silift.difference.symboliclink.handler;

import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.extension.ExtensionManager;
import org.sidiff.common.extension.IExtension;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.silift.difference.symboliclink.SymbolicLinkObject;
import org.silift.difference.symboliclink.SymbolicLinks;

/**
 * A SymbolicLinkHandler is responsible for calculating and resolving
 * symbolic links.
 * 
 * @author cpietsch
 *
 */
public interface ISymbolicLinkHandler extends IExtension {

	Description<ISymbolicLinkHandler> DESCRIPTION = Description.of(ISymbolicLinkHandler.class,
			"org.sidiff.symboliclink.handler.symbolic_link_handler_extension", "symbolic_link", "symbolic_link_handler");

	ExtensionManager<ISymbolicLinkHandler> MANAGER = new ExtensionManager<>(DESCRIPTION);

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
	 * 					the container object of a {@link SymbolicLinkObject}
	 * @param targetModel
	 * 					the {@link Resource} containing the target model with objects the symbolic links will be solved to
	 * @param calculateReliability
	 * 					flag to determine if a reliability shall be calculated
	 * @return a map, which assigns an object to the appropriate {@link SymbolicLinkObject}
	 */
	public Map<SymbolicLinkObject, EObject> resolveSymbolicLinkObjects(SymbolicLinks symbolicLinks, Resource targetModel, boolean calculateReliability);

	/**
	 * Determines if the handler is able to compute reliabilities
	 * 
	 * @return <code>true</code> if the symbolic-link-handler is able to compute reliabilities, otherwise <code>false</code>
	 */
	public boolean canComputeReliability();
}
