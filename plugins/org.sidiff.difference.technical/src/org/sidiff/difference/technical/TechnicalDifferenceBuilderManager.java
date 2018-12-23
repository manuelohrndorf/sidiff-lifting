package org.sidiff.difference.technical;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.extension.IExtension.Description;
import org.sidiff.common.extension.TypedExtensionManager;

public class TechnicalDifferenceBuilderManager extends TypedExtensionManager<ITechnicalDifferenceBuilder> {

	public TechnicalDifferenceBuilderManager(Description<? extends ITechnicalDifferenceBuilder> description) {
		super(description);
	}

	/**
	 * Returns the available technical difference builders for the documentTypes of the given models.
	 * If no convenient builder is found, a generic technical difference builder will be returned.
	 * @param modelA the first model
	 * @param modelB the second model
	 * @return suitable technical difference builders
	 */
	public List<ITechnicalDifferenceBuilder> getTechnicalDifferenceBuilders(Resource modelA, Resource modelB) {
		return getExtensions().stream()
			.filter(techBuilder -> techBuilder.canHandleModels(modelA, modelB))
			.collect(Collectors.toList());
	}

	@Override
	public Optional<ITechnicalDifferenceBuilder> getDefaultExtension() {
		return Optional.of(new GenericTechnicalDifferenceBuilder());
	}
}
