package org.sidiff.difference.technical.ui.widgets;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.sidiff.common.emf.access.Scope;
import org.sidiff.common.settings.BaseSettingsItem;
import org.sidiff.common.settings.ISettingsChangedListener;
import org.sidiff.common.ui.widgets.AbstractRadioWidget;
import org.sidiff.common.ui.widgets.IWidgetValidation;
import org.sidiff.common.ui.widgets.IWidgetValidation.ValidationMessage.ValidationType;
import org.sidiff.matching.api.settings.MatchingSettings;

public class ScopeWidget extends AbstractRadioWidget<Scope> implements IWidgetValidation, ISettingsChangedListener {

	private static ILabelProvider labelProvider = new ColumnLabelProvider() {
		@Override
		public String getText(Object element) {
			switch((Scope)element) {
				case RESOURCE: return "Single Resource";
				case RESOURCE_SET: return "Complete Resource Set";
			};
			throw new AssertionError();
		}
	};

	private MatchingSettings settings;

	public ScopeWidget() {
		setTitle("Scope");
		setLabelProvider(labelProvider);
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
	public boolean validate() {
		return settings.getScope() != Scope.RESOURCE_SET
				|| settings.getMatcher() == null
				|| settings.getMatcher().isResourceSetCapable();
	}

	@Override
	public ValidationMessage getValidationMessage() {
		if (validate()) {
			return ValidationMessage.OK;
		}
		return new ValidationMessage(ValidationType.ERROR, "Selected matching engine " + settings.getMatcher().getName()
				+ " does not support resourceset scope, select another matching engine!");
	}

	@Override
	public void settingsChanged(Enum<?> item) {
		if (item == BaseSettingsItem.SCOPE) {
			if(settings.getScope() == null) {
				setSelection(Collections.emptyList());
			} else {
				setSelection(Collections.singletonList(settings.getScope()));
			}
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