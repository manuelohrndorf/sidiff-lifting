package org.sidiff.difference.symmetric.mergeimports;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.sidiff.common.emf.ExternalReferenceCalculator;
import org.sidiff.common.emf.access.ExternalReferenceContainer;
import org.sidiff.common.emf.access.Scope;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.matching.model.Correspondence;

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

	protected SymmetricDifference symmetricDifference;

	protected Scope scope;

	protected boolean relink;

	protected ExternalReferenceContainer refsA;
	protected ExternalReferenceContainer refsB;
	
	protected ResourceSetAdapter resourceSetAdapter;

	protected PackageRegistryAdapter registryAdapter;
	
	protected ModelImports imports;
	
	protected boolean merged;

	public MergeImports(Scope scope, boolean relink) {
		super();

		this.scope = scope;
		this.relink = relink;
		this.merged = false;
	}

	/**
	 * Merge imports into the difference.
	 * 
	 * @see {@link MergeImports#unmerge()}
	 */
	public void merge() {

		if (!merged) {
			merged = true;
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

			// Create merge imports container:
			imports = new ModelImports(registryAdapter, resourceSetAdapter);

			/*
			 * Print report
			 */

			LogUtil.log(LogEvent.DEBUG, "\nMerge imports from Registry (Original <-> Copy):");
			for (Correspondence c : registryAdapter.getCorrespondences()) {
				LogUtil.log(LogEvent.DEBUG, c.getMatchedA() + " <-> " + c.getMatchedB());
				assert (imports.containsImportsModelA(c.getMatchedA()));
				assert (imports.containsImportsModelB(c.getMatchedB()));
			}

			LogUtil.log(LogEvent.DEBUG, "\nAdditional ResourceSet objects (A <-> B):");
			if (resourceSetAdapter != null) {
				for (Correspondence c : resourceSetAdapter.getCorrespondences()) {
					LogUtil.log(LogEvent.DEBUG, c.getMatchedA() + " <-> " + c.getMatchedB());
					assert (imports.containsImportsModelA(c.getMatchedA()));
					assert (imports.containsImportsModelB(c.getMatchedB()));
				}
			}
		}
	}

	/**
	 * Undo merge.
	 * 
	 * @see {@link MergeImports#merge()}
	 */
	public void unmerge() {
		if (merged) {
			merged = false;
			LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");
			LogUtil.log(LogEvent.NOTICE, "----------------------- UNMERGE IMPORTS --------------------");
			LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");

			// delegate cleanup to adapters
			registryAdapter.cleanup();

			if (scope == Scope.RESOURCE) {
				resourceSetAdapter.cleanup();
			}
		}
	}

	public ModelImports getImports() {
		return imports;
	}

	public SymmetricDifference getSymmetricDifference() {
		return symmetricDifference;
	}
	
	public void setSymmetricDifference(SymmetricDifference symmetricDifference) {
		this.symmetricDifference = symmetricDifference;
	}
	
	public ResourceSetAdapter getResourceSetAdapter() {
		return resourceSetAdapter;
	}

	public PackageRegistryAdapter getRegistryAdapter() {
		return registryAdapter;
	}

	protected void initRegistryAdapter() {
		registryAdapter = new PackageRegistryAdapter(symmetricDifference, refsA, refsB, relink);
		registryAdapter.init();
	}

	protected void initResourceSetAdapter() {
		resourceSetAdapter = new ResourceSetAdapter(symmetricDifference, refsA.getResourceSetReferences(), refsB.getResourceSetReferences());
		resourceSetAdapter.init();
	}

}
