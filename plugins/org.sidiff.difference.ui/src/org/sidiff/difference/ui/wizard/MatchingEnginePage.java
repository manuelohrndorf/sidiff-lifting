package org.sidiff.difference.ui.wizard;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.sidiff.common.settings.settings.ISettingsChangedListener;
import org.sidiff.common.ui.widgets.IWidget;
import org.sidiff.common.ui.widgets.IWidgetSelection;
import org.sidiff.common.ui.widgets.IWidgetValidation;
import org.sidiff.difference.ui.widgets.MatchingEngineWidget;
import org.sidiff.difference.ui.widgets.ScopeWidget;
import org.sidiff.matching.api.settings.MatchingSettings;

public class MatchingEnginePage extends WizardPage {

	private final String DEFAULT_MESSAGE = "...";
	private Composite container;
	private SelectionAdapter validationListener;
	private ScopeWidget scopeWidget;
	private MatchingEngineWidget matchingEngineWidget;
	private final MatchingSettings settings;
	private final Collection<Resource> models;

	public MatchingEnginePage(Collection<Resource> models, MatchingSettings settings) {
		super("MatchingEnginePage");

		this.settings=settings;
		this.models = new ArrayList<Resource>(models);
		this.setTitle("...");
		// this.setImageDescriptor(Activator.getImageDescriptor("icon.png"));
		// Listen for validation failures:
		validationListener = new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
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
		scopeWidget = new ScopeWidget();
		scopeWidget.setSettings(settings);
		addWidget(container, scopeWidget);
		matchingEngineWidget = new MatchingEngineWidget(models, false);
		matchingEngineWidget.setSettings(settings);
		addWidget(container, matchingEngineWidget);
		settings.addSettingsChangedListener(new ISettingsChangedListener() {
			@Override
			public void settingsChanged(Enum<?> item) {
				validate();
			}
		});
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
	}

	private boolean validateWidget(IWidgetValidation widget) {
		return false;
	}


}
