package org.sidiff.remote.application.ui.connector.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.sidiff.common.settings.ISettingsChangedListener;
import org.sidiff.common.ui.widgets.IWidgetValidation.ValidationMessage.ValidationType;
import org.sidiff.common.ui.widgets.UriValidationWidget;
import org.sidiff.remote.application.connector.settings.RepositorySettings;

/**
 * 
 * @author cpietsch
 *
 */
public class RepositoryUriValidationWidget extends UriValidationWidget implements ISettingsChangedListener {

	private RepositorySettings settings;
		
	// ---------- UI Elements ----------
	
	private Text port_text;
	
	private Text path_text;
	
	// ---------- Constructor ----------
	
	public RepositoryUriValidationWidget(RepositorySettings settings) {
		super("Repository");
		this.settings = settings;
	}
	
	// ---------- IWidget ----------
	
	@Override
	public Composite createControl(Composite parent) {
		super.createControl(parent);
		Label port_label = new Label(uri_group, SWT.NONE);
		port_label.setText("Port:");
		port_text = new Text(uri_group, SWT.BORDER);
		port_text.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				if(isValidPort(port_text.getText())) {
					settings.setRepositoryPort(Integer.parseInt(port_text.getText()));
				}else {
					settings.setRepositoryPort(-1);
				}
				
				port_text.notifyListeners(SWT.Selection, new Event());
			}
		});
		
		Label path_label = new Label(uri_group, SWT.NONE);
		path_label.setText("Path:");
		path_text = new Text(uri_group, SWT.BORDER);
		path_text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		path_text.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				if(isValidPath(path_text.getText())) {
					settings.setRepositoryPath(path_text.getText());
				}else {
					settings.setRepositoryPath("");
				}
				
			}
		});
		return container;
	}
	
	// ---------- UriValidationWidget ----------
	
	@Override
	public void modifyTextHook() {
		if(super.validate()) {
			this.settings.setRepositoryURL(this.uri_text.getText());
		}

	}
	
	@Override
	public boolean validate() {
		return super.validate() && isValidPort(this.port_text.getText()) && isValidPath(this.path_text.getText());
	}

	@Override
	public ValidationMessage getValidationMessage() {
		if (validate()) {
			validationMessage = new ValidationMessage(ValidationType.OK, "");
		} else {
			validationMessage = new ValidationMessage(ValidationType.ERROR, "Please insert an valid URL and port for " + label_group);
		}
		return validationMessage;
	}
	
	private boolean isValidPort(String s) {
		try {
			int port = Integer.parseInt(port_text.getText());
			return 0  < port && port < 65535;
		}catch (NumberFormatException nfe) {
			return false;
		}
	}
	
	private boolean isValidPath(String s) {
		return s.matches("([a-zA-z0-9])+(([_\\-\\./])([a-zA-z0-9])+)*");
	}
	
	// ---------- IWidgetSelection -----------
	
		@Override
		public void addSelectionListener(SelectionListener listener) {
			super.addSelectionListener(listener);
			if (port_text == null || path_text == null) {
				throw new RuntimeException("Create controls first!");
			}
			port_text.addSelectionListener(listener);
			path_text.addSelectionListener(listener);
		}

		@Override
		public void removeSelectionListener(SelectionListener listener) {
			super.removeSelectionListener(listener);
			if (port_text != null) {
				port_text.removeSelectionListener(listener);
			}
			if(path_text != null) {
				path_text.removeSelectionListener(listener);
			}
		}
		
	// ---------- ISettingsChangedListener ----------
	
	@Override
	public void settingsChanged(Enum<?> item) {
		// TODO Auto-generated method stub
		
	}
}
