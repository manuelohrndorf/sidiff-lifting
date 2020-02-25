package org.sidiff.integration.preferences.matching.tabs;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.sidiff.integration.preferences.fieldeditors.ICompositePreferenceField;
import org.sidiff.integration.preferences.fieldeditors.IPreferenceField;
import org.sidiff.integration.preferences.fieldeditors.PreferenceFieldFactory;
import org.sidiff.integration.preferences.matching.settingsadapter.MatchingSettingsAdapter;
import org.sidiff.integration.preferences.tabs.AbstractDomainPreferenceTab;
import org.sidiff.integration.preferences.valueconverters.ExtensionValueConverter;
import org.sidiff.matcher.IMatcher;

/**
 * 
 * Class for the matching settings tab.
 * @author Daniel Roedder
 * @author rmueller
 */
public class DomainMatchingEnginesPreferenceTab extends AbstractDomainPreferenceTab {

	@Override
	public void createPreferenceFields(List<IPreferenceField> list) {
		// get all non-generic matchers
		Collection<IMatcher> matchers = IMatcher.MANAGER.getExtensions(Collections.singleton(getDocumentType()), false);

		IPreferenceField matchersField =
			PreferenceFieldFactory.createOrderedList(
				MatchingSettingsAdapter.KEY_MATCHERS(getDocumentType()),
				"Matchers",
				matchers,
				ExtensionValueConverter.getInstance());
		list.add(matchersField);
		ICompositePreferenceField<IPreferenceField> matcherOptions =
				PreferenceFieldFactory.createConfigurableExtensionsFields(
						matchers, matchersField, MatchingSettingsAdapter::KEY_MATCHER_OPTIONS);
		if(!matcherOptions.isEmpty()) {
			list.add(matcherOptions);			
		}
	}
}
