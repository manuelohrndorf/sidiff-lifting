package  org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.impl.ocl.tracker.activeocl;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

class ContextData {

	private final EObject myContext;

	private final Set<EStructuralFeature> myFeatures;

	public ContextData(EObject context) {
		myContext = context;
		myFeatures = new HashSet<EStructuralFeature>();
	}

	public void addFeature(EStructuralFeature feature) {
		myFeatures.add(feature);
	}

	public Set<EStructuralFeature> getFeatures() {
		return myFeatures;
	}

	public EObject getContext() {
		return myContext;
	}
}
