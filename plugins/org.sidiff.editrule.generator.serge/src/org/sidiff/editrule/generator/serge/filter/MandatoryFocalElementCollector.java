package org.sidiff.editrule.generator.serge.filter;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EClassifier;
import org.sidiff.editrule.generator.serge.configuration.Configuration;
import org.sidiff.editrule.generator.serge.filter.ClassifierInclusionConfiguration.InclusionType;
import org.sidiff.editrule.generator.serge.metamodelanalysis.EClassifierInfoManagement;

/**
 * This class collects the user defined classifiers for which dedicated rules
 * should be generated.
 * 
 * @author mrindt
 *
 */
public class MandatoryFocalElementCollector {

	/**
	 * Collects the user defined classifiers for which dedicated rules
	 * should be generated. If none are explicitly specified by the user,
	 * all classifiers of the meta-model are considered to be included as focal classifiers.
	 * 
	 * @return set of classifiers
	 */
	public static Set<EClassifier> collectConfiguredAndRequiredFocalClassifiers() {
		
		EClassifierInfoManagement ECM = EClassifierInfoManagement.getInstance();
		ClassifierInclusionConfiguration CIC = ClassifierInclusionConfiguration.getInstance();		
		Set<EClassifier> intermediateResult = new HashSet<EClassifier>();
		Set<EClassifier> result = new HashSet<EClassifier>();
		
		// Collect all ALWAYS included focus types
		for (EClassifier eC : CIC.getFocusClassifierInclusionTypes().keySet()) {
			if (InclusionType.ALWAYS.equals(CIC.getFocusInclusionType(eC)))
				intermediateResult.add(eC);
		}
		result.addAll(intermediateResult);	
		
		// Collect all Classifiers if only the default inclusion option is set to ALWAYS
		if(result.isEmpty() && CIC.getDefaultFocusInclusionType().equals(InclusionType.ALWAYS)) {
			result.addAll(Configuration.getInstance().METAMODEL.getEClassifiers());
		}
		
		return result;
		
	}

}
