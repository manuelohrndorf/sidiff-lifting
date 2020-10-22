package org.sidiff.common.henshin;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.henshin.interpreter.impl.EngineImpl;
import org.eclipse.emf.henshin.interpreter.info.RuleInfo;
import org.eclipse.emf.henshin.model.Rule;

/**
 * {@link EngineImpl} which also clears its cache and removes
 * the rule listener content adapter on {@link #shutdown()}.
 * @author rmueller
 */
public class SelfCleaningEngineImpl extends EngineImpl {

	private Set<Rule> toBeCleaned = new HashSet<>();

	@Override
	public void shutdown() {
		super.shutdown();
		clearCache();
		toBeCleaned.forEach(rule -> rule.eAdapters().remove(ruleListener));
	}

	@Override
	public RuleInfo getRuleInfo(Rule rule) {
		toBeCleaned.add(rule);
		return super.getRuleInfo(rule);
	}
}
