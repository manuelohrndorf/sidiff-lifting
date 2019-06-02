package org.sidiff.patching.ui.widgets;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Spinner;
import org.sidiff.common.emf.settings.ISettingsChangedListener;
import org.sidiff.common.emf.settings.ISettingsItem;
import org.sidiff.common.ui.widgets.AbstractContainerWidget;
import org.sidiff.common.ui.widgets.IWidgetValidation;
import org.sidiff.common.ui.widgets.IWidgetValidation.ValidationMessage.ValidationType;
import org.sidiff.matching.api.settings.MatchingSettingsItem;
import org.sidiff.patching.api.settings.PatchingSettings;
import org.sidiff.patching.api.settings.PatchingSettingsItem;

public class ReliabilityWidget extends AbstractContainerWidget implements IWidgetValidation, ISettingsChangedListener {

	private final int MIN = 0;
	private final int MAX = 100;
	private final int INCREMENT = 5;
	private final int DEFAULT = 50;

	private PatchingSettings settings;

	private Scale scale;
	private Spinner spinner;

	public ReliabilityWidget() {
		super();
		setTitle("Minimal Reliability");
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
			updateSelection();
		}
	}

	public PatchingSettings getSettings() {
		return settings;
	}

	public void setSettings(PatchingSettings settings) {
		this.settings = settings;
		this.settings.addSettingsChangedListener(this);
		updateSelection();
	}

	@Override
	public boolean isEnabled() {
		return super.isEnabled() && checkComputability();
	}

	@Override
	protected Composite createContents(Composite container) {
		Composite gridContainer = new Composite(container, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(gridContainer);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(gridContainer);

		scale = new Scale(gridContainer, SWT.HORIZONTAL);
		scale.setMinimum(MIN);
		scale.setMaximum(MAX);
		scale.setIncrement(INCREMENT);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(scale);
		scale.addSelectionListener(SelectionListener.widgetSelectedAdapter(
				e -> settings.setMinReliability(scale.getSelection())));

		spinner = new Spinner(gridContainer, SWT.NONE);
		spinner.setMinimum(MIN);
		spinner.setMaximum(MAX);
		spinner.setIncrement(INCREMENT);
		GridDataFactory.fillDefaults().applyTo(spinner);
		spinner.addSelectionListener(SelectionListener.widgetSelectedAdapter(
				e -> settings.setMinReliability(spinner.getSelection())));

		if(settings.getMinReliability() < 0) {
			settings.setMinReliability(DEFAULT);
		}
		updateSelection();
		return container;
	}

	protected void updateSelection() {
		if(spinner == null || scale == null) {
			return;
		}
		spinner.setSelection(settings.getMinReliability());
		scale.setSelection(settings.getMinReliability());
	}
}