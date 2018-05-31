package org.sidiff.patching.ui.wsupdate.widgets;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.sidiff.common.ui.widgets.AbstractWidget;
import org.sidiff.common.ui.widgets.IWidgetSelection;
import org.sidiff.common.ui.widgets.IWidgetValidation;
import org.sidiff.common.ui.widgets.IWidgetValidation.ValidationMessage.ValidationType;
import org.sidiff.patching.ui.wsupdate.util.WSUModels;

public class WSUModelsWidget extends AbstractWidget implements IWidgetSelection, IWidgetValidation {

	private WSUModels mergeModels;

	private Composite container;

	/**
	 * 2D-Matrix of all buttons, column-major.
	 * The first index is the button's group, i.e. role (base, theirs, mine).
	 * The second index is the particular file from {@link #mergeModels}.
	 */
	private Button buttons[][];

	private List<SelectionListener> listeners;
	private SelectionListener buttonListener;

	public WSUModelsWidget(WSUModels mergeModels) {
		this.mergeModels = mergeModels;
		this.listeners = new LinkedList<SelectionListener>();
		this.buttons = new Button[WSUModels.NUM_ROLES][WSUModels.NUM_ROLES];
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

		Group modelsGroup = new Group(container, SWT.NONE);
		{
			GridLayout grid = new GridLayout(3, false);
			grid.marginWidth = 10;
			grid.marginHeight = 10;
			modelsGroup.setLayout(grid);
			modelsGroup.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
			modelsGroup.setText("Select roles of used models:");
		}

		createRoleButtonGroup(modelsGroup, "Base version", WSUModels.ROLE_BASE);
		createRoleButtonGroup(modelsGroup, "Workspace version", WSUModels.ROLE_MINE);
		createRoleButtonGroup(modelsGroup, "Repository version", WSUModels.ROLE_THEIRS);

		return container;
	}

	protected void createRoleButtonGroup(Composite container, String label, int role) {
		Group group = new Group(container, SWT.NONE);
		{
			GridLayout grid = new GridLayout(1, false);
			grid.marginWidth = 10;
			grid.marginHeight = 10;
			group.setLayout(grid);
			group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
			group.setText(label);
		}

		createModelButton(group, role, WSUModels.ROLE_BASE);
		createModelButton(group, role, WSUModels.ROLE_MINE);
		createModelButton(group, role, WSUModels.ROLE_THEIRS);
	}

	protected void createModelButton(Group group, int role, int file) {
		Button button = new Button(group, SWT.RADIO);
		button.setData(new ButtonData(role, mergeModels.getFiles().get(file)));
		button.setText(mergeModels.getLabels().get(file));
		button.setSelection(role == file);
		button.addSelectionListener(getButtonSelectionListener());
		buttons[role][file] = button;
	}

	protected SelectionListener getButtonSelectionListener() {
		if(buttonListener == null) {
			buttonListener = new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					Button button = (Button)e.widget;
					if(button.getSelection()) {
						ButtonData data = (ButtonData)e.widget.getData();
						switch(data.role) {
							case WSUModels.ROLE_MINE:
								mergeModels.setModelMine(data.file);
								break;
	
							case WSUModels.ROLE_THEIRS:
								mergeModels.setModelTheirs(data.file);
								break;
	
							case WSUModels.ROLE_BASE:
								mergeModels.setModelBase(data.file);
								break;
						}

						getWidgetCallback().requestValidation();
						for(SelectionListener listener : listeners) {
							listener.widgetSelected(e);
						}
					}
				}
			};
		}
		return buttonListener;
	}

	@Override
	public Composite getWidget() {
		return container;
	}

	public WSUModels getMergeModels(){
		if(validate()) {
			return this.mergeModels;
		}
		return null;
	}

	@Override
	public boolean validate() {
		// for each input file, i.e. row of button, make sure that only one of them is selected
		for(int i = 0; i < WSUModels.NUM_ROLES; i++) {
			if(!( buttons[WSUModels.ROLE_BASE][i].getSelection()
				^ buttons[WSUModels.ROLE_MINE][i].getSelection()
				^ buttons[WSUModels.ROLE_THEIRS][i].getSelection())) {
				return false;
			}
		}
		return true;
	}

	@Override
	public ValidationMessage getValidationMessage() {
		if (validate()) {
			return ValidationMessage.OK;
		} else {
			return new ValidationMessage(ValidationType.ERROR, "Please define only one role for each model!");
		}
	}

	@Override
	public void addSelectionListener(SelectionListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeSelectionListener(SelectionListener listener) {
		listeners.remove(listener);
	}

	protected static class ButtonData {
		protected final int role;
		protected final IFile file;

		public ButtonData(int role, IFile file) {
			this.role = role;
			this.file = file;
		}
	}
}
