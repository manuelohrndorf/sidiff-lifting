package org.sidiff.difference.technical.ui.widgets;

import java.util.Collections;
import java.util.List;

import org.sidiff.common.settings.BaseSettings;
import org.sidiff.common.settings.BaseSettingsItem;
import org.sidiff.common.settings.ISettingsChangedListener;
import org.sidiff.common.ui.widgets.AbstractCheckboxWidget;
import org.sidiff.common.ui.widgets.IWidgetDisposable;

public class ValidateModelsWidget extends AbstractCheckboxWidget<String> implements IWidgetDisposable, ISettingsChangedListener {

	private static final String VALIDATE = "Validate Models";

	private BaseSettings settings;

	public ValidateModelsWidget() {
		setTitle("Input Model Validation");
		addModificationListener((oldValues, newValues) -> settings.setValidate(newValues.contains(VALIDATE)));
	}

	@Override
	public List<String> getSelectableValues() {
		return Collections.singletonList(VALIDATE);
	}

	@Override
	public void settingsChanged(Enum<?> item) {
		if(item == BaseSettingsItem.VALIDATE) {
			setSelection(settings.isValidate() ? Collections.singletonList(VALIDATE) : Collections.emptyList());
		}
	}

	public BaseSettings getSettings() {
		return settings;
	}

	public void setSettings(BaseSettings settings) {
		if(this.settings != null) {
			this.settings.removeSettingsChangedListener(this);
		}
		this.settings = settings;
		this.settings.addSettingsChangedListener(this);
		settingsChanged(BaseSettingsItem.VALIDATE);
	}

	@Override
	public void dispose() {
		if(settings != null) {
			settings.removeSettingsChangedListener(this);
			settings = null;
		}
	}
}
