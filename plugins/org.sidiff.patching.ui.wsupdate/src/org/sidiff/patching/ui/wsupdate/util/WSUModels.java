package org.sidiff.patching.ui.wsupdate.util;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.emf.input.InputModels;
import org.sidiff.common.emf.modelstorage.SiDiffResourceSet;

public class WSUModels extends InputModels {

	public static final int ROLE_MINE   = 0;
	public static final int ROLE_THEIRS = 1;
	public static final int ROLE_BASE   = 2;
	public static final int NUM_ROLES   = 3;

	protected WSUModels(SiDiffResourceSet resourceSet, List<Resource> resources) {
		super(resourceSet, resources);
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

	public InputModels getBaseTheirsModels() {
		return InputModels.builder().addModel(getResourceBase()).addModel(getResourceTheirs()).build();
	}

	public static Builder wsuBuilder() {
		return new Builder();
	}
	
	public static class Builder extends InputModels.Builder<WSUModels> {
		protected Builder() {
			super(WSUModels::new);
			assertNumModels(3);
		}
	}
}
