package org.sidiff.patching.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
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
					if (isContainmentReferenceType(notification.getFeature())) {
						System.out.println("\t==> Add-Object: " + notification.getNewValue());
						for (IModelChangeListener listener : listeners) {
							listener.objectAdded((EObject) notification.getNewValue());
						}
					}
					if (isNonContainmentReferenceType(notification.getFeature())) {
						System.out.println("\t==> Add-Reference (" + ((EReference) notification.getFeature()).getName()
								+ "): " + notification.getNotifier() + " => " + notification.getNewValue());
						for (IModelChangeListener listener : listeners) {
							listener.referenceAdded((EReference) notification.getFeature(),
									(EObject) notification.getNotifier(), (EObject) notification.getNewValue());

						}
					}
					break;

				case Notification.REMOVE:
					System.out.println("Remove: " + notification);
					if (isContainmentReferenceType(notification.getFeature())) {
						System.out.println("\t==> Remove-Object: " + notification.getOldValue());
						for (IModelChangeListener listener : listeners) {
							listener.objectRemoved((EObject) notification.getOldValue());
						}
					}
					if (isNonContainmentReferenceType(notification.getFeature())) {
						System.out.println("\t==> Remove-Reference ("
								+ ((EReference) notification.getFeature()).getName() + "): "
								+ notification.getNotifier() + " => " + notification.getOldValue());
						for (IModelChangeListener listener : listeners) {
							listener.referenceRemoved((EReference) notification.getFeature(),
									(EObject) notification.getNotifier(), (EObject) notification.getOldValue());

						}
					}
					break;

				case Notification.SET:
					System.out.println("Set: " + notification);
					if (isAttributeDefinition(notification.getFeature())) {
						System.out.println("\t==> Set-Attribute-Value ("
								+ ((EAttribute) notification.getFeature()).getName() + "): "
								+ notification.getNotifier() + " => " + notification.getNewValue());
						for (IModelChangeListener listener : listeners) {
							listener.attributeValueSet((EAttribute) notification.getFeature(),
									(EObject) notification.getNotifier(), notification.getNewValue());
						}
					}
					break;

				default:
					System.out.println("Unkown: " + notification);
					break;
				}
			}
		};
		resource.eAdapters().add(adapter);
	}

	private boolean isAttributeDefinition(Object feature) {
		if (feature instanceof EAttribute) {
			return true;
		}

		return false;
	}

	private boolean isNonContainmentReferenceType(Object feature) {
		if (feature instanceof EReference) {
			EReference referenceType = (EReference) feature;
			return !referenceType.isContainment();
		}

		return false;
	}

	private boolean isContainmentReferenceType(Object feature) {
		if (feature instanceof EReference) {
			EReference referenceType = (EReference) feature;
			return referenceType.isContainment();
		}

		return false;
	}

	public void addListener(IModelChangeListener listener) {
		listeners.add(listener);
	}

	public void removeListern(IModelChangeListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Information about model changes. Corresponds mostly to our definitions of
	 * low-level changes with the following abbreviations:
	 * <ul>
	 * <li>Only non-containment reference changes are reported as reference
	 * changes.</li>
	 * <li>Containment references changes are not separately reported. Here,
	 * only the AddObject and removeObject changes are reported.</li>
	 * <li>Each value setting of an attribute is reported, not only
	 * AttributeValueChanges</li>
	 * </ul>
	 * 
	 */
	public interface IModelChangeListener {

		public void objectAdded(EObject eObject);

		public void objectRemoved(EObject eObject);

		public void referenceAdded(EReference referenceType, EObject src, EObject tgt);

		public void referenceRemoved(EReference referenceType, EObject src, EObject tgt);

		public void attributeValueSet(EAttribute attribute, EObject object, Object value);
	}

}
