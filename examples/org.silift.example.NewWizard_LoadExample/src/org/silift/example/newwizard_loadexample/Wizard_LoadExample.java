/**
 * 
 */
package org.silift.example.newwizard_loadexample;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.dialogs.IOverwriteQuery;

/**
 * @author M
 *
 */
public class Wizard_LoadExample extends Wizard implements INewWizard {

	/**
	 * 
	 */
	public Wizard_LoadExample() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
	 */
	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		// TODO Auto-generated method stub

	}



	@Override
	public void addPages() {
		// TODO Auto-generated method stub
		
		WizardPage page1 = new Page1("Load the PI Example");
		
		addPage(page1);
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		
		// create Project
		createProject();
		
		return true;
	}
	
	private void createProjectFromZipFile(String projectName) {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IProjectDescription newProjectDescription = workspace.newProjectDescription(projectName);
		IProject newProject = workspace.getRoot().getProject(projectName);
		newProject.create(newProjectDescription, null);
		newProject.open(null);

		ZipFile zipFile = new ZipFile(workspace.getRoot().getLocation() + "/" + projectName + ".zip");
		IOverwriteQuery overwriteQuery = new IOverwriteQuery() {
		    public String queryOverwrite(String file) { return ALL; }
		};
//		//ZipLeveledStructureProvider provider = new ZipLeveledStructureProvider(zipFile);
//		List<Object> fileSystemObjects = new ArrayList<Object>();
//		Enumeration<? extends ZipEntry> entries = zipFile.entries();
//		while (entries.hasMoreElements()) {
//		    fileSystemObjects.add((Object)entries.nextElement());
//		}
//		ImportOperation importOperation = new ImportOperation(newProject.getFullPath(), new ZipEntry(projectName), provider, overwriteQuery, fileSystemObjects);
//		importOperation.setCreateContainerStructure(false);
//		importOperation.run(new NullProgressMonitor());
	}

	/**
	 * creates the project and put into the workspace
	 */
	private void createProject() {

		IWorkspace workspace = ResourcesPlugin.getWorkspace();

		IProject project = workspace.getRoot().getProject("");

		IProjectDescription projectDesc = workspace.newProjectDescription("");

		projectDesc.setLocation(null);  // Use default location

		// TODO add any natures, builders, ... required to the project description

		try {
			project.create(projectDesc, null);
			
			project.open(null);
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		

		// TODO create any folders and files you want

		
	}

}
