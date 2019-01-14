package org.sidiff.integration.structureview.actions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.EditorSite;
import org.eclipse.ui.internal.WorkbenchPage;
import org.eclipse.ui.part.WorkbenchPart;
import org.sidiff.common.io.IOUtil;
import org.sidiff.common.ui.util.MessageDialogUtil;
import org.sidiff.integration.SiLiftCompareConfiguration;
import org.sidiff.integration.SiLiftCompareDifferencer;
import org.sidiff.integration.SiLiftCompareDifferencer.IModelViewerAdapter;
import org.sidiff.integration.editor.IEditorIntegration;
import org.sidiff.integration.internal.Activator;
import org.sidiff.integration.remote.CompareResource;
import org.sidiff.integration.remote.CompareResource.Side;

/**
 * This action tries to open diagram files corresponding with the
 * {@link CompareResource} delivered by the
 * {@link SiLiftStructureMergeViewerContentProvider}. The behaviour of the action
 * depends on the type of {@link CompareResource}.
 * 
 */
@SuppressWarnings("restriction")
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

		IEditorIntegration editorIntegration = IEditorIntegration.MANAGER.getIntegrationEditorForModel(res.getResource());
		if(editorIntegration == null) {
			throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID,
					label + ": No suitable diagram or editor integration was found"));
		}

		URI uri;
		try {
			uri = resolveRelatedFile(res, editorIntegration);
		} catch (IOException e) {
			throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID,
					label + ": " + e.getMessage(), e));
		}

		IEditorPart editorPart = editorIntegration.openDiagram(uri);
		if(editorPart == null) {
			throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID,
					label + ": Diagram editor part could not be created."));
		}

		try {
			changePartName(editorPart, label);
		} catch (CoreException e) {
			Activator.logError(label + ": Changing diagram editor part name failed.", e);
		}

		return editorPart;
	}

	protected URI resolveRelatedFile(CompareResource res, IEditorIntegration editorIntegration) throws CoreException, IOException {
		String diagramFileExt = editorIntegration.getFileExtensions().get("diagram");
		if(diagramFileExt == null) {
			throw new IOException("Editor integration does not support diagramming: " + editorIntegration);
		}
		URI diagramUri = res.resolveRelatedFile(diagramFileExt);
		if(diagramUri == null) {
			throw new IOException("Cannot resolve diagram file (." + diagramFileExt + ")");
		} else if(diagramUri.isFile()) {
			// We assume that file URIs are temporary files,
			// so we must copy the other resources to the temporary folder
			// to preserve relative references in the diagram resource.
			URIConverter uriConverter =  res.getResource().getResourceSet().getURIConverter();
			File diagramFolder = new File(diagramUri.trimSegments(1).toFileString());
			String modelName = res.getURI().trimFileExtension().lastSegment();

			// Try to rename the diagram file to its original name
			File tmpDiagramFile = new File(diagramUri.toFileString());
			File renamedDiagramFile = new File(diagramFolder, modelName + "." + diagramFileExt);
			if(!renamedDiagramFile.exists() && tmpDiagramFile.renameTo(renamedDiagramFile)) {
				diagramUri = diagramUri.trimSegments(1).appendSegment(renamedDiagramFile.getName());
			}

			// Copy all related files to the diagram file's folder
			for(String fileKey : editorIntegration.getFileExtensions().keySet()) {
				if("diagram".equals(fileKey)) {
					// diagram was already copied
					continue;
				}
				String fileExt = editorIntegration.getFileExtensions().get(fileKey);
				File outFile = new File(diagramFolder, modelName + "." + fileExt);
				if(!outFile.exists()) {
					URI srcUri = res.resolveRelatedFile(fileExt);
					try (InputStream inStream = uriConverter.createInputStream(srcUri);
							OutputStream outStream = new FileOutputStream(outFile)) {
						IOUtil.transfer(inStream, outStream);
						outStream.flush();
					}
					outFile.deleteOnExit();
				}
			}
		}
		return diagramUri;
	}

	protected void changePartName(IEditorPart part, String name) throws CoreException {
		try {
			Method method = WorkbenchPart.class.getDeclaredMethod("setPartName", String.class);
			method.setAccessible(true);
			method.invoke(part, name);
		} catch(Exception e) {
			throw new CoreException(new Status(IStatus.WARNING, Activator.PLUGIN_ID, "Changing part names failed", e));
		};
	}

	protected void positionEditorParts(IEditorPart editorLeft, IEditorPart editorRight, IEditorPart editorAncestor) {
		if(editorLeft == null || editorRight == null) {
			return;
		}
		WorkbenchPage page = (WorkbenchPage) editorLeft.getSite().getPage();
		EPartService partService = page.getCurrentPerspective().getContext().get(EPartService.class);
		MPartSashContainerElement relTo = (MPartSashContainerElement)partService.getActivePart().getParent();

		MPart partLeft = ((EditorSite)editorLeft.getSite()).getModel();
		MPart partRight = ((EditorSite)editorRight.getSite()).getModel();
		MPart partAncestor = editorAncestor == null ? null : ((EditorSite)editorAncestor.getSite()).getModel();
		if(partLeft == null || partRight == null) {
			return;
		}

		partLeft.setToBeRendered(true);
		partRight.setToBeRendered(true);
		if(partAncestor != null) {
			partAncestor.setToBeRendered(true);
		}

		MPartStack toInsertLeft = MBasicFactory.INSTANCE.createPartStack();
		if (!toInsertLeft.getChildren().contains(partLeft))
			toInsertLeft.getChildren().add(partLeft);
		toInsertLeft.setSelectedElement(partLeft);

		MPartStack toInsertRight = MBasicFactory.INSTANCE.createPartStack();
		if (!toInsertRight.getChildren().contains(partRight))
			toInsertRight.getChildren().add(partRight);
		toInsertRight.setSelectedElement(partRight);

		MPartStack toInsertAncestor = null;
		if(partAncestor != null) {
			toInsertAncestor = MBasicFactory.INSTANCE.createPartStack();
			if (!toInsertAncestor.getChildren().contains(partAncestor))
				toInsertAncestor.getChildren().add(partAncestor);
			toInsertAncestor.setSelectedElement(partAncestor);
		}

		EModelService modelService = page.getCurrentPerspective().getContext().get(EModelService.class);
		if(toInsertAncestor != null) {
			modelService.insert(toInsertAncestor, relTo, EModelService.BELOW, 0.25f);
		}
		modelService.insert(toInsertLeft, toInsertAncestor != null ? toInsertAncestor : relTo, EModelService.BELOW, 0.25f);
		modelService.insert(toInsertRight, toInsertLeft, EModelService.RIGHT_OF, 0.5f);
	}

	protected void updateEnabledState() {
		setEnabled(differencer.getLeft() != null
				&& differencer.getLeft().getResource() != null
				&& differencer.getRight() != null
				&& differencer.getRight().getResource() != null);
	}
}
