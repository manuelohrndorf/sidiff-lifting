package org.sidiff.integration.preferences.ui.pages.internal;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EPackage;
import org.sidiff.integration.preferences.fieldeditors.IPreferenceField;
import org.sidiff.integration.preferences.significance.IDomainSignificance;
import org.sidiff.integration.preferences.tabs.IPreferenceTab;
import org.sidiff.integration.preferences.tabs.PreferenceTabUtil;
import org.sidiff.integration.preferences.ui.pages.PreferenceFieldPage;
import org.sidiff.integration.preferences.ui.pages.PropertyAndPreferencePage;
import org.sidiff.integration.preferences.ui.pages.TabbedPreferencePage;
import org.sidiff.integration.preferences.util.PipelineStepUtil;
import org.sidiff.integration.preferences.util.PipelineStepUtil.PipelineStep;

/**
 * Static factory for pipeline step, and index preference pages.
 * @author rmueller
 */
public class PreferencePageFactory {

	/**
	 * Creates a preference page for the given preference page name and pipeline step.
	 * @param page the page
	 * @param pipelineStep the pipeline step
	 * @return new preference page containing preference tabs
	 * for the given page and pipeline step, <code>null</code> if no preference tabs for this page exist
	 */
	public static PropertyAndPreferencePage createPipelineStepPage(String page, PipelineStep pipelineStep) {
		List<IPreferenceTab> tabs = PreferenceTabUtil.getPreferenceTabs(page, pipelineStep.getId());
		if(tabs.isEmpty())
			return null;

		TabbedPreferencePage stepPage = new TabbedPreferencePage();

		// first create all tabs for domain independent settings
		for(IPreferenceTab tab : tabs) {
			if(!(tab instanceof IPreferenceTab.DomainSpecific)) {
				List<IPreferenceField> fields = new ArrayList<>();
				tab.createPreferenceFields(fields);
				if(fields.isEmpty())
					continue; // empty tabs are skipped
				PreferenceFieldPage prefFieldPage = new PreferenceFieldPage();
				prefFieldPage.addFields(fields);
				prefFieldPage.setHelpContextId(tab.getHelpContextId());
				stepPage.addTab(prefFieldPage, "All domains", "Domain-independent settings");
			}
		}

		// afterwards create tabs for domain specific settings
		for(IPreferenceTab tab : tabs) {
			if(tab instanceof IPreferenceTab.DomainSpecific) {
				for(String docType : IDomainSignificance.MANAGER.getSignificantDocumentTypes()) {
					((IPreferenceTab.DomainSpecific)tab).setDocumentType(docType);
					List<IPreferenceField> fields = new ArrayList<>();
					tab.createPreferenceFields(fields);
					if(fields.isEmpty())
						continue; // empty tabs are skipped
					PreferenceFieldPage prefFieldPage = new PreferenceFieldPage();
					prefFieldPage.addFields(fields);
					prefFieldPage.setHelpContextId(tab.getHelpContextId());
					stepPage.addTab(prefFieldPage, EPackage.Registry.INSTANCE.getEPackage(docType).getName(), docType);
				}
			}
		}
		
		return stepPage;
	}

	public static PropertyAndPreferencePage createIndexPage(String pageName, String preferenceQualifier) {
		TabbedPreferencePage indexPage = new TabbedPreferencePage();
		indexPage.setPreferenceQualifier(preferenceQualifier);
		for(PipelineStep step : PipelineStepUtil.getAllAvailablePipelineSteps().values()) {
			PropertyAndPreferencePage subPage = PreferencePageFactory.createPipelineStepPage(pageName, step);
			if(subPage != null) {
				indexPage.addTab(subPage, step.getTitle());
			}
		}
		return indexPage;
	}
}
