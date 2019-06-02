package org.sidiff.patching.ui.wsupdate.widgets;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.sidiff.common.ui.widgets.AbstractContainerWidget;
import org.sidiff.common.ui.widgets.IWidgetValidation;
import org.sidiff.common.ui.widgets.IWidgetValidation.ValidationMessage.ValidationType;
import org.sidiff.patching.ui.wsupdate.util.WSUModels;

public class WSUModelsWidget extends AbstractContainerWidget implements IWidgetValidation {

	private WSUModels mergeModels;

	/**
	 * 2D-Matrix of all buttons, column-major.
	 * The first index is the button's group, i.e. role (base, theirs, mine).
	 * The second index is the particular file from {@link #mergeModels}.
	 */
	private Button buttons[][];

	private SelectionListener buttonListener;

	public WSUModelsWidget(WSUModels mergeModels) {
		setTitle("Roles of used models");
		this.mergeModels = mergeModels;
		this.buttons = new Button[WSUModels.NUM_ROLES][WSUModels.NUM_ROLES];
	}
	
	@Override
	protected Composite createContents(Composite container) {
		Composite composite = new Composite(container, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(3).applyTo(composite);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(composite);

		createRoleButtonGroup(composite, "Base version", WSUModels.ROLE_BASE);
		createRoleButtonGroup(composite, "Workspace version", WSUModels.ROLE_MINE);
		createRoleButtonGroup(composite, "Repository version", WSUModels.ROLE_THEIRS);

		return composite;
	}

	protected void createRoleButtonGroup(Composite container, String label, int role) {
		Group group = new Group(container, SWT.NONE);
		group.setText(label);
		GridLayoutFactory.swtDefaults().applyTo(group);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(group);

		createModelButton(group, role, WSUModels.ROLE_BASE);
		createModelButton(group, role, WSUModels.ROLE_MINE);
		createModelButton(group, role, WSUModels.ROLE_THEIRS);
	}

	protected void createModelButton(Group group, int role, int file) {
		Button button = new Button(group, SWT.RADIO);
		button.setData(new ButtonData(role, file));
		button.setText(mergeModels.getLabels().get(file));
		button.setSelection(role == file);
		button.addSelectionListener(getButtonSelectionListener());
		buttons[role][file] = button;
	}

	protected SelectionListener getButtonSelectionListener() {
		if(buttonListener == null) {
			buttonListener = new SelectionAdapter() {
				private int oldSelection = -1;
				
				@Override
				public void widgetSelected(SelectionEvent e) {
					Button button = (Button)e.widget;
					ButtonData data = (ButtonData)e.widget.getData();
					if(button.getSelection()) {
						if(oldSelection >= 0) {
							// find selection of the newly selected file in other role
							// and set its selection to the old selection
							for(int i = 0; i < WSUModels.NUM_ROLES; i++) {
								if(data.role != i && buttons[i][data.file].getSelection()) {
									buttons[i][oldSelection].setSelection(true);
									buttons[i][data.file].setSelection(false);
									mergeModels.swap(data.file, oldSelection);
									break;
								}
							}
							
							oldSelection = -1;
							getWidgetCallback().requestValidation();
						}
					} else {
						// get old selection for the changed role
						for(int i = 0; i < WSUModels.NUM_ROLES; i++) {
							if(buttons[data.role][i] == button) {
								oldSelection = i;
								break;
							}
						}
					}
				}
			};
		}
		return buttonListener;
	}

	public WSUModels getMergeModels(){
		return mergeModels;
	}

	private boolean areRolesExclusive() {
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
	protected ValidationMessage doValidate() {
		if (areRolesExclusive()) {
			return ValidationMessage.OK;
		}
		return new ValidationMessage(ValidationType.ERROR, "Please define only one role for each model!");
	}

	protected static class ButtonData {
		protected final int role;
		protected final int file;

		public ButtonData(int role, int file) {
			this.role = role;
			this.file = file;
		}
	}
}
