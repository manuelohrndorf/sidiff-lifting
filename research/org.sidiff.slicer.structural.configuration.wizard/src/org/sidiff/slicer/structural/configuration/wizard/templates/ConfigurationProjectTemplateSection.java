package org.sidiff.slicer.structural.configuration.wizard.templates;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.pde.core.plugin.IPluginBase;
import org.eclipse.pde.core.plugin.IPluginElement;
import org.eclipse.pde.core.plugin.IPluginExtension;
import org.eclipse.pde.core.plugin.IPluginModelBase;
import org.eclipse.pde.core.plugin.IPluginModelFactory;
import org.eclipse.pde.core.plugin.IPluginReference;
import org.eclipse.pde.ui.templates.OptionTemplateSection;
import org.eclipse.pde.ui.templates.PluginReference;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.ide.IDE;
import org.sidiff.slicer.configuration.project.builder.SlicingConfigurationProjectNature;
import org.sidiff.slicer.configuration.project.runtime.ISlicingConfigurationProject;
import org.sidiff.slicer.structural.configuration.ConfigurationFactory;
import org.sidiff.slicer.structural.configuration.SlicingConfiguration;
import org.sidiff.slicer.structural.configuration.wizard.ConfigurationWizardPlugin;
import org.sidiff.slicer.structural.configuration.wizard.pages.ConfigurationModelWizardImportSelectionPage;
import org.sidiff.slicer.structural.configuration.wizard.pages.ConfigurationModelWizardPropertiesPage;
import org.sidiff.slicer.structural.configuration.wizard.util.ConfigurationWizardUtil;

public class ConfigurationProjectTemplateSection extends OptionTemplateSection
{
	protected SlicingConfiguration slicingConfiguration = null;

	/**
	 * This is the slicing configuration properties page.
	 */
	protected ConfigurationModelWizardPropertiesPage propertiesPage;

	/**
	 * This is the import selection page.
	 */
	protected ConfigurationModelWizardImportSelectionPage importSelectionPage;

	private static final String KEY_CLASS_NAME = "className"; //$NON-NLS-1$
	private static final String KEY_PROJECT_NAME = "projectName"; //$NON-NLS-1$
	private static final String KEY_CONFIGURATION_FOLDER = "configurationFolder"; //$NON-NLS-1$
	private static final String KEY_CONFIGURATION_FILE_EXTENSION = "configurationFileExtension"; //$NON-NLS-1$

	private String packageName;

	public ConfigurationProjectTemplateSection()
	{
		setPageCount(2);
		createOptions();
	}

	private void createOptions()
	{
		// the labels are not shown anywhere
		addOption(KEY_CLASS_NAME, "Class Name", null, 0); //$NON-NLS-1$
		addOption(KEY_PROJECT_NAME, "Project Name", null, 0); //$NON-NLS-1$
		addOption(KEY_CONFIGURATION_FOLDER, "Configuration Folder", null, 0); //$NON-NLS-1$
		addOption(KEY_CONFIGURATION_FILE_EXTENSION, "Configuration File Extension", null, 0); //$NON-NLS-1$
	}

	@Override
	public void addPages(Wizard wizard)
	{
		if(slicingConfiguration == null)
		{
			slicingConfiguration = ConfigurationFactory.eINSTANCE.createSlicingConfiguration();
		}

		importSelectionPage = new ConfigurationModelWizardImportSelectionPage(slicingConfiguration);
		wizard.addPage(importSelectionPage);

		propertiesPage = new ConfigurationModelWizardPropertiesPage();
		wizard.addPage(propertiesPage);

		markPagesAdded();
	}

	/**
	 * This is the folder relative to your install url and template directory
	 * where the templates are stored.
	 */
	@Override
	public String getSectionId()
	{
		return "configurationproject"; //$NON-NLS-1$
	}

	@Override
	public void initializeFields(IPluginModelBase model)
	{
		final String id = model.getPluginBase().getId();
		initializeOption(KEY_PACKAGE_NAME, getFormattedPackageName(id));
		initializeOption(KEY_CLASS_NAME, model.getPluginBase().getName().replaceAll("\\s+", "") + "SlicingConfigurationProject"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		initializeOption(KEY_PROJECT_NAME, model.getPluginBase().getName());
		initializeOption(KEY_CONFIGURATION_FOLDER, "configurations"); //$NON-NLS-1$
		initializeOption(KEY_CONFIGURATION_FILE_EXTENSION,
				ConfigurationWizardPlugin.INSTANCE.getString("_UI_ConfigurationEditorFilenameExtension")); //$NON-NLS-1$
		this.packageName = getFormattedPackageName(id);
	}

	@Override
	public void execute(IProject project, IPluginModelBase model, IProgressMonitor monitor) throws CoreException
	{
		super.execute(project, model, monitor);
		generateSlicingConfiguration(project, model);
	}

	protected void generateSlicingConfiguration(IProject project, IPluginModelBase model)
	{
		if(slicingConfiguration == null)
		{
			try
			{
				// create the configurations folder anyway
				project.getFolder(getStringOption(KEY_CONFIGURATION_FOLDER)).create(false, true, null);
			}
			catch(CoreException e)
			{
				ConfigurationWizardPlugin.INSTANCE.log(e);
			}
			return;
		}

		try
		{
			final IFile modelFile = project.getFile(getStringOption(KEY_CONFIGURATION_FOLDER) + "/" + //$NON-NLS-1$
				model.getPluginBase().getName().replaceAll("\\s+", "") + "." + getStringOption(KEY_CONFIGURATION_FILE_EXTENSION)); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
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

			PlatformUI.getWorkbench().getProgressService().run(false, false, operation);

			ConfigurationWizardUtil.revealSelection(PlatformUI.getWorkbench(), new StructuredSelection(modelFile));

			// must be run on UI thread
			Display.getCurrent().asyncExec(new Runnable()
			{
				@Override
				public void run()
				{
					try
					{
						IDE.openEditor(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage(), modelFile, true);
					}
					catch(PartInitException exception)
					{
						MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
								ConfigurationWizardPlugin.INSTANCE.getString("_UI_OpenEditorError_label"), //$NON-NLS-1$
								exception.getMessage());
					}
				}
			});
		}
		catch(Exception exception)
		{
			ConfigurationWizardPlugin.INSTANCE.log(exception);
		}
	}

	@Override
	protected void updateModel(IProgressMonitor monitor) throws CoreException
	{
		IPluginBase plugin = model.getPluginBase();
		IPluginModelFactory factory = model.getPluginFactory();

		// extension for ISlicingConfigurationProject
		{
			IPluginExtension extension = createExtension(ISlicingConfigurationProject.EXTENSION_POINT_ID, true);
	
			IPluginElement element = factory.createElement(extension);
			element.setName("project"); //$NON-NLS-1$
	
			String fullClassName = getStringOption(KEY_PACKAGE_NAME) + "." + getStringOption(KEY_CLASS_NAME); //$NON-NLS-1$
			element.setAttribute("class", fullClassName); //$NON-NLS-1$
	
			extension.add(element);
	
			plugin.add(extension);
		}

		// add the nature to the project
		// this also adds the builder to the project
		try
		{
			IProjectDescription description = project.getDescription();
			String[] natures = description.getNatureIds();
			String[] newNatures = new String[natures.length + 1];
			System.arraycopy(natures, 0, newNatures, 0, natures.length);
			newNatures[natures.length] = SlicingConfigurationProjectNature.NATURE_ID;
			description.setNatureIds(newNatures);
			project.setDescription(description, null);
		}
		catch(CoreException e)
		{
			ConfigurationWizardPlugin.INSTANCE.log(e);
		}
	}

	@Override
	public String getUsedExtensionPoint()
	{
		return ISlicingConfigurationProject.EXTENSION_POINT_ID;
	}

	/**
	 * The location of your plugin supplying the template content
	 */
	@Override
	protected URL getInstallURL()
	{
		return ConfigurationWizardPlugin.getPlugin().getBundle().getEntry("/"); //$NON-NLS-1$
	}

	@Override
	protected ResourceBundle getPluginResourceBundle()
	{
		return Platform.getResourceBundle(ConfigurationWizardPlugin.getPlugin().getBundle());
	}

	/**
	 * You can use this method to add files relative to your section id
	 */
	@Override
	public String[] getNewFiles()
	{
		return new String[0];
	}

	@Override
	public IPluginReference[] getDependencies(String schemaVersion)
	{
		List<PluginReference> result = new ArrayList<>();
		result.add(new PluginReference("org.eclipse.ui", null, 0)); //$NON-NLS-1$
		result.add(new PluginReference("org.eclipse.core.runtime", null, 0)); //$NON-NLS-1$
		result.add(new PluginReference("org.eclipse.core.resources", null, 0)); //$NON-NLS-1$
		result.add(new PluginReference("org.sidiff.slicer", null, 0)); //$NON-NLS-1$
		result.add(new PluginReference("org.sidiff.slicer.structural", null, 0)); //$NON-NLS-1$
		result.add(new PluginReference("org.sidiff.slicer.structural.configuration", null, 0)); //$NON-NLS-1$
		result.add(new PluginReference("org.sidiff.slicer.configuration.project", null, 0)); //$NON-NLS-1$
		return result.toArray(new IPluginReference[0]);
	}

	@Override
	public boolean isDependentOnParentWizard()
	{
		return true;
	}

	@Override
	public int getNumberOfWorkUnits()
	{
		return super.getNumberOfWorkUnits() + 1;
	}

	@Override
	public String getStringOption(String name)
	{
		if(name.equals(KEY_PACKAGE_NAME))
		{
			return packageName;
		}
		return super.getStringOption(name);
	}

	protected String getFormattedPackageName(String id)
	{
		StringBuffer buffer = new StringBuffer();
		for(int i = 0; i < id.length(); i++)
		{
			char ch = id.charAt(i);
			if(buffer.length() == 0)
			{
				if(Character.isJavaIdentifierStart(ch))
					buffer.append(Character.toLowerCase(ch));
			}
			else
			{
				if(Character.isJavaIdentifierPart(ch) || ch == '.')
					buffer.append(ch);
			}
		}
		return buffer.toString().toLowerCase(Locale.ENGLISH);
	}

	@Override
	public String getDescription()
	{
		return ConfigurationWizardPlugin.INSTANCE.getString("_UI_ProjectWizard_description"); //$NON-NLS-1$
	}

	@Override
	public String getLabel()
	{
		return ConfigurationWizardPlugin.INSTANCE.getString("_UI_ProjectWizard_title"); //$NON-NLS-1$
	}
}
