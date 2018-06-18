package org.sidiff.vcmsintegration.remote.svn;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.team.svn.core.connector.SVNRevision;
import org.eclipse.team.svn.core.operation.AbstractGetFileContentOperation;
import org.eclipse.team.svn.core.operation.remote.GetFileContentOperation;
import org.eclipse.team.svn.core.resource.IRepositoryLocation;
import org.eclipse.team.svn.core.resource.IRepositoryResource;
import org.eclipse.team.svn.core.svnstorage.SVNRepositoryFile;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;

/**
 * This class allows to serialize temporary files from a SVN-Repository.
 * <p><b>Deprecated, to be rewritten more independent from the concrete repository implementation (svn, git, ...)</b></p>
 */
// TODO: This class is used by the ShowDiagramAction, which should not depend on SVN plugins directly
@Deprecated
public class SVNAccess {

	/**
	 * The target directory
	 */
	private File tmpDirectory;

	/**
	 * The SVN Repository resource
	 */
	private IRepositoryResource resource;

	/**
	 * The progress monitor
	 */
	private IProgressMonitor monitor;

	/**
	 * The {@link URI} for the model file
	 */
	private URI modelUri;

	/**
	 * Creates a new temporary directory
	 * 
	 * @param the resource which should be serialized
	 * @throws IOException
	 */
	public SVNAccess(IRepositoryResource resource) throws IOException {
		this.resource = resource;
		this.tmpDirectory = File.createTempFile("vcms-tmp", "");
		this.tmpDirectory.delete();
		this.tmpDirectory.mkdir();
	}

	/**
	 * Creates a new {@link IProgressMonitor} to fetch the diagram files form
	 * repository.
	 * 
	 * @param all file types required by the {@link IEditorIntegration}
	 * @return the {@link URI} of the model file
	 * @throws IOException
	 */
	public URI serializeRepositoryResource(final Map<String, String> extensions) throws IOException {
		IRunnableWithProgress runnable = new IRunnableWithProgress() {
			public void run(IProgressMonitor newMonitor) {
				monitor = newMonitor;
				monitor.beginTask("Trying to fetch Diagrams", IProgressMonitor.UNKNOWN);
				try {
					modelUri = makeTemporaryFiles(extensions);
				} catch (IOException e) {
					e.printStackTrace();
				}
				monitor.done();
			}
		};
		modelUri = makeTemporaryFiles(extensions);

		IWorkbench wb = PlatformUI.getWorkbench();
		IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
		Shell shell = win != null ? win.getShell() : null;
		try {
			ProgressMonitorDialog dialog = new ProgressMonitorDialog(shell);
			dialog.run(true, false, runnable);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return modelUri;
	}

	/**
	 * Creates a new temporary file for each required file depending on the
	 * extensions and the {@link IRepositoryResource}.
	 * 
	 * @param all file types required by the {@link IEditorIntegration}
	 * @return the {@link URI} of the model file
	 * @throws IOException
	 */
	private URI makeTemporaryFiles(Map<String, String> extensions) throws IOException {
		if (resource instanceof SVNRepositoryFile) {
			String url = trimFileExtension(this.resource.getUrl());
			String name = trimFileExtension(this.resource.getName());
			IRepositoryLocation location = resource.getRepositoryLocation();
			SVNRevision revision = resource.getSelectedRevision();

			for (Map.Entry<String, String> extension : extensions.entrySet()) {
				File tmpFile = new File(this.tmpDirectory, name + "." + extension.getValue());
				SVNRepositoryFile repositoryFile = new SVNRepositoryFile(location, url + "." + extension.getValue(), revision);
				if (!fetchRepositoryResource(repositoryFile, tmpFile)) {
					MessageDialog.openError(null, "Show Diagram", "Cannot find Diagram files for selected Revision");
					return null;
				}
				if (extension.getKey().equals("model")) {
					return URI.createFileURI(tmpFile.getAbsolutePath());
				}
			}
		} else {
			LogUtil.log(LogEvent.ERROR, "The given resource type is not supported: " + resource);
		}
		return null;
	}

	/**
	 * Fetches the {@link IRepositoryResource} in the temporary directory.
	 * 
	 * @param resource
	 * @param target
	 * @return true when operation was successful
	 * @throws IOException
	 */
	private boolean fetchRepositoryResource(IRepositoryResource resource, File target) throws IOException {
		AbstractGetFileContentOperation fileContent = new GetFileContentOperation(resource);
		fileContent.run(monitor);
		try(InputStream inStream = fileContent.getContent()) {
			return Files.copy(inStream, target.toPath(), StandardCopyOption.REPLACE_EXISTING) > 0;
		}
	}

	/**
	 * Helper method to trim the file extension from a string.
	 * 
	 * @param fileWithExtionsion
	 * @return fileWithoutExtension
	 */
	private String trimFileExtension(String file) {
		final int index = file.lastIndexOf(".");
		if (index > 0)
			file = file.substring(0, index);
		return file;
	}
}
