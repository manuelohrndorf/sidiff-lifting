package org.sidiff.editrule.generator.ui.widgets;

import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.sidiff.common.ui.widgets.AbstractWidget;
import org.sidiff.common.ui.widgets.IWidgetValidation.ValidationMessage.ValidationType;
import org.sidiff.editrule.generator.IEditRuleGenerator;
import org.sidiff.editrule.generator.settings.EditRuleGenerationSettings;

public class EditRuleGeneratorWidget extends AbstractWidget {

	//FIXME Listen to changes and adapt the chosen generator accordingly

	protected EditRuleGenerationSettings settings;

	protected SortedMap<String, IEditRuleGenerator> generators;

	protected Composite container;
	protected List list_generators;

	public EditRuleGeneratorWidget() {
		generators = new TreeMap<>(IEditRuleGenerator.MANAGER.getExtensions().stream()
				.collect(Collectors.toMap(IEditRuleGenerator::getName, Function.identity())));
	}

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
		matchingLabel.setText("EditRule Generator:");

		list_generators = new List(container, SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL);
		{
			GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
			data.heightHint = 70;
			list_generators.setLayoutData(data);
		}
		list_generators.setItems(generators.keySet().toArray(new String[0]));

		// Set selection:
		if (list_generators.getItemCount() > 0) {
			list_generators.setSelection(0);
		}
		list_generators.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				settings.setGenerator(getSelection());
			}
		});

		if(list_generators.getItems().length != 0) {
			settings.setGenerator(this.getSelection());
		}

		return container;
	}

	@Override
	public Composite getWidget() {
		return container;
	}

	public IEditRuleGenerator getSelection() {
		return generators.get(list_generators.getSelection()[0]);
	}

	public SortedMap<String, IEditRuleGenerator> getGeneratorMap() {
		return generators;
	}

	@Override
	protected ValidationMessage doValidate() {
		if(list_generators.getItemCount() == 0) {
			return new ValidationMessage(ValidationType.ERROR, "No Edit Rule generators available!");
		} else if(list_generators.getSelectionIndex() == -1) {
			return new ValidationMessage(ValidationType.ERROR, "Please select an Edit Rule generator!");
		}
		return ValidationMessage.OK;
	}

	public EditRuleGenerationSettings getSettings() {
		return settings;
	}

	public void setSettings(EditRuleGenerationSettings settings) {
		this.settings = settings;
	}
}