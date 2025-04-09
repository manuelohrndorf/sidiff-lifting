package  org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.impl.ocl.tracker.activeocl;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ocl.ParserException;
import org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.impl.ocl.activeocl.ActiveOclListener;
import org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.impl.ocl.activeocl.ActiveOclResult;

class ActiveOclResultImpl implements ActiveOclResult, ActiveOclListener {

	private final List<ActiveOclListener> myListeners;

	private Object myResult;

	private final ListeningDependencyCollector myDependencyCollector;

	private final DependencyEvaluator myDependencyEvaluator;

	public ActiveOclResultImpl(EObject context, String oclExp) throws ParserException {
		myListeners = new ArrayList<ActiveOclListener>();

		myDependencyCollector = new ListeningDependencyCollector(this);

		myDependencyEvaluator = new DependencyEvaluator(context, oclExp, myDependencyCollector);

		myResult = myDependencyEvaluator.evaluate();
	}

	public void onResultChanged() {
		myDependencyCollector.clear();

		Object newResult = myDependencyEvaluator.evaluate();
		if (myResult == null && newResult == null //
				|| myResult != null && myResult.equals(newResult)) {
			return;
		}
		myResult = newResult;

		for (ActiveOclListener listener : myListeners) {
			listener.onResultChanged();
		}
	}

	public void removeListener(ActiveOclListener listener) {
		myListeners.remove(listener);
	}

	public Object getResult() {
		return myResult;
	}

	public void dispose() {
		myDependencyCollector.clear();
	}

	public boolean canListenForChanges() {
		return true;
	}

	public void addListener(ActiveOclListener listener) {
		myListeners.add(listener);
	}

	public Notifier adaptToEMFNotifier() {
		
		return null;
	}
}
