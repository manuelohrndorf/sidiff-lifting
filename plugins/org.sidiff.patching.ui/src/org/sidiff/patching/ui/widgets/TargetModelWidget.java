package org.sidiff.patching.ui.widgets;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.ui.dialogs.WorkspaceResourceDialog;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.*;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.common.ui.util.UIUtil;
import org.sidiff.common.ui.widgets.AbstractWidget;
import org.sidiff.common.ui.widgets.IWidgetValidation.ValidationMessage.ValidationType;

public class TargetModelWidget extends AbstractWidget {

	private Composite container;
	private Button modelChooseButton;
	private Text targetModelText;

	private URI selectedModel;

	@Override
	public Composite createControl(Composite parent) {

		container = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().applyTo(container);

		Group modelChooseGroup = new Group(container, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).margins(10, 10).applyTo(modelChooseGroup);
		modelChooseGroup.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));
		modelChooseGroup.setText("Target model:");

		targetModelText = new Text(modelChooseGroup, SWT.BORDER);
		targetModelText.setEditable(false);
		targetModelText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		modelChooseButton = new Button(modelChooseGroup, SWT.PUSH);
		modelChooseButton.setText("Choose Model");
		modelChooseButton.addSelectionListener(SelectionListener.widgetSelectedAdapter(event -> {
			IFile files[] = WorkspaceResourceDialog.openFileSelection(UIUtil.getActiveShell(),
					"Target model selection", "Select the target model for patch application", false, null, null);
			if(files.length > 0) {
				selectedModel = EMFStorage.toPlatformURI(files[0]);
				targetModelText.setText(selectedModel.toString());
				getWidgetCallback().requestValidation();
			}
		}));
		return container;
	}

	@Override
	public Composite getWidget() {
		return container;
	}

	public URI getSelectedModel() {
		return selectedModel;
	}

	@Override
	protected ValidationMessage doValidate() {
		if (selectedModel == null) {
			return new ValidationMessage(ValidationType.ERROR, "Please select a target model!");
		}
		return ValidationMessage.OK;
	}
}