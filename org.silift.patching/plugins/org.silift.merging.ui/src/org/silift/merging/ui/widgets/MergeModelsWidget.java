package org.silift.merging.ui.widgets;

import java.awt.FlowLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.eclipse.core.resources.IContainer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.silift.common.util.ui.widgets.IWidget;
import org.silift.common.util.ui.widgets.IWidgetSelection;
import org.silift.common.util.ui.widgets.IWidgetValidation;
import org.silift.merging.ui.util.MergeModels;

public class MergeModelsWidget implements IWidget, IWidgetSelection, IWidgetValidation {

	private MergeModels mergeModels;
	
	private Composite container;
	private Button buttonValidateModels;
	
	private JComboBox<String> modelBaseBox;
	private JLabel modelBaseLabel;
	private JPanel modelBasePanel;
	
	private JComboBox<String> modelMineBox;
	private JComboBox<String> modelTheirsBox;

	private boolean validateModels = false;

	public MergeModelsWidget(MergeModels mergeModels) {
		this.mergeModels = mergeModels;
	}

	private String[] getFileNames() {
		String resourceMine_name = mergeModels.getFileMine().getName();
		String resourceTheirs_name = mergeModels.getFileTheirs().getName();
		String resourceBase_name = mergeModels.getFileBase().getName();

		IContainer parentMine = mergeModels.getFileMine().getParent();
		IContainer parentTheirs = mergeModels.getFileTheirs().getParent();
		IContainer parentBase = mergeModels.getFileBase().getParent();

		/*
		 * TODO ?
		while (resourceA_name.equals(resourceB_name)) {
			resourceA_name = parentA.getName() + "/" + resourceA_name;
			resourceB_name = parentB.getName() + "/" + resourceB_name;
			parentA = parentA.getParent();
			parentB = parentB.getParent();
		}
		*/

		String[] result = { resourceMine_name, resourceTheirs_name, resourceBase_name };
		return result;
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

		// Generate model file names:
		String[] names = getFileNames();


		Group modelsGroup = new Group(container, SWT.NONE);
		{
			GridLayout grid = new GridLayout(3, false);
			grid.marginWidth = 10;
			grid.marginHeight = 10;
			modelsGroup.setLayout(grid);
			modelsGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

			modelsGroup.setText("Select role of models:");
		}
		
		//Base Model
		 modelBaseLabel = new JLabel("Select base model:");
		
		modelBaseBox = new JComboBox<String>(names);
		modelBaseBox.setSelectedIndex(2);
		
		modelBasePanel = new JPanel(new FlowLayout());
		modelBasePanel.add(modelBaseLabel);
		modelBasePanel.add(modelBaseBox);


		/*
		 *  Validate models
		 */

		buttonValidateModels = new Button(modelsGroup, SWT.CHECK);
		{
			buttonValidateModels.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2,
					1));
			buttonValidateModels.setSelection(validateModels);
			buttonValidateModels.setText("Validate Models");
		}
		buttonValidateModels.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				validateModels = buttonValidateModels.getSelection();
			}
		});

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


	public boolean isValidateModels() {
		return validateModels;
	}

	@Override
	public boolean validate() {
		if (modelBaseBox.getSelectedIndex() == 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String getValidationMessage() {
		if (validate()) {
			return "";
		} else {
			return "Please select a source model!";
		}
	}

	@Override
	public void addSelectionListener(SelectionListener listener) {
	}

	@Override
	public void removeSelectionListener(SelectionListener listener) {
	}
}
