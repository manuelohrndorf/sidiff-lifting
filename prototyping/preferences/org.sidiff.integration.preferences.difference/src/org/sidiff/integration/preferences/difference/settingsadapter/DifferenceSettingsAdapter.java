package org.sidiff.integration.preferences.difference.settingsadapter;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.jface.preference.IPreferenceStore;
import org.sidiff.common.settings.AbstractSettings;
import org.sidiff.difference.technical.ITechnicalDifferenceBuilder;
import org.sidiff.difference.technical.IncrementalTechnicalDifferenceBuilder;
import org.sidiff.difference.technical.api.settings.DifferenceSettings;
import org.sidiff.difference.technical.util.TechnicalDifferenceBuilderUtil;
import org.sidiff.integration.preferences.difference.Activator;
import org.sidiff.integration.preferences.settingsadapter.AbstractSettingsAdapter;

/**
 * 
 * @author Robert Müller
 *
 */
public class DifferenceSettingsAdapter extends AbstractSettingsAdapter {

	public static final String KEY_TECHNICAL_DIFFERENCE_BUILDERS = "technicalDifferenceBuilders";
	public static String KEY_TECHNICAL_DIFFERENCE_BUILDERS(String documentType) {
		return KEY_TECHNICAL_DIFFERENCE_BUILDERS + "[" + documentType + "]";
	}
	public static final String KEY_MERGE_IMPORTS = "mergeImports";
	public static final String KEY_UNMERGE_IMPORTS = "unmergeImports";

	private List<ITechnicalDifferenceBuilder> technicalDifferenceBuilderList;
	private boolean mergeImports;
	private boolean unmergeImports;

	@Override
	public boolean canAdapt(AbstractSettings settings) {
		return settings instanceof DifferenceSettings;
	}

	@Override
	public void adapt(AbstractSettings settings) {
		DifferenceSettings diffSettings = (DifferenceSettings)settings;
		ITechnicalDifferenceBuilder techBuilder = createTechBuilder();
		if(techBuilder != null) {
			diffSettings.setTechBuilder(techBuilder);
		}
		diffSettings.setMergeImports(mergeImports);
		diffSettings.setUnmergeImports(unmergeImports);
	}

	@Override
	public void load(IPreferenceStore store) {
		// get keys of all domain specific technical difference builders
		Set<String> techDiffBuilderKeys = new LinkedHashSet<String>();
		for(String docType : getDocumentTypes()) {
			for(String techDiffBuilderKey : store.getString(KEY_TECHNICAL_DIFFERENCE_BUILDERS(docType)).split(";")) {
				if(!techDiffBuilderKey.isEmpty()) {
					techDiffBuilderKeys.add(techDiffBuilderKey);
				}
			}
		}
		// get keys of all generic technical difference builders, if no domain specific ones are set
		if(techDiffBuilderKeys.isEmpty()) {
			for(String techDiffBuilderKey : store.getString(KEY_TECHNICAL_DIFFERENCE_BUILDERS).split(";")) {
				if(!techDiffBuilderKey.isEmpty()) {
					techDiffBuilderKeys.add(techDiffBuilderKey);
				}
			}
		}
		// get the technical difference builders
		technicalDifferenceBuilderList = new ArrayList<ITechnicalDifferenceBuilder>();
		for(String techDiffBuilderKey : techDiffBuilderKeys) {
			if(techDiffBuilderKey.equals("org.sidiff.difference.technical.GenericTechnicalDifferenceBuilder")) {
				technicalDifferenceBuilderList.add(TechnicalDifferenceBuilderUtil.getGenericTechnicalDifferenceBuilder());
			} else {
				ITechnicalDifferenceBuilder techBuilder = TechnicalDifferenceBuilderUtil.getTechnicalDifferenceBuilder(techDiffBuilderKey);
				if(techBuilder != null) {
					technicalDifferenceBuilderList.add(techBuilder);
				} else {
					addWarning("Technical Difference Builder with key '" + techDiffBuilderKey + "' was not found.");
				}
			}
		}
		if(technicalDifferenceBuilderList.isEmpty()) {
			addError("No Technical Difference Builders were specified.");
		}

		mergeImports = store.getBoolean(KEY_MERGE_IMPORTS);
		unmergeImports = store.getBoolean(KEY_UNMERGE_IMPORTS);
	}

	protected ITechnicalDifferenceBuilder createTechBuilder() {
		switch(technicalDifferenceBuilderList.size()) {
			case 0: return null;
			case 1: return technicalDifferenceBuilderList.get(0);
			default: return new IncrementalTechnicalDifferenceBuilder(technicalDifferenceBuilderList);
		}
	}

	@Override
	public void initializeDefaults(IPreferenceStore store) {
		store.setDefault(KEY_TECHNICAL_DIFFERENCE_BUILDERS,
				"org.sidiff.difference.technical.GenericTechnicalDifferenceBuilder");
		store.setDefault(KEY_TECHNICAL_DIFFERENCE_BUILDERS("http://www.eclipse.org/emf/2002/Ecore"),
				"org.sidiff.ecore.difference.technical.TechnicalDifferenceBuilderEcore");
		store.setDefault(KEY_TECHNICAL_DIFFERENCE_BUILDERS("http://www.eclipse.org/uml2/5.0.0/UML"),
				"org.sidiff.uml2v4.difference.technical.TechnicalDifferenceBuilderUML");
		store.setDefault(KEY_MERGE_IMPORTS, true);
		store.setDefault(KEY_UNMERGE_IMPORTS, true);
	}

	@Override
	protected BasicDiagnostic getDiagnosticGroup() {
		return new BasicDiagnostic(Activator.PLUGIN_ID, 0, "Difference settings", null);
	}
}
