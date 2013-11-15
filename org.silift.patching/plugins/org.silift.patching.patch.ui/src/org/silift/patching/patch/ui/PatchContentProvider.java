package org.silift.patching.patch.ui;
import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.sidiff.patching.util.PatchUtil;

public class PatchContentProvider implements ITreeContentProvider {

	@Override
	public void dispose() {
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return null;
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		
		// Read (compressed) patch file:
		if (parentElement instanceof IFile) {
			IPath path = ((IFile) parentElement).getLocation();
			
			if (PatchUtil.isPatchFile(path)) {
				
				File uncompressedPath = PatchUtil.extractPatch(path);	
				File[] patch = uncompressedPath.listFiles();
				IFile[] patchResources = new IFile[patch.length]; 
				
				for (int i = 0; i < patch.length; i++) {
					IWorkspace workspace = ResourcesPlugin.getWorkspace(); 
					IPath location = Path.fromOSString(patch[i].getAbsolutePath()); 
					patchResources[i] = workspace.getRoot().getFileForLocation(location);
				}
				
				return patchResources;	
			}
		}
		
		return null;
	}

	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		return true;
	}
}
