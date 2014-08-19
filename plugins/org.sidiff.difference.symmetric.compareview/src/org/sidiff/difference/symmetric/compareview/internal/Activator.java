package org.sidiff.difference.symmetric.compareview.internal;

import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		final DifferenceSelectionController controller = DifferenceSelectionController.getInstance();
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(!PlatformUI.isWorkbenchRunning()){ 
					// wait for workbench 
				}
				while(PlatformUI.getWorkbench() == null){ 
					// really wait for workbench
				}

				PlatformUI.getWorkbench().addWindowListener(new IWindowListener() {
					
					@Override
					public void windowOpened(IWorkbenchWindow window) {
						ISelectionService selectionService = window.getSelectionService();
						selectionService.addSelectionListener(controller);
					}
					
					@Override
					public void windowDeactivated(IWorkbenchWindow window) {}
					
					@Override
					public void windowClosed(IWorkbenchWindow window) {}
					
					@Override
					public void windowActivated(IWorkbenchWindow window) {}
				});
				
				for(IWorkbenchWindow window : PlatformUI.getWorkbench().getWorkbenchWindows()){
					ISelectionService selectionService = window.getSelectionService();
					selectionService.addSelectionListener(controller);
				}
			}
		});
		thread.start();
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

}
