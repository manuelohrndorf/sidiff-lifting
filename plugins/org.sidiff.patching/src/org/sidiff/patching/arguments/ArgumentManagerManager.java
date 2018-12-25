package org.sidiff.patching.arguments;

import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.extension.ExtensionManager;
import org.sidiff.common.extension.IExtension.Description;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.patching.ExecutionMode;

public class ArgumentManagerManager extends ExtensionManager<IArgumentManager> {

	public ArgumentManagerManager(Description<? extends IArgumentManager> description) {
		super(description);
	}

	/**
	 * <p>Returns an argument manager that supports the given settings,
	 * and is able to resolve arguments for the given models.</p>
	 * <p>If an argument manager with the preferred mode is available, it will be returned.
	 * Else, any supported argument manager is returned.</p>
	 * @param asymmetricDifference the asymmetric difference
	 * @param targetModel the target model
	 * @param settings the argument manager settings
	 * @param preferredMode the preferred mode
	 * @return argument manager that supports the input parameters,
	 * <code>null</code> if no appropriate one was found
	 */
	public IArgumentManager getArgumentManager(AsymmetricDifference asymmetricDifference, Resource targetModel,
			IArgumentManagerSettings settings, ExecutionMode preferredMode) {
		IArgumentManager fallbackManager = null;
		for(IArgumentManager manager : getExtensions()) {
			if(manager.canResolveArguments(asymmetricDifference, targetModel, settings)) {
				if(preferredMode == manager.getExecutionMode()) {
					// if the manager has the preferred mode, it is returned right away
					return manager;
				} else if(fallbackManager == null) {
					// else the first viable manager is stored as a fallback
					fallbackManager = manager;
				}
			}
		}
		return fallbackManager;
	}
}
