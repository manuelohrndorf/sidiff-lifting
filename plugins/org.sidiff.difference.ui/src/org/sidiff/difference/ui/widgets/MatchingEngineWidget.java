package org.sidiff.difference.ui.widgets;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
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
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.ui.PlatformUI;
import org.sidiff.common.emf.access.Scope;
import org.sidiff.common.settings.BaseSettingsItem;
import org.sidiff.common.settings.ISettingsChangedListener;
import org.sidiff.common.ui.widgets.IWidget;
import org.sidiff.common.ui.widgets.IWidgetSelection;
import org.sidiff.common.ui.widgets.IWidgetValidation;
import org.sidiff.common.ui.widgets.IWidgetValidation.ValidationMessage.ValidationType;
import org.sidiff.configuration.IConfigurable;
import org.sidiff.matcher.IMatcher;
import org.sidiff.matcher.IncrementalMatcher;
import org.sidiff.matcher.MatcherUtil;
import org.sidiff.matching.api.settings.MatchingSettings;

public class MatchingEngineWidget implements IWidget, IWidgetSelection, IWidgetValidation, ISettingsChangedListener {

	private final boolean compabilityMode;
	
	protected MatchingSettings settings;
	protected Collection<Resource> inputModels;

	protected SortedMap<String, IMatcher> matchers;

	protected Composite container;
	
	protected List list_matchers;
	
	protected Button moveUp;
	protected Button moveDown;

	
	protected Composite config_container;
	
	protected Button useIncrementalMatcher;
	
	protected IPageChangedListener pageChangedListener;


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

		// Set selection:
		if (list_matchers.getItems().length != 0) {
			// Prefer Unique identifier matcher
			int index = list_matchers.indexOf("Ecore ID Matcher");

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
				
				if(list_matchers.getSelectionCount() > 1){
					moveUp.setEnabled(false);
					moveDown.setEnabled(false);
				}else{
					moveUp.setEnabled(true);
					moveDown.setEnabled(true);
				}
				
				settings.setMatcher(getSelection());
				config_container.dispose();
				config_container = new Composite(container, SWT.NONE);
				{
					GridLayout grid = new GridLayout(1, false);
					grid.marginWidth = 0;
					grid.marginHeight = 0;
					config_container.setLayout(grid);
				}
				//TODO DR 08.12.2015
				/*
				 * This shall be done in own widgets, one for @see{IConfigurable} similar to
				 * this code and one for @see{IConfigurationCapable} which is responsible for selecting one
				 * of the available configurations
				 */
				
				if(compabilityMode && getSelection() instanceof IConfigurable){
					final IConfigurable configurableMatcher = (IConfigurable)getSelection();
					for(String option : configurableMatcher.getConfigurationOptions().keySet()){
						
						final String key = option;
						// use a checkbox for boolean values 
						if(configurableMatcher.getConfigurationOptions().get(option) instanceof Boolean){
							final Button button = new Button(config_container, SWT.CHECK);
							button.setText(option);
							button.addSelectionListener(new SelectionAdapter() {
								@Override
								public void widgetSelected(SelectionEvent e) {
									if(button.getSelection()){
										configurableMatcher.setConfigurationOption(key, true);
									}else{
										configurableMatcher.setConfigurationOption(key, false);
									}
								}
							});
						}
					}
					
				}

				container.getParent().layout();
			}
		});

		
		moveUp = new Button(container, SWT.PUSH);
		moveUp.setText("Move Up");
		moveUp.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int index = list_matchers.getSelectionIndex();
				if(index > 0){
					list_matchers.deselect(index);
					String item = list_matchers.getItem(index);
					list_matchers.remove(index);
					list_matchers.add(item, index-1);
					list_matchers.select(index-1);
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
					String item = list_matchers.getItem(index);
					list_matchers.deselect(index);
					list_matchers.remove(index);
					list_matchers.add(item, index+1);
					list_matchers.select(index+1);
				}
			}
		});
		
		config_container = new Composite(container, SWT.NONE);
		{
			GridLayout grid = new GridLayout(1, false);
			grid.marginWidth = 0;
			grid.marginHeight = 0;
			config_container.setLayout(grid);
		}		
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
		for (Iterator<IMatcher> iterator = MatcherUtil.getAvailableMatchers(inputModels).iterator(); iterator.hasNext();) {
			IMatcher matcher = iterator.next();
			matchers.put(matcher.getName(), matcher);
		}
	}

	public IMatcher getSelection() {
		
		// If more than one Matcher selected make use of @link{IncrementalMatcher} class.
		if(list_matchers.getSelectionCount() > 1){
			
			// Create Matcher list according to selection
			ArrayList<IMatcher> imatchers = new ArrayList<IMatcher>();
			for(String matcherName : list_matchers.getSelection()){
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
		else{
			//TODO Possible out-of-bounds Exception
			return matchers.get(list_matchers.getSelection()[0]);
		}

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
	public ValidationMessage getValidationMessage() {
		ValidationMessage message;
		if (validate()) {
			message = new ValidationMessage(ValidationType.OK, "");
		} else {
			if (settings.getScope().equals(Scope.RESOURCE_SET)
					&& !matchers.get(list_matchers.getSelection()[0]).isResourceSetCapable()) {
				message = new ValidationMessage(ValidationType.ERROR, "Selected matching engine does not support resourceset scope, select another matching engine!");
			} else {
				message = new ValidationMessage(ValidationType.ERROR, "Please select a matching engine");
			}
			message = new ValidationMessage(ValidationType.ERROR, "Unexpected error due to the selection of the matching engine");
		}
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
		}
	}

	public MatchingSettings getSettings() {
		return settings;
	}

	public void setSettings(MatchingSettings settings) {
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