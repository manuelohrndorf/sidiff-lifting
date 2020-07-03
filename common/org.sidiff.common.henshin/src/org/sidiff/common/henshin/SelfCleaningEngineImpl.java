package org.sidiff.common.henshin;

import org.eclipse.emf.henshin.interpreter.impl.EngineImpl;

/**
 * {@link EngineImpl} which also clears its cache and removes
 * the rule listener content adapter on {@link #shutdown()}.
 * @author rmueller
 */
public class SelfCleaningEngineImpl extends EngineImpl {

	@Override
	public void shutdown() {
		super.shutdown();
		clearCache();
		if (ruleListener.getTarget() != null) {
			ruleListener.unsetTarget(ruleListener.getTarget());
		}
	}
}
