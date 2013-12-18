package org.sidiff.patching.ui.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Spinner;
import org.silift.common.util.ui.widgets.IWidget;
import org.silift.common.util.ui.widgets.IWidgetSelection;
import org.silift.common.util.ui.widgets.IWidgetValidation;

public class ReliabilityWidget implements IWidget, IWidgetSelection, IWidgetValidation {

	private final int defaultReliability = 50;

	private Composite container;
	private Scale scale;
	private Spinner spinner;
	private int reliability;

	public ReliabilityWidget(Integer reliability) {
		if(reliability != null)
			this.reliability = reliability;
		else
			this.reliability = defaultReliability;
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

		Group reliabilityGroup = new Group(container, SWT.NONE);
		{
			GridLayout grid = new GridLayout(2, false);
			grid.marginWidth = 10;
			grid.marginHeight = 10;
			reliabilityGroup.setLayout(grid);
			reliabilityGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		}
		reliabilityGroup.setText("Minimal Reliability:");

		scale = new Scale(reliabilityGroup, SWT.BORDER);
		scale.setMaximum(100);
		scale.setIncrement(5);
		scale.setSelection(this.reliability);
		scale.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		spinner = new Spinner(reliabilityGroup, SWT.NONE);
		spinner.setMaximum(100);
		spinner.setSelection(this.reliability);

		scale.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				int perspectiveValue = scale.getSelection() + scale.getMinimum();
				spinner.setSelection(perspectiveValue);
				reliability = scale.getSelection();
			}
		});
		
		spinner.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				scale.setSelection(spinner.getSelection());
				reliability = spinner.getSelection();
			}
		});
		
		
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

	public int getReliability() {
		if (validate()) {
			return this.reliability;
		} else {
			return -1;
		}
	}

	@Override
	public boolean validate() {
		//Nothing
		return true;
	}

	@Override
	public String getValidationMessage() {
			return "";
	}


	@Override
	public void addSelectionListener(SelectionListener listener) {
		if((spinner == null) || (scale == null)){
			throw new RuntimeException("Create controls first!");
		}
		spinner.addSelectionListener(listener);
		scale.addSelectionListener(listener);
	}

	@Override
	public void removeSelectionListener(SelectionListener listener) {
		if(spinner != null)
			spinner.removeSelectionListener(listener);
		if(scale != null)
			scale.removeSelectionListener(listener);
	}
}