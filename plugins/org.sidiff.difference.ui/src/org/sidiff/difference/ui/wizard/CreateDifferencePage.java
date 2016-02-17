package org.sidiff.difference.ui.wizard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.PageChangedEvent;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.sidiff.common.settings.settings.ISettingsChangedListener;
import org.sidiff.common.ui.widgets.IWidget;
import org.sidiff.common.ui.widgets.IWidgetSelection;
import org.sidiff.common.ui.widgets.IWidgetValidation;
import org.sidiff.common.ui.widgets.IWidgetValidation.ValidationMessage.ValidationType;
import org.sidiff.configuration.IConfigurable;
import org.sidiff.difference.ui.widgets.table.ConfigurableTableEntryProvider;
import org.sidiff.difference.ui.widgets.table.ConfigurationCapeableTableEntryProvider;
import org.sidiff.difference.ui.widgets.table.DynamicTableEntryProviders;
import org.sidiff.difference.ui.widgets.table.PropertyTableWidget;
import org.sidiff.matcher.IMatcher;
import org.sidiff.matcher.IncrementalMatcher;
import org.sidiff.matcher.similarity.flooding.SimilarityFloodingMatcher;
import org.sidiff.matching.api.settings.MatchingSettings;
import org.sidiff.matching.api.settings.MatchingSettingsItem;

public class CreateDifferencePage extends WizardPage implements IPageChangedListener {

	private final String DEFAULT_MESSAGE = "...";
	private final MatchingSettings settings;
	private Composite container;
	private final SelectionAdapter validationListener;
	private final IPropertyChangeListener propertyChangeListener;
	private PropertyTableWidget matcherConfigurationOptionsWidget;
	private List<DynamicTableEntryProviders> matcherProviders = new ArrayList<DynamicTableEntryProviders>();

	public CreateDifferencePage(MatchingSettings settings) {
		super("CreateDifferencePage");

		this.settings = settings;
		this.setTitle("...");
		// this.setImageDescriptor(Activator.getImageDescriptor("icon.png"));
		// Listen for validation failures:
		validationListener = new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				validate();
			}
		};
		propertyChangeListener = new IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				// TODO [LM] Viele Aufrufe:
				// Wenn ein Provider hinzugefügt/entfernt wird, treten viele
				// solche Events auf (Entries add/remove, provider add/remove)
				validate();
			}
		};
	}

	@Override
	public void createControl(Composite parent) {

		// Add scrolling to this page
		final Composite wrapper = new Composite(parent, SWT.NONE);
		{
			GridLayout layout = new GridLayout(1, false);
			layout.marginWidth = 0;
			layout.marginHeight = 0;
			wrapper.setLayout(layout);
		}

		final ScrolledComposite sc = new ScrolledComposite(wrapper, SWT.V_SCROLL);
		GridData sc_data = new GridData(SWT.FILL, SWT.FILL, true, true);
		{
			sc.setLayoutData(sc_data);

			sc.setExpandHorizontal(true);
			sc.setExpandVertical(true);
		}

		container = new Composite(sc, SWT.NULL);
		{
			GridLayout layout = new GridLayout(1, false);
			layout.marginWidth = 10;
			layout.marginHeight = 10;
			container.setLayout(layout);
		}

		sc.setContent(container);

		// Create widgets for this page:
		createWidgets();

		// Compute height:
		sc.setMinSize(container.computeSize(SWT.DEFAULT, SWT.DEFAULT, true));
		Point containerSize = container.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
		sc_data.heightHint = containerSize.y;

		// Required to avoid an error in the system:
		setControl(wrapper);

		// Set dialog message:
		/*
		 * Note: Needed to force correct layout for scrollbar!? * Set at least
		 * to setMessage(" ")!
		 */
		setMessage(DEFAULT_MESSAGE);

		// Initial validation:
		validate();
	}

	private void createWidgets() {
		// Create and add table
		matcherConfigurationOptionsWidget = new PropertyTableWidget(true);
		addWidget(container, matcherConfigurationOptionsWidget);
		// Add page changes listener

		// Add property change listener for validation
		matcherConfigurationOptionsWidget.addPropertyChangeListener(propertyChangeListener);
		// Add test provider
		matcherConfigurationOptionsWidget
				.addProvider(new ConfigurableTableEntryProvider("Test", "ABC", new IConfigurable() {

					Map<String, Object> options = null;

					@Override
					public void setConfigurationOption(String key, Object value) {
						System.out.println("" + key + " -> " + value);
					}

					@Override
					public Map<String, Object> getConfigurationOptions() {
						if (options == null) {
							options = new HashMap<String, Object>();
							options.put("Hallo", "Welt");
							options.put("Integer", 1);
							options.put("Float", 1.123456f);
							options.put("Double", 1.123456789d);
							options.put("Boolean", Boolean.TRUE);
							options.put("Enum", ExampleEnum.PIZZA);
						}
						return options;
					}

					@Override
					public void configure(Map<String, Object> config) {
						throw new UnsupportedOperationException("Not implemented yet");
					}
				}));
		matcherConfigurationOptionsWidget.addProvider(new ConfigurationCapeableTableEntryProvider("Test2", "",
				new SimilarityFloodingMatcher(), "http://www.eclipse.org/emf/2002/Ecore")); //TODO Remove bundle for AnnotatedSignatureMatcher
		// Add provider for differencing services
		DifferencingServiceSelectionTableEntryProvider p = new DifferencingServiceSelectionTableEntryProvider(
				"Services", "http://www.eclipse.org/emf/2002/Ecore", settings);
		matcherConfigurationOptionsWidget.addProvider(p);
		// Add its subproviders for configuring the selected services
		for (DynamicTableEntryProviders pr : p.getSubProviders()) {
			matcherConfigurationOptionsWidget.addProvider(pr);
		}
		// Update the providers for the the selected matchers
		updateMatcherProviders();
		// Add a llistener for updating the providers for matching when the
		// selected matchers are changed
		settings.addSettingsChangedListener(new ISettingsChangedListener() {
			@Override
			public void settingsChanged(Enum<?> item) {
				if (MatchingSettingsItem.MATCHER.equals(item)) {
					updateMatcherProviders();
					validate();
				}
			}
		});
		// Inital validation
		validate();
	}

	private List<IMatcher> accumulateIncrementalMatchers(IMatcher matcher) {
		List<IMatcher> found = new ArrayList<IMatcher>();
		if (matcher != null) {
			if (matcher instanceof IncrementalMatcher) {
				for (IMatcher m : ((IncrementalMatcher) matcher).getMatchers()) {
					found.addAll(accumulateIncrementalMatchers(m));
				}
			} else {
				found.add(matcher);
			}
		}
		return found;
	}

	/**
	 * VALIDATE AFTER CALLING!
	 */
	private void updateMatcherProviders() {
		IMatcher matcher = settings.getMatcher();
		List<IMatcher> matchers = accumulateIncrementalMatchers(matcher);
		List<DynamicTableEntryProviders> toDelete = new ArrayList<DynamicTableEntryProviders>();
		for (DynamicTableEntryProviders provider : matcherProviders) {
			boolean found = false;
			for (IMatcher m : matchers) {
				if (provider.getConfigurationObject().equals(m)) {
					found = true;
					break;
				}
			}
			if (!found)
				toDelete.add(provider);
		}
		for (DynamicTableEntryProviders provider : toDelete) {
			matcherConfigurationOptionsWidget.removeProvider(provider);
		}
		matcherProviders.removeAll(toDelete);
		for (IMatcher m : matchers) {
			boolean found = false;
			for (DynamicTableEntryProviders provider : matcherProviders) {
				if (provider.getConfigurationObject().equals(m)) {
					found = true;
					break;
				}
			}
			if (!found) {
				DynamicTableEntryProviders entry = new DynamicTableEntryProviders("Matcher", m.getServiceID());
				entry.setConfigurationObject(m, "");
				matcherProviders.add(entry);
				matcherConfigurationOptionsWidget.addProvider(entry);
			}
		}
	}

	private void addWidget(Composite parent, IWidget widget) {
		// Create controls:
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		widget.createControl(parent);
		widget.setLayoutData(data);

		// Add validation:
		if (widget instanceof IWidgetSelection) {
			((IWidgetSelection) widget).addSelectionListener(validationListener);
		}
	}

	private void validate() {
		validateWidget(matcherConfigurationOptionsWidget);
	}

	private void validateWidget(IWidgetValidation widget) {
		if (!widget.validate()) {
			if (widget.getValidationMessage().getType().equals(ValidationType.ERROR)) {
				setErrorMessage(widget.getValidationMessage().getMessage());
				setPageComplete(false);
			} else {
				setMessage(widget.getValidationMessage().getMessage(), IMessageProvider.WARNING);
				setPageComplete(true);
			}
		} else {
			setErrorMessage(null);
			setMessage(DEFAULT_MESSAGE, IMessageProvider.NONE);
			setPageComplete(true);
		}
	}

	public void updateSettings() {
		// TODO [LM]
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public void pageChanged(PageChangedEvent event) {
		if (matcherConfigurationOptionsWidget != null) {
			matcherConfigurationOptionsWidget.pageChanged(event);
		}
		validate();
	}

}
