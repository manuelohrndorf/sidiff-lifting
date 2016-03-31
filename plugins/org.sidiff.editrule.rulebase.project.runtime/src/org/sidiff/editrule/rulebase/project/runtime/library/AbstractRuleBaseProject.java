package org.sidiff.editrule.rulebase.project.runtime.library;

import org.osgi.framework.FrameworkUtil;
import org.sidiff.editrule.rulebase.RuleBase;
import org.sidiff.editrule.rulebase.project.runtime.storage.RuleBaseStorage;
import org.sidiff.editrule.rulebase.view.IRuleBaseFactory;
import org.sidiff.editrule.rulebase.view.RuleBaseViewLibrary;
import org.sidiff.editrule.rulebase.view.basic.IBasicRuleBase;

/**
 * Basic rulebase project implementation.
 */
public abstract class AbstractRuleBaseProject implements IRuleBaseProject {
	
	/**
	 * The rulebase instances.
	 */
	private RuleBase rulebase;
	
	public AbstractRuleBaseProject() {
	}
	
	@Override
	public <R extends IBasicRuleBase> R getRuleBaseView(Class<R> view) {
		R rulebaseView = null;
		
		if (supportsRuleBaseView(view)) {
			IRuleBaseFactory<R> ruleBaseFactory = RuleBaseViewLibrary.getRulebaseViewFactory(view);
			
			if (ruleBaseFactory != null) {
				rulebaseView = (R) ruleBaseFactory.createRuleBase();
				rulebaseView.init(getRuleBaseData());
			}	
		}
		
		return rulebaseView;
	}
	
	@Override
	public boolean supportsRuleBaseView(Class<? extends IBasicRuleBase> view) {
		return RuleBaseViewLibrary.getSupportedViewTypes(getAttachmentTypes()).contains(view);
	}

	@Override
	public RuleBase getRuleBaseData() {
		
		if (rulebase == null) {
			rulebase = RuleBaseStorage.loadRuleBasePlugin(getRuleBasePath(), getRuleBasePluginID());
		}
		
		return rulebase;
	}
	
	protected String getRuleBasePluginID() {
		return FrameworkUtil.getBundle(getClass()).getSymbolicName();
	}

	@Override
	public String getRuleBasePath() {
		return RULEBASE_FILE;
	}
}
