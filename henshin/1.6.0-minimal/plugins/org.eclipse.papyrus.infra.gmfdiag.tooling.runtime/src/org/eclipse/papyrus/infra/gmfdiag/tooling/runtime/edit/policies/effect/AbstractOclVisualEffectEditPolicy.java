package  org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.edit.policies.effect;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.core.listener.NotificationListener;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CanonicalEditPolicy;
import org.eclipse.gmf.runtime.emf.type.core.commands.SetValueCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.Style;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.GMFToolingRuntimePlugin;
import org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.ocl.tracker.OclTracker;
import org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.ocl.tracker.OclTrackerFactory;
import org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.ocl.tracker.OclTracker.Registrator;

public abstract class AbstractOclVisualEffectEditPolicy extends CanonicalEditPolicy {

	private OclTracker myOclTracker;

	private Registrator myOclRegistrator;

	private final OclTrackerFactory myOclTrackerFactory;

	protected abstract String getExpressionBody();

	public AbstractOclVisualEffectEditPolicy() {
		this(GMFToolingRuntimePlugin.getInstance().getOclTrackerFactory());
	}

	public AbstractOclVisualEffectEditPolicy(OclTrackerFactory.Type factoryType) {
		this(GMFToolingRuntimePlugin.getInstance().getOclTrackerFactory(factoryType));
	}

	public AbstractOclVisualEffectEditPolicy(OclTrackerFactory factory) {
		myOclTrackerFactory = factory;
	}

	private OclTracker getOclTracker() {
		if (myOclTracker == null) {
			myOclTracker = myOclTrackerFactory.createOclTracker(getExpressionBody(), false);
		}
		return myOclTracker;
	}

	private boolean initializeOclTracker() {
		EObject context = getContext();
		if (context == null) {
			return false;
		}
		getOclTracker().initialize(context);
		return true;
	}

	protected EObject getContext() {
		return getSemanticHost();
	}

	// TODO: never called from hierarchy since handleNotificationEvent is overriden
	@Override
	protected boolean shouldHandleNotificationEvent(Notification event) {
		return getOclTracker().handleNotification(event);
	}

	private void installVisualEffect() {
		if (!initializeOclTracker()) {
			return;
		}
		getOclTracker().installListeners(getHostImpl().getEditingDomain(), //
				new NotificationListener() {

					public void notifyChanged(Notification notification) {
						refreshVisualEffect();
					}
				}, getOclRegistrator());
	}

	@Override
	public void activate() {
		super.activate();
		installVisualEffect();
		refreshVisualEffect();
	}

	@Override
	protected void refreshSemantic() {
		//Important - we are NOT actually CanonicalEditPolicy, and not capable to perform semantic refresh
		//We extends CanonicalEditPolicy 
		//super.refreshSemantic();
	}

	@Override
	public void deactivate() {
		getOclTracker().uninstallListeners();
		super.deactivate();
	}

	private void refreshVisualEffect() {
		if (!initializeOclTracker()) {
			return;
		}
		setVisualEffectValue(getOclTracker().getValue());
	}

	protected abstract void setVisualEffectValue(Object value);

	@Override
	protected void handleNotificationEvent(Notification event) {
		if (shouldHandleNotificationEvent(event)) {
			refreshVisualEffect();
		}
	}

	protected final void executeICommand(ICommand command) {
		executeCommand(new ICommandProxy(command));
	}

	protected IGraphicalEditPart getHostImpl() {
		return (IGraphicalEditPart) getHost();
	}

	/**
	 * We are using CanonicalEditPolicy infrastructure for listening to the
	 * model, executing commands, etc. However, we are not going to
	 * install/remove any new children from view-tree.
	 */
	@Override
	protected final List<EObject> getSemanticChildrenList() {
		return Collections.emptyList();
	}

	protected final void ensureHasStyle(EClass styleClass) {
		if (!NotationPackage.eINSTANCE.getStyle().isSuperTypeOf(styleClass)) {
			throw new IllegalArgumentException("Notation Style expected:" + styleClass); //$NON-NLS-1$
		}
		IGraphicalEditPart editPart = getHostImpl();
		View view = editPart.getNotationView();
		Style style = view.getStyle(styleClass);
		if (style == null) {
			style = (Style) styleClass.getEPackage().getEFactoryInstance().create(styleClass);
			SetRequest request = new SetRequest(editPart.getEditingDomain(), view, NotationPackage.eINSTANCE.getView_Styles(), style);
			executeICommand(new SetValueCommand(request));
		}
	}

	protected final Registrator getOclRegistrator() {
		if (myOclRegistrator == null) {
			myOclRegistrator = new Registrator() {

				public void registerListener(String filterId, NotificationListener listener, EObject element) {
					addListenerFilter(filterId, listener, element);
				}

				public void unregisterListener(String filterId) {
					removeListenerFilter(filterId);
				}
			};
		}
		return myOclRegistrator;
	}
}
