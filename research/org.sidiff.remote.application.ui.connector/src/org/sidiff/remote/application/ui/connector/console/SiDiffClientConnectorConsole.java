package org.sidiff.remote.application.ui.connector.console;

import java.nio.charset.Charset;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.console.IOConsole;
import org.sidiff.remote.application.ui.connector.ConnectorUIPlugin;

public class SiDiffClientConnectorConsole extends IOConsole {

	public static final ImageDescriptor CONSOLE_VIEW_IMG = ConnectorUIPlugin.getImageDescriptor("full/obj16/console_view.gif");
	
	public static final String CONSOLE_NAME = "SiDiff Client Connector Console";
	
	public SiDiffClientConnectorConsole () {
		this(CONSOLE_NAME, CONSOLE_VIEW_IMG);
	}
	
	public SiDiffClientConnectorConsole(String name, ImageDescriptor imageDescriptor) {
		super(name, imageDescriptor);
		// TODO Auto-generated constructor stub
	}

	public SiDiffClientConnectorConsole(String name, String consoleType, ImageDescriptor imageDescriptor,
			boolean autoLifecycle) {
		super(name, consoleType, imageDescriptor, autoLifecycle);
		// TODO Auto-generated constructor stub
	}

	public SiDiffClientConnectorConsole(String name, String consoleType, ImageDescriptor imageDescriptor,
			Charset charset, boolean autoLifecycle) {
		super(name, consoleType, imageDescriptor, charset, autoLifecycle);
		// TODO Auto-generated constructor stub
	}

	public SiDiffClientConnectorConsole(String name, String consoleType, ImageDescriptor imageDescriptor,
			String encoding, boolean autoLifecycle) {
		super(name, consoleType, imageDescriptor, encoding, autoLifecycle);
		// TODO Auto-generated constructor stub
	}

	public SiDiffClientConnectorConsole(String name, String consoleType, ImageDescriptor imageDescriptor) {
		super(name, consoleType, imageDescriptor);
		// TODO Auto-generated constructor stub
	}

	

}
