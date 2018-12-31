package org.sidiff.remote.application.preferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.sidiff.common.emf.access.Scope;
import org.sidiff.difference.lifting.api.util.PipelineUtils;
import org.sidiff.difference.lifting.recognitionrulesorter.IRecognitionRuleSorter;
import org.sidiff.difference.rulebase.view.ILiftingRuleBase;
import org.sidiff.difference.technical.ITechnicalDifferenceBuilder;
import org.sidiff.remote.common.settings.ExtractionProperties;
import org.sidiff.remote.common.settings.GeneralProperties;
import org.sidiff.remote.common.settings.IRemotePreferencesSupplier;
import org.sidiff.remote.common.settings.MultiSelectionRemoteApplicationProperty;
import org.sidiff.remote.common.settings.RemoteApplicationProperty;
import org.sidiff.remote.common.settings.RemotePreferences;
import org.sidiff.remote.common.settings.SingleSelectionRemoteApplicationProperty;
import org.sidiff.remote.common.settings.ValidationProperties;

/**
 * The "local" remote preference supplier creates default
 * remote preferences for the local SiDiff environment.
 * @author cpietsch, Robert Müller
 */
public class LocalRemotePreferencesSupplier implements IRemotePreferencesSupplier {

	public static final String NAME = "Local Remote Preference Supplier";
	public static final String KEY = "LocalRemotePreferencesSupplier";

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public RemotePreferences getRemotePreferences() {
		return new RemotePreferences(
				createGeneralProperties(),
				createExtractionProperties(),
				createValidationProperties());
	}

	private GeneralProperties createGeneralProperties() {
		Map<String,String> scope_items = new HashMap<String, String>();
		scope_items.put(Scope.RESOURCE.name(), "Resource");
		scope_items.put(Scope.RESOURCE_SET.name(), "Resource Set");

		return new GeneralProperties(
				createSingleStringProperty(GeneralProperties.SCOPE, RemoteApplicationProperty.GENERIC_DOCUMENT_TYPE, scope_items, Scope.RESOURCE.name()));
	}

	private ExtractionProperties createExtractionProperties() {
		return new ExtractionProperties(
				createBooleanProperty(ExtractionProperties.MERGE_IMPORTS, true),
				createBooleanProperty(ExtractionProperties.UNMERGE_IMPORTS, true),
				createTechnicalDifferenceBuilderPropertyList(),
				createRecognitionRuleSorterPropertyList(),
				createLiftingRuleBasePropertyList());
	}

	private ValidationProperties createValidationProperties() {
		return new ValidationProperties(
				createBooleanProperty(ValidationProperties.VALIDATE_MODELS, true));
	}

	private List<MultiSelectionRemoteApplicationProperty<String>> createTechnicalDifferenceBuilderPropertyList() {
		Map<String, List<ITechnicalDifferenceBuilder>> builders = new HashMap<>();
		for (ITechnicalDifferenceBuilder builder : PipelineUtils.getAllAvailableTechnicalDifferenceBuilders()) {
			for (String documentType : builder.getDocumentTypes()) {
				builders.computeIfAbsent(documentType, (unused) -> new ArrayList<>()).add(builder);
			}
		}

		List<MultiSelectionRemoteApplicationProperty<String>> technicalDifferenceBuilderProperties = new ArrayList<>();
		for(String documentType : builders.keySet()) {
			Map<String, String> items = builders.get(documentType).stream()
					.collect(Collectors.toMap(ITechnicalDifferenceBuilder::getKey, ITechnicalDifferenceBuilder::getName));
			List<String> value = items.keySet().stream().findFirst().map(Collections::singletonList).orElseGet(Collections::emptyList);
			technicalDifferenceBuilderProperties.add(
					createMultiStringProperty(ExtractionProperties.TECHNICAL_DIFFERENCE_BUILDER, documentType, items, value));
		}
		return technicalDifferenceBuilderProperties;
	}

	private List<SingleSelectionRemoteApplicationProperty<String>> createRecognitionRuleSorterPropertyList() {
		Map<String, List<IRecognitionRuleSorter>> sorters = new HashMap<>();
		for(IRecognitionRuleSorter sorter : PipelineUtils.getAllAvailableRecognitionRuleSorters()) {
			for(String docType : sorter.getDocumentTypes()) {
				sorters.computeIfAbsent(docType, unused -> new ArrayList<>()).add(sorter);
			}
		}

		List<SingleSelectionRemoteApplicationProperty<String>> recognitionRuleSorterProperties = new ArrayList<>();
		for(String documentType : sorters.keySet()) {
			Map<String, String> items = sorters.get(documentType).stream()
					.collect(Collectors.toMap(IRecognitionRuleSorter::getKey, IRecognitionRuleSorter::getName));
			String value = items.keySet().stream().findFirst().orElse("");
			recognitionRuleSorterProperties.add(createSingleStringProperty(ExtractionProperties.RECOGNITION_RULE_SORTER, documentType, items, value));
		}
		return recognitionRuleSorterProperties;
	}

	private List<MultiSelectionRemoteApplicationProperty<String>> createLiftingRuleBasePropertyList() {
		Map<String, List<ILiftingRuleBase>> ruleBases = new HashMap<>();
		for (ILiftingRuleBase ruleBase : PipelineUtils.getAllAvailableRulebases()) {
			for (String documentType : ruleBase.getDocumentTypes()) {
				ruleBases.computeIfAbsent(documentType, (unused) -> new ArrayList<>()).add(ruleBase);
			}
		}

		List<MultiSelectionRemoteApplicationProperty<String>> ruleBaseProperties = new ArrayList<>();
		for(String documentType : ruleBases.keySet()) {
			Map<String, String> items = ruleBases.get(documentType).stream()
					.collect(Collectors.toMap(ILiftingRuleBase::getKey, ILiftingRuleBase::getName));
			ruleBaseProperties.add(
					createMultiStringProperty(ExtractionProperties.RULE_BASE, documentType, items, new ArrayList<>(items.keySet())));
		}
		return ruleBaseProperties;
	}

	private SingleSelectionRemoteApplicationProperty<String> createSingleStringProperty(
			String name, String docType, Map<String, String> items, String value) {
		SingleSelectionRemoteApplicationProperty<String> property = new SingleSelectionRemoteApplicationProperty<>(name, items, value);
		property.setDocumentType(docType);
		return property;
	}

	private MultiSelectionRemoteApplicationProperty<String> createMultiStringProperty(
			String name, String docType, Map<String, String> items, List<String> values) {
		MultiSelectionRemoteApplicationProperty<String> property = new MultiSelectionRemoteApplicationProperty<>(name, items, values);
		property.setDocumentType(docType);
		return property;
	}

	private SingleSelectionRemoteApplicationProperty<Boolean> createBooleanProperty(String name, boolean value) {
		Map<String,Boolean> boolean_items = new HashMap<String, Boolean>();
		boolean_items.put(Boolean.toString(true), true);
		boolean_items.put(Boolean.toString(false), false);
		return new SingleSelectionRemoteApplicationProperty<Boolean>(name, boolean_items, value);
	}
}
