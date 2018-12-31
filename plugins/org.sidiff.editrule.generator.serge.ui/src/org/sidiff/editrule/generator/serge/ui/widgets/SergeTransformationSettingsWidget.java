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

public class SergeTransformationSettingsWidget extends AbstractWidget {

	private SergeSettings settings;

	private Group container;
	
	@Override
	public Composite createControl(Composite parent) {
		// TransformationGroup
		container = new Group(parent, SWT.SHADOW_IN);
		container.setLayout(new GridLayout());
		container.setText("Transformation Settings");

		// Checkbox delete manual transformations
		final Button checkbox_deleteManualTrafos = new Button(container, SWT.CHECK);
		checkbox_deleteManualTrafos.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		checkbox_deleteManualTrafos.setText("Delete existing, manually created transformations ");
		checkbox_deleteManualTrafos.setSelection(settings.isDeleteManualTransformations());
		checkbox_deleteManualTrafos.addSelectionListener(SelectionListener.widgetSelectedAdapter(
				e -> settings.setOverwriteConfigInTargetFolder(checkbox_deleteManualTrafos.getSelection())));

		// Radiobutton delete existing transformations
		final Button radiobutton_deleteGeneratedTrafos = new Button(container, SWT.RADIO);
		radiobutton_deleteGeneratedTrafos.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		radiobutton_deleteGeneratedTrafos.setText("Delete existing, generated transformations");
		radiobutton_deleteGeneratedTrafos.setSelection(settings.isDeleteGeneratedTransformations());
		radiobutton_deleteGeneratedTrafos.addSelectionListener(SelectionListener.widgetSelectedAdapter(
				e -> settings.setDeleteGeneratedTransformations(radiobutton_deleteGeneratedTrafos.getSelection())));

		// Radiobutton keep existing, generated transformations
		final Button radiobutton_keepGeneratedTrafos = new Button(container, SWT.RADIO);
		radiobutton_keepGeneratedTrafos.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		radiobutton_keepGeneratedTrafos.setText("Keep existing, generated transformations");
		radiobutton_keepGeneratedTrafos.setSelection(!settings.isDeleteGeneratedTransformations());
		radiobutton_keepGeneratedTrafos.addSelectionListener(SelectionListener.widgetSelectedAdapter(
				e -> settings.setDeleteGeneratedTransformations(!radiobutton_keepGeneratedTrafos.getSelection())));

		// Checkbox overwrite existing transformations
		final Button checkbox_overwriteGeneratedTransfos = new Button(container, SWT.CHECK);
		checkbox_overwriteGeneratedTransfos.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));
		checkbox_overwriteGeneratedTransfos.setText("Overwrite existing, generated transformation");
		checkbox_overwriteGeneratedTransfos.setSelection(settings.isOverwriteGeneratedTransformations());
		checkbox_overwriteGeneratedTransfos.addSelectionListener(SelectionListener.widgetSelectedAdapter(
				e -> settings.setOverwriteGeneratedTransformations(checkbox_overwriteGeneratedTransfos.getSelection())));
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
