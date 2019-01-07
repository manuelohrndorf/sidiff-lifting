package org.sidiff.integration.editor.highlighting.gmf.internal;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gef.handles.MoveHandleLocator;
import org.eclipse.gmf.runtime.diagram.core.listener.DiagramEventBroker;
import org.eclipse.gmf.runtime.diagram.core.listener.NotificationListener;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.AbstractDecorator;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoratorTarget;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.sidiff.integration.editor.access.IntegrationEditorAccess;
import org.sidiff.integration.editor.highlighting.StyledObject;
import org.sidiff.integration.editor.highlighting.internal.SelectionController;

public class SelectionDecorator extends AbstractDecorator {

	private NotificationListener notificationListener = new NotificationListener() {
		@Override
		public void notifyChanged(Notification notification) {
			refresh();
		}
	};

	public SelectionDecorator(IDecoratorTarget decoratorTarget) {
		super(decoratorTarget);
	}

	@Override
	public void activate() {
		IGraphicalEditPart gep = (IGraphicalEditPart) getDecoratorTarget().getAdapter(IGraphicalEditPart.class);
		DiagramEventBroker.getInstance(gep.getEditingDomain()).addNotificationListener(gep.getNotationView(),
				NotationPackage.eINSTANCE.getDescriptionStyle_Description(), notificationListener);
		SelectionControllerDiagram.getInstance().registerDecorator(this, getDecoratorTarget());
		refresh();
	}

	@Override
	public void deactivate() {
		removeDecoration();

		IGraphicalEditPart gep = (IGraphicalEditPart) getDecoratorTarget().getAdapter(IGraphicalEditPart.class);
		DiagramEventBroker.getInstance(gep.getEditingDomain()).removeNotificationListener(gep.getNotationView(),
				NotationPackage.eINSTANCE.getDescriptionStyle_Description(), notificationListener);
		SelectionControllerDiagram.getInstance().unregisterDecorator(this, getDecoratorTarget());
	}

	@Override
	public void refresh() {
		removeDecoration();
		if(!SelectionController.getInstance().isEnabled()) {
			return;
		}

		IGraphicalEditPart editPart = (IGraphicalEditPart)getDecoratorTarget().getAdapter(IGraphicalEditPart.class);
		View view = (View)getDecoratorTarget().getAdapter(View.class);
		if(SelectionControllerDiagram.getInstance().getPrefferedDecoratorTarget(view.getElement()) != getDecoratorTarget()) {
			return;
		}
		Optional<StyledObject> styled = findStyledObject(ViewUtil.resolveSemanticElement(view));
		if(!styled.isPresent()) {
			return;
		}

		if (styled.get().isFocus()) {
			int x = editPart.getFigure().getBounds().x;
			int y = editPart.getFigure().getBounds().y;
	
			FigureCanvas canvas = (FigureCanvas) editPart.getViewer().getControl();
			canvas.scrollSmoothTo(x, y);
		}

		editPart.getViewer().reveal(editPart);

		if (view instanceof Node) {
			IFigure decoration = new SelectionNodeDecorationFigure(editPart.getFigure(), styled.get());
			setDecoration(getDecoratorTarget().addShapeDecoration(decoration, IDecoratorTarget.Direction.CENTER, 0, false));
		} else if (editPart.getFigure() instanceof PolylineConnection) {
			SelectionEdgeDecorationFigure decoration = new SelectionEdgeDecorationFigure((PolylineConnection)editPart.getFigure(), styled.get());
			setDecoration(getDecoratorTarget().addDecoration(decoration, new MoveHandleLocator((PolylineConnection)editPart.getFigure()), false));
		}
	}

	private Optional<StyledObject> findStyledObject(EObject viewDataElement) {
		final Collection<EObject> highlightable = IntegrationEditorAccess.getInstance().getHighlightableElements(viewDataElement);
		return SelectionController.getInstance().getSelected().stream()
			.filter(styled -> highlightable.stream().anyMatch(
					e -> Objects.equals(EcoreUtil.getURI(styled.getEObject()).fragment(), EcoreUtil.getURI(e).fragment())
							|| EcoreUtil.equals(styled.getEObject(), e)))
			.findFirst();
	}
}
