package org.sidiff.difference.symmetric.compareview.internal.handler;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.e4.ui.model.application.ui.basic.MPartSashContainer;
import org.eclipse.e4.ui.model.application.ui.basic.MPartSashContainerElement;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchPage;
//import org.eclipse.ui.internal.EditorSashContainer;
//import org.eclipse.ui.internal.EditorStack;
//import org.eclipse.ui.internal.LayoutPart;


@SuppressWarnings("restriction")
public class CollapseCompareViewHandler extends AbstractHandler implements IHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		WorkbenchPage page = (WorkbenchPage) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		EPartService partService = page.getCurrentPerspective().getContext().get(EPartService.class);
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
//		IEditorPart differenceEditor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
//		WorkbenchPage page = (WorkbenchPage) differenceEditor.getSite().getPage();
//		EditorSashContainer container = (EditorSashContainer) page.getEditorPresentation().getLayoutPart();
//		
//		if(container.getChildren().length > 1){
//			EditorStack firstStack = (EditorStack) container.getChildren()[0];
//			List<EditorStack> temp = new ArrayList<EditorStack>();
//			for(int i = 1; i < container.getChildren().length; i++){
//				temp.add((EditorStack) container.getChildren()[i]);
//			}
//			
//			for(EditorStack editorStack : temp){
//				for(LayoutPart part : editorStack.getChildren()){
//					firstStack.add(part);
//				}
//				container.remove(editorStack);
//			}
//		}
		return null;
	}

}
