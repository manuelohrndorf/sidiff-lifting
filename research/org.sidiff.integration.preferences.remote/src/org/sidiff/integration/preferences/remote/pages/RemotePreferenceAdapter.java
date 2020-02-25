package org.sidiff.integration.preferences.remote.pages;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
 * The remote preference adapter allows storage of
 * {@link RemotePreferences} using a {@link IPreferenceStore}
 * @author rmueller
 */
public class RemotePreferenceAdapter {

	private static final String REMOTE_APPLICATION_PREFERENCE_QUALIFIER = "org.sidiff.integration.preferences.remote.application";

	/**
	 * Prefix for preferences that store the document types for which preferences
	 * for a particular property exist.
	 */
	private static final String DOCUMENT_TYPE_PREFIX = "docTypes:";

	private static final String PREFERENCE_SERVER_ID = "serverId";

	private IPreferenceStore preferenceStore;

	/**
	 * Creates an adapter that uses the default global preference store.
	 */
	public RemotePreferenceAdapter() {
		this.preferenceStore = new ScopedPreferenceStore(InstanceScope.INSTANCE, REMOTE_APPLICATION_PREFERENCE_QUALIFIER);
	}

	/**
	 * Creates an adapter that uses the given preference store.
	 * @param preferenceStore the preference store
	 */
	public RemotePreferenceAdapter(IPreferenceStore preferenceStore) {
		this.preferenceStore = Objects.requireNonNull(preferenceStore);
	}

	public IPreferenceStore getPreferenceStore() {
		return preferenceStore;
	}

	/**
	 * Returns remote preferences based on the values
	 * stored in the preference store.
	 * @return remote preferences
	 */
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

	private boolean override;

	/**
	 * Updates the preference store using the given remote preferences.
	 * If the serverId matches the current serverId, only non-existent values are updated.
	 * Else, the previous values will be overridden.
	 * @param serverId an ID that uniquely identifies a server
	 * @param remotePreferences the remote preferences
	 */
	public void setRemotePreferences(String serverId, RemotePreferences remotePreferences) {
		override = !preferenceStore.getString(PREFERENCE_SERVER_ID).equals(serverId);
		if(override) {
			preferenceStore.setValue(PREFERENCE_SERVER_ID, serverId);
		}

		setGeneralProperties(remotePreferences.getGeneralProperties());
		setExtractionProperties(remotePreferences.getExtractionProperties());
		setValidationProperties(remotePreferences.getValidationProperties());
	}

	private void setGeneralProperties(GeneralProperties generalProperties) {
		setRemoteProperty(generalProperties.getScope());
	}

	private void setExtractionProperties(ExtractionProperties extractionProperties) {
		setRemoteProperty(extractionProperties.getMergeImports());
		setRemoteProperty(extractionProperties.getUnmergeImports());

		storeDocumentTypes(extractionProperties.getTechnicalDifferenceBuilderProperties());
		extractionProperties.getTechnicalDifferenceBuilderProperties().forEach(this::setRemoteProperty);

		storeDocumentTypes(extractionProperties.getRecognitionRuleSorterProperties());
		extractionProperties.getRecognitionRuleSorterProperties().forEach(this::setRemoteProperty);

		storeDocumentTypes(extractionProperties.getRuleBaseProperties());
		extractionProperties.getRuleBaseProperties().forEach(this::setRemoteProperty);
	}

	private void setValidationProperties(ValidationProperties validationPropeties) {
		setRemoteProperty(validationPropeties.getValidateModels());
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
			updatePreference(DOCUMENT_TYPE_PREFIX + name, StringListSerializer.DEFAULT.serialize(docTypes));
		}
	}

	private void setRemoteProperty(MultiSelectionRemoteApplicationProperty<String> property) {
		updatePreference(getKey(property), StringListSerializer.DEFAULT.serialize(property.getValues()));
	}

	private void setRemoteProperty(SingleSelectionRemoteApplicationProperty<? extends Serializable> property) {
		updatePreference(getKey(property), Objects.toString(property.getValue()));
	}

	private void updatePreference(String name, String value) {
		if(override || !preferenceStore.contains(name)) {
			preferenceStore.setValue(name, value);
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
