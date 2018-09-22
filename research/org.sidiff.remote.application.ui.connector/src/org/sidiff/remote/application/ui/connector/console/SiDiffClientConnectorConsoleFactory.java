package org.sidiff.remote.application.ui.connector.console;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.console.IConsoleFactory;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.IConsoleView;
import org.sidiff.remote.application.connector.exception.InvalidProjectInfoException;
import org.sidiff.remote.application.connector.meta.ProjectInfo;
import org.sidiff.remote.application.ui.connector.console.SiDiffClientConnectorConsole.LogEvent;

public class SiDiffClientConnectorConsoleFactory implements IConsoleFactory {

	private SiDiffClientConnectorConsole console;
	@Override
	public void openConsole() {
		IConsoleManager consoleManager = ConsolePlugin.getDefault().getConsoleManager();
		for(IConsole console : consoleManager.getConsoles()) {
			if(console.getName().equals(SiDiffClientConnectorConsole.CONSOLE_NAME)) {
				console = (SiDiffClientConnectorConsole) console;
				break;
			}
		}
		if(console == null) {
			console = new SiDiffClientConnectorConsole();
			consoleManager.addConsoles(new IConsole[] { console });
			IWorkbench wb = PlatformUI.getWorkbench();
			IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
			IWorkbenchPage page = win.getActivePage();

			String id = IConsoleConstants.ID_CONSOLE_VIEW;
			try {
				IConsoleView view = (IConsoleView) page.showView(id);
				view.display(console);
				view.getSite().getPage().addSelectionListener(IPageLayout.ID_PROJECT_EXPLORER, new ISelectionListener() {

					@Override
					public void selectionChanged(IWorkbenchPart part, ISelection selection) {
						if (selection instanceof IStructuredSelection) {
							IStructuredSelection structuredSelection = (IStructuredSelection) selection;
							if (structuredSelection.size() == 1) {
								if (part.getSite().getId().equals(IPageLayout.ID_PROJECT_EXPLORER)
										&& structuredSelection.getFirstElement() instanceof IProject) {
									IProject project = (IProject) structuredSelection.getFirstElement();
									try {
										ProjectInfo projectInfo = ProjectInfo.readProjectInfo(project.getName());
										if (projectInfo.isConnected()) {
											console.log(LogEvent.NOTICE, projectInfo.toString());
										}
									} catch (InvalidProjectInfoException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}
						}
					}
				});
			} catch (PartInitException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		consoleManager.showConsoleView(console);
	}

	public SiDiffClientConnectorConsole getConsole() {
		if(console == null) {
			openConsole();
		}
		return console;
	}
}
