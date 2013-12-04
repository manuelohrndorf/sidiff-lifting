package org.silift.patching.patch.ui;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

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
		// TODO
//		// Read (compressed) patch file:
//		if (parentElement instanceof IFile) {
//			IPath path = ((IFile) parentElement).getLocation();
//			
//			if (PatchUtil.isPatchFile(path)) {
//				
//				File uncompressedPath = PatchUtil.extractPatch(path);	
//				File[] patch = uncompressedPath.listFiles();
//				IFile[] patchResources = new IFile[patch.length]; 
//				
//				for (int i = 0; i < patch.length; i++) {
//					IWorkspace workspace = ResourcesPlugin.getWorkspace(); 
//					IPath location = Path.fromOSString(patch[i].getAbsolutePath()); 
//					patchResources[i] = workspace.getRoot().getFileForLocation(location);
//				}
//				
//				return patchResources;	
//			}
//		}
		
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
