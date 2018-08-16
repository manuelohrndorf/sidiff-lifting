package org.sidiff.remote.application.ui.connector.widgets;

import java.util.List;

import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.sidiff.common.settings.ISettingsChangedListener;
import org.sidiff.common.ui.widgets.IWidgetValidation.ValidationMessage.ValidationType;
import org.sidiff.common.ui.widgets.UriValidationWidget;
import org.sidiff.remote.application.connector.ConnectorFacade;
import org.sidiff.remote.application.connector.exception.ConnectionException;
import org.sidiff.remote.application.connector.exception.InvalidSessionException;
import org.sidiff.remote.application.connector.exception.RemoteApplicationException;
import org.sidiff.remote.application.connector.settings.RepositorySettings;
import org.sidiff.remote.application.connector.settings.RepositorySettingsItem;
import org.sidiff.remote.application.ui.connector.dialogs.InteractiveElementTreeSelectionDialog;
import org.sidiff.remote.application.ui.connector.model.AdaptableTreeModel;
import org.sidiff.remote.application.ui.connector.model.ModelUtil;
import org.sidiff.remote.application.ui.connector.providers.TreeModelContentProvider;
import org.sidiff.remote.application.ui.connector.providers.TreeModelLabelProvider;
import org.sidiff.remote.common.ProxyObject;

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
	
	private Button browse_button;
	
	// ---------- Constructor ----------
	
	public RepositoryUriValidationWidget(RepositorySettings settings) {
		super("Repository");
		this.settings = settings;
		this.settings.addSettingsChangedListener(this);
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
		GridData gd_uri_group_input = new GridData();
		gd_uri_group_input.horizontalSpan = 2;
		port_text.setLayoutData(gd_uri_group_input);
		
		Label path_label = new Label(uri_group, SWT.NONE);
		path_label.setText("Path:");
		path_text = new Text(uri_group, SWT.BORDER);
		path_text.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				settings.setRepositoryPath(path_text.getText());
				path_text.notifyListeners(SWT.Selection, new Event());
			}
		});
		
		GridData gd_path_text = new GridData(GridData.FILL_HORIZONTAL);
		path_text.setLayoutData(gd_path_text);
		
		browse_button = new Button(uri_group, SWT.PUSH);
		browse_button.setText("Browse...");
		browse_button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					List<ProxyObject> proxyObjects =  ConnectorFacade.browseRepositoryContent(settings.getRepositoryURL(), settings.getRepositoryPort(), "", settings.getUserName(), settings.getPassword());
					AdaptableTreeModel model = new AdaptableTreeModel();
					model.getRoot().addAllChildren(ModelUtil.transform(proxyObjects));				
					InteractiveElementTreeSelectionDialog dialog = new InteractiveElementTreeSelectionDialog(container.getShell(), new TreeModelLabelProvider(new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE)), new TreeModelContentProvider(), settings);
					dialog.setInput(model);
					dialog.setTitle("Browse " + settings.getRepositoryURL());
					int result = dialog.open();
					
					if(result == InteractiveElementTreeSelectionDialog.CANCEL) {
						settings.setRepositoryPath("");
					}
				}catch(ConnectionException | InvalidSessionException | RemoteApplicationException exception) {
					MessageDialog.openError(container.getShell(), "Error", exception.getMessage());
				}
			}
		});
		return container;
	}
	
	// ---------- UriValidationWidget ----------
	
	@Override
	public void modifyTextHook() {
		this.settings.setRepositoryURL(this.uri_text.getText());
		uri_text.notifyListeners(SWT.Selection, new Event());

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
		return s.matches("(/?[a-zA-z0-9])+(([_\\-\\./])([a-zA-z0-9])+)*");
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
		if(item.equals(RepositorySettingsItem.REP_PATH)) {
			path_text.setText(settings.getRepositoryPath());
		}
		
	}
}
