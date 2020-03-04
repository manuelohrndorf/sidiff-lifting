package org.sidiff.difference.lifting.ui.widgets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.sidiff.common.emf.input.InputModels;
import org.sidiff.common.emf.settings.ISettingsChangedListener;
import org.sidiff.common.extension.ui.labelprovider.ExtensionLabelProvider;
import org.sidiff.common.ui.widgets.AbstractRadioWidget;
import org.sidiff.common.ui.widgets.IWidgetDisposable;
import org.sidiff.difference.lifting.api.settings.LiftingSettings;
import org.sidiff.difference.lifting.api.settings.LiftingSettingsItem;
import org.sidiff.difference.lifting.recognitionrulesorter.IRecognitionRuleSorter;

public class RecognitionRuleSorterWidget extends AbstractRadioWidget<IRecognitionRuleSorter> implements IWidgetDisposable {

	private InputModels inputModels;
	private LiftingSettings settings;

	private final ISettingsChangedListener settingsChangedListener = item -> {
		if(item == LiftingSettingsItem.RECOGNITION_RULE_SORTER) {
			setSelection(Collections.singletonList(settings.getRrSorter()));
			getWidgetCallback().requestValidation();
		}
	};

	public RecognitionRuleSorterWidget(InputModels inputModels, LiftingSettings settings) {
		this.inputModels = Objects.requireNonNull(inputModels, "inputModels must not be null");
		this.settings = Objects.requireNonNull(settings, "settings must not be null");
		setTitle("Recognition Rule Sorter");
		setLabelProvider(new ExtensionLabelProvider());
		settings.addSettingsChangedListener(settingsChangedListener);
		addModificationListener((oldValues, newValues) -> {
			if(!newValues.isEmpty()) {
				settings.setRrSorter(newValues.get(0));
			}
		});
	}

	@Override
	protected void hookInitButtons() {
		super.hookInitButtons();
		if(getSelection().isEmpty()) {
			setSelection(Collections.singletonList(settings.getRrSorter()));
		}
	}

	public LiftingSettings getSettings() {
		return settings;
	}

	@Override
	public List<IRecognitionRuleSorter> getSelectableValues() {
		return new ArrayList<>(IRecognitionRuleSorter.MANAGER.getExtensions(inputModels.getDocumentTypes(), true));
	}

	@Override
	public void dispose() {
		settings.removeSettingsChangedListener(settingsChangedListener);
	}
}