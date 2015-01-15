package org.silift.patching.patch.ui.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.sidiff.difference.lifting.settings.ISettingsChangedListener;
import org.sidiff.difference.lifting.settings.LiftingSettings;
import org.silift.common.util.ui.widgets.IWidget;
import org.silift.common.util.ui.widgets.IWidgetSelection;
import org.silift.common.util.ui.widgets.IWidgetValidation;

public class EditRuleMatchWidget implements IWidget, IWidgetSelection, IWidgetValidation, ISettingsChangedListener {

	private LiftingSettings settings;
	private Composite container;
	private Button serialize_ErMatchesButton;
	
	@Override
	public void settingsChanged(Enum<?> item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean validate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ValidationMessage getValidationMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addSelectionListener(SelectionListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeSelectionListener(SelectionListener listener) {
		// TODO Auto-generated method stub
		
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
		
		serialize_ErMatchesButton = new Button(container, SWT.CHECK);
		serialize_ErMatchesButton.setText("serialize edit rule matches");
		serialize_ErMatchesButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e){
				settings.setSerializeEditRuleMatch(serialize_ErMatchesButton.getSelection());
			}
		});
		
		return container;
	}

	@Override
	public Composite getWidget() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLayoutData(Object layoutData) {
		// TODO Auto-generated method stub
		
	}

	public LiftingSettings getSettings() {
		return settings;
	}

	public void setSettings(LiftingSettings settings) {
		this.settings = settings;
	}

}
