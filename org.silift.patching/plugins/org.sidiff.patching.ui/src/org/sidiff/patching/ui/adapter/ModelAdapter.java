package org.sidiff.patching.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EContentAdapter;

public class ModelAdapter {

	private List<ModelAdapter.IModelChangeListener> listeners;

	public ModelAdapter(Resource resource) {
		this.listeners = new ArrayList<ModelAdapter.IModelChangeListener>();
		
		EContentAdapter adapter = new EContentAdapter() {
			public void notifyChanged(Notification notification) {
				super.notifyChanged(notification);
				switch (notification.getEventType()) {
				case Notification.ADD:
					System.out.println("Add: " + notification);
					for (IModelChangeListener listener : listeners) {
						 listener.objectAdded((EObject) notification.getNewValue());
					}
					break;
				case Notification.REMOVE:
					System.out.println("Remove: " + notification);
					for (IModelChangeListener listener : listeners) {
						listener.objectRemoved((EObject) notification.getOldValue());
					}
					break;
				case Notification.SET:
					System.out.println("Set: " + notification);
					break;
				default:
					System.out.println("Unkown: " + notification);
					break;
				}
//				if (notification.getNotifier() instanceof EObject) {
//
//					// only respond to changes to structural features of the object
//					if (notification.getFeature() instanceof EStructuralFeature) {
//						Object newValue = notification.getNewValue();
//						if (newValue != null && newValue instanceof EObject) {
//							for (IModelChangeListener listener : listeners) {
//								 listener.objectAdded((EObject) newValue);
//							}
//						}
//						Object oldValue = notification.getOldValue();
//						if (oldValue != null && oldValue instanceof EObject) {
//							for (IModelChangeListener listener : listeners) {
//								listener.objectRemoved((EObject) notification.getOldValue());
//							}
//						}
//					}
//				}
			}
		};
		resource.eAdapters().add(adapter);
	}

	public void addListener(IModelChangeListener listener) {
		listeners.add(listener);
	}

	public void removeListern(IModelChangeListener listener) {
		listeners.remove(listener);
	}

	public interface IModelChangeListener {

		public void objectAdded(EObject eObject);

		public void objectRemoved(EObject eObject);

	}

}
