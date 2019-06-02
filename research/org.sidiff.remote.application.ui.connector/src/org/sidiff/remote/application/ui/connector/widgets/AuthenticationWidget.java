package org.sidiff.remote.application.ui.connector.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.sidiff.common.emf.settings.ISettingsChangedListener;
import org.sidiff.common.emf.settings.ISettingsItem;
import org.sidiff.common.ui.widgets.AbstractWidget;
import org.sidiff.common.ui.widgets.IWidgetValidation.ValidationMessage.ValidationType;
import org.sidiff.remote.application.connector.settings.RepositorySettings;

/**
 * 
 * @author cpietsch
 *
 */
public class AuthenticationWidget extends AbstractWidget implements ISettingsChangedListener {
	
	/**
	 * 
	 */
	private RepositorySettings settings;

	// ---------- UI Elements ----------
	
	private Composite container;
	
	private Group credentials_group;
	
	private Text name_text;
	
	private Text password_text;
	
	// ---------- Constructor ----------

	public AuthenticationWidget(RepositorySettings settings) {
		this.settings = settings;
	}

	// ---------- IWidget ----------
	
	@Override
	public Composite createControl(Composite parent) {
		container = new Composite(parent, SWT.NONE);
		
		GridLayout gl_container = new GridLayout(1, false);
		gl_container.marginWidth = 0;
		gl_container.marginHeight = 0;
		container.setLayout(gl_container);
		
		this.credentials_group = new Group(this.container, SWT.NONE);
		
		GridLayout gl_group = new GridLayout(2, false);
		gl_group.marginWidth = 10;
		gl_group.marginHeight = 10;
		this.credentials_group.setLayout(gl_group);
		
		this.credentials_group.setLayoutData(new GridData(GridData.FILL_BOTH));
	
		this.credentials_group.setText("Authentication");
		
		Label name_label = new Label(credentials_group, SWT.NONE);
		name_label.setText("Username:");
		
		this.name_text = new Text(credentials_group, SWT.BORDER);
		this.name_text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		this.name_text.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				settings.setUserName(name_text.getText());
				getWidgetCallback().requestValidation();
			}
		});
		
		Label password_label = new Label(credentials_group, SWT.NONE);
		password_label.setText("Password:");
		
		this.password_text = new Text(credentials_group, SWT.PASSWORD | SWT.BORDER);
		this.password_text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		this.password_text.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				char[] password = password_text.getTextChars();
				settings.setPassword(password);
				getWidgetCallback().requestValidation();
			}
		});
		
		return this.container;
	}

	@Override
	public Composite getWidget() {
		return this.container;
	}

	@Override
	public void setLayoutData(Object layoutData) {
		this.container.setLayoutData(layoutData);
	}

	// ---------- IWidgetValidation ----------

	@Override
	protected ValidationMessage doValidate() {
		if(!name_text.getText().isEmpty()) {
			return ValidationMessage.OK;
		} else {
			return new ValidationMessage(ValidationType.ERROR, "Please enter your username");
		}
	}

	// ---------- ISettingsChangedListener ---------- 
	
	@Override
	public void settingsChanged(ISettingsItem item) {
		// TODO Auto-generated method stub
		
	}

	// ---------- Getter- and Setter-Methods ---------- 
	
}
