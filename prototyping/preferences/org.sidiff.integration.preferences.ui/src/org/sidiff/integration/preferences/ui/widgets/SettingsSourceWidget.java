package org.sidiff.integration.preferences.ui.widgets;

import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Assert;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.sidiff.common.settings.AbstractSettings;
import org.sidiff.common.ui.widgets.IWidget;
import org.sidiff.common.ui.widgets.IWidgetSelection;
import org.sidiff.common.ui.widgets.IWidgetValidation;
import org.sidiff.common.ui.widgets.IWidgetValidation.ValidationMessage.ValidationType;
import org.sidiff.integration.preferences.util.PreferenceStoreUtil;
import org.sidiff.integration.preferences.util.SettingsAdapterUtil;

/**
 * 
 * @author Robert Müller
 *
 */
public class SettingsSourceWidget implements IWidget, IWidgetValidation, IWidgetSelection {

	public static enum Source {
		GLOBAL,
		PROJECT,
		CUSTOM
	}

	// UI components
	private Composite container;
	private Group group;
	private Map<Source, Button> buttons;
	private SelectionListener buttonSelectionListener;
	private List<SelectionListener> selectionListeners;

	// inputs
	private AbstractSettings settings;
	private IProject project;
	private Set<String> documentTypes;

	// outputs
	private Source source;
	private ValidationMessage message;

	public SettingsSourceWidget(AbstractSettings settings, IProject project, Set<String> documentTypes) {
		this.settings = settings;
		this.project = project;
		this.documentTypes = documentTypes;
		this.selectionListeners = new LinkedList<SelectionListener>();
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

		group = new Group(container, SWT.SHADOW_IN);
		group.setText("Settings Source:");
		group.setLayout(new RowLayout(SWT.VERTICAL));
		buttons = new EnumMap<Source, Button>(Source.class);

		Button radioGlobal = new Button(group, SWT.RADIO);
		radioGlobal.setText("Use global settings");
		radioGlobal.addSelectionListener(getButtonSelectionListener());
		buttons.put(Source.GLOBAL, radioGlobal);

		boolean useProjectSpecific = PreferenceStoreUtil.useSpecificSettings(project);
		Button radioProject = new Button(group, SWT.RADIO);
		radioProject.setText("Use settings of project");
		radioProject.addSelectionListener(getButtonSelectionListener());
		radioProject.setEnabled(useProjectSpecific);
		buttons.put(Source.PROJECT, radioProject);

		Button radioCustom = new Button(group, SWT.RADIO);
		radioCustom.setText("Use custom settings");
		radioCustom.addSelectionListener(getButtonSelectionListener());
		buttons.put(Source.CUSTOM, radioCustom);

		setSource(useProjectSpecific ? Source.PROJECT : Source.CUSTOM);
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

	public void setSource(Source source) {
		Assert.isNotNull(source);
		this.source = source;

		for(Map.Entry<Source, Button> entry : buttons.entrySet()) {
			entry.getValue().setSelection(entry.getKey() == source);
		}

		updateSettings();

		for(SelectionListener listener : selectionListeners) {
			Event e = new Event();
			e.widget = buttons.get(source);
			listener.widgetSelected(new SelectionEvent(e));
		}
	}

	public Source getSource() {
		return source;
	}

	private void updateSettings() {
		switch(source) {	
			case GLOBAL:
				SettingsAdapterUtil.adaptSettingsGlobal(settings, documentTypes);
				break;

			case PROJECT:
				SettingsAdapterUtil.adaptSettingsProject(settings, project, documentTypes);
				break;

			case CUSTOM:
				// settings are not changed
				break;
		}
	}

	private SelectionListener getButtonSelectionListener() {
		if(buttonSelectionListener == null) {
			buttonSelectionListener = new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					for(Map.Entry<Source, Button> entry : buttons.entrySet()) {
						if(entry.getValue() == e.getSource()) {
							setSource(entry.getKey());
							break;
						}
					}
				}
			};
		}
		return buttonSelectionListener;
	}

	@Override
	public boolean validate() {
		if(source != Source.CUSTOM && !settings.validateSettings()) {
			message = new ValidationMessage(ValidationType.ERROR, "The settings are not valid.");
			return false;
		}

		message = new ValidationMessage(ValidationType.OK, "");
		return true;
	}

	@Override
	public ValidationMessage getValidationMessage() {
		return message;
	}

	@Override
	public void addSelectionListener(SelectionListener listener) {
		selectionListeners.add(listener);
	}

	@Override
	public void removeSelectionListener(SelectionListener listener) {
		selectionListeners.remove(listener);
	}
}
