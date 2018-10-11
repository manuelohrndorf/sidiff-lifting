package org.sidiff.integration.editor.handlers;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.e4.ui.model.application.ui.basic.MPartSashContainer;
import org.eclipse.e4.ui.model.application.ui.basic.MPartSashContainerElement;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.ui.PlatformUI;

public class HideModelsHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		EPartService partService = PlatformUI.getWorkbench().getService(EPartService.class);
		MPartSashContainerElement partStack = (MPartSashContainerElement) partService.getActivePart().getParent();
		MPartSashContainerElement temp = (MPartSashContainerElement) partStack.getParent();
		MPartSashContainer sashContainer = (MPartSashContainer) temp;
		
		if(sashContainer.getChildren().size() > 1 && sashContainer.getChildren().get(1) instanceof MPartSashContainer){
			MPartSashContainer lowerSashContainer = (MPartSashContainer) sashContainer.getChildren().get(1);
			MPartStack partStackA = (MPartStack) lowerSashContainer.getChildren().get(0);
			MPartStack partStackB = (MPartStack) lowerSashContainer.getChildren().get(1);
			
			lowerSashContainer.getChildren().remove(partStackA);
			lowerSashContainer.getChildren().remove(partStackB);
			
			((MPartStack) partStack).getChildren().addAll(partStackA.getChildren());
			((MPartStack) partStack).getChildren().addAll(partStackB.getChildren());
		}
		return null;
	}

}
