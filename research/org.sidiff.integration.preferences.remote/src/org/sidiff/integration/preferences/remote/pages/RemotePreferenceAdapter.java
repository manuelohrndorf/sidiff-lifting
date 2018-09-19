package org.sidiff.integration.preferences.remote.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.sidiff.common.util.StringListSerializer;
import org.sidiff.remote.common.settings.ExtractionProperties;
import org.sidiff.remote.common.settings.GeneralProperties;
import org.sidiff.remote.common.settings.MultiSelectionRemoteApplicationProperty;
import org.sidiff.remote.common.settings.RemoteApplicationProperty;
import org.sidiff.remote.common.settings.RemotePreferences;
import org.sidiff.remote.common.settings.SingleSelectionRemoteApplicationProperty;
import org.sidiff.remote.common.settings.ValidationProperties;

/**
 * 
 * @author Robert Müller
 *
 */
public class RemotePreferenceAdapter {

	private static final String REMOTE_APPLICATION_PREFERENCE_QUALIFIER = "org.sidiff.integration.preferences.remote.application";

	/**
	 * Prefix for preferences that store the document types for which preferences
	 * for a particular property exist.
	 */
	private static final String DOCUMENT_TYPE_PREFIX = "docTypes:";

	private IPreferenceStore preferenceStore;

	public RemotePreferenceAdapter() {
		this.preferenceStore = new ScopedPreferenceStore(InstanceScope.INSTANCE, REMOTE_APPLICATION_PREFERENCE_QUALIFIER);
	}

	public IPreferenceStore getPreferenceStore() {
		return preferenceStore;
	}

	public RemotePreferences getRemotePreferences() {
		return new RemotePreferences(
				createGeneralProperties(),
				createExtractionProperties(),
				createValidationProperties());
	}

	private GeneralProperties createGeneralProperties() {
		return new GeneralProperties(
				createSingleStringProperty(RemoteApplicationProperty.GENERIC_DOCUMENT_TYPE, GeneralProperties.SCOPE));
	}

	private ExtractionProperties createExtractionProperties() {
		return new ExtractionProperties(
				createBooleanProperty(ExtractionProperties.MERGE_IMPORTS),
				createBooleanProperty(ExtractionProperties.UNMERGE_IMPORTS),
				createPropertyList(ExtractionProperties.TECHNICAL_DIFFERENCE_BUILDER, this::createMultiStringProperty),
				createPropertyList(ExtractionProperties.RECOGNITION_RULE_SORTER, this::createSingleStringProperty),
				createPropertyList(ExtractionProperties.RULE_BASE, this::createMultiStringProperty));
	}

	private ValidationProperties createValidationProperties() {
		return new ValidationProperties(
					createBooleanProperty(ValidationProperties.VALIDATE_MODELS));
	}

	private <T extends RemoteApplicationProperty<String>> List<T> createPropertyList(
			final String name, BiFunction<String, String, T> propertyCreator) {
		return StringListSerializer.DEFAULT
				.deserialize(preferenceStore.getString(DOCUMENT_TYPE_PREFIX + name)).stream()
				.map(docType -> propertyCreator.apply(docType, name))
				.collect(Collectors.toList());
	}

	private SingleSelectionRemoteApplicationProperty<String> createSingleStringProperty(String docType, String name) {
		SingleSelectionRemoteApplicationProperty<String> property = new SingleSelectionRemoteApplicationProperty<>(
				name, null, preferenceStore.getString(getKey(docType, name)));
		property.setDocumentType(docType);
		return property;
	}

	private MultiSelectionRemoteApplicationProperty<String> createMultiStringProperty(String docType, String name) {
		List<String> values = StringListSerializer.DEFAULT.deserialize(
				preferenceStore.getString(getKey(docType, name)));
		MultiSelectionRemoteApplicationProperty<String> property =
				new MultiSelectionRemoteApplicationProperty<>(name, null, values);
		property.setDocumentType(docType);
		return property;
	}

	private SingleSelectionRemoteApplicationProperty<Boolean> createBooleanProperty(String name) {
		return new SingleSelectionRemoteApplicationProperty<Boolean>(name, null, preferenceStore.getBoolean(name));
	}

	public void setRemotePreferences(RemotePreferences remotePreferences) {
		setGeneralProperties(remotePreferences.getGeneralProperties());
		setExtractionProperties(remotePreferences.getExtractionProperties());
		setValidationProperties(remotePreferences.getValidationProperties());
	}

	private void setGeneralProperties(GeneralProperties generalProperties) {
		setRemoteProperty(generalProperties.getScope());
	}

	private void setExtractionProperties(ExtractionProperties extractionProperties) {
		setRemotePropertyBoolean(extractionProperties.getMergeImports());
		setRemotePropertyBoolean(extractionProperties.getUnmergeImports());

		storeDocumentTypes(extractionProperties.getTechnicalDifferenceBuilderProperties());
		extractionProperties.getTechnicalDifferenceBuilderProperties().stream().forEach(this::setRemoteProperty);

		storeDocumentTypes(extractionProperties.getRecognitionRuleSorterProperties());
		extractionProperties.getRecognitionRuleSorterProperties().stream().forEach(this::setRemoteProperty);

		storeDocumentTypes(extractionProperties.getRuleBaseProperties());
		extractionProperties.getRuleBaseProperties().stream().forEach(this::setRemoteProperty);
	}

	private void setValidationProperties(ValidationProperties validationPropeties) {
		setRemotePropertyBoolean(validationPropeties.getValidateModels());
	}

	private <T extends RemoteApplicationProperty<String>> void storeDocumentTypes(List<T> list) {
		String name = null;
		List<String> docTypes = new ArrayList<>();
		for(T item : list) {
			if(name == null) {
				name = item.getName();
			} else {
				Assert.isLegal(name.equals(item.getName()), "list of properties has ambiguous name");
			}
			docTypes.add(item.getDocumentType());
		}
		if(name != null) {
			preferenceStore.setValue(DOCUMENT_TYPE_PREFIX + name, StringListSerializer.DEFAULT.serialize(docTypes));
		}
	}

	private void setRemoteProperty(MultiSelectionRemoteApplicationProperty<String> property) {
		if(!preferenceStore.contains(getKey(property))) {
			preferenceStore.setValue(getKey(property), StringListSerializer.DEFAULT.serialize(property.getValues()));
		}
	}

	private void setRemoteProperty(SingleSelectionRemoteApplicationProperty<String> property) {
		if(!preferenceStore.contains(getKey(property))) {
			preferenceStore.setValue(getKey(property), property.getValue());
		}
	}

	private void setRemotePropertyBoolean(SingleSelectionRemoteApplicationProperty<Boolean> property) {
		if(!preferenceStore.contains(getKey(property))) {
			preferenceStore.setValue(getKey(property), property.getValue());
		}
	}

	static String getKey(RemoteApplicationProperty<?> property) {
		return getKey(property.getDocumentType(), property.getName());
	}

	private static String getKey(String docType, String name) {
		if(docType.equals(RemoteApplicationProperty.GENERIC_DOCUMENT_TYPE)) {
			return name;
		}
		return name + "[" + docType + "]";
	}
}
