package org.sidiff.patching.ui.wsupdate.util;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.emf.input.InputModels;

public class WSUModels extends InputModels {

	public static final int ROLE_MINE   = 0;
	public static final int ROLE_THEIRS = 1;
	public static final int ROLE_BASE   = 2;
	public static final int NUM_ROLES   = 3;

	public WSUModels(IFile fileMine, IFile fileTheirs, IFile fileBase) {
		super(fileMine, fileTheirs, fileBase);
	}

	public WSUModels(Resource resourceMine, Resource resourceTheirs, Resource resourceBase) {
		super(resourceMine, resourceTheirs, resourceBase);
	}

	public IFile getFileMine() {
		return getFiles().get(ROLE_MINE);
	}

	public IFile getFileTheirs() {
		return getFiles().get(ROLE_THEIRS);
	}

	public IFile getFileBase() {
		return getFiles().get(ROLE_BASE);
	}

	public Resource getResourceMine() {
		return getResources().get(ROLE_MINE);
	}

	public Resource getResourceTheirs() {
		return getResources().get(ROLE_THEIRS);
	}

	public Resource getResourceBase() {
		return getResources().get(ROLE_BASE);
	}

	public void setModelMine(IFile fileMine) {
		internalGetFiles().set(ROLE_MINE, fileMine);
		internalGetResources().set(ROLE_MINE, getResource(fileMine));
	}

	public void setModelTheirs(IFile fileTheirs) {
		internalGetFiles().set(ROLE_THEIRS, fileTheirs);
		internalGetResources().set(ROLE_THEIRS, getResource(fileTheirs));
	}

	public void setModelBase(IFile fileBase) {
		internalGetFiles().set(ROLE_BASE, fileBase);
		internalGetResources().set(ROLE_BASE, getResource(fileBase));
	}
}
