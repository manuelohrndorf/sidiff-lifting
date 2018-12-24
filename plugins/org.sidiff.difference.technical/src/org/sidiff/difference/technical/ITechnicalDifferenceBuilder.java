package org.sidiff.difference.technical;

import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.emf.access.Scope;
import org.sidiff.common.extension.ITypedExtension;
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
public interface ITechnicalDifferenceBuilder extends ITypedExtension {

	Description<ITechnicalDifferenceBuilder> DESCRIPTION = Description.of(ITechnicalDifferenceBuilder.class,
			"org.sidiff.difference.technical.technical_difference_builder_extension", "technical", "difference_builder");

	TechnicalDifferenceBuilderManager MANAGER = new TechnicalDifferenceBuilderManager(DESCRIPTION);

	/**
	 * Derives the technical difference. A default implementation is given by
	 * the abstract class {@link AbstractTechnicalDifferenceBuilder}
	 * 
	 * @param difference
	 * @return {@link SymmetricDifference}
	 */
	SymmetricDifference deriveTechDiff(SymmetricDifference difference, Scope scope);

	/**
	 * Returns whether this technical difference builder can handle the given models
	 * 
	 * @param modelA
	 * @param modelB
	 * @return
	 */
	boolean canHandleModels(Resource modelA, Resource modelB);
}
