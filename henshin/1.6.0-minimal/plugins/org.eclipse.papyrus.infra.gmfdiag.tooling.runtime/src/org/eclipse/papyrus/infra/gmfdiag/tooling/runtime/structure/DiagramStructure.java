package org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.structure;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.View;

/**
 *
 * @deprecated since 3.1
 *             use {@link org.eclipse.papyrus.infra.gmfdiag.common.structure.DiagramStructure} API instead
 *
 *             This class will be removed in Papyrus 5.0, see Bug 541027
 *
 *
 */
@Deprecated
public abstract class DiagramStructure {

	public abstract int getVisualID(View view);

	public abstract String getModelID(View view);

	public abstract int getNodeVisualID(View containerView, EObject domainElement);

	public abstract boolean checkNodeVisualID(View containerView, EObject domainElement, int candidate);

	public abstract boolean isCompartmentVisualID(int visualID);

	public abstract boolean isSemanticLeafVisualID(int visualID);

}
