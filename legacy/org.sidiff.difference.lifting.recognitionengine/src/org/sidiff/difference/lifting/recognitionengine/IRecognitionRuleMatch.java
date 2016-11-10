package org.sidiff.difference.lifting.recognitionengine;

import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Node;

public interface IRecognitionRuleMatch {

	/**
	 * Simple getter.
	 * 
	 * @return the mapping of LHS or RHS nodes to diff objects.
	 */
	public Map<Node, Set<EObject>> getNodeMapping();

	/**
	 * Returns the value which is bound to a recognition rule parameter
	 * application. We use this method to retrieve actual values of
	 * ValueParameters.
	 * 
	 * @param name
	 * @return
	 */
	public Object getParameterValue(String name);
}
