package org.sidiff.vcmsintegration.structureview.actions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.e4.ui.model.application.ui.basic.MBasicFactory;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartSashContainerElement;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.EditorSite;
import org.eclipse.ui.internal.WorkbenchPage;
import org.eclipse.ui.part.WorkbenchPart;
import org.sidiff.integration.editor.access.IntegrationEditorAccess;
import org.sidiff.integration.editor.extension.IEditorIntegration;
import org.sidiff.vcmsintegration.Activator;
import org.sidiff.vcmsintegration.SiLiftCompareConfiguration;
import org.sidiff.vcmsintegration.SiLiftCompareDifferencer;
import org.sidiff.vcmsintegration.SiLiftCompareDifferencer.IModelViewerAdapter;
import org.sidiff.vcmsintegration.remote.CompareResource;
import org.sidiff.vcmsintegration.remote.CompareResource.Side;
import org.sidiff.vcmsintegration.structureview.SiLiftStructureMergeViewer;
import org.sidiff.vcmsintegration.structureview.SiLiftStructureMergeViewerContentProvider;
import org.sidiff.vcmsintegration.util.MessageDialogUtil;

/**
 * This action tries to open diagram files corresponding with the
 * {@link CompareResource} delivered by the
 * {@link SiLiftStructureMergeViewerContentProvider}. The behaviour of the action
 * depends on the type of {@link CompareResource}. Possible types are local, git
 * and svn.
 * 
 */
public class ShowDiagramAction extends Action {

	private SiLiftCompareDifferencer differencer;

	/**
	 * Creates a new {@link ShowDiagramAction} and sets the description
	 * and icon for the toolbar used in the {@link SiLiftStructureMergeViewer}.
	 * 
	 * @param contentProvider
	 */
	public ShowDiagramAction(SiLiftCompareConfiguration config) {
		this.setText("Open Diagram");
		this.setImageDescriptor(Activator.getImageDescriptor(Activator.IMAGE_SHOW_DIAGRAM));
		this.differencer = config.getDifferencer();
		this.differencer.addModelViewerAdapter(new IModelViewerAdapter() {
			@Override
			public void setDirty(Side side, boolean dirty) {
				// do nothing
			}

			@Override
			public void onRefresh(Side side) {
				// do nothing
			}

			@Override
			public void onChange(Side side, CompareResource compRes) {
				updateEnabledState();
			}
		});
	}

	/**
	 * The main implementation of the {@link ShowDiagramAction}.
	 */
	@Override
	public void run() {
		MessageDialogUtil.showProgressDialog(new IRunnableWithProgress() {
			@Override
			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				MultiStatus multiStatus = new MultiStatus(Activator.PLUGIN_ID, 0, "Error showing diagrams", null);

				IEditorPart leftDiagram = null;
				try {
					leftDiagram = openDiagram("Left", differencer.getModifiedLeft());
				} catch (CoreException e) {
					multiStatus.add(e.getStatus());
				}

				IEditorPart rightDiagram = null;
				try {
					rightDiagram = openDiagram("Right", differencer.getModifiedRight());
				} catch (CoreException e) {
					multiStatus.add(e.getStatus());
				}

				IEditorPart ancestorDiagram = null;
				if(differencer.getAncestor().getResource() != null) {
					try {
						ancestorDiagram = openDiagram("Ancestor", differencer.getAncestor());
					} catch (CoreException e) {
						multiStatus.add(e.getStatus());
					}
				}

				if(leftDiagram != null) {
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().activate(leftDiagram);
				}
				positionEditorParts(leftDiagram, rightDiagram, ancestorDiagram);

				if(!multiStatus.isOK()) {
					throw new InvocationTargetException(new CoreException(multiStatus));
				}
			}
		});
	}

	protected IEditorPart openDiagram(String label, CompareResource res) throws CoreException {
		if(res.getResource() == null) {
			throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID,
					label + ": Diagram cannot be shown for empty resource."));
		}
		IEditorIntegration editorIntegration = IntegrationEditorAccess.getInstance().getIntegrationEditorForModel(res.getResource());
		if(editorIntegration == null) {
			throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID,
					label + ": No suitable diagram or editor integration was found"));
		}
		String diagramFileExt = editorIntegration.getFileExtensions().get("diagram");
		if(diagramFileExt == null) {
			throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID,
					label + ": Diagramming not supported by editor integration: " + editorIntegration));
		}
		URI uri = res.resolveRelatedFile(diagramFileExt);
		IEditorPart editorPart = editorIntegration.openDiagram(uri);
		if(editorPart == null) {
			throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID,
					label + ": Diagram editor part could not be created."));
		}

		try {
			changePartNames(editorPart, label);
		} catch (CoreException e) {
			Activator.logError(label + ": Changing diagram editor part name failed.", e);
		}

		return editorPart;
	}

	protected void changePartNames(IEditorPart part, String name) throws CoreException {
		try {
			Method method = WorkbenchPart.class.getDeclaredMethod("setPartName", String.class);
			method.setAccessible(true);
			method.invoke(part, name);
		} catch(Exception e) {
			throw new CoreException(new Status(IStatus.WARNING, Activator.PLUGIN_ID, "Changing part names failed", e));
		};
	}

	// TODO: fix positioning of the editor parts. the ancestor part is not positioned at all.
	// https://dzone.com/articles/programmatically-split-editor-
	protected void positionEditorParts(IEditorPart partLeft, IEditorPart partRight, IEditorPart ancestorPart) {
		if(partLeft == null || partRight == null) {
			return;
		}

		WorkbenchPage page = (WorkbenchPage) partLeft.getSite().getPage();
		EPartService partService = page.getCurrentPerspective().getContext().get(EPartService.class);
		MPartSashContainerElement relTo = (MPartSashContainerElement) partService.getActivePart().getParent();

		MPartSashContainerElement toInsertA = null;
		MPartSashContainerElement toInsertB = null;

		boolean insertA = true;
		boolean insertB = true;

		for (MPart part : partService.getParts()) {
			if (part.getElementId().equals("org.eclipse.e4.ui.compatibility.editor") && part.getParent() != relTo) {
				if (toInsertA == null) {
					insertA = false;
					toInsertA = (MPartSashContainerElement) part.getParent();
				} else if (toInsertB == null && part.getParent() != toInsertA) {
					insertB = false;
					toInsertB = (MPartSashContainerElement) part.getParent();
				}
			}
		}

		MPart partA = null;
		if (partLeft != null) {
			partA = ((EditorSite) partLeft.getSite()).getModel();
		}

		MPart partB = null;
		if (partRight != null) {
			partB = ((EditorSite) partRight.getSite()).getModel();
		}

		if (partA != null)
			partA.setToBeRendered(true);
		if (partB != null)
			partB.setToBeRendered(true);


		if (toInsertA == null) {
			toInsertA = MBasicFactory.INSTANCE.createPartStack();
		}

		MPartStack toInsertAStack = (MPartStack) toInsertA;
		if (partA != null && !toInsertAStack.getChildren().contains(partA))
			toInsertAStack.getChildren().add(partA);
		if (partA != null)
			toInsertAStack.setSelectedElement(partA);

		if (toInsertB == null) {
			toInsertB = MBasicFactory.INSTANCE.createPartStack();
		}

		MPartStack toInsertBStack = (MPartStack) toInsertB;
		if (partB != null && !toInsertBStack.getChildren().contains(partB))
			toInsertBStack.getChildren().add(partB);
		if (partB != null)
			toInsertBStack.setSelectedElement(partB);

		EModelService modelService = page.getCurrentPerspective().getContext().get(EModelService.class);
		if (insertA)
			modelService.insert(toInsertA, relTo, EModelService.RIGHT_OF, 0.25f);
		if (insertB)
			modelService.insert(toInsertB, toInsertA, EModelService.BELOW, 0.5f);
	}

	protected void updateEnabledState() {
		setEnabled(differencer.getLeft() != null
				&& differencer.getLeft().getResource() != null
				&& differencer.getRight() != null
				&& differencer.getRight().getResource() != null);
	}
}
