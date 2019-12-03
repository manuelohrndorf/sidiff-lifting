package org.sidiff.integration.preferences.connector;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

/**
 * 
 * @author cpietsch
 *
 */
public class ConnectorPreferencesPage extends PreferencePage implements IWorkbenchPreferencePage {

	private Composite container;
	
	private Group uri_group;
	
	private Group auth_group;
	
	private StringFieldEditor uri_field;
	
	private IntegerFieldEditor port_field;
	
	private StringFieldEditor user_field;
	
	private StringFieldEditor password_field;
	

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(new ScopedPreferenceStore(InstanceScope.INSTANCE, ConnectorPreferencesPlugin.PLUGIN_ID));
		setDescription("SiDiff - Remote Application Connector Preferences");
	}

	@Override
	protected Control createContents(Composite parent) {
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		GridLayout gridLayout = new GridLayout();
		
		this.container = new Composite(parent, SWT.LEFT);
		this.container.setLayoutData(gridData);	
		this.container.setLayout(gridLayout);
		
		
		this.uri_group = new Group(this.container, SWT.NONE);
		this.uri_group.setLayoutData(gridData);
		this.uri_group.setLayout(gridLayout);
		this.uri_group.setText("SiDiff Remote Application Server");
		
		this.uri_field = new StringFieldEditor(ConnectorPreferencesConstants.P_URL, "URL", this.uri_group);
		this.uri_field.setPage(this);
		this.uri_field.setPreferenceStore(getPreferenceStore());
		this.uri_field.load();
		
		this.port_field = new IntegerFieldEditor(ConnectorPreferencesConstants.P_Port, "Port", this.uri_group);
		this.port_field.setPage(this);
		this.port_field.setPreferenceStore(getPreferenceStore());
		this.port_field.load();
		
		this.auth_group = new Group(container, SWT.LEFT);
		this.auth_group.setLayoutData(gridData);
		this.auth_group.setLayout(gridLayout);
		this.auth_group.setText("Authentication");
		
		this.user_field = new StringFieldEditor(ConnectorPreferencesConstants.P_USER, "User", this.auth_group);
		this.user_field.setPage(this);
		this.user_field.setPreferenceStore(getPreferenceStore());
		this.user_field.load();
		
		this.password_field = new StringFieldEditor(ConnectorPreferencesConstants.P_PASSWORD, "Password", this.auth_group);
		this.password_field.getTextControl(this.auth_group).setEchoChar('*');
		this.password_field.setPage(this);
		this.password_field.setPreferenceStore(getPreferenceStore());
		this.password_field.load();
		
		
		return this.container;
	}
	
	@Override
	protected void performDefaults() {
		this.uri_field.loadDefault();
		this.port_field.loadDefault();
		this.user_field.loadDefault();
		this.password_field.loadDefault();
		
		super.performDefaults();
	}
	
	@Override
	public boolean performOk() {
		this.uri_field.store();;
		this.port_field.store();
		this.user_field.store();
		this.password_field.store();
		
		return super.performOk();
	}
	
}