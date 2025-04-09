package  org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.impl.ocl.tracker.activeocl;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.diagram.core.listener.NotificationListener;
import org.eclipse.ocl.ParserException;
import org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.ocl.tracker.OclTrackerBase;

class ActiveOclTracker extends OclTrackerBase {

	private NotificationListener myListener;

	private SimpleDependencyCollector myDependecyCollector;

	private List<EObject> myParserElements;

	private Registrator myRegistrator;

	private Object myValue;

	private boolean myValueInitialized = false;

	private final boolean myCached;

	public ActiveOclTracker(String expressionBody, boolean cached) {
		super(expressionBody);
		myCached = cached;
		myDependecyCollector = new SimpleDependencyCollector();
	}

	public void installListeners(TransactionalEditingDomain domain, NotificationListener listener, Registrator registrator) {
		myListener = listener;
		myRegistrator = registrator;

		registerListeners();
	}

	public void uninstallListeners() {
		unregisterListeners();
	}

	private void registerListeners() {
		if (myRegistrator == null) {
			return;
		}
		myParserElements = new ArrayList<EObject>(myDependecyCollector.getContext2Data().keySet());
		for (int i = 0; i < myParserElements.size(); i++) {
			myRegistrator.registerListener("ActiveOclTracker#SemanticModel" + i, myListener, myParserElements.get(i)); //$NON-NLS-1$
		}
	}

	private void unregisterListeners() {
		if (myParserElements == null) {
			return;
		}
		for (int i = 0; i < myParserElements.size(); i++) {
			myRegistrator.unregisterListener("ActiveOclTracker#SemanticModel" + i); //$NON-NLS-1$
		}
	}

	public Object getValue() {
		if (!myValueInitialized || !isCached()) {
			doInitialize();
		}
		return myValue;
	}

	public boolean handleNotification(Notification notification) {
		ContextData contextData = myDependecyCollector.getContext2Data().get(notification.getNotifier());
		boolean affected = contextData != null && contextData.getFeatures().contains(notification.getFeature());
		if (affected) {
			doInitialize();
		}
		return affected;
	}

	@Override
	protected void doInitialize() {
		unregisterListeners();
		myDependecyCollector.clear();
		try {
			myValue = new ActiveOclEvaluatorImpl().evaluate(getContext(), getExpressionBody(), myDependecyCollector);
		} catch (ParserException e) {
			throw new RuntimeException(e);
		}
		registerListeners();
	}

	private boolean isCached() {
		return myCached;
	}
}
