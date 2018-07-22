package org.sidiff.remote.application.ui.connector.console;

import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleFactory;
import org.eclipse.ui.console.IConsoleManager;

public class SiDiffClientConnectorConsoleFactory implements IConsoleFactory {

	@Override
	public void openConsole() {
		IConsoleManager consoleManager = ConsolePlugin.getDefault().getConsoleManager();
	    SiDiffClientConnectorConsole console = new SiDiffClientConnectorConsole();
	    consoleManager.addConsoles( new IConsole[] { console } );
	    consoleManager.showConsoleView( console );
	}

}
