package org.sidiff.merging.twoway.ui.pages;

import org.sidiff.common.ui.pages.AbstractWizardPage;
import org.sidiff.difference.lifting.ui.util.InputModels;
import org.sidiff.difference.lifting.ui.widgets.DifferenceBuilderWidget;
import org.sidiff.difference.lifting.ui.widgets.MatchingEngineWidget;
import org.sidiff.merging.twoway.facade.TwoWayMergingSettings;

/**
 * 
 * @author cpietsch
 *
 */
public class TwoWayMergeConfigurationPage extends AbstractWizardPage {

	private InputModels inputModels;
	
	private TwoWayMergingSettings settings;
	
	private MatchingEngineWidget matchingWidget;
	
	private DifferenceBuilderWidget differenceBuilderWidget;

	
	public TwoWayMergeConfigurationPage(String pageName, String title, InputModels inputModels, TwoWayMergingSettings settings) {
		super(pageName, title);
		this.inputModels = inputModels;
		this.settings = settings;
	}

	@Override
	protected void createWidgets() {
		
		matchingWidget = new MatchingEngineWidget(inputModels);
		matchingWidget.setSettings(settings);
		addWidget(container, matchingWidget);
		
		differenceBuilderWidget = new DifferenceBuilderWidget(inputModels);
		differenceBuilderWidget.setSettings(settings);
		addWidget(container, differenceBuilderWidget);
	}

	@Override
	protected String getDefaultMessage() {
		// TODO Auto-generated method stub
		return "Merge two models";
	}

}
