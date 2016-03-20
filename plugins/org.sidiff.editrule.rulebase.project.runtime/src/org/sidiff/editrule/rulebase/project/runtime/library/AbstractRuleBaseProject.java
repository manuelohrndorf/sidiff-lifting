package org.sidiff.editrule.rulebase.project.runtime.library;

import org.osgi.framework.FrameworkUtil;
import org.sidiff.editrule.rulebase.RuleBase;
import org.sidiff.editrule.rulebase.project.runtime.storage.RuleBaseStorage;
import org.sidiff.editrule.rulebase.type.IRuleBaseFactory;
import org.sidiff.editrule.rulebase.type.RuleBaseTypeLibrary;
import org.sidiff.editrule.rulebase.type.basic.IBasicRuleBase;

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
	public <R extends IBasicRuleBase> R getRuleBase(Class<R> type) {
		R rulebaseWrapper = null;
		
		if (getRuleBaseTypes().contains(type.getName())) {
			IRuleBaseFactory<R> ruleBaseFactory = RuleBaseTypeLibrary.getRulebaseType(type);
			
			if (ruleBaseFactory != null) {
				rulebaseWrapper = (R) ruleBaseFactory.createRuleBase();
				rulebaseWrapper.init(getRuleBaseData());
			}	
		}
		
		return rulebaseWrapper;
	}
	
	@Override
	public RuleBase getRuleBaseData() {
		
		if (rulebase == null) {
			rulebase = RuleBaseStorage.loadRuleBase(getRuleBasePath(), getRuleBasePluginID());
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
