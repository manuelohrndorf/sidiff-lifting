package org.sidiff.remote.application.preferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sidiff.common.emf.access.Scope;
import org.sidiff.difference.lifting.api.util.PipelineUtils;
import org.sidiff.difference.lifting.recognitionrulesorter.IRecognitionRuleSorter;
import org.sidiff.difference.rulebase.view.ILiftingRuleBase;
import org.sidiff.difference.technical.ITechnicalDifferenceBuilder;
import org.sidiff.remote.common.settings.ExtractionProperties;
import org.sidiff.remote.common.settings.GeneralProperties;
import org.sidiff.remote.common.settings.IRemotePreferencesSupplier;
import org.sidiff.remote.common.settings.MultiSelectionRemoteApplicationProperty;
import org.sidiff.remote.common.settings.RemotePreferences;
import org.sidiff.remote.common.settings.SingleSelectionRemoteApplicationProperty;
import org.sidiff.remote.common.settings.ValidationProperties;

/**
 * 
 * @author cpietsch
 *
 */
public class RemotePreferencesSupplier implements IRemotePreferencesSupplier {

	private RemotePreferences preferences;
	
	public RemotePreferencesSupplier() {
		Map<String,String> scope_items = new HashMap<String, String>();
		scope_items.put(Scope.RESOURCE.name(), "Resource");
		scope_items.put(Scope.RESOURCE_SET.name(), "Resource Set");

		Map<String,Boolean> boolean_items = new HashMap<String, Boolean>();
		boolean_items.put(Boolean.toString(true), true);
		boolean_items.put(Boolean.toString(false), false);
		
		Map<String, List<ITechnicalDifferenceBuilder>> builders = new HashMap<String, List<ITechnicalDifferenceBuilder>>();
		for (ITechnicalDifferenceBuilder technicalDifferenceBuilder : PipelineUtils
				.getAllAvailableTechnicalDifferenceBuilders()) {
			for (String documentType : technicalDifferenceBuilder.getDocumentTypes()) {
				if (!builders.containsKey(documentType)) {
					builders.put(documentType, new ArrayList<ITechnicalDifferenceBuilder>());
				}
				builders.get(documentType).add(technicalDifferenceBuilder);
			}
		}

		Map<String, List<IRecognitionRuleSorter>> sorters = new HashMap<String, List<IRecognitionRuleSorter>>();
		for (IRecognitionRuleSorter recognitionRuleSorter : PipelineUtils.getAllAvailableRecognitionRuleSorters()) {

			if (!sorters.containsKey(recognitionRuleSorter.getDocumentType())) {
				sorters.put(recognitionRuleSorter.getDocumentType(),
						new ArrayList<IRecognitionRuleSorter>());
			}
			sorters.get(recognitionRuleSorter.getDocumentType()).add(recognitionRuleSorter);
		}

		Map<String, List<ILiftingRuleBase>> rules = new HashMap<String, List<ILiftingRuleBase>>();
		for (ILiftingRuleBase rulesBase : PipelineUtils.getAllAvailableRulebases()) {
			for (String documentType : rulesBase.getDocumentTypes()) {
				if (!rules.containsKey(documentType)) {
					rules.put(documentType, new ArrayList<ILiftingRuleBase>());
				}
				rules.get(documentType).add(rulesBase);
			}
		}
		
		this.preferences = new RemotePreferences(createGeneralProperties(scope_items), createExtractionProperties(boolean_items, builders, sorters, rules), createValidationProperties(boolean_items));
	}
	
	@Override
	public String getName() {
		return this.getClass().getSimpleName();
	}

	@Override
	public String getKey() {
		return this.getClass().getName();
	}

	@Override
	public RemotePreferences getRemotePreference() {
		return preferences;
	}
	
	private GeneralProperties createGeneralProperties(Map<String, String> scope_items) {
		return new GeneralProperties(
				createSingleStringProperty(GeneralProperties.SCOPE, scope_items, Scope.RESOURCE.name()));
	}

	private ExtractionProperties createExtractionProperties(Map<String,Boolean> boolean_items, Map<String, List<ITechnicalDifferenceBuilder>> builders, Map<String, List<IRecognitionRuleSorter>> sorters, Map<String, List<ILiftingRuleBase>> rules) {
		return new ExtractionProperties(
				createBooleanProperty(ExtractionProperties.MERGE_IMPORTS, boolean_items, true),
				createBooleanProperty(ExtractionProperties.UNMERGE_IMPORTS, boolean_items, true),
				createTechnicalDifferenceBuilderPropertyList(builders),
				createRecognitionRuleSorterPropertyList(sorters),
				createLiftingRuleBasePropertyList(rules));
	}

	private ValidationProperties createValidationProperties(Map<String, Boolean> validation_items) {
		return new ValidationProperties(
					createBooleanProperty(ValidationProperties.VALIDATE_MODELS, validation_items, true));
	}

	private List<MultiSelectionRemoteApplicationProperty<String>> createTechnicalDifferenceBuilderPropertyList (Map<String, List<ITechnicalDifferenceBuilder>> builders){
		List<MultiSelectionRemoteApplicationProperty<String>> technicalDifferenceBuilderProperties = new ArrayList<MultiSelectionRemoteApplicationProperty<String>>();
		for(String documentType : builders.keySet()) {
			Map<String, String> builder_items = new HashMap<String, String>();
			List<String> builder_values  = new ArrayList<String>();
			for(ITechnicalDifferenceBuilder builder : builders.get(documentType)) {
				builder_items.put(builder.getKey(), builder.getName());
			}
			builder_values.add(builder_items.keySet().iterator().next());
			MultiSelectionRemoteApplicationProperty<String> multiSelectionRemoteApplicationProperty =
					new MultiSelectionRemoteApplicationProperty<String>(
							ExtractionProperties.TECHNICAL_DIFFERENCE_BUILDER, builder_items, builder_values);
			multiSelectionRemoteApplicationProperty.setDocumentType(documentType);
			technicalDifferenceBuilderProperties.add(createMultiStringProperty(documentType, builder_items, builder_values, ExtractionProperties.TECHNICAL_DIFFERENCE_BUILDER));
		}
		return technicalDifferenceBuilderProperties;
	}

	private List<SingleSelectionRemoteApplicationProperty<String>> createRecognitionRuleSorterPropertyList (Map<String, List<IRecognitionRuleSorter>> sorters){
		List<SingleSelectionRemoteApplicationProperty<String>> recognitionRuleSorterProperties = new ArrayList<>();
		for(String documentType : sorters.keySet()) {
			Map<String, String> sorter_items = new HashMap<String, String>();
			for(IRecognitionRuleSorter sorter : sorters.get(documentType)) {
				sorter_items.put(sorter.getKey(), sorter.getName());
			}
			SingleSelectionRemoteApplicationProperty<String> singleSelectionRemoteApplicationProperty =
					new SingleSelectionRemoteApplicationProperty<String>(ExtractionProperties.RECOGNITION_RULE_SORTER, sorter_items,
							sorter_items.keySet().iterator().next());
			singleSelectionRemoteApplicationProperty.setDocumentType(documentType);
			recognitionRuleSorterProperties.add(singleSelectionRemoteApplicationProperty);
		}
		return recognitionRuleSorterProperties;
	}
	
	private List<MultiSelectionRemoteApplicationProperty<String>> createLiftingRuleBasePropertyList (Map<String, List<ILiftingRuleBase>> rules){
		List<MultiSelectionRemoteApplicationProperty<String>> ruleBaseProperties = new ArrayList<>();
		for(String documentType : rules.keySet()) {
			Map<String, String> ruleBase_items = new HashMap<String, String>();
			for(ILiftingRuleBase ruleBase : rules.get(documentType)) {
				ruleBase_items.put(ruleBase.getName(), ruleBase.getName());
			}
			List<String> ruleBase_values = new ArrayList<String>(ruleBase_items.keySet());
			MultiSelectionRemoteApplicationProperty<String> multiSelectionRemoteApplicationProperty =
					new MultiSelectionRemoteApplicationProperty<String>(ExtractionProperties.RULE_BASE, ruleBase_items, ruleBase_values);
			multiSelectionRemoteApplicationProperty.setDocumentType(documentType);
			ruleBaseProperties.add(multiSelectionRemoteApplicationProperty);
		}
		return ruleBaseProperties;
	}
	
	private SingleSelectionRemoteApplicationProperty<String> createSingleStringProperty(String name, Map<String, String> items, String value) {
		SingleSelectionRemoteApplicationProperty<String> property = new SingleSelectionRemoteApplicationProperty<>(
				name, items, value);
		return property;
	}

	private MultiSelectionRemoteApplicationProperty<String> createMultiStringProperty(String docType, Map<String, String> builder_items, List<String> values, String name) {
		MultiSelectionRemoteApplicationProperty<String> property = new MultiSelectionRemoteApplicationProperty<>(name, builder_items, values);
		property.setDocumentType(docType);
		return property;
	}

	private SingleSelectionRemoteApplicationProperty<Boolean> createBooleanProperty(String name, Map<String,Boolean> boolean_items, boolean value) {
		return new SingleSelectionRemoteApplicationProperty<Boolean>(name, boolean_items, value);
	}
}
