package org.sidiff.integration.preferences.matching.settingsadapter;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.jface.preference.IPreferenceStore;
import org.sidiff.candidates.ICandidates;
import org.sidiff.common.emf.settings.ISettings;
import org.sidiff.common.extension.configuration.ConfigurationOption;
import org.sidiff.common.extension.configuration.IConfigurableExtension;
import org.sidiff.common.extension.configuration.IExtensionConfiguration;
import org.sidiff.common.util.StringListSerializer;
import org.sidiff.integration.preferences.matching.internal.MatchingPreferencesPlugin;
import org.sidiff.integration.preferences.settingsadapter.AbstractSettingsAdapter;
import org.sidiff.matcher.IMatcher;
import org.sidiff.matcher.IncrementalMatcher;
import org.sidiff.matching.api.settings.MatchingSettings;
import org.sidiff.matching.api.settings.MatchingSettingsItem;

/**
 * @author rmueller
 */
public class MatchingSettingsAdapter extends AbstractSettingsAdapter {

	public static final String KEY_MATCHERS = "matchers";
	public static String KEY_MATCHERS(String documentType) {
		return KEY_MATCHERS + "[" + documentType + "]";
	}
	public static String KEY_MATCHER_OPTIONS(String matcher, String option) {
		return "matcherOptions[" + matcher + "][" + option + "]";
	}
	public static final String KEY_CANDIDATES_SERVICE = "candidatesService";

	private List<IMatcher> matchers;
	private ICandidates candidatesService;

	@Override
	public boolean canAdapt(ISettings settings) {
		return settings instanceof MatchingSettings;
	}

	@Override
	public void adapt(ISettings settings) {
		MatchingSettings matchingSettings = (MatchingSettings)settings;

		if(isConsidered(MatchingSettingsItem.MATCHER)) {
			IMatcher matcher = createMatcher();
			if(matcher != null) {
				matchingSettings.setMatcher(matcher);
			}
		}
		if(candidatesService != null && isConsidered(MatchingSettingsItem.CANDITATE_SERVICE)) {
			matchingSettings.setCandidatesService(candidatesService);
		}
	}

	@Override
	public void load(IPreferenceStore store) {
		loadMatchers(store);
		loadCandidatesService(store);
	}

	protected void loadMatchers(IPreferenceStore store) {
		// get keys of all domain specific matchers
		List<String> matcherKeys = new ArrayList<>();
		for(String docType : getDocumentTypes()) {
			matcherKeys.addAll(StringListSerializer.DEFAULT.deserialize(store.getString(KEY_MATCHERS(docType))));
		}
		// get keys of all generic matchers
		matcherKeys.addAll(StringListSerializer.DEFAULT.deserialize(store.getString(KEY_MATCHERS)));
		// get the matchers
		matchers = new ArrayList<>();
		for(String matcherKey : matcherKeys) {
			IMatcher matcher = IMatcher.MANAGER.getExtension(matcherKey).orElse(null);
			if(matcher != null) {
				IExtensionConfiguration configuration = ((IConfigurableExtension)matcher).getConfiguration();
				for(ConfigurationOption<?> option : configuration.getConfigurationOptions()) {
					option.setValueUnsafe(store.getString(KEY_MATCHER_OPTIONS(matcherKey, option.getKey())));
				}
				matchers.add(matcher);
			} else {
				addWarning("Matcher with key '" + matcherKey + "' was not found.");
			}
		}
		if(matchers.isEmpty()) {
			addError("No Matchers were specified.");
		}
	}

	protected void loadCandidatesService(IPreferenceStore store) {
		String candidatesServiceId = store.getString(KEY_CANDIDATES_SERVICE);
		candidatesService = ICandidates.MANAGER.getExtension(candidatesServiceId).orElse(null);
		if(candidatesService == null) {
			addError("Candidates Service with id '" + candidatesServiceId + "' was not found.");
		}
	}

	private IMatcher createMatcher() {
		switch(matchers.size()) {
			case 0: return null;
			case 1: return matchers.get(0);
			default: return new IncrementalMatcher(matchers);
		}
	}

	@Override
	public void initializeDefaults(IPreferenceStore store) {
		store.setDefault(KEY_MATCHERS, "org.sidiff.matcher.signature.name.NamedElementMatcher");
		store.setDefault(KEY_CANDIDATES_SERVICE, "InterModelTypeCandidates");
	}

	@Override
	protected BasicDiagnostic getDiagnosticGroup() {
		return new BasicDiagnostic(MatchingPreferencesPlugin.PLUGIN_ID, 0, "Matching settings", null);
	}
}
