package org.sidiff.integration.preferences.matching.tabs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.configuration.IConfigurable;
import org.sidiff.integration.preferences.fieldeditors.IPreferenceField;
import org.sidiff.integration.preferences.fieldeditors.PreferenceFieldFactory;
import org.sidiff.integration.preferences.matching.settingsadapter.MatchingSettingsAdapter;
import org.sidiff.integration.preferences.tabs.AbstractDomainPreferenceTab;
import org.sidiff.integration.preferences.valueconverters.IPreferenceValueConverter;
import org.sidiff.integration.preferences.valueconverters.IdentityPreferenceValueConverter;
import org.sidiff.matcher.IMatcher;
import org.sidiff.matcher.MatcherUtil;

/**
 * 
 * Class for the matching settings tab.
 * @author Daniel Roedder, Robert Müller
 */
public class DomainMatchingEnginesPreferenceTab extends AbstractDomainPreferenceTab {

	private IPreferenceField matchersField;
	private Map<String, IPreferenceField> matcherOptions;

	@Override
	public void createPreferenceFields(List<IPreferenceField> list) {
		// get all non-generic matchers
		List<IMatcher> matchers = new ArrayList<>();
		for(IMatcher matcher : MatcherUtil.getAvailableMatchers(Collections.singleton(getDocumentType()))) {
			if(!matcher.getDocumentTypes().contains(EMFModelAccess.GENERIC_DOCUMENT_TYPE)) {
				matchers.add(matcher);
			}
		}

		matchersField = PreferenceFieldFactory.createOrderedList(
				MatchingSettingsAdapter.KEY_MATCHERS(getDocumentType()),
				"Matchers",
				matchers,
				new IPreferenceValueConverter<IMatcher>() {
					@Override
					public String getValue(IMatcher value) {
						return value.getKey();
					}
					@Override
					public String getLabel(IMatcher value) {
						return value.getName();
					}
				});
		matchersField.addPropertyChangeListener(new IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				List<String> newValue = Arrays.asList((String[])event.getNewValue());
				for(Entry<String, IPreferenceField> matcherOptions : matcherOptions.entrySet()) {
					matcherOptions.getValue().setVisible(newValue.contains(matcherOptions.getKey()));
				}
			}
		});
		list.add(matchersField);

		matcherOptions = new HashMap<String, IPreferenceField>();
		for(IMatcher matcher : matchers) {
			if(matcher instanceof IConfigurable) {
				IPreferenceField matcherOption = PreferenceFieldFactory.createCheckBoxList(
						MatchingSettingsAdapter.KEY_MATCHER_OPTIONS(matcher.getKey()),
						matcher.getName(),
						((IConfigurable)matcher).getConfigurationOptions().keySet(),
						new IdentityPreferenceValueConverter());
				list.add(matcherOption);
				matcherOptions.put(matcher.getKey(), matcherOption);
			}
		}
	}
}
