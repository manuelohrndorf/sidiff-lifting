package org.sidiff.remote.application.connector.settings;

import java.util.Set;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.MultiStatus;
import org.sidiff.common.emf.settings.AbstractSettings;

public class CheckoutSettings extends AbstractSettings {

	
	private IPath target_path;
	

	public IPath getTargetPath() {
		return target_path;
	}
	
	public void setTargetPath(IPath target_path) {
		if(target_path == null || !target_path.equals(this.target_path)) {
			this.target_path = target_path;
			notifyListeners(CheckoutSettingsItem.TARGET_FOLDER);
		}
	}


	@Override
	protected void validate(MultiStatus multiStatus) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initDefaults(Set<String> documentTypes) {
		// TODO Auto-generated method stub
		
	}
}
