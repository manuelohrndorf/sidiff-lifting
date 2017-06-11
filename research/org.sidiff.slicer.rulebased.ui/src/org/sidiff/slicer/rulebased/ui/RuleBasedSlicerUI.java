package org.sidiff.slicer.rulebased.ui;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class RuleBasedSlicerUI extends AbstractUIPlugin {

	public static final String PLUGIN_ID = "org.sidiff.slicer.rulebased.ui";
	
	public static final String IMG_RUN_ENABLED = "run_enabled.gif";
	public static final String IMG_RUN_DISABLED = "run_disabled.gif";
	public static final String IMG_EXPANDALL = "expandall.gif";
	public static final String IMG_COLLAPSEALL = "collapseall.gif";
	
	public RuleBasedSlicerUI() {
		// TODO Auto-generated constructor stub
	}

	public static ImageDescriptor getImageDescriptor(String name) {
		return ImageDescriptor.createFromURL(FileLocator.find(Platform.getBundle(PLUGIN_ID),
				new Path(String.format("icons/%s", name)), null));
	}
}
