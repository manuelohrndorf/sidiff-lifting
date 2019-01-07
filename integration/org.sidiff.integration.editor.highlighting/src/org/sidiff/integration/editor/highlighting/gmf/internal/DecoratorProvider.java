package org.sidiff.integration.editor.highlighting.gmf.internal;

import org.eclipse.gmf.runtime.common.core.service.AbstractProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.CreateDecoratorsOperation;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoratorProvider;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoratorTarget;
import org.eclipse.gmf.runtime.notation.View;

public class DecoratorProvider extends AbstractProvider implements IDecoratorProvider {

	public static final String DECORATOR_KEY = "org.sidiff.integration.editor.highlighting.decorator.HighlighingDecorator";

	@Override
	public boolean provides(IOperation operation) {
		if(operation instanceof CreateDecoratorsOperation) {
			IDecoratorTarget target = ((CreateDecoratorsOperation)operation).getDecoratorTarget();
			IGraphicalEditPart editPart = (IGraphicalEditPart)target.getAdapter(IGraphicalEditPart.class);
			View view = (View)target.getAdapter(View.class);
			return editPart != null && view != null && ViewUtil.resolveSemanticElement(view) != null;
		}
		return false;
	}

	@Override
	public void createDecorators(IDecoratorTarget decoratorTarget) {
		decoratorTarget.installDecorator(DECORATOR_KEY, new SelectionDecorator(decoratorTarget));
	}
}
