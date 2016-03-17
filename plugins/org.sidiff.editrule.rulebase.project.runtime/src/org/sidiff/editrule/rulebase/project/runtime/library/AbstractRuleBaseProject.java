package org.sidiff.editrule.rulebase.project.runtime.library;

import java.util.HashSet;
import java.util.Set;

import org.osgi.framework.FrameworkUtil;
import org.sidiff.editrule.rulebase.RuleBase;
import org.sidiff.editrule.rulebase.project.runtime.storage.RuleBaseStorage;
import org.sidiff.editrule.rulebase.type.extension.IRuleBase;
import org.sidiff.editrule.rulebase.type.extension.IRuleBaseFactory;
import org.sidiff.editrule.rulebase.type.extension.RuleBaseTypeLibrary;

/**
 * Basic rulebase project implementation.
 */
public abstract class AbstractRuleBaseProject implements IRuleBaseProject {
	
	/**
	 * The rulebase instances.
	 */
	private RuleBase rulebase;
	
	/**
	 * Meta-Information: Name
	 */
	private String name;
	
	/**
	 * Meta-Information: Rulebase types
	 */
	private Set<Class<? extends IRuleBase>> types;
	
	public AbstractRuleBaseProject() {
		
		// Read rulebase meta information:
		this.types = new HashSet<Class<? extends IRuleBase>>();
		StringBuilder name = new StringBuilder();
		
		RuleBaseMetaUtil.read(RULEBASE_META_FILE, name, types);
		
		this.name = name.toString();
	}
	
	@Override
	public String getRuleBaseName() {
		return name;
	}
	
	@Override
	public Set<Class<? extends IRuleBase>> getRuleBaseTypes() {
		return types;
	}
	
	@Override
	public <R extends IRuleBase> R getRuleBase(Class<R> type) {
		R rulebaseWrapper = null;
		
		if (getRuleBaseTypes().contains(type)) {
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
