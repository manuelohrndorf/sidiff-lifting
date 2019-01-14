package org.sidiff.patching.patch.ui.widgets;

import java.util.Collections;
import java.util.List;

import org.sidiff.common.settings.ISettingsChangedListener;
import org.sidiff.common.ui.widgets.AbstractCheckboxWidget;
import org.sidiff.common.ui.widgets.IWidgetDisposable;
import org.sidiff.difference.lifting.api.settings.LiftingSettings;
import org.sidiff.difference.lifting.api.settings.LiftingSettingsItem;

public class EditRuleMatchWidget extends AbstractCheckboxWidget<String> implements IWidgetDisposable, ISettingsChangedListener {

	private static final String SERIALIZE = "Serialize edit rule matches";

	private LiftingSettings settings;

	public EditRuleMatchWidget() {
		setTitle("Edit Rule Matches");
		addModificationListener((oldValues, newValues) -> settings.setSerializeEditRuleMatch(newValues.contains(SERIALIZE)));
	}

	@Override
	public List<String> getSelectableValues() {
		return Collections.singletonList(SERIALIZE);
	}

	public LiftingSettings getSettings() {
		return settings;
	}

	public void setSettings(LiftingSettings settings) {
		if(this.settings != null) {
			this.settings.removeSettingsChangedListener(this);
		}
		this.settings = settings;
		this.settings.addSettingsChangedListener(this);
		settingsChanged(LiftingSettingsItem.SERIALIZE_EDIT_RULE_MATCH);
	}

	@Override
	public void settingsChanged(Enum<?> item) {
		if(item == LiftingSettingsItem.SERIALIZE_EDIT_RULE_MATCH) {
			setSelection(settings.isSerializeEditRuleMatch() ? Collections.singletonList(SERIALIZE) : Collections.emptyList());
		}
	}

	@Override
	public void dispose() {
		if(settings != null) {
			settings.removeSettingsChangedListener(this);
			settings = null;
		}
	}
}
