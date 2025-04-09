package  org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.impl.ocl.activeocl;

import org.eclipse.emf.common.notify.Notifier;

public interface ActiveOclResult {

	Object getResult();

	boolean canListenForChanges();

	void addListener(ActiveOclListener listener);

	void removeListener(ActiveOclListener listener);

	Notifier adaptToEMFNotifier();

	void dispose();
}
