package org.sidiff.difference.lifting.ui.widgets;

import java.util.*;

import org.sidiff.common.emf.input.InputModels;
import org.sidiff.common.emf.settings.ISettingsChangedListener;
import org.sidiff.common.extension.ui.labelprovider.ExtensionLabelProvider;
import org.sidiff.common.ui.widgets.*;
import org.sidiff.difference.lifting.api.settings.LiftingSettings;
import org.sidiff.difference.lifting.api.settings.LiftingSettingsItem;
import org.sidiff.difference.lifting.recognitionrulesorter.IRecognitionRuleSorter;

public class RecognitionRuleSorterWidget extends AbstractListWidget<IRecognitionRuleSorter> implements IWidgetDisposable {

	private final List<IRecognitionRuleSorter> selectableValues;
	private LiftingSettings settings;

	private final ISettingsChangedListener settingsChangedListener = item -> {
		if(item == LiftingSettingsItem.RECOGNITION_RULE_SORTER) {
			setSelection(settings.getRrSorter());
			getWidgetCallback().requestValidation();
		}
	};

	public RecognitionRuleSorterWidget(InputModels inputModels, LiftingSettings settings) {
		super(IRecognitionRuleSorter.class);
		this.settings = Objects.requireNonNull(settings, "settings must not be null");
		selectableValues = new ArrayList<>(IRecognitionRuleSorter.MANAGER.getExtensions(inputModels.getDocumentTypes(), true));
		setTitle("Recognition Rule Sorter");
		setLowerUpperBounds(1, 1);
		setTableHeight(30);
		setLabelProvider(new ExtensionLabelProvider());
		settings.addSettingsChangedListener(settingsChangedListener);
		setEqualityDelegate(IRecognitionRuleSorter.MANAGER.getEquality());
		setSelection(settings.getRrSorter());
		addModificationListener((oldValues, newValues) -> settings.setRrSorter(newValues.isEmpty() ? null : newValues.get(0)));
	}

	@Override
	public List<IRecognitionRuleSorter> getSelectableValues() {
		return selectableValues;
	}

	@Override
	public void dispose() {
		settings.removeSettingsChangedListener(settingsChangedListener);
	}
}
