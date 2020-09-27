package org.sidiff.patching.patch.ui;

import java.io.IOException;

import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.common.util.URI;
import org.eclipse.ui.*;
import org.sidiff.common.file.ZipUtil;
import org.sidiff.difference.asymmetric.api.AsymmetricDiffFacade;

public class PatchEditorLauncher implements IEditorLauncher {

	public static final String EDITOR_ID = "org.sidiff.difference.asymmetric.presentation.AsymmetricEditorID";
	public static final String ARCHIVE_URI_PREFIX = "archive:file:///";
	public static final String ARCHIVE_SEPERATOR = "!/";

	@Override
	public void open(IPath path) {
		// Search asymmetric difference:
		try {
			for (String entry : ZipUtil.getEntries(path.toFile().toPath())) {
				if (entry.endsWith(AsymmetricDiffFacade.ASYMMETRIC_DIFF_EXT)) {
					URI uri = URI.createURI(ARCHIVE_URI_PREFIX + path.toOSString() + ARCHIVE_SEPERATOR + entry);
					IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					try {
						page.openEditor(new URIEditorInput(uri), EDITOR_ID);
					} catch (PartInitException e) {
						throw new RuntimeException(e);
					}
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
