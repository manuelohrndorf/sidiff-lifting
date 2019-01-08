package org.sidiff.difference.technical.ui.widgets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.sidiff.common.emf.access.Scope;
import org.sidiff.common.settings.ISettingsChangedListener;
import org.sidiff.common.ui.widgets.AbstractModifiableWidget;
import org.sidiff.common.ui.widgets.IWidgetDisposable;
import org.sidiff.common.ui.widgets.IWidgetValidation;
import org.sidiff.common.ui.widgets.IWidgetValidation.ValidationMessage.ValidationType;
import org.sidiff.matcher.IMatcher;
import org.sidiff.matcher.IncrementalMatcher;
import org.sidiff.matching.api.settings.MatchingSettings;
import org.sidiff.matching.api.settings.MatchingSettingsItem;
import org.sidiff.matching.input.InputModels;

public class MatchingEngineWidget extends AbstractModifiableWidget<IMatcher> implements IWidgetValidation, IWidgetDisposable, ISettingsChangedListener {

	protected MatchingSettings settings;

	protected SortedMap<String, IMatcher> matchers;
	
	protected Composite container;
	protected org.eclipse.swt.widgets.List list_matchers;
	protected Button moveUp;
	protected Button moveDown;

	protected ValidationMessage message;

	public MatchingEngineWidget(InputModels inputModels, MatchingSettings settings) {
		this.matchers = new TreeMap<>(IMatcher.MANAGER.getMatchers(inputModels.getResources()).stream()
				.collect(Collectors.toMap(matcher -> matcher.getName(), matcher -> matcher)));
		this.settings = Objects.requireNonNull(settings, "Settings must not be null");
		this.settings.addSettingsChangedListener(this);
	}

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

		list_matchers = new org.eclipse.swt.widgets.List(container, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL);
		{
			GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
			gridData.minimumHeight = 70;
			gridData.verticalSpan = 2;
			list_matchers.setLayoutData(gridData);
		}
		list_matchers.setItems(matchers.keySet().toArray(new String[0]));
		list_matchers.addSelectionListener(SelectionListener.widgetSelectedAdapter(e -> setSelection(getSelectedMatchers())));

		moveUp = new Button(container, SWT.PUSH);
		moveUp.setText("Move Up");
		moveUp.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, false, false));
		moveUp.addSelectionListener(SelectionListener.widgetSelectedAdapter(e -> {
				int index = list_matchers.getSelectionIndex();
				if(index > 0) {
					moveItem(index, index-1);
				}
			}));

		moveDown = new Button(container, SWT.PUSH);
		moveDown.setText("Move Down");
		moveDown.setLayoutData(new GridData(SWT.FILL, SWT.END, false, false));
		moveDown.addSelectionListener(SelectionListener.widgetSelectedAdapter(e -> {
				int index = list_matchers.getSelectionIndex();
				if(index < list_matchers.getItemCount()-1) {
					moveItem(index, index+1);
				}
			}));

		if (list_matchers.getItems().length > 0) {
			initSelection();
		}
		return container;
	}

	protected void initSelection() {
		if(getSelection().isEmpty()) {
			setSelection(getSettingsMatchers());
			if(getSelection().isEmpty()) {
				// select default
				IMatcher defaultSelection = matchers.get("Ecore ID Matcher");
				if(defaultSelection != null) {
					setSelection(Collections.singletonList(defaultSelection));					
				}
			}
		}
	}
	
	protected void moveItem(int from, int to) {
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

	@Override
	public Composite getWidget() {
		return container;
	}

	@Override
	public void hookSetSelection() {
		if(list_matchers == null) {
			return;
		}

		list_matchers.deselectAll();
		IMatcher prevMatcher = null;
		for(IMatcher matcher : getSelection()) {
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

		settings.setMatcher(getSelectedMatcher());
	}

	protected List<IMatcher> getSelectedMatchers() {
		return Stream.of(list_matchers.getSelection())
			.sorted((o1,o2) -> list_matchers.indexOf(o1) - list_matchers.indexOf(o2))
			.map(matchers::get)
			.collect(Collectors.toList());
	}

	public IMatcher getSelectedMatcher() {
		List<IMatcher> selection = getSelection();
		switch(selection.size()) {
			case 0: return null;
			case 1: return selection.get(0);
			default: return new IncrementalMatcher(selection);
		}
	}
	
	@Override
	public List<IMatcher> getSelectableValues() {
		return new ArrayList<>(matchers.values());
	}

	@Override
	public boolean validate() {
		if(list_matchers.getItemCount() == 0) {
			message = new ValidationMessage(ValidationType.ERROR, "No matchers are available");
			return false;
		} else if(list_matchers.getSelectionIndex() == -1) {
			message = new ValidationMessage(ValidationType.ERROR, "Please select a matching engine");
			return false;
		} else if(settings.getScope().equals(Scope.RESOURCE_SET) && !getSelectedMatcher().isResourceSetCapable()) {
			message = new ValidationMessage(ValidationType.ERROR,
					"Selected matching engine does not support ResourceSet scope, select another matching engine!");
			return false;
		} else {
			message = ValidationMessage.OK;
			return true;
		}
	}

	@Override
	public ValidationMessage getValidationMessage() {
		return message;
	}

	@Override
	public void settingsChanged(Enum<?> item) {
		if (item == MatchingSettingsItem.MATCHER) {
			setSelection(getSettingsMatchers());
			getWidgetCallback().requestValidation();
		}
	}

	protected java.util.List<IMatcher> getSettingsMatchers() {
		if(settings.getMatcher() == null) {
			return Collections.emptyList();
		} else if(settings.getMatcher() instanceof IncrementalMatcher) {
			return ((IncrementalMatcher)settings.getMatcher()).getMatchers();
		} else {
			return Collections.singletonList(settings.getMatcher());
		}
	}

	protected void updateButtonStates() {
		if(moveUp == null || moveDown == null) {
			return;
		}
		moveUp.setEnabled(list_matchers.getSelectionCount() == 1 && list_matchers.getSelectionIndex() > 0);
		moveDown.setEnabled(list_matchers.getSelectionCount() == 1 && list_matchers.getSelectionIndex() < list_matchers.getItemCount()-1);
	}

	public MatchingSettings getSettings() {
		return settings;
	}

	@Override
	public void dispose() {
		settings.removeSettingsChangedListener(this);
	}
}