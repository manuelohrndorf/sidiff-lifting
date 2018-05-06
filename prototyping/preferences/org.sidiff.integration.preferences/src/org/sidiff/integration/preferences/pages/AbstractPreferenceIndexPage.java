package org.sidiff.integration.preferences.pages;

import java.util.ArrayList;
import java.util.List;

import org.sidiff.integration.preferences.util.PipelineStepUtil;
import org.sidiff.integration.preferences.util.PipelineStepUtil.PipelineStep;

/**
 * 
 * @author Robert Müller
 *
 */
public abstract class AbstractPreferenceIndexPage extends AbstractNestedPreferencePage {

	private String page;
	private List<PropertyAndPreferencePage> subPages;

	public AbstractPreferenceIndexPage(String page) {
		this.page = page;
	}

	@Override
	protected List<PropertyAndPreferencePage> createSubPages() {
		subPages = new ArrayList<PropertyAndPreferencePage>();
		for(PipelineStep step : PipelineStepUtil.getAllAvailablePipelineSteps().values()) {
			PipelineStepPreferencePage subPage = PipelineStepPreferencePage.create(page, step);
			if(subPage != null) {
				subPages.add(subPage);
			}
		}
		return subPages;
	}
}
