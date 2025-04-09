package  org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.part;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.ui.commands.CommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.CreateCommand;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest.ViewDescriptor;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.gmf.runtime.emf.commands.core.command.CompositeTransactionalCommand;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.LogHelper;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * @since 3.2
 */
public abstract class DefaultCreateShortcutHandler extends AbstractHandler {

	private final LogHelper myLogHelper;

	private final PreferencesHint myPreferencesHint;

	public DefaultCreateShortcutHandler(LogHelper logHelper, PreferencesHint preferencesHint) {
		myLogHelper = logHelper;
		myPreferencesHint = preferencesHint;
	}

	public abstract ICommand createShortcutDecorationCommand(View view, TransactionalEditingDomain editingDomain, List<CreateViewRequest.ViewDescriptor> descriptors);

	public abstract DefaultElementChooserDialog createChooserDialog(Shell parentShell, View view);

	public Object execute(org.eclipse.core.commands.ExecutionEvent event) throws org.eclipse.core.commands.ExecutionException {
		IEditorPart diagramEditor = HandlerUtil.getActiveEditorChecked(event);
		Shell shell = diagramEditor.getEditorSite().getShell();
		assert diagramEditor instanceof DiagramEditor;
		TransactionalEditingDomain editingDomain = ((DiagramEditor) diagramEditor).getEditingDomain();
		ISelection selection = HandlerUtil.getCurrentSelectionChecked(event);
		assert selection instanceof IStructuredSelection;
		assert ((IStructuredSelection) selection).size() == 1;
		assert ((IStructuredSelection) selection).getFirstElement() instanceof EditPart;
		EditPart selectedDiagramPart = (EditPart) ((IStructuredSelection) selection).getFirstElement();
		final View view = (View) selectedDiagramPart.getModel();

		Point d2dCursorBeforeDialog = getLastClickPosition(shell, event);
		DefaultElementChooserDialog chooserDialog = createChooserDialog(shell, view);
		int result = chooserDialog.open();
		if (result != Window.OK) {
			return null;
		}

		List<EObject> allSelectedObjects = getEObjectsByURI(chooserDialog.getSelectedModelElementURIs(), editingDomain);
		if (allSelectedObjects.isEmpty()) {
			return null;
		}

		DropObjectsRequest request = createDragDropRequest(allSelectedObjects, d2dCursorBeforeDialog);
		Command gefCommand = selectedDiagramPart.getCommand(request);
		ICommand iCommand;
		if (gefCommand != null && gefCommand.canExecute()) {
			iCommand = new CommandProxy(gefCommand);
		} else {
			iCommand = createCompositeCommand(allSelectedObjects, view, editingDomain);
		}

		if (iCommand != null) {
			try {
				OperationHistoryFactory.getOperationHistory().execute(iCommand, new NullProgressMonitor(), null);
			} catch (ExecutionException e) {
				myLogHelper.logError("Unable to create shortcut", e); //$NON-NLS-1$
				throw e;
			}
		}

		return null;
	}

	private List<EObject> getEObjectsByURI(List<URI> selectedModelElementURIs, TransactionalEditingDomain editingDomain) {
		List<EObject> result = new ArrayList<EObject>();
		for (URI nextURI : selectedModelElementURIs) {
			try {
				EObject next = editingDomain.getResourceSet().getEObject(nextURI, true);
				if (next != null) {
					result.add(next);
				}
			} catch (WrappedException e) {
				myLogHelper.logError("Exception while loading object: " + nextURI.toString(), e); //$NON-NLS-1$
			}
		}
		return result;
	}

	private CompositeTransactionalCommand createCompositeCommand(List<EObject> allSelectedObjects, View view, TransactionalEditingDomain editingDomain) {
		CompositeTransactionalCommand result = new CompositeTransactionalCommand(editingDomain, "Add Shortcuts");
		List<ViewDescriptor> goodDescriptors = new ArrayList<CreateViewRequest.ViewDescriptor>(allSelectedObjects.size());
		for (EObject next : allSelectedObjects) {
			CreateViewRequest.ViewDescriptor viewDescriptor = new CreateViewRequest.ViewDescriptor(new EObjectAdapter(next), Node.class, null, myPreferencesHint);
			ICommand createCommand = new CreateCommand(editingDomain, viewDescriptor, view);
			if (createCommand.canExecute()) {
				result.add(createCommand);
				goodDescriptors.add(viewDescriptor);
			}
		}
		if (result.isEmpty()) {
			return null;
		}
		ICommand addDecoration = createShortcutDecorationCommand(view, editingDomain, goodDescriptors);
		if (addDecoration != null) {
			result.add(addDecoration);
		}
		return result;
	}

	private DropObjectsRequest createDragDropRequest(List<EObject> allSelectedObjects, Point d2dCursorBeforeDialog) {
		DropObjectsRequest req = new DropObjectsRequest();
		req.setObjects(allSelectedObjects);
		req.setAllowedDetail(DND.DROP_COPY);
		req.setRequiredDetail(DND.DROP_COPY);
		req.setLocation(d2dCursorBeforeDialog);
		return req;
	}

	private Point getLastClickPosition(Shell shell, ExecutionEvent event) {
		Point fromSourceProvider = (Point) HandlerUtil.getVariable(event, LastClickPositionProvider.SOURCE_LAST_CLICK);
		if (fromSourceProvider != null) {
			return fromSourceProvider;
		}
		return new Point(0, 0);
	}
}
