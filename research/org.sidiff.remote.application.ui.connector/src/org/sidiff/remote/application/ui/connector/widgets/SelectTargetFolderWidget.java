package org.sidiff.remote.application.ui.connector.widgets;

import org.eclipse.core.resources.IFolder;
import org.sidiff.common.settings.ISettingsChangedListener;
import org.sidiff.common.ui.widgets.SelectResourceWidget;
import org.sidiff.remote.application.connector.settings.CheckoutSettings;

public class SelectTargetFolderWidget extends SelectResourceWidget<IFolder> implements ISettingsChangedListener {

	private CheckoutSettings settings;
	
	public SelectTargetFolderWidget(CheckoutSettings settings) {
		super("Destination Folder");
		this.settings = settings;
	}

	@Override
	public void settingsChanged(Enum<?> item) {
		// TODO Auto-generated method stub
		
	}

}
