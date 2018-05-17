package org.sidiff.difference.technical.ui.widgets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.ui.PlatformUI;
import org.sidiff.common.emf.access.Scope;
import org.sidiff.common.settings.BaseSettingsItem;
import org.sidiff.common.settings.ISettingsChangedListener;
import org.sidiff.common.ui.widgets.AbstractWidget;
import org.sidiff.common.ui.widgets.IWidgetSelection;
import org.sidiff.common.ui.widgets.IWidgetValidation;
import org.sidiff.common.ui.widgets.IWidgetValidation.ValidationMessage.ValidationType;
import org.sidiff.configuration.IConfigurable;
import org.sidiff.matcher.IMatcher;
import org.sidiff.matcher.IncrementalMatcher;
import org.sidiff.matcher.MatcherUtil;
import org.sidiff.matching.api.settings.MatchingSettings;
import org.sidiff.matching.api.settings.MatchingSettingsItem;

public class MatchingEngineWidget extends AbstractWidget implements IWidgetSelection, IWidgetValidation, ISettingsChangedListener {

	private final boolean compabilityMode;
	
	protected MatchingSettings settings;
	protected Collection<Resource> inputModels;

	protected SortedMap<String, IMatcher> matchers;

	protected Composite container;
	protected List list_matchers;
	protected Button moveUp;
	protected Button moveDown;
	protected Collection<MatcherConfiguration> matcherConfigurations;

	protected IPageChangedListener pageChangedListener;

	protected ValidationMessage message;

	public MatchingEngineWidget(Collection<Resource> inputModels, boolean compabilityMode) {
		this.inputModels = inputModels;
		this.compabilityMode = compabilityMode;
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
			GridLayout grid = new GridLayout(2, false);
			grid.marginWidth = 0;
			grid.marginHeight = 0;
			container.setLayout(grid);
		}
		
		// Matcher controls:
		Label matchingLabel = new Label(container, SWT.NONE);
		matchingLabel.setText("Matching Engine:");
		{
			GridData gridData = new GridData();
			gridData.horizontalAlignment = GridData.FILL;
			gridData.horizontalSpan = 2;
			matchingLabel.setLayoutData(gridData);
		}
		
		list_matchers = new List(container, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL);
		{
			GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
			gridData.heightHint = matchers.keySet().size() * 16;
			gridData.verticalSpan = 2;
			list_matchers.setLayoutData(gridData);
		}
		list_matchers.setItems(matchers.keySet().toArray(new String[0]));
		list_matchers.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateButtonStates();

				IMatcher selection = getSelection();
				if(selection != null) {
					settings.setMatcher(selection);
				}

				updateConfigurationWidgetVisibility();
			}
		});

		
		moveUp = new Button(container, SWT.PUSH);
		moveUp.setText("Move Up");
		moveUp.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int index = list_matchers.getSelectionIndex();
				if(index > 0){
					moveItem(index, index-1);
				}
			}
		});
		
		
		
		moveDown = new Button(container, SWT.PUSH);
		moveDown.setText("Move Down");
		moveDown.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int index = list_matchers.getSelectionIndex();
				if(index < list_matchers.getItemCount()-1){
					moveItem(index, index+1);
				}
			}
		});

		// Set selection:
		if (list_matchers.getItems().length != 0) {
			updateSelection();
		} else {
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
					"Missing Matcher", "No matchers are found!");
		}
		if(settings.getMatcher() == null) {
			settings.setMatcher(this.getSelection());
		}

		createConfigurationWidget();

		return container;
	}

	private void moveItem(int from, int to) {
		if(from == to) {
			return;
		}

		boolean wasSelected = list_matchers.isSelected(from);
		if(wasSelected) list_matchers.deselect(from);
		String item = list_matchers.getItem(from);
		list_matchers.remove(from);
		list_matchers.add(item, to);
		if(wasSelected) list_matchers.select(to);
		updateButtonStates();
	}

	private void createConfigurationWidget() {
		if(!compabilityMode) {
			return;
		}

		// TODO DR 08.12.2015
		/*
		 * This shall be done in own widgets, one for @see{IConfigurable} similar to
		 * this code and one for @see{IConfigurationCapable} which is responsible for
		 * selecting one of the available configurations
		 */

		Composite config_container = new Composite(container, SWT.NONE);
		{
			GridLayout grid = new GridLayout(1, false);
			grid.marginWidth = 0;
			grid.marginHeight = 0;
			config_container.setLayout(grid);
		}

		matcherConfigurations = new LinkedList<MatcherConfiguration>();
		for(Map.Entry<String, IMatcher> entry : matchers.entrySet()) {
			if(entry.getValue() instanceof IConfigurable) {
				MatcherConfiguration config = new MatcherConfiguration(entry.getValue());
				config.createControl(config_container);
				matcherConfigurations.add(config);
			}
		}

		updateConfigurationWidgetVisibility();
		updateConfigurationWidgetSelection();
	}

	private void updateConfigurationWidgetVisibility() {
		if(matcherConfigurations != null) {
			for(MatcherConfiguration config : matcherConfigurations) {
				config.updateVisibility();
			}
		}
	}

	private void updateConfigurationWidgetSelection() {
		if(matcherConfigurations != null) {
			for(MatcherConfiguration config : matcherConfigurations) {
				config.updateSelection();
			}
		}
	}

	@Override
	public Composite getWidget() {
		return container;
	}

	protected void getMatchers() {
		matchers = new TreeMap<String, IMatcher>();

		// Search registered matcher extension points
		java.util.List<IMatcher> matcherSet = MatcherUtil.getAvailableMatchers(inputModels);

		for (IMatcher matcher : matcherSet) {
			matchers.put(matcher.getName(), matcher);
		}
	}

	public IMatcher getSelection() {
		// If more than one Matcher selected make use of @link{IncrementalMatcher} class.
		String selection[] = list_matchers.getSelection();
		// order the selection according to the position in the list,
		// as the order of list_matchers.getSelection() is undefined
		Arrays.sort(selection, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return list_matchers.indexOf(o1) - list_matchers.indexOf(o2);
			}
		});

		if(selection.length == 0) {
			return null;
		} else if(selection.length == 1) {
			return matchers.get(selection[0]);
		} else {
			// Create Matcher list according to selection
			ArrayList<IMatcher> imatchers = new ArrayList<IMatcher>();
			for(String matcherName : selection){
				imatchers.add(matchers.get(matcherName));
			}
			
			// If there has been more than one beforehand, just update the matcher
			if(settings.getMatcher() instanceof IncrementalMatcher){
				IncrementalMatcher incMatcher = (IncrementalMatcher) settings.getMatcher();				
				incMatcher.setMatchers(imatchers);
				return settings.getMatcher();
			}
			// Otherwise create a new IncrementalMatcher and include all selected matchers
			else{
				IncrementalMatcher incMatcher = new IncrementalMatcher(imatchers);
				return incMatcher;				
			}
		}
	}

	public SortedMap<String, IMatcher> getMatchingEngines() {
		return matchers;
	}

	@Override
	public boolean validate() {
		if (list_matchers.getSelectionIndex() == -1) {
			message = new ValidationMessage(ValidationType.ERROR, "Please select a matching engine");
			return false;
		} else if(settings.getScope().equals(Scope.RESOURCE_SET) && !getSelection().isResourceSetCapable()) {
			message = new ValidationMessage(ValidationType.ERROR,
					"Selected matching engine does not support resourceset scope, select another matching engine!");
			return false;
		} else {
			message = new ValidationMessage(ValidationType.OK, "");
			return true;
		}
	}

	@Override
	public ValidationMessage getValidationMessage() {
		return message;
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
		if (item.equals(BaseSettingsItem.SCOPE)) {
			pageChangedListener.pageChanged(null);
		} else if (item.equals(MatchingSettingsItem.MATCHER)) {
			updateSelection();
		}
	}

	private java.util.List<IMatcher> getSettingsMatchers() {
		if(settings.getMatcher() == null) {
			return Collections.emptyList();
		} else if(settings.getMatcher() instanceof IncrementalMatcher) {
			return ((IncrementalMatcher)settings.getMatcher()).getMatchers();
		} else {
			return Collections.singletonList(settings.getMatcher());
		}
	}

	private void updateSelection() {
		if(list_matchers == null) {
			return;
		}

		// select based on settings
		if(settings.getMatcher() != null) {
			list_matchers.deselectAll();
			IMatcher prevMatcher = null;
			for(IMatcher matcher : getSettingsMatchers()) {
				int index = list_matchers.indexOf(matcher.getName());
				if(index != -1) {
					list_matchers.select(index);
					// move the current matcher below the previous matcher
					if(prevMatcher != null) {
						int prevIndex = list_matchers.indexOf(prevMatcher.getName());
						if(index < prevIndex) {
							moveItem(index, prevIndex);
						}
					}
					prevMatcher = matcher;
				}
			}
		}

		// select default
		if(list_matchers.getSelectionCount() == 0) {
			// Prefer Unique identifier matcher
			int index = list_matchers.indexOf("Ecore ID Matcher");

			if (index != -1)
				list_matchers.setSelection(index);
			else
				list_matchers.setSelection(0);
		}

		updateButtonStates();
		updateConfigurationWidgetVisibility();
		updateConfigurationWidgetSelection();
	}

	private void updateButtonStates() {
		moveUp.setEnabled(list_matchers.getSelectionCount() == 1 && list_matchers.getSelectionIndex() > 0);
		moveDown.setEnabled(list_matchers.getSelectionCount() == 1 && list_matchers.getSelectionIndex() < list_matchers.getItemCount()-1);
	}

	public MatchingSettings getSettings() {
		return settings;
	}

	public void setSettings(MatchingSettings settings) {
		this.settings = settings;
		this.settings.addSettingsChangedListener(this);
		updateSelection();
	}

	public IPageChangedListener getPageChangedListener() {
		return pageChangedListener;
	}

	public void setPageChangedListener(IPageChangedListener pageChangedListener) {
		this.pageChangedListener = pageChangedListener;
	}

	private class MatcherConfiguration {
		private IMatcher matcher;
		private Group group;
		private Map<String, Button> buttons;

		public MatcherConfiguration(IMatcher matcher) {
			if(!(matcher instanceof IConfigurable))
				throw new ClassCastException("matcher must implement IConfigurable");
			this.matcher = matcher;
		}

		public void createControl(Composite parent) {
			group = new Group(parent, SWT.NONE);
			group.setText(matcher.getName());
			group.setLayout(new RowLayout(SWT.VERTICAL));
			group.setLayoutData(new GridData()); // must be grid data to properly show / hide the group

			buttons = new HashMap<String, Button>();
			final IConfigurable configurable = (IConfigurable)matcher;
			for (final String optionKey : configurable.getConfigurationOptions().keySet()) {
				// use a checkbox for boolean values
				if (configurable.getConfigurationOptions().get(optionKey) instanceof Boolean) {
					final Button button = new Button(group, SWT.CHECK);
					button.setText(optionKey);
					button.addSelectionListener(new SelectionAdapter() {
						@Override
						public void widgetSelected(SelectionEvent e) {
							configurable.setConfigurationOption(optionKey, button.getSelection());
						}
					});
					buttons.put(optionKey, button);
				}
			}
		}

		public void updateVisibility() {
			if(list_matchers == null || group == null) {
				return;
			}

			boolean selected = list_matchers.isSelected(list_matchers.indexOf(matcher.getName()));
			group.setVisible(selected);
			((GridData)group.getLayoutData()).exclude = !selected;
			group.requestLayout();
		}

		public void updateSelection() {
			if(buttons == null) {
				return;
			}

			for(Map.Entry<String, Button> entry : buttons.entrySet()) {
				boolean selection = (Boolean)((IConfigurable)matcher).getConfigurationOptions().get(entry.getKey());

				for(IMatcher settingsMatcher : getSettingsMatchers()) {
					if(settingsMatcher instanceof IConfigurable && matcher.getKey().equals(settingsMatcher.getKey())) {
						selection = (Boolean)((IConfigurable)settingsMatcher).getConfigurationOptions().get(entry.getKey());
						break;
					}
				}

				entry.getValue().setSelection(selection);
			}
		}
	}
}