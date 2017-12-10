package org.sidiff.difference.asymmetric.mergeimports;

import org.sidiff.common.emf.access.ExternalReferenceContainer;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.ObjectParameterBinding;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.asymmetric.ParameterBinding;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.difference.symmetric.mergeimports.PackageRegistryAdapter;
import org.sidiff.matching.model.Correspondence;

public class AsymmetricPackageRegistryAdapter extends PackageRegistryAdapter {

	/**
	 * The asymmetric difference difference (if to be created)
	 */
	private AsymmetricDifference asymmetricDifference;

	/**
	 * Creates a package registry adapter which also takes care of the
	 * asymmetric difference. That is, references to external object parameters
	 * are relinked after unmerge if necessary.
	 * 
	 * Note: <br/>
	 * - Creation of a ResourceSetAdapter must be followed by a call to init(). <br/>
	 * - Before a ResourceSetAdapter is destroyed, call cleanup().
	 * 
	 * @param difference
	 * @param asymmetricDifference
	 * @param registryReferencesA
	 * @param registryReferencesB
	 * @param relink
	 */
	public AsymmetricPackageRegistryAdapter(
			SymmetricDifference difference, AsymmetricDifference asymmetricDifference,
			ExternalReferenceContainer registryReferencesA, ExternalReferenceContainer registryReferencesB,
			boolean relink) {

		super(difference, registryReferencesA, registryReferencesB, relink);
		this.asymmetricDifference = asymmetricDifference;
	}

	public void setAsymmetricDifference(AsymmetricDifference asymmetricDifference) {
		this.asymmetricDifference = asymmetricDifference;
	}

	/**
	 * Links the relinked external references back to their originals.
	 */
	protected void undoRelink() {
		super.undoRelink();

		// External B-Parameters
		if (asymmetricDifference != null) {
			for (OperationInvocation op : asymmetricDifference.getOperationInvocations()) {
				for (ParameterBinding binding : op.getParameterBindings()) {
					if (binding instanceof ObjectParameterBinding) {
						ObjectParameterBinding objParamBinding = (ObjectParameterBinding) binding;
						if (objParamBinding.getActualB() != null) {
							Correspondence c = copy2Correspondence.get(objParamBinding.getActualB());
							if (c != null) {
								LogUtil.log(LogEvent.DEBUG, objParamBinding.getFormalName());
								objParamBinding.setActualB(c.getMatchedA());
								LogUtil.log(LogEvent.DEBUG, "  " + c.getMatchedB() + " -> " + c.getMatchedA());
							}
						}
					}
				}
			}
		}
	}
}
