package org.sidiff.difference.symmetric.compareview.internal;

import org.eclipse.gmf.runtime.common.core.service.AbstractProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.CreateDecoratorsOperation;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoratorProvider;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoratorTarget;

public class DifferenceSelectionDecoratorProvider extends AbstractProvider implements IDecoratorProvider {
	
	public static final String DIFFERENCE_DECORATOR_KEY = "difference_decorator";
	
	@Override
	public boolean provides(IOperation operation) {
		if(operation instanceof CreateDecoratorsOperation){
			return true;
		}
		return false;
	}

	@Override
	public void createDecorators(IDecoratorTarget decoratorTarget) {
		decoratorTarget.installDecorator(DIFFERENCE_DECORATOR_KEY, new DifferenceSelectionDecorator(decoratorTarget));
	}

}
