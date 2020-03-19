package org.sidiff.patching.ui.widgets;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.sidiff.common.emf.settings.ISettingsChangedListener;
import org.sidiff.common.emf.settings.ISettingsItem;
import org.sidiff.common.ui.widgets.AbstractRadioWidget;
import org.sidiff.patching.api.settings.PatchingSettings;
import org.sidiff.patching.api.settings.PatchingSettingsItem;
import org.sidiff.patching.validation.ValidationMode;

public class ValidationModeWidget extends AbstractRadioWidget<ValidationMode> implements ISettingsChangedListener {

	private PatchingSettings settings;

	public ValidationModeWidget() {
		setTitle("Validation mode");
		addModificationListener((oldValues, newValues) -> {
			if(!getSelection().isEmpty()) {
				settings.setValidationMode(getSelection().get(0));
			} else {
				settings.setValidationMode(ValidationMode.NO_VALIDATION);
			}
		});
	}
	
	@Override
	public List<ValidationMode> getSelectableValues() {
		return Arrays.asList(ValidationMode.values());
	}

	@Override
	public void settingsChanged(ISettingsItem item) {
		if(item == PatchingSettingsItem.VALIDATION_MODE) {
			if(settings.getScope() == null) {
				setSelection(Collections.emptyList());
			} else {
				setSelection(Collections.singletonList(settings.getValidationMode()));
			}
			getWidgetCallback().requestValidation();
		}
	}

	public PatchingSettings getSettings() {
		return settings;
	}

	public void setSettings(PatchingSettings settings) {
		if(this.settings != null) {
			this.settings.removeSettingsChangedListener(this);
		}
		this.settings = settings;
		this.settings.addSettingsChangedListener(this);
		settingsChanged(PatchingSettingsItem.VALIDATION_MODE);
	}
}
