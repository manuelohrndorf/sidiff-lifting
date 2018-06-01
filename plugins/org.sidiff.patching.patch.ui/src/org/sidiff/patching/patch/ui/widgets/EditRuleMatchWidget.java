package org.sidiff.patching.patch.ui.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.sidiff.common.settings.ISettingsChangedListener;
import org.sidiff.common.ui.widgets.AbstractWidget;
import org.sidiff.common.ui.widgets.IWidgetSelection;
import org.sidiff.difference.lifting.api.settings.LiftingSettings;
import org.sidiff.difference.lifting.api.settings.LiftingSettingsItem;

public class EditRuleMatchWidget extends AbstractWidget implements IWidgetSelection, ISettingsChangedListener {

	private LiftingSettings settings;
	private Composite container;
	private Button serialize_ErMatchesButton;

	@Override
	public void addSelectionListener(SelectionListener listener) {
		if(serialize_ErMatchesButton == null) {
			throw new IllegalStateException("createControl must be called first");
		}
		serialize_ErMatchesButton.addSelectionListener(listener);
	}

	@Override
	public void removeSelectionListener(SelectionListener listener) {
		if(serialize_ErMatchesButton == null) {
			throw new IllegalStateException("createControl must be called first");
		}
		serialize_ErMatchesButton.removeSelectionListener(listener);
	}

	@Override
	public Composite createControl(Composite parent) {
		container = new Composite(parent, SWT.NONE);
		{
			GridLayout grid = new GridLayout(1, false);
			grid.marginWidth = 0;
			grid.marginHeight = 0;
			container.setLayout(grid);
		}

		Group erMatchesGroup = new Group(container, SWT.NONE);
		{
			GridLayout grid = new GridLayout(1, false);
			grid.marginWidth = 10;
			grid.marginHeight = 10;
			erMatchesGroup.setLayout(grid);

			GridData data = new GridData(SWT.FILL, SWT.CENTER, true, true);
			erMatchesGroup.setLayoutData(data);
			erMatchesGroup.setText("Edit Rule Matches:");
		}

		serialize_ErMatchesButton = new Button(erMatchesGroup, SWT.CHECK);
		serialize_ErMatchesButton.setText("Serialize edit rule matches");
		serialize_ErMatchesButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				settings.setSerializeEditRuleMatch(serialize_ErMatchesButton.getSelection());
			}
		});
		updateSelection();

		return container;
	}

	@Override
	public Composite getWidget() {
		return container;
	}

	public LiftingSettings getSettings() {
		return settings;
	}

	public void setSettings(LiftingSettings settings) {
		this.settings = settings;
		this.settings.addSettingsChangedListener(this);
		updateSelection();
	}

	@Override
	public void settingsChanged(Enum<?> item) {
		if(item == LiftingSettingsItem.SERIALIZE_EDIT_RULE_MATCH) {
			updateSelection();
			getWidgetCallback().requestValidation();
		}
	}

	private void updateSelection() {
		if(serialize_ErMatchesButton != null) {
			serialize_ErMatchesButton.setSelection(settings.isSerializeEditRuleMatch());
		}
	}
}
