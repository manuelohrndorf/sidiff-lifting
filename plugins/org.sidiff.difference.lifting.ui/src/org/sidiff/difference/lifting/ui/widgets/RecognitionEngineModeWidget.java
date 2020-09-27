package org.sidiff.difference.lifting.ui.widgets;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.sidiff.common.emf.settings.ISettingsChangedListener;
import org.sidiff.common.ui.widgets.AbstractRadioWidget;
import org.sidiff.common.ui.widgets.IWidgetDisposable;
import org.sidiff.difference.lifting.api.settings.*;

public class RecognitionEngineModeWidget extends AbstractRadioWidget<RecognitionEngineMode> implements IWidgetDisposable {

	private LiftingSettings settings;
	private Predicate<RecognitionEngineMode> filter = mode -> true;

	private final ISettingsChangedListener settingsChangeListener = item -> {
		if(item == LiftingSettingsItem.RECOGNITION_ENGINE_MODE) {
			setSelection(settings.getRecognitionEngineMode());
			getWidgetCallback().requestValidation();
		}
	};

	public RecognitionEngineModeWidget(LiftingSettings settings) {
		this.settings = Objects.requireNonNull(settings, "settings must not be null");
		setTitle("Recognition Engine Mode");
		settings.addSettingsChangedListener(settingsChangeListener);
		addModificationListener((oldValues, newValues) -> settings.setRecognitionEngineMode(newValues.isEmpty() ? null : newValues.get(0)));
	}

	@Override
	protected void hookInitButtons() {
		super.hookInitButtons();
		if(getSelection().isEmpty()) {
			setSelection(settings.getRecognitionEngineMode());
		}
	}

	public LiftingSettings getSettings() {
		return settings;
	}

	@Override
	public List<RecognitionEngineMode> getSelectableValues() {
		return Stream.of(RecognitionEngineMode.values()).filter(filter).collect(Collectors.toList());
	}

	/**
	 * Sets a filter predicate that determines which RecognitionEngineMode should be shown.
	 * @param filter the filter predicate
	 */
	public void setFilter(Predicate<RecognitionEngineMode> filter) {
		this.filter = Objects.requireNonNull(filter);
	}

	@Override
	public void dispose() {
		settings.removeSettingsChangedListener(settingsChangeListener);
	}
}