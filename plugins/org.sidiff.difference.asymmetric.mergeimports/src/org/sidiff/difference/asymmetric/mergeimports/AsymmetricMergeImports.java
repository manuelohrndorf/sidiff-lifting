package org.sidiff.difference.asymmetric.mergeimports;

import java.util.Set;

import org.sidiff.common.emf.access.Scope;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.symmetric.mergeimports.MergeImports;

/**
 * The import merger is used to merge external references of model A and B into
 * the given difference. That means the algorithm will create a copy of the
 * external referenced objects and link the original and copy to a new
 * Correspondence. The next step is to (re)link the external references of the
 * models (A/B) and changes to the copied objects. The list of objects that will
 * be merged into the difference depends on the
 * {@link AsymmetricMergeImports#getMergeList(Set)} algorithm.
 */
public class AsymmetricMergeImports extends MergeImports {

	private AsymmetricDifference asymmetricDifference;


	public AsymmetricMergeImports(
			Scope scope, boolean relink) {

		super(scope, relink);
	}

	public AsymmetricDifference getAsymmetricDifference() {
		return asymmetricDifference;
	}

	public void setAsymmetricDifference(AsymmetricDifference asymmetricDifference) {
		this.asymmetricDifference = asymmetricDifference;
		if(registryAdapter != null) {
			((AsymmetricPackageRegistryAdapter)registryAdapter).setAsymmetricDifference(asymmetricDifference);
		}
	}

	public AsymmetricPackageRegistryAdapter getRegistryAdapter() {
		return (AsymmetricPackageRegistryAdapter)registryAdapter;
	}

	protected void initRegistryAdapter() {
		registryAdapter = new AsymmetricPackageRegistryAdapter(symmetricDifference, asymmetricDifference, refsA, refsB, relink);
		registryAdapter.init();
	}
}
