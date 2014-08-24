package org.sidiff.difference.lifting.ui.widgets;

import java.util.Iterator;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.ui.PlatformUI;
import org.sidiff.difference.lifting.facade.util.PipelineUtils;
import org.sidiff.difference.lifting.settings.ISettingsChangedListener;
import org.sidiff.difference.lifting.settings.Settings;
import org.sidiff.difference.lifting.settings.SettingsItem;
import org.sidiff.difference.lifting.ui.util.InputModels;
import org.sidiff.difference.matcher.IMatcher;
import org.sidiff.difference.matcher.util.MatcherUtil;
import org.silift.common.util.emf.Scope;
import org.silift.common.util.ui.widgets.IWidget;
import org.silift.common.util.ui.widgets.IWidgetSelection;
import org.silift.common.util.ui.widgets.IWidgetValidation;

public class MatchingEngineWidget implements IWidget, IWidgetSelection, IWidgetValidation, ISettingsChangedListener {

	protected Settings settings;
	protected InputModels inputModels;

	protected SortedMap<String, IMatcher> matchers;

	protected Composite container;
	protected List list_matchers;

	protected IPageChangedListener pageChangedListener;

	public MatchingEngineWidget(InputModels inputModels) {
		this.inputModels = inputModels;

		// Connect scope widget:

		getMatchers();
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	@Override
	public Composite createControl(Composite parent) {

		container = new Composite(parent, SWT.NONE);
		{
			GridLayout grid = new GridLayout(1, false);
			grid.marginWidth = 0;
			grid.marginHeight = 0;
			container.setLayout(grid);
		}

		// Matcher controls:
		Label matchingLabel = new Label(container, SWT.NONE);
		matchingLabel.setText("Matching Engine:");

		list_matchers = new List(container, SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL);
		{
			GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
			data.heightHint = 70;
			list_matchers.setLayoutData(data);
		}
		list_matchers.setItems(matchers.keySet().toArray(new String[0]));

		// Set selection:
		if (list_matchers.getItems().length != 0) {
			// Prefer Unique identifier matcher
			int index = list_matchers.indexOf("UUID Matcher");

			if (index != -1)
				list_matchers.setSelection(index);
			else
				list_matchers.setSelection(0);

		} else {
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Missing Matcher",
					"No matchers are found!");
		}
		list_matchers.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				settings.setMatcher(getSelection());
			}
		});

		settings.setMatcher(this.getSelection());

		return container;
	}

	@Override
	public Composite getWidget() {
		return container;
	}

	@Override
	public void setLayoutData(Object layoutData) {
		container.setLayoutData(layoutData);
	}

	protected void getMatchers() {
		matchers = new TreeMap<String, IMatcher>();

		// Search registered matcher extension points
		Set<IMatcher> matcherSet = MatcherUtil.getAvailableMatchers(inputModels.getResourceA(),
				inputModels.getResourceB());

		for (Iterator<IMatcher> iterator = matcherSet.iterator(); iterator.hasNext();) {
			IMatcher matcher = iterator.next();
			matchers.put(matcher.getName(), matcher);
		}
	}

	public IMatcher getSelection() {

		return matchers.get(list_matchers.getSelection()[0]);

	}

	public SortedMap<String, IMatcher> getMatchingEngines() {
		return matchers;
	}

	@Override
	public boolean validate() {
		if (list_matchers.getSelectionIndex() == -1) {
			return false;
		} else if (settings.getScope().equals(Scope.RESOURCE_SET)
				&& !matchers.get(list_matchers.getSelection()[0]).isResourceSetCapable()) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public String getValidationMessage() {
		if (validate()) {
			return "";
		} else {
			if (settings.getScope().equals(Scope.RESOURCE_SET)
					&& !matchers.get(list_matchers.getSelection()[0]).isResourceSetCapable()) {
				return "Selected matching engine does not support resourceset scope, select another matching engine!!";

			} else {
				return "Please select a matching engine";
			}
		}
	}

	@Override
	public void addSelectionListener(SelectionListener listener) {
		if (list_matchers == null) {
			throw new RuntimeException("Create controls first!");
		}
		list_matchers.addSelectionListener(listener);
	}

	@Override
	public void removeSelectionListener(SelectionListener listener) {
		if (list_matchers != null) {
			list_matchers.removeSelectionListener(listener);
		}
	}

	@Override
	public void settingsChanged(Enum<?> item) {
		if (item.equals(SettingsItem.SCOPE)) {
			pageChangedListener.pageChanged(null);
		}
	}

	public Settings getSettings() {
		return settings;
	}

	public void setSettings(Settings settings) {
		this.settings = settings;
		this.settings.addSettingsChangedListener(this);
	}

	public IPageChangedListener getPageChangedListener() {
		return pageChangedListener;
	}

	public void setPageChangedListener(IPageChangedListener pageChangedListener) {
		this.pageChangedListener = pageChangedListener;
	}
}