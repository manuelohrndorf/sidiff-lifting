package org.sidiff.difference.technical;

import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.emf.access.Scope;
import org.sidiff.difference.symmetric.SymmetricDifference;

/**
 * This interface belongs to the 'org.sidiff.difference.technical' extension
 * point. This extension point is used to add a new technical difference builder
 * to the lifting engine. A plug-in that adds this extension point has to
 * implement this interface.
 * 
 * A generic implementation is given by
 * {@link GenericTechnicalDifferenceBuilder}
 */
public interface ITechnicalDifferenceBuilder {
	/**
	 * The shared extension point id.
	 */
	public static final String extensionPointID = "org.sidiff.difference.technical.technical_difference_builder_extension";

	/**
	 * Returns the description name of the technical difference builder.
	 * 
	 * @return the technical difference builder name.
	 */
	public String getName();

	/**
	 * Derives the technical difference. A default implementation is given by
	 * the abstract class {@link TechnicalDifferenceBuilder}
	 * 
	 * @param difference
	 * @return {@link SymmetricDifference}
	 */
	public SymmetricDifference deriveTechDiff(SymmetricDifference difference, Scope scope);

	/**
	 * @return the document type the technical difference builder is primarily
	 *         implemented for.
	 */
	public String getDocumentType();

	/**
	 * Returns whether this technical difference builder can handle models of
	 * the given documentType.
	 * 
	 * @param modelA
	 * @param modelB
	 * @return
	 */
	public boolean canHandle(Resource modelA, Resource modelB);
	
	/**
	 * Returns whether this technical difference builder can handle models of
	 * the given documentType.
	 * 
	 * @param docType
	 * @return
	 */
	public boolean canHandle(String docType);

}
