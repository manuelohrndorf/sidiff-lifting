package org.sidiff.patching.patch.ui;
import java.io.IOException;

import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.common.util.URI;
import org.eclipse.ui.IEditorLauncher;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.sidiff.common.file.ZipUtil;
import org.sidiff.difference.asymmetric.api.AsymmetricDiffFacade;
import org.sidiff.patching.util.PatchUtil;

public class PatchEditorLauncher implements IEditorLauncher {

	public static final String EDITOR_ID = "org.sidiff.difference.asymmetric.presentation.AsymmetricEditorID";
	public static final String ARCHIVE_URI_PREFIX = "archive:file:///";
	public static final String ARCHIVE_SEPERATOR = "!/";
	
	@Override
	public void open(IPath path) {
		if (PatchUtil.isPatchFile(path)) {
			
			// Search asymmetric difference:
			URI uri = null;
			
			try {
				for (String entry : ZipUtil.getEntries(path.toFile().toPath())) {
					if (entry.endsWith(AsymmetricDiffFacade.ASYMMETRIC_DIFF_EXT)) {
						uri = URI.createURI(ARCHIVE_URI_PREFIX + path.toOSString() + ARCHIVE_SEPERATOR + entry);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// Open editor:
			if (uri != null) {
				IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				try {
					page.openEditor(new URIEditorInput(uri), EDITOR_ID);
				} catch (PartInitException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}
