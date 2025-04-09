package  org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.parsers;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.QueryDelegate;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.gmf.runtime.diagram.core.listener.NotificationListener;
import org.eclipse.ocl.ParserException;
import org.eclipse.ocl.common.OCLConstants;
import org.eclipse.ocl.ecore.EcoreEnvironmentFactory;
import org.eclipse.ocl.ecore.OCL;
import org.eclipse.ocl.ecore.OCL.Helper;
import org.eclipse.ocl.ecore.OCLExpression;
import org.eclipse.ocl.ecore.delegate.OCLQueryDelegateFactory;
import org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.ocl.tracker.OclTracker.Registrator;

public class OclChoiceParser extends ChoiceParserBase {

	private static final String ITEM_PARAM_NAME = "_item";

	private static final String FILTER_ID = "OCLChoiceParser";

	private final OCL myOcl;

	private final OCLExpression myItemsOclExpression;

	private final QueryDelegate myShowOclQuery;

	private final AdapterFactory myAdapterFactory;

	private Tracker myTracker;

	public OclChoiceParser(EStructuralFeature feature, String itemsExpression, String showExpression, AdapterFactory adapterFactory) {
		super(feature);

		myAdapterFactory = adapterFactory;

		myOcl = OCL.newInstance(new EcoreEnvironmentFactory());

		EClass contextClass = feature.getEContainingClass();

		Helper helper = myOcl.createOCLHelper();
		helper.setContext(contextClass);
		try {
			myItemsOclExpression = helper.createQuery(itemsExpression);
		} catch (ParserException e) {
			throw new RuntimeException(e);
		}

		if (showExpression == null) {
			myShowOclQuery = null;
		} else {
			Map<String, EClassifier> parameters = new HashMap<String, EClassifier>();
			parameters.put(ITEM_PARAM_NAME, getFeature().getEType());
			myShowOclQuery = new OCLQueryDelegateFactory(OCLConstants.OCL_DELEGATE_URI).createQueryDelegate(contextClass, parameters, showExpression);
		}

		myTracker = new Tracker();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Collection<Object> getItems(EObject element) {
		return (Collection<Object>) myOcl.evaluate(element, myItemsOclExpression);
	}

	@Override
	protected String getEditChoice(EObject element, Object item) {
		Object result;
		if (item == null) {
			result = null;
		} else if (myShowOclQuery == null) {
			result = item;
		} else {
			Map<String, Object> arguments = new HashMap<String, Object>();
			arguments.put(ITEM_PARAM_NAME, item);
			try {
				result = myShowOclQuery.execute(element, arguments);
			} catch (InvocationTargetException e) {
				throw new RuntimeException(e);
			}
		}

		if (result == null) {
			return getNullItemEditChoice();
		} else if (result instanceof EObject) {
			EObject eResult = (EObject) result;
			myTracker.setTracked(eResult);
			return ((IItemLabelProvider) myAdapterFactory.adapt(eResult, IItemLabelProvider.class)).getText(eResult);
		} else {
			return result.toString();
		}
	}

	protected String getNullItemEditChoice() {
		return "";
	}

	@Override
	public boolean isAffectingEvent(Object event, int flags) {
		return myTracker.isAffectingEvent(event, flags) || super.isAffectingEvent(event, flags);
	}

	public void installListeners(NotificationListener listener, Registrator oclRegistrator) {
		myTracker.start(listener, oclRegistrator);
	}

	public void uninstallListeners() {
		myTracker.stop();
	}

	private static class Tracker {

		private Registrator myOclRegistrator;

		private NotificationListener myListener;

		private EObject myTracked;

		private boolean myTracking = false;

		public void start(NotificationListener listener, Registrator oclRegistrator) {
			myOclRegistrator = oclRegistrator;
			myListener = listener;
			myTracking = true;
			registerListener();
		}

		public void stop() {
			unregisterListener();
			myTracking = false;
		}

		public void setTracked(EObject tracked) {
			if (tracked != myTracked) {
				unregisterListener();
				myTracked = tracked;
				registerListener();
			}
		}

		private void registerListener() {
			if (!myTracking || myTracked == null) {
				return;
			}
			myOclRegistrator.registerListener(FILTER_ID, myListener, myTracked);
		}

		private void unregisterListener() {
			if (!myTracking || myTracked == null) {
				return;
			}
			myOclRegistrator.unregisterListener(FILTER_ID);
		}

		public boolean isAffectingEvent(Object event, int flags) {
			if (event instanceof Notification) {
				Notification notification = (Notification) event;
				if (notification.getNotifier() == myTracked) {
					return true;
				}
			}
			return false;
		}
	}
}
