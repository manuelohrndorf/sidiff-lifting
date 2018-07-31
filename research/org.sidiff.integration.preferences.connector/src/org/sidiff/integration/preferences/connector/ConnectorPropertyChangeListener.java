package org.sidiff.integration.preferences.connector;

import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.sidiff.remote.common.Credentials;

public class ConnectorPropertyChangeListener implements IPropertyChangeListener {

	private Credentials credentials;
	

	public ConnectorPropertyChangeListener(Credentials credentials) {
		this.credentials = credentials;
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if(event.getProperty().equals(ConnectorPreferencesConstants.P_URL)) {
			this.credentials.setUrl(event.getNewValue().toString());
		}else if(event.getProperty().equals(ConnectorPreferencesConstants.P_Port)) {
			this.credentials.setPort(Integer.parseInt(event.getNewValue().toString()));
		}else if (event.getProperty().equals(ConnectorPreferencesConstants.P_USER)) {
			 this.credentials.setUser(event.getNewValue().toString());
         }else if(event.getProperty().equals(ConnectorPreferencesConstants.P_PASSWORD)) {
        	 this.credentials.setPassword(event.getNewValue().toString());
         }
		
	}

}
