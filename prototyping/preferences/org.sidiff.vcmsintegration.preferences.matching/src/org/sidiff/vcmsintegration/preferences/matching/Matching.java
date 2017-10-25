package org.sidiff.vcmsintegration.preferences.matching;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.sidiff.candidates.CandidatesUtil;
import org.sidiff.candidates.ICandidates;
import org.sidiff.configuration.IConfigurable;
import org.sidiff.correspondences.CorrespondencesUtil;
import org.sidiff.correspondences.ICorrespondences;
import org.sidiff.matcher.IMatcher;
import org.sidiff.matcher.MatcherUtil;
import org.sidiff.similarities.ISimilarities;
import org.sidiff.similarities.SimilaritiesServiceUtil;
import org.sidiff.similaritiescalculation.ISimilaritiesCalculation;
import org.sidiff.similaritiescalculation.SimilaritiesCalculationUtil;
import org.sidiff.vcmsintegration.preferences.fieldeditors.CheckListSelectField;
import org.sidiff.vcmsintegration.preferences.fieldeditors.OrderListSelectField;
import org.sidiff.vcmsintegration.preferences.fieldeditors.PreferenceField;
import org.sidiff.vcmsintegration.preferences.fieldeditors.RadioBoxPreferenceField;
import org.sidiff.vcmsintegration.preferences.fieldeditors.ValueAndLabelProvider;
import org.sidiff.vcmsintegration.preferences.interfaces.ISiDiffEnginesPreferenceTab;

/**
 * 
 * Class for the matching settings tab.
 * @author Daniel Roedder
 */
public class Matching implements ISiDiffEnginesPreferenceTab{
	
	/**
	 * List to hold all {@link org.sidiff.vcmsintegration.preferences.fieldeditors.PreferenceField}
	 */
	private ArrayList<PreferenceField> fieldList;
	
	/**
	 * The {@link OrderListSelectField} for the order the {@link IMatcher}s are used
	 */
	private OrderListSelectField matchingOrder;
	
	/**
	 * The {@link RadioBoxPreferenceField} for the candidate service
	 */
	private RadioBoxPreferenceField candidatesServices;
	
	/**
	 * The {@link PreferenceField} for the correspondences service
	 */
	private PreferenceField correspondencesServices;
	
	/**
	 * The {@link PreferenceField} for the similarities services
	 */
	private PreferenceField similaritiesServices;
	
	/**
	 * The {@link PreferenceField} for the similarities calculation services
	 */
	private PreferenceField similaritiesCalculationServices;
	
	/**
	 * The {@link HashMap} for the matcher options fields
	 */
	private HashMap<String, PreferenceField> matcherOptions;
	
	/**
	 * The {@link IPreferenceStore} to be used
	 */
	private IPreferenceStore store;
	
	/**
	 * @see org.sidiff.vcmsintegration.preferences.interfaces.ISiDiffEnginesPreferenceTab#getTitle()
	 */
	@Override
	public String getTitle() {
		return "Matching";
	}

	/**
	 * @see org.sidiff.vcmsintegration.preferences.interfaces.ISiDiffEnginesPreferenceTab#getTabContent()
	 */
	@Override
	public Iterable<PreferenceField> getTabContent() {
		fieldList = new ArrayList<PreferenceField>();
		
		matcherOptions = new HashMap<String, PreferenceField>();
		
		List<IMatcher> matchers = MatcherUtil.getAllAvailableMatchers();
		matchingOrder = OrderListSelectField.createFromIterable("matchingOrder", "Matching Order", matchers, new ValueAndLabelProvider<IMatcher>() {
			public String[] get(IMatcher input) { return new String[]{input.getKey(), input.getName()};}
		});
		fieldList.add(matchingOrder);
		
		for(IMatcher matcher : matchers) {
			if(matcher instanceof IConfigurable) {
				Map<String, Object> options = ((IConfigurable) matcher).getConfigurationOptions();
				PreferenceField matcherOptions = CheckListSelectField.createFromIterable(matcher.getKey(), matcher.getName(), options.keySet(), new ValueAndLabelProvider<String>() {
					public String[] get(String input) { return new String[]{input, input}; }
				});
				fieldList.add(matcherOptions);
				this.matcherOptions.put(matcher.getKey(), matcherOptions);
			}
		}
		
		candidatesServices = RadioBoxPreferenceField.createFromIterable("candidatesServices", "Candidates Services", CandidatesUtil.getAvailableCandidatesServices(), new ValueAndLabelProvider<ICandidates>() {
			@Override public String[] get(ICandidates input) { return new String[]{ input.getServiceID(), input.getClass().getSimpleName() }; }
		});
		fieldList.add(candidatesServices);
		
		correspondencesServices = RadioBoxPreferenceField.createFromIterable("correspondencesServices", "Correspondences Services", CorrespondencesUtil.getAllAvailableCorrespondencesServices(), new ValueAndLabelProvider<ICorrespondences>() {
			@Override public String[] get(ICorrespondences input) { return new String[]{ input.getServiceID(), input.getClass().getSimpleName() }; }
		});
		fieldList.add(correspondencesServices);
		
		similaritiesServices = RadioBoxPreferenceField.createFromIterable("similaritiesServices", "Similarities Services", SimilaritiesServiceUtil.getAvailableSimilaritiesService(), new ValueAndLabelProvider<ISimilarities>() {
			@Override public String[] get(ISimilarities input) { return new String[]{ input.getServiceID(), input.getClass().getSimpleName() }; }
		});
		fieldList.add(similaritiesServices);
		
		similaritiesCalculationServices = RadioBoxPreferenceField.createFromIterable("similaritiesCalculationServices", "Similarities Calculation Services", SimilaritiesCalculationUtil.getAvailableISimilaritiesCalculationServices(), new ValueAndLabelProvider<ISimilaritiesCalculation>() {
			@Override public String[] get(ISimilaritiesCalculation input) { return new String[]{ input.getServiceID(), input.getClass().getSimpleName() }; }
		});
		fieldList.add(similaritiesCalculationServices);
		
		for (PreferenceField field : fieldList) {
			field.setPreferenceStore(store);
		}
		
		return fieldList;
	}
	
	/**
	 * Superclass method, used to enable / disable matcher specific options if the matcher is enabled / disabled
	 * @see org.sidiff.vcmsintegration.preferences.interfaces.ISiDiffEnginesPreferenceTab#propertyChange(org.eclipse.jface.util.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		System.out.printf("Property Changed! Old: %s; New: %s\n", event.getOldValue(), event.getNewValue());
		
		if(event.getSource() == matchingOrder) {
			List<String> newValue = Arrays.asList((String[]) event.getNewValue());
			for(Entry<String, PreferenceField> matcherOptions: this.matcherOptions.entrySet()) {
				if(newValue.contains(matcherOptions.getKey())) {
					matcherOptions.getValue().enable();
				} else {
					matcherOptions.getValue().disable();
				}
			}
		}
	}

	/**
	 * @see org.sidiff.vcmsintegration.preferences.interfaces.ISiDiffEnginesPreferenceTab#setPreferenceStore(org.eclipse.jface.preference.IPreferenceStore)
	 */
	@Override
	public void setPreferenceStore(IPreferenceStore store) {
		this.store = store;
		
	}

	/**
	 * @see org.sidiff.vcmsintegration.preferences.interfaces.ISiDiffEnginesPreferenceTab#getStepInPipeline()
	 */
	@Override
	public int getStepInPipeline() {
		return 0;
	}

}
