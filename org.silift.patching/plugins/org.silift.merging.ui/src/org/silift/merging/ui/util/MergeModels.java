package org.silift.merging.ui.util;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.difference.lifting.facade.LiftingFacade;
import org.silift.common.util.access.EMFModelAccessEx;

public class MergeModels {
	private IFile fileMine;
	private IFile fileTheirs;
	private IFile fileBase;

	private Resource resourceMine;
	private Resource resourceTheirs;
	private Resource resourceBase;

	private String documentType;

	public MergeModels(IFile fileMine, IFile fileTheirs, IFile fileBase) {
		this.fileMine = fileMine;
		this.fileTheirs = fileTheirs;
		this.fileBase = fileBase;		
	}

	public MergeModels(Resource resourceMine, Resource resourceTheirs, Resource resourceBase) {
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
			resourceMine = LiftingFacade.loadModel(fileMine.getLocation().toOSString());
		}

		return resourceMine;
	}

	public Resource getResourceTheirs() {
		if (resourceTheirs == null) {
			resourceTheirs = LiftingFacade.loadModel(fileTheirs.getLocation().toOSString());
		}

		return resourceTheirs;
	}

	public Resource getResourceBase() {
		if (resourceBase == null) {
			resourceBase = LiftingFacade.loadModel(fileBase.getLocation().toOSString());
		}

		return resourceBase;
	}

	public String getDocumentType() {
		if (documentType == null) {
			documentType = EMFModelAccessEx.getCharacteristicDocumentType(getResourceMine());
		}

		return documentType;
	}

	public void setModelMine(IFile fileMine) {
		this.fileMine = fileMine;
		this.resourceMine = LiftingFacade.loadModel(this.fileMine.getLocation().toOSString());
	}

	public void setModelTheirs(IFile fileTheirs) {
		this.fileTheirs = fileTheirs;
		this.resourceTheirs = LiftingFacade.loadModel(this.fileTheirs.getLocation().toOSString());
	}

	public void setModelBase(IFile fileBase) {
		this.fileBase = fileBase;
		this.resourceBase = LiftingFacade.loadModel(this.fileBase.getLocation().toOSString());
	}

}
