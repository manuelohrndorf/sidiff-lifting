package org.sidiff.integration.editor.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartSashContainerElement;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.impl.BasicFactoryImpl;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.emf.common.util.URI;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.EditorSite;
import org.sidiff.integration.editor.IEditorIntegration;

@SuppressWarnings("restriction")
public class ShowModelsHandler extends AbstractHandler implements IHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		URI uriA = URI.createURI((String)event.getParameters().get("org.sidiff.integration.editor.commands.ShowModels.ModelA"));
		URI uriB = URI.createURI((String)event.getParameters().get("org.sidiff.integration.editor.commands.ShowModels.ModelB"));

		IEditorIntegration deA = IEditorIntegration.MANAGER.getIntegrationEditorForModelOrDiagramFile(uriA);
		IEditorIntegration deB = IEditorIntegration.MANAGER.getIntegrationEditorForModelOrDiagramFile(uriB);

		IEditorPart editorAT = deA.openModelInDefaultEditor(uriA);
		IEditorPart editorA = deA.openDiagramForModel(uriA);
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().activate(editorAT);

		IEditorPart editorBT = deB.openModelInDefaultEditor(uriB);
		IEditorPart editorB = deB.openDiagramForModel(uriB);
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().activate(editorBT);
		
		//The EditorSashContainer is the LayoutPart that contains all EditorStacks and can be used to add new Stacks,
		//as well as arrange them, similar to a perspective layout
		//Here two additional EditorStacks are created with one diagram editor for each of them.
		//If there are already 3 EditorStacks, the existing ones are used.
		EPartService partService = PlatformUI.getWorkbench().getService(EPartService.class);
		MPartSashContainerElement relTo = (MPartSashContainerElement) partService.getActivePart().getParent();
		
		MPartSashContainerElement toInsertA = null;
		MPartSashContainerElement toInsertB = null;

		boolean insertA = true;
		boolean insertB = true;
		
		for(MPart part : partService.getParts()){
			if (part.getElementId().equals("org.eclipse.e4.ui.compatibility.editor") && part.getParent() != relTo) {
				if (toInsertA == null) {
					insertA = false;
					toInsertA = (MPartSashContainerElement) part.getParent();
				} else if (toInsertB == null && part.getParent() != toInsertA){
					insertB = false;
					toInsertB = (MPartSashContainerElement) part.getParent();
				}
			}
		}
		
		MPart partA = null;
		if(editorA != null){
			partA = ((EditorSite) editorA.getSite()).getModel();
			partA.setToBeRendered(true);
		}
		
		MPart partAT = null;
		if(editorAT != null){
			partAT = ((EditorSite) editorAT.getSite()).getModel();
			partAT.setToBeRendered(true);
		}
		
		MPart partB = null;
		if(editorB != null){
			partB = ((EditorSite) editorB.getSite()).getModel();
			partB.setToBeRendered(true);
		}
		
		MPart partBT = null;
		if(editorBT != null){
			partBT = ((EditorSite) editorBT.getSite()).getModel();
			partBT.setToBeRendered(true);
		}

		EModelService modelService = PlatformUI.getWorkbench().getService(EModelService.class);

		if (toInsertA == null){
			toInsertA = BasicFactoryImpl.eINSTANCE.createPartStack();
		}
		
		MPartStack toInsertAStack = (MPartStack) toInsertA;
		if(partA != null && !toInsertAStack.getChildren().contains(partA))
			toInsertAStack.getChildren().add(partA);
		if(partAT != null && !toInsertAStack.getChildren().contains(partAT))
			toInsertAStack.getChildren().add(partAT);
		if(partA != null)
			toInsertAStack.setSelectedElement(partA);
		
		if (toInsertB == null){
			toInsertB = BasicFactoryImpl.eINSTANCE.createPartStack();
		}
		
		MPartStack toInsertBStack = (MPartStack) toInsertB;
		if(partB != null && !toInsertBStack.getChildren().contains(partB))
			toInsertBStack.getChildren().add(partB);
		if(partBT != null && !toInsertBStack.getChildren().contains(partBT))
			toInsertBStack.getChildren().add(partBT);
		if(partB != null)
			toInsertBStack.setSelectedElement(partB);
		
		if (insertA) modelService.insert(toInsertA, relTo, EModelService.RIGHT_OF, 0.25f);
		if (insertB) modelService.insert(toInsertB, toInsertA, EModelService.BELOW, 0.5f);
		return null;
	}
}
