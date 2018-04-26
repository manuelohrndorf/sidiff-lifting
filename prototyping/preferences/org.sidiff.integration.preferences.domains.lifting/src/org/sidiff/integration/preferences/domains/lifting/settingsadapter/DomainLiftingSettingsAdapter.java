package org.sidiff.integration.preferences.domains.lifting.settingsadapter;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jface.preference.IPreferenceStore;
import org.sidiff.common.settings.AbstractSettings;
import org.sidiff.difference.lifting.api.settings.LiftingSettings;
import org.sidiff.difference.lifting.api.util.PipelineUtils;
import org.sidiff.difference.lifting.recognitionrulesorter.IRecognitionRuleSorter;
import org.sidiff.difference.lifting.recognitionrulesorter.util.RecognitionRuleSorterLibrary;
import org.sidiff.difference.rulebase.view.ILiftingRuleBase;
import org.sidiff.integration.preferences.interfaces.ISettingsAdapter;

/**
 * 
 * @author Robert Müller
 *
 */
public class DomainLiftingSettingsAdapter implements ISettingsAdapter, ISettingsAdapter.DomainSpecific {

	public static String KEY_RULE_BASES(String documentType) {
		return "ruleBases[" + documentType + "]";
	}
	public static String KEY_RECOGNITION_RULE_SORTER(String documentType) {
		return "recognitionRuleSorter[" + documentType + "]";
	}

	private String documentType;

	private Set<ILiftingRuleBase> ruleBases;
	private IRecognitionRuleSorter rrSorter;

	@Override
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	@Override
	public boolean canAdapt(AbstractSettings settings) {
		return settings instanceof LiftingSettings;
	}

	@Override
	public void adapt(AbstractSettings settings) {
		LiftingSettings liftingSettings = (LiftingSettings)settings;
		liftingSettings.setRuleBases(ruleBases);
		liftingSettings.setRrSorter(rrSorter);
	}

	@Override
	public void load(IPreferenceStore store) {
		ruleBases = new HashSet<ILiftingRuleBase>();
		for(ILiftingRuleBase rbase : PipelineUtils.getAvailableRulebases(documentType)) {
			if(store.getBoolean(KEY_RULE_BASES(documentType) + ":" + rbase.getName())) {
				ruleBases.add(rbase);
			}
		}

		rrSorter = RecognitionRuleSorterLibrary.getRecognitionRuleSorter(store.getString(KEY_RECOGNITION_RULE_SORTER(documentType)));
		if(rrSorter == null) {
			rrSorter = RecognitionRuleSorterLibrary.getDefaultRecognitionRuleSorter(Collections.singleton(documentType));
		}
	}

	@Override
	public void initializeDefaults(IPreferenceStore store) {
		store.setDefault(KEY_RECOGNITION_RULE_SORTER("http://www.eclipse.org/emf/2002/Ecore"), "EcoreRRSorter");
		store.setDefault(KEY_RECOGNITION_RULE_SORTER("http://www.eclipse.org/uml2/5.0.0/UML"), "GenericRRSorter");

		// TODO: these are not correct as the rulebases have no good permanently saveable value
		store.setDefault(KEY_RULE_BASES("http://www.eclipse.org/emf/2002/Ecore") + ":" + "Ecore Atomic", true);
		store.setDefault(KEY_RULE_BASES("http://www.eclipse.org/uml2/5.0.0/UML") + ":" + "UML_2v4_Atomic", true);
	}

	@Override
	public int getPosition() {
		return 31;
	}
}
