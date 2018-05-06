package org.sidiff.integration.preferences.difference.settingsadapter;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;
import org.sidiff.common.settings.AbstractSettings;
import org.sidiff.difference.technical.ITechnicalDifferenceBuilder;
import org.sidiff.difference.technical.IncrementalTechnicalDifferenceBuilder;
import org.sidiff.difference.technical.api.settings.DifferenceSettings;
import org.sidiff.difference.technical.util.TechnicalDifferenceBuilderUtil;
import org.sidiff.integration.preferences.interfaces.ISettingsAdapter;

/**
 * 
 * @author Robert Müller
 *
 */
public class DomainDifferenceSettingsAdapter implements ISettingsAdapter, ISettingsAdapter.DomainSpecific {

	public static String KEY_TECHNICAL_DIFFERENCE_BUILDERS(String documentType) {
		return "technicalDifferenceBuilders[" + documentType + "]";
	}

	private String documentType;

	private List<ITechnicalDifferenceBuilder> technicalDifferenceBuilderList;

	@Override
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	@Override
	public boolean canAdapt(AbstractSettings settings) {
		return settings instanceof DifferenceSettings;
	}

	@Override
	public void adapt(AbstractSettings settings) {
		DifferenceSettings diffSettings = (DifferenceSettings)settings;
		diffSettings.setTechBuilder(createTechBuilder());
	}

	@Override
	public void load(IPreferenceStore store) {
		technicalDifferenceBuilderList = new ArrayList<ITechnicalDifferenceBuilder>();
		for(String techDiffBuilderKey : store.getString(KEY_TECHNICAL_DIFFERENCE_BUILDERS(documentType)).split(";")) {
			technicalDifferenceBuilderList.add(TechnicalDifferenceBuilderUtil.getTechnicalDifferenceBuilder(techDiffBuilderKey));
		}
	}

	protected ITechnicalDifferenceBuilder createTechBuilder() {
		if(technicalDifferenceBuilderList.size() == 1)
			return technicalDifferenceBuilderList.get(0);
		return new IncrementalTechnicalDifferenceBuilder(technicalDifferenceBuilderList);
	}

	@Override
	public void initializeDefaults(IPreferenceStore store) {
		store.setDefault(KEY_TECHNICAL_DIFFERENCE_BUILDERS("http://www.eclipse.org/emf/2002/Ecore"),
				"org.sidiff.ecore.difference.technical.TechnicalDifferenceBuilderEcore");
		store.setDefault(KEY_TECHNICAL_DIFFERENCE_BUILDERS("http://www.eclipse.org/uml2/5.0.0/UML"),
				"org.sidiff.uml2v4.difference.technical.TechnicalDifferenceBuilderUML");
	}

	@Override
	public int getPosition() {
		return 21;
	}
}
