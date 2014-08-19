package org.sidiff.difference.symmetric.compareview.internal.handler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartSashContainerElement;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.impl.BasicFactoryImpl;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecoretools.diagram.edit.commands.InitializeAndLayoutDiagramCommand;
import org.eclipse.emf.ecoretools.diagram.edit.parts.EPackageEditPart;
import org.eclipse.emf.ecoretools.diagram.part.EcoreDiagramEditor;
import org.eclipse.emf.ecoretools.diagram.part.EcoreDiagramEditorPlugin;
import org.eclipse.emf.ecoretools.diagram.part.EcoreDiagramEditorUtil;
import org.eclipse.emf.ecoretools.diagram.part.EcoreVisualIDRegistry;
import org.eclipse.emf.ecoretools.diagram.part.Messages;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.core.services.ViewService;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.core.GMFEditingDomainFactory;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.EditorSite;
import org.eclipse.ui.internal.WorkbenchPage;
import org.eclipse.ui.part.FileEditorInput;
import org.sidiff.difference.symmetric.SymmetricDifference;
//import org.eclipse.ui.internal.EditorPane;
//import org.eclipse.ui.internal.EditorSashContainer;
//import org.eclipse.ui.internal.EditorStack;
//import org.eclipse.ui.internal.PageLayout;


@SuppressWarnings("restriction")
public class LayoutCompareViewHandler extends AbstractHandler implements IHandler {

	private IEditorPart differenceEditor = null;
	private Map<String, EditorFileCombination> editorFileCombinations = null;
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		differenceEditor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		editorFileCombinations = new HashMap<String, LayoutCompareViewHandler.EditorFileCombination>();
		
		for(Object key : event.getParameters().keySet()){
			String parameterName = (String) key;
			String[] elements = ((String) event.getParameters().get(parameterName)).split(",");
			if(elements.length == 4){
				EditorFileCombination combination = new EditorFileCombination();
				combination.editorId = elements[0];
				combination.treeEditorId = elements[1];
				combination.modelFile = elements[2];
				combination.diagramFile = elements[3];
				editorFileCombinations.put(combination.modelFile, combination);
			}
		}
		
		EditingDomain editingDomain = ((IEditingDomainProvider) differenceEditor).getEditingDomain();
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		
		Resource resourceA = null;
		Resource resourceB = null;
		SymmetricDifference difference = null;
		
		IEditorPart editorA = null;
		IEditorPart editorAT = null;
		IEditorPart editorB = null;
		IEditorPart editorBT = null;
		
		//first get the difference object to retrieve the two compared ecore resources
		for(Resource resource : editingDomain.getResourceSet().getResources()){
			if(resource.getContents().get(0) instanceof SymmetricDifference){
				difference = (SymmetricDifference) resource.getContents().get(0);
			}
		}
		resourceA = difference.getModelA();
		resourceB = difference.getModelB();
		
		URI uriA = resourceA.getURI();
		URI uriB = resourceB.getURI();
		
	
//		Path resourcePathA = new Path(resourceA.getURI().toPlatformString(true).replace(".smf.xmi", ""));
//		Path resourcePathB = new Path(resourceB.getURI().toPlatformString(true).replace(".smf.xmi", ""));
		EditorFileCombination combination = editorFileCombinations.get(	uriA.fileExtension());
		if(combination == null){
			ErrorDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Unknow model type, no editor found.", "", new Status(Status.ERROR, "org.sidiff.difference.symmetric.compareview", ""));
			return null;
		}
		//for each resource check for existence of the diagram file
		//in the ecore case we can create a missing diagram file if needed
		
		URI diagramUriA = URI.createURI(uriA.toString().replaceAll(combination.modelFile + "$", combination.diagramFile));
		IFile diagramA = root.getFile(new Path(diagramUriA.toPlatformString(true)));
		
//		IFile diagramA = root.getFile(new Path(resourceA.getURI().toPlatformString(true).replace(".smf.xmi", "").replaceAll(combination.modelFile + "$", combination.diagramFile)));
//		if(!diagramA.exists()){
//			if(combination.editorId.equals(EcoreDiagramEditor.ID)){
//				try {
//					diagramA.create(null, true, new NullProgressMonitor());
//					createDiagram(resourceA.getContents().get(0), diagramA);
//				} catch (CoreException e) {
//					EcoreDiagramEditorPlugin.getInstance().logError("Unable to create diagram file", e); //$NON-NLS-1$
//				}		
//			}
//		}
		editorAT = openDiagram(uriA, combination.treeEditorId);
		if(diagramA.exists())
			editorA = openDiagram(diagramUriA, combination.editorId);
		
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().activate(editorAT);

		URI diagramUriB = URI.createURI(uriB.toString().replaceAll(combination.modelFile + "$", combination.diagramFile));
		IFile diagramB = root.getFile(new Path(diagramUriB.toPlatformString(true)));

//		IFile diagramB = root.getFile(new Path(resourceB.getURI().toPlatformString(true).replace(".smf.xmi", "").replaceAll(combination.modelFile + "$", combination.diagramFile)));
//		if(!diagramB.exists()){
//			if(combination.editorId.equals(EcoreDiagramEditor.ID)){
//				try {
//					diagramB.create(null, true, new NullProgressMonitor());
//					createDiagram(resourceB.getContents().get(0), diagramB);
//				} catch (CoreException e) {
//					EcoreDiagramEditorPlugin.getInstance().logError("Unable to create diagram file", e); //$NON-NLS-1$
//				}
//			}
//		}
		editorBT = openDiagram(uriB, combination.treeEditorId);
		if(diagramB.exists())
			editorB = openDiagram(diagramUriB, combination.editorId);
		
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
	
	private IEditorPart openDiagram(URI diagramURI, String editorId){
		try {
			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			return page.openEditor(new URIEditorInput(diagramURI), editorId);
		} catch (PartInitException ex) {
			EcoreDiagramEditorPlugin.getInstance().logError("Unable to open editor", ex); //$NON-NLS-1$
		}
		return null;
	}
	
	private IEditorPart openDiagram(IFile diagramFile, String editorId){
		try {
			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			return page.openEditor(new FileEditorInput(diagramFile), editorId);
		} catch (PartInitException ex) {
			EcoreDiagramEditorPlugin.getInstance().logError("Unable to open editor", ex); //$NON-NLS-1$
		}
		return null;
	}
	
	private void createDiagram(final EObject root, IFile diagramFile){
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


	private static class EditorFileCombination {
		
		public String editorId = null;
		public String treeEditorId = null;
		public String modelFile = null;
		public String diagramFile = null;
		
	}
}
