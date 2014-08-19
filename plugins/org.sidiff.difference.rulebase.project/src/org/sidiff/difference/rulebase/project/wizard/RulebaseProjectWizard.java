package org.sidiff.difference.rulebase.project.wizard;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.pde.internal.core.ClasspathComputer;
import org.eclipse.pde.internal.core.natures.PDE;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;
import org.sidiff.difference.rulebase.builder.RuleBaseBuilder;
import org.sidiff.difference.rulebase.nature.RuleBaseProjectNature;
import org.sidiff.difference.rulebase.project.Activator;

/**
 * This is a simple wizard for creating a new model file.
 */
@SuppressWarnings("restriction")
public class RulebaseProjectWizard extends Wizard implements INewWizard {
	
	/**
	 * This is the file creation page.
	 */
	protected WizardNewProjectCreationPage newProjectCreationPage;
	
	/**
	 * This is the RuleBase Definition Page
	 */
	protected RulebaseDefinitionPage rulebaseDefinitionPage;

	/**
	 * The rulebase project.
	 */
	private IProject project;

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setWindowTitle(Activator.INSTANCE.getString("_UI_Wizard_label"));
	}

	/**
	 * Do the work after everything is specified.
	 */
	@Override
	public boolean performFinish() {
		try {
			// Do the work within an operation:
			WorkspaceModifyOperation operation = new WorkspaceModifyOperation() {
				@Override
				protected void execute(IProgressMonitor progressMonitor) {
					try {
						// Create the rulebase project:
						createProject();
					} catch (CoreException e) {
						e.printStackTrace();
					} finally {
						progressMonitor.done();
					}
				}
			};

			// Run 'new rulebase' job:
			getContainer().run(false, false, operation);

			// Finished?
			if (project != null) {
				return true;
			} else {
				return false;
			}
		} catch (Exception exception) {
			Activator.INSTANCE.log(exception);
			return false;
		}

	}
	
	private void createProject() throws CoreException {
		
		// Create project:
		IProject project = newProjectCreationPage.getProjectHandle();
		if (!project.exists()) {
			project.create(new NullProgressMonitor());
			project.open(null);
		}
		IProjectDescription description = project.getDescription();
		description.setNatureIds(new String[] { RuleBaseProjectNature.NATURE_ID, PDE.PLUGIN_NATURE, JavaCore.NATURE_ID });
		project.setDescription(description, new NullProgressMonitor());

		// Create folders:
		IFolder srcFolder = project.getFolder("src");
		if (!srcFolder.exists()) {
			srcFolder.create(true, true, new NullProgressMonitor());
		}
		IFolder rulebaseFolder = project.getFolder(RuleBaseBuilder.BUILD_FOLDER);
		if (!rulebaseFolder.exists()) {
			rulebaseFolder.create(true, true, new NullProgressMonitor());
		}
		IFolder rulesFolder = project.getFolder(RuleBaseBuilder.SOURCE_FOLDER);
		if (!rulesFolder.exists()) {
			rulesFolder.create(true, true, new NullProgressMonitor());
		}
		IFolder metaInfFolder = project.getFolder("META-INF");
		if (!metaInfFolder.exists()) {
			metaInfFolder.create(true, true, new NullProgressMonitor());
		}

		String projectIDName = newProjectCreationPage.getProjectName();

		// Set output:
		IJavaProject javaProject = JavaCore.create(project);

		IPath binPath = project.getFullPath().append("bin");
		javaProject.setOutputLocation(binPath, null);

		// Create package and rulebase class:
		String rulebaseName = rulebaseDefinitionPage.getRulebaseName();
		IPackageFragmentRoot packageRoot = javaProject.getPackageFragmentRoot(srcFolder);
		IPackageFragment rulebasePackage = packageRoot.createPackageFragment(projectIDName, true, new NullProgressMonitor());
		ICompilationUnit rulebaseClass = rulebasePackage.createCompilationUnit(
				String.format("%sRulebase.java", rulebaseName),
					createRulebaseClassContent(rulebasePackage.getElementName(), rulebaseName, projectIDName, RuleBaseBuilder.RULEBASE_FILE), 
					true, new NullProgressMonitor());

		// Set classpath:
		String executionEnvironment = "JavaSE-1.6";

		IClasspathEntry[] newEntries = new IClasspathEntry[3];
		ClasspathComputer.setComplianceOptions(javaProject, executionEnvironment);
		newEntries[0] = JavaCore.newSourceEntry(packageRoot.getPath());
		newEntries[1] = ClasspathComputer.createJREEntry(executionEnvironment);
		newEntries[2] = ClasspathComputer.createContainerEntry();
		javaProject.setRawClasspath(newEntries, null);

		// Create MANIFEST:
		IFile manifestFile = metaInfFolder.getFile("MANIFEST.MF");
		
		// Remove camel-case:
		String regex = "([a-z])([A-Z]+)";
		String replacement = "$1 $2";
		String projectName = rulebaseName.replaceAll(regex, replacement);
		
		String manifestContents = createManifestContents(projectIDName, projectName);
		manifestFile.create(new ByteArrayInputStream(manifestContents.getBytes()), true, new NullProgressMonitor());

		// Create build.properties:
		IFile buildPropertiesFile = project.getFile("build.properties");
		String buildPropertiesContents = createBuildPropertiesContents(RuleBaseBuilder.SOURCE_FOLDER, RuleBaseBuilder.BUILD_FOLDER);
		buildPropertiesFile.create(new ByteArrayInputStream(buildPropertiesContents.getBytes()), true, new NullProgressMonitor());

		// Create plugin.xml:
		IFile pluginXMLFile = project.getFile("plugin.xml");
		String pluginXMLContents = createPluginXMLContents(
				String.format("%s.%s", rulebasePackage.getElementName(),
						rulebaseClass.getElementName().replace(".java", "")));

		pluginXMLFile.create(new ByteArrayInputStream(pluginXMLContents.getBytes()), true, new NullProgressMonitor());
		
		// Update:
		this.project = project;
		this.project.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
	}

	private String createPluginXMLContents(String rulebaseClassName) {
		return String.format(readTemplate("pluginXML.temp"), rulebaseClassName);
	}

	private String createBuildPropertiesContents(String editRuleFolder, String recognitionRuleFolder) {
		return String.format(readTemplate("buildProperties.temp"), editRuleFolder, recognitionRuleFolder);
	}

	private String createRulebaseClassContent(String packageName, String rulebaseName, String bundleId, String rulebaseFile) {
		return String.format(readTemplate("rulebaseclass.temp"), packageName, rulebaseName, bundleId, rulebaseFile);
	}

	private String createManifestContents(String bundleId, String projectName) {
		return String.format(readTemplate("manifest.temp"), bundleId, projectName);
	}

	private String readTemplate(String templateName) {
		try {
			URL templateURL = FileLocator.find(Platform.getBundle(Activator.PLUGIN_ID), new Path("templates/" + templateName), null);
			
			BufferedInputStream in = new BufferedInputStream(templateURL.openStream());
			StringBuffer template = new StringBuffer();
			int c = 0;
			while ((c = in.read()) != -1) {
				template.append((char) c);
			}
			return template.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * The framework calls this to create the contents of the wizard.
	 */
	@Override
	public void addPages() {
		
		// Create a page, set the title, and the initial model file name.
		newProjectCreationPage = new WizardNewProjectCreationPage("RulebaseWizard");
		newProjectCreationPage.setTitle("Rulebase project");
		newProjectCreationPage.setDescription("Create a new Rulebase project");
		addPage(newProjectCreationPage);

		// Create Definition page 
		rulebaseDefinitionPage = new RulebaseDefinitionPage("RulebaseWizardPage");
		rulebaseDefinitionPage.setTitle("Rulebase Definition");
		rulebaseDefinitionPage.setDescription("Specifiy settings for Rulebase creation");
		addPage(rulebaseDefinitionPage);
	}

}
