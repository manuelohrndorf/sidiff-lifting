package org.sidiff.evaluation.silift.lifting.utils;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.sidiff.evaluation.silift.lifting.engine.AbstractEvaluationEngine;

/**
 * 
 * @author cpietsch
 *
 */
public class EvaluationEngineUtil {

	public static AbstractEvaluationEngine getEvaluationEngines(String key){
		
		for (IConfigurationElement configurationElement : Platform.getExtensionRegistry().getConfigurationElementsFor(AbstractEvaluationEngine.extensionPointID)) {
			try {
				AbstractEvaluationEngine engineExtension = (AbstractEvaluationEngine) configurationElement.createExecutableExtension("difference_evaluation_engine");
				if (key.equals(engineExtension.getKey())) {
					return engineExtension;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
