package org.sidiff.editrule.generator.ui.widgets;

import java.util.Iterator;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

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
import org.sidiff.editrule.generator.IEditRuleGenerator;
import org.sidiff.editrule.generator.settings.EditRuleGenerationSettings;
import org.sidiff.editrule.generator.util.EditRuleGeneratorUtil;
import org.silift.common.util.ui.widgets.IWidget;
import org.silift.common.util.ui.widgets.IWidgetSelection;
import org.silift.common.util.ui.widgets.IWidgetValidation;

public class EditRuleGeneratorWidget implements IWidget, IWidgetSelection, IWidgetValidation {

	
	//FIXME Listen to changes and adapt the chosen generator accordingly
	
	protected EditRuleGenerationSettings settings;

	protected SortedMap<String, IEditRuleGenerator> generators;

	protected Composite container;
	protected List list_generators;

	public EditRuleGeneratorWidget() {

		getGenerators();
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
		matchingLabel.setText("EditRule Generator:");

		list_generators = new List(container, SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL);
		{
			GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
			data.heightHint = 70;
			list_generators.setLayoutData(data);
		}
		list_generators.setItems(generators.keySet().toArray(new String[0]));

		// Set selection:
		if (list_generators.getItems().length != 0) {		
				list_generators.setSelection(0);

		} else {
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Missing EditRule Generator",
					"No EditRule generators are found!");
		}
		list_generators.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				settings.setGenerator(getSelection());
			}
		});

		settings.setGenerator(this.getSelection());

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

	protected void getGenerators() {
		generators = new TreeMap<String, IEditRuleGenerator>();

		// Search registered edit rule generator extension point
		Set<IEditRuleGenerator> matcherSet = EditRuleGeneratorUtil.getAvailableEditRuleGenerators();
		for (Iterator<IEditRuleGenerator> iterator = matcherSet.iterator(); iterator.hasNext();) {
			IEditRuleGenerator matcher = iterator.next();
			generators.put(matcher.getName(), matcher);
		}
	}

	public IEditRuleGenerator getSelection() {
		return generators.get(list_generators.getSelection()[0]);
	}

	public SortedMap<String, IEditRuleGenerator> getGeneratorMap() {
		return generators;
	}

	@Override
	public boolean validate() {
		if (list_generators.getSelectionIndex() == -1) {
			return false;
		}  else {
			return true;
		}
	}

	@Override
	public String getValidationMessage() {
		if (validate()) {
			return "";
		} else {
			return "Please select an EditRule generator";
		}		
	}

	@Override
	public void addSelectionListener(SelectionListener listener) {
		if (list_generators == null) {
			throw new RuntimeException("Create controls first!");
		}
		list_generators.addSelectionListener(listener);
	}

	@Override
	public void removeSelectionListener(SelectionListener listener) {
		if (list_generators != null) {
			list_generators.removeSelectionListener(listener);
		}
	}

	public void setEnabled(Boolean enabled) {
		container.setEnabled(enabled);
	}
	public EditRuleGenerationSettings getSettings() {
		return settings;
	}

	public void setSettings(EditRuleGenerationSettings settings) {
		this.settings = settings;
	}

	public List getList_generators() {
		return list_generators;
	}

	public void setList_generators(List list_generators) {
		this.list_generators = list_generators;
	}	
	
	
}