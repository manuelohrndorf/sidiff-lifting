package org.sidiff.slicer.structural.configuration.wizard.pages;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.sidiff.slicer.structural.configuration.wizard.ConfigurationWizardPlugin;

public class ConfigurationModelWizardNewFileCreationPage extends WizardNewFileCreationPage
{
	/**
	 * The supported extension for created files.
	 */
	public static final String FILE_EXTENSION = ConfigurationWizardPlugin.INSTANCE.getString("_UI_ConfigurationEditorFilenameExtension"); //$NON-NLS-1$

	/**
	 * Pass in the selection.
	 */
	public ConfigurationModelWizardNewFileCreationPage(IStructuredSelection selection)
	{
		super(ConfigurationWizardPlugin.INSTANCE.getString("_UI_ConfigurationModelWizard_page_filename_title"), selection); //$NON-NLS-1$
		setTitle(ConfigurationWizardPlugin.INSTANCE.getString("_UI_ConfigurationModelWizard_page_filename_title")); //$NON-NLS-1$
		setDescription(ConfigurationWizardPlugin.INSTANCE.getString("_UI_ConfigurationModelWizard_page_filename")); //$NON-NLS-1$
		setFileName(ConfigurationWizardPlugin.INSTANCE.getString("_UI_ConfigurationEditorFilenameDefaultBase") + "." + FILE_EXTENSION); //$NON-NLS-1$ //$NON-NLS-2$
		initSelection(selection);
	}

	protected void initSelection(IStructuredSelection selection)
	{
		// Try and get the resource selection to determine a current directory for the file dialog.
		//
		if(selection != null && !selection.isEmpty())
		{
			// Get the resource...
			//
			Object selectedElement = selection.iterator().next();
			if(selectedElement instanceof IResource)
			{
				// Get the resource parent, if its a file.
				//
				IResource selectedResource = (IResource)selectedElement;
				if(selectedResource.getType() == IResource.FILE)
				{
					selectedResource = selectedResource.getParent();
				}

				// This gives us a directory...
				//
				if(selectedResource instanceof IFolder || selectedResource instanceof IProject)
				{
					// Set this for the container.
					//
					setContainerFullPath(selectedResource.getFullPath());

					// Make up a unique new name here.
					//
					String defaultModelBaseFilename = ConfigurationWizardPlugin.INSTANCE.getString("_UI_ConfigurationEditorFilenameDefaultBase"); //$NON-NLS-1$
					String modelFilename = defaultModelBaseFilename + "." + FILE_EXTENSION; //$NON-NLS-1$
					for(int i = 1; ((IContainer)selectedResource).findMember(modelFilename) != null; ++i)
					{
						modelFilename = defaultModelBaseFilename + i + "." + FILE_EXTENSION; //$NON-NLS-1$
					}
					setFileName(modelFilename);
				}
			}
		}
	}

	/**
	 * The framework calls this to see if the file is correct.
	 */
	@Override
	protected boolean validatePage()
	{
		if(super.validatePage())
		{
			String extension = new Path(getFileName()).getFileExtension();
			if(!FILE_EXTENSION.equals(extension))
			{
				setErrorMessage(ConfigurationWizardPlugin.INSTANCE.getString("_WARN_FilenameExtension", new Object[] { FILE_EXTENSION })); //$NON-NLS-1$
				return false;
			}
			return true;
		}
		return false;
	}

	public IFile getModelFile()
	{
		return ResourcesPlugin.getWorkspace().getRoot().getFile(getContainerFullPath().append(getFileName()));
	}
}
