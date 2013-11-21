package org.silift.common.util.ui;

import java.io.File;
import java.io.FileNotFoundException;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;

public class UIUtil {

	/**
	 * Open a file with the associated editor.
	 * 
	 * @param path
	 *            The path on the file system.
	 * @throws FileNotFoundException 
	 */
	public static void openEditor(String path) throws FileNotFoundException {
		File osFile = new File(path);

		if (osFile.exists() && osFile.isFile()) {
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			IPath location = Path.fromOSString(osFile.getAbsolutePath());
			IFile file = workspace.getRoot().getFileForLocation(location);

			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			
			if (file != null) {
				// Open from workspace:
				IEditorDescriptor desc = PlatformUI.getWorkbench().
						getEditorRegistry().getDefaultEditor(file.getName());

				try {
					page.openEditor(new FileEditorInput(file), desc.getId());
				} catch (PartInitException e) {
					e.printStackTrace();
				}
			} else {
				// Open from file system:
				IFileStore fileStore = EFS.getLocalFileSystem().getStore(osFile.toURI());

				try {
					IDE.openEditorOnFileStore(page, fileStore);
				} catch (PartInitException e) {
					e.printStackTrace();
				}
			}
		} else {
			throw new FileNotFoundException();
		}
	}
}
