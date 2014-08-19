package org.sidiff.difference.lifting.ui.util;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.difference.lifting.facade.LiftingFacade;
import org.silift.common.util.access.EMFModelAccessEx;

public class InputModels {
	private IFile fileA;
	private IFile fileB;

	private Resource resourceA;
	private Resource resourceB;

	private String documentType;

	public InputModels(IFile fileA, IFile fileB) {
		this.fileA = fileA;
		this.fileB = fileB;
	}

	public InputModels(Resource resourceA, Resource resourceB) {
		this.resourceA = resourceA;
		this.resourceB = resourceB;
	}

	public void swap() {
		if ((fileA != null) && (fileB != null)) {
			IFile tmp = fileA;
			fileA = fileB;
			fileB = tmp;
		}

		if ((resourceA != null) && (resourceB != null)) {
			Resource tmp = resourceA;
			resourceA = resourceB;
			resourceB = tmp;
		}
	}

	public IFile getFileA() {
		if (fileA == null) {
			URI uri = resourceA.getURI();
			String fileString = URI.decode(uri.path());
			fileA = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(fileString));
		}

		return fileA;
	}

	public IFile getFileB() {
		if (fileB == null) {
			URI uri = resourceB.getURI();
			String fileString = URI.decode(uri.path());
			fileB = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(fileString));
		}

		return fileB;
	}

	public Resource getResourceA() {
		if (resourceA == null) {
			resourceA = LiftingFacade.loadModel(fileA.getLocation().toOSString());
		}

		return resourceA;
	}

	public Resource getResourceB() {
		if (resourceB == null) {
			resourceB = LiftingFacade.loadModel(fileB.getLocation().toOSString());
		}

		return resourceB;
	}

	public String getDocumentType() {
		if (documentType == null) {
			documentType = EMFModelAccessEx.getCharacteristicDocumentType(getResourceA());
		}

		return documentType;
	}
}
