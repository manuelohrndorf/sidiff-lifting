package org.sidiff.integration.preferences.pages;

import java.util.ArrayList;
import java.util.List;

import org.sidiff.integration.preferences.util.PipelineStepUtil;
import org.sidiff.integration.preferences.util.PipelineStepUtil.PipelineStep;

/**
 * Abstract superclass for the main preferences pages (General, Engines, Validation, etc.)
 * containing a nested page for each pipeline step if settings for that step are available.
 * @author Robert Müller
 *
 */
public abstract class AbstractPreferenceIndexPage extends AbstractNestedPreferencePage {

	private String page;

	public AbstractPreferenceIndexPage(String page) {
		this.page = page;
	}

	@Override
	protected List<PropertyAndPreferencePage> createSubPages() {
		List<PropertyAndPreferencePage> subPages = new ArrayList<PropertyAndPreferencePage>();
		for(PipelineStep step : PipelineStepUtil.getAllAvailablePipelineSteps().values()) {
			PipelineStepPreferencePage subPage = PipelineStepPreferencePage.create(page, step);
			if(subPage != null) {
				subPages.add(subPage);
			}
		}
		return subPages;
	}
}
