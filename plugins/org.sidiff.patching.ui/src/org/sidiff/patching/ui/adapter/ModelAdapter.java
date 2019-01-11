package org.sidiff.patching.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;

public class ModelAdapter extends EContentAdapter {

	private List<IModelChangeListener> listeners = new ArrayList<>();

	public ModelAdapter() {
	}

	@Override
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

	private static boolean isAttributeDefinition(Object feature) {
		if (feature instanceof EAttribute) {
			return true;
		}
		return false;
	}

	private static boolean isNonContainmentReferenceType(Object feature) {
		if (feature instanceof EReference) {
			EReference referenceType = (EReference) feature;
			return !referenceType.isContainment();
		}
		return false;
	}

	private static boolean isContainmentReferenceType(Object feature) {
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
}
