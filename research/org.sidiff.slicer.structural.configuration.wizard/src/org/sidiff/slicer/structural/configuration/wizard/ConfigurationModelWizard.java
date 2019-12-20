package org.sidiff.slicer.structural.configuration.wizard;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.ide.IDE;
import org.sidiff.slicer.structural.configuration.ConfigurationFactory;
import org.sidiff.slicer.structural.configuration.SlicingConfiguration;
import org.sidiff.slicer.structural.configuration.wizard.pages.ConfigurationModelWizardImportSelectionPage;
import org.sidiff.slicer.structural.configuration.wizard.pages.ConfigurationModelWizardNewFileCreationPage;
import org.sidiff.slicer.structural.configuration.wizard.pages.ConfigurationModelWizardPropertiesPage;
import org.sidiff.slicer.structural.configuration.wizard.util.ConfigurationWizardUtil;

/**
 * This is a wizard for creating a new slicing configuration.
 */
public class ConfigurationModelWizard extends Wizard implements INewWizard
{
	/**
	 * This is the file creation page.
	 */
	protected ConfigurationModelWizardNewFileCreationPage newFileCreationPage;

	/**
	 * This is the slicing configuration properties page.
	 */
	protected ConfigurationModelWizardPropertiesPage propertiesPage;

	/**
	 * This is the import selection page.
	 */
	protected ConfigurationModelWizardImportSelectionPage importSelectionPage;

	/**
	 * Remember the selection during initialization for populating the default
	 * container.
	 */
	protected IStructuredSelection selection;

	/**
	 * Remember the workbench during initialization.
	 */
	protected IWorkbench workbench;

	/**
	 * The created slicing configuration
	 */
	protected SlicingConfiguration slicingConfiguration = null;

	/**
	 * This just records the information.
	 */
	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection)
	{
		this.workbench = workbench;
		this.selection = selection;
		setWindowTitle(ConfigurationWizardPlugin.INSTANCE.getString("_UI_ConfigurationModelWizard_title")); //$NON-NLS-1$
		setDefaultPageImageDescriptor(ExtendedImageRegistry.INSTANCE.getImageDescriptor(ConfigurationWizardPlugin.INSTANCE.getImage("full/wizban/NewConfiguration"))); //$NON-NLS-1$
	}

	/**
	 * Do the work after everything is specified.
	 */
	@Override
	public boolean performFinish()
	{
		try
		{
			final IFile modelFile = newFileCreationPage.getModelFile();
			propertiesPage.updateSlicingConfiguration(slicingConfiguration);

			WorkspaceModifyOperation operation = new WorkspaceModifyOperation()
			{
				@Override
				protected void execute(IProgressMonitor progressMonitor)
				{
					try
					{
						ResourceSet resourceSet = new ResourceSetImpl();
						URI fileURI = URI.createPlatformResourceURI(modelFile.getFullPath().toString(), true);
						Resource resource = resourceSet.createResource(fileURI);
						resource.getContents().add(slicingConfiguration);

						Map<Object, Object> options = new HashMap<Object, Object>();
						options.put(XMLResource.OPTION_ENCODING, propertiesPage.getEncoding());
						resource.save(options);
					}
					catch(Exception exception)
					{
						ConfigurationWizardPlugin.INSTANCE.log(exception);
					}
					finally
					{
						progressMonitor.done();
					}
				}
			};

			getContainer().run(false, false, operation);

			ConfigurationWizardUtil.revealSelection(workbench, new StructuredSelection(modelFile));

			try
			{
				IDE.openEditor(workbench.getActiveWorkbenchWindow().getActivePage(), modelFile, true);
			}
			catch(PartInitException exception)
			{
				MessageDialog.openError(workbench.getActiveWorkbenchWindow().getShell(),
						ConfigurationWizardPlugin.INSTANCE.getString("_UI_OpenEditorError_label"), //$NON-NLS-1$
						exception.getMessage());
				return false;
			}

			return true;
		}
		catch(Exception exception)
		{
			ConfigurationWizardPlugin.INSTANCE.log(exception);
			return false;
		}
	}

	/**
	 * The framework calls this to create the contents of the wizard.
	 */
	@Override
	public void addPages()
	{
		if(slicingConfiguration == null)
		{
			slicingConfiguration = ConfigurationFactory.eINSTANCE.createSlicingConfiguration();
		}

		newFileCreationPage = new ConfigurationModelWizardNewFileCreationPage(selection);
		addPage(newFileCreationPage);

		importSelectionPage = new ConfigurationModelWizardImportSelectionPage(slicingConfiguration);
		addPage(importSelectionPage);

		propertiesPage = new ConfigurationModelWizardPropertiesPage();
		addPage(propertiesPage);
	}
}
