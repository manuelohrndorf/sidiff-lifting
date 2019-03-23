package org.sidiff.difference.technical.ui.widgets;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.Assert;
import org.sidiff.common.emf.input.InputModels;
import org.sidiff.common.extension.ui.labelprovider.ExtensionLabelProvider;
import org.sidiff.common.ui.widgets.AbstractListWidget;
import org.sidiff.common.ui.widgets.IWidgetValidation;
import org.sidiff.common.ui.widgets.IWidgetValidation.ValidationMessage.ValidationType;
import org.sidiff.difference.technical.ITechnicalDifferenceBuilder;
import org.sidiff.difference.technical.IncrementalTechnicalDifferenceBuilder;
import org.sidiff.difference.technical.api.settings.DifferenceSettings;
import org.sidiff.difference.technical.api.settings.DifferenceSettingsItem;

public class DifferenceBuilderWidget extends AbstractListWidget<ITechnicalDifferenceBuilder> implements IWidgetValidation {

	private InputModels inputModels;
	private DifferenceSettings settings;

	private List<ITechnicalDifferenceBuilder> builders;
	private ValidationMessage message;

	public DifferenceBuilderWidget(InputModels inputModels, DifferenceSettings settings) {
		super(ITechnicalDifferenceBuilder.class);
		setTitle("Technical Difference Builder");
		setLabelProvider(new ExtensionLabelProvider());
		this.inputModels = Objects.requireNonNull(inputModels, "InputModels must not be null");
		Assert.isLegal(inputModels.getResources().size() == 2, "InputModels must contain exactly two models");
		this.builders = ITechnicalDifferenceBuilder.MANAGER.getTechnicalDifferenceBuilders(
				inputModels.getResources().get(0), inputModels.getResources().get(1));
		this.settings = Objects.requireNonNull(settings, "Settings must not be null");
		this.settings.addSettingsChangedListener(item -> {
			if(item == DifferenceSettingsItem.TECH_BUILDER) {
				setSelection(getSettingsBuilders());
				getWidgetCallback().requestValidation();
			}
		});
		addModificationListener((oldMatchers,newMatchers) -> {
			settings.setTechBuilder(wrapBuilders(getSelection()));
		});
	}

	@Override
	public boolean validate() {
		if(getSelectableValues().isEmpty()) {
			message = new ValidationMessage(ValidationType.ERROR, "No technical difference builders are found.");
			return false;
		}

		List<ITechnicalDifferenceBuilder> selection = getSelection();
		boolean genericSelected = selection.stream().anyMatch(ITechnicalDifferenceBuilder::isGeneric);
		if(selection.size() > 1 && genericSelected) {
			message = new ValidationMessage(ValidationType.ERROR,
					"To use the generic technical difference builder, another builder must not be selected.");
			return false;
		}

		// check for each doc type if a builder is selected that can handle it
		Set<String> unsupportedDocTypes = new HashSet<>(inputModels.getDocumentTypes());
		if(genericSelected) {
			unsupportedDocTypes.clear();
		} else {
			for(ITechnicalDifferenceBuilder builder : selection) {
				if(builder.canHandle(inputModels.getDocumentTypes())) {
					unsupportedDocTypes.removeAll(builder.getDocumentTypes());
				}
			}
		}
		if(!unsupportedDocTypes.isEmpty()) {
			message = new ValidationMessage(ValidationType.WARNING, "Missing technical difference builder for: "
						+ unsupportedDocTypes.stream().collect(Collectors.joining(", ")));
			return false;
		}
		message = ValidationMessage.OK;
		return true;
	}

	@Override
	public ValidationMessage getValidationMessage() {
		return message;
	}

	public DifferenceSettings getSettings() {
		return settings;
	}

	protected static ITechnicalDifferenceBuilder wrapBuilders(List<ITechnicalDifferenceBuilder> selection) {
		switch(selection.size()) {
			case 0: return null;	
			case 1: return selection.get(0);
			default: return new IncrementalTechnicalDifferenceBuilder(selection);
		}
	}

	protected List<ITechnicalDifferenceBuilder> getSettingsBuilders() {
		if(settings.getTechBuilder() == null) {
			return Collections.emptyList();
		} else if(settings.getTechBuilder() instanceof IncrementalTechnicalDifferenceBuilder) {
			return ((IncrementalTechnicalDifferenceBuilder)settings.getTechBuilder()).getTechnicalDifferenceBuilders();
		} else {
			return Collections.singletonList(settings.getTechBuilder());
		}
	}

	@Override
	public List<ITechnicalDifferenceBuilder> getSelectableValues() {
		return Collections.unmodifiableList(builders);
	}

	@Override
	protected void hookInitSelection() {
		if(getSelection().isEmpty()) {
			setSelection(getSettingsBuilders());
		}
	}
}