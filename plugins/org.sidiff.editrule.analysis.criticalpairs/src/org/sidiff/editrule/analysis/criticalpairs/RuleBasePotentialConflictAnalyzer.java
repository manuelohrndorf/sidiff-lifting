package org.sidiff.editrule.analysis.criticalpairs;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EPackage;

/**
 * Base class for rulebase potential conflict analyzer.
 * 
 * @author Manuel Ohrndorf, cpietsch
 */
public abstract class RuleBasePotentialConflictAnalyzer extends PotentialConflictAnalyzer {

	/**
	 * Initializes a new rulebase potential conflict analyzer.
	 */
	public RuleBasePotentialConflictAnalyzer() {
		super();
	}

	/**
	 * @param documentTypes
	 *            All document types of the rulebase(s).
	 * @return All imports of the Henshin rules (document types) which shall be analyzed.
	 */
	protected Set<EPackage> getImports(Collection<String> documentTypes) {

		Set<EPackage> docTypePackages = new HashSet<EPackage>();

		for (String docType : documentTypes) {
			EPackage docTypePackage = EPackage.Registry.INSTANCE.getEPackage(docType);
			assert (docTypePackage != null) : "Package for docType " + docType
					+ " not found in the global EMF package registry";
			docTypePackages.add(docTypePackage);
		}

		return docTypePackages;
	}
}
