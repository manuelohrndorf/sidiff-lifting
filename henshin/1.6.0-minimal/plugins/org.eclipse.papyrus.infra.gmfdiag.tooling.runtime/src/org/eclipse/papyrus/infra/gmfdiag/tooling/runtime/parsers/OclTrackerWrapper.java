package  org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.parsers;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.ocl.tracker.OclTracker;
import org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.ocl.tracker.OclTrackerFactory;

abstract class OclTrackerWrapper {

	private final OclTrackerFactory myOclTrackerFactory;

	private OclTracker myOclTracker;

	public OclTrackerWrapper(OclTrackerFactory oclTrackerFactory) {
		myOclTrackerFactory = oclTrackerFactory;
	}

	public OclTracker getOclTracker() {
		if (myOclTracker == null) {
			myOclTracker = myOclTrackerFactory.createOclTracker(getExpressionBody(), true);
		}
		return myOclTracker;
	}

	public String getUpdatedString(IAdaptable element, int flags) {
		EObject target = (EObject) element.getAdapter(EObject.class);
		getOclTracker().initialize(target);
		return String.valueOf(getOclTracker().getValue());
	}

	public boolean isAffectingEvent(Object event, int flags) {
		if (event instanceof Notification) {
			Notification notification = (Notification) event;
			return getOclTracker().handleNotification(notification);
		}
		return false;
	}

	protected abstract String getExpressionBody();
}
