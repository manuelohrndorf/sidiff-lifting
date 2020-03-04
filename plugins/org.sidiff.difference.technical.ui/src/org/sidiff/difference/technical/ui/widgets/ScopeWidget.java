package org.sidiff.difference.technical.ui.widgets;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.sidiff.common.emf.access.Scope;
import org.sidiff.common.emf.settings.BaseSettingsItem;
import org.sidiff.common.emf.settings.ISettingsChangedListener;
import org.sidiff.common.emf.settings.ISettingsItem;
import org.sidiff.common.emf.ui.labelprovider.ScopeLabelProvider;
import org.sidiff.common.ui.widgets.AbstractRadioWidget;
import org.sidiff.common.ui.widgets.IWidgetValidation.ValidationMessage.ValidationType;
import org.sidiff.matching.api.settings.MatchingSettings;

public class ScopeWidget extends AbstractRadioWidget<Scope> implements ISettingsChangedListener {

	private MatchingSettings settings;

	public ScopeWidget() {
		setTitle("Scope");
		setLabelProvider(new ScopeLabelProvider());
		addModificationListener((oldValues, newValues) -> {
			if(!getSelection().isEmpty()) {
				settings.setScope(getSelection().get(0));
			} else {
				settings.setScope(Scope.RESOURCE);
			}
		});
	}

	@Override
	public List<Scope> getSelectableValues() {
		return Arrays.asList(Scope.values());
	}

	@Override
	protected ValidationMessage doValidate() {
		if (settings.getScope() != Scope.RESOURCE_SET
				|| settings.getMatcher() == null
				|| settings.getMatcher().isResourceSetCapable()) {
			return ValidationMessage.OK;
		}
		return new ValidationMessage(ValidationType.ERROR, "Selected matching engine " + settings.getMatcher().getName()
				+ " does not support ResourceSet scope, select another matching engine!");
	}

	@Override
	public void settingsChanged(ISettingsItem item) {
		if (item == BaseSettingsItem.SCOPE) {
			if(settings.getScope() == null) {
				setSelection(Collections.emptyList());
			} else {
				setSelection(Collections.singletonList(settings.getScope()));
			}
			getWidgetCallback().requestValidation();
		}
	}

	public MatchingSettings getSettings() {
		return settings;
	}

	public void setSettings(MatchingSettings settings) {
		if(this.settings != null) {
			this.settings.removeSettingsChangedListener(this);
		}
		this.settings = settings;
		this.settings.addSettingsChangedListener(this);
		settingsChanged(BaseSettingsItem.SCOPE);
	}
}