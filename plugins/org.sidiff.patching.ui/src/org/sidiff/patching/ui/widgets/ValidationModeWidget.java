package org.sidiff.patching.ui.widgets;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.sidiff.common.settings.ISettingsChangedListener;
import org.sidiff.common.ui.widgets.AbstractRadioWidget;
import org.sidiff.patching.api.settings.PatchingSettings;
import org.sidiff.patching.api.settings.PatchingSettingsItem;
import org.sidiff.patching.validation.ValidationMode;

public class ValidationModeWidget extends AbstractRadioWidget<ValidationMode> implements ISettingsChangedListener {

	private PatchingSettings settings;
	
	private static ILabelProvider labelProvider = new ColumnLabelProvider() {
		@Override
		public String getText(Object element) {
			switch((ValidationMode)element) {
				case NO_VALIDATION: return "No Validation";
				case MODEL_VALIDATION: return "Model Validation";
				case ITERATIVE_VALIDATION: return "Iterative Validation";
			};
			throw new AssertionError();
		}
	};

	public ValidationModeWidget() {
		setTitle("Validation mode");
		setLabelProvider(labelProvider);
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
	public void settingsChanged(Enum<?> item) {
		if(item == PatchingSettingsItem.VALIDATION_MODE) {
			if(settings.getScope() == null) {
				setSelection(Collections.emptyList());
			} else {
				setSelection(Collections.singletonList(settings.getValidationMode()));
			}
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
