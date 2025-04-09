package  org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.impl.ocl.tracker.activeocl;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.impl.ocl.activeocl.ActiveOclDependencyCollector;

abstract class ActiveOclDependencyCollectorBase<T extends ContextData> implements ActiveOclDependencyCollector {

	private final Map<EObject, T> myContext2Data;

	public ActiveOclDependencyCollectorBase() {
		myContext2Data = new HashMap<EObject, T>();
	}

	public void registerDependency(EObject context, EStructuralFeature feature) {
		T contextData = myContext2Data.get(context);

		if (contextData == null) {
			contextData = createContextData(context);
			myContext2Data.put(context, contextData);
		}

		contextData.addFeature(feature);
	}

	public void clear() {
		myContext2Data.clear();
	}

	public Map<EObject, T> getContext2Data() {
		return myContext2Data;
	}

	protected abstract T createContextData(EObject context);
}
