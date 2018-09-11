package org.sidiff.remote.application.ui.connector;

import java.io.IOException;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
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
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.IConsoleView;
import org.eclipse.ui.console.IOConsoleOutputStream;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.sidiff.integration.preferences.connector.ConnectorPreferencesConstants;
import org.sidiff.integration.preferences.connector.ConnectorPreferencesPlugin;
import org.sidiff.integration.preferences.connector.ConnectorPropertyChangeListener;
import org.sidiff.remote.application.connector.ConnectorPlugin;
import org.sidiff.remote.application.connector.exception.InvalidProjectInfoException;
import org.sidiff.remote.application.connector.meta.ProjectInfo;
import org.sidiff.remote.application.ui.connector.console.SiDiffClientConnectorConsole;
import org.sidiff.remote.application.ui.connector.console.SiDiffClientConnectorConsole.LogEvent;

/**
 * The activator class controls the plug-in life cycle
 * 
 * @author cpietsch
 */
public class ConnectorUIPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.sidiff.remote.application.ui.connector"; //$NON-NLS-1$
	
	// View Icons
	public static final ImageDescriptor IMG_CONSOLE_VIEW = ConnectorUIPlugin.getImageDescriptor("full/obj16/console_view.gif");
	
	// Object Icons
	public static final ImageDescriptor IMG_OBJ16_FILE = ConnectorUIPlugin.getImageDescriptor("full/obj16/file_obj.gif");
	
	public static final ImageDescriptor IMG_OBJ16_FOLDER = ConnectorUIPlugin.getImageDescriptor("full/obj16/folder_obj.gif");
	
	// Enabled Action Icons
	public static final ImageDescriptor IMG_ELCL16_CHECKIN_ACTION = ConnectorUIPlugin.getImageDescriptor("full/elcl16/checkin_action.png");
	
	public static final ImageDescriptor IMG_ELCL16_CHECKOUT_ACTION = ConnectorUIPlugin.getImageDescriptor("full/elcl16/checkout_action.png");
	
	public static final ImageDescriptor IMG_ELCL16_COLLAPSEALL_ACTION = ConnectorUIPlugin.getImageDescriptor("full/elcl16/collapseall_action.png");
	
	public static final ImageDescriptor IMG_ELCL16_EXPANDALL_ACTION = ConnectorUIPlugin.getImageDescriptor("full/elcl16/expandall_action.png");
	
	public static final ImageDescriptor IMG_ELCL16_NEW_LOCATION_ACTION = ConnectorUIPlugin.getImageDescriptor("full/elcl16/new_location_action.png");
	
	public static final ImageDescriptor IMG_ELCL16_REFRESH_REMOTE_ACTION = ConnectorUIPlugin.getImageDescriptor("full/elcl16/refresh_remote_action.png");
	
	public static final ImageDescriptor IMG_ELCL16_SELECT_SUBTREE_ACTION = ConnectorUIPlugin.getImageDescriptor("full/elcl16/select_subtree_action.gif");

	public static final ImageDescriptor IMG_ELCL16_SYNCED_ACTION = ConnectorUIPlugin.getImageDescriptor("full/elcl16/synced_action.png");
	
	public static final ImageDescriptor IMG_ELCL16_UPDATE_ACTION = ConnectorUIPlugin.getImageDescriptor("full/elcl16/update_action.png");
	
	// Disabled Action Icons
	public static final ImageDescriptor IMG_DLCL16_CHECKIN_ACTION = ConnectorUIPlugin.getImageDescriptor("full/elcl16/checkin_action.png");
	
	public static final ImageDescriptor IMG_DLCL16_CHECKOUT_ACTION = ConnectorUIPlugin.getImageDescriptor("full/elcl16/checkout_action.png");
	
	public static final ImageDescriptor IMG_DLCL16_COLLAPSEALL_ACTION = ConnectorUIPlugin.getImageDescriptor("full/elcl16/collapseall_action.png");
	
	public static final ImageDescriptor IMG_DLCL16_EXPANDALL_ACTION = ConnectorUIPlugin.getImageDescriptor("full/elcl16/expandall_action.png");
	
	public static final ImageDescriptor IMG_DLCL16_NEW_LOCATION_ACTION = ConnectorUIPlugin.getImageDescriptor("full/elcl16/new_location_action.png");
	
	public static final ImageDescriptor IMG_DLCL16_REFRESH_REMOTE_ACTION = ConnectorUIPlugin.getImageDescriptor("full/elcl16/refresh_remote_action.png");
	
	public static final ImageDescriptor IMG_DLCL16_SELECT_SUBTREE_ACTION = ConnectorUIPlugin.getImageDescriptor("full/elcl16/select_subtree_action.gif");

	public static final ImageDescriptor IMG_DLCL16_SYNCED_ACTION = ConnectorUIPlugin.getImageDescriptor("full/elcl16/synced_action.png");
	
	public static final ImageDescriptor IMG_DLCL16_UPDATE_ACTION = ConnectorUIPlugin.getImageDescriptor("full/elcl16/update_action.png");

	// Overlay Icons
	public static final ImageDescriptor IMG_OVR_VERSION_CONTROLLED = ConnectorUIPlugin.getImageDescriptor("full/ovr/version_controlled.png");

	// The shared instance
	private static ConnectorUIPlugin plugin;
	
	private static SiDiffClientConnectorConsole console;
	
	/**
	 * The constructor
	 */
	public ConnectorUIPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		
		initConsole();
		
		IPreferenceStore store = ConnectorPreferencesPlugin.getDefault().getPreferenceStore();
		store.addPropertyChangeListener(new IPropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				switch(event.getProperty()) {
				case ConnectorPreferencesConstants.P_URL:
					break;
				case ConnectorPreferencesConstants.P_Port:
					break;
				case ConnectorPreferencesConstants.P_USER:
					break;
				case ConnectorPreferencesConstants.P_PASSWORD:
					break;
				default:
				}
				
			}
		});
		
		String url = store.getString(ConnectorPreferencesConstants.P_URL);
		int port = store.getInt(ConnectorPreferencesConstants.P_Port);
		String user = store.getString(ConnectorPreferencesConstants.P_USER);
		String password = store.getString(ConnectorPreferencesConstants.P_PASSWORD);
		ConnectorPlugin.getInstance().init(url, port, user, password);
		
		store.addPropertyChangeListener(new ConnectorPropertyChangeListener(ConnectorPlugin.getInstance().getCredentials()));
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static ConnectorUIPlugin getDefault() {
		return plugin;
	}
	
	public static void initConsole() {
		if(ConnectorUIPlugin.console == null) {
			IConsoleManager consoleManager = ConsolePlugin.getDefault().getConsoleManager();
			for(IConsole console : consoleManager.getConsoles()) {
				if(console.getName().equals(SiDiffClientConnectorConsole.CONSOLE_NAME)) {
					ConnectorUIPlugin.console = (SiDiffClientConnectorConsole) console;
					return;
				}
			}
			ConnectorUIPlugin.console = new SiDiffClientConnectorConsole();
			consoleManager.addConsoles(new IConsole[] { console });
			consoleManager.showConsoleView( console );
		}
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
	
	public static void printMessage(String msg) {
		IOConsoleOutputStream out = console.newOutputStream();
		out.setActivateOnWrite(true);
		try {
			out.write(msg);
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, "icons/" + path);
	}
	
	public static SiDiffClientConnectorConsole getClientConnectorConsole() {
		return console;
	}
}
