package org.sidiff.difference.lifting.ui.widgets;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import org.sidiff.common.emf.input.InputModels;
import org.sidiff.common.emf.settings.ISettingsChangedListener;
import org.sidiff.common.ui.widgets.AbstractCheckboxWidget;
import org.sidiff.common.ui.widgets.IWidgetDisposable;
import org.sidiff.common.ui.widgets.IWidgetValidation.ValidationMessage.ValidationType;
import org.sidiff.difference.lifting.api.settings.LiftingSettings;
import org.sidiff.difference.lifting.api.settings.LiftingSettingsItem;
import org.sidiff.difference.lifting.api.settings.RecognitionEngineMode;
import org.sidiff.difference.lifting.api.util.PipelineUtils;
import org.sidiff.difference.lifting.ui.labelprovider.RulebaseLabelProvider;
import org.sidiff.difference.rulebase.view.ILiftingRuleBase;

public class RulebaseWidget extends AbstractCheckboxWidget<ILiftingRuleBase> implements IWidgetDisposable {

	private LiftingSettings settings;

	private List<ILiftingRuleBase> rulebases;

	private final ISettingsChangedListener settingsChangedListener = item -> {
		if(item == LiftingSettingsItem.RECOGNITION_ENGINE_MODE) {
			setEnabled(isLiftingEnabled());
		} else if(item == LiftingSettingsItem.RULEBASES) {
			setSelection(new ArrayList<>(settings.getRuleBases()));
			getWidgetCallback().requestValidation();
		}
	};

	public RulebaseWidget(InputModels inputModels, LiftingSettings settings) {
		this.settings = Objects.requireNonNull(settings, "settings must not be null");
		this.rulebases = new ArrayList<>(PipelineUtils.getAvailableRulebases(inputModels.getDocumentTypes()));
		setTitle("Rulebases");
		setLabelProvider(new RulebaseLabelProvider());
		settings.addSettingsChangedListener(settingsChangedListener);
		addModificationListener((oldValues, newValues) -> {
			settings.setRuleBases(new HashSet<>(newValues));
		});
	}

	@Override
	protected void hookInitButtons() {
		super.hookInitButtons();
		if(getSelection().isEmpty()) {
			setSelection(new ArrayList<>(settings.getRuleBases()));
		}
	}

	@Override
	protected ValidationMessage doValidate() {
		if (!isLiftingEnabled() || !getSelection().isEmpty()) {
			return ValidationMessage.OK;
		}
		return new ValidationMessage(ValidationType.ERROR, "Please select at least one rulebase!");
	}

	private boolean isLiftingEnabled() {
		return settings != null && settings.getRecognitionEngineMode() != RecognitionEngineMode.NO_LIFTING;
	}

	public LiftingSettings getSettings() {
		return settings;
	}

	@Override
	public List<ILiftingRuleBase> getSelectableValues() {
		return rulebases;
	}
	
	@Override
	public void dispose() {
		settings.removeSettingsChangedListener(settingsChangedListener);
	}
}