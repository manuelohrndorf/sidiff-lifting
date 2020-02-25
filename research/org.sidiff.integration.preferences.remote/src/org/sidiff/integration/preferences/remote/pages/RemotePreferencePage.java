package org.sidiff.integration.preferences.remote.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.widgets.Display;
import org.sidiff.integration.preferences.fieldeditors.IPreferenceField;
import org.sidiff.integration.preferences.fieldeditors.PreferenceFieldFactory;
import org.sidiff.integration.preferences.remote.internal.RemotePreferencesPlugin;
import org.sidiff.integration.preferences.ui.pages.PreferenceFieldPage;
import org.sidiff.integration.preferences.ui.pages.TabbedPreferencePage;
import org.sidiff.integration.preferences.valueconverters.MappingPreferenceValueConverter;
import org.sidiff.remote.application.connector.ConnectorFacade;
import org.sidiff.remote.application.connector.exception.ConnectionException;
import org.sidiff.remote.common.settings.ExtractionProperties;
import org.sidiff.remote.common.settings.GeneralProperties;
import org.sidiff.remote.common.settings.MultiSelectionRemoteApplicationProperty;
import org.sidiff.remote.common.settings.RemoteApplicationProperty;
import org.sidiff.remote.common.settings.RemotePreferences;
import org.sidiff.remote.common.settings.SingleSelectionRemoteApplicationProperty;
import org.sidiff.remote.common.settings.ValidationProperties;

/**
 * @author rmueller
 */
public class RemotePreferencePage extends TabbedPreferencePage {

	private RemotePreferenceAdapter preferenceAdapter;

	private PreferenceFieldPage generalPage;
	private TabbedPreferencePage extractionPage;
	private PreferenceFieldPage validationPage;

	private Map<String,PreferenceFieldPage> extractionDocTypes;

	public RemotePreferencePage() {
		super("Remote");
		super.noDefaultButton();
		super.setDescription("SiDiff - Remote Application Preferences");
		preferenceAdapter = new RemotePreferenceAdapter();
		initializeTabs();
	}

	private void initializeTabs() {
		final RemotePreferenceLoadingRunnable loadingRunnable = new RemotePreferenceLoadingRunnable();
		BusyIndicator.showWhile(Display.getCurrent(), loadingRunnable);

		IStatus result = loadingRunnable.getResult();
		if(result.isOK()) {
			final RemotePreferences remotePreferences = loadingRunnable.getRemotePreferences();
			initGeneralPage(remotePreferences.getGeneralProperties());
			initExtractionPage(remotePreferences.getExtractionProperties());
			initValidationPage(remotePreferences.getValidationProperties());
		} else {
			setErrorMessage(result.getMessage());
			ErrorDialog.openError(Display.getCurrent().getActiveShell(),
				"Remote preferences could not be loaded", result.getMessage(), result);
		}
	}

	private void initGeneralPage(GeneralProperties generalProperties) {
		generalPage = new PreferenceFieldPage();
		generalPage.addField(createRadioBox(generalProperties.getScope()));
		addTab(generalPage, "General");
	}

	private void initExtractionPage(ExtractionProperties extractionProperties) {
		extractionPage = new TabbedPreferencePage();
		extractionDocTypes = new HashMap<>();

		createExtractionSubPage(extractionProperties.getMergeImports())
			.addField(createCheckBox(extractionProperties.getMergeImports()));

		createExtractionSubPage(extractionProperties.getUnmergeImports())
			.addField(createCheckBox(extractionProperties.getUnmergeImports()));

		createPreferenceFields(extractionProperties.getTechnicalDifferenceBuilderProperties(),
				this::createExtractionSubPage, this::createOrderedList);

		createPreferenceFields(extractionProperties.getRecognitionRuleSorterProperties(),
				this::createExtractionSubPage, this::createRadioBox);

		createPreferenceFields(extractionProperties.getRuleBaseProperties(),
				this::createExtractionSubPage, this::createCheckBoxList);

		addTab(extractionPage, "Extraction");
	}

	private void initValidationPage(ValidationProperties validationProperties) {
		validationPage = new PreferenceFieldPage();
		validationPage.addField(createCheckBox(validationProperties.getValidateModels()));
		addTab(validationPage, "Validation");
	}

	@Override
	protected IPreferenceStore doGetPreferenceStore() {
		return preferenceAdapter.getPreferenceStore();
	}

	private <T extends RemoteApplicationProperty<String>> void createPreferenceFields(
			List<T> properties,
			Function<RemoteApplicationProperty<?>, PreferenceFieldPage> pageCreator,
			Function<T, IPreferenceField> fieldCreator) {
		for(T property : properties) {
			pageCreator.apply(property).addField(fieldCreator.apply(property));
		}
	}

	private PreferenceFieldPage createExtractionSubPage(RemoteApplicationProperty<?> property) {
		PreferenceFieldPage page = extractionDocTypes.get(property.getDocumentType());
		if(page == null) {
			page = new PreferenceFieldPage();
			String docType = property.getDocumentType();
			extractionDocTypes.put(docType, page);
			if(docType.equals(RemoteApplicationProperty.GENERIC_DOCUMENT_TYPE)) {
				extractionPage.addTab(page, "All domains");
			} else {
				String title = EPackage.Registry.INSTANCE.getEPackage(docType).getName();
				extractionPage.addTab(page, title, docType);
			}
		}
		return page;
	}

	private IPreferenceField createCheckBoxList(MultiSelectionRemoteApplicationProperty<String> property) {
		return PreferenceFieldFactory.createCheckBoxList(RemotePreferenceAdapter.getKey(property), property.getName(),
				property.getItems().keySet(), new MappingPreferenceValueConverter(property.getItems()));
	}

	private IPreferenceField createOrderedList(MultiSelectionRemoteApplicationProperty<String> property) {
		return PreferenceFieldFactory.createOrderedList(RemotePreferenceAdapter.getKey(property), property.getName(),
				property.getItems().keySet(), new MappingPreferenceValueConverter(property.getItems()));
	}

	private IPreferenceField createRadioBox(SingleSelectionRemoteApplicationProperty<String> property) {
		return PreferenceFieldFactory.createRadioBox(RemotePreferenceAdapter.getKey(property), property.getName(),
				property.getItems().keySet(), new MappingPreferenceValueConverter(property.getItems()));
	}

	private IPreferenceField createCheckBox(SingleSelectionRemoteApplicationProperty<Boolean> property) {
		return PreferenceFieldFactory.createCheckBox(RemotePreferenceAdapter.getKey(property), property.getName());
	}

	private class RemotePreferenceLoadingRunnable implements Runnable {

		private IStatus result;
		private RemotePreferences remotePreferences;

		@Override
		public void run() {
			if(ConnectorFacade.getCredentials() == null || !ConnectorFacade.getCredentials().isValid()) {
				result = new Status(IStatus.ERROR, RemotePreferencesPlugin.PLUGIN_ID,
						"Remote Application Connector is not configured correctly. Please configure it first.");
				return;
			}

			try {
				remotePreferences = Objects.requireNonNull(ConnectorFacade.getRemotePreferences(), "Server returned no remote preferences.");
				preferenceAdapter.setRemotePreferences(ConnectorFacade.getCredentials().getUrl(), remotePreferences);
				result = Status.OK_STATUS;
			} catch (ConnectionException e) {
				result = new Status(IStatus.ERROR, RemotePreferencesPlugin.PLUGIN_ID, "Connecting to remote server failed.", e);
			}
		}

		public IStatus getResult() {
			return result;
		}

		public RemotePreferences getRemotePreferences() {
			return remotePreferences;
		}
	}
}
