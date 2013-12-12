package org.sidiff.patching.util;

import org.sidiff.difference.matcher.IMatcher;
import org.sidiff.patching.IPatchCorrespondence;
import org.sidiff.patching.internal.DelegatingPatchCorrespondence;

public class CorrespondenceUtil {

	/**
	 * Creates an IPatchCorrespondence which delegates to the matcher obtained
	 * for the given key.
	 * 
	 * @param documentType
	 * @return
	 */
	public static IPatchCorrespondence getPatchCorrespondence(IMatcher matcher) {
		// TODO (CP): We should not use hard-coded matcher key "SiDiff", but get
		// key from a UI dialog.
		// Default in the UI dialog (invoked from PatchApplyHandler) should be
		// set according to patch manifest.

//		IMatcher matcher = PipelineUtils.getMatcherByKey("SiDiff", rOrigin, rPatched);
		DelegatingPatchCorrespondence patchCorrespondence = new DelegatingPatchCorrespondence(matcher);
		
		return patchCorrespondence;
	}
}
