package org.sidiff.remote.application.ui.connector.console;

import java.io.IOException;
import java.nio.charset.Charset;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.console.IOConsole;
import org.eclipse.ui.console.IOConsoleOutputStream;
import org.sidiff.remote.application.ui.connector.ConnectorUIPlugin;

public class SiDiffClientConnectorConsole extends IOConsole {
	
	public static final String CONSOLE_NAME = "SiDiff Client Connector Console";
	
	public SiDiffClientConnectorConsole () {
		this(CONSOLE_NAME, ConnectorUIPlugin.IMG_CONSOLE_VIEW);
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

	public void log(LogEvent logEvent, String message) {
		 IOConsoleOutputStream out = newOutputStream();
		 try {
			out.write(logEvent + ": " + message);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				out.flush();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	public enum LogEvent {
		ERROR,
		NOTICE,
		WARNING
	}
}
