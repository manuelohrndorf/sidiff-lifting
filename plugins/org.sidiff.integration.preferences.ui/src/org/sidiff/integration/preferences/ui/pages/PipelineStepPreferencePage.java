package org.sidiff.integration.preferences.ui.pages;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EPackage;
import org.sidiff.integration.preferences.fieldeditors.IPreferenceField;
import org.sidiff.integration.preferences.significance.DomainSignificanceUtil;
import org.sidiff.integration.preferences.tabs.IPreferenceTab;
import org.sidiff.integration.preferences.tabs.PreferenceTabUtil;
import org.sidiff.integration.preferences.util.PipelineStepUtil.PipelineStep;

/**
 * Preference page that contains {@link IPreferenceTab}s.
 * For each domain independent preference tab, a tab is created.
 * Afterwards, for each domain specific preference tab, a tab is created for each significant document type.
 * @author Robert Müller
 *
 */
public class PipelineStepPreferencePage extends TabbedPreferenceFieldPage {

	private List<IPreferenceTab> tabs;

	private PipelineStepPreferencePage(List<IPreferenceTab> tabs) {
		super.noDefaultAndApplyButton();
		this.tabs = tabs;
	}

	@Override
	protected void createPreferenceFields() {
		// first create all tabs for domain independent settings
		for(IPreferenceTab tab : tabs) {
			if(!(tab instanceof IPreferenceTab.DomainSpecific)) {
				List<IPreferenceField> fields = new ArrayList<IPreferenceField>();
				tab.createPreferenceFields(fields);
				if(fields.isEmpty())
					continue; // empty tabs are skipped
				addTab("All domains", "Domain-independent settings", fields);
			}
		}

		// afterwards create tabs for domain specific settings
		for(IPreferenceTab tab : tabs) {
			if(tab instanceof IPreferenceTab.DomainSpecific) {
				for(String docType : DomainSignificanceUtil.getSignificantDocumentTypes()) {
					((IPreferenceTab.DomainSpecific)tab).setDocumentType(docType);
					List<IPreferenceField> fields = new ArrayList<IPreferenceField>();
					tab.createPreferenceFields(fields);
					if(fields.isEmpty())
						continue; // empty tabs are skipped
					addTab(EPackage.Registry.INSTANCE.getEPackage(docType).getName(), docType, fields);
				}
			}
		}
	}

	/**
	 * Creates a {@link PipelineStepPreferencePage} for the given preference page and pipeline step.
	 * @param page the page
	 * @param pipelineStep the pipeline step
	 * @return new {@link PipelineStepPreferencePage} containing preference tabs
	 * for the given page and pipeline step, <code>null</code> if no preference tabs for this page exist
	 */
	public static PipelineStepPreferencePage create(String page, PipelineStep pipelineStep) {
		List<IPreferenceTab> tabs = PreferenceTabUtil.getPreferenceTabs(page, pipelineStep.getId());
		if(tabs.isEmpty())
			return null;
		PipelineStepPreferencePage stepPage = new PipelineStepPreferencePage(tabs);
		stepPage.setTitle(pipelineStep.getTitle());
		return stepPage;
	}
}
