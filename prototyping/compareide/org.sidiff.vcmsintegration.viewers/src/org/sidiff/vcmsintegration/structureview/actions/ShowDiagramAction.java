package org.sidiff.vcmsintegration.structureview.actions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.e4.ui.model.application.ui.basic.MBasicFactory;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartSashContainerElement;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.action.Action;
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
import org.sidiff.vcmsintegration.remote.CompareResource;
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
	}

	/**
	 * The main implementation of the {@link ShowDiagramAction}.
	 */
	@Override
	public void run() {
		CompareResource left = differencer.getModifiedLeft();
		CompareResource right = differencer.getModifiedRight();
		CompareResource ancestor = differencer.getAncestor();
		System.out.println("CompareResource:");
		System.out.println("  left:     " + left);
		System.out.println("  right:    " + right);
		System.out.println("  ancestor: " + ancestor);

		IntegrationEditorAccess access = IntegrationEditorAccess.getInstance();
		IEditorIntegration integrationLeft = null;
		if(left.getResource() != null) {
			integrationLeft = access.getIntegrationEditorForModel(left.getResource());
		}
		IEditorIntegration integrationRight = null;
		if(right.getResource() != null) {
			integrationRight = access.getIntegrationEditorForModel(right.getResource());
		}
		IEditorIntegration integrationAncestor = null;
		if(ancestor.getResource() != null) {
			integrationAncestor = access.getIntegrationEditorForModel(ancestor.getResource());
		}
		System.out.println("IEditorIntegration:");
		System.out.println("  left:     " + integrationLeft);
		System.out.println("  right:    " + integrationRight);
		System.out.println("  ancestor: " + integrationAncestor);
		if(integrationLeft == null || integrationRight == null) {
			MessageDialogUtil.showErrorDialog("Diagram not supported", "No suitable diagram or editor integration was found.");
			return;
		}

		URI uriLeft = left.resolveRelatedFile(integrationLeft.getFileExtensions().get("diagram"));
		URI uriRight = right.resolveRelatedFile(integrationRight.getFileExtensions().get("diagram"));
		URI uriAncestor = null;
		if(integrationAncestor != null) {
			uriAncestor = ancestor.resolveRelatedFile(integrationAncestor.getFileExtensions().get("diagram"));
		}
		System.out.println("URI:");
		System.out.println("  left:     " + uriLeft);
		System.out.println("  right:    " + uriRight);
		System.out.println("  ancestor: " + uriAncestor);

		IEditorPart partLeft = integrationLeft.openDiagram(uriLeft);
		IEditorPart partRight = integrationRight.openDiagram(uriRight);
		IEditorPart partAncestor = null;
		if(integrationAncestor != null && uriAncestor != null) {
			partAncestor = integrationAncestor.openDiagram(uriAncestor);
		}
		System.out.println("IEditorPart:");
		System.out.println("  left:     " + partLeft);
		System.out.println("  right:    " + partRight);
		System.out.println("  ancestor: " + partAncestor);
		if(partLeft == null || partRight == null) {
			MessageDialogUtil.showErrorDialog("Failed to open editors", "Diagram editor parts could not be created.");
			return;
		}

		// Changes the tab names via reflection
		changePartNames(partLeft, partRight, partAncestor);

		/*first.addPropertyListener(new IPropertyListener() {
			@Override
			public void propertyChanged(Object source, int propId) {
				ViewerRegistry.getInstance().getStructureMergeViewer().onRefreshNotify();
			}
		});*/

		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().activate(partLeft);

		positionEditorParts(partLeft, partRight);
	}

	protected void changePartNames(IEditorPart partLeft, IEditorPart partRight, IEditorPart partAncestor) {
		try {
			Method method = WorkbenchPart.class.getDeclaredMethod("setPartName", String.class);
			method.setAccessible(true);
			method.invoke(partLeft, "Left");
			method.invoke(partRight, "Right");
			if(partAncestor != null) {
				method.invoke(partAncestor, "Ancestor");
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}

	// TODO: does it work as expected? https://dzone.com/articles/programmatically-split-editor-
	protected void positionEditorParts(IEditorPart partLeft, IEditorPart partRight) {
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

	public void updateEnabledState() {
		setEnabled(differencer.getLeft().getResource() != null && differencer.getRight().getResource() != null);
	}
}
