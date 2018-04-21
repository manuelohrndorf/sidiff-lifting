package org.sidiff.integration.preferences.difference.factory;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;
import org.sidiff.difference.technical.ITechnicalDifferenceBuilder;
import org.sidiff.difference.technical.IncrementalTechnicalDifferenceBuilder;
import org.sidiff.difference.technical.api.settings.DifferenceSettings;
import org.sidiff.difference.technical.util.TechnicalDifferenceBuilderUtil;
import org.sidiff.integration.preferences.exceptions.InvalidSettingsException;
import org.sidiff.integration.preferences.matching.factory.MatchingSettingsFactory;
import org.silift.difference.symboliclink.handler.ISymbolicLinkHandler;
import org.silift.difference.symboliclink.handler.util.SymbolicLinkHandlerUtil;

/**
 * 
 * Factory class for {@link org.sidiff.difference.technical.api.settings.DifferenceSettings}
 * @author Daniel Roedder, Robert Müller
 */
public class DifferenceSettingsFactory extends MatchingSettingsFactory {

	/**
	 * The {@link List} for all {@link ITechnicalDifferenceBuilder} to be used
	 */
	protected List<ITechnicalDifferenceBuilder> technicalDifferenceBuilderList;

	/**
	 * The {@link ISymbolicLinkHandler} to be used
	 */
	protected ISymbolicLinkHandler symbolicLinkHandler;

	/**
	 * Boolean if or not to merge imports
	 */
	protected boolean mergeImports;

	/**
	 * @see org.sidiff.integration.preferences.matching.factory.MatchingSettingsFactory#getFeatureLevel()
	 */
	@Override
	public String getFeatureLevel() {
		return "Difference";
	}

	/**
	 * @see org.sidiff.integration.preferences.matching.factory.MatchingSettingsFactory#doSetFields(java.lang.String, org.eclipse.jface.preference.IPreferenceStore)
	 */
	@Override
	public void doSetFields(String documentType, IPreferenceStore store) {
		super.doSetFields(documentType, store);

		String techDiffBuilders = store.getString(documentType.concat("technicalDifferenceBuilderOrder"));
		String[] techDiffBuilders2 = techDiffBuilders.split(";");

		technicalDifferenceBuilderList = new ArrayList<ITechnicalDifferenceBuilder>();
		
		for (String techDiffBuilderKey : techDiffBuilders2) {
			
			technicalDifferenceBuilderList.add(TechnicalDifferenceBuilderUtil.getTechnicalDifferenceBuilder(techDiffBuilderKey));
		}

		//You can only get all handlers, not one by key?
		//symbolicLinkHandler = SymbolicLinkHandlerUtil.getAvailableSymbolicLinkHandlers().iterator().next();

		//FIXED Iterate over all SymbolicLinkHandlers and get the selected from the settings

		for (ISymbolicLinkHandler handler : SymbolicLinkHandlerUtil.getAvailableSymbolicLinkHandlers()) {
			
			if (handler.getKey().equals(store.getString("symbolicLinkHandlers"))) symbolicLinkHandler = handler;
		}

		mergeImports = store.getBoolean("mergeImports");
	}

	/**
	 * @see org.sidiff.integration.preferences.matching.factory.MatchingSettingsFactory#getSettings()
	 */
	@Override
	public DifferenceSettings getSettings() throws InvalidSettingsException {
		DifferenceSettings settings = new DifferenceSettings(
				scope, validate, createMatcher(), candidatesService,
				correspondencesService, createTechBuilder());
		if (!settings.validateSettings()) throw new InvalidSettingsException();
		settings.setMergeImports(mergeImports);
		return settings;
	}

	protected ITechnicalDifferenceBuilder createTechBuilder() {
		if(technicalDifferenceBuilderList.size() == 1)
			return technicalDifferenceBuilderList.get(0);
		return new IncrementalTechnicalDifferenceBuilder(technicalDifferenceBuilderList);
	}
}
