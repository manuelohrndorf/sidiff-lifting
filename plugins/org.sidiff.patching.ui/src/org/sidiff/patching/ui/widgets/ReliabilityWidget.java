package org.sidiff.patching.ui.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Spinner;
import org.sidiff.common.settings.ISettingsChangedListener;
import org.sidiff.common.ui.widgets.AbstractWidget;
import org.sidiff.common.ui.widgets.IWidgetSelection;
import org.sidiff.common.ui.widgets.IWidgetValidation;
import org.sidiff.common.ui.widgets.IWidgetValidation.ValidationMessage.ValidationType;
import org.sidiff.matching.api.settings.MatchingSettingsItem;
import org.sidiff.patching.api.settings.PatchingSettings;
import org.sidiff.patching.api.settings.PatchingSettingsItem;

public class ReliabilityWidget extends AbstractWidget implements IWidgetSelection,
		IWidgetValidation, ISettingsChangedListener {

	private final int defaultReliability = 50;

	private PatchingSettings settings;

	private Composite container;
	private Scale scale;
	private Spinner spinner;

	public ReliabilityWidget() {
		
	}

	private boolean checkComputability() {
		if (settings.useSymbolicLinks()) {
			return settings.getSymbolicLinkHandler().canComputeReliability();
		} else {
			return settings.getMatcher() != null;
		}
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	@Override
	public Composite createControl(Composite parent) {

		container = new Composite(parent, SWT.NONE);
		{
			GridLayout grid = new GridLayout(2, false);
			grid.marginWidth = 0;
			grid.marginHeight = 0;
			container.setLayout(grid);
		}

		Group reliabilityGroup = new Group(container, SWT.NONE);
		{
			GridLayout grid = new GridLayout(2, false);
			grid.marginWidth = 10;
			grid.marginHeight = 10;
			reliabilityGroup.setLayout(grid);
			reliabilityGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		}
		reliabilityGroup.setText("Minimal Reliability:");

		scale = new Scale(reliabilityGroup, SWT.NONE);
		scale.setMaximum(100);
		scale.setIncrement(5);
		scale.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		scale.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				settings.setMinReliability(scale.getSelection());
			}
		});

		spinner = new Spinner(reliabilityGroup, SWT.NONE);
		spinner.setMaximum(100);
		spinner.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				settings.setMinReliability(spinner.getSelection());
			}
		});

		if(settings.getMinReliability() < 0) {
			settings.setMinReliability(defaultReliability);
		}
		updateSelection();
		return container;
	}

	@Override
	public Composite getWidget() {
		return container;
	}

	public int getReliability() {
		if (checkComputability()) {
			return settings.getMinReliability();
		} else {
			return -1;
		}
	}

	@Override
	public boolean validate() {
		return true; // always valid
	}

	@Override
	public ValidationMessage getValidationMessage() {
		if (checkComputability()) {
			return ValidationMessage.OK;
		} else {
			if (settings.useSymbolicLinks()) {
				return new ValidationMessage(ValidationType.INFORMATION, "Selected Symbolic Link Handler does not support Reliability!");
			} else {
				return new ValidationMessage(ValidationType.INFORMATION, "Selected Matching Engine does not support Reliability!");
			}
		}
	}

	@Override
	public void addSelectionListener(SelectionListener listener) {
		if(scale == null || spinner == null) {
			throw new IllegalStateException("createControl must be called first");
		}
		spinner.addSelectionListener(listener);
		scale.addSelectionListener(listener);
	}

	@Override
	public void removeSelectionListener(SelectionListener listener) {
		if(scale == null || spinner == null) {
			throw new IllegalStateException("createControl must be called first");
		}
		spinner.removeSelectionListener(listener);
		scale.removeSelectionListener(listener);
	}

	@Override
	public void settingsChanged(Enum<?> item) {
		if (item == MatchingSettingsItem.MATCHER
				|| item == PatchingSettingsItem.SYMBOLIC_LINK_HANDLER) {
			// update enabled state
			setEnabled(isEnabled());
			getWidgetCallback().requestValidation();
		} else if(item == PatchingSettingsItem.RELIABILITY) {
			updateSelection();
			getWidgetCallback().requestValidation();
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

	private void updateSelection() {
		if(spinner == null || scale == null) {
			return;
		}
		spinner.setSelection(settings.getMinReliability());
		scale.setSelection(settings.getMinReliability());
	}
}