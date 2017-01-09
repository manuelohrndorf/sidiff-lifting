package org.sidiff.patching.ui.wsupdate.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.difference.lifting.api.util.PipelineUtils;

public class WSUModels {
	private IFile fileMine;
	private IFile fileTheirs;
	private IFile fileBase;

	private Resource resourceMine;
	private Resource resourceTheirs;
	private Resource resourceBase;

	private Set<String> documentTypes;

	public WSUModels(IFile fileMine, IFile fileTheirs, IFile fileBase) {
		this.fileMine = fileMine;
		this.fileTheirs = fileTheirs;
		this.fileBase = fileBase;		
	}

	public WSUModels(Resource resourceMine, Resource resourceTheirs, Resource resourceBase) {
		this.resourceMine = resourceMine;
		this.resourceTheirs = resourceTheirs;
		this.resourceBase = resourceBase;
	}


	public IFile getFileMine() {
		if (fileMine == null) {
			URI uri = resourceMine.getURI();
			String fileString = URI.decode(uri.path());
			fileMine = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(fileString));
		}

		return fileMine;
	}

	public IFile getFileTheirs() {
		if (fileTheirs == null) {
			URI uri = resourceTheirs.getURI();
			String fileString = URI.decode(uri.path());
			fileTheirs = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(fileString));
		}

		return fileTheirs;
	}
	public IFile getFileBase() {
		if (fileBase == null) {
			URI uri = resourceBase.getURI();
			String fileString = URI.decode(uri.path());
			fileBase = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(fileString));
		}

		return fileBase;
	}
	
	public Resource getResourceMine() {
		if (resourceMine == null) {
			resourceMine = PipelineUtils.loadModel(fileMine.getLocation().toOSString());
		}

		return resourceMine;
	}

	public Resource getResourceTheirs() {
		if (resourceTheirs == null) {
			resourceTheirs = PipelineUtils.loadModel(fileTheirs.getLocation().toOSString());
		}

		return resourceTheirs;
	}

	public Resource getResourceBase() {
		if (resourceBase == null) {
			resourceBase = PipelineUtils.loadModel(fileBase.getLocation().toOSString());
		}

		return resourceBase;
	}

	public Set<String> getDocumentTypes() {
		if (documentTypes == null) {
			List<Resource> resources = new ArrayList<Resource>();
			resources.add(getResourceMine());
			resources.add(getResourceTheirs());
			resources.add(getResourceBase());
			documentTypes = EMFModelAccess.getDocumentTypes(resources);
		}

		return documentTypes;
	}

	public void setModelMine(IFile fileMine) {
		this.fileMine = fileMine;
		this.resourceMine = PipelineUtils.loadModel(this.fileMine.getLocation().toOSString());
	}

	public void setModelTheirs(IFile fileTheirs) {
		this.fileTheirs = fileTheirs;
		this.resourceTheirs = PipelineUtils.loadModel(this.fileTheirs.getLocation().toOSString());
	}

	public void setModelBase(IFile fileBase) {
		this.fileBase = fileBase;
		this.resourceBase = PipelineUtils.loadModel(this.fileBase.getLocation().toOSString());
	}

}
