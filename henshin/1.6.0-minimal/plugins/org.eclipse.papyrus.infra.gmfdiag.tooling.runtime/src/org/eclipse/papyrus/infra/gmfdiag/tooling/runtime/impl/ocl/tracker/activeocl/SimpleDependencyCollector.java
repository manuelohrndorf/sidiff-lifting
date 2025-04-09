package  org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.impl.ocl.tracker.activeocl;

import org.eclipse.emf.ecore.EObject;

class SimpleDependencyCollector extends ActiveOclDependencyCollectorBase<ContextData> {

	@Override
	protected ContextData createContextData(EObject context) {
		return new ContextData(context);
	}
}
