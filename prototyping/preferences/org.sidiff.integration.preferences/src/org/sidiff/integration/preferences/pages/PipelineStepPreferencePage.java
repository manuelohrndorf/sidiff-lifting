package org.sidiff.integration.preferences.pages;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EPackage;
import org.sidiff.integration.preferences.fieldeditors.PreferenceField;
import org.sidiff.integration.preferences.interfaces.IPreferenceTab;
import org.sidiff.integration.preferences.util.DomainSignificanceUtil;
import org.sidiff.integration.preferences.util.PipelineStepUtil.PipelineStep;
import org.sidiff.integration.preferences.util.PreferenceTabUtil;

/**
 * 
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
				List<PreferenceField> fields = new ArrayList<PreferenceField>();
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
					List<PreferenceField> fields = new ArrayList<PreferenceField>();
					tab.createPreferenceFields(fields);
					if(fields.isEmpty())
						continue; // empty tabs are skipped
					addTab(EPackage.Registry.INSTANCE.getEPackage(docType).getName(), docType, fields);
				}
			}
		}
	}

	public static PipelineStepPreferencePage create(String page, PipelineStep pipelineStep) {
		List<IPreferenceTab> tabs = PreferenceTabUtil.getPreferenceTabs(page, pipelineStep.getId());
		if(tabs.isEmpty())
			return null;
		PipelineStepPreferencePage stepPage = new PipelineStepPreferencePage(tabs);
		stepPage.setTitle(pipelineStep.getTitle());
		return stepPage;
	}
}
