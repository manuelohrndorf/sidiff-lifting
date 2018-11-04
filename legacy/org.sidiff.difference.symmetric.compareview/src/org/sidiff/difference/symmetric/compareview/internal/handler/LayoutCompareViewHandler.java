package org.sidiff.difference.symmetric.compareview.internal.handler;
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
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.EditorSite;
import org.eclipse.ui.internal.WorkbenchPage;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.integration.editor.access.IntegrationEditorAccess;
import org.sidiff.integration.editor.extension.IEditorIntegration;

@SuppressWarnings("restriction")
public class LayoutCompareViewHandler extends AbstractHandler implements IHandler {

	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IEditorPart differenceEditor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		EditingDomain editingDomain = ((IEditingDomainProvider) differenceEditor).getEditingDomain();

		//first get the difference object to retrieve the two compared ecore resources
		SymmetricDifference difference = null;
		for(Resource resource : editingDomain.getResourceSet().getResources()){
			if(resource.getContents().get(0) instanceof SymmetricDifference){
				difference = (SymmetricDifference) resource.getContents().get(0);
				break;
			}
		}
		Resource resourceA = difference.getModelA();
		Resource resourceB = difference.getModelB();
		
		URI uriA = resourceA.getURI();
		URI uriB = resourceB.getURI();
	
		IEditorIntegration deA = IntegrationEditorAccess.getInstance().getIntegrationEditorForModel(resourceA);
		IEditorIntegration deB = IntegrationEditorAccess.getInstance().getIntegrationEditorForModel(resourceB);

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
		WorkbenchPage page = (WorkbenchPage) differenceEditor.getSite().getPage();
		EPartService partService = page.getCurrentPerspective().getContext().get(EPartService.class);
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
		}
		
		MPart partAT = null;
		if(editorAT != null){
			partAT = ((EditorSite) editorAT.getSite()).getModel();
		}
		
		MPart partB = null;
		if(editorB != null){
			partB = ((EditorSite) editorB.getSite()).getModel();
		}
		
		MPart partBT = null;
		if(editorBT != null){
			partBT = ((EditorSite) editorBT.getSite()).getModel();
		}
		
		if(partA != null)
			partA.setToBeRendered(true);
		if(partAT != null)
			partAT.setToBeRendered(true);
		if(partB != null)
			partB.setToBeRendered(true);
		if(partBT != null)
			partBT.setToBeRendered(true);
		
		EModelService modelService = page.getCurrentPerspective().getContext().get(EModelService.class);

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
//		EditorSashContainer container = (EditorSashContainer) page.getEditorPresentation().getLayoutPart();
//		EditorPane paneA = (EditorPane) ((EditorSite) editorA.getSite()).getPane();
//		EditorPane paneB = (EditorPane) ((EditorSite) editorB.getSite()).getPane();
//	
//		container.removeEditor(paneA);
//		container.removeEditor(paneB);
//		
//		EditorStack stackA = null;
//		EditorStack stackB = null;
//		if(container.getChildren().length < 3){
//			stackA = new EditorStack(container, page);
//			stackB = new EditorStack(container, page);
//			container.add(stackA, PageLayout.RIGHT, 0.3f, container.getActiveWorkbook());
//			container.add(stackB, PageLayout.BOTTOM, 0.5f, stackA);
//		} else {
//			stackA = (EditorStack) container.getChildren()[1];
//			stackB = (EditorStack) container.getChildren()[2];
//		}
//		stackA.add(paneA);
//		stackB.add(paneB);
//		
//		//show the correct editorpanes
//		stackA.setSelection(paneA);
//		stackB.setSelection(paneB);
//		
//		//Selections will be made in the difference editor so it should remain focus
//		differenceEditor.setFocus();
		
		return null;
	}
	
	
//	private static IEditorPart openDiagram(URI diagramURI, String editorId){
//		
//		try {
//			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
//			return page.openEditor(new URIEditorInput(diagramURI), editorId);
//		} catch (PartInitException ex) {
//			return null;
//		}
//	}
//	
//	private static IEditorPart openDiagram(IFile diagramFile, String editorId){
//		try {
//			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
//			return page.openEditor(new FileEditorInput(diagramFile), editorId);
//		} catch (PartInitException ex) {
//			return null;
//		}
//	}
	
	/*
	// Not compatible to Eclipse Luna
	private static void createDiagram(final EObject root, IFile diagramFile){
		TransactionalEditingDomain editingDomain = GMFEditingDomainFactory.INSTANCE.createEditingDomain();

		List<IFile> affectedFiles = new ArrayList<IFile>();
		affectedFiles.add(diagramFile);
		
		URI diagramModelURI = URI.createPlatformResourceURI(diagramFile.getFullPath().toString(), true);
		ResourceSet resourceSet = editingDomain.getResourceSet();
		final Resource diagramResource = resourceSet.createResource(diagramModelURI);
		AbstractTransactionalCommand command = new AbstractTransactionalCommand(editingDomain, Messages.EcoreNewDiagramFileWizard_InitDiagramCommand, affectedFiles) {

			protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
				int diagramVID = EcoreVisualIDRegistry.getDiagramVisualID(root);
				if (diagramVID != EPackageEditPart.VISUAL_ID) {
					return CommandResult.newErrorCommandResult(Messages.EcoreNewDiagramFileWizard_IncorrectRootError);
				}
				Diagram diagram = ViewService.createDiagram(root, EPackageEditPart.MODEL_ID, EcoreDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
				diagramResource.getContents().add(diagram);

				return CommandResult.newOKCommandResult();
			}
		};
		try {
			// Create Diagram
			OperationHistoryFactory.getOperationHistory().execute(command, new NullProgressMonitor(), null);
			diagramResource.save(EcoreDiagramEditorUtil.getSaveOptions());

			// Initialize and Layout Diagram
			if (diagramResource.getContents().get(0) instanceof Diagram) {
				InitializeAndLayoutDiagramCommand initializeAndLayoutDiagram = new InitializeAndLayoutDiagramCommand(editingDomain, (Diagram) diagramResource.getContents().get(0), false);
				OperationHistoryFactory.getOperationHistory().execute(initializeAndLayoutDiagram, new NullProgressMonitor(), null);
				diagramResource.save(EcoreDiagramEditorUtil.getSaveOptions());
			}
		} catch (ExecutionException e) {
			EcoreDiagramEditorPlugin.getInstance().logError("Unable to create model and diagram", e); //$NON-NLS-1$
		} catch (IOException ex) {
			EcoreDiagramEditorPlugin.getInstance().logError("Save operation failed for: " + diagramModelURI, ex); //$NON-NLS-1$
		} 
	}
	 */
}
