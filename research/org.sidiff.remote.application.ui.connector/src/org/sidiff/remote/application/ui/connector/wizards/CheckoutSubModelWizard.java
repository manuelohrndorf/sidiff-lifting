package org.sidiff.remote.application.ui.connector.wizards;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.sidiff.remote.application.connector.settings.CheckoutSettings;
import org.sidiff.remote.application.ui.connector.ConnectorUIPlugin;
import org.sidiff.remote.application.ui.connector.pages.CheckoutDestinationPage;

public class CheckoutSubModelWizard extends Wizard implements INewWizard {

	/**
	 * The wizard id
	 */
	public static String ID = "org.sidiff.remote.ui.connector.wizard.co";

	/**
	 * 
	 */
	private CheckoutSettings settings;
	
	/**
	 * Holds an exception caught by an inner class
	 */
	private Exception exception;
	
	public CheckoutSubModelWizard(CheckoutSettings settings) {
		this.settings = settings;
	}
	
	// ---------- UI Elements ----------
	
	/**
	 * The delta module {@link ImageDescriptor}
	 */
//	private static final ImageDescriptor SUBMODEL = ConnectorUIPlugin.getImageDescriptor("full/wizban/newdmfolder_wiz.png");
	
	/**
	 * Remember the selection during initialization for populating the default container.
	 */
	protected IStructuredSelection selection;

	/**
	 * Remember the workbench during initialization.
	 */
	protected IWorkbench workbench;
	
	/**
	 * Holds the delta module settings page
	 */
	protected CheckoutDestinationPage projectSelectionPage;
	
	// ---------- INewWizard ----------
	
	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.workbench = workbench;
//		this.selection = selection;
//		if(selection.size() == 1){
//			IProject project = null;
//			if(selection.getFirstElement() instanceof IProject){
//				project = (IProject) selection.getFirstElement();
//			}else if(selection.getFirstElement() instanceof IResource){
//				project = ((IResource)selection.getFirstElement()).getProject();
//			}
//			
//			if(project != null){
//				
//				this.settings.setProject(project);
//			}
//			
//		}		
		setWindowTitle("Check out to project");
	}

	// ---------- Wizard ----------
	
	@Override
	public boolean performFinish() {
		
		return true;
	}

	@Override
	public void addPages() {
		this.projectSelectionPage = new CheckoutDestinationPage("ProjectSelectionPage", "Project selection page", settings);
		projectSelectionPage.setDescription("Select a target folder in the given project");
		addPage(projectSelectionPage);
	}
}
