package org.sidiff.editrule.generator.serge.ui.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.sidiff.common.ui.widgets.AbstractWidget;
import org.sidiff.editrule.generator.serge.settings.SergeSettings;

public class SergeConfigSerializationWidget extends AbstractWidget {

	private SergeSettings settings;
	private Group container;
	
	@Override
	public Composite createControl(Composite parent) {
		// Group Config Serialization
		container = new Group(parent, SWT.SHADOW_IN);
		container.setText("Config Serialization");
		container.setLayout(new GridLayout());

		// Checkbox overwrite config
		final Button checkbox_overwriteConfig = new Button(container, SWT.CHECK);
		checkbox_overwriteConfig.setText("Overwrite existing config");
		checkbox_overwriteConfig.setSelection(settings.isOverwriteConfigInTargetFolder());
		checkbox_overwriteConfig.addSelectionListener(SelectionListener.widgetSelectedAdapter(
				e -> settings.setOverwriteConfigInTargetFolder(checkbox_overwriteConfig.getSelection())));

		// Group Log Serialization
		Group grpLogSerialization = new Group(container, SWT.SHADOW_IN);
		grpLogSerialization.setLayout(new GridLayout());
		grpLogSerialization.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		grpLogSerialization.setText("Log Serialization");
		
		// Checkbox save log serialization
		final Button checkbox_saveLogSerialization = new Button(grpLogSerialization, SWT.CHECK);
		checkbox_saveLogSerialization.setText("Save logs");
		checkbox_saveLogSerialization.setSelection(settings.isSaveLogs());
		checkbox_saveLogSerialization.addSelectionListener(SelectionListener.widgetSelectedAdapter(
				e -> settings.setSaveLogs(checkbox_saveLogSerialization.getSelection())));

		// Checkbox delete previous logs
		final Button checkbox_deleteLogs = new Button(grpLogSerialization, SWT.CHECK);
		checkbox_deleteLogs.setText("Delete previous logs");
		checkbox_deleteLogs.setSelection(settings.isDeleteLogs());
		checkbox_deleteLogs.addSelectionListener(SelectionListener.widgetSelectedAdapter(
				e -> settings.setDeleteLogs(checkbox_deleteLogs.getSelection())));
		return container;
	}

	@Override
	public Composite getWidget() {
		return container;
	}
	
	public SergeSettings getSettings() {
		return settings;
	}
	
	public void setSettings(SergeSettings settings) {
		this.settings = settings;
	}
}
