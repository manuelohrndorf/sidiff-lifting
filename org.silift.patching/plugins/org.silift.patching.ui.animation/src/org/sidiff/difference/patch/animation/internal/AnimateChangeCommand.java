package org.sidiff.difference.patch.animation.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.core.commands.DeleteCommand;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.core.services.ViewService;
import org.eclipse.gmf.runtime.diagram.core.util.ViewRefactorHelper;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewRequest.ConnectionViewDescriptor;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest.ViewDescriptor;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.core.util.EMFCoreUtil;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.emf.type.core.ClientContextManager;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IClientContext;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.emf.type.core.ISpecializationType;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.sidiff.difference.patch.animation.GMFAnimation.EditorMatching;
import org.sidiff.difference.symmetric.Correspondence;
import org.sidiff.difference.symmetric.SymmetricFactory;

public class AnimateChangeCommand extends AbstractTransactionalCommand {

	private static List<PendingAdd> pendingAdds = new ArrayList<PendingAdd>();
	
	private Notification notification = null;
	private EditorMatching editorMatching = null;

	public AnimateChangeCommand(TransactionalEditingDomain domain, String label, List<IFile> affectedFiles, Notification notification, EditorMatching matching) {
		super(domain, label, affectedFiles);
		this.notification = notification;
		this.editorMatching = matching;
	}

	@Override
	public boolean canExecute() {
		return true;
	}

	@Override
	public boolean canUndo() {
		return false;
	}

	private void execute_unset() {
		if(notification.getNotifier() instanceof EObject && notification.getFeature() != null){
			ViewRefactorHelper refactorHelper = new ViewRefactorHelper();

			EObject changedObject = (EObject) notification.getNotifier();
			EStructuralFeature feature = (EStructuralFeature) notification.getFeature();
			EObject viewedObject = null;
			
			if(editorMatching.matching != null){
				viewedObject = editorMatching.matching.getCorrespondingObjectInB(changedObject);
				viewedObject.eUnset(feature);
			} else {
				viewedObject = changedObject;
			}

			refactorHelper.refactor(viewedObject, viewedObject);
		}
	}

	private void execute_set() {
		if(notification.getNotifier() instanceof EObject && notification.getFeature() != null && notification.getNewValue() != null){
			EObject changedObject = (EObject) notification.getNotifier();
			Object newValue = notification.getNewValue();
			EStructuralFeature feature = (EStructuralFeature) notification.getFeature();
			EObject viewedObject = changedObject;
			
			if(newValue instanceof EGenericType){
				return;
			}
			
			if(editorMatching.matching != null){
				if(newValue instanceof EObject){
					newValue = editorMatching.matching.getCorrespondingObjectInB((EObject) newValue);
				}
				viewedObject = editorMatching.matching.getCorrespondingObjectInB(changedObject);
				viewedObject.eSet(feature, newValue);
			}
			
			if(newValue instanceof EObject){
				for(PendingAdd pendingAdd : pendingAdds){
					if(viewedObject == pendingAdd.newObject){
						createEdge(pendingAdd.adapter, (Diagram) pendingAdd.containerView, pendingAdd.semanticHint, pendingAdd.changedContainer, (EObject) newValue);
					}
				}
				pendingAdds.clear();	
			} 
			

//			Set<EObject> eObjects = new HashSet<EObject>();
//			eObjects.add(changedObject);
//			SelectionDecorationFacade.decorate(eObjects);
		}
	}

	private void execute_remove_many() {
		// TODO Auto-generated method stub
		
	}

	private void execute_remove() {
		final EObject removedObject = (EObject) notification.getOldValue();
		final EReference reference = (EReference) notification.getFeature();
		if(reference == null){
			return;
		}
		final EClass referenceClass = reference.getEContainingClass();
		final EObject changedContainer = (EObject) notification.getNotifier();
		
		EObject viewedContainer = changedContainer;
		EObject removedViewedObject = removedObject;
		
		if(editorMatching.matching != null){
			viewedContainer = editorMatching.matching.getCorrespondingObjectInB(changedContainer);
			removedViewedObject = editorMatching.matching.getCorrespondingObjectInB(removedObject);
		}
		
		if(reference.isContainment()){
			View removedView = getReferencingView(removedViewedObject);

			if(removedView != null){
				DeleteCommand deleteCommand = new DeleteCommand(removedView);
				ICommandProxy command = new ICommandProxy(deleteCommand);

				editorMatching.editor.getDiagramEditDomain().getDiagramCommandStack().execute(command);
			}
		} else {
			View containerView = getReferencingView(viewedContainer);
			
			ElementTypeRegistry typeRegistry = ElementTypeRegistry.getInstance();
			IClientContext clientContext = ClientContextManager.getInstance().getClientContextFor(containerView);
			
			IElementType elementType = null;
			ISpecializationType[] spezializations = typeRegistry.getSpecializationTypes(clientContext);
			for(ISpecializationType spezialization : spezializations){
				if(spezialization.getId().toLowerCase().contains(String.format("%s%s", referenceClass.getName().toLowerCase(), reference.getName().toLowerCase()))){
					elementType = spezialization;
				}
			}
			if(elementType instanceof IHintedType){
				String semanticHint = ((IHintedType) elementType).getSemanticHint();
				Diagram diagram = containerView.getDiagram();
				List<Edge> toBeDeleted = new ArrayList<Edge>();
				for(Object object : diagram.getPersistedEdges()){
					if(object instanceof Edge){
						Edge edge = (Edge) object;
						if(edge.getType().equalsIgnoreCase(semanticHint)){
							toBeDeleted.add(edge);
						}
					}
				}
				
				for(Edge edge : toBeDeleted){
					DeleteCommand deleteCommand = new DeleteCommand(edge);
					ICommandProxy command = new ICommandProxy(deleteCommand);

					editorMatching.editor.getDiagramEditDomain().getDiagramCommandStack().execute(command);
				}
			}
		}
	}

	private void execute_move() {
		// TODO Auto-generated method stub
		
	}

	private void execute_add_many() {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("unchecked")
	private void execute_add() {
		if(notification.getNotifier() instanceof EObject && notification.getFeature() != null && notification.getNewValue() != null){
			final EObject changedContainer = (EObject) notification.getNotifier();
			final EObject addedObject = (EObject) notification.getNewValue();
			final EReference reference = (EReference) notification.getFeature();
			final EClass referenceClass = reference.getEContainingClass();
			
			EObject viewedContainer = changedContainer;
			EObject addedViewedObject = addedObject;
			if(editorMatching.matching != null){
				viewedContainer = editorMatching.matching.getCorrespondingObjectInB(changedContainer);
				addedViewedObject = editorMatching.matching.getCorrespondingObjectInB(addedObject);
				// if there is no object corresponding object, this is a newly created object
				if(addedViewedObject == null){
					addedViewedObject = EcoreUtil.copy(addedObject);

					if(reference.isContainment()){
						Correspondence newCorrespondence = SymmetricFactory.eINSTANCE.createCorrespondence();
						newCorrespondence.setObjA(addedObject);
						newCorrespondence.setObjB(addedViewedObject);
						editorMatching.matching.addCorrespondence(newCorrespondence);
					}
				}
			}
			
			if(reference.isMany()){
				((List<EObject>) viewedContainer.eGet(reference)).add(addedViewedObject);
			} else {
				viewedContainer.eSet(reference, EcoreUtil.copy(addedViewedObject));
			}

			View containerView = getReferencingView(viewedContainer);

			IElementType elementType = null;
			EObject container = containerView;
			
			boolean finished = false;
			
			containerView = (View) container;
			if(containerView == null){
				return;
			}

			ElementTypeRegistry typeRegistry = ElementTypeRegistry.getInstance();
			IClientContext clientContext = ClientContextManager.getInstance().getClientContextFor(containerView);
			
			elementType = typeRegistry.getElementType(addedViewedObject, clientContext);
			ISpecializationType[] spezializations = typeRegistry.getSpecializationTypes(clientContext);
			for(ISpecializationType spezialization : spezializations){
				if(spezialization.getId().toLowerCase().contains(String.format("%s%s", referenceClass.getName().toLowerCase(), reference.getName().toLowerCase()))){
					elementType = spezialization;
				}
			}
			
			if(elementType instanceof IHintedType){
				String semanticHint = ((IHintedType) elementType).getSemanticHint();
				if(elementType instanceof ISpecializationType){
					IAdaptable adapter = new NullAdapter(elementType);
					finished = createEdge(adapter, containerView.getDiagram(), semanticHint, viewedContainer, addedViewedObject);
				}
				
				EObjectAndElementTypeAdapter adapter = new EObjectAndElementTypeAdapter(addedViewedObject, elementType);
				
				if(!finished){
					finished = createNode(adapter, containerView, semanticHint); 
				}
				
				if(!finished){
					PendingAdd pendingAdd = new PendingAdd();
					pendingAdd.adapter = adapter;
					pendingAdd.newObject = addedViewedObject;
					pendingAdd.changedContainer = viewedContainer;
					pendingAdd.containerView = containerView.getDiagram();
					pendingAdd.semanticHint = semanticHint;
					pendingAdds.add(pendingAdd);
				} 
			}
		}
	}
	
	private void printElementTypes(IElementType[] elementTypes){
		for(IElementType type : elementTypes){
			if(type instanceof IHintedType){
				System.out.println(String.format("HintedType: %s semanticHint: %s", type.getId(), ((IHintedType) type).getSemanticHint()));
			} else {
				System.out.println(String.format("Type: %s", type.getId()));
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private Collection<View> getChildViews(View containerView) {
		List<View> childViews = new ArrayList<View>(containerView.getPersistedChildren());
		List<View> temp = new ArrayList<View>();
		for(View childView : childViews){
			temp.addAll(getChildViews(childView));
		}
		childViews.addAll(temp);
		return childViews;
	}

	private boolean createNode(EObjectAdapter adapter, View containerView, String semanticHint){
		if(ViewService.getInstance().provides(Node.class, 
				adapter, 
				containerView, 
				semanticHint, 
				ViewUtil.APPEND, 
				true, 
				PreferencesHint.USE_DEFAULTS)){
			EditPart editPart = (EditPart) editorMatching.editor.getDiagramGraphicalViewer().getEditPartRegistry().get(containerView);
			EditPolicy createEditPolicy = editPart.getEditPolicy(EditPolicyRoles.CREATION_ROLE);
			ViewDescriptor viewDescriptor =  new ViewDescriptor(adapter, Node.class, semanticHint, true, PreferencesHint.USE_DEFAULTS);
			CreateViewRequest request = new CreateViewRequest(RequestConstants.REQ_CREATE, viewDescriptor);
			if(editPart.understandsRequest(request)){
				GridLayouter layouter = GridLayouter.getInstance();
				Point nextPosition = layouter.nextPosition();
				
				request.setSize(new Dimension(-1, -1));
				request.setLocation(nextPosition);
				
				Command command = editPart.getCommand(request);
				editorMatching.editor.getDiagramEditDomain().getDiagramCommandStack().execute(command);
			}

//			LayoutService.getInstance().layout(containerView, LayoutType.DEFAULT);
			return true;
		} 

		return false;
	}
	
	private boolean createEdge(IAdaptable adapter, Diagram diagram, String semanticHint, EObject sourceObject, EObject targetObject){
		try {
			if(ViewService.getInstance().provides(Edge.class, 
					adapter, 
					diagram, 
					semanticHint, 
					ViewUtil.APPEND, 
					true, 
					PreferencesHint.USE_DEFAULTS)){
				
				if(canCreateEdge(adapter)){
					View source = getReferencingView(sourceObject);
					View target = getReferencingView(targetObject);
					
					EditPart sourceEditPart = (EditPart) editorMatching.editor.getDiagramGraphicalViewer().getEditPartRegistry().get(source);
					EditPart targetEditPart = (EditPart) editorMatching.editor.getDiagramGraphicalViewer().getEditPartRegistry().get(target);

					CreateConnectionViewRequest request = new CreateConnectionViewRequest(new ConnectionViewDescriptor(adapter, semanticHint, ViewUtil.APPEND, true, PreferencesHint.USE_DEFAULTS));
					request.setSourceEditPart(sourceEditPart);
					request.setTargetEditPart(targetEditPart);
					
					request.setType(RequestConstants.REQ_CONNECTION_START);
					sourceEditPart.getCommand(request);
					request.setType(RequestConstants.REQ_CONNECTION_END);
					
					Command command = targetEditPart.getCommand(request);
					editorMatching.editor.getDiagramEditDomain().getDiagramCommandStack().execute(command);

//					LayoutService.getInstance().layout(diagram, LayoutType.DEFAULT);
					return true;
				}
			}
		} catch (Exception e) { e.printStackTrace(); }
		return false;
	}

	private boolean canCreateEdge(IAdaptable adapter) {
		Object object = null;
		if(adapter instanceof EObjectAdapter){
			object = ((EObjectAdapter) adapter).getRealObject();
		}
		
		if(object == null){
			return true;
		} else if(object instanceof EReference){
			EReference reference = (EReference) object;
			return reference.eContainer() != null && reference.getEType() != null;
		} 
		return false;
	}

	@SuppressWarnings("unchecked")
	private View getReferencingView(EObject element) {
		Collection<View> views = EMFCoreUtil.getReferencers(element, new EReference[]{NotationPackage.eINSTANCE.getView_Element()});

		// remove subviews since they will be refactored with their parent
		for (Iterator<View> i = views.iterator(); i.hasNext();) {
			View view = (View) i.next();
			
			EObject parent = null;
			while ((parent = view.eContainer()) instanceof View) { 
				if (views.contains(parent)) {
					i.remove();
					break;
				}
				view = (View) parent;
			}
		}
		
		if(views.size() > 0){
			return views.iterator().next();
		} else {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	private Collection<View> getReferencingViews(EObject element) {
		Collection<View> views = EMFCoreUtil.getReferencers(element, new EReference[]{NotationPackage.eINSTANCE.getView_Element()});		
		return views;
	}

	protected CommandResult doExecuteWithResult(IProgressMonitor monitor,
			IAdaptable info) throws ExecutionException {
		switch (notification.getEventType()) {
		case Notification.ADD:{
			execute_add();
			break;
		}
		case Notification.ADD_MANY:{
			execute_add_many();
			break;
		}
		case Notification.MOVE:{
			execute_move();
			break;
		}
		case Notification.REMOVE:{
			execute_remove();
			break;
		}
		case Notification.REMOVE_MANY:{
			execute_remove_many();
			break;
		}
		case Notification.SET:{
			execute_set();
			break;
		}
		case Notification.UNSET:{
			execute_unset();
			break;
		}
		}
		return CommandResult.newOKCommandResult();
	}

	public class EObjectAndElementTypeAdapter extends EObjectAdapter {

		private final IElementType elementType;
		
		public EObjectAndElementTypeAdapter(EObject subject, IElementType elementType) {
			super(subject);
			this.elementType = elementType;
		}

		@Override
		public Object getAdapter(@SuppressWarnings("rawtypes") Class adapter) {
			if (adapter.isInstance(elementType)) {
				return elementType;
			}
			return super.getAdapter(adapter);
		}
	}
	
 	public class NullAdapter implements IAdaptable {
 		
 		private final IElementType elementType;
 		
 		public NullAdapter(IElementType elementType){
 			this.elementType = elementType;
 		}
 		
		@SuppressWarnings("rawtypes")
		@Override
		public Object getAdapter(Class adapter) {
			if (adapter.isInstance(elementType)) {
				return elementType;
			}
			return null;
		}
 		
 	}
	
	public class PendingAdd {
		
		public View containerView = null;
		public EObjectAdapter adapter = null;
		public EObject newObject = null;
		public EObject changedContainer = null;
		public int visualID = 0;
		public String semanticHint = null;
		
		public PendingAdd(){
			super();
		}
		
		public PendingAdd(View containerView, EObjectAdapter adapter, EObject newObject, int visualID) {
			super();
			this.containerView = containerView;
			this.adapter = adapter;
			this.newObject = newObject;
			this.visualID = visualID;
		}
		
		public PendingAdd(View containerView, EObjectAdapter adapter, EObject newObject, String semanticHint) {
			super();
			this.containerView = containerView;
			this.adapter = adapter;
			this.newObject = newObject;
			this.semanticHint = semanticHint;
		}
	}
}
