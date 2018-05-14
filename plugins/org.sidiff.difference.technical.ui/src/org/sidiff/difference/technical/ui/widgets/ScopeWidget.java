package org.sidiff.difference.technical.ui.widgets;

import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.sidiff.common.emf.access.Scope;
import org.sidiff.common.settings.BaseSettingsItem;
import org.sidiff.common.settings.ISettingsChangedListener;
import org.sidiff.common.ui.widgets.IWidget;
import org.sidiff.common.ui.widgets.IWidgetSelection;
import org.sidiff.common.ui.widgets.IWidgetValidation;
import org.sidiff.common.ui.widgets.IWidgetValidation.ValidationMessage.ValidationType;
import org.sidiff.matching.api.settings.MatchingSettings;
import org.sidiff.matching.api.settings.MatchingSettingsItem;

public class ScopeWidget implements IWidget, IWidgetSelection, IWidgetValidation, ISettingsChangedListener {
	
	private MatchingSettings settings;
	private Composite container;
	private Button resourceButton;
	private Button resourceSetButton;
	private IPageChangedListener pageChangedListener;
	
	public ScopeWidget() {
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	@Override
	public Composite createControl(Composite parent) {

		container = new Composite(parent, SWT.NONE);
		{
			GridLayout grid = new GridLayout(1, false);
			grid.marginWidth = 0;
			grid.marginHeight = 0;
			container.setLayout(grid);
		}

		Group comparisonGroup = new Group(container, SWT.NONE);
		{
			GridLayout grid = new GridLayout(1, false);
			grid.marginWidth = 10;
			grid.marginHeight = 10;
			comparisonGroup.setLayout(grid);
			comparisonGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		}
		comparisonGroup.setText("Scope:");

		resourceButton = new Button(comparisonGroup, SWT.RADIO);
		resourceButton.setText("Single resource");
		resourceButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(resourceButton.getSelection()) {
					settings.setScope(Scope.RESOURCE);
				}
			}
		});

		resourceSetButton = new Button(comparisonGroup, SWT.RADIO);
		resourceSetButton.setText("Complete resourceset");
		resourceSetButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(resourceSetButton.getSelection()) {
					settings.setScope(Scope.RESOURCE_SET);
				}
			}
		});

		if(settings.getScope() == null) {
			settings.setScope(Scope.RESOURCE);
		}
		updateButtonStates();
		return container;
	}

	@Override
	public Composite getWidget() {
		return container;
	}

	@Override
	public void setLayoutData(Object layoutData) {
		container.setLayoutData(layoutData);
	}



	@Override
	public boolean validate() {

		if (resourceButton.getSelection() || resourceSetButton.getSelection()) {
			if (settings.getScope().equals(Scope.RESOURCE_SET)
					&& !settings.getMatcher().isResourceSetCapable()) {
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	@Override
	public ValidationMessage getValidationMessage() {
		if (validate()) {
			return new ValidationMessage(ValidationType.OK, "");
		} else if(settings.getScope().equals(Scope.RESOURCE_SET) && !settings.getMatcher().isResourceSetCapable()) {
			return new ValidationMessage(ValidationType.ERROR, "Selected matching engine " + settings.getMatcher().getName() + " does not support resourceset scope, select another matching engine!");
		}else{
			return new ValidationMessage(ValidationType.ERROR, "Please select a scope!");
		}
	}

	@Override
	public void addSelectionListener(SelectionListener listener) {
		if ((resourceButton == null) || (resourceSetButton == null)) {
			throw new RuntimeException("Create controls first!");
		}
		resourceButton.addSelectionListener(listener);
		resourceSetButton.addSelectionListener(listener);
	}

	@Override
	public void removeSelectionListener(SelectionListener listener) {
		if ((resourceButton != null) || (resourceSetButton != null)) {
			resourceButton.removeSelectionListener(listener);
			resourceSetButton.removeSelectionListener(listener);
		}
	}

	@Override
	public void settingsChanged(Enum<?> item) {
		if(item.equals(MatchingSettingsItem.MATCHER)){
			if (pageChangedListener != null) pageChangedListener.pageChanged(null);
		} else if (item.equals(BaseSettingsItem.SCOPE)) {
			updateButtonStates();
		}
	}

	private void updateButtonStates() {
		if (resourceButton != null) resourceButton.setSelection(settings.getScope() == Scope.RESOURCE);
		if (resourceSetButton != null) resourceSetButton.setSelection(settings.getScope() == Scope.RESOURCE_SET);
	}

	public MatchingSettings getSettings() {
		return settings;
	}

	public void setSettings(MatchingSettings settings) {
		this.settings = settings;
		this.settings.addSettingsChangedListener(this);
		updateButtonStates();
	}

	public IPageChangedListener getPageChangedListener() {
		return pageChangedListener;
	}

	public void setPageChangedListener(IPageChangedListener pageChangedListener) {
		this.pageChangedListener = pageChangedListener;
	}

}