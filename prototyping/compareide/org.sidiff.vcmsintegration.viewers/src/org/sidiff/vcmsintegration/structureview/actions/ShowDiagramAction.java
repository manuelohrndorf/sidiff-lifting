package org.sidiff.vcmsintegration.structureview.actions;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import org.eclipse.core.runtime.Assert;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartSashContainerElement;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.impl.BasicFactoryImpl;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.EditorSite;
import org.eclipse.ui.internal.WorkbenchPage;
import org.eclipse.ui.part.WorkbenchPart;
import org.sidiff.vcmsintegration.Activator;
import org.sidiff.vcmsintegration.ViewerRegistry;
import org.sidiff.vcmsintegration.contentprovider.SiLiftStructuredViewerContentProvider;
import org.sidiff.vcmsintegration.editor.access.IntegrationEditorAccess;
import org.sidiff.vcmsintegration.editor.extension.IEditorIntegration;
import org.sidiff.vcmsintegration.remote.CompareResource;
import org.sidiff.vcmsintegration.remote.svn.SVNAccess;
import org.sidiff.vcmsintegration.structureview.SiLiftStructureMergeViewer;

/**
 * This action tries to open diagram files corresponding with the
 * {@link CompareResource} delivered by the
 * {@link SiLiftStructuredViewerContentProvider}. The behaviour of the action
 * depends on the type of {@link CompareResource}. Possible types are local, git
 * and svn.
 * 
 */
public class ShowDiagramAction extends Action {
	
	/**
	 * Provides {@link CompareResource} for the two given input resources that
	 * are being compared.
	 */
	private SiLiftStructuredViewerContentProvider contentProvider;

	/**
	 * Creates a new {@link ShowDiagramAction} with a
	 * {@link SiLiftStructuredViewerContentProvider} and sets the description
	 * and icon for the toolbar used in the {@link SiLiftStructureMergeViewer}.
	 * 
	 * @param contentProvider
	 */
	public ShowDiagramAction(SiLiftStructuredViewerContentProvider contentProvider) {
		Assert.isNotNull(contentProvider);
		this.setText("Open Diagram");
		this.setImageDescriptor(Activator.getImageDescriptor(Activator.IMAGE_SHOW_DIAGRAM));
		this.contentProvider = contentProvider;
	}

	/**
	 * The main implementation on the {@link ShowDiagramAction}.
	 */
	@Override
	public void run() {
		// get the compare resources
		CompareResource left = contentProvider.getLeft();
		CompareResource right = contentProvider.getRight();
		CompareResource ancestor = contentProvider.getAncestor();

		// the show diagram action does not support git
		// TODO: is this still a problem?
		/*if (left.isGit() || right.isGit()) {
			MessageDialog.openWarning(null, "Open Diagram", "Remote Resources in Git Repositories are not supported!");
			return;
		}*/

		// get the uris
		// TODO: improve uri conversion
		// TODO: getResource() might be null
		URI uriAncestor = null;
		URI uriLeft = URI.createPlatformResourceURI(left.getResource().getURI().toString(), true);
		URI uriRight = URI.createPlatformResourceURI(right.getResource().getURI().toString(), true);
		if (ancestor != null) {
			// TODO: why different for ancestor?
			uriAncestor = ancestor.getResource().getURI();
		}

		// get the editor integration for given uris
		IntegrationEditorAccess access = IntegrationEditorAccess.getInstance();
		IEditorIntegration integrationLeft = access.getIntegrationEditorForModelOrDiagramFile(uriLeft);
		IEditorIntegration integrationRight = access.getIntegrationEditorForModelOrDiagramFile(uriRight);
		IEditorIntegration integrationAncestor = null;
		if (uriAncestor != null)
			integrationAncestor = access.getIntegrationEditorForModelOrDiagramFile(uriAncestor);
		try {
			uriLeft = getURIForCompareResource(left, integrationLeft.getFileExtensions());
			uriRight = getURIForCompareResource(right, integrationRight.getFileExtensions());
			// FIXME: the serialization of the ancestor causes error. The problem
			// is the type of the revision.
			if (ancestor != null) {
				// uriAncestor = getURIForCompareResource(ancestor)
				uriAncestor = null;
			} else {
				uriAncestor = null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// open the diagrams
		IEditorPart first = null;
		IEditorPart second = null;
		if (integrationLeft != null && uriLeft != null)
			first = integrationLeft.openDiagramForModel(uriLeft);
		if (integrationRight != null && uriRight != null) {
			second = integrationRight.openDiagramForModel(uriRight);
		}
		
		// TODO: first and second might be null here, improve general error handling of actions

		// Changes the tab names via reflection
		try {
			Method method = WorkbenchPart.class.getDeclaredMethod("setPartName", String.class);
			method.setAccessible(true);
			method.invoke(first, "Left");
			method.invoke(second, "Right");
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

		if (integrationAncestor != null && uriAncestor != null)
			integrationAncestor.openDiagramForModel(uriAncestor);

		first.addPropertyListener(new IPropertyListener() {
			@Override
			public void propertyChanged(Object source, int propId) {
				ViewerRegistry.getInstance().getStructureMergeViewer().onRefreshNotify();
			}
		});

		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().activate(first);

		WorkbenchPage page = (WorkbenchPage) first.getSite().getPage();
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
		if (first != null) {
			partA = ((EditorSite) first.getSite()).getModel();
		}

		MPart partB = null;
		if (second != null) {
			partB = ((EditorSite) second.getSite()).getModel();
		}

		if (partA != null)
			partA.setToBeRendered(true);
		if (partB != null)
			partB.setToBeRendered(true);

		EModelService modelService = page.getCurrentPerspective().getContext().get(EModelService.class);

		if (toInsertA == null) {
			toInsertA = BasicFactoryImpl.eINSTANCE.createPartStack();
		}

		MPartStack toInsertAStack = (MPartStack) toInsertA;
		if (partA != null && !toInsertAStack.getChildren().contains(partA))
			toInsertAStack.getChildren().add(partA);
		if (partA != null)
			toInsertAStack.setSelectedElement(partA);

		if (toInsertB == null) {
			toInsertB = BasicFactoryImpl.eINSTANCE.createPartStack();
		}

		MPartStack toInsertBStack = (MPartStack) toInsertB;
		if (partB != null && !toInsertBStack.getChildren().contains(partB))
			toInsertBStack.getChildren().add(partB);
		if (partB != null)
			toInsertBStack.setSelectedElement(partB);

		if (insertA)
			modelService.insert(toInsertA, relTo, EModelService.RIGHT_OF, 0.25f);
		if (insertB)
			modelService.insert(toInsertB, toInsertA, EModelService.BELOW, 0.5f);
	}

	/**
	 * Depending on the type of the {@link CompareResource} this method returns
	 * the {@link URI} of a local or temporary file. Temporary files are
	 * serialized by the {@link SVNAccess} class.
	 * 
	 * @param compareResource
	 * @param all file types required by the {@link IEditorIntegration}
	 * @return the {@link URI} of the modelFile
	 * @throws IOException
	 */
	private URI getURIForCompareResource(CompareResource compareResource, Map<String, String> extensions) throws IOException {
		// TODO: implementation of this method???
		return compareResource.getResource().getURI();
		/*if (compareResource.isLocal()) {
			return URI.createPlatformResourceURI(compareResource.getResource().getURI().toString(), true);
		} else if (compareResource.isSvn()) {
			IRepositoryResource repositoryResource = ((IResourcePropertyProvider) compareResource.getTypedElement()).getRemote();
			SVNAccess access = new SVNAccess(repositoryResource);
			return access.serializeRepositoryResource(extensions);
		} else {
			return null;
		}*/
	}
}
