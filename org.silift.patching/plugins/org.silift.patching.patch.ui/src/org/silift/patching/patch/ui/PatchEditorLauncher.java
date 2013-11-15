package org.silift.patching.patch.ui;
import java.io.File;
import java.io.FileNotFoundException;

import org.eclipse.core.runtime.IPath;
import org.eclipse.ui.IEditorLauncher;
import org.sidiff.difference.asymmetric.facade.AsymmetricDiffFacade;
import org.sidiff.difference.util.ui.UIUtil;
import org.sidiff.patching.util.PatchUtil;

public class PatchEditorLauncher implements IEditorLauncher {

	@Override
	public void open(IPath path) {
		if (PatchUtil.isPatchFile(path)) {
			File uncompressedPath = PatchUtil.extractPatch(path);
			
			try {
				// Open asymmetric difference:
				File[] patch = uncompressedPath.listFiles();
				
				for (int i = 0; i < patch.length; i++) {
					String fileName = patch[i].getName();
					if (fileName.endsWith(AsymmetricDiffFacade.ASYMMETRIC_DIFF_EXT)) {
						UIUtil.openEditor(patch[i].getAbsolutePath());
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}
