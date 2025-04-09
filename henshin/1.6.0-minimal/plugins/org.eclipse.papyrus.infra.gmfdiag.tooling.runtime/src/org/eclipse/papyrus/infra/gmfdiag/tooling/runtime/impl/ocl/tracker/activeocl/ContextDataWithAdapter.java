package  org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.impl.ocl.tracker.activeocl;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;

class ContextDataWithAdapter extends org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.impl.ocl.tracker.activeocl.ContextData {

	private final Adapter myAdapter;

	public ContextDataWithAdapter(EObject context, final org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.impl.ocl.activeocl.ActiveOclListener listener) {
		super(context);
		myAdapter = new AdapterImpl() {

			@Override
			public void notifyChanged(Notification notification) {
				if (getFeatures().contains(notification.getFeature())) {
					listener.onResultChanged();
				}
			}

			@Override
			public boolean isAdapterForType(Object type) {
				return true;
			}
		};

		getContext().eAdapters().add(myAdapter);
	}

	public void dispose() {
		getContext().eAdapters().remove(myAdapter);
	}
}
