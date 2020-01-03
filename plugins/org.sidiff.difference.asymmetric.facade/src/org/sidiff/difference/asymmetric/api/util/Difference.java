package org.sidiff.difference.asymmetric.api.util;

import java.util.Objects;

import org.eclipse.emf.common.util.URI;
import org.sidiff.common.emf.modelstorage.SiDiffResourceSet;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.api.AsymmetricDiffFacade;
import org.sidiff.difference.lifting.api.LiftingFacade;
import org.sidiff.difference.symmetric.SymmetricDifference;

public class Difference {

	private final SymmetricDifference symmetric;
	private final AsymmetricDifference asymmetric;

	public Difference(SymmetricDifference symmetric, AsymmetricDifference asymmetric) {
		this.symmetric = Objects.requireNonNull(symmetric);
		this.asymmetric = Objects.requireNonNull(asymmetric);
	}

	public SymmetricDifference getSymmetric() {
		return symmetric;
	}

	public AsymmetricDifference getAsymmetric() {
		return asymmetric;
	}

	/**
	 * Serializes this difference.
	 * @param resourceSet the resource set
	 * @param outputDir The serialization output directory.
	 * @param fileName The file name of the difference (without file extension).
	 */
	public void serialize(SiDiffResourceSet resourceSet, URI outputDir, String fileName) {
		resourceSet.saveEObjectAs(getSymmetric(),
				outputDir.appendSegment(fileName).appendFileExtension(LiftingFacade.SYMMETRIC_DIFF_EXT));
		resourceSet.saveEObjectAs(getAsymmetric(),
				outputDir.appendSegment(fileName).appendFileExtension(AsymmetricDiffFacade.ASYMMETRIC_DIFF_EXT));
	}
}
