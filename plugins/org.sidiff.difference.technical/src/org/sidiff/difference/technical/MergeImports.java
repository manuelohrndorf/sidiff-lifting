package org.sidiff.difference.technical;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.symmetric.Correspondence;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.silift.common.util.emf.ExternalReferenceCalculator;
import org.silift.common.util.emf.ExternalReferenceContainer;
import org.silift.common.util.emf.Scope;

/**
 * The import merger is used to merge external references of model A and B into
 * the given difference. That means the algorithm will create a copy of the
 * external referenced objects and link the original and copy to a new
 * Correspondence. The next step is to (re)link the external references of the
 * models (A/B) and changes to the copied objects. The list of objects that will
 * be merged into the difference depends on the
 * {@link MergeImports#getMergeList(Set)} algorithm.
 */
public class MergeImports {

	private SymmetricDifference symmetricDifference;
	private AsymmetricDifference asymmetricDifference;

	private Scope scope;

	private boolean relink;

	private ExternalReferenceContainer refsA;
	private ExternalReferenceContainer refsB;
	private ResourceSetAdapter resourceSetAdapter;

	private PackageRegistryAdapter registryAdapter;
	private HashSet<EObject> imports;

	/**
	 * 
	 * 
	 * @param symmetricDifference
	 * @param scope
	 * @param relink
	 */
	public MergeImports(SymmetricDifference symmetricDifference, Scope scope, boolean relink) {
		super();

		this.symmetricDifference = symmetricDifference;
		this.scope = scope;
		this.relink = relink;
	}

	/**
	 * 
	 * 
	 * @param symmetricDifference
	 * @param asymmetricDifference
	 * @param scope
	 * @param relink
	 */
	public MergeImports(SymmetricDifference symmetricDifference, AsymmetricDifference asymmetricDifference,
			Scope scope, boolean relink) {

		this(symmetricDifference, scope, relink);
		this.asymmetricDifference = asymmetricDifference;
	}

	/**
	 * Merge imports into the difference.
	 * 
	 * @see {@link MergeImports#unmerge()}
	 */
	public void merge() {

		LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");
		LogUtil.log(LogEvent.NOTICE, "----------------------- MERGE IMPORTS ----------------------");
		LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");

		LogUtil.log(LogEvent.NOTICE, "resourceA: " + symmetricDifference.getModelA());
		LogUtil.log(LogEvent.NOTICE, "resourceB: " + symmetricDifference.getModelB());

		LogUtil.log(LogEvent.DEBUG, "\nresSetA:");
		ResourceSet resSetA = symmetricDifference.getModelA().getResourceSet();
		for (Resource r : resSetA.getResources()) {
			assert (!r.getContents().isEmpty()) : "Resource '" + r.getURI() + "' is empty!";
			LogUtil.log(LogEvent.DEBUG, r);
			for (EObject root : r.getContents()) {
				LogUtil.log(LogEvent.DEBUG, "\troot: " + root);
			}

		}

		LogUtil.log(LogEvent.DEBUG, "\nresSetB:");
		ResourceSet resSetB = symmetricDifference.getModelB().getResourceSet();
		for (Resource r : resSetB.getResources()) {
			assert (!r.getContents().isEmpty()) : "Resource '" + r.getURI() + "' is empty!";
			LogUtil.log(LogEvent.DEBUG, r);
			for (EObject root : r.getContents()) {
				LogUtil.log(LogEvent.DEBUG, "\troot: " + root);
			}
		}

		// Collect external references
		ExternalReferenceCalculator referenceCalculatorA = new ExternalReferenceCalculator();
		this.refsA = referenceCalculatorA.calculate(symmetricDifference.getModelA(), scope);
		ExternalReferenceCalculator referenceCalculatorB = new ExternalReferenceCalculator();
		this.refsB = referenceCalculatorB.calculate(symmetricDifference.getModelB(), scope);

		// Registry adapter (always needed)
		initRegistryAdapter();

		// ResourceSet adapter (only needed when scope == RESOURCE).
		if (scope == Scope.RESOURCE) {
			initResourceSetAdapter();
		}

		// Collect imports from registry and resource set
		imports = new HashSet<EObject>();
		imports.addAll(registryAdapter.getImports());
		if (scope == Scope.RESOURCE) {
			imports.addAll(resourceSetAdapter.getImports());
		}

		/*
		 * Print report
		 */
		// Print merge list
		if (imports.size() > 0) {
			LogUtil.log(LogEvent.DEBUG, "\nMerge imports from Registry (Original <-> Copy):");
			for (Correspondence c : registryAdapter.getCorrespondences()) {
				LogUtil.log(LogEvent.DEBUG, c.getObjA() + " <-> " + c.getObjB());
				assert (imports.contains(c.getObjA()));
				assert (imports.contains(c.getObjB()));
			}

			LogUtil.log(LogEvent.DEBUG, "\nAdditional ResourceSet objects (A <-> B):");
			if (resourceSetAdapter != null) {
				for (Correspondence c : resourceSetAdapter.getCorrespondences()) {
					LogUtil.log(LogEvent.DEBUG, c.getObjA() + " <-> " + c.getObjB());
					assert (imports.contains(c.getObjA()));
					assert (imports.contains(c.getObjB()));
				}
			}
		} else {
			LogUtil.log(LogEvent.DEBUG, "\nNothing to do...\n");
		}
	}

	/**
	 * Undo merge.
	 * 
	 * @see {@link MergeImports#merge()}
	 */
	public void unmerge() {

		LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");
		LogUtil.log(LogEvent.NOTICE, "----------------------- UNMERGE IMPORTS --------------------");
		LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");

		// delegate cleanup to adapters
		registryAdapter.cleanup();
		if (scope == Scope.RESOURCE) {
			resourceSetAdapter.cleanup();
		}
	}

	public Set<EObject> getImports() {
		return imports;
	}

	public ResourceSetAdapter getResourceSetAdapter() {
		return resourceSetAdapter;
	}

	public PackageRegistryAdapter getRegistryAdapter() {
		return registryAdapter;
	}

	private void initRegistryAdapter() {
		if (asymmetricDifference == null) {
			registryAdapter = new PackageRegistryAdapter(symmetricDifference, refsA, refsB, relink);
		} else {
			registryAdapter = new PackageRegistryAdapter(symmetricDifference, asymmetricDifference, refsA, refsB,
					relink);
		}
		registryAdapter.init();
	}

	private void initResourceSetAdapter() {
		resourceSetAdapter = new ResourceSetAdapter(symmetricDifference, refsA.getResourceSetReferences(),
				refsB.getResourceSetReferences());
		resourceSetAdapter.init();
	}

}
