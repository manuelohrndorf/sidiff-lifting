package org.sidiff.patching.ui.handler;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IFile;
import org.sidiff.difference.asymmetric.api.AsymmetricDiffFacade;

public class PatchApplyPropertyTester extends PropertyTester {

	public PatchApplyPropertyTester() {

	}

	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		IFile file = (IFile) receiver;
		String filePath = file.getLocation().toOSString();
		if (filePath.endsWith(AsymmetricDiffFacade.ASYMMETRIC_DIFF_EXT) || filePath.endsWith(AsymmetricDiffFacade.PATCH_EXTENSION))
			return true;

		return false;
	}

}
