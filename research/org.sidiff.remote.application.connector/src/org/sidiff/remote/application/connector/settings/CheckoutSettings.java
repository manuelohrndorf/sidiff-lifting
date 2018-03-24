package org.sidiff.remote.application.connector.settings;

import org.eclipse.core.runtime.IPath;
import org.sidiff.common.settings.AbstractSettings;

public class CheckoutSettings extends AbstractSettings {

	
	private IPath target_path;
		
	@Override
	public boolean validateSettings() {
		// TODO Auto-generated method stub
		return false;
	}
	

	public IPath getTargetPath() {
		return target_path;
	}
	
	public void setTargetPath(IPath target_path) {
		if(target_path == null || !target_path.equals(this.target_path)) {
			this.target_path = target_path;
			notifyListeners(CheckoutSettingsItem.TARGET_FOLDER);
		}
	}
}
