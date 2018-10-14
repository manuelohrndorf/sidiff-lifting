package org.sidiff.difference.symmetric.compareview.internal;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LayoutListener;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.core.listener.DiagramEventBroker;
import org.eclipse.gmf.runtime.diagram.core.listener.NotificationListener;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.AbstractDecorator;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoratorTarget;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.swt.graphics.Color;
import org.eclipse.ui.PlatformUI;
import org.sidiff.difference.symmetric.compareview.DecorationHook;
import org.sidiff.integration.editor.access.IntegrationEditorAccess;

public class DifferenceSelectionDecorator extends AbstractDecorator {
	
	private static final String HOOK_ID = "org.sidiff.difference.symmetric.compareview";
	
	private Map<PolylineConnection, Style> decoratedLines = null;
	private DifferenceSelectionController controller = DifferenceSelectionController.getInstance();
	
	private LayoutListener layoutListener = new LayoutListener() {
		
		@Override
		public void setConstraint(IFigure child, Object constraint) {}
		
		@Override
		public void remove(IFigure child) {}
		
		@Override
		public void postLayout(IFigure container) {
//			refresh();
		}
		
		@Override
		public boolean layout(IFigure container) { return false; }
		
		@Override
		public void invalidate(IFigure container) {}
	};
	
	private static class Style {
			
		public Color color = null;
		public int lineWidth = 0;
	
		public Style(Color c, int lw){
			color = c;
			lineWidth = lw;
		}
	}

	public DifferenceSelectionDecorator(IDecoratorTarget decoratorTarget) {
		super(decoratorTarget);
		decoratedLines = new HashMap<PolylineConnection, Style>();
	}
	
	public View decorate() {
		removeDecoration();
				
		IGraphicalEditPart editPart = (IGraphicalEditPart) getDecoratorTarget().getAdapter(EditPart.class);
		
		View view = (View) getDecoratorTarget().getAdapter(View.class);

		if(controller.getPrefferedDecoratorTarget(view.getElement()) == getDecoratorTarget()){			
			if(selectionContains(view.getElement())){
				IExtensionRegistry registry = Platform.getExtensionRegistry();
				IConfigurationElement[] config = registry.getConfigurationElementsFor(HOOK_ID);
				try {
					for(IConfigurationElement configElement : config) {
						final Object object = configElement.createExecutableExtension("hook");
						if(object instanceof DecorationHook) {
							DecorationHook hook = (DecorationHook) object;
							hook.onViewWillBeDecorated(view);
						}
					}
				} catch(CoreException ex) {
					ex.printStackTrace();
				}

				int x = editPart.getFigure().getBounds().x;
				int y = editPart.getFigure().getBounds().y;
				
				FigureCanvas canvas = (FigureCanvas) editPart.getViewer().getControl();
				canvas.scrollSmoothTo(x, y);
				
				editPart.getViewer().reveal(editPart);
				if(view instanceof Node){
					IFigure figure = editPart.getFigure();

					IFigure decoration = new DifferenceSelectionDecorationFigure();
					decoration.setSize(figure.getSize());

					setDecoration(getDecoratorTarget().addShapeDecoration(decoration, IDecoratorTarget.Direction.CENTER, 0, false));
				} else if(view instanceof Edge){
					PolylineConnection connection = (PolylineConnection) editPart.getFigure();
					decoratedLines.put(connection, new Style(connection.getForegroundColor(), connection.getLineWidth()));
					connection.setForegroundColor(ColorConstants.red);
					connection.setLineWidth(2);
				} 
				return view;
			}
		}
		return null;
	}
	
	@Override
	public void refresh() {
		decorate();
	}
	
	private boolean selectionContains(EObject viewDataElement){
		Collection<EObject> viewDataElements = IntegrationEditorAccess.getInstance().getHighlightableElements(viewDataElement);
		
		if(viewDataElements != null && !viewDataElements.isEmpty()){
			for(EObject selected : controller.getSelected()){
				for (EObject element : viewDataElements){
					String fragmentA = EcoreUtil.getURI(selected).fragment();
					String fragmentB = EcoreUtil.getURI(element).fragment();
					
					if (fragmentA.equals(fragmentB)){
						return true;
					}					
				}			
			}
		}
			
		return false;
	}

	public void test(){
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
	}
	@Override
	protected void removeDecoration() {
		super.removeDecoration();
		
		for(PolylineConnection connection : decoratedLines.keySet()){
			connection.setForegroundColor(decoratedLines.get(connection).color);
			connection.setLineWidth(decoratedLines.get(connection).lineWidth);
		}
	}

	private NotificationListener notificationListener = new NotificationListener() {

        /* (non-Javadoc)
         * @see org.eclipse.gmf.runtime.diagram.core.listener.NotificationListener#notifyChanged(org.eclipse.emf.common.notify.Notification)
         */
        public void notifyChanged(Notification notification) {
            refresh();
        }
	};

	/**
	 * Adds decoration if applicable.
	 */
	public void activate() {

		IGraphicalEditPart gep = (IGraphicalEditPart) getDecoratorTarget().getAdapter(IGraphicalEditPart.class);
		assert gep != null;

		DiagramEventBroker.getInstance(gep.getEditingDomain()).addNotificationListener(gep.getNotationView(), NotationPackage.eINSTANCE.getDescriptionStyle_Description(), notificationListener);
		controller.registerDecorator(this, getDecoratorTarget());
		
		GraphicalEditPart editPart = (GraphicalEditPart) getDecoratorTarget().getAdapter(GraphicalEditPart.class);
		editPart.getFigure().addLayoutListener(layoutListener);
	}

	/**
	 * Removes the decoration.
	 */
	public void deactivate() {
		removeDecoration();

		IGraphicalEditPart gep = (IGraphicalEditPart) getDecoratorTarget().getAdapter(IGraphicalEditPart.class);
		assert gep != null;

		DiagramEventBroker.getInstance(gep.getEditingDomain()).removeNotificationListener(gep.getNotationView(), NotationPackage.eINSTANCE.getDescriptionStyle_Description(), notificationListener);
		controller.unregisterDecorator(this);

		GraphicalEditPart editPart = (GraphicalEditPart) getDecoratorTarget().getAdapter(GraphicalEditPart.class);
		editPart.getFigure().removeLayoutListener(layoutListener);
	}
}
