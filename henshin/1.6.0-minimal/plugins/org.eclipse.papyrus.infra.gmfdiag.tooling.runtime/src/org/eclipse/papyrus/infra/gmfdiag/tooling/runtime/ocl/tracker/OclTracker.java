package  org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.ocl.tracker;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.diagram.core.listener.NotificationListener;

public interface OclTracker {

	public void initialize(EObject context);

	public void installListeners(TransactionalEditingDomain domain, NotificationListener listener, Registrator registrator);

	public void uninstallListeners();

	public Object getValue();

	public boolean handleNotification(Notification notification);

	public String getExpressionBody();

	public interface Registrator {

		public void registerListener(String filterId, NotificationListener listener, EObject element);

		public void unregisterListener(String filterId);
	}

}
