package org.sidiff.remote.application.ui.connector.widgets;

import org.eclipse.core.resources.IFolder;
import org.sidiff.common.settings.ISettingsChangedListener;
import org.sidiff.common.ui.widgets.SelectResourceWidget;
import org.sidiff.remote.application.connector.settings.CheckoutSettings;

/**
 * 
 * @author cpietsch
 *
 */
public class SelectTargetFolderWidget extends SelectResourceWidget<IFolder> implements ISettingsChangedListener {

	private CheckoutSettings settings;
	
	// ---------- Constructor ----------
	
	public SelectTargetFolderWidget(CheckoutSettings settings) {
		super("Destination Folder");
		this.settings = settings;
	}

	// ---------- SelectResourceWidget ----------
	
	@Override
	public void selectionHook() {
		this.settings.setTargetPath(resource.getLocation());
	}

	// ---------- ISettingsChangedListener ----------
	
	@Override
	public void settingsChanged(Enum<?> item) {
		// TODO Auto-generated method stub
		
	}
}
