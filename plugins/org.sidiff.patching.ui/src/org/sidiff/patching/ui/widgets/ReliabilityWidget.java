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
import org.sidiff.common.ui.widgets.IWidget;
import org.sidiff.common.ui.widgets.IWidgetInformation;
import org.sidiff.common.ui.widgets.IWidgetSelection;
import org.sidiff.difference.lifting.settings.ISettingsChangedListener;
import org.sidiff.difference.lifting.settings.Settings;
import org.sidiff.difference.lifting.settings.SettingsItem;
import org.sidiff.difference.matcher.IMatcher;
import org.silift.difference.symboliclink.handler.ISymbolicLinkHandler;
import org.silift.patching.settings.PatchingSettings;

public class ReliabilityWidget implements IWidget, IWidgetSelection, IWidgetInformation, ISettingsChangedListener {

	private final int defaultReliability = 50;

	private Settings settings;
	private Composite container;
	private Scale scale;
	private Spinner spinner;
	private int reliability;

	public ReliabilityWidget(Integer reliability) {
		if(reliability != null)
			this.reliability = reliability;
		else
			this.reliability = defaultReliability;
		
		// Connect to matching engine widget:
//		this.matchingEngineWidget = matchingEngineWidget;
//		
//		matchingEngineWidget.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				checkMatcher();
//			}
//		});
	}
	
	private boolean checkComputability() {
		if(settings.useSymbolicLinks()){
			ISymbolicLinkHandler symbolicLinkHandler = settings.getSymbolicLinkHandler();
			if((symbolicLinkHandler != null) && symbolicLinkHandler.canComputeReliability()){
				scale.setEnabled(true);
				spinner.setEnabled(true);
				container.setEnabled(true);
				return true;
			}
		}else{
			IMatcher matcher = settings.getMatcher();

			if ((matcher != null) && matcher.canComputeReliability()) {
				scale.setEnabled(true);
				spinner.setEnabled(true);
				container.setEnabled(true);
				return true;
			}
		}
		scale.setEnabled(false);
		spinner.setEnabled(false);
		container.setEnabled(false);
		return false;
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

		scale = new Scale(reliabilityGroup, SWT.NONE);
		scale.setMaximum(100);
		scale.setIncrement(5);
		scale.setSelection(this.reliability);
		scale.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		spinner = new Spinner(reliabilityGroup, SWT.NONE);
		spinner.setMaximum(100);
		spinner.setSelection(this.reliability);

		scale.addListener(SWT.Selection, new Listener() {
			@Override
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
				((PatchingSettings)settings).setMinReliability(reliability);
			}
		});
		
		// Initialize...
		checkComputability();
		
		((PatchingSettings)settings).setMinReliability(reliability);
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
		if (checkComputability()) {
			return this.reliability;
		} else {
			return -1;
		}
	}

	@Override
	public String getInformationMessage() {
		if (checkComputability()) {
			return "";
		} else {
			if(settings.useSymbolicLinks()){
				return "Selected Symbolic Link Handler does not support Reliability!";
			}else{
				return "Selected Matching Engine does not support Reliability!";
			}
		}
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

	@Override
	public void settingsChanged(Enum<?> item) {
		if(item.equals(SettingsItem.MATCHER) || item.equals(SettingsItem.SYMBOLIC_LINK_HANDLER)){
			checkComputability();
		}
	}

	public Settings getSettings() {
		return settings;
	}

	public void setSettings(Settings settings) {
		this.settings = settings;
		this.settings.addSettingsChangedListener(this);
	}
}