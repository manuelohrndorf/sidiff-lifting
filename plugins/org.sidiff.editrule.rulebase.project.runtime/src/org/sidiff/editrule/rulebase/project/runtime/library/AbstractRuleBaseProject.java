package org.sidiff.editrule.rulebase.project.runtime.library;

import java.util.HashMap;
import java.util.Map;

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

	private Map<Class<? extends IBasicRuleBase>, IBasicRuleBase> views = new HashMap<>();

	public AbstractRuleBaseProject() {
	}
	
	@Override
	public <R extends IBasicRuleBase> R getRuleBaseView(Class<R> view) {
		if (supportsRuleBaseView(view)) {
			return view.cast(views.computeIfAbsent(view, v -> {
				IRuleBaseFactory<R> ruleBaseFactory = RuleBaseViewLibrary.getRulebaseViewFactory(view);
				if (ruleBaseFactory != null) {
					R rulebaseView = ruleBaseFactory.createRuleBase();
					rulebaseView.init(getRuleBaseData());
					return rulebaseView;
				}
				return null;
			}));
		}
		return null;
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

	@Override
	public void unloadRuleBaseData() {
		rulebase = null;
		views.clear();
	}

	protected String getRuleBasePluginID() {
		return FrameworkUtil.getBundle(getClass()).getSymbolicName();
	}

	@Override
	public String getRuleBasePath() {
		return RULEBASE_FILE;
	}
}
