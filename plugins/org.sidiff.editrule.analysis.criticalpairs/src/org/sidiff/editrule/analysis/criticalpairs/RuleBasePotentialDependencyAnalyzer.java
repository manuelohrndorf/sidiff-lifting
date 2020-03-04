package org.sidiff.editrule.analysis.criticalpairs;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.ecore.EPackage;

/**
 * Base class for rulebase potential dependency analyzer.
 * 
 * @author Manuel Ohrndorf
 */
public abstract class RuleBasePotentialDependencyAnalyzer extends PotentialDependencyAnalyzer {

	/**
	 * Initializes a new rulebase potential dependency analyzer.
	 * 
	 * @param transientPDs
	 *            <code>true</code> to calculate transient potential dependencies; 
	 *            <code>false</code> otherwise.
	 * @param nonTransientPDs
	 *            <code>true</code> to calculate non transient potential dependencies; 
	 *            <code>false</code> otherwise.
	 */
	public RuleBasePotentialDependencyAnalyzer(boolean transientPDs, boolean nonTransientPDs) {
		super(transientPDs, nonTransientPDs);
	}

	/**
	 * @param documentTypes
	 *            All document types of the rulebase(s).
	 * @return All imports of the Henshin rules (document types) which shall be analyzed.
	 */
	protected Set<EPackage> getImports(Collection<String> documentTypes) {
		Set<EPackage> imports = new HashSet<>();

		for (String docType : documentTypes) {
			EPackage ePackage = EPackage.Registry.INSTANCE.getEPackage(docType);
			Assert.isNotNull(ePackage,
				"Package for docType " + docType + " not found in the global EMF package registry");
			imports.add(ePackage);
		}

		return imports;
	}
}
