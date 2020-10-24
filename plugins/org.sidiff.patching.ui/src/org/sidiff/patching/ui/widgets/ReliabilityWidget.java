package org.sidiff.patching.ui.widgets;

import org.sidiff.common.emf.settings.ISettingsChangedListener;
import org.sidiff.common.emf.settings.ISettingsItem;
import org.sidiff.common.ui.widgets.IWidgetValidation.ValidationMessage.ValidationType;
import org.sidiff.common.ui.widgets.NumberSelectionWidget;
import org.sidiff.matching.api.settings.MatchingSettingsItem;
import org.sidiff.patching.api.settings.PatchingSettings;
import org.sidiff.patching.api.settings.PatchingSettingsItem;

public class ReliabilityWidget extends NumberSelectionWidget implements ISettingsChangedListener {

	private PatchingSettings settings;

	public ReliabilityWidget(PatchingSettings settings) {
		super("Minimal Reliability", 0, 100, 5);
		this.settings = settings;
		if (settings.getMinReliability() < 0) {
			settings.setMinReliability(50); // set default if unset
		}
		setSelection(settings.getMinReliability());
	}

	private boolean checkComputability() {
		if (settings.useSymbolicLinks()) {
			return settings.getSymbolicLinkHandler().canComputeReliability();
		}
		return settings.getMatcher() != null;
	}

	public int getReliability() {
		if (checkComputability()) {
			return settings.getMinReliability();
		}
		return -1;
	}

	@Override
	protected ValidationMessage doValidate() {
		if (checkComputability()) {
			return ValidationMessage.OK;
		} else if (settings.useSymbolicLinks()) {
			return new ValidationMessage(ValidationType.INFORMATION, "Selected Symbolic Link Handler does not support Reliability!");
		} else {
			return new ValidationMessage(ValidationType.INFORMATION, "Selected Matching Engine does not support Reliability!");
		}
	}

	@Override
	public void settingsChanged(ISettingsItem item) {
		if (item == MatchingSettingsItem.MATCHER
				|| item == PatchingSettingsItem.SYMBOLIC_LINK_HANDLER) {
			// update enabled state
			setEnabled(checkComputability());
			getWidgetCallback().requestValidation();
		} else if(item == PatchingSettingsItem.RELIABILITY) {
			setSelection(settings.getMinReliability());
		}
	}

	@Override
	public boolean isEnabled() {
		return super.isEnabled() && checkComputability();
	}
}