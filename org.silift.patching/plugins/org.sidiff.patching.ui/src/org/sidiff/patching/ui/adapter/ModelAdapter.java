package org.sidiff.patching.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;

public class ModelAdapter {

	private List<ModelAdapter.IModelChangeListener> listeners;

	public ModelAdapter(Resource resource) {
		this.listeners = new ArrayList<ModelAdapter.IModelChangeListener>();

		EContentAdapter adapter = new EContentAdapter() {
			public void notifyChanged(Notification notification) {
				super.notifyChanged(notification);
				switch (notification.getEventType()) {
				case Notification.ADD:
					if (isContainmentReferenceType(notification.getFeature())) {
						LogUtil.log(LogEvent.DEBUG, "\t==> Add-Object: " + notification.getNewValue());
						for (IModelChangeListener listener : listeners) {
							listener.objectAdded((EObject) notification.getNewValue());
						}
					}
					if (isNonContainmentReferenceType(notification.getFeature())) {
						LogUtil.log(LogEvent.DEBUG,
								"\t==> Add-Reference (" + ((EReference) notification.getFeature()).getName() + "): "
										+ notification.getNotifier() + " => " + notification.getNewValue());
						for (IModelChangeListener listener : listeners) {
							listener.referenceAdded((EReference) notification.getFeature(),
									(EObject) notification.getNotifier(), (EObject) notification.getNewValue());

						}
					}
					break;

				case Notification.REMOVE:
					if (isContainmentReferenceType(notification.getFeature())) {
						LogUtil.log(LogEvent.DEBUG, "\t==> Remove-Object: " + notification.getOldValue());
						for (IModelChangeListener listener : listeners) {
							listener.objectRemoved((EObject) notification.getOldValue());
						}
					}
					if (isNonContainmentReferenceType(notification.getFeature())) {
						LogUtil.log(LogEvent.DEBUG,
								"\t==> Remove-Reference (" + ((EReference) notification.getFeature()).getName() + "): "
										+ notification.getNotifier() + " => " + notification.getOldValue());
						for (IModelChangeListener listener : listeners) {
							listener.referenceRemoved((EReference) notification.getFeature(),
									(EObject) notification.getNotifier(), (EObject) notification.getOldValue());

						}
					}
					break;

				case Notification.SET:
					if (isAttributeDefinition(notification.getFeature())) {
						LogUtil.log(LogEvent.DEBUG,
								"\t==> Set-Attribute-Value (" + ((EAttribute) notification.getFeature()).getName()
										+ "): " + notification.getNotifier() + " => " + notification.getNewValue());
						for (IModelChangeListener listener : listeners) {
							listener.attributeValueSet((EAttribute) notification.getFeature(),
									(EObject) notification.getNotifier(), notification.getNewValue());
						}
					}
					break;

				default:
					LogUtil.log(LogEvent.DEBUG, "Unhandled EMF notification event: " + notification);
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
	 * Information about model changes. The model changes that are reported
	 * mostly correspond to our definitions of low-level changes with the
	 * following abbreviations:
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
