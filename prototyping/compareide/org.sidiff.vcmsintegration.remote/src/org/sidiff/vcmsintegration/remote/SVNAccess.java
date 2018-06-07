package org.sidiff.vcmsintegration.remote;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
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
 * 
 *
 */
public class SVNAccess {
	/**
	 * The target directory
	 */
	public File tmpDirectory;
	/**
	 * The SVN Repository resource
	 */
	public IRepositoryResource resource;
	/**
	 * The progress monitor
	 */
	public IProgressMonitor monitor;

	/**
	 * The {@link URI} for the model file
	 */
	public URI modelUri;

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
	public URI serializeRepositoryResource(final HashMap<String, String> extensions) throws IOException {
		IRunnableWithProgress runnable = new IRunnableWithProgress() {
			public void run(IProgressMonitor newMonitor) {
				monitor = newMonitor;
				monitor.beginTask("Trying to fetch Diagrams", IProgressMonitor.UNKNOWN);
				try {
					modelUri = makeTemporaryFiles(extensions);
				} catch (IOException e) {
					// TODO Auto-generated catch block
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
	private URI makeTemporaryFiles(HashMap<String, String> extensions) throws IOException {
		URI result = null;
		String url = trimFileExtension(this.resource.getUrl());
		String name = trimFileExtension(this.resource.getName());

		if (resource instanceof SVNRepositoryFile) {
			IRepositoryLocation location = resource.getRepositoryLocation();
			SVNRevision revision = resource.getSelectedRevision();
			SVNRepositoryFile repositoryFile = null;
			File tmpFile = null;
			for (Map.Entry<String, String> extension : extensions.entrySet()) {
				tmpFile = new File(this.tmpDirectory, name + "." + extension.getValue());
				repositoryFile = new SVNRepositoryFile(location, url + "." + extension.getValue(), revision);
				if (!fetchRepositoryResource(repositoryFile, tmpFile)) {
					MessageDialog.openError(null, "Show Diagram", "Cannot find Diagram files for selected Revision");
					return null;
				}
				if (extension.getKey().equals("model")) {
					result = URI.createFileURI(tmpFile.getAbsolutePath());
				}
			}
		} else {
			LogUtil.log(LogEvent.ERROR, "The given resource type is not supported");
			return null;
		}
		return result;
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
		AbstractGetFileContentOperation fileContent = null;
		fileContent = new GetFileContentOperation(resource);
		fileContent.run(monitor);

		InputStream inputStream = fileContent.getContent();
		OutputStream outputStream = new FileOutputStream(target);

		boolean successful = false;
		byte[] bytes = new byte[4096];
		int read = inputStream.read(bytes);
		while (read != -1) {
			outputStream.write(bytes, 0, read);
			read = inputStream.read(bytes);
			successful = true;
		}
		inputStream.close();
		if (outputStream != null) {
			outputStream.close();
		}
		return successful;
	}

	/**
	 * Helper method to trim the file extension from a string.
	 * 
	 * @param fileWithExtionsion
	 * @return fileWithoutExtension
	 */
	private String trimFileExtension(String file) {
		if (file.indexOf(".") > 0)
			file = file.substring(0, file.lastIndexOf("."));
		return file;
	}
}
