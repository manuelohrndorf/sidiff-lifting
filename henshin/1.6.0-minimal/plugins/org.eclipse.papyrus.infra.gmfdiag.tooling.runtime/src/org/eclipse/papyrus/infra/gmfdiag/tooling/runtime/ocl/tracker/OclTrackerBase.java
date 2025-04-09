package  org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.ocl.tracker;

import org.eclipse.emf.ecore.EObject;

public abstract class OclTrackerBase implements OclTracker {

	private EObject myContext;

	private final String myExpressionBody;

	protected OclTrackerBase(String expressionBody) {
		myExpressionBody = expressionBody;
	}

	public final String getExpressionBody() {
		return myExpressionBody;
	}

	public final void initialize(EObject context) {
		if (myContext == context) {
			return;
		}
		myContext = context;
		doInitialize();
	}

	protected final EObject getContext() {
		return myContext;
	}

	protected abstract void doInitialize();
}
