package org.sidiff.remote.application.ui.connector.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
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
public class RemoteApplicationUriValidationWidget extends UriValidationWidget implements ISettingsChangedListener {

	private RepositorySettings settings;
	
	// ---------- UI Elements ----------
	
	private Text port_text;
	
	// ---------- Constructor ----------
	
	public RemoteApplicationUriValidationWidget(RepositorySettings settings) {
		super("Remote Application Sever");
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
				settings.setApplicationServerPort(Integer.parseInt(port_text.getText()));
			}
		});
		
		if(settings.getApplicationServerUrl() != null && !settings.getApplicationServerUrl().isEmpty() && settings.getApplicationServerPort() > 0) {
			uri_text.setEnabled(false);
			port_text.setEnabled(false);
		}
		return container;
	}
	
	// ---------- UriValidationWidget ----------
	@Override
	public void modifyTextHook() {
		if(validate()) {
			this.settings.setApplicationServerUrl(this.uri_text.getText());
		}

	}
	
	

	@Override
	public boolean validate() {
		return super.validate() && !this.port_text.getText().isEmpty();
	}

	@Override
	public ValidationMessage getValidationMessage() {
		if (validate()) {
			validationMessage = new ValidationMessage(ValidationType.OK, "");
		} else {
			validationMessage = new ValidationMessage(ValidationType.ERROR, "Please insert an URL and port for " + label_group);
		}
		return validationMessage;
	}	
	
	// ---------- ISettingsChangedListener ----------
	
	@Override
	public void settingsChanged(Enum<?> item) {
		// TODO Auto-generated method stub
		
	}
}
