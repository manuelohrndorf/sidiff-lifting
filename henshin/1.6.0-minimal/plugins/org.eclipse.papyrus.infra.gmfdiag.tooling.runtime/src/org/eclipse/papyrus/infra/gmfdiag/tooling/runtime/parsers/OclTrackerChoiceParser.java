package  org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.parsers;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.GMFToolingRuntimePlugin;
import org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.ocl.tracker.HasOclTracker;
import org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.ocl.tracker.OclTracker;
import org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.ocl.tracker.OclTrackerFactory;

public class OclTrackerChoiceParser extends OclChoiceParser implements HasOclTracker {

	private final OclTrackerWrapper myLabelDelegate;

	public OclTrackerChoiceParser(EStructuralFeature feature, String itemsExpression, String showExpression, AdapterFactory adapterFactory) {
		this(feature, itemsExpression, showExpression, adapterFactory, GMFToolingRuntimePlugin.getInstance().getOclTrackerFactory());
	}

	public OclTrackerChoiceParser(EStructuralFeature feature, String itemsExpression, String showExpression, AdapterFactory adapterFactory, OclTrackerFactory.Type factoryType) {
		this(feature, itemsExpression, showExpression, adapterFactory, GMFToolingRuntimePlugin.getInstance().getOclTrackerFactory(factoryType));
	}

	public OclTrackerChoiceParser(EStructuralFeature feature, String itemsExpression, String showExpression, AdapterFactory itemProvidersAdapterFactory, OclTrackerFactory oclTrackerFactory) {
		super(feature, itemsExpression, showExpression, itemProvidersAdapterFactory);

		EClassifier featureType = feature.getEType();
		String typeName = (feature instanceof EReference) ? featureType.getName() : featureType.getInstanceClass().getSimpleName();
		final String labelOclExpressionText = "let _item : " + typeName + " = self." + feature.getName() + " in " + showExpression;

		myLabelDelegate = new OclTrackerWrapper(oclTrackerFactory) {

			@Override
			protected String getExpressionBody() {
				return labelOclExpressionText;
			}
		};
	}

	@Override
	public String getEditString(IAdaptable adapter, int flags) {
		EObject element = (EObject) adapter.getAdapter(EObject.class);
		if (element != null && !getFeature().getEContainingClass().isInstance(element)) {
			return ""; // occurs on node deletion
		}
		myLabelDelegate.getUpdatedString(adapter, flags);
		return super.getEditString(adapter, flags);
	}

	public OclTracker getOclTracker() {
		return myLabelDelegate.getOclTracker();
	}

	@Override
	public boolean isAffectingEvent(Object event, int flags) {
		return myLabelDelegate.isAffectingEvent(event, flags) || super.isAffectingEvent(event, flags);
	}
}
