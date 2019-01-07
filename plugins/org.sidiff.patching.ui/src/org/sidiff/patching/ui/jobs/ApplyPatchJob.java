package org.sidiff.patching.ui.jobs;

import org.eclipse.emf.common.util.URI;
import org.sidiff.patching.api.settings.PatchingSettings;
import org.sidiff.patching.patch.patch.Patch;

public class ApplyPatchJob extends ApplyAsymmetricDifferenceJob {

	public ApplyPatchJob(Patch patch, URI targetURI, PatchingSettings settings) {
		super(patch.getAsymmetricDifference(), targetURI, settings);
	}
}
